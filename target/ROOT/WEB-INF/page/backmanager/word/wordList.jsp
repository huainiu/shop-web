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
	var url = "${basePath}backmanager/word/manage/page.htm?currentPage=" + currentPage + "&word=" + $('#keyword').val();
	window.location.href = url;
} 
function delword(id){
	if(confirm("确定要删除吗?")){
		$.ajax({
			type: "POST",
	        url: "${basePath}backmanager/word/manage/del.htm?id=" + id,
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
function reload(){
	if(confirm("确定要reload吗?")){
		$.ajax({
			type: "POST",
	        url: "${basePath}backmanager/word/manage/reload.htm",
	        data: "",
	        dataType:"json",
	        success: function(httpObj){
	        	if(httpObj.flag){
	        		alert("重载成功");
	        	}else{
	        		alert("重载失败");
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
						<li class="cur">敏感词列表</li>
					</ul>
					<!--用户列表 s-->
					<table width="100%" class="user-table">
						<tr>
							<th>搜索:</th>
							<td style="text-align: left;">
								<input type="text" id="keyword" name="keyword" class="input" style="margin-left: 20px;" value="${keyword}">
								<input type="button" value="搜索" id="search" name="search" class="button" style="margin-left: 10px;">
							</td>
							<th>重载</th>
							<td style="text-align: left; width: 50%">
								<input type="button" value="reload keyword" id="reload" name="reload" onclick="reload();" class="button" style="margin-left: 10px;">
								<span style="color: red; margin-left: 20px">注意: 敏感词不支持实时生效，涉及删除，修改的操作，如果需要实时生效，请点击重载按钮</span>
							</td>
							<th>操作:</th>
							<td style="text-align: right;">
								<input type="button" value="添加敏感词" onclick="javascript:window.location.href='${basePath}backmanager/word/manage/detail.htm?url='+window.location.href;" class="button" style="margin-right: 10px;">
							</td>
						</tr>
					</table>
					<div class="user-box">
						<table width="100%" class="user-table">
							<thead>
								<tr>
									<th width="10%">编号</th>
									<th width="20%">关键词名称</th>
									<th width="20%">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageView.records}" var="word">
								<tr>
									<td>${word.id }</td>
									<td>${word.word }</td>
									<td>
										<input value="编辑" class="button" type="button" onclick="javascript:window.location.href='${basePath}backmanager/word/manage/detail.htm?id=${word.id }&url='+window.location.href;">
										<input value="删除" class="button" type="button" onclick="delword(${word.id})">
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<!--page s-->
						<c:import url="../common/splitpage.jsp"/>
						<!--page e-->
					</div>
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
	$('#word-manage').addClass('cur');
	</script>
</body>
</html>