<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="./common/meta.jsp" %>
<c:set var="res" value="${dataSet.goodsDetailInfoDto}"></c:set>
<c:set value="${dataSet.attributes }" var="attributes"></c:set>
<title>${_title_}</title>
<meta name="keywords" content="${_keywords_}" />
<meta name="description" content="${_description_}" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<link rel="stylesheet" type="text/css" href="http://y.b5mcdn.com/css/search/search_detail.css?t=${today}">
<%@ include file="./common/baiduAD3.jsp" %>
</head>
<body style="position: relative;">
	<jsp:include page="./heads/${c}-search-header.jsp" flush="true"></jsp:include>
	<div id="nav" class="wp">
		<div class="breadcrumb">
			<span>所有分类</span>
			<c:if test="${dataSet.categoryValue != ''}">
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
						<a href="${orgbasePath}s/Search?key=${item}">${item}</a>
					</c:if>
				</c:forEach>
			</c:if>
			<c:if test="${not empty res.Title}">
				<span>&gt;</span>
				<span class="prod-name">${fn:substring(res.Title, 0, 50)}${fn:length(res.Title) > 50 ? "..." : ""}</span>
			</c:if>
		</div>
	</div>
	<c:url value="${b5m:gotoCps(res.Source, res.Url)}" var="gotoUrl" />
	<c:if test="${res.Source == '淘宝网' ||  res.Source == '天猫' || res.Source == '卓越亚马逊' || res.Source == '京东商城' || res.Source == '当当网'}">
		<c:url value="${res.Url}" var="gotoUrl" />
	</c:if>

	<%@ include file="./snippet/item-head.jsp"%>

	<div class="wp mb50" id="cp-ctn">
		<div class="tab-ctn">
			<ul class="tab cf" style="width: auto;">
				<c:if test="${productDetail != null && productDetail != ''}">
					<li><a class="tit cur" data="ah-product-detail" href="javascript:;">商品详情</a></li>
				</c:if>
				<li id="ah-shop"><a class="tit" data="ah-shop" href="javascript:;">全部商城</a></li>
				<li id="comment-li"><a class="tit" data="ah-comment" id="comment-tab" href="javascript:;">评论(0)</a></li>
				<c:if test="${fn:length(attributes) > 0}">
					<li><a class="tit" data="ah-detail" href="javascript:;">详情参数</a></li>
				</c:if>
				<li><a class="tit" data="ah-price-tend" href="javascript:;">价格趋势</a></li>
				<li class="un-tab btn-b5m"><a href="javascript:void(0);" data-attr="100803" class="bk-btn bwm-btn">帮我买</a> <a href="${basePath}exchange/item.htm?docId=${res.DOCID}&c=${col}" target="_blank" data-attr="102002" class="bk-btn orange">帮钻兑换</a></li>
			</ul>
		</div>

		<c:if test="${productDetail != null && productDetail != ''}">
			<a href="javascript:;" class="tab-anchor" name="ah-product-detail"></a>
			<div class="panel">
				<div class="pd-img" id="product-detail">${productDetail}</div>
				<script type="text/javascript" src="${basePath }js/js/imgLazy.js?t=${today}"></script>
				<c:if test="${res.Source == '当当网'}">
					<script type="text/javascript" src="${basePath }js/js/dangdang.js?t=${today}"></script>
					<link rel="stylesheet" type="text/css" href="${basePath }css/dangdang.css?t=${today}" />
				</c:if>
			</div>
		</c:if>
		<h3 class="sub-pl-tit ah-shop">全部商城</h3>
		<a href="javascript:;" class="tab-anchor ah-shop" name="ah-shop"></a>
		<div class="panel ah-shop">
			<div class="tit-inner">
				<a href="javascript:;">正品商城</a>
			</div>
			<div class="table cf">
				<table>
					<tbody id="all-shop-view" class="shopivew">
					</tbody>
				</table>
			</div>
		</div>
		<div class="panel personal">
			<div class="tit-inner">
				<a href="javascript:;">个人卖家</a>
				<span class="mark-personal">不能保证是正品，请谨慎购买</span>
			</div>
			<div class="table cf">
				<table>
					<tbody id="taobao-shop-view" class="shopivew">
					</tbody>
				</table>
			</div>
		</div>
		<h3 class="sub-pl-tit" id="comment-tit">评论</h3>
		<a href="javascript:;" class="tab-anchor" name="ah-comment" id="comment-ah"></a>
		<div class="panel comment" id="comment-panel">
			<div class="sel-ctn" id="sel-ctn">
				<a id="all" attr-type="ALL" class="selected" href="javascript:;">全部</a> <a id="good" attr-type="GOOD" href="javascript:;">好评(0)</a> <a id="normal" attr-type="NORMAL" href="javascript:;">中评(0)</a> <a id="bad" attr-type="BAD" href="javascript:;">差评(0)</a>
			</div>
			<div class="items" id="comment-view"></div>
			<div id="paging-view"></div>
		</div>
		<c:if test="${fn:length(attributes) > 0}">
			<h3 class="sub-pl-tit">详情参数</h3>
			<a href="javascript:;" class="tab-anchor" name="ah-detail"></a>
			<div class="panel product-param">
				<table>
					<tbody>
						<tr>
							<td class="th" colspan="2">基本参数</td>
						</tr>
						<c:forEach items="${attributes}" var="attr" varStatus="status">
							<tr>
								<td>${attr.key}</td>
								<td>${attr.value}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		<h3 class="sub-pl-tit">价格趋势(近90天的价格趋势)</h3>
		<a href="javascript:;" class="tab-anchor" name="ah-price-tend"></a>
		<div class="panel price-view">
			<div class="price-trend-hc" id="topPriceHistroyDiv"></div>
		</div>
	</div>
	<jsp:include page="./common/baiduAD2.jsp" flush="true">
		<jsp:param name="id" value="u1728063" />
	</jsp:include>
	<div class="wp mb20" id="hw-goods-recommend"></div>
	<jsp:include page="./common/baiduAD.jsp" flush="true">
		<jsp:param name="id" value="u1400309" />
	</jsp:include>

	<%@ include file="./common/footer.jsp"%>
	<input id="source" type="hidden" value="${res.Source}" />
	<input id="price" type="hidden" value="${res.Price}" />
	<input id="highPrice" type="hidden" value="${highPrice}" />
	<input id="cookieId" type="hidden" value="${cookieId}" />
	<input id="adRecordUrl" type="hidden" value="${adRecordUrl}" />
	<input id="originUrl" type="hidden" value="${res.originUrl}" />
	<input id="docId" type="hidden" value="${dataSet.docId}" />
	<input id="uuid" type="hidden" value="${res.uuid}" />
	<input id="productTitle" type="hidden" value="${res.Title}" />
	<input id="c" type="hidden" value="${c}" />
	<input id="col" type="hidden" value="${col}" />
	<!-- 评论类型 -->
	<input id="commentType" type="hidden" />
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/highcharts.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/b5mtrend.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/price-trend-common.min.js?t=${today}"></script>
	<%-- <script type="text/javascript" src="${basePath }js/js/shop-detail.js?t=${today}"></script> --%>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/shop-detail.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/search_detail.js?t=${today}"></script>
	<script type="text/javascript">
		//<![CDATA[
		window.PriceCompare.detailFun();
		//]]>
		var isLogin = Cookies.get("login") === "true" && Cookies.get("token");
		$(document).ready(function() {
			ShopDetail.shopHaiwaiAd({
				container : $("#hw-goods-recommend"),
				url : 'http://click.simba.taobao.b5m.com/haiwai/data/4_0.html?keywords='+encodeURIComponent("${category == null ? fn:split(res.Title, ' ')[0] : category}")
			});
		});
	</script>
</body>
</html>