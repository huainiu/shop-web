package com.b5m.web.controller.base;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;

import com.b5m.base.common.Lang;
import com.b5m.bean.dto.LinkDto;
import com.b5m.bean.dto.shoplist.ShopListDto;
import com.b5m.bean.dto.shoplist.SuiSearchDto;
import com.b5m.common.env.GlobalInfo;
import com.b5m.common.utils.shoplist.ShoplistHelper;
import com.b5m.goods.promotions.service.ISuppliserService;
import com.b5m.sf1.helper.SearchResultHelper;
import com.b5m.sf1.impl.SF1NewQueryService;
import com.mchange.v1.util.StringTokenizerUtils;

public class SearchResultBaseController extends AbstractBaseController {

	@Resource
	protected SF1NewQueryService service;

	@Resource(name = "suppliserCachedService")
	protected ISuppliserService supplierService;

	/**
	 * @description 搜索前进行处理
	 * @param dto
	 * @author echo
	 * @since 2013-8-22
	 * @email echo.weng@b5m.com
	 */
	protected void beforeSearch(SuiSearchDto dto, HttpServletRequest request) {
		if (dto.getCurrPageNo() == null)
			dto.setCurrPageNo(1);
		// 每页显示个数
		if (dto.getPageSize() == 10)
			dto.setPageSize(GlobalInfo.getShopListPage());
		if (dto.getLimit() != null)
			dto.setPageSize(dto.getLimit());
		String categoryValue = dto.getCategoryValue();
		String[] categorys = null;
		if (!StringUtils.isEmpty(categoryValue)) {
			categorys = StringTokenizerUtils.tokenizeToArray(categoryValue, ">");
		}
		if (StringUtils.isEmpty(dto.getKeyWord())) {
			if (!StringUtils.isEmpty(categoryValue)) {
				if (categorys != null && categorys.length > 0) {
					request.setAttribute("keyWord", categorys[categorys.length - 1]);
					dto.setKeyWord(categorys[categorys.length - 1]);
				}
			}
		}
		request.setAttribute("categorySize", 0);
		if (!Lang.isEmpty(dto.getCategoryValue())) {
			request.setAttribute("categorySize", Lang.split(dto.getCategoryValue(), ">").length);
		}
		dto.setOrignKeyWord(dto.getKeyWord());
		request.setAttribute("category", dto.getCategoryValue());
		if (categorys != null && categorys.length > 0) {
			request.setAttribute("_category", categorys[categorys.length - 1]);
		}
	}

	/**
	 * <font style="font-weight:bold">Description: </font> <br/>
	 * 页面参数设置
	 * 
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年3月11日 下午1:35:09
	 * 
	 * @param model
	 * @param shopList
	 * @param dto
	 */
	protected void fillModeAttr(Model model, ShopListDto shopList, SuiSearchDto dto) {
		model.addAttribute("docResourceDtos", shopList.getPageView().getRecords());

		model.addAttribute("showTypeDtos", shopList.getShowTypeDtos());
		// model.addAttribute("sourceValueType",
		// isListClicked(shopList.getShowTypeDtos()));
		model.addAttribute("dataSetGoodsCounter", shopList.getDataSetGoodsCounter());
		model.addAttribute("dataSetTuanCounter", shopList.getDataSetTuanCounter());
		model.addAttribute("dataSetTicketCounter", shopList.getDataSetTicketCounter());
		model.addAttribute("categoryList", shopList.getCategoryList());
		model.addAttribute("sourceLinks", shopList.getSourceLinks());

		// 属性链接
		model.addAttribute("attrsLinks", shopList.getAttrLinkDtos());
		model.addAttribute("itemCount", shopList.getItemCount());
		model.addAttribute("filterList", shopList.getFilterList());
		model.addAttribute("priceList", shopList.getPriceList());
		model.addAttribute("sortList", shopList.getSortList());
		model.addAttribute("navigationList", shopList.getNavigationList());
		model.addAttribute("priceFormat", shopList.getPriceFormat());
		model.addAttribute("includeSourceCount", SearchResultHelper.countSource(shopList.getFilterList(), shopList.getSourceLinks()));

		model.addAttribute("topGroubLabel", shopList.getTopGroupLabelStr());
		model.addAttribute("pageView", shopList.getPageView());
		model.addAttribute("pageCodeLink", shopList.getPageCodeLink());

		LinkDto[] linkDtos = shopList.getCfgLinks();
		model.addAttribute("freeDeliveryLink", linkDtos[0].getUrl());
		model.addAttribute("codLink", linkDtos[1].getUrl());
		model.addAttribute("genuineLink", linkDtos[2].getUrl());
		model.addAttribute("compareLink", shopList.getOrgUrl());

		model.addAttribute("relatedQueryList", shopList.getRelatedQueryList());
		model.addAttribute("priceTrendDocIds", shopList.getPriceTrendDocIds());
	}

	// 将查询的条件 设置到model中
	protected void setConditionToModel(Model model, SuiSearchDto dto) {
		// 关键字
		ShoplistHelper.setKeywords(model, dto.getKeyWord(), dto);
		model.addAttribute("orignKeyword", dto.getOrignKeyWord());
		model.addAttribute("sortField", dto.getSortField());
		model.addAttribute("sortType", dto.getSortType());
		// 货到付款
		model.addAttribute("isCOD", dto.getIsCOD());
		// 免运送
		model.addAttribute("isFreeDelivery", dto.getIsFreeDelivery());
		// 正版行货
		model.addAttribute("isGenuine", dto.getIsGenuine());
		// 比价
		model.addAttribute("isCompare", dto.isCompare());
		// 历史最低价
		model.addAttribute("isLowPrice", dto.getIsLowPrice());
	}
}
