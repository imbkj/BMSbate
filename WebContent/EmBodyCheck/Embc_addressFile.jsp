<!--
	创建人：陈耀家
	创建时间：2014-9-1
	用途：导出体检介绍信
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,Util.UserInfo,java.util.Date"
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
	poCtrl.addCustomToolButton("导出", "Send", 1);

	//设置保存页面
	//poCtrl.setSaveFilePage("../EmBenefit/SaveAutFile.jsp");

	//判断文档是否暂存
	String sql = "select * from EmBcSetupAddress where ebsa_id=" + request.getParameter("id");

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

	Date trialTime = new Date();
	Calendar calendar = new GregorianCalendar();
	calendar.setTime(trialTime);
	String nowYear = String.valueOf(calendar.get(Calendar.YEAR));
	String nowMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
	String nowDate = String.valueOf(calendar.get(Calendar.DATE));
	Integer area = 0;
	while (rs.next()) {
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
		if (plandate != null && !plandate.equals("")) {
			String a[] = plandate.split("-");
			year = a[0];
			month = a[1];
			date = a[2];
		}
		if (rs.getString("rnd") != null) {
			nextdate = rs.getString("rnd");
		}
		if (rs.getString("ebcl_items") != null) {
			items = rs.getString("ebcl_items");
		}
		area = rs.getInt("ebcl_area");
		if (area != null) {
			if (area.equals(4)) {
				fileName = "EmBc_Ft.doc";
			} else if (area.equals(119)) {
				fileName = "EmBc_ftc.doc";
			} else if (area.equals(5)) {
				fileName = "EmBc_Lh.doc";
			} else if (area.equals(125)) {
				fileName = "EmBc_Ns.doc";
			} else if (area.equals(6)) {
				fileName = "EmBc_Nsc.doc";
			} else if (area.equals(120)) {
				fileName = "NS2.doc";
			}
		}
	}
	WordDocument doc = new WordDocument();
	DataTag nametag = doc.openDataTag("{name}");
	nametag.setValue(name);

	DataTag sextag = doc.openDataTag("{sex}");
	sextag.setValue(sex);

	DataTag idcardtag = doc.openDataTag("{idcard}");
	idcardtag.setValue(idcard);

	DataTag yeartag = doc.openDataTag("{year}");
	yeartag.setValue(year);

	DataTag monthtag = doc.openDataTag("{month}");
	monthtag.setValue(month);

	DataTag datetag = doc.openDataTag("{date}");
	datetag.setValue(date);

	DataTag nextdatetag = doc.openDataTag("{nextdate}");
	nextdatetag.setValue(nextdate);

	DataTag itemstag = doc.openDataTag("{items}");
	itemstag.setValue(items);

	DataTag nowYeartag = doc.openDataTag("{nowYear}");
	nowYeartag.setValue(nowYear);

	DataTag nowMonthtag = doc.openDataTag("{nowMonth}");
	nowMonthtag.setValue(nowMonth);

	DataTag nowDatetag = doc.openDataTag("{nowDate}");
	nowDatetag.setValue(nowDate);

	poCtrl.setWriter(doc);
	poCtrl.webOpen("../OfficeFile/Templet/EmBodyCheck/" + fileName,
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
		function Send() {
			//1:打开   2:保存   3:另存为   4:打印   5:打印设置   6:文件属性 
			document.getElementById("PageOfficeCtrl1").ShowDialog(3);

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