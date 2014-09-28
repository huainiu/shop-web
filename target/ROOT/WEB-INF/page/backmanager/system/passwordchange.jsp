<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp"/>
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript">
$(function(){
	$("#submit").click(function(){
		var password = $("#password").val();
		if(password.length < 6){
			$("#tapPassword").show();
			return;
		}else{
			$("#tapPassword").hide();
		}
		var passwordok = $("#passwordok").val();
		if(password != passwordok){
			$("#tapPasswordok").show();
		}else{
			$("#tapPasswordok").hide();
		}
		var url = "${basePath}backmanager/user/changepassword.htm";
		var param = {password:password};
		$.post(url, param,
	        function(httpObj){
	        	if(httpObj.flag){
	        		alert("密码修改成功");
	        	}else{
	        		alert("密码修改失败, 原因:" + httpObj.message);
	        	}
	     });
	});
});
</script>
</head>
<body>
	<!--header s-->
	<div id="header"></div>
	<!--header e-->
	<!--wrap s-->
	<div id="wrap" class="grid-s160 clear-fix">
		<div class="col-main">
			<div class="wrap-main">
				<div class="user" id="user">
					<ul class="user-tab">
						<li class="cur">系统管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<h3>密码修改</h3>
						<table width="600px;" class="user-table">
							<tr>
								<th style="width:200px;text-align: right;">新密码:</th>
								<td style="text-align: left;">
								    <input type="password" id="password" name="password" class="input" style="margin-left: 20px;">
								    <span style="color: #FF6600;display: none;" id="tapPassword">密码不能小于6位</span>
								</td>
							</tr>
							<tr>
								<th style="text-align: right;">确认密码:</th>
								<td style="text-align: left;">
								    <input type="password" id="passwordok" name="passwordok" class="input" style="margin-left: 20px;">
								    <span style="color: #FF6600;display: none;" id="tapPasswordok">密码不匹配</span>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="button" id="submit" class="button" value="提交">
								</td>
							</tr>
						</table>
					</div>
					<!--用户列表 e-->
				</div>
			</div>
		</div>
		<c:import url="../common/left.jsp"></c:import>
	</div>
	<!--wrap e-->
	<!--footer s-->
	<div id="footer"></div>
	<!--footer e-->
	<script type="text/javascript" src="${basePath }/js/js/backstage.js?t=${today}"></script>
	<script type="text/javascript">
	$('#system-passwordchange').addClass('cur');
	</script>
</body>
</html>