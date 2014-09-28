package com.b5m.service.snapshot;

import java.util.Properties;

import com.b5m.common.utils.SpringContextUtils;
/**
 * description 
 *
 * @Company b5m
 * @author echo
 * @since 2014年1月8日
 */
public class SnapshotTools {
	
	public static void saveSnapshot(String docId, String collection){
		SnapshotService snapshotService = SpringContextUtils.getBean("snapshotService", SnapshotService.class);
		snapshotService.saveSnapshot(docId, collection);
	}
	
	public static String getSnapShotDetailUrl(){
		Properties properties = SpringContextUtils.getBean("properties", Properties.class);
		String viewUrl = properties.getProperty("snapshot.view.url");
		return viewUrl;
	}
}
