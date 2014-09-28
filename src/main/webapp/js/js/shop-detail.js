var is_init_comment = false;
var is_init_mini = false;
var val = $("#quantity005").val();
$(function() {
	// 按钮事件初始化
	initBtn();

	// SKU加载
	ontimerSKU();

	// 初始化分页的点击事件
	initSplitPageClick();

	// 按条件选择评论
	initSelectCommentTag();

	// banx
	showBanx($("#goods-title").text(), $("#cookieId").val(), $("#adRecordUrl").val(), 12);

	// 初始化商城
	getAllShopInfo("ASC");

	// 相关商品
	getRecommandProduces();

	// 分享按钮
	initShare();
});

function showBanx(keyword, cookieId, recordUrl, adSize) {
	var res_docid = $('#docId').val();
	if ($(".recommend-prod").length != 0)
		return;
	$.ajax({
		type : "get",
		url : "http://click.simba.taobao.b5m.com/s/data/" + adSize + "_0_V.html",
		data : {
			keywords : keyword,
			cid : cookieId,
			isDetail : true
		},
		dataType : "jsonp",
		jsonp : 'jsoncallback',
		success : function(json) {
			var ads = json.val;
			$("#shop-recommend").append('<div class="recommend-prod"><h3>正品推荐</h3> <ul class="recommend-prod-list" id="J_prod_slider">  </ul> <div class="trigger-button" id="J_trigger"> <span class="arrow-prod arrow-up arrow-up-disable">上</span><span class="arrow-prod arrow-down">下</span> </div></div>');
			if (ads.length != 0) {
				for ( var i = 0; i < ads.length; i++) {
					var docId = ads[i].DOCID;
					if (j != 1 && docId == res_docid) {
						j = 1;
						continue;
					}
					var jumpUlr = ads[i].Url;
					var picUrl = ads[i].Picture;
					var title = ads[i].Title;
					picUrl = picUrl.replace('img.b5m.com', 'tfs01.b5mcdn.com');
					if (picUrl.indexOf("tfs01") >= 0)
						picUrl = picUrl + "/120x120";
					var data_url = picUrl;
					var orgPic = "";
					if (ads[i].OriginalPicture) {
						orgPic = ads[i].OriginalPicture.split(",")[0];
					}
					if (i >= adSize / 2)
						picUrl = "";
					var adrshtml = "<img alt='' src='" + recordUrl + "?cid=" + cookieId + "&aid=" + ads[i].aid + "&da=V" + (i + 1) + "&ad=108&dl=" + encodeURIComponent(window.location.href) + "&dstl=" + ads[i].durl + "&lt=8800&t=" + new Date().getTime() + "&rp=1002&dd=" + ads[i].DOCID + "' style='display: none;'>";
					var html = '<li><a href="' + jumpUlr + '" target="_blank"><i class="pic-center"><img src="' + picUrl + '" data-src="' + data_url + '" onerror="this.src=\'' + orgPic + '\'" alt=""></i><span>' + title + '</span></a>' + adrshtml + '</li>';
					$("#J_prod_slider").append(html);
				}
			}
			var j = 0;
			if (ads.length - j < adSize / 2) {
				var url = _basePath + "goodsDetail/recommandProduces.htm?t=" + new Date().getTime();
				var data = {
					docId : $("#docId").val(),
					title : $('#productTitle').val()
				};
				$.ajax({
					type : "POST",
					url : url,
					data : data,
					dataType : "jsonp",
					jsonp : 'jsonCallback',
					success : function(httpObj) {
						var diff = adSize / 2 - (ads.length - j);
						if (httpObj != "") {
							$.each(httpObj, function(i, item) {
								if (i < diff) {
									var shop = item.shopList[0];
									var jumpUlr = shop.Url;
									var picUrl = shop.Picture;
									var title = shop.Title;
									picUrl = picUrl.replace('img.b5m.com', 'tfs01.b5mcdn.com');
									var orgPic = "";
									if (shop.OriginalPicture) {
										orgPic = shop.OriginalPicture.split(",")[0];
									}
									if (picUrl.indexOf("tfs01") >= 0)
										picUrl = picUrl + "/120x120";
									var adrshtml = "<img alt='' src='" + recordUrl + "?cid=" + cookieId + "&aid=" + shop.aid + "&da=V" + (ads.length - j + i + 1) + "&ad=108&dl=" + encodeURIComponent(window.location.href) + "&dstl=" + shop.durl + "&lt=8800&t=" + new Date().getTime() + "&rp=1002&dd=" + shop.DOCID + "' style='display: none;'>";
									var html = '<li><a href="' + jumpUlr + '" target="_blank"><i class="pic-center"><img src="' + picUrl + '" onerror="this.src=\'' + orgPic + '\'" alt=""></i><span>' + title + '</span></a>' + adrshtml + '</li>';
									$("#J_prod_slider").append(html);
								}
							});
						}
					}
				});
			}
			if (ads.length <= 4) {
				$("#J_trigger").hide();
			}
		}
	});
}

