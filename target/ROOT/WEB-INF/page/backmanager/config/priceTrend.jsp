<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp" />
<title>资源操作</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
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
						<li class="cur">数据源管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<h3>价格趋势数据源管理</h3>
						<table width="600px;" class="user-table">
							<tr>
								<th>数据源:</th>
								<td style="text-align: left;">
									<select class="select" name="source" id="source" style="margin-left: 20px; width: 100px;">
										<option value="0" selected="selected">Hbase</option>
										<option value="1">SF1</option>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="button" id="submit" class="button" value="切换">
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
		$('#source-manager').addClass('cur');
		var source = eval('${source}');
		$(function() {
			//初始化
			$("#source").val(source);

			//提交
			$("#submit").click(function() {
				var source = $("#source").val();
				if (confirm("确定要切换吗?")) {
					$.ajax({
						type : "POST",
						url : "${basePath}backmanager/config/save.htm?source=" + source,
						data : "",
						dataType : "json",
						success : function(httpObj) {
							alert(httpObj.msg);
						}
					});
				}
			});
		});
	</script>
</body>
</html>