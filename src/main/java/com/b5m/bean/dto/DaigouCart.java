package com.b5m.bean.dto;

import java.io.Serializable;

public class DaigouCart implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 用户id
	private String uid;
	// cookieid
	private String cookieid;
	// 商品title
	private String title;
	// DOCID
	private String productId;
	// 商品链接地址
	private String url;
	// 商品图片
	private String image;
	// 商品价格
	private String price;
	// 均价
	private String priceAvg;
	// 商家
	private String source;
	// 0 表示商品下架 1表示成功
	private int status;

	private String goodsSpec;

	private String type;

	private Integer count = 1;

	private String channel;
	
	// 第三方网站图片
	private String goodsOriginImgUrl;
	
	private String skuId;
	
	private String outsideLink;
	
	// 来源
	// 1 值得买
	/*
	 * 2 b5t降价推荐 3 首页爆款 4 WAP 5 淘特价 6 团购 7 淘沙 8 APP 9 韩国馆
	 */
	private String origin;

	private Integer direct_buy = 0;

	// 是否免邮
	private Integer is_postage = 0;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCookieid() {
		return cookieid;
	}

	public void setCookieid(String cookieid) {
		this.cookieid = cookieid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPriceAvg() {
		return priceAvg;
	}

	public void setPriceAvg(String priceAvg) {
		this.priceAvg = priceAvg;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getGoodsSpec() {
		return goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannel() {
		return this.channel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Integer getDirect_buy() {
		return direct_buy;
	}

	public void setDirect_buy(Integer direct_buy) {
		this.direct_buy = direct_buy;
	}

	public Integer getIs_postage() {
		return is_postage;
	}

	public void setIs_postage(Integer is_postage) {
		this.is_postage = is_postage;
	}

	public String getGoodsOriginImgUrl() {
		return goodsOriginImgUrl;
	}

	public void setGoodsOriginImgUrl(String goodsOriginImgUrl) {
		this.goodsOriginImgUrl = goodsOriginImgUrl;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getOutsideLink() {
		return outsideLink;
	}

	public void setOutsideLink(String outsideLink) {
		this.outsideLink = outsideLink;
	}
	
}
