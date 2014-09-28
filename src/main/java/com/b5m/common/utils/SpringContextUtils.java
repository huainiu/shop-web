package com.b5m.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import com.b5m.common.env.GlobalInfo;
/**
 * @Company B5M.com
 * @description
 * spring context utils
 * @author echo
 * @since 2013-6-17
 * @email echo.weng@b5m.com
 */
public class SpringContextUtils implements ApplicationContextAware, InitializingBean{
    public static ApplicationContext applicationcontext;

    public static Object getBean(String name){
        return applicationcontext.getBean(name);
    }
    
    public static <T> T getBean(String name, Class<T> type){
        return applicationcontext.getBean(name, type);
    }
    
    public static <T> List<T> getBeans(Class<T> type){
    	Map<String, T> beanMap = applicationcontext.getBeansOfType(type);
    	return new ArrayList<T>(beanMap.values());
    }
    
    public static void publishEvent(ApplicationEvent event){
        applicationcontext.publishEvent(event);
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)throws BeansException{
        SpringContextUtils.applicationcontext = applicationContext;
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		File file = null;
        URL url = this.getClass().getClassLoader().getResource("sysConfig.properties");
        if(url != null){
            String path = url.getPath();
            file = new File(path);
        }
	    if(file.exists()){
	        InputStream inputStream = new FileInputStream(file);
	        GlobalInfo.load(inputStream);
	    }		
	}

}
