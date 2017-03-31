<!--
	创建人：张志强
	创建时间：2013-12-5
	用途：劳动合同制作详细页面
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,java.text.SimpleDateFormat"
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
	//隐藏菜单
	poCtrl.setMenubar(false);
	//设置页面的显示标题
	poCtrl.setCaption("雇员合同");
	//添加自定义按钮
	poCtrl.addCustomToolButton("发送法务审核", "Send", 2);
	//设置保存页面
	poCtrl.setSaveFilePage("../EmBaseCompact/SaveFile_SA.jsp");

	//链接数据库操作开始
	String sql = "select emba_name,emba_sex,CONVERT(varchar(100), emba_birth ,23),isnull(emba_folk,'       /        '),emba_idcard,isnull(emba_hjadd,'       /        '),isnull(emba_epmobile,'               '),isnull(emba_phone,'               '),"
			+ "isnull(emba_mobile,'      /         '),isnull(emba_email,'       /        '),coba_company,"
			+ "isnull(ebco_working_station,'       /        '),isnull(ebco_work_place,'       /        ') place,"
			+ "cast(year(ebco_incept_date) as varchar(4))+'年'+cast(month(ebco_incept_date) as varchar(4))+'月'+cast(day(ebco_incept_date) as varchar(4))+'日',"
			+ "case when ebco_maturity_date is null then '  / 年 / 月 / 日  ' else cast(year(ebco_maturity_date) as varchar(4))+'年'+cast(month(ebco_maturity_date) as varchar(4))+'月'+cast(day(ebco_maturity_date) as varchar(4))+'日' end,"
			+ "case when ebco_probation_incept is null then '  / 年 / 月 / 日  ' else cast(year(ebco_probation_incept) as varchar(4))+'年'+cast(month(ebco_probation_incept) as varchar(4))+'月'+cast(day(ebco_probation_incept) as varchar(4))+'日' end,"
			+ "case when ebco_probation_mdate is null then '  / 年 / 月 / 日  ' else cast(year(ebco_probation_mdate) as varchar(4))+'年'+cast(month(ebco_probation_mdate) as varchar(4))+'月'+cast(day(ebco_probation_mdate) as varchar(4))+'日' end,"
			+ "isnull(ebco_teaching_hour,' / '),ebco_wage,ebco_wage_bt,ebco_probation_wage,ebco_probation_bt,"
			+ "ebco_wage_gz,ebco_probation_gz,ebco_payroll_mode,cast(year(getdate()) as varchar(4))+'年'+cast(month(getdate()) as varchar(4))+'月'+cast(day(getdate()) as varchar(4))+'日',cast(year(ebco_addtime) as varchar(4))+'年'+cast(month(ebco_addtime) as varchar(4))+'月'+cast(day(ebco_addtime) as varchar(4))+'日' addtime"
			+ " from EmBaseCompact a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid where ebco_id="
			+ request.getParameter("ebcc_id");
	ResultSet rs = stmt.executeQuery(sql);
	System.out.println(sql);
	//数据库结束

	//定义WordDocument对象
	WordDocument doc = new WordDocument();

	//定义DataTag对象，向区域赋值
	while (rs.next()) {
		DataTag Tag1 = doc.openDataTag("{员工姓名}");
		Tag1.setValue(rs.getString(1));

		DataTag Tag5 = doc.openDataTag("{身份证号码}");
		Tag5.setValue(rs.getString(5));

		DataTag Tag9 = doc.openDataTag("{联系电话}");
		Tag9.setValue(rs.getString(9));

		DataTag Tag10 = doc.openDataTag("{签订时间}");
		Tag10.setValue(rs.getString("addtime"));
		
		DataTag Tag118 = doc.openDataTag("{合同到期时间}");
		Tag118.setValue(rs.getString(15));
		
		DataTag Tag18 = doc.openDataTag("{合同起始时间}");
		Tag18.setValue(rs.getString(14));

		DataTag Tag19 = doc.openDataTag("{工作地点}");
		Tag19.setValue(rs.getString("place"));

		java.util.Date date;
		java.util.Date date1;
		java.util.Date date2;
		java.util.Date date3;
		java.util.Date date4;
		java.util.Date date5;
		java.util.Date date9;
		java.util.Date date12;
		java.util.Date date15;

		// 首先设置"Mon Dec 28 00:00:00 CST 2008"的格式,用来将其转化为Date对象

		String str1 = "";
		String str2 = "";
		String str3 = "";
		String str4 = "";
		String str5 = "";
		
		
		//将已有的时间字符串转化为Date对象
		if (!request.getParameter("emba1").equals("0")) {
			SimpleDateFormat df1 = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			date1 = df1.parse(request.getParameter("emba1"));
			df1 = new SimpleDateFormat("yyyy-MM-dd");
			str1 = request.getParameter("emba1");// 获得格式化后的日期字符串
		}
		
		
			str2 = request.getParameter("emba2");// 获得格式化后的日期字符串
		
		if (!request.getParameter("emba3").equals("0")) {
			SimpleDateFormat df3 = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			date3 = df3.parse(request.getParameter("emba3"));
			df3 = new SimpleDateFormat("yyyy-MM-dd");
			str3 = df3.format(date3);// 获得格式化后的日期字符串
		}

	
			str4 =  request.getParameter("emba4");// 获得格式化后的日期字符串

		
		if (!request.getParameter("emba5").equals("0")) {
			SimpleDateFormat df5 = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			date5 = df5.parse(request.getParameter("emba5"));
			df5 = new SimpleDateFormat("yyyy-MM-dd");
			str5 = df5.format(date5);// 获得格式化后的日期字符串
		}
		
		int emi=0;

		if (str2 != "") {
			emi=emi+1;
			DataTag Tag20 = doc.openDataTag("{变更起始日}");
			Tag20.setValue(emi+"、乙方与甲方的劳动合同期限以及与丙方的聘用期限将变更为自" + str2);

			DataTag Tag21 = doc.openDataTag("{变更到期日}");
			Tag21.setValue("（请填写现行有效劳动合同的起始日）起至" + str3
					+ "（请填写变更后的劳动合同终止日）。");
		} else {
			DataTag Tag20 = doc.openDataTag("{变更起始日}");
			Tag20.setValue("　");

			DataTag Tag21 = doc.openDataTag("{变更到期日}");
			Tag21.setValue("　");
		}

		if (!request.getParameter("emba7").equals("") ) {
			emi=emi+1;
			DataTag Tag22 = doc.openDataTag("{工作地点}");
			Tag22.setValue(request.getParameter("emba7") + "。");

			DataTag Tag23 = doc.openDataTag("{工作地点调整时间}");
			Tag23.setValue(emi+"、自 " + str1 + "起，乙方的工作地点调整为");
		} else {
			DataTag Tag22 = doc.openDataTag("{工作地点}");
			Tag22.setValue("　");
			DataTag Tag23 = doc.openDataTag("{工作地点调整时间}");
			Tag23.setValue("　");
		}

		if (!request.getParameter("emba9").equals("")) {
			emi=emi+1;
			DataTag Tag24 = doc.openDataTag("{工资调整时间}");
			Tag24.setValue(emi+"、自 " + str1
					+ "起，乙方的月工资（含加班工资）/正常工作时间的月工资调整为人民币");

			DataTag Tag25 = doc.openDataTag("{工资}");
			Tag25.setValue(request.getParameter("emba9") + "元。");
		} else {
			DataTag Tag24 = doc.openDataTag("{工资调整时间}");
			Tag24.setValue("　");

			DataTag Tag25 = doc.openDataTag("{工资}");
			Tag25.setValue("　");
		}

		if (!request.getParameter("emba11").equals("")) {
			emi=emi+1;
			DataTag Tag26 = doc.openDataTag("{岗位}");
			Tag26.setValue(request.getParameter("emba11") + "。");

			DataTag Tag27 = doc.openDataTag("{岗位调整时间}");
			Tag27.setValue(emi+"、自 " + str1 + "起，乙方的岗位调整为");
		} else {
			DataTag Tag26 = doc.openDataTag("{岗位}");
			Tag26.setValue("　");

			DataTag Tag27 = doc.openDataTag("{岗位调整时间}");
			Tag27.setValue("　");
		}
	}

	poCtrl.setWriter(doc);

	//判断文档是否暂存
	String sql2 = "select count(*),puof_url from PubOffice where puof_pute_id=4 and puof_tid="
			+ request.getParameter("ebcc_id") + " group by puof_url";
	ResultSet rs2 = stmt.executeQuery(sql2);
	Integer cont;
	String ofile;
	cont = 0;
	ofile = "";
	while (rs2.next()) {
		cont = rs2.getInt(1);
		ofile = rs2.getString(2);
	}

	//如果存在文档，将打开暂存文档，否则打开新文档
	poCtrl.webOpen("../OfficeFile/Templet/EmBaseCompact/agreement.doc",
			OpenModeType.docNormalEdit, "szciic");

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	rs.close();
	stmt.close();
	conn.close();
	session.setAttribute("ebcc_id", null);
	session.setAttribute("ebcc_id", request.getParameter("ebcc_id"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>合同信息</title>
</head>
<body>
	<script type="text/javascript">
		function Send() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			window.location = "../EmBaseCompact/EmBaseCompactSA_AddDocOk.jsp?ebcc_id=<%=request.getParameter("ebcc_id")%>&user=<%=request.getParameter("user")%>&emba1=<%=request.getParameter("emba1")%>&emba2=<%=request.getParameter("emba2")%>&emba3=<%=request.getParameter("emba3")%>&emba4=<%=request.getParameter("emba4")%>&emba5=<%=request.getParameter("emba5")%>&emba6=<%=request.getParameter("emba6")%>&emba7=<%=request.getParameter("emba7")%>&emba8=<%=request.getParameter("emba8")%>&emba9=<%=request.getParameter("emba9")%>&emba10=<%=request.getParameter("emba10")%>&emba11=<%=request.getParameter("emba11")%>";
		}
	</script>
	<form id="form1">
		<div style="width: auto; height: 400px;">
			<po:PageOfficeCtrl id="PageOfficeCtrl1">
			</po:PageOfficeCtrl>
		</div>
	</form>
</body>
</html>
