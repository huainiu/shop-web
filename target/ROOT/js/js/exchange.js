var val = $("#count").val();
var beansPrice = $("#beans-price").val();
new PCAS("province", "city", "area");
$(function() {
	beansInit();
	buttonInit();
	if ($("#refer").val() != "hot") {
		ontimerPrice();
		ontimerSKU();
	}
	exchangeFed.indexInit();
});

function beansInit() {
	var balance = $("#beans-balance").text() * 1;
	var pay = $("#beans-pay").text() * 1;
	if (balance < pay) {
		$("#remain-tip").show();
		$("#buzuP").show();
		$("#zugouP").hide();
		$("#infoDiv").hide();
	} else {
		$("#remain-tip").hide();
		$("#buzuP").hide();
		$("#zugouP").show();
		$("#infoDiv").show();
	}
}

function buttonInit() {
	$("#inc").click(function() {
		++val;
		calculateBeans();
	});
	$("#dec").click(function() {
		if (val > 1) {
			--val;
			calculateBeans();
		}
	});
	$("#address-btn").click(function() {
		var result = validate();
		if (result == false) {
			return;
		}
		saveOrUpdate();
	});
	$("#confirm-btn").click(function() {
		exchange();
	});
	$("input[name=address]").click(function() {
		var val = $(this).val();
		$(".address-item").removeClass("current");
		if (val == "new") {
			clearForm($(".adress-new"));
			$(".adress-new").show();
			$("#address-id").val("");
		} else {
			$("#address-id").val(val);
			$(".adress-new").hide();
		}
		$(this).parents(".address-item").addClass("current");
	});
	$(".update-address").click(function() {
		initAddress($(this).parents(".address-item"));
		$(this).parents(".address-item").find(".address-item-radio").prop("checked", true);
	});
	$(".delete-address").click(function() {
		deleteAddress($(this).parents(".address-item"));
	});
	$("#setAgree").change(function() {
		var b = $(this).is(":checked");
		if (!b) {
			$("#confirm-btn").removeClass("determ-bg");
		} else {
			$("#confirm-btn").addClass("determ-bg");
		}
	});
	$(".bt-green").click(function() {
		location.href = ucenterUrl + "forward.htm?method=/user/trade/common/exchange/index";
	});
}

function clearForm(obj) {
	$(':input', obj).each(function() {
		var type = this.type;
		var tag = this.tagName.toLowerCase();
		if (type == 'text' || type == 'password' || tag == 'textarea')
			this.value = "";
		else if (type == 'checkbox' || type == 'radio')
			this.checked = false;
		else if (tag == 'select')
			this.selectedIndex = 0;
	});
};

function initAddress(obj) {
	var json = eval("(" + obj.find(".itemJson").val() + ")");
	$("#address-id").val(json.id);
	new PCAS("province", "city", "area", json.provinceName, json.cityName, json.districtName);
	$("#detailAddress").val(json.detailAddress);
	$("#zipcode").val(json.zipcode);
	$("#username").val(json.userName);
	$("#mobile").val(json.mobile);
	$("#phone").val(json.phone);
	$("#phoneDistrict").val(json.phoneDistrict);
	$("#phoneExtension").val(json.phoneExtension);
	$("#qq").val(json.qq);
	// $("#setDefault").prop("checked", json.def);
	$(".adress-new").show();
}

function isEmpty(val) {
	if (val == null || val == "")
		return true;
	return false;
}

