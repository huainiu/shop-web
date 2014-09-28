package com.b5m.client.daigoucart;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSONObject;
import com.b5m.bean.dto.DaigouCart;

public interface DaigouCartClient {
	
	JSONObject addDaigouCart(DaigouCart daigouCart) throws HttpException, IOException;
	
}
