package com.b5m.service.www.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b5m.bean.entity.HotKeywords;
import com.b5m.common.spring.aop.Cache;
import com.b5m.dao.Dao;
import com.b5m.service.www.HotKeywordsService;

@Service("hotKeywordsService")
public class HotKeywordsServiceImpl implements HotKeywordsService{
	
	@Autowired
	private Dao dao;

	@Override
	@Cache(cacheEmpty = true, timeout = 3600)
	public List<HotKeywords> randomKeywords(List<HotKeywords> allKeywords, String channel, int size){
		int totalSize = allKeywords.size();
		if(size >= totalSize) return allKeywords;
		Random random = new Random();
		List<HotKeywords> newList = new ArrayList<HotKeywords>(size);
		List<HotKeywords> newAllKeywords = new ArrayList<HotKeywords>(allKeywords);
		for(int i = 0;;i++){
			int index = random.nextInt(totalSize);
			newList.add(newAllKeywords.get(index));
			if(newList.size() == size){
				break;
			}
			newAllKeywords.remove(index);
			totalSize--;
		}
		return newList;
	}
	
	@Override
	@Cache(cacheEmpty = true, localCache = true, timeout = 86400)
	public List<HotKeywords> queryAllHotKeywords(String channel){
		List<HotKeywords> keyHotKeywords = dao.queryBySql("select id, name from t_hot_keywords where channel = ?", new String[]{channel} , HotKeywords.class);
//		List<HotKeywords> keyHotKeywords = dao.query(HotKeywords.class, Cnd.where("channel", Op.EQ, channel));
		return keyHotKeywords;
	}
	
}
