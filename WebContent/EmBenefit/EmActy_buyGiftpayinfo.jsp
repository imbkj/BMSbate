<%@page import="java.math.BigDecimal"%>
<%@ page language="java" import="com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<%@page import="com.zhuozhengsoft.pageoffice.excelwriter.*"%>
<%@page import="java.awt.Color"%>
<%@page import="Model.EmActySuppilerGiftInfoModel"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Date"%>
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
	//隐藏Office工具条
	poCtrl1.setOfficeToolbars(false);
	poCtrl1.setMenubar(false);
	//隐藏菜单
	
	String sql="select gift_id,gift_name,gift_brand,gift_color,gift_production,gift_class,gift_inaddress," +
			"convert(char(10),gift_intime,120) as gift_intime,gift_totalnum,gift_supid,gift_remark," +
			"gift_nownum,gift_state,gift_Addname,convert(char(16),gift_addtime,120) as gift_addtime," +
			"supp_Name, gift_price,gift_nowprice,ownmonth,gift_allprice,convert(char(10),gift_validdate,120) as gift_validdate," +
			"case gift_state when 0 then '未审核' when 1 then '已审核' when 2 then '已采购' when 3 then '已入库' when 4 then '退回' end statename," +
			"gift_tarpid,gift_totalprice,gift_auditname,convert(char(16),gift_audittime,120) as gift_audittime,gift_buyname," +
			"convert(char(10),gift_invoicedate,120) as gift_invoicedate,convert(char(10),gift_paydate,120) as gift_paydate," +
			"convert(char(10),gift_buytime,120) as gift_buytime,gift_inname,countnum,bnum,gift_invoicenumber  " +
			" from EmActySuppilerGiftInfo a left join  EmActySupplierInfo b on a.gift_supid=b.supp_id " +
			"left join (select gout_giftid,count(*) as countnum from EmActyGiftOutInfo group by gout_giftid) c on a.gift_id=c.gout_giftid " +
			"left join (select gtbk_giftid,count(*) as bnum from EmActyGiftBackInfo group by gtbk_giftid) d on a.gift_id=d.gtbk_giftid " +
			" where 1=1 and  gift_id="+request.getParameter("id");
	ResultSet rs = stmt.executeQuery(sql);
	Integer ownmonth=0,y=5,row=1; ;
	String buydate="",validdate="",invoicedate="",invoicenumber="",paydate="",remark="",giftname="";
	BigDecimal allprice=new BigDecimal("0.0"),totalprice=new BigDecimal("0.0");
	//Table table = sheet.openTable("B5:H5");
	while (rs.next()) {
		ownmonth=rs.getInt("ownmonth");
		giftname=rs.getString("gift_name");
		if(rs.getString("gift_buytime")!=null)
		{
			buydate=rs.getString("gift_buytime");
		}
		if(rs.getString("gift_validdate")!=null)
		{
			validdate=rs.getString("gift_validdate");
		}
		if(rs.getString("gift_invoicedate")!=null)
		{
			invoicedate=rs.getString("gift_invoicedate");
		}
		if(rs.getString("gift_invoicenumber")!=null)
		{
			invoicenumber=rs.getString("gift_invoicenumber");
		}
		if(rs.getString("gift_paydate")!=null)
		{
			paydate=rs.getString("gift_paydate");
		}
		if(rs.getString("gift_remark")!=null)
		{
			remark=rs.getString("gift_remark");
		}
		if(rs.getString("gift_allprice")!=null)
		{
			allprice=rs.getBigDecimal("gift_allprice");
		}
		if(rs.getString("gift_totalprice")!=null)
		{
			totalprice=rs.getBigDecimal("gift_totalprice");
		}
		
		Cell cella = sheet.openCell("B"+y);
		cella.setValue(row+"");
		Cell cellb = sheet.openCell("C"+y);
		cellb.setValue(giftname+"");
		Cell cellc = sheet.openCell("D"+y);
		cellc.setValue(ownmonth+"");
		Cell celld= sheet.openCell("E"+y);
		celld.setValue(buydate+"");
		Cell celle= sheet.openCell("F"+y);
		celle.setValue(allprice+"");
		Cell cellf= sheet.openCell("G"+y);
		cellf.setValue(totalprice+"");
		Cell cellg = sheet.openCell("H"+y);
		cellg.setValue(invoicedate+"");
		Cell cellh= sheet.openCell("I"+y);
		cellh.setValue(invoicedate+"");
		Cell celli= sheet.openCell("J"+y);
		celli.setValue(invoicenumber+"");
		Cell cellj= sheet.openCell("K"+y);
		cellj.setValue(paydate+"");
		Cell cellk= sheet.openCell("L"+y);
		cellk.setValue(remark+"");
		//table.nextRow();
		y=y+1;
		row=row+1;
	}
	Cell cellb2 = sheet.openCell("B2");
	cellb2.setValue(ownmonth+"‘"+giftname+"’信息总汇：");
	//table.close();
	poCtrl1.setWriter(workBook);

	poCtrl1.setCaption("打印借卡条");
	//添加自定义按钮
	poCtrl1.addCustomToolButton("打印","print",6);
	poCtrl1.addCustomToolButton("另存为","Save",3);
	
	//设置保存页面
	poCtrl1.setSaveFilePage("../EmBenefit/EmActy_giftInfoSave.jsp");
	//隐藏菜单栏
	poCtrl1.setMenubar(false);
	//隐藏工具栏
	//poCtrl1.setCustomToolbar(false);
	//打开Word文件
	//判断文档是否暂存
	
	//如果存在文档，将打开暂存文档，否则打开新文档
	poCtrl1.webOpen("../OfficeFile/Templet/EmBenefit/giftconfirm.xls",
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
	<div style="width: auto; height: 450px;">
		<po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
	</div>
</body>
</html>
