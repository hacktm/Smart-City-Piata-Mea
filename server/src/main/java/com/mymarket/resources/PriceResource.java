package com.mymarket.resources;

import java.io.IOException;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

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
import com.mymarket.core.Price;
import com.mymarket.db.PriceDAO;
import com.sun.jersey.api.NotFoundException;

@Path("/price")
@Produces(MediaType.APPLICATION_JSON)
public class PriceResource {

	private final PriceDAO priceDao;

	public PriceResource(PriceDAO priceDao) {
		this.priceDao = priceDao;
	}

//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@UnitOfWork
//	public List<Price> getMarkets() {
//		return priceDao.findAll();
//	}
	
	@GET
	@Path("/price/id")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Price getPriceById(@PathParam("Id") LongParam id) {
		final Optional<Price> price = priceDao.findById(id.get());
		if (!price.isPresent()) {
			throw new NotFoundException("No such price.");
		}
		return price.get();
	}
	
	@PUT
	@UnitOfWork
	public void addPrice(String json){
		try {
			Price readValue = new ObjectMapper().readValue(json, Price.class);
		//	priceDao.create(readValue);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
