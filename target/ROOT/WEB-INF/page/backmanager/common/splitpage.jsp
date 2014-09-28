<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<div class="page clear-fix">
	<ul>
		<li>共${pageView.totalPage}页</li>
		<li>
			<c:if test="${pageView.currentPage > 1}">
				<a href="javascript:void(0)" class="prev" onclick="gotoPage(${pageView.currentPage-1})">&lt;</a>
			</c:if>
			<c:if test="${pageView.pageIndex.startindex > 1}">
				<a href="javascript:void(0)" onclick="gotoPage(1)" class="first">首页</a>
				<span>...</span>
			</c:if>
			<c:forEach begin="${pageView.pageIndex.startindex}" end="${pageView.pageIndex.endindex}" var="pageCode">
				<c:set value="" var="cur"/>
				<c:if test="${pageView.currentPage == pageCode}">
					<c:set value="cur" var="cur"/>
				</c:if>
				<a class="${cur}" href="javascript:void(0);" onclick="gotoPage(${pageCode})">${pageCode}</a>
			</c:forEach>
			
			<c:if test="${pageView.pageIndex.endindex < pageView.totalPage}">
				<span>...</span>
				<a href="javascript:void(0)" onclick="gotoPage(${pageView.totalPage})" class="last">尾页</a>
			</c:if>
			<c:if test="${pageView.currentPage < pageView.totalPage}">
				<a href="javascript:void(0);" class="next" onclick="javascript:gotoPage(${pageView.currentPage + 1});">&gt;</a>
			</c:if>
		</li>
		<li>到第<input type="text" id="pageNum" value="${pageView.currentPage}">页</li>
	</ul>
	<script type="text/javascript">
	$("#pageNum").change(function(){
		var pageNum = this.value;
		if(pageNum > parseInt("${pageView.totalPage}")){
			pageNum = "${pageView.totalPage}";
		}
		if(pageNum < 1){
			pageNum = 1;
		}
		pageNum = parseInt(pageNum);
		if(!pageNum) pageNum = 1;
		gotoPage(pageNum);
	});
	</script>
</div>
