package com.b5m.service.exchange;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.b5m.bean.dto.exchange.AddressDto;
import com.b5m.bean.dto.exchange.GoodsDto;

public interface IExchangeService {

	JSONObject getUserAddress(String userId);

	JSONObject updateOrAddAddress(AddressDto dto);

	JSONObject deleteAddress(AddressDto dto);

	JSONObject order(GoodsDto dto);

	boolean setHotInfo(String docId, Map<String, String> map);

	boolean setGoodsInfo(GoodsDto dto);

	JSONObject getHotProduct(String id);
}
