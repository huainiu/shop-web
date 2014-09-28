package com.b5m.goods.promotions.cached;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.b5m.cached.CachedBase;
import com.b5m.cached.CachedConfigure;
import com.b5m.cached.ICachedProxy;
import com.b5m.cached.exception.CachedException;
import com.b5m.cached.template.CachedTemplateBase;
import com.b5m.cached.template.ICachedTemplate;
import com.b5m.cached.template.ISourceExtracter;
import com.b5m.cached.template.LocalCache;
import com.b5m.cached.template.SimpleCachedTemplate;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.dto.SuppliserPromotionsDto;
import com.b5m.goods.promotions.service.ISuppliserService;
/**
 * 使用了 {@link ICachedTemplate}对象的缓存服务
 * @author jacky
 *
 */
public class SuppliserCachedTemplateService extends CachedBase implements
		ISuppliserService {
	public static final String KEY_ALL_SUPPLISERS = "all_supplisers";
	
	public static final String KEY_ALL_SUPPLISER_PROM = "all_suppliser_proms";
	
	private final ICachedTemplate cachedTemplate;
	
	private final ISuppliserService suppliserService;
	
	private LocalCache localCache;
	
	private final Logger logger = Logger.getLogger(this.getClass());

	public SuppliserCachedTemplateService(ICachedProxy proxy,
			ISuppliserService suppliserService, CachedConfigure configure) {
		super(proxy, configure);
		this.suppliserService = suppliserService;
		cachedTemplate = new SimpleCachedTemplate(this);
		localCache = new LocalCache(configure.getExpiredTime());
		addExtracters();		
	}
	
	/*private Map<String, SuppliserDto> convertToMap(List<SuppliserDto> suppliserDtos){
		Map<String, SuppliserDto> mapSuppliser = new HashMap<String, SuppliserDto>(suppliserDtos.size());
		for(SuppliserDto suppliserDto : suppliserDtos){
			if(null == suppliserDto.getName() ||
					"".equals(suppliserDto.getName())){
				logger.warn(new StringBuilder("a suppliser's name is empty."));
				continue;
			}
			mapSuppliser.put(suppliserDto.getName(), suppliserDto);
		}
		return mapSuppliser;
	}*/
	
	private void addExtracters(){
		cachedTemplate.addExtracter(KEY_ALL_SUPPLISERS, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				/*List<SuppliserDto> suppliserDtos = suppliserService.findAllSuppliser();
				logger.debug("<load><supplisers> count:" + suppliserDtos.size());*/
				// 需要按照商户名称进行索引
				/*Map<String, SuppliserDto> mapSuppliser = convertToMap(suppliserDtos);
				return mapSuppliser;*/
				return findAllSuppliser();
			}
			
		});
		
		cachedTemplate.addExtracter(KEY_ALL_SUPPLISER_PROM, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				Map<String, SuppliserPromotionsDto>  mapSuppliserProm = suppliserService.findAllSuppliserPromotion();
				logger.debug("<load><suppliser_proms> count:" + mapSuppliserProm.size());
				return mapSuppliserProm;
			}
			
		});
	}

	@Override
	public boolean load(Object... args) throws CachedException {
		cachedTemplate.load(args);
		return true;
	}

	@Override
	public boolean unload(Object... args) throws CachedException {
		cachedTemplate.unload(args);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SuppliserDto> findAllSuppliser() {
		//try {因为suppliserDtos 这个数据量太大了，所以修改存储策略
			List<SuppliserDto> suppliserDtos = localCache.get(KEY_ALL_SUPPLISERS, List.class);
			if(suppliserDtos == null){//用mamcache 问题很多 直接用本地缓存
				suppliserDtos = suppliserService.findAllSuppliser();
				/*ICachedProxy proxy = cachedTemplate.getCacheable().getCachedProxy();
				//获取一串id
				List<String> list = proxy.getMaybeNull(KEY_ALL_SUPPLISERS, List.class);
				if(list == null){
					list = new ArrayList<String>();
					for(SuppliserDto suppliserDto : suppliserDtos){
						list.add(KEY_ALL_SUPPLISERS + suppliserDto.getId());
						proxy.put(KEY_ALL_SUPPLISERS + suppliserDto.getId(), JSON.toJSONString(suppliserDto), configure.getExpiredTime());
					}
					proxy.put(KEY_ALL_SUPPLISERS, list, configure.getExpiredTime());
				}else{
					Map<String, Object> map = proxy.gets(list);
					suppliserDtos = new ArrayList<SuppliserDto>();
					for(String key : map.keySet()){
						suppliserDtos.add(JSON.parseObject(map.get(key).toString(), SuppliserDto.class));
					}
				}*/
				localCache.put(KEY_ALL_SUPPLISERS, suppliserDtos);
			}
			return suppliserDtos;
		/*} catch (CachedException e) {
			return (List<SuppliserDto>)((CachedTemplateBase)cachedTemplate).getExtracter(KEY_ALL_SUPPLISERS).extract(null);
		}*/
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, SuppliserPromotionsDto> findAllSuppliserPromotion() {
		try {
			return cachedTemplate.get(KEY_ALL_SUPPLISER_PROM, Map.class, null);
		} catch (CachedException e) {
			return (Map<String, SuppliserPromotionsDto>)((CachedTemplateBase)cachedTemplate).getExtracter(KEY_ALL_SUPPLISER_PROM).extract(null);
		}
	}

	@Override
	public SuppliserPromotionsDto findBySource(String source) {
		return findAllSuppliserPromotion().get(source);
	}

	@Override
	public List<SuppliserDto> findByCountry(String country) {
		List<SuppliserDto> supplisers = findAllSuppliser();
		List<SuppliserDto> specifieds = new ArrayList<SuppliserDto>();
		for(SuppliserDto suppliser : supplisers){
			if(country.equals(suppliser.getCountry())){
				specifieds.add(suppliser);
			}
		}
		return specifieds;
	}

	@Override
	public List<SuppliserDto> querySupplier(String keyword) {
		return suppliserService.querySupplier(keyword);
	}

}
