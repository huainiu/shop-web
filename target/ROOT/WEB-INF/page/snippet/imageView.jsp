<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<ul class="grid-view cf" id="J_goods_list">
	<c:forEach items="${docResourceDtos}" varStatus="stat" var="docResourceDto">
		<c:set value="${docResourceDto.res}" var="resource" />
		<c:set value="${docResourceDto.norms}" var="norms" />
		<c:set value="${docResourceDto.subResources}" var="subResources" />
		<c:set value="${docResourceDto.suitablePriceRes}" var="suitablePriceRes" />
		<c:set var="linkDocId" value="${resource.DOCID}"></c:set>
		<c:set var="res" value="${resource}"></c:set>
		<c:set var="_title2" value="${resource.Title}"></c:set>
		<c:url var="detailUrl" value="${basePath}item/${res.DOCID}.html"></c:url>
		<c:set var="buttonName" value="去看看"></c:set>
		<c:set value="${resource.Picture}" var="picturePath" />
		
		<c:set value="${fn:indexOf(resource.Picture, 'img.b5m.com')}" var="pictureIndex" />
		<c:if test="${pictureIndex < 0}">
			<c:set value="${fn:indexOf(resource.Picture, 'b5mcdn.com')}" var="pictureIndex" />
		</c:if>
		<c:if test="${pictureIndex > 0}">
			<c:set value="${resource.Picture}/200X200" var="picturePath" />
		</c:if>
		
		<c:set value="${fn:split(resource.OriginalPicture, ',')[0]}" var="firstPic"/>
		<c:if test="${fn:indexOf(firstPic, 'taobaocdn.com') > 0 && fn:indexOf(firstPic, '.jpg_') < 0}">
			<c:set value="${firstPic}_200x200.jpg" var="picturePath" />
		</c:if>
		<c:if test="${fn:indexOf(firstPic, 'alicdn.com') > 0 && fn:indexOf(firstPic, '.jpg_') < 0}">
			<c:set value="${firstPic}_200x200.jpg" var="picturePath" />
		</c:if>
		
		<c:set var="price" value="${res.Price}"></c:set>
		<li class="grid-ls" data-attr="1008" iscompare="${fn:length(subResources) >= 1}" docid="${fn:length(subResources) >= 1 ? resource.DOCID : res.DOCID}" id="${fn:length(subResources) >= 1 ? resource.DOCID : res.DOCID}">
			<div class="grid-mod" data-attr="1008">
				<div class="grid-in" data-attr="1008">
					<div class="pic-wrap" data-attr="1008">
						<c:if test="${stat.index < 4 }">
							<a class="pic" href="${detailUrl}" data-attr="1008" target="_blank"><img data-attr="1008" class="grid-mod-pic" src="${picturePath}" alt="${_title2}" onerror="this.src='${fn:split(resource.OriginalPicture, ',')[0]}'"></a>
						</c:if>
						<c:if test="${stat.index >= 4 }">
							<a class="pic" href="${detailUrl}" data-attr="1008" target="_blank"><img data-attr="1008" class="grid-mod-pic" src="http://tfs01.b5mcdn.com/image/T1BZxCBXJv1RCvBVdK" lazy-src="${picturePath}" onerror="this.src='${fn:split(resource.OriginalPicture, ',')[0]}'" alt="${_title2}"></a>
						</c:if>
					</div>
						<div class="etalon J_type">
							<c:if test="${norms != null }">
								<c:forEach items="${norms }" var="item" varStatus="stat">
									<c:if test="${stat.index == 0 }">
										<c:set value="${item.price }" var="price"></c:set>
										<a href="javascript:void(0);" data-price="${item.price }" class="a-active">${item.name }</a>
									</c:if>
									<c:if test="${stat.index > 0 }">
										<a href="javascript:void(0);" data-price="${item.price }">${item.name }</a>
									</c:if>
								</c:forEach>
							</c:if>
						</div>
					<!-- title s-->
					<div class="summary" data-attr="1008">
						<a href="${detailUrl}" data-attr="1008" title="${_title2}" target="_blank" data-docid="${linkDocId }">${_title2}</a>
					</div>
					<!-- title e-->
					<div class="price" data-attr="1008">
						<a href="${detailUrl_2}" data-attr="1008" target="_blank"><strong data-attr="1008"><b data-attr="1008">&yen;</b><span class="price-txt">${fn:split(price, '-')[0]}</span></strong></a> <span data-attr="1008" style="display: none;" class="${fn:length(subResources) >= 1 ? '' : 'price-trend'}" data-docid="${linkDocId}" id="price-trend-${linkDocId}"></span> <input type="hidden" source="${res.Source}" id="price-trend-data-${linkDocId}" value="" />
						<c:if test="${not empty resource.SalesAmount && resource.SalesAmount >= 0 }">
							<span class="sell-count" data-attr="1008">已售${resource.SalesAmount }件</span>
						</c:if>
					</div>
					<!-- star s-->
					<div class="start" data-attr="1008">
						<c:if test="${fn:length(subResources) >= 1}">
							<div class="nums-goods l" data-attr="1008">
								<em>${resource.SourceCount}</em>家商城
							</div>
						</c:if>
						<c:choose>
							<c:when test="${fn:length(subResources) >= 1}">
								<div class="nums-goods l" data-attr="1008">
									<em>${resource.ItemCount}</em>个比价商品
								</div>
							</c:when>
							<c:otherwise>
								<c:if test="${resource.Source != '淘宝网'}">
									<a href="${basePath}exchange/item.htm?docId=${res.DOCID}" target="_blank" class="duihuan r" data-attr="102001"></a>
								</c:if>
								<div class="nums-goods l" data-attr="1008">
									<em>${resource.Source}</em>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="grid-tail" data-attr="100800" lp="${resource.canDaigou ? '1' : '0'}">
                        <span data-attr="100800" lp="${resource.canDaigou ? '1' : '0'}"><i data-attr="100800"></i></span>
                    </div>
					<!-- star e-->
					<c:if test="${resource.canDaigou == 'true'}">
						<div class="lowest_price"></div>
					</c:if>
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
				<c:set value="${docResourceDto.norms}" var="norms" />
				<c:set value="${docResourceDto.subResources}" var="subResources" />
				<c:set value="${docResourceDto.suitablePriceRes}" var="suitablePriceRes" />
				<c:set var="linkDocId" value="${resource.DocId}"></c:set>

				<c:set var="res" value="${resource}"></c:set>
				<c:if test="${fn:length(subResources) >= 1}">
					<c:set var="res" value="${suitablePriceRes}"></c:set>
				</c:if>

				<c:set var="_title2" value="${resource.Title}"></c:set>
				<c:url var="detailUrl" value="${basePath}item/${res.DocId}.html"></c:url>
				<c:set var="buttonName" value="去看看"></c:set>
				
				<c:set value="${resource.Picture}" var="picturePath" />
				<c:set value="${fn:indexOf(resource.Picture, 'img.b5m.com')}" var="pictureIndex" />
				<c:if test="${pictureIndex < 0}">
					<c:set value="${fn:indexOf(resource.Picture, 'b5mcdn.com')}" var="pictureIndex" />
				</c:if>
				<c:if test="${pictureIndex > 0}">
					<c:set value="${resource.Picture}/200X200" var="picturePath" />
				</c:if>
		
				<c:if test="${fn:length(subResources) >= 1}">
					<c:url var="detailUrl" value="${basePath}compare/${keyword1}@${suitablePriceRes.Price}_${resource.DocId}.html"></c:url>
					<c:set var="buttonName" value="去比价"></c:set>
				</c:if>
				<li class="grid-ls">
					<div class="grid-mod">
						<div class="grid-in">
							<div class="pic-wrap">
								<a class="pic" href="${detailUrl}" target="_blank"><img data-attr="1008" class="grid-mod-pic" src="${basePath}images/placeholder.png" onerror="this.src='${fn:split(resource.OriginalPicture, ',')[0]}'" lazy-src="${picturePath}" alt="${_title2}"></a>
							</div>
							<c:if test="${norms != null }">
								<div class="etalon J_type">
									<c:forEach items="${norms }" var="item">
										<a href="javascript:void(0);" data-price="${item.price }">${item.name }</a>
									</c:forEach>
								</div>
							</c:if>
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
							<c:if test="${resource.canDaigou == 'true'}">
								<div class="lowest_price"></div>
							</c:if>
						</div>
						<div class="grid-tail" data-attr="100800" lp="${resource.canDaigou ? '1' : '0'}">
							<span data-attr="100800" lp="${resource.canDaigou ? '1' : '0'}"></span>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</c:forEach>
</c:if>
