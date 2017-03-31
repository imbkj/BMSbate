<!--
	创建人：林少斌
	创建时间：2013-11-22
	用途：公司合同补充协议显示
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../../js/Conn.jsp"%>
<%
	PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath() + "/poserver.do");
	//隐藏Office工具条
	poCtrl.setOfficeToolbars(false);
	//隐藏自定义工具栏
	poCtrl.setCustomToolbar(false);
	//隐藏菜单
	poCtrl.setMenubar(false);
	//设置页面的显示标题
	poCtrl.setCaption("公司合同补充协议");

	//链接数据库操作开始

	//判断文档是否暂存
	String sql = "SELECT TOP 1 puof_url FROM PubOffice WHERE puof_pute_id=1 and puof_tid="
			+ request.getParameter("ccsa_id") + " ORDER BY puof_id DESC";
	ResultSet rs = stmt.executeQuery(sql);
	String ofile;
	ofile = "";
	while (rs.next()) {
		ofile = rs.getString(1);
	}

	//如果存在文档，将打开暂存文档，否则打开新文档
		poCtrl.webOpen("../" + ofile,
				OpenModeType.docNormalEdit, "szciic");

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	rs.close();
	stmt.close();
	conn.close();
	session.setAttribute("compact_id",null);
	session.setAttribute("compact_id",request.getParameter("ccsa_id"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>公司合同补充协议信息</title>
</head>
<body>
	<form id="form1">
		<div style="width: auto; height: 700px;">
			<po:PageOfficeCtrl id="PageOfficeCtrl1">
			</po:PageOfficeCtrl>
		</div>
	</form>
</body>
</html>