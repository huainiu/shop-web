package com.b5m.app;

import java.io.InputStream;
import java.net.URL;

import org.junit.Test;

import com.izenesoft.sns.util.ImageServer;
import com.izenesoft.sns.util.ImageServer.ImageBean;

public class ImageUploadServiceApp {
	@Test
	public void testUpload() throws Exception{
		URL url = new URL("http://img.b5m.com/image/T1GThvB5Dj1RCvBVdK");
		InputStream in = null;
		try{
			ImageBean imageBean = ImageServer.save(url.openConnection().getInputStream(), "10.10.106.3", 18821);
			System.out.println("http://img.b5m.com/image/" + imageBean.getImageName());
		}catch(Exception e){
			
		}
	}
}
