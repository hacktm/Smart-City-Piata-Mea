package com.mymarket.schedule;

import io.dropwizard.hibernate.HibernateBundle;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.mymarket.MyMarketConfiguration;
import com.mymarket.core.Market;
import com.mymarket.core.Product;
import com.mymarket.db.AverageDAO;
import com.mymarket.db.AverageDAO.AverageCalculationResult;
import com.mymarket.db.MarketDAO;
import com.mymarket.db.ProductDAO;

public class CalculateAverages implements org.quartz.Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		SessionFactory sessionFactory = getSessionFactory(context);
		
		final MarketDAO marketDao = new MarketDAO(sessionFactory);
		final ProductDAO productDao = new ProductDAO(sessionFactory);
		final AverageDAO averageDao = new AverageDAO(sessionFactory);

		DateTime now = DateTime.now().withTimeAtStartOfDay();
		Date date = now.toDate();

		for (Market market : marketDao.findAll()) {
			List<AverageCalculationResult> results = averageDao.createAveragesByMarketAndDate(market, now);
			for (AverageCalculationResult result : results) {
				Product product = productDao.findById(result.getProductId()).get();
				averageDao.create(market, product, result.getValue(), date);
			}
		}

	}

	private SessionFactory getSessionFactory(JobExecutionContext context)
			throws JobExecutionException {
		HibernateBundle<MyMarketConfiguration> hibernateBundle = getHibernateBundle(context);

		SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
		return sessionFactory;
	}

	private HibernateBundle<MyMarketConfiguration> getHibernateBundle(
			JobExecutionContext context) throws JobExecutionException {
		SchedulerContext schedulerContext = null;
		try {
			schedulerContext = context.getScheduler().getContext();
		} catch (SchedulerException e1) {
			throw new JobExecutionException(e1);
		}

		@SuppressWarnings("unchecked")
		HibernateBundle<MyMarketConfiguration> hibernateBundle = (HibernateBundle<MyMarketConfiguration>) schedulerContext
				.get("hibernateBundle");
		return hibernateBundle;
	}
}
