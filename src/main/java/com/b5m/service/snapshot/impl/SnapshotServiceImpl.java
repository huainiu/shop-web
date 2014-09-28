package com.b5m.service.snapshot.impl;

import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.b5m.base.common.utils.WebTools;
import com.b5m.service.snapshot.SnapshotService;

/**
 * description 
 * 快照保存类
 * @Company b5m
 * @author echo
 * @since 2014年1月7日
 */
@Service("snapshotService")
public class SnapshotServiceImpl implements SnapshotService{
	
	@Resource(name = "searchThreadPool")
	private ThreadPoolExecutor searchThreadPool;
	
	@Resource(name = "properties")
	private Properties properties;
	
	@Override
	public void saveSnapshot(final String docId, final String collection){
		searchThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				String uploadUrl = properties.getProperty("snapshot.upload.url");
				uploadUrl = uploadUrl + "?docId=" + docId + "&collection=" + collection;
				WebTools.executeGetMethod(uploadUrl);
			}
		});
	}
	
}
