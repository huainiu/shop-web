package com.b5m.sf1.dto.res;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.b5m.base.common.utils.CollectionTools;
import com.b5m.bean.dto.shoplist.DocResourceDto;

/**
 * @author echo
 */
public class SearchDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4582998373128702292L;
	//总数
	public long totalCount = 0;
	// 资源
	private List<Map<String, String>> resourcesList = CollectionTools.newList();

	private List<DocResourceDto> docResourceDtos = CollectionTools.newList();
	// 属性
	private List<GroupTree> attributeTrees = CollectionTools.newList();
	// 置顶category
	private List<GroupTree> topGroups = CollectionTools.newList();
	// 相关搜索
	private List<String> relatedQueries = CollectionTools.newList();
	// 分组列表
	private Map<String, GroupTree> groups = new HashMap<String, GroupTree>();
	// 分类树
	private GroupTree categoryTree = new GroupTree();

	private String keywords;
	// 服务器处理时间
	private double speedTime;
	//如果搜索不到，利用设置query_abbreviation 返回的结果
	private List<SearchDTO> ReSearchDTOs = CollectionTools.newList();
	//判断结果是否是从快照拿到的
	private boolean isSnapshot = false;
	//sf1拆词后的结果
	private String analyzerResult;

	public List<Map<String, String>> getResourcesList() {
		return resourcesList;
	}

	public void setResourcesList(List<Map<String, String>> resourcesList) {
		this.resourcesList = resourcesList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<DocResourceDto> getDocResourceDtos() {
		return docResourceDtos;
	}

	public void setDocResourceDtos(List<DocResourceDto> docResourceDtos) {
		this.docResourceDtos = docResourceDtos;
	}

	public List<GroupTree> getAttributeTrees() {
		return attributeTrees;
	}

	public void setAttributeTrees(List<GroupTree> attributeTrees) {
		this.attributeTrees = attributeTrees;
	}

	public List<GroupTree> getTopGroups() {
		return topGroups;
	}

	public void setTopGroups(List<GroupTree> topGroups) {
		this.topGroups = topGroups;
	}

	public List<String> getRelatedQueries() {
		return relatedQueries;
	}

	public void setRelatedQueries(List<String> relatedQueries) {
		this.relatedQueries = relatedQueries;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public double getSpeedTime() {
		return speedTime;
	}

	public void setSpeedTime(double speedTime) {
		this.speedTime = speedTime;
	}

	public Map<String, GroupTree> getGroups() {
		return groups;
	}

	public void setGroups(Map<String, GroupTree> groups) {
		this.groups = groups;
	}

	public GroupTree getCategoryTree() {
		return categoryTree;
	}

	public void setCategoryTree(GroupTree categoryTree) {
		this.categoryTree = categoryTree;
	}

	public List<SearchDTO> getReSearchDTOs() {
		return ReSearchDTOs;
	}

	public void setReSearchDTOs(List<SearchDTO> reSearchDTOs) {
		ReSearchDTOs = reSearchDTOs;
	}

	public boolean isSnapshot() {
		return isSnapshot;
	}

	public void setSnapshot(boolean isSnapshot) {
		this.isSnapshot = isSnapshot;
	}

	public String getAnalyzerResult() {
		return analyzerResult;
	}

	public void setAnalyzerResult(String analyzerResult) {
		this.analyzerResult = analyzerResult;
	}
	
}
