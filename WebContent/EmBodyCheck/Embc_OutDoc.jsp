<!--
	创建人：陈耀家
	创建时间：2014-9-1
	用途：导出体检介绍信
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,Model.embodycheckoperlogModel,bll.EmBodyCheck.EmBcInfo_OperateBll,bll.EmBodyCheck.EmBcInfo_SelectBll,com.zhuozhengsoft.pageoffice.wordwriter.*,Util.UserInfo,java.util.Date"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
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
	poCtrl.setCaption("体检介绍信");
	//添加自定义按钮
	poCtrl.addCustomToolButton("导出", "Save", 1);
	poCtrl.addCustomToolButton("打印", "Print", 1);
	//设置保存页面
	//poCtrl.setSaveFilePage("../EmBenefit/SaveAutFile.jsp");

	//判断文档是否暂存
	String sql = "select *,convert(varchar(10),ebcl_bookdate,120) bookdate,convert(varchar(10),ebcl_plandate,120) plandate,case ebcl_bookmode when 2 then '至'+convert(varchar(10),year(dateadd(d,15,ebcl_bookdate)))+'年'+convert(varchar(10),month(dateadd(d,15,ebcl_bookdate)))+'月'+convert(varchar(10),day(dateadd(d,15,ebcl_bookdate)))+'日' end rnd"
			+ " from embodycheck a "
			+ " inner join embodychecklist c on a.embc_id=c.ebcl_embc_id"
			+ " left join EmBcSetupAddress ad on c.ebcl_area=ad.ebsa_id "
			+ " where ebcl_id=" + request.getParameter("id");
	embodycheckoperlogModel logm = new embodycheckoperlogModel();
	logm.setBclg_addname(UserInfo.getUsername());
	logm.setBclg_content("确认并申报了体检信息");
	logm.setBclg_ebcl_id(Integer.parseInt(request.getParameter("id")));
	EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
	EmBcInfo_SelectBll sbll = new EmBcInfo_SelectBll();
	obll.insertLog(logm);
	ResultSet rs = stmt.executeQuery(sql);
	String name = "";
	String idcard = "";
	String sex = "";
	String year = "";
	String month = "";
	String date = "";
	String items = "";
	String plandate = "";
	String nextdate = "";
	String fileName = "";
	String bookdate = "";

	Date trialTime = new Date();
	Calendar calendar = new GregorianCalendar();
	calendar.setTime(trialTime);
	String nowYear = String.valueOf(calendar.get(Calendar.YEAR));
	String nowMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
	String nowDate = String.valueOf(calendar.get(Calendar.DATE));
	Integer area = 0;
	while (rs.next()) {
		if (rs.getString("ebsa_doc") != null) {
			fileName = rs.getString("ebsa_doc");
		}
		name = rs.getString("embc_name");
		if (rs.getString("embc_idcard") != null) {
			idcard = rs.getString("embc_idcard");
		}
		if (rs.getString("embc_sex") != null) {
			sex = rs.getString("embc_sex");
		}
		if (rs.getString("plandate") != null) {
			plandate = rs.getString("plandate");
		}
		if (rs.getString("bookdate") != null) {
			bookdate = rs.getString("bookdate");
		}
		if (bookdate != null && !bookdate.equals("")) {
			String a[] = bookdate.split("-");
			year = a[0];
			month = a[1];
			date = a[2];
		}
		if (rs.getString("rnd") != null) {
			nextdate = rs.getString("rnd");
		}
		if (rs.getString("ebcl_items") != null) {
			items = sbll.getItemList(rs.getString("ebcl_itemnums"));
		}
		area = rs.getInt("ebcl_area");
		if (area != null && (fileName == null || fileName.equals(""))) {
			fileName=rs.getString("ebsa_doc");
		}
	}
	WordDocument doc = new WordDocument();
	DataTag nametag = doc.openDataTag("{姓名}");
	nametag.setValue(name);

	DataTag sextag = doc.openDataTag("{性别}");
	sextag.setValue(sex);

	DataTag idcardtag = doc.openDataTag("{身份证}");
	idcardtag.setValue(idcard);

	DataTag yeartag = doc.openDataTag("{体检年}");
	yeartag.setValue(year);

	DataTag monthtag = doc.openDataTag("{体检月}");
	monthtag.setValue(month);

	DataTag datetag = doc.openDataTag("{体检日}");
	datetag.setValue(date);

	DataTag nextdatetag = doc.openDataTag("{至}");
	nextdatetag.setValue(nextdate);

	DataTag itemstag = doc.openDataTag("{体检项目}");
	itemstag.setValue(items);

	DataTag nowYeartag = doc.openDataTag("{年}");
	nowYeartag.setValue(nowYear);

	DataTag nowMonthtag = doc.openDataTag("{月}");
	nowMonthtag.setValue(nowMonth);

	DataTag nowDatetag = doc.openDataTag("{日}");
	nowDatetag.setValue(nowDate);

	poCtrl.setWriter(doc);
	
	
	poCtrl.webOpen("/temp"+fileName,
			OpenModeType.docNormalEdit, "szciic");

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需
	stmt.close();
	conn.close();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		function Save() {
			//1:打开   2:保存   3:另存为   4:打印   5:打印设置   6:文件属性 
			document.getElementById("PageOfficeCtrl1").ShowDialog(3);

		}
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