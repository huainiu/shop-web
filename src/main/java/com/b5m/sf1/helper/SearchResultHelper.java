package com.b5m.sf1.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.StringTools;
import com.b5m.bean.dto.AttrLinkDto;
import com.b5m.bean.dto.FilterLinkDto;
import com.b5m.bean.dto.LinkDto;
import com.b5m.bean.dto.shoplist.DocResourceDto;
import com.b5m.bean.dto.shoplist.ShopListDto;
import com.b5m.bean.dto.shoplist.SortType;
import com.b5m.bean.dto.shoplist.SuiSearchDto;
import com.b5m.bean.entity.filterAttr.Attibute;
import com.b5m.common.env.Sf1Constants;
import com.b5m.common.utils.DataUtils;
import com.b5m.common.utils.ImageUtils;
import com.b5m.common.utils.MemCachedUtils;
import com.b5m.common.utils.shoplist.LinkDtoHelper;
import com.b5m.dao.domain.page.PageView;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.sf1.dto.req.AttrSearchBean;
import com.b5m.sf1.dto.req.GetAttr;
import com.b5m.sf1.dto.req.SF1SearchBean;
import com.b5m.sf1.dto.req.SortSearchBean;
import com.b5m.sf1.dto.res.Group;
import com.b5m.sf1.dto.res.GroupTree;
import com.b5m.sf1.dto.res.SearchDTO;

public class SearchResultHelper {
	public static final String IS_FREE_DELIVERY = "1";
	public static final String IS_COD = "1";
	public static final String IS_GENUINE = "1";

	public static SF1SearchBean convertTo4Search(SuiSearchDto searchDto) {
		SF1SearchBean sf1SearchBean = new SF1SearchBean();
		sf1SearchBean.setCollection(searchDto.getCollectionName());
		sf1SearchBean.setKeywords(searchDto.getKeyWord());
		sf1SearchBean.setNeedSearchMode(true);
		sf1SearchBean.setLimit(searchDto.getPageSize());
		sf1SearchBean.setOffset((searchDto.getCurrPageNo() - 1) * searchDto.getPageSize());
		// 排序设置
		setSortFilter(searchDto, sf1SearchBean);
		// 类目设置
		sf1SearchBean.setCategory(searchDto.getCategoryValue());
		// 商家设置
		sf1SearchBean.setSources(DataUtils.specialCharDe(searchDto.getSourceValue()));
		// 过滤属性设置
		setAttrFilter(searchDto, sf1SearchBean);
		if (searchDto.isCompare()) {
			sf1SearchBean.addCondition("itemcount", ">", "1");
		}
		if ("1".equals(searchDto.getIsLowPrice())) {
			sf1SearchBean.addCondition("isLowPrice", ">", "0f");
		}
		sf1SearchBean.setSprice(searchDto.getPriceFrom());
		sf1SearchBean.setEprice(searchDto.getPriceTo());
		setFCGCondition(searchDto, sf1SearchBean);
		addGroup(searchDto, sf1SearchBean);
		sf1SearchBean.addSearchIn("Title");
		sf1SearchBean.setRequireRelated(searchDto.isRequireRelated());
		sf1SearchBean.setGetAttr(new GetAttr(true, 20));
		sf1SearchBean.setQueryAbbreviation(searchDto.getQueryAbbreviation());
		return sf1SearchBean;
	}

	public static boolean needReSearch(SuiSearchDto dto) {
		return StringUtils.isEmpty(dto.getCategoryValue()) && StringUtils.isEmpty(dto.getBrandValue()) && StringUtils.isEmpty(dto.getIsCOD()) && StringUtils.isEmpty(dto.getIsFreeDelivery()) && StringUtils.isEmpty(dto.getIsGenuine()) && (dto.getCurrPageNo() == null || (dto.getCurrPageNo() != null && dto.getCurrPageNo() == 1)) && StringUtils.isEmpty(dto.getPriceFrom()) && StringUtils.isEmpty(dto.getPriceTo()) && StringUtils.isEmpty(dto.getSourceValue());
	}

