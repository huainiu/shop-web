package com.b5m.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.DateTools;
import com.b5m.base.common.utils.StringTools;
import com.b5m.base.common.utils.UrlTools;
import com.b5m.base.common.utils.WebTools;
import com.b5m.bean.dto.LinkDto;
import com.b5m.bean.dto.shoplist.DocResourceDto;
import com.b5m.bean.dto.shoplist.ShopListDto;
import com.b5m.bean.dto.shoplist.SuiSearchDto;
import com.b5m.bean.entity.MallBrandInfo;
import com.b5m.bean.entity.SearchRecommend;
import com.b5m.bean.entity.filterAttr.Attibute;
import com.b5m.common.utils.DataUtils;
import com.b5m.common.utils.LoginHelper;
import com.b5m.common.utils.shoplist.LinkDtoHelper;
import com.b5m.common.utils.shoplist.ShoplistHelper;
import com.b5m.dao.domain.page.PageView;
import com.b5m.frame.pojo.UserCenter;
import com.b5m.service.daigou.DaigouService;
import com.b5m.service.searchresult.SearchResultService;
import com.b5m.service.www.MallBrandInfoService;
import com.b5m.service.www.SearchRecommendService;
import com.b5m.sf1.dto.res.SearchDTO;
import com.b5m.sf1.helper.SearchResultHelper;
import com.b5m.web.controller.base.SearchResultBaseController;

@Controller
public class ShopListController extends SearchResultBaseController {
	@Resource(name = "searchResultService")
	private SearchResultService searchResultService;
	@Resource(name = "searchRecommendService")
	private SearchRecommendService searchRecommendService;
	@Resource
	private MallBrandInfoService mallBrandInfoService;
	@Autowired
	private DaigouService daigouService;

	@RequestMapping("/index")
	public String initTtjIndex(Model model) {
		return "index";
	}

	@RequestMapping("/searchRecommend")
	@ResponseBody
	public void searchRecommend(SearchRecommend searchRecommend, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (StringUtils.isEmpty(searchRecommend.getContent()) || StringUtils.isEmpty(searchRecommend.getKeyword())) {
			output(-1, "failure", "");
			return;
		}
		String ip = WebTools.getIpAddr(request);
		UserCenter user = LoginHelper.getUserCenter(request);
		searchRecommend.setCreateTime(DateTools.now());
		searchRecommend.setIp(ip);
		if (user != null) {
			searchRecommend.setUid(user.getUserId());
			searchRecommend.setUsername(user.getEmail());
		}
		searchRecommendService.addSearchRecommend(searchRecommend);
		output(0, "success", "");
	}

	/**
	 * @description 商品搜索结果页
	 * @param dto
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @author echo
	 * @since 2013-8-22
	 * @email echo.weng@b5m.com
	 */
	@RequestMapping("/goodsSearchList")
	public String searchGoodsList(SuiSearchDto dto, Model model, HttpServletRequest request, HttpServletResponse response) {
		return commonSearchGoodsList(dto, model, request, response, true);
	}

	@RequestMapping("/search/data")
	@ResponseBody
	public void searchGoodsListData(SuiSearchDto dto, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		commonSearchGoodsList(dto, (Model) model, request, response, true);
		JSONObject json = new JSONObject();
		json.put("pageView", model.get("pageView"));
		json.put("categoryList", model.get("categoryList"));
		json.put("attrsLinks", model.get("attrsLinks"));
		json.put("filterList", model.get("filterList"));
		json.put("priceList", model.get("priceList"));
		json.put("sourceLinks", model.get("sourceLinks"));
		output(json);
	}

