package com.b5m.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.b5m.base.common.utils.StringTools;
import com.b5m.bean.entity.MallBrandInfo;
import com.b5m.dao.domain.page.PageView;
import com.b5m.service.www.MallBrandInfoService;
import com.b5m.web.controller.base.AbstractBaseController;

@Controller
public class TagesetController extends AbstractBaseController{
	private static final int TYPE_KEYWORDS = 2;
	private static final int TYPE_BRAND = 1;
	private static final int TYPE_MALL = 0;

	private static final Integer PAGE_SIZE = 120;
	
	@Resource
	private MallBrandInfoService mallBrandInfoService;

	@RequestMapping("/tag/keywords")
	public String tag(HttpServletRequest request){
		commonRandonOp(request, TYPE_KEYWORDS);
		return "tageset";
	}
	
	@RequestMapping("/tag/keywords/{currentPage}")
	public String tageNum(HttpServletRequest request, @PathVariable("currentPage") Integer currentPage){
		return _tag("", currentPage, request);
	}
	
	@RequestMapping("/tag/keywords/{word}/{currentPage}")
	public String _tag(@PathVariable("word") String word, @PathVariable("currentPage") Integer currentPage, HttpServletRequest request){
		commonOp(request, TYPE_KEYWORDS, word, currentPage);
		return "tageset";
	}
	
	@RequestMapping("/tag/brands")
	public String brands(HttpServletRequest request){
		PageView<MallBrandInfo> pageView = mallBrandInfoService.queryPage(1, PAGE_SIZE, TYPE_BRAND, "");
		commonOp(request, TYPE_BRAND, "", 1, pageView);
		return "tageset";
	}
	
	@RequestMapping("/tag/brands/{currentPage}")
	public String brandsNum(HttpServletRequest request, @PathVariable("currentPage") Integer currentPage){
		return _brands("", currentPage, request);
	}
	
	@RequestMapping("/tag/brands/{word}/{currentPage}")
	public String _brands(@PathVariable("word") String word, @PathVariable("currentPage") Integer currentPage, HttpServletRequest request){
		commonOp(request, TYPE_BRAND, word, currentPage);
		return "tageset";
	}
	
	@RequestMapping("/tag/malls")
	public String shops(HttpServletRequest request){
		commonRandonOp(request, TYPE_MALL);
		return "tageset";
	}
	
	@RequestMapping("/tag/malls/{currentPage}")
	public String shopsNum(HttpServletRequest request, @PathVariable("currentPage") Integer currentPage){
		return _shops("", currentPage, request);
	}
	
	@RequestMapping("/tag/malls/{word}/{currentPage}")
	public String _shops(@PathVariable("word") String word, @PathVariable("currentPage") Integer currentPage, HttpServletRequest request){
		commonOp(request, TYPE_MALL, word, currentPage);
		return "tageset";
	}
	
	public void commonRandonOp(HttpServletRequest request, Integer type){
		List<MallBrandInfo> mallBrandInfos = mallBrandInfoService.randonPage(500);
		PageView<MallBrandInfo> pageView = new PageView<MallBrandInfo>(PAGE_SIZE, 1);
		pageView.setRecords(mallBrandInfos);
		request.setAttribute("nopage", true);
		commonOp(request, type, "", 1, pageView);
	}
	
	public void commonOp(HttpServletRequest request, Integer type, String word, Integer currentPage, PageView<MallBrandInfo> pageView){
		setTitle(request, currentPage, type, word);
		request.setAttribute("pageView", pageView);
		request.setAttribute("searchs", mallBrandInfoService.queryWords());
		String tage = "keywords";
		if(type == TYPE_BRAND){
			tage = "brands";
		}
		if(type == TYPE_MALL){
			tage = "malls";
		}
		StringBuilder pageUrl = new StringBuilder("/tag/");
		pageUrl.append(tage).append("/");
		if(!StringTools.isEmpty(word)){
			pageUrl.append(word).append("/");
		}
		request.setAttribute("displaytage", tage);
		request.setAttribute("pageUrl", pageUrl);
	}
	
	public void commonOp(HttpServletRequest request, Integer type, String word, Integer currentPage){
		PageView<MallBrandInfo> pageView = mallBrandInfoService.queryPage(currentPage, PAGE_SIZE, type, word);
		commonOp(request, type, word, currentPage, pageView);
	}

	public void setTitle(HttpServletRequest request, Integer currentPage, Integer type, String word){
		String pageStr = "";
		if(currentPage != null){
			pageStr = "_" + word + currentPage;
		}
		String pageTag = "关键词";
		if(type == TYPE_BRAND){
			pageTag = "品牌";
		}
		request.setAttribute("title", "每日更新热门" + pageTag + "top500" + pageStr + "-购物搜索-帮5买");
		request.setAttribute("Keywords", "每日更新,热门" + pageTag +"top500"+",购物搜索,帮5买");
		request.setAttribute("description", "帮5买购物搜索引擎为你每日更新淘宝购物搜索top500热门"+pageTag+"，当日热门趋势尽在购物搜索"+pageTag+"页。");
	}
	
}
