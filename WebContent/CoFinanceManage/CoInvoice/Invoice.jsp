<!--
	创建人：彭耀
	创建时间：2015-3-13
	用途：打印发票
-->
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,Util.UserInfo,java.util.Date,Model.CoInvoiceModel,bll.CoFinanceManage.CoInvoiceBll,Util.NumberToCN"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../../js/Conn.jsp"%>
<%
	PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath() + "/poserver.do");
	//隐藏Office工具条
	poCtrl.setOfficeToolbars(false);
	//隐藏自定义工具栏
	//poCtrl.setCustomToolbar(false);
	//痕迹

	//隐藏菜单
	poCtrl.setMenubar(false);
	//设置页面的显示标题
	poCtrl.setCaption("发票打印");
	//添加自定义按钮
	poCtrl.addCustomToolButton("打印","Print",6);
	poCtrl.addCustomToolButton("另存为","Save",3);
	//设置保存页面
	//poCtrl.setSaveFilePage("../EmBenefit/SaveAutFile.jsp");

	//判断文档是否暂存

	Integer id = Integer.parseInt(request.getParameter("id"));
	CoInvoiceBll bll = new CoInvoiceBll();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	CoInvoiceModel cm = bll.getInvoice(id);
	String name = cm.getCoin_title()!=null?cm.getCoin_title():"";
	String date = cm.getCoin_idate()!=null?cm.getCoin_idate():"";
	String remark = cm.getCoin_remark()!=null?cm.getCoin_remark():"";
	
	
	WordDocument doc = new WordDocument();
	DataTag datetag = doc.openDataTag("{date}");
	datetag.setValue(date);
	DataTag nametag = doc.openDataTag("{name}");
	nametag.setValue(name);


	for(int i=0,j=1;i<7;i++){
		
		DataTag om1 = doc.openDataTag("{ownmonth"+j+"}");
		System.out.println(i);
		if(j<=cm.getList().size()){
			//System.out.println(cm.getList().get(i).getCoii_owmonth());
			om1.setValue(cm.getList().get(i).getCoii_owmonth().toString());
		}else{
			om1.setValue("");
		}
		DataTag om2 = doc.openDataTag("{month"+j+"}");
		if(j<=cm.getList().size()){
			//System.out.println(cm.getList().get(i).getCoii_owmonth());
			if(cm.getList().get(i).getCoii_owmonth2()!=null){
				om2.setValue("-"+cm.getList().get(i).getCoii_owmonth2().toString());
			}else{
				om2.setValue("");
			}
			
		}else{
			om2.setValue("");
		}
		
		DataTag item1 = doc.openDataTag("{item"+j+"}");
		if(j<=cm.getList().size()){
			item1.setValue(cm.getList().get(i).getCoii_content());
		}else{
			item1.setValue("");
		}
			DataTag charge1 = doc.openDataTag("{charge"+j+"}");
		if(j<=cm.getList().size()){
			charge1.setValue(cm.getList().get(i).getCoii_fee().toString());
		}else{
			charge1.setValue("");
		}
		j++;
	}
	
	DataTag remarkT = doc.openDataTag("{remark}");
	remarkT.setValue(remark);
	DataTag moneyT1 = doc.openDataTag("{money1}");
	moneyT1.setValue(NumberToCN.number2CNMontrayUnit(cm.getCoin_total()));
	DataTag moneyT2 = doc.openDataTag("{money2}");
	moneyT2.setValue(cm.getCoin_total().toString());
	DataTag addname = doc.openDataTag("{addname}");
	addname.setValue(cm.getCoin_addname().toString());
	
	poCtrl.setWriter(doc);
	poCtrl.webOpen("../OfficeFile/Templet/CfmaInvoice/invoice.doc",
			OpenModeType.docNormalEdit, "szciic");

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		function Print() {
		document.getElementById("PageOfficeCtrl1").PrintPreview();
	}
	</script>
	<form id="form1">
		<div style="width: auto; height: 600px;">
			<po:PageOfficeCtrl id="PageOfficeCtrl1">
			</po:PageOfficeCtrl>
		</div>
	</form>
</body>
</html>