function initShare() {
	seajs.use('modules/common/share/1.0.0/share.js', function(Shared) {
		var pic = [];
		$(".mini-slider img").each(function() {
			pic.push($(this).attr("src"));
		});
		new Shared({
			id : "#b5m-share",
			title : "帮5买全网兑换",
			content : $("#goods-title").text(),
			pic : pic
		});
	});
}

function randomById(min, max, sort) {
	var docid = $("#docId").val().split("");
	var s = [];
	for ( var i = 0; i < docid.length; i++) {
		var c = docid[i];
		if (!isNaN(c)) {
			s.push(c);
		}
	}
	if (sort == 2) {
		s = s.reverse();
	}
	var num = s.join("");
	if (num.length > 8)
		num = num.substr(0, 8);
	num = num.replace(/(0)*/, "");
	var rs = new Number(num) % max;
	return rs >= min ? rs : min;
}

function initBtn() {
	if (parseInt($(".prod-sell-all strong").text()) == 0) {
		$(".prod-sell-all strong").text(randomById(10, 500, 1));
	}
	$(".btn-b5m-buy, .btn-shop-cart, .bwm-btn").click(function() {
		if ($(this).hasClass("bwm-btn")) {
			document.documentElement.scrollTop = 0;
			document.body.scrollTop = 0;
		}
		if (!validateSKU()) {
			showWarn();
			return false;
		}
		if ($(this).hasClass("btn-b5m-buy")) {
			addCart(function(json) {
				if (json.code == 1) {
					window.location.href = json.val;
				}
			}, 1);
		} else if ($(this).hasClass("bwm-btn")) {
			addCart(function() {
				window.location.href = "http://cart.b5m.com";
			});
		} else {
			addCart();
		}
		if ($(this).hasClass("btn-shop-cart")) {
			cartToggle(true);
		}
		closeWarn();
	});
	$(".add-cart-success a, .cart-close").click(function() {
		cartToggle(false);
	});
	$(".btn-subtraction").click(function() {
		if (val > 1) {
			--val;
			calculateBeans();
		}
	});
	$(".btn-add").click(function() {
		++val;
		calculateBeans();
	});
}

function showWarn() {
	$(".prod-sku-info").addClass("centrt-sku-error");
	$(".info-tips").show();
}

function closeWarn() {
	$(".prod-sku-info").removeClass("centrt-sku-error");
	$(".info-tips").hide();
}

function validateSKU() {
	var $props = $("dd.gg_prop");
	var num = 0;
	for ( var index = 0; index < $props.length; index++) {
		if ($($props[index]).find("a.checked").length > 0) {
			num++;
		}
	}
	if (num < $props.length) {
		return false;
	}
	return true;
}

