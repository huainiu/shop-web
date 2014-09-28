$(function() {
	// banx
	showBanx($("#goods-title").text(), $("#cookieId").val(), $("#adRecordUrl").val(), 12);
});

function showBanx(keyword, cookieId, recordUrl, adSize) {
	var res_docid = $('#docId').val();
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
			$("#shop-recommend").append('<div class="recommend-prod"><h3>推荐商品</h3> <ul class="recommend-prod-list" id="J_prod_slider">  </ul> <div class="trigger-button" id="J_trigger"> <span class="arrow-prod arrow-up arrow-up-disable">上</span><span class="arrow-prod arrow-down">下</span> </div></div>');
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
					if (i >= adSize / 2)
						picUrl = "";
					var adrshtml = "<img alt='' src='" + recordUrl + "?cid=" + cookieId + "&aid=" + ads[i].aid + "&da=V" + (i + 1) + "&ad=108&dl=" + encodeURIComponent(window.location.href) + "&dstl=" + ads[i].durl + "&lt=8800&t=" + new Date().getTime() + "&rp=1002&dd=" + ads[i].DOCID + "' style='display: none;'>";
					var html = '<li><a href="' + jumpUlr + '" target="_blank"><i class="pic-center"><img src="' + picUrl + '" data-src="' + data_url + '" alt=""></i><span>' + title + '</span></a>' + adrshtml + '</li>';
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
									if (picUrl.indexOf("tfs01") >= 0)
										picUrl = picUrl + "/120x120";
									var adrshtml = "<img alt='' src='" + recordUrl + "?cid=" + cookieId + "&aid=" + shop.aid + "&da=V" + (ads.length - j + i + 1) + "&ad=108&dl=" + encodeURIComponent(window.location.href) + "&dstl=" + shop.durl + "&lt=8800&t=" + new Date().getTime() + "&rp=1002&dd=" + shop.DOCID + "' style='display: none;'>";
									var html = '<li><a href="' + jumpUlr + '" target="_blank"><i class="pic-center"><img src="' + picUrl + '" alt=""></i><span>' + title + '</span></a>' + adrshtml + '</li>';
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

var ShopDetail = {};

ShopDetail.shopHaiwaiAd = function(opt) {
	var container = opt.container;
	var $ul = $('<ul class="grid-view cf"></ul>');
	$ul.appendTo(container);
	Ajax.jsonp(opt.url, {}, function(result) {
		$.each(result.val, function(index, ele) {
			var $li = $('<li class="grid-mod"></li>');
			$li.append('<a class="pic" href="' + ('http://korea.b5m.com/item/' + ele.DOCID) + '.html"> <img src="' + ele.Picture + '" alt="' + ele.Title + '"></a>');
			$li.append('<div class="grid-item"><a class="des" href="' + ele.Url + '">' + ele.Title + '</a> </div> ');
			$li.append('<div class="grid-item gird-price"> <span class="goods-origin r">' + ele.Source.split(",")[0] + '</span> <strong class="price"><b>￥</b>' + ele.Price.split("-")[0] + '</strong> </div>  ');
			$li.append('<div class="grid-item grid-go-buy"> <a href="' + ele.Url + '" class="go-buy l">去看看</a> </div>');
			$ul.append($li);
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
