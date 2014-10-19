package com.mymarket;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

import java.util.Arrays;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mymarket.core.Average;
import com.mymarket.core.Market;
import com.mymarket.core.Product;
import com.mymarket.schedule.CalculateAverages;

public class QuartzManager implements Managed {
	Logger logger = LoggerFactory.getLogger(QuartzManager.class);
	private MyMarketConfiguration configuration;
	private Environment environment;

	public QuartzManager(Environment environment,
			MyMarketConfiguration configuration) {
		this.environment = environment;
		this.configuration = configuration;
	}

	@Override
	public void start() throws Exception {
		initializeQuartz();
	}

	@Override
	public void stop() throws Exception {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.shutdown(true);
	}

	private void initializeQuartz() throws SchedulerException {
		// Grab the Scheduler instance from the Factory
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		// and start it off
		scheduler.start();

		Trigger triggerByMinute = TriggerUtils.makeMinutelyTrigger(Integer
				.valueOf(configuration.getAverageComputationMinutes()));
		triggerByMinute.setName("computeAveragesEvery");
		JobDetail job = new JobDetail("computeAverages",
				CalculateAverages.class);

		// Tell quartz to schedule the job using our trigger
		scheduler.scheduleJob(job, triggerByMinute);
	}

}
