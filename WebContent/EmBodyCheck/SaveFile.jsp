<!--
	创建人：彭耀
	创建时间：2014-3-26
	用途：供应商合同审核保存处理页面
-->
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>
<%
	String date = new SimpleDateFormat("yyyyMMddHHmmss")
			.format(Calendar.getInstance().getTime());

	String sql2 = "select count(*),puof_url from PubOffice where puof_pute_id=8 and puof_tid="
			+ session.getAttribute("EAcompact_id") + " group by puof_url";
	ResultSet rs2 = stmt.executeQuery(sql2);
	Integer cont;
	String ofile;
	cont = 0;
	ofile = "";
	while (rs2.next()) {
		cont = rs2.getInt(1);
		ofile = rs2.getString(2);
	}
	
	String user = (String)session.getAttribute("EAuser");
	FileSaver fs = new FileSaver(request, response);
	//如果存在文档，将打开暂存文档，否则打开新文档
	if(ofile.equals("")){
		ofile="gys"+date+".doc";
		String sql = "insert into PubOffice (puof_pute_id,puof_tid,puof_url,puof_state,puof_addname,puof_addtime) values (8,"
				+ session.getAttribute("EAcompact_id")
				+ ",'gys"
				+ date
				+ ".doc',1,'"
				+ user
				+ "',getdate())";
		stmt.executeUpdate(sql);
		stmt.close();
	}

	fs.saveToFile(request.getSession().getServletContext()
			.getRealPath("OfficeFile/DownLoad/EmBenefit/")
			+ "/" + ofile);
	

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
