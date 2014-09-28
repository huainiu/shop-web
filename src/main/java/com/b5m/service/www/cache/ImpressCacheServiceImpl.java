package com.b5m.service.www.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.b5m.base.common.utils.CollectionTools;
import com.b5m.bean.entity.Impress;
import com.b5m.cached.CachedBase;
import com.b5m.cached.CachedConfigure;
import com.b5m.cached.ICachedProxy;
import com.b5m.cached.exception.CachedException;
import com.b5m.common.env.GlobalInfo;
import com.b5m.common.log.LogUtils;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.cnd.Op;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;
import com.b5m.service.www.ImpressService;
import com.b5m.service.www.utils.CacheUtils;
import com.b5m.service.www.utils.JsonUtils;

/**
 * @Company B5M.com
 * @description 评论缓存服务类
 * @author echo
 * @since 2013-6-18
 * @email echo.weng@b5m.com
 */
public class ImpressCacheServiceImpl extends CachedBase implements ImpressService {

	@Autowired
	@Qualifier("impressService")
	private ImpressService impressService;

	public ImpressCacheServiceImpl(ICachedProxy proxy, CachedConfigure configure) {
		super(proxy, configure);
	}

	@Override
	public boolean load(Object... args) throws CachedException {
		return false;
	}

	@Override
	public boolean unload(Object... args) throws CachedException {
		return false;
	}

	@Override
	public void addImpress(Impress impress) {
		impressService.addImpress(impress);
		if (impress.getOper() == GlobalInfo.OPER_PASS) {
			addCache(impress);
		}
	}

	@Override
	public Impress getImpress(Long id) {
		try {
			String json = proxy.get(CacheUtils.getImpressKey(id), String.class);
			if (JsonUtils.isEmpty(json)) {
				return impressService.getImpress(id);
			}
			return JsonUtils.json2Impress(json);
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
			return impressService.getImpress(id);
		}
	}

	@Override
	public Impress getImpressFromDb(Long id) {
		return impressService.getImpress(id);
	}

	@Override
	public List<Impress> getImpresss(List<Long> ids) {
		List<Impress> impressList = new ArrayList<Impress>();
		List<Long> fromDbIds = new ArrayList<Long>(ids.size());
		try {
			List<String> keyCollections = new ArrayList<String>();
    		for(Long id : ids){
    			keyCollections.add(CacheUtils.getImpressKey(id));
    		}
    		Map<String, String> resultMap = proxy.gets(keyCollections, String.class);
    		for(Long id : ids){
    			String json = resultMap.get(CacheUtils.getImpressKey(id));
    			if(JsonUtils.isEmpty(json)){
        			fromDbIds.add(id);
        			continue;
        		}
    			impressList.add(JsonUtils.json2Impress(json));
    		}
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
			impressList = impressService.getImpresss(ids);
			updateCaches(impressList);
			return impressList;
		}
		List<Impress> impressListbd = impressService.getImpresss(fromDbIds);
		updateCaches(impressListbd);
		impressList.addAll(impressListbd);
		return impressList;
	}

	@Override
	public void updateImpress(Impress impress) {
		Impress impressdb = impressService.getImpress(impress.getId());
		impressService.updateImpress(impress);
		if ((impressdb.getOper() == GlobalInfo.OPER_UN_REVIEW || impressdb.getOper() == GlobalInfo.OPER_UN_PASS) && impress.getOper() == GlobalInfo.OPER_PASS) {
			addCache(impress);
		}
		if (impressdb.getOper() == GlobalInfo.OPER_PASS && impress.getOper() == GlobalInfo.OPER_UN_PASS) {
			removeCache(impress);
		}
		if (impressdb.getOper() == GlobalInfo.OPER_PASS && impress.getOper() == GlobalInfo.OPER_PASS) {
			updateCache(impress);
		}
	}

	@Override
	public void deleteImpress(Impress impress) {
		impressService.deleteImpress(impress);
		if (impress.getOper() == GlobalInfo.OPER_PASS) {// 表示通过
			removeCache(impress);
		}
	}

	@Override
	public void clickImpress(Long id) {
		impressService.clickImpress(id);
		Impress impress = impressService.getImpress(id);
		updateCache(impress);
	}

	@Override
	public List<Impress> queryPassedImpress(int startIndex, int size, Long suppliserId) {
		return queryImpressPages(startIndex, size, suppliserId);
	}

	/**
	 * 查询length小于4的印象
	 */
	@Override
	public List<Impress> queryImpressLessLength(int size, Long suppliserId) {
		int count = 0;
		int length = 0;
		int sum = queryImpressCount(suppliserId);
		List<Impress> result = new ArrayList<Impress>();
		while (sum >= size * count) {
			List<Impress> list = queryImpressPages(count*size , size, suppliserId);
			for (int i = 0; i < list.size(); i++) {
				Impress impress = list.get(i);
				if (impress.getContent().length() <= 4) {
					result.add(impress);
					length++;
					if (length >= size) {
						return result;
					}
				}
			}
			count++;
		}
		return result;
	}