// 取得全部商家的信息
function getAllShopInfo(sortType) {
	$('.ah-shop').hide();
	$('.personal').hide();
	$("#ah-shop").hide();
	$('.shopview').addClass('ajax_loading');
	var docId = $("#uuid").val();
	var url = _basePath + "goodsDetail/shopInfo.htm?t=" + new Date().getTime();
	var params = "docId=" + docId + "&sortType=" + sortType;
	var head = '<tr class="th"> <td class="logo">商城</td> <td class="name">商品名称</td> <td class="price">价格 <a href="javascript:;" class="sort-mark">&nbsp;</a></td> <td class="bz">服务保障</td> <td class="do">操作</td> </tr>';
	$.ajax({
		type : "GET",
		url : url,
		data : params,
		dataType : "jsonp",
		jsonp : 'jsonCallback',
		success : function(httpObj) {
			var sources = "";
			if (httpObj && httpObj.shopInfoList) {
				$('#all-shop-view').html(head);
				var html = "";
				var bool = false;
				$.each(httpObj.shopInfoList, function(i, item) {
					if (item.sourceName != '淘宝网') {
						$('.ah-shop').show();
						var clazz = "";
						var first = "";
						var b = item.shopListSize > 1;
						$.each(item.shopList, function(index, shop) {
							bool = true;
							var expand = '';
							if (index > 0) {
								clazz = "more";
								if (index == item.shopListSize - 1) {
									clazz += " last";
								}
							} else {
								sources += item.shopName + "," + shop.DOCID + ";";
								first = "expand-ctn";
								if (b) {
									expand = '<a class="expand" href="javascript:;" source="' + item.shopName + '"> <span class="arrow">∨</span> <span class="tip">展开</span>其余' + (item.shopListSize - 1) + '件商品 </a>';
								}
							}
							var title = limitStr(shop.Title, 50, "...");
							html += '<tr class="' + clazz + '" docid="' + shop.DOCID + '">';
							if (index == 0) {
								html += '<td> ';
								if (item.url) {
									html += '<a target="_blank" href=' + item.url + '><img class="shop-brand" src="' + item.logoImgUrl + '" alt="' + item.shopName + '"></a>';
								} else {
									html += '<span>' + item.shopName + '</span>';
								}
								html += '</td>';
							} else {
								html += '<td></td> ';
							}
							html += buildShopHtml(item, shop, title, expand, first);
							html += '</tr>';
						});
						$('.ah-shop').each(function() {
							$(this).show();
						});
					} else {
						if (item.shopList.length > 1 || $('#docId').val() != item.shopList[0].DOCID) {
							$('#taobao-shop-view').html(head);
							var taoHtml = '';
							$.each(item.shopList, function(index, shop) {
								if ($('#docId').val() != shop.DOCID) {
									bool = true;
									var title = limitStr(shop.Title, 50, "...");
									taoHtml += '<tr docid="' + shop.DOCID + '">';
									taoHtml += '<td><a target="_blank" href=' + item.url + '><img  src="' + item.logoImgUrl + '" class="p-shop-logo shop-brand" alt="' + item.shopName + '"></a> <br></td>';
									taoHtml += buildShopHtml(item, shop, title, '', '');
									taoHtml += '</tr>';
								}
							});
							$('#taobao-shop-view').append(taoHtml);
							$('.personal').show();
						}
					}
				});
				if (bool) {
					$("#ah-shop").show();
				} else {
					$(".ah-shop").remove();
					$("#ah-shop").remove();
					var ss = $('.tab').get(0).anchors;
					$.each(ss, function(i, item) {
						if ($(item).attr("name") == "ah-shop") {
							ss.splice(i, 1);
							return false;
						}
					});
				}
				$('#shopview').removeClass('ajax_loading');
				$('#all-shop-view').append(html);
			}
			initSource(sources);
			initExpandSource("ASC");
			$(".tab-ctn ul li a:visible:eq(0)").addClass("cur");
		},
		error : function(xmlHttpRequest, errorMessage, errorThrow) {
			$('#all-shop-view').removeClass('ajax_loading');
		}
	});
}

function showExpand($this) {
	var $btn = $this;
	$btn.toggleClass('open');
	var open = $btn.hasClass('open');
	$btn.find('.arrow').html(open ? '∧' : '∨').end().find('.tip').html(open ? '收起' : '展开');
	var $tmp = $btn.closest('tr').next();
	var method = open ? 'show' : 'hide';
	while ($tmp.hasClass('more')) {
		$tmp[method]();
		$tmp = $tmp.next();
	}
}

function initExpandSource(sortType) {
	$(document).delegate(".expand-ctn .expand", {
		click : function(e) {
			$this = $(this);
			var source = $this.attr("source");
			if ($this.attr("flag") != "1") {
				var uuid = $("#uuid").val();
				var url = _basePath + "goodsDetail/shopInfo/singleSource.htm?t=" + new Date().getTime();
				var params = {
					docId : uuid,
					sortType : sortType,
					source : source
				};
				$.ajax({
					type : "POST",
					url : url,
					data : params,
					dataType : "jsonp",
					jsonp : 'jsonCallback',
					success : function(shopList) {
						var item = {
							shopTag : ""
						};
						$this.parent().siblings(".bz").find("li").each(function() {
							item.shopTag += $(this).text() + ",";
						});
						var docid = $this.parents("tr").attr("docid");
						var html = "";
						$.each(shopList, function(index, shop) {
							if (docid != shop.DOCID) {
								var clazz = "";
								clazz = "more";
								if (index == shopList.length - 1) {
									clazz += " last";
								}
								var title = limitStr(shop.Title, 50, "...");
								html += '<tr class="' + clazz + '" docid="' + shop.DOCID + '">';
								html += '<td></td> ';
								html += buildShopHtml(item, shop, title, "", "");
								html += '</tr>';
							}
						});
						$this.parents("tr").after(html);
						showExpand($this);
					}
				});
				$this.attr("flag", "1");
				return;
			}
			showExpand($this);
		}
	});
}

