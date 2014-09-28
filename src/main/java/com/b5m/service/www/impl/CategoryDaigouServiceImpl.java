package com.b5m.service.www.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b5m.bean.entity.CategoryDaigou;
import com.b5m.common.spring.aop.Cache;
import com.b5m.dao.Dao;
import com.b5m.service.www.CategoryDaigouService;

@Service("categoryDaigouService")
public class CategoryDaigouServiceImpl implements CategoryDaigouService {

	@Autowired
	private Dao dao;
	
	@Override
	@Cache(cacheEmpty = true, timeout = 86400, key="all_categorydaigou")
	public List<CategoryDaigou> queryAllCategory() {
		return dao.queryAll(CategoryDaigou.class);
	}

}
