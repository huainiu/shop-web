package com.b5m.service.daigou.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.Constants;
import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.StringTools;
import com.b5m.base.common.utils.WebTools;
import com.b5m.bean.dto.DaigouCart;
import com.b5m.bean.dto.OntimePriceBean;
import com.b5m.bean.dto.SkuRequest;
import com.b5m.client.daigoucart.DaigouCartClient;
import com.b5m.client.daigoucart.DaigouCartLogUtils;
import com.b5m.service.daigou.CenterService;
import com.b5m.service.daigou.DaigouLogUtils;
import com.b5m.service.ontimeprice.OntimeService;
import com.b5m.service.ontimeprice.SkuBean;
import com.b5m.sf1.Sf1Query;
import com.b5m.sf1.dto.req.CondSearchBean;
import com.b5m.sf1.dto.req.SF1SearchBean;
import com.b5m.sf1.helper.MonitorUtils;
import com.b5m.sf1.helper.Sf1Helper;
import com.b5m.sf1.helper.Sf1Helper.HttpClientFactory;
import com.mchange.v1.util.StringTokenizerUtils;

@Service
public class CenterServiceImpl implements CenterService {
	@Autowired
	private DaigouCartClient daigouCartClient;

	@Autowired
	private OntimeService ontimeService;

	@Autowired
	private Sf1Query sf1Query;
	
	@Autowired
	@Qualifier("sysConfig")
	private Properties sysConfig;
	
	public BigDecimal subPriceByRule(String url) {
		if (StringUtils.isEmpty(url))
			return new BigDecimal(0.5);
		return new BigDecimal("1");
	}

