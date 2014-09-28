<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="./common/meta.jsp"%>
<c:set var="res" value="${dataSet.goodsDetailInfoDto}"></c:set>
<c:set value="${dataSet.attributes }" var="attributes"></c:set>
<title>${_title_}</title>
<meta name="keywords" content="${_keywords_}" />
<meta name="description" content="${_description_}" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_result.css?t=${today}">
<link rel="stylesheet" type="text/css" href="http://y.b5mcdn.com/css/search/detail.css?t=${today}">
<link type="text/css" rel="stylesheet" href="http://y.b5mcdn.com/css/search/bdg.css?t=${today}" />
</head>
<body style="position: relative;">
	<%@ include file="./common/search.jsp"%>
	<!--nav s -->
	<div id="nav" class="wp">
		<div class="nav-menu">
			<c:if test="${dataSet.categoryValue != ''}">
				<span>所有分类</span>
				<c:set value="${fn:split(dataSet.categoryValue, '>')}" var="categoryList" />
				<c:set value="" var="cat" />
				<c:forEach items="${categoryList }" begin="0" end="${fn:length(categoryList)}" var="item" varStatus="status">
					<c:if test="${item != null && item != ''}">
						<c:if test="${status.index == 0}">
							<c:set value="${item}" var="cat" />
						</c:if>
						<c:if test="${status.index != 0}">
							<c:set value="${cat}&gt;${item}" var="cat" />
						</c:if>
						<span>&gt;</span>
						<a href="${basePath}search/s/${cat}___image________________.html">${item}</a>
					</c:if>
				</c:forEach>
				<c:if test="${res.Title != null && res.Title != '' }">
					<span>&gt;</span>
					<span class="prod-name">${res.Title}</span>
				</c:if>
			</c:if>
		</div>
	</div>
	<!--nav e -->
	<c:url value="${b5m:gotoCps(res.Source, res.Url)}" var="gotoUrl" />
	<c:if test="${res.Source == '淘宝网' ||  res.Source == '天猫' || res.Source == '卓越亚马逊' || res.Source == '京东商城' || res.Source == '当当网'}">
		<c:url value="${res.Url}" var="gotoUrl" />
	</c:if>
	<!--layout-wrap s-->
	<div class="wp" id="detail-info-head">
		<h2 class="goods-title">${res.Title}</h2>
		<div class="layout-c cf">
			<div class="layout-m l">
				<div class="layout-content">
					<div class="prod-info">
						<div class="prod-hd cf mb20">
							<div class="l">
								<span class="prod-origin">${res.Source}</span> <strong class="price-info"><i>¥</i><span>${res.Price}</span> <!-- 降价了提醒我 --></strong>
							</div>
							<div class="r">
								<!-- <a href="#" class="prod-youhui"></a> -->
								<c:if test="${res.Source != '淘宝网'}">
									<a href="${basePath}exchange/item.htm?docId=${res.DOCID}" class="prod-duihuan" target="_blank" data-attr="102002"></a>
								</c:if>
							</div>
							<!-- 优惠券 -->
							<!-- <div class="r" id="coupon"></div> -->
						</div>
						<div class="prod-bd mb20">
							<p class="cf">
								<span class="r">此产品目前处于：<span class="status-icon" id="price-status">平稳中</span></span> <span class="l his-price">历史价格</span>
							</p>
							<p class="align-right cl5555">
								与之前价格相比<span id="price-date">(2014年3月31日)</span> <span class="clff78" id="price-diff">0</span>
							</p>
							<div class="price-trend" id="topPriceHistroyDiv" source="${res.Source}" style="height: 192px; width: 420px;"></div>
							<p class="cl3a94">最近90天的价格历史（最低价）</p>
						</div>
						<div class="prod-ft">
							<p class="cf prod-comments mb20">
								<span class="r">月销量(<span class="clff78">${res.SalesAmount > 0 ? res.SalesAmount : 0}</span>) 评论（<span class="clff78" id="head-comment-count">0</span>）
								</span> <span class="l star-grade">该商品暂无评分</span>
							</p>
							<p class="btn-mods">
								<c:if test="${!dataSet.snap && isDaigou}">
									<a href="${gotoUrl}" class="direct-shopping clff78" target="_blank" data-attr="100806">直接去购买</a>
								</c:if>
								<c:if test="${!dataSet.snap}">
									<c:if test="${isDaigou}"><a href='${basePath}${dataSet.goodsDetailInfoDto.daigouSource.Url}' target="_blank" class="btn-mod btn-mod-3 r" id="goBuy" data-attr="100803"><span>帮我买</span></a></c:if>
									<c:if test="${!isDaigou}"><a href='${gotoUrl}' class="btn-mod btn-mod-2" lp="1" id="goBuy" target="_blank" data-attr="100806">直接去购买</a></c:if>
								</c:if>
								<a href="javascript:void(0)" class="btn-mod btn-mod-1" id="J_count_down">收藏以及降价提醒</a>
								<c:if test="${dataSet.snap}">
									<a href="javascript:void(0)" class="btn-mod btn-mod-2"><span>已下架</span></a>
								</c:if>
							</p>
						</div>
					</div>
				</div>
			</div>
			<div class="layout-l l">
				<div class="main-slider">
					<div class="main-slider-pic">
						<img src="${res.Picture}" alt="${res.Title}">
					</div>
					<div class="slider-bar cf mini-slider">
						<ul class="cf ">
							<c:forEach items="${fn:split(res.OriginalPicture, ',') }" var="item">
								<li><span><img src="${item }"></span></li>
							</c:forEach>
						</ul>
						<c:if test="${fn:length(fn:split(res.OriginalPicture, ',')) > 5}">
							<a href="javascript:void(0);" class="slider-trigger slider-left arrow-left-disable"></a>
							<a href="javascript:void(0);" class="slider-trigger slider-right"></a>
						</c:if>
					</div>
				</div>
				<div class="tab">
				<script type="text/javascript">
					var ucenterUrl = "${loginAndRegisterURL}";
					bShareOpt = {
						uuid : "4e7eeacf-bf73-4b9c-97a7-7ebe6aa2c3ed",
						url : window.location.href,
						summary : "${res.Title}",
						pic : "${res.Picture}",
						vUid : "${user_session_key_id}",
						product : "${fn:substringBefore(res.Source, ',')}",
						price : "${res.price}",
						brand : "",
						tag : "${category}",
						category : "${category}",
						template : "1"
					};
				</script>
				<div class="share-opt">
					<div class="l">
						<div class="bshare-custom icon-medium" style="float: right; margin-top: 10px; margin-right: -7px;">
							<div class="bsPromo bsPromo1"></div>
							<a title="分享到QQ空间" class="bshare-qzone" style="background: url('/images/share/share_01.jpg') no-repeat;" href="javascript:;"></a> <a title="分享到新浪微博" class="bshare-sinaminiblog" style="background: url('/images/share/share_02.jpg') no-repeat;" href="javascript:;"></a> <a title="分享到腾讯微博" class="bshare-qqmb" style="background: url('/images/share/share_03.jpg') no-repeat;" href="javascript:;"></a> <a title="分享到人人网" class="bshare-renren" style="background: url('/images/share/share_04.gif') no-repeat;" href="javascript:;"></a>
						</div>
						<span style="float: right; line-height: 44px; margin-right: 15px;">分享给朋友:</span>
						<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/button.js#style=-1&amp;ssc=false&amp;mdiv=-1&amp;type=15"></script>
						<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC2.js?t=${today}"></script>
					</div>
				</div>
			</div>
			</div>
			<div class="layout-r l" id="shop-recommend"></div>
		</div>
	</div>
	<div class="wp">
		<div class="detail-container cf">
			<div class="main-detal">
				<!--tab s-->
				<div class="tab">
					<div class="tab-hd" id="detail-tabs">
						<ul>
							<c:set var="num" value="0" />
							<c:if test="${productDetail != '' && productDetail != null}">
								<c:set var="num" value="1" />
								<li id="shop-detail" class="cur"><a href="${detailLink}" onclick="return false;">商品详情</a></li>
							</c:if>
							<li id="pinglun" class="${num == 0 ? 'cur': ''}">
								<a href="${commentLink}" onclick="return false;"><span id="comment-chars">评论</span><span class="comment-count"></span></a>
							</li>
							<c:if test="${fn:length(attributes) > 0}">
								<li id="canshu"><a href="${paramLink}" onclick="return false;">详情参数</a></li>
							</c:if>
						</ul>
					</div>
					<div class="tab-bd">
						<c:if test="${productDetail != '' && productDetail != null}">
							<div class="tab-box" style="display: block; text-align: center;">
								<div id="product-detail" style="text-align: left;">
									${productDetail}
								</div>
								<script type="text/javascript">
									//京东
									$("img[data-lazyload]").each(function() {
										var $this = $(this);
										$this.attr("src", $this.attr("data-lazyload"));
									});
									//淘宝
									$("img[data-ks-lazyload]").each(function() {
										var $this = $(this);
										$this.attr("src", $this.attr("data-ks-lazyload"));
									});
									//苏宁
									$("img[src2]").each(function() {
										var $this = $(this);
										$this.attr("src", $this.attr("src2"));
									});
									$("#product-detail a").each(function() {
										$(this).attr("href","javascript:void(0)");
									});
									//亚马逊
									$("img[data-original]").each(function() {
										var $this = $(this);
										$this.attr("src", $this.attr("data-original"));
										$this.removeAttr("height");
									});
									//国美
									$("img[gome-src]").each(function() {
										var $this = $(this);
										$this.attr("src", $this.attr("gome-src"));
									});
								</script>
							</div>
							<!-- 针对当当网的 进行特殊处理 -->
							<c:if test="${res.Source == '当当网'}">
								<script type="text/javascript">
									var $content = $("#content_all");
									if($content.length > 0){
										$content.html($content.siblings("textarea").text());
	
										var $catalog = $("#catalog_all");
										$catalog.html($catalog.siblings("textarea").text());
										$catalog.css("display", "block");
	
										var $detailall = $("#detail_all");
										$detailall.html($detailall.find("textarea").text());
										$detailall.find("a").each(function() {
											$(this).removeAttr("href").removeAttr("target");
										});
									}else{
										if($("#product-detail textarea").length > 0){
											$("#product-detail").html($("textarea").text());
										}
									}
								</script>
								<style> 
								.pro_content .section { margin-bottom: 10px; position: relative; overflow: hidden; }  
								.pro_content .key { padding-top: 10px; }  
								.pro_content .key li { text-indent: 22px; width: 33%; text-align: left; float: left; line-height: 24px; }  
								.product_book .pro_content .section .tit { font-size: 14px; }  
								.pro_content .section .tit,.pro_content .right_title1,.pro_content .left_title_1 { background-color: #f5f5f5; font-weight: bold; height: 31px; line-height: 31px; padding-left: 17px; color: #404040; }  
								.pro_content .section .descrip,.right_content { font-size: 14px; padding: 25px 29px 15px; }  
								.mall_goods_foursort_style { padding: 15px 0; }  
								.mall_goods_foursort_style_frame { float: left; line-height: 22px; overflow: hidden; color: #666; width: 33%; text-indent: 22px; }  
								.mall_goods_foursort_style_frame a { color: #666; } 
								</style>
							</c:if>
						</c:if>
						<!--评论 s-->
						<div class="tab-box ${num < 1 ? 'hide' : ''}">
							<div class="shop-comments" id="all-comment-view">
								<ul class="comment-type cf">
									<li id="all" attr-type="ALL" class="cur"><span>全部</span></li>
									<li id="good" attr-type="GOOD"><span>好评</span></li>
									<li id="normal" attr-type="NORMAL"><span>中评</span></li>
									<li id="bad" attr-type="BAD"><span>差评</span></li>
								</ul>
								<!-- 评论内容 -->
								<ul class="comment-list" id="comment-view"></ul>
							</div>
						</div>
						<!--评论 e-->
						<c:if test="${fn:length(attributes) > 0}">
							<div class="tab-box hide">
								<table class="details-parameters" width="100%">
									<thead>
										<tr>
											<td colspan="4">基本参数</td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${attributes}" var="attr" varStatus="status">
											<tr>
												<th width="50%" style="font-weight: normal;">${attr.key}</th>
												<td width="50%">${attr.value}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:if>
						<!--详情参数 e-->
						<!--价格趋势 s-->
						<div class="tab-box hide">
							<div style="display: none">
								<p>
									<strong>价格趋势</strong>(近3个月的价格趋势) <span class="gray"></span>
								</p>
								<div id="priceTrendDiv"></div>
							</div>
						</div>
						<!--价格趋势 e-->
					</div>
				</div>
			</div>
			<div class="side-detail">
				<c:if test="${shopInfo != null }">
					<div class="side-mod merchant-mod mb20">
	            		<h3 class="side-mod-title">商家信息</h3>
	            		<dl>
	            			<dt><a href="${shopInfo.url }" target="_blank">${shopInfo.name }</a></dt>
	            			<dd><span class="r">与同行业相比</span><strong class="pingfen">店铺评分</strong></dd>
	            			<c:if test="${shopInfo.map.des != null }">
	            				<dd><span>描述相符:</span><em class="score">${shopInfo.desAgreed }</em><span class="${shopInfo.map.des == '高于' ? 'status-up' : 'status-down' }">${shopInfo.map.des}</span><span class="${shopInfo.map.des == '高于' ? 'status-up-txt' : 'status-down-txt' }">${shopInfo.desHigherRate }</span></dd>
	            			</c:if>
	            			<c:if test="${shopInfo.map.serv != null }">
	            				<dd><span>服务相符:</span><em class="score">${shopInfo.servAttitude}</em><span class="${shopInfo.map.serv == '高于' ? 'status-up' : 'status-down' }">${shopInfo.map.serv}</span><span class="${shopInfo.map.serv == '高于' ? 'status-up-txt' : 'status-down-txt' }">${shopInfo.servScHigherScore }</span></dd>
	            			</c:if>
	            			<c:if test="${shopInfo.map.cons != null }">
	            				<dd><span>物流相符:</span><em class="score">${shopInfo.consSpeed }</em><span class="${shopInfo.map.cons == '高于' ? 'status-up' : 'status-down' }">${shopInfo.map.cons}</span><span class="${shopInfo.map.cons == '高于' ? 'status-up-txt' : 'status-down-txt' }">${shopInfo.consSpeedRate }</span></dd>
	            			</c:if>
	            		</dl>
	            	</div>
				</c:if>
				<c:if test="${relatedList != null }">
					<div class="side-mod relative-mod mb20">
						<h3 class="side-mod-title">相关分类</h3>
						<ul class="relative-list cf">
							<c:forEach items="${relatedList }" var="item">
								<li><a data-attr="1010" href="${basePath}${item.text}.html">${fn:length(item.text) > 6 ? item.text : fn:substring(item.text, 0, 6) }</a></li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
				<div class="side-mod relative-prod">
					<h3 class="side-mod-title">相关商品</h3>
					<ul class="relative-prod" id="recommand-shop-view">
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- 相关商品 s -->
	<div class="wp">
		<div class="tab">
			<script type="text/javascript">
				var cpro_id = "u1395431";
			</script>
			<script src="http://cpro.baidustatic.com/cpro/ui/c.js?t=${today}"></script>
		</div>
	</div>
	<div class="wp">
		<!-- 海外推荐商品 e -->
		<div id="ad-buttom">
			<div class="hw-goods-recommend"></div>
			<%-- <iframe src="http://click.simba.taobao.b5m.com/ad/hhaiwai_4_0.html?keywords=${category == null ? fn:split(res.Title, ' ')[0] : category}&cid=${cookieId}" frameborder="0" width="100%" scrolling="no" height="413px;"> </iframe> --%>
		</div>
		<!-- 百度广告 -->
		<div>
			<script type="text/javascript">
				var cpro_id = "u1400309";
			</script>
			<script src="http://cpro.baidustatic.com/cpro/ui/c.js?t=${today}"></script>
		</div>
	</div>
	<!--footer s-->
	<%@ include file="./common/footer.jsp"%>
	<!-- 淘点睛 -->
	<input type='hidden' id='has_tdj' value='1' />
	<input id="source" type="hidden" value="${res.Source}" />
	<input id="price" type="hidden" value="${res.Price}" />
	<input id="shopurl" type="hidden" value="${gotoUrl}" />
	<input id="url" type="hidden" value="${res.originUrl}" />
	<input id="keyword" type="hidden" value="${category == null ? fn:split(res.Title, ' ')[0] : category}" />
	<input id="cookieId" type="hidden" value="${cookieId}" />
	<input id="adRecordUrl" type="hidden" value="${adRecordUrl}" />
	<input id="docId" type="hidden" value="${dataSet.docId}" />
	<input id="topPriceHistroy" type="hidden" value="" />
	<input id="priceTrend" type="hidden" value="" />
	<input id="produceLoaded" type="hidden" value="false" />
	<input id="priceTrendType" type="hidden" value="" />
	<input id="productTitle" type="hidden" value="${res.Title}" />
	<input id="category" type="hidden" value="${res.Category}" />
	<input id="lastCategory" type="hidden" value="${category}" />
	<input id="canDaigou" type="hidden" value="${isDaigou}">
	<input id="daigouVal" type="hidden" value='${dataSet.goodsDetailInfoDto.daigouSource}'>
	<%@ include file="./snippet/daigou.jsp"%>
	<!-- 评论类型 -->
	<input id="commentType" type="hidden" />
	<script src="http://y.b5mcdn.com/scripts/search/details.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/highcharts.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/b5mtrend.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/price-trend-common.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/shop-detail.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/PCASClass.js"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/bdg.js?t=${today}"></script>
	<%-- <script type="text/javascript" src="${basePath}js/daigou/bdg.js"></script> --%>
	<script type="text/javascript">
		var isLogin = Cookies.get("login") === "true" && Cookies.get("token");
		$(document).ready(function() {
			var tabNum = "${tabNum}";
			var num = "${num}";
			if (num == 0)
				tabNum = "1";
			switch (tabNum) {
			case "1":
				tabInit($("#pinglun"));
				break;
			case "2":
				tabInit($("#canshu"));
				break;
			case "5":
				tabInit($("#shop-detail"));
				break;
			}
			ShopDetail.shopHaiwaiAd({container:$(".hw-goods-recommend"),url:'http://click.simba.taobao.b5m.com/haiwai/data/4_0.html?keywords=${category == null ? fn:split(res.Title, " ")[0] : category}'});
		});
		$(".bodyopen").click(function(){
			var daigouVal = $.parseJSON($("#daigouVal").val());//eval("(" + $("#daigouVal").val() + ")");
			var canDaigou = $("#canDaigou").val();
			var data = {};data.val = {};
			data.val.isDaigou = canDaigou;
			data.val.daigouSource = daigouVal;
        	Daigou.compareShow(data, $(this));
        });
	</script>
</body>
</html>