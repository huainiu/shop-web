<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ taglib prefix="page" uri="http://www.b5m.com/tag/page" %>
<%@ page session="false"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@ include file="./common/meta.jsp" %>
	<title>${keyword}_${keyword}价格_${keyword}怎么样-购物搜索-帮5买</title>
	<meta name="keywords" content="${keyword},${keyword}价格,${keyword}怎么样,购物搜索,帮5买" />
	<meta name="description" content="帮5买购物搜索引擎为您提供${keyword}正品导购，${keyword}价格介绍，${keyword}各商城比价信息。轻松购物，更能省钱。" />
	<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/juhe_detail_v1.css?t=${today}">
</head>
<body>
	<%@ include file="./common/search.jsp"%>
	<div class="wp">
		<div class="cf mt20 mb20 breadcrumb">
			<h1>
			<a href="${basePath}">帮5买</a>
			<span>&gt;</span>
			<a href="${basePath}tag/keywords.html">热门搜索词</a>
			<span>&gt;</span>
			<span class="reci-keyword">${keyword}</span>
			</h1>
		</div>
		<c:if test="${fn:length(relatedQueryList) > 0}">
			<dl class="intelligent-recommend">
				<dt>您是不是想找：</dt>
				<dd>
					<c:forEach var="relatedQuery" items="${relatedQueryList}">
						<a data-attr="1010" href="${basePath}cp/${relatedQuery.base64Text}.html">${relatedQuery.text}</a>
					</c:forEach>
				</dd>
			</dl>
		</c:if>
	</div>
	
	<div class="wp cf">
		<div class="main">
			<div class="container">
				<!--横版的分类 s-->
				<div class="m-bord horizontal-category mb20" id="J_filter_cate">
					<c:set var="layer" value="${categoryLayer}"></c:set>
					<c:set var="hasMid" value="true"></c:set>
					<c:choose>
						<c:when test="${layer == 0 || layer == 1}">
							<c:if test="${fn:length(categoryList.linkTree) == 0}">
								<c:set var="hasMid" value="false"></c:set>
							</c:if>
						</c:when>
						<c:when test="${layer == 2 || layer == 3}">
							<c:forEach items="${categoryList.linkTree}" var="category" >
								<c:forEach items="${category.linkTree}" var="categoryLayer1">
									<c:if test="${categoryLayer1.link.clicked}">
										<c:if test="${fn:length(categoryLayer1.linkTree) == 0}">
											<c:set var="hasMid" value="false"></c:set>
										</c:if>
									</c:if>
								</c:forEach>
							</c:forEach>
						</c:when>
					</c:choose>
					<c:if test="${hasMid}">
						<div class="s-menu-list">
							<%@ include file="./snippet/category_list_mid.jsp"%>
						</div>
					</c:if>
				</div>		
				<!--横版的分类 s-->
				<!--搜索条件 e-->
				<div class="sort-bar cf mt20">
					<ul class="sort-item l">
						<c:forEach items="${sortList}" var="sort" varStatus="stat">
							<c:if test="${sort.text=='默认'}">
								<c:set var="cur" value="${empty sortField}" />
							</c:if>
							<c:if test="${sort.text=='销量'}">
								<c:set var="cur" value="${sortField=='CommentCount'}" />
							</c:if>
							<c:if test="${sort.text=='好评'}">
								<c:set var="cur" value="${sortField=='Score'}" />
							</c:if>
							<c:set var="priceCls" value=""></c:set>
							<c:if test="${sort.text=='价格'}">
								<c:set var="cur" value="${sortField=='Price'}" />
								<c:if test="${sortField=='Price'}">
									<c:set var="priceCls" value="${sortType == 'DESC'? 'down' : (sortType == 'ASC' ? 'up' : '')}"></c:set>
								</c:if>
							</c:if>
							<li class="${priceCls == '' ? (cur?'up':'') : priceCls}"><a data-attr='1007' class="${sort.text =='价格' ? 'price' : ''}"
								href="${sort.url}" rel="nofollow">${sort.text}</a></li>
						</c:forEach>
					</ul>
					<div class="price-input ml20 l ">
						价格区间 <input type="text" class="txt" name="startPrice" id="startPrice"> - <input type="text" class="txt" name="endPrice" id="endPrice"><input type="button" value="确定" class="btn" id="inner-search-btn">
					</div>
					<ul class="sort-conditions ml20 l" id="ul">
						<li class="${isFreeDelivery == 1 ? 'cur' : '' }"><a href="${freeDeliveryLink}" data-attr='1007' rel="nofollow">免运费</a></li>
						<li class="${isGenuine == 1 ? 'cur' : '' }"><a href="${genuineLink}" data-attr='1007' rel="nofollow">正品行货</a></li>
						<li class="${isCOD == 1 ? 'cur' : '' }"><a href="${codLink}" data-attr='1007' rel="nofollow">货到付款</a></li>
					</ul>
				</div>				
				<!--搜索条件 e-->

				<div class="goods-list">
					<%@ include file="./snippet/hotimageView.jsp"%>
					<!--page s-->
					<page:page pageView="${pageView}" pageClass="page cf mt20 mb20" firstPageUrl="${basePath}tage/${orign}.html"></page:page>
					<%-- <c:import url="common/page.jsp"></c:import> --%>
					<!--page e-->
				</div>
			</div>			
		</div>
		<c:if test="${fn:length(mallBrandInfos) > 0}">
			<dl class="intelligent-recommend">
				<dt>热词推荐：</dt>
				<dd>
					<c:forEach var="mallBrandInfo" items="${mallBrandInfos}">
						<a data-attr="1010" href="${basePath}${mallBrandInfo.url}.html">${mallBrandInfo.name}</a>
					</c:forEach>
				</dd>
			</dl>
		</c:if>
	</div>
	<!--淘特价 e-->
	<input id="taoKeywords" type="hidden" value="${taoKeywords}">
	<input id="keyword" type="hidden" value="${keyword}">
	<input id="cookieId" type="hidden" value="${cookieId}">
	<input id="orignKeyWord" type="hidden" value="${orignKeyword}">
	<input id="category" type="hidden" value="${category}">
	<!--底部 s-->
	<%@ include file="./common/footer.jsp"%>
	<!--底部 e-->
    <!--[if lt IE 9]>
	<script src="http://y.b5mcdn.com/scripts/search/media_query_v3.js?t=${today}"></script>
	<![endif]-->
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/highcharts.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/b5mtrend.min.js?t=${today}"></script>
	
    <script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/imglazyload.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/price-trend-common.min.js?t=${today}"></script>
	<%-- <script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/resultlist.min.js?t=${today}"></script> --%>
	<script type="text/javascript" src="/js/js/resultlist.js"></script>
    <!-- 价格趋势图 -->
	<div id="photo_price" style="display: none;position: absolute;">
		<div id="photo_price_content" style="display: none"></div>
	</div>
	<input type="hidden" id="price-trend-docids" value="${priceTrendDocIds}">
	<input type="hidden" id="price-trend-type">
	<script type="text/javascript">
		$(function() {
			$("img").imglazyload({fadeIn:true});
		});
		$(".feed-slogan a").attr("href", "http://ucenter.b5m.com/?loginReferer=" + encodeURIComponent(window.location.href));
		var isLogin = Cookies.get("login") === "true" && Cookies.get("token");
		if(isLogin){
			$(".feed-slogan").addClass("feed-lottery");
		}else{
			$(".feed-slogan").addClass("feed-login");
		}
		$("a").click(function() {
			var $this = $(this);
			var linkurl = $this.attr("linkurl");
			if(linkurl){
				$this.attr("href", linkurl);
			}
			var localhref = window.location.href;
			if (!localhref || localhref.indexOf("?") <= 0)
				return;
			var $thisHref = $this.attr("href");
			var params = localhref.split("?")[1];
			if ($thisHref && $thisHref.indexOf("?") < 0) {
				$this.attr("href", $thisHref + "?" + params);
			}
		});
	</script>
</body>
</html>