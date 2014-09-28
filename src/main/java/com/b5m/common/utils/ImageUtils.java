package com.b5m.common.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.b5m.base.common.utils.StringTools;

/**
 * 图片大小url转换工具
 * 
 * @author yuxiaolong
 * 
 */
public class ImageUtils {

	public static String getImgBySize(String url, int weight, int hight) {
		if (StringUtils.isBlank(url)) {
			return url;
		}

		String name = RandomStringUtils.randomAlphanumeric(12);

		if (url.startsWith("http://img.b5m.com/image") && (hight > 0 || weight > 0)) {
			if (!url.endsWith("/")) {
				return url = url + "/" + weight + "x" + hight + "/100/" + name + ".jpg";
			} else {
				return url = url + weight + "x" + hight + "/100/" + name + ".jpg";
			}
		} else {
			return url;
		}
	}

	public static String isImageFile(MultipartFile fileData) {
		String fileType = fileData.getOriginalFilename().substring(fileData.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
		String[] fileTypes = { "jpeg", "jpg", "png", "gif" };
		String type = "";
		for (String ft : fileTypes) {
			if (ft.equals(fileType)) {
				type = ft;
				break;
			}
		}
		return type;
	}

	/**
	 * 图像切割（改）
	 * 
	 * @param in
	 *            源图像流
	 * @param x
	 *            目标切片起点x坐标
	 * @param y
	 *            目标切片起点y坐标
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度
	 */
	public static BufferedImage abscut(InputStream in, int x, int y, int destWidth, int destHeight) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(in);
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度
			if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				// 改进的想法:是否可用多线程加快切割速度
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制缩小后的图
				g.dispose();

				return tag;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 压缩图片
	 * 
	 * @param src
	 *            图片文件
	 * @param w
	 *            目标宽
	 * @param h
	 *            目标高
	 * @param per
	 *            百分比
	 */
	public static BufferedImage smallerImage(Image src, int w, int h, float per) {
		int old_w = src.getWidth(null); // 得到源图宽
		int old_h = src.getHeight(null);
		int new_w = 0;
		int new_h = 0; // 得到源图长

		double w2 = (old_w * 1.00) / (w * 1.00);
		double h2 = (old_h * 1.00) / (h * 1.00);

		// 图片跟据长宽留白，成一个正方形图。
		BufferedImage oldpic;
		if (old_w > old_h) {
			oldpic = new BufferedImage(old_w, old_w, BufferedImage.TYPE_INT_RGB);
		} else {
			if (old_w < old_h) {
				oldpic = new BufferedImage(old_h, old_h, BufferedImage.TYPE_INT_RGB);
			} else {
				oldpic = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
			}
		}
		Graphics2D g = oldpic.createGraphics();
		g.setColor(Color.white);
		if (old_w > old_h) {
			g.fillRect(0, 0, old_w, old_w);
			g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, Color.white, null);
		} else {
			if (old_w < old_h) {
				g.fillRect(0, 0, old_h, old_h);
				g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, Color.white, null);
			} else {
				g.drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
			}
		}
		g.dispose();
		src = oldpic;
		// 图片调整为方形结束
		if (old_w > w)
			new_w = (int) Math.round(old_w / w2);
		else
			new_w = old_w;
		if (old_h > h)
			new_h = (int) Math.round(old_h / h2);// 计算新图长宽
		else
			new_h = old_h;
		BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
		// tag.getGraphics().drawImage(src,0,0,new_w,new_h,null); //绘制缩小后的图
		tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);

		return tag;
	}
	
	public static void replaceImgUrl(List<Map<String, String>> result){
    	String imgUrlStr = ConfigPropUtils.getValue("img.cdn.link");
    	for(Map<String, String> produce : result){
    		String pic = produce.get("Picture");
   		    produce.put("Picture", newPic(pic, imgUrlStr));
    	}
    }
	
	public static void replaceImgUrl(Map<String, String> produce){
    	String imgUrlStr = ConfigPropUtils.getValue("img.cdn.link");
		String pic = produce.get("Picture");
		produce.put("Picture", newPic(pic, imgUrlStr));
    }
	
    public static String newPic(String pic, String imgUrlStr){//替换链接地址为cdn的
    	String[] imgUrls = StringTools.split(imgUrlStr, ",");
    	if(StringTools.isEmpty(pic)) return pic;
    	int hashcode = pic.hashCode();
    	int index = hashcode % imgUrls.length;
    	if(index < 0) index = - index;
    	return StringTools.replace(pic, "img.b5m.com", imgUrls[index]);
    }
	
}
