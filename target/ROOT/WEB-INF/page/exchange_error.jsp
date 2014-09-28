<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html>
	<head>
	<base href="${basePath}" />
	<%@ include file="./common/meta.jsp"%>
	<title>${keyword}搜索- 帮5买 - 轻松发现，轻松选择！</title>
	<meta name="robots" content="index, follow" />
	<meta name="googlebot" content="index, follow" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="image/x-icon" rel="shortcut icon" href="${basePath}favicon.ico" mce_href="${basePath}favicon.ico" />
	<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_result.css?t=${today}" />
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
	<script type="text/javascript">
		var _basePath = "${basePath}";
		var _server = "${server}";
	</script>
	</head>
	<body>
		<!--public-bar s-->
		<%@ include file="./common/search.jsp" %>
		<div class="flex clear-fix">
			<div class="search-tips">
				<div class="search-tips-in">
					<h4>抱歉!<br>您选择的商品暂时不支持帮钻兑换</h4><br>
					<p> 建议您：<br> 选择其他商品或者稍后再进行兑换<br><br></p>
				</div>
			</div>
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
		<%@ include file="./common/footer.jsp" %>
		<%-- <c:import url="common/footer.jsp"></c:import> --%>
		<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/tao-shop.min.js?t=${today}"></script>
		<!--footer e-->
		<script type="text/javascript">
			$(function(){
				var history = JSON.parse(localStorage.getItem("view-history"));
				//获取相关商品
				var url = "${basePath}relateGoods.htm?t=" + new Date().getTime();
				var data = "";
				if(history){
					//没有历史数据
					data += history.reverse();
				}
				$.post(url, {docIds:data}, function(result){
					if(result.length > 0){
						getTaoGoods(result[0].Category, 8);
					}
				});
			});
		</script>
		<!--[if lte IE 9]>
		<script src="http://y.b5mcdn.com/scripts/search/media_query.js?t=${today}"></script>
		<![endif]-->
	</body>
</html>