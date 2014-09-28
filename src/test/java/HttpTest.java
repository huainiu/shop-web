import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import com.b5m.base.common.utils.DateTools;
import com.b5m.base.common.utils.WebTools;
import com.b5m.common.utils.PWCode;

public class HttpTest {

	public void post() throws Exception {
		for (int i = 0; i < 10; i++) {
			String url = "http://ucenter.b5m.com/user/message/data/addNotice.htm";
			Map<String, String> map = new HashMap<String, String>();
			String pName = "aaa";
			String uid = "7e1e75fca78740bcb79c924ceaf69aa6";
			String content = "您好！您兑换的【" + pName + "】现已通过审核，物品将于3个工作日内发货，请保持手机开机或QQ在线，以便我们的工作人员能够及时联系到您。欢迎使用兑换中心，查看更多兑换请到http://ucenter.b5m.com/dh";
			map.put("title", pName + "兑换审核通过系统通知");
			map.put("content", content);
			map.put("logo", "http://img.b5m.com/image/T1jzJsB5Jg1RCvBVdK");
			map.put("fromStr", "兑换中心");
			map.put("fromUrl", "http://ucenter.b5m.com/dh/");
			map.put("type", "0");
			map.put("status", "0");
			map.put("recId", uid);
			map.put("token", verification());
			String s = WebTools.executePostMethod(url, map);
			System.out.println(s);
		}

	}

	private String verification() {
		String s1 = PWCode.getMD5Code_64("message");
		String code = PWCode.getMD5Code_16("f" + s1 + "s");
		return code;
	}

	public void main() throws HttpException, IOException, InterruptedException {
		for (int i = 0; i < 15; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						HttpClient client = new HttpClient();
						PostMethod method = new PostMethod("http://www.b5m.com/lottery.php?t=" + new Date().getTime());
						method.addRequestHeader("Cookie", "cookieId=402357ba56ea4463b9a26249534eeff4;sessionId=f29b8da7de364efea292b983d8385250; token=d4fb4115bb254bd5a0182e2df8b989d8; nickname=%E6%99%9A%E7%A7%8B; email=jiangshangtao%40126.com; login=true; avatar=http%3A%2F%2Fcdn.b5m.com%2Fupload%2Fweb%2Fucenter%2Favatars%2F201406%2F130%2Fd4fb4115bb254bd5a0182e2df8b989d8.jpeg; showname=%25E6%2599%259A%25E7%25A7%258B;");
						method.addRequestHeader("Connection", "keep-alive");
						method.addRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
						method.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
						method.addRequestHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");

						method.addParameter("ajax_mod", "award");
						method.addParameter("lottery_type", "day");
						try {
							int status = client.executeMethod(method);
							System.out.println(status + "success--------------------------------------------" + method.getResponseBodyAsString() + ":" + DateTools.formate(new Date(), "yyyy-MM-dd HH:mm:ss"));
							method.releaseConnection();
						} catch (HttpException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		Thread.sleep(100000000000000000l);
	}
}
