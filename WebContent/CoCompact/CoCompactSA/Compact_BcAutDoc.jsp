<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同制作详细页面
-->
<%@page import="Controller.systemWindowController"%>
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../../js/Conn.jsp"%>
<%
	System.out.print(request.getParameter("coco_tapr_id"));
	PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath() + "/poserver.do");
	//隐藏Office工具条
	poCtrl.setOfficeToolbars(true);
	//隐藏自定义工具栏
	//poCtrl.setCustomToolbar(false);
	//隐藏菜单
	poCtrl.setMenubar(false);
	//设置页面的显示标题
	poCtrl.setCaption("公司合同");
	//添加自定义按钮
	poCtrl.addCustomToolButton("暂存", "Save", 1);
	poCtrl.addCustomToolButton("审核", "Send", 2);
	poCtrl.addCustomToolButton("退回", "Out", 3);
	//设置保存页面
	poCtrl.setSaveFilePage("../CoCompact/CoCompactSA/SaveFile.jsp");

	//链接数据库操作开始
	String sql = "select coco_compactid,isnull(cola_company,''),year(getdate()) year,month(getdate()) month,day(getdate()) day from cocompact a left join CoLatencyClient b on b.cola_id=a.coco_cola_id where coco_id="
			+ request.getParameter("coco_id");
	ResultSet rs = stmt.executeQuery(sql);

	//数据库结束

	//定义WordDocument对象
	WordDocument doc = new WordDocument();

	//定义DataTag对象，向区域赋值
	while (rs.next()) {
		DataTag deptTag = doc.openDataTag("{compact_id}");
		deptTag.setValue(rs.getString(1));

		DataTag userTag = doc.openDataTag("{company}");
		userTag.setValue(rs.getString(2));

		DataTag yearTag = doc.openDataTag("{year}");
		yearTag.setValue(rs.getString(3));

		DataTag monthTag = doc.openDataTag("{month}");
		monthTag.setValue(rs.getString(4));

		DataTag dayTag = doc.openDataTag("{day}");
		dayTag.setValue(rs.getString(5));
	}

	poCtrl.setWriter(doc);

	//判断文档是否暂存
	String sql2 = "select count(*),puof_url from PubOffice where puof_pute_id=1 and puof_tid="
			+ request.getParameter("coco_id") + " group by puof_url";
	System.out.print(sql2);
	ResultSet rs2 = stmt.executeQuery(sql2);
	Integer cont;
	String ofile;
	cont = 0;
	ofile = "";
	while (rs2.next()) {
		cont = rs2.getInt(1);
		ofile = rs2.getString(2);
	}

	//判断文档是否暂存
	String sql3 = "select * from CoCompactSA a left join coofferlistchange b on b.colc_ccsa_id=a.ccsa_id where ccsa_id="
			+ request.getParameter("coco_id");
	System.out.println(sql3);
	ResultSet rs3 = stmt.executeQuery(sql3);
	String file_con = "";
	String file_con1 = "";
	while (rs3.next()) {
		file_con = rs3.getString("colc_name");
		file_con1 = rs3.getString("colc_content");
	}

	//如果存在文档，将打开暂存文档，否则打开新文档

	poCtrl.webOpen("../" + ofile, OpenModeType.docNormalEdit, "szciic");

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	rs.close();
	stmt.close();
	conn.close();
	session.setAttribute("compact_id", null);
	session.setAttribute("compact_id", request.getParameter("coco_id"));

	if (file_con!=null||file_con!="") {
		out.print(file_con + "：" + file_con1);
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>合同信息</title>
</head>
<body>
	<script type="text/javascript">
		function Save() {
			document.getElementById("PageOfficeCtrl1").WebSave();
		}
		function Send() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			window.location = "../CoCompact/CoCompactSA/Compact_BcSendOk.jsp?coco_id=<%=request.getParameter("coco_id")%>&coco_tapr_id=<%=request.getParameter("coco_tapr_id")%>&user=<%=request.getParameter("user")%>";
		}
		function Out() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			window.location = "../CoCompact/CoCompactSA/Compact_Out.jsp?coco_id=<%=request.getParameter("coco_id")%>&coco_tapr_id=<%=request.getParameter("coco_tapr_id")%>&user=<%=request.getParameter("user")%>&sub=0";
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
