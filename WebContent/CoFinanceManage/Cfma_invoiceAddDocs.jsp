
<%@page import="com.zhuozhengsoft.pageoffice.excelreader.Workbook"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@page import="org.zkoss.poi.hwpf.usermodel.Bookmark"%>
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,com.zhuozhengsoft.pageoffice.excelwriter.*,bll.CoFinanceManage.Cfma_SelBll,Model.CoFinanceCollectModel,org.zkoss.zk.ui.Executions,java.sql.ResultSet,java.sql.PreparedStatement"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
<%
	List<CoFinanceCollectModel> cfcmList = (List<CoFinanceCollectModel>) Executions
			.getCurrent().getDesktop().getSession()
			.getAttribute("cfcmList");
	PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath() + "/poserver.do");
	System.out.println(request.getContextPath());
	//隐藏Office工具条
	poCtrl.setOfficeToolbars(false);
	//隐藏自定义工具栏
	//poCtrl.setCustomToolbar(false);
	//隐藏菜单
	poCtrl.setMenubar(false);
	//设置页面的显示标题
	poCtrl.setCaption("打印收款");
	//添加自定义按钮
	poCtrl.addCustomToolButton("打印", "print", 1);

	//定义WordDocument对象
	WordDocument doc = new WordDocument();
	//定义DataTag对象，向区域赋值

	DataTag deptTag = doc.openDataTag("{serviceFee}");
	deptTag.setValue(cfcm.getServiceFee() + "");
	DataTag deptTag1 = doc.openDataTag("{salaryOfAfterTax}");
	deptTag1.setValue(cfcm.getSalaryOfAfterTax() + "");
	DataTag deptTag2 = doc.openDataTag("{shebaofee}");
	deptTag2.setValue(cfcm.getSheBaoFee() + "");
	DataTag deptTag3 = doc.openDataTag("{oMoveFee}");
	deptTag3.setValue(cfcm.getoMoveFee() + "");
	System.out.println(cfcm.getoMoveFee());
	DataTag deptTag4 = doc.openDataTag("{businessProtectFee}");
	deptTag4.setValue(cfcm.getBusinessProtectFee() + "");
	DataTag deptTag5 = doc.openDataTag("{finanServiceFee}");
	deptTag5.setValue(cfcm.getFinanServiceFee() + "");
	DataTag deptTag6 = doc.openDataTag("{bodyTestFee}");
	deptTag6.setValue(cfcm.getBodyTestFee() + "");
	DataTag deptTag7 = doc.openDataTag("{residencePermitFee}");
	deptTag7.setValue(cfcm.getResidencePermitFee() + "");
	DataTag deptTag8 = doc.openDataTag("{activityFee}");
	deptTag8.setValue(cfcm.getActivityFee() + "");
	DataTag deptTag9 = doc.openDataTag("{recruitServiceFee}");
	deptTag9.setValue(cfcm.getRecruitServiceFee() + "");
	DataTag deptTag10 = doc.openDataTag("{businessServiceFee}");
	deptTag10.setValue(cfcm.getBusinessServiceFee() + "");
	DataTag deptTag11 = doc.openDataTag("{book}");
	deptTag11.setValue(cfcm.getBookFee() + "");
	DataTag deptTag12 = doc.openDataTag("{lasscFee}");
	deptTag12.setValue(cfcm.getLasscFee() + "");
	DataTag deptTag13 = doc.openDataTag("{houseGjj}");
	deptTag13.setValue(cfcm.getHouseGjj() + "");
	DataTag deptTag14 = doc.openDataTag("{other}");
	deptTag14.setValue(cfcm.getOther() + "");
	DataTag deptTag15 = doc.openDataTag("{deformityFee}");
	deptTag15.setValue(cfcm.getDeformityFee() + "");
	DataTag deptTag16 = doc.openDataTag("{fileManageFee}");
	deptTag16.setValue(cfcm.getFileManageFee() + "");
	DataTag deptTag17 = doc.openDataTag("{total}");
	deptTag17.setValue(cfcm.getCfco_TotalPaidIn() + "");

	DataTag deptTag18 = doc.openDataTag("{pirntRemark}");
	deptTag18.setValue(cfcm.getCfco_remark());
	DataTag deptTag19 = doc.openDataTag("{remitter}");
	deptTag19.setValue(cfcm.getCoba_company());
	DataTag deptTag20 = doc.openDataTag("{ownmonth}");
	deptTag20.setValue(cfcm.getOwnmonth() + "");
	DataTag deptTag21 = doc.openDataTag("{kefu}");
	deptTag21.setValue(cfcm.getCoba_client());
	DataTag deptTag22 = doc.openDataTag("{date}");
	deptTag22.setValue(cfcm.getCfco_addtime());

	poCtrl.setWriter(doc);

	poCtrl.webOpen("../OfficeFile/Templet/CfmaInvoice/"
			+ "CfmaPrintModel.doc", OpenModeType.docNormalEdit,
			"szciic");

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