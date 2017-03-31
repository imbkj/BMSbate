
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="org.zkoss.poi.hwpf.usermodel.Bookmark"%>
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,com.zhuozhengsoft.pageoffice.excelwriter.*,Model.EmSalaryDataModel,org.zkoss.zk.ui.Executions,java.sql.ResultSet,java.sql.PreparedStatement"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
<%
	//从session获得到data对象
	EmSalaryDataModel m = (EmSalaryDataModel) Executions.getCurrent()
			.getDesktop().getSession().getAttribute("m");
	System.out.println("数量=" + m.getItemList().size());
	WordDocument doc = new WordDocument();

	DataRegion title = doc.createDataRegion("Para0", 
			DataRegionInsertType.After, "[home]");
	title.setValue(m.getEsda_ba_name() + m.getOwnmonth() + "月份的工资单");
	title.getFont().setBold(true);
	title.getFont().setSize(16);
	title.getFont().setName("黑体");
	title.getFont().setItalic(false);
	DataRegion d = null;
	ParagraphFormat dPara = null;

	//创建可编辑域d 
	d = doc.createDataRegion("Para1", DataRegionInsertType.After,
			"Para0");

	int rowcount = 0;
	if (m.getItemList().size() % 2 == 0) {
		rowcount = m.getItemList().size() / 2;
	} else {
		rowcount = (m.getItemList().size() + 1) / 2;
	}

	//创建表格 row,col,大小
	com.zhuozhengsoft.pageoffice.wordwriter.Table tb = d.createTable(m
			.getItemList().size() / 2, 4,
			WdAutoFitBehavior.wdAutoFitFixed);
	//设置行的高度
	tb.setRowsHeight(24);

	boolean flag = false;
	//表格赋值开始
	for (int i = 1; i < m.getItemList().size(); i++) {
		if (i % 2 == 0) {
			continue;
		}
		tb.openCellRC((i + 1) / 2, 1).setValue(
				m.getItemList().get(i - 1).getCsii_item_name());
		tb.openCellRC((i + 1) / 2, 1).setValue(
				m.getItemList().get(i - 1).getAmount() + "");
		tb.openCellRC((i + 1) / 2, 3).setValue(
				m.getItemList().get(i).getCsii_item_name());
		tb.openCellRC((i + 1) / 2, 4).setValue(
				m.getItemList().get(i).getAmount() + "");
	}
	System.out.println("数量=" + m.getItemList().size());
	if (m.getItemList().size() % 2 != 0) {
		tb.openCellRC(m.getItemList().size() + 1 / 2, 1).setValue(
				m.getItemList().get(m.getItemList().size() - 1)
						.getCsii_item_name());
		tb.openCellRC(m.getItemList().size() + 1 / 2, 2).setValue(
				m.getItemList().get(m.getItemList().size() - 1)
						.getAmount()
						+ "");
	}

	//赋值完毕

	//创建ParagraphFormat对象
	dPara = d.getParagraphFormat();
	//设置段落的行间距、对齐方式、首行缩进

	dPara.setLineSpacingRule(WdLineSpacing.wdLineSpace1pt5);
	dPara.setAlignment(WdParagraphAlignment.wdAlignParagraphLeft);
	dPara.setFirstLineIndent(21);

	PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath() + "/poserver.do");
	System.out.println(request.getContextPath());
	//隐藏Office工具条
	poCtrl.setOfficeToolbars(true);
	//隐藏自定义工具栏
	poCtrl.setCustomToolbar(false);
	//隐藏菜单
	poCtrl.setMenubar(false);
	
	
	poCtrl.setWriter(doc);
	poCtrl.webOpen("../OfficeFile/Templet/SalaryPaper/"
			+ "template.doc", OpenModeType.docNormalEdit, "szciic");

	poCtrl.setTagId("PageOfficeCtrl1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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