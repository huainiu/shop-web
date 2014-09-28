package com.b5m.client.autofill;

import java.util.List;
import java.util.Map;

import com.b5m.bean.dto.AutoFillInfo;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-11-5
 * @email wuming@b5m.com
 */
public interface SF1AutoFillClient {
	
	Map<String, List<AutoFillInfo>> allAutoFillSearch(String prefix, Integer pageSize) throws Exception;
	
}
