<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp" />
<title>资源角色</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript" src="${basePath}js/js/Jquery.Query.js?t=${today}"></script>
<script type="text/javascript">
	function gotoPage(currentPage){
		var url = "${basePath}backmanager/meta/metas.htm" + $.query.set('currentPage', currentPage).toString();
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
						<li class="cur">页面描述列表</li>
					</ul>
					<!--资讯列表 s-->
					<div class="user-box">
						<table width="100%" class="user-table">
							<tr>
								<th>操作:</th>
								<td style="text-align: right;">
									<input type="button" value="添加新资讯" onclick="javascript:window.location.href='${basePath}backmanager/meta/edit.htm'+$.query.set('currentPage', ${pageView.currentPage}).toString();" id="add" name="add" class="button" style="margin-right: 10px;">
								</td>
							</tr>
						</table>
						<table width="100%" class="user-table">
							<thead>
								<tr>
									<th width="7%">ID</th>
									<th width="8%">页面名称</th>
									<th width="25%">title</th>
									<th width="25%">描述</th>
									<th width="25%">keyword</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageView.records}" var="bean">
									<tr id="${bean.id}">
										<td>${bean.id }</td>
										<td>${bean.pageName }</td>
										<td>${bean.title }</td>
										<td>${bean.des }</td>
										<td>${bean.keyword }</td>
										<td>
											<input value="编辑" class="button" type="button"	onclick="javascript:window.location.href='${basePath}backmanager/meta/edit.htm' + $.query.set('id', ${bean.id }).toString();">
										</td>
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
		$('#link-manage').addClass('cur');
		
		$('#find').click(function(){
		});
	</script>
</body>
</html>