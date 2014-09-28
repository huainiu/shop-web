var source;
var shopurl;
var is_init_comment = false;
var is_init_allPriceTrend = false;
var is_init_displayShopInfo = false;
// var is_init_displayLowShopInfo = false;
$(function() {
	// 商品图片滚动
	scrollPic('#show-pic');

	// 初始化分页的点击事件
	initSplitPageClick();

	// 按条件选择评论
	initSelectCommentTag();

	// 标签事件
	initTabSelect();

	// banx
	showBanx($(".goods-title").text(), $("#cookieId").val(), $("#adRecordUrl").val(), 8);

	// 全部商城
	init();

	// 价格趋势
	getSourcePriceHistory();

	// 商城选择
	initCheck();

	// 更多参数
	initMoreAttr();

});

function init() {
	// 取得全部商家信息
	getAllShopInfo('', 'ASC');
	sortShopInfo();
}

function getAllPriceTrend() {
	var docid = $("#docId").val();
	var options = {
		price : $(".price-info span").text(),
		site : $("#topPriceHistroyDiv").attr("source"),
		height : 400,
		width : 950,
		titleAlign : "left",
		crosshairsColor : [ "#ff1919", "#ff1919" ]
	};
	var url = _basePath + "pricehistory/allSourcePriceHistory.htm";
	var container = $("#priceTrendDiv");
	container.parent().show();
	if (!container.html()) {
		container.b5mtrend(docid, url, options);
	}
	is_init_allPriceTrend = true;
}

function initCheck() {
	$(".checkbox-icon").parent().click(function() {
		var $this = $(this).find(".checkbox-icon");
		var name = $this.attr("name");
		var $tabShopItem = $(".tab-shop-item[name=" + name + "]");
		if ($this.hasClass("checkbox--checked")) {
			$this.removeClass("checkbox--checked");
			$tabShopItem.hide();
		} else {
			$this.addClass("checkbox--checked");
			$tabShopItem.show();
		}
	});
}
function tabInit($this) {
	$this.addClass("cur").siblings('li').removeClass('cur');
	var index = $this.index();
	var id = $this.attr('id');
	$('.tab-box').eq(index).show().siblings().hide();
	if (id == 'tab-price-trend' && is_init_allPriceTrend == false) {
		getAllPriceTrend();
	} else if (id == 'pinglun' && is_init_comment == false) {
		getCommentsInfo();
	} else if (id == 'allshop' && is_init_displayShopInfo == false) {
		displayShopInfo();
	}
}
function initMoreAttr() {
	$('.target-tab').on('click', function() {
		var $target = $('#' + $(this).data('type'));
		targetTab($target);
	});

	$('.all-xq').on('click', function() {
		$(this).hide();
		$(this).siblings('.details-parameters').find('tr').show();
	})
}

