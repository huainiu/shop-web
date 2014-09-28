<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../../common/meta.jsp" />
<title>用户操作</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}"></link>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript" src="${basePath}js/js/Jquery.Query.js?t=${today}"></script>
<style type="text/css">
ul {
	padding: 10px;
}

ul li {
	float: left;
	width: 100px;
}
</style>
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
						<li class="cur">用户管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<c:if test="${user.id != null}">
							<h3>用户编辑</h3>
						</c:if>
						<c:if test="${user.id == null}">
							<h3>用户添加</h3>
						</c:if>
						<input type="hidden" id="id" name="id" value="${user.id}">
						<table width="600px;" class="user-table">
							<tr>
								<td colspan="2" style="text-align: right;">
									<input type="button" class="button" id="backManage" value="返回" style="margin-right: 10px;">
								</td>
							</tr>
							<tr>
								<th>用户名:</th>
								<td style="text-align: left;">
									<input type="text" id="userName" name="userName" class="input" style="margin-left: 20px;" value="${user.userName}">
								</td>
							</tr>
							<tr>
								<th>邮箱:</th>
								<td style="text-align: left;">
									<input type="text" id="email" name="email" class="input" style="margin-left: 20px;" value="${user.email}">
								</td>
							</tr>
							<tr>
								<th>角色:</th>
								<td style="text-align: left;">
									<div>
										<ul id="roles">
										</ul>
										<div style="clear: both;"></div>
									</div>
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
		<c:import url="../../common/left.jsp"></c:import>
	</div>
	<script type="text/javascript" src="${basePath}js/js/backstage.js?t=${today}"></script>
	<!--wrap e-->
	<!--footer s-->
	<div id="footer"></div>
	<!--footer e-->
	<script>
		$('#user-manager').addClass('cur');
		$(function() {
			$("#backManage").click(function() {
				var page = $.query.get('page');
				var url = "${basePath}backmanager/user.htm";
				if (page != null) {
					url = url + "?currentPage=" + page;
				}
				window.location.href = url;
			});
			var userRoleList = eval('${userRoleList}');
			var roleList = eval('${roleList}');
			if (roleList) {
				$.each(roleList, function(i, role) {
					var b = false;
					if (userRoleList) {
						$.each(userRoleList, function(j, id) {
							if (id == role.id)
								b = true;
						});
					}
					$("#roles").append("<li>" + role.name + ":<input type='checkbox' class='role_checkbox' name='checkbox' id='" + role.id + "' value='" + role.id + "'></li>");
					if (b)
						$("#" + role.id).attr("checked", "true");
				});
			}
			$("#submit").click(function() {
				var id = $("#id").val();
				var url = "";
				if (!id) {
					url = "${basePath}backmanager/user/add.htm";
				} else {
					url = "${basePath}backmanager/user/update.htm";
				}
				var userName = $("#userName").val();
				if (!userName) {
					alert("用户名不能为空");
					return;
				}
				var email = $("#email").val();
				var roles = "";
				var checked = $("input[name='checkbox']:checked");
				$.each(checked, function(i, item) {
					roles = roles + item.value + ",";
				});
				var param = {
					userName : userName,
					email : email,
					id : id,
					roles : roles
				};
				$.ajax({
					url : url,
					type : 'POST',
					data : param,
					dataType : 'json',
					success : function(result) {
						alert(result.msg);
					}
				});
			});
		});
	</script>
</body>
</html>