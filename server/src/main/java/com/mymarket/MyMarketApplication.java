package com.mymarket;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

import com.mymarket.auth.ExampleAuthenticator;
import com.mymarket.cli.RenderCommand;
import com.mymarket.core.Average;
import com.mymarket.core.Market;
import com.mymarket.core.Price;
import com.mymarket.core.Product;
import com.mymarket.core.Template;
import com.mymarket.core.User;
import com.mymarket.db.AverageDAO;
import com.mymarket.db.MarketDAO;
import com.mymarket.db.PersonDAO;
import com.mymarket.db.ProductDAO;
import com.mymarket.db.UserDAO;
import com.mymarket.health.TemplateHealthCheck;
import com.mymarket.resources.MarketResource;
import com.mymarket.resources.MyMarketResource;
import com.mymarket.resources.PeopleResource;
import com.mymarket.resources.PersonResource;
import com.mymarket.resources.ProductResource;
import com.mymarket.resources.ProtectedResource;
import com.mymarket.resources.UserResource;
import com.mymarket.resources.ViewResource;
import com.mymarket.schedule.CalculateAverages;

public class MyMarketApplication extends Application<MyMarketConfiguration> {
	public static void main(String[] args) throws Exception {
		
		for (String arg :args) {
			
		}
		new MyMarketApplication().run(args);
	}

	final HibernateBundle<MyMarketConfiguration> hibernateBundle = new HibernateBundle<MyMarketConfiguration>(
			Market.class, Product.class, Price.class, User.class, Average.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(
				MyMarketConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

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
		bootstrap.addBundle(hibernateBundle);
		bootstrap.addBundle(new ViewBundle());
	}

	private void initializeQuartz() throws SchedulerException {
		// Grab the Scheduler instance from the Factory
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.getContext().put("hibernateBundle", hibernateBundle);

		// and start it off
		scheduler.start();

		Trigger fiveMinuteTrigger = TriggerUtils.makeMinutelyTrigger(5);
		fiveMinuteTrigger.setName("computeAveragesEvery5Minutes");
		JobDetail job = new JobDetail("computeAverages",
				CalculateAverages.class);

		// Tell quartz to schedule the job using our trigger
		scheduler.scheduleJob(job, fiveMinuteTrigger);
	}

	@Override
	public void run(MyMarketConfiguration configuration, Environment environment)
			throws ClassNotFoundException {
		final PersonDAO dao = new PersonDAO(hibernateBundle.getSessionFactory());
		final MarketDAO marketDao = new MarketDAO(
				hibernateBundle.getSessionFactory());
		final ProductDAO productDao = new ProductDAO(
				hibernateBundle.getSessionFactory());
		final AverageDAO averageDao = new AverageDAO(
				hibernateBundle.getSessionFactory());
	      final UserDAO userDao = new UserDAO(hibernateBundle.getSessionFactory());

		final Template template = configuration.buildTemplate();

		environment.healthChecks().register("template",
				new TemplateHealthCheck(template));

		environment.jersey().register(
				new BasicAuthProvider<>(new ExampleAuthenticator(),
						"SUPER SECRET STUFF"));
		environment.jersey().register(new MyMarketResource(template));
		environment.jersey().register(new ViewResource());
		environment.jersey().register(new ProtectedResource());
		environment.jersey().register(new PeopleResource(dao));
		environment.jersey().register(new PersonResource(dao));
		environment.jersey()
				.register(new MarketResource(marketDao, averageDao));
		environment.jersey().register(new ProductResource(productDao));
		environment.jersey().register(new UserResource(userDao));
		
		try {
			initializeQuartz();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
