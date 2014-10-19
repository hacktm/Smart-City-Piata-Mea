package com.mymarket.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.mymarket.core.Product;
import com.sun.jersey.api.NotFoundException;

public class ProductDAO extends AbstractDAO<Product> {

	public ProductDAO(SessionFactory factory) {
		super(factory);
	}

	public Optional<Product> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public Product create(Product product) {
		return persist(product);
	}

	public List<Product> findAll() {
		return list(namedQuery("com.mymarket.core.Product.findAll"));
	}

	public Product update(Product productValues) {
		Optional<Product> findById = findById(productValues.getId());
		if (!findById.isPresent()) {
			throw new NotFoundException("Product missing");
		}
		Product found = findById.get();
		found.setImageUrl(!StringUtils.isEmpty(productValues.getImageUrl()) ? productValues.getImageUrl() : found.getImageUrl());
		found.setOrigin(!StringUtils.isEmpty(productValues.getOrigin()) ? productValues.getOrigin() : found.getOrigin());
		
		persist(found);
		
		return found;
	}
 
}