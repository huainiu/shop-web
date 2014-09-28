<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="topbar">
</div>
<div class="header">
	<div class="wp cf">
		<div class="logo l">
			<a href="http://www.b5m.com"><img src="http://y.b5mcdn.com/static/images/www/logo.png" alt="#"></a>
		</div>
		<div class="search-tools">
			<form class="search-form" id="frm1" onsubmit="return searchfrm(this, this.id)" target="_blank" name="frm1" method="post">
				<input type="text" style="display: none;" value="${basePath}[##].html" id="urlfrm1">
				<span class="header-search-content">
					<label for="搜全网">搜索从这里开始</label>
					<input type="text" id="query" class="header-search-key J_autofill" x-webkit-speech="" value="${keyword}" placeholder="快速搜索全网旅游特价商品，景点、酒店">
					<a class="header-rearch-submit" href="javascript:void(0);" target="_self" onclick="searchfrm(document.frm1, 'frm1');if (document.getElementById('query').value != '')document.frm1.submit();">搜全网</a>
				</span>
				<c:if test="${fn:length(hotkey) > 0}">
					<span class="header-rearch-hotkey">
						热门搜索：<c:forEach items="${hotkey}" var="key"><a href="${basePath}${key.name}.html" data-attr="1005">${key.name }</a></c:forEach>
					</span>
				</c:if>
			</form>
			<div id="downList">
				<div class="downList">
					<ul id="list"></ul> 
				</div>
			</div>
		</div>
           <div class="top-country">
               <a title="美国馆" href="http://usa.b5m.com" target="_blank" data-attr='1004' class="usa">美国馆</a>
               <a title="韩国馆" href="http://korea.b5m.com" class="kor" target="_blank" data-attr='1004'>韩国馆</a>
           </div>
	</div>
	<div class="search-bar">
		<div class="wp cf">
			<ul class="nav-list">
				<li class="cur">
					<a href="http://www.b5m.com" target="_blank" data-attr='1004'>首页</a>
				</li>
				<li>
					<a href="http://zdm.b5m.com" target="_blank" data-attr='1004'>值得买</a>
				</li>
				<li>
					<a href="http://tejia.b5m.com" target="_blank" data-attr='1004'>淘特价</a>
				</li>
				<li>
					<a href="http://haiwai.b5m.com/" target="_blank" data-attr='1004'>海外馆</a> <i class="hot">热门</i>
				</li>
				<li>
					<a href="http://tuan.b5m.com" target="_blank" data-attr='1004'>帮团购</a>
				</li>
				<li>
					<a href="http://you.b5m.com" target="_blank" data-attr='1004'>帮5游</a>
				</li>
				<li>
					<a href="http://piao.b5m.com" target="_blank" data-attr='1004'>帮票务</a>
				</li>
				<li>
					<a href="http://yisheng.b5m.com/" target="_blank" data-attr='1004'>找医生</a><i class="new">新产品</i>
				</li>
				<li>
					<a href="http://t.b5m.com/" target="_blank" data-attr='1004'>帮5淘</a>
				</li>
			</ul>
			<div class="nav-more r" data-hover="nav-more-hover">
				<span class="nav-more-txt nav-icon icon-more">更多</span>
				<ul class="nav-more-list cf">
					<li><a href="http://guang.b5m.com/" target="_blank" class="nav-icon icon-b5g">帮5逛</a></li>
					<li><a href="http://korea.b5m.com/" target="_blank" class="nav-icon icon-hgg">韩国馆</a></li>
					<li><a href="http://www.b5m.com/forum.php" target="_blank" class="nav-icon icon-dis">讨论区</a></li>
					<li><a href="http://zdm.b5m.com/gzx/index_p1.html" target="_blank" class="nav-icon icon-war">购真相</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>