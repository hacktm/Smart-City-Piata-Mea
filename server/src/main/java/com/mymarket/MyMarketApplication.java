package com.mymarket;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import org.hibernate.SessionFactory;

import com.mymarket.auth.ExampleAuthenticator;
import com.mymarket.cli.RenderCommand;
import com.mymarket.core.Template;
import com.mymarket.db.AverageDAO;
import com.mymarket.db.MarketDAO;
import com.mymarket.db.PersonDAO;
import com.mymarket.db.ProductDAO;
import com.mymarket.db.UserDAO;
import com.mymarket.health.TemplateHealthCheck;
import com.mymarket.resources.CalculateAveragesResource;
import com.mymarket.resources.MarketResource;
import com.mymarket.resources.MyMarketResource;
import com.mymarket.resources.PeopleResource;
import com.mymarket.resources.PersonResource;
import com.mymarket.resources.ProductResource;
import com.mymarket.resources.ProtectedResource;
import com.mymarket.resources.UserResource;
import com.mymarket.resources.ViewResource;

public class MyMarketApplication extends Application<MyMarketConfiguration> {

	public static final HibernateSessionFactoryBuilder builder = new HibernateSessionFactoryBuilder();

	public static void main(String[] args) throws Exception {
		new MyMarketApplication().run(args);
	}

	@Override
	public String getName() {
		return "mymarket";
	}

	@Override
	public void initialize(Bootstrap<MyMarketConfiguration> bootstrap) {
		bootstrap.addCommand(new RenderCommand());
		bootstrap.addBundle(new AssetsBundle());
		bootstrap.addBundle(new MigrationsBundle<MyMarketConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(
					MyMarketConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});
		bootstrap.addBundle(builder.getBundle());
		bootstrap.addBundle(new ViewBundle());
	}


	@Override
	public void run(MyMarketConfiguration configuration, Environment environment)
			throws ClassNotFoundException {

		final Template template = configuration.buildTemplate();
		environment.healthChecks().register("template",	new TemplateHealthCheck(template));

		setupJersey(environment, template);
		
		setupQuartz(configuration, environment);
	}

	private void setupQuartz(MyMarketConfiguration configuration,
			Environment environment) {
		environment.lifecycle().manage(	new QuartzManager(environment, configuration));
	}

	private void setupJersey(Environment environment, final Template template) {
		SessionFactory sessionFactory = builder.get();
		final PersonDAO dao = new PersonDAO(sessionFactory);
		final MarketDAO marketDao = new MarketDAO(sessionFactory);
		final ProductDAO productDao = new ProductDAO(sessionFactory);
		final AverageDAO averageDao = new AverageDAO(sessionFactory);
		final UserDAO userDao = new UserDAO(sessionFactory);
		
		JerseyEnvironment jersey = environment.jersey();
		jersey.register(new BasicAuthProvider<>(new ExampleAuthenticator(),
				"SUPER SECRET STUFF"));
		jersey.register(new MyMarketResource(template));
		jersey.register(new ViewResource());
		jersey.register(new ProtectedResource());
		jersey.register(new PeopleResource(dao));
		jersey.register(new PersonResource(dao));
		jersey.register(new MarketResource(marketDao, averageDao));
		jersey.register(new ProductResource(productDao));
		jersey.register(new CalculateAveragesResource(marketDao, productDao, averageDao));
		jersey.register(new UserResource(userDao));
		
	}

}
