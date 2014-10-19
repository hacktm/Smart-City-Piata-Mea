package com.mymarket.resources;

import io.dropwizard.hibernate.UnitOfWork;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.joda.time.DateTime;

import com.mymarket.core.Market;
import com.mymarket.core.Product;
import com.mymarket.db.AverageDAO;
import com.mymarket.db.AverageDAO.AverageCalculationResult;
import com.mymarket.db.MarketDAO;
import com.mymarket.db.ProductDAO;

@Path("/calculateAverages")
public class CalculateAveragesResource {

	private final MarketDAO marketDao;
	private final AverageDAO averageDao;
	final ProductDAO productDao;

	public CalculateAveragesResource(MarketDAO marketDao2, ProductDAO productDao, AverageDAO averageDao) {
		this.marketDao = marketDao2;
		this.averageDao = averageDao;
		this.productDao = productDao;
	}

	@GET
	@UnitOfWork
	public void getMarkets() {

		DateTime now = DateTime.now().withTimeAtStartOfDay();
		Date date = now.toDate();

		for (Market market : marketDao.findAll()) {
			List<AverageCalculationResult> results = averageDao
					.createAveragesByMarketAndDate(market, now);
			for (AverageCalculationResult result : results) {
				Product product = productDao.findById(result.getProductId())
						.get();
				averageDao.create(market, product, result.getValue(), date);
			}
		}
	}
}
