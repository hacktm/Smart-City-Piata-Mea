package com.mymarket.resources;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.mymarket.core.Market;
import com.mymarket.db.MarketDAO;
import com.sun.jersey.api.NotFoundException;

@Path("/market")
@Produces(MediaType.APPLICATION_JSON)
public class MarketResource {

	private final MarketDAO marketDao;

	public MarketResource(MarketDAO marketDao2) {
		this.marketDao = marketDao2;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public List<Market> getMarkets() {
		return marketDao.findAll();
	}
	
	@GET
	@Path("/market/id")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Market getMarketById(@PathParam("Id") LongParam id) {
		final Optional<Market> market = marketDao.findById(id.get());
		if (!market.isPresent()) {
			throw new NotFoundException("No such market.");
		}
		return market.get();
	}
	
	@PUT
	@Path("/market")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public void addMarket(String json){
	}

}
