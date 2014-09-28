package com.b5m.goods.promotions.service;

import java.util.List;
import java.util.Map;

import com.b5m.goods.promotions.dto.CPSInfoDto;
import com.b5m.goods.promotions.dto.SuppliserDto;

public interface ICPSService {

	/**
	 * 找出所有的返利信息。
	 * @return key:supplier name, value:cps info
	 */
	public Map<String, CPSInfoDto> findAllCPSInfo();
	
	public List<CPSInfoDto> findBySuppliser(SuppliserDto suppliser);
}
