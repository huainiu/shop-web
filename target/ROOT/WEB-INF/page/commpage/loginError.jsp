<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		if (request.getAttribute("back_manage_user") != null) {
	%>
		<script type="text/javascript">
			location.href = "/backmanager/impress.htm";
		</script>
	<%		
		} else {
	%>
		<div align="center" style="width: 500px; height: 300; border: 1">
			用户名或密码错误，请<a href="/backLogin.jsp">重新登录</a>
		</div>
	<%
		}
	%>
</body>
</html>