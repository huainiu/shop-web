function CartCenter(opt) {
	this.docId = opt.docId || '';
	this.url = opt.url || '';
	this.priceAvg = opt.priceAvg || '';
	this.ref = opt.ref || '';
	this.paramurl = opt.paramurl || '';
	this.origin = opt.origin || '';
	this.subPrice = Number(opt.subPrice) || 0.5;
	this.key = opt.key || opt.docId;
	this.b5tPrice = opt.b5tPrice || '';
	this.title = opt.title;
	this.imgPath =opt.imgPath;
}
CartCenter.prototype.init = function() {
	var _this = this;
	// 显示规格
	this.showGuige();
	// 商品详情
	this.shopInfo();
	// 点击减号
	$(".btn-subtraction").click(function() {
		if (!$(this).hasClass("p-reduceno")) {
			var pval = $(".p-text").val();
			pval = Number(Number(pval) - Number(1));
			if (pval <= 1) {
				pval = 1;
			}
			$(".p-text").val(pval);
			_this.modifyPrice(pval);
		}
	});
	// 点击加号
	$(".btn-add").click(function() {
		var pval = $(".p-text").val();
		var max = $(".maxNum").val();
		if (Number(max) > 0) {
			if (Number(max) > Number(pval)) {
				pval = Number(Number(pval) + Number(1));
			}
		} else {
			pval = Number(Number(pval) + Number(1));
		}
		$(".p-text").val(pval);
		_this.modifyPrice(pval);
	});
	$(".p-text").keyup(function() {
		if (isNaN($(".p-text").val()) || $(".p-text").val() <= 1) {
			$(".p-text").val(1);
		}
		var pval = $(".p-text").val();
		pval = Number(pval);
		_this.modifyPrice(pval);
	});
	$(".centre-error-close").click(function() {
		_this.closeggts();
	});
};
CartCenter.prototype.closeggts = function() {
	$(".centre-details-r").removeClass("error");
	$(".centre-error-msg").hide();
};
CartCenter.prototype.modifyPrice = function(pval) {
	var orignPrice = $(".total-product-price").attr("data");
	var orignPriceAvg = $(".average-price").attr("data");
	var dikouPrice = this.subPrice * pval;
	if (dikouPrice > 10)
		dikouPrice = 10;
	var remainPrice = (parseFloat(orignPriceAvg) - parseFloat(orignPrice)) * pval;
	if (remainPrice <= 0) {
		remainPrice = dikouPrice;
		if (orignPrice < 5) {
			remainPrice = 0;
			dikouPrice = 0;
		}
		$(".average-price").text(new Number(orignPrice).toFixed(2) + "元");
	} else {
		remainPrice = remainPrice + dikouPrice;
		$(".average-price").text(orignPriceAvg + "元");
	}
	$(".remain-price").text(new Number(remainPrice).toFixed(2) + "元");
	var price = parseFloat(orignPrice) * pval - dikouPrice;
	$(".chongzhika").text(new Number(parseFloat(price) / 1000).toFixed(1));
	$(".total-product-price").text(new Number(price).toFixed(2) + "元");
	if (price < 0) {
		$(".total-product-price").text('暂无报价');
	}
	price = price + 0.99;
	var num = parseInt(price);
	$(".bangzhuan").text(num);
};
CartCenter.prototype.showGuige = function() {
	var _this = this;
	var data = {
		"docId" : _this.docId,
		"url" : _this.url
	};
	if (window.location.host.indexOf("tao") == 0) {
		data.c = "tao";
	}
	$.ajax({
		url : _basePath + "ontimesku/single.htm",
		data : data,
		type : "POST",
		success : function(result) {
			_this.displayGuige(result.val);
		},
		error : function(result) {
			_this.displayGuige(result.val);
		}
	});
};
CartCenter.prototype.displayGuige = function(val) {
	$(".centrt-sku").show();
	$("#skuLoad").hide();
	this.clickBtn();
	if (!val.skuProps || val.skuProps.length < 1) {
		return;
	}
	var $count = $(".centre-details-quantity").prev();
	var html = '';
	for ( var index = 0; index < val.skuProps.length; index++) {
		var skuProp = val.skuProps[index];
		html = html + '<dt>' + skuProp.name + ':</dt><dd class="gg_prop">';
		for ( var i = 0; i < skuProp.props.length; i++) {
			html = html + '<a href=\"javascript:void(0)\" class="guige">' + skuProp.props[i] + '</a>';
		}
		html = html + "</dd>";
	}
	$count.before(html);
	this.guigeAddEvent(val);
};

