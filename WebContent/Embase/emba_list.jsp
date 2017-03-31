<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="Model.EmbaseModel"%>
<%@page import="Controller.Embase.Embase_editListController"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/skin.css" />
<title>JSP测试</title>
</head>
<%
	String s= request.getParameter("list").toString();
	Embase_editListController emco=new Embase_editListController();
	List<EmbaseModel> list=emco.getEmbaselist();
%>
<body>
	<table width="100%" class="cont tr_color">
		<tr>
			<th>公司编号</th>
			<th>公司名称</th>
			<th>员工编号</th>
			<th>姓名</th>
			<th>客服</th>
			<th>操作</th>
		</tr>
		<%
			for(EmbaseModel m:list)
			{
		%>
		<tr align="center" class="d">
			<td><%=m.getCid() %></td>
			<td><%=m.getCoba_name() %></td>
			<td><%=m.getGid() %></td>
			<td><%=m.getEmba_name() %></td>
			<td><%=m.getCoba_client() %></td>
			<td>操作</td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>