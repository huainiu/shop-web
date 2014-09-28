<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="length" value="${fn:length(result.data)}"></c:set>
<c:forEach items="${result.data}" varStatus="stats">
	<c:if test="${stats.count <= 3}">
	<c:set value="${result.data[length-stats.count]}" var="address"></c:set>
	<div class="bdgposa">
		<input id="${address.id}_json_val" value='${address}' type="hidden">
		<div class="bdg_adress">
			<div class="bdguser_name">
				<h5>${address.userName}&nbsp;<span>${address.districtName}</span></h5>
			</div>
			<p class="user_adress">${address.detailAddress}</p>
			<p class="user_phone"><span><b class="user_modify" data="${address.id}">修改</b>&nbsp;</span>${address.mobile}</p>
			<div class="adress_mask" data="${address.id}"></div>
		</div>
		<a class="del_adress" data="${address.id}"></a>
		<a class="moren_adress"></a>
	</div>
	</c:if>
</c:forEach>
<script type="text/javascript">
//点击勾选地址
/* $(".bdg_adress").click(function(){
	var bdg_adressh=$(this).outerHeight();
	$(this).parent(".bdgposa").siblings().find(".adress_mask").removeClass("haschecked").css({"display":"none"});
	$(this).find(".adress_mask").addClass("haschecked").css({"height":bdg_adressh,"display":"block"});
	$(".zfbbtn").find("span").hide();
	$(".zfbbtn").removeClass("zfbbtnfail").addClass("zftbtnsuccess");
	$(".bdgselest_tishi").hide();	
	$(".bdg_addadressbg").hide();
	$(".bdg_mask").show();
}); */
$(".bdg_adress").click(function(){
	var bdg_adressh=$(this).outerHeight();
	if(!$(this).find(".adress_mask").hasClass("haschecked")){
		$(this).parent(".bdgposa").siblings().find(".adress_mask").removeClass("haschecked").css({"height":bdg_adressh,"display":"block"});
		$(this).find(".adress_mask").addClass("haschecked").css({"display":"none"});
		$(".buynowbtn").find("span").hide();
		$(this).parent(".bdgposa").find(".del_adress").hide();
		$(this).parent(".bdgposa").siblings().find(".del_adress").show();
		$(this).parent(".bdgposa").siblings().find(".moren_adress").hide();
		$(".bdgselest_tishi,.bdg_addadressbg").hide();
		$(this).parent(".bdgposa").find(".moren_adress").show();
	}else{
		$(".adress_mask").removeClass("haschecked").hide();
		$(".moren_adress").hide();
		$(".del_adress").show();
	}
});
$(".bdg_adress")[0].click();
//关闭左侧地址列表的当前地址
$(".del_adress").click(function(){
	if(confirm("是否永远删除地址")){
		var $this = $(this);
		var id = $this.attr("data");
		$.ajax({
			type : "post",
			url : _basePath + "exchange/address/delete.htm",
			data : {id: id},
			success : function(json) {
				if (json.val.ok == false) {
					alert(json.val.data);
					return;
				}
				$(".adresslist").load(_basePath + "exchange/address/query.htm");
			}
		});
		$(this).parent(".bdgposa").remove();
	};
});
//点击修改地址
$(".user_modify").click(function(e){
	var $this = $(this);
	var id = $this.attr("data");
	eval("json=" + $("#" + id + "_json_val").val());
	new PCAS("seachprov", "seachcity", "seachdistrict", json.provinceName, json.cityName, json.districtName);
	$("#txtaddress").val(json.detailAddress);
	$("#txtzipcode").val(json.zipcode);
	$("#txtreceivername").val(json.userName);
	$("#txtmobile").val(json.mobile);
	$("#txtareacode").val(json.phoneDistrict);
	$("#txttel").val(json.phone);
	$("#txtextension").val(json.phoneExtension);	
	$("#close_addadress_text").text("修改地址");
	$("#txtid").val(json.id);
	$("#txtqq").val(json.qq);
	e.stopPropagation();
	
	var bdgh=$(".bdg_goods").outerHeight()+2;
	var adressh=$(".add_newsadressbg").outerHeight();
	$(".add_newsadressbg").css({"top":(bdgh-adressh)/2}).show();
	$(".bdg_addadressbg").show().css({"z-index":"100002"});
	e.stopPropagation();
});
</script>