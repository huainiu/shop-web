<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_no_result.css?t=${today}">
</head>
<body>
	<%@ include file="common/search.jsp" %>
	<div class="wp">
		<div class="breadcrumb-no-404">
			<i class="icon-exclamation"></i><span class="tips">抱歉，没有找到“<strong>${keyword }</strong>”相关结果！</span>
		</div>
		<div id="J_prod_list"></div>
	</div>
	<%@ include file="common/footer.jsp"%>
	<%@ include file="snippet/recommendProduct.jsp"%>
<%-- <script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/??handlebars.js,search_no_result_404.js?t=${today}"></script> --%>
<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/handlebars.js?t=${today}"></script>
<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/search_no_result_404.js?t=${today}"></script>
</body>

</html>