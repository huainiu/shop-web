<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="utf-8">
<title>管理后台--登陆</title>
<c:import url="../common/meta.jsp"/>
<link type="text/css" href="${basePath}css/base.css?t=${today}"
	rel="stylesheet" />
<link type="text/css" href="${basePath}css/constyle.css?t=${today}"
	rel="stylesheet" />
<script type="text/javascript"
	src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript">
	function getLogin() {
		if ($('#username').val() == '' || $('#password').val() == '') {
			$('#message').show();
			$('#message p').text('用户名或密码不能为空'); 
			return false;
		}
		$('#user').submit();
	}
	$(function() {
		document.onkeydown = function(e){
			var keycode;
			//var realkey;
			var ev = document.all ? window.event : e;    
			keycode = ev.keyCode;
			//alert("按键码: " + keycode + " 字符: " + realkey);
			if (keycode == 13) {
				getLogin();
			}
		}
	});
	
	$(document).ready(function() {
		if ('' == 'timeout')
			alert("session超时，请重新登陆！");
		$("#username").focus();
		
		if($('#message-data').val()){
 			$('#message').show();
			$('#message p').text($('#message-data').val()); 
		}
		
	});
	
</script>
</head>

<body
	style="background: url(${basePath}images/backstage/logbg.jpg) repeat;">
	<div class="loginpage">
		<div class="logincon">
			<div class="loglogo">b5m.com-Manage Tool</div>
			<div class="logform">
				<h3>欢迎登录 B5M Manage Tool</h3>
				<input type="hidden" value="${message }" id="message-data">
				<form method="post" action="/backmanager/user/login.htm" id="user">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><em>用户名：</em></td>
							<td><input name="username" type="text" class="loinptext"
								id="username" /></td>
						</tr>
						<tr>
							<td><em>密码：</em></td>
							<td><input name="password" type="password" class="loinptext"
								id="password" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><input type="button" class="b5mgbg losubbtn"
								value="Sign up" onclick="getLogin()" /></td>
						</tr>
						<tr id="message" style="display: none;">
							<td>&nbsp;</td>
							<td><p class="smalfont" style="color: red;"></p></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
