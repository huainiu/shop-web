package com.b5m.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.b5m.bean.dto.shoplist.DocResourceDto;
import com.b5m.bean.dto.shoplist.SuiSearchDto;
import com.b5m.bean.entity.MallBrandInfo;
import com.b5m.common.utils.shoplist.LinkDtoHelper;
import com.b5m.dao.domain.page.PageView;
import com.b5m.service.www.MallBrandInfoService;
import com.b5m.sf1.dto.res.SearchDTO;
import com.b5m.sf1.helper.SearchResultHelper;
import com.b5m.sf1.impl.SF1NewQueryService;
import com.b5m.web.controller.base.AbstractBaseController;

@Controller
public class MallBrandInfoController extends AbstractBaseController{
	
	@Autowired
	@Qualifier("mallBrandInfoService")
	private MallBrandInfoService mallBrandInfoService;
	
	@Resource
	private SF1NewQueryService service;
	
	@RequestMapping("/shop_{id}")
	public String mallBrand(@PathVariable("id") Long id, HttpServletRequest request){
		return mallBrand(id, 1, request);
	}
	
	@RequestMapping("/shop_{id}/{currentPage}")
	public String mallBrand(@PathVariable("id") Long id, @PathVariable("currentPage") Integer currentPage, HttpServletRequest request){
		MallBrandInfo mallBrandInfo =  mallBrandInfoService.queryById(id);
		if(mallBrandInfo == null || isDisable(mallBrandInfo)){
			return "redirect:http://www.b5m.com/404.html";
		}
		SuiSearchDto dto = new SuiSearchDto();
		dto.setKeyWord(mallBrandInfo.getKeywords());
		dto.setCategoryValue(mallBrandInfo.getCategory());
		if(mallBrandInfo.getType() == 0){
			dto.setSourceValue(mallBrandInfo.getName());
		}else{
			dto.setBrandValue("品牌:" + mallBrandInfo.getName());
		}
		dto.setBrandValue(mallBrandInfo.getName());
		dto.setCurrPageNo(currentPage);
		dto.setPageSize(20);
		dto.setSortField("CommentCount");
		dto.setSortType("DESC");
		dto.setQueryAbbreviation(1);
		SearchDTO searchDto = service.search(dto);
		PageView<DocResourceDto> pageView = LinkDtoHelper.getPageView(currentPage, 20, searchDto.getTotalCount(), 5);
        pageView.setUrl("/shop_" + id + "/{pageCode}.htm");
        List<SearchDTO> reSearchDTOs = searchDto.getReSearchDTOs();
        if(searchDto.getDocResourceDtos().isEmpty() && !reSearchDTOs.isEmpty()){
        	searchDto.setDocResourceDtos(reSearchDTOs.get(0).getDocResourceDtos());
        }
        pageView.setRecords(searchDto.getDocResourceDtos());
		request.setAttribute("pageView", pageView);
		request.setAttribute("mallBrandInfo", mallBrandInfo);
		request.setAttribute("priceTrendDocIds", SearchResultHelper.getPriceTrendDocIds(searchDto));
		return "mallbrand";
	}
	
	public boolean isDisable(MallBrandInfo mallBrandInfo){
		return mallBrandInfo.getStatus() == 0;
	}
}
