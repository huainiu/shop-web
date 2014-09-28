<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>www-vs-tao</title>
	<style type="text/css">
	body{
		background: #72B67B;
		font-size: 12px;
	}
	div.h{
		padding: 5px 10px 3px 160px;
		line-height: 12px;
		height: 22px;
		width: 500px;
		margin: 20px auto 0;
		border: 0 none;
	}
	div.h input.t{
		background: none repeat scroll 0 0 #FFFFFF;
		border: 1px solid #DFF0FF;
		color: #000000;
		margin: 0 0 0 7px;
		padding: 0 1px;
	}
	div.h input.b{
		background: none repeat scroll 0 0 #0F4DA0;
		color: #FFFFFF;
		border: none;
	}
	body.f div.h input.b{
		font-size: 18px;
	}
	span{
		color: #FFF;
		font-size: 18px;
		font-weight: bold;
	}
	#image{
		text-align: center;
		margin: 200px auto 0;
	}
	</style>
	<script type="text/javascript">
	function search(){
		var keyword = document.getElementById("keyword").value;
		if(keyword){
			window.location.href = "www-vs-etao.jsp?keyword=" + keyword;
		}
	} 
	function searchForKeyUp(e){
		if(window.event) // IE
		{
		  	keynum = e.keyCode
		}else if(e.which) // Netscape/Firefox/Opera
		{
		   keynum = e.which;
		}
		if(keynum == 13){
			search();
		}
	}
	</script>
</head>
<body id="body" class="f">
    <div id="image">
    	<img alt="" src="http://s.b5m.com:80/images/images/logo_170_53_24.png">
    </div>
    <div class="h">
    	<span>www vs tao</span> <input type="text" class="t" name="keyword" id="keyword" onkeydown="searchForKeyUp(event)" onkeydown="searchForKeyUp(event)" onkeyup="searchForKeyUp(event)" /> <input class="b" type="button" value="Search" onclick="search()" />
    </div>
</body>
</html>
