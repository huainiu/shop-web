package com.b5m.service.www.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.b5m.bean.entity.Suppliser;
import com.b5m.cached.CachedBase;
import com.b5m.cached.CachedConfigure;
import com.b5m.cached.ICachedProxy;
import com.b5m.cached.exception.CachedException;
import com.b5m.cached.template.ISourceExtracter;
import com.b5m.cached.template.SimpleCachedTemplate;
import com.b5m.common.log.LogUtils;
import com.b5m.service.www.SuppliserService;

/**
 *@author echo
 */
public class SuppliserCacheServiceImpl extends CachedBase implements SuppliserService{
	private static final String KEY_ALL_NEW_SUPPLIER = "KEY_ALL_NEW_SUPPLIER";
	private final SimpleCachedTemplate template;
	
	@Autowired
	@Qualifier("newSuppliserService")
	private SuppliserService suppliserService;

	public SuppliserCacheServiceImpl(ICachedProxy proxy,
			CachedConfigure configure) {
		super(proxy, configure);
		template = new SimpleCachedTemplate(this);
		addExtracters();
	}

	private void addExtracters() {
		template.addExtracter(KEY_ALL_NEW_SUPPLIER, new ISourceExtracter<List<Suppliser>>() {
			
			@Override
			public List<Suppliser> extract(Object... args) {
				List<Suppliser> supplisers = suppliserService.listSuppliser();
				LogUtils.info(this.getClass(), "load successed, have been load supplier size is[" + supplisers.size() + "]");
				return supplisers;
			}
		});
	}

	@Override
	public List<Suppliser> listSuppliser() {
		try {
			return template.get(KEY_ALL_NEW_SUPPLIER, List.class);
		} catch (CachedException e) {
			return suppliserService.listSuppliser();
		}
	}

	@Override
	public void saveSuppliser(Suppliser suppliser) {
		suppliserService.saveSuppliser(suppliser);
		template.load();
	}

	@Override
	public void removeSuppliser(Long id) {
		suppliserService.removeSuppliser(id);
		template.load();
	}

	@Override
	public void updateSuppliser(Suppliser suppliser) {
		suppliserService.updateSuppliser(suppliser);
		template.load();
	}

	@Override
	public Suppliser querySuppliserById(Long id) {
		List<Suppliser> supplisers = listSuppliser();
		for(Suppliser suppliser : supplisers){
			if(id == suppliser.getId()){
				return suppliser;
			}
		}
		return suppliserService.querySuppliserById(id);
	}

	@Override
	public boolean load(Object... args) throws CachedException {
		template.load(args);
		return true;
	}

	@Override
	public boolean unload(Object... args) throws CachedException {
		template.unload(args);
		return true;
	}

	@Override
	public Suppliser getSuppliserByName(String name) {
		List<Suppliser> supplisers = listSuppliser();
		for(Suppliser suppliser : supplisers){
			if(suppliser.getName().equals(name)){
				return suppliser;
			}
		}
		return suppliserService.getSuppliserByName(name);
	}

}
