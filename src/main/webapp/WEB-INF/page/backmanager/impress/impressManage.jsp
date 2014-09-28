<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp"/>
<title>商家印象管理</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script language="javascript" type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js?t=${today}"></script>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript">
$(function(){
	$("#addImpress").click(function(){
		var supplierId = $("#supplierId").val();                          
		window.location.href = "${basePath}backmanager/impress/detail.htm?supplierId=" + supplierId;
	});
});
function gotoPage(currentPage){
	var keyword = $("#keyword").val();
	var supplierId = $("#supplierId").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var oper = $("#oper").val();
	var url = "${basePath}backmanager/impress/manage/page.htm?keyword=" + encodeURI(encodeURI(keyword)) + "&supplierId=" + supplierId+"&startDate="+startDate+"&oper=" + oper + "&endDate="+endDate + "&currentPage=" + currentPage;
	window.location.href = url;
}
function delImpress(id){
	if(confirm("确定要删除嘛?")){
		$.ajax({
			type: "POST",
	        url: "${basePath}backmanager/impress/manage/del.htm?id=" + id,
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
						<li class="cur">商家印象管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<table width="100%" class="user-table">
							<tr>
								<th>商家:</th>
								<td style="text-align: left;">
									<b5m:select list="${suppliserList}" value="${supplierId}" id="supplierId" name="supplierId" noEmpty="false" styleClass="select" style="margin-left: 20px;width: 100px;"/>
								</td>
								<th>开始时间:</th>
							    <td><input class="Wdate" type="text" id="startDate" name="startDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${startDate}"></td>
							    <th>结束时间:</th>
							    <td><input class="Wdate" type="text" id="endDate" name="endDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${endDate}"></td>
							    <th>是否审核</th>
							    <td>
							    	<select id="oper" name="oper" class="select" style="width: 100px;">
							    		<option value="-2">全部</option>
							    		<option value="1">通过</option>
							    		<option value="0">未通过</option>
							    		<option value="-1">未审核</option>
							    	</select>
							    	<script type="text/javascript">
							    	$("#oper").val("${oper}");
							    	</script>
							    </td>
							    <th>搜索:</th>
								<td style="text-align: left;">
									<input type="text" id="keyword" name="keyword" class="input" style="margin-left: 20px;" value="${keyword}">
									<input type="button" value="搜索" id="search" name="search" class="button" style="margin-left: 10px;">
								</td>
								<th>操作:</th>
								<td style="text-align: left;">
									<input type="button" value="添加新印象" id="addImpress" name="addImpress" class="button" style="float: left;margin-left: 10px;">
								</td>
							</tr>
						</table>
						<table width="100%" class="user-table">
							<thead>
								<tr>
									<th width="50">序号</th>
									<th width="150">商城</th>
									<th width="150">时间</th>
									<th>印象</th>
									<th width="100">评价数量</th>
									<th width="100">是否已审核</th>
									<th width="200">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageView.records}" var="inpress">
									<tr>
										<td>${inpress.id}</td>
										<td>${inpress.suppliser.name}</td>
										<td>${inpress.createTime}</td>
										<td>${inpress.content}</td>
										<td>${inpress.impressCount}</td>
										<td>${inpress.oper == -1?'未审核':(inpress.oper == 1?'通过':'未通过')}</td>
										<td>
											<input value="编辑" onclick="javascript:window.location.href='${basePath}backmanager/impress/detail.htm?id=${inpress.id}'" class="button" type="button" idValue="${inpress.id}">
										    <input value="删除" class="button" type="button" onclick="delImpress(${inpress.id})">
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
		$('#impress-manager').addClass('cur');
	</script>
</body>
</html>