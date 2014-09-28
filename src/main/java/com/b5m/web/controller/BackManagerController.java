package com.b5m.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.b5m.base.common.utils.WebTools;
import com.b5m.bean.dto.dianping.BackManageCnd;
import com.b5m.bean.dto.dianping.OperationDto;
import com.b5m.bean.entity.Comment;
import com.b5m.bean.entity.Impress;
import com.b5m.bean.entity.Info;
import com.b5m.bean.entity.Key;
import com.b5m.bean.entity.Link;
import com.b5m.bean.entity.Meta;
import com.b5m.bean.entity.SearchHotKey;
import com.b5m.bean.entity.SearchRecommend;
import com.b5m.bean.entity.Suppliser;
import com.b5m.bean.entity.Word;
import com.b5m.dao.domain.page.PageView;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.service.backmanage.BackManageService;
import com.b5m.web.controller.base.AbstractBaseController;
import com.b5m.web.filter.KeywordFilter;

@Controller
@RequestMapping("/backmanager")
public class BackManagerController extends AbstractBaseController {

	Logger log = Logger.getLogger(getClass());

	@Resource(name = "backManageService")
	private BackManageService backManageService;

	@RequestMapping(value = { "/login", "/user/login" })
	public String index() {
		return "/backmanager/backLogin";
	}

	@RequestMapping("/supplier/manage/page")
	public String listSuppliser(ModelMap model, Integer currentPage) {
		return backManageService.listSuppliser(model, currentPage);
	}

	@RequestMapping(value = "/addSuppliser", method = RequestMethod.POST)
	public String addSuppliser(@ModelAttribute Suppliser suppliser) {
		return backManageService.addSuppliser(suppliser);
	}

	@RequestMapping(value = "/editSuppliser", method = RequestMethod.POST)
	public String editSuppliser(@ModelAttribute Suppliser suppliser) {
		return backManageService.editSuppliser(suppliser);
	}

	/**
	 * 从老项目中直接查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/supplier/autofill")
	@ResponseBody
	public List<SuppliserDto> searchSupplier(String keyword) {
		return backManageService.searchSupplier(keyword);
	}

	@RequestMapping(value = "/removeSuppliser", method = RequestMethod.GET)
	public String removeSuppliser(Long id) {
		return backManageService.removeSuppliser(id);
	}

	/** ---------------------印象管理---------------------- **/
	@RequestMapping("/impress/audit/page")
	public String listImpressAudit(ModelMap model, @ModelAttribute BackManageCnd backManageCnd) {
		// 审核的需要查询未审核的 -1表示未审核
		backManageCnd.setOper(-1);
		backManageService.listImpress(model, backManageCnd);
		return "/backmanager/impress/impressAudit";
	}

	@RequestMapping("/impress/manage/page")
	public String listImpressManage(ModelMap model, @ModelAttribute BackManageCnd backManageCnd) {
		Integer oper = backManageCnd.getOper();
		if (oper != null && oper == -2) {
			backManageCnd.setOper(null);
		}
		backManageService.listImpress(model, backManageCnd);
		return "/backmanager/impress/impressManage";
	}

	/**
	 * @description 印象审核
	 * @param model
	 * @param id
	 * @param isPassed
	 * @return
	 * @return Map<String,Object>
	 * @date 2013-6-23
	 * @author xiuqing.weng
	 */
	@RequestMapping("/impress/audit")
	@ResponseBody
	public Map<String, Object> impressAudit(ModelMap model, String id, boolean isPassed) {
		return backManageService.impressAudit(model, id, isPassed);
	}

	@RequestMapping("/impress/manage/del")
	@ResponseBody
	public Map<String, Object> impressDel(ModelMap model, Long id) {
		return backManageService.impressDel(model, id);
	}

	@RequestMapping("/impress/detail")
	public String impressDetail(ModelMap model, Long id, Long supplierId) {
		backManageService.impressDetail(model, id, supplierId);
		return "/backmanager/impress/detail";
	}

	@RequestMapping("/impress/manage/edit")
	@ResponseBody
	public Map<String, Object> impressEdit(ModelMap model, @ModelAttribute Impress impress) {
		return backManageService.impressEdit(model, impress);
	}

