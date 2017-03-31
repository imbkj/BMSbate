<%@ page language="java" import="com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<%@page import="com.zhuozhengsoft.pageoffice.excelwriter.*"%>
<%@page import="java.awt.Color"%>
<%@page import="java.text.*"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
<%
	//设置PageOfficeCtrl控件的服务页面
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	poCtrl1.setServerPage(request.getContextPath() + "/poserver.do"); //此行必须
	//poCtrl1.setJsFunction_AfterDocumentOpened("adjustCell()");//自动调整行高

	//链接数据库操作开始
	//人事服务数据
	String sql = "select LEN(coli_content),* from CoOffer a left join CoOfferList b on b.coli_coof_id=a.coof_id where coli_pclass='人事服务' and coof_id="+request.getParameter("coof_id");
	ResultSet rs = stmt.executeQuery(sql);
	Integer cont;
	Integer i;
	String ofile;
	Double co_fee;
	cont = 0;
	i = 0;
	ofile = "";
	co_fee = 0.00;
	while (rs.next()) {
		i = i + 1;
		cont = cont + rs.getInt(1);
		ofile = ofile + i + "：" + rs.getString("coli_content");
		co_fee = co_fee + rs.getFloat("coli_fee");
		//System.out.println(co_fee);
	}

	//其它福利数据
	String sql2 = "select LEN(coli_content),* from CoOffer a left join CoOfferList b on b.coli_coof_id=a.coof_id where coli_pclass<>'人事服务' and coof_id="+request.getParameter("coof_id");
	ResultSet rs2 = stmt.executeQuery(sql2);
	Integer cont2;
	Integer i2;
	String ofile2;
	Double co_fee2;
	cont2 = 0;
	i2 = 0;
	ofile2 = "";
	co_fee2 = 0.00;
	while (rs2.next()) {
		i2 = i2 + 1;
		cont2 = cont2 + rs2.getInt(1);
		ofile2 = ofile2 + i2 + "：" + rs2.getString("coli_content");
		co_fee2 = co_fee2 + rs2.getFloat("coli_fee");
		//System.out.println(co_fee);
	}
	//数据库结束

	//定义Workbook对象
	Workbook workBook = new Workbook();
	//定义Sheet对象，"Sheet1"是打开的Excel表单的名称
	Sheet sheet = workBook.openSheet("Sheet1");

	//定义table对象，设置table对象的设置范围
	Table table = sheet.openTable("B22:I23");

	//sheet.openTable("B22:I23").RowHeight = 30;
	sheet.openTable("B23:I23").setRowHeight((cont / 30 + 1) * 20);
	//定义Cell对象
	Cell cellB4 = sheet.openCell("B22");
	//给单元格赋值
	cellB4.setValue("1");

	Cell cellC4 = sheet.openCell("C22");
	cellC4.setValue("人事代理");

	Cell cellD4 = sheet.openCell("D22");
	cellD4.setValue(ofile);

	Cell cellE4 = sheet.openCell("I22");
	cellE4.setValue(String.format("%.2f", Double.parseDouble(co_fee.toString())));

	//sheet.openTable("B22:I23").RowHeight = 30;
	sheet.openTable("B23:I23").setRowHeight((cont2 / 30 + 1) * 20);
	//定义Cell对象
	Cell cellB5 = sheet.openCell("B23");
	//给单元格赋值
	cellB5.setValue("2");

	Cell cellC5 = sheet.openCell("C23");
	cellC5.setValue("其它福利");

	Cell cellD5 = sheet.openCell("D23");
	cellD5.setValue(ofile2);

	Cell cellE5 = sheet.openCell("I23");
	cellE5.setValue(String.format("%.2f", Double.parseDouble(co_fee2.toString())));

	//总计
	Double zco_fee;
	zco_fee = co_fee + co_fee2;
	Cell cellE6 = sheet.openCell("I25");
	cellE6.setValue(String.format("%.2f", Double.parseDouble(zco_fee.toString())));

	poCtrl1.setWriter(workBook);

	poCtrl1.setCaption("报价单详情");
	//添加自定义按钮
	poCtrl1.addCustomToolButton("保存","Save",1);
	//设置保存页面
	poCtrl1.setSaveFilePage("SaveFile.jsp");
	//隐藏菜单栏
	poCtrl1.setMenubar(false);
	//隐藏工具栏
	//poCtrl1.setCustomToolbar(false);
	//打开Word文件
	//判断文档是否暂存
	String sql3 = "select count(*),pubf_file from PubOfficeFile where pubf_name='报价单信息' and pubf_tabid="
			+ request.getParameter("coof_id") + " group by pubf_file";
	ResultSet rs3 = stmt.executeQuery(sql3);
	Integer cont3;
	String ofile3;
	cont3 = 0;
	ofile3 = "";
	while (rs3.next()) {
		cont3 = rs3.getInt(1);
		ofile3 = rs3.getString(2);
	}

	//如果存在文档，将打开暂存文档，否则打开新文档
	if (cont3 > 0) {
		poCtrl1.webOpen("../OfficeFile/Templet/CoQuotation/" + ofile,
				OpenModeType.xlsNormalEdit, "szciic");
	} else {
		poCtrl1.webOpen(
				"../OfficeFile/Templet/CoQuotation/af_quotation.xls",
				OpenModeType.xlsNormalEdit, "szciic");
	}

	poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
	session.setAttribute("cq_id", null);
	session.setAttribute("cq_id", request.getParameter("coco_id"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>报价单详情</title>
<script type="text/javascript">
	function Save() {
		document.getElementById("PageOfficeCtrl1").WebSave();
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
