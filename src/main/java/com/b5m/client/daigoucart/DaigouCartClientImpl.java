package com.b5m.client.daigoucart;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSONObject;
import com.b5m.bean.dto.DaigouCart;
import com.b5m.client.AbstractClient;
import com.b5m.client.CallBack;
import com.b5m.client.HttpClientFactory;

public class DaigouCartClientImpl extends AbstractClient implements DaigouCartClient{

	public DaigouCartClientImpl(String url, HttpClientFactory httpClientFactory) {
		super(url, httpClientFactory);
	}
	
	public JSONObject addDaigouCart(DaigouCart daigouCart) throws HttpException, IOException{
		
		final String params = generatorDaigouCartRequest(daigouCart);
//		final String request = "pro_info=" + URLEncoder.encode(params, "UTF-8");
//		final String request = params;
//		final String request = req.replace("\"", "%22");
		return sendPostLogin(params, new CallBack<JSONObject>() {

			@Override
			public JSONObject call(Object... args) {
				if(args.length > 1){
					int status = (Integer) args[1];
					if(status != 200){
						DaigouCartLogUtils.error("save daigou cart error, error status[" + status + "], url[" + url + "], requst[" + params + "]");
					}
				}
				DaigouCartLogUtils.info("save daigou cart success for result[" + args[0] + "], url[" + url + "],  requst[" + params + "]");
				return JSONObject.parseObject(args[0].toString());
			}
			
		});
//		return sendGet(request, );
	}
	
	private String generatorDaigouCartRequest(DaigouCart daigouCart) throws UnsupportedEncodingException{
		JSONObject request = new JSONObject();
		request.put("uid", daigouCart.getUid());
		request.put("cookieid", daigouCart.getCookieid());
		request.put("title", daigouCart.getTitle());
		request.put("product_id", daigouCart.getProductId());
		request.put("url", daigouCart.getUrl());
		request.put("image", daigouCart.getImage());
		request.put("price", daigouCart.getPrice());
		request.put("price_avg", daigouCart.getPriceAvg());
		request.put("source", daigouCart.getSource());
		request.put("goodspec", daigouCart.getGoodsSpec());
		request.put("count", daigouCart.getCount());
		request.put("channel", daigouCart.getChannel());
		request.put("type", daigouCart.getType());
		request.put("direct_buy", daigouCart.getDirect_buy());
		request.put("origin", daigouCart.getOrigin());
		request.put("is_postage", daigouCart.getIs_postage());
		request.put("goodsOriginImgUrl", daigouCart.getGoodsOriginImgUrl());
		request.put("skuId", daigouCart.getSkuId());		
		request.put("outsideLink", daigouCart.getOutsideLink());
		return request.toJSONString();
	}

}
