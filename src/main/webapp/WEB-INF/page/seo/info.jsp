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
	<link rel="stylesheet" type="text/css" href="${basePath }css/info.css?t=${today}" />
<body>
<%@ include file="../common/search.jsp"%>
<div class="wraper">
	<div class="seo-wraper clear-fix">
		<div class="seo-wp-l">
			<div class="seo-wp-bord">
				<h2 class="seo-in-tit">${bean.title }</h2>
				<span class="timer">${bean.time } 发布</span>
				<div class="seo-in-cont">
					${bean.content }
				</div>
			</div>
		</div>
		<div class="seo-wp-r">
			<div class="seo-ban">
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
<script src="scripts/jQueryRotate.2.2.js?t=${today}"></script>
<script src="scripts/jquery.easing.min.js?t=${today}"></script>
<script type="text/javascript" src="scripts/lottery.js?t=${today}"></script>

<!--[if IE 6 ]>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/hover_fix.js?t=${today}"></script>
<![endif]-->
<%@ include file="../common/footer.jsp"%>
</body>
</html>