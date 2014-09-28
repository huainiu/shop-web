package com.b5m.goods.hotkeys.cached;

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
import com.b5m.cached.template.SimpleCachedTemplate;
import com.b5m.goods.hotkeys.dto.HpSugGroupDto;
import com.b5m.goods.hotkeys.service.IHpSugGroupService;

public class HpSugGroupCachedTemplateService extends CachedBase implements
		IHpSugGroupService {
	public static final String KEY_KEYWORDS_HOME = "keywords_home";
	
	public static final String KEY_HP_GROUPS = "hp_groups";
	
	private final ICachedTemplate cachedTemplate;

	private final IHpSugGroupService hpHugGroupService;
	
	private final Logger logger = Logger.getLogger(this.getClass());

	public HpSugGroupCachedTemplateService(ICachedProxy proxy,IHpSugGroupService hpHugGroupService,
			CachedConfigure configure) {
		super(proxy, configure);
		this.hpHugGroupService = hpHugGroupService;
		cachedTemplate = new SimpleCachedTemplate(this);
		
		addExtracters();
	}
	
	private void addExtracters(){
		cachedTemplate.addExtracter(KEY_KEYWORDS_HOME, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				List<String> hotKeywords = hpHugGroupService.getHotKeywords();
				logger.debug("<load><keywords> count:" + hotKeywords.size());
				return hotKeywords;
			}
			
		});
		
		cachedTemplate.addExtracter(KEY_HP_GROUPS, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				Map<String, HpSugGroupDto> hpGroups = hpHugGroupService.findAll();
				logger.debug("<load><hpSugGroupDto> count:" + hpGroups.size());
				return hpGroups;
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
	public Map<String, HpSugGroupDto> findAll() {
		try {
			return cachedTemplate.get(KEY_HP_GROUPS, Map.class, null);
		} catch (CachedException e) {
			return (Map<String, HpSugGroupDto>)((CachedTemplateBase)cachedTemplate).getExtracter(KEY_HP_GROUPS).extract(null);
		}
	}

	@Override
	public HpSugGroupDto findByGroupName(String groupName) {
		return findAll().get(groupName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getHotKeywords() {
		try {
			return cachedTemplate.get(KEY_KEYWORDS_HOME, List.class, null);
		} catch (CachedException e) {
			return (List<String>)((CachedTemplateBase)cachedTemplate).getExtracter(KEY_KEYWORDS_HOME).extract(null);
		}
	}

}
