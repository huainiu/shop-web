<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>www-vs-etao</title>
<style type="text/css">
body,html{
   border: 0px;
   margin: 0px;
   background: #72B67B;
}
li{
	list-style-type: none;
	float: left;
	margin-left: 20px;
	color: #FFFFFF;
}
li a{
	color: #FFFFFF;
}
</style>
<script type="text/javascript">
function onlyWww(){
	parent.onlyWww();
}
function onlyTao(){
	parent.onlyTao();
}
function split(){
	parent.split();
}
function image(){
	parent.image();
}
function list(){
	parent.list();
}
function search(){
	var keyword = document.getElementById("keyword").value;
	if(keyword){
		parent.reload(keyword);
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
<body>
	<div>
		<ul>
		   <li><span>www-vs-etao</span></li>
		   <li><input type="text" id="keyword" value="<%=request.getParameter("keyword")%>" onkeydown="searchForKeyUp(event)" onkeydown="searchForKeyUp(event)" onkeyup="searchForKeyUp(event)"><input type="button" value="search" onclick="search()"></li>
		   <li><a href="javascript:split();">split</a></li>
		   <li><a href="javascript:onlyWww();">www only</a></li>
		   <li><a href="javascript:onlyTao();">tao only</a></li>
		   <li>view pattern： <a href="javascript:image();">grid</a></li>
		   <li><a href="javascript:list();">list</a></li>
		</ul>
	</div>
</body>
</html>