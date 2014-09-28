package com.b5m.service.pricetrend;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class PriceTrend implements Serializable {
	private static final long serialVersionUID = 6023372447977130729L;
	private String docId = "";
	private BigDecimal rangePriceHigh;
	private BigDecimal rangePriceLow;
	private List<PricePerDay> pricePerDays = new ArrayList<PricePerDay>();

	public PriceTrend() {
	}

	public PriceTrend(String docId, String price, Date date) {
		super();
		this.docId = docId;
		this.rangePriceHigh = new BigDecimal(price);
		this.rangePriceLow = new BigDecimal(price);
		pricePerDays.add(new PricePerDay(date, price));
	}

	public void addPricePerDay(PricePerDay pricePerDay) {
		pricePerDays.add(pricePerDay);
	}

	public void removePricePerDay(PricePerDay pricePerDay) {
		pricePerDays.remove(pricePerDay);
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public BigDecimal getRangePriceHigh() {
		return rangePriceHigh;
	}

	public void setRangePriceHigh(BigDecimal rangePriceHigh) {
		this.rangePriceHigh = rangePriceHigh;
	}

	public BigDecimal getRangePriceLow() {
		return rangePriceLow;
	}

	public void setRangePriceLow(BigDecimal rangePriceLow) {
		this.rangePriceLow = rangePriceLow;
	}

	public List<PricePerDay> getPricePerDays() {
		return pricePerDays;
	}

	public void setPricePerDays(List<PricePerDay> pricePerDays) {
		this.pricePerDays = pricePerDays;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this) + "\n";
	}

}