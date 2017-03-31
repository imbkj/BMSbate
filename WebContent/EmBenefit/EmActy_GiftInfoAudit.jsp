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
	List<Integer> cidlist=new ArrayList<Integer>();
	String sql="select distinct(cid) cid from EmWelfare where emwf_sortid='"+request.getParameter("id")+"'";
	ResultSet rs = stmt.executeQuery(sql);
	
	while (rs.next()) {
		cidlist.add(rs.getInt("cid"));
	}
	Integer ownmonth=0,y=5,row=1;
	String buydate="",validdate="",invoicedate="",invoicenumber="",paydate="",remark="",giftname="";
	BigDecimal zj=new BigDecimal("0.0");
	Table table = sheet.openTable("B5:H5");
	for(int jk=0;jk<cidlist.size();jk++)
	{
		Integer cid=cidlist.get(jk);
		String sqls="select count(emwf_charge) emwf_charge,emwf_company,a.ownmonth,prod_name,sum(emwf_amount) num,"+
				" sum(emwf_amount)*prod_discountprice price "+
				" from EmWelfare a left join EmActySupProductInfo b on a.emwf_gift_id=b.prod_id,EmActySuppilerGiftInfo c "+
				" where a.emwf_sortid=c.gift_sortid and cid="+cid+" and emwf_sortid='"+request.getParameter("id")+"' "+
				" GROUP BY emwf_company,a.ownmonth,prod_name,prod_discountprice";
		 ResultSet rss = stmt.executeQuery(sqls);
		while(rss.next())
		{
			Cell cella = sheet.openCell("B"+y);
			cella.setValue(row+"");
			if(rss.getString("emwf_company")!=null)
			{
				Cell cells = sheet.openCell("C"+y);
				cells.setValue(rss.getString("emwf_company"));
			}
			
			if(rss.getString("prod_name")!=null)
			{
				Cell cellb = sheet.openCell("D"+y);
				cellb.setValue(rss.getString("prod_name"));
			}
			
			Cell celle = sheet.openCell("E"+y);
			celle.setValue(rss.getInt("num")+"");
			
			Cell cellc = sheet.openCell("F"+y);
			cellc.setValue(rss.getInt("ownmonth")+"");
			
			Cell cellg = sheet.openCell("G"+y);
			if(rss.getString("price")!=null)
			{
				cellg.setValue(rss.getString("price"));
				zj=zj.add(rss.getBigDecimal("price"));
			}
			table.nextRow();
			y++;
			row++;
		}
		
	}
	if(y>5)
	{
		Cell cellb2 = sheet.openCell("G"+y);
		cellb2.setValue("总计："+zj+"元");
	}
	table.close();
	
	poCtrl1.setWriter(workBook);

	poCtrl1.setCaption("审核");
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
	poCtrl1.webOpen("../OfficeFile/Templet/EmBenefit/activityconfirm.xls",
				OpenModeType.xlsNormalEdit, "szciic");
	poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
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
	<div style="width: auto; height: 400px;">
		<po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
	</div>
</body>
</html>
