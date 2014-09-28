<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp"/>
<title>商家</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}" />
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<style type="text/css">
#supplierList td:HOVER{
   background-color: #EBEBEB;
}
#supplierList td{
	height: 25px;
	line-height: 25px;
	border:none;
	color:#444;
	font:12px/1.5 tahoma,\5b8b\4f53;
	padding-left: 5px;
}
#supplierList tr:HOVER {
	cursor: pointer;
}
</style>
<script type="text/javascript">
var lastselectedrow = 0;
var selectedrow = null;
$(function(){
	if(!"${supplier.id}"){
		empty();
	}
	$("#name").keydown(function(e){
		var keynum;
		if(window.event) {// IE
			keynum = window.event.keyCode;
		}else if(e.which){ // Netscape/Firefox/Opera
			keynum = e.which;
		}
		if(keynum==38){
		    var tdlist = $(".list_selected");
			if(selectedrow==null){
				selectedrow = 0;
				lastselectedrow = tdlist.lenght;
			}
			lastselectedrow = selectedrow;
			tdlist[lastselectedrow].style.backgroundColor="";
			selectedrow--;
			if(selectedrow<0)
				selectedrow=tdlist.length-1;
			tdlist[selectedrow].style.backgroundColor="#EBEBEB";
		} else if(keynum==40){
		    var tdlist = $(".list_selected");
			if(selectedrow == null){
				selectedrow = tdlist.length-1;
				lastselectedrow = tdlist.length-1;
			}
			lastselectedrow = selectedrow;
			tdlist[lastselectedrow].style.backgroundColor="";
			selectedrow++
			if(selectedrow>=tdlist.length)
				selectedrow=0;
			tdlist[selectedrow].style.backgroundColor="#EBEBEB";
		} else if(keynum==13){
		    var tdlist = $(".list_selected");
		    select(tdlist[selectedrow]);
		}
	});
	$("#submit").click(function(){
		var name = $("#name").val();
		var id = $("#id").val();
		var url = $("#url").val();
		var logo = $("#logo").val();
		var param = {id:id,name:name,url:url,logo:logo};
		$.post("${basePath}backmanager/supplier/manage/edit.htm", param,
	        function(httpObj){
	        	if(httpObj.flag){
	        		if(!id){
	        			$("#name").val("");
	        			$("#id").val("");
	        			$("#logo").val("");
	        			$("#url").val("");
	        		}
	        		alert("提交成功");
	        	}else{
	        		alert("提交失败");
	        	}
	     });
	});
	$("#backManage").click(function(){
		window.location.href = "${basePath}backmanager/supplier/manage/page.htm";
	});
});
var orignValue = "";
function searchSupplier(e){
	var keyword = $("#name").val();
	if(!keyword) {
		empty();
		$("#supplierList").css("display", "none");
		return;
	}
	if(orignValue == keyword) return;
	var param = {keyword:keyword};
	$.post("${basePath}backmanager/supplier/autofill.htm", param,
        function(httpObj){
			var table = "<table>";
			var have = false;
			var num = 0;
			$.each(httpObj, function(i, item){
				table = table + "<tr><td style=\"width:210px;text-align: left;\" class=\"list_selected\" onclick=\"select(this)\" logo=\""+item.logo+"\" url=\"" + item.url + "\" name=\"" + item.name + "\">" + item.name + "</td></tr>";
				have = true;
				num++;
			});
			if(num >= 20){
				$("#supplierList").css("height","200px");
				$("#supplierList").css("overflowY","scroll");
			}else{
				$("#supplierList").css("overflowY", "hidden");
			}
			table = table + "</table>";
			$("#supplierList").html(table);
			if(have){
				$("#supplierList").css("display", "block");
				selectedrow = 0;
			}
			orignValue = keyword;
     });
}
function select(td){
	var logo = $(td).attr("logo");
	var url = $(td).attr("url");
	var name = $(td).attr("name");
	$("#name").val(name);
	$("#logo").val(logo);
	$("#url").val(url);
	orignValue = name;
	$("#supplierList").css("display", "none");
}
function empty(){
	$("#name").val("");
	$("#logo").val("");
	$("#url").val("");
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
						<li class="cur">商家管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
					    <c:if test="${supplier.id != null}">
							<h3>商家编辑</h3>
					    </c:if>
					    <c:if test="${supplier.id == null}">
					    	<h3>商家添加</h3>
					    </c:if>
						<input type="hidden" id="id" name="id" value="${supplier.id}">
						<table width="800px;" class="user-table">
							<tr>
								<td colspan="2" style="text-align: right;">
									<input type="button" class="button" id="backManage" value="返回" style="margin-right: 10px;">
								</td>
							</tr>
							<tr>
								<th>商家名称:</th>
								<td style="text-align: left;">
									<input type="text" id="name" name="name" onkeyup="searchSupplier(event);" onfocus="searchSupplier();" class="input" style="margin-left: 20px;width: 200px;" value="${supplier.name}">
									<div style="position:relative;">
										<div id="supplierList" style="display:none;width: 210px;position:absolute;left: 20px;top: 0px; background-color: white;border: 1px solid #DDDDDD;">
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th>链接地址:</th>  
							    <td style="text-align: left;"><input type="text" id="url" name="url" class="input" style="margin-left: 20px;width: 600px;" value="${supplier.url}"></td>
							</tr>
							<tr>
								<th>logo:</th>
							    <td style="text-align: left;">
							    	<input type="text" id="logo" name="logo" class="input" style="margin-left: 20px;width: 400px;" value="${supplier.logo}">
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
		<c:import url="../common/left.jsp"></c:import>
	</div>
	<!--wrap e-->
	<!--footer s-->
	<div id="footer"></div>
	<!--footer e-->
	<script type="text/javascript" src="${basePath }js/js/backstage.js?t=${today}"></script>
	<script type="text/javascript">
	$('#suppliser-manage').addClass('cur');
	</script>
</body>
</html>