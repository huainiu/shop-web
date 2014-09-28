<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ page session="false"%>
<%
	String server = request.getServerName();
	StringBuilder sb = new StringBuilder();
	sb.append(request.getScheme()).append("://").append(server)
			.append(":").append(request.getServerPort())
			.append(request.getContextPath()).append("/");
	request.setAttribute("basePath", sb.toString());
	request.setAttribute("server", server);
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="${basePath}" />
<title>购物搜索_比价购物搜索-帮5买购物搜索引擎</title>
<meta name="keywords" content="购物搜索,比价购物,比价购物搜索,购物搜索引擎,帮5买" />
<meta name="description" content="帮5买购物搜索是中国最大的比价购物搜索引擎，独立购物搜索门户，通过10年积累购物搜索技术和多元化的垂直搜索，帮助大家解决“买什么”和“去哪儿买”的购物搜索问题。" />
<meta name="data-mps" content="1016">
<link rel="stylesheet" href="http://y.b5mcdn.com/css/common/common.css?t=${today}" />
<link rel="stylesheet" type="text/css" href="http://y.b5mcdn.com/css/common/head_b.css?t=${today}" />
<link rel="stylesheet" type="text/css" href="http://y.b5mcdn.com/css/search/search_index.css?t=${today}" />
</head>
<script type="text/javascript">
	var _basePath = "${basePath}";
	var _server = "${server}";
</script>
<body>
	<!-- topbar -->
	<jsp:include page="./common/topBar.jsp" />
	<!-- body -->
	<div class="wraper">
		<div class="search-index">
			<a href="http://www.b5m.com" class="logo"><img src="${basePath }images/b5m-logo.png" alt="帮5买-轻松发现-轻松选择" width="200px"></a>
			<div class="search-tools">
				<form class="search-form" id="frm1" onsubmit="return searchfrm(this, this.id)" target="_blank" name="frm1" method="post">
					<input type="hidden" value="http://s.b5m.com/s/Search?key=[##]" id="urlfrm1"> 
					<span class="header-search-content"> 
						<label for="搜全网">搜索从这里开始</label> 
						<input type="text" autofocus class="header-search-key J_search J_autofill" id="query" x-webkit-speech="" value="${keyword}" placeholder="快速搜索全网旅游特价商品，景点、酒店"> 
						<a class="header-rearch-submit" href="javascript:void(0);" target="_self" onclick="searchfrm(document.frm1, 'frm1');if (document.getElementById('query').value != '')document.frm1.submit();">搜全网</a>
					</span>
				</form>
			</div>
		</div>
	</div>
	<!--footer s-->
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
	<script src="http://y.b5mcdn.com/scripts/search/suiHeader.min.js?t=${today}" type="text/javascript"></script>
	<input autocomplete="off" type="hidden" id="ajaxCollection" value="b5mo" />
	<input autocomplete="off" type="hidden" id="hdnCurIndex" />
	<input autocomplete="off" type="hidden" id="hdnKeyTemp" />
	<input autocomplete="off" type="hidden" id="hdnTarget" value="${basePath}${defSearchUrlNoKey}%23keyword%23.html " />
	<c:import url="./common/footer.jsp"></c:import>
	<script type="text/javascript">
		$('.J_search').focus();
		
		$(function() {
			$.ajax({
				type : "get",
				url : "${basePath}/api/hot/keywords.htm?channel=b5mp&size=6",
				success : function(json) {
					var val = json.val;
					if (json.code == 0 && val.length > 0) {
						var html = '<span class="header-rearch-hotkey"> 热门搜索： ';
						for ( var i = 0; i < val.length; i++) {
							var word = val[i];
							html += '<a href="http://s.b5m.com/s/Search?key='+word.name + '" data-attr="1005">' + word.name + '</a> ';
						}
						html += "</span>";
						$(".header-search-content").after(html);
					}
				}
			})
		})
	</script>
</body>
</html>