// 取得全部商家的信息
function getAllShopInfo(sortField, sortType) {
	$('#all-shop-view').html(shopHead());
	$('#all-shop-view').addClass('ajax_loading');
	var docId = $("#docId").val();
	var canDaigou = $("#canDaigou").val();
	var url = _basePath + "goodsDetail/shopInfo.htm?t=" + new Date().getTime();
	var params = "docId=" + docId + "&sortField=" + sortField + "&sortType=" + sortType + "&isDaigou=" + canDaigou;
	$.ajax({
		type : "GET",
		url : url,
		data : params,
		dataType : "json",
		success : function(httpObj) {
			if (httpObj) {
				if (!(canDaigou && ('true' == canDaigou))) {
					if (httpObj.lowestSource) {
						var lowestSource = httpObj.lowestSource;
						if (lowestSource.Source == '天猫' || lowestSource.Source == '淘宝网' || lowestSource.Source == '卓越亚马逊' || lowestSource.Source == '京东商城' || lowestSource.Source == '当当网') {
							if (lowestSource.Url.indexOf('url=') > 0) {
								lowestSource.Url = decodeURIComponent(lowestSource.Url.substr(lowestSource.Url.indexOf('url=') + 4));
							}
						}
						$('.source-count').text("(" + httpObj.shopInfoList.length + ")");
						$("#low-price-source").text("最低价：" + lowestSource.Source);
						$("#low-price-price").text(lowestSource.price);
						$(".goBuy").attr("href", lowestSource.Url);
						$(".goBuy").attr("href", lowestSource.Url);
						$("#header-lowest-price").text(lowestSource.price);
						$(".low-price").show();
					}
				}
				var html = "";
				$.each(httpObj.shopInfoList, function(i, item) {
					if (item.url) {
						if (item.shopName == '淘宝网') {
							item.url = 'http://www.taobao.com/go/chn/tbk_channel/onsale.php?pid=mm_41856994_4212380_14408153&eventid=101329';
						}
						if (item.shopName == '京东商城') {
							item.url = 'http://www.jd.com';
						}
						html += '<div class="tab-shop-item clear-fix" name="' + getName(item.shopName) + '"><p class="shop-logo"><a href=\"' + item.url + '\" cps="true" target="_blank" class="geo-pic" title=\"' + item.shopName + '\" sourceName="' + item.sourceName + '">';
						html += '<span><img src=\"' + item.logoImgUrl + '\" alt=\"' + item.shopName + '\"></span>';
					} else {
						html += '<div class="tab-shop-item clear-fix" name="' + getName(item.shopName) + '"><p class="shop-logo"><a href=\"' + item.url + '\" cps="true" target="_blank" class="geo-pic" title=\"' + item.shopName + '\" sourceName="' + item.sourceName + '">';
						html += '<span>' + item.shopName + '</span>';
					}
					html += '</a></p><ul class="shop-list">';
					var b = item.shopListSize > 1;
					var clazz = "";
					$.each(item.shopList, function(index, shop) {
						if (index == 0 && b) {
							clazz = "first";
						} else {
							clazz = "";
						}
						var title = shop.Title;
						if (title && title.length >= 50) {
							title = title.substr(0, 50) + "...";
						}
						html += '<li class="clear-fix ' + clazz + '">' + '<div class="shop-info">' + '<img alt="" src="' + shop.Picture + '" class="goods-pic">';
						shopurl = _basePath + "item/" + (shop.DocId || shop.DOCID) + ".html";
						html += '<div class="cell"><a href="' + shopurl + '" target="_blank" title="' + shop.Title + '" data-docid="' + shop.uuid + '" >' + title + '</a></div>';

						html += '</div>' + '<div class="shop-ratio"><input type="hidden" source="' + item.shopName + '" id="price-trend-data-' + (shop.DocId || shop.DOCID) + '" value="" /><i id="price-trend-' + (shop.DocId || shop.DOCID) + '"></i><em>' + (shop.price || shop.Price) + '</em><b>元</b></div>' + '<div class="shop-support">' + buildTag(item.shopTag) + '</div>' + '<div class="shop-goto btn-wrap"><a href="' + shop.Url + '" target="_blank" cps="true" class="go-buy fl tjh" title="' + shop.Title + '" data-docid="' + shop.uuid + '" >去购买</a>';
						if (item.sourceName) {
							html += '<a class="dianping" target="_blank" href="' + _basePath + 'dianping.htm?source=' + encodeURIComponent(item.sourceName) + '" >商家点评</a>';
						}
						html += '</div></li>';
					});
					if (item.shopListSize > 1) {
						html += '</ul><div class="opt open" source="' + item.shopName + '"><a href="javascript:void(0);">展开其余' + (item.shopListSize-1) + '件商品</a></div></div>';
					} else {
						html += '</ul></div>';
					}
				});
				$('#all-shop-view').removeClass('ajax_loading');
				$('#all-shop-view').append(html);
				// 商家展开收起
				initShopInfo(sortField, sortType);
				// 将详细折叠起来
				displayShopInfo();
				showPriceIcon();
				banner2(".shop-ratio", "#photo_price");
				// 推荐商品放在全部商品组装完之后
				getRecommandProduces();
				// 初始化评论商家
				initSource();
			}
		},
		error : function(xmlHttpRequest, errorMessage, errorThrow) {
			$('#all-shop-view').removeClass('ajax_loading');
		}
	});
}
function getName(source) {
	if (source == '天猫') {
		return "tianmao";
	} else if (source == '淘宝网') {
		return 'taobao';
	}
	return 'zhengpin';
}

