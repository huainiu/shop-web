package com.b5m.web.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.b5m.base.common.utils.StringTools;
import com.b5m.base.common.utils.UrlTools;
import com.b5m.bean.dto.shoplist.ShopListDto;
import com.b5m.bean.dto.shoplist.SuiSearchDto;
import com.b5m.bean.entity.filterAttr.Attibute;
import com.b5m.common.utils.shoplist.ShoplistHelper;
import com.b5m.service.searchresult.SearchResultService;
import com.b5m.sf1.dto.res.SearchDTO;
import com.b5m.sf1.helper.SearchResultHelper;
import com.b5m.web.controller.base.SearchResultBaseController;

@Controller
public class ClothingSearchResultController extends SearchResultBaseController{
	
	@Resource(name = "searchResultService")
	private SearchResultService searchResultService;
	
	@RequestMapping("/clothing/{keyword}")
	public String searchClothing(@PathVariable("keyword") String keyword,SuiSearchDto searchDto, Model model, HttpServletRequest request, HttpServletResponse response) {
		keyword = UrlTools.urlDecode(keyword);
		if (searchDto == null) searchDto = new SuiSearchDto();
		searchDto.setKeyWord(keyword);
		request.setAttribute("keyWord", keyword);
		request.setAttribute("path", "/clothing/");
		return commonSearchClothing(searchDto, model, request, response, "clothing");
	}
	
	@RequestMapping("/clothing/{categoryValue}/{keyword}")
	public String searchClothing(@PathVariable("keyword") String keyword, @PathVariable("categoryValue") String categoryValue, SuiSearchDto searchDto, Model model, HttpServletRequest request, HttpServletResponse response) {
		keyword = UrlTools.urlDecode(keyword);
		categoryValue = UrlTools.urlDecode(categoryValue);
		categoryValue = StringTools.replace(categoryValue, "^", "/");
		if (searchDto == null) searchDto = new SuiSearchDto();
		searchDto.setKeyWord(keyword);
		searchDto.setCategoryValue(categoryValue);
		request.setAttribute("keyWord", keyword);
		request.setAttribute("categoryValue", categoryValue);
		request.setAttribute("path", "/clothing/");
		return commonSearchClothing(searchDto, model, request, response, "clothing");
	}
	
	protected String commonSearchClothing(SuiSearchDto dto, Model model, HttpServletRequest request, HttpServletResponse response, String collectionName) {
		if (StringUtils.isEmpty(dto.getKeyWord()) && StringUtils.isEmpty(dto.getCategoryValue())) {
			return "noresult";
		}
		beforeSearch(dto, request);
		if (StringUtils.isEmpty(dto.getKeyWord())) {
			dto.setKeyWord("*");
		}
		ShoplistHelper.setTitle(model, dto);
		Map<String, Attibute> filterMap = searchResultService.queryAttrFilterList(dto.getKeyWord());
		dto.setFilterMap(filterMap);
		// 搜索
		SearchDTO searchDTO = service.search(dto, collectionName);
		// 保存查询条件, 和查询业务逻辑无关,前端展示使用
		setConditionToModel(model, dto);
		if (SearchResultHelper.isNoResult(searchDTO)) {
			return "noresult";
		}
		// 过滤不展示的属性
		ShopListDto shopList = SearchResultHelper.createShopListDto(searchDTO, dto, request, response);
		fillModeAttr(model, shopList, dto);
		model.addAttribute("ls", request.getParameter("ls"));
		return "new-resultlist";
	}
}
