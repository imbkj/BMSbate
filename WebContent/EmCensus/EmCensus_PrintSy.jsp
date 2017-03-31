<%@ page language="java" import="com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<%@page import="com.zhuozhengsoft.pageoffice.excelwriter.*"%>
<%@page import="java.awt.Color"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
<%
	//设置PageOfficeCtrl控件的服务页面
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	poCtrl1.setServerPage(request.getContextPath() + "/poserver.do"); //此行必须
	//poCtrl1.setJsFunction_AfterDocumentOpened("adjustCell()");//自动调整行高

	//链接数据库操作开始
	//人事服务数据
	String sql = "select convert(decimal(18,0),emhj_fee) as emhj_fee,*,b.emba_spell,c.coba_company ,emba_name from EmCensus a left join (select gid,emba_spell,";
			sql=sql+" emba_name from embase ) b on a.gid=b.gid left join (select cid,coba_company,coba_shortname,";
			sql=sql+" coba_client from cobase) c on a.cid=c.cid where 1=1  and emhj_id="+request.getParameter("emhj_id");
	ResultSet rs = stmt.executeQuery(sql);
	String coba_shortname="";
	String emhj_idcard="";
	String emhj_name="";
	String emhj_no="";
	String coba_client="";
	Date ehbc_outime=null;
	String ehbc_case="";
	String ehbc_fee="";
	String backinfo="";
	while (rs.next()) {
		coba_shortname=rs.getString("coba_shortname");
		emhj_idcard="'"+rs.getString("emhj_idcard");
		emhj_name=rs.getString("emhj_name");
		emhj_no=rs.getString("emhj_no");
		coba_client=rs.getString("coba_client");
		ehbc_outime=rs.getDate("emhj_botime");
		ehbc_case=rs.getString("emhj_case");
		ehbc_fee=rs.getString("emhj_fee");
		backinfo="已收到深圳中智公司退还的押金"+rs.getString("emhj_fee")+"元。   还卡人签名：          还卡日期：";
	}
	//定义Workbook对象
	Workbook workBook = new Workbook();
	//定义Sheet对象，"Sheet1"是打开的Excel表单的名称
	Sheet sheet = workBook.openSheet("Sheet1");
	//隐藏Office工具条
	poCtrl1.setOfficeToolbars(false);
	//隐藏菜单
	poCtrl1.setMenubar(false);
	Cell cellb7 = sheet.openCell("B7");
	cellb7.setValue(coba_shortname+"");
	Cell celld7 = sheet.openCell("D7");
	celld7.setValue(emhj_name+"");
	Cell cellf7 = sheet.openCell("F7");
	if(emhj_idcard!=null)
	{
		cellf7.setValue(emhj_idcard+"");
	}
	else
	{
		cellf7.setValue("");
	}
	Cell cellh7 = sheet.openCell("H7");
	if(emhj_no!=null)
	{
		cellh7.setValue(emhj_no+"");
	}
	else
	{
		cellh7.setValue("");
	}
	Cell cellb8 = sheet.openCell("B8");
	cellb8.setValue(coba_client+"");
	Cell celld8 = sheet.openCell("D8");
	celld8.setValue(ehbc_outime+"");
	Cell cellf8 = sheet.openCell("F8");
	cellf8.setValue(ehbc_case+"");
	Cell cellh8 = sheet.openCell("H8");
	cellh8.setValue(ehbc_fee+"");
	
	Cell cella12 = sheet.openCell("A12");
	cella12.setValue(backinfo+""); 
	

	poCtrl1.setWriter(workBook);

	poCtrl1.setCaption("打印借卡条");
	//添加自定义按钮
	poCtrl1.addCustomToolButton("打印","print",6);
	poCtrl1.addCustomToolButton("另存为","Save",3);
	
	//设置保存页面
	poCtrl1.setSaveFilePage("../EmCensus/savefile.jsp");
	//隐藏菜单栏
	poCtrl1.setMenubar(false);
	//隐藏工具栏
	//poCtrl1.setCustomToolbar(false);
	//打开Word文件
	//判断文档是否暂存
	
	//如果存在文档，将打开暂存文档，否则打开新文档
	
		poCtrl1.webOpen(
				"../OfficeFile/Templet/EmHj/Hkjt_C.xls",
				OpenModeType.xlsNormalEdit, "szciic");
	

	poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
	session.setAttribute("cq_id", null);
	session.setAttribute("cq_id", request.getParameter("coco_id"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>报价单详情</title>
<script type="text/javascript">
function print() {
	document.getElementById("PageOfficeCtrl1").PrintPreview();
}
function Save() {
	document.getElementById("PageOfficeCtrl1").WebSave();
	document.getElementById("PageOfficeCtrl1").ShowDialog(3);
}
</script>
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
	<div style="width: auto; height: 650px;">
		<po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
	</div>
</body>
</html>
