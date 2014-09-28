<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
    <c:import url="../common/meta.jsp" />
	<meta charset="utf-8">
	<title>${meta.title }</title>
	<meta name="keywords" content="${meta.keyword }" />
	<meta name="description" content="${meta.des }" />
	<link rel="stylesheet" type="text/css" href="http://staticcdn.b5m.com/css/common/common.css?t=${today}" />
	<link rel="stylesheet" type="text/css" href="${basePath }css/keys.css?t=${today}" />
</head>
<body>
<%@ include file="../common/search.jsp"%>
<div class="wraper clear-fix">
    <div class="seo-cont-l">
        <div class="crumb cf">
            <div class="l">当前位置：</div><strong>关键字列表</strong>
        </div>

        <div class="seo-content">
            <div class="seo-ban">
                <img src="http://pic.junli.cc/640x60/ccc" width="640" height="60" alt="百度联盟广告位640-60">
            </div>
            <h3 class="keyword-tit">关键字列表</h3>
            <ul class="keyword-list cf">
            	<c:forEach items="${list }" var="bean">
                	<li><a href="${basePath }topic/${infos_link}.htm?keyword=${bean.name}" title="${bean.name }">${bean.name }</a></li>
                </c:forEach>
            </ul>
            <div class="seo-ban">
                <img src="http://pic.junli.cc/640x60/ccc" width="640" height="60" alt="百度联盟广告位640-60">
            </div>
        </div>

    </div>
    <div class="seo-cont-r">
        <div class="seo-ban">
           <a href="javascript:;" title="图片属性"><img src="http://pic.junli.cc/250x250/ccc" width="250" height="250" alt="图片尺寸250-250" /></a>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/common.js?t=${today}"></script>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/autoFill.js?t=${today}"></script>
<%@ include file="../common/footer.jsp"%>
</body>
</html>