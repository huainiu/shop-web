<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>
<div class="footer">
	<div class="footer-links">
		<a target="_blank" class="item" href="http://www.b5m.com/about">关于帮5买</a> | <a target="_blank" class="item" href="http://www.b5m.com/about/contact">联系我们</a> | <a target="_blank" class="item" href="http://www.b5m.com/about/business">合作商家</a> | <a target="_blank" class="item" href="http://www.b5m.com/about/links">友情链接</a> | <a target="_blank" class="item" href="http://www.b5m.com/about/job">加入我们</a> | <a target="_blank" class="item" href="http://app.b5m.com/index.html">移动应用</a> | <a target="_blank" class="item" href="http://www.b5m.com/sitemap.html">站点地图</a> | <a style="margin: 0 0 0 10px;" target="_blank" class="item" href="http://www.b5m.com/zixunhot.html">热门</a><a style="margin: 0px;" target="_blank"
			class="item" href="http://s.b5m.com/tag/keywords.html">标签</a> | <a target="_blank" class="item" href="http://u.b5m.com">广告联盟</a> | <a target="_blank" class="item" href="http://ad.b5m.com/tuiguang.html">卖家中心</a>
	</div>
	<div class="footer-copyright">
		Copyright © 2011-2014 B5M.COM. All rights reserved. Powered by B5Msoft Co.,Ltd. <br> 增值电信业务经营许可证：沪B2-20130065号 | 中华人民共和国互联网药品信息服务资格证书（沪）-非经营性-2013-0034 <a href="http://www.b5m.com/yiliaozhengshu.html">沪卫(中医)网审【2014】10032号</a>
	</div>
	<div class="footer-public">
		<a target="_blank" href="http://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&amp;entyId=20120620142952978"> <img src="http://y.b5mcdn.com/static/images/common/shgs.gif" alt="上海工商">
		</a> <a id="___szfw_logo___" href="https://search.szfw.org/cert/l/CX20140423003765008027" target="_blank"> <img src="http://y.b5mcdn.com/static/images/common/cxwz.png" alt="诚信网站">
		</a>
		<script type="text/javascript">
			/* (function() {
				document.getElementById("___szfw_logo___").oncontextmenu = function() {
					return false;
				}
			})(); */
		</script>
		<a target="_blank" href="http://www.315online.com.cn/member/315120051.html"> <img src="http://y.b5mcdn.com/static/images/common/315.gif" alt="网站交易保障中心安全网购门户">
		</a>
		<!--可信网站图片LOGO安装开始-->
		<!-- <script src="http://kxlogo.knet.cn/seallogo.dll?sn=e14050531011548491ouit000000&size=0"></script> -->
		<!--可信网站图片LOGO安装结束-->
	</div>
</div>
<script id="b5m-libs" type="text/javascript" src="http://y.b5mcdn.com/static/public/sea-modules/dist/libs.js" rootpath="http://y.b5mcdn.com/static/public/sea-modules/dist" version="${today}"></script>
<script type="text/javascript" src="http://y.b5mcdn.com/??,scripts/common/common.js,scripts/common/autoFill.js,scripts/search/suiHeader.js?t=${today}"></script>
<script type="text/javascript">
	$(function() {
		$('.J_autofill').autoFill('', '${c==null?"b5mo":c}');
	});
</script>
<c:if test="${needShowAdRecord == '' || _needShowAdRecord || needShowAdRecord == null}">
	<script type="text/javascript">
		_atrk_opts = {
			atrk_acct : "InfVh1aUXR00ax",
			domain : "b5m.com",
			dynamic : true
		};
		(function() {
			var as = document.createElement('script');
			as.type = 'text/javascript';
			as.async = true;
			as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js?t=${today}";
			var s = document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(as, s);
		})();
	</script>
	<noscript>
		<img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=InfVh1aUXR00ax" style="display: none" height="1" width="1" alt="" />
	</noscript>
	<script type='text/javascript' src='http://cdn.bang5mai.com/upload/web/public/app/tongji/stat2.min.js?t=${today}'></script>
</c:if>