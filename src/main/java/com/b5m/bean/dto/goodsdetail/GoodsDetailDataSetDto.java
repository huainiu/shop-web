package com.b5m.bean.dto.goodsdetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.b5m.dao.domain.page.PageView;

public class GoodsDetailDataSetDto extends GoodsBaseDto {
	private static final long serialVersionUID = 1L;

	// 头部导航分类
	private String categoryValue;

	// 商品详情信息
	private Map<String, Object> goodsDetailInfoDto;

	// 商品最高价 重构的时候 移到商品详情里面
	private int minPrice = 0;

	// 商品最低价 重构的时候 移到商品详情里面
	private int maxPrice = 0;

	// 商品属性
	private Map<String, String> attributes;

	// 评论资源列表
	private List<Map<String, String>> commentResources;

	private PageView<Map<String, String>> commentPageView;

	// 商家详细信息
	private Map<String, Map<String, String>> splInfoMap;

	// 产品评分信息
	private RankDto rankDto;

	// 商家评论数
	private long spuCommentCount = 0;

	// 评论总结
	private List<String[]> comments;

	// 评论总数
	private Long commentsNum;

	// 重构的时候 用类表示
	private List<Map<String, String>> imageInfos;

	private List<ShopInfoDto> shopInfoList;

	private Map<String, String> lowestSource;

	private Map<String, List<ShopInfoDto>> normsShopInfoList;

	private List<String> norms;
	// 是否是快照数据
	private boolean isSnap = false;

	public Map<String, Object> getGoodsDetailInfoDto() {
		if (goodsDetailInfoDto == null) {
			return new HashMap<String, Object>();
		}
		return goodsDetailInfoDto;
	}

	public void setGoodsDetailInfoDto(Map<String, Object> goodsDetailInfoDto) {
		this.goodsDetailInfoDto = goodsDetailInfoDto;
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	/*
	 * public List<String> getSplService(){ if(splService == null) return new
	 * ArrayList<String>(); return splService; }
	 * 
	 * public void setSplService(List<String> splService){ this.splService =
	 * splService; }
	 */

	public List<Map<String, String>> getCommentResources() {
		if (commentResources == null) {
			return new ArrayList<Map<String, String>>();
		}
		return commentResources;
	}

	public void setCommentResources(List<Map<String, String>> commentResources) {
		this.commentResources = commentResources;
	}

	public Map<String, Map<String, String>> getSplInfoMap() {
		if (splInfoMap == null) {
			return new HashMap<String, Map<String, String>>();
		}
		return splInfoMap;
	}

	public void setSplInfoMap(Map<String, Map<String, String>> splInfoMap) {
		this.splInfoMap = splInfoMap;
	}

	public RankDto getRankDto() {
		if (rankDto == null) {
			return new RankDto();
		}
		return rankDto;
	}

	public void setRankDto(RankDto rankDto) {
		this.rankDto = rankDto;
	}

	public long getSpuCommentCount() {
		return spuCommentCount;
	}

	public void setSpuCommentCount(long spuCommentCount) {
		this.spuCommentCount = spuCommentCount;
	}

	public List<String[]> getComments() {
		if (comments == null) {
			comments = new ArrayList<String[]>();
		}
		return comments;
	}

	public void setComments(List<String[]> comments) {
		this.comments = comments;
	}

	public List<Map<String, String>> getImageInfos() {
		if (imageInfos == null) {
			imageInfos = new ArrayList<Map<String, String>>();
		}
		return imageInfos;
	}

	public void setImageInfos(List<Map<String, String>> imageInfos) {
		this.imageInfos = imageInfos;
	}

	public Map<String, String> getAttributes() {
		if (attributes == null) {
			attributes = new HashMap<String, String>();
		}
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public List<ShopInfoDto> getShopInfoList() {
		return shopInfoList;
	}

	public void setShopInfoList(List<ShopInfoDto> shopInfoList) {
		this.shopInfoList = shopInfoList;
	}

	public Long getCommentsNum() {
		return commentsNum;
	}

	public void setCommentsNum(Long commentsNum) {
		this.commentsNum = commentsNum;
	}

	public PageView<Map<String, String>> getCommentPageView() {
		return commentPageView;
	}

	public void setCommentPageView(PageView<Map<String, String>> commentPageView) {
		this.commentPageView = commentPageView;
	}

	public Map<String, String> getLowestSource() {
		return lowestSource;
	}

	public void setLowestSource(Map<String, String> lowestSource) {
		this.lowestSource = lowestSource;
	}

	public boolean isSnap() {
		return isSnap;
	}

	public void setSnap(boolean isSnap) {
		this.isSnap = isSnap;
	}

	public Map<String, List<ShopInfoDto>> getNormsShopInfoList() {
		return normsShopInfoList;
	}

	public void setNormsShopInfoList(Map<String, List<ShopInfoDto>> normsShopInfoList) {
		this.normsShopInfoList = normsShopInfoList;
	}

	public List<String> getNorms() {
		return norms;
	}

	public void setNorms(List<String> norms) {
		this.norms = norms;
	}

}
