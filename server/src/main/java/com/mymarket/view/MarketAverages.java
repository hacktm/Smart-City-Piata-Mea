package com.mymarket.view;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mymarket.core.Average;
import com.mymarket.core.Market;

public class MarketAverages {

	Market market;
	List<Average> averages;
	
	@JsonProperty
	public Market getMarket() {
		return market;
	}
	public void setMarket(Market market) {
		this.market = market;
	}
	
	@JsonProperty
	public List<Average> getAverages() {
		return averages;
	}
	public void setAverages(List<Average> averages) {
		this.averages = averages;
	}
	
	
}