	public static boolean isNoResult(SearchDTO searchDTO) {
		return searchDTO == null || searchDTO.getTotalCount() == 0;
	}
	
	public static boolean needReSearchMore(SearchDTO searchDTO){
		return searchDTO != null && searchDTO.getTotalCount() <= 8;
	}

	public static int countSource(List<FilterLinkDto> filters, List<LinkDto> sources) {
		// 先判断filters中是否有商家，如果有，则返回filter中的商家数量
		int count = getSourceFilterCount(filters);
		if (count > 0)
			return count;
		// 如果没有，则直接返回sources的数量
		return sources.size();
	}

	public static String getTaoKeyword(String groupLabel, String keyword) {
		// tao的关键字搜索 如果没有顶级标签 就用关键字去搜索
		if (StringUtils.isBlank(groupLabel)) {
			return keyword;
		}
		return groupLabel.replace(",", ">");
	}

	protected static int getSourceFilterCount(List<FilterLinkDto> filters) {
		int count = 0;
		for (FilterLinkDto filter : filters) {
			if (filter.getFilterType().equals("商家")) {
				count++;
			}
		}
		return count;
	}

	protected static List<AttrLinkDto> createAttrList(SearchDTO searchDto, HttpServletRequest request, HttpServletResponse response, String basePath) {
		List<GroupTree> attributeTrees = searchDto.getAttributeTrees();
		List<AttrLinkDto> attrLinkDtos = CollectionTools.newListWithSize(5);
		AttrLinkDto brandAttrLink = null;
		for (GroupTree groupTree : attributeTrees) {
			if (groupTree == null || StringUtils.isEmpty(groupTree.getGroup().getGroupName()))
				continue;
			AttrLinkDto attrLinkDto = new AttrLinkDto();
			attrLinkDto.setName(groupTree.getGroup().getGroupName());
			String displayName = groupTree.getGroup().getDisPlayName();
			if(!StringTools.isEmpty(displayName)){
				attrLinkDto.setName(displayName);
			}
			groupTree.setGroupTree(CollectionTools.subList(groupTree.getGroupTree(), 0, 30));
			attrLinkDto.setAttrList(LinkDtoHelper.generateAttrLinks(request, response, groupTree, basePath));
			if (!attrLinkDto.getAttrList().isEmpty()) {
				if (!"品牌".equals(groupTree.getGroup().getGroupName())) {
					attrLinkDtos.add(attrLinkDto);
				} else {
					brandAttrLink = attrLinkDto;
				}
			}
		}
		if (brandAttrLink != null)
			attrLinkDtos.add(0, brandAttrLink);
		return attrLinkDtos;
	}

	private static List<LinkDto> createPriceLinks(SearchDTO searchDto, SuiSearchDto dto, HttpServletRequest request, HttpServletResponse response, String basePath) {
		if (!searchDto.getGroups().containsKey("Price")) {
			return CollectionTools.newListWithSize(0);
		}
		GroupTree grougRoot = searchDto.getGroups().get("Price");
		List<GroupTree> groupTrees = grougRoot.getGroupTree();
		List<String> priceRangs = CollectionTools.newListWithSize(groupTrees.size());
		for (GroupTree groupTree : groupTrees) {
			priceRangs.add(groupTree.getGroup().getGroupName());
		}
		return LinkDtoHelper.generatePriceRangeLinks(request, response, basePath, priceRangs.toArray(new String[] {}));
	}

	public static List<LinkDto> createRelatedQueryLinks(SearchDTO searchDto, HttpServletRequest request, HttpServletResponse response, String basePath) {
		List<LinkDto> linkDtos = new ArrayList<LinkDto>();
		List<String> relatedQueries = searchDto.getRelatedQueries();
		if (relatedQueries.isEmpty())
			return linkDtos;
		for (String relatedQuery : relatedQueries) {
			linkDtos.add(LinkDtoHelper.generateRelatedQueryLinkDto(request, relatedQuery, response, basePath));
		}
		return linkDtos;
	}

