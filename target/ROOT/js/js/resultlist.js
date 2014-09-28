var ResultPage = function() {
};
ResultPage.init = function(){
	$("#startPrice").keyup(function() {
		ResultPage.validatePrice(document.getElementById("startPrice"));
	});
	// 校验
	$("#endPrice").keyup(function() {
		ResultPage.validatePrice(document.getElementById("endPrice"));
	});
	// 回车事件
	$("#startPrice").keydown(function(e) {
		if (e && e.keyCode == 13) { // enter 键
			ResultPage.searchByPrice();
		}
	});
	$("#endPrice").keydown(function(e) {
		if (e && e.keyCode == 13) { // enter 键
			ResultPage.searchByPrice();
		}
	});
	$("#inner-search-btn").click(function(e) {
		e.preventDefault();
		ResultPage.searchByPrice();
	});
	// $("#J_submit").click(function(){Recommend() });
	$('#cancel-filter').click(function(evt) {
		var pathname = location.pathname;
		var pos = pathname.indexOf('/s/');
		var pre = pathname.substring(0, pos + 3);
		var filename = pathname.substring(pos + 3);
		var conds = filename.split('_');
		conds[5] = conds[9] = conds[10] = conds[13] = '';
		filename = conds.join('_');
		pathname = pre + filename;
		$(this).attr('href', pathname);
	});
	// 历史点击记录
	$('li.grid-ls').on('click', function() {
		ResultPage.setHistory($(this));
	});
	$('.etalon a').click(function(evt) {
		var price = $(this).attr("data-price");
		$(this).parents(".grid-in").find(".price-txt").text(price);
	});
};
ResultPage.showAd = function(keywords, cookId, recordUrl, adSize, needShowAd, noRecord) {
	if(needShowAd) adSize = parseInt(adSize) + 1;//如果搜索结果中显示广告，则需要多查询一个广告出来
	// 广告展示
	$.ajax({
		type : "get",
		async : false,
		url : "http://click.simba.taobao.b5m.com/s/data/" + adSize + "_0_V.html",
		dataType : "jsonp",
		data : {
			keywords : keywords,
			cid : cookId
		},
		jsonp : 'jsoncallback',
		success : function(json) {
			var ads = json.val;
			if (ads.length == 0) {
				return;
			}
			$(".side-r").append("<div class='goods-recommend'><h3>商品推荐</h3><ul class='grid-view cf'></ul></div>");
			var pvHtml = "";
			var needScollAppendHtml = "";
			var rightAdNum = 0;
			for (var i = 0; i < ads.length; i++) {
				if(needShowAd && i == 0){//如果需要显示广告，则将广告的第一个挪到搜索结果页去
					ResultPage.addFirstAd(needShowAd, ads[i]);
					continue;
				}
				rightAdNum++;
				var jumpUlr = ads[i].Url;
				var picUrl = ads[i].Picture;
				var title = ads[i].Title;
				var price = ads[i].Price;
				picUrl = picUrl.replace('img.b5m.com','tfs01.b5mcdn.com');
				if(picUrl.indexOf('b5mcdn.com') > 0){
					picUrl = picUrl + "/260x258";
				}
				var pichtml = "<div class='pic-wrap'><a class='pic' href='" + jumpUlr + "' target='_blank'><img alt='' src='" + picUrl + "'></a></div>";
				var pricehtml = "<div class='price'><strong><b>¥</b>" + price + "</strong></div>";
				var summaryhtml = "<div class='summary'><a href='" + jumpUlr + "' target='_blank'>" + title + "</a></div>";
				var adrshtml = "";
				
				if(!noRecord){
					pvHtml += "<img alt='' src='" + recordUrl + "?cid=" + cookId + "&aid=" + ads[i].aid + "&da=V" + (i + 1) + "&ad=108&dl=" + encodeURIComponent(window.location.href) + "&dstl=" + ads[i].durl + "&lt=8800&t="+new Date().getTime()+"&rp=1002&dd=" + ads[i].DOCID + "' style='display: none;'>";
				}
				var html = "<li class='grid-ls'><div class='grid-mod'><div class='grid-in'>" + pichtml + summaryhtml + adrshtml + pricehtml + "<div class='grid-in'></div></div></li>";
				if(i >= 3){
					needScollAppendHtml = needScollAppendHtml + html;
				}else{
					$(".side-r .grid-view").append(html);
				}
			}
			var html = '<li class="grid-ls"><div class="grid-mod"><div class="grid-in"><div class="pic-wrap"><a class="pic" href="http://korea.b5m.com/hydr_index.html?mps=0.0.0.0.0"><img src="http://y.b5mcdn.com/static/images/search/U-262-200.jpg" alt=""></a></div></div></div></li>';
			if(rightAdNum >= 3){
				needScollAppendHtml = needScollAppendHtml + html;
			}else{
				$(".side-r .grid-view").append(html);
			}
			var adHaveAppend = false;
			$(window).scroll(function () {
				if(adHaveAppend) return;
				adHaveAppend = true;
				$(".side-r .grid-view").append(needScollAppendHtml);
			});
			if(!noRecord){
				setTimeout(function(){
					$("body").append(pvHtml);
				}, 1000);
			}
		},
		error : function() {
		}
	});
};
ResultPage.addFirstAd = function(needShowAd, ad){
	if(needShowAd){
		var jumpUlr = ad.Url;
		var picUrl = ad.Picture;
		var title = ad.Title;
		var price = ad.Price;
		var $li = $('<li class="grid-ls"></li>');
		var $gridmod = $('<li class="grid-mod"></li>');
		var $gridin = $('<div class="grid-in"></div>');
		$li.append($gridmod);$gridmod.append($gridin);
		$gridin.append('<div class="pic-wrap"><a class="pic" href="'+jumpUlr+'" target="_blank"><img class="grid-mod-pic" src="'+picUrl+'/238x238" alt="'+title+'"></a></div>');
		$gridin.append('<div class="summary"><a data-attr="1008" href="http://s.b5m.com/item/a49ed38aa93ae822c19d88481a9fa6ee.html" title="'+title+'" target="_blank">'+title+'</a></div>');
		$gridin.append('<div class="price"><strong><b>¥</b>' + price + '</strong></div>');
		$gridin.append('<div class="start"><div class="nums-goods r"><em></em></div></div>');
		var $ul = $('#J_goods_list');
		$ul.find('li').eq(0).before($li);
		$ul.find('li').last().remove();
	}
};
ResultPage.initPriceTrend = function(){
	var docids = $("#price-trend-docids").val();
	$.post(_basePath + "pricehistory/priceTrendTypes.htm", {
		docIds : docids
	}, function(result) {
		$("#price-trend-type").val(result);
		ResultPage.showPriceIcon();
	});
};
ResultPage.showPriceIcon = function() {
	var priceTrendType = $("#price-trend-type").val();
	if (priceTrendType) {
		var typesInfo = priceTrendType.split(";");
		for ( var i = 0; i < typesInfo.length; i++) {
			var docid = typesInfo[i].split(",")[0];
			var type = typesInfo[i].split(",")[1];
			// priceHistoryIcon_1014a238307ebbe72bffde0228de9e2c
			var $div = $("#price-trend-" + docid);
			if (type == "-1") {// flat-icon
				$div.addClass("price-down");
			} else if (type == "0") {
				$div.addClass("price-fair");
			} else if (type == "1") {
				$div.addClass("price-up");
			} else {
				$div.addClass("price-fair");
			}
			$div.show();
			banner2("#price-trend-" + docid, "#photo_price");
		}
	}
};
var his_length = 2;
ResultPage.setHistory = function(e) {
	var his = Cookies.get('his');
	var url = e.find('a.pic').attr('href');
	var pic = e.find("a.pic img").attr('src');
	var title = e.find(".summary a").attr('title');
	var price = e.find(".price strong").text().replace('¥', '');
	var val = url + "^" + pic + "^" + title + "^" + price;
	if (his && his != "undefined") {
		var array = his.split(";");
		var flag = true;
		for ( var i = 0; i < array.length; i++) {
			if (url.indexOf(array[i].split("^")[0]) >=0 || array[i].split("^")[0].indexOf(url) >=0) {
				flag = false;
				break;
			}
		}
		if (flag) {
			if (array.length == his_length) {
				array.shift();
			}
			array.push(val);
			his = array.join(";");
			Cookies.set('his', his);
			var len = $("#show_his").find('li.grid-ls').length;
			if (len >= his_length) {
				$("#show_his").find('li.grid-ls')[len-1].remove();
			}
			$("#show_his").prepend(addHistory(val.split("^")));
		}
	} else {
		Cookies.set('his', val);
		$("#show_his").html(addHistory(val.split("^")));
	}
	var hisList = $('.scroll-history .grid-view').find('li'),
	len = hisList.length ;
	if(len > 2){
	        $('.scroll-history').css('height',400);
	}
};
ResultPage.showHistory = function() {
	var history_info = Cookies.get("his");
	var content = "";
	if (history_info && history_info != "undefined") {
		history_arg = history_info.split(";");
		var length = history_arg.length;
		for (var i = length - 1; i >= 0; i--) {
			if (history_arg[i] && history_arg[i] != "undefined") {
				var wlink = history_arg[i].split("^");
					content += addHistory(wlink);
			}
		}
		$("#show_his").html(content);
	} else {
		$("#show_his").html("对不起，您没有任何浏览记录");
	}
};
function addHistory(wlink) {
	wlink[1] = wlink[1].replace("img.b5m.com", "tfs01.b5mcdn.com");
	var dom = '<li class="grid-ls"><div class="grid-mod"><div class="grid-in"><div class="pic-wrap"> <div class="pic-mod">' + ' <a class="pic" href="' + wlink[0] + '" target="_blank"><img src="' + wlink[1] + '" lazy-src="' + wlink[1] + '" alt=""></a> </div> </div> <div class="summary">' + '<a href="' + wlink[0] + '">' + wlink[2] + '</a></div> <div class="price-mod"> <strong class="price">' + '<b>¥</b><span  class="cl-f00">' + wlink[3] + '</span></strong> </div> </div> </div> </li>';
	return dom;
}
var origValue = "";
/* 价格处理 s */
ResultPage.validatePrice = function(obj) {
	var temp = obj.value;
	var reg = /^[\d]+(\.[\d]*)?$/;
	// var reg = /^[\d]+$/;
	var max = 9999999;
	if (!reg.test(temp)) {
		if (obj.value)
			obj.value = origValue;
	}
	origValue = obj.value;
	if (parseInt(temp, '10') > parseInt(max, '10')) {
		obj.value = max;
	}
};
ResultPage.searchByPrice = function() {
	var startPrice = document.getElementById("startPrice").value;
	var endPrice = document.getElementById("endPrice").value;
	var pathname = location.pathname;
	if (startPrice && (startPrice.indexOf(".") + 1 == startPrice.length)) {
		startPrice = startPrice + "0";
	}
	if (endPrice && (endPrice.indexOf(".") + 1 == endPrice.length)) {
		endPrice = endPrice + "0";
	}
	var ss = pathname.split("_");
	var category = $("#category").val();
	if (!category)
		category = "";
	if (ss.length < 2) {
		pathname = _basePath + "search/s/" + category + "___image______" + startPrice + "_" + endPrice + "_________" + $("#orignKeyWord").val() + ".html";
	} else {
		var pos = pathname.indexOf('/s/');
		var pre = pathname.substring(0, pos + 3);
		var filename = pathname.substring(pos + 3);
		var conds = filename.split('_');
		// low
		conds[9] = startPrice;
		// high
		conds[10] = endPrice;
		if (parseInt(startPrice, '10') > parseInt(endPrice, '10')) {
			conds[9] = endPrice;
			conds[10] = startPrice;
		}
		conds[13] = "";
		filename = conds.join('_');
		pathname = pre + filename;
	}
	if ($("#compareprice").attr("value") == "true") {
		pathname += "?compare=true";
	}
	goUrlByATag(pathname);
};
/* 价格处理 e */

