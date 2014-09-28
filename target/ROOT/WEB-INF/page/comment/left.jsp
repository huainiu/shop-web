<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="col-sub">
	<div class="comment-sort">
		<h3>大家都在评</h3>
		<ol class="sort-ls">
			<c:forEach items="${supplierList}" var="supplier" varStatus="stat">
				<li>
					<span class="fr">
						<c:set var="status" value="${supplier.percent < 50 ? 'bad': (supplier.percent == 50 ? 'mid': 'good')}"></c:set>
						<a href="javascript:void(0);" class="default-bg ${status}"><span style="width:${supplier.percent}%;cursor: pointer;"></span></a>
						<span class="num-peo"><em>${supplier.commentNum}</em>人发表评价</span>
					</span>							
					<em class="num top1">${stat.index + 1}</em><a class="mall" href="${basePath}dianping.htm?source=${supplier.encodeName}" name="${supplier.name}">${supplier.name}</a>
					<em class="tooltip" style="display: none;"><i></i><span>${supplier.goodPinNum}个好评</span></em>
				</li>
			</c:forEach>
		</ol>					
	</div>
</div>

<script type="text/javascript">
	$(function() {
		$('ol.sort-ls li').each(function() {
			var fr = $(this);
			fr.find('a.default-bg').hover(function() {
				fr.find('em.tooltip').show();
			}, function() {
				fr.find('em.tooltip').hide();
			});
		});
	});
</script>