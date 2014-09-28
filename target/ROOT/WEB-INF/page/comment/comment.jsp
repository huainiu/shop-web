<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="say" id='supplierId' val='${suppliser.id}'>
	<div class="say-hd">
		<h2>大家对Ta的评价</h2>
	</div>
	<div class="say-bd">
		<div class="layout clear-fix">
			<div class="layout-name fl">我的印象：<span>*</span></div>
			<div class="layout-cont fl">
				<div class="tags" val="0">
					<a href="javascript:void(0);" class="well">很好</a>
					<a href="javascript:void(0);" class="general">一般</a>
					<a href="javascript:void(0);" class="poor">差劲</a>
				</div>
				<p class="fast-go">已有帮5买帐号？<a href="javascript:void(0)">快速登录</a></p>
			</div>
		</div>
		<div class="layout clear-fix">
			<div class="layout-name fl">我的评价：<span>*</span></div>
			<div class="layout-cont fl">
				<div class="coms">
					<textarea id="J_comment" onkeyup="tapKeyUp(event)"></textarea>
				</div>
			</div>
		</div>
		<div class="layout clear-fix">
			<div class="layout-name fl">验证码：<span>*</span></div>
			<div class="layout-cont fl">
				<div class="vers">
					<input type="text" class="fl" id="validateCode">
					<img src="${basePath}randomcode.do" class="fl" id="validateImg">
					<span class="fl" id="change">看不清？<a href="javascript:void(0);">换一张</a></span>
				</div>
			</div>
			<div class="fr" id="submit"><a class="send-content" href="javascript:;">发布</a></div>
		</div>
	</div>
</div>

<script>
	$(function(){
		var login = ${user_session_login !=null && user_session_login == 'true'};
		if(login){
			$(".fast-go").hide();
		}
		$('#submit').click(function() {
			if ($('#J_comment').val() == null || $.trim($('#J_comment').val()) == '') {
				showTips("请输入评论内容", -1);
				return;
			} else if ($('#J_comment').val().length < 10) {
				showTips("内容不少于10字", -1);
				return;
			}else if ($('#validateCode').val() == null || $.trim($('#validateCode').val()) == '') {
				showTips("请输入验证码", -1);
				return;
			}
			$.ajax({  
	          type : 'POST',  
	          url : '${basePath}/dianping/comments/add.htm',  
	          data : {'supplierId': $('#supplierId').attr('val'), 'content': $('#J_comment').val(), 'type' : $('div.tags').attr('val'), 'validateCode': $('#validateCode').val()},  
	          dataType : 'json',  
	          success : function(data) {  
	        	if (data.val == true) {
	        		showTips("评论成功，正在审核", data.code);
	        		$('#J_comment').val("");
	        		$('#validateCode').val("");
	        	} else {
	        		showTips(data.msg, data.code);
	        	}
	        	changValidaImg();
	          },  
	          error : function(data) {  }  
		    });  
		});
		$('#change').bind('click', changValidaImg);
		$('#validateImg').bind('click', changValidaImg);
		$('div.tags a.well').click(function() {
			clear();
			setType(0);
			$(this).css({'background':'#39b54a','color':'#fff'}); 
		});
		$('div.tags a.general').click(function() {
			clear();
			setType(1);
			$(this).css({'background':'#f68e56','color':'#fff'}); 
		});
		$('div.tags a.poor').click(function() {
			clear();
			setType(2);
			$(this).css({'background':'#ed1c24','color':'#fff'}); 
		});
		$('p.fast-go a').click(function(){
			window.location.href = "${loginAndRegisterURL}user/user/tologins.htm?redirectUrl=" + encodeURIComponent(window.location.href);
		});
		$('div.tags a.well').trigger('click');
	});
	//限制字数
	function tapKeyUp(e) {
		var src = e.srcElement ? e.srcElement : e.target;
		var content = $.trim(src.value);
		if (content.length > 150) {
			src.value = content.substring(0, 150);
		}
		return true;
	}
	function clear() {
		$('div.tags a').css({'background':'#fff', 'color': '#666'});
	}
	function setType(i) {
		$('div.tags').attr('val', i);
	}
	function changValidaImg() {
		$('#validateImg').attr('src', '${basePath}randomcode.do?time=' + new Date().valueOf());
	}
</script>