<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ page session="false"%>
<div class="wp" id="detail-info-head">
	<div class="ly-c cf">
		<div class="ly-m l">
			<div class="ly-content">
				<h3 class="prod-title" id="goods-title">${res.Title}</h3>
				<div class="prod-duihuan">
					<fmt:formatNumber var="beans" value="${res.Price * 100}" pattern="#" />
					<fmt:formatNumber var="getbeans" value="${res.Price}" pattern="#" />
					<p>
						兑换该商品需要<span>${beans }</span>帮钻<a href="http://www.b5m.com/bangdou" target="_blank" class="getBangzuan">去赚帮钻<i class="icon-bangzuan"></i></a><br>购买该商品可获得<span>${getbeans }</span>帮钻
					</p>
				</div>
				<div class="prod-average-price">
					<p>
						全网均价：<span class="definite-price"><i>¥</i>${highPrice }</span>
					</p>
				</div>
				<div class="prod-ratio">
					<dl>
						<c:if test="${subDocs != null}">
							<dt>更多比价：</dt>
							<c:forEach items="${subDocs }" var="item" begin="0" end="2">
								<dd>
									<strong>${item.Source }：</strong><a href="${orgbasePath}item/${item.DOCID}.html" title="${item.Title }" target="_blank">${item.Title }</a><span><i>¥</i>${item.Price }</span>
								</dd>
							</c:forEach>
						</c:if>
						<c:if test="${recommand != null}">
							<dt>同类推荐：</dt>
							<c:forEach items="${recommand }" var="item" begin="0" end="2">
								<dd>
									<strong>${item.Source }：</strong><a href="${orgbasePath}item/${item.DOCID}.html" title="${item.Title }" target="_blank">${item.Title }</a><span><i>¥</i>${item.Price }</span>
								</dd>
							</c:forEach>
						</c:if>
					</dl>
				</div>
				<div class="prod-b5m-price ">
					<div class="b5m-price">
						<div class="b5m-price-low">
							<fmt:formatNumber var="productPrice" value="${res.Price}" pattern="#0.00" />
							<c:if test="${productPrice > 5.0}">
								<fmt:formatNumber var="productPrice" value="${productPrice - 0.5}" pattern="#0.00" />
							</c:if>
							单品价格：<span><i>¥</i>${productPrice}</span>
						</div>
						<div class="b5m-price-trend">
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
					</div>
					<%-- <div class="cut-price l">
						<div class="cut-price-num">
							<fmt:formatNumber var="spread" value="${highPrice - productPrice}" pattern="#0.00" />
							帮您节省了：<span><i>¥</i>${spread}</span>
						</div>
					</div> --%>
				</div>
				<div class="prod-btns">
					<a href="javascript:;" class="btn btn-sold-out">商品已下架</a> <a href="${orgbasePath }s/Search?key=${res.Title}" class="what-is-b5m">浏览其他商品</a>
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
		</div>
		<div class="ly-r l" id="shop-recommend"></div>
	</div>
</div>