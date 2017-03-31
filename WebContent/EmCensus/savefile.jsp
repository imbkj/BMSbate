<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,
	java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>

<%
	// 格式化日期
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	// 格式化日期(产生文件名)
	String newfilename = sdf.format(Calendar.getInstance().getTime()) + ".xls";
	FileSaver fs = new FileSaver(request, response);
	//如果存在文档，将打开暂存文档，否则打开新文档
	fs.saveToFile(request.getSession().getServletContext()
		.getRealPath("EmCensus/file/download")
		+ "/" + newfilename);
	fs.close();
	//cb.cardprint(ehbc_id,tperid,user);
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
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
<script type="text/javascript">
alert('保存成功！');
//window.location.href='../CoCompact/Compact_AutSelect.zul';
</script>
</body>
</html>