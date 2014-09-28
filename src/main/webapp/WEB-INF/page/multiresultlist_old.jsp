<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<%@ include file="./common/meta.jsp" %>
<title>${seotitle}推荐，${seotitle}哪个好，${seotitle}哪款好，${seotitle}比价-${currentPage}${currentPage == '' ? '' : '-'}帮5买</title>
<meta name="keywords" content="${seotitle}推荐，${seotitle}哪个好，${seotitle}哪款好，${seotitle}比价-${currentPage}${currentPage == '' ? '' : '-'}帮5买" />
<meta name="description" content="帮5买比价网汇聚了全网所有购物网站的${seotitle}，全网进行${seotitle}比价，用数据、用价格告诉你买${seotitle}什么网站好，什么${seotitle}品牌最好。帮5买比价网，一个可以${seotitle}比价的网站！" />
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_result.css?t=${today}">
<style type="text/css">
#photo_price {
	width: 426px;
	height: 184px;
	background: #fff;
	position: absolute;
	z-index: 100;
	border: 2px solid #e5e5e7;
}
#photo_price p {
	line-height: 20px;
	height: 20px;
	text-align: center;
}
#ad img {
	width: 200px;
}
#ad {
	margin-top: 20px;
}
</style>
</head>
<body>
	<%@ include file="./common/search.jsp" %>
	<%-- <jsp:include page="common/search.jsp"></jsp:include> --%>
	<div class="flex">
		<!-- 面包屑 s -->
		<div class="breadcrumbs clear-fix">
			<c:set value="${fn:length(searchDTOWraps)}" var="searchDTOWrapsSize"></c:set>
			<h2 class="fl">
				抱歉，没有与“<em class="search-key">${orignKeyword}</em>”匹配的相关产品，但是我们帮你找到了
				<c:forEach items="${searchDTOWraps}" var="searchDTOWrap" varStatus="stat">
				“<em>${searchDTOWrap.keywords}</em>”
				<c:if test="${stat.index == searchDTOWrapsSize - 2 && searchDTOWrapsSize > 1}">和</c:if>
				</c:forEach>
				的搜索结果。
			</h2>
		</div>
		<!-- 面包屑 e -->
	</div>
	<div class="flex clear-fix">
		<!-- 类目 s -->
		<div class="fl side mr20">
			<!-- 全部商品分类 s -->
			<%@ include file="./snippet/category_all.jsp" %>
			<%-- <c:import url="snippet/category_all.jsp"></c:import> --%>
			<!-- 全部商品分类 e -->
			<div class="mod-bd mt20">
				<script type="text/javascript">
					var cpro_id = "u1395038";
				</script>
				<script src="http://cpro.baidustatic.com/cpro/ui/c.js?t=${today}" type="text/javascript"></script>
			</div>
			
			<div class="mod-buy-list mod-list mt20"></div>
			
			<div class="mod-bd mt20" style="margin-left: 20px">
				<script type="text/javascript">
					var cpro_id = "u1395518";
				</script>
				<script src="http://cpro.baidustatic.com/cpro/ui/c.js?t=${today}" type="text/javascript"></script>
			</div>
			
			<%-- <div id="ad" class="mt20">${GoodsResultAds}</div> --%>
		</div>
		<!-- 类目 e -->
		<div class="cell">
			<div class="goods-list split-result">
				<c:forEach items="${searchDTOWraps}" var="searchDTOWrap">
					<div class="goods-hd">
						<a href="${basePath}search/s/___image________________${searchDTOWrap.keywords}.html" target="_blank" class="more">更多</a>
						<c:set value="${fn:length(searchDTOWrap.keywords)}" var="keywordslength" />
						<c:set value="${fn:length(orignKeyword)}" var="orignKeywordlength" />
						<c:set value="${fn:indexOf(orignKeyword, searchDTOWrap.keywords)}" var="keywordsindex" />
						<h3 class="keys-word">
							<c:if test="${keywordsindex > -1}">
								<del>${fn:substring(orignKeyword, 0, keywordsindex)}</del>
							</c:if>
							${searchDTOWrap.keywords}
							<c:if test="${keywordsindex < orignKeywordlength - 1}">
								<del>${fn:substring(orignKeyword, keywordsindex + keywordslength, orignKeywordlength)}</del>
							</c:if>
						</h3>
					</div>
					<ul class="grid-view clear-fix">
						<c:forEach items="${searchDTOWrap.docResourceDtos}" var="docResourceDto" begin="0" end="4">
							<c:set value="${docResourceDto.res}" var="resource" />
							<c:set value="${docResourceDto.subResources}" var="subResources" />
							<c:set value="${docResourceDto.suitablePriceRes}" var="suitablePriceRes" />
							<c:set var="linkDocId" value="${resource.DocId}"></c:set>

							<c:set var="res" value="${resource}"></c:set>
							<c:if test="${fn:length(subResources) >= 1}">
								<c:set var="res" value="${suitablePriceRes}"></c:set>
							</c:if>

							<c:url var="detailUrl" value="${basePath}item/${res.DocId}.html"></c:url>
							<c:set var="buttonName" value="去看看"></c:set>
							<c:if test="${fn:length(subResources) >= 1}">
								<c:url var="detailUrl" value="${basePath}compare/${keyword1}@${suitablePriceRes.Price}_${resource.DocId}.html"></c:url>
								<c:set var="buttonName" value="去比价"></c:set>
							</c:if>

							<c:url value="${b5m:gotoCps(res.Source, res.Url)}" var="gotoUrl" />
							<c:if test="${res.Source == '淘宝网' ||  res.Source == '天猫' || res.Source == '卓越亚马逊' || res.Source == '京东商城' || res.Source == '当当网'}">
								<c:url value="${res.Url}" var="gotoUrl" />
								<c:set value="data-rd=\"1\"" var="datard1"></c:set>
								<c:set value="data-rd=\"2\"" var="datard2"></c:set>
								<c:if test="${res.Source == '卓越亚马逊' || res.Source == '京东商城' || res.Source == '当当网'}">
									<c:set value="data-rd=\"1\"" var="datard2"></c:set>
								</c:if>
							</c:if>

							<li class="grid-mod">
								<div class="pic-wrap">
									<a class="pic" target="_blank" href="${resource.Url}"><img src="${basePath}images/placeholder.png"
										lazy-src="${resource.Picture}" alt="${resource.Title}"></a>
									<ul class="shops-ls">
										<c:choose>
											<c:when test="${fn:length(subResources) >= 1}">
												<c:forEach items="${subResources}" var="subResource" begin="0" end="2" varStatus="stat">
													<c:url value="${b5m:gotoCps(subResource.Source,subResource.Url)}" var="gotoSubResourceUrl" />
													<c:if test="${subResource.Source == '淘宝网' ||  subResource.Source == '天猫'}">
														<c:url value="${subResource.Url}" var="gotoSubResourceUrl" />
														<c:set value="data-rd=\"2\"" var="subdatard"></c:set>
													</c:if>
													<!-- 只用一跳 -->
													<c:if test="${subResource.Source == '卓越亚马逊' || subResource.Source == '京东商城' || subResource.Source == '当当网'}">
														<c:url value="${subResource.Url}" var="gotoSubResourceUrl" />
														<c:set value="data-rd=\"1\"" var="subdatard"></c:set>
													</c:if>
													<li><a class="fl" target="_blank" href="${gotoSubResourceUrl}" data-docid="${linkDocId }" ${subdatard}>${subResource.Source}</a>
														<a class="fr" target="_blank" href="${gotoSubResourceUrl}" data-docid="${linkDocId }" ${subdatard}>${subResource.Price}元</a></li>
												</c:forEach>
												<li class="more-shop"><a
													href="${basePath}compare/${searchDTOWrap.keywords}@${suitablePriceRes.Price}_${resource.DocId}.html" data-docid="${linkDocId }"
													target="_blank" class="fr">更多商家</a></li>
											</c:when>
											<c:otherwise>
												<li><a class="fl" target="_blank" href="${detailUrl}" style="height: 20px;" data-docid="${linkDocId}" ${datard2}>${_title2}</a>
												</li>
												<li>&nbsp;</li>
												<li><a class="fl" target="_blank" href="${gotoUrl}" data-docid="${linkDocId}" ${datard2}>${res.Source}</a> <a class="fr"
													target="_blank" href="${gotoUrl}" data-docid="${linkDocId}" ${datard2}>${res.price}元</a></li>
												<li class="more-shop"><a href="${gotoUrl}" data-docid="${linkDocId }" ${datard1} target="_blank" class="fr">去购买</a></li>
											</c:otherwise>
										</c:choose>
									</ul>
								</div> <!-- title s--> <c:set var="_title2" value="${res.Title}"></c:set> <c:if test="${fn:length(_title2) > 18}">
									<c:set var="_title2" value="${fn:substring(_title2, 0, 18)}..."></c:set>
								</c:if>
								<p>
									<a class="des" href="${gotoUrl}" title="${res.Title}" target="_blank" data-docid="${linkDocId }" ${datard1}>${_title2}</a>
								</p> <!-- title e-->
								<div class="price">
									<c:if test="${fn:length(subResources) > 1}">
										<div class="nums-goods">
											<em>${resource.SourceCount}</em>家商城
										</div>
									</c:if>
									<strong><b>&yen;</b>${fn:length(subResources) >= 1 ? res.Price : res.price}</strong> <span style="display: none;"
										class="${fn:length(subResources) >= 1 ? '' : 'price-trend'}" data-docid="${linkDocId}" id="price-trend-${linkDocId}"></span>
									<input type="hidden" id="price-trend-data-${linkDocId}" value="" />
								</div> <!-- star s-->
								<div class="start clear-fix">
									<c:if test="${not empty resource.Score}">
										<c:set var="score" value="${resource.Score/5*100}"></c:set>
										<div class="start-warp fl">
											<div class="start-in" style="width:${score}%;">&nbsp;</div>
										</div>
										<fmt:formatNumber type="number" var="viewscore" value="${resource.Score}" pattern="#0.0" />
										<strong class="fl">${viewscore}</strong>
									</c:if>
									<c:choose>
										<c:when test="${fn:length(subResources) >= 1}">
											<div class="nums-goods">
												<em>${resource.itemCount}</em>个比价商品
											</div>
										</c:when>
										<c:otherwise>
											<div class="nums-goods">${resource.Source}</div>
										</c:otherwise>
									</c:choose>
								</div> <!-- star e-->
								<div class="btn-wrap clear-fix">
									<a href="${detailUrl}" target="_blank" class="go-buy fl" data-docid="${linkDocId}" ${datard1}>${buttonName }</a>
								</div>
							</li>
						</c:forEach>
					</ul>
				</c:forEach>
			</div>
		</div>
	</div>
	<!--淘特价 s-->
	<div class="flex">
		<div id="tao">
			<div class="inner">
				<div class="tao-hd">
					<a href="http://tejia.b5m.com/?utm_source=mainb5t&utm_medium=b5t&utm_campaign=tao_item" target="_blank">更多&gt;</a>
					<h2>淘特价</h2>
				</div>
				<div class="tao-bd"></div>
			</div>
		</div>
	</div>
	<!-- 价格趋势图 -->
	<div id="photo_price" style="display: none">
		<p>
			<strong>价格趋势</strong>(近3个月的价格趋势) <span class="gray"></span>
		</p>
		<div id="photo_price_content" style="display: none"></div>
	</div>
	<input type="hidden" id="price-trend-docids" value="${priceTrendDocIds}">
	<input type="hidden" id="price-trend-type">
	<script type="text/javascript">
		$("a").click(function(){
			var localhref = window.location.href;
			if(!localhref || localhref.indexOf("?") <= 0) return;
			var $this = $(this);
			var $thisHref = $this.attr("href");
			var params = localhref.split("?")[1];
			if($thisHref && $thisHref.indexOf("?") < 0){
				$this.attr("href", $thisHref + "?" + params);
			}
		});
	</script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/imglazyload.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/www_s.js?t=${today}"></script>

	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/tao-shop.${min}js"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/highcharts.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/b5mtrend.min.js?t=${today}"></script>

	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/price-trend-common.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/resultlist.min.js?t=${today}"></script>
	<input id="taoKeywords" type="hidden" value="${taoKeywords}">
	<!--底部 s-->
	<%@ include file="./common/footer.jsp" %>
	<%-- <c:import url="common/footer.jsp"></c:import> --%>
	<!--底部 e-->
	<!--[if lte IE 9]>
	<script src="http://y.b5mcdn.com/scripts/search/media_query.js?t=${today}"></script>
	<![endif]-->
</body>