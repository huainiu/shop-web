<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp" />
<title>资讯操作</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript" src="${basePath}js/js/Jquery.Query.js?t=${today}"></script>
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
						<li class="cur">页面管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<input type="hidden" id="id" name="id" value="${bean.id}">
						<table class="user-table">
							<tr>
								<td colspan="2" style="text-align: right;">
									<input type="button" class="button" id="backManage" value="返回" style="margin-right: 10px;">
								</td>
							</tr>
							<tr>
								<th style="width: 200px;">页面名称:</th>
								<td style="text-align: center; width: 90%;">
									<input type="text" id="pageName" name="pageName" class="input" style="width: 90%;" value="${bean.pageName}">
								</td>
							</tr>
							<tr>
								<th style="width: 200px;">title:</th>
								<td style="text-align: center;">
									<input type="text" id="title" name="title" class="input" style="width: 90%;" value="${bean.title}">
								</td>
							</tr>
							<tr>
								<th style="width: 200px;">描述:</th>
								<td style="text-align: center;">
									<input type="text" id="des" name="des" class="input" style="width: 90%;" value="${bean.des}">
								</td>
							</tr>
							<tr>
								<th style="width: 200px;">keyword:</th>
								<td style="text-align: center;">
									<input type="text" id="keyword" name="keyword" class="input" style="width: 90%;" value="${bean.keyword}">
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
	<script type="text/javascript" src="${basePath }js/js/backstage.js?t=${today}"></script>
	<!--wrap e-->
	<!--footer s-->
	<div id="footer"></div>
	<!--footer e-->
	<script type="text/javascript">
		$('#meta-manager').addClass('cur');
		$(function() {
			//返回
			$("#backManage").click(function() {
				var url = "${basePath}backmanager/meta/metas.htm" + $.query.REMOVE("id");
				window.location.href = url;
			});
			//提交
			$("#submit").click(function() {
				var id = $("#id").val();
				var url = "${basePath}backmanager/meta/save.htm";
				var pageName = $("#pageName").val();
				var title = $("#title").val();
				var des = $("#des").val();
				var keyword = $("#keyword").val();
				if (!pageName || !title || !des || !keyword) {
					alert("不能设为空值");
					return;
				}
				var param = {
					pageName : pageName,
					title : title,
					des : des,
					keyword : keyword,
					id : id
				};
				$.ajax({
					url : url,
					type : 'POST',
					data : param,
					dataType : 'json',
					success : function(result) {
						alert(result.msg);
						location.href = "${basePath}backmanager/meta/edit.htm" + $.query.set("id", result.val);
					}
				});
			});
		});
	</script>
</body>
</html>