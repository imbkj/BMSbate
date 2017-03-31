<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同审核详细页面
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
<%
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
	poCtrl.addCustomToolButton("退回", "Out", 0);
	poCtrl.addCustomToolButton("暂存", "Save", 1);
	poCtrl.addCustomToolButton("审核", "Send", 2);
	//设置保存页面
	poCtrl.setSaveFilePage("../CoCompact/SaveFile.jsp");

	//判断文档是否暂存
	String sql2 = "select count(*),puof_url from PubOffice where puof_pute_id=2 and puof_tid="
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
	
	// 设置文件打开后执行的js function
	poCtrl.setJsFunction_AfterDocumentOpened( "AfterDocumentOpened()");

	//如果存在文档，将打开暂存文档，否则打开新文档
	poCtrl.webOpen("../OfficeFile/DownLoad/CoCompact/" + ofile,
	OpenModeType.docAdmin, "szciic");
	

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	stmt.close();
	conn.close();
	session.setAttribute("compact_id",null);
	session.setAttribute("compact_id",request.getParameter("coco_id"));
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
		function Out() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			
			window.location = "../CoCompact/Compact_Out.jsp?coco_id=<%=request.getParameter("coco_id")%>&coco_tapr_id=<%=request.getParameter("coco_tapr_id")%>&user=<%=request.getParameter("user")%>&sub=0&way=<%=request.getParameter("way")%>";
		}
		function Send() {
			if(<%=request.getParameter("emco_class")%>==5){
			alert('请选择合同类型');
			}else{
				
			document.getElementById("PageOfficeCtrl1").WebSave();
			window.location = "../CoCompact/Compact_AutOk.jsp?coco_id=<%=request.getParameter("coco_id")%>&aut_st=2&coco_tapr_id=<%=request.getParameter("coco_tapr_id")%>&user=<%=request.getParameter("user")%>&coco_class=<%=request.getParameter("emco_class")%>&way=<%=request.getParameter("way")%>";
		}
		}

		 function AfterDocumentOpened() {
	        	document.getElementById("PageOfficeCtrl1").ShowRevisions = true;
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
