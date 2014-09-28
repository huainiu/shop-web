Date.prototype.format = function(format)
{
    var o = {
    "M+" : this.getMonth()+1, //month
    "d+" : this.getDate(),    //day
    "h+" : this.getHours(),   //hour
    "m+" : this.getMinutes(), //minute
    "s+" : this.getSeconds(), //second
    "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
    "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,
    RegExp.$1.length==1 ? o[k] :
    ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}
$(function(){
	$("#allPass").click(function(){
		if(!backIds()){
			alert("您好没有选择，请选择");
			return;
		}
		audit($(this), true, "确定全部通过嘛?", backIds());
	});
	$("#allNoPass").click(function(){
		if(!backIds()){
			alert("您好没有选择，请选择");
			return;
		}
		audit($(this), false, "确定全部不通过嘛?", backIds());
	});
	function backIds(){
		var ids = "";
		$("input[name='tableSelect']").each(function(){
			if(this.checked){
				ids = ids + this.value + "_";
			}
		});
		return ids;
	}
	$("#allSelect").change(function(){
		var flag = false;
		if(this.checked){
			flag = true;
		}
		$("input[name='tableSelect']").each(function(){
			this.checked = flag;
		});
	});
	$("input[name='btnpass']").click(function(){
		audit($(this), true, "确定通过嘛?", $(this).attr("idValue"));
	});
	$("input[name='btnnopass']").click(function(){
		audit($(this), false, "确定不通过嘛?", $(this).attr("idValue"));
	});
	$('#supplierId').change(function(){
		gotoPage(1);
	});
	$("#search").click(function(){
		gotoPage(1);
	});
	$("#type").change(function(){
		gotoPage(1);
	});
});
function getByClass(oParent,sClass)
{
	var arr = [];
	var aEle = document.getElementsByTagName('*');

	for (var i = 0; i<aEle.length; i++){
		if(aEle[i].className.indexOf(sClass) !=-1)
		{
			arr[arr.length] = aEle[i];
		}
	}
	return arr;
}
function clientHeight(id)
{
	if(!id) return false;
	var wrap= document.getElementById(id);
	var sideBar = getByClass(wrap,'col-sub')[0];
	var wrapMain = getByClass(wrap,'wrap-main')[0];

	wrap.style.height = sideBar.style.height = wrapMain.style.height =  document.documentElement.clientHeight-126+'px';
	window.onresize = function()
	{
		clientHeight(id);
	};
}
clientHeight('wrap');

function tab(id)
{
	if(!id) return false;
	var obj = document.getElementById(id);
	var tabMenu = getByClass(obj,'user-tab')[0].getElementsByTagName('li');
	var tabBox = getByClass(obj,'user-box');

	for (var i = 0; i<tabMenu.length; i++)
	{
		tabMenu[i].index = i;
		tabMenu[i].onclick = function()
		{
			for(var i = 0; i<tabMenu.length; i++)
			{
				tabMenu[i].className = '';
				tabBox[i].style.display = 'none';
			}
			this.className = 'cur';
			tabBox[this.index].style.display = 'block';
		}
	}
}
tab('user');

//设置分页,这个方法多个页面会调用
function setSplitPageData(splitDto){
	var html = "";
	html += '<div id="split-page-view"><table width="100%"><tbody><tr><td align="center"><div class="page clear-fix"><span class="all">共'+splitDto.totalPageNo+'页</span>';
	
	if(parseInt(splitDto.totalPageNo) > 0){
		if(splitDto.prev != null && splitDto.prev != ''){
			html += '<a class="prev" href="javascript:void(0)" data=\"'+splitDto.prev.text+'\">上一页</a>';
		}
		
		$.each(splitDto.pages, function(i, item){
			var currPageNo = parseInt(splitDto.currentPageNo);
			if(i == 0){
				if(currPageNo == 1){
					html += '<span class="cur">1</span>';
				} else {
					html += '<a href="javascript:void(0)" data='+item.text+'>'+item.text+'</a>';
				}
				if(currPageNo >= 5){
					html += '<span>...</span>';
				}
			}
			
			if(i > 0 && i < ($(splitDto.pages).length - 1)){
				if((currPageNo - 2) <= parseInt(item.text) && currPageNo > parseInt(item.text)){
					html += '<a href="javascript:void(0)" data='+item.text+'>'+item.text+'</a>';
				} else if(currPageNo == parseInt(item.text)){
					html += '<span class="cur">'+item.text+'</span>';
				} else if((currPageNo + 2) >= parseInt(item.text) && currPageNo < parseInt(item.text)) {
					html += '<a href="javascript:void(0)" data='+item.text+'>'+item.text+'</a>';
				}
			}
			
			if(i == ($(splitDto.pages).length - 1)){
				if(currPageNo <= (parseInt(splitDto.totalPageNo) - 4)){
					html += '<span>...</span>';
				}
				
				if(parseInt(splitDto.totalPageNo) != 1){
					if(currPageNo == parseInt(splitDto.totalPageNo)){
						html += '<span class="cur">'+splitDto.totalPageNo+'</span>';
					} else {
						html += '<a href="javascript:void(0)" data='+item.text+'>'+item.text+'</a>';
					}
				}
			}
		});
		
		if(splitDto.next != null && splitDto.next != ''){
			html += '<a class="next" href="javascript:void(0)" data=\"'+splitDto.next.text+'\">下一页</a>';
		}
	}
	
	html += '</div></td></tr></tbody></table></div>';
	console.log(html);
	return html;
}