package com.b5m.service.ontimeprice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class SkuBean {
	//健值属性 值是price
	private JSONObject sku;
	
	private List<SkuProp> skuProps;
	
	private BigDecimal lowestPrice;
	
	private BigDecimal highestPrice;
	
	public JSONObject getSku() {
		return sku;
	}

	public void setSku(JSONObject sku) {
		this.sku = sku;
	}
	
	public void addSkuProp(SkuProp skuProp){
		if(skuProps == null){
			skuProps = new ArrayList<SkuProp>();
		}
		skuProps.add(skuProp);
	}
	
	public SkuProp getByName(String name){
		if(skuProps == null) return null;
		for(SkuProp skuProp : skuProps){
			if(name.equals(skuProp.getName())){
				return skuProp;
			}
		}
		return null;
	}

	public List<SkuProp> getSkuProps() {
		return skuProps;
	}

	public void setSkuProps(List<SkuProp> skuProps) {
		this.skuProps = skuProps;
	}

	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public BigDecimal getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(BigDecimal highestPrice) {
		this.highestPrice = highestPrice;
	}
	
}
