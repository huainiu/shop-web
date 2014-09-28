package com.b5m.service.www;

import java.util.List;

import com.b5m.bean.entity.HotKeywords;

public interface HotKeywordsService {
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 随即返回数据
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年4月17日 下午1:52:21
	 *
	 * @param allKeywords
	 * @param size
	 * @return
	 */
	List<HotKeywords> randomKeywords(List<HotKeywords> allKeywords, String channel, int size);
	
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 获取某个频道的所有数据
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年4月17日 下午1:52:34
	 *
	 * @param channel
	 * @return
	 */
	List<HotKeywords> queryAllHotKeywords(String channel);
	
}
