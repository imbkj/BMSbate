<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同制作保存处理页面
-->
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,java.text.*,Util.UserInfo"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>
<%
	String date = new SimpleDateFormat("yyyyMMddHHmmss")
			.format(Calendar.getInstance().getTime());

	String username1 = UserInfo.getUsername();

	String sql2 = "select count(*),puof_url from PubOffice where puof_pute_id=3 and puof_tid="
			+ session.getAttribute("ebcc_id") + " group by puof_url";
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
				.getRealPath("OfficeFile/DownLoad/EmBaseCompact/")
				+ "/" + ofile);
	} else {
		fs.saveToFile(request.getSession().getServletContext()
				.getRealPath("OfficeFile/DownLoad/EmBaseCompact/")
				+ "/EmBaseCompact" + date + ".doc");
		String sql = "insert into PubOffice (puof_tid,puof_pute_id,puof_addname,puof_addtime,puof_url,puof_type,puof_ver) values ("
				+ session.getAttribute("ebcc_id")
				+ ",3,'"+username1+"',getdate(),'EmBaseCompact" + date + ".doc','1','"+session.getAttribute("ver_name")+"')";

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
