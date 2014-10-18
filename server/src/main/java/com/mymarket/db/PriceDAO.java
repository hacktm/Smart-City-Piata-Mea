package com.mymarket.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.mymarket.core.Market;
import com.mymarket.core.Price;
import com.mymarket.core.Product;

public class PriceDAO extends AbstractDAO<Price> {
	public PriceDAO(SessionFactory factory) {
		super(factory);
	}

	public Optional<Price> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public Price create(Market person, Product product, Date date, float value) {
		Price price = new Price();
		price.setDate(date);
		price.setValue(value);
		price.setProduct(product);
		price.setMarket(person);
		return persist(price);
	}

	public List<Price> findByMarketAndDate(Market market, DateTime now) {
		
		DateTime todayStart = now.withTimeAtStartOfDay();
		DateTime tomorrowStart = now.plusDays( 1 ).withTimeAtStartOfDay();
		
		Query query = namedQuery("com.mymarket.core.Price.findByMarketAndDate");
		query.setParameter(":market_id", market.getId());
		query.setParameter(":startdate", todayStart.toDate());
		query.setParameter(":enddate", tomorrowStart.toDate());

		return list(query);

	}

}
