<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String server = request.getServerName();
	StringBuilder sb = new StringBuilder();
	sb.append(request.getScheme()).append("://").append(server).append(":").append(request.getServerPort()).append(request.getContextPath()).append("/");
	request.setAttribute("basePath", sb.toString());
	request.setAttribute("server", server);
%>
<meta name="robots" content="index, follow" />
<meta name="googlebot" content="index, follow" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="image/x-icon" rel="shortcut icon" href="${basePath}favicon.ico" mce_href="${basePath}favicon.ico">
<link rel="stylesheet" href="http://y.b5mcdn.com/css/common/common.css?t=${today}" />
<link rel="stylesheet" href="http://y.b5mcdn.com/css/www/www_v1.css?t=${today}">
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_result.css?t=${today}">
<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<link rel="stylesheet" href="http://y.b5mcdn.com/css/common/head_b.css?t=${today}" />
<script type="text/javascript">
var _basePath = "${basePath}";
var _server = "${server}";
</script>