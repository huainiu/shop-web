/**
 * Constants.java
 *
 * 功  能：[demo]常量类
 * 类  名：Constants
 *
 *   ver     变更日       公司      作者     变更内容
 * ──────────────────────────────────────────────
 *   V1.00  '10-11-26  iZENEsoft  金录       初版
 *
 * Copyright (c) 2009 iZENEsoft Business Software corporation All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.b5m.common.env;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * 本类是用于常量定义。<BR>
 * 
 * @author 杨闿
 * @version Ver 1.00 10-11-26 初版
 */
public class Sf1Constants {

	/**
	 * 网址截取长度
	 */
	public static final int URL_SUB_LENGTH = 36;

	/**
	 * 团购APP字段
	 */
	public static final String FIELD_GROUPON_TIME_BEGIN = "TimeBegin";
	public static final String FIELD_GROUPON_TIME_END = "TimeEnd";
	public static final String FIELD_GROUPON_CATEGORY = "Category";
	public static final String FIELD_GROUPON_CITY = "City";
	public static final String FIELD_GROUPON_PRICE = "Price";
	public static final String FIELD_GROUPON_PRICE_ORIGN= "PriceOrign";
	public static final String FIELD_GROUPON_PRICE_SAVE= "SavePrice";

	/**
	 * 业务对应字段[业务字段]
	 */
	public static final String FIELD_APP_DOCID = "DocId";
	public static final String FIELD_APP_URL = "Url";
	public static final String FIELD_APP_SITE = "Site";
	public static final String FIELD_APP_TITLE = "Title";
	public static final String FIELD_APP_PRICE = "price";
	public static final String FIELD_APP_CONTENT = "Content";
	public static final String FIELD_APP_POINT = "Point";
	public static final String FIELD_APP_IMGURL = "ImageUrl";
	public static final String FIELD_APP_COMMENT = "Comment";
	public static final String FIELD_APP_DATE = "Date";
	public static final String FIELD_APP_URLDATA = "UrlDate";
	public static final String FIELD_APP_SHORT_URL = "ShortUrl";
	public static final String FIELD_APP_COLLECTION_NAME = "collectionName";
	public static final String FIELD_APP_SIMILAR = "similar";
	public static final String FIELD_APP_DUPL = "duplicate";
	public static final String FIELD_APP_CATEGORY = "Category";
	public static final String FIELD_APP_ATTR = "Attribute";
	public static final String FIELD_APP_SOURCE = "Source";
	public static final String FIELD_APP_SOURCE_IMG = "Source_img";
	public static final String FIELD_APP_PICTURE = "Picture";
	public static final String FIELD_APP_PICTURE_MULTI = "Picture_multi"; 
	public static final String FIELD_APP_ITEM_COUNT = "itemCount";
	public static final String FIELD_APP_UUID = "uuid";
	public static final String FIELD_APP_PRICE_TREND = "priceTrend"; // 保存价格趋势是否存在以及类型的字段
	public static final String FIELD_APP_PICTURE_SOURCE = "OriginalPicture";
	public static final String FIELD_APP_SCORE = "Score";
	public static final String FIELD_APP_BRAND = "Brand";
	public static final String FIELD_APP_XIHUAN = "Xihuan";
	public static final String FIELD_APP_FENXIANG = "Fenxiang";
	public static final String FIELD_APP_GUANZHU = "Guanzhu";
	public static final String FIELD_APP_BUY_COUNT = "BuyCount";
	public static final String FIELD_APP_TARGET_CATEGORY = "Category";
	public static final String FIELD_APP_PRODDOCID = "ProdDocid";
	public static final String FIELD_APP_IMG = "Img";
	public static final String FIELD_APP_PRICESTART = "PriceStart";
	public static final String FIELD_APP_PRICEEND = "PriceEnd";
	public static final String FIELD_APP_USERPREVIEW = "UserPreview";
	public static final String FIELD_APP_LIKECOUNT = "LikeCount";
	public static final String FIELD_APP_UP_DOCID = "DOCID";
	public static final String FIELD_APP_ISTOP = "IsTop";
	public static final String FIELD_APP_TOPTIME = "TopTime";
	public static final String FIELD_APP_ISESSENCE = "IsEssence";
	public static final String FIELD_APP_ISHIDE = "IsHide";
	public static final String FIELD_APP_ISRECOMMAND = "IsRecommand";
	public static final String FIELD_APP_RECOMMANDTIME = "RecommendTime";
	public static final String FIELD_APP_GMEMCOUNT = "GMemCount";
	public static final String FIELD_APP_GID = "GID";
	public static final String FIELD_APP_ISFREEDELIVERY = "isFreeDelivery";
	public static final String FIELD_APP_ISCOD = "isCOD";
	public static final String FIELD_APP_ISGENUINE = "isGenuine";
	
	
	public static final String FIELD_APP_SHARETYPE = "ShareType";
	public static final String FIELD_APP_VISITCOUNT = "VisitCount";
	public static final String FIELD_APP_LABELNAMES = "LabelNames";
	public static final String FIELD_APP_ORDERSEQ = "OrderSeq";

	/**
	 * 二手字段对应
	 */
	public static final String FIELD_APP_SECOND_TIME_BEGIN = "TimeBegin";
	public static final String FIELD_APP_SECOND_SHOW_TIME = "showTime";
	public static final String FIELD_APP_SECOND_PRICE = "Price";
	public static final String FIELD_APP_SECOND_PLAY_ADDRESS = "PlayAddress";
	public static final String FIELD_APP_SECOND_PLAY_DATE = "PlayDate";
	public static final String FIELD_APP_SECOND_PLAY_CITY = "City";

