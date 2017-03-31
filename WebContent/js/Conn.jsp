<%@ page language="java" import="java.util.*,java.sql.*,Util.ReadProperties;"
	pageEncoding="utf-8"%>
<%
	
	ReadProperties rProp = new ReadProperties("/Conn/ciic.properties");

	Class.forName(rProp.getString("jdbc.driverClassName"));
	String url = rProp.getString("jdbc.url");
	Connection conn = DriverManager.getConnection(url,
		rProp.getString("jdbc.username"),
		rProp.getString("jdbc.password"));

	Statement stmt = conn
			.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
%>