	@RequestMapping("/cp/{keyword}")
	public String tag(@PathVariable("keyword") String orign, Integer currentPage, Model model, HttpServletRequest request, HttpServletResponse response) {
		String keyword = DataUtils.strDecode(orign);
		SuiSearchDto searchDto = new SuiSearchDto();
		searchDto.setKeyWord(keyword);
		request.setAttribute("keyWord", keyword);
		request.setAttribute("orign", orign);
		if (currentPage != null) {
			searchDto.setCurrPageNo(currentPage);
		}
		String result = commonSearchGoodsList(searchDto, model, request, response, false);
		Map<String, Object> map = model.asMap();
		PageView<DocResourceDto> pageView = (PageView<DocResourceDto>) map.get("pageView");
		List<LinkDto> relatedQueryList = (List<LinkDto>) map.get("relatedQueryList");
		if (relatedQueryList != null) {
			for (LinkDto linkDto : relatedQueryList) {
				linkDto.setBase64Text(DataUtils.strEncode(linkDto.getText()));
			}
			pageView.setUrl("/cp/" + orign + "_{pageCode}.html");
		}
		if ("resultlist".equals(result)) {
			List<MallBrandInfo> mallBrandInfos = mallBrandInfoService.randonPage(10);
			request.setAttribute("mallBrandInfos", mallBrandInfos);
			return "hotresultlist";
		}
		return result;
	}

	@RequestMapping("/{categoryValue}/{keyword}")
	public String searchGoodsListForKeyAndCategory(@PathVariable("keyword") String keyword, @PathVariable("categoryValue") String categoryValue, String isLowPrice, Model model, HttpServletRequest request, HttpServletResponse response) {
		keyword = UrlTools.urlDecode(keyword);
		categoryValue = UrlTools.urlDecode(categoryValue);
		categoryValue = StringTools.replace(categoryValue, "^", "/");
		SuiSearchDto searchDto = new SuiSearchDto();
		searchDto.setKeyWord(keyword);
		searchDto.setCategoryValue(categoryValue);
		searchDto.setIsLowPrice(isLowPrice);
		request.setAttribute("keyWord", keyword);
		request.setAttribute("categoryValue", categoryValue);
		return commonSearchGoodsList(searchDto, model, request, response, true);
	}

	@RequestMapping("/{keyword}")
	public String searchGoodsListForKey(@PathVariable("keyword") String keyword, String isLowPrice, Model model, HttpServletRequest request, HttpServletResponse response) {
		if ("500".equals(keyword) || "404".equals(keyword)) {
			response.setStatus(Integer.valueOf(keyword));
			return "commpage/404page";
		}
		keyword = UrlTools.urlDecode(keyword);
		SuiSearchDto searchDto = new SuiSearchDto();
		searchDto.setKeyWord(keyword);
		searchDto.setIsLowPrice(isLowPrice);
		request.setAttribute("keyWord", keyword);
		String result = commonSearchGoodsList(searchDto, model, request, response, true);
		return result;
	}

	private boolean noNeedSearchCorrect(String refine, SuiSearchDto dto, String refineQuery) {
		if (!StringUtils.isEmpty(refine) || dto.getKeyWord().equals(refineQuery) || StringUtils.isEmpty(refineQuery))
			return true;
		return false;
	}

	private String getCollection(HttpServletRequest req) {
		return "aggregator";
	}

