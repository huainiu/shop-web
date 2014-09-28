package com.b5m.common.env;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * description 
 * 全局变量
 * 所有的可配置的都会放在这里
 * @Company b5m
 * @author echo
 * @since 2013-6-13
 */
public class GlobalInfo{
    private static Properties sysProp = new Properties();
    public static Long DEFAULT_COMMENT_IMPRESS_MEMCACHE_EXPIRED = 0L;
    public static Integer DEFAULT_WORD_PAGE_SIZE = 10;
    public static Integer DEFAULT_USER_PAGE_SIZE = 10;
    public static Integer DEFAULT_ROLE_PAGE_SIZE = 10;
    public static Integer DEFAULT_RESOURCE_PAGE_SIZE = 10;
    public static Integer DEFAULT_COMMENT_PAGE_SIZE = 10;
    public static Integer DEFAULT_IMPRESS_PAGE_SIZE = 14;
    public static Integer DEFAULT_BACK_MANAGE_LIST_PAGE_SIZE = 5;
    public static Integer DEFAULT_COMMENT_LIMIT_TIME = 60;
    public static Integer DEFAULT_IMPRESS_LIMIT_TIME = 60;
    public static Integer DEFAULT_IMPRESS_CLICK_LIMIT_TIME = 60;
    public static Integer DEFAULT_OFFDETAILMONTHLY_PAGE_SIZE = 15;
    public static Integer DEFAULT_PRICECOMPAREMONTHLY_PAGE_SIZE = 12;
    public static Integer DEFAULT_SHOP_LIST_PAGE = 12;
    public static Integer DEFAULT_PHP_ARTICLE_SIZE = 6;
    
    public static String KEY_COMMENT_CONSUMER_THREAD_SIZE = "COMMENT_CONSUMER_THREAD_SIZE";
    public static String KEY_COMMENT_BLOCKING_QUEUE_SIZE = "COMMENT_BLOCKING_QUEUE_SIZE";
    public static String KEY_IMPRESSION_CONSUMER_THREAD_SIZE = "COMMENT_CONSUMER_THREAD_SIZE";
    public static String KEY_IMPRESSION_BLOCKING_QUEUE_SIZE = "COMMENT_BLOCKING_QUEUE_SIZE";
    public static String KEY_SEARCHRECOMMEND_CONSUMER_THREAD_SIZE = "SEARCHRECOMMEND_CONSUMER_THREAD_SIZE";
    public static String KEY_SEARCHRECOMMEND_BLOCKING_QUEUE_SIZE = "SEARCHRECOMMEND_BLOCKING_QUEUE_SIZE";
    public static String KEY_COMMENT_FILED_SAVE_MEM = "COMMENT_FILED_SAVE_MEM";
    public static String KEY_IMPRESS_FILED_SAVE_MEM = "IMPRESS_FILED_SAVE_MEM";
    public static String KEY_COMMENT_IMPRESS_MEMCACHE_EXPIRED = "COMMENT_IMPRESS_MEMCACHE_EXPIRED";
    public static String KEY_COMMENT_PAGE_SIZE = "COMMENT_PAGE_SIZE";
    public static String KEY_IMPRESS_PAGE_SIZE = "IMPRESS_PAGE_SIZE";
    public static String KEY_BACK_MANAGE_LIST_PAGE_SIZE = "BACK_MANAGE_LIST_PAGE_SIZE";
    public static String KEY_COMMENT_LIMIT_TIME = "COMMENT_LIMIT_TIME";
    public static String KEY_IMPRESS_LIMIT_TIME = "IMPRESS_LIMIT_TIME";
    public static String KEY_IMPRESS_CLICK_LIMIT_TIME = "IMPRESS_CLICK_LIMIT_TIME";
    public static String KEY_SHOP_LIST_PAGE = "SHOP_LIST_PAGE";
    public static String KEY_PHP_ARTICLE_SIZE = "PHP_ARTICLE_SIZE";
    
    
    
