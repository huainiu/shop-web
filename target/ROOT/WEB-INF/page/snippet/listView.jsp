
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<ul class="list-view clear-fix">
	<c:forEach items="${docResourceDtos}" varStatus="stat" var="docResourceDto">
		<c:set value="${docResourceDto.res}" var="resource" />
		<c:set value="${docResourceDto.suitablePriceRes}" var="suitablePriceRes" />
		<c:set value="${docResourceDto.subResources}" var="subResources" />
		<c:set var="linkDocId" value="${resource.DocId}"></c:set>

		<c:set var="res" value="${resource}"></c:set>
		<c:if test="${fn:length(subResources) >= 1}">
			<c:set var="res" value="${suitablePriceRes}"></c:set>
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

		<c:set var="_title2" value="${resource.Title}"></c:set>

		<c:url var="detailUrl" value="${basePath}item/${res.DocId}.html"></c:url>
		<c:set var="buttonName" value="去看看"></c:set>
		<c:if test="${fn:length(subResources) >= 1}">
			<c:url var="detailUrl" value="${basePath}compare/${resource.DocId}.html"></c:url>
			<c:set var="buttonName" value="去比价"></c:set>
		</c:if>

        <c:set value="${fn:indexOf(resource.Picture, 'img.b5m.com')}" var="pictureIndex" />
        <c:if test="${pictureIndex > 0}">
           <c:set value="${resource.Picture}/98X98" var="picturePath"/>
        </c:if>
		<li class="list-mod clear-fix">
			<div class="col-left"><!-- img.b5m.com -->
				<div class="pic-wrap fl">
					<a data-attr="1008" class="pic" target="_blank" href="${detailUrl}"><img data-attr="1008" src="${basePath}images/placeholder.png" lazy-src="${picturePath}"
						alt="${_title2}"></a>
				</div>
				<div class="cell">
					<!-- title s-->
					<p>
						<a data-attr="1008" class="des" target="_blank" href="${detailUrl}" target="_blank" data-docid="${linkDocId }">${_title2}</a>
					</p>
					<!-- title e-->
					<c:set var="attrs" value="${fn:split(resource.Attribute, ',')}"></c:set>
					<c:forEach var="attr" items="${attrs}" varStatus="idx">
						<%-- <c:if test="${empty sourceValue}"> --%>
						<c:if test="${idx.index < 2}">
							<c:set var="attrArray" value="${fn:split(attr, ':')}"></c:set>
							<c:if test="${attrArray[0] != ''}">
								<p>${attrArray[0]}:${attrArray[1]}</p>
							</c:if>
						</c:if>
					</c:forEach>
					<!-- star s-->
					<div class="start clear-fix">
						<c:if test="${not empty resource.Score}">
							<c:set var="score" value="${resource.Score/5*100}"></c:set>
							<div class="start-warp">
								<div class="start-in" style="width:${score}%;">&nbsp;</div>
							</div>
							<fmt:formatNumber type="number" var="viewscore" value="${resource.Score}" pattern="#0.0" />
							<strong class="fl">${viewscore}</strong>
						</c:if>
						<c:if test="${empty resource.Score}">
							<div>
								<div>&nbsp;</div>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="col-middle">
				<div class="middle-in">
					<p class="price">
						<span style="display: none;" class="${fn:length(subResources) >= 1 ? '' : 'price-trend'}" data-docid="${linkDocId}"
							id="price-trend-${linkDocId}"></span>
						<input type="hidden" source="${res.Source}" id="price-trend-data-${linkDocId}" value="" />
						<strong><b>&yen;</b>${fn:length(subResources) >= 1 ? res.Price : res.price}</strong>
					</p>
					<ul class="shops-ls">
						<c:choose>
							<c:when test="${fn:length(subResources) >= 1}">
								<c:forEach items="${subResources}" var="subResource" begin="0" end="2" varStatus="stat">
									<c:set var="Source" value="${fn:substring(subResource.Source, 0, 8)}"></c:set>
									<c:url value="${b5m:gotoCps(subResource.Source,subResource.Url)}" var="gotoSubResourceUrl" />
									<c:if test="${subResource.Source == '淘宝网' ||  subResource.Source == '天猫'}">
										<c:url value="${subResource.Url}" var="gotoSubResourceUrl" />
										<c:set value="data-rd=\"2\"" var="subdatard2"></c:set>
									</c:if>
									<!-- 只用一跳 -->
									<c:if test="${subResource.Source == '卓越亚马逊' || subResource.Source == '京东商城' || subResource.Source == '当当网'}">
										<c:url value="${subResource.Url}" var="gotoSubResourceUrl" />
										<c:set value="data-rd=\"1\"" var="subdatard"></c:set>
									</c:if>

									<li>
										<%-- <a class="fl" target="_blank" href="${gotoSubResourceUrl}" title="${subResource.Source}" data-docid="${linkDocId }" ${subdatard2}>${Source}</a>
			                   	    <a class="fr" target="_blank" href="${gotoSubResourceUrl}" data-docid="${linkDocId }" ${subdatard2}>${subResource.Price}元</a> --%>
										<a data-attr="1008" class="fl" href="${basePath}item/${subResource.DOCID}.html" target="_blank" title="${subResource.Source}"
										data-docid="${subResource.DOCID}">${Source}</a> <a data-attr="1008" class="fr" href="${basePath}item/${subResource.DOCID}.html"
										target="_blank" data-docid="${subResource.DOCID}">${subResource.Price}元</a>
									</li>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li><c:set var="Source" value="${fn:substring(res.Source, 0, 8)}"></c:set> <%-- <a class="fl" href="${gotoUrl}" target="_blank" data-docid="${linkDocId}" ${datard2}>${res.Source}</a>
		                   	    <a class="fr" href="${gotoUrl}" target="_blank" data-docid="${linkDocId}" ${datard2}>${res.price}元</a> --%> <a
									class="fl" href="${basePath}item/${linkDocId}.html" target="_blank" title="${res.Source}"
									data-docid="${subResource.DOCID}">${Source}</a> <a data-attr="1008" class="fr" href="${basePath}item/${linkDocId}.html" target="_blank"
									data-docid="${subResource.DOCID}">${res.price}元</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
					<c:if test="${fn:length(subResources) >= 1}">
						<div class="nums-goods">
							<em>${resource.itemCount}</em>个比价商品
						</div>
					</c:if>
				</div>
			</div>
			<div class="col-right btn-wrap">
				<a data-attr="1008" href="${detailUrl}" target="_blank" class="go-buy fl" data-docid="${linkDocId}" ${datard1}>${buttonName }</a>
			</div>
		</li>
	</c:forEach>
</ul>