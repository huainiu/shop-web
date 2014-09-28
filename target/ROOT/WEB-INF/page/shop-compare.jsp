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
<%-- <c:import url="./common/meta.jsp" /> --%>
<c:set var="res" value="${dataSet.goodsDetailInfoDto}"></c:set>
<c:if test="${isDaigou}">
	<c:set var="res" value="${dataSet.goodsDetailInfoDto.daigouSource}"></c:set>
</c:if>
<title>${_title_}</title>
<meta name="keywords" content="${_keywords_}" />
<meta name="description" content="${_description_}" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_result.css?t=${today}">
<link rel="stylesheet" type="text/css" href="http://y.b5mcdn.com/css/search/detail.css?t=${today}">
<link type="text/css" rel="stylesheet" href="http://y.b5mcdn.com/css/search/bdg.css?t=${today}" />
</head>
<body style="position: relative;">
	<!--search s-->
	<%@ include file="./common/search.jsp"%>
	<!-- 价格趋势图 -->
	<div id="photo_price" style="display: none">
		<p>
			<strong>价格趋势</strong>(近3个月的价格趋势) <span class="gray"></span>
		</p>
		<div id="photo_price_content" style="display: none"></div>
	</div>
	<!--nav s -->
	<div id="nav" class="wp">
		<div class="nav-menu">
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
				<span>${res.Title}</span>
			</c:if>
		</div>
	</div>
	<!--nav e -->

	<!--layout-wrap s-->
	<div class="wp" id="compare-info-head">
		<h2 class="goods-title">${res.Title}</h2>
		<div class="layout-c cf">
			<div class="layout-m l">
				<div class="layout-content">
					<div class="prod-info">
						<div class="prod-hd cf mb20">
							<div class="l">
								<span class="prod-origin" id="low-price-source"></span> <strong class="price-info"><i>¥</i><span id="low-price-price">${fn:split(res.Price, '-')[0]}</span> <!-- 降价了提醒我 --></strong>
							</div>
							<!-- 优惠券 -->
							<div class="r" id="coupon"></div>
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
								<span class="r">月销量(<span class="clff78">${res.SalesAmount > 0 ? res.SalesAmount : 0}</span>) 评论
								</span> <span class="l star-grade">该商品暂无评分</span>
							</p>
							<p class="btn-mods">
								<a href="javascript:void(0)" class="btn-mod btn-mod-1" id="J_count_down">收藏以及降价提醒</a>
								<c:if test="${dataSet.snap}">
									<a href="javascript:void(0)" class="btn-mod btn-mod-2"><span>已下架</span></a>
								</c:if>
								<c:if test="${!dataSet.snap}">
									<c:if test="${isDaigou}">
										<a href='${basePath}${dataSet.goodsDetailInfoDto.daigouSource.Url}' target="_blank" class="btn-mod btn-mod-3" id="goBuy" lp="1" data-attr="100803"><span>帮我买</span></a>
									</c:if>
									<c:if test="${!isDaigou}">
										<a href='${gotoUrl}' class="btn-mod btn-mod-2 goBuy" target="_blank" data-attr="100806">直接去购买</a>
									</c:if>
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
		<!--tab s-->
		<div class="tab">
			<div class="tab-hd" id="detail-tabs">
				<c:set var="num" value="0" />
				<ul>
					<c:if test="${productDetail != '' && productDetail != null}">
						<c:set var="num" value="1" />
						<li id=detail class="cur"><a href="${detailLink}" onclick="return false;">商品详情</a></li>
					</c:if>
					<li id="allshop"><a href="${allShopLink}" onclick="return false;">全部商城<span class="source-count"></span></a></li>
					<li id="pinglun"><a href="${commentLink}" onclick="return false;"><span id="comment-chars">评论</span><span class="comment-count"></span></a></li>
					<li id="canshu"><a href="${paramLink}" onclick="return false;">详情参数</a></li>
					<li id="tab-price-trend"><a href="${tabPriceTrendLink}" onclick="return false;">价格趋势</a></li>
					<li id="low"><a href="${lowLink}" onclick="return false;">低价排行</a></li>
				</ul>
			</div>
			<div class="tab-bd">
				<c:if test="${productDetail != '' && productDetail != null}">
					<div class="tab-box" style="display: block;">
						<div style="text-align: center;" id="product-detail">${productDetail}</div>
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
								$(this).remove();
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
							$content.html($content.siblings("textarea").text());

							var $catalog = $("#catalog_all");
							$catalog.html($catalog.siblings("textarea").text());
							$catalog.css("display", "block");

							var $detailall = $("#detail_all");
							$detailall.html($detailall.find("textarea").text());
							$detailall.find("a").each(function() {
								$(this).removeAttr("href").removeAttr("target");
							});
						</script>
						<style>
