<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同制作保存处理页面
-->
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.excelwriter.*,java.text.*,Util.UserInfo"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>
<%
	FileSaver fs = new FileSaver(request, response);

//如果存在文档，将打开暂存文档，否则打开新文档

	fs.saveToFile(request.getSession().getServletContext().getRealPath("OfficeFile/UpLoad/CoHousingFund/workbook/index.xls"));


	//数据库结束
	fs.close();
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'SaveFile.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>
</body>
</html>
