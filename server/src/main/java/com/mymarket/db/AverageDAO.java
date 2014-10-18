package com.mymarket.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.mymarket.core.Average;
import com.mymarket.core.Market;
import com.mymarket.core.Product;

public class AverageDAO extends AbstractDAO<Average> {
	public static class AverageCalculationResult {
		public AverageCalculationResult(Long productId2, Float average2) {
			this.productId = productId2;
			this.average = average2;
		}
		public Long productId;
		public Float average;
		
		public Long getProductId() {
			return productId;
		}

		public Float getValue() {
			return average;
		}
	}
	
	public AverageDAO(SessionFactory factory) {
		super(factory);
 	}

	public Optional<Average> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public Average create(Market market, Product product, float value, Date day) {
		Average average = new Average();
		average.setMarket(market);
		average.setProduct(product);
		average.setValue(value);
		average.setDate(day);
		return persist(average);
	}

	public List<Average> findAll() {
		return list(namedQuery("com.myAverage.core.Average.findAll"));
	}
	
	public List<Average> findAllByMarket(Long marketId) {
		Query namedQuery = namedQuery("com.myAverage.core.Average.findAllByMarket");
		namedQuery.setParameter("marketId", marketId);
		return list(namedQuery);
	}


	public List<Average> findByName(String name) {
		return list(namedQuery("com.myAverage.core.Average.findByName"));
	}
 
	@SuppressWarnings("unchecked")
	public List<AverageCalculationResult> createAveragesByMarketAndDate(Market market , DateTime now){
		
		DateTime tomorrowStart = now.plusDays( 1 ).withTimeAtStartOfDay();
		
		Query query = sqlQuery("SELECT PRODUCT_ID, SUM(VALUE) / COUNT(product_id) NUMBER FROM piata.price WHERE market_id = :market_id and date between :startdate and :enddate GROUP by pricE.PRODUCT_ID");
		query.setParameter(":market_id", market.getId());
		query.setParameter(":startdate", now.toDate());
		query.setParameter(":enddate", tomorrowStart.toDate());
		List<AverageCalculationResult> results = new ArrayList<AverageCalculationResult> ();
		
		for (Object[] row : (List<Object[]>)query.list()) {
			Long productId = ((Number)row[0]).longValue();
			Float average2 = ((Number)row[1]).floatValue();
			results.add(new AverageCalculationResult(productId, average2));
		}
		return results;
	}
	
	public Query sqlQuery(String queryString) {
		return currentSession().createSQLQuery(queryString);
	}
}
