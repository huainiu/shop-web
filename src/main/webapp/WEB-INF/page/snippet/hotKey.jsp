<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ page session="false"%> 

<c:set var="b5mUrl" value="${basePath}"/>
<c:set var="b5mUrlSearch" value="${b5mUrl}${defSearchUrlNoKey}"/>

<div id="key" class="clear-fix">
<div class="key-list">
	<ul class="clear-fix">
		<li>
			<a href="${b5mUrlSearch}iPhone.html" target="_blank" class="on">iPhone</a>
			<a href="${b5mUrlSearch}%E5%AE%89%E5%8D%93.html" target="_blank">安卓</a>
			<a href="${b5mUrlSearch}%E6%99%BA%E8%83%BD%E6%89%8B%E6%9C%BA.html" target="_blank">智能手机</a>
			<a href="${b5mUrlSearch}%E4%B8%89%E6%98%9F.html" target="_blank">三星</a>
			<a href="${b5mUrlSearch}%E5%8F%8C%E6%A0%B8.html" target="_blank">双核</a>
		</li>
		<li>
			<a href="${b5mUrlSearch}%E9%9B%AA%E7%BA%BA.html" target="_blank" class="on">雪纺</a>
			<a href="${b5mUrlSearch}%E5%BC%80%E8%A1%AB.html" target="_blank">开衫</a>
			<a href="${b5mUrlSearch}%E5%B0%8F%E8%A5%BF%E8%A3%85.html" target="_blank">小西装</a>
			<a href="${b5mUrlSearch}%E9%9F%A9%E7%89%88.html" target="_blank">韩版</a>
			<a href="${b5mUrlSearch}%E6%98%BE%E7%98%A6.html" target="_blank">显瘦</a>
		</li>
		<li>
			<a href="${b5mUrlSearch}%E9%98%B2%E6%99%92.html" target="_blank" class="on">防晒</a>
			<a href="${b5mUrlSearch}%E7%BE%8E%E7%99%BD.html" target="_blank">美白</a>
			<a href="${b5mUrlSearch}%E8%84%B1%E6%AF%9B.html" target="_blank">脱毛</a>
			<a href="${b5mUrlSearch}%E9%9A%94%E7%A6%BB.html" target="_blank">隔离</a>
			<a href="${b5mUrlSearch}%E5%8D%B8%E5%A6%86.html" target="_blank">卸妆</a>
		</li>
		<li>
			<a href="${b5mUrlSearch}%E5%B0%8F%E7%B1%B3.html" target="_blank">小米</a>
			<a href="${b5mUrlSearch}%E5%A4%A7%E5%B1%8F%E6%89%8B%E6%9C%BA.html" target="_blank" class="on">大屏手机</a>
			<a href="${b5mUrlSearch}HTC One.html" target="_blank">HTC One</a>
			<a href="${b5mUrlSearch}%E8%81%94%E6%83%B3.html" target="_blank">联想</a>
			<a href="${b5mUrlSearch}%E7%B4%A2%E5%B0%BC.html" target="_blank">索尼</a>
		</li>
		<li>
			<a href="${b5mUrlSearch}%E8%83%8C%E5%BF%83.html" target="_blank" class="on">背心</a>
			<a href="${b5mUrlSearch}%E5%B0%8F%E8%84%9A%E8%A3%A4.html" target="_blank">小脚裤</a>
			<a href="${b5mUrlSearch}%E5%93%88%E4%BC%A6%E8%A3%A4.html" target="_blank">哈伦裤</a>
			<a href="${b5mUrlSearch}%E9%93%85%E7%AC%94%E8%A3%A4.html" target="_blank">铅笔裤</a>
			<a href="${b5mUrlSearch}%E8%BF%9E%E8%A1%A3%E8%A3%99.html" target="_blank">连衣裙</a>
		</li>
		<li>
			<a href="${b5mUrlSearch}%E7%9C%BC%E9%9C%9C.html" target="_blank">眼霜</a>
			<a href="${b5mUrlSearch}%E4%BF%9D%E6%B9%BF.html" target="_blank">保湿</a>
			<a href="${b5mUrlSearch}%E9%9D%A2%E8%86%9C.html" target="_blank" class="on">面膜</a>
			<a href="${b5mUrlSearch}%E6%B4%81%E9%9D%A2.html" target="_blank">洁面</a>
			<a href="${b5mUrlSearch}%E5%8E%BB%E9%BB%91%E5%A4%B4.html" target="_blank">去黑头</a>
		</li>
		<li>
			<a href="${b5mUrlSearch}%E9%AD%85%E6%97%8F.html" target="_blank">魅族</a>
			<a href="${b5mUrlSearch}%E4%B8%89%E6%98%9FGalaxy.html" target="_blank" class="on">三星Galaxy</a>
			<a href="${b5mUrlSearch}3G.html" target="_blank">3G</a>
			<a href="${b5mUrlSearch}Windows Mobile.html" target="_blank">Windows Mobile</a>
			<a href="${b5mUrlSearch}%E5%9B%9B%E6%A0%B8.html" target="_blank">四核</a>
		</li>
		<li>
			<a href="${b5mUrlSearch}%E5%87%89%E9%9E%8B.html" target="_blank">凉鞋</a>
			<a href="${b5mUrlSearch}%E9%B1%BC%E5%98%B4%E9%9E%8B.html" target="_blank">鱼嘴鞋</a>
			<a href="${b5mUrlSearch}%E7%9F%AD%E8%A3%A4.html" target="_blank">短裤</a>
			<a href="${b5mUrlSearch}%E7%B3%96%E6%9E%9C%E8%89%B2%E7%B3%BB.html" target="_blank">糖果色系</a>
			<a href="${b5mUrlSearch}%E7%A2%8E%E8%8A%B1.html" target="_blank" class="on">碎花</a>
		</li>
		<li>
			<a href="${b5mUrlSearch}%E9%81%AE%E7%91%95.html" target="_blank">遮瑕</a>
			<a href="${b5mUrlSearch}%E7%B2%89%E5%BA%95%E6%B6%B2.html" target="_blank" class="on">粉底液</a>
			<a href="${b5mUrlSearch}%E7%9D%AB%E6%AF%9B%E8%86%8F.html" target="_blank">睫毛膏</a>
			<a href="${b5mUrlSearch}%E7%BA%A4%E4%BD%93%E4%B9%B3.html" target="_blank">纤体乳</a>
			<a href="${b5mUrlSearch}%E7%9C%BC%E7%BA%BF%E7%AC%94.html" target="_blank">眼线笔</a>
		</li>
		<li>
			<a href="${b5mUrlSearch}%E5%8F%8C%E5%8D%A1%E5%8F%8C%E5%BE%85.html" target="_blank">双卡双待</a>
			<a href="${b5mUrlSearch}WCDMA.html" target="_blank">WCDMA</a>
			<a href="${b5mUrlSearch}%E8%B6%85%E9%95%BF%E5%BE%85%E6%9C%BA.html" target="_blank" class="on">超长待机</a>
			<a href="${b5mUrlSearch}%E8%B6%85%E8%96%84%E6%89%8B%E6%9C%BA.html" target="_blank">超薄手机</a>
			<a href="${b5mUrlSearch}%E8%AF%BA%E5%9F%BA%E4%BA%9A.html" target="_blank">诺基亚</a>
		</li>
		<li>
			<a href="${b5mUrlSearch}%E5%90%8A%E5%B8%A6.html" target="_blank" >吊带</a>
			<a href="${b5mUrlSearch}%E5%B9%B3%E5%BA%95%E9%9E%8B.html" target="_blank">平底鞋</a>
			<a href="${b5mUrlSearch}%E8%95%BE%E4%B8%9D.html" target="_blank">蕾丝</a>
			<a href="${b5mUrlSearch}%E5%8D%B0%E8%8A%B1.html" target="_blank">印花</a>
			<a href="${b5mUrlSearch}%E6%9D%A1%E7%BA%B9.html" target="_blank" class="on">条纹</a>
		</li>
		<li>
			<a href="${b5mUrlSearch}%E7%BE%8E%E7%94%B2.html" target="_blank" class="on">美甲</a>
			<a href="${b5mUrlSearch}%E9%A6%99%E5%A5%88%E5%84%BF.html" target="_blank">香奈儿</a>
			<a href="${b5mUrlSearch}%E9%9B%85%E8%AF%97%E5%85%B0%E9%BB%9B.html" target="_blank">雅诗兰黛</a>
			<a href="${b5mUrlSearch}%E4%B8%9D%E8%8A%99%E5%85%B0.html" target="_blank">丝芙兰</a>
			<a href="${b5mUrlSearch}%E7%88%BD%E8%82%A4%E6%B0%B4.html" target="_blank">爽肤水</a>
			</li>
		</ul>
	</div>
	<a href="javascript:void(0);" id="prev">prev</a>
	<a href="javascript:void(0);" id="next">next</a>