	protected String commonSearchGoodsList(SuiSearchDto dto, Model model, HttpServletRequest request, HttpServletResponse response, boolean isResearchMore) {
		if (StringUtils.isEmpty(dto.getKeyWord()) && StringUtils.isEmpty(dto.getCategoryValue())) {
			return "noresult";
		}
		beforeSearch(dto, request);
		if (StringUtils.isEmpty(dto.getKeyWord())) {
			dto.setKeyWord("*");
		}
		ShoplistHelper.setTitle(model, dto);
		String refine = request.getParameter("refine");
		String refineQuery = service.queryCorrect(dto.getKeyWord());
		if (!noNeedSearchCorrect(refine, dto, refineQuery)) {
			model.addAttribute("refineQuery", refineQuery);
			dto.setKeyWord(refineQuery);
		}
		Map<String, Attibute> filterMap = searchResultService.queryAttrFilterList(dto.getKeyWord());
		dto.setFilterMap(filterMap);

		dto.setCollectionName(getCollection(request));
		// 搜索
		SearchDTO searchDTO = service.search(dto);
		// 保存查询条件, 和查询业务逻辑无关,前端展示使用
		setConditionToModel(model, dto);
		if (SearchResultHelper.isNoResult(searchDTO) && SearchResultHelper.needReSearch(dto)) {
			dto.setKeyWord(searchDTO.getAnalyzerResult());
			SearchDTO[] searchDTOWraps = service.multiSearch(dto);
			if (searchDTOWraps.length >= 1 && searchDTOWraps[0] != null) {
				model.addAttribute("priceTrendDocIds", SearchResultHelper.getPriceTrendDocIds(searchDTOWraps[0]));
				model.addAttribute("searchDTOWraps", searchDTOWraps);
				return "multiresultlist";
			}
		}
		if (SearchResultHelper.isNoResult(searchDTO)) {
			return "noresult";
		}
		if (SearchResultHelper.needReSearchMore(searchDTO)) {
			dto.setKeyWord(searchDTO.getAnalyzerResult());
			SearchDTO[] searchDTOWraps = service.multiSearch(dto);
			if (searchDTOWraps.length >= 1 && searchDTOWraps[0] != null) {
				model.addAttribute("reSearchMore", true);
				for (SearchDTO _searchDTO : searchDTOWraps) {
					// 设置代购代购标识符
					setDaigouFlag(_searchDTO.getDocResourceDtos());
				}
				model.addAttribute("searchDTOWraps", searchDTOWraps);
			}
		}
		// 过滤掉已经选择了的属性
		needRemoveAttr(filterMap, dto.getBrandValue());
		// 过滤属性，属性排序，属性名显示
		SearchResultHelper.filterAttr(filterMap, searchDTO);
		// 设置代购代购标识符
		setDaigouFlag(searchDTO.getDocResourceDtos());

		ShopListDto shopList = SearchResultHelper.createShopListDto(searchDTO, dto, request, response);
		//迟早会删除的
		sortTaosha(shopList);
		
		// 关键词手工排序
		if (!"*".equals(dto.getKeyWord()))
			searchResultService.sort(searchDTO, dto.getKeyWord());

		fillModeAttr(model, shopList, dto);
		model.addAttribute("ls", request.getParameter("ls"));

		Map<String, String> parameters = LinkDtoHelper.initParameters(LinkDtoHelper.cloneParameters(request.getParameterMap()));
		String url = LinkDtoHelper.generateUrl(parameters, "", request, response);

		// 判断是否需要显示广告
		model.addAttribute("needShowAd", searchResultService.needShowAd(dto.getKeyWord(), dto.getCurrPageNo(), searchResultService.queryAllAdKeywords()));
		model.addAttribute("lowestLink", url);
		model.addAttribute("_needShowAd", request.getParameter("needShowAd"));
		List<String> keywords = notShowAdRecord();
		model.addAttribute("_needShowAdRecord", "true");
		if (keywords.contains(dto.getKeyWord())) {
			model.addAttribute("_needShowAdRecord", "false");
		}
		return "resultlist";
	}
	
	//把淘沙商品放在第一位
	public void sortTaosha(ShopListDto shopList){
		List<LinkDto> linkDtos = shopList.getSourceLinks();
		LinkDto taosha = null;
		for(LinkDto linkDto : linkDtos){
			if("淘沙商城".equals(linkDto.getText())){
				taosha = linkDto;
			}
		}
        if(taosha != null){
        	linkDtos.remove(taosha);
        	linkDtos.add(0, taosha);
        }
	}

	// 为了基调
	public List<String> notShowAdRecord() {
		List<String> list = new ArrayList<String>();
		list.add("手机");
		list.add("羽绒服");
		list.add("毛呢外套");
		list.add("男装");
		list.add("手机数码");
		list.add("棉服");
		list.add("毛衣");
		list.add("雪地靴");
		list.add("鞋包");
		list.add("ipad air");
		list.add("马丁靴");
		return list;
	}

	protected void setDaigouFlag(List<DocResourceDto> produceList) {
		for (DocResourceDto produce : produceList) {
			daigouService.canDaigouAndSetFlag(produce.getRes());
		}
	}

	// 过滤掉已经选择了的属性
	protected void needRemoveAttr(Map<String, Attibute> filterMap, String attrs) {
		if (StringTools.isEmpty(attrs))
			return;
		String[] strs = StringTools.split(attrs, ",");
		for (String str : strs) {
			String name = StringTools.split(str, ":")[0];
			Attibute value = new Attibute();
			value.setStatus(0);
			value.setName(name);
			filterMap.put(name, value);
		}
	}

}