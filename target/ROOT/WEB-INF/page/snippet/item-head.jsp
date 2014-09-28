<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ page session="false"%>
<div class="wp" id="detail-info-head">
	<div class="ly-c cf">
		<div class="ly-m l">
			<div class="ly-content loading-tip" style="height: 300.5px; padding-top: 210.5px;" id="load-div">
				<img src="http://y.b5mcdn.com/images/search/5-121204194026.gif" alt=""> <br> <span>商品信息正在在玩命加载中…</span>
			</div>
			<div class="ly-content" style="display: none;">
				<h3 class="prod-title" id="goods-title">${res.Title}</h3>
				<fmt:formatNumber var="beans" value="${res.Price * 100}" pattern="#" />
				<fmt:formatNumber var="getbeans" value="${res.Price}" pattern="#" />
				<div class="prod-sell-info">
					<div class="prod-sell-all">
						<strong class="prod-num">${empty res.SalesAmount || res.SalesAmount < 0? 0 : res.SalesAmount}</strong><span>累计销量</span>
					</div>
					<div class="prod-sell-bangzuan">
						<strong class="prod-num getbeans">${getbeans }</strong><span>买就送帮钻</span>
					</div>
					<div class="prod-sell-comment">
						<strong class="prod-num">${empty res.CommentCount || res.CommentCount < 0? 0 : res.CommentCount}</strong><span>评论</span>
					</div>
				</div>
				<div class="prod-sku-info centrt-sku">
					<p class="info-tips" style="display: none;">
						<em class="icon-warning-red"></em>请选择你要的商品信息
					</p>
					<dl class="">
						<dt id="buy-count-name">数量:</dt>
						<dd class="centre-details-quantity">
							<span data-id="005" data-cid="C_002" class="btn-subtraction">-</span><span class="input-box"><input id="quantity005" name="quantity005" type="text" value="1" class="p-text" disabled="disabled"></span><span data-id="005" data-cid="C_002" class="btn-add">+</span><font> 件</font>
						</dd>
					</dl>
				</div>
				<div class="prod-b5m-price">
					<div class="b5m-price">
						<div class="b5m-price-low" id="price-txt">
							<fmt:formatNumber var="productPrice" value="${res.Price}" pattern="#0.00" />
							<c:if test="${productPrice > 5.0}">
								<fmt:formatNumber var="productPrice" value="${productPrice - 0.5}" pattern="#0.00" />
							</c:if>
							<p>
								帮我买价：<span><i>¥</i><span id="productPrice">${productPrice}</span></span>
							</p>
							<p>
								全网均价：
								<del>
									¥
									<df id="high-price">${highPrice }</df>
								</del>
							</p>
						</div>
						<div class="b5m-price-trend">
							<span class="price-trend r icon-down" style="display: none;" id="price-trend--1"><i class="trend"></i>近期下降<i class="down-circle"></i></span> <span class="price-trend r icon-flat" style="display: none;" id="price-trend-0"><i class="trend"></i>近期平稳<i class="down-circle"></i></span> <span class="price-trend r icon-up" style="display: none;" id="price-trend-1"><i class="trend"></i>近期上升<i class="down-circle"></i></span>
							<div id="mini-priceTrend-view" style="display: none; z-index: 10000; top: 44px; left: -90px; position: absolute"></div>
							<c:if test="${res.Source != '淘宝网'}">
								<span class="icon-zhengpin"><i>正</i>正品保证</span>
							</c:if>
							<c:if test="${res.isLowCompPrice == '1'}">
								<span class="icon-dijia"><i>低</i>全网低价</span>
							</c:if>
							<c:if test="${res.Source == '淘宝网' && res.isLowCompPrice != '1'}">
								<span class="icon-tuikua"><i>退</i>下单后涨价，即退还所付现金</span>
							</c:if>
						</div>
						<div class="b5m-price-buy">
							<a href="${basePath}exchange/item.htm?docId=${res.DOCID}&c=${col}" class="bangzuanduihuan" target="_blank" data-attr="102002">帮钻兑换</a> <a href="${gotoUrl}" target="_blank" data-attr="100806" class="zixinggoumai">自行购买&gt;</a>
						</div>
					</div>
				</div>
				<div class="prod-btns">
					<a href="javascript:void(0);" class="btn btn-b5m-buy bodyopen" id="goBuy"><i></i>帮我买</a> <a href="javascript:void(0);" class="btn btn-shop-cart"><i></i>加入购物车</a> <span class="add-cart-success" style="display: none;"> <a href="http://cart.b5m.com" class="go-to-cart">去购物车结算</a> <a href="javascript:void(0);" class="go-shoping">继续购物</a><i class="cart-close"></i></span>
					<div class="erweima">
						<div class="erweima-phone">
							<p>手机端购买</p>
							<span>立减2元</span>
						</div>
						<div class="erweima-pic">
							<img src="http://app.b5m.com/images/recode.jpg" alt="">
						</div>
					</div>
					<p>
						<span>
							<a href="http://www.b5m.com/bwm" target="_blank" class="what-is-b5m">什么是帮我买？</a>
							<a class="what-is-b5m" style="width: 250px;font-size: 16px;font-weight: bold;margin-left: -66px;">（客服电话：400-0859-285）</a>
						</span>
					</p>
				</div>
			</div>
		</div>
		<div class="ly-l l">
			<div class="main-slider">
				<div class="main-slider-pic">
					<c:set var="pics" value="${res.OriginalPicture }"></c:set>
					<c:if test="${res.Source =='易迅网'}">
						<c:set var="pics" value="${res.Picture }"></c:set>
					</c:if>
					<img src="${fn:split(pics, ',')[0]}" alt="${res.Title}" onerror="this.src='${res.Picture}'">
				</div>
				<div class="slider-bar cf mini-slider">
					<ul class="cf ">
						<c:forEach items="${fn:split(pics, ',') }" var="item">
							<li><span><img src="${item}" alt=""></span></li>
						</c:forEach>
					</ul>
					<c:if test="${fn:length(fn:split(pics, ',')) > 5}">
						<a href="javascript:void(0);" class="slider-trigger slider-left arrow-left-disable"></a>
						<a href="javascript:void(0);" class="slider-trigger slider-right"></a>
					</c:if>
				</div>
			</div>
			<div class="share-opt l">
				<span class="l">分享给朋友:</span>
				<div class="bshare-custom icon-medium l" id="b5m-share"></div>
			</div>
			<div class="prod-remind">
				<span class="remind-me" id="J_count_down" data-url="${orgbasePath}item/${res.DOCID}.html"><i></i>降价提醒我</span>
			</div>
		</div>
		<div class="ly-r l" id="shop-recommend">
			<c:if test="${not empty subDocs}">
				<div class="recommend-prod">
					<h3>全网比价</h3>
					<ul class="recommend-prod-list" id="J_prod_slider">
						<c:forEach items="${subDocs }" var="item">
							<c:set var="pic" value="${fn:replace(item.Picture, 'img.b5m.com', 'tfs01.b5mcdn.com') }"></c:set>
							<c:if test="${fn:contains(pic, 'tfs01')}">
								<c:set var="pic" value="${pic }/120x120"></c:set>
							</c:if>
							<li><a href="${orgbasePath}item/${item.DOCID}.html" target="_blank"><i class="pic-center"><img src="${pic }" data-src="${pic }" onerror="this.src='${fn:split(item.OriginalPicture, ',')[0] }'" alt=""></i><span>${item.Price }</span></a></li>
						</c:forEach>
					</ul>
					<div class="trigger-button" id="J_trigger">
						<span class="arrow-prod arrow-up arrow-up-disable">上</span><span class="arrow-prod arrow-down">下</span>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>