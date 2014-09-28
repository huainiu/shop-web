<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp" />
<title>资源角色</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script language="javascript" type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js?t=${today}"></script>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript">
	$(function() {
		$("#export").click(function() {
			var start = $('#startDate').val();
			var end = $('#endDate').val();
			var url = "${basePath}backmanager/recommend/export.htm";
			if (start) {
				url = url + "?startDate=" + start;
				if (end)
					url = url + "&endDate=" + end;
			} else if (end) {
				url = url + "?endDate=" + end;
			}
			window.open(url);
			return false;
		});

		$("#search").click(function() {
			gotoPage(1);
		});
	});
	
	function gotoPage(currentPage){
		if(!currentPage) currentPage = 1;
		var start = $('#startDate').val();
		var end = $('#endDate').val();
		var url = "${basePath}backmanager/recommend/page.htm?currentPage=" + currentPage;
		if (start != "") {
			url = url + "&startDate=" + start;
		} 
		if (end != "") {
			url = url + "&endDate=" + end;
		}
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
				<div class="user" id="feedback">
					<ul class="user-tab">
						<li class="cur">用户反馈</li>
					</ul>
					<!--反馈列表 s-->
					<div class="user-box">
						<table width="100%" class="user-table">
							<tr>
								<th>开始时间:</th>
								<td>
									<input class="Wdate" type="text" id="startDate" name="startDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${startDate}">
								</td>
								<th>结束时间:</th>
								<td>
									<input class="Wdate" type="text" id="endDate" name="endDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${endDate}">
								</td>
								<th>搜索:</th>
								<td style="text-align: right;">
									<input type="button" value="搜索" id="search" name="search" class="button" style="margin-right: 10px;">
								</td>
								<th>操作:</th>
								<td style="text-align: right;">
									<input type="button" value="导出" id="export" name="export" class="button" style="margin-right: 10px;">
								</td>
							</tr>
						</table>
						<table width="100%" class="user-table">
							<thead>
								<tr>
									<th>关键词</th>
									<th>反馈信息</th>
									<th>电子邮箱</th>
									<th>IP</th>
									<th>提交时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageView.records}" var="recommend">
									<tr id="${recommend.id}">
										<td width="15%">${recommend.keyword }</td>
										<td width="45%">${recommend.content }</td>
										<td>${recommend.email }</td>
										<td>${recommend.ip }</td>
										<td>${recommend.createTime }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!--page s-->
						<c:import url="../common/splitpage.jsp" />
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
		$('#feedback-manage').addClass('cur');

	</script>
</body>
</html>