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

import com.mymarket.auth.ExampleAuthenticator;
import com.mymarket.cli.RenderCommand;
import com.mymarket.core.Market;
import com.mymarket.core.Template;
import com.mymarket.db.MarketDAO;
import com.mymarket.db.PersonDAO;
import com.mymarket.health.TemplateHealthCheck;
import com.mymarket.resources.MarketResource;
import com.mymarket.resources.MyMarketResource;
import com.mymarket.resources.PeopleResource;
import com.mymarket.resources.PersonResource;
import com.mymarket.resources.ProtectedResource;
import com.mymarket.resources.ViewResource;

public class MyMarketApplication extends Application<MyMarketConfiguration> {
    public static void main(String[] args) throws Exception {
        new MyMarketApplication().run(args);
    }

    private final HibernateBundle<MyMarketConfiguration> hibernateBundle =
            new HibernateBundle<MyMarketConfiguration>(Market.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(MyMarketConfiguration configuration) {
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
            public DataSourceFactory getDataSourceFactory(MyMarketConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(MyMarketConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {
        final PersonDAO dao = new PersonDAO(hibernateBundle.getSessionFactory());
        final MarketDAO marketDao = new MarketDAO(hibernateBundle.getSessionFactory());
        
        final Template template = configuration.buildTemplate();

        environment.healthChecks().register("template", new TemplateHealthCheck(template));

        environment.jersey().register(new BasicAuthProvider<>(new ExampleAuthenticator(),
                                                              "SUPER SECRET STUFF"));
        environment.jersey().register(new MyMarketResource(template));
        environment.jersey().register(new ViewResource());
        environment.jersey().register(new ProtectedResource());
        environment.jersey().register(new PeopleResource(dao));
        environment.jersey().register(new PersonResource(dao));
        environment.jersey().register(new MarketResource(marketDao));
        
    }
}
