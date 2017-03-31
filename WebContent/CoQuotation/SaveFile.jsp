<%@ page language="java" import="java.text.*,com.zhuozhengsoft.pageoffice.*" pageEncoding="gb2312"%>
<%@ include file="../js/Conn.jsp"%>
<%
String date = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
FileSaver fs=new FileSaver(request,response);
String sql2 = "select count(*),pubf_file from PubOfficeFile where pubf_name='报价单信息' and pubf_tabid="
+ session.getAttribute("cq_id") + " group by pubf_file";
ResultSet rs2 = stmt.executeQuery(sql2);
Integer cont;
String ofile;
cont = 0;
ofile = "";
while (rs2.next()) {
cont = rs2.getInt(1);
ofile = rs2.getString(2);
}

//如果存在文档，将打开暂存文档，否则打开新文档
if (cont > 0) {
fs.saveToFile(request.getSession().getServletContext().getRealPath("OfficeFile/DownLoad/CoQuotation/")+"/"+ofile);
fs.close();
} else {
fs.saveToFile(request.getSession().getServletContext()
	.getRealPath("OfficeFile/DownLoad/CoQuotation/")
	+ "/CQ" + date + ".doc");
String sql = "insert into PubOfficeFile (pubf_tabid,pubf_name,pubf_addname,pubf_addtime,pubf_file) values ("
	+ session.getAttribute("cq_id")
	+ ",'报价单信息','user',getdate(),'CQ" + date + ".xls')";
stmt.executeUpdate(sql);
}

stmt.close();
conn.close();
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>

  </body>
</html>
