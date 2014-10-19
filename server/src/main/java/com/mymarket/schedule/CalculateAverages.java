package com.mymarket.schedule;

import io.dropwizard.hibernate.UnitOfWork;

import java.net.HttpURLConnection;
import java.net.URL;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculateAverages implements org.quartz.Job {
	Logger logger = LoggerFactory.getLogger(CalculateAverages.class);

	@UnitOfWork
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			logger.info("calculating averages");
			HttpURLConnection openConnection = (HttpURLConnection) new URL("http://localhost:8080/api/calculateAverages").openConnection();
			openConnection.setRequestMethod("GET");
			logger.info("finished averages " + openConnection.getResponseMessage());
		} catch (Exception e) {
			logger.error("Calculate averages", e);
		}
	}
}
