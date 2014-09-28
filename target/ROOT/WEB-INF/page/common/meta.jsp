<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%
	String server = request.getServerName();
	int port = request.getServerPort();
	if (port == 80) {
		String key = "s";
		if (server.contains("stage") || server.contains("prod"))
			key = "search";
		server = server.replaceFirst("[^.]*", key);
	}
	StringBuilder sb = new StringBuilder();
	StringBuilder sb2 = new StringBuilder();
	sb.append(request.getScheme()).append("://").append(server)
			.append(port == 80 ? "" : ":" + port)
			.append(request.getContextPath()).append("/");
	sb2.append(request.getScheme()).append("://")
			.append(request.getServerName())
			.append(port == 80 ? "" : ":" + port)
			.append(request.getContextPath()).append("/");
	request.setAttribute("basePath", sb.toString());
	request.setAttribute("orgbasePath", sb2.toString());
	request.setAttribute("server", server);
%>
<link type="image/x-icon" rel="shortcut icon" href="${basePath}favicon.ico" mce_href="${basePath}favicon.ico" />
<link rel="stylesheet" href="http://y.b5mcdn.com/css/??common/common.css,search/search_head_v3.css?t=${today}" />
<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript">
	var _basePath = "${basePath}";
	var _orgBasePath = "${orgbasePath}";
	var _server = "${server}";
	var ucenterUrl = "${loginAndRegisterURL}";
</script>
<meta name="data-mps" content="${empty mps ? '1016' : mps}">