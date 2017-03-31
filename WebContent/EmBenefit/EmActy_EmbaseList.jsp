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
	Sheet sheet = workBook.openSheet("礼品");
	
	Sheet sheeth = workBook.openSheet("礼品");
	//隐藏Office工具条
	poCtrl1.setOfficeToolbars(false);
	poCtrl1.setMenubar(false);
	//隐藏菜单
	String embf_mold="",sqlstr="",sqlsh="";
	//查询礼品
	sqlstr=" and emwf_state=8 and embf_mold='礼品'";
	sqlsh=" and emwf_linktime is not null and embf_mold='活动'";
	Integer ownmonth=0,y=2,row=1,fs=0;
	BigDecimal zj =new BigDecimal(0.00);
	String buydate="",validdate="",invoicedate="",invoicenumber="",paydate="",remark="",giftname="";
	BigDecimal allprice=new BigDecimal("0.0"),totalprice=new BigDecimal("0.0");
		String sqls="select emwf_id,cid,gid,emwf_company,emwf_name,emwf_idcard,convert(varchar(10),emwf_intime,120)emwf_intime,"
				+ "embf_name,emwf_paykind,emwf_delivery,emwf_charge,emwf_sendname,a.ownmonth,prod_discountprice," +
				"convert(varchar(10),emwf_taketime,120) emwf_taketime,convert(varchar(10),emwf_sendtime,120) emwf_sendtime,"
				+ "convert(varchar(10),emwf_signtime,120)emwf_signtime,emwf_signname,emwf_state,emwf_addname,convert(varchar(19),emwf_addtime,120)emwf_addtime,"
				+ "case when emwf_signtime is null then '未签收' else '已签收' end emwf_signState,emwf_sortid,"
				+ "emwf_embf_id,convert(varchar(10),emwf_need,120)emwf_need,emwf_amount,emwf_dept,emwf_client,embf_mold,emwf_family,emwf_standard,"
				+ "prod_name productName,supp_name suppName,emwf_content,gift_remark "
				+ " from EmWelfare a inner join EmActySuppilerGiftInfo e on a.emwf_sortid=e.gift_sortid inner join EmBenefit b on a.emwf_embf_id=b.embf_id "
				+ " left join EmActySupProductInfo c on a.emwf_gift_id=c.prod_id"
				+ " left join EmActySupplierInfo d on c.prod_supid=d.supp_id where 1=1";
		ResultSet rss = stmt.executeQuery(sqls+sqlstr+" and emwf_sortid='"+request.getParameter("id")+"' order by emwf_content");
		 Table table = sheet.openTable("A2:N2");
		while(rss.next())
		{
			//Table table = sheet.openTable("A4:N4");
			Cell cellaa = sheet.openCell("A"+y);
			cellaa.setValue(row+"");
			
			Cell cella = sheet.openCell("B"+y);
			cella.setValue(rss.getString("emwf_company")+"");
			
			Cell cellb = sheet.openCell("C"+y);
			cellb.setValue(rss.getInt("ownmonth")+"");
			
			Cell cellc = sheet.openCell("D"+y);
			cellc.setValue(rss.getString("emwf_name"));
			
			Cell celld = sheet.openCell("E"+y);
			if(rss.getString("emwf_idcard")!=null)
			{
				celld.setValue(rss.getString("emwf_idcard"));
			}
			Cell cellf = sheet.openCell("F"+y);
			if(rss.getString("productName")!=null)
			{
				cellf.setValue(rss.getString("productName"));
			}
			Cell cellgs = sheet.openCell("G"+y);
			if(rss.getString("embf_name")!=null)
			{
				cellgs.setValue(rss.getString("embf_name"));
			}
			
			Cell cellg = sheet.openCell("H"+y);
			if(rss.getString("emwf_need")!=null)
			{
				cellg.setValue(rss.getString("emwf_need"));
			}
			
			
			Cell cellh = sheet.openCell("I"+y);
			if(rss.getString("emwf_amount")!=null)
			{
				cellh.setValue(rss.getString("emwf_amount"));
				fs=fs+rss.getInt("emwf_amount");
			}
			
			Cell celli = sheet.openCell("J"+y);
			if(rss.getString("emwf_charge")!=null)
			{
				celli.setValue(rss.getString("emwf_charge"));
				//zj=zj+rss.getInt("emwf_charge");
			}
			
			Cell cellj = sheet.openCell("K"+y);
			if(rss.getString("emwf_dept")!=null)
			{
				cellj.setValue(rss.getString("emwf_dept"));
			}
			
			Cell cellk = sheet.openCell("L"+y);
			if(rss.getString("emwf_client")!=null)
			{
				cellk.setValue(rss.getString("emwf_client"));
			}
			
			Cell celll = sheet.openCell("M"+y);
			if(rss.getString("prod_discountprice")!=null)
			{
				if(rss.getString("emwf_amount")!=null)
				{
					BigDecimal bd=new BigDecimal(rss.getString("prod_discountprice"));
					BigDecimal bds=new BigDecimal(rss.getString("emwf_amount"));
					//bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal pr=bd.multiply(bds);
					if(pr!=null)
					{
						celll.setValue(pr.toString());
						zj=zj.add(pr);
					}
				}
			}
			
			Cell cellm = sheet.openCell("N"+y);
			if(rss.getString("gift_remark")!=null)
			{
				cellm.setValue(rss.getString("gift_remark"));
			}
			table.nextRow();
			
			y++;
			row++;
		} 
	if(y>2)
	{
		Cell cellb2 = sheet.openCell("M"+y);
		cellb2.setValue("合计："+zj+"元");
		Cell celli = sheet.openCell("I"+y);
		celli.setValue("合计："+fs+"份");
	}
	table.close();
	
	Integer ownmonths=0,ys=2,rows=1,fss=0;
	BigDecimal zjs=new BigDecimal("0.00");
	ResultSet rsh= stmt.executeQuery(sqls+sqlsh+" and emwf_sortid='"+request.getParameter("id")+"' order by emwf_content");
	 Table tableh = sheet.openTable("A2:N2");
	while(rsh.next())
	{
		//Table table = sheet.openTable("A4:N4");
		Cell cellaa = sheet.openCell("A"+ys);
		cellaa.setValue(rows+"");
		
		Cell cella = sheet.openCell("B"+ys);
		cella.setValue(rsh.getString("emwf_company")+"");
		
		Cell cellb = sheet.openCell("C"+ys);
		cellb.setValue(rsh.getInt("ownmonth")+"");
		
		Cell cellc = sheet.openCell("D"+ys);
		cellc.setValue(rsh.getString("emwf_name"));
		
		Cell celld = sheet.openCell("E"+ys);
		if(rsh.getString("emwf_idcard")!=null)
		{
			celld.setValue(rsh.getString("emwf_idcard"));
		}
		Cell cellf = sheet.openCell("F"+ys);
		if(rsh.getString("productName")!=null)
		{
			cellf.setValue(rsh.getString("productName"));
		}
		Cell cellgs = sheet.openCell("G"+ys);
		if(rsh.getString("embf_name")!=null)
		{
			cellgs.setValue(rsh.getString("embf_name"));
		}
		
		Cell cellg = sheet.openCell("H"+ys);
		if(rsh.getString("emwf_need")!=null)
		{
			cellg.setValue(rsh.getString("emwf_need"));
		}
		
		
		Cell cellh = sheet.openCell("I"+ys);
		if(rsh.getString("emwf_amount")!=null)
		{
			cellh.setValue(rsh.getString("emwf_amount"));
			fss=fss+rsh.getInt("emwf_amount");
		}
		
		Cell celli = sheet.openCell("J"+ys);
		if(rsh.getString("emwf_charge")!=null)
		{
			celli.setValue(rsh.getString("emwf_charge"));
			
		}
		
		Cell cellj = sheet.openCell("K"+ys);
		if(rsh.getString("emwf_dept")!=null)
		{
			cellj.setValue(rsh.getString("emwf_dept"));
		}
		
		Cell cellk = sheet.openCell("L"+ys);
		if(rsh.getString("emwf_client")!=null)
		{
			cellk.setValue(rsh.getString("emwf_client"));
		}
		
		Cell celll = sheet.openCell("M"+ys);
		if(rsh.getString("prod_discountprice")!=null)
		{
			if(rsh.getString("emwf_amount")!=null)
			{
				BigDecimal bd=new BigDecimal(rsh.getString("prod_discountprice"));
				BigDecimal bds=new BigDecimal(rsh.getString("emwf_amount"));
				//bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal pr=bd.multiply(bds);
				if(pr!=null)
				{
					celll.setValue(pr+"");
					zjs=zjs.add(pr);
				}
			}
		}
		
		Cell cellm = sheet.openCell("N"+ys);
		if(rsh.getString("gift_remark")!=null)
		{
			cellm.setValue(rsh.getString("gift_remark"));
		}
		tableh.nextRow();
		
		ys++;
		rows++;
	} 
	if(ys>2)
	{
		Cell cellb2 = sheet.openCell("M"+ys);
		cellb2.setValue("合计："+zjs+"元");
		Cell celli = sheet.openCell("I"+ys);
		celli.setValue("合计："+fss+"份");
	}
	//table.close();
	tableh.close();
	rss.close();
	rsh.close();
	
	
	
	
	poCtrl1.setWriter(workBook);

	poCtrl1.setCaption("支付信息");
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
	poCtrl1.webOpen("../OfficeFile/Templet/EmBenefit/embaselist.xls",
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
