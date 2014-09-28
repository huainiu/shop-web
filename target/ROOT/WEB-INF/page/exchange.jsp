<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="title" content="${produce.Title }订单提交-帮5买全网商品免费兑换">
<meta name="keywords" content="${produce.Title },免费,兑换,全网,商品,订单,提交,帮5买">
<meta name="description" content="${produce.Title }免费兑换订单提交页面，帮5买全网商品免费兑换计划www.b5m.com/bangdou。">
<%@ include file="./common/meta.jsp"%>
<title>兑换中心</title>
<link rel="stylesheet" href="http://y.b5mcdn.com/css/exchange/exchange.css?t=${today}" />
</head>
<body style="position: relative;">
	<%@ include file="./common/search.jsp"%>
	<%@ include file="./snippet/exchange_content.jsp"%>
	<script type="text/javascript">
		var cpro_id = "u1583444";
	</script>
	<script src="http://cpro.baidustatic.com/cpro/ui/f.js" type="text/javascript"></script>
	<script id="b5m-libs" type="text/javascript" src="http://y.b5mcdn.com/static/public/sea-modules/dist/libs.js" rootPath="http://y.b5mcdn.com/static/public/sea-modules/dist" version="${today}"></script>
	<%@ include file="./common/footer.jsp"%>
	<div class="elastic-layer">
		<a class="elastic-delet"></a>
		<p class="elastic-midd cfx">
			<span class="elast-mid-l"></span> <span class="elast-mid-r"><b>恭喜您，兑换成功</b>我们的客服人员会尽快处理敬请留意您的兑换订单！</span>
		</p>
		<a class="bt-green">确定</a>
		<div class="elastic-share">
			<h3>我的分享:</h3>
			<div class="b5m-share-mod"></div>
		</div>
	</div>
	<div class="elastic-layer-bg"></div>
</body>
</html>