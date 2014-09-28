<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ page session="false"%>
<!-- 只有淘宝和天猫的 设置一级跳或者二级跳。 去购买 一级跳，浮层中显示 二级跳，标题,更多商家和去比较 都是去比较页面 -->
<ul class="grid-view cf" id="J_goods_list">
	<c:forEach items="${docResourceDtos}" varStatus="stat" var="docResourceDto">
		<c:set value="${docResourceDto.res}" var="resource" />
		<c:set value="${docResourceDto.subResources}" var="subResources" />
		<c:set value="${docResourceDto.suitablePriceRes}" var="suitablePriceRes" />
		<c:set var="linkDocId" value="${resource.DOCID}"></c:set>
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
		<c:url var="detailUrl" value="${basePath}item/${res.DOCID}.html"></c:url>
		<c:url var="detailUrl_2" value="${basePath}item/${res.DOCID}_2.html"></c:url>
		<c:url var="detailUrl_3" value="${basePath}item/${res.DOCID}_3.html"></c:url>
		<c:set var="buttonName" value="去看看"></c:set>
		<c:if test="${fn:length(subResources) >= 1}">
			<c:url var="detailUrl" value="${basePath}compare/${resource.DOCID}.html"></c:url>
			<c:url var="detailUrl_2" value="${basePath}compare/${resource.DOCID}_2.html"></c:url>
			<c:url var="detailUrl_3" value="${basePath}compare/${resource.DOCID}_3.html"></c:url>
			<c:set var="buttonName" value="去比价"></c:set>
		</c:if>
		<c:set value="${fn:indexOf(resource.Picture, 'img.b5m.com')}" var="pictureIndex" />
		<c:set value="${resource.Picture}" var="picturePath" />
		<c:if test="${pictureIndex > 0}">
			<c:set value="${resource.Picture}/238X238" var="picturePath" />
		</c:if>
		<li class="grid-ls" data-attr="1008" iscompare="${fn:length(subResources) >= 1}" docid="${fn:length(subResources) >= 1 ? resource.DOCID : res.DOCID}" id="${fn:length(subResources) >= 1 ? resource.DOCID : res.DOCID}">
			<div class="grid-mod">
				<div class="grid-in">
					<div class="pic-wrap">
						<a class="pic" href="${detailUrl}" target="_blank"><img data-attr="1008" class="grid-mod-pic" src="${picturePath}" alt="${_title2}"></a>
						<%-- <c:if test="${resource.isLowPrice != null && resource.isLowPrice != '' && resource.isLowPrice != '0.00'}">
							<span class="icon-countdown icon-zuidijia">降价</span>
						</c:if> --%>
					</div>
					<!-- title s-->
					<div class="summary">
						<a href="${detailUrl}" title="${_title2}" target="_blank" data-docid="${linkDocId }">${_title2}</a>
					</div>
					<!-- title e-->
					<div class="price">
						<a href="${detailUrl_2}" target="_blank"><strong><b>&yen;</b>${res.Price}</strong></a> <span style="display: none;" class="${fn:length(subResources) >= 1 ? '' : 'price-trend'}" data-docid="${linkDocId}" id="price-trend-${linkDocId}"></span> <input type="hidden" source="${res.Source}" id="price-trend-data-${linkDocId}" value="" />
						<c:if test="${not empty resource.SalesAmount && resource.SalesAmount >= 0 }">
							<span class="sell-count">已售${resource.SalesAmount }件</span>
						</c:if>
					</div>
					<!-- star s-->
					<div class="start">
						<c:if test="${fn:length(subResources) >= 1}">
							<div class="nums-goods l">
								<em>${resource.SourceCount}</em>家商城
							</div>
						</c:if>
						<c:choose>
							<c:when test="${fn:length(subResources) >= 1}">
								<div class="nums-goods l">
									<em>${resource.ItemCount}</em>个比价商品
								</div>
							</c:when>
							<c:otherwise>
								<div class="nums-goods l">
									<em>${resource.Source}</em>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<!-- star e-->
				</div>
			</div>
		</li>
	</c:forEach>
</ul>

<c:if test="${reSearchMore}">
	<!--网格模式 e-->
	<div class="chaici-tips mt20">
		<p>
			抱歉，这个“<span class="cl-ff78">${orignKeyword}</span>”匹配的结果较少，我们帮你找到了
			<c:forEach items="${searchDTOWraps}" var="searchDTOWrap" varStatus="stat">
				“<span class="cl-ff78">${searchDTOWrap.keywords}</span>”
				<c:if test="${stat.index == searchDTOWrapsSize - 2 && searchDTOWrapsSize > 1}">和</c:if>
			</c:forEach>
			的搜索结果。
		</p>
	</div>
	<c:forEach items="${searchDTOWraps}" var="searchDTOWrap">
		<div class="breadcrumb cf chaici-keywd mt20">
			<div class="r">
				<a href="${basePath}search/s/___image________________${searchDTOWrap.keywords}.html" target="_blank">更多</a>
			</div>
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
		<!--网格模式 s-->
		<ul class="grid-view cf">
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
								<a class="pic" href="${detailUrl}" target="_blank"><img data-attr="1008" class="grid-mod-pic" src="${picturePath}" alt="${_title2}"></a>
								<%-- <c:if test="${resource.isLowPrice != null && resource.isLowPrice != '' && resource.isLowPrice != '0.00'}">
									<span class="icon-countdown icon-zuidijia">降价</span>
								</c:if> --%>
							</div>
							<div class="summary">
								<a href="${detailUrl}" title="${_title2}" target="_blank" data-docid="${linkDocId }">${_title2}</a>
							</div>
							<div class="price">
								<strong><b>&yen;</b>${fn:length(subResources) >= 1 ? res.Price : res.price}</strong> <span style="display: none;" class="${fn:length(subResources) >= 1 ? '' : 'price-trend'}" data-docid="${linkDocId}" id="price-trend-${linkDocId}"></span> <input type="hidden" source="${res.Source}" id="price-trend-data-${linkDocId}" value="" />
								<c:if test="${not empty resource.SalesAmount && resource.SalesAmount >= 0 }">
								<span class="sell-count">已售${resource.SalesAmount }件</span>
							</c:if>
							</div>
							<!-- star s-->
							<div class="start">
								<c:if test="${fn:length(subResources) >= 1}">
									<div class="nums-goods l">
										<em>${resource.SourceCount}</em>家商城
									</div>
								</c:if>
								<c:choose>
									<c:when test="${fn:length(subResources) >= 1}">
										<div class="nums-goods l">
											<em>${resource.itemCount}</em>个比价商品
										</div>
									</c:when>
									<c:otherwise>
										<div class="nums-goods l">
											<em>${resource.Source}</em>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
							<!-- star e-->
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</c:forEach>
</c:if>
