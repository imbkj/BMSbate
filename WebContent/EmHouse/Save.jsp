<!--
	创建人：彭耀
	创建时间：2014-1-16
	用途：公积金清册保存页面
-->
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,java.text.*,Util.UserInfo"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>
<%
	FileSaver fs = new FileSaver(request, response);

	// 保存当前文档到服务器文件夹。
	fs.saveToFile(request.getSession().getServletContext()
			.getRealPath("OfficeFile/DownLoad/EmHouse/")
			+ "/" + session.getAttribute("exfileName"));
	System.out.println(request.getSession().getServletContext()
			.getRealPath("OfficeFile/DownLoad/EmHouse/")
			+ "/" + session.getAttribute("exfileName"));
	// 文档保存最后需调用 close 方法。
	fs.close();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html>