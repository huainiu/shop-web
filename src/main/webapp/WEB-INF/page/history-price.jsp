<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="./common/meta.jsp" />
<title>${categoryName }历史价格查询，${categoryName }网上商品报价-第${pageView.currentPage }页-帮5买</title>
<meta name="keywords" content="历史价格查询,${categoryName },网上商品报价,帮5买" />
<meta name="description" content="帮5买${categoryName }历史价格查询，可以查看商品的价格趋势、历史最高价、上月价格。" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" href="http://y.b5mcdn.com/css/jiagebaogao/css.css?t=${today}" />
<!--[if lte IE 6]>
<script src="${basePath}js/js/DD_belatedPNG_0.0.8a.js?t=${today}"></script>
<script type="text/javascript">
	DD_belatedPNG.fix('.top .logo-pic b,.top .site-circle,.top .site-talk p,#wrap .impress-more .more b,#wrap .impress-bd .add-tag span,.tips .success i, .tips .waring i,.tips .close,.comment-sort .default-bg, .comment-sort .default-bg span');
</script>
<![endif]-->
</head>
<body>
	<%@ include file="./common/search.jsp"%>
	<div class="wp clear-fix">
		<div class="crumb">
			<a href="http://www.b5m.com">首页</a> > <a href="http://zdm.b5m.com/jiagebaogao.html">价格战报告</a> > <strong>历史最低价</strong>
		</div>
		<div class="side-menu">
			<ul id="categories">
			</ul>
		</div>
		<div class="layout-cont column-zdj clear-fix">
			<ul class="clear-fix">
			<c:forEach items="${pageView.records}" var="bean">
				<li><a class="pic" href="${basePath}item/${bean.oid }.html" target="_blank"> <img src="${bean.picUrl }" /> <small> <strong>${bean.title }</strong> <span><b>${bean.curPrice }元</b>${bean.source }</span>
							<u data-url="${bean.url }">去购买</u>
					</small>
				</a> <a href="${basePath}item/${bean.oid }.html" target="_blank" class="tit"><if > </if>${fn:substring(bean.title, 0, 20)}</a>
					<p>
						<b>当前价格： ￥${bean.curPrice }</b>
					</p>
					<p>上月价格： ￥${bean.lastMonthPrice }</p>
					<p>
						<u>${bean.source }</u>历史最高价：￥${bean.hisHighPrice }
					</p> <a class="bt_yellow" href="${basePath}item/${bean.oid }.html" target="_blank">去看看</a></li>
					</c:forEach>
			</ul>
			<%@ include file="./common/page.jsp"%>
		</div>
	</div>
	<input type="hidden" id="monthId" value="${monthId }">
	<script>
		var curCategory = "${curCategory}";
	</script>
	<script src="http://y.b5mcdn.com/scripts/jiagebaogao/jiagebaogaoFed.js?t=${today}"></script>
	<script src="${basePath}js/js/price-report.js?t=${today}"></script>
	<script>
		$(function() {
			jiagebaogaoFed.lishizuidiLoadFun();
			getCategory("detail");
			gotoBuy();
		})
	</script>
	<%@ include file="./common/footer.jsp"%>
</body>
</html>