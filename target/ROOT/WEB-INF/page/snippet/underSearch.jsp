<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>

<input autocomplete="off" type="hidden" id="ajaxCollection" value="b5mp" />
<input autocomplete="off" type="hidden" id="hdnCurIndex" />
<input autocomplete="off" type="hidden" id="hdnKeyTemp" />
<input autocomplete="off" type="hidden" id="hdnTarget" value="${basePath}${defSearchUrlNoKey}%23keyword%23.html" />

<!--search s-->
						
 <div class="search clear-fix">
	<div class="hd-search" style="margin:30px 0 0;float:right;">
		<div class="hd-search-area">
			
     <input class="input" autocomplete="off" type="text" id="query2"  
         x-webkit-speech onkeydown="selectBykeyEventInBottom(event);" onkeyup="getAutoFill(event, '${basePath}');"
         maxLength="40" value="${keyword}">
			<label id="label2"  style="display: none;" >${showLabel}</label>
		<a class="btn" href="javascript:doSubmitInBottom();">搜索</a>
		<b id="closes2" class="close" style="display: none;">&times;</b>
		</div>
	</div>
</div> 
<!--search e-->
						
<script type="text/javascript">
//搜索框文字
label("label2");
//高亮
nav_active();
/*
 * 按上下键，选择autofill列表的值
 * @param eve 事件对象
 */
function selectBykeyEventInBottom(eve) {	
	if (!eve) {
		eve = window.event;
	}//火狐中是 window.event
	if ((eve.keyCode || eve.which) == 13) {
		doSubmitInBottom();
	}
}

/*
 * 提交搜索
 */
function doSubmitInBottom() {
	/*if (typeof (doBeforeSearch) != "undefined") {
		doBeforeSearch();
	}*/
	var target = document.getElementById("hdnTarget").value;
	var keyWordObj = document.getElementById("query2");
	var keyWord = trim(keyWordObj.value);
	if (keyWord == "" || keyWord == "*") {
		keyWordObj.value = "";
		keyWordObj.focus();
		return;
	}
	
	while(true){
		if(keyWord.indexOf('_')>=0){
			keyWord = keyWord.replace("_"," ");
		}else{
			break;
		}
	}
	
	while(true){
		if(keyWord.indexOf('\\')>=0){
			keyWord = keyWord.replace("\\"," ");
		}else{
			break;
		}
	}
	
	while(true){
		if(keyWord.indexOf('"')>=0){
			keyWord = keyWord.replace('"','&quot;');
		}else{
			break;
		}
	}
	
	if(keyWord.length>100){
		alert("关键词不能超过100个字符，请重新输入");
		return;
	}
	
	appendHistoryCookie(keyWord);
	
	keyWord = encodeURIComponent(keyWord);
	while(true){
		if(keyWord.indexOf('%2F')>=0){
			keyWord = keyWord.replace("%2F","/");
		}else{
			break;
		}
	}
	var url = target.replace('%23keyword%23',keyWord);
	url = url.replace('#keyword#',keyWord);
	goUrlByATag(url);
	
	if (typeof (doAfterSearch) != "undefined") {
		doAfterSearch();
	}
}	
//显示和隐藏搜索框文字
$(function(){
	initQueryInput2();
});


function initQueryInput2(){
	if($("#query2").val() == ''){
        $('#label2').show();
        $(this).css({'border-color':'#9c9c9c'});
        
    }
  $("#query2").bind("blur",function(){
      if($("#query2").val() == ''){
          $('#label2').show();
          $(this).css({'border-color':'#9c9c9c'});
          
      }
  }).bind("focus",function(){
  	$(this).css({'border-color':'#e87e01'});
      $('#label2').hide();
  });
  
  $("#label2").bind("click",
  function(){
      $('#label2').hide();
      $("#query2").focus();
  });
};
</script>