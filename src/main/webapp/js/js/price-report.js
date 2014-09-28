function getCategory(flag){
    $.getJSON(_basePath + "report/categories.htm?flag="+flag+"&monthId="+$('#monthId').val(), function(result){
        var json = result.val;
        var html = "";
        var count = 0;
        var url = window.location.href;
        $.each(json, function(i, item){
            var data = item.count == 0 ? "no-data" : "";
            var cur = curCategory == item.id ? true : false;
			if (cur) {
				html += "<li class='active " + data + "'><a href='" + replaceParam(url, item.id) + "'>" + item.category + " ( " + item.count + " )</a><s class='side-menu-ico'></s></li>";
			} else {
				html += "<li class='" + data + "'><a href='" + replaceParam(url, item.id) + "'>" + item.category + " ( " + item.count + " )</a></li>";
			}
            count += item.count;
        });
        html = "<li><a style='cursor:default;color:black;' href='javascript: void(0);'>全部分类 ( " + count + " )</a></li>" + html;
        $('#categories').html(html);
    });
}

function replaceParam(url, id){
	if (url.indexOf("?") > 0) {
		if (url.indexOf("category=") > 0) {
        	url = url.replace(/(category=\d*)/g, 'category=' + id);
    	} else {
			url = url + "&category=" + id;
		}
		url = url.replace(/(mps=.*?&)|(&mps=[^&]*)/g, '');
    } else {
	    url = url + "?category=" + id;
	}
	return url;
}

function gotoPage(page){
    var url = window.location.href;
    if (url.indexOf("currentPage=") > 0) {
        url = url.replace(/(currentPage=\d*)/g, 'currentPage=' + page);
    } else {
        if (url.indexOf("?") > 0) {
            url += "&currentPage=" + page;
        } else {
            url += "?currentPage=" + page;
        }
    }
	url = url.replace(/(mps=.*?&)|(&mps=[^&]*)/g, '');
    window.location.href = url;
}

function gotoBuy() {
	$("small>u",".column-zdj").click(function(){
		window.open($(this).attr("data-url"));
		return false;
	});
}
