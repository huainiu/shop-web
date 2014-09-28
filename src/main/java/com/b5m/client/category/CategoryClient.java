package com.b5m.client.category;

import java.util.Map;

public interface CategoryClient {
	
	/**
	 * @description
	 * 获取中英文mapping
	 * @return
	 * @author echo weng
	 * @since 2013年12月18日
	 * @mail echo.weng@b5m.com
	 */
	Map<String, String> queryZYMapping();
	
}