function buildShopHtml(item, shop, title, expand, first) {
	var errorimg = "";
	if (shop.OriginalPicture) {
		errorimg = shop.OriginalPicture.split(",")[0];
	}
	var html = "";
	html += '<td class="sp-desc ' + first + '"><a target="_blank" href="' + _orgBasePath + 'item/' + shop.DocId + '.html' + '"><img onerror="this.src=\'' + errorimg + '\'" src="' + shop.Picture + '" class="sp-img-t" alt="' + title + '"></a><div class="i-sp-desc"><a target="_blank" href="' + _orgBasePath + 'item/' + shop.DocId + '.html' + '">' + title + '</a></div> ' + expand + '</td>';
	html += '<td class="price-td"><span class="price">' + (shop.price || shop.Price) + '</span></td>';
	html += '<td class="bz"> <ul>' + buildTag(item.shopTag) + '</ul> </td>';
	html += '<td><a href="' + _orgBasePath + 'item/' + shop.DocId + '.html" class="buy" target="_blank" cps="true" title="' + title + '">帮我买</a></td>';
	return html;
}

function buildTag(text) {
	var html = "";
	if (text) {
		var tag = text.split(',');
		$.each(tag, function(i, item) {
			if (i >= 3)
				return false;
			if (item != "" && item != undefined)
				html += "<li>" + item + "</li>";
		});
	} else {
		html += '&nbsp;';
	}
	return html;
}

