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
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript">
$(function(){
	$("#submit").click(function(){
		var supplierId = $("#supplierId").val();
		var id = $("#id").val();
		var content = $("#content").val();
		var type = $("#type").val();
		var user = $("#customerUser").val();
		if(!user){
			user = "admit";
			alert("用户不能为空");
			return;
		}
		var url = "${basePath}backmanager/comment/manage/edit.htm";
		if(!content){
			alert("评论内容不能为空");
			return
		}
		var param = {id:id,supplierId:supplierId,content:encodeURI(content),type:type,user:user};
		$.post(url, param,
	        function(httpObj){
	        	if(httpObj.flag){
	        		if(!id){
		        		$("#content").val("");
		        		$("#type").val(0);
	        		}
	        		alert("提交成功");
	        	}else{
	        		alert("提交失败");
	        	}
	     });
	});
	$("#backManage").click(function(){
		window.location.href = "${basePath}backmanager/comment/manage/page.htm?suppliserId=" + $("#supplierId").val();
	});
	var orignValue = $("#commentCount").val();
	$("#commentCount").change(function(){
		if(!/^\d+(\.\d+)?$/.test(this.value)){
			$("#tapcommentCount").show();
			this.value = orignValue;
		}else{
			orignValue = this.value;
			$("#tapcommentCount").hide();
		}
	});
});
function tapKeyUp(e){
	var content = $("#content").val();
	if(content.length > 150){
		return false;
	}
	return true;
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
					    <c:if test="${comment.id != null}">
							<h3>商家评论编辑</h3>
					    </c:if>
					    <c:if test="${comment.id == null}">
					    	<h3>商家评论添加</h3>
					    </c:if>
						<input type="hidden" id="id" name="id" value="${comment.id}">
						<table width="600px;" class="user-table">
							<tr>
								<td colspan="2" style="text-align: right;">
									<input type="button" class="button" id="backManage" value="返回" style="margin-right: 10px;">
								</td>
							</tr>
							<tr>
								<th>商家:</th>
								<td style="text-align: left;">
									<b5m:select list="${suppliserList}" value="${comment.supplierId}" id="supplierId" name="supplierId" noEmpty="true" styleClass="select" style="margin-left: 20px;width: 100px;"/>
								</td>
							</tr>
							<tr>
								<th>用户:</th>  
							    <td style="text-align: left;"><input type="text" id="customerUser" name="customerUser" class="input" style="margin-left: 20px;" value="${comment.user}"></td>
							</tr>
							<tr>
								<th>评论类型:</th>  
							    <td style="text-align: left;">
							    	<select class="select" name="type" id="type" style="margin-left: 20px;width: 100px;">
										<option selected="selected" value="0">好评</option>
										<option value="1">一般</option>
										<option value="2">很差</option>
									</select>
									<script type="text/javascript">
									$("#type").val("${comment.type}");
									</script>
							    </td>
							</tr>
							<tr>
								<th>评论:</th>  
							    <td style="text-align: left;">
							    <textarea rows="5" cols="5" id="content" onkeyup="return tapKeyUp(event)" onkeypress="return tapKeyUp(event)" onkeydown="return tapKeyUp(event)" name="content" class="input" 
							    style="margin-left: 20px;width: 250px;height: 50px;">${comment.content}</textarea>
							    <span style="color: #FF6600">长度不得大于150</span>
							</tr>
							<tr>
								<td colspan="2">
									<input type="button" id="submit" class="button" value="提交">
								</td>
							</tr>
						</table>
						<script type="text/javascript">
						if("${comment.id}"){
							$("#supplierId").attr("disabled", "disabled").css("background-color", "#F1F1F1");
							$("#customerUser").attr("disabled", "disabled").css("background-color", "#F1F1F1");
						}else{
							$("#customerUser").val('admin');
						}
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
	<script type="text/javascript" src="${basePath }/js/js/backstage.js?t=${today}"></script>
	<script type="text/javascript">
	$('#comment-manager').addClass('cur');
	</script>
</body>
</html>