    public static final int OPER_UN_REVIEW = -1;	//未审核
    public static final int OPER_UN_PASS = 0;		//未通过
    public static final int OPER_PASS = 1;			//通过
    
    public static final int VIEW_HISTORY_NUM = 8;	//根据浏览记录推荐的数量
    public static final int HOT_KEYWORD_TIME_RATE = -7;	//距离现在几天的热词
    public static final int HOT_KEYWORD_NUM = 1;
    
    public static void load(InputStream inputStream) throws IOException{
        sysProp.load(inputStream);
    }
    
    public static void load(String path) throws FileNotFoundException, IOException{
        InputStream inputStream = new FileInputStream(path);
        load(inputStream);
    }
    
    public static Object get(String key){
        return sysProp.get(key);
    }
    
    public static Integer getCommentPageSize(){
    	Integer pageSize = getInt(KEY_COMMENT_PAGE_SIZE);
    	if(pageSize == null) pageSize = DEFAULT_COMMENT_PAGE_SIZE;
    	return pageSize;
    }
    
    public static Integer getBackManageListPageSize(){
    	Integer pageSize = getInt(KEY_BACK_MANAGE_LIST_PAGE_SIZE);
    	if(pageSize == null) pageSize = DEFAULT_BACK_MANAGE_LIST_PAGE_SIZE;
    	return pageSize;
    }
    
    public static Integer getImpressPageSize(){
    	Integer pageSize = getInt(KEY_IMPRESS_PAGE_SIZE);
    	if(pageSize == null) pageSize = DEFAULT_IMPRESS_PAGE_SIZE;
    	return pageSize;
    }
    
    public static Long getCommImpreExpired(){
    	Long expired = GlobalInfo.getLong(KEY_COMMENT_IMPRESS_MEMCACHE_EXPIRED);
        if(expired == null) expired = DEFAULT_COMMENT_IMPRESS_MEMCACHE_EXPIRED;
        return expired;
    }
    
    public static Integer getCommentLimitTime(){
    	Integer commentLitmitTime = GlobalInfo.getInt(KEY_COMMENT_LIMIT_TIME);
    	if(commentLitmitTime == null) commentLitmitTime = DEFAULT_COMMENT_LIMIT_TIME;
        return commentLitmitTime;
    }
    
    public static Integer getImpressLimitTime(){
    	Integer impressLitmitTime = GlobalInfo.getInt(KEY_IMPRESS_LIMIT_TIME);
    	if(impressLitmitTime == null) impressLitmitTime = DEFAULT_IMPRESS_LIMIT_TIME;
        return impressLitmitTime;
    }
    
    public static Integer getImpressClickLimitTime(){
    	Integer impressClickLitmitTime = GlobalInfo.getInt(KEY_IMPRESS_CLICK_LIMIT_TIME);
    	if(impressClickLitmitTime == null) impressClickLitmitTime = DEFAULT_IMPRESS_CLICK_LIMIT_TIME;
        return impressClickLitmitTime;
    }
    
    public static Integer getShopListPage(){
    	Integer shopListPage = GlobalInfo.getInt(KEY_SHOP_LIST_PAGE);
    	if(shopListPage == null) shopListPage = DEFAULT_SHOP_LIST_PAGE;
    	return shopListPage;
    }
    
    public static Integer getPhpArticleSize(){
    	Integer articleSize = GlobalInfo.getInt(KEY_PHP_ARTICLE_SIZE);
    	if(articleSize == null) articleSize = DEFAULT_PHP_ARTICLE_SIZE;
    	return articleSize;
    }
    
    public static String getStr(String key){
        Object o = sysProp.get(key);
        if(null == o) return null;
        return o.toString();
    }
    
    public static Integer getInt(String key){
        Object o = sysProp.get(key);
        if(null == o) return null;
        return Integer.parseInt(o.toString());
    }
    
    public static Long getLong(String key){
    	Object o = sysProp.get(key);
        if(null == o) return null;
        return Long.parseLong(o.toString());
    }
    
}
