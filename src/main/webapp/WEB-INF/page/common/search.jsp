<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@page session="false"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="top-hdbanner"></div>
<div class="tpbar"></div>
<div class="head">
	<div class="wp cf">
		<div class="logo l">
			<a href="http://www.b5m.com"><img src="http://y.b5mcdn.com/images/logos/b5m.png" alt="#"></a>
		</div>
		<div class="logo-banner">
			<a data-mps="21045" href="http://www.b5m.com/qwdh.html"><img src="http://cdn.bang5mai.com/upload/web/cmsphp//link/201408/d80e6b5d0e0117202633.jpg" alt="" width="80" height="80"></a>
		</div>
		<div class="search-input" style="margin: 25px 0px 0px 33px;">
			<form class="search-form" id="frm1" onsubmit="return searchfrm(this, this.id)" target="_blank" name="frm1" method="post">
				<input type="hidden" value="http://s.b5m.com/s/Search?key=[##]" id="urlfrm1"> <span class="header-search-content"> <label for="搜全网">搜索从这里开始</label> <input type="text" id="query" class="header-search-key J_autofill" x-webkit-speech="" value="${keyword}" placeholder="快速搜索全网旅游特价商品，景点、酒店"> <a class="header-rearch-submit" href="javascript:void(0);" target="_self" onclick="searchfrm(document.frm1, 'frm1');if (document.getElementById('query').value != '')document.frm1.submit();">搜全网</a>
				</span>
			</form>
			<div id="downList">
				<div class="downList">
					<ul id="list"></ul>
				</div>
			</div>
		</div>
		<div class="top-hot-key">
			<div id="hot-key-div"></div>
		</div>
	</div>
	<div class="nav-bar">
		<div class="wp cf">
			<ul class="nav-list">
				<li class="cur"><a href="http://www.b5m.com" target="_blank" data-attr='1004'>首页</a></li>
				<li><a href="http://zdm.b5m.com" target="_blank" data-attr='1004'>值得买</a></li>
				<li><a href="http://tejia.b5m.com" target="_blank" data-attr='1004'>淘特价</a></li>
				<li><a href="http://tao.b5m.com/" target="_blank" data-attr='1004'>淘沙</a><i class="new">new</i></li>
				<li><a href="http://korea.b5m.com" target="_blank" data-attr='1004'>韩国馆</a></li>
				<li><a href="http://lady.b5m.com/" target="_blank" data-attr='1004'>女人街</a> <i class="new">new</i></li>
				<li><a href="http://you.b5m.com" target="_blank" data-attr='1004'>帮5游</a></li>
				<li><a href="http://tuan.b5m.com" target="_blank" data-attr='1004'>帮团购</a></li>
				<li><a href="http://bjr.b5m.com/" target="_blank" data-attr='1004'>帮金融</a></li>
				<li><a href="http://t.b5m.com/" target="_blank" data-attr='1004'>帮5淘</a></li>
			</ul>
			<div class="nav-more r" data-hover="nav-more-hover">
				<span class="nav-more-txt nav-icon icon-more">更多</span>
				<ul class="nav-more-list cf">
					<li><a href="http://haiwai.b5m.com/" target="_blank" class="nav-icon icon-hgg" data-attr='1004'>海外馆</a></li>
					<li><a href="http://bbs.b5m.com" target="_blank" class="nav-icon icon-dis" data-attr='1004'>讨论区</a></li>
					<li><a href="http://daikuan.b5m.com" target="_blank" class="nav-icon icon-search" data-attr='1004'>帮贷款</a></li>
					<li><a href="http://gzx.b5m.com/" target="_blank"  class="nav-icon icon-war" data-attr='1004'>购真相</a></li>
				</ul>
			</div>
			<div class="r-nav">
				<a href="http://ucenter.b5m.com/dh" target="_blank" data-attr='1004'>兑换中心</a>
			</div>
		</div>
	</div>
</div>