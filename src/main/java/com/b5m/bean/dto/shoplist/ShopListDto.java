package com.b5m.bean.dto.shoplist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.b5m.bean.dto.AttrLinkDto;
import com.b5m.bean.dto.FilterLinkDto;
import com.b5m.bean.dto.LinkDto;
import com.b5m.bean.dto.LinkTreeDto;
import com.b5m.bean.dto.NavigationLinkDto;
import com.b5m.dao.domain.page.PageView;

/**
 * 这个是整个/goodsSearchList需要使用到的所有DTO
 * 
 * @author jacky
 * 
 */
@SuppressWarnings("rawtypes")
public class ShopListDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -2988062066868406177L;

	private long dataSetGoodsCounter;

	private long dataSetTuanCounter;

	private long dataSetTicketCounter;

	private long itemCount;

	private LinkTreeDto categoryList;

	private List<List<String[]>> shoreInfoList;

	private List<LinkDto> sourceLinks;

	private List<LinkDto> brandList;

	private List<FilterLinkDto> filterList;

	private List<LinkDto> priceList;

	private List<LinkDto> sortList;

	private NavigationLinkDto navigationList;

	private String priceFormat;

	private List<LinkDto> showTypeDtos;

	private List<AttrLinkDto> attrLinkDtos;

	private Map<String, List<String>> topGroupLabel;

	private PageView pageView;

	private String pageCodeLink;

	// 免运费 正品行货 货到付款 链接
	private LinkDto[] cfgLinks;

	private String topGroupLabelStr;
	
	private List<LinkDto> relatedQueryList;
	
	private String refineQuery;
	
	//需要显示价格趋势的 docId
	private String priceTrendDocIds;
	
	private String resultview;
	
	private String orgUrl;

	public LinkDto[] getCfgLinks() {
		return cfgLinks;
	}

	public void setCfgLinks(LinkDto[] cfgLinks) {
		this.cfgLinks = cfgLinks;
	}

	public long getDataSetGoodsCounter() {
		return dataSetGoodsCounter;
	}

	public void setDataSetGoodsCounter(long dataSetGoodsCounter) {
		this.dataSetGoodsCounter = dataSetGoodsCounter;
	}

	public long getDataSetTuanCounter() {
		return dataSetTuanCounter;
	}

	public void setDataSetTuanCounter(long dataSetTuanCounter) {
		this.dataSetTuanCounter = dataSetTuanCounter;
	}

	public long getDataSetTicketCounter() {
		return dataSetTicketCounter;
	}

	public void setDataSetTicketCounter(long dataSetTicketCounter) {
		this.dataSetTicketCounter = dataSetTicketCounter;
	}

	public long getItemCount() {
		return itemCount;
	}

	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}

	public LinkTreeDto getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(LinkTreeDto categoryList) {
		this.categoryList = categoryList;
	}

	public List<List<String[]>> getShoreInfoList() {
		return shoreInfoList;
	}

	public void setShoreInfoList(List<List<String[]>> shoreInfoList) {
		this.shoreInfoList = shoreInfoList;
	}

	public List<LinkDto> getSourceLinks() {
		return sourceLinks;
	}

	public void setSourceLinks(List<LinkDto> sourceLinks) {
		this.sourceLinks = sourceLinks;
	}

	public List<LinkDto> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<LinkDto> brandList) {
		this.brandList = brandList;
	}

	public List<FilterLinkDto> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<FilterLinkDto> filterList) {
		this.filterList = filterList;
	}

	public List<LinkDto> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<LinkDto> priceList) {
		this.priceList = priceList;
	}

	public List<LinkDto> getSortList() {
		return sortList;
	}

	public void setSortList(List<LinkDto> sortList) {
		this.sortList = sortList;
	}

	public NavigationLinkDto getNavigationList() {
		return navigationList;
	}

	public void setNavigationList(NavigationLinkDto navigationList) {
		this.navigationList = navigationList;
	}

	public String getPriceFormat() {
		return priceFormat;
	}

	public void setPriceFormat(String priceFormat) {
		this.priceFormat = priceFormat;
	}

	public List<LinkDto> getShowTypeDtos() {
		return showTypeDtos;
	}

	public void setShowTypeDtos(List<LinkDto> showTypeDtos) {
		this.showTypeDtos = showTypeDtos;
	}

	public Map<String, List<String>> getTopGroupLabel() {
		if (topGroupLabel == null)
			return new HashMap<String, List<String>>(0);
		return topGroupLabel;
	}

	public void setTopGroupLabel(Map<String, List<String>> topGroupLabel) {
		this.topGroupLabel = topGroupLabel;
	}

	public String getTopGroupLabelStr() {
		return topGroupLabelStr;
	}

	public void setTopGroupLabelStr(String topGroupLabelStr) {
		this.topGroupLabelStr = topGroupLabelStr;
	}

	public PageView getPageView() {
		return pageView;
	}

	public void setPageView(PageView pageView) {
		this.pageView = pageView;
	}

	public String getPageCodeLink() {
		return pageCodeLink;
	}

	public void setPageCodeLink(String pageCodeLink) {
		this.pageCodeLink = pageCodeLink;
	}

	public List<AttrLinkDto> getAttrLinkDtos() {
		if (attrLinkDtos == null)
			attrLinkDtos = new ArrayList<AttrLinkDto>(0);
		return attrLinkDtos;
	}

	public void setAttrLinkDtos(List<AttrLinkDto> attrLinkDtos) {
		this.attrLinkDtos = attrLinkDtos;
	}

	public List<LinkDto> getRelatedQueryList() {
		if(relatedQueryList == null) relatedQueryList = new ArrayList<LinkDto>();
		return relatedQueryList;
	}

	public void setRelatedQueryList(List<LinkDto> relatedQueryList) {
		this.relatedQueryList = relatedQueryList;
	}

	public String getPriceTrendDocIds() {
		return priceTrendDocIds;
	}

	public void setPriceTrendDocIds(String priceTrendDocIds) {
		this.priceTrendDocIds = priceTrendDocIds;
	}

	public String getRefineQuery() {
		return refineQuery;
	}

	public void setRefineQuery(String refineQuery) {
		this.refineQuery = refineQuery;
	}

	public String getResultview() {
		return resultview;
	}

	public void setResultview(String resultview) {
		this.resultview = resultview;
	}

	public String getOrgUrl() {
		return orgUrl;
	}

	public void setOrgUrl(String orgUrl) {
		this.orgUrl = orgUrl;
	}
	
}