	public List<Impress> queryImpressPages(int startIndex, int size, Long suppliserId) {
		try {
			String json = proxy.getMaybeNull(CacheUtils.getAllImpressListKey(suppliserId), String.class);
			if (!StringUtils.isEmpty(json)) {
				List<Long> ids = JsonUtils.json2IdList(json);
				List<Long> displayIds = CollectionTools.subList(ids, startIndex, startIndex + size);
				return getImpresss(displayIds);
			} else {
				Long expired = GlobalInfo.getCommImpreExpired();
				// 如果查询不到数据 memcache又没有报错，则表明memcache中没有数据 需要重新load一下
				List<Long> ids = impressService.queryIds(getAllPassCnd(suppliserId));
				proxy.put(CacheUtils.getAllImpressListKey(suppliserId), JsonUtils.idList2Json(ids), expired);
				return impressService.queryImpress(startIndex, size, suppliserId);
			}
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
			return impressService.queryImpress(startIndex, size, suppliserId);
		}
	}

	@Override
	public int queryImpressCount(Long supplierId) {
		try {
			String json = proxy.getMaybeNull(CacheUtils.getAllImpressListKey(supplierId), String.class);
			if (!JsonUtils.isEmpty(json)) {
				List<Long> ids = JsonUtils.json2IdList(json);
				return ids.size();
			}
			updateCacheForIds(supplierId);
			return impressService.queryImpressCount(supplierId);
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
			return impressService.queryImpressCount(supplierId);
		}
	}

	@Override
	public Map<Long, Integer> queryImpressCountMap(List<Long> supplierIds){
		List<String> keyCollections = new ArrayList<String>(10);
		for(Long supplierId : supplierIds){
			keyCollections.add(CacheUtils.getAllImpressListKey(supplierId));
		}
		try {
			Map<Long, Integer> impressCountMap = new HashMap<Long, Integer>(10);
			Map<String, String> resultMap = proxy.gets(keyCollections, String.class);
			for(Long supplierId : supplierIds){
				String json = resultMap.get(CacheUtils.getAllImpressListKey(supplierId));
				if (!JsonUtils.isEmpty(json)) {
					List<Long> ids = JsonUtils.json2IdList(json);
					impressCountMap.put(supplierId, ids.size());
				}else{
					updateCacheForIds(supplierId);
					impressCountMap.put(supplierId, impressService.queryImpressCount(supplierId));
				}
			}
			return impressCountMap;
		} catch (CachedException e) {
			return impressService.queryImpressCountMap(supplierIds);
		}
	}
	
	private void updateCacheForIds(Long supplierId) throws CachedException{
		Cnd cnd = Cnd.where("supplierId", Op.EQ, supplierId).add("oper", Op.EQ, 1).desc("impressCount");
		List<Long> ids = impressService.queryIds(cnd);
		proxy.put(CacheUtils.getAllImpressListKey(supplierId), JsonUtils.idList2Json(ids), GlobalInfo.getCommImpreExpired());
	}
	
	@Override
	public List<Long> queryIds(Cnd cnd) {
		return impressService.queryIds(cnd);
	}

	@Override
	public PageView<Impress> queryPage(Cnd cnd, PageCnd pageCnd) {
		return impressService.queryPage(cnd, pageCnd);
	}

	protected void addCache(Impress impress) {
		try {
			proxy.put(CacheUtils.getImpressKey(impress.getId()), JsonUtils.impress2Json(impress), configure.getExpiredTime());
			Cnd cnd = Cnd.where("supplierId", Op.EQ, impress.getSupplierId()).add("oper", Op.EQ, 1).desc("impressCount");
			List<Long> ids = impressService.queryIds(cnd);
			proxy.put(CacheUtils.getAllImpressListKey(impress.getSupplierId()), JsonUtils.idList2Json(ids), GlobalInfo.getCommImpreExpired());
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
		}
	}

	protected void updateCache(Impress impress) {
		try {
			proxy.put(CacheUtils.getImpressKey(impress.getId()), JsonUtils.impress2Json(impress), configure.getExpiredTime());
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
		}
	}

	protected void updateCaches(List<Impress> impressList) {
		try {
			for (Impress impress : impressList) {
				proxy.put(CacheUtils.getImpressKey(impress.getId()), JsonUtils.impress2Json(impress), configure.getExpiredTime());
			}
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
		}
	}

	protected void removeCache(Impress impress) {
		try {
			try {
				proxy.remove(CacheUtils.getImpressKey(impress.getId()));
			} catch (Exception e) {
				LogUtils.error(this.getClass(), e);
			}
			List<Long> ids = impressService.queryIds(getAllPassCnd(impress.getSupplierId()));
			proxy.put(CacheUtils.getAllImpressListKey(impress.getSupplierId()), JsonUtils.idList2Json(ids), GlobalInfo.getCommImpreExpired());
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
		}
	}

	protected Cnd getAllPassCnd(Long supplierId) {
		Cnd cnd = Cnd.where("oper", Op.EQ, 1).add("supplierId", Op.EQ, supplierId).desc("impressCount");		
		return cnd;
	}

	@Override
	public List<Impress> queryImpress(int startIndex, int size, Long suppliserId) {
		return null;
	}

}
