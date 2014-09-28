package com.b5m.service.www.utils;

public class CacheUtils{
    
    /**
     * 返回Comment 的memcache key
     * @param table
     * @param shop
     * @param type
     * @return
     */
    public static String getCommentListKey(Long supplierId, Integer type){
        return getKey("www1_Comment", String.valueOf(supplierId), String.valueOf(type));
    }
    
    /**
     * 返回Comment 的memcache key
     * @param table
     * @param shop
     * @param type
     * @return
     */
    public static String getAllCommentListKey(Long supplierId){
        return getKey("www1_Comment", String.valueOf(supplierId), "All");
    }
    
    /**
     * 返回Comment 的memcache key ：Comment_id <br/>
     * 该key主要用来保存 以Comment_id为key Comment为值
     * @param id
     * @return
     */
    public static String getCommentKey(Long id){
        return getKey("www1_Comment", String.valueOf(id));
    }
    
    /**
     * 返回Impress 的memcache key ：Impress_id <br/>
     * 该key主要用来保存 以Impress_id为key Impress为值
     * @param id
     * @return
     */
    public static String getImpressKey(Long id){
        return getKey("www1_Impress", String.valueOf(id));
    }
    
    /**
     * 返回Impress 的memcache key ：Impress_id <br/>
     * 该key主要用来保存 以Impress_id为key Impress为值
     * @param id
     * @return
     */
    public static String getAllImpressListKey(Long supplierId){
        return getKey("www1_Impress", String.valueOf(supplierId), "All");
    }
    
//    public static String get
    
    public static String getKey(String ...strings){
        StringBuilder sb = new StringBuilder();
        int length = strings.length;
        for(int i = 0; i < length; i++){
            sb.append(strings[i]);
            if(i < length - 1){
                sb.append("_");
            }
        }
        return sb.toString();
    }
    
}
