<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同制作保存处理页面
-->
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../js/Conn.jsp"%>
<%
	String date = new SimpleDateFormat("yyyyMMddHHmmss")
			.format(Calendar.getInstance().getTime());

	String sql2 = "select count(*),pubf_file from PubOfficeFile where pubf_name='补充协议' and pubf_tabid="
			+ session.getAttribute("compact_id") + " group by pubf_file";
	ResultSet rs2 = stmt.executeQuery(sql2);
	Integer cont;
	String ofile;
	cont = 0;
	ofile = "";
	while (rs2.next()) {
		cont = rs2.getInt(1);
		ofile = rs2.getString(2);
	}
	FileSaver fs = new FileSaver(request, response);
	//如果存在文档，将打开暂存文档，否则打开新文档
	if (cont > 0) {
		fs.saveToFile(request.getSession().getServletContext()
				.getRealPath("OfficeFile/DownLoad/CoCompact/")
				+ "/" + ofile);
	} else {
		fs.saveToFile(request.getSession().getServletContext()
				.getRealPath("OfficeFile/DownLoad/CoCompact/")
				+ "/AF_FS2" + date + ".doc");
		String sql = "insert into PubOfficeFile (pubf_tabid,pubf_name,pubf_addname,pubf_addtime,pubf_file) values ("
				+ session.getAttribute("compact_id")
				+ ",'补充协议','user',getdate(),'BC" + date + ".doc')";
		stmt.executeUpdate(sql);
	}

	//链接数据库操作开始
	
	stmt.close();
	conn.close();
	fs.close();
	//数据库结束
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
</body>
</html>
