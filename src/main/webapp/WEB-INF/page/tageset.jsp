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
<title>${title}</title>
<meta name="keywords" content="${Keywords}" />
<meta name="description" content="${description}" />
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/juhe_index_v1.css?t=${today}">
</head>
<body>
	<%@ include file="./common/search.jsp"%>
	<div class="wp">
		<div class="breadcrumb cf mt20 mb20">
			<a href="${basePath}">帮5买</a>
			<span>&gt;</span>
			<span class="reci-keyword">${displaytage == 'keywords'?'热门搜索词':'热门品牌词'}</span>
		</div>

		<!--热词分类 s-->
		<ul class="hot-category cf">
			<li class="${displaytage == 'keywords'?'cur':''}"><a href="${basePath}tag/keywords.html">热门商品词</a></li>
			<li class="${displaytage == 'brands'?'cur':''}"><a href="${basePath}tag/brands.html">热门品牌词</a></li>
		</ul>
		<!--热词分类 e-->
		
		<!--字母分类 s-->
		<div class="word-category">
			<span>按字母分类</span>
			<c:forEach var="word" items="${searchs}"><a href="/tag/${displaytage}/${word}/1.htm">${word}</a></c:forEach>
		</div>
		<!--字母分类 e-->
		<ul class="hot-words cf">
			<c:forEach var="mallBrandInfo" items="${pageView.records}"><li><a href="${basePath}${mallBrandInfo.url}.html">${mallBrandInfo.name}</a></li></c:forEach>
		</ul>
		<c:if test="${nopage != true}">
			<div class="page-num mb20">
				<c:forEach begin="1" end="${pageView.totalPage}" var="step">
					<a href="${pageUrl}${step}.htm">${step}</a>
				</c:forEach>
			</div>
		</c:if>
	</div>
	<%@ include file="./common/footer.jsp"%>
</body>
</html>