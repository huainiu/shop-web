package com.b5m.common.utils;

import java.io.File;
import java.util.Properties;

public class FileUtil {

	public static void createFile(String path) {
		File file = new File(path);
		if(!file.exists()) file.mkdirs();
	}

	public static String getSearchDetailPath(){
		Properties properties = SpringContextUtils.getBean("properties", Properties.class);
		String snapPath = properties.getProperty("snap.path");
		return snapPath;
	}
	
	public static String getConfigValue(String key){
		Properties properties = SpringContextUtils.getBean("properties", Properties.class);
		return properties.getProperty(key);
	}
}
