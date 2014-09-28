<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp" />
<title>资讯操作</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript" src="${basePath}js/js/Jquery.Query.js?t=${today}"></script>
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
						<li class="cur">链接管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<c:if test="${bean.id != null}">
							<h3>链接编辑</h3>
						</c:if>
						<c:if test="${bean.id == null}">
							<h3>链接添加</h3>
						</c:if>
						<input type="hidden" id="id" name="id" value="${bean.id}">
						<table class="user-table">
							<tr>
								<td colspan="2" style="text-align: right;">
									<input type="button" class="button" id="backManage" value="返回" style="margin-right: 10px;">
								</td>
							</tr>
							<tr>
								<th style="width: 200px;">功能名称:</th>
								<td style="text-align: center;">
									<c:if test="${bean.id == null}">
										<input type="text" id="name" name="name" class="input" style="width: 80%;" value="${bean.name}">
									</c:if>
									<c:if test="${bean.id != null}">
										<input type="text" id="name" name="name" class="input" style="width: 80%;" value="${bean.name}" disabled="disabled">
									</c:if>
								</td>
							</tr>
							<tr>
								<th style="width: 200px;">链接:</th>
								<td style="text-align: center;">
									<input type="text" id="link" name="link" class="input" style="width: 80%;" value="${bean.link}">
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
	<script type="text/javascript" src="${basePath }js/js/backstage.js?t=${today}"></script>
	<!--wrap e-->
	<!--footer s-->
	<div id="footer"></div>
	<!--footer e-->
	<script type="text/javascript">
		$('#link-manager').addClass('cur');
		$(function() {
			//返回
			$("#backManage").click(function() {
				var url = "${basePath}backmanager/link/links.htm" + $.query.REMOVE("id");
				window.location.href = url;
			});
			//提交
			$("#submit").click(function() {
				var id = $("#id").val();
				var url = "${basePath}backmanager/link/save.htm";
				var name = $("#name").val();
				var link = $("#link").val();
				if (!name) {
					alert("名称不能为空");
					return;
				}
				if (!link) {
					alert("链接不能为空");
					return;
				}
				var param = {
					name : name,
					link : link,
					id : id
				};
				$.ajax({
					url : url,
					type : 'POST',
					data : param,
					dataType : 'json',
					success : function(result) {
						alert(result.msg);
						location.href = "${basePath}backmanager/link/edit.htm"+$.query.set("id",result.val);
					}
				});
			});
		});
	</script>
</body>
</html>