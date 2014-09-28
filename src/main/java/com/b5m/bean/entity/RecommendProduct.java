package com.b5m.bean.entity;

import java.io.Serializable;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Table;

@Table("t_recommend_product")
public class RecommendProduct extends DomainObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "title")
	private String title;

	@Column(name = "price")
	private String price;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "collection")
	private String collection;

	@Column(name = "url")
	private String url;

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice() {
		return price;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getCollection() {
		return collection;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}