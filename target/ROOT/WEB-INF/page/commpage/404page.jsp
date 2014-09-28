<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<c:import url="/WEB-INF/page/common/meta.jsp" />
<base href="${basePath}" />
<title>帮5买 - 轻松发现， 轻松选择！</title>
<meta name="title" content="帮5买 - 轻松发现， 轻松选择！" />
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_no_result.css?t=${today}">
</head>
<body>
	<%@ include file="/WEB-INF/page/common/search.jsp"%>
	<div class="wp">
		<div class="breadcrumb-no-404">
			<i class="icon-exclamation"></i><span class="tips">抱歉，好像出了点问题。<strong id="J_coutDown">10</strong> 秒后带您回到<a href="http://www.b5m.com" target="_blank" class="">首页</a>。
			</span>
		</div>
		<div id="J_prod_list"></div>
	</div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	<%@ include file="/WEB-INF/page/snippet/recommendProduct.jsp"%>
	<script type="text/javascript">
		$(function() {
			countDown(10, "http://www.b5m.com");
		});
		function countDown(secs, surl) {
			$("#J_coutDown").text(secs);
			if (--secs > 0) {
				setTimeout("countDown(" + secs + ",'" + surl + "')", 1000);
			} else {
				location.href = surl;
			}
		}
	</script>
<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/handlebars.js?t=${today}"></script>
<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/search_no_result_404.js?t=${today}"></script>
</body>
</html>