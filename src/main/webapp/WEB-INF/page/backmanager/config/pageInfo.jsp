<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp" />
<title>资源操作</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<style type="text/css">
.ico_close {
	float: right;
	cursor: pointer;
}
</style>
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
						<li class="cur">page配置管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<h3>page配置管理</h3>
						<table width="600px;" class="user-table">
							<tr>
								<th>search-hotkey:</th>
								<td style="text-align: left;">
									<input type="text" id="hotkey" class="input" style="margin-left: 20px;"/>
								</td>
							</tr>
							<c:forEach items="${list}" var="key">
								<tr>
									<td colspan="2">
										<span>${key.name}</span><s class='ico_close'><img src='${basePath }images/close.png'></img></s>
									</td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="2">
									<input type="button" id="submit" class="button" value="添加">
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
		$('#pageConfig-manager').addClass('cur');
		$(function() {
			//提交
			$("#submit").click(function() {
				var value = $("#hotkey").val();
				if (confirm("确定要保存吗?")) {
					$.ajax({
						type : "POST",
						url : "${basePath}backmanager/pageConfig/save.htm",
						data : {
							'value' : value
						},
						dataType : "json",
						success : function(httpObj) {
							location.reload();
						}
					});
				}
			});

			$(".ico_close").click(function() {
				var value = $(this).prev().text();
				if (confirm("确定要删除吗?")) {
					$.ajax({
						type : "POST",
						url : "${basePath}backmanager/pageConfig/delete.htm",
						data : {
							'value' : value
						},
						dataType : "json",
						success : function(httpObj) {
							location.reload();
						}
					});
				}
			});
		});
	</script>
</body>
</html>