<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="impress">
	<div class="impress-hd">
		<span>共有<b>${suppliser.impressNum}</b>个印象</span>
		<h2>大家对Ta的印象</h2>
	</div>
	<div class="impress-bd clear-fix">
		<c:forEach items="${impressList}" var="impress">
			<a href="javascript:void(0);" id="${impress.id}">${impress.content}(${impress.impressCount})</a>
		</c:forEach>
		<div class="add-tag">
			<input type="text" value="添加印象">
			<span></span>
		</div>
	</div>
	<div class="impress-more clear-fix">
		<span class="more" status="hide" id="show-more">查看更多<b></b></span>
		<span class="lines"></span>
	</div>
	<div class="tips tips1" style="display:none;">
		<span class="close">关闭</span>
		<span class="success"><i></i>添加成功，审核中</span>
	</div>
	<div class="tips tips2" style="display:none;">
		<span class="close">关闭</span>
		<span class="waring"><i></i>请输入您的印象</span>
	</div>
</div>
<script type="text/javascript">
$(function(){
	hide();
	$(".impress .add-tag input").focus(function(){
		if(this.value == '添加印象'){
			this.value = "";
		}
	});
	$(".impress .add-tag input").blur(function(){
		if(this.value == ''){
			this.value = "添加印象";
		}
	});
	$(".impress .add-tag span").click(function(){
		var $tag = $(".impress .add-tag input");
		var value = $tag.val();
		if(!value || value == "添加印象"){
			showTips("请输入您的印象", -1);
			return;
		}
		if(value.length > 8){
			showTips("印象不超过8字", -1);
			return;
		}
		var url = "${basePath}dianping/impress/add.htm";
		var param = {supplierId:"${suppliser.id}",content:value};
		$.post(url, param,
	        function(httpObj){
	        	if(httpObj.flag){
	        		showTips("添加成功，审核中", 0);
	        		$tag.val("添加印象");
	        	}else{
	        		showTips(httpObj.message, -1);
	        	}
	     });
	});
	$("#show-more").click(function(){
		var status = $(this).attr("status");
		var $moreImpress = $("a[name='more-impress']");
		if("hide" == status){
			if($moreImpress.length > 0){
				$moreImpress.show();
			}else{
				var url = "${basePath}dianping/impress/show/more.htm";
				var param = {supplierId:"${suppliser.id}"};
				$.post(url, param,
			        function(httpObj){
						$.each(httpObj, function(i, item){
							var impress = $("<a href=\"javascript:void(0);\" name='more-impress' id='" + item.id + "'>" + item.content + "(" + item.impressCount + ")</a>");
							$(".add-tag").before(impress);
						});
						removeClickEvent();
						addClickEvent();
			     });
			}
			$(this).attr("status", "show");
			$(this).html("隐藏<b></b>");
			$(this).addClass("toggle");
		}else{
			$moreImpress.hide();
			$(this).attr("status", "hide");
			$(this).html("查看更多<b></b>");
			$(this).removeClass("toggle");
		}
	});
	$(".close").click(function(){
		hide();
	});
	addClickEvent();
});
var warnIsShow = false;
function success(text){
	warnIsShow = false;
	$(".impress .tips2").hide(100);
	$(".impress .tips1").show(500);
	$(".success").html("<i></i>"+text);
}
function info(text){
	if(!warnIsShow){
		$(".impress .tips1").hide(100);
		$(".impress .tips2").show(500);
		warnIsShow = true;
		setTimeout(function(){
			$(".impress .tips2").fadeOut(1000);
		}, 5000);
	}
	$(".waring").html("<i></i>"+text);
}
function hide(){
	warnIsShow = false;
	$(".impress .tips1").hide(100);
	$(".impress .tips2").hide(100);
}
function flashClick(id){
	var url = "${basePath}dianping/impress/click/get.htm";
	var param = {id:id};
	$.get(url, param,
        function(httpObj){
			if(httpObj.flag){
				if(httpObj.impressCount && httpObj.impressCount > 0){
					$("#"+id).text(httpObj.content + "("+httpObj.impressCount+")");
				}
			}
        });
}
function addClickEvent(){
	$(".impress-bd a").click(function(){
		var url = "${basePath}dianping/impress/click.htm";
		var id = $(this).attr("id");
		var param = {id:id};
		$.post(url, param,
	        function(httpObj){
				if(httpObj.flag){
					showTips("点击印象成功", 0);
					flashClick(id);
				}else{
					showTips(httpObj.message, -1);
				}
	     });
	});
}
function removeClickEvent(){
	$(".impress-bd a").unbind("click");
}
</script>
