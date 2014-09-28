//打开遮罩
function Daigou(){}
/*Daigou.show = function(data, $bodyopen){
	if(data.val.isDaigou){
		setValue(data.val.daigouSource);
		//判断商品信息位置
		var windowh = $(window).height();
		var windoww = $(window).width();
		var bodyh = $("body").outerHeight();
		var bdg_goodsh = $(".bdg_goods").outerHeight();
		var bdg_goodsw = $(".bdg_goods").outerWidth();
		var t = document.documentElement.scrollTop || document.body.scrollTop; 
		$(".bdg_mask").css({"height":bodyh,"minHeight":windowh});
		$(".bdg_goods").css({"left":(windoww-(bdg_goodsw+2))/2,"top":(windowh-(bdg_goodsh+2))/2+t});
		$(".bdg_mask,.bdg_goods").show();
		if(!isLogin){
			$(".bdg_posadress").hide();
		}else{
			$(".adresslist").load(_basePath + "exchange/address/query.htm");
		}
	}
};*/
//已经时时获取价格了，这里就不再时时获取了
Daigou.compareShow = function(data, $bodyopen){
	Daigou.initBeforShow();
	if(data.val.isDaigou){
		setValue(data.val.daigouSource);
		//判断商品信息位置
		var windowh = $(window).height();
    	var windoww = $(window).width();
    	var bodyh = $("body").outerHeight();
    	var bdg_goodsh = $(".bdg_goods").outerHeight();
    	var bdg_goodsw = $(".bdg_goods").outerWidth();
    	var t = document.documentElement.scrollTop || document.body.scrollTop; 
    	$(".bdg_mask,.bdg_addadressbg").css({"height":bodyh,"minHeight":windowh});
    	$(".bdg_goods").css({"left":(windoww-(bdg_goodsw+2))/2,"top":(windowh-(bdg_goodsh+2))/2+t});
    	
    	$(".bdg_mask,.bdg_goods").show();
    	if(data.val.isDone){//下架
    		$(".bdg_goods_have").hide();
    		$(".bdg_goods_none").show();
    	}else{
    		$(".bdg_goods_none").hide();
    		$(".bdg_goods_have").show();
    		
    		var $_this = $('.progress_barcor');
			$.ajax({
				url:_basePath + "ontimesku/single.htm",
				data:{"docId": data.val.daigouSource.DOCID,"url":data.val.daigouSource.originUrl},
				type:"POST",
				success:function(result){
					$(".guige_refresh").html("");
					Daigou.showGuige($_this, result.val);
				},error:function(result){
					$(".guige_refresh").html("");
					Daigou.showGuige($_this, result.val);
				}
			});
    		//进度条显示
    		/*$('.progress_barcor').animate({"width":"310px"},3000,function(){
    			var $_this = $(this);
    			$.ajax({
    				url:_basePath + "ontimesku/single.htm",
    				data:{"docId": data.val.daigouSource.DOCID,"url":data.val.daigouSource.originUrl},
    				type:"POST",
    				success:function(result){
    					$(".guige_refresh").html("");
    					Daigou.showGuige($_this, result.val);
    				},error:function(result){
    					$(".guige_refresh").html("");
    					Daigou.showGuige($_this, result.val);
    				}
    			});
    		});*/
    	}
		/*if(!isLogin){
			$(".bdg_posadress").hide();
		}else{
			$(".adresslist").load(_basePath + "exchange/address/query.htm");
		}*/
	}
};
Daigou.initBeforShow = function(){
	$(".buynowbtn").unbind("click");
	$(".zfbbtn").unbind("click");
	$(".btnpayment").removeClass("btnpaymentcur");
	$(".guige_refresh").html("");
	$(".guige_refresh").hide();
	$(".bdg_progress_bar").show();
}
Daigou.show = function(data, $bodyopen){
	Daigou.initBeforShow();
	var $this = $bodyopen;
	var docId = $this.attr("docid");
	var url = $this.attr("url");
	if(data.val.isDaigou){
		$.ajax({
			url:_basePath + "ontimeprice/single.htm",
            data:{"docId":docId,"url":url},
            type:"POST",
            success:function(result){
            	if(result.val.status == 1 && result.val.price && result.val.price != -1){
        			data.val.daigouSource.Price = result.val.price;
        		}
            	setValue(data.val.daigouSource);
            	//判断商品信息位置
            	var windowh = $(window).height();
            	var windoww = $(window).width();
            	var bodyh = $("body").outerHeight();
            	var bdg_goodsh = $(".bdg_goods").outerHeight();
            	var bdg_goodsw = $(".bdg_goods").outerWidth();
            	var t = document.documentElement.scrollTop || document.body.scrollTop; 
            	$(".bdg_mask,.bdg_addadressbg").css({"height":bodyh,"minHeight":windowh});
            	$(".bdg_goods").css({"left":(windoww-(bdg_goodsw+2))/2,"top":(windowh-(bdg_goodsh+2))/2+t});
            	$(".bdg_mask,.bdg_goods").show();
            	if(result.val.status == 0){//表示下架了
            		$(".bdg_goods_have").hide();
            		$(".bdg_goods_none").show();
            	}else{
            		$(".bdg_goods_none").hide();
            		
            		var $_this = $('.progress_barcor');
        			$.ajax({
        				url:_basePath + "ontimesku/single.htm",
        				data:{"docId":docId,"url":url},
        				type:"POST",
        				success:function(result){
        					Daigou.showGuige($_this, result.val);
        				},error:function(result){
        					Daigou.showGuige($_this, result.val);
        				}
        			});
            		//进度条显示
            		/*$('.progress_barcor').animate({"width":"310px"},3000,function(){
            			var $_this = $(this);
            			$.ajax({
            				url:_basePath + "ontimesku/single.htm",
            				data:{"docId":docId,"url":url},
            				type:"POST",
            				success:function(result){
            					Daigou.showGuige($_this, result.val);
            				},error:function(result){
            					Daigou.showGuige($_this, result.val);
            				}
            			});
            		});*/
            		/*if(!isLogin){
            			$(".bdg_posadress").hide();
            		}else{
            			$(".adresslist").load(_basePath + "exchange/address/query.htm");
            		}*/
            	}
            }
		});
	}
};
//显示规格
Daigou.showGuige = function($this, val){
	onBtnClick();
	$this.parent(".bdg_progress_bar").hide().parent(".goods_informationlr").siblings(".guigeboxcurbox").find(".guige_refresh").show();
	if(!val.skuProps || val.skuProps.length < 1) return;
	for(var index = 0; index < val.skuProps.length; index++){
		var skuProp = val.skuProps[index];
		var $container = $("<dl></dl>").append("<dt>" + skuProp.name + "</dt>");
		var $dd = $("<dd></dd>");
		$container.append($dd);
		$container.append("<div class=\"clear\"></div>");
		var $ul = $("<ul class=\"ggxz gg_prop\"></ul>");
		$dd.append($ul);
		for(var i = 0; i < skuProp.props.length; i++){
			$ul.append("<li><a href=\"javascript:void(0)\">" + skuProp.props[i] + "<b></b></a></li>");
		}
		$(".guige_refresh").append($container);
	}
	//选择商品规格信息
	$(".ggxz>li").click(function(){
		$(this).addClass("cur").siblings().removeClass("cur");
		var $props = $(".gg_prop");
		var num = 0;
		var goodsSpec = getGoodsSpec();
		var price = val.sku[goodsSpec];
		if(price){
			$("#_goodsPrice").val(price);
			var pval = $(".p-text").val();
			pval = Number(pval);
			modifyPrice(pval);
		}
		for(var index = 0; index < $props.length; index++){
			if($($props[index]).find("li.cur").length > 0){
				num++;
			}
		}
		if(num == $props.length){
			closeggts();
		};
	});
	//关闭选择商品规格信息错误提示
	$(".guige_close").click(function(){
		closeggts();
	});
};

function addDaigoucart(_docId, _goodsPriceAvg, _goodsSpec, callback) {
	$.ajax({
		type : "post",
		url : _basePath + "daigoucart/add.htm",
		async: false,
		data : {
			docId : _docId,
			priceAvg : _goodsPriceAvg,
			goodsSpec : _goodsSpec,
			count : pval
		},success : function(json) {
			if (typeof callback === 'function') {
				callback(json);
			}
		}
	});
}
function onBtnClick(){
	$(".buynowbtn").click(function(){
		var $props = $(".gg_prop");
		for(var index = 0; index < $props.length; index++){
			if($($props[index]).find("li.cur").length<=0){
				$(".guigeboxcurbox").addClass("guigeboxcur");
				$(".gg_error_message").show();
				$(".guige_close").css({"display":"block"});
				$(".buynowbtn").removeClass("zftbtnbuynow").addClass("zftbtnbuynow_addgg");
				$(".zfbbtn").removeClass("zftbtnsuccess").addClass("zftbtnsuccess_addgg");
				return;
			}
		}
		var _docId = $("#_docId").val();
		var _goodsPriceAvg = $("#_goodsPriceAvg").val();
		var _goodsSpec = getGoodsSpec();
		var pval = $(".p-text").val();
		addDaigoucart(_docId, _goodsPriceAvg, _goodsSpec, function(json){
			$(".bdg_mask,.bdg_goods").hide();
			jumpTo("http://cart.b5m.com");
		});
		/*if(!isLogin){
		goUrlByATag($("#tp-login").attr("href"));
	}else{*/
		/*var $props = $(".gg_prop");
		for(var index = 0; index < $props.length; index++){
			if($($props[index]).find("li.cur").length<=0){
				$(".guigeboxcurbox").addClass("guigeboxcur");
				$(".gg_error_message").show();
				$(".guige_close").css({"display":"block"});
				$(".buynowbtn").removeClass("zftbtnbuynow").addClass("zftbtnbuynow_addgg");
				$(".zfbbtn").removeClass("zftbtnsuccess").addClass("zftbtnsuccess_addgg");
				return;
			}
		}
		var divlen=$(".adresslist").find(".bdgposa").length;
		if(divlen<=0){
			$(".bdg_addadressbg").show().css({"z-index":"100000"});
			$(".add_useradress").css({"z-index":"1000000"});
			$(".bdg_tishi").show();
			$(".bdg_mask").hide();
		}else{
			if($(".haschecked").length>0){
				$(".adresslistbox").css({"z-index":"100000"});
				$(".bdg_mask").show();
				$(".bdgselest_tishi").hide();
				//生成代购订单
				var url = createDaigouOrder();
				jumpTo(url);
			}else{
				$(".bdg_addadressbg").show().css({"z-index":"100000"});
				$(".bdg_mask").hide();
				$(".adresslistbox").css({"z-index":"1000000"});
				$(".bdgselest_tishi").show();
			}
			$(".add_useradress").css({"z-index":"100000"});
			$(".bdg_tishi").hide();
		}
		closeggts();*/
//	}
	});
	$(".zfbbtn").click(function(){
		var $props = $(".gg_prop");
		for(var index = 0; index < $props.length; index++){
			if($($props[index]).find("li.cur").length<=0){
				$(".guigeboxcurbox").addClass("guigeboxcur");
				$(".gg_error_message").show();
				$(".guige_close").css({"display":"block"});
				$(".buynowbtn").removeClass("zftbtnbuynow").addClass("zftbtnbuynow_addgg");
				$(".zfbbtn").removeClass("zftbtnsuccess").addClass("zftbtnsuccess_addgg");
				return;
			}
		}
		var _docId = $("#_docId").val();
		var _goodsPriceAvg = $("#_goodsPriceAvg").val();
		var _goodsSpec = getGoodsSpec();
		var pval = $(".p-text").val();
		$.ajax({
			type : "post",
			url : _basePath + "daigoucart/add.htm",
			async: false,
			data : {
				docId : _docId,
				priceAvg : _goodsPriceAvg,
				goodsSpec : _goodsSpec,
				count : pval
			},success : function(json) {
				$(".btnpayment").addClass("btnpaymentcur");
				$(".shoppingimg").addClass("shoppingimgcur");
				
				$(".btnpayment").attr("href", "http://cart.b5m.com");
				alert(json.msg);
			}
		});
	});
}
//得到地区码
/*function getAreaID() {
	var area = 0;
	if ($("#seachdistrict").val() != "0") {
		area = $("#seachdistrict").val();
	} else if ($("#seachcity").val() != "0") {
		area = $("#seachcity").val();
	} else {
		area = $("#seachprov").val();
	}
	return area;
}
function showAreaID() {
	//地区码
	var areaID = getAreaID();
	//地区名
	var areaName = getAreaNamebyID(areaID);
	alert("您选择的地区码：" + areaID + "      地区名：" + areaName);
}
//根据地区码查询地区名
function getAreaNamebyID(areaID) {
	var areaName = "";
	if (areaID.length == 2) {
		areaName = area_array[areaID];
	} else if (areaID.length == 4) {
		var index1 = areaID.substring(0, 2);
		areaName = area_array[index1] + " " + sub_array[index1][areaID];
	} else if (areaID.length == 6) {
		var index1 = areaID.substring(0, 2);
		var index2 = areaID.substring(0, 4);
		areaName = area_array[index1] + " " + sub_array[index1][index2] + " " + sub_arr[index2][areaID];
	}
	return areaName;
}
var $seachprov = $("#seachprov");//省
var $seachcity = $("#seachcity");//市
var $seachdistrict = $("#seachdistrict");//县
var $txtaddress = $("#txtaddress");//街道地址
var $txtzipcode = $("#txtzipcode");//邮证编码
var $txtreceivername = $("#txtreceivername");//收货人姓名
var $txtmobile = $("#txtmobile");//手机号码
var $txtareacode = $("#txtareacode");//区号
var $txttel = $("#txttel");//座机号
var $txtqq = $("#txtqq");//qq
var $txtextension = $("#txtextension");//分机号
//提交
function but_submit() {
	if ($seachprov.val() == "0") {
		alert("请选择省！");
		$seachprov.focus();
		return
	}
	if ($seachcity.val() == "0") {
		alert("请选择市！")
		$seachcity.focus();
		return
	}
	if ($seachdistrict.val() == "0") {
		alert("请选择县！")
		$seachdistrict.focus();
		return;
	}
	if ($txtaddress.val() == "") {
		alert("地址不能为空！");
		$txtaddress.focus();
		return;
	}
	if ($txtaddress.val().length < 6) {
		alert("地址不能少于6个字符！");
		$txtaddress.focus();
		return;
	}
	if ($txtaddress.val().length > 120) {
		alert("地址不能多于120个字符！");
		$txtaddress.focus();
		return;
	}
	if ($txtzipcode.val() == "") {
		alert("邮证编码不能为空！");
		$txtzipcode.focus();
		return;
	}
	if ($txtzipcode.val()) {
		if (isNaN($txtzipcode.val())) {
			alert("邮证编码输入有误！");
			$txtaddress.focus();
			return;
		}
		if ($txtzipcode.val().length < 6) {
			alert("邮证编码不能少于6位！");
			$txtaddress.focus();
			return;
		}
	}
	if ($txtreceivername.val() == "") {
		alert("收货人姓名不能为空！");
		$txtreceivername.focus();
		return;
	}
	if ($txtmobile.val() == "" && ($txtareacode.val() == "" || $txttel.val() == "")) {
		alert("手机和座机必须填写至少一项！");
		$txtmobile.focus();
		return;
	}
	if ($txtmobile.val() != "") {
		if ($txtmobile.val().length < 11) {
			alert("手机号码长度不足11位！");
			$txtmobile.focus();
			return;
		}
	}
	if (isNaN($txtmobile.val())) {
		alert("手机号码输入格式不对！");
	}
	if ($txtareacode.val() != "" && $txttel.val() != "") {
		var flag = true;
		if ($txtareacode.val().length < 3 || $txtareacode.val().length > 4) {
			flag = false;
		}
		if ($txttel.val().length != 8) {
			flag = false;
		}
		if(!flag){
			alert("座机号码有误！");
			$txtareacode.focus();
			return;
		}
	}
	var id = $("#txtid").val();
	if(!id) id = "";
	var url = "" == "" ? "exchange/address/add.htm" : "exchange/address/update.htm";
	$.ajax({
		type : "post",
		url : _basePath + url,
		data : {
			id: id,
			userName : $txtreceivername.val(),
			provinceName : $seachprov.val(),
			cityName : $seachcity.val(),
			districtName : $seachdistrict.val(),
			zipcode : $txtzipcode.val(),
			detailAddress : $txtaddress.val(),
			mobile : $txtmobile.val(),
			qq : $txtqq.val(),
			phoneDistrict : $txtareacode.val(),
			phone : $txttel.val(),
			phoneExtension : $txtextension.val(),
			def : $("#setDefault").is(":checked") ? 0 : 1
		},
		success : function(json) {
			if (json.val.ok == false) {
				alert(json.val.data);
				return;
			}
			but_cancel();
			$(".closetishi")[0].click();
			$(".add_newsadressbg").hide();
			$(".adresslist").load(_basePath + "exchange/address/query.htm");
		}
	});
}
//取消
function but_cancel() {
	$seachprov.val("0");//省
	$seachcity.val("0");//市
	$seachdistrict.val("0");;//县
	$txtaddress.val("");//街道地址
	$txtzipcode.val("");//邮证编码
	$txtreceivername.val("");//收货人姓名
	$txtmobile.val("");//手机号码
	$txtareacode.val("");//区号
	$txttel.val("");//座机号
	$txtextension.val("");//分机号
	$("#txtid").val("");
	$(".add_newsadressbg").hide();
	$(".bdg_addadressbg").hide().css({"z-index":"100000"});
	$(".form_adress").removeClass("form_adresscur").find(".hide").hide();
	$(".add_newsadressbg").hide();
}*/
$(".bodyopen").on("click", function(){
	//判断商品信息位置
	var windowh = $(window).height();
	var windoww = $(window).width();
	var bodyh = $("body").outerHeight();
	var bdg_goodsh = $(".bdg_goods").outerHeight();
	var bdg_goodsw = $(".bdg_goods").outerWidth();
	var t = document.documentElement.scrollTop || document.body.scrollTop; 
	$(".bdg_mask").css({"height":bodyh,"minHeight":windowh});
	$(".bdg_goods").css({"left":(windoww-(bdg_goodsw+2))/2,"top":(windowh-(bdg_goodsh+2))/2+t});
	$(".bdg_mask,.bdg_goods").show();
});
//取消错误规格信息提示
function closeggts(){
	$(".guigeboxcurbox").removeClass("guigeboxcur");
	$(".gg_error_message,.guige_close").hide();
	$(".buynowbtn").removeClass("zftbtnbuynow_addgg").addClass("zftbtnbuynow");
	$(".zfbbtn").removeClass("zftbtnsuccess_addgg").addClass("zftbtnsuccess");
}
function getGoodsSpec(){
	var goosSpec = "";
	$(".gg_prop .cur").each(function(){
		var propName = $(this).parent().parent().siblings("dt").text();
		var propValue = $(this).children("a").text();
		goosSpec = goosSpec + propName + ":" + propValue + ";";
	});
	if(goosSpec.length > 0 && goosSpec.charAt(goosSpec.length - 1) == ';'){
		goosSpec = goosSpec.substring(0, goosSpec.length - 1);
	}
	return goosSpec;
}
function modifyPrice(num){
	var _goodsPriceAvg = $("#_goodsPriceAvg").val();
	var _goodsPrice = $("#_goodsPrice").val();
	//如果大于全网均价，则全网均价等于商品价格
	if(parseFloat(_goodsPriceAvg) < parseFloat(_goodsPrice)){
		$("#_goodsPriceAvg").val(_goodsPrice);
		_goodsPriceAvg = _goodsPrice;
	}
	var remainPrice = (parseFloat(_goodsPriceAvg) - parseFloat(_goodsPrice)) * num;
	$("#averageprice").html("<span>¥</span>" + _goodsPriceAvg);
	$("#daigouprice").html("<span>¥</span>" + _goodsPrice);
	$("#remainprice").html("<span>¥</span>" + remainPrice.toFixed(2));
	$(".price_listz").html(" <span>¥" + parseFloat(parseFloat(_goodsPrice) * num).toFixed(2) + "</span>总价");
	
}
$(function() {
	//$("#seachdistrict_div").hide();
	//new PCAS("seachprov", "seachcity", "seachdistrict");
	//关闭遮罩
	$(".closeall").click(function(e){
		e.stopPropagation();
		$(".bdg_mask,.bdg_goods").hide();
	});
	//打开添加地址弹窗
	/*$(".add_useradress").click(function(){
		$("#close_addadress_text").text("添加新地址");
		//$(".add_newsadressbg").show();
		addadress();
	});*/
	//关闭添加地址弹窗
	/*$(".close_addadress").click(function(){
		but_cancel();
	});*/
	//判断有没勾选默认地址
	/*function createDaigouOrder(){
		var logisticsId = $(".adress_mask.haschecked").attr("data");
		var _goodsPriceAvg = $("#_goodsPriceAvg").val();
		var pval=$(".p-text").val();
		pval = Number(pval);
		var _goodsPrice = parseFloat(parseFloat($("#_goodsPrice").val()) * pval + 12.00).toFixed(2);
		var _goodsUrl = $("#_goodsUrl").val();
		var _goodsImgUrl = $("#_goodsImgUrl").val();
		var _goodsName = $("#_goodsName").val();
		var _goodsSource = $("#_goodsSource").val();
		var _docId = $("#_docId").val();
		var _goodsNum = $("#_goodsNum").val();
		var _goodsSpec = getGoodsSpec();
		var url = null;
		$.ajax({
			type : "post",
			url : _basePath + "exchange/daigou/order.htm",
			async: false,
			data : {
				logisticsId : logisticsId,
				goodsPriceAvg : _goodsPriceAvg,
				goodsPrice : _goodsPrice,
				goodsUrl : _goodsUrl,
				goodsImgUrl : _goodsImgUrl,
				goodsName : _goodsName,
				goodsSource : _goodsSource,
				docId : _docId,
				goodsNum : _goodsNum,
				goodsSpec : _goodsSpec
			},success : function(json) {
				if (json.code == -1) {
					alert(json.val);
					return;
				}else if(json.val.ok == false) {
					alert(json.val.data);
					return;
				}else if(json.val.error){
					alert(json.val.error);
					return;
				}
				$(".bdg_zfbbg").show();
				url = json.val.url;
			}
		});
		return url;
	}*/
	/*$(".zftbtnsuccess").click(function(){
		$(".shopping_cart").find(".btnpayment").addClass("btnpaymentcur");
		$(".shopping_cart").find(".shoppingimg").addClass("shoppingimgcur");
	});*/
	/*function addadress(){
		var bdgh=$(".bdg_goods").outerHeight()+2;
		var adressh=$(".add_newsadressbg").outerHeight();
		$(".add_newsadressbg").css({"top":(bdgh-adressh)/2}).show();
		$(".bdg_addadressbg").show().css({"z-index":"100002"});
	}*/
	/*$(".zfbbtn").click(function(){
		if(!isLogin){
			goUrlByATag($("#tp-login").attr("href"));
		}else{
			var divlen=$(".adresslist").find(".bdgposa").length;
			if(divlen<=0){
				$(".bdg_addadressbg").show().css({"z-index":"100000"});
				$(".add_useradress").css({"z-index":"1000000"});
				$(".bdg_tishi").show();
				$(".bdg_mask").hide();
			}else{
				if($(".haschecked").length>0){
					$(".adresslistbox").css({"z-index":"100000"});
					$(".bdg_mask").show();
					$(".bdgselest_tishi").hide();
					var logisticsId = $(".adress_mask.haschecked").attr("data");
					var _goodsPriceAvg = $("#_goodsPriceAvg").val();
					var pval=$(".p-text").val();
					pval = Number(pval);
					var _goodsPrice = parseFloat(parseFloat($("#_goodsPrice").val()) * pval + 12.00).toFixed(2);
					var _goodsUrl = $("#_goodsUrl").val();
					var _goodsImgUrl = $("#_goodsImgUrl").val();
					var _goodsName = $("#_goodsName").val();
					var _goodsSource = $("#_goodsSource").val();
					var _docId = $("#_docId").val();
					var _goodsNum = $("#_goodsNum").val();
					$.ajax({
						type : "post",
						url : _basePath + "exchange/daigou/order.htm",
						async: false,
						data : {
							logisticsId : logisticsId,
							goodsPriceAvg : _goodsPriceAvg,
							goodsPrice : _goodsPrice,
							goodsUrl : _goodsUrl,
							goodsImgUrl : _goodsImgUrl,
							goodsName : _goodsName,
							goodsSource : _goodsSource,
							docId : _docId,
							goodsNum : _goodsNum
						},success : function(json) {
							if (json.code == -1) {
								alert(json.val);
								return;
							}else if(json.val.ok == false) {
								alert(json.val.data);
								return;
							}else if(json.val.error){
								alert(json.val.error);
								return;
							}
							$(".bdg_zfbbg").show();
							window.open(json.val.url, "_blank");
//							jumpTo(json.val.url);
						}
					});
				}else{
					$(".bdg_addadressbg").show().css({"z-index":"100000"});
					$(".adresslistbox").css({"z-index":"1000000"});
					$(".bdgselest_tishi").show();
					$(".bdg_mask").hide();
				}
				$(".add_useradress").css({"z-index":"100000"});
				$(".bdg_tishi").hide();
			}
		}
	});*/
	//判断有没勾选默认地址
	/*$(".zfbbtn").click(function(){
		var divlen=$(".adresslist").find(".bdgposa").length;
		if(divlen<=0){
			$(".bdg_addadressbg").show().css({"z-index":"100000"});
			$(".add_useradress").css({"z-index":"1000000"});
			$(".bdg_tishi").show();
		}else{
			if($(".haschecked").length>0){
				$(".adresslistbox").css({"z-index":"100000"});
				$(".bdg_mask").show();
				$(".bdgselest_tishi").hide();
			}else{
				$(".bdg_addadressbg").show().css({"z-index":"100000"});
				$(".adresslistbox").css({"z-index":"1000000"});
				$(".bdgselest_tishi").show();
			}
			$(".add_useradress").css({"z-index":"100000"});
			$(".bdg_tishi").hide();
		}	
	});*/
	//关闭添加地址提示语
	$(".closetishi").click(function(){
		$(".bdg_tishi").hide();	
		$(".bdg_addadressbg").hide();
		$(".bdg_mask").show();
	});
	$(".closesel_ts").click(function(){
		$(".bdgselest_tishi").hide();	
		$(".bdg_addadressbg").hide();
		$(".bdg_mask").show();
	});
	//关闭支付状态
	$(".payfail,.close_zfbbox").click(function(){
		$(".bdg_addadressbg").hide().css({"z-index":"9999"});
		$(".bdg_zfbbg").hide();
		/*if(){
			$(".add_useradress").css({"z-index":"100000"});
		}else{
			$(".adresslist").css({"z-index":"100000"});
		}*/
		
	});
	//鼠标滑过添加地址
	$(".user_phone").find("b").hover(function(){
		$(this).addClass("cur");
	},function(){
		$(this).removeClass("cur");
	});
	//点击减号
	$(".p-reducel").click(function(){
		if(!$(this).hasClass("p-reduceno")){
			var pval = $(".p-text").val();
			pval = Number(Number(pval)-Number(1));
			$(".p-text").val(pval);
			if($(".p-text").val() <= 1){
				$(".p-reducel").addClass("p-reduceno");
			}else{
				$(".p-reducel").removeClass("p-reduceno");
			}
			modifyPrice(pval);
		}
	});
	//点击加号
	$(".p-reducer").click(function(){
		var pval=$(".p-text").val();
		pval = Number(Number(pval) + Number(1));
		$(".p-text").val(pval);
		if(isNaN($(".p-text").val()) || $(".p-text").val()<=1){
			$(".p-reducel").addClass("p-reduceno");
		}else{
			$(".p-reducel").removeClass("p-reduceno");
		}
		modifyPrice(pval);
	});
	$(".p-text").keyup(function(){
		if(isNaN($(".p-text").val()) || $(".p-text").val()<=1){
			$(".p-reducel").addClass("p-reduceno");
			$(".p-text").val(1);
		}else{
			$(".p-reducel").removeClass("p-reduceno");
		}
		var pval=$(".p-text").val();
		pval = Number(pval);
		modifyPrice(pval);
	});
	$(".problemicon").hover(function(){
		$(".b5mcommitment_posbg").show();
	},function(){
		$(".b5mcommitment_posbg").hide();
	});
});
function jumpTo(url){
	var el = document.createElement("a");
	document.body.appendChild(el);
	el.href=url;
	el.target = "_blank";
	if(el.click) {
        el.click();
    }else{//safari浏览器click事件处理
        try{
            var evt = document.createEvent('Event');
            evt.initEvent('click',true,true);
            el.dispatchEvent(evt);
        }catch(e){//alert(e)
        	window.localhost.href = url;
        };       
    }
}
$(function() {
	//判断删除弹窗位置
	window.onload = windowHeight;
	function windowHeight() {  
		var windowh = $(window).height();
		var windoww = $(window).width();
		var bodyh = $("body").outerHeight();
		var delh = $(".bdg_goodsdelbg").outerHeight();
		var delw = $(".bdg_goodsdelbg").outerWidth();
		var t = document.documentElement.scrollTop || document.body.scrollTop;
		$(".bdg_mask").css({"height":bodyh,"minHeight":windowh});
		$(".bdg_goodsdelbg").css({"left":(windoww-delw)/2,"top":(windowh-delh)/2+t});
	}  
	setInterval(windowHeight, 500);
	//点击商品列表查看操作
	$(".show_bdggoods").click(function(){
		//判断商品信息位置
		var windowh = $(window).height();
		var windoww = $(window).width();
		var bodyh = $("body").outerHeight();
		var bdg_goodsh = $(".bdg_goods").outerHeight();
		var bdg_goodsw = $(".bdg_goods").outerWidth();
		var t = document.documentElement.scrollTop || document.body.scrollTop; 
		$(".bdg_mask").css({"height":bodyh,"minHeight":windowh});
		$(".bdg_goods").css({"left":(windoww-(bdg_goodsw+2))/2,"top":(windowh-(bdg_goodsh+2))/2+t});
		$(".bdg_mask,.bdg_goods").show();
	});
	//点击商品列表删除操作
	$(".del_bdggoods").click(function(){
		$(".bdg_mask,.bdg_goodsdelbg").show();
	});
	//点击删除弹窗的按钮操作
	$(".goodsdel_del,.goodsdel_cansel").click(function(){
		$(".bdg_mask,.bdg_goodsdelbg").hide();
	});
});
function setValue(daigouSource){
	console.log(daigouSource.HighPrice + ":" + daigouSource.Price);
	if(daigouSource.HighPrice < daigouSource.Price){
		daigouSource.HighPrice = daigouSource.Price;
	}
	$("#averageprice").html("<span>¥</span>" + daigouSource.HighPrice);
	$("#daigouprice").html("<span>¥</span>" + daigouSource.Price);
	$("#remainprice").html("<span>¥</span>" + (daigouSource.HighPrice - daigouSource.Price).toFixed(2));
	$(".price_listz").html(" <span>¥" + parseFloat(parseFloat(daigouSource.Price)).toFixed(2) + "</span>总价");
	$(".imgbox").find("img").attr("src", daigouSource.Picture);
	$(".zftbtnsuccess").attr("docid", daigouSource.DOCID);
	$(".goodsname").html(daigouSource.Title);
	
	$("#_goodsPriceAvg").val(daigouSource.HighPrice);
	$("#_goodsPrice").val(parseFloat(daigouSource.Price).toFixed(2));
	$("#_goodsUrl").val(daigouSource.Url);
	$("#_goodsImgUrl").val(daigouSource.Picture);
	$("#_goodsName").val(daigouSource.Title);
	$("#_goodsSource").val(daigouSource.Source);
	$("#_docId").val(daigouSource.DOCID);
	$(".p-text").val(1);
}