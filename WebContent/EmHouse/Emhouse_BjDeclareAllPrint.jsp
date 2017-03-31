<%@ page language="java" import="com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<%@page import="com.zhuozhengsoft.pageoffice.excelwriter.*"%>
<%@page import="java.awt.Color"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Date"%>
<%@page import="Model.EmHouseBJModel.*"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
<%
	//设置PageOfficeCtrl控件的服务页面
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	poCtrl1.setServerPage(request.getContextPath() + "/poserver.do"); //此行必须
	//poCtrl1.setJsFunction_AfterDocumentOpened("adjustCell()");//自动调整行高
	//定义Workbook对象
	Workbook workBook = new Workbook();
	//定义Sheet对象，"Sheet1"是打开的Excel表单的名称
	Sheet sheet = workBook.openSheet("Sheet1");
	String idstr= request.getParameter("idstr");
	String sql = "select  convert(varchar(10),emhb_addtime,120) as emhb_addtime,"
			+ "convert(varchar(10),emhb_declaretime,120) as emhb_declaretime,"
			+ " emhb_id,cid,gid,emhb_name,emhb_company,emhb_companyid,ownmonth,emhb_feemonth,emhb_startmonth,emhb_stopmonth,"
			+ "emhb_houseid,emhb_idcard,emhb_radix,emhb_reason,convert(decimal(18,2),emhb_total) as emhb_total,"
			+ "emhb_cpp,emhb_opp,case emhb_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' "
			+ "when 3 then '退回' else '待确定' end states,emhb_backreason,"
			+ "emhb_addname,emhb_declarename,emhb_ifdeclare,emhb_single,"
			+ "emhb_flag,emhb_flagname,emhb_excelfile,emhb_remark "
			+ " from EmHouseBJ where 1=1 and emhb_id in("+idstr+")";
	ResultSet rs = stmt.executeQuery(sql);
	String companyid="";
	String company="";
	int i=5;
	int y=0;
	double fee=0;
	int j=0;
	while (rs.next()) {
		y=y+1;
		sheet.openCell("A"+i).setValue(y+"");
		if(rs.getString("emhb_name")!=null)
		{
			sheet.openCell("C"+i).setValue(rs.getString("emhb_name")+"");
		}
		if(j==0)
		{
			if(rs.getString("emhb_company")!=null&&rs.getString("emhb_companyid")!=null)
			{
				j=1;
				sheet.openCell("B3").setValue(rs.getString("emhb_company")+"");
				sheet.openCell("B2").setValue(rs.getString("emhb_companyid")+"");	
			}
		}
		if(rs.getString("emhb_startmonth")!=null)
		{
			sheet.openCell("D"+i).setValue(rs.getString("emhb_startmonth")+"");
		}
		if(rs.getString("emhb_stopmonth")!=null)
		{
			sheet.openCell("E"+i).setValue(rs.getString("emhb_stopmonth")+"");
		}
		if(rs.getString("emhb_houseid")!=null)
		{
			sheet.openCell("B"+i).setValue(rs.getString("emhb_houseid")+"");
		}
		if(rs.getString("emhb_total")!=null&&!rs.getString("emhb_total").equals("")&&rs.getString("emhb_total")!="")
		{
			sheet.openCell("F"+i).setValue(rs.getString("emhb_total")+"");
			fee=fee+rs.getDouble("emhb_total");
		}
		if(rs.getString("emhb_reason")!=null)
		{
			sheet.openCell("G"+i).setValue(rs.getString("emhb_reason")+"");
		}
		i=i+1;
		
	}
	//人数合计
	//sheet.openCell("C"+i).setValue(y+"人");
	sheet.openCell("F"+i).setValue(fee+"");
	//隐藏Office工具条
	poCtrl1.setOfficeToolbars(false);
	//隐藏菜单
	poCtrl1.setMenubar(false);

	poCtrl1.setWriter(workBook);

	poCtrl1.setCaption("打印借卡条");
	//添加自定义按钮
	poCtrl1.addCustomToolButton("打印","print",6);
	poCtrl1.addCustomToolButton("另存为","Save",3);
	
	//设置保存页面
	poCtrl1.setSaveFilePage("SaveFile.jsp");
	//隐藏菜单栏
	poCtrl1.setMenubar(false);
	//隐藏工具栏
	//poCtrl1.setCustomToolbar(false);
	//打开Word文件
	//判断文档是否暂存
	
	//如果存在文档，将打开暂存文档，否则打开新文档
	
		poCtrl1.webOpen("../OfficeFile/Templet/EmHouse/EmHouseBJ.xls",
				OpenModeType.xlsNormalEdit, "szciic");
	

	poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
	session.setAttribute("cq_id", null);
	session.setAttribute("cq_id", request.getParameter("idstr"));
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
