package com.mymarket;

import org.hibernate.SessionFactory;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

import com.mymarket.core.Average;
import com.mymarket.core.Market;
import com.mymarket.core.Price;
import com.mymarket.core.Product;
import com.mymarket.core.User;

public class HibernateSessionFactoryBuilder {

	final HibernateBundle<MyMarketConfiguration> hibernateBundle = new HibernateBundle<MyMarketConfiguration>(
			Market.class, Product.class, Price.class, User.class, Average.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(
				MyMarketConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};;

	public HibernateSessionFactoryBuilder() {
		 
	}

	public SessionFactory get() {
		return hibernateBundle.getSessionFactory();
	}
	
	public HibernateBundle<MyMarketConfiguration> getBundle(){
		return hibernateBundle;
	}
}
