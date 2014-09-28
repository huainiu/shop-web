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
	<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_result_v3.css?t=${today}">
</head>
<body>
	<%@ include file="./common/search.jsp"%>
	<div class="wp">
		<p class="search-tips breadcrumb mt20 mb20">
			抱歉，没有与“<span class="search-key cl-ff78">${orignKeyword}</span>”匹配的相关产品，但是我们帮你找到了
				<c:forEach items="${searchDTOWraps}" var="searchDTOWrap" varStatus="stat">
				“<span class="search-key cl-ff78">${searchDTOWrap.keywords}</span>”
				<c:if test="${stat.index == searchDTOWrapsSize - 2 && searchDTOWrapsSize > 1}">和</c:if>
				</c:forEach>
				的搜索结果。
		</p>
	</div>
	<div class="wp cf">
		<div class="main l">
			<div class="container">
				<c:forEach items="${searchDTOWraps}" var="searchDTOWrap">
					<div class="breadcrumb cf chaici-keywd">
						<div class="r"><a href="${basePath}search/s/___image________________${searchDTOWrap.keywords}.html" target="_blank">更多</a></div>
						<c:set value="${fn:length(searchDTOWrap.keywords)}" var="keywordslength" />
						<c:set value="${fn:length(orignKeyword)}" var="orignKeywordlength" />
						<c:set value="${fn:indexOf(orignKeyword, searchDTOWrap.keywords)}" var="keywordsindex" />
						<c:set value="" var="delWords" />
						<h2 class="l">
							<c:if test="${keywordsindex > -1}">
								<c:set value="${fn:substring(orignKeyword, 0, keywordsindex)}" var="delWords" />
							</c:if>
								${searchDTOWrap.keywords}
							<c:if test="${keywordsindex < orignKeywordlength - 1}">
								<c:set value="${fn:substring(orignKeyword, keywordsindex + keywordslength, orignKeywordlength)}" var="delWords2" />
								<del>${delWords} ${delWords2}</del>
							</c:if>
						</h2>
					</div>
					<div class="goods-list">
						<!--网格模式 s-->
						<ul class="grid-view cf mb20">
							<c:forEach items="${searchDTOWrap.docResourceDtos}" var="docResourceDto" begin="0" end="3">
								<c:set value="${docResourceDto.res}" var="resource" />
								<c:set value="${docResourceDto.subResources}" var="subResources" />
								<c:set value="${docResourceDto.suitablePriceRes}" var="suitablePriceRes" />
								<c:set var="linkDocId" value="${resource.DocId}"></c:set>
							
								<c:set var="res" value="${resource}"></c:set>
								<c:if test="${fn:length(subResources) >= 1}">
									<c:set var="res" value="${suitablePriceRes}"></c:set>
								</c:if>
								
								<c:set var="_title2" value="${resource.Title}"></c:set>
	
								<c:url value="${b5m:gotoCps(res.Source, res.Url)}" var="gotoUrl" />
								<c:if test="${res.Source == '淘宝网' ||  res.Source == '天猫' || res.Source == '卓越亚马逊' || res.Source == '京东商城' || res.Source == '当当网'}">
									<c:url value="${res.Url}" var="gotoUrl" />
									<c:set value="data-rd=\"1\"" var="datard1"></c:set>
									<c:set value="data-rd=\"2\"" var="datard2"></c:set>
									<c:if test="${res.Source == '卓越亚马逊' || res.Source == '京东商城' || res.Source == '当当网'}">
										<c:set value="data-rd=\"1\"" var="datard2"></c:set>
									</c:if>
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
								<li class="grid-ls">
									<div class="grid-mod">
										<div class="grid-in">
											<div class="pic-wrap">
												<a class="pic" target="_blank" href="${detailUrl}"><img src="${resource.Picture}" alt="${_title2}">
													<c:if test="${resource.isLowPrice != null && resource.isLowPrice != '' && resource.isLowPrice != '0.00'}"><span class="icon-countdown icon-zuidijia">降价</span></c:if>
												</a>
											</div>
											<div class="summary"><a data-attr="1008" href="${detailUrl}" title="${_title2}" target="_blank" data-docid="${linkDocId }">${_title2}</a></div>
											<div class="price">
												<c:if test="${fn:length(subResources) >= 1}">
													<div class="nums-goods r">
														<em>${resource.SourceCount}</em>家商城
													</div>
												</c:if>
												<strong><b>&yen;</b>${fn:length(subResources) >= 1 ? res.Price : res.price}</strong> <span style="display: none;"
													class="${fn:length(subResources) >= 1 ? '' : 'price-trend'}" data-docid="${linkDocId}" id="price-trend-${linkDocId}"></span>
												<input type="hidden" source="${res.Source}" id="price-trend-data-${linkDocId}" value="" />
											</div>
											<!-- star s-->
											<div class="start">
												<c:if test="${not empty resource.Score}">
													<c:set var="score" value="${resource.Score/5*100}"></c:set>
													<div class="start-warp l">
														<div class="start-in" style="width:${score}%;">&nbsp;</div>
													</div>
													<fmt:formatNumber type="number" var="viewscore" value="${resource.Score}" pattern="#0.0" />
													<strong class="l">${viewscore}</strong>
												</c:if>
												<c:choose>
													<c:when test="${fn:length(subResources) >= 1}">
														<div class="nums-goods r">
															<em>${resource.itemCount}</em>个比价商品
														</div>
													</c:when>
													<c:otherwise>
														<div class="nums-goods r"><em>${resource.Source}</em></div>
													</c:otherwise>
												</c:choose>
											</div> <!-- star e-->
										</div>
									</div> 
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:forEach>	
			</div>
		</div>		
		<c:set value="${searchDTOWraps[0].keywords}" var="firstKeyWords" />
		<div class="side side-r l mb20">
			<c:set var="sourceSize" value="${fn:length(searchDTOWraps)}"></c:set>
			<fmt:formatNumber type="number" value="${sourceSize/4 + 1 + (sourceSize%4 == 0 ? 0 : 1)}" maxFractionDigits="0" var="adSize"/>
			<iframe id="iFrame1" name="iFrame1" style="display:block;" src="http://click.simba.taobao.b5m.com/ad/vlb5m_${adSize}_0.html?keywords=${firstKeyWords}" frameborder="0" width="100%" scrolling="no"></iframe>
		</div>
	</div>

	<!--淘特价 e-->
	<input id="taoKeywords" type="hidden" value="${taoKeywords}">
	<input id="keyword" type="hidden" value="${keyword}">
	<input id="orignKeyWord" type="hidden" value="${orignKeyword}">
	<input id="category" type="hidden" value="${category}">
	<!--底部 s-->
	<%@ include file="./common/footer.jsp"%>
	
	<script src="http://y.b5mcdn.com/scripts/search/search_v3.js?t=${today}"></script>
    <!--[if lt IE 9]>
	<script src="http://y.b5mcdn.com/scripts/search/media_query_v3.js?t=${today}"></script>
	<![endif]-->
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/highcharts.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/b5mtrend.min.js?t=${today}"></script>
	
    <script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/imglazyload.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/price-trend-common.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/resultlist.min.js?t=${today}"></script>
	
	<!-- 价格趋势图 -->
	<div id="photo_price" style="display: none;position: absolute;">
		<div id="photo_price_content" style="display: none"></div>
	</div>
	<input type="hidden" id="price-trend-docids" value="${priceTrendDocIds}">
	<input type="hidden" id="price-trend-type">
	<script type="text/javascript">
		$(function() {
			$("img").imglazyload({fadeIn:true});
			$.ajax({
				url : "http://click.simba.taobao.b5m.com/ad/data/vb5m_0.html",
				type : 'POST',
				data : {
					keywords : "${keyword}"
				},
				dataType : 'jsonp',
				jsonp : 'jsoncallback',
				success : function(result) {
					if (!result || result.val == 0) {
						$('#ad-right iframe').css('visibility', 'hidden');
					}
				}
			});
		});
		$("a").click(function() {
			var localhref = window.location.href;
			if (!localhref || localhref.indexOf("?") <= 0)
				return;
			var $this = $(this);
			var $thisHref = $this.attr("href");
			var params = localhref.split("?")[1];
			if ($thisHref && $thisHref.indexOf("?") < 0) {
				$this.attr("href", $thisHref + "?" + params);
			}
		});
	</script>
	
	
</body>
</html>