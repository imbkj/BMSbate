<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同审核详细页面
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.excelwriter.*,com.zhuozhengsoft.pageoffice.tags.*,Util.UserInfo"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
<%
	boolean b =false;

	PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath() + "/poserver.do");
	
	//隐藏自定义工具栏
	
	//隐藏菜单
	poCtrl.setMenubar(false);
	//设置页面的显示标题

	poCtrl.setCaption(request.getParameter("t"));
	OpenModeType omt;
	if(UserInfo.getDepID().equals("10")){
		//添加自定义按钮
		poCtrl.addCustomToolButton("保存", "Save", 1);
		b=true;
		omt=OpenModeType.xlsNormalEdit;
	}else{
		omt=OpenModeType.xlsNormalEdit;
	}
	poCtrl.setCustomToolbar(b);
	poCtrl.setOfficeToolbars(b);

	//设置保存页面
	poCtrl.setSaveFilePage("../CoHousingFund/SaveFile.jsp");

	String ofile = "../" + request.getParameter("filename");

	// 设置文件打开后执行的js function
	poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");

	//如果存在文档，将打开暂存文档，否则打开新文档
	
	poCtrl.webOpen(ofile, omt, "szciic");

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需
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

		function AfterDocumentOpened() {
			document.getElementById("PageOfficeCtrl1").ShowRevisions = true;
		}
	</script>
	<form id="form1">
		<div style="width: auto; height: 700px;z-index:0;">
			<po:PageOfficeCtrl id="PageOfficeCtrl1">
			</po:PageOfficeCtrl>
		</div>
	</form>
</body>
</html>
