<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<ul>
	<c:forEach var="article" items="${articles}" varStatus="stat">
	<li>
		<a target="_blank" href="${article.link}" class="pic">
            <c:set value="${fn: replace(article.pic,'cdn.b5m.com','cdn.bang5mai.com')}" var="picturePath"/>
			<img src="${picturePath}" alt="" height="50" onerror="${article.old_pic}">
		</a>
		<div class="txt">
			<a href="${article.link}">${fn:substring(article.title, 0, 6)}${fn:length(article.title) > 6 ? '…' : ''}</a>
			<p>${fn:substring(article.summary, 0, 12)}${fn:length(article.summary) > 12 ? '…' : ''}</p>
		</div>
	</li>
	</c:forEach>
</ul>