function openShopListForSource($opt, sortField, sortType, flag) {
	var $shopListContainer = $opt.parent().find(".shop-list");
	var $li = $shopListContainer.find("li");
	if ($li.length > 1) {
		shopInfoOperation($opt, flag);
		return;
	}
	var docId = $("#docId").val();
	var url = _basePath + "goodsDetail/shopInfo/singleSource.htm?t=" + new Date().getTime();
	var params = {
		docId : docId,
		sortField : sortField,
		sortType : sortType,
		source : $opt.attr("source")
	};
	var $opta = $opt.find("a");
	var text = $opta.html();
	$opta.html("展开中...");
	$.ajax({
		type : "POST",
		url : url,
		data : params,
		dataType : "json",
		success : function(httpObj) {
			var html = singleSourceHtml($opt, httpObj);
			$shopListContainer.append(html);
			$opta.html(text);
			shopInfoOperation($opt, flag);
			showPriceIcon();
			banner2(".shop-ratio", "#photo_price");
		},
		error : function(xmlHttpRequest, errorMessage, errorThrow) {
		}
	});
}

function singleSourceHtml($opt, httpObj) {
	var item = {};
	item.shopName = $opt.attr("source");
	item.shopTag = $opt.parent().find(".shop-support").html();
	var html = "";
	$.each(httpObj, function(index, shop) {
		var title = shop.Title;
		if (title && title.length >= 50) {
			title = title.substr(0, 50) + "...";
		}
		if (index > 0) {
			html += '<li class="clear-fix">' + '<div class="shop-info">' + '<img alt="" src="' + shop.Picture + '" class="goods-pic">';
			var _shopurl = _basePath + "item/" + (shop.DocId || shop.DOCID) + ".html";
			html += '<div class="cell"><a href="' + _shopurl + '" target="_blank" cps="true" title="' + shop.Title + '" data-docid="' + shop.uuid + '" >' + title + '</a></div>';
			html += '</div>' + '<div class="shop-ratio"><input type="hidden" source="' + item.shopName + '" id="price-trend-data-' + shop.DocId + '" value="" /><i id="price-trend-' + shop.DocId + '"></i><em>' + shop.price + '</em><b>元</b></div>' + '<div class="shop-support">' + item.shopTag + '</div>' + '<div class="shop-goto btn-wrap"><a href="' + shop.Url + '" target="_blank" cps="true" class="go-buy fl tjh" title="' + shop.Title + '" data-docid="' + shop.uuid + '" >去购买</a>';
			if (item.sourceName) {
				html += '<a class="dianping" target="_blank" href="' + _basePath + 'dianping.htm?source=' + encodeURIComponent(item.sourceName) + '" >商家点评</a>';
			}
			html += '</div></li>';
		}
	});
	return html;
}

function shopHead() {
	return html = '<ul class="tab-shop-header clear-fix"><li class="mall">商城</li><li class="name-goods">商品名称</li><li class="price-goods">价格</li><li class="support-goods">服务保障</li><li class="operate-goods">操作</li></ul>';
}

function showPriceIcon() {
	var priceTrendType = $("#price-trend-type").val();
	if (priceTrendType) {
		var typesInfo = priceTrendType.split(";");
		for ( var i = 0; i < typesInfo.length; i++) {
			var docid = typesInfo[i].split(",")[0];
			var type = typesInfo[i].split(",")[1];
			var $div = $("#price-trend-" + docid);
			if (type == "-1") {// flat-icon
				$div.addClass("low-icon");
			} else if (type == "0") {
				$div.addClass("flat-icon");
			} else if (type == "1") {
				$div.addClass("high-icon");
			} else {
				$div.addClass("flat-icon");
			}
			$div.show();
			banner2("#price-trend-" + docid, "#photo_price");
		}
	}
}

