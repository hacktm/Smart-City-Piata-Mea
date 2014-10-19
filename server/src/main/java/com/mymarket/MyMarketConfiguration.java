package com.mymarket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mymarket.core.Template;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class MyMarketConfiguration extends Configuration {
	@NotEmpty
	private String template;

	@NotEmpty
	private String defaultName = "Stranger";

	private Integer averageComputationMinutes = 1;

	@Valid
	@NotNull
	private DataSourceFactory database = new DataSourceFactory();

	@JsonProperty
	public String getTemplate() {
		return template;
	}

	@JsonProperty
	public void setTemplate(String template) {
		this.template = template;
	}

	@JsonProperty
	public String getDefaultName() {
		return defaultName;
	}

	@JsonProperty
	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public Template buildTemplate() {
		return new Template(template, defaultName);
	}

	@JsonProperty("database")
	public DataSourceFactory getDataSourceFactory() {
		return database;
	}

	@JsonProperty("database")
	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.database = dataSourceFactory;
	}

	@JsonProperty("averageComputationMinutes")
	public Integer getAverageComputationMinutes() {
		return averageComputationMinutes;
	}

	@JsonProperty("averageComputationMinutes")
	public void setAverageComputationMinutes(Integer averageComputationMinutes) {
		this.averageComputationMinutes = averageComputationMinutes;
	}

}
