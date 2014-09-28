<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp"/>
<title>商家印象</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript">
$(function(){
	$("#submit").click(function(){
		var supplierId = $("#supplierId").val();
		var id = $("#id").val();
		var content = $("#content").val();
		var impressCount = $("#impressCount").val();
		var user = $("#customerUser").val();
		if(!user){
			user = "admit";
			alert("用户不能为空");
			return;
		}
		if(!content){
			alert("印象内容不能为空");
			return
		}
		if(content.length > 8){
			alert("印象内容长度不能大于8");
			return;
		}
		var url = "${basePath}backmanager/impress/manage/edit.htm";
		var param = {id:id,supplierId:supplierId,content:encodeURI(content),impressCount:impressCount,user:user};
		$.post(url, param,
	        function(httpObj){
	        	if(httpObj.flag){
	        		if(!id){
		        		$("#content").val("");
		        		$("#impressCount").val("");
	        		}
	        		alert("提交成功");
	        	}else{
	        		alert("提交失败");
	        	}
	     });
	});
	$("#backManage").click(function(){
		window.location.href = "${basePath}backmanager/impress/manage/page.htm?suppliserId=" + $("#supplierId").val();
	});
	var orignValue = $("#impressCount").val();
	$("#impressCount").change(function(){
		if(!/^\d+(\.\d+)?$/.test(this.value)){
			$("#tapImpressCount").show();
			this.value = orignValue;
		}else{
			orignValue = this.value;
			$("#tapImpressCount").hide();
		}
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
						<li class="cur">商家印象管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
					    <c:if test="${impress.id != null}">
							<h3>商家印象编辑</h3>
					    </c:if>
					    <c:if test="${impress.id == null}">
					    	<h3>商家印象添加</h3>
					    </c:if>
						<input type="hidden" id="id" name="id" value="${impress.id}">
						<table width="600px;" class="user-table">
							<tr>
								<td colspan="2" style="text-align: right;">
									<input type="button" class="button" id="backManage" value="返回" style="margin-right: 10px;">
								</td>
							</tr>
							<tr>
								<th>商家:</th>
								<td style="text-align: left;">
									<b5m:select list="${suppliserList}" value="${impress.supplierId}" id="supplierId" name="supplierId" noEmpty="true" styleClass="select" style="margin-left: 20px;width: 100px;"/>
								</td>
							</tr>
							<tr>
								<th>用户:</th>  
							    <td style="text-align: left;"><input type="text" id="customerUser" name="customerUser" class="input" style="margin-left: 20px;" value="${impress.user}"></td>
							</tr>
							<tr>
								<th>评价数量:</th>
							    <td style="text-align: left;">
							    	<input type="text" id="impressCount" name="impressCount" class="input" style="margin-left: 20px;" value="${impress.impressCount}">
							    	<span style="color: #FF6600;display: none;" id="tapImpressCount">只能输入数字</span>
							    </td>
							</tr>
							<tr>
								<th>印象:</th>  
							    <td style="text-align: left;">
							    <input type="text" id="content" name="content" class="input" 
							    style="margin-left: 20px;width: 200px;" value="${impress.content}">
							    <c:if test="${impress.id == null}">
								    <span style="color: #FF6600">长度不得大于8</span>
							    </c:if>
							</tr>
							<%-- <c:if test="${impress != null }">
								<tr>
									<th>审核</th>
									<td style="text-align: left;">
										<select id="oper" name="oper" class="select" style="margin-left: 20px;width: 150px;">
											<option value="0" <c:if test="${impress.oper == 0 }" >selected</c:if>>未通过</option>
											<option value="1" <c:if test="${impress.oper == 1 }" >selected</c:if>>通过</option>
									</select>
									</td>
								</tr>
							</c:if> --%>
							<tr>
								<td colspan="2">
									<input type="button" id="submit" class="button" value="提交">
								</td>
							</tr>
						</table>
						<script type="text/javascript">
						if("${impress.id}"){
							$("#supplierId").attr("disabled", "disabled").css("background-color", "#F1F1F1");
							$("#customerUser").attr("disabled", "disabled").css("background-color", "#F1F1F1");
							$("#content").attr("disabled", "disabled").css("background-color", "#F1F1F1");
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
	<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
	<script type="text/javascript" src="${basePath }js/js/backstage.js?t=${today}"></script>
	<script type="text/javascript">
	$('#impress-manager').addClass('cur');
	</script>
</body>
</html>