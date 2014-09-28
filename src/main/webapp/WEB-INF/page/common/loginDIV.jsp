<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="false"%>
<div id="fixed-login" style="display: none;">
	<div id="fixed-login-inner">
		<div class="login-guide"></div>
		<div class="login-form">
			<input type="email" value="" onkeyup="tapKeyUp(event)" onkeypress="tapKeyUp(event)" onkeydown="tapKeyUp(event)" placeholder="邮箱"
				class="email"> <input type="password" value="" onkeyup="tapKeyUp(event)" onkeypress="tapKeyUp(event)"
				onkeydown="tapKeyUp(event)" placeholder="密码" class="password"> <a href="javascript:void(0)" id="fixed-login-button">登 录</a>
		</div>
		<div class="login-reg" style="top: 35px">
		<!-- <div class="login-reg"> -->
			还没有帐号？ <a href="${loginAndRegisterURL}forward.htm?method=/user/info/register&userType=8" target="_blank">立即注册&gt;</a>
		</div>
		<div class="login-share" style="display: none;">
		<!-- <div class="login-share"> -->
			<a href="${loginAndRegisterURL}user/user/auth.htm?type=2&refererUrl="><b class="wb"></b>微博登录</a> <a
				href="${loginAndRegisterURL}user/user/auth.htm?type=1&refererUrl="><b class="qq"></b>QQ登录</a> <a
				href="${loginAndRegisterURL}user/user/auth.htm?type=3&refererUrl="><b class="tb"></b>淘宝登录</a>
			<script type="text/javascript">
				$('div.login-share a').each(function() {
					$(this).attr("href", $(this).attr("href") + window.location.href);
				});
			</script>
		</div>
		<a href="javascript:;" class="login-closed">关闭</a>
	</div>
</div>

<script type="text/javascript">
	var isBtnClose = false;
	$(function() {
		$('a.login-closed').click(function() {
			$('#fixed-login').hide();
			isBtnClose = true;
		});
		var login = ${user_session_login != null && user_session_login == 'true' };
		/* if (login == false) {
			$('#fixed-login').show();
		} */
		$(window).bind('scroll.back', function() {
			var st = $(window).scrollTop();
			if ($(window).width() >= 1260 && st > 277) {
				if (!login && !isBtnClose) {
					$('#fixed-login').show();
				}
			}
		});
		$('#fixed-login-button').click(function(e) {
			e.preventDefault();
			var v = $('input.email').val();
			if ($('input.email').val() == "" || $('input.password').val() == "") {
				return;
			}
			var myreg = /(\S)+[@]{1}(\S)+[.]{1}(\w)+/;
			var content = $('input.email').val();

			if (!myreg.test(v)) {
				window.location.href = "${loginAndRegisterURL}user/user/tologins.htm?data=" + encodeURIComponent("邮箱格式错误!") + "&email=" + content + "&redirectUrl=" + encodeURIComponent(window.location.href); //进入登录页面
				return;
			}
			$.ajax({
				type : "post",
				dataType : "jsonp",
				url : "${loginAndRegisterURL}user/user/data/loginNum.htm",
				data : "email=" + $('input.email').val() + "&jsonpCallback=success1",
				async : false
			});
		});
	});
	function tapKeyUp(e) {
		var code = parseInt(e.keyCode || e.which);
		if (code == 13) {
			$('#fixed-login-button').trigger('click');
			return false;
		}
		var src = e.srcElement ? e.srcElement : e.target;
		var content = $.trim(src.value);
		if (content.length > 40) {
			src.value = content.substring(0, 40);
		}
		return true;
	}
	var success1 = function(data) {
		if (data.ok == false) {
			window.location.href = "${loginAndRegisterURL}user/user/tologins.htm?data=" + encodeURIComponent("登录信息错误!") + "&email=" + $('input.email').val() + "&redirectUrl=" + encodeURIComponent(window.location.href); //进入登录页面
		} else {
			$.ajax({
				type : "post",
				dataType : "jsonp",
				url : "${loginAndRegisterURL}user/user/data/login.htm",
				data : "email=" + $('input.email').val() + "&password=" + $('input.password').val() + "&jsonpCallback=success3",
				async : false
			});
		}
	}

	var success3 = function(data) {
		/* if (data.ok == true) {
			setTimeout("load()", 500);
		} else {
			window.location.href = "${loginAndRegisterURL}user/user/tologins.htm?data=" + encodeURIComponent(data.data) + "&email=" + $('input.email').val() + "&redirectUrl=" + encodeURIComponent(window.location.href); //进入登录页面
		} */
		if (data.ok == true) {
			if (data.code == '10011') {
				var count = 0;
				var $scripts = $('<span>' + data.data + '</span>').find('script');
				$scripts.each(function() {
					$.getScript($(this).attr('src'), function() {
						count = count + 1;
					});
				});
				var si = setInterval(function() {
					if (count == $scripts.size()) {
						window.location.href = window.location.href;
						clearInterval(si);
					}
				}, 500);
			} else if (data.code == '10012') {
				window.location.href = data.data + '&loginReferer=' + encodeURIComponent(window.location.href);
			} else {
				load();
			}
		} else {
			window.location.href = "${loginAndRegisterURL}user/user/tologins.htm?data=" + encodeURIComponent(data.data) + "&email=" + $('input.email').val() + "&redirectUrl=" + encodeURIComponent(window.location.href); //进入登录页面
		}
	};
	function load() {
		window.location.href = window.location.href;
	}
</script>
</body>
</html> --%>