function initShopInfo(sortField, sortType) {
	$('.opt').each(function() {
		var $this = $(this), flag = true;
		$this.click(function() {
			openShopListForSource($this, sortField, sortType, flag);
			flag = !flag;
		});
	});
}

function shopInfoOperation($opt, flag) {
	$target_box = $opt.parent('.tab-shop-item'), target_box_h = $target_box.css('height', 'auto').height();
	target_ls_h = $target_box.find('li').outerHeight(), txt = $opt.find("a").html();
	if (flag) {
		$target_box.css('height', target_ls_h + 10).stop(true, true).animate({
			'height' : target_box_h
		});
		flag = false;
		txt = txt.replace('展开', '收起');
		$opt.find("a").html(txt);
		$opt.addClass('close');
	} else {
		$target_box.stop(true, true).animate({
			'height' : target_ls_h - 1
		});
		flag = true;
		txt = txt.replace('收起', '展开');
		$opt.find("a").html(txt);
		$opt.removeClass('close');
	}
	return flag;
}

// 计算隐藏的高度
function displayShopInfo() {
	if ($('#all-shop-view').parent().css('display') == 'none')
		return;
	$('#all-shop-view').find('.tab-shop-item').each(function() {
		var $this = $(this), h = $this.find('li:eq(0)').outerHeight();
		$this.height(h - 2);
		is_init_displayShopInfo = true;
	});
}

function initTabSelect() {
	function fixedPos(obj) {
		if (!obj)
			return false;
		var iTarget = $(obj).offset().top;
		$('html,body').animate({
			scrollTop : iTarget
		}, 'fast');
	}
	$('#detail-tabs').on('click', 'li', function() {
		$(this).addClass("cur").siblings('li').removeClass('cur');
		fixedPos("#detail-tabs");
		var index = $(this).index();
		var id = $(this).attr('id');
		$('.tab-box').eq(index).show().siblings().hide();
		if (id == 'tab-price-trend' && is_init_allPriceTrend == false) {
			getAllPriceTrend();
		} else if (id == 'pinglun' && is_init_comment == false) {
			getCommentsInfo();
		} else if (id == 'allshop' && is_init_displayShopInfo == false) {
			displayShopInfo();
		}
	});
	$('#comment-display-view').on('click', 'a', function() {
		$('#detail-tabs').find('li:eq(2)').click();
	});
}

function sortShopInfo() {
	$(".shop-seq-item a").click(function() {
		if ($(this).parent('li').hasClass('cur') && !$(this).parent('li').hasClass('last')) {
			return;
		}
		$(this).parent('li').addClass('cur').siblings().removeClass('cur');
		var operType = $(this).attr('type');
		if (operType == 'all-shop-new') {// 最新
			getAllShopInfo('Date', 'DESC');
		} else if (operType == 'all-shop-hot') {// 最热门
			getAllShopInfo('_ctr', 'DESC');
		} else if (operType == 'all-shop-asc') {// 最便宜
			$(this).attr("type", "all-shop-desc");
			$(this).find("img").attr("src", _basePath + "images/images/arr_up_07_08.gif");
			getAllShopInfo('Price', 'ASC');
		} else if (operType == 'all-shop-desc') {// 最贵
			$(this).attr("type", "all-shop-asc");
			$(this).find("img").attr("src", _basePath + "images/images/arr_down_07_08.gif");
			getAllShopInfo('Price', 'DESC');
		} else {
			getAllShopInfo('', '');
		}
	});
}

