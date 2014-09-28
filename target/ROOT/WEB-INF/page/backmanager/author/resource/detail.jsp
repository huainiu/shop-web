<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../../common/meta.jsp" />
<title>资源操作</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript" src="${basePath}js/js/Jquery.Query.js?t=${today}"></script>
<script type="text/javascript">
	
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
						<li class="cur">资源管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<c:if test="${resource.id != null}">
							<h3>资源编辑</h3>
						</c:if>
						<c:if test="${resource.id == null}">
							<h3>资源添加</h3>
						</c:if>
						<input type="hidden" id="id" name="id" value="${resource.id}">
						<table width="600px;" class="user-table">
							<tr>
								<td colspan="2" style="text-align: right;">
									<input type="button" class="button" id="backManage" value="返回" style="margin-right: 10px;">
								</td>
							</tr>
							<tr>
								<th>权限名称:</th>
								<td style="text-align: left;">
									<input type="text" id="name" name="name" class="input" style="margin-left: 20px;" value="${resource.name}">
								</td>
							</tr>
							<tr>
								<th>完整路径(不包括域名,从backmanager后的斜杠开始):</th> 
								<td style="text-align: left;">
									<input type="text" id="path" name="path" class="input" style="margin-left: 20px;" value="${resource.path}">
								</td>
							</tr>
							<tr>
								<th>是否显示:</th>
								<td style="text-align: left;">
									<select class="select" name="view" id="view" style="margin-left: 20px;width: 100px;">
										<option value="1" selected="selected">是</option>
										<option value="0">否</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>是否是叶节点:</th>
								<td style="text-align: left;">
									<select class="select" name="leaf" id="leaf" style="margin-left: 20px;width: 100px;">
										<option value="1" selected="selected">是</option>
										<option value="0">否</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>父节点:</th>
								<td style="text-align: left;">
									<b5m:select list="${fList}" value="${resource.parentId}" id="parent" name="parent" noEmpty="true" styleClass="select"
										style="margin-left: 20px;width: 100px;" valueProperty="id" viewProperty="name"/>
								</td>
							</tr>
							<tr>
								<th>顺序（数字越大越在上面,展示使用）:</th>
								<td style="text-align: left;">
									<input type="text" id="seq" name="seq" class="input" style="margin-left: 20px;" value="${resource.seq}">
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
		$('#resouce-manager').addClass('cur');
		var view = eval('${resource.isView}');
		var leaf = eval('${resource.isLeaf}');
		var value = eval('${resource.parentId}');
		$(function() {
			//初始化
			 $("#view").val(view);
			 $("#leaf").val(leaf);
			 if (!value) {
				$('#parent').val(0); 
			 }
			//返回
			$("#backManage").click(function() {
				var page = $.query.get('page');
				var url = "${basePath}backmanager/resource.htm";
				if (page != null) {
					url = url + "?currentPage=" + page;
				}
				window.location.href = url;
			});
			//提交
			$("#submit").click(function() {
				var id = $("#id").val();
				var url = "";
				if (!id) {
					url = "${basePath}backmanager/resource/add.htm";
				} else {
					url = "${basePath}backmanager/resource/update.htm";
				}
				var name = $("#name").val();
				var path = $("#path").val();
				var view = $("#view").val();
				var leaf = $("#leaf").val();
				var parentId = $("#parent").val();
				var seq = $("#seq").val();
				if (!name) {
					alert("角色名不能为空");
					return;
				} 
				var param = {
					name : name,
					path : path,
					isView : view,
					isLeaf : leaf,
					parentId : parentId,
					seq : seq,
					id : id
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