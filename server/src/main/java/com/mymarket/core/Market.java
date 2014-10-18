package com.mymarket.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "market")
@NamedQueries({
		@NamedQuery(name = "com.mymarket.core.Market.findAll", query = "SELECT p FROM Market p"),
		@NamedQuery(name = "com.mymarket.core.Market.findById", query = "SELECT p FROM Market p WHERE p.id = :id") ,
		@NamedQuery(name = "com.mymarket.core.Market.findByName", query = "SELECT p FROM Market p WHERE p.name = :name") })

public class Market {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "location", nullable = false)
	private String location;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