function scrollPic(obj) {
	if (!obj)
		return false;
	var oParent = $(obj), oPrevBtn = oParent.find('#prev'), oNextBtn = oParent.find('#next'), oPicNum = oParent.find('#pic-num'), oArea = oParent.find('.show-area'), oAreaList = oParent.find('.show-pic-list'), aLiLen = oAreaList.find('li').length, oLiWidth = parseInt(oAreaList.find('li').outerWidth(true), 10), aEffLiLen = Math.ceil(parseInt(oArea.css('width'), 10) / oLiWidth), iNow = 0, timer = null;
	oPicNum.html('<strong>' + (iNow + 1) + '</strong>' + '/' + aLiLen);
	oAreaList.css({
		width : (oLiWidth * aLiLen) + 'px'
	});
	function startMove() {
		if (aLiLen > aEffLiLen) {
			oAreaList.stop(true).animate({
				left : -iNow * oLiWidth
			});
		}
	}
	function autoPlay() {
		iNow++;
		if (iNow == aLiLen) {
			iNow = 0;
		}
		startMove();
		oPicNum.html('<strong>' + (iNow + 1) + '</strong>' + '/' + aLiLen);
	}
	timer = setInterval(autoPlay, 5000);
	oParent.hover(function() {
		clearInterval(timer);
	}, function() {
		timer = setInterval(autoPlay, 5000);
	});
	oNextBtn.click(function() {
		if (iNow < (aLiLen - aEffLiLen)) {
			iNow++;
			startMove();
			oPicNum.html('<strong>' + (iNow + 1) + '</strong>' + '/' + aLiLen);
		}
	});
	oPrevBtn.click(function() {
		if (iNow > 0) {
			iNow--;
			startMove();
			oPicNum.html('<strong>' + (iNow + 1) + '</strong>' + '/' + aLiLen);
		}
	});
}

function showBanx(keyword, cookieId, recordUrl, adSize) {
	$.ajax({
		type : "get",
		url : "http://click.simba.taobao.b5m.com/s/data/" + adSize + "_0_V.html",
		dataType : "jsonp",
		data : {
			keywords : keyword,
			cid : cookieId,
			isDetail : true
		},
		dataType : "jsonp",
		jsonp : 'jsoncallback',
		success : function(json) {
			var ads = json.val;

			$("#shop-recommend").append('<div class="recommend-prod"> <h3>推荐商品</h3> <ul class="recommend-prod-list" id="J_prod_slider">  </ul> <div class="trigger-button" id="J_trigger"> <span class="arrow-prod arrow-up arrow-up-disable">上</span><span class="arrow-prod arrow-down">下</span> </div> </div>');
			if (ads.length != 0) {
				for ( var i = 0; i < ads.length; i++) {
					var jumpUlr = ads[i].Url;
					var picUrl = ads[i].Picture;
					var title = ads[i].Title;
					picUrl = picUrl.replace('img.b5m.com', 'tfs01.b5mcdn.com');
					if (picUrl.indexOf("tfs01") >= 0)
						picUrl = picUrl + "/120x120";
					var data_url = picUrl;
					if (i >= adSize / 2)
						picUrl = "";
					var adrshtml = "<img alt='' src='" + recordUrl + "?cid=" + cookieId + "&aid=" + ads[i].aid + "&da=V" + (i + 1) + "&ad=108&dl=" + encodeURIComponent(window.location.href) + "&dstl=" + ads[i].durl + "&lt=8800&t=" + new Date().getTime() + "&rp=1002&dd=" + ads[i].DOCID + "' style='display: none;'>";
					var html = '<li><a href="' + jumpUlr + '" target="_blank"><i class="pic-center"><img src="' + picUrl + '" data-src="' + data_url + '" alt=""></i><span>' + title + '</span></a>' + adrshtml + '</li>';
					$("#shop-recommend .recommend-prod-list").append(html);
				}
			}
			if (ads.length < adSize / 2) {
				var url = _basePath + "goodsDetail/recommandProduces.htm?t=" + new Date().getTime() + "&filter=true";
				var data = {
					docId : $("#docId").val(),
					title : $('#productTitle').val().split(" ")[0]
				};
				$.ajax({
					type : "POST",
					url : url,
					data : data,
					success : function(httpObj) {
						var diff = adSize / 2 - ads.length;
						if (httpObj != "") {
							$.each(httpObj, function(i, item) {
								if (i < diff) {
									var shop = item.shopList[0];
									var jumpUlr = shop.Url;
									var picUrl = shop.Picture;
									var title = shop.Title;
									picUrl = picUrl.replace('img.b5m.com', 'tfs01.b5mcdn.com');
									if (picUrl.indexOf("tfs01") >= 0)
										picUrl = picUrl + "/120x120";
									var adrshtml = "<img alt='' src='" + recordUrl + "?cid=" + cookieId + "&aid=" + shop.aid + "&da=V" + (ads.length + i + 1) + "&ad=108&dl=" + encodeURIComponent(window.location.href) + "&dstl=" + shop.durl + "&lt=8800&t=" + new Date().getTime() + "&rp=1002&dd=" + shop.DOCID + "' style='display: none;'>";
									var html = '<li><a href="' + jumpUlr + '" target="_blank"><i class="pic-center"><img src="' + picUrl + '" alt=""></i><span>' + title + '</span></a>' + adrshtml + '</li>';
									$("#shop-recommend .recommend-prod-list").append(html);
								}
							});
						}
					}
				});
			}
			if (ads.length <= adSize / 2) {
				$("#J_trigger").hide();
			}
		},
		error : function() {
		}
	});
}

