<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@page session="false"%>

<div class="page clear-fix cf">
    <div>
		<c:set value="" var="isDis"/>
		<c:if test="${pageView.currentPage <= 1}">
			<a href="javascript:void(0)" class="prev dis" data-attr='1009'><</a>
		</c:if>
		<c:if test="${pageView.currentPage > 1}">
			<a href="javascript:void(0)" class="prev" data-attr='1009' onclick="gotoPage(${pageView.currentPage-1});return false;"><</a>
		</c:if>
		<c:if test="${pageView.pageIndex.startindex != 1}">
			<a href="javascript:void(0)" data-attr='1009' onclick="gotoPage(1);return false;" class="first">首页</a>
			<span>...</span>
		</c:if>
		<c:forEach begin="${pageView.pageIndex.startindex}" end="${pageView.pageIndex.endindex}" var="pageCode">
			<c:set value="" var="cur"/>
			<c:if test="${pageView.currentPage == pageCode}">
				<c:set value="cur" var="cur"/>
			</c:if>
			<a class="${cur}" data-attr='1009' href="javascript:void(0);" onclick="gotoPage(${pageCode});return false;">${pageCode}</a>
		</c:forEach>
		
		<c:if test="${pageView.pageIndex.endindex != pageView.totalPage}">
			<span>...</span>
			<a href="javascript:void(0)" data-attr='1009' onclick="gotoPage(${pageView.totalPage});return false;" class="last">尾页</a>
		</c:if>
		<c:if test="${pageView.currentPage < pageView.totalPage}">
			<a href="javascript:void(0)" data-attr='1009' class="next" onclick="gotoPage(${pageView.currentPage+1});return false;">></a>
		</c:if>
		<c:if test="${pageView.currentPage >= pageView.totalPage}">
			<a href="javascript:void(0)" data-attr='1009' class="next dis">></a>
		</c:if>
		<span class="all">共${pageView.totalPage}页&nbsp;&nbsp;去第</span>
		
		<span class="page-input">
			<input id="txt_page" maxlength="4" type="text" title="输入页码，按回车快速跳转" value="" onkeydown="return keydown(event);" onkeyup="return keydown(event);" ><a href="javascript:void(0)" data-attr='1009' name="go" onclick="clickGo();return false;">GO</a>
		</span>
		<span class="go">页</span>                 
	</div>
</div>
<script type="text/javascript">
function gotoPage(pageNum){
	var url = "${pageCodeLink}".replace("{PageCode}", pageNum);
	if($("#compareprice").attr("value") == "true"){
		url += "?compare=true";
	}
	window.location.href = url;
};
$(function(){
	$(".page-input input").change(function(){
		gotoPage($(this).val());
	});
});
function clickGo(){
	var val = parseInt($(".page-input input").val());
	if(val){
		if(val > parseInt("${pageView.totalPage}")){
			val = "${pageView.totalPage}";
		}
		if(val < 0){
			val = 1;
		}
	}else{
		val = 1;
	}
	gotoPage(val);
}
function keydown(event) {
	var e =  window.event || event;
	var code = parseInt(e.keyCode || e.which);
	var val = parseInt($(".page-input input").val());
	if(val > parseInt("${pageView.totalPage}")){
		$(".page-input input").val("${pageView.totalPage}");
		return false;
	}
	if (code == 46 || code == 8 || (code >=48 && code <= 57) || (code >=96 && code <= 105))
		return true;
	if (code == 13) {
		$('a[name="go"]').trigger('click');
		return false;
	}
	return false;
}
</script>
