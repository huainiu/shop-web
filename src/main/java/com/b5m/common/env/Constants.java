package com.b5m.common.env;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * description 
 * 系统中不会更改的常量都放在这里
 * @Company b5m
 * @author echo
 * @since 2013-6-13
 */
public class Constants{
    /**
     * 请求参数中的多值的分隔符
     */
    public static final String REQUEST_PARAM_DELIMER = ",";
    
    public static final int KEYWORDS_LIMIT = 10;
    
    public static final String  CHAR_SET = "UTF-8";
    
    public static final String ISO8859 = "ISO8859-1";
    
    public static final String IMAGE_SHOW_TYPE = "image";
    
    public static final String CPS_SERVER_ADDRESS = "http://old.b5m.com/";
    
    //评论的分页大小
    public static final int COMMENT_PAGE_SIZE = 10;
    
    
    /**------------------web----------------**/
    /** 请求或发送数据时的默认编码,UTF-8 */
    public static String DEFAULT_ENCODING = "UTF-8";

    /** HTML类型文档的ContentType,text/html;charset=$default_encoding */
    public final static String HTML_CONTENT_TYPE = "text/html;charset="  + DEFAULT_ENCODING;

    public final static String XML_CONTENT_TYPE = "text/xml;charset="  + DEFAULT_ENCODING;

    /** application/x-json */
    public final static String JSON_CONTENT_TYPE = "application/x-json";

    /** 数据类型 */
    public final static int TYPE_STRING = Types.VARCHAR;
    public final static int TYPE_INTEGER = Types.INTEGER;
    public final static int TYPE_LONG = Types.BIGINT;
    public final static int TYPE_BOOLEAN = Types.BOOLEAN;
    public final static int TYPE_DOUBLE = Types.DOUBLE;
    public final static int TYPE_FLOAT = Types.FLOAT;
    public final static int TYPE_DATE = Types.DATE;
    public final static int TYPE_ARRAY = Types.ARRAY;

    /** 数据类型映射表 */
    public final static Map<String, Integer> TYPE_MAPPING = new HashMap<String, Integer>();
    static {
        TYPE_MAPPING.put("string", TYPE_STRING);
        TYPE_MAPPING.put("integer", TYPE_INTEGER);
        TYPE_MAPPING.put("long", TYPE_LONG);
        TYPE_MAPPING.put("boolean", TYPE_BOOLEAN);
        TYPE_MAPPING.put("double", TYPE_DOUBLE);
        TYPE_MAPPING.put("float", TYPE_FLOAT);
        TYPE_MAPPING.put("date", TYPE_DATE);
        TYPE_MAPPING.put("array", TYPE_ARRAY);
    }

    public final static String LOG_NAME = "plugin.b5m.log";
    public final static String MSG_FORMAT_START = "[";
    public final static String MSG_FORMAT_END = "]";
    public final static String MSG_FORMAT_SPLIT = " ";
}