function initSource(source) {
	if (source != '') {
		var ss = source.split(";");
		var div = $("#sel-ctn");
		div.append('来源商城： <select id="from-shop-city" name="from-shop-city"></select>');
		var select = $("#from-shop-city");
		for ( var i = 0; i < ss.length; i++) {
			if (i + 1 == ss.length)
				break;
			var s = ss[i].split(",");
			select.append($("<option>").val(s[1]).text(s[0]));
		}
		select.change(function() {
			$('#sel-ctn a').removeClass('selected').siblings('#all').addClass('selected');
			getComments('ALL', 1, true);

		});
		select.get(0).selectedIndex = -1;
	}
	getComments('ALL', 1, false);
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

var t;
function initHover() {
	$contain = $('#mini-priceTrend-view');
	$(".price-trend").click(function() {
		clearTimeout(t);
		if (is_init_mini == false) {
			showPriceTrend($("#docId").val(), $contain, "200", "450");
			is_init_mini = true;
		}
		$contain.toggle();
	});
}

function showPriceTrend(docid, $contain, height, width) {
	var source = $("#source").val();
	var price = $("#price").val();
	var options = {
		price : price,
		site : source,
		height : height || "400",
		width : width || "950",
		titleAlign : "left",
		crosshairsColor : [ "#ff1919", "#ff1919" ],
		legendEnabled : false,
		handler : function(result) {
			$contain.addClass("ajax_loading");
			$("#topPriceHistroyDiv").addClass("ajax_loading");
			if (result.val) {
				$("#price-trend-" + result.val.averiageType).show();
				$contain.removeClass("ajax_loading");
				return result.val.averiage;
			}
		}
	};

	var url = _basePath + "pricehistory/goodsDetail.htm?fill=true&source=" + encodeURIComponent(source) + "&price=" + price;
	$contain.b5mtrend(docid, url, options);
}

function ontimerPrice() {
	$.ajax({
		type : "post",
		url : _basePath + "ontimeprice/single.htm",
		timeout : 3000,
		dataType : "jsonp",
		jsonp : 'jsonCallback',
		data : {
			docId : $("#docId").val(),
			url : $("#originUrl").val()
		},
		success : function(json) {
			if (json.code == 0 && json.val.price > 0) {
				var price = json.val.price;
				changePrice(price);
				if ($("#high-price").text() < price) {
					$("#high-price").text(new Number(price).toFixed(2));
					$("#highPrice").val(new Number(price).toFixed(2));
				}
			}
		}
	});
}

function changePrice(price) {
	$("#price").val(new Number(price).toFixed(2));
	calculateBeans();
}

function setSKUPrice(val) {
	if (val.sku) {
		var sku = val.sku;
		var lowPrice = 0;
		for ( var key in sku) {
			if (lowPrice == 0) {
				lowPrice = sku[key];
			} else {
				if (lowPrice > sku[key]) {
					lowPrice = sku[key];
				}
			}
		}
		if (lowPrice != 0) {
			changePrice(lowPrice);
		}
	} else {
		if (val.lowestPrice) {
			var price = val.lowestPrice;
			changePrice(price);
		}
	}
	if (val.highestPrice) {
		if ($("#high-price").text() < val.highestPrice) {
			$("#high-price").text(new Number(val.highestPrice).toFixed(2));
			$("#highPrice").val(new Number(val.highestPrice).toFixed(2));
		}
	}
}

function buildSKU(val) {
	if (!val.skuProps || val.skuProps.length < 1)
		return;
	for ( var index = 0; index < val.skuProps.length; index++) {
		var skuProp = val.skuProps[index];
		var html = '<dt>' + skuProp.name + ':</dt><dd class="gg_prop">';
		for ( var i = 0; i < skuProp.props.length; i++) {
			html += '<a href="javascript:void(0)" class="guige">' + skuProp.props[i] + '</a> ';
		}
		html += '</dd>';
		$("#buy-count-name").before($(html));
	}
}

function ontimerSKU() {
	$.ajax({
		type : "post",
		url : _basePath + "ontimesku/single.htm",
		timeout : 5000,
		dataType : "jsonp",
		jsonp : 'jsonCallback',
		data : {
			url : $("#originUrl").val(),
			docId : $("#docId").val(),
			col : $("#col").val()
		},
		success : function(json) {
			var val = json.val;
			if (val.sku && val.skuProps) {
				buildSKU(val);
				setSKUPrice(val);
			} else {
				ontimerPrice();
				return;
			}
			$(".gg_prop a").click(function() {
				if ($(this).attr("disable") != "true") {
					if (!$(this).hasClass("checked")) {
						$(this).addClass("checked").siblings().removeClass("checked");
					} else {
						$(this).removeClass("checked");
					}
					if (validateSKU()) {
						closeWarn();
					}
					var goodsSpec = getGoodsSpec();
					var price = val.sku[goodsSpec];
					if (price) {
						changePrice(price);
					}
					hideSKU(val, goodsSpec, $(this));
				}
			});
		},
		complete : function(XMLHttpRequest, status) {
			$("#load-div").remove();
			$(".ly-content").show();
			// 价格趋势
			try {
				showPriceTrend($('#docId').val(), $("#topPriceHistroyDiv"));
				initHover();
			} catch (e) {
			}
		}
	});
}

function hideSKU(val, goodsSpec, $this) {
	if (goodsSpec == "") {
		$(".gg_prop a").css("color", "black").removeAttr("disable");
		return;
	}
	var k = goodsSpec.split(";");
	var length = k.length;
	var sku = val.sku;
	var nameList = [];
	$(".centrt-sku dt[id!='buy-count-name']").each(function() {
		nameList.push($(this).text());
	});
	var selectobjs = $(".centrt-sku .checked");
	$(".guige ").not(selectobjs).not($this).each(function() {
		var text = $(this).text();
		var name = $(this).parent().prev().text();
		var temp = [];
		var regStr = "";
		var $siblingsObj = $(this).siblings(".checked");
		var str = name + text;
		var objName = name + $siblingsObj.text();
		if ($siblingsObj.length) {
			for ( var i = 0; i < length; i++) {
				(objName != k[i]) && temp.push(k[i]);
			}
		} else {
			temp = k.concat();
		}
		temp.push(str);
		temp = sortList(nameList, temp);
		regStr = temp.join(";");
		var reg = new RegExp(regStr);
		var b = false;
		for ( var key in sku) {
			if (reg.test(key)) {
				b = true;
				break;
			}
		}
		if (!b) {
			$(this).css("color", "#ccc").attr("disable", true);
		} else {
			$(this).css("color", "black").removeAttr("disable");
		}
	});
}

function sortList(namelist, list) {
	var p = [];
	var s = "";
	for ( var i = 0; i < namelist.length; i++) {
		var b = false;
		var key = namelist[i];
		for ( var j = 0; j < list.length; j++) {
			if (list[j].indexOf(key) > -1) {
				b = true;
				s = list[j];
				break;
			}
		}
		if (b) {
			p.push(s.replace(/([\(\)\.\?\+\{\}\[\]\-\=\^\$\*])/g, "\\$1"));
		} else {
			p.push("[^;]*;*");
		}
	}
	return p;
}

function calculateBeans() {
	var p = $("#price").val();
	if (p > 5) {
		p = p - 0.5;
	}
	$("#productPrice").text(new Number(p * val).toFixed(2));
	var high = $("#highPrice").val();
	var spread = high - p;
	if (spread < 0) {
		$(".cut-price-num").css("visibility", "hidden");
	} else {
		$("#spread").text(new Number((high - p) * val).toFixed(2));
		$(".cut-price-num").css("visibility", "visible");
	}
	$(".getbeans").text(new Number($("#price").val() * val).toFixed(0));
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

function addCart(call, direct_buy) {
	var localUrl = window.location.href;
	var origin = "";
	if (localUrl.indexOf("mps=") > 0) {
		var mpsStr = localUrl.split("mps=")[1];
		if (mpsStr.indexOf("\&") > 0) {
			mpsStr = mpsStr.split("\&")[0];
		}
		origin = mpsStr;
	} else {
		origin = getCookie("_b5mtraffic");
	}
	$.ajax({
		type : "post",
		url : _basePath + "daigoucart/add.htm",
		dataType : "jsonp",
		jsonp : 'jsonCallback',
		data : {
			docId : $("#docId").val(),
			priceAvg : $("highPrice").val(),
			goodsSpec : getGoodsSpec(),
			count : $("#quantity005").val(),
			col : $("#col").val(),
			direct_buy : direct_buy,
			origin : origin
		},
		success : function(json) {
			if (json.code == 1) {
				if (typeof call === 'function') {
					call(json);
				}
				b5m.ui.setProductNum();
			} else {
				alert(json.msg);
			}
		}
	});
};

function getGoodsSpec() {
	var goosSpec = "";
	$(".centrt-sku .checked").each(function() {
		var propName = $(this).parent().prev().text().replace(":", "");
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

function cartToggle(b) {
	if (b) {
		$(".add-cart-success").show(500);
	} else {
		$(".add-cart-success").hide(500);
	}
}

function getRecommandProduces() {
	var url = _basePath + "goodsDetail/recommandProduces.htm?t=" + new Date().getTime();
	var data = {
		docId : $("#docId").val(),
		title : $('#productTitle').val()
	};
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		dataType : "jsonp",
		jsonp : 'jsonCallback',
		success : function(httpObj) {
			$('#recommand-shop-view').removeClass('ajax_loading');
			if (httpObj != null) {
				var html = "";
				var shopNum = 0;
				$.each(httpObj, function(i, item) {
					$.each(item.shopList, function(index, shop) {
						shopNum++;
						var dataRd2 = '';
						if (item.shopName == '天猫' || item.shopName == '淘宝网' || item.shopName == '卓越亚马逊' || item.shopName == '京东商城' || item.shopName == '当当网') {
							shop.Url = decodeURIComponent(shop.Url.substr(shop.Url.indexOf('url=') + 4));
							dataRd2 = 'data-rd=\"1\"';
						}
						shop.Url = _orgBasePath + 'item/' + shop.DocId + '.html';
						dataRd2 = '';
						html += '<li><div class="prod-mod"><a target="_blank" data-attr="1010" class="prod-pic" cps="true" href="' + shop.Url + '"' + dataRd2 + '><img src="' + shop.Picture + '" alt=""></a></div><a target="_blank" class="prod-des" href="' + shop.Url + '"' + dataRd2 + '>' + shop.Title + '</a>';
						if (shop.itemCount > 1) {
							var sourceCount = 1;
							if (shop.Source.indexOf(",") > 0) {
								sourceCount = shop.Source.split(",").length;
							}
							if (shop.price.indexOf("-") > 0) {
								shop.price = shop.price.split("-")[0];
							}
							html += '<div class="prod-price"><div class="nums-goods"><em>' + sourceCount + '</em>家商城</div><strong><b>&yen;</b>' + shop.price + '</strong></div>';
						} else {
							html += '<span class="prod-price">&yen;' + shop.price + '</span>';
						}
						if (shopNum == 4) {
							return false;
						}
					});
					if (shopNum == 4) {
						return false;
					}
				});
				var $recommandShopView = $('#recommand-shop-view');
				$recommandShopView.append(html);
			} else {
				$('#recommand-shop-view').parent().hide();
			}
		},
		error : function(xmlHttpRequest, textStatus, errorThrow) {
			$('#recommand-shop-view').parent().hide();
			$('#recommand-shop-view').removeClass('ajax_loading');
		}
	});
}

function limitStr(str, len, offsetStr) {
	var result = str;
	if (!len) {
		len = 15;
	}
	if (result.length > len) {
		result = result.substring(0, len);
		if (offsetStr) {
			result += offsetStr;
		} else {
			result += "...";
		}
	}
	return result;
}

function getCommentType() {
	return $('#sel-ctn a.selected').attr('attr-type');
}

function initSplitPageClick() {
	$(document).on('click', '#paging-view a', function() {
		if ("go" != $(this).attr("name") && !$(this).hasClass("dis")) {
			getComments(getCommentType(), $(this).attr('data'));
		}
	});
}

function clickGo(totalPage) {
	var val = parseInt($(".page-input input").val());
	if (val) {
		if (val > parseInt(totalPage)) {
			val = totalPage;
		}
		if (val < 0) {
			val = 1;
		}
	} else {
		val = 1;
	}
	getComments(getCommentType(), val);
}

function keydown(event, totalPage) {
	var e = window.event || event;
	var code = parseInt(e.keyCode || e.which);
	var val = parseInt($(".page-input input").val());
	if (val > parseInt(totalPage)) {
		$(".page-input input").val(totalPage);
		return false;
	}
	if (code == 46 || code == 8 || (code >= 48 && code <= 57) || (code >= 96 && code <= 105))
		return true;
	if (code == 13) {
		clickGo(totalPage);
		return false;
	}
	return false;
}

function getLocalTime(ms) {
	var d = new Date(ms);
	return d.getFullYear() + "-" + patchZero((d.getMonth() + 1)) + "-" + patchZero(d.getDate());
}

function patchZero(i) {
	return i >= 10 ? i : "0" + i;
}

function random(min, max) {
	return Math.floor(min + Math.random() * max);
}

function createName() {
	var name = "";
	var selectChar = new Array('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
	var charIndex = Math.floor(Math.random() * 26);
	name += selectChar[charIndex];
	var length = random(6, 10);
	for ( var i = 0; i < length; i++) {
		name += "*";
	}
	var charIndex = Math.floor(Math.random() * 26);
	name += selectChar[charIndex];
	return name;
}

function getPHPComments() {
	var num = randomById(1, 10, 2);
	var url = _basePath + "php/comments.htm";
	var data = {
		docId : $("#docId").val(),
		num : num
	};
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		dataType : "jsonp",
		jsonp : 'jsonCallback',
		success : function(result) {
			var html = "";
			$.each(result.data, function(i, item) {
				html += buildCommentHtml(createName(), item.word, getLocalTime(item.create_time * 1000));
			});
			$('#comment-view').html(html);
			$(".prod-sell-comment strong").text(num);
			$('#comment-tab').text("评论(" + num + ")");
			$("#good").text("好评(" + num + ")");
			is_init_comment = true;
		}
	});
}

function buildCommentHtml(author, content, time) {
	return '<div class="item cf"> <div class="dp"> <a href="javascript:;" class="h-icon"><img src="' + _basePath + 'images/avatar/' + random(1, 20) + '.png" alt="xxx"></a>' + author + ' </div> <div class="dc"> <div class="t">内容：</div> <div class="cmt-ct-ctn"> <div class="cmt-ct">' + content + '</div> <div class="ctime"> <span class="rt">评论时间：' + time + '</span> </div> </div> </div> </div>';
}

function getComments(commentTypeStr, currentPageNo, reset) {
	$('#comment-view').html('');
	$('#paging-view').html('');
	$('#comment-view').addClass('ajax_loading');
	var id = $("#from-shop-city").val() || $("#docId").val();
	var data = {
		docId : id,
		commentTypeStr : commentTypeStr,
		currPageNo : currentPageNo
	};
	var url = _basePath + "goodsDetail/detailsComments.htm?t=" + new Date().getTime();
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		dataType : "jsonp",
		jsonp : 'jsonCallback',
		success : function(result) {
			$('#comment-view').removeClass('ajax_loading');
			if (id == $("#docId").val() && result.totalRecord <= 0 && (commentTypeStr == "GOOD" || commentTypeStr == "ALL" || commentTypeStr == "")) {
				getPHPComments();
				is_init_comment = true;
				return;
			}
			if (!is_init_comment && result.totalRecord > 0) {
				$(".prod-sell-comment strong").text(result.totalRecord);
			}
			if (result.totalRecord >= 0) {
				if (!is_init_comment || reset) {
					$('#comment-tab').text("评论(" + result.totalRecord + ")");
					$("#good").text("好评(" + result.goodNum + ")");
					$("#normal").text("中评(" + result.midNum + ")");
					$("#bad").text("差评(" + result.badNum + ")");
					is_init_comment = true;
				}
			}
			if (result.totalRecord <= 0) {
				$('#comment-view').html("<li><span class='no-comment-txt'>商品暂时没有评论<span></li>");
				return;
			}
			var html = "";
			$.each(result.records, function(i, item) {
				html += buildCommentHtml(item.author == null ? createName() : item.author, item.content, getLocalTime(item.createTime));
			});
			$('#comment-view').html(html);

			var pageHtml = "";
			pageHtml += '<div id="split-page-view" class="clear-fix page"><div>';
			if (result.currentPage <= 1) {
				pageHtml += '<a href="javascript:void(0)" class="prev dis"><</a>';
			} else {
				pageHtml += '<a href="javascript:void(0)" class="prev" data="' + (result.currentPage - 1) + '"><</a>';
			}
			if (result.pageIndex.startindex > 1) {
				pageHtml += '<a href="javascript:void(0)" data="1" class="first">首页</a>';
				pageHtml += '<span>...</span>';
			}
			for ( var pageCode = result.pageIndex.startindex; pageCode <= result.pageIndex.endindex; pageCode++) {
				var cur = "";
				if (pageCode == result.currentPage) {
					cur = "cur";
				}
				pageHtml += '<a class="' + cur + '" href="javascript:void(0);" data="' + pageCode + '">' + pageCode + '</a>';
			}
			if (result.pageIndex.endindex < result.totalPage) {
				pageHtml += '<span class="point">...</span>';
				pageHtml += '<a href="javascript:void(0)" data="' + result.totalPage + '" class="last">尾页</a>';
			}
			if (result.currentPage < result.totalPage) {
				pageHtml += '<a href="javascript:void(0)" class="prev" data="' + (result.currentPage + 1) + '">></a>';
			}
			if (result.currentPage >= result.totalPage) {
				pageHtml += '<a href="javascript:void(0)" class="prev dis">></a>';
			}
			pageHtml += '<span class="all">共' + result.totalPage + '页&nbsp;&nbsp;去第</span>';
			pageHtml += '<span class="page-input">';
			pageHtml += '<input type="text" onkeydown="return keydown(event, ' + result.totalPage + ');" onkeyup="return keydown(event,' + result.totalPage + ');">';
			pageHtml += '<a href="javascript:void(0)" name="go" onclick="clickGo(' + result.totalPage + ');return false;">GO</a>';
			pageHtml += '</span>';
			pageHtml += '<span class="go">页</span>';
			pageHtml += '</div></div>';
			$("#paging-view").html(pageHtml);
		},
		error : function(xmlHttpRequest, textStatus, errorThrow) {
			$('#comment-view').removeClass('ajax_loading');
		}
	});
}
function initSelectCommentTag() {
	$('.sel-ctn').on('click', 'a', function() {
		if ($(this).hasClass('selected')) {
			return;
		}
		$(this).addClass("selected").siblings('a').removeClass('selected');
		getComments($(this).attr("attr-type"), 1);
	});
}

