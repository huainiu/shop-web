function isKeywordLegal(keyword){
	if(keyword == "%5C")
		return false;
	return true;
}
/*
 * 提交搜索
 */
function doSubmit() {
	/*if (typeof (doBeforeSearch) != "undefined") {
		doBeforeSearch();
	}*/
	var target = document.getElementById("hdnTarget").value;
	var keyWordObj = $(".J_autofill")[0];
	var keyWord = trim(keyWordObj.value);
	if (keyWord == "" || keyWord == "*") {
		keyWordObj.value = "";
		keyWordObj.focus();
		return;
	}
	if(keyWord.length>100){
		alert("关键词不能超过100个字符，请重新输入");
		return;
	}
	//appendHistoryCookie(keyWord);
	keyWord = encodeURIComponent(keyWord);
	keyWord = getParams(keyWord);
	var url = target.replace('%23keyword%23',keyWord);
	url = url.replace('#keyword#',keyWord);
	if(isKeywordLegal(keyWord)){
		setTimeout(function(){
			goUrlByATag(url);
		}, 10);
	}else{
		url = url.replace(keyWord, '').trim();
		url += "?keyWord=" + keyWord;
		setTimeout(function(){
			goUrlByATag(url);
		}, 10);
	}
	
	if (typeof (doAfterSearch) != "undefined") {
		doAfterSearch();
	}
}
function searchfrm(frm,frmid){
	var url = frm[0].value;
	var str = frm[1].value;
	//var str_city = frm[3].value;
	if( str)
	{
		var openurl =  url.replace('[##]',getParams(str));
		/*if(str_city){
			var openurl =  url.replace('[**]',str_city);
		}*/
		frm.action = openurl;
		frm.target = '_self';
		return true;
	}
	  return false;
} 
function searchfrm1(){
	var str = document.getElementById('frmf5id').value;
	//var str_city = frm[3].value;
	if( str)
	{
		 document.getElementById('kwid').value=encodeURIComponent(str);
		return true;
	}
	  return false;
}
function formSubmit(form){  
	   document.getElementById(form).submit();  
} 
function submit_search(e){
	$(e).next('input:submit:first').trigger('click');
	return false;
}
function submit_search_b(e){
	$('#formid').attr('action',_basePath + 'search/s/___image________________[###].html');
	var a_val = $('.header-search-key').val();
    if(a_val ==''){
        return false;
    }
	var url = $('#formid').attr('action');
	var url = url.replace('[###]',encodeURIComponent(a_val));
	//alert(url);
	$('#formid').attr('action',url);
	$(e).next('input:submit:first').trigger('click'); 
	//alert(1111);
	$('#formid').attr('action',url);
	return true;
	//return false;
}
function goUrlByATag(url){
	var el = document.createElement("a");
	document.body.appendChild(el);
	el.href=url;
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
// JavaScript Document
/*
 * 去掉字符串前后空格
 * @param keyWord 没转换前字符串
 * @return 去掉前后空格后的字符串
 */
function trim(keyWord) {
	var regex = /^\s*|\s*$/g;
	if(typeof(keyWord)!='undefined') {
		var trimstr = keyWord.replace(regex, "");
		return trimstr;
	} else {
		return "";
	}
}
function goUrlByATag(url){
	var el = document.createElement("a");
	document.body.appendChild(el);
	el.href=url;
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
function getParams(str){
	str = str.replace(/_/g,"");
	str = str.replace(/\//g,"");
	str = encodeURIComponent(str);
	str = str.replace(/%2F/,"/");
	return str;
}
