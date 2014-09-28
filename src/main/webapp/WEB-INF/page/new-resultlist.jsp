<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ taglib prefix="page" uri="http://www.b5m.com/tag/page"%>
<%@ page session="false"%>
<%
	request.setAttribute("collectionName", "clothing/");
%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<%@ include file="./common/meta.jsp"%>
<title>${seotitle}推荐，${seotitle}哪个好，${seotitle}哪款好，${seotitle}比价-${currentPage}${currentPage == '' ? '' : '-'}${_category}${_category == null ? '' : '-'}帮5买</title>
<meta name="keywords" content="${seotitle}推荐,${seotitle}哪个好,${seotitle}哪款好,帮5买" />
<meta name="description" content="帮5买${seotitle}推荐${_category}频道，为您解答${seotitle}哪个好？${seotitle}哪款好？帮5买比价网，给您最佳${seotitle}推荐。" />
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/autofill_fushi.css?t=${today}">
<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_fushi_v1.css?t=${today}">
</head>
<body>
	<%@ include file="./common/search.jsp"%>
	<div class="wp">
		<div class="breadcrumb cf mt20 mb20">
			<h2 class="l">
				<c:if test="${fn:length(navigationList.navigations) >= 1}">&nbsp;&nbsp;</c:if>
				<c:choose>
					<c:when test="${refineQuery != null && refineQuery != ''}">
						以下为您显示“<em class="search-key cl-ff78">${refineQuery}</em>”的搜索结果。 仍然搜索：“
						<a href="${basePath}search/s/___image________________${orignKeyword}.html?refine=true"><em class="search-key cl-ff78">${orignKeyword}</em></a>”
					</c:when>
					<c:when test="${keyword != '*' || orignKeyword != '*'}">搜索“<em class="search-key search-key cl-ff78">${keyword != '*' ? keyword : orignKeyword}</em>”</c:when>
				</c:choose>
			</h2>
			<div class="r">
				在${includeSourceCount}个商家中，找到相关商品<strong class="product-nums cl-ff78">${itemCount}</strong>个
			</div>
		</div>
	</div>
	<div class="wp cf">
		<div class="main l">
			<div class="container">
				<div class="brand-panel clear-fix" style="display: none;">
					<div class="brand-logo fl"></div>
					<div class="brand-des cell"></div>
				</div>
				<!--检索区 s-->
				<jsp:include page="snippet/filterAttr.jsp"></jsp:include>
				<!--检索区 e-->
				<!--搜索条件 e-->
				<div class="sort-bar cf">
					<dl class="sort-item">
						<dt>排序：</dt>
						<c:forEach items="${sortList}" var="sort" varStatus="stat">
							<c:set var="btnCls" value="sort-btn" />
							<c:if test="${sort.text=='销量'}">
								<c:set var="cur" value="${sortField=='SalesAmount'}" />
							</c:if>
							<c:set var="priceCls" value=""></c:set>
							<c:if test="${sort.text=='价格'}">
								<c:set var="btnCls" value="sort-btn btn-price" />
								<c:set var="cur" value="${sortField=='Price'}" />
								<c:if test="${sortField=='Price'}">
									<c:set var="priceCls" value="${sortType == 'DESC'? 'price-down' : (sortType == 'ASC' ? 'up' : '')}"></c:set>
								</c:if>
							</c:if>
							<dd class="">
								<a class="${btnCls } ${priceCls == '' ? (cur?'up':'') : priceCls}" rel="nofollow" data-attr='1007' href="${sort.url}">${sort.text} <c:if test="${sort.text=='价格'}">
										<i></i>
									</c:if>
								</a>
							</dd>
						</c:forEach>
					</dl>
				</div>
				<!--搜索条件 e-->
				<div class="goods-list">
					<%@ include file="./snippet/clothingView.jsp"%>
					<page:page pageView="${pageView}" pageClass="page cf mt20 mb20"></page:page>
					<!--page e-->
				</div>
				<c:if test="${fn:length(relatedQueryList) > 0}">
					<dl class="intelligent-recommend">
						<dt>您是不是想找：</dt>
						<dd>
							<c:forEach var="relatedQuery" items="${relatedQueryList}">
								<a data-attr="1010" href="${basePath}clothing/${relatedQuery.text}.html">${relatedQuery.text}</a>
							</c:forEach>
						</dd>
					</dl>
				</c:if>
			</div>
		</div>
		<div class="side side-l l">
			<div class="m-bord">
				<c:if test="${fn:length(categoryList.linkTree)>0}">
					<div class="s-menu-list " id="J_category_nav">
						<!-- 全部商品分类 e -->
						<%@ include file="./snippet/category_list.jsp"%>
					</div>
				</c:if>
				<div class="goods-filter">
					<!--商家筛选 s-->
					<h3 class="s-icon goods-f">商家筛选</h3>
					<%@ include file="./snippet/source_list.jsp"%>
					<!--商家筛选 e-->
				</div>
			</div>
			<div class="goods-history cf mt20">
				<h3 class="his-title">历史浏览商品</h3>
				<ul class="grid-view">
				</ul>
			</div>
		</div>
	</div>

	<div class="b5m-share-mod-no"></div>

	<!--淘特价 e-->
	<input id="keyword" type="hidden" value="${keyword}">
	<input id="cookieId" type="hidden" value="${cookieId}">
	<input id="orignKeyWord" type="hidden" value="${orignKeyword}">
	<input id="category" type="hidden" value="${category}">
	<c:set var="sourceSize" value="${fn:length(docResourceDtos)}"></c:set>
	<fmt:formatNumber type="number" value="${sourceSize/4 + 1 + (sourceSize%4 == 0 ? 0 : 1)}" maxFractionDigits="0" var="adSize" />
	<!--底部 s-->
	<%@ include file="./common/footer.jsp"%>
	<!-- <a href="javascript:void(0);" class="goToSearchPage"></a> -->
	<!--底部 e-->
	<script src="http://y.b5mcdn.com/scripts/search/search_v3.js?t=${today}"></script>
	<!--[if lt IE 9]>
	<script src="http://y.b5mcdn.com/scripts/search/media_query_v3.js?t=${today}"></script>
	<![endif]-->
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/imglazyload.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/autoFill_Fushi.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/fushi_search_v1.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/good_fav.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/static/public/sea-modules/seajs/seajs/2.1.1/sea.js"></script>
	<script type="text/javascript">
		$(function() {
			$("img").imglazyload({
				fadeIn : true
			});
			$('#cancel-filter').click(function(evt) {
				$(this).attr('href', "/clothing/${keyword}.html");
			});
			$('li.grid-ls').on('click', function() {
				setHistory($(this));
			});
			var params = [];
			$('li.grid-ls').each(function() {
				params.push({
					id :"#"+$(this).find(".goods-share").attr('id'),
					href : location.href,        //$(this).find("#originalUrl").val(), 
					content : $(this).find("#shopTitle").val(),
					pic: $(this).find("#picUrl").val().split(','),
					title:"帮5买搜索频道",
					type : 2
				});
				var span = $(this).find(".span-all-comment");
				var url = span.attr("attr-url");
				span.click(function(){
					window.open(url);
				});
			});
			initShare(params);
			showHistory();
			$(".feed-awards").remove();
			/* if (location.href.indexOf("_") > 0) {
				$('.goToSearchPage').attr("href", location.href.replace("/clothing", "/search/s"));
			} else {
				$('.goToSearchPage').attr("href", location.href.replace("/clothing", ""));
			} */
		});
		$("a").click(function() {
			var $this = $(this);
			var linkurl = $this.attr("linkurl");
			if (linkurl) {
				$this.attr("href", linkurl);
			}
			var localhref = window.location.href;
			if (!localhref || localhref.indexOf("?") <= 0)
				return;
			var $thisHref = $this.attr("href");
			var params = localhref.split("?")[1];
			if ($thisHref && $thisHref.indexOf("?") < 0) {
				$this.attr("href", $thisHref + "?" + params);
			}
		});

		/* 多选搜索*/
		function searchMulti(attrs) {
			var pathname = location.pathname;
			var category = $("#category").val();
			if (!category)
				category = "";
			var ss = pathname.split("_");
			if (ss.length < 2) {
				pathname = _basePath + "clothing/" + category + "___image__" + attrs + "______________" + $("#orignKeyWord").val() + ".html";
			} else {
				var pos = pathname.indexOf('clothing/');
				var pre = pathname.substring(0, pos + 3);
				var filename = pathname.substring(pos + 3);
				var conds = filename.split('_');
				conds[5] = conds[5] + "," + attrs;
				conds[13] = "";
				filename = conds.join('_');
				pathname = pre + filename;
			}
			if ($("#compareprice").attr("value") == "true") {
				pathname += "?compare=true";
			}
			goUrlByATag(pathname);
		}
		var his_length = 5;
		function setHistory(e) {
			var his = Cookies.get('his_new');
			var url = e.find('#originalUrl').val();
			var pic = e.find(".grid-mod-pic").attr('src');
			var title = e.find(".summary a").attr('title');
			var price = e.find("span.cl-f00").html();
			var val = url + "^" + pic + "^" + title + "^" + price;
			if (his && his != "undefined") {
				var array = his.split(";");
				var flag = true;
				for ( var i = 0; i < array.length; i++) {
					if (array[i].split("^")[0] == url) {
						flag = false;
						break;
					}
				}
				if (flag) {
					if (array.length == his_length) {
						array.shift();
					}
					array.push(val);
					his = array.join(";");
					Cookies.set('his_new', his);
					if ($(".goods-history .grid-view").find('li.grid-ls').length == his_length) {
						$(".goods-history .grid-view").find('li.grid-ls')[his_length-1].remove();
					} 
					$(".goods-history .grid-view").prepend(addHistory(val.split(";")[0].split("^")));
				}
			} else {
				Cookies.set('his_new', val);
				$(".goods-history .grid-view").html(addHistory(val.split(";")[0].split("^")));
			}
		}

		function showHistory() {
			var history_info = Cookies.get("his_new");
			var content = "";
			if (history_info && history_info != "undefined") {
				var history_arg = history_info.split(";");
				var i;
				var length = history_arg.length;
				for (i = length -1; i >= 0; i--) {
					if (history_arg[i] && history_arg[i] != "undefined") {
						var wlink = history_arg[i].split("^");
						content += addHistory(wlink);
					}
				}
				$(".goods-history .grid-view").html(content);
			} else {
				$(".goods-history .grid-view").html("对不起，您没有任何浏览记录");
			}
		}

		function addHistory(wlink) {
			var dom = '<li class="grid-ls"><div class="grid-mod"><div class="grid-in"><div class="pic-wrap"> <div class="pic-mod">' + ' <a class="pic" href="'+wlink[0]+'" target="_blank"><img src="'+wlink[1]+'" alt=""></a> </div> </div> <div class="summary">' + '<a href="'+wlink[0]+'">' + wlink[2] + '</a></div> <div class="price-mod"> <strong class="price">' + '<b>¥</b><span  class="cl-f00">' + wlink[3] + '</span></strong> </div> </div> </div> </li>';
			return dom;
		}

		function initShare(params) {
			seajs.use('m/share/1.0.0/share.js', function(Shared) {
				new Shared({
					shareMode : false,
					id : ".b5m-share-mod-no",
					params : params,
				});
			});
		}
	</script>
</body>
</html>