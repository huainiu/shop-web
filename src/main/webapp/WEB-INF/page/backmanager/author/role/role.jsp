<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../../common/meta.jsp" />
<title>资源角色</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script language="javascript" type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js?t=${today}"></script>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript">
$(function(){
	$("#add").click(function(){
		window.location.href = "${basePath}backmanager/role/detail.htm?page="+${pageView.currentPage};
	});
})
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
						<li class="cur">资源角色</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<table width="100%" class="user-table">
							<tr>
								<th>操作:</th>
								<td style="text-align: right;">
									<input type="button" value="添加新角色" id="add" name="add" class="button" style="margin-right: 10px;">
								</td>
							</tr>
						</table>
						<table width="100%" class="user-table">
							<thead>
								<tr>
									<th width="50">ID</th>
									<th width="50">角色名称</th>
									<th width="100">权限列表</th>
									<th width="200">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageView.records}" var="role">
									<tr id="${role.id}">
										<td width="15%">${role.id }</td>
										<td width="15%">${role.name }</td>
										<td style="height: 30px;">
											<c:forEach items="${role.archList}" var="map">
												<div style=" width: 70%;word-wrap: break-word;word-break: normal;  overflow: hidden; text-align: center;margin-left:auto; margin-right:auto;">
													${map['res'].name}&nbsp;[
													<c:forEach items="${map['list']}" var="res">
													${res.name }
												</c:forEach>
													]&nbsp;<br>
												</div>
											</c:forEach>
											<div style="clear: both;"></div>
										</td>
										<td width="20%">
											<input value="编辑" class="button" type="button"
												onclick="javascript:window.location.href='${basePath}backmanager/role/detail.htm?id=${role.id }&page='+${pageView.currentPage}">
											<input value="删除" class="button" type="button" onclick="delrole(${role.id})">
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!--page s-->
						<c:import url="../../common/splitpage.jsp" />
						<!--page e-->
					</div>
					<!--用户列表 e-->
				</div>
			</div>
		</div>
		<c:import url="../../common/left.jsp"></c:import>
	</div>
	<!--wrap e-->
	<!--footer s-->
	<div id="footer"></div>
	<!--footer e-->
	<script type="text/javascript" src="${basePath }/js/js/backstage.js?t=${today}"></script>
	<script type="text/javascript">
		$('#role-manage').addClass('cur');
		function delrole(id) {
			var url = "${basePath}backmanager/role/delete.htm";
			var param = {
				id : id,
			};
			$.ajax({
				url : url,
				type : 'POST',
				data : param,
				dataType : 'json',
				success : function(result) {
					$("#" + id).remove();
					alert(result.msg);
				}
			});
		}
		
		function gotoPage(page) {
			window.location.href = "${basePath}backmanager/role.htm?currentPage=" + page;	
		}
	</script>
</body>
</html>