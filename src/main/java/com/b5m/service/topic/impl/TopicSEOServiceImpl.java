package com.b5m.service.topic.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.DateTools;
import com.b5m.bean.entity.Info;
import com.b5m.bean.entity.Key;
import com.b5m.bean.entity.Link;
import com.b5m.bean.entity.Meta;
import com.b5m.dao.Dao;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.cnd.Op;
import com.b5m.service.topic.TopicSEOService;

@Service
public class TopicSEOServiceImpl implements TopicSEOService {

	@Autowired
	private Dao dao;

	/**
	 * link : name
	 */
	public static Map<String, String> map = new HashMap<String, String>();

	@SuppressWarnings("unused")
	private void init() {
		List<Link> list = dao.queryAll(Link.class);
		for (Link link : list) {
			String name = link.getName();
			String linkString = link.getLink();
			String e_link = getLink(name);
			if (e_link != null) {
				map.remove(e_link);
				map.put(linkString, name);
			}
		}
	}

	@Override
	public List<Info> getInfos(String keyword) {
		Cnd cnd = Cnd._new();
		if (!StringUtils.isEmpty(keyword)) {
			cnd.add("title", Op.LIKE, keyword);
			cnd.or("content", Op.LIKE, keyword);
		}
		List<Info> list = dao.query(Info.class, cnd);
		for (Info info : list) {
			info.setTime(DateTools.formate(info.getUpdateTime(), "yyyy年MM月dd日"));
		}
		return list;
	}

	@Override
	public String getLink(String name) {
		for (Iterator<Entry<String, String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			String e_link = entry.getKey();
			String e_name = entry.getValue();
			if (e_name.equals(name)) {
				return e_link;
			}
		}
		return null;
	}

	@Override
	public Info getInfo(Long id) {
		Info info = dao.get(Info.class, id);
		info.setTime(DateTools.formate(info.getUpdateTime(), "yyyy年MM月dd日"));
		return info;
	}

	@Override
	public List<Key> getKeys() {
		List<Key> list = dao.queryAll(Key.class);
		return list;
	}
	
	@Override
	public Meta getMeta(String pageName) {
		Meta bean = new Meta();
		List<Meta> list = dao.query(Meta.class, Cnd.where("pageName", Op.EQ, pageName));
		if (!CollectionTools.isEmpty(list)) {
			bean = list.get(0);
		}
		return bean;
	}

	public static Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		TopicSEOServiceImpl.map = map;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

}
