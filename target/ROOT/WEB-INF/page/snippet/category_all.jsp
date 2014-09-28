<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<c:set var="domain" value="${fn:substring(basePath, 0, fn:length(basePath)-1)}"></c:set>
<!-- all cagtegory s -->
<div class="all-cate" id="J_all_cate">
	<h2>
		全部商品分类<i></i>
	</h2>
	<ul class="main-item" id="J_menu">
		<c:forEach items="${categoryJson}" var="cat" varStatus="status">
			<c:if test="${status.index%2 == 0}">
				<li class="item-odd">
			</c:if>
			<c:if test="${status.index%2 == 1}">
				<li>
			</c:if>
			<span class="icon icon-${cat.icon }"></span>
			<div class="main-cate">
				<a href="#">${cat.name }</a> <span class="arrow"></span>
			</div>
			<div class="sub_item">
				<div class="sub-in">
					<c:forEach items="${cat.sub_categories}" var="cat2">
						<dl>
							<dt>
								<a href="${cat2.url }">${cat2.name }</a>
							</dt>
							<dd>
								<c:forEach items="${cat2.sub_categories}" var="cat3">
									<a href="${cat3.url }">${cat3.name }</a>
								</c:forEach>
							</dd>
						</dl>
					</c:forEach>
				</div>
			</div>
			</li>
		</c:forEach>
	</ul>
</div>
<!-- all cagtegory e -->