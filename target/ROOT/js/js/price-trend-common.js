//显示价格趋势图按钮
function banner2(obj, div){
	var oDiv=$(div);
	var oBj=$(obj);
	var oHeight=oBj.height()+4;
	var timer=null;
	oBj.hover(function(){
		clearTimeout(timer);
		var offset = 213;
		var _w = $(window).width() - $(this).offset().left;
		if(_w < 250){
			offset = _w - 40;
		}
		var docid = this.id.split("-")[2];
		var oLeft=parseInt($(this).offset().left) - 213 * 2 + offset;
		var oTop=parseInt($(this).offset().top) + 15;
		var left=oLeft;
		var top=oTop + oHeight;
		timer=setTimeout(function(){
			if(docid){
				// 获得价格趋势图并显示
				showProductPriceTrend(docid,oDiv,left,top);
			}
		},250);
	},function(){
		clearTimeout(timer);
		timer=setTimeout(function(){
			oDiv.hide();
		},250);
	});
	oDiv.hover(function(){
		clearTimeout(timer);
	},function(){
		clearTimeout(timer);
		timer=setTimeout(function(){
			oDiv.hide();
		},250);
	});
};
//显示单个商品的价格趋势图
function showProductPriceTrend(docid, $showDiv, left, top){
	var $priceTrendContaner = $("#photo_price_content");
	$priceTrendContaner.html('');
	$dataInput = $("#price-trend-data-"+docid);
	var options={
        height:184,
        width:426,
        titleAlign:"left",
        crosshairsColor:["#ff1919","#ff1919"]
    };
	var source = encodeURIComponent($dataInput.attr("source"));
	var url = _basePath + "pricehistory/priceTrend.htm?source=" + source;
	
	$priceTrendContaner.b5mtrend(docid, url, options);
	$showDiv.css({"left":left,"top":top,"display":"block"});
	$priceTrendContaner.show();
	$priceTrendContaner.parent().show();
}
function formate(date){
    return date.getFullYear() + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日";
}
function getChineseTrend(type){
    var s = "价格平稳";
    if (type > 0)  s = "涨价";
    if (type < 0)  s = "跌价";
    return s;
}
function getTypeClass(type){
    var cls = "flat-icon";
    if (type > 0) cls = "high-icon";
    if (type < 0) cls = "low-icon";
    return cls;
}

/*var Taodianjin={};
Taodianjin.tdj = {s:"mm_41856994_4212380_13770372",search:"mm_41856994_4274314_14460045"};
Taodianjin.pid = function(){
	if("s.b5m.com" == _server){
		return Taodianjin.tdj.s;
	}
	return Taodianjin.tdj.search;
};
Taodianjin.domain = function(){
	if("s.b5m.com" == _server){
		return "s.b5m.com";
	}
	return "search.b5m.com";
};
//Taodianjin.pid = "mm_41856994_4274314_14460045";
Taodianjin.getId = function(url){
	var id = url.split("id=")[1];
	if(id.indexOf("&") > 0){
		id = id.split("&")[0];
	}
	return id;
};
Taodianjin.loadEt = function(callback){
	$.ajax({
        url : "http://g.click.taobao.com/load",
        data : "rf=" + Taodianjin.domain() + "&pid=" + Stat.tdj().pid,
        type : "get",
        dataType : "jsonp",
        jsonp: "cb",
        success: function(d){
        	if(typeof callback ==='function'){
        		callback(d.code);
        	}
        },
        error:function(){
        
        }
    });
};
Taodianjin.relate = function(itemid, code, callback){
	$.ajax({
        url : "http://g.click.taobao.com/display",
        data : "pid=" + Stat.tdj().pid + "&wt=10&tl=720x220&rd=1&ct=itemid%3D"+itemid+"&st=2&rf=" + Taodianjin.domain() + "&et="+code+"&v=2.0&_=1382324707035&pgid=c6c8adef0fdca94bf99321b8d3ef9e36",
        type : "get",
        dataType : "jsonp",
        jsonp: "cb",
        success: function(d){
        	if(typeof callback ==='function'){
        		if(d.code == 200){
        			callback(d.data.items);
        		}
        	}
        },
        error:function(){
        
        }
    });
};
Taodianjin.fill = function(itemid, $container, source){
	if(!Stat.tdj()) return;
	Taodianjin.loadEt(function(code){
		Taodianjin.relate(itemid, code, function(items){
			var html = "";
			var have = false;
			$.each(items, function(index, item){
				have = true;
				html += '<li class="grid-mod recommend-common">'
					+ '<div class="pic-wrap">'
					+ 	'<a class="pic" href="'+item.ds_item_click+'" target="_blank"><img src="'+item.ds_img.src+'" alt=""></a>'
					+ '</div>'
					+ '<p><a class="des" href="' + item.ds_item_click + '" target="_blank">'+item.ds_nick+'</a></p>'
					+ '<div class="price">'
					+ 	'<div class="nums-goods">' + source + '</div>'
					+ 	'<strong><b>&yen;</b>'+item.ds_discount_price+'</strong>'
					+ 	'<del class="old-price">&yen;'+item.ds_reserve_price+'</del>'
					+ '</div>'
					+ '<div class="start clear-fix">'
					+  	'<p>销量：' + item.ds_sell + '件</p>'
					+ '</div>'
					+ '<div class="btn-wrap clear-fix"><a class="go-buy fl" href="' + item.ds_item_click + '" target="_blank">去抢购</a></div>'
					+ '</li>';
			});
			if(have){
				if($container.find("li").length < 4){
					$container.html('');
				}
			}
			$container.append(html);
		});
	});
};
Taodianjin.link = function(url, callback){
	Taodianjin.loadEt(function(code){
		var links = "http://g.click.taobao.com/q?pid=" + Stat.tdj().pid + "&rd=1&ct=" + escape("url=" + escape(url)).replace("/", "%252F") + "&rf=" + Taodianjin.domain() + "&et=" + code + "&pgid=0ed1ddeb7899bc090e868d6396e3fd03&v=1.1";
		if(typeof callback ==='function'){
    		callback(links);
    	}
	});
};*/