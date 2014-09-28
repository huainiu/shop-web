<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ taglib prefix="page" uri="http://www.b5m.com/tag/page" %>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="./common/meta.jsp"%>
<title>${mallBrandInfo.name}官网_${mallBrandInfo.name}价格_${mallBrandInfo.name}怎么样-帮5买</title>
<meta name="keywords" content="${mallBrandInfo.name}官网,${mallBrandInfo.name}价格,${mallBrandInfo.name}怎么样-帮5买" />
<meta name="description" content="帮5买电子商务聚合平台，将消费者${mallBrandInfo.name}、京东、淘宝等电商网站联接起来，是消费者了解${mallBrandInfo.name}怎么样，${mallBrandInfo.name}网上购物、${mallBrandInfo.name}正品优惠信息的平台，并最终成为网民网上购物的入口。" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_result_shangjia.css?t=${today}">
</head>
<body>
	<%@ include file="./common/search.jsp"%>
	<div class="wp">
		<div class="cf mt20 mb20 breadcrumb">
			<a href="${basePath}">帮5买</a>
			<span>&gt;</span>
			<a href="${basePath}tag/brands.html">热门品牌词</a>
			<span>&gt;</span>
			<span class="reci-keyword">${mallBrandInfo.name}</span>
		</div>
	</div>
	<div class="wp cf">
        <!--品牌 s-->
        <div class="brand-panel mb20">
            <h2 class="hd"><strong class="title">${mallBrandInfo.name}简介</strong></h2>
            <div class="brand-panel-in cf">
                <div class="brand-logo fl">
                    <img src="${mallBrandInfo.img}" height="80" alt="">
                </div>
                <div class="brand-des cell">
                    <h2><strong><a href="javascript:void(0)">${mallBrandInfo.name}</a></strong></h2>
                    <p><a href="javascript:void(0)">${mallBrandInfo.description}</a></p>
                </div>
            </div>
        </div>
        <!--品牌 d-->
        <!--商品列表 s -->
        <div class="goods-list">
            <h2 class="hot-product"><strong>${mallBrandInfo.name}热销商品</strong></h2>
            <!--网格模式 s-->
            <ul class="grid-view cf mb20">
            	<c:forEach items="${pageView.records}" var="resourceDto">
            		<c:set value="${resourceDto.res}" var="res"></c:set>
            		<c:set value="${resourceDto.subResources}" var="subResources" />
            		<c:set var="linkDocId" value="${res.DocId}"></c:set>
            		<c:url var="detailUrl" value="${basePath}item/${res.DocId}.html"></c:url>
					<c:set var="buttonName" value="去看看"></c:set>
					<c:if test="${fn:length(subResources) >= 1}">
						<c:url var="detailUrl" value="${basePath}compare/${res.DocId}.html"></c:url>
						<c:set var="buttonName" value="去比价"></c:set>
					</c:if>
	                <li class="grid-ls">
	                    <div class="grid-mod">
	                        <div class="grid-in">
	                        	<c:set value="${fn:indexOf(res.Picture, 'img.b5m.com')}" var="pictureIndex" />
						        <c:if test="${pictureIndex > 0}">
						           <c:set value="${res.Picture}/238X238" var="picturePath"/>
						        </c:if>
	                            <div class="pic-wrap">
	                            	<a class="pic" href="${detailUrl}" data-attr="1008" target="_blank"><img data-attr="1008" class="grid-mod-pic"  src="${basePath}images/placeholder.png" lazy-src="${picturePath}" alt="${resource.Title}"></a>
	                            </div>
	                            <div class="summary"><a data-attr="1008" href="${detailUrl}" title="${res.Title}" target="_blank" data-docid="${linkDocId }">${res.Title}</a></div>
	                            <div class="price">
									<c:if test="${fn:length(subResources) >= 1}">
										<div class="nums-goods r">
											<em>${res.SourceCount}</em>家商城
										</div>
									</c:if>
									<strong><b>&yen;</b>${fn:split(res.price, '-')[0]}</strong> <span style="display: none;"
										class="${fn:length(subResources) >= 1 ? '' : 'price-trend'}" data-docid="${linkDocId}" id="price-trend-${linkDocId}"></span>
									<input type="hidden" source="${res.Source}" id="price-trend-data-${linkDocId}" value="" />
								</div> 
								<div class="start">
									<c:if test="${not empty res.Score}">
										<c:set var="score" value="${res.Score/5*100}"></c:set>
										<div class="start-warp l">
											<div class="start-in" style="width:${score}%;">&nbsp;</div>
										</div>
										<fmt:formatNumber type="number" var="viewscore" value="${res.Score}" pattern="#0.0" />
										<strong class="l">${viewscore}</strong>
									</c:if>
									<c:choose>
										<c:when test="${fn:length(subResources) >= 1}">
											<div class="nums-goods r">
												<em>${res.itemCount}</em>个比价商品
											</div>
										</c:when>
										<c:otherwise>
											<div class="nums-goods r"><em>${res.Source}</em></div>
										</c:otherwise>
									</c:choose>
								</div> <!-- star e-->
	                        </div>
	                    </div>
	                </li>
            	</c:forEach>
            </ul>
            <!--网格模式 e-->
            <page:page pageView="${pageView}" pageClass="page cf mt20 mb20" firstPageUrl="${basePath}shop_${mallBrandInfo.id}.htm"></page:page>
        </div>
        <!--商品列表 e-->
    </div>
    <%@ include file="./common/footer.jsp"%>
    <div id="photo_price" style="display: none;position: absolute;">
		<div id="photo_price_content" style="display: none"></div>
	</div>
    <input type="hidden" id="price-trend-docids" value="${priceTrendDocIds}">
    <input type="hidden" id="price-trend-type">
    <script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/highcharts.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/b5mtrend.min.js?t=${today}"></script>
    <script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/price-trend-common.min.js?t=${today}"></script>
    <script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/imglazyload.min.js?t=${today}"></script>
    <script type="text/javascript">
		$(function() {
			$("img").imglazyload({fadeIn:true});
			var docids = $("#price-trend-docids").val();
			$.post(_basePath + "pricehistory/priceTrendTypes.htm", {docIds:docids}, function(result){
				$("#price-trend-type").val(result);
				showPriceIcon();
			});
		});
		function showPriceIcon(){
			var priceTrendType = $("#price-trend-type").val();
			if(priceTrendType){
				var typesInfo = priceTrendType.split(";");
				for (var i = 0; i < typesInfo.length; i++) {
					var docid = typesInfo[i].split(",")[0];
					var type = typesInfo[i].split(",")[1];
					var $div = $("#price-trend-" + docid);
					if(type=="-1"){//flat-icon
						$div.addClass("price-down");
					}else if(type=="0"){
						$div.addClass("price-fair");
					}else if(type=="1"){
						$div.addClass("price-up");
					} else{
						$div.addClass("price-fair");
					}
					$div.show();
					banner2("#price-trend-" + docid, "#photo_price");
				}
			}
		}
	</script>
</body>
</html>