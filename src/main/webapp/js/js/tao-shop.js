function getTaoGoods(taoKeywords, pageSize){
	if(!pageSize) pageSize = 8;
	$.post(_basePath + "taoGoods.htm?t=" + new Date().getTime(), {keyword:taoKeywords,pageSize:pageSize}, function(result){
		$("#tao").css({'background-image':'none'});
		var html = "";
		$.each(result, function(i, item){
			var discount = (Math.ceil(item.discount*100)/10).toFixed(1);
			html += '<div class="tao-mod"><h3 class="tit"><a href="'+item.sourceUrlFor3w+'" target="_blank">' + item.name + '</a></h3><div class="pic"><a href=\"'+item.sourceUrlFor3w+'\" target="_blank"><img src=\"'+item.imgurl+'\" />'
				 + '</a><div class="original"><span class="del">原价：&yen;'+item.sourcePrice+'</span><span class="zhe">'+discount+'折</span><span class="spc"><b>'+item.totalClick+'</b>人已购</span></div></div>'
				 + '<div class="now"><span class="price"><b>&yen;</b>'+item.salesPrice+'</span><a href="'+item.sourceUrlFor3w+'" target="_blank" class="btn">去抢购</a></div></div>';
		});
		if(html != ""){
			$('.tao-bd').append(html);
			$("#tao").show();
		} else {
			$("#tao").css({'display':'none'});
		}
	}).error(function() {
		$("#tao").css({'display':'none'});
	});
}