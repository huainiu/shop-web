package com.b5m.bean.dto.shoplist;

import java.util.Map;

import com.b5m.bean.dto.PageSplitDto;
import com.b5m.bean.entity.filterAttr.Attibute;
import com.b5m.sf1.dto.req.SortSearchBean;

public class SuiSearchDto extends PageSplitDto {
	private static final long serialVersionUID = -8504077015373397005L;
	private String collectionName = "";
	// 关键字
	private String keyWord = "";
	// 最初搜索的关键字
	private String orignKeyWord = "";
	// 排序字段
	private String sortField = "";
	// 排序类型
	private String sortType = SortSearchBean.ASC;
	// 显示方式 Image:缩略图 | List:列表
	private String showType = "Image";
	// 分类值
	private String categoryValue = "";
	// 在多少分类里面进行查询
	private Integer autoSelectLimit;
	// 商家值
	private String sourceValue = "";
	// 品牌值
	private String brandValue = "";
	// 搜索类型,分为search和category
	private String searchType = "search";
	// 属性名字符串
	private String attrNames = "";
	// 属性值字符串
	private String attrValue = "";
	// DOCID
	private String docid = "";
	// 是否写入关键字
	private boolean logKeyword = true;
	// 价格下限
	private String priceFrom = "";
	// 价格上限
	private String priceTo = "";
	// 商家服务
	private String splServiceValue = "";
	// 是否获得属性group
	private boolean isGetAttrTree = true;
	// 点击商品的offset
	private int clickOffset = 0;
	// 过滤条件
	private String filterField = "";
	// 韩国馆过滤
	private String kaviFilter = "";
	// 0表示不免运费，1表示免运费
	private String isFreeDelivery = "";
	private boolean isCompare = false;
	// （is cash on delivery) 0表示不支持货到付款，1表示 货到付款
	private String isCOD = "";
	// 0表示水货，1表示正品行货
	private String isGenuine = "";
	// 0表示非最低价, 1表示最低价
	private String isLowPrice = "";
	private boolean isRequireRelated;
	private Integer queryAbbreviation;
	// 属性过滤 属性合并
	private Map<String, Attibute> filterMap;

	private Integer limit;

	public String getKaviFilter() {
		return kaviFilter;
	}

	public void setKaviFilter(String kaviFilter) {
		this.kaviFilter = kaviFilter;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getSortField() {
		return sortField;
	}

	public String getBrandValue() {
		return brandValue;
	}

	public void setBrandValue(String brandValue) {
		this.brandValue = brandValue;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public String getSourceValue() {
		return sourceValue;
	}

	public void setSourceValue(String sourceValue) {
		this.sourceValue = sourceValue;
	}

	public String getAttrNames() {
		return attrNames;
	}

	public void setAttrNames(String attrNames) {
		this.attrNames = attrNames;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	/*
	 * public boolean isLog_group_label() { return log_group_label; }
	 * 
	 * public void setLog_group_label(boolean logGroupLabel) { log_group_label =
	 * logGroupLabel; }
	 */

	public String getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(String priceFrom) {
		this.priceFrom = priceFrom;
	}

	public String getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(String priceTo) {
		this.priceTo = priceTo;
	}

	public boolean isLogKeyword() {
		return logKeyword;
	}

	public void setLogKeyword(boolean logKeyword) {
		this.logKeyword = logKeyword;
	}

	public String getSplServiceValue() {
		return splServiceValue;
	}

	public void setSplServiceValue(String splServiceValue) {
		this.splServiceValue = splServiceValue;
	}

	public boolean isGetAttrTree() {
		return isGetAttrTree;
	}

	public void setGetAttrTree(boolean isGetAttrTree) {
		this.isGetAttrTree = isGetAttrTree;
	}

	public int getClickOffset() {
		return clickOffset;
	}

	public void setClickOffset(int clickOffset) {
		this.clickOffset = clickOffset;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getFilterField() {
		return filterField;
	}

	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public Integer getAutoSelectLimit() {
		return autoSelectLimit;
	}

	public void setAutoSelectLimit(Integer autoSelectLimit) {
		this.autoSelectLimit = autoSelectLimit;
	}

	public String getIsFreeDelivery() {
		return isFreeDelivery;
	}

	public void setIsFreeDelivery(String isFreeDelivery) {
		this.isFreeDelivery = isFreeDelivery;
	}

	public String getIsCOD() {
		return isCOD;
	}

	public void setIsCOD(String isCOD) {
		this.isCOD = isCOD;
	}

	public String getIsGenuine() {
		return isGenuine;
	}

	public void setIsGenuine(String isGenuine) {
		this.isGenuine = isGenuine;
	}

	public String getIsLowPrice() {
		return isLowPrice;
	}

	public void setIsLowPrice(String isLowPrice) {
		this.isLowPrice = isLowPrice;
	}

	public String getOrignKeyWord() {
		return orignKeyWord;
	}

	public void setOrignKeyWord(String orignKeyWord) {
		this.orignKeyWord = orignKeyWord;
	}

	public boolean isCompare() {
		return isCompare;
	}

	public void setCompare(boolean isCompare) {
		this.isCompare = isCompare;
	}

	public boolean isRequireRelated() {
		return isRequireRelated;
	}

	public void setRequireRelated(boolean isRequireRelated) {
		this.isRequireRelated = isRequireRelated;
	}

	public Integer getQueryAbbreviation() {
		return queryAbbreviation;
	}

	public void setQueryAbbreviation(Integer queryAbbreviation) {
		this.queryAbbreviation = queryAbbreviation;
	}

	public Map<String, Attibute> getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(Map<String, Attibute> filterMap) {
		this.filterMap = filterMap;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}