/* 多选搜索 */

function searchMulti(attrs) {
	var pathname = location.pathname;
	var category = $("#category").val();
	if (!category)
		category = "";
	var ss = pathname.split("_");
	if (ss.length < 2) {
		pathname = _basePath + "search/s/" + category + "___image__" + attrs + "______________" + $("#orignKeyWord").val() + ".html";
	} else {
		var pos = pathname.indexOf('/s/');
		var pre = pathname.substring(0, pos + 3);
		var filename = pathname.substring(pos + 3);
		var conds = filename.split('_');
		conds[5] = conds[5] + "," + attrs;
		conds[13] = "";
		filename = conds.join('_');
		pathname = pre + filename;
	}
	if ($("#compareprice").attr("value") == "true") {
		pathname += "?compare=true";
	}
	goUrlByATag(pathname);
}
ResultPage.showClothingLogo = function(){
	var i = 0;
	$("dl.m-dl dt a").each(function(){
		if (i >= 5)
			return;
		if($(this).text()=="女装" || $(this).text()=="女鞋"){
			$("#gotofushi").show();
			if (location.href.indexOf("/search/s") > 0){
				$('#gotofushi').attr("href", location.href.replace("/search/s", "/clothing"));
			} else {
				$('#gotofushi').attr("href", location.origin + location.pathname.replace("/", "/clothing/"));
			}
		}
		i++;
	});
};
ResultPage.showRecomand = function(opt){
	var container = opt.container;
	ResultPage.getRelKeywords(opt.keywords, function(relkeywords){
		if(!relkeywords){
			container.parent().parent().hide();
			return;
		}
		var url = _basePath + "goodsDetail/recommandProduces.htm?t=" + new Date().getTime() + "&filter=false";
		var data = {title : relkeywords, pageSize : 3};
		Ajax.post(url, data, function(result){
			var index = 0;
			$.each(result, function(i, item) {
				$.each(item.shopList, function(i, shop) {
					shop.Picture = shop.Picture.replace("img.b5m.com", "tfs01.b5mcdn.com");
					var $li = $('<li class="grid-ls"></li>');
					var href = _basePath + "item/" + shop.DOCID + ".html";
					var content = '<div class="grid-mod">' + 
					'<div class="grid-in">' + 
					'<div class="pic-wrap">' + 
					'<div class="pic-mod">' + 
					'<a class="pic" target="_blank" href="'+href+'"><img src="' + shop.Picture + '/118x118" lazy-src="'+shop.Picture + '/118x118" alt=""></a>' + 
					'</div>' + 
					'</div>' + 
					'<div class="summary"><a target="_blank" href="'+href+'">'+shop.Title+'</a></div>' + 
					'<div class="price-mod">' + 
					'<strong class="price"><b>¥</b><span  class="cl-f00">'+shop.price+'</span></strong>' + 
					'</div>' + 
					'</div>' + 
					'</div>';
					if(index < 3){
						$li.append(content).appendTo(container);
					}
					index++;
				});
			});
			if(index == 0){
				container.parent().parent().hide();
			}else{
				container.parent().parent().show();
			}
		});
	}, function(){
		container.parent().parent().hide();
	});
	
};
ResultPage.getRelKeywords = function(keywords, callback, errorback){
//	http://search.b5m.com/allAutoFill.htm?keyWord=ip
	var url = _basePath + "/allAutoFill.htm";
	Ajax.post(url, {keyWord : keywords}, function(result){
		var relkeywordsList = result.b5mo;
		var relkeywords = null;
		if(relkeywordsList && relkeywordsList.length > 0){
			relkeywords = relkeywordsList[0].value;
			//如果相同 则取第二个
			if(relkeywords == keywords && relkeywordsList.length > 1){
				relkeywords = relkeywordsList[1].value;
			}
		}
		callback(relkeywords);
	});
};
var Ajax = {};
Ajax.post = function(url, data, callback){
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		success : function(result) {
			callback(result);
		},
		error: function(){
			errorback();
		}});
};


