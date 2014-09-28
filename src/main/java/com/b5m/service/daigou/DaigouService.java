package com.b5m.service.daigou;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface DaigouService {
	
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 返回代购商品
	 * 判断其他商城的价格是否低于 天猫最低价且低于京东最低价
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年5月12日 下午2:21:40
	 *
	 * @param shopInfoDtos
	 * @return
	 */
	JSONObject returnDaigouShop(JSONArray jsonArray);
	
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 是否能进行代购
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年5月30日 下午3:29:22
	 *
	 * @param produce
	 * @param needOntimePrice 是否时时获取价格
	 * @return
	 */
	boolean canDaigou(Map<String, Object> produce, String col);
	
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 判断并设置能代购的表识符
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年5月30日 下午3:44:50
	 *
	 * @param produce
	 * @return
	 */
	boolean canDaigouAndSetFlag(Map<String, String> produce);
}
