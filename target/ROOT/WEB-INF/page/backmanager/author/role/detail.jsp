<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../../common/meta.jsp" />
<title>角色操作</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript" src="${basePath}js/js/Jquery.Query.js?t=${today}"></script>
<style type="text/css">
ul {
	padding: 10px;
}

ul li {
	float: left;
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
						<li class="cur">角色管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<c:if test="${role.id != null}">
							<h3>角色编辑</h3>
						</c:if>
						<c:if test="${role.id == null}">
							<h3>角色添加</h3>
						</c:if>
						<input type="hidden" id="id" name="id" value="${role.id}">
						<table width="800px;" class="user-table">
							<tr>
								<td colspan="2" style="text-align: right;">
									<input type="button" class="button" id="backManage" value="返回" style="margin-right: 10px;">
								</td>
							</tr>
							<tr>
								<th>角色名:</th>
								<td style="text-align: left;">
									<input type="text" id="name" name="name" class="input" style="margin-left: 20px;" value="${role.name}">
								</td>
							</tr>
							<tr>
								<th>权限分配:</th>
								<td style="text-align: left; padding: 0 10px;">
									<c:forEach items="${resourceList}" var="map">
										<div style="width: 100%; border-bottom: 1px dashed black; font-weight: bold;">
											${map['res'].name}:<input type="checkbox" name="checkbox" id="${map['res'].id }">
										</div>
										<c:if test="${fn:length(map['list']) > 0}">
											<div>
												<ul>
													<c:forEach items="${map['list']}" var="res">
														<li style="width: 25%;">${res.name }:<input type="checkbox" name="checkbox" pid="${map['res'].id }" id='${res.id}'></li>
													</c:forEach>
												</ul>
												<div style="clear: both;"></div>
											</div>
										</c:if>
									</c:forEach>
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
	<script type="text/javascript" src="${basePath }js/js/backstage.js?t=${today}"></script>
	<!--wrap e-->
	<!--footer s-->
	<div id="footer"></div>
	<!--footer e-->
	<script type="text/javascript">
		$('#role-manager').addClass('cur');
		$(function() {
			var resIdList = eval('${resIdList}');
			//初始化checkbox
			if (resIdList) {
				$.each(resIdList, function(i, id) {
					var input = $("#" + id);
					if (input) {
						input.prop("checked", true);
					}
				});
			}
			//返回
			$("#backManage").click(function() {
				var page = $.query.get('page');
				var url = "${basePath}backmanager/role.htm";
				if (page != null) {
					url = url + "?currentPage=" + page;
				}
				window.location.href = url;
			});
			//父子级联动
			$('input[name="checkbox"]').click(function() {
				var pid = $(this).attr("pid");
				if (pid) { //子节点
					if ($(this).is(':checked') == true && $("#" + pid).is(":checked") == false) {
						$("#" + pid).prop("checked", true);
					}
				} else { //父节点
					var id = $(this).attr("id");
					if ($(this).is(':checked') == false) {
						$("input[pid=" + id + "]").each(function() {
							$(this).removeAttr("checked");
						});
					}
				}
			});
			//提交
			$("#submit").click(function() {
				var id = $("#id").val();
				var url = "";
				if (!id) {
					url = "${basePath}backmanager/role/add.htm";
				} else {
					url = "${basePath}backmanager/role/update.htm";
				}
				var name = $("#name").val();
				if (!name) {
					alert("角色名不能为空");
					return;
				}
				var resources = "";
				var checked = $("input[name='checkbox']:checked");
				$.each(checked, function(i, item) {
					resources = resources + item.id + ",";
				});
				var param = {
					name : name,
					id : id,
					resources : resources
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