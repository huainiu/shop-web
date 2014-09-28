<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%-- <c:import url="common/meta.jsp"/> --%>
<%@ include file="./common/meta.jsp"%>
<base href="${basePath}" />
<title>${keyword}搜索- 帮5买 - 轻松发现，轻松选择！</title>
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_result.css?t=${today}">
<link rel="stylesheet" type="text/css" href="http://y.b5mcdn.com/css/search/detail.css?t=${today}">
</head>
<body>
	<!--public-bar s-->
	<%@ include file="./common/search.jsp"%>
	<c:set var="_keyword" value="${fn:substringBefore(keyword, ' ')}"></c:set>
	<div class="flex clear-fix">
		<div style="float: left; margin-top: 2px">
			<script type="text/javascript">
				var cpro_id = "u1414092";
			</script>
			<script src="http://cpro.baidustatic.com/cpro/ui/c.js?t=${today}" type="text/javascript"></script>
		</div>
		<div class="search-tips" style="left: 30%;">
			<div class="search-tips-in">
				<h4>
					对不起!<br>您想找的商品已经不再销售
				</h4>
				<br>
				<p>
					<c:if test="${keyword != null && keyword != '' }">
						建议您：<br>继续搜索“<a href="${basePath}search/s/___image________________${keyword}.html" class="cl-eb7">${keyword}</a>” 或 
					</c:if>
					<a href="http://www.b5m.com" class="cl-eb7">返回首页</a><br> <br>
				</p>
			</div>
		</div>
		<div style="float: right; margin-top: 2px;">
			<script type="text/javascript">
				var cpro_id = "u1414094";
			</script>
			<script src="http://cpro.baidustatic.com/cpro/ui/c.js?t=${today}" type="text/javascript"></script>
		</div>
	</div>
	<div class="flex" id="div-recommend-title">
		<h3 class="recommend-title flex">推荐商品</h3>
		<ul class="grid-view recommend-view clear-fix" id="recommand-shop-view"></ul>
	</div>
	<div class="flex">
		<!--淘特价 s-->
		<div id="tao">
			<div class="inner">
				<div class="tao-hd">
					<a href="http://tejia.b5m.com/?utm_source=mainb5t&utm_medium=b5t&utm_campaign=tao_item" target="_blank">更多&gt;</a>
					<h2>淘特价</h2>
				</div>
				<div class="tao-bd"></div>
			</div>
		</div>
		<!--淘特价 e-->
	</div>
	<!--footer s-->
	<%@ include file="./common/footer.jsp"%>
	<script type="text/javascript" src="${basePath}js/js/tao-shop.${min}js"></script>
	<!--footer e-->
	<script type="text/javascript">
		$(function() {
			var history = JSON.parse(localStorage.getItem("view-history"));
			//获取相关商品
			var url = "${basePath}relateGoods.htm?t=" + new Date().getTime();
			var data = "";
			if (history) {
				//没有历史数据
				data += history.reverse();
			}
			$.post(url, {
				docIds : data
			}, function(result) {
				if (result.length > 0) {
					getTaoGoods(result[0].Category, 8);
				}
			});
			if ("${keyword}" == '') {
				$("#div-recommend-title").hide();
			} else {
				getRecommandProduces();
			}
		});

		function getRecommandProduces() {
			var url = _basePath + "goodsDetail/recommandProduces.htm?t=" + new Date().getTime();
			var data = {
				docId : "${docId}",
				title : "${keyword}",
				pageSize : 10
			};
			$.ajax({
				type : "POST",
				url : url,
				data : data,
				success : function(httpObj) {
					$('#recommand-shop-view').removeClass('ajax_loading');
					if (httpObj != "") {
						var html = "";
						var shopNum = 0;
						$.each(httpObj, function(i, item) {
							$.each(item.shopList, function(index, shop) {
								shopNum++;
								var dataRd2 = '';
								if (item.shopName == '天猫' || item.shopName == '淘宝网' || item.shopName == '卓越亚马逊' || item.shopName == '京东商城' || item.shopName == '当当网') {
									shop.Url = decodeURIComponent(shop.Url.substr(shop.Url.indexOf('url=') + 4));
									dataRd2 = 'data-rd=\"1\"';
								}
								shop.Url = _basePath + 'item/' + shop.DocId + '.html';
								var btnText = "去看看";
								if (shop.itemCount > 1) {
									shop.Url = _basePath + 'compare/' + shop.DocId + '.html';
									btnText = "去比价";
								}
								html += '<li class="grid-mod">' + '<div class="pic-wrap">' + '<a target="_blank" data-attr="1010" class="pic" href="' + shop.Url + '"' + dataRd2 + '>' + '<img src="' + shop.Picture + '" alt="">' + '</a>' + '</div>' + '<p><a target="_blank" class="des" href="' + shop.Url + '"' + dataRd2 + '>' + shop.Title + '</a></p>';
								if (shop.itemCount > 1) {
									var sourceCount = 1;
									if (shop.Source.indexOf(",") > 0) {
										sourceCount = shop.Source.split(",").length;
									}
									if (shop.price.indexOf("-") > 0) {
										shop.price = shop.price.split("-")[0];
									}
									html += '<div class="price"><div class="nums-goods"><em>' + sourceCount + '</em>家商城</div><strong><b>&yen;</b>' + shop.price + '</strong></div>';
								} else {
									html += '<p class="price"><strong><b>&yen;</b>' + shop.price + '</strong></p>';
								}
								html += '<div class="start clear-fix"><div class="nums-goods">' + (shop.itemCount > 1 ? '<em>' + shop.itemCount + '</em>个比价商品' : shop.Source) + '</div></div>' + '<div class="btn-wrap clear-fix"><a data-attr="1010" target="_blank" class="go-buy fl" href="' + shop.Url + '"' + dataRd2 + '>' + btnText + '</a></div>' + '</li>';
								if (shopNum == 6) {
									return false;
								}
							});
							if (shopNum == 6) {
								return false;
							}
						});
						var $recommandShopView = $('#recommand-shop-view');
						$recommandShopView.append(html);
					} else {
						$('#recommand-shop-view').parent().hide();
					}
				},
				error : function(xmlHttpRequest, textStatus, errorThrow) {
					$('#recommand-shop-view').parent().hide();
					$('#recommand-shop-view').removeClass('ajax_loading');
				}
			});
		}
	</script>
	<!--[if lte IE 9]>
		<script src="http://y.b5mcdn.com/scripts/search/media_query.js?t=${today}"></script>
		<![endif]-->
</body>
</html>