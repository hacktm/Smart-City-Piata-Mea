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
import com.mymarket.core.Average;
import com.mymarket.core.Market;
import com.mymarket.db.AverageDAO;
import com.mymarket.db.MarketDAO;
import com.sun.jersey.api.NotFoundException;

@Path("/market")
@Produces(MediaType.APPLICATION_JSON)
public class MarketResource {

	private final MarketDAO marketDao;
	private final AverageDAO averageDao;

	public MarketResource(MarketDAO marketDao2, AverageDAO averageDao) {
		this.marketDao = marketDao2;
		this.averageDao = averageDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public List<Market> getMarkets() {
		return marketDao.findAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Market getMarketById(@PathParam("id") LongParam id) {
		final Optional<Market> market = marketDao.findById(id.get());
		if (!market.isPresent()) {
			throw new NotFoundException("No such market.");
		}
		return market.get();
	}
	
	@GET
	@Path("/{id}/product")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public List<Average> getMarketAveragesById(@PathParam("id") LongParam id) {
		final List<Average> marketAverages = averageDao.findAllByMarket(id.get());
		if (marketAverages.isEmpty()) {
			throw new NotFoundException("No such market.");
		}
		return marketAverages;
	}
	
	@PUT
	@UnitOfWork
	public void addMarket(String json){
		try {
			Market readValue = new ObjectMapper().readValue(json, Market.class);
			marketDao.create(readValue);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
