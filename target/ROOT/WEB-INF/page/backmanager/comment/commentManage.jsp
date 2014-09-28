<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp"/>
<title>商家评论管理</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script language="javascript" type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js?t=${today}"></script>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript">
$(function(){
	$("#addComment").click(function(){
		var supplierId = $("#supplierId").val();                          
		window.location.href = "${basePath}backmanager/comment/detail.htm?supplierId=" + supplierId;
	});
});
function delComment(id){
	if(confirm("确定要删除嘛?")){
		$.ajax({
			type: "POST",
	        url: "${basePath}backmanager/comment/manage/del.htm?id=" + id,
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
function gotoPage(currentPage){
	var keyword = $("#keyword").val();
	var supplierId = $("#supplierId").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var type = $("#type").val();
	var url = "${basePath}backmanager/comment/manage/page.htm?keyword=" + encodeURI(encodeURI(keyword)) + "&supplierId=" + supplierId+"&type=" + type + "&startDate="+startDate+"&endDate="+endDate + "&currentPage=" + currentPage;
	window.location.href = url;
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
						<li class="cur">商家评论管理</li>
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
							    <th>评价</th>
							    <td>
							    	<select id="type" name="type" class="select" style="width: 100px;">
							    		<option value="-1">全部</option>
							    		<option value="0">好评</option>
							    		<option value="1">一般</option>
							    		<option value="2">差评</option>
							    	</select>
							    	<script type="text/javascript">
							    	$("#type").val("${type}");
							    	</script>
							    </td>
							    <th>搜索:</th>
								<td style="text-align: left;">
									<input type="text" id="keyword" name="keyword" class="input" style="margin-left: 20px;" value="${keyword}">
									<input type="button" value="搜索" id="search" name="search" class="button" style="margin-left: 10px;">
								</td>
								<th>操作:</th>
							    <td>
							    	<input type="button" value="添加新评论" id="addComment" name="addComment" class="button" style="float: left;margin-left: 10px;">
							    </td>
							</tr>
						</table>
						<table width="100%" class="user-table">
							<thead>
								<tr>
									<th>序号</th>
									<th>商家名称</th>
									<th>时间</th>
									<th>用户</th>
									<th width="100">评价</th>
									<th width="100">是否已审核</th>
									<th>评论内容</th>
									<th>操作</th>
								</tr>
							</thead>
							<c:forEach items="${pageView.records}" var="comment">
								<%-- <colgroup>
									<col width="2%"/>
									<col width="3%"/>
									<col width="10%"/>
									<col width="10%"/>
									<col width="5%"/>
									<col width="15%"/>
									<col width="5%"/>
									<col width="10%"/>
									<col width="5%"/>
									<col width="5%"/>
									<col width="10%"/>
									<col width="5%"/>
									<col width="20%"/>
								</colgroup> --%>
								<tbody>
									<tr>
										<td>${comment.id }</td>
										<td>${comment.suppliser.name }</td>
										<td>${comment.createTime }</td>
										<td>${comment.user}</td>
										<td>${comment.type==0?'好评':(comment.type==1?'一般':'吐嘈')}</td>
										<td>${comment.oper == -1?'未审核':(comment.oper == 1?'通过':'未通过')}</td>
										<td style="text-align: left;padding: 0 20px;max-width: 600px;">${comment.content}</td>
										<td>
											<input value="编辑" onclick="javascript:window.location.href='${basePath}backmanager/comment/detail.htm?id=${comment.id}'" class="button" type="button">
										    <input value="删除" class="button" type="button" onclick="delComment(${comment.id})">
										</td>
									</tr>
								</tbody>
							</c:forEach>
						</table>
						<!--page s-->
						<jsp:include page="../common/splitpage.jsp"/>
						<!--page e-->
					</div>
					<!--用户列表 e-->
				</div>
			</div>
		</div>
		<jsp:include page="../common/left.jsp"/>
	</div>
	<!--wrap e-->
	<!--footer s-->
	<div id="footer"></div>
	<!--footer e-->
	<script type="text/javascript" src="${basePath }/js/js/backstage.js?t=${today}"></script>
	<script type="text/javascript">
		$('#comment-manager').addClass('cur');
	</script>
</body>
</html>