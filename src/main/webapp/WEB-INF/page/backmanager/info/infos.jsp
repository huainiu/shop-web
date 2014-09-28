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
		var url = "${basePath}backmanager/info/infos.htm" + $.query.set('currentPage', currentPage).toString();
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
						<li class="cur">资讯列表</li>
					</ul>
					<!--资讯列表 s-->
					<div class="user-box">
						<table width="100%" class="user-table">
							<tr>
								<th>搜索:</th>
								<td style="text-align: left;">
									<input type="text" id="keyword" name="keyword" class="input" style="margin-left: 20px;" value="${keyword}">
									<input type="button" value="搜索" id="find" name="find" class="button" style="margin-left: 10px;">
								</td>
								<th>操作:</th>
								<td style="text-align: right;">
									<input type="button" value="添加新资讯" onclick="javascript:window.location.href='${basePath}backmanager/info/edit.htm'+$.query.set('currentPage', ${pageView.currentPage}).toString();" id="add" name="add" class="button" style="margin-right: 10px;">
								</td>
							</tr>
						</table>
						<table width="100%" class="user-table">
							<thead>
								<tr>
									<th>ID</th>
									<th>资讯标题</th>
									<th>资讯内容</th>
									<th>更新日期</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageView.records}" var="bean">
									<tr id="${bean.id}">
										<td>${bean.id }</td>
										<td>${bean.title }</td>
										<c:if test="${fn:indexOf(bean.content, '<br>') >= 0}">
											<c:set var="flag" value="1"></c:set>
											<c:forEach items="${fn:split(bean.content, '<br>')}" var="content">
												<c:if test="${content != null && content != '' && flag == 1}">
													<td>${fn:substring(content, 0, 30)}...</td>
													<c:set var="flag" value="2"></c:set>
												</c:if>
											</c:forEach>
										</c:if>
										<c:if test="${fn:indexOf(bean.content, '<br>') < 0}">
											<td>${fn:substring(bean.content, 0, 30)}...</td>
										</c:if>
										<td>${bean.updateTime }</td>
										<td>
											<input value="编辑" class="button" type="button"	onclick="javascript:window.location.href='${basePath}backmanager/info/edit.htm' + $.query.set('id', ${bean.id }).toString();">
											<input value="删除" class="button" type="button" onclick="delBean(${bean.id})">
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
		$('#info-manage').addClass('cur');
		
		$('#find').click(function(){
			search($('#keyword').val());	
		});
		
		function search(keyword) {
			var url = "${basePath}backmanager/info/infos.htm?keyword="+keyword;
			window.location.href = url;
		}
		
		function delBean(id) {
			var url = "${basePath}backmanager/info/delete.htm";
			$.ajax({
				url : url,
				type : 'POST',
				data : {'id' : id},
				dataType : 'json',
				success : function(result) {
					alert(result.msg);
					gotoPage('${pageView.currentPage}');
				}
			});
		}
	</script>
</body>
</html>