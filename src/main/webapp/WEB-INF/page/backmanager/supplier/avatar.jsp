<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${basePath}js/js/upload/jquery.uploadify.min.js?t=${today}"></script>
<script type="text/javascript" src="${basePath}js/js/upload/editAvatar.js?t=${today}"></script>
<script type="text/javascript">
var rootPath = '${basePath}';
$(function() {
	var J_avatar = $("#J_avatar");
	var loading = $('.cutbox .loading');
    $("#swfupload-btn").uploadify({
		height        : 27,
		swf           : '${basePath}js/js/upload/uploadify.swf',
		uploader      : '${basePath}backmanager/supplier/logo/upload.htm',
		width         : 66,
		buttonCursor  : 'cursor',
		fileSizeLimit : '2048KB',
		queueSizeLimit : 1,
		removeCompleted : true,
		buttonText	  : '浏览',
		'fileTypeDesc' : 'Image Files',
        'fileTypeExts' : '*.jpeg; *.jpg; *.png; *.gif',
        'multi' : false,
		'onUploadStart'  : function(Data) {
			$('.avatar-upoad input[type=text]').val(Data.name);
			J_avatar.show();
			$("#J_saveAvatar").show();
			loading.html('0% 正在上传...').show();
			$("#J_cancelCut").trigger("click");
        },
        'onUploadError' : function(file, errorCode, errorMsg, errorString) {
        	$("#swfupload-btn").uploadify('cancel');
            alert('文件' + file.name + ' 上传失败' + errorCode + ';errorMsg' + errorMsg);
            loading.hide();
        },
        'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
        	loading.html(Math.floor(bytesUploaded / bytesTotal * 100) + '% 正在上传...');
        },
        'onUploadSuccess' : function(file, data, response) {
        	var data = $.parseJSON(data);
        	if(!data.oper){
            	loading.html('上传失败');
            	$("#J_saveAvatar,#J_avatar").hide();
    			loading.hide();
    			$('.avatar-upoad input[type=text]').val('');
    			alert(data.data);
            } else {
            	loading.html('上传成功,正在加载...');
	            setLogo(data.data);
	            $('#logo_input').attr('disabled', 'disabled');
	            $('.avatar-preview').find('img').attr('src', data.data + '?rnd='+Math.random());
	            cut(data.data);
	            
            }
        }
	});
    var img130 = $('div.img130 img');
    var img48  = $('div.img48 img');
    var img32  = $('div.img32 img');
    function cut(imageUrl) {
    	if(url){
    		$('.cutbox').find('img').attr('src',imageUrl+'?t='+Math.random());
    	}
        $('.cutbox').find('img').cut({
            callback: function(data) {
                data.x = Math.floor(data.x * data.imgRealW / data.imgW);
                data.y = Math.floor(data.y * data.imgRealH / data.imgH);
                
                data.w = Math.floor(data.w * data.imgRealW / data.imgW) ;
                data.h = Math.floor(data.h * data.imgRealH / data.imgH) ;
                var ajaxData = {};
                
                var cutResault = document.createElement('canvas');
                var url,type;
                
                //判断是否可以裁剪
                if(!cutResault.getContext && cutResault.toDataURL){
                	type = 'up';
                    $('.img130').html('<canvas width="130" height="130"></canvas>');
                    $('.img48').html('<canvas width="48" height="48"></canvas>');
                    $('.img32').html('<canvas width="32" height="32"></canvas>');
                    
                    var canvas130 = $('.img130 canvas').get(0);
                    var canvas48 = $('.img48 canvas').get(0);
                    var canvas32 = $('.img32 canvas').get(0);
                    var img = this;
                    img.src= $('.cutbox').find('img').attr('src');
                    canvas130.getContext('2d').drawImage(img, data.x,data.y,data.w,data.h,0, 0, 110, 55);
                    canvas48.getContext('2d').drawImage(canvas130,0,0,48,48);
                    canvas32.getContext('2d').drawImage(canvas48,0,0,32,32);
                    url = rootPath+'backmanager/supplier/logo/upload.htm';
                    //alert(window.atob(canvas130.toDataURL("image/png").substring(22)))
                    ajaxData = {
                        avatar1 : canvas130.toDataURL("image/png"),
                        avatar2 : canvas48.toDataURL("image/png").substring(22),
                        avatar3 : canvas32.toDataURL("image/png").substring(22)
                    };
                } else {
                	type = 'cut';
                	url = rootPath +'backmanager/supplier/logo/cutAvatar.htm';
                    ajaxData = {
                        x    : Math.max(data.x,0),
                        y    : Math.max(data.y,0),
                        destWidth : Math.min(data.w,data.imgRealW),
                        destHeight : Math.min(data.h,data.imgRealH),
                        filePath : imageUrl
                    }
                }
                $('.cutbox').find('.loading').html('正在保存...').show();
                $("#J_saveAvatar").addClass('saveBtnDis');
                $.ajax({
                    type : 'post',
                    url : url + "?t=" + new Date().getTime(),
                    dataType : 'json',
                    data : ajaxData,
                    success  : function(res){
                        if(res.oper){
                        	if(type == 'cut'){
                        		$(".avatar-old").next().attr('src',res.data+'?t='+Math.random());
                        	}
                        	J_avatar.find('img').attr('src',res.data + '?rnd='+Math.random());
                        	J_avatar.find('img').css({
                        		width : '100%',
                        		height : '100%',
                        		left : 0,
                        		top : 0
                        	});
                        	$("#J_saveAvatar").hide();
                			loading.hide();
                			$('.avatar-upoad input[type=text]').val('');
                			setLogo(res.data);
                        }
                    },
                    error : function(){
                        $('.cutbox').find('.loading').hide();
                        $("#J_saveAvatar").removeClass('saveBtnDis');
                    },
                    complate : function(){
                    	console.log("into complate");
                        $('.cutbox').find('.loading').hide();
                        $("#J_saveAvatar").removeClass('saveBtnDis');
                    }
                });
            },
            saveBtn: $("#J_saveAvatar"),
            cancelCutBtn: $("#J_cancelCut"),
            cuting : function(data){ 
            	setPreview(130,data,img130); 
            	setPreview(48,data,img48); 
            	setPreview(32,data,img32); 
           	}
        });
    }
    
    function setPreview(size, data ,img){ 
    	var num = size/data.w ; 
    	img.css({ left : - data.x * num + 'px', top : - data.y * num + 'px', width : data.imgW * num + 'px', height : data.imgH * num + 'px'});
   	}
    $("#J_reCut").bind('click', function(e) {
        e.preventDefault();
        cut();
    });
    
    $('#J_deleteImg').click(function(){
    	var url = rootPath + "backmanager/supplier/logo/delete.htm?t=" + new Date().getTime() + "&filePath=" + $('#file_path').val();
    	$.post(url, function(result){
    		if(result.oper){
    			alert(result.message);
    			setLogo("");
    			$('#logo_input').removeAttr("disabled");
    			$('#file_input_text').val("");
    			$("#J_avatar").hide();
    		}
    	});
    });
});

