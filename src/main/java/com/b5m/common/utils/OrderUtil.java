
package com.b5m.common.utils;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;


/* *
 *类名：OrderUtil
 *功能：自定义订单类
 *详细：工具类，可以用作获取订单编号等
 */
public class OrderUtil {
	
    /** 年月日时分秒(无下划线) yyyyMMddHHmmssSSS */
    public static final String dtLong = "yyyyMMddHHmmssSSS";
    
    /**
     * 默认 订单前缀
     */
    public static final String DEFAUTL_ORDER_PREFIX = "WWW_";
    
    /**
     * userId 长度
     */
    public static final int USER_ID_LENGTH = 32 ;
    
    /**
     * 默认获取 订单号
     * @return 订单号
     */
    public static String getOrderNum(String userId){
    	return getOrderNum(DEFAUTL_ORDER_PREFIX, userId);
    }
    
    /**
     * 设置 订单前缀，获取 订单号
     * @param orderPrefix 订单前缀
     * @return 订单号
     */
    public static String getOrderNumByPre(String orderPrefix,String userId){
    	return getOrderNum(orderPrefix,userId);
    }
   
    /**
     * 获取订单号 
     * @param orderPrefix 前缀
     * @return 格式: 前缀+时间戳（精确到毫秒）+用户id 前3位+随机数（3位）
     */
	private static String getOrderNum(String orderPrefix,String userId){
		
		String threeRandomUserId = getThreeRandomUserId(userId);
		if(null == threeRandomUserId)
			return null;
		
		StringBuffer strOrder = new StringBuffer();
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtLong);
		if(StringUtils.isBlank(orderPrefix)){
			strOrder.append(DEFAUTL_ORDER_PREFIX);
		}else{
			strOrder.append(orderPrefix);
		}
		strOrder.append(df.format(date)).append(threeRandomUserId).append(getThree());
		return strOrder.toString();
	}
	
	/**
	 * 获取 随机 用户id 前3位
	 * @param userId
	 * @return
	 */
	public static String getThreeRandomUserId(String userId){
		if(StringUtils.isBlank(userId))
			return null;
		if(userId.length()!=USER_ID_LENGTH)
			return null;
		return userId.substring(0, 3);
	}
	
	/**
	 * 产生随机的三位数
	 * @return
	 */
	public static String getThree(){
		return getRandomNum(3);
	}
	
	/**
	 * 产生随机的6位数
	 * @return
	 */
	public static String getSix(){
		return getRandomNum(6);
	}
	
	private static String getRandomNum(int num){
		return RandomStringUtils.randomNumeric(num);
	}
	
	public static void main(String[] args){
		System.out.println(getOrderNum("89d884ab8ccf40a29dc2a51ee7f5c666"));
	}
	
}
