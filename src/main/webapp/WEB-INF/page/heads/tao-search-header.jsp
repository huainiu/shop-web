<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="false"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/taosha.css?v=${today }">
<div class="taosha">
	<span></span>
	<div class="tpbar"></div>
	<div class="row search">
		<div class="container">
			<a href="http://tao.b5m.com" class="logo"><img src="http://img.b5m.com/image/T1MMdgBmYs1RCvBVdK" alt=""></a>
			<div class="logo-banner">
				<a href="http://www.b5m.com/qiang.html"><img width="80" height="80" alt="" src="http://cdn.bang5mai.com/upload/web/cmsphp//link/201408/9908ea152c12022293132.jpg"></a>
			</div>
			<div class="search-box">
				<form class="search-form" id="frm1" onsubmit="if(document.getElementById('query').value=='')return false; searchfrm(this, this.id)" target="_blank" name="frm1" method="post">
					<input type="hidden" value="http://tao.b5m.com/s/Search?key=[##]" id="urlfrm1"> <i class="icon-search">搜索</i> <input id="query" class="J_autofill" type="text" placeholder="快速搜索  全网比价 请输入您想买的商品">
					<button onclick="searchfrm(document.frm1, 'frm1');if (document.getElementById('query').value != '')document.frm1.submit();">搜索</button>
				</form>
			</div>
			<div class="search-hd">
				<div class="tel_400">
					客服热线：<b>400-077-5037</b>
				</div>
			</div>
		</div>
	</div>
	<div class="row nav">
		<div class="container">
			<div class="grid_3">
				<div class="index-menu">
					<cite> 正品100% 淘的就是底价</cite>
					<h4 class="index-menu-title">
						<a href="http://tao.b5m.com">首&nbsp;页</a>
					</h4>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	//<![CDATA[
	var $search_box = $('.search .search-box');
	$search_box.find('input').on({
		focus : function() {
			$search_box.addClass('search-box-hl');
		},
		blur : function() {
			$search_box.removeClass('search-box-hl');
		}
	});
	//]]>
</script>