var adShow = true;
function showPrice(_this) {
	var removenode = $('.adv-flat-pic');
	var trend = $('#price_trend');
	if (adShow) {
		trend.show();
		removenode.hide();
		$(_this).text("查看广告");
	} else {
		trend.hide();
		removenode.show();
		$(_this).text("查看价格走势");
	}
	adShow = !adShow;
}

function getTypeClassInCompare(type) {
	var cls = "status-flat";
	if (type > 0)
		cls = "status-up";
	if (type < 0)
		cls = "status-down";
	return cls;
}

function getChineseTrendInCompare(type) {
	var s = "平稳中";
	if (type > 0)
		s = "上涨中";
	if (type < 0)
		s = "下跌中";
	return s;
}

// 显示以商家为分组的价格趋势图
function getSourcePriceHistory() {
	var docid = $("#docId").val();
	var options = {
		price : $("#low-price-price").text(),
		site : "最低价",
		height : 192,
		width : 420,
		titleAlign : "left",
		crosshairsColor : [ "#ff1919", "#ff1919" ],
		legendEnabled : false,
		handler : function(result) {
			$("#topPriceHistroyDiv").addClass("ajax_loading");
			if (result.val) {
				$("#priceTrendType").val(result.val.averiageType);
				$("#price-status").addClass(getTypeClassInCompare(result.val.averiageType));
				$("#price-status").text(getChineseTrendInCompare(result.val.averiageType));
				if (result.val.changePrice) {
					$("#price-date").text("(" + formate(new Date(result.val.changePrice.date)) + ")");
					var sign = (parseInt(result.val.nowPrice.price) - parseInt(result.val.changePrice.price)) > 0 ? "+" : "";
					$("#price-diff").text(sign + (result.val.nowPrice.price - result.val.changePrice.price).toFixed(1));
				} else {
					$("#price-date").text('无变化');
					$("#price-diff").html('');
				}
				$("#topPriceHistroyDiv").removeClass("ajax_loading");
				$('#topPriceHistroyDiv').show();
				return result.val.averiage;
			}
		}
	};
	var url = _basePath + "pricehistory/sourcePriceHistory.htm";
	var container = $("#topPriceHistroyDiv");
	container.b5mtrend(docid, url, options);
}

