<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="eva">
	<div class="eva-hd">
		<ul>
			<li><a href="javascript:void(0);" class="cur" val="-1">全部(${countMap["comment_-1"]})</a></li>
			<li><a href="javascript:void(0);" val="0">好(${countMap["comment_0"]})</a></li>
			<li><a href="javascript:void(0);" val="1">一般(${countMap["comment_1"]})</a></li>
			<li><a href="javascript:void(0);" val="2">差劲(${countMap["comment_2"]})</a></li>
		</ul>
		<h2>大家对Ta的评价</h2>
	</div>
	<div class="eva-bd"></div>
	<div class="clear-fix page"></div>
</div>
<script>
	$(function() {
		getCommentInfo(1, null);
		$('.page').on('click', 'a', function() {
			if ($(this).attr('name') == 'go') {
				if ($('#goNum').val() != '' && !isNaN($('#goNum').val())) {
					if (parseInt($('#goNum').val()) > parseInt($('span.go').attr('maxPage'))) {
						getCommentInfo($('span.go').attr('maxPage'), $('a.cur').attr('val'));
					} else if (parseInt($('#goNum').val()) <= 0) {
						getCommentInfo(1, $('a.cur').attr('val'));
					} else {
						getCommentInfo($('#goNum').val(), $('a.cur').attr('val'));
					}
				} else {
					$('#goNum').val('');
				}
				return;
			}
			getCommentInfo($(this).attr('data'), $('a.cur').attr('val'));
		});

		$('div.eva-hd a').each(function() {
			$(this).click(function() {
				$('div.eva-hd a').removeClass('cur');
				$(this).addClass('cur');
				getCommentInfo(1, $(this).attr('val'));
			});
		});
	});

	function getCommentInfo(currentPage, type) {
		$('div.eva-bd').html('');
		$('div.eva-bd').addClass('ajax_loading');
		$.ajax({
			type : 'GET',
			url : '${basePath}dianping/comments/list.htm?time=' + new Date().getTime(),
			data : {
				'supplierId' : $('#supplierId').attr('val'),
				'currentPage' : currentPage,
				'type' : type
			},
			dataType : 'json',
			success : function(data) {
				var list = data.records;
				var html = '';
				$('div.eva-bd').removeClass('ajax_loading');
				$('div.eva-bd').html('');
				$.each(list, function(i, item) {
					html = '<div class="list">';
					html += '<div class="list-hd clear-fix">';
					html += '<div class="fl"><span>' + item.user + '</span>觉得Ta<em class="g" style="color:' + getColor(item.type) + '">' + getType(item.type) + '</em></div>';
					html += '<div class="fr"><label>分享到：</label>';
					html += '<a href="javascript:void(0);" class="sina" title="分享到新浪微博" val="' + i + '"></a><a href="javascript:void(0);" class="qq" title="分享到腾讯微博" val="' + i + '"></a></div></div>';
					html += '<div class="list-bd"><p id="content' + i + '">' + item.content + '</p></div>';
					html += '<div class="list-avatar"><a href="javascript:;"><img src="' + getAvatar(item.avatar) + '" width="48" height="48" style="cursor: default;"></a></div>';
					html += '<div class="list-time">' + new Date(item.createTime).format("yyyy-MM-dd hh:mm:ss") + '</div></div>';
					$('div.eva-bd').append(html);
				});
				$('div.fr').on('click', 'a', share);
				var pageHtml = setPage(data);
				$('.page').html(pageHtml);
				if (parseInt(data.currentPageNo) == 1) {
					$('a.first').addClass('dis');
				}
				if (parseInt(data.totalPageNo) == 0 || parseInt(data.currentPageNo) == parseInt(data.totalPageNo)) {
					$('a.last').addClass('dis');
				}
			},
			error : function(data) {
				$('div.eva-bd').removeClass('ajax_loading');
			}
		});
	}

   function share() {
		var i = $(this).attr('val');
		var title = $('#content' + i).text();
		var clacc = $(this).attr('class');
		var url = "${basePath}dianping.htm?source=" + encodeURIComponent("${suppliser.name}");
		var text = encodeURIComponent("我在这里 http://s.b5m.com 看到有人对“${suppliser.name}”的评论为“"+title+"”，我很支持。你觉得“${suppliser.name}”怎么样? ");
		if (clacc == 'sina')
			window.open('http://v.t.sina.com.cn/share/share.php?appkey=3222906970&title=' + text+"&url=" + encodeURIComponent(url) + "&content=utf8&searchPic=false");
		else if (clacc == 'qq')
			window.open("http://share.v.t.qq.com/index.php?c=share&a=index&title=" + text + "&site=http://s.b5m.com");
	}

	function getType(type) {
		var json = {
			'0' : '很好',
			'1' : '一般',
			'2' : '差劲'
		};
		return json[type];
	}

	function getColor(type) {
		var json = {
			'0' : '#39b54a',
			'1' : '#f68e56',
			'2' : '#ed1c24'
		};
		return json[type];
	}

	function getAvatar(avatar) {
		if (avatar != null && avatar != '') {
			return avatar;
		}
		return '${basePath}images/images/avatar.png';
	}

	//分页设置
	function setPage(pageView) {
		var html = "<div>";
		if (parseInt(pageView.totalPage) > 1) {
			if (pageView.currentPage <= 1) {
				html += '<a class="prev dis" href="javascript:void(0)">&lt;</a>';
			}
			if (pageView.currentPage > 1) {
				html += '<a class="prev" href="javascript:void(0)" data=\"1\">&lt;</a>';
			}
			if(pageView.pageIndex.startindex > 1){
				html += '<a class="first" href="javascript:void(0)" data=\"1\">首页</a>';
				html += '<span>...</span>';
			}
			for(var i = pageView.pageIndex.startindex; i <= pageView.pageIndex.endindex; i++){
				if(pageView.currentPage == i){
					html += '<a href="javascript:void(0)" class="cur">' + i + '</a>';
				}else{
					html += '<a href="javascript:void(0)" data=' + i + '>' + i + '</a>';
				}
			}
			if(pageView.pageIndex.endindex < pageView.totalPage){
				html += '<span>...</span>';
				html += '<a href="javascript:void(0)" data="' + pageView.totalPage + '" class="last">尾页</a>';
			}
			if(pageView.currentPage != pageView.totalPage){
				html += '<a href="javascript:void(0)" data="' + (pageView.currentPage + 1) + '" class="prev">&gt;</a>';
			}
			if(pageView.currentPage == pageView.totalPage){
				html += '<a href="javascript:void(0)" class="prev dis">&gt;</a>';
			}
		}
		html += '<span class="all">共' + pageView.totalPage + '页&nbsp;&nbsp;去第</span>';
		html += '<span class="page-input"> <input type="text" id="goNum" onkeydown="return keydown(event);" onkeyup="return keydown(event);">';
		html += '<a href="javascript:void(0)" name="go">GO</a>';
		html += '</span> <span class="go" maxPage="' + pageView.totalPage + '">页</span>';
		html += '</div></div>';
		return html;
	}
	
	function keydown(e) {
		var e =  window.event || e;
		var code = parseInt(e.keyCode || e.which);
		if (code == 46 || code == 8 || (code >=48 && code <= 57) || (code >=96 && code <= 105))
			return true;
		if (code == 13) {
			$('a[name="go"]').trigger('click');
			return false;
		}
		return false;
	}

	//long 转 timestamp
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, //month
			"d+" : this.getDate(), //day
			"h+" : this.getHours(), //hour
			"m+" : this.getMinutes(), //minute
			"s+" : this.getSeconds(), //second
			"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter
			"S" : this.getMilliseconds()
		//millisecond	
		}
		if (/(y+)/.test(format))
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(format))
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		return format;
	}
</script>