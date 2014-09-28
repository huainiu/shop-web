<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="false"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<link rel="stylesheet" href="http://staticcdn.b5m.com/css/haiwai/header.css?v=${today }">
<div class="top-hdbanner"></div>
<div class="tpbar"></div>
<div class="header">
	<div class="header-box cfx">
		<h1 class="header-logo fl">
			<a title="韩国馆" href="/"><img src="http://staticcdn.b5m.com/images/logos/haiwai.png" alt="韩国馆"></a>
		</h1>
		<div class="logo-banner">
			<a data-mps="21045" href="http://www.b5m.com/qwdh.html"><img src="http://cdn.bang5mai.com/upload/web/cmsphp//link/201408/d80e6b5d0e0117202633.jpg" alt="" width="80" height="80"></a>
		</div>
		<div class="header-tools-search" style="margin: 25px 0px 0px 33px;">
			<form action="http://haiwai.b5m.com" onsubmit="if(document.getElementById('scbar_txt').value=='')return false;form_submit()" autocomplete="off" method="get" name="scbar_form" id="scbar_form">
				<span class="header-search-content"> <input data-attr="1005" type="text" name="key" x-webkit-grammar="builtin:search" x-webkit-speech="" autocomplete="off" id="scbar_txt" placeholder="快速搜索全网最新商品与资讯" value="" class="header-search-key J_autofill af-input act"> <input type="submit" class="header-rearch-submit" onclick="if(document.getElementById('scbar_txt').value!='')setTimeout(form_submit(this),0);" value="搜全网">
				</span>
			</form>
			<script type="text/javascript">
				function form_submit() {
					var value = document.getElementById('scbar_txt').value;
					value = value.replace(/(^\s*)|(\s*$)/g, "");

					if (value != '') {
						var keywords = value.split("/");
						if (keywords.length > 1) {
							value = '';
							for ( var i = 0; i < keywords.length; i++) {
								if (keywords[i] != "" && keywords[i] != null) {
									value += keywords[i];
									if (i < (keywords.length - 1)) {
										value += " ";
									}
								} else {
									if (i < (keywords.length - 1)) {
										value += " ";
									}
								}
							}
						}
						document.getElementById("scbar_txt").value = value;
						document.scbar_form.action = "http://haiwai.b5m.com/s/Search";
						if (/msie/i.test(navigator.userAgent)) { //ie brower
							setTimeout(function() {
								document.scbar_form.submit();
							}, 0);
						} else {
							var e = document.createEvent('MouseEvent');
							e.initEvent('click', false, false);
							setTimeout(function() {
								document.scbar_form.submit();
							}, 0);
						}
					}
				}
			</script>
		</div>
		<div class="top-hot-key"></div>
	</div>
	<div class="header-nav-box">
		<div class="wp">
			<div class="header-nav-back">
				<a data-attr="1004" href="http://www.b5m.com/" title="帮5买首页" class="icon"><u>帮5买首页</u></a><s class="header-nav-back-s2"></s><s></s>
			</div>
			<ul>
				<li class="index cur"><a data-attr="1004" href="http://haiwai.b5m.com" target="_self">首页</a></li>
				<li><i class="hot"></i><a data-attr="1004" target="_blank" href="http://korea.b5m.com">韩国馆</a></li>
				<li><a data-attr="1004" target="_blank" href="http://usa.b5m.com">美国馆</a></li>
				<li><a data-attr="1004" target="_blank" href="http://haiwai.b5m.com/zhuanti-all-1.html">专题</a></li>
			</ul>
		</div>
	</div>
</div>