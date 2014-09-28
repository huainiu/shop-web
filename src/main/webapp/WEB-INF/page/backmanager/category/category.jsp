<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/jsp/jstl/compont"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="../../common/meta.jsp" />
<title>资源管理</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/backstage.css?t=${today}">
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<script src="${basePath}js/js/drag/core.js?t=${today}" type="text/javascript" language="JavaScript"></script>
<script src="${basePath}js/js/drag/events.js?t=${today}" type="text/javascript" language="JavaScript"></script>
<script src="${basePath}js/js/drag/css.js?t=${today}" type="text/javascript" language="JavaScript"></script>
<script src="${basePath}js/js/drag/coordinates.js?t=${today}" type="text/javascript" language="JavaScript"></script>
<script src="${basePath}js/js/drag/drag.js?t=${today}" type="text/javascript" language="JavaScript"></script>
<script src="${basePath}js/js/drag/dragsort.js?t=${today}" type="text/javascript" language="JavaScript"></script>
<script src="${basePath}js/js/drag/cookies.js?t=${today}" type="text/javascript" language="JavaScript"></script>
<style>
.boxes {
	font-family: Arial, sans-serif;
	list-style-type: none;
	margin: 0px;
	padding: 0px;
	width: 752px;
	float: left;
	display: none;
}

.context li {
	cursor: move;
	position: relative;
	float: left;
	width: 750px;
	border: 1px solid #000;
	text-align: center;
	background-color: #eeeeff;
	list-style-type: none;
	font-family: Arial, sans-serif;
	font-size: 16px;
}

.boxes li {
	background-color: #91C1E3;
}

ul.boxes .second {
	min-height: 50px;
}

.third .box {
	margin: 2px 2px 0px 0px;
	width: auto;
	height: 28px;
	padding-top: 7px;
	padding-left: 15px;
	padding-right: 15px;
	background-color: #eeeeff;
}

#pbox {
	position: relative;
	z-index: 0;
	float: left;
	overflow: hidden;
}

#pbox li {
	float: none;
	margin: 2px 2px 0px 0px;
	width: 100px;
	height: 28px;
	padding-top: 5px;
}

#pbox li.cur {
	background: none repeat scroll 0 0 #F05A18;
	border: 1px solid #C03B00;
	color: #FFFFFF;
}

.treeBox {
	position: relative;
	z-index: 0;
	float: left;
	overflow: hidden;
	margin-right: 300px;
}

span.edit {
	position: relative;
	z-index: 0
}

.treebxClose,.ico_close {
	display: none;
	position: absolute;
	right: 3px;
	width: 11px;
	height: 18px;
	color: #ffffff;
	background-color: red;
	font-size: 14px;
	line-height: 18px;
	cursor: pointer;
}

.ico_close {
	display: block;
	right: 4px;
	top: 1px;
	background-color: transparent;
}

button.add3 {
	position: absolute;
	background-color: white;
	color: #14F35A;
	cursor: pointer;
	float: left;
	right: 1px;
	bottom: 1px; 
	vertical-align : text-bottom;
	font-weight: bolder;
	vertical-align: text-bottom;
	clip:rect(5px5px5px5px);
}
</style>
</head>
<body>
	<!--header s-->
	<div id="header"></div>
	<!--header e-->
	<!--wrap s-->
	<div id="wrap" class="grid-s160 clear-fix">
		<div class="col-main">
			<div class="wrap-main">
				<div class="user" id="user">
					<ul class="user-tab">
						<li class="cur">类目管理</li>
					</ul>
					<!--用户列表 s-->
					<div class="user-box">
						<table width="100%" class="user-table">
							<tr>
								<th>操作:</th>
								<td style="text-align: right;">
									<input type="button" value="添加一级类目" id="add" name="add" class="button" style="margin-right: 10px;" onclick="add();">
									<input type="button" value="添加二级类目" id="add" name="add" class="button" style="margin-right: 10px;" onclick="add2();">
									<input type="button" value="保存" id="add" name="add" class="button" style="margin-right: 10px;" onclick="writes();">
								</td>
							</tr>
						</table>
						<div class="context">
							<div class="treeBox" id="J_treebox">
								<ul id="pbox">
								</ul>
							</div>
							<div id="r_treebox" class="treeBox"></div>
						</div>
					</div>
					<!--用户列表 e-->
				</div>
			</div>
		</div>
		<c:import url="../common/left.jsp"></c:import>
	</div>
	<!--wrap e-->
	<script type="text/javascript" src="${basePath}js/js/table.js?t=${today}"></script>
	<script type="text/javascript">
		$('#category-manage').addClass('cur');

		function writes() {
			var array = get("pbox", "pbox-a");
			var result = new Array();
			$.each(array, function(i, item) {
				var obj = {};
				var name = item.text();
				obj.name = name;
				var id = item.attr('id');
				var array_2_3 = get("r_boxes_" + id, "a");
				obj.subCategoryLinkDtoList = array_2_3;
				result.push(obj);
			});
			$.ajax({
				url : "${basePath}backmanager/category/save.htm",
				type : 'POST',
				data : "content=" + JSON.stringify(result),
				dataType : 'json',
				success : function(result) {
					if (result.msg == 'true') {
						alert('保存成功');
					} else {
						alert('保存失败');
					}
				}
			});
		}
	</script>
</body>
</html>