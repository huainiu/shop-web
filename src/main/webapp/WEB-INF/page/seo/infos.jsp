<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ page session="false"%>
<!doctype html>
<html>
<head>
	<c:import url="../common/meta.jsp" />
	<meta charset="utf-8">
	<title>${meta.title }</title>
	<meta name="keywords" content="${meta.keyword }" />
	<meta name="description" content="${meta.des }" />
	<link rel="stylesheet" type="text/css" href="http://staticcdn.b5m.com/css/common/common.css?t=${today}" />
	<link rel="stylesheet" type="text/css" href="${basePath }css/infos.css?t=${today}" />
<body>
<%@ include file="../common/search.jsp"%>
<div class="wraper">
	<div class="seo-wraper clear-fix">
		<div class="seo-wp-l">
			<div class="seo-wp-bord">
				<div class="seo-wp-tit">
					<c:if test="${word != null}">
						<h2><span>${word }的相关资讯</span></h2>
					</c:if>
					<c:if test="${word == null}">
						<h2><span>所有资讯</span></h2>
					</c:if>
				</div>
				<ul class="seo-wp-list">
					<c:forEach items="${list }" var="bean">
						<li>
							<h3><a href="${basePath }topic/${info_link}.htm?id=${bean.id}" title="${bean.title }">${bean.title }</a></h3>
							<span class="timer">${bean.time }</span>
							<c:if test="${fn:indexOf(bean.content, '<br>') >= 0}">
								<c:set var="flag" value="1"></c:set>
								<c:forEach items="${fn:split(bean.content, '<br>')}" var="content">
									<c:if test="${content != null && content != '' && flag == 1}">
										<p>${fn:substring(content, 0, 150)}...<a href="${basePath }topic/${info_link}.htm?id=${bean.id}" title="">查看详情 ></a></p>
										<c:set var="flag" value="2"></c:set>
									</c:if>
								</c:forEach>
							</c:if>
							
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="seo-wp-r">
			<div class="">
				<a href="javascript:;" title=""><img src="http://pic.junli.cc/196x196/ccc" width="196" height="196" alt="图片尺寸196-196" /></a>
			</div>
		</div>
	</div>
	<div class="seo-banWp">
		<a href="javascript:;" title=""><img src="http://pic.junli.cc/980x90/ccc" width="980" height="90" alt="图片尺寸980-90"></a>
	</div>
</div>

<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/common.js?t=${today}"></script>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/autoFill.js?t=${today}"></script>

<!--[if IE 6 ]>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/hover_fix.js?t=${today}"></script>
<![endif]-->
<%@ include file="../common/footer.jsp"%>
</body>
</html>