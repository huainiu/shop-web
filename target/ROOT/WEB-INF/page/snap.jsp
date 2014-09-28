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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<link rel="stylesheet" type="text/css" href="http://y.b5mcdn.com/css/search/search_detail.css?t=${today}">
</head>
<body style="position: relative;">
	<jsp:include page="./heads/${c }${empty c ? '' : '-'}search-header.jsp" flush="true"></jsp:include>
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
						<a href="${orgbasePath}s/Search?car=${cat}">${item}</a>
					</c:if>
				</c:forEach>
			</c:if>
			<c:if test="${not empty res.Title}">
				<span>&gt;</span>
				<span class="prod-name">${fn:substring(res.Title, 0, 50)}${fn:length(res.Title) > 50 ? "..." : ""}</span>
			</c:if>
		</div>
	</div>
	<%@ include file="./snippet/snap-head.jsp"%>

	<c:if test="${fn:length(attributes) > 0}">
		<div id="cp-ctn" class="wp mb50">
			<div class="tab-ctn">
				<ul class="tab cf" style="width: auto;">
					<li><a class="tit cur" data="ah-detail" href="javascript:;">详情参数</a></li>
				</ul>
			</div>

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
		</div>
	</c:if>
	<div class="wp mb20"></div>
	<jsp:include page="./common/baiduAD.jsp" flush="true">
		<jsp:param name="id" value="u1395431" />
	</jsp:include>
	<div class="wp mb20" id="hw-goods-recommend"></div>

	<%@ include file="./common/footer.jsp"%>
	<input id="source" type="hidden" value="${res.Source}" />
	<input id="price" type="hidden" value="${res.Price}" />
	<input id="cookieId" type="hidden" value="${cookieId}" />
	<input id="adRecordUrl" type="hidden" value="${adRecordUrl}" />
	<input id="docId" type="hidden" value="${dataSet.docId}" />
	<input id="uuid" type="hidden" value="${res.uuid}" />
	<input id="productTitle" type="hidden" value="${res.Title}" />
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/snap.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/search_detail.js?t=${today}"></script>
	<script type="text/javascript">
		//<![CDATA[
		window.PriceCompare.detailFun();
		//]]>
		var isLogin = Cookies.get("login") === "true" && Cookies.get("token");
		$(document).ready(function() {
			ShopDetail.shopHaiwaiAd({
				container : $("#hw-goods-recommend"),
				url : 'http://click.simba.taobao.b5m.com/haiwai/data/4_0.html?keywords=' + encodeURIComponent("${category == null ? fn:split(res.Title, " ")[0] : category}")
			});
		});
	</script>
</body>
</html>