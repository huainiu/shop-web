<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<c:import url="../../common/meta.jsp"/>
<title>商家管理</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script src="${basePath}/js/js/jquery-1.9.0.min.js?t=${today}"></script>
<script type="text/javascript">
function gotoPage(currentPage){
	var url = "${basePath}backmanager/supplier/manage/page.htm?currentPage=" + currentPage;
	window.location.href = url;
} 
function delSupplier(id){
	if(confirm("确定要删除嘛?")){
		$.ajax({
			type: "POST",
	        url: "${basePath}backmanager/supplier/manage/del.htm?id=" + id,
	        data: "",
	        dataType:"json",
	        success: function(httpObj){
	        	if(httpObj.flag){
	        		alert("删除成功");
	        	}else{
	        		alert("删除失败");
	        	}
	        	gotoPage(1);
	        }
		});
	}
}
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
						<li class="cur">商家列表</li>
					</ul>
					<!--用户列表 s-->
					<table width="100%" class="user-table">
						<tr>
							<th>操作:</th>
							<td style="text-align: right;">
								<input type="button" value="添加商家" onclick="javascript:window.location.href='${basePath}backmanager/supplier/detail.htm';" class="button" style="margin-right: 10px;">
							</td>
						</tr>
					</table>
					<div class="user-box">
						<table width="100%" class="user-table">
							<thead>
								<tr>
									<th width="10%">编号</th>
									<th width="20%">商家名称</th>
									<th width="30%">链接地址</th>
									<th width="20%">创建时间</th>
									<th width="20%">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageView.records}" var="supplier">
								<tr>
									<td>${supplier.id }</td>
									<td>${supplier.name }</td>
									<td>${supplier.url }</td>
									<td>${supplier.createTime }</td>
									<td>
										<input value="编辑" class="button" type="button" onclick="javascript:window.location.href='${basePath}backmanager/supplier/detail.htm?id=${supplier.id }';">
										<input value="删除" class="button" type="button" onclick="delSupplier(${supplier.id})">
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<!--page s-->
						<c:import url="../common/splitpage.jsp"/>
						<!--page e-->
					</div>
					<!--用户列表 e-->
					<!--创建用户 s-->
					<div class="user-box" style="display:none;">
						<h3>创建商户</h3>
						<form action="addSuppliser.htm" method="POST">
						<div class="user-form">
							<ul class="user-form-list">
								<li class="item">
									<label>商户名称：</label>
									<input type="text" name="name" class="input">
								</li>
								<li class="item">
									<label>商户code：</label>
									<input type="text" name="code" class="input">
								</li>
								<li class="item">
									<label>商户url：</label>
									<input type="text" name="url" class="input">
								</li>
								<li class="item">
									<label>商户logo：</label>
									<input type="text" name="logo" class="input">
								</li>
								<li class="item">
									<label>&nbsp;</label>
									<button type="submit" class="button">添加</button>
								</li>
							</ul>
						</div>
						</form>
					</div>
					<!--创建用户 e-->
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
	$('#suppliser-manage').addClass('cur');
	</script>
</body>
</html>