function getRecommandProduces() {
	var url = _basePath + "goodsDetail/recommandProduces.htm?t=" + new Date().getTime();
	var data = {
		docId : $("#docId").val(),
		title : $('#productTitle').val().split(" ")[0]
	};
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		success : function(httpObj) {
			$('#recommand-shop-view').removeClass('ajax_loading');
			if (httpObj != "") {
				var html = "";
				var shopNum = 0;
				$.each(httpObj, function(i, item) {
					$.each(item.shopList, function(index, shop) {
						shopNum++;
						shop.Url = _basePath + 'item/' + shop.DocId + '.html';
						var btnText = "去看看";
						if (shop.itemCount > 1) {
							shop.Url = _basePath + 'compare/' + shop.DocId + '.html';
							btnText = "去比价";
						}
						html += '<li class="grid-mod">' + '<div class="pic-wrap">' + '<a target="_blank" data-attr="1010" class="pic" href="' + shop.Url + '">' + '<img src="' + shop.Picture + '" alt="">' + '</a>' + '</div>' + '<p><a target="_blank" class="des" href="' + shop.Url + '">' + shop.Title + '</a></p>';
						if (shop.itemCount > 1) {
							var sourceCount = 1;
							if (shop.Source.indexOf(",") > 0) {
								sourceCount = shop.Source.split(",").length;
							}
							if (shop.price.indexOf("-") > 0) {
								shop.price = shop.price.split("-")[0];
							}
							html += '<div class="price"><div class="nums-goods"><em>' + sourceCount + '</em>家商城</div><strong><b>&yen;</b>' + shop.price + '</strong></div>';
						} else {
							html += '<p class="price"><strong><b>&yen;</b>' + shop.price + '</strong></p>';
						}
						html += '<div class="start clear-fix"><div class="nums-goods">' + (shop.itemCount > 1 ? '<em>' + shop.itemCount + '</em>个比价商品' : shop.Source) + '</div></div>' + '<div class="btn-wrap clear-fix"><a data-attr="1010" target="_blank" class="go-buy fl" href="' + shop.Url + '">' + btnText + '</a></div>' + '</li>';
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

function getId(url) {
	var id = url.split("id=")[1];
	if (id.indexOf("&") > 0) {
		id = id.split("&")[0];
	}
	return id;
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

function buildTag(text) {
	var html = "";
	if (text) {
		var tag = text.split(',');
		$.each(tag, function(i, item) {
			if (i >= 3)
				return false;
			html += "<p>" + item + "</p>";
		});
	} else {
		html += '&nbsp;';
	}
	return html;
}

function initSplitPageClick() {
	$(document).on('click', '#split-page-view a', function() {
		if ("go" != $(this).attr("name") && !$(this).hasClass("dis")) {
			getComments($('#commentType').val(), $(this).attr('data'));
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
	getComments($('#commentType').val(), val);
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

function getComments(commentTypeStr, currentPageNo, handler) {
	getCommentById($("#sel").val(), commentTypeStr, currentPageNo, handler);
}

function getCommentById(id, commentTypeStr, currentPageNo, handler) {
	$('#comment-view').html('');
	$('#comment-view').addClass('ajax_loading');
	var data = {
		docId : id,
		commentTypeStr : commentTypeStr == null ? "ALL" : commentTypeStr,
		currPageNo : currentPageNo == null ? 1 : currentPageNo,
	};
	var url = _basePath + "goodsDetail/detailsComments.htm?t=" + new Date().getTime();
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		success : function(result) {
			if (commentTypeStr == null || commentTypeStr == "" || commentTypeStr == "ALL") {
				if (result.totalRecord >= 0) {
					$("#pinglun").show();
					$('.comment-count').text("(" + result.totalRecord + ")");
					$("#head-comment-count").text(result.totalRecord);
				}

				// 好中差评数量
				if (result.goodNum >= 0)
					$("#good span").text("好评(" + result.goodNum + ")");
				if (result.midNum >= 0)
					$("#normal span").text("中评(" + result.midNum + ")");
				if (result.badNum >= 0)
					$("#bad span").text("差评(" + result.badNum + ")");
			}
			assemebl(result, handler);
			$('#comment-view').removeClass('ajax_loading');
		}
	});
}

function getLocalTime(ms) {
	var d = new Date(ms);
	return d.getFullYear() + "-" + d.getMonth() + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();//new Date(ms).toLocaleString().replace(/年|月/g, "-").replace(/日|下午|上午/g, " ");
}

function getValue(value) {
	return value == null ? "" : value;
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

function assemebl(result, handler) {
	is_init_comment = true;
	if (result.totalRecord == 0) {
		$('#comment-view').html("<li><span class='no-comment-txt'>商品暂时没有评论<span></li>");
		return;
	}
	var html = "";
	$.each(result.records, function(i, item) {
		html += '<li><p class="comment-user-info"> <span class="user-info-name fl"> <img src="' + _basePath + 'images/avatar/' + random(1, 20) + '.png" width="63" height="63" alt=""> </span> <span class="user-info-txt">' + (item.author == null ? createName() : item.author) + '</span> </p> <div class="comment-content-info">  <div class="comment-content">' + item.content + '<span class="comment-title cl999">内容：</span> <p class="comment-prod-info cl999"> <span class="comment-time fr  cl999"> 评论时间：' + getLocalTime(item.createTime) + ' </span> <span>购买日期：' + getLocalTime(item.createTime) + '</span> <span>' + getValue(item.commentAttr) + '</span> </p> </div> </div></li>';
	});
	html += '';
	if (result.totalPage > 1) {
		// 初始化分页数据
		html += '<div id="split-page-view" class="clear-fix page"><div>';
		if (result.currentPage <= 1) {
			html += '<a href="javascript:void(0)" class="prev dis"><</a>';
		} else {
			html += '<a href="javascript:void(0)" class="prev" data="' + (result.currentPage - 1) + '"><</a>';
		}
		if (result.pageIndex.startindex > 1) {
			html += '<a href="javascript:void(0)" data="1" class="first">首页</a>';
			html += '<span>...</span>';
		}
		for ( var pageCode = result.pageIndex.startindex; pageCode <= result.pageIndex.endindex; pageCode++) {
			var cur = "";
			if (pageCode == result.currentPage) {
				cur = "cur";
			}
			html += '<a class="' + cur + '" href="javascript:void(0);" data="' + pageCode + '">' + pageCode + '</a>';
		}
		if (result.pageIndex.endindex < result.totalPage) {
			html += '<span class="point">...</span>';
			html += '<a href="javascript:void(0)" data="' + result.totalPage + '" class="last">尾页</a>';
		}
		if (result.currentPage < result.totalPage) {
			html += '<a href="javascript:void(0)" class="prev" data="' + (result.currentPage + 1) + '">></a>';
		}
		if (result.currentPage >= result.totalPage) {
			html += '<a href="javascript:void(0)" class="prev dis">></a>';
		}
		html += '<span class="all">共' + result.totalPage + '页&nbsp;&nbsp;去第</span>';
		html += '<span class="page-input">';
		html += '<input type="text" onkeydown="return keydown(event, ' + result.totalPage + ');" onkeyup="return keydown(event,' + result.totalPage + ');">';
		html += '<a href="javascript:void(0)" name="go" onclick="clickGo(' + result.totalPage + ');return false;">GO</a>';
		html += '</span>';
		html += '<span class="go">页</span>';
		html += '</div></div>';
	}
	if (result.totalRecord > 0) {
		$('#comment-view').html(html);
	}
	if (handler) {
		handler(result);
	}
}

function getCommentsInfo() {
	$('#commentType').val('');
	getComments('ALL', 1);
}

function initSelectCommentTag() {
	$('.comment-type').on('click', 'li', function() {
		if ($(this).hasClass('cur')) {
			return;
		}
		$(this).addClass("cur").siblings('li').removeClass('cur');
		$('#commentType').val($(this).attr("attr-type"));
		getComments($(this).attr("attr-type"), 1);
	});
}

function initSource() {
	var select = $("#sel");
	$(".tab-shop-item .shop-list li:nth-child(1) .shop-ratio").each(function() {
		var source = $(this).find("input").attr("source");
		var id = $(this).find("i").attr("id").split("-")[2];
		select.append($("<option>").val(id).text(source));
	});
	select.change(function() {
		var id = $(this).val();
		getCommentById(id);
	});
	select.get(0).selectedIndex = 0;
	getComments('ALL', 1);
}

function targetTab(obj) {
	if (!obj)
		return;
	obj.trigger('click');
}