	/**
	 * 票据对应业务字段
	 */
	public static final String FIELD_TICKET_APP_ADDRESS = "Address";
	public static final String FIELD_TICKET_APP_START_TIME = "StartTime";
	public static final String FIELD_TICKET_APP_END_TIME = "EndTime";
	public static final String FIELD_TICKET_APP_SOURCE_SERVICE = "SourceService";
	public static final String FIELD_TICKET_APP_TIME = "Time";
	public static final String FIELD_TICKET_APP_PRICE = "Price";
	public static final String FIELD_TICKET_APP_PRICES = "Prices";
	public static final String FIELD_TICKET_APP_PRICES_RANGE = "PriceRange";
	public static final String FIELD_TICKET_APP_PICTURE_SEAT = "PictureSeat";
	public static final String FIELD_TICKET_APP_LOWEST_PRICE = "LowestTicketPrice";
	
	/**
	 * 逛街对应业务字段
	 */
	public static final String FIELD_GUANG_APP_IMG = "Img";
	public static final String FIELD_GUANG_APP_CREATE_TIME = "CreateTime";
	public static final String FIELD_GUANG_APP_SHARE_UUID = "ShareUuid";
	public static final String FIELD_GUANG_APP_SHARE_DOCID = "ShareDocid";
	public static final String FIELD_GUANG_APP_NAME = "Name";
	public static final String FIELD_GUANG_APP_CONTENT_TEXT = "contentText"; 
	public static final String FIELD_GUANG_APP_APP_ID = "appId";
	public static final String FIELD_GUANG_APP_GMEM_COUNT = "GMemCount";
	public static final String FIELD_GUANG_APP_UPDATE_TIME = "UpdateTime";
	
	/**
	 * 评测对应业务字段
	 */
	public static final String FIELD_REVIEW_APP_Summary = "Summary";
	
	/**
	 * 专辑对应业务字段
	 */
	public static final String FIELD_ZHUAN_APP_ITEM_COUNT = "ItemCount";
	
	/**
     * 完整分组在页面中使的名字
     */
    public static final String WHOLE_CATEGORY = "whole_";

	/**
	 * 检索文档服务器时所使用的关键字名
	 */

	public static final String STR_DOC_SPLIT = "&&";
	public static final String STR_DOC_SPLIT2 = "--";
	public static final String GROUP_SPILT = ">";
	
	/**
	 * 用户登录标识
	 */
	public static final String USER_ID = "userId";
	public static final String USER_API_ID = "userApiId";
	public static final String USER_NAME = "userName";

	/**
	 * sessionid
	 */
	public static final String SESSION_ID = "sessionId";

	/**
	 * 保持登录的cookie的id
	 */
	public static final String KEEP_LOGIN = "KEEP_LOGIN";

	//
	public static final String LOGIN_TOKEN = "loginToken";

	public static final String REQUEST_URL = "requestUrl";

	// 价格趋势
	public static final String PRICE_TREND_DOWN = "down"; // 上升
	public static final String PRICE_TREND_UP = "up"; // 下降

	// 浏览其实字符串,后面要接数据集名
	public static final String VIEW_HISTORY = "yourViewHistory_";

	// 浏览其实字符串,后面要接数据集名
	public static final String HISTORY_DETAIL= "log-detail-";
	
	public static final String YOUR_HISTORY = "yourHistory";

	/**
	 * 列表类型
	 */
	public static final String IMGE_LYOUT = "Image";
	public static final String LIST_LYOUT = "List";

	/**
	 * US NumberFormat
	 */
	public static final NumberFormat US_NUMBER_FORMAT = NumberFormat
			.getInstance(Locale.US);

	// 点击量排序
	public static final String SORT_CTR = "_ctr";

	// 关联度排序
	public static final String SORT_RANK = "_rank";

	// 侧小折扣页面商家分类List中的Map用到的字段
	public static final String MAP_KEY_ID = "id"; // id
	public static final String MAP_KEY_NAME = "name"; // name
	public static final String MAP_KEY_CHECKED = "checked"; // 是否被选

	public static final String SPL_PROMO_ORDER_NEW = "new"; // 最新
	public static final String SPL_PROMO_ORDER_HOT = "hot"; // 人气

	public static final String OFFSET_DIRECT = "DIRECT";// 直接可以抵用
	public static final String OFFSET_LIMIT = "LIMIT"; // 有了足够的金额才可以抵用

	public static final String AJAX_SUCCESS = "success";
	public static final String AJAX_ERROR = "error";
	public static final String AJAX_LOGIN_TIME_OUT = "login_time_out";
	public static final String AJAX_ALREADY_HAVE = "already_have";

	public static final String SPLDB = "SplDB";	//商家数据集名称
	
	public static final int AUTOFILL_LIMIT = 10;

	public static final String SEARCH_TYPE_SEARCH = "search";
	

    public static final String FILE_UPLOAD_LOCAL_PATH = "/opt/cdn-image/upload/"; //本地保存的地址
    public static final String FILE_UPLOAD_SERVER_PATH = "http://cdn.bang5mai.com/upload/"; //远程服务器访问图片地址

	//商家的国家
	public static final String SUPPLISER_COUNTRY_KOREA = "韩国";
	public static final String SUPPLISER_COUNTRY_CHINA = "中国";	
	
	public static final String IS_FREE_DELIVERY = "1";
	public static final String IS_COD = "1";
	public static final String IS_GENUINE = "1";
	
}
