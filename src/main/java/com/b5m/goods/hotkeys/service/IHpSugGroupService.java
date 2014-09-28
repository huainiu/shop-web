package com.b5m.goods.hotkeys.service;

import java.util.List;
import java.util.Map;

import com.b5m.goods.hotkeys.dto.HpSugGroupDto;

public interface IHpSugGroupService {

	/**
	 * 
	 * @return key is groupName, value is HpSugGroupDto
	 */
	Map<String, HpSugGroupDto> findAll();
	
	/**
	 * 通过Group Name进行查找
	 * @param groupName
	 * @return
	 */
	HpSugGroupDto findByGroupName(String groupName);
	
	/**
	 * 获取热搜词，Key是热搜词，Value是Url
	 * @return
	 */
	List<String> getHotKeywords();
}
