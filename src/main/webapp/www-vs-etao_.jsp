 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>www-vs-tao</title>
</head>
<frameset rows="40,*" framespacing="0"  frameborder="0" border="0">
  <frame src="head.jsp?keyword=<%=request.getParameter("keyword")%>" name="topFrame" scrolling="NO" noresize >
  <frameset cols="50%,*" id="splitcol" framespacing="0" frameborder="0" border="0">
      <frame src="http://s.b5m.com/search/s/___image________________<%=request.getParameter("keyword")%>.html" name="left" id="left" scrolling="auto" noresize id="left">
      <frame src="http://s.etao.com/search?spm=1002.8.0.329.z3IvFu&usearch=yes&style=grid&s=0&&q=<%=request.getParameter("keyword")%>" name="right" id="right" scrolling="auto" noresize id="right">
  </frameset>
</frameset>
</html>