	/** ----------------商家评论--------------------- **/
	@RequestMapping("/comment/audit/page")
	public String listCommentAudit(ModelMap model, @ModelAttribute BackManageCnd backManageCnd) {
		backManageCnd.setOper(-1);
		backManageService.listComment(model, backManageCnd);
		return "/backmanager/comment/commentAudit";
	}

	@RequestMapping("/comments/audit")
	@ResponseBody
	public Map<String, Object> commentAudit(ModelMap model, String id, boolean isPassed) {
		return backManageService.commentAudit(model, id, isPassed);
	}

	@RequestMapping("/comment/manage/page")
	public String listCommentManage(ModelMap model, @ModelAttribute BackManageCnd backManageCnd) {
		Integer oper = backManageCnd.getOper();
		if (oper != null && oper == -2) {
			backManageCnd.setOper(null);
		}
		backManageService.listComment(model, backManageCnd);
		return "/backmanager/comment/commentManage";
	}

	@RequestMapping("/comment/detail")
	public String commentDetail(ModelMap model, Long id, Long supplierId) {
		backManageService.commentDetail(model, id, supplierId);
		return "/backmanager/comment/detail";
	}

	@RequestMapping("/comment/manage/del")
	@ResponseBody
	public Map<String, Object> commentDel(ModelMap model, Long id) {
		return backManageService.commentDel(model, id);
	}

	@RequestMapping("/comment/manage/edit")
	@ResponseBody
	public Map<String, Object> commentEdit(ModelMap model, @ModelAttribute Comment comment) {
		return backManageService.commentEdit(model, comment);
	}

	@RequestMapping("/supplier/detail")
	public String supplierDetail(ModelMap model, Long id) {
		backManageService.supplierDetail(model, id);
		return "/backmanager/supplier/detail";
	}

	@RequestMapping("/supplier/manage/del")
	@ResponseBody
	public Map<String, Object> supplierDel(ModelMap model, Long id) {
		return backManageService.supplierDel(model, id);
	}

	@RequestMapping("/supplier/manage/edit")
	@ResponseBody
	public Map<String, Object> supplierEdit(ModelMap model, @ModelAttribute Suppliser supplier) {
		return backManageService.supplierEdit(model, supplier);
	}

	@RequestMapping(value = "/supplier/logo/upload", method = RequestMethod.POST)
	@ResponseBody
	public OperationDto editAvatar(@RequestParam(value = "Filedata") MultipartFile fileData, ModelMap model) {
		return backManageService.uploadFile(fileData);
	}

	@RequestMapping(value = "/supplier/logo/cutAvatar", method = RequestMethod.POST)
	@ResponseBody
	public OperationDto cutAvatar(String filePath, Integer x, Integer y, Integer destWidth, Integer destHeight, Model model) {
		if (StringUtils.isBlank(filePath)) {
			return null;
		}
		OperationDto oper = backManageService.cutImageAndSave(filePath, x, y, destWidth, destHeight);
		return oper;
	};

	@RequestMapping(value = "/supplier/logo/delete", method = RequestMethod.GET)
	@ResponseBody
	public OperationDto cutAvatar(String filePath) {
		System.out.println(filePath);
		if (StringUtils.isBlank(filePath)) {
			return null;
		}
		OperationDto oper = backManageService.deleteImage(filePath);
		return oper;
	};

	/** ----------------敏感词--------------------- **/
	@RequestMapping("/word/manage/page")
	public String wordList(ModelMap model, Integer currentPage, @RequestParam(value = "word", required = false) String word) {
		backManageService.wordList(model, currentPage, word);
		return "/backmanager/word/wordList";
	}

	@RequestMapping("/word/manage/detail")
	public String wordDetail(ModelMap model, Long id, @RequestParam(value = "url", required = false) String url) {
		if (id != null)
			backManageService.wordDetail(model, id);
		model.addAttribute("url", url);
		return "/backmanager/word/detail";
	}

	@RequestMapping("/word/manage/edit")
	@ResponseBody
	public Map<String, Object> wordEdit(ModelMap model, @ModelAttribute Word word) {
		return backManageService.wordEdit(word);
	}

	@RequestMapping("/word/manage/del")
	@ResponseBody
	public Map<String, Object> wordRemove(ModelMap model, Long id) {
		return backManageService.wordRemove(id);
	}

	@RequestMapping("/word/manage/reload")
	@ResponseBody
	public Map<String, Object> wordReload(ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			KeywordFilter.reload();
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
		}
		return map;
	}

	/*---------------------------------------category操作-----------------------------------------------*/
	@RequestMapping("/category/page")
	public String category() {
		return "/backmanager/category/category";
	}

	@RequestMapping(value = "/category/save", method = RequestMethod.POST)
	public void save(HttpServletRequest request, HttpServletResponse response, String content) throws Exception {
		Boolean b = backManageService.saveCategory(content);
		output(0, b.toString(), null);
	}

	@RequestMapping("/cache/reload/page")
	public String reloadCache() {
		return "/backmanager/cache/cache-manager";
	}

	/*---------------------------------------recommon操作-----------------------------------------------*/
	@RequestMapping("/recommend/page")
	public String recommend(ModelMap map, Integer currentPage, String startDate, String endDate) {
		PageView<SearchRecommend> pageView = backManageService.getRecommends(startDate, endDate, currentPage);
		map.addAttribute("pageView", pageView);
		map.addAttribute("startDate", startDate);
		map.addAttribute("endDate", endDate);
		return "/backmanager/recommend/page";
	}

	@RequestMapping("/recommend/export")
	public void exportRecommend(ModelMap map, HttpServletRequest request, HttpServletResponse response, String startDate, String endDate, String fileName) {
		try {
			backManageService.exportRecommend(response, startDate, endDate, fileName);
		} catch (Exception e) {
			log.error("/recommend/export-------------" + e.getMessage());
		}
	}

	/*---------------------------------------config操作-----------------------------------------------*/
	@RequestMapping("/config/get")
	public String getConfig(ModelMap map) {
		Integer value = 0;
		try {
			value = backManageService.getConfig();
		} catch (Exception e) {
			log.error("/config/get-------------" + e.getMessage());
		}
		map.addAttribute("source", value);
		return "/backmanager/config/priceTrend";
	}

	@RequestMapping(value = "/config/save", method = RequestMethod.POST)
	@ResponseBody
	public void saveConfig(ModelMap map, Integer source) throws Exception {
		Boolean b = backManageService.saveConfig(source);
		output(0, b.toString(), null);
	}

	@RequestMapping("/pageConfig/get")
	public String getPageConfig(ModelMap map) {
		List<SearchHotKey> list = new ArrayList<SearchHotKey>();
		try {
			list = backManageService.getPageConfig();
		} catch (Exception e) {
			log.error("/config/get-------------" + e.getMessage());
		}
		map.addAttribute("list", list);
		return "/backmanager/config/pageInfo";
	}

	@RequestMapping(value = "/pageConfig/save", method = RequestMethod.POST)
	@ResponseBody
	public void savePageConfig(ModelMap map, String value) throws Exception {
		Boolean b = backManageService.savePageConfig(value);
		output(0, b.toString(), null);
	}

	@RequestMapping(value = "/pageConfig/delete", method = RequestMethod.POST)
	@ResponseBody
	public void deletePageConfig(ModelMap map, String value) throws Exception {
		Boolean b = backManageService.deletePageConfig(value);
		output(0, b.toString(), null);
	}

	
	/*---------------------------------------资讯管理-----------------------------------------------*/
	
	@RequestMapping("/info/infos")
	public String getInfos(ModelMap map, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "currentPage", required = false, defaultValue="1") Integer currentPage, @RequestParam(value = "pageSize", required = false, defaultValue="10") Integer pageSize) {
		PageView<Info> pageView = backManageService.queryInfos(currentPage, pageSize, keyword);
		if (!StringUtils.isEmpty(keyword)) {
			map.addAttribute("keyword", keyword);
		}
		map.addAttribute("pageView", pageView);
		return "/backmanager/info/infos";
	}
	
	@RequestMapping("/info/edit")
	public String getInfo(ModelMap map, @RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Info info = backManageService.getInfo(id);
			map.addAttribute("bean", info);
		}
		return "/backmanager/info/detail";
	}
	
	@RequestMapping(value = "/info/save", method = RequestMethod.POST)
	@ResponseBody
	public void saveInfo(String title, String content, @RequestParam(value = "id", required = false) Long id) throws Exception {
		Long Id = backManageService.saveInfo(title, content, id);
		output(0, "保存成功", Id);
	}
	
	@RequestMapping(value = "/info/delete", method = RequestMethod.POST)
	@ResponseBody
	public void deleteInfo(Long id) throws Exception {
		Boolean b = backManageService.deleteInfo(id);
		output(0, "删除成功", b);
	}
	
	/*---------------------------------------关键词管理-----------------------------------------------*/
	
	@RequestMapping("/key/keys")
	public String getKeys(ModelMap map, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "currentPage", required = false, defaultValue="1") Integer currentPage, @RequestParam(value = "pageSize", required = false, defaultValue="10") Integer pageSize) {
		PageView<Key> pageView = backManageService.queryKeys(currentPage, pageSize, keyword);
		if (!StringUtils.isEmpty(keyword)) {
			map.addAttribute("keyword", keyword);
		}
		map.addAttribute("pageView", pageView);
		return "/backmanager/info/keys";
	}
	
	@RequestMapping("/key/edit")
	public String getKey(ModelMap map, @RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Key key = backManageService.getKey(id);
			map.addAttribute("bean", key);
		}
		return "/backmanager/info/key_detail";
	}
	
	@RequestMapping(value = "/key/save", method = RequestMethod.POST)
	@ResponseBody
	public void saveKey(String name, @RequestParam(value = "id", required = false) Long id) throws Exception {
		Long Id = backManageService.saveKey(name, id);
		output(0, "保存成功", Id);
	}
	
	@RequestMapping(value = "/key/delete", method = RequestMethod.POST)
	@ResponseBody
	public void deleteKey(Long id) throws Exception {
		Boolean b = backManageService.deleteKey(id);
		output(0, "删除成功", b);
	}
	
	/*---------------------------------------topic管理-----------------------------------------------*/
	
	@RequestMapping("/link/links")
	public String getLinks(ModelMap map, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "currentPage", required = false, defaultValue="1") Integer currentPage, @RequestParam(value = "pageSize", required = false, defaultValue="10") Integer pageSize) {
		PageView<Link> pageView = backManageService.queryLinks(currentPage, pageSize, keyword);
		if (!StringUtils.isEmpty(keyword)) {
			map.addAttribute("keyword", keyword);
		}
		map.addAttribute("pageView", pageView);
		return "/backmanager/info/links";
	}
	
	@RequestMapping("/link/edit")
	public String getLink(ModelMap map, @RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Link link = backManageService.getLink(id);
			map.addAttribute("bean", link);
		}
		return "/backmanager/info/link_detail";
	}
	
	@RequestMapping(value = "/link/save", method = RequestMethod.POST)
	@ResponseBody
	public void saveLink(String name, String link, @RequestParam(value = "id", required = false) Long id) throws Exception {
		Long Id = backManageService.saveLink(name, link, id);
		output(0, "保存成功", Id);
	}
	
	/*---------------------------------------描述管理-----------------------------------------------*/
	
	@RequestMapping("/meta/metas")
	public String getLinks(ModelMap map, @RequestParam(value = "currentPage", required = false, defaultValue="1") Integer currentPage, @RequestParam(value = "pageSize", required = false, defaultValue="10") Integer pageSize) {
		PageView<Meta> pageView = backManageService.queryMetas(currentPage, pageSize);
		map.addAttribute("pageView", pageView);
		return "/backmanager/info/metas";
	}
	
	@RequestMapping("/meta/edit")
	public String getMeta(ModelMap map, @RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Meta bean = backManageService.getMeta(id);
			map.addAttribute("bean", bean);
		}
		return "/backmanager/info/meta_detail";
	}
	
	@RequestMapping(value = "/meta/save", method = RequestMethod.POST)
	@ResponseBody
	public void saveLink(String title, String des, String keyword, String pageName, @RequestParam(value = "id", required = false) Long id) throws Exception {
		Long Id = backManageService.saveMeta(pageName, title, des, keyword, id);
		output(0, "保存成功", Id);
	}
	
}
