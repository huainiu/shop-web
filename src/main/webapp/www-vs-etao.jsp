 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>www-vs-tao</title>
<script type="text/javascript" src="http://staticcdn.b5m.com/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
<style type="text/css">
	*{margin:0;padding:0}
	img,iframe{border:0}
	html,body{height:100%}
	
	#header,#header iframe{width:100%;height:60px;}

	#left,#right{float:left;width:50%;overflow:auto}
	#leftFrame,#rightFrame{width:1500px;height:5700px}
</style>
</head>
     
<body>
	<div id="header">
		<iframe src="head.jsp?keyword=<%=request.getParameter("keyword")%>"></iframe>
	</div>
	<div id="wrap">
		<div id="left">
			<iframe src="${pageContext.request.contextPath}/<%=request.getParameter("keyword")%>.html" id="leftFrame"></iframe>
		</div>
		<div id="right">
			<iframe src="http://s.etao.com/search?spm=1002.8.0.329.z3IvFu&usearch=yes&style=grid&s=0&q=<%=request.getParameter("keyword")%>" id="rightFrame"></iframe>
		</div>
	</div>
	<script type="text/javascript">
    function onlyWww(){
    	$("#left").css("width", "100%");
    	$("#right").css("width", "0px");
    	$("iframe").css("width", "100%");
    }
    function onlyTao(){
    	$("#left").css("width", "0px");
    	$("#right").css("width", "100%");
    	$("iframe").css("width", "100%");
    }
    function split(){
    	$("#left").css("width", "50%");
    	$("#right").css("width", "50%");
    	$("iframe").css("width", "100%");
    }
    function reload(keyword){
   		window.location.href = '${pageContext.request.contextPath}/www-vs-etao.jsp?keyword='+keyword;
    }
    function image(){
    	window.location.href = '${pageContext.request.contextPath}/www-vs-etao.jsp?keyword=<%=request.getParameter("keyword")%>';
    }
    function list(){
    	window.location.href = '${pageContext.request.contextPath}/www-vs-etao.jsp?keyword=<%=request.getParameter("keyword")%>';
    }
	function clientHeight(id){
		if(!id) return false;
		var wrap= document.getElementById(id);
		var left = document.getElementById('left');
		var right = document.getElementById('right');

		left.scrollTop = right.scrollTop = 0;
		left.scrollLeft = 180;
		right.scrollLeft = 380;

		left.onscroll = function(){
			right.scrollTop = left.scrollTop;
		};

		right.onscroll = function(){
			left.scrollTop = right.scrollTop;
		};

		wrap.style.height = left.style.height = right.style.height = document.documentElement.clientHeight-60 + 'px';

		window.onresize = function()
		{
			clientHeight(id);
		};
	}
	clientHeight('wrap');
	</script>
</body>
</html>