.pro_content .section {
	margin-bottom: 10px;
	position: relative;
	overflow: hidden;
}

.pro_content .key {
	padding-top: 10px;
}

.pro_content .key li {
	text-indent: 22px;
	width: 33%;
	text-align: left;
	float: left;
	line-height: 24px;
}

.product_book .pro_content .section .tit {
	font-size: 14px;
}

.pro_content .section .tit,.pro_content .right_title1,.pro_content .left_title_1
	{
	background-color: #f5f5f5;
	font-weight: bold;
	height: 31px;
	line-height: 31px;
	padding-left: 17px;
	color: #404040;
}

.pro_content .section .descrip,.right_content {
	font-size: 14px;
	padding: 25px 29px 15px;
}

.mall_goods_foursort_style {
	padding: 15px 0;
}

.mall_goods_foursort_style_frame {
	float: left;
	line-height: 22px;
	overflow: hidden;
	color: #666;
	width: 33%;
	text-indent: 22px;
}

.mall_goods_foursort_style_frame a {
	color: #666;
}
</style>
					</c:if>
				</c:if>
				<!--全部商家 s-->
				<div class="tab-box ${num == 1 ? 'hide' : ''}">
					<!--筛选 s-->
					<div class="tab-filter">
						<ul class="shop-seq-item clear-fix">
							<li class="first cur"><a href="javascript:void(0);" type="all-shop-default">默认</a></li>
							<li><a href="javascript:void(0);" type="all-shop-new">最新</a></li>
							<li><a href="javascript:void(0);" type="all-shop-hot">最热门</a></li>
							<li class="last"><a href="javascript:void(0);" type="all-shop-asc">价格 <img src="${basePath}images/images/arr_up_07_08.gif" /></a></li>
						</ul>
						<div class="mall-compare">
							<span><i class="checkbox-icon checkbox--checked" name="zhengpin"></i>正品商城</span> <span><i class="checkbox-icon checkbox--checked" name="tianmao"></i>天猫</span> <span><i class="checkbox-icon checkbox--checked" name="taobao"></i>淘宝</span>
						</div>
					</div>
					<!--筛选 e-->
					<!--商家 s-->
					<div class="tab-shop clear-fix" id="all-shop-view">
						<ul class="tab-shop-header clear-fix">
							<li class="mall">商城</li>
							<li class="name-goods">商品名称</li>
							<li class="price-goods">价格</li>
							<li class="trend-goods">走势图</li>
							<li class="support-goods">服务保障</li>
							<li class="operate-goods">操作</li>
						</ul>
					</div>
					<!--商家 e-->
				</div>
				<!--全部商家 e-->
				<!--评论 s-->
				<div class="tab-box hide">
					<!-- 评论内容 -->
					<div class="shop-comments" id="all-comment-view">
						<ul class="comment-type cf">
							<li id="all" attr-type="ALL" class="cur"><span>全部</span></li>
							<li id="good" attr-type="GOOD"><span>好评</span></li>
							<li id="normal" attr-type="NORMAL"><span>中评</span></li>
							<li id="bad" attr-type="BAD"><span>差评</span></li>
						</ul>
						<div class="shop_select">
							来源商城： <select name="sel" id="sel"></select>
						</div>
						<ul class="comment-list" id="comment-view"></ul>
					</div>
					<!--tab-bd-comments e-->
				</div>
				<!--评论 e-->
				<!--详情参数 s-->
				<c:set value="${dataSet.attributes }" var="attributes"></c:set>
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
				<!--详情参数 e-->
				<!--价格趋势 s-->
				<div class="tab-box hide">
					<div style="display: none">
						<p>
							<strong>价格趋势</strong>(近90天的价格趋势) <span class="gray"></span>
						</p>
						<div id="priceTrendDiv"></div>
					</div>
				</div>
				<!--价格趋势 e-->
				<!--低价排行 s-->
				<div class="tab-box hide" id="lowDiv">
					<ul class="grid-view clear-fix recommend-view" id="lowDiv-view">
						<c:forEach items="${recommendList }" var="item">
							<li class="grid-mod">
								<div class="pic-wrap">
									<a target="_blank" data-attr="1010" class="pic" href="${basePath }item/${item.DocId}.html"> <img src="${item.Picture }" alt=""></a>
								</div>
								<p>
									<a target="_blank" class="des" href="${basePath }item/${item.DocId}.html">${item.Title }</a>
								</p>
								<div class="price">
									<div class="nums-goods">
										<em>2</em> 家商城
									</div>
									<strong><b>¥</b> ${item.price }</strong>
								</div>
								<div class="btn-wrap clear-fix">
									<a data-attr="1010" target="_blank" class="go-buy fl" href="${basePath}item/${item.DocId}.html">去看看</a>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
				<!--低价排行 e-->
			</div>
			<!--tab e-->
		</div>
		<div class="tab">
			<script type="text/javascript">
				var cpro_id = "u1400309";
			</script>
			<script src="http://cpro.baidustatic.com/cpro/ui/c.js?t=${today}"></script>
		</div>
		<!-- 商品详细 e -->
		<!-- 相关商品 s -->
		<div class="wp">
			<ul class="grid-view clear-fix recommend-view" id="recommand-shop-view"></ul>
		</div>
		<!-- 相关商品 e -->
		<!--shop-show e-->
		<script type="text/javascript">
			/*商品详情页C1*/
			var cpro_id = "u1395431";
		</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js?t=${today}"></script>
	</div>
	<%@ include file="./common/footer.jsp"%>
	<!-- 淘点睛 -->
	<input type='hidden' id='has_tdj' value='1' />
	<input id="docId" type="hidden" value="${dataSet.docId}" />
	<input id="topPriceHistroy" type="hidden" value="" />
	<input id="priceTrend" type="hidden" value="" />
	<input id="produceLoaded" type="hidden" value="false" />
	<input id="price-trend-type" type="hidden" value="" />
	<input id="productTitle" type="hidden" value="${res.Title}" />
	<input id="category" type="hidden" value="${res.Category}" />
	<input id="cookieId" type="hidden" value="${cookieId}" />
	<input id="adRecordUrl" type="hidden" value="${adRecordUrl}" />
	<!-- 全部商家的排序 -->
	<input id="sortField" type="hidden" value="${sortField}" />
	<input id="sortType" type="hidden" value="${sortType}" />
	<input id="daigouVal" type="hidden" value='${dataSet.goodsDetailInfoDto.daigouSource}'>
	<input id="canDaigou" type="hidden" value="${isDaigou}">
	<%@ include file="./snippet/daigou.jsp"%>

	<!-- 评论类型 -->
	<input id="commentType" type="hidden" />
	<script src="http://y.b5mcdn.com/scripts/search/details.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/highcharts.js?t=${today}"></script>
	<%-- <script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/shop-compare.min.js?t=${today}"></script> --%>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/shop-compare.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/price-trend-common.min.js?t=${today}"></script>
	<!-- <script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/b5mtrend.min.js?t=${today}"></script> -->
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/b5mtrend.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/PCASClass.js"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/bdg.js?t=${today}"></script>
	<!--footer s-->
	<script type="text/javascript">
		var isLogin = Cookies.get("login") === "true" && Cookies.get("token");
		$(document).ready(function() {
			var tabNum = "${tabNum}";
			var num = "${num}";
			if (num == 0)
				tabNum = "0";//如果没有详情，则全部商品的tab高亮
			switch (tabNum) {
			case "0":
				tabInit($("#allshop"));
				break;
			case "1":
				tabInit($("#pinglun"));
				break;
			case "2":
				tabInit($("#canshu"));
				break;
			case "3":
				tabInit($("#tab-price-trend"));
				break;
			case "5":
				tabInit($("#detail"));
				break;
			}
		});
		function tabInit($this) {
			$this.addClass("cur").siblings('li').removeClass('cur');
			var index = $this.index();
			var id = $this.attr('id');
			$('.tab-box').eq(index).show().siblings().hide();
			if (id == 'tab-price-trend' && is_init_allPriceTrend == false) {
				getAllPriceTrend();
			} else if (id == 'pinglun' && is_init_comment == false) {
				getCommentsInfo();
			} else if (id == 'allshop' && is_init_displayShopInfo == false) {
				displayShopInfo();
			}
		}
		$(".bodyopen").click(function(){
			var daigouVal = $.parseJSON($("#daigouVal").val());//eval("(" + $("#daigouVal").val() + ")");
			var canDaigou = $("#canDaigou").val();
			var data = {};
			data.val = {};
			data.val.isDaigou = canDaigou;
			data.val.daigouSource = daigouVal;
			Daigou.compareShow(data, $(this));
		});
	</script>
</body>
</html>