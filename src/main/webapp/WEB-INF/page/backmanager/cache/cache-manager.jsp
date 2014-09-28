<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp"/>
<title>缓存清除</title>
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
						<li class="cur">缓存管理</li>
					</ul>
					<div class="user-box">
						<h3>缓存重新加载</h3>
						<input type="hidden" id="id" name="id" value="${impress.id}">
						<table width="600px;" class="user-table">
							<tr>
								<td>
									<ul>
										<li style="height: 145px;width: 145px;border: 1px solid #e0e0e0;overflow: hidden;color:#999;float:left;margin：0 19.3px 20px 0;vertical-align:middle;">
										   广告缓存
										</li>
									</ul>
								</td>
							</tr>
						</table>
					</div>
					
					<div class="user-box">
						<h3>Memcache缓存清除</h3>
						<input type="hidden" id="id" name="id" value="${impress.id}">
						<table width="600px;" class="user-table">
							<tr>
								<td>
									
								</td>
							</tr>
						</table>
					</div>
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
	$('#system-cache').addClass('cur');
	</script>
</body>
</html>