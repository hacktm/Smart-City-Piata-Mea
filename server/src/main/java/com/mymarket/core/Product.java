package com.mymarket.core;

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
	
	@Column(name = "name", nullable = false)
	String name;
	
	@Column(name = "description")
	String description;
	
	@Column(name = "image_url")
	String imageUrl;
	
	@Column(name = "origin", nullable = false)
	String origin;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	
}
