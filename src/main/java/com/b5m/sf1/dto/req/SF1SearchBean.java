package com.b5m.sf1.dto.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.b5m.base.common.utils.StringTools;

public class SF1SearchBean {
	private Integer limit;
	private Integer offset;
	private String collection;
	private String keywords;// 关键词
	private String serverPath;
	private String category;
	private String sources;// 商家过滤
	private List<CondSearchBean> condLst = new ArrayList<CondSearchBean>();
	private List<SortSearchBean> sortList = new ArrayList<SortSearchBean>();
	private List<AttrSearchBean> attrList = new ArrayList<AttrSearchBean>();
	private List<GroupBean> groupBeans = new ArrayList<GroupBean>();
	private List<SelectSearchBean> selectList = new ArrayList<SelectSearchBean>();
	private String sprice;// 开始价格
	private String eprice;// 最后价格
	private boolean isRequireRelated = true;
	private boolean logKeywords = true;
	private Integer queryAbbreviation;
	private List<String> searchIn = new ArrayList<String>();
	private boolean needSearchMode = true;// 是否需要搜索模式
	private Map<String, Object> searchMode;
	/**
	 * 是否获取属性
	 */
	private GetAttr getAttr;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}

	public void addCondition(String name, String operator, String... params) {
		condLst.add(new CondSearchBean(name, operator, params));
	}

	public List<CondSearchBean> getCondLst() {
		return condLst;
	}

	public void setCondLst(List<CondSearchBean> condLst) {
		this.condLst = condLst;
	}

	public void addSort(String name, String type) {
		sortList.add(new SortSearchBean(name, type));
	}

	public List<SortSearchBean> getSortList() {
		return sortList;
	}

	public void setSortList(List<SortSearchBean> sortList) {
		this.sortList = sortList;
	}

	public String getSprice() {
		if (sprice == null)
			return "";
		return sprice;
	}

	public void setSprice(String sprice) {
		this.sprice = sprice;
	}

	public String getEprice() {
		if (eprice == null)
			return "";
		return eprice;
	}

	public void setEprice(String eprice) {
		this.eprice = eprice;
	}

	public List<AttrSearchBean> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<AttrSearchBean> attrList) {
		this.attrList = attrList;
	}

	public boolean isRequireRelated() {
		return isRequireRelated;
	}

	public void setRequireRelated(boolean isRequireRelated) {
		this.isRequireRelated = isRequireRelated;
	}

	public boolean isLogKeywords() {
		return logKeywords;
	}

	public void setLogKeywords(boolean logKeywords) {
		this.logKeywords = logKeywords;
	}

	public GetAttr getGetAttr() {
		// if(getAttr == null) getAttr = new GetAttr();
		return getAttr;
	}

	public void setGetAttr(GetAttr getAttr) {
		this.getAttr = getAttr;
	}

	public List<GroupBean> getGroupBeans() {
		return groupBeans;
	}

	public void setGroupBeans(List<GroupBean> groupBeans) {
		this.groupBeans = groupBeans;
	}

	public void addGroup(String name, boolean range) {
		this.groupBeans.add(new GroupBean(name, range));
	}

	public Integer getQueryAbbreviation() {
		return queryAbbreviation;
	}

	public void setQueryAbbreviation(Integer queryAbbreviation) {
		this.queryAbbreviation = queryAbbreviation;
	}

	public List<SelectSearchBean> getSelectList() {
		return selectList;
	}

	public void setSelectList(List<SelectSearchBean> selectList) {
		this.selectList = selectList;
	}

	public void addSelect(String scdName, String appName) {
		selectList.add(new SelectSearchBean(scdName, appName));
	}

	public List<String> getSearchIn() {
		return searchIn;
	}

	public void addSearchIn(String name) {
		searchIn.add(name);
	}

	public void setSearchIn(List<String> searchIn) {
		this.searchIn = searchIn;
	}

	public boolean isNeedSearchMode() {
		return needSearchMode;
	}

	public void setNeedSearchMode(boolean needSearchMode) {
		this.needSearchMode = needSearchMode;
	}

	public boolean noSelPrirceGroup() {
		return !StringTools.isEmpty(sprice) || !StringTools.isEmpty(eprice);
	}

	public Map<String, Object> getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(Map<String, Object> searchMode) {
		this.searchMode = searchMode;
	}
}
