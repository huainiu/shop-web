package com.b5m.client.imgserver;

import java.net.URL;

import org.apache.commons.lang.StringUtils;

import com.b5m.common.env.GlobalInfo;
import com.izenesoft.sns.util.ImageServer;
import com.izenesoft.sns.util.ImageServer.ImageBean;
import com.mchange.v1.util.StringTokenizerUtils;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-11-7
 * @email wuming@b5m.com
 */
public class ImageServerUtils {
    
	/**
	 * @description
	 * 将图片保存到图片服务器上 
	 * @param imagePath 图片的路径
	 * @return
	 * @author echo
	 * @since 2013-11-7
	 * @email echo.weng@b5m.com
	 */
	public static String saveImgAndRtnPath(String imagePath){
		String[] ips = rtnIps();
		for(String ip : ips){
			try{
				return saveImgAndRtnPath(imagePath, ip, GlobalInfo.getInt("img.server.port"));
			}catch(Exception e){
			}
		}
		return null;
	}
	
	protected static String saveImgAndRtnPath(String imagePath, String ip, int port) throws Exception{
		URL url = new URL(imagePath);
		ImageBean imageBean = ImageServer.save(url.openConnection().getInputStream(), ip, port);
		return "http://img.b5m.com/image/" + imageBean.getImageName();
	}
	
	/**
	 * @description
	 * 根据图片的路径 imagePath 进行删除图片
	 * @param imagePath
	 * @author echo
	 * @since 2013-11-7
	 * @email echo.weng@b5m.com
	 */
	public static void deleteImg(String imagePath){
		String[] ips = rtnIps();
		for(String ip : ips){
			try {
				ImageServer.delete(getImgName(imagePath), ip, GlobalInfo.getInt("img.server.port"));
			} catch (Exception e) {
			}
		}
	}
	
	protected static String[] rtnIps(){
		String ipstr = GlobalInfo.getStr("img.server.ip");
		String[] ips = StringTokenizerUtils.tokenizeToArray(ipstr, ",");
		return ips;
	}
	
	protected static String getImgName(String imagePath){
		if(StringUtils.isEmpty(imagePath)) return "";
		return imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.length());
	}
	
}
