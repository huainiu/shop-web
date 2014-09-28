package com.b5m.service.www.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.b5m.bean.entity.Comment;
import com.b5m.bean.entity.Impress;
import com.b5m.common.env.GlobalInfo;
import com.b5m.common.log.LogUtils;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-6-18
 * @email echo.weng@b5m.com
 */
public class JsonUtils{
    
    /**
     * 将comment转化成json格式
     * @param comment
     * @return
     */
    public static String comment2Json(Comment comment){
        return object2Json(comment, GlobalInfo.KEY_COMMENT_FILED_SAVE_MEM);
    }
    
    /**
     * 将comment转化成json格式
     * @param comment
     * @return
     */
    public static String object2Json(Object o, String syskey){
        Map<String, Object> map = new HashMap<String, Object>();
        String fields = GlobalInfo.getStr(syskey);
        String[] fieldList = fields.split(",");
        try{
            for (String field : fieldList){
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field, o.getClass());
                Method method = propertyDescriptor.getReadMethod();
                Object value = method.invoke(o);
                if(value != null) map.put(field, value);
            }
        }catch (IntrospectionException e){
            LogUtils.error(JsonUtils.class, e);
        }catch (IllegalArgumentException e){
            LogUtils.error(JsonUtils.class, e);
        }catch (IllegalAccessException e){
            LogUtils.error(JsonUtils.class, e);
        }catch (InvocationTargetException e){
            LogUtils.error(JsonUtils.class, e);
        }
        return JSON.toJSONString(map);
    }
    
    /**
     * 将列表id转化成json 
     * @param ids
     * @return
     */
    public static String idList2Json(List<Long> ids){
    	return JSON.toJSONString(ids);
    }
    
    /**
     * 将json转化成列表id
     * @param json
     * @return
     */
    public static List<Long> json2IdList(String json){
    	return JSON.parseArray(json, Long.class);
    }
    
    public static boolean isEmpty(String json){
    	if(StringUtils.isEmpty(json)) return true;
    	String _new = json.replaceAll("\\[|\\]|\\{|\\}", "");
    	if(StringUtils.isEmpty(_new)) return true;
    	return false;
    }
    
    /**
     * 将impress 转化成json
     * @param impress
     * @return
     */
    public static String impress2Json(Impress impress){
        return object2Json(impress, GlobalInfo.KEY_IMPRESS_FILED_SAVE_MEM);
    }
    
    public static Impress json2Impress(String json){
        return JSON.parseObject(json, Impress.class);
    }
    
    public static Comment json2Comment(String json){
        return JSON.parseObject(json, Comment.class);
    }
}