//设置logo路径
function setLogo(path){
	$('#file_path').val(path);
	$('#logo_input').val(path);
}
</script>
<input type="hidden" id="file_path"/>
<div class="avatar-old" style="height:21px;width:100%">
	<span style="color:#333;font-weight:bold;font-size:14px">当前图标</span>
</div>
	<c:if test="${uc.avatarTransient == null}">
	    <img src="${rootPath }/images/backstage/default_user_pic.jpg" style="display:block" alt="" width="110" height="55" onerror="this.src='${rootPath }/images/ucenter/default_user_pic.jpg'" />
    </c:if><c:if test="${uc.avatarTransient != null}">
    	 <img src="${uc.avatarTransient}?t=${random}" alt="" width="110" height="55" style="margin-top:0" onerror="this.src='${rootPath }/images/ucenter/default_user_pic.jpg'"  />
    </c:if>
<div class="avatar-old" style="height:50px;width:100%;height:auto">
	<span style="color:#333;font-weight:bold;font-size:14px">设置新的图片</span>
	<span style="line-height: 18px;color:#999;display:block;line-height:20px">请选择一个新照片进行上传编辑。<br />支持JPG、JPEG、GIF、PNG格式的图片。<br />图片大小不能超过 2MB</span>
</div>
 <div class="avatar-upoad" style="margin-top:5px">
     <input type="text" class="text" value="" readonly="readonly" autocomplete="off" style="margin-left:0" target="hidden_frame" id="file_input_text"/>
   	 <div><input type="file"id="swfupload-btn" class="upload-btn" /></div>
     <span></span>
 </div>
 <div class="avatar-new" id="J_avatar">
     <span class="title"  style="color:#333;font-weight:bold;font-size:14px">预览新图片</span>
     <div class="cutbox">
         <img src="${rootPath }/images/backstage/default_user_pic.jpg" width="300" height="300" />
         <div class="loading">正在加载...</div>
     </div>
     <div class="avatar-preview">
         <span class="title">您上传的图片会自动生成3种尺寸，<br />请注意中小尺寸的头像是否清晰</span>
         <div class="avatar-preview-left">
             <div class="img130">
                 <img src="${rootPath }/images/backstage/default_user_pic.jpg" />
             </div>
             <span>大尺寸头像<br/>130×130像素</span>
         </div>
         <div class="avatar-preview-right">
             <div class="img48">
                 <img src="${rootPath }/images/backstage/default_user_pic.jpg" />
             </div>
             <span>中尺寸头像<br/>48×48  像素<br />(自动生成)</span>
             <div class="img32 small">
                 <img  src="${rootPath }/images/backstage/default_user_pic.jpg"  />
             </div>
             <span>小尺寸头像<br/>32×32  像素<br />(自动生成)</span>
         </div>
     </div>
     <div class="clear-fix"></div>
     <a href="#" class="saveBtn saveBtnDis" id="J_saveAvatar">保存</a><!-- 不可点击的样式为 saveBth saveBtnDis -->
     <!--<a href="#" class="saveBtn" id="J_reCut" style="float:left">裁剪</a>-->
     <a href="#" class="saveBtn saveBtnDis" id="J_cancelCut">取消裁剪</a>
     <a href="#" class="saveBtn saveBtnDis" id="J_deleteImg">删除图片</a>
 </div>