function targetTab(obj) {
	if (!obj)
		return;
	obj.trigger('click');
}

var ShopDetail = {};

ShopDetail.shopHaiwaiAd = function(opt) {
	var container = opt.container;
	var $ul = $('<ul class="grid-view cf"></ul>');
	var docid = $("#docId").val();
	$ul.appendTo(container);
	Ajax.jsonp(opt.url, {}, function(result) {
		$.each(result.val, function(index, ele) {
			if (ele.DOCID != docid) {
				var $li = $('<li class="grid-mod"></li>');
				$li.append('<a class="pic" href="' + ('http://korea.b5m.com/item/' + ele.DOCID) + '.html"> <img src="' + ele.Picture + '" alt="' + ele.Title + '"></a>');
				$li.append('<div class="grid-item"><a class="des" href="' + ele.Url + '">' + ele.Title + '</a> </div> ');
				$li.append('<div class="grid-item gird-price"> <span class="goods-origin r">' + ele.Source.split(",")[0] + '</span> <strong class="price"><b>￥</b>' + ele.Price.split("-")[0] + '</strong> </div>  ');
				$li.append('<div class="grid-item grid-go-buy"> <a href="' + ele.Url + '" class="go-buy l">去看看</a> </div>');
				$ul.append($li);
			}
		});
	});
};
var Ajax = {};
Ajax.jsonp = function(url, data, callback) {
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		dataType : "jsonp",
		jsonp : 'jsonCallback',
		success : function(result) {
			callback(result);
		}
	});
};
