<%@page import="java.net.URLEncoder"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ page session="false"%>
<ul class="grid-view clear-fix" id="J_goods_list">
	<c:forEach items="${docResourceDtos}" varStatus="stat" var="docResourceDto">
		<c:set value="${docResourceDto.res}" var="resource" />
		<c:set var="linkDocId" value="${resource.DOCID}"></c:set>
		<c:set var="res" value="${resource}"></c:set>
		<c:set var="_title2" value="${resource.Title}"></c:set>
		<c:url var="detailUrl" value="${fn:indexOf(res.URL, 'http://')>=0 ? '' : 'http://'}${res.URL }"></c:url>
		<c:set var="buttonName" value="去看看"></c:set>
		<c:set value="${fn:indexOf(resource.Picture, 'b5mcdn.com')}" var="pictureIndex" />
		<c:if test="${pictureIndex > 0}">
			<c:set value="${resource.Picture}/238X238" var="picturePath" />
		</c:if>
		<li class="grid-ls">
			<div class="grid-mod">
				<div class="grid-in">
					<div class="pic-wrap">
						<div class="pic-mod">
							<a class="pic" href="${detailUrl}" data-attr="1008" target="_blank"><img data-attr="1008" class="grid-mod-pic" src="${fn:split(res.Pic, ',')[0] }" alt="${_title2}"></a>
						</div>
						<div class="mini-slider">
							<ul>
								<c:forEach items="${fn:split(res.Pic, ',') }" var="item">
									<li><a href=""><img src="${item }" width='25' height='25' alt=""></a></li>
								</c:forEach>
							</ul>
							<span class="arrow arrow-left">left</span> <span class="arrow arrow-right">right</span>
						</div>
					</div>
					<!-- star s-->
					<div class="start">
						<strong class="l">${resource.Brand }</strong>
						<div class="nums-goods r">已售${resource.SalesAmount }件</div>
					</div>
					<!-- star e-->
					<!-- title s-->
					<div class="summary">
						<a data-attr="1008" href="javascript:void(0)" linkurl="${detailUrl}" title="${_title2}" target="_blank" data-docid="${linkDocId }">${_title2}</a>
					</div>
					<!-- title e-->
					<div class="price-mod">
						<div class="nums-goods r">
							评论<b class="cl-f00">${res.CommentCount }</b>条
						</div>
						<strong class="price"><b>¥</b><span class="cl-f00">${res.Price}</span></strong>
					</div>
					<p class="goods-fun">
						<a class="goods-add" href="javascript:void(0)"></a> <a class="goods-share" id="share${stat.index }" href="javascript:void(0)"></a>
					</p>
					<p class="mall-info">
						<a href="${detailUrl}" class="mall-name">${res.Source }</a>
					</p>
					<input id="CommentURL" value="${res.CommentURL}" type="hidden" /> <input id="originalUrl" value="${detailUrl}" type="hidden" /> <input id="shopTitle" value="${_title2}" type="hidden" /> <input id="picUrl" value="${res.Pic}" type="hidden" />
				</div>
				<c:if test="${fn:length(res.Comment) > 0 }">
				<div class="goods-comments" style="display: none;">
					<h3>热门评论</h3>
					<ul class="content-comments">
						<li>
								<c:forEach items="${fn:split(res.Comment, '^^^') }" var="comment" varStatus="stat" begin="0" end="2">
										<p class="comments-detail">${comment }</p>
								</c:forEach>
						</li>
					</ul>
					<div class="show-more-comment show-more-comment-open">
						<span attr-url="${detailUrl}" class="span-all-comment">查看所有评论内容</span>
					</div>
					<span class="arrow-comment"></span>
				</div>
				</c:if>
			</div>
		</li>
	</c:forEach>
</ul>