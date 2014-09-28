<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../../common/meta.jsp"/>
<title>资源管理</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script language="javascript" type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js?t=${today}"></script>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript">
$(function(){
	$("#add").click(function(){
		window.location.href = "${basePath}backmanager/resource/detail.htm?page="+${pageView.currentPage};
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
						<li class="cur">资源管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<table width="100%" class="user-table">
							<tr>
								<th>操作:</th>
								<td style="text-align: right;">
									<input type="button" value="添加新资源" id="add" name="add" class="button" style="margin-right: 10px;">
								</td>
							</tr>
						</table>
						<table width="100%" class="user-table">
							<thead>
								<tr>
									<th >ID</th>
									<th >资源名称</th>
									<th >是否是叶节点</th>
									<th >是否可显示</th>
									<th >父节点</th>
									<th >path</th>
									<th >序号</th>
									<th width="200">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageView.records}" var="resource">
									<tr id="${resource.id}">
										<td width="15%">${resource.id }</td>
										<td width="15%">${resource.name }</td>
										<td >${resource.isLeaf == 1 ? "是" : "否" }</td>
										<td >${resource.isView == 1 ? "是" : "否" }</td>
										<td >${resource.parent.name }</td>
										<td >${resource.path }</td>
										<td >${resource.seq }</td>
										<td width="20%">
											<input value="编辑" class="button" type="button"
												onclick="javascript:window.location.href='${basePath}backmanager/resource/detail.htm?id=${resource.id }&page='+${pageView.currentPage}">
											<input value="删除" class="button" type="button" onclick="delresource(${resource.id})">
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!--page s-->
						<c:import url="../../common/splitpage.jsp"/>
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
		$('#resource-manage').addClass('cur');
		function delresource(id) {
			var url = "${basePath}backmanager/resource/delete.htm";
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
			window.location.href = "${basePath}backmanager/resource.htm?currentPage=" + page;	
		}
	</script>
</body>
</html>