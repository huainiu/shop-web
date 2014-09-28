package com.b5m.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.b5m.bean.entity.Info;
import com.b5m.bean.entity.Key;
import com.b5m.service.topic.TopicSEOService;
import com.b5m.service.topic.impl.TopicSEOServiceImpl;
import com.b5m.web.controller.base.AbstractBaseController;

@Controller
@RequestMapping("/topic")
public class TopicSEOController extends AbstractBaseController {

	@Autowired
	private TopicSEOService topicSEOServiceImpl;
	
	@RequestMapping(value = "/{path}")
	public ModelAndView getTopic(ModelAndView model, HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("path") String path,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "id", required = false) Long id) throws Exception {
		String name = TopicSEOServiceImpl.map.get(path);
		if (name != null) {
			model.setViewName("forward:/topic/seo/"+name + ".htm");
			model.addObject("keyword", keyword);
			model.addObject("id", id);
		} else {
			model.setViewName("commpage/404page");
		}
		return model;
	}
	
	@RequestMapping("/seo/infos")
	public String infos(ModelMap model, String keyword) throws Exception {
		List<Info> list = topicSEOServiceImpl.getInfos(keyword);
		model.addAttribute("list", list);
		model.addAttribute("word", keyword);
		model.addAttribute("info_link", topicSEOServiceImpl.getLink("info"));
		model.addAttribute("meta", topicSEOServiceImpl.getMeta("infos"));
		return "seo/infos";
	}
	
	@RequestMapping("/seo/info")
	public String info(ModelMap model, Long id) throws Exception {
		Info info = topicSEOServiceImpl.getInfo(id);
		model.addAttribute("bean", info);
		model.addAttribute("meta", topicSEOServiceImpl.getMeta("info"));
		return "seo/info";
	}
	
	@RequestMapping("/seo/keys")
	public String keys(ModelMap model) throws Exception {
		List<Key> list = topicSEOServiceImpl.getKeys();
		model.addAttribute("list", list);
		model.addAttribute("infos_link", topicSEOServiceImpl.getLink("infos"));
		model.addAttribute("meta", topicSEOServiceImpl.getMeta("keys"));
		return "seo/keys";
	}

}
