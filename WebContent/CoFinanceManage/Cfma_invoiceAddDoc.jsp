
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="org.zkoss.poi.hwpf.usermodel.Bookmark"%>
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,com.zhuozhengsoft.pageoffice.excelwriter.*,bll.CoFinanceManage.Cfma_SelBll,Model.CoBaseModel,org.zkoss.zk.ui.Executions,java.sql.ResultSet,java.sql.PreparedStatement"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>
<%
	List<CoBaseModel> cfcmList = (List<CoBaseModel>) Executions
	.getCurrent().getDesktop().getSession()
	.getAttribute("cfcmList");
	//System.out.println(" jsp = " + cfcmList.size());
	WordDocument doc = new WordDocument();
	
	DataRegion title = doc.createDataRegion("Para1",
	DataRegionInsertType.After, "[home]");
	DataRegion d = null;
	DataRegion d2 = null;
	ParagraphFormat dPara = null;
	com.zhuozhengsoft.pageoffice.wordwriter.Table tb =null;
	com.zhuozhengsoft.pageoffice.wordwriter.Table tb2 =null;
	int p =1;
	int j =0;
	int k=0;

	for (int i = 0; i < cfcmList.size(); i++) {
		if(i==3){k=-1;}
		else{k=0;}
	d = doc.createDataRegion("Para" + (p + 1),
	DataRegionInsertType.After, "Para"+p);
	tb = d
	.createTable(13, 4, WdAutoFitBehavior.wdAutoFitWindow);
		
	tb.openCellRC(1+j*14+k, 1).setValue(
	"汇款人姓名:" + cfcmList.get(i).getCoba_company()
	+ "                     账单所属月: "
	+ cfcmList.get(i).getOwnmonth());
		
	tb.openCellRC(1+j*14+k, 1).mergeTo(1+j*14+k, 2);
	tb.openCellRC(1+j*14+k, 1).mergeTo(1+j*14+k, 3);
	tb.openCellRC(1+j*14+k, 1).mergeTo(1+j*14+k, 4);
	tb.openCellRC(2+j*14+k,1).setValue(String.valueOf(tb.getIndex()));
	String addtime=cfcmList.get(i).getAmount().getAddtime()==null?"":cfcmList.get(i).getAmount().getAddtime();
	tb.openCellRC(2+j*14+k, 1)
	.setValue(
	"客服代表:   "
	+ cfcmList.get(i).getCoba_client()
	+ "                                                实收日期:"
	+addtime );
	
	tb.openCellRC(2+j*14+k, 1).mergeTo(2+j*14+k, 2);
	tb.openCellRC(2+j*14+k, 1).mergeTo(2+j*14+k, 3);
	tb.openCellRC(2+j*14+k, 1).mergeTo(2+j*14+k, 4);

	tb.openCellRC(3+j*14+k, 1).setValue("服务费:");
	tb.openCellRC(3+j*14+k, 2).setValue(
	cfcmList.get(i).getAmount().getServiceFee() + "");
	tb.openCellRC(3+j*14+k, 3).setValue("税后工资");
	tb.openCellRC(3+j*14+k, 4).setValue(
	cfcmList.get(i).getAmount().getSalaryOfAfterTax() + "");
	
	tb.openCellRC(4+j*14+k, 1).setValue("社保费");
	tb.openCellRC(4+j*14+k, 2).setValue(
	cfcmList.get(i).getAmount().getSheBaoFee() + "");
	tb.openCellRC(4+j*14+k, 3).setValue("个调税");
	tb.openCellRC(4+j*14+k, 4)
	.setValue(cfcmList.get(i).getAmount().getoMoveFee() + "");
	
	tb.openCellRC(5+j*14+k, 1).setValue("商保费");
	tb.openCellRC(5+j*14+k, 2).setValue(
	cfcmList.get(i).getAmount().getBusinessProtectFee() + "");
	tb.openCellRC(5+j*14+k, 3).setValue("财务服务费");
	tb.openCellRC(5+j*14+k, 4).setValue(
	cfcmList.get(i).getAmount().getFinanServiceFee() + "");
	
	tb.openCellRC(6+j*14+k, 1).setValue("体检费");
	tb.openCellRC(6+j*14+k, 2).setValue(
	cfcmList.get(i).getAmount().getBodyTestFee() + "");
	tb.openCellRC(6+j*14+k, 3).setValue("居住证");
	tb.openCellRC(6+j*14+k, 4).setValue(
	cfcmList.get(i).getAmount().getResidencePermitFee() + "");
	
	tb.openCellRC(7+j*14+k, 1).setValue("活动费");
	tb.openCellRC(7+j*14+k, 2).setValue(
	cfcmList.get(i).getAmount().getActivityFee() + "");
	tb.openCellRC(7+j*14+k, 3).setValue("招聘服务");
	tb.openCellRC(7+j*14+k, 4).setValue(
	cfcmList.get(i).getAmount().getRecruitServiceFee() + "");
	
	tb.openCellRC(8+j*14+k, 1).setValue("书报费");
	tb.openCellRC(8+j*14+k, 2).setValue(cfcmList.get(i).getAmount().getBookFee() + "");
	tb.openCellRC(8+j*14+k, 3).setValue("商务服务费");
	tb.openCellRC(8+j*14+k, 4).setValue(
	cfcmList.get(i).getAmount().getBusinessServiceFee() + "");
	
	tb.openCellRC(9+j*14+k, 1).setValue("社保卡");
	tb.openCellRC(9+j*14+k, 2)
	.setValue(cfcmList.get(i).getAmount().getLasscFee() + "");
	tb.openCellRC(9+j*14+k, 3).setValue("公积金");
	tb.openCellRC(9+j*14+k, 4)
	.setValue(cfcmList.get(i).getAmount().getHouseGjj() + "");
	
	tb.openCellRC(10+j*14+k, 1).setValue("其他");
	tb.openCellRC(10+j*14+k, 2).setValue(cfcmList.get(i).getAmount().getOther() + "");
	tb.openCellRC(10+j*14+k, 3).setValue("残疾人保障金");
	tb.openCellRC(10+j*14+k, 4).setValue(
	cfcmList.get(i).getAmount().getDeformityFee() + "");
	
	tb.openCellRC(11+j*14+k, 1).setValue("档案服务费");
	tb.openCellRC(11+j*14+k, 2).setValue(
	cfcmList.get(i).getAmount().getFileManageFee() + "");
	tb.openCellRC(11+j*14+k, 3).setValue("税金");
	tb.openCellRC(11+j*14+k, 4).setValue(
	cfcmList.get(i).getAmount().getTaxes() + "");
	
	tb.openCellRC(12+j*14+k, 1).setValue("合计");
	tb.openCellRC(12+j*14+k, 2).setValue(
	cfcmList.get(i).getAmount().getCfmb_TotalPaidIn() + "");
	tb.openCellRC(12+j*14+k, 3).setValue("");
	tb.openCellRC(12+j*14+k, 4).setValue("");
	
	if(cfcmList.get(i).getAmount().getRemark()!=null && !cfcmList.get(i).getAmount().getRemark().equals("null")){
		tb.openCellRC(13+j*14+k, 1).setValue(
		"备注:  " + cfcmList.get(i).getAmount().getRemark());
	}else{
		tb.openCellRC(13+j*14+k, 1).setValue(
		"备注:  ");
	}
	tb.openCellRC(13+j*14+k, 1).mergeTo(13+j*14+k, 2);
	tb.openCellRC(13+j*14+k, 1).mergeTo(13+j*14+k, 3);
	tb.openCellRC(13+j*14+k, 1).mergeTo(13+j*14+k, 4);
	
	tb.setRowsHeight(18);
	//创建ParagraphFormat对象
	dPara = d.getParagraphFormat();
	//设置段落的行间距、对齐方式、首行缩进
	//dPara.setLineSpacingRule(WdLineSpacing.wdLineSpace1pt5);
	dPara.setAlignment(WdParagraphAlignment.wdAlignParagraphLeft);
	//dPara.setFirstLineIndent(18);
	p++;
	
		
		
	if(i<2){
		d2 = doc.createDataRegion("Para" + (p + 1),
		DataRegionInsertType.After, "Para"+p);
		tb2 = d2.createTable(1, 1, WdAutoFitBehavior.wdAutoFitWindow);
		tb2.openCellRC(14+j*14+k, 1).setValue("");
		p++;
	}else{
		if(i>2 && (i+1)%3!=0 && i<(cfcmList.size()-1)){
		d2 = doc.createDataRegion("Para" + (p + 1),
			DataRegionInsertType.After, "Para"+p);
		tb2 = d2.createTable(1, 1, WdAutoFitBehavior.wdAutoFitWindow);
		tb2.openCellRC(1, 1).setValue("");
		
		p++;
		}
	}
		
		if(i<3){
			j++;
		}else{
			j=0;
		}
	
		
	}
	
	PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath() + "/poserver.do");
	//System.out.println(request.getContextPath());
	//隐藏Office工具条
	poCtrl.setOfficeToolbars(false);
	//隐藏自定义工具栏
	//poCtrl.setCustomToolbar(false);
	//隐藏菜单
	poCtrl.setMenubar(false);
	//设置页面的显示标题
	poCtrl.setCaption("打印收款");
	//添加自定义按钮
	poCtrl.addCustomToolButton("打印", "print()", 1);
	
	poCtrl.setWriter(doc);

	poCtrl.webOpen("../OfficeFile/Templet/CfmaInvoice/"
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
	<script language="javascript" type="text/javascript">
		function print() {
			document.getElementById("PageOfficeCtrl1").ShowDialog(4); //打印
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