<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp"/>
<title>商家</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript">
$(function(){
	$("#submit").click(function(){
		var id = $("#id").val();
		var word = $("#word").val();
		var isDelete = $("#isDelete").val();
		var param = {id:id,word:word,isDelete:isDelete};
		$.post("${basePath}backmanager/word/manage/edit.htm", param,
	        function(httpObj){
	        	if(httpObj.flag){
	        		if(!id){
	        			$("#id").val("");
	        			$("#word").val("");
	        		}
	        		alert("提交成功");
	        	}else{
	        		alert("提交失败");
	        	}
	     });
	});
	$("#backManage").click(function(){
		if ("${url}" == "") {
			window.location.href = "${basePath}backmanager/word/manage/page.htm";
		}
		window.location.href = "${url}";
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
						<li class="cur">敏感词管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
					    <c:if test="${word.id != null}">
							<h3>敏感词编辑</h3>
					    </c:if>
					    <c:if test="${word.id == null}">
					    	<h3>敏感词添加</h3>
					    </c:if>
						<input type="hidden" id="id" name="id" value="${word.id}">
						<input type="hidden" id="isDelete" name="isDelete" value="${word.isDelete == null ? 0 : word.isDelete}">
						<table width="800px;" class="user-table">
							<tr>
								<td colspan="2" style="text-align: right;">
									<input type="button" class="button" id="backManage" value="返回" style="margin-right: 10px;">
								</td>
							</tr>
							<tr>
								<th>敏感词名称:</th>
								<td style="text-align: left;">
									<input type="text" id="word" name="word" class="input" style="margin-left: 20px;" value="${word.word}">
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="button" id="submit" class="button" value="提交">
								</td>
							</tr>
						</table>
						<script type="text/javascript">
						
						</script>
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
	<script type="text/javascript" src="${basePath }js/js/backstage.js?t=${today}"></script>
	<script type="text/javascript">
	$('#word-manage').addClass('cur');
	</script>
</body>
</html>