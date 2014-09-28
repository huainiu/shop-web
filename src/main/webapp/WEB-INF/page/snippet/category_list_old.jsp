<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<h3 class="s-icon s-all">
	<c:choose>
	    <c:when test="${orignKeyword != '*' && categoryLayer > 0 && categoryList.link.url != ''}">
	    	<a href="${categoryList.link.url}" data-attr='1003' style='color:#000'>所有分类</a>
	    </c:when>
	    <c:otherwise>所有分类</c:otherwise>
    </c:choose>
</h3>


<dl class="m-dl">
	<c:forEach items="${categoryList.linkTree}" var="category" varStatus="stat1">
		<c:if test="${stat1.index < 2}">
			<dt>${category.link.text}</dt>
			<c:forEach items="${category.linkTree}" var="groupTree" varStatus="stat2">
				<dd>
					<a class=${groupTree.link.clicked ? "\"s-icon cur\""  : "s-icon"} href="${groupTree.link.url}" data-attr='1003'>${groupTree.link.text}</a>
					<c:set value="${groupTree.linkTree}" var="groupTreeSize"></c:set>
					<c:if test="${fn:length(groupTreeSize)>0}">
						<ul class="m-ul" style="display:block">
						<c:forEach items="${groupTree.linkTree}" var="tree2">
							<li><a ${tree2.link.clicked ? "class=\"cur\" " : ""} href="${tree2.link.url}" data-attr='1003'>${tree2.link.text}</a></li>
						</c:forEach>
						</ul>
					</c:if>
				</dd>
			</c:forEach >
		</c:if>
	</c:forEach>	
</dl>


<c:if test="${fn:length(categoryList.linkTree) > 2}">
	<div class="more-c more-category">
		<span><a href="javascript:void(0);" class="s-icon more-btn">更多分类 &gt;</a></span>
		<ul class="m-items cf ">
			<c:forEach items="${categoryList.linkTree}" var="category" varStatus="stat">
				<c:if test="${stat.index >= 2}">
					<li><a href="${category.link.url}" data-attr='1003'>${category.link.text}</a></li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
</c:if>

