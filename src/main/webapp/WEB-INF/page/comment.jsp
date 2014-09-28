<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html>
	<head>
		<c:import url="./common/meta.jsp"/>
		<title>${source}点评 - 帮5买 - 轻松发现，轻松选择！</title>
		<meta name="keywords" content="${source}点评 - 帮5买, qJyhebt0XrpmTrWtjlhJMdYXGD8" />
		<meta name="description" content="${source}点评 - 帮5买" />
		<meta http-equiv="x-ua-compatible" content="ie=8" />
		<link rel="stylesheet" type="text/css" href="${basePath}css/new_header.css?t=${today}">
		<link rel="stylesheet" href="http://y.b5mcdn.com/css/common/common.css?t=${today}" />
		<link rel="stylesheet" type="text/css" href="${basePath}css/review.css?t=${today}">
		<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
		<script type='text/javascript' src='http://y.b5mcdn.com/scripts/common/common.js?t=${today}'></script>
		<!--[if lte IE 6]>
		<script src="${basePath}js/js/DD_belatedPNG_0.0.8a.js?t=${today}"></script>
		<script type="text/javascript">
			DD_belatedPNG.fix('.top .logo-pic b,.top .site-circle,.top .site-talk p,#wrap .impress-more .more b,#wrap .impress-bd .add-tag span,.tips .success i, .tips .waring i,.tips .close,.comment-sort .default-bg, .comment-sort .default-bg span');
		</script>
		<![endif]-->
	</head>
	<body>
		<!--public-bar s-->
		<c:import url="./common/topBar.jsp"></c:import>
		<!--public-bar e-->
		<!--top s-->
		<div class="top" id="J_top">
			<div class="site">
				<div style="position:absolute;left:26px;top:15px">
					<div class="logo-pic"><span><a href="${suppliser.url}" target="_blank"	><img src="${suppliser.logo}"></a></span><b></b></div>
				</div>
				<div class="logo-info">
					<h2></h2>
					<p></p>
				</div>
				<div class="site-score">
					<fmt:formatNumber type="number" var="percent" value="${suppliser.percent/10}" maxFractionDigits="0"/>
					<%-- <fmt:formatNumber type="number" var="circle" value="${(suppliser.percent+5)/10-2}" maxFractionDigits="0"/> --%>
					<fmt:formatNumber type="number" var="circle" value="${(suppliser.percent)/12.5}" maxFractionDigits="0"/>
					<div class="site-circle s${circle }"></div>
					<div class="site-think">
						<p><b>${suppliser.percent}%</b>好评</p>
						<c:set var="status" value="${suppliser.percent < 50 ? 'p': (suppliser.percent == 50 ? 'g': 'w')}"></c:set>
						<c:set var="text" value="${suppliser.percent < 50 ? '差劲': (suppliser.percent == 50 ? '一般': '很好')}"></c:set>
						<p>大家认为Ta<em class="${status}">${text}</em></p>
					</div>
				</div>
				<div class="site-talk">
					<p>谈谈我的印象</p>
					<a href="javascript:void(0);" id="J_talk">我要点评</a>
				</div>
			</div>
		</div>		
		<div class="place_blank"></div>
		<!--top e-->

		<!--wrap s-->
		<div id="wrap" class="clear-fix">
			<div class="col-main">
				<c:import url="./comment/impress.jsp"/>
				<!--e-->
				<!--s-->
				<c:import url="./comment/splitcomment.jsp"/>
				<!--e-->
				<!--s-->
				<c:import url="./comment/comment.jsp"/>
				<!--e-->
			</div>
			<c:import url="./comment/left.jsp"/>
		</div>
		<!--wrap e-->
		<!--footer s-->
		<c:import url="./common/footer.jsp"></c:import>
		<!--footer e-->
		<script>
			$(function(){
				var offset_top = $('#J_top').offset().top;

				$(window).scroll(function(){
					var w_scrollTop = $(window).scrollTop();

					if(w_scrollTop > offset_top){
						$('#J_top').addClass('nav-fixed');
						$('.tips').addClass('fixed_pos1');
					}
					if(w_scrollTop <= offset_top){
						$('#J_top').removeClass('nav-fixed');
						$('.tips').removeClass('fixed_pos1');
					}
				});

				$(document).on('click','#J_talk',function(){
					var $comment = $('#J_comment'),
						com_top = $comment.offset().top;
						$comment.focus();
				});
				$('.tips').click(closeTip);
			});
			function showTips(text, code){
					var $tips;
					var span;
					if (code == 0) {
						$tips = $('.tips1');
						span = $tips.find('span.success');
					}
					else {
						$tips = $('.tips2');
						span = $tips.find('span.waring');
					}
					span.html('').append('<i></i>'+text);
					closeTip();
					$tips.show().delay(3000).fadeOut();
			}
			function closeTip() {
				$('.tips').hide();
			}
		</script>
	</body>
</html>