<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>商品详情</title>
<%@ include file="./common/meta.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9; IE=EmulateIE10" />
<link rel="stylesheet" href="http://staticcdn.b5m.com/proj/cart/css/centre_min.css?t=${today}" />
</head>
</head>
<body>
	<%@ include file="./common/search.jsp"%>
	<!-- body html-->
	<div class="body">
	    <div class="centre-details">
	        <ul class="centre-details-l">
	            <li class="pic"><img src="${product.Picture}" onerror="this.src='${fn:split(product.OriginalPicture, ',')[0]}'" /></li>
	        </ul>
	        <ul class="centre-details-r">
	            <li class="centre-details-title">
	                <h3><a href="${product.detailUrl}" class="purple-hover" target="_blank">${product.Title}</a></h3>
	            </li>
	             <li class="centre-details-middle">
	                <dl class="centrt-sku" style="display: none;">
	                	<input type="hidden" class="maxNum" value="${maxNum}" />
	                	<dt>数量：</dt><dd class="centre-details-quantity">
	                        <span data-id="005" data-cid="C_002" class="btn-subtraction">-</span>
	                        <span class="input-box"><input id="quantity005" name="quantity005" type="text" class="p-text" value="1" readonly /></span>
	                        <span data-id="005" data-cid="C_002" class="btn-add">+</span>
	                        <font> 件</font>
						</dd>
	                </dl>
	               <dl class="centre-onload" id="skuLoad">
	                    <span class="load-box"></span>
	                	<p>商品信息正在玩命加载中...</p>
	                </dl>
	                <dl class="centre-details-price">
	                    <span class="price-lt"><em class="icon-money"></em>商品原价：<font class="average-price" data="${priceAvg}">${priceAvg}元</font></span>
	                    <span class="price-lb"><em class="icon-logo"></em>帮我买价格：<font class="total-product-price" data="${product.Price}">${product.afterRemainPrice}元</font></span>
	                    <span class="price-r"><em class="icon-stand-by"></em>立减<font class="remain-price">${remainPrice}元</font></span>
	                </dl>
	                <dl class="centre-error-msg">
	                	<a class="centre-error-close">&times;</a>
	                    <p><em class="icon-warning-red"></em>请选择你要的商品信息</p>
	                </dl>
	
	            </li>
	            <!-- 立即购买按钮 -->
	            <li class="centre-details-btns">
	                <a class="btn-center-red btn-red-two" href="javascript:void(0);" data-attr="1016"><em class="icon-shopping"></em>立即购买</a>
	                <a class="btn-center-orange btn-orange">放入购物车</a>
	                <span class="add-cart-success"> <a href="http://cart.b5m.com" class="go-to-cart">去购物车结算</a> <a href="javascript:void(0);" class="go-shoping">继续购物</a><i class="cart-close"></i></span>
	            </li>
	            <!-- 帮我买代购声明 -->
	            <!--<li class="centre-details-text">
					<label><input type="checkbox" name="clause" id="clause" value="" checked/>我已阅读<a href="http://cdn.bang5mai.com/upload/web/cmsphp/html/bwmstatement.htm" target="_blank">《帮我买代购声明》</a>并接收其条款</label>
	            </li>-->
	        </ul>
	    </div>
	    <div class="center-description">
	    	<ul>
		        <a href="http://www.b5m.com/bwm" target="_blank">
		        	<li class="centre-a">
		                <h3><b>帮5买</b>&nbsp;&nbsp;7年专业服务&nbsp;&nbsp;值得信赖</h3>
		            </li>
	            </a>
	             <a href="http://www.315online.com.cn/member/315120051.html" target="_blank">
		        	<li class="centre-b">
		                <h3>权威认证 放心购买</h3>
		            </li>
	            </a>
	        	<li class="centre-c">
	                <h3>优惠 低价</h3>
	                <p>1、支付一次运费，未来30天所有电商所有商品包邮 <a href="http://www.b5m.com/goldenvip.html" target="_blank">【详情】</a></p>
	                <p>2、下单送帮钻，帮钻可以无条件兑换所有电商所有商品 <a href="http://www.b5m.com/bangzuan" target="_blank">【详情】</a></p>
	                <p>3、下单立减1元</p>
	            </li>
	        	<li class="centre-d">
	                <h3>客服服务</h3>
	                <p><a onclick="ec_cs_fnDialogOK();return false;" href="javascript:void(0)">点我咨询</a>（周一~周日 早上9：30~晚上10：00）</p>
	                <p>400-0859-285（周一~周日 早上9：30~晚上10：00）</p>
	            </li>
	        </ul>
	    </div>
	    <div class="centre-details-container">
	   		<h3>商品详情</h3>
	   	</div>
	   	<dl class="centre-onload" id="detailLoad">
	        <span class="load-box"></span>
	    	<p>商品详情正在玩命加载中...</p>
	    </dl>
	    <div class="pd-img" id="shopInfo">
	    </div>
	</div>
	<script src="http://staticcdn.b5m.com/static/scripts/public/copyright.js" type="text/javascript"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/jquery-1.9.1.min.js"></script>
	<script id="b5m-libs" type="text/javascript" src="http://y.b5mcdn.com/static/public/sea-modules/dist/libs.js" rootPath="http://staticcdn.b5m.com/static/public/sea-modules/dist" version="20150421094218"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/common.js?t=${today}"></script>
	<script type="text/javascript" src="http://cdn.bang5mai.com/upload/web/public/app/tongji/stat2.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/cart-cater.js?t=${today}"></script>
	<%-- <script type="text/javascript" src="${basePath}js/daigou/cart-cater.js"></script> --%>
	<script type="text/javascript">
		$(function() {
			var cartCenter = new CartCenter({
				docId : '${product.DOCID}',
				url : '${paramurl}' || '${product.Url}',
				priceAvg : '${priceAvg}',
				ref : '${ref}',
				paramurl : '${paramurl}',
				origin : '${origin}',
				subPrice : '${subPrice}',
				key : '${memKey}',
				b5tPrice : '${b5tPrice}',
				title: encodeURIComponent('${product.Title}'),
				imgPath: encodeURIComponent('${product.Picture}'),
			});
			cartCenter.init();
			var key = "${category != null ? category:product.Title}";
			$(".btn-gray").attr("href", "http://s.b5m.com/s/Search?key=" + encodeURIComponent(key));
		});
	</script>
</body>
</html>