	// 获取o的docId SubResources为空 说明这个是o的商品
	public static String getPriceTrendDocIds(SearchDTO searchDto) {
		StringBuilder sb = new StringBuilder();
		for (DocResourceDto docResourceDto : searchDto.getDocResourceDtos()) {
			if (docResourceDto.getSubResources().isEmpty()) {
				String docId = docResourceDto.getRes().get("DOCID");
				sb.append(docId + ",");
			}
		}
		if (sb.length() < 1)
			return "";
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static void filterAttr(Map<String, Attibute> filterMap, SearchDTO searchDto) {
		List<GroupTree> attributeTrees = searchDto.getAttributeTrees();
		if (CollectionUtils.isEmpty(attributeTrees) || CollectionUtils.isEmpty(filterMap))
			return;
		String[] attrRanks = getAttrRanks(filterMap);
		if(attrRanks.length > 0){//如果排序值大于1 则进行排序
			attributeTrees = rankAttrValue(attributeTrees, attrRanks);
			searchDto.setAttributeTrees(attributeTrees);
		}
		int length = attributeTrees.size();
		for (int i = 0; i < length; i++) {
			GroupTree groupTree = attributeTrees.get(i);
			String name = groupTree.getGroup().getGroupName();
			if (groupTree == null || StringUtils.isEmpty(name) || !filterMap.containsKey(name))
				continue;
			Attibute attr = filterMap.get(name);
			//设置属性显示名称
			if (attr.getStatus() == 1) {
				attributeTrees.remove(groupTree);
				i--;
				length--;
				continue;
			} else {
				groupTree.getGroup().setDisPlayName(attr.getDisplayName());
				Set<String> set = attr.getValues();
				List<GroupTree> subGroupTrees = groupTree.getGroupTree();
				if (!CollectionUtils.isEmpty(set) && !CollectionUtils.isEmpty(subGroupTrees)){
					int sublength = subGroupTrees.size();
					for (int j = 0; j < sublength; j++) {
						GroupTree subGroupTree = subGroupTrees.get(j);
						String subName = subGroupTree.getGroup().getGroupName();
						if (StringUtils.isEmpty(subName) || !set.contains(subName))
							continue;
						subGroupTrees.remove(subGroupTree);
						j--;
						sublength--;
					}
				}
				String[] attrValueRanks = attr.getRankArray();
				if(attrValueRanks.length > 0){//如果排序值大于1 则进行排序
					groupTree.setGroupTree(rankAttrValue(subGroupTrees, attrValueRanks));
				}
			}
		}
	}
	
	public static String[] getAttrRanks(Map<String, Attibute> filterMap){
		Attibute attibute = filterMap.get("ALL");
		if(attibute == null) return new String[0];
		return attibute.getKeyRes().getRankArray();
	}
	
	public static List<GroupTree> rankAttrValue(List<GroupTree> subGroupTrees, String[] attrValueRanks){
		List<GroupTree> rankTop = new ArrayList<GroupTree>();
		for(String attrValue : attrValueRanks){
			if(attrValue == "ALL") continue;
			GroupTree contain = contain(subGroupTrees, attrValue);
			if(contain != null){
				subGroupTrees.remove(contain);
			}else{
				contain = new GroupTree();
				contain.setGroup(new Group(attrValue, 0, true, attrValue));
			}
			rankTop.add(contain);
		}
		rankTop.addAll(subGroupTrees);
		return rankTop;
	}
	
	public static GroupTree contain(List<GroupTree> subGroupTrees, String attrValue){
		for(GroupTree groupTree : subGroupTrees){
			if(attrValue.equals(groupTree.getGroup().getGroupName())){
				return groupTree;
			}
		}
		return null;
	}
	
	public boolean contain(String[] attrValueRanks, String subName){
		if(attrValueRanks.length < 1) return false;
		for(String attrValue : attrValueRanks){
			if(attrValue.equals(subName)) return true;
		}
		return false;
	}

	public static ShopListDto createShopListDto(SearchDTO searchDto, SuiSearchDto dto, HttpServletRequest request, HttpServletResponse response) {
		String memKey = getShopListDtoKey(dto);
		Object o = MemCachedUtils.getCache(memKey);
		if (o != null)
			return (ShopListDto) o;

		ShopListDto shopList = new ShopListDto();
		String basePath = LinkDtoHelper.generateContextPathBase(request);

		// SF1QueryHelper.sortCategoryTreesByDesc(searchDto);
		// 创建商家文本和超链接
		shopList.setSourceLinks(createSourceLinks(searchDto, dto, request, response, basePath));
		// shopList.setShowTypeDtos(LinkDtoHelper.generateShowTypeLinkDtos(request,
		// response, basePath));
		shopList.setDataSetGoodsCounter(Sf1DataHelper.getGoodsCounter(searchDto));

		// SF1DataHelper.sortCategoryTreeByItemCount(
		// searchDto.getCategoryTree());

		Sf1DataHelper.sortCategoryTree(searchDto.getTopGroups(), searchDto.getCategoryTree());
		// 设置分类列表的链接
		shopList.setCategoryList(LinkDtoHelper.generateCategoryTreeLinks(request, response, searchDto.getCategoryTree(), basePath));

		// 顶级标签
		// 用来作为掏特价推荐商品的关键字
		shopList.setTopGroupLabelStr(Sf1DataHelper.getCategoryGroubLabel(searchDto.getCategoryTree()));
		// 过滤属性
		List<AttrLinkDto> attrLinkDtos = createAttrList(searchDto, request, response, basePath);
		shopList.setAttrLinkDtos(attrLinkDtos);

		shopList.setPageCodeLink(LinkDtoHelper.generatePageCodeLink(request, response, basePath));
		shopList.setItemCount(searchDto.getTotalCount());
		shopList.setFilterList(LinkDtoHelper.generateFilterLinks(request, response, basePath));
		shopList.setPriceList(createPriceLinks(searchDto, dto, request, response, basePath));
		PageView<DocResourceDto> pageView = LinkDtoHelper.getPageView(dto.getCurrPageNo(), dto.getPageSize(), searchDto.getTotalCount(), 5);
		pageView.setUrl(shopList.getPageCodeLink());
		pageView.setRecords(searchDto.getDocResourceDtos());
		ImageUtils.replaceImgUrl(searchDto.getResourcesList());

		shopList.setPageView(pageView);
		// 创建商品列表数据
		if (null != request.getAttribute("path"))
			shopList.setSortList(LinkDtoHelper.generateSortLinks(request, response, basePath, new SortType[] { SortType.SalesAmount, SortType.Price }));
		else
			shopList.setSortList(LinkDtoHelper.generateSortLinks(request, response, basePath));
		shopList.setNavigationList(LinkDtoHelper.generateNavigationLink(request, response, basePath));
		shopList.setPriceFormat(LinkDtoHelper.generatePriceFormat(request, response, basePath));
		shopList.setCfgLinks(LinkDtoHelper.generateCFGLink(request, response, basePath));

		shopList.setRelatedQueryList(createRelatedQueryLinks(searchDto, request, response, basePath));

		shopList.setPriceTrendDocIds(getPriceTrendDocIds(searchDto));
		shopList.setOrgUrl(LinkDtoHelper.generateOrgUrl(request, response, basePath));
		return shopList;
	}

	protected static void setSortFilter(SuiSearchDto searchDto, SF1SearchBean sf1SearchBean) {
		List<SortSearchBean> sortList = new ArrayList<SortSearchBean>();
		sortList.add(new SortSearchBean(searchDto.getSortField(), searchDto.getSortType()));
		sf1SearchBean.setSortList(sortList);
	}

	protected static void setAttrFilter(SuiSearchDto searchDto, SF1SearchBean sf1SearchBean) {
		if (StringTools.isEmpty(searchDto.getBrandValue())) {
			return;
		}
		String[] attrValueArray = StringTools.split(searchDto.getBrandValue(), ",");
		if (attrValueArray.length == 0)
			return;
		Map<String, Attibute> attrMap = searchDto.getFilterMap();
		if (attrMap == null) return;
		for (String attrValue : attrValueArray) {
			if (StringUtils.isEmpty(attrValue))
				continue;
			attrValue = DataUtils.specialCharDe(attrValue);
			String[] attr = StringTools.split(attrValue, ":");
			if (attr.length < 2)
				continue;
			// 将品牌成改利用attr_label进行过滤
			String value = DataUtils.replace(attr[1], "@^^@", ":");
			Attibute attibute = attrMap.get("ALL");
			boolean haveMerge = false;
			if(attibute != null) {
				String[] attrs = attibute.getRelByDisplayName(value);
				if(attrs.length > 0){
					haveMerge = true;
					for(String a : attrs){
						sf1SearchBean.getAttrList().add(new AttrSearchBean(attr[0], a));
					}
				}
			}
			if(!haveMerge){
				sf1SearchBean.getAttrList().add(new AttrSearchBean(attr[0], value));
			}
		}
	}

	protected static void setFCGCondition(SuiSearchDto searchDto, SF1SearchBean sf1SearchBean) {
		if (Sf1Constants.IS_FREE_DELIVERY.equals(searchDto.getIsFreeDelivery())) {
			sf1SearchBean.addCondition("isFreeDelivery", "=", IS_FREE_DELIVERY);
		}
		/*if (Sf1Constants.IS_COD.equals(searchDto.getIsCOD())) {
			sf1SearchBean.addCondition("isCOD", "=", IS_COD);
		}*/
		if (Sf1Constants.IS_GENUINE.equals(searchDto.getIsGenuine())) {
			sf1SearchBean.addCondition("isGenuine", "=", IS_GENUINE);
		}
	}

	protected static void addGroup(SuiSearchDto searchDto, SF1SearchBean sf1SearchBean) {
		if (!sf1SearchBean.noSelPrirceGroup()) {
			sf1SearchBean.addGroup("Price", true);
		}
		sf1SearchBean.addGroup("Source", false);
		sf1SearchBean.addGroup("Category", false);
	}

	protected static String getShopListDtoKey(SuiSearchDto dto) {
		String str = JSON.toJSONString(dto);
		String key = DigestUtils.md5Hex(str);
		return key;
	}

	protected static List<LinkDto> createSourceLinks(SearchDTO searchDto, SuiSearchDto dto, HttpServletRequest request, HttpServletResponse response, String basePath) {
		if (!searchDto.getGroups().containsKey("Source")) {
			return CollectionTools.newListWithSize(0);
		}
		// 排序商家，将拥有商品数量最多的商家放到最前面
		Sf1DataHelper.sortGroupTreeByDesc(searchDto.getGroups().get("Source"));
		GroupTree root = searchDto.getGroups().get("Source");
		if (root == null)
			return CollectionTools.newListWithSize(0);
		return LinkDtoHelper.generateSourceLinks(request, response, root.getGroupTree(), basePath);
	}

	protected static boolean isKaviSearch(SuiSearchDto dto) {
		return "true".equals(dto.getKaviFilter());
	}

	public static GroupTree filter4KaviSource(GroupTree sources, Map<String, SuppliserDto> kaviSupplisers) {
		GroupTree filted = new GroupTree();
		filted.setGroup(sources.getGroup());
		for (GroupTree source : sources.getGroupTree()) {
			if (kaviSupplisers.containsKey(source.getGroup().getGroupName())) {
				filted.addSub(source);
			}
		}
		return filted;
	}
}