CartCenter.prototype.shopInfo = function() {
	var _this = this;
	var data = {
		"url" : _this.url
	};
	var reqUrl = _basePath + "daigoucart/shopDetail.htm";
	if (this.ref == '06') {
		$.getJSON("http://hd.b5m.com/mbaobao/detail.php?docId=" + _this.docId + "&jsonpCallback=?", function(json) {
			$("#detailLoad").hide();
			$('#shopInfo').html(json.data);
		});
	} else {
		$.ajax({
			url : reqUrl,
			data : data,
			type : "POST",
			success : function(result) {
				if (result != "") {
					$("#detailLoad").hide();
					$('#shopInfo').html(result);
				} else {
					$('#detailLoad').html("暂无商品详情页");
				}
			},
			error : function(result) {
				$('#detailLoad').html("暂无商品详情页");
			}
		});
	}

};

CartCenter.prototype.guigeAddEvent = function(val) {
	var _this = this;
	var $props = $(".gg_prop");
	$props.find("a").click(function() {
		var $this = $(this);
		if ($this.attr("disable"))
			return;

		$this.siblings("a").each(function() {
			$(this).removeClass("checked");
		});

		if ($this.hasClass("checked")) {
			$this.removeClass("checked");
		} else {
			$this.addClass("checked");
		}
		var num = 0;
		for ( var index = 0; index < $props.length; index++) {
			if ($($props[index]).find("a.checked").length > 0) {
				num++;
			}
		}
		$props.find("a").each(function() {
			$(this).css("color", "black").removeAttr("disable");
		});
		if (num == $props.length - 1) {
			_this.changeDisplayGuige(val, $props, false);
			return;
		}
		if (num == $props.length) {
			_this.changeDisplayGuige(val, $props, true);
			_this.closeggts();
		}
		$props.find("a").each(function() {
			if ($(this).attr("disable")) {
				$(this).removeClass("checked");
			}
		});
		var goodsSpec = _this.getGoodsSpec();
		var price = val.sku[goodsSpec];
		if (price) {
			/*
			 * if(new Number(price) > 5){ price = new Number(price) -
			 * _this.subPrice; }
			 */
			$(".total-product-price").attr("data", price);
			$(".product-price").text(price);
			var pval = $(".p-text").val();
			pval = Number(pval);
			_this.modifyPrice(pval);
		}
	});
};

CartCenter.prototype.changeDisplayGuige = function(val, $props, islastChange) {
	var _this = this;
	var goodsSpec = "";
	var tempGoodsSpecs = null;
	var $noCheckProps = null;
	for ( var index = 0; index < $props.length; index++) {
		var $prop = $($props[index]);
		var $checked = $prop.find("a.checked");
		if ((islastChange && index < $props.length - 1) || (!islastChange && $checked.length > 0)) {
			if (!tempGoodsSpecs) {
				goodsSpec = goodsSpec + _this.oneSpec($checked);
			} else {
				for ( var i = 0; i < tempGoodsSpecs.length; i++) {
					tempGoodsSpecs[i] = tempGoodsSpecs[i] + _this.oneSpec($checked);
				}
			}
		} else {
			$nosel = $prop;
			$noCheckProps = $prop.find("a");
			tempGoodsSpecs = new Array($noCheckProps.length);
			var _num = 0;
			$noCheckProps.each(function() {
				tempGoodsSpecs[_num] = goodsSpec + _this.oneSpec($(this));
				_num++;
			});
		}
	}
	for ( var index = 0; index < tempGoodsSpecs.length; index++) {
		tempGoodsSpecs[index] = _this.removeLastSemicolon(tempGoodsSpecs[index]);
		if (!val.sku[tempGoodsSpecs[index]]) {
			$($noCheckProps[index]).css("color", "#ccc").attr("disable", true);
		}
	}
};