function validate() {
	var result = {
		userName : $("#username").val(),
		provinceName : $("#province").val(),
		cityName : $("#city").val(),
		districtName : $("#area").val(),
		zipcode : $("#zipcode").val(),
		detailAddress : $("#detailAddress").val(),
		mobile : $("#mobile").val(),
		phoneDistrict : $("#phoneDistrict").val(),
		phone : $("#phone").val(),
		phoneExtension : $("#phoneExtension").val(),
		zipcode : $("#zipcode").val(),
		qq : $("#qq").val()
	};
	if (isEmpty(result.provinceName)) {
		alert("请选择省！");
		return false;
	}
	if (isEmpty(result.cityName)) {
		alert("请选择市！");
		return false;
	}
	if (isEmpty(result.districtName)) {
		alert("请选择县！");
		return false;
	}
	if (isEmpty(result.detailAddress)) {
		alert("地址不能为空！");
		return false;
	}
	if (result.detailAddress.length < 6) {
		alert("地址不能少于6个字符！");
		return false;
	}
	if (result.detailAddress.length > 120) {
		alert("地址不能多于120个字符！");
		return false;
	}
	if (isEmpty(result.zipcode)) {
		alert("邮证编码不能为空！");
		return false;
	}
	if (result.zipcode.length != 6) {
		alert("邮证编码必须6位！");
		return false;
	}
	if (isEmpty(result.userName)) {
		alert("收货人姓名不能为空！");
		return false;
	}
	if (isEmpty(result.mobile) && (isEmpty(result.phoneDistrict) || isEmpty(result.phone))) {
		alert("手机和座机必须填写至少一项！");
		return false;
	}
	if (!isEmpty(result.mobile)) {
		if (result.mobile.length != 11) {
			alert("手机号码长度必须11位！");
			return false;
		}
	} else {
		if (result.phoneDistrict.length != 3 && result.phoneDistrict.length != 4) {
			alert("座机区号必须3位或4位！");
			return false;
		}
	}
	if (isEmpty(result.qq)) {
		alert("qq不能为空！");
		return false;
	}
	if (result.qq.length < 5) {
		alert("qq不能少于6个字符！");
		return false;
	}
	return true;
}

function calculateBeans() {
	$("#beans-pay").text(beansPrice * val);
	$("#beans-pay-2").text(beansPrice * val);
	beansInit();
}

function saveOrUpdate() {
	var id = $("#address-id").val();
	var url = isEmpty(id) ? "exchange/address/add.htm" : "exchange/address/update.htm";
	$.ajax({
		type : "post",
		url : _basePath + url,
		data : {
			id : id,
			userName : $("#username").val(),
			provinceName : $("#province").val(),
			cityName : $("#city").val(),
			districtName : $("#area").val(),
			zipcode : $("#zipcode").val(),
			detailAddress : $("#detailAddress").val(),
			mobile : $("#mobile").val(),
			phoneDistrict : $("#phoneDistrict").val(),
			phone : $("#phone").val(),
			phoneExtension : $("#phoneExtension").val(),
			zipcode : $("#zipcode").val(),
			qq : $("#qq").val()
		},
		success : function(json) {
			if (json.val == null) {
				alert("系统提交失败, 请稍后再尝试");
				return;
			}
			if (json.val.ok == false) {
				alert(json.val.data);
				return;
			}
			location.reload();
		}
	});
}

function deleteAddress(obj) {
	var id = obj.attr("attr-id");
	$.ajax({
		type : "post",
		url : _basePath + "exchange/address/delete.htm",
		data : {
			id : id
		},
		success : function(json) {
			if (json.val == null) {
				alert("系统提交失败, 请稍后再尝试");
				return;
			}
			if (json.val.ok == false) {
				alert(json.val.data);
				return;
			}
			obj.remove();
		}
	});
}

function exchange() {
	if (!validateExchange()) {
		return;
	}
	var userId = getCookie("token");
	if (isEmpty(userId)) {
		alert("没有用户身份, 请先登录");
		return;
	}
	$("#loading").show();
	$.ajax({
		type : "post",
		url : _basePath + "exchange/order.htm",
		data : {
			userId : userId,
			logisticsId : $("input[name=address]:checked").val(),
			number : $("#count").val(),
			bdNum : beansPrice,
			goodsName : $("#title").text(),
			goodsImgUrl : $("#picture").attr("src"),
			goodsUrl : $("#goods-url").val(),
			goodsType : 1,
			goodsPrice : $("#price").text(),
			goodsRemark : $("#remark").val() + " 规格:" + $("#sel-sku").text(),
			sku : $("#sel-sku").text(),
			refer : $("#refer").val(),
			docId : $("#docId").val(),
			referRule : $("#referRule").val()
		},
		success : function(json) {
			$("#loading").hide();
			if (json.val == null) {
				alert("系统提交失败, 请稍后再尝试");
				return;
			}
			if (json.val.ok == false) {
				alert(json.val.data);
				return;
			}
			exchangeFed.showDialog();
		}
	});
}

function ontimerPrice() {
	if (beansPrice <= 0) {
		$.ajax({
			type : "post",
			url : _basePath + "exchange/ontimePrice.htm",
			timeout : 5000,
			data : {
				url : $("#goods-url").val(),
			},
			success : function(json) {
				if (json.code == 0 && json.val > 0) {
					$("#price").text(json.val);
					beansPrice = calBean(json.val);
					$("#beans-price").val(beansPrice);
					calculateBeans();
				} else {
					alert("价格获取异常, 请联系客服");
					// window.location.href = "http://www.b5m.com";
				}
			},
			complete : function(XMLHttpRequest, status) {
				if (status == 'timeout') {
					alert("价格获取异常, 请联系客服");
					// window.location.href = "http://www.b5m.com";
				}
			}
		});
	}
}

function buildSKU(val) {
	for ( var index = 0; index < val.skuProps.length; index++) {
		var skuProp = val.skuProps[index];
		var html = '<div class="list-cima"><span class="cima-l">' + skuProp.name + '：</span> <span class="cima-alink">';
		for ( var i = 0; i < skuProp.props.length; i++) {
			html += '<a class="sku" href="javascript:void(0);">' + skuProp.props[i] + '<em></em></a> ';
		}
		html += '</span></div>';
		$("#buy-count").before($(html));
	}
}

function ontimerSKU() {
	$.ajax({
		type : "post",
		url : _basePath + "ontimesku/single.htm",
		timeout : 5000,
		data : {
			url : $("#goods-url").val(),
			docId : $("#docId").val()
		},
		success : function(json) {
			var val = json.val;
			if (!val.skuProps || val.skuProps.length < 1)
				return;
			if (val.sku && val.skuProps) {
				buildSKU(val);
			}
			// 选择商品规格信息
			$("a.sku").click(function() {
				$(this).addClass("alink-bord").siblings().removeClass("alink-bord");
				var goodsSpec = getGoodsSpec();
				var price = val.sku[goodsSpec];
				$("#sel-sku").text("" + goodsSpec + "");
				if (price) {
					$("#price").text(price);
					beansPrice = calBean(price);
					$("#beans-price").val(beansPrice);
					calculateBeans();
				}
			});
		}
	});
}

function getGoodsSpec() {
	var goosSpec = "";
	$(".cima-alink .alink-bord").each(function() {
		var propName = $(this).parent().siblings(".cima-l").text().replace("：", "");
		var propValue = $(this).text();
		goosSpec += propName + ":" + propValue + ";";
	});
	if (goosSpec.length > 0 && goosSpec.charAt(goosSpec.length - 1) == ';') {
		goosSpec = goosSpec.substring(0, goosSpec.length - 1);
	}
	return goosSpec;
}

function calBean(price) {
	var pc = parseFloat(price);
	return parseInt(pc.toFixed(2).toString().replace(".", ""));
}

function error(msg) {
	$("#error-tips").text(msg);
	$(".select-address").show();
}

function validateExchange() {	
	if (isNaN($("input[name=address]:checked").val())) {
		error("请先选择收货地址");
		return false;
	}
	if (!$("#setAgree").is(":checked")) {
		error("请阅读兑换规则并同意");
		return false;
	}
	if (Number($("#beans-pay").text()) > Number($("#beans-balance").text())) {
		error("帮钻余额不足");
		return false;
	}
	if (!validateSKU()) {
		error("请选择商品的具体规格");
		return false;
	}
	return true;
}

function validateSKU() {
	var $props = $(".cima-alink");
	var num = 0;
	for ( var index = 0; index < $props.length; index++) {
		if ($($props[index]).find("a.alink-bord").length > 0) {
			num++;
		}
	}
	if (num < $props.length) {
		return false;
	}
	return true;
}

function getCookie(name) {
	var strCookie = document.cookie;
	var arrCookie = strCookie.split("; ");
	for ( var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split("=");
		if (arr[0] == name)
			return arr[1];
	}
	return "";
}