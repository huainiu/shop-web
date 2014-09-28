package com.b5m.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b5m.base.common.utils.DateTools;
import com.b5m.base.common.utils.WebTools;
import com.b5m.bean.entity.Comment;
import com.b5m.bean.entity.Impress;
import com.b5m.bean.entity.Suppliser;
import com.b5m.common.env.GlobalInfo;
import com.b5m.common.log.LogUtils;
import com.b5m.common.utils.LoginHelper;
import com.b5m.common.utils.RandomValidateCode;
import com.b5m.common.utils.UcMemCachedUtils;
import com.b5m.dao.domain.page.PageView;
import com.b5m.frame.pojo.UserCenter;
import com.b5m.service.www.CommentService;
import com.b5m.service.www.ImpressService;
import com.b5m.service.www.SuppliserService;
import com.b5m.web.controller.base.AbstractBaseController;
import com.b5m.web.filter.KeywordFilter;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-7-25
 * @email echo.weng@b5m.com
 */
@Controller
@RequestMapping("/dianping")
public class DianpingController extends AbstractBaseController {

	@Autowired
	@Qualifier("pCommentService")
	private CommentService commentService;

	@Autowired
	@Qualifier("newSuppliserCacheService")
	private SuppliserService suppliserService;

	public final String PREFIX_LAST_COMMENT = "last_comment";
	public final String PREFIX_CLICK_IMPRESS = "last_click_impress";
	public final String PREFIX_IMPRESS = "last_impress";
	public final String DEFAULT_AVATAR = "";

	@Autowired
	@Qualifier("pImpressService")
	private ImpressService impressService;

	@RequestMapping
	public String commentIndex(ModelMap model, HttpServletResponse response, String source) {
		Suppliser suppliser = suppliserService.getSuppliserByName(source);
		if (suppliser == null) {
			return "commpage/404page";
		} else {
			model.addAttribute("suppliser", suppliser);
			setImpressInfo(suppliser, model);
			setSupplierList(suppliser, model);
			setCommentCount(suppliser.getId(), model);
			return "comment";
		}
	}

	@RequestMapping("/comments/list")
	@ResponseBody
	public PageView<Comment> commentList(Integer currentPage, @RequestParam(value = "supplierId", required = true) Long supplierId, Integer type) {
		if (currentPage == null)
			currentPage = 1;
		PageView<Comment> pageView = getCommentInfo(supplierId, currentPage, type);
		return pageView;
	}
	
	@RequestMapping(value = "/comments/add", method = RequestMethod.POST)
	@ResponseBody
	public void addComment(ModelMap model, HttpServletRequest request, Long supplierId, String content, int type, String validateCode) throws Exception {
		String ip = WebTools.getIpAddr(request);
		Object obj = UcMemCachedUtils.getCache(RandomValidateCode.RANDOMCODEKEY + "_" + ip);
		UcMemCachedUtils.cleanCache(RandomValidateCode.RANDOMCODEKEY + "_" + ip);
		if (obj == null || !obj.toString().equalsIgnoreCase(validateCode)) {
			output(1001, "验证码错误", false);
			return;
		}
		if (ip == null || "".equals(ip)) {
			output(1002, "未知IP", false);
			return;
		}
		if (UcMemCachedUtils.getCache(PREFIX_LAST_COMMENT + ip) != null) {
			output(1003, "您添加评论太快", false);
			return;
		}
		boolean b = KeywordFilter.isContentKeyWords(content);
		if (b) {
			output(1004, "评论内容不通过", false);
			return;
		}
		Comment comment = new Comment();
		comment.setSupplierId(supplierId);
		comment.setContent(content);
		comment.setType(type);
		UserCenter user = LoginHelper.getUserCenter(request);
		if (user != null) {
			comment.setAvatar(user.getAvatarTransient());
			comment.setBakStr1(user.getUserId());
		}
		comment.setUser(getUserName(request, user));
		comment.setCreateTime(DateTools.now());
		comment.setUpdateTime(DateTools.now());
		comment.setOper(GlobalInfo.OPER_PASS);
		commentService.addComment(comment);
		UcMemCachedUtils.setCache(PREFIX_LAST_COMMENT + ip, System.currentTimeMillis(), GlobalInfo.getCommentLimitTime());
		output(0, "", true);
	}

	@RequestMapping(value = "/impress/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addImpress(HttpServletRequest request, String content, Long supplierId) {
		String ip = WebTools.getIpAddr(request);
		Map<String, Object> map = new HashMap<String, Object>();
		if (UcMemCachedUtils.getCache(PREFIX_IMPRESS + ip) != null) {
			map.put("flag", false);
			map.put("message", "您添加印象太快");
			return map;
		}
		boolean b = KeywordFilter.isContentKeyWords(content);
		if (b) {
			map.put("flag", false);
			map.put("message", "印象内容不通过");
			return map;
		}
		try {
			Impress impress = createImpress(request, content, supplierId);
			impressService.addImpress(impress);
		} catch (Exception e) {
			LogUtils.error(this.getClass(), e);
		}
		map.put("flag", true);
		UcMemCachedUtils.setCache(PREFIX_IMPRESS + ip, System.currentTimeMillis(), GlobalInfo.getImpressLimitTime());
		return map;
	}

