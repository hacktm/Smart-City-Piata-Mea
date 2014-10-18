package com.mymarket.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "average")
@NamedQueries({
    @NamedQuery(
        name = "com.mymarket.core.Average.findAll",
        query = "SELECT p FROM Average p"
    ),
    @NamedQuery(
        name = "com.mymarket.core.Average.findAllByMarket",
        query = "SELECT p FROM Average p WHERE p.market.id = :market_id"
    )
})
public class Average {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "market_id", nullable = false, updatable = false)
	public Market market;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false, updatable = false)
	public Product product;

	@Column
	public Float value;

	@Column
	private Date date;

	@JsonProperty
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	@JsonProperty
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	@JsonProperty
	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public void setDate(Date day) {
		this.date = day;
	}
	@JsonProperty
	public Date getDate() {
		return this.date;
	}

}
