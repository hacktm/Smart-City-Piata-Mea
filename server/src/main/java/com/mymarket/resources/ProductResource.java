package com.mymarket.resources;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.mymarket.db.MarketDAO;
import com.mymarket.db.PriceDAO;
import com.mymarket.db.ProductDAO;
import com.mymarket.view.ProductAddView;
import com.sun.jersey.api.NotFoundException;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

	private final ProductDAO productDao;
	private final MarketDAO marketDao;
	private final PriceDAO priceDao;

	public ProductResource(ProductDAO productDao, MarketDAO marketDao,
			PriceDAO priceDao) {
		this.productDao = productDao;
		this.marketDao = marketDao;
		this.priceDao = priceDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public List<Product> getMarkets() {
		return productDao.findAll();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Product getProductById(@PathParam("id") LongParam id) {
		final Optional<Product> product = productDao.findById(id.get());
		if (!product.isPresent()) {
			throw new NotFoundException("No such product.");
		}
		return product.get();
	}

	@PUT
	@UnitOfWork
	public void addProduct(String json) {
		try {
			ProductAddView productAddValues = new ObjectMapper().readValue(
					json, ProductAddView.class);

			Product productValues = productAddValues.getProduct();

			Product product = productDao.create(productValues);
			Optional<Market> foundMarket = marketDao.findById(productAddValues
					.getMarketId());
			if (!foundMarket.isPresent()) {
				throw new NotFoundException("Piata lipsa");
			}

			Market market = foundMarket.get();
			priceDao.create(market, product, new Date(),
					productAddValues.getValue());

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Path("/{id}")
	@UnitOfWork
	public void editProduct(@PathParam("id") LongParam id, String json) {
		try {
			ProductAddView productAddValues = new ObjectMapper().readValue(
					json, ProductAddView.class);

			Product productValues = productAddValues.getProduct();
			productValues.setId(id.get());

			Product product = productDao.update(productValues);
			Optional<Market> foundMarket = marketDao.findById(productAddValues
					.getMarketId());
			if (!foundMarket.isPresent()) {
				throw new NotFoundException("Piata lipsa");
			}

			Market market = foundMarket.get();
			priceDao.create(market, product, new Date(),
					productAddValues.getValue());

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