	@RequestMapping(value = "/impress/click", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> clickImpress(HttpServletRequest request, Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		String ip = WebTools.getIpAddr(request);
		if (UcMemCachedUtils.getCache(PREFIX_CLICK_IMPRESS + ip) != null) {
			map.put("flag", false);
			map.put("message", "您点击印象太快");
			return map;
		}
		try {
			impressService.clickImpress(id);
		} catch (Exception e) {
			LogUtils.error(this.getClass(), e);
		}
		map.put("flag", true);
		UcMemCachedUtils.setCache(PREFIX_CLICK_IMPRESS + ip, System.currentTimeMillis(), GlobalInfo.getImpressClickLimitTime());
		return map;
	}

	@RequestMapping(value = "/impress/click/get", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getClick(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Impress impress = impressService.getImpress(id);
			map.put("flag", true);
			map.put("impressCount", impress.getImpressCount());
			map.put("content", impress.getContent());
		} catch (Exception e) {
			map.put("flag", false);
			map.put("click", 0);
			LogUtils.error(this.getClass(), e);
		}
		return map;
	}

	@RequestMapping(value = "/impress/show/more", method = RequestMethod.POST)
	@ResponseBody
	public List<Impress> showMoreImpress(HttpServletRequest request, Long supplierId) {
		return impressService.queryPassedImpress(GlobalInfo.getImpressPageSize(), 5000, supplierId);
	}

	private Impress createImpress(HttpServletRequest request, String content, Long supplierId) {
		Impress impress = new Impress();
		impress.setContent(content);
		impress.setCreateTime(DateTools.now());
		impress.setImpressCount(1L);
		impress.setOper(GlobalInfo.OPER_PASS);
		impress.setSupplierId(supplierId);
		impress.setUser(getUserName(request, null));
		impress.setUpdateTime(DateTools.now());
		return impress;
	}

	private String getUserName(HttpServletRequest request, UserCenter user) {
		String ip = WebTools.getIpAddr(request);
		String name = user != null ? user.getEmail() : ip;
		return name;
	}

	private void setImpressInfo(Suppliser suppliser, ModelMap model) {
		List<Impress> impresses = impressService.queryPassedImpress(0, GlobalInfo.getImpressPageSize(), suppliser.getId());
		model.put("impressList", impresses);
	}

	private void setCommentCount(Long supplierId, ModelMap model) {
		model.put("countMap", commentService.queryCountByType(supplierId));
	}

	private void setSupplierList(Suppliser suppliser, ModelMap model) {
		List<Suppliser> supplisers = suppliserService.listSuppliser();
		List<Long> supplierIds = getSupplierIds(supplisers);
		Map<Long, Integer> totalCommentNumMap = commentService.queryCommentCount(supplierIds, null);
		Map<Long, Integer> goodCommentNumMap = commentService.queryCommentCount(supplierIds, 0);
		Map<Long, Integer> impressNumMap = impressService.queryImpressCountMap(supplierIds);
		for (Suppliser s : supplisers) {
			s.setImpressNum(impressNumMap.get(s.getId()));
			int totalNum = totalCommentNumMap.get(s.getId());
			s.setCommentNum(totalNum);
			int goodNum = goodCommentNumMap.get(s.getId());
			s.setGoodPinNum(goodNum);
			if (totalNum != 0) {
				s.setPercent(new BigDecimal(goodNum * 100).divide(new BigDecimal(totalNum), 0, RoundingMode.UP));
			} else {
				s.setPercent(new BigDecimal(100));
			}
			if (suppliser.getId() == s.getId()) {
				suppliser.setImpressNum(s.getImpressNum());
				suppliser.setCommentNum(s.getCommentNum());
				suppliser.setPercent(s.getPercent());
				suppliser.setGoodPinNum(suppliser.getGoodPinNum());
			}
		}
		model.put("supplierList", supplisers);
	}

	private List<Long> getSupplierIds(List<Suppliser> supplisers){
		List<Long> ids = new ArrayList<Long>(10);
		for (Suppliser s : supplisers) {
			ids.add(s.getId());
		}
		return ids;
	}
	
	private PageView<Comment> getCommentInfo(Long suppliserId, Integer currentPage, Integer type) {
		PageView<Comment> pageView = commentService.queryPassedPage(currentPage, GlobalInfo.getCommentPageSize(), suppliserId, type);
		List<Comment> list = pageView.getRecords();
		// 隐藏IP后两位
		String reg = "^(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)$";
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setUser(list.get(i).getUser().replaceAll(reg, "$1.$2.*.*"));
		}
		return pageView;
	}

}