	public String getPrice(JSONObject product) {
		OntimePriceBean ontimePriceBean = new OntimePriceBean();
		ontimePriceBean.addDocId(product.getString("DOCID"), product.getString("Url"));
		JSONArray priceArray = ontimeService.queryPrices(ontimePriceBean);
		int length = priceArray.size();
		if (length >= 1) {
			try {
				JSONObject jsonObject = priceArray.getJSONObject(0);
				return jsonObject.getString("price");
			} catch (Exception e) {
				DaigouLogUtils.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public String setCategory(HttpServletRequest request, String category) {
		if (StringUtils.isEmpty(category))
			return category;
		String[] strs = StringTokenizerUtils.tokenizeToArray(category, ">");
		if (strs.length <= 0)
			return category;
		request.setAttribute("category", strs[strs.length - 1]);
		return strs[strs.length - 1];
	}

	public DaigouCart createCart(HttpServletRequest request, String key, String docId, String priceAvg, String goodsSpec, Integer count, String channel, String ref, String bk, String url, String origin, String col, String skuId, Integer act, String newPrice, String title, String imgPath) {
		JSONObject product = getCacheProduct(docId, ref, url, bk, col);
		DaigouCart cart = new DaigouCart();
		if (product == null) {
			product = new JSONObject();
			product.put("DOCID", StringTools.MD5(url));
			product.put("Source", "帮我淘");
			product.put("Url", url);
			product.put("detailUrl", url);
			product.put("OriginalPicture", "");
			product.put("Price", newPrice);
			try {
				product.put("Picture", java.net.URLDecoder.decode(imgPath,"UTF-8"));
				product.put("Title", java.net.URLDecoder.decode(title,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		setSource(ref, product);
		setOrigin(ref, url, origin, cart);
		cart.setChannel(channel);
		cart.setStatus(1);
		cart.setType(bk);
		if (!StringUtils.isEmpty(product.getString("is_postage"))) {
			cart.setIs_postage(Integer.valueOf(product.getString("is_postage")));
		}

		String picture = product.getString("Picture");
		cart.setImage(picture);
		String originalPicture = product.getString("OriginalPicture");
		if (!StringUtils.isEmpty(originalPicture)) {
			String[] pics = StringUtils.split(originalPicture, ",");
			if (pics.length > 0) {
				cart.setGoodsOriginImgUrl(pics[0]);
				if (StringUtils.isEmpty(picture)) {
					cart.setImage(pics[0]);
				}
			}
		}

		cart.setOutsideLink(product.getString("Url"));
		if (!StringUtils.isEmpty(goodsSpec)) {
			Object object = ontimeService.querySkuAll(new SkuRequest(docId,product.getString("Url")), docId, request);
			if (object != null) {
				JSONArray skus = (JSONArray) object;
				for (int i = 0; i < skus.size(); i++) {
					JSONObject sku = (JSONObject) skus.get(i);
					char c = sku.getString("properties_name").charAt(sku.getString("properties_name").length() - 1);
					if (c == ';') {
						sku.put("properties_name", sku.getString("properties_name").substring(0, sku.getString("properties_name").length() - 1));
					}
					if (goodsSpec.equals(sku.getString("properties_name"))) {
						cart.setSkuId(sku.getString("sku_id"));
					}
				}
			}
		}

		cart.setSource(product.getString("Source"));

		if (StringUtils.isEmpty(docId)) {
			docId = StringTools.MD5(url);
		}
		cart.setProductId(docId);
		if (StringUtils.isEmpty(key)) {
			key = docId;
		}

		BigDecimal price = product.getBigDecimal("Price");
		if (act == null || act != 1) {
			JSONObject ontimePrice = ontimePrice(docId, product.getString("Url"));
			if (ontimePrice != null) {
				BigDecimal _price = ontimePrice.getBigDecimal("price");
				if (_price != null && _price.compareTo(BigDecimal.ZERO) > 0) {
					price = _price;
				}
			}

			String _price = getSkuPrice(key, goodsSpec, url, request);
			if (_price != null) {
				price = new BigDecimal(_price);
				cart.setPrice(_price);
			}

			if (!StringUtils.isEmpty(newPrice)) {
				BigDecimal thirdPrice = new BigDecimal(newPrice);
				if(price!=null){
					if (thirdPrice.compareTo(price) > 0) {
						price = thirdPrice;
					}
				}else{
					price = thirdPrice;
					cart.setPrice(String.valueOf(price));
				}
				
			}

			if (price.compareTo(new BigDecimal("5")) > 0) {
				price = price.subtract(subPriceByRule(url));
			}
		}

		if (priceAvg == null || new BigDecimal(priceAvg).compareTo(price) < 0) {
			priceAvg = price.toString();
		}

		cart.setPrice(price.toString());
		cart.setTitle(product.getString("Title"));
		// 设置详情url
		setDetailUrl(cart, product, url, docId, ref);

		cart.setPriceAvg(priceAvg);
		cart.setGoodsSpec(goodsSpec);
		if (count != null)
			cart.setCount(count);
		// 设置用户信息
		Cookie[] cks = request.getCookies();
		cart.setCookieid(WebTools.getCooKieValue("cookieId", cks));
		String login = WebTools.getCooKieValue("login", cks);
		if (login != null && "true".equals(login)) {
			cart.setUid(WebTools.getCooKieValue("token", cks));
		}
		if (isQG(ref)) {
			cart.setProductId(product.getString("ID"));
		}
		return cart;
	}

	private String getSkuPrice(String key, String goodsSpec, String url, HttpServletRequest req) {
		if (StringUtils.isEmpty(goodsSpec))
			return null;
		String _price = ontimeService.getPriceFromSku(key, goodsSpec);
		if (StringUtils.isEmpty(_price)) {
			String docId = StringTools.MD5(url);
			SkuBean result = ontimeService.querySkuProp(new SkuRequest(docId, url), docId, req);
			if (result != null) {
				return result.getSku().getString(goodsSpec);
			}
		}
		return _price;
	}

	private void setDetailUrl(DaigouCart cart, JSONObject product, String url, String docId, String ref) {
		String detailUrl = getDetailUrl(product, url, docId, ref);
		cart.setUrl(detailUrl);
		if (!StringUtils.isEmpty(url)) {
			cart.setProductId(StringTools.MD5(url));
		}
	}

	public String getDetailUrl(JSONObject product, String url, String docId, String ref) {
		if (isTuan(ref)) {
			return "http://tuan.b5m.com/view_" + docId + ".html";
		} else if (isQG(ref)) {
			return "http://www.b5m.com/qiang.html";
		} else if (isTicket(ref)) {
			return "http://ticket.b5m.com/view_" + docId + ".html";
		}
		String detailurl = product.getString("detailurl");
		if (!StringUtils.isEmpty(detailurl)) {
			return detailurl;
		}
		if (!StringUtils.isEmpty(url)) {
			return url;
		}
		return "http://s.b5m.com/item/" + docId + ".html";
	}

	private JSONObject ontimePrice(String docId, String url) {// product.getString("Url")
		OntimePriceBean ontimePriceBean = new OntimePriceBean();
		ontimePriceBean.addDocId(docId, url);
		ontimePriceBean.setFlag(1);
		try {
			JSONArray jsonArray = ontimeService.queryPrices(ontimePriceBean);
			JSONObject ontimePrice = jsonArray.getJSONObject(0);
			return ontimePrice;
		} catch (Exception e) {
			return null;
		}
	}

	/* 获取商品信息
	 * 值得买、淘特价、抢购、麦包包   ==> PHP接口
	 * 团购、 兑换商品: DocId==>SF1接口       Url==>实时SKU接口获取商品信息
	 */
	public JSONObject getCacheProduct(String docId, String ref, String url, String bk, String col) {
		// String key = StringTools.MD5(new
		// StringBuilder().append(docId).append("_").append(ref).append("_").append(url).append("_").append(bk).append("_").append(col).append("_").toString());
		// Object object = MemCachedUtils.getCache(key);
		// if (object != null)
		// return (JSONObject) object;
		JSONObject jsonObject = getProduct(docId, ref, url, bk, col);
		if (jsonObject != null)
			jsonObject.put("detailUrl", getDetailUrl(jsonObject, url, docId, ref));
//		MemCachedUtils.setCache(key, jsonObject);
		DaigouCartLogUtils.info("Product: "+jsonObject);
		return jsonObject;
	}

	private JSONObject getProduct(String docId, String ref, String url, String bk, String col) {
		if (StringUtils.isEmpty(docId) && StringUtils.isEmpty(url))
			return null;
		// 重试三边
		for (int i = 0; i < 3; i++) {
			if ("2".equals(bk)) {
				String bkStr = WebTools.executeGetMethod("http://cms-api.b5m.com/other/index.php?a=get_product_info_by_docid&docid=" + docId);
				DaigouCartLogUtils.info("wap PHP返回数据 : docid = " + docId + " ---  result : " + bkStr);
				JSONObject bkJson = JSONObject.parseObject(bkStr);
				return bkJson.getJSONObject("data").getJSONObject("info");
			}
			if (!StringUtils.isEmpty(url))
				return getOntimeDetail(url);
			if (isTejia(ref))
				return getTejia(docId);
			if (isZDM(ref))
				return getZDM(docId);
			if (isQG(ref))
				return getQG(docId);
			if (isMaiBaoBao(ref))
				return getMaiBaoBao(docId);
			
			SF1SearchBean sf1rBean = new SF1SearchBean();
			sf1rBean.setCollection(getCollection(ref, col));
			sf1rBean.setCondLst(CollectionTools.newList(new CondSearchBean("DOCID", "=", docId)));
			try {
				JSONObject result = sf1Query.doGetRtnJson(sf1rBean);
				DaigouCartLogUtils.info("sf1Query返回数据 : docid = " + docId + " ---  result : " + result.toJSONString());
				JSONArray jsonArray = result.getJSONArray("resources");
				JSONObject source = jsonArray.getJSONObject(0);
				return source;
			} catch (Exception e) {
				DaigouCartLogUtils.error("query product detail error, docId[" + docId + "], ref[" + ref + "], url[" + url + "]", e);
			}
		}
		return null;
	}

	private JSONObject getOntimeDetail(String url) {
		boolean isHas = false;
		String[] siteArr = { "taobao.com", "tmall.com", "tmall.hk", "amazon.cn", "amazon.com", "amazon.co.jp", "bookuu.com", "dangdang.com", "jd.com", "360buy.com", "taoshu.com", "winxuan.com", "gome.com.cn", "vjia.com", "suning.com", "yixun.com", "51buy.com", "yhd.com", "yihaodian.com", "m18.com", "mogujie.com", "meilishuo.com", "lifevc.com" };

		for (int i = 0; i < siteArr.length; i++) {
			if (url.indexOf(siteArr[i]) > 0) {
				isHas = true;
			}
		}
		if (!isHas) {
			try {
				throw new Exception();
			} catch (Exception e) {
				DaigouCartLogUtils.error("Url is not in grab site");
				return null;
			}
		}

		JSONObject resource = ontimeService.queryDetail(url);
		DaigouCartLogUtils.info("RealTime SKU Data: "+resource);
		if (resource == null) {
			return null;
		}
		if (StringUtils.isEmpty(resource.getString("Title"))) {
			SF1SearchBean sf1rBean = new SF1SearchBean();
			sf1rBean.setCondLst(CollectionTools.newList(new CondSearchBean("DOCID", "=", StringTools.MD5(url))));
			try {
				JSONObject result = sf1Query.doGetRtnJson(sf1rBean);
				JSONArray jsonArray = result.getJSONArray("resources");
				JSONObject source = jsonArray.getJSONObject(0);
				resource.put("Title", source.getString("Title"));
			} catch (Exception e) {
				
			}
		}
		resource.put("Picture", StringUtils.split(resource.getString("OriginalPicture"), ",")[0]);
		resource.put("OriginalPicture", "");
		resource.put("DOCID", StringTools.MD5(url));
		return resource;
	}

	private JSONObject getTejia(String docId) {
		String result = WebTools.executeGetMethod("http://cms-api.b5m.com/tejia/index.php?a=get_product_info_by_docid&docid=" + docId);
		DaigouCartLogUtils.info("特价PHP返回数据 : docid = " + docId + " ---  result : " + result);
		if (StringUtils.isEmpty(result))
			return null;
		JSONObject resultJson = JSONObject.parseObject(result);
		if ("103".equals(resultJson.getString("code"))) {
			return null;
		}
		JSONObject data = resultJson.getJSONObject("data");
		return data;
	}

	private JSONObject getZDM(String docId) {
		String result = WebTools.executeGetMethod("http://zdm.b5m.com/daigou.html?docId=" + docId);
		DaigouCartLogUtils.info("值得买PHP返回数据 : docid = " + docId + " ---  result : " + result);
		if (StringUtils.isEmpty(result))
			return null;
		JSONObject resultJson = JSONObject.parseObject(result);
		JSONObject data = resultJson.getJSONObject("data");
		return data;
	}

	private JSONObject getQG(String docId) {
		String result = WebTools.executeGetMethod("http://cms-api.b5m.com/huodong/index.php?a=get_yiyuanduihuan_product&id=" + docId);
		DaigouCartLogUtils.info("抢购PHP返回数据 : docid = " + docId + " ---  result : " + result);
		if (StringUtils.isEmpty(result))
			return null;
		JSONObject resultJson = JSONObject.parseObject(result);
		int code = resultJson.getInteger("code");
		if (code!=0)
			return null;
		JSONObject data = resultJson.getJSONObject("data");
		if (data == null)
			return null;
		JSONObject resource = new JSONObject();
		resource.put("DOCID", docId);
		resource.put("ID", data.getString("id"));
		resource.put("detailurl", data.getString("origin_link"));
		resource.put("Picture", data.getString("image_url"));
		resource.put("Title", data.getString("title"));
		resource.put("Price", data.getString("exchange_price"));
		resource.put("Original_price", data.getString("original_price"));
		resource.put("Url", data.getString("origin_link"));
		return resource;
	}
	
	private JSONObject getMaiBaoBao(String docId){
		String result = WebTools.executeGetMethod("http://hd.b5m.com/mbaobao/item.php?docId=" + docId);
		DaigouCartLogUtils.info("MaiBaoBao From PHP Data: "+result);
		if (StringUtils.isEmpty(result))
			return null;
		JSONObject resultJson = JSONObject.parseObject(result);
		if (resultJson == null)
			return null;
		int code = resultJson.getInteger("code");
		if (code!=0)
			return null;
		JSONObject data = resultJson.getJSONObject("data");
		if (data == null)
			return null;
		JSONObject resource = new JSONObject();
		resource.put("DOCID", docId);
		resource.put("ID", data.getString("DOCID"));
		resource.put("Picture", data.getString("Picture"));
		resource.put("Title", data.getString("Title"));
		resource.put("Price", data.getString("Price"));
		resource.put("Original_price", data.getString("OriginalPrice"));
		resource.put("Url", data.getString("Url"));
		resource.put("detailurl", data.getString("Url"));
		resource.put("is_postage", data.getString("is_postage"));
		return resource;		
	}

	private String getCollection(String ref, String col) {
		if (isTuan(ref))
			return "tuanm";
		if (isTicket(ref))
			return "ticketp";
		if (!StringUtils.isEmpty(col)) {
			return col;
		}
		return Sf1Helper.CONTEXT_COLLECTION;
	}

	private boolean isTuan(String ref) {
		return "01".equals(ref);
	}

	private boolean isTejia(String ref) {
		return "02".equals(ref);
	}

	private boolean isZDM(String ref) {
		return "03".equals(ref);
	}

	// 抢购商品
	public boolean isQG(String ref) {
		return "04".equals(ref);
	}

	// 票务
	private boolean isTicket(String ref) {
		return "05".equals(ref);
	}

	// 麦包包
	private boolean isMaiBaoBao(String ref) {
		return "06".equals(ref);
	}

	private void setSource(String ref, JSONObject source) {
		if (isTuan(ref)) {
			source.put("Source", "团购");
		}
		if (isTejia(ref)) {
			source.put("Source", "特价");
		}
		if (isZDM(ref)) {
			source.put("Source", "值得买");
		}
		if (isQG(ref)) {
			source.put("Source", "抢购");
		}
		if (isTicket(ref)) {
			source.put("Source", "票务");
		}
		if (isMaiBaoBao(ref)) {
			source.put("Source", "麦包包");
		}
	}

	private void setOrigin(String ref, String url, String origin, DaigouCart cart) {
		if (!StringUtils.isEmpty(origin)) {
			cart.setOrigin(origin);
			return;
		}
		if (isTuan(ref)) {
			cart.setOrigin("团购");
			return;
		}
		if (isTejia(ref)) {
			cart.setOrigin("特价");
			return;
		}
		if (isZDM(ref)) {
			cart.setOrigin("值得买");
			return;
		}
		if (isQG(ref)) {
			cart.setOrigin("抢购");
			return;
		}
		if (isTicket(ref)) {
			cart.setOrigin("票务");
			return;
		}
		if (isMaiBaoBao(ref)) {
			cart.setOrigin("麦包包");
			return;
		}
		if (!StringUtils.isEmpty(url)) {
			cart.setOrigin("帮5淘");
			return;
		}
	}

	public String getshopDetail(String url) {
		if (!StringUtils.isEmpty(url) && (url.indexOf("taobao.com") > 0 || url.indexOf("tmall.com") > 0)) {
			url = regexUrl(url);
		}
		for (int i = 0; i < 3; i++) {
			JSONObject resultObj = queryShopDetail(url);
			if (resultObj != null){
				String data = resultObj.getString("data");
				if(data.indexOf("data-lazyload=")>0){
					data = data.replaceAll("data-lazyload=", "src=");
				}			
				if(data.indexOf("<textarea style=\"height:0px;border-width:0px;\">")>=0){
					data = data.replaceAll("<textarea style=\"height:0px;border-width:0px;\">", "").replaceAll("</textarea>", "");
					data = data.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
				}
				data = fiterHtmlTag(data,"a");
				data = fiterHtmlTag(data,"map");
				return data;
			}
		}
		return null;
	}
	
	public JSONObject queryShopDetail(String url) {
		String reqUrl = "http://"+sysConfig.getProperty("center.shop.detail")+"/rt-spider/getGoodsDetail";
		String paramStr = "{\"timeout\":\"5000\",\"url\":\""+url+"\",\"keys\":[\"Title\"]}";		
		long timeStart = System.currentTimeMillis();
		JSONObject resultObj = null;
		try {
			String str = doInternalSearch(reqUrl, paramStr);
			resultObj = JSONObject.parseObject(str);
			if(resultObj.getInteger("code")!=200){
				return null;
			}
		} catch (Exception e) {
			DaigouCartLogUtils.error("Shop Detail Fail: "+e);
		} finally {
			DaigouCartLogUtils.info("Shop Detail Cost:"+(System.currentTimeMillis() - timeStart)+"ms, Result["+resultObj+"], Request[Url:"+reqUrl+",Params:"+paramStr+"]");
		}
		return resultObj;
	}
	
	public String regexUrl(String url) {
		String idRegexs = "(\\?id=\\d+|&id=\\d+)";
		String pid = matcherId(url, idRegexs);
		if(pid.indexOf("id=")>=0){
			pid = pid.split("id=")[1];
		}else{
			return url;
		}
		String[] siteUrl = url.split("item.htm");
		url = siteUrl[0] + "item.htm?id=" + pid;
		return url;
	}
	

	/**
	 * 基本功能：过滤指定标签
	 * @param str
	 * @param tag 指定标签
	 * @return String
	 */
	public static String fiterHtmlTag(String str, String tag) {
		String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";
		Pattern pattern = Pattern.compile(regxp);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString().replaceAll("</" + tag + ">", "");
	}
	
	
	protected static String doInternalSearch(String url, String jsonString) {
		PostMethod method = null;
		// 创建请求方法
		try {
			MonitorUtils.latencyCollecterBegin();
			HttpClient httpClient = HttpClientFactory.getHttpClient();
			method = createPostMethod(url, jsonString.toString());
			// 执行请求
			int statusCode = httpClient.executeMethod(method);
			httpClient.getParams().setSoTimeout(5000);
			httpClient.getParams().setConnectionManagerTimeout(5000);
			if (statusCode == HttpStatus.SC_OK) {
				// 获得返回串
				String resultMsg = method.getResponseBodyAsString().trim();
				return resultMsg;
			} else {
				throw new RuntimeException("Connect fail, url:" + url + " code:" + statusCode);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (HttpException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				MonitorUtils.latencyCollecterCommit();
			} catch (Exception e) {
			}
			if (null != method) {
				method.releaseConnection();
			}
		}

	}

	protected static PostMethod createPostMethod(String URL, String content) throws UnsupportedEncodingException {
		PostMethod method = new PostMethod(URL);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Constants.DEFAULT_ENCODING);
		method.getParams().setParameter(HttpMethodParams.BUFFER_WARN_TRIGGER_LIMIT, 5242880);// 警报限制设置为5M
		method.setRequestEntity(new StringRequestEntity(content, "application/json", Constants.DEFAULT_ENCODING));
		method.setRequestHeader("Connection", "Keep-Alive");
		return method;
	}
	
	public static String matcherId(String goodsUrl, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(goodsUrl);
		while (m.find()) {
			goodsUrl = m.group(1);
		}
		return goodsUrl;
	}

	/* 中间页页面价格逻辑
	 * 1、首先显示传递过来的价格(帮我淘)。
	 * 2、如没有传递价格，显示实时价格(调用实时接口)。
	 * 3、如实时价格获取失败，显示商品信息价格(值得买、淘特价、抢购、麦包包、团购、 兑换商品)。
	 */
	@Override
	public void setShopInfo(HttpServletRequest request, JSONObject product,
			String newPrice, String priceAvg, String ref, String docId,
			String url, Integer maxNum) {
		String price = null;
		if (!StringUtils.isEmpty(newPrice)) {
			price = newPrice;
		} else {
			price = getPrice(product);
			DaigouCartLogUtils.info("RealTime result price " + price);
		}

		if (price != null && !"-1.0".equals(price)) {
			product.put("Price", price);
		} else {
			product.put("Price", product.getBigDecimal("Price"));
		}
		BigDecimal _price = product.getBigDecimal("Price");
		BigDecimal _priceAvg = null;
		try {
			_priceAvg = new BigDecimal(priceAvg);
		} catch (Exception e) {
			_priceAvg = _price;
			if (("04".equals(ref) ||"06".equals(ref)) && !"94".equals(docId)) {
				_priceAvg = product.getBigDecimal("Original_price");
			}
		}
		boolean needRemain = false;
		if (_price.compareTo(new BigDecimal("5")) > 0) {
			needRemain = true;
			_price = _price.subtract(subPriceByRule(url));
		}
		BigDecimal remainPrice = _priceAvg.subtract(_price);
		// 商品单价显示-0.5元，传给购物车的单价也需要相应-0.5元；
		// 节省的钱的公式：若 数量*（均价-单价）≤0，则省0.5元；若 数量*（均价-单价）＞0，则省 数量*（均价-单价） 元；
		if (remainPrice.compareTo(BigDecimal.ZERO) <= 0) {
			// 单价小于5元者执行下列逻辑
			// 若 数量*（均价-单价）≤0，则省0元；若 数量*（均价-单价）＞0，则省 数量*（均价-单价） 元；
			if (needRemain) {
				remainPrice = subPriceByRule(url);
			} else {
				remainPrice = new BigDecimal("0");
			}
		}
		
		if (!"94".equals(docId) || !"04".equals(ref)) {
			product.put("Price", _price.add(subPriceByRule(url)).setScale(2));
		}
		product.put("afterRemainPrice", _price.setScale(2));
		
		// 抢购每人只能买一件商品
		if (isQG(ref)) {
			if (maxNum == null || maxNum <= 0) {
				maxNum = 1;
			}
		} else {
			maxNum = null;
		}
		
		String memKey = product.getString("DOCID");
		if (StringUtils.isEmpty(memKey) || memKey.length() != 32) {
			memKey = StringTools.MD5(product.getString("Url"));
		}
		
		request.setAttribute("priceAvg", _priceAvg.setScale(2));
		request.setAttribute("remainPrice", remainPrice.setScale(2));
		request.setAttribute("bangzhuan", _price.setScale(0, RoundingMode.UP));
		request.setAttribute("chongzhika", _price.divide(new BigDecimal("1000"), 1, RoundingMode.UP));
		request.setAttribute("maxNum", maxNum);
		request.setAttribute("memKey", memKey);
		request.setAttribute("product", product);
		request.setAttribute("ref", ref);
		request.setAttribute("paramurl", url);
		request.setAttribute("b5tPrice", newPrice);
		request.setAttribute("subPrice", subPriceByRule(url));
		setCategory(request, product.getString("Category"));
	}
	
}
