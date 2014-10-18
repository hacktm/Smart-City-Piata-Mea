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
@Table(name = "product")
@NamedQueries({
    @NamedQuery(
        name = "com.mymarket.core.Product.findAll",
        query = "SELECT p FROM Product p"
    ),
    @NamedQuery(
        name = "com.mymarket.core.Product.findById",
        query = "SELECT p FROM Product p WHERE p.id = :id"
    )
})
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column
	String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
