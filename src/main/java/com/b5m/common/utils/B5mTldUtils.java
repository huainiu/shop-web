package com.b5m.common.utils;

import java.util.Arrays;
import java.util.List;

import com.b5m.common.env.Constants;
/**
 * description 
 * 页面上所有跟标签有关的 都放在这里
 * @Company b5m
 * @author echo
 * @since 2013-6-13
 */
public class B5mTldUtils{
    public static String left(String s, int max) {
        char[] cs = s.toCharArray();
        int count = 0;
        int last = cs.length;
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] > 255)
                count += 2;
            else
                count++;
            if (count > max) {
                last = i + 1;
                break;
            }
        }
        if (count <= max) // string is short or just the size!
            return s;
        // string is too long:
        max -= 3;
        for (int i = last - 1; i >= 0; i--) {
            if (cs[i] > 255)
                count -= 2;
            else
                count--;
            if (count <= max) {
                return s.substring(0, i) + "...";
            }
        }
        return "...";
    }
    
    /**
     * split字符串
     * @param text 被截取的字符串
     * @param regex 截取字符串的字符
     * @return
     */
    public static List<String> spilt(String text, String regex){
        return Arrays.asList(text.split(regex));
    }

    public static void main(String[] args) {
        System.out.println(left("中文zh e@NYume~@1nxxxYYY中午啊", 15));
    }
    
    /**
     * 默认sourceName编码
     * description
     *
     * @param sourceName
     * @param url
     * @return
     * @author echo weng
     * @since 2013-5-27
     * @mail echo.weng@b5m.com
     */
    public static String gotoCps(String sourceName, String url){
        return gotoCps(sourceName, url, true);
    }
    
    public static String gotoCps(String sourceName, String url, boolean needEncoding){
        /*if(needEncoding) sourceName = UrlEncodingUtils.urlEncoding(sourceName);
        String serverUrl = Constants.CPS_SERVER_ADDRESS;
        if(!serverUrl.endsWith("/")){
            serverUrl = serverUrl + "/";
        }
        if(url.indexOf("?") > 0){
            String[] subUrls = url.split("\\?");
            url = subUrls[0] + "?" + UrlEncodingUtils.urlEncoding(subUrls[1]);
        }
        return serverUrl + "suiProductSender.htm?method=goToCps&splName=" + sourceName + "&url=" + url + "";*/
    	return url;
    }
    
    public static String gotoCpsNoEncoding(String sourceName, String url){
        return gotoCps(sourceName, url, false);
    }
    
    public static String dealWithTaoSourceUrl(String url){
        if(url.indexOf("&unid=") <= 0) return url;
        String[] suburls = url.split("&unid=");
        StringBuilder sb = new StringBuilder(suburls[0]);
        sb.append("&unid=www");
        if(suburls[1].indexOf("&") >= 0){
            sb.append(suburls[1].substring(suburls[1].indexOf("&"))); 
        }
        return sb.toString();
    }
    
    /*public static void main(String[] args){
        System.out.println(gotoCps("淘宝", "http://www.ocj.com.cn/shop/detailshop.jsp?seq_cate_num=250&item_Code=133737"));
    }*/
}