</div>
<script type="text/javascript">
/* @introduction : 文本滚动插件
 * @version : 0.1
 */
(function($){
	$.fn.extend({
		Scroll:function(opt,callback)
		{
			//参数初始化
			if(!opt)
			{
				opt = {};
			}
			var oBtnUp = $('#' + opt.up);
			var	oBtnDown = $('#' + opt.down);
			var timerID;
			var This = this.eq(0).find('ul:first');
			var lineH = This.find('li:first').height();
			var line = opt.line?parseInt(opt.line,10):parseInt(this.height()/lineH,10);
			var speed = opt.speed?parseInt(opt.speed,10):500;
			var timer = opt.timer;

			if(line === 0)
			{
				line = 1;
			}

			var upHeight = -line*lineH;

			//滚动函数
			var scrollUp = function()
			{
				oBtnUp.unbind('click',scrollUp);
				This.animate({marginTop:upHeight},speed,function(){
					for(var i=1;i<=line;i++)
					{
						This.find('li:first').appendTo(This);
					}
					This.css({marginTop:0});
					oBtnUp.bind('click',scrollUp);
				});
			};

			var scrollDown = function()
			{
				oBtnDown.unbind('click',scrollDown);
				for(var i=1;i<=line;i++)
				{
					This.find('li:last').show().prependTo(This);
				}
				This.css({marginTop:upHeight});
				This.animate({marginTop:0},speed,function(){
                    oBtnDown.bind('click',scrollDown);
				});
			};

			var autoPlay = function()
			{
				if(timer)timerID = window.setInterval(scrollUp,timer);
			};

			var autoStop = function()
			{
				if(timer)window.clearInterval(timerID);
			};

			This.hover(autoStop,autoPlay).mouseleave();
			oBtnUp.css('cursor','pointer').click(scrollUp).hover(autoStop,autoPlay);
			oBtnDown.css('cursor','pointer').click(scrollDown).hover(autoStop,autoPlay);
		}
	});
})(jQuery);

/**
 * 热搜词滚动
 */
$(function(){
	//调用文本插件
	$('.key-list').Scroll({line:1,speed:500,timer:5000,up:'prev',down:'next'});

	//下载插件动画效果
	function startMove()
	{
		var oPlugTools= $('.plug-tools img');
		oPlugTools.hover(function(){
			oPlugTools.stop(true).animate({'margin-top':'0'});

		},function(){
			oPlugTools.stop(true).animate({'margin-top':'10px'});
		});
	}

	startMove();

	//顶部用户昵称下拉菜单效果
	$('.logged .per').hover(function(){
		$(this).addClass('hover');
	},function(){
		$(this).removeClass('hover');
	});

	//插件提示效果
	$(window).load(function()
	{
		setTimeout(function()
		{
			if(!$('#b5mmain').length)
			{
				$("#plug-bar").slideDown("slow");
			}
		},500);
	});

	//关闭插件
	function closed()
	{
		$("#plug-bar").slideUp("normal");
	}
});
</script>