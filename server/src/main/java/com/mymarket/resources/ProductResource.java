package com.mymarket.resources;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.mymarket.core.Market;
import com.mymarket.core.Product;
import com.mymarket.db.ProductDAO;
import com.sun.jersey.api.NotFoundException;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

	private final ProductDAO productDao;

	public ProductResource(ProductDAO productDao) {
		this.productDao = productDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public List<Product> getMarkets() {
		return productDao.findAll();
	}
	
	@GET
	@Path("/product/id")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Product getMarketById(@PathParam("Id") LongParam id) {
		final Optional<Product> product = productDao.findById(id.get());
		if (!product.isPresent()) {
			throw new NotFoundException("No such product.");
		}
		return product.get();
	}
	
	@PUT
	@UnitOfWork
	public void addProduct(String json){
		try {
			Product readValue = new ObjectMapper().readValue(json, Product.class);
			productDao.create(readValue);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
