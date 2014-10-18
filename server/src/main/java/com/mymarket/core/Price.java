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

@Entity
@Table(name = "price")
@NamedQueries({
    @NamedQuery(
        name = "com.mymarket.core.Price.findAll",
        query = "SELECT p FROM Price p"
    ),
    @NamedQuery(
        name = "com.mymarket.core.Price.findById",
        query = "SELECT p FROM Price p WHERE p.id = :id"
    ),
    @NamedQuery(
            name = "com.mymarket.core.Price.findByMarketAndDate",
            query = "SELECT p FROM Price p WHERE p.market.id = :market_id and p.date between :startdate and :enddate"
        )
})
public class Price {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "market_id")
	private Market market;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name = "date", nullable = false)
	private Date date;
	
	@Column(name = "value", nullable = false)
	private float value;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	
}