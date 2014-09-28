;
var popWindowShareFed = {
	style : function() {
		if (window.location.href.indexOf("b5m.com") > -1) {
			document.domain = 'b5m.com';
		}
		var shareLink = document.createElement('link'), bodyDom = document.getElementsByTagName('body')[0];
		shareLink.setAttribute('href', "http://staticcdn.b5m.com/css/common/popwindow-share.css?stamp=" + Math.random());
		shareLink.setAttribute('rel', "stylesheet");
		bodyDom.appendChild(shareLink);
		return true;
	}(),
	/* 检测IE6 */
	ie6 : function() {
		return typeof document.getElementsByTagName("body")[0].style.maxHeight == "undefined";
	}(),
	/* 最大分享内容字节数 */
	insertNum : 120,
	/* 分享内容输入 */
	shareTxt : function() {
		var _this = this, $txtArea = $('.popwindow-share-txt', '.popwindow-share'), msg = $txtArea.attr('msg');
		/* 输入框默认值检测 */
		$txtArea.focus(function() {
			var $this = $(this), thisVal = $this.val().replace(/\s+/gi, '');
			if (msg.replace(/\s+/gi, '') == thisVal) {
				$this.val('').css("color", "#333");
			}
		}).blur(function() {
			var $this = $(this), thisVal = $this.val().replace(/\s+/gi, '');
			if (msg.replace(/\s+/gi, '') == thisVal || thisVal == "") {
				$this.val(msg).css("color", "#999");
			} else {
				$this.css("color", "#333");
			}
		}).trigger("blur");
		/* 输入框输入文字 */
		var insertNumDom = $('.popwindow-share-txtCanInsertNum');
		$txtArea.on({
			keyup : function() {
				var $this = $(this), thisVal = $this.val(), thisValLen = thisVal.length, canInsertNum = _this.insertNum - thisValLen;
				if (thisVal == msg) {
					insertNumDom.text(_this.insertNum);
					return;
				}
				if (canInsertNum < 20) {
					insertNumDom.css("color", '#f00');
				} else {
					insertNumDom.removeAttr('style');
				}
				if (thisValLen >= _this.insertNum) {
					$this.val(thisVal.substr(0, _this.insertNum));
					insertNumDom.text(0);
				} else {
					insertNumDom.text(canInsertNum);
				}
			}
		});
		$('.popwindow-share-close', '.popwindow-share').click(function() {
			$('.popwindow-share').remove();
		});
	},
	/* 分享弹窗关闭 */
	popwindowClose : function() {
		$('.popwindow-share').remove();
	},
	/**
	 * 授权弹窗关闭
	 */
	authClose : function() {
		$('#share-auth').remove();
	},
	/**
	 * 授权弹窗
	 * 
	 * @param type
	 *            授权类型
	 */
	auth : function(type) {
		var popWinHtml = '<div id="share-auth"><iframe src="http://ucenter.b5m.com/user/third/login/auth.htm?type=' + (type || 1) + '&isIframe=1&refererUrl=&api=/setToken" frameborder="0" width="800" height="460"></iframe><u class="popwindow-share-close"></u></div>', _this = this;
		$(popWinHtml).appendTo($("body"));
		$('.popwindow-share-close').click(function() {
			_this.authClose();
		});
	},
	click : function(shareArgs) {
		var $this = $(this), basePath = "http://ucenter.b5m.com", type = $this.attr('attr-type'), sync = $this.attr('attr-sync');
		if (shareArgs.type)
			type = shareArgs.type;
		else
			shareArgs.type = type;
		$.ajax({
			url : basePath + "/user/third/getToken.htm",
			dataType : "jsonp",
			jsonp : 'jsonpCallback',
			async : false,
			data : {
				state : type
			},
			success : function(result) {
				if (result && result.ok == true) {
					popWindowShareFed.popwindow(shareArgs, function(res) {
						var url = basePath + "/user/third/share.htm";
						var data = {
							state : type,
							summary : res['content'],
							title : res['title']
						};
						if (type == 1) {
							data.url = res['href'];
						} else {
							data.summary = data.summary + "  " + res['href'];
						}
						if (res['pic']) {
							data.images = res['pic'];
						}
						if (type == 1) {
							if (sync) {
								data.nswb = sync;
							} else {
								data.comment = res['content'];
							}
						}
						$.ajax({
							type : "POST",
							url : url,
							data : data,
							dataType : "jsonp",
							jsonp : "jsonpCallback",
							success : function(httpObj) {
								if (httpObj.ok) {
									popWindowShareFed.popwindowClose();
									alert('分享成功');
								} else {
									alert('分享失败，请稍后分享');
								}
							}
						});
					});
				} else if (result.ok == false) {
					_this.auth(type);
				}
			}
		});
	},
	init : function(args) {
		var _Args = $.extend({
			params:[],
			shareMode : false,
			id : ".box-share-ico",
			title : "帮5买",
			href : window.location.href,
			content : "帮5买作为专业的比价网、购物搜索门户，不仅是综合购物比价网站，还是一个汇聚旅游、团购、特价、资讯的购物搜索门户。帮5买，帮您轻松选择的比价网。",
			pic : false
		}, {}, args || {}), icoHtml = '<div class="box-popShare"><span class="share-ico share-sina" attr-type="2"></span><span class="share-ico share-qzone" attr-type="1" attr-sync="1"></span><span class="share-ico share-weibo" attr-type="1"></span></div>', icoBox = $(_Args.id), _this = this;
		var shareArgs = _Args;
		var params = _Args.params;
		if (!_Args.pic) {
			delete _Args.pic;
		}
		if (!_Args.shareMode) {
			$(icoHtml).appendTo(icoBox);
		}
		if (params.length > 0) {
			$.each(params, function(i, item) {
				$(item.id).click(function() {
					popWindowShareFed.click(item);
				});
			});
		} else {
			icoBox.on({
				click : function() {
					popWindowShareFed.click(shareArgs);
				}
			}, ".share-ico");
		}
	},
	/**
	 * 弹窗 - 弹出
	 * 
	 * @param args{
	 *            title:(string)分享标题 href:(string)分享链接 content:(string)分享内容
	 *            fun:(function) 分享回调 }
	 * @param callBack{function}分享回调
	 * @returns {object} 返回分享对象
	 */
	popwindow : function(args, callBack) {
		var _Args = $.extend({}, {
			title : "帮5买值得买频道",
			href : "http://www.b5m.com",
			content : "",
			type : 1,
			pic : [],
			fun : false
		}, args || {}), popWindowStr = '<div class="popwindow-share"><div class="popwindow-share-insert"><h3>分享<span>还能输入<b class="popwindow-share-txtCanInsertNum">120</b>个字符</span></h3><textarea class="popwindow-share-txt" msg="请输入分享内容...">' + _Args['content'] + '</textarea><p><b>分享来自“' + _Args['title'] + '”</b><a href="' + _Args['href'] + '" target="_blank">' + _Args['href'] + '</a></p></div>', $popBox, popBoxHeight = 0;
		/* 生成分享图片 */
		if (_Args.pic.length > 0) {
			var imgScrollHtml, imgHtml = '';
			for ( var i = 0; i < _Args.pic.length; i++) {
				imgHtml += '<span><img src="' + _Args.pic[i] + '"/><s></s></span>';
			}
			imgScrollHtml = '<div class="popwindow-share-pic"><p><input type="checkbox" id="popwindow-share-piccheckbox" value="1" class="popwindow-share-piccheckbox" checked/><label for="popwindow-share-piccheckbox">同时分享选中图片</label></p><div class="popwindow-share-picbox"><div class="ov"><div class="popwindow-share-cont ov">' + imgHtml + '</div></div><s class="popwindow-share-pic-next"><u></u></s><s class="popwindow-share-pic-prev"><u></u></s></div></div>';
			popWindowStr += imgScrollHtml;
		}
		popWindowStr += '<div class="popwindow-share-btbox"><span class="popwindow-share-subBt">分享</span><a href="http://ucenter.b5m.com/user/third/login/auth.htm?type=' + _Args.type + '&refererUrl=&api=/setToken" class="popwindow-share-transit" target="_blank">换个账号分享</a></div><s class="popwindow-share-close"></s></div>';
		$(popWindowStr).appendTo($("body"));
		$popBox = $('.popwindow-share');
		popBoxHeight = $popBox.outerHeight(true);
		if (typeof document.body.style.maxWidth == "undefined") {
			var winHeight = $(window).height();
			$(window).resize(function() {
				winHeight = $(window).height();
			});
			$(window).scroll(function() {
				$popBox.css("top", (winHeight - popBoxHeight) / 2 + $(this).scrollTop() + "px");
			}).trigger('scroll');
		} else {
			$popBox.css("marginTop", "-" + (popBoxHeight / 2) + "px");
		}
		popWindowShareFed.shareTxt();
		if (_Args.pic.length > 0) {
			$('span:first', '.popwindow-share-cont').addClass("cur");
			popWindowShareFed.imgScroll();
			popWindowShareFed.sltShareImg();
		}
		/* 点击分享按钮 */
		$(".popwindow-share-subBt", '.popwindow-share').click(function() {
			var shareVals = {}, shareTxtBox = $('.popwindow-share-txt');
			if (shareTxtBox.attr('msg') == shareTxtBox.val()) {

			}
			shareVals.title = _Args['title'];
			shareVals.href = _Args['href'];
			shareVals.content = shareTxtBox.val();
			if (_Args.pic.length > 0) {
				if ($('#popwindow-share-piccheckbox').is(":checked"))
					shareVals.pic = $(".cur>img", '.popwindow-share-cont').attr('src');
			}
			/* 分享回调 */
			if (callBack) {
				callBack(shareVals);
			}
			if (_Args['fun']) {
				_Args['fun'](shareVals);
			}
		});
		return this;
	},
	/* 分享图片滚动 */
	imgScroll : function(args) {
		var _Args = $.extend({}, {
			auto : false
		}, args || {}), scrollBoxId = '.popwindow-share-picbox', scrollContId = '.popwindow-share-cont', autoScroll, scrollTim = 4000;
		if ($('span', scrollContId).length < 5) {
			$(".popwindow-share-pic-next,.popwindow-share-pic-prev", scrollBoxId).css("opacity", '0.3');
			return this;
		}
		/* 向前滚动 */
		var scrollNext = function() {
			var firstDom = $('span:first', scrollContId), $contBox = $(scrollContId);
			$contBox.animate({
				marginLeft : "-=" + firstDom.outerWidth(true)
			}, function() {
				$contBox.append(firstDom).removeAttr("style");
			});
		};
		/* 向后滚动 */
		var scrollPrev = function() {
			var lastDom = $('span:last', scrollContId), $contBox = $(scrollContId), domWidth = lastDom.outerWidth(true);
			$contBox.prepend(lastDom).css("marginLeft", '-' + domWidth + "px").animate({
				marginLeft : "+=" + domWidth
			});
		};
		if (_Args['auto']) {
			$(scrollBoxId).hover(function() {
				clearInterval(autoScroll);
			}, function() {
				autoScroll = setInterval(function() {
					scrollNext();
				}, scrollTim)
			}).trigger("mouseout");
		}
		$(".popwindow-share-pic-next,.popwindow-share-pic-prev", scrollBoxId).on({
			"click" : function() {
				var $this = $(this);
				if ($this.hasClass('popwindow-share-pic-next')) {
					scrollPrev();
				} else {
					scrollNext();
				}
			}
		});
		return this;
	},
	/* 分享图片选择 */
	sltShareImg : function() {
		$("span", '.popwindow-share-cont').click(function() {
			if (!$('#popwindow-share-piccheckbox').is(":checked")) {
				return false;
			}
			var $this = $(this);
			if ($this.find('s').length == 0) {
				$this.append("<s></s>");
			}
			$this.addClass('cur').siblings().removeClass('cur');
			return this;
		});
	}
};
