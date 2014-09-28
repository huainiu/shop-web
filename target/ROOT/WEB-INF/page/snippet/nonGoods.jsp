<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%> 
<%@ page import="com.b5m.common.env.GlobalWebCfg"%>
<%@ page session="false"%>
<%
String httpTiaoURL = GlobalWebCfg.getTiaoHttpUrl(request);
%>
<!--团购 s-->
<c:if test="${dataSetTuanCounter>0}">
	<div id ="tuan" class="mod">
		<div class="mod-hd">
			<h2>关于“<em>${keyword}</em>”的团购</h2>
			<span>找到<strong>
			<c:choose>
				<c:when test="${dataSetTuanCounter>9999}">9999+</c:when>
				<c:otherwise>${dataSetTuanCounter}	</c:otherwise>
			</c:choose>
			</strong>个，<a href="${b5mUrl}o/tuan/s/${keyword}_________" target="_blank">查看更多&gt;</a></span>
		</div>
		<c:forEach var="tuan" items="${dataSetTuan}" varStatus="stat">
			<div class="mod-bd">
				<div class="mod-group grid-s130 clear-fix">
					<div class="col-main">
						<div class="wrap-main">
							 <h3>
								<a data-type="tuan_site"  href="${b5mUrl}suiProductSender.htm?method=goToCps&splName=${tuan.Source}&url=${tuan.Url}" target="_blank">${tuan.Source}</a>
							</h3> 
							<p class="group-explan">
							<c:set var="_title" value="${tuan.Title}"/>
								<c:if test="${fn:length(_title) > 80}">
									<c:set var="_title" value="${fn:substring(_title, 0, 100)}..." />
								</c:if>
								<a data-type="tuan_title"  href="${b5mUrl}suiProductSender.htm?method=goToCps&splName=${tuan.Source}&url=${tuan.Url}" target="_blank">${ _title}</a>
			 				</p>
							<p class="group-price">
								<span class="now">&yen; ${Pricelist[stat.index]}</span>
								<del class="org">原价：${tuan.PriceOrign}</del>
								<span class="dis">折扣：${tuan.Discount}折</span> 
								<span class="save">节省：&yen; ${tuan.PriceOrign-Pricelist[stat.index]}</span>
							</p>
						</div>
					</div>
					<div class="col-sub">
						<p class="group-photo">
							<a data-type="tuan_pic" href="${b5mUrl}suiProductSender.htm?method=goToCps&splName=${tuan.Source}&url=${tuan.Url}" target="_blank"><img
								src="http://cdn.bang5mai.com/b5m_img/${tuan.Picture}"></a>
						</p>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</c:if>
<!--团购 e-->
<!--色界 s-->
<c:if test="${dataSetSeJieCounter>0}">
	<div class="mod">
		<div class="mod-hd">
			<h2>
				关于“<em>${keyword}</em>”的相关图片
			</h2>
			<span>找到<strong>
			<c:choose>
				<c:when test="${dataSetSeJieCounter>9999}">9999+</c:when>
				<c:otherwise>${dataSetSeJieCounter}	</c:otherwise>
			</c:choose>
			</strong>个，<a href="<%=httpTiaoURL%>best/s/______${keyword}.html" target="_blank">查看更多&gt;</a></span>
		</div>
		<div class="mod-bd">
			<ul class="mod-sj clear-fix">
				<c:forEach var="sj" items="${dataSetSeJie}">
					<li>
					<span>
						<a href="<%=httpTiaoURL%>best/item/${sj.AppId}.html" target="_blank">
						<%-- <a href="${b5mUrl}sejie/item/${sj.AppId}.html" target="_blank"> --%> 
							<img class="autoImg" src="${sj.Img}">
						</a>
					</span>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>
<!--色界 e-->
<!--票务 s-->
<%-- <c:if test="${dataSetTicketCounter>0}">
	<div class="mod">
		<div class="mod-hd">
			<h2>
				“<em>${keyword}</em>”的票务
			</h2>
			<span>找到<strong>${dataSetTicketCounter }</strong>个，<a
				href="${b5mUrl}o/ticket/s/${keyword}_______"
				target="_blank">查看更多&gt;</a></span>
		</div>
		<div class="mod-bd">
			<ul class="mod-ticket">
				<c:forEach var="ticket" items="${dataSetTicket}"
					varStatus="status">
					<li>
						<h3>
							<a href="${ticket.Url}" target="_blank">${ticket.Title}</a>
						</h3>
						<p class="ticket-price">票价：${ticket.Price}</p>
						<p class="ticket-explan">
							<c:set var="content" value="${ticket.Content}"></c:set>
						<c:set var="content" value="${fn:replace(content, '&gt;', '>')}"></c:set>
						<c:set var="content" value="${fn:replace(content, '&lt;', '<')}"></c:set>
							${ticket.Content}
						</p>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if> --%>
<!--票务 e-->