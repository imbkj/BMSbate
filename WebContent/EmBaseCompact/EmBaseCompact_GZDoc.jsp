<!--
	创建人：张志强
	创建时间：2013-12-5
	用途：劳动合同打印详细页面
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
<%
	System.out.println(request.getParameter("ebco_id"));
	PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath() + "/poserver.do");
	//隐藏Office工具条
	poCtrl.setOfficeToolbars(false);
	//隐藏自定义工具栏
	//poCtrl.setCustomToolbar(false);
	//隐藏菜单
	poCtrl.setMenubar(false);
	//设置页面的显示标题
	poCtrl.setCaption("雇员合同");
	//添加自定义按钮
	poCtrl.addCustomToolButton("退回", "Out", 1);
	poCtrl.addCustomToolButton("已盖章", "Send", 2);
	//设置保存页面
	poCtrl.setSaveFilePage("../EmBaseCompact/SaveFile.jsp");

	//定义WordDocument对象
	WordDocument doc = new WordDocument();

	poCtrl.setWriter(doc);

	//判断文档是否暂存
	String sql2 = "select count(*),puof_url from PubOffice where puof_pute_id=3 and puof_tid="
			+ request.getParameter("ebcc_id") + " group by puof_url";
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
		poCtrl.webOpen("../OfficeFile/DownLoad/EmBaseCompact/" + ofile,
				OpenModeType.docNormalEdit, "szciic");
	} else {
		poCtrl.webOpen(
				"../OfficeFile/Templet/EmBaseCompact/EmBaseCompact.doc",
				OpenModeType.docNormalEdit, "szciic");
	}

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	stmt.close();
	conn.close();
	session.setAttribute("ebcc_id", null);
	session.setAttribute("ebcc_id", request.getParameter("ebcc_id"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>合同信息</title>
</head>
<body>
	<script type="text/javascript">
		function Send() {
			window.location = "../EmBaseCompact/EmBaseCompact_GZDocOk.jsp?ebcc_id=<%=request.getParameter("ebcc_id")%>&user=<%=request.getParameter("user")%>&ebcc_tapr_id=<%=request.getParameter("ebcc_tapr_id")%>";
		}
		function Out() {
			window.location = "../EmBaseCompact/EmBaseCompact_Out.jsp?ebcc_id=<%=request.getParameter("ebcc_id")%>&user=<%=request.getParameter("user")%>&ebcc_tapr_id=<%=request.getParameter("ebcc_tapr_id")%>&sub=0";
		}
	</script>
	<form id="form1">
		<div style="width: auto; height: 700px;">
			<po:PageOfficeCtrl id="PageOfficeCtrl1">
			</po:PageOfficeCtrl>
		</div>
	</form>
</body>
</html>