CartCenter.prototype.oneSpec = function($a) {
	var propName = $a.parent().prev().text();
	var propValue = $a.text();
	return propName + propValue + ";";
};

CartCenter.prototype.haveSelGuige = function() {
	var price = $(".total-product-price").val();
	if (isNaN(price))
		return false;
	var $props = $(".gg_prop");
	for ( var index = 0; index < $props.length; index++) {
		if ($($props[index]).find("a.checked").length <= 0) {
			$(".centre-details-r").addClass("error");
			$(".centre-error-msg").show();
			return false;
		}
	}
	return true;
};

CartCenter.prototype.clickBtn = function() {
	var _this = this;
	$(".btn-orange").click(function() {
		if (!_this.haveSelGuige())
			return;
		_this.addCart(function(json) {
			if (json.code != 1) {
				alert(json.msg);
				return;
			}
			try {
				b5m.ui.setProductNum();
			} catch (e) {
			}
			$(".add-cart-success").show(500);
		});
	});
	$(".add-cart-success").on('click', '.cart-close', function() {
		$(".add-cart-success").hide(500);
	});
	$(".add-cart-success").on('click', '.go-shoping', function() {
		$(".add-cart-success").hide(500);
	});

	$(".btn-red-two").click(function() {
		if (!_this.haveSelGuige())
			return false;
		_this.addCart(function(json) {
			if (json.code != 1) {
				alert(json.msg);
			}else{
				location.href = json.val;
			}
			return false;
		}, 1);
		try {
			b5m.ui.setProductNum();
		} catch (e) {
		}
		return true;
	});
};

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

CartCenter.prototype.addCart = function(call, direct_buy) {
	var _this = this;
	var _docId = _this.docId;
	var _priceAvg = _this.priceAvg;
	var _goodsSpec = _this.getGoodsSpec();
	var pval = $("#quantity005").val();
	var localUrl = window.location.href;
	if (localUrl.indexOf("mps=") > 0) {
		var mps = localUrl.split("mps=")[1];
		if (mps.indexOf("\&") > 0) {
			mps = mps.split("\&")[0];
		}
		_this.origin = mps;
	} else {
		_this.origin = getCookie("_b5mtraffic");
	}
	$.ajax({
		type : "post",
		url : _basePath + "daigoucart/add.htm",
		async : false,
		data : {
			docId : _docId,
			priceAvg : _priceAvg,
			goodsSpec : _goodsSpec,
			count : pval,
			ref : _this.ref,
			origin : _this.origin,
			url : _this.paramurl,
			direct_buy : direct_buy,
			key : _this.key,
			b5tPrice: _this.b5tPrice,
			title: _this.title,
			imgPath: _this.imgPath
		},success : function(json) {
			call(json);
		}
	});
};
CartCenter.prototype.getGoodsSpec = function() {
	var goosSpec = "";
	$(".gg_prop .checked").each(function() {
		var propName = $(this).parent().prev().text();
		var propValue = $(this).text();
		goosSpec = goosSpec + propName + propValue + ";";
	});
	return this.removeLastSemicolon(goosSpec);
};
CartCenter.prototype.removeLastSemicolon = function(goosSpec) {
	if (goosSpec.length > 0 && goosSpec.charAt(goosSpec.length - 1) == ';') {
		goosSpec = goosSpec.substring(0, goosSpec.length - 1);
	}
	return goosSpec;
};
function jumpTo(url) {
	var el = document.createElement("a");
	document.body.appendChild(el);
	el.href = url;
	el.target = "_blank";
	if (el.click) {
		el.click();
	} else {// safari浏览器click事件处理
		try {
			var evt = document.createEvent('Event');
			evt.initEvent('click', true, true);
			el.dispatchEvent(evt);
		} catch (e) {// alert(e)
			window.localhost.href = url;
		}
		;
	}
}