package com.mymarket.core;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "market")
@NamedQueries({
		@NamedQuery(name = "com.mymarket.core.Market.findAll", query = "SELECT p FROM Market p"),
		@NamedQuery(name = "com.mymarket.core.Market.findById", query = "SELECT p FROM Market p WHERE p.id = :id"),
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

	@OneToMany
	private List<Average> averages = new ArrayList<Average> ();
	
 	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@JsonProperty
	public List<Average> getAverages() {
		return this.averages;
	}
	
	public void setAverages(List<Average> averages2) {
		this.averages = averages2;
	}

}
