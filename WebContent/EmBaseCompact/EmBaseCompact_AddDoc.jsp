<!--
	创建人：张志强
	创建时间：2013-12-5
	用途：劳动合同制作详细页面
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
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
	
	//添加自定义按钮
	//poCtrl.addCustomToolButton("暂存", "Save", 1);
	poCtrl.addCustomToolButton("发送审核", "Send", 2);
	//设置保存页面
	poCtrl.setSaveFilePage("../EmBaseCompact/SaveFile.jsp");

	//链接数据库操作开始
	String sql = "select top 1 emba_name,isnull(emba_sex,'         '),isnull(CONVERT(varchar(100), emba_birth ,23),'      '),isnull(emba_folk,'               '),isnull(emba_idcard,'      '),isnull(emba_hjadd,'               '),isnull(emba_epmobile,'               '),isnull(emba_phone,'               '),"
			+ "isnull(emba_mobile,'               '),isnull(emba_email,'               '),coba_company,"
			+ "isnull(ebcc_working_station,'       /        '),isnull(ebcc_work_place,'       /        '),"
			+ "cast(year(ebcc_incept_date) as varchar(4))+'年'+cast(month(ebcc_incept_date) as varchar(4))+'月'+cast(day(ebcc_incept_date) as varchar(4))+'日',"
			+ "case when ebcc_maturity_date is null then '  / 年 / 月 / 日  ' else cast(year(ebcc_maturity_date) as varchar(4))+'年'+cast(month(ebcc_maturity_date) as varchar(4))+'月'+cast(day(ebcc_maturity_date) as varchar(4))+'日' end,"
			+ "case when ebcc_probation_incept is null then '  / 年 / 月 / 日  ' else cast(year(ebcc_probation_incept) as varchar(4))+'年'+cast(month(ebcc_probation_incept) as varchar(4))+'月'+cast(day(ebcc_probation_incept) as varchar(4))+'日' end,"
			+ "case when ebcc_probation_mdate is null then '  / 年 / 月 / 日  ' else cast(year(ebcc_probation_mdate) as varchar(4))+'年'+cast(month(ebcc_probation_mdate) as varchar(4))+'月'+cast(day(ebcc_probation_mdate) as varchar(4))+'日' end,"
			+ "isnull(ebcc_teaching_hour,' / '),ebcc_wage,ebcc_wage_bt,ebcc_probation_wage,ebcc_probation_bt,"
			+ "ebcc_wage_gz,ebcc_probation_gz,ebcc_payroll_mode,cast(year(getdate()) as varchar(4))+'年'+cast(month(getdate()) as varchar(4))+'月'+cast(day(getdate()) as varchar(4))+'日',ebcc_compact_type,a.cid"
			+ " from EmBaseCompactChange a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid where ebcc_id="
			+ request.getParameter("ebcc_id");
	ResultSet rs = stmt.executeQuery(sql);
	System.out.println(sql);

	//数据库结束

	//定义WordDocument对象
	WordDocument doc = new WordDocument();
	
	String compact_type="";
	String cid="";

	//定义DataTag对象，向区域赋值
	while (rs.next()) {
		
		compact_type=rs.getString("ebcc_compact_type");
		
		cid=rs.getString("cid");
		
		DataTag deptTag = doc.openDataTag("{姓名}");
		deptTag.setValue(rs.getString(1));

		DataTag Tag2 = doc.openDataTag("{性别}");
		Tag2.setValue(rs.getString(2));

		DataTag Tag3 = doc.openDataTag("{出生日期}");
		Tag3.setValue(rs.getString(3));

		DataTag Tag4 = doc.openDataTag("{民族}");
		Tag4.setValue(rs.getString(4));

		DataTag Tag5 = doc.openDataTag("{身份证号码}");
		Tag5.setValue(rs.getString(5));

		DataTag Tag6 = doc.openDataTag("{户口所在地}");
		Tag6.setValue(rs.getString(6));

		DataTag Tag7 = doc.openDataTag("{家庭联系电话}");
		Tag7.setValue(rs.getString(7));

		DataTag Tag8 = doc.openDataTag("{办公室联系电话}");
		Tag8.setValue(rs.getString(8));

		DataTag Tag9 = doc.openDataTag("{手机号码}");
		Tag9.setValue(rs.getString(9));

		DataTag Tag10 = doc.openDataTag("{家庭地址}");
		Tag10.setValue("               ");

		DataTag Tag11 = doc.openDataTag("{家庭邮编}");
		Tag11.setValue("               ");

		DataTag Tag12 = doc.openDataTag("{通讯地址}");
		Tag12.setValue("               ");

		DataTag Tag13 = doc.openDataTag("{通讯邮编}");
		Tag13.setValue("               ");

		DataTag Tag14 = doc.openDataTag("{电子邮箱}");
		Tag14.setValue(rs.getString(10));

		DataTag Tag15 = doc.openDataTag("{公司名称}");
		Tag15.setValue(rs.getString(11));

		DataTag Tag16 = doc.openDataTag("{工作岗位}");
		Tag16.setValue(rs.getString(12));

		DataTag Tag17 = doc.openDataTag("{工作地点}");
		Tag17.setValue(rs.getString(13));

		DataTag Tag18 = doc.openDataTag("{合同起始日}");
		Tag18.setValue(rs.getString(14));

		DataTag Tag19 = doc.openDataTag("{合同到期日}");
		Tag19.setValue(rs.getString(15));

		String sy_state;
		sy_state = " / ";
		if (rs.getString(16).equals("  / 年 / 月 / 日  ")) {
			sy_state = "无";
		} else {
			sy_state = "有";
		}
		DataTag Tag20 = doc.openDataTag("{有无试用期}");
		Tag20.setValue(sy_state);

		DataTag Tag21 = doc.openDataTag("{试用期起始日}");
		Tag21.setValue(rs.getString(16));

		DataTag Tag22 = doc.openDataTag("{试用期到期日}");
		Tag22.setValue(rs.getString(17));

		String gs_state;
		gs_state = rs.getString(18);
		if (rs.getString(18).equals("")) {
			gs_state = " / ";
		}
		if (rs.getString(18).equals("标准工时制")) {
			gs_state = "1";
		}
		if (rs.getString(18).equals("不定时工作制")) {
			gs_state = "2";
		}
		if (rs.getString(18).equals("综合计算工时制")) {
			gs_state = "3";
		}
		DataTag Tag23 = doc.openDataTag("{工时选项}");
		Tag23.setValue(gs_state);

		String gz_state;
		gz_state = " / ";
		if (!rs.getString(19).equals("")) {
			gz_state = "1";
		}
		if (!rs.getString(23).equals("")) {
			gz_state = "2";
		}
		DataTag Tag24 = doc.openDataTag("{工资选项}");
		Tag24.setValue(gz_state);

		String gz_1;
		if (!rs.getString(19).equals("")) {
			gz_1 = rs.getString(19);
		} else {
			gz_1 = " / ";
		}
		DataTag Tag25 = doc.openDataTag("{正常工资}");
		Tag25.setValue(gz_1);

		String gz_2;
		if (!rs.getString(20).equals("")) {
			gz_2 = rs.getString(20);
		} else {
			gz_2 = " / ";
		}
		DataTag Tag26 = doc.openDataTag("{正常月综合补贴}");
		Tag26.setValue(gz_2);

		String gz_3;
		if (!rs.getString(21).equals("")) {
			gz_3 = rs.getString(21);
		} else {
			gz_3 = " / ";
		}
		DataTag Tag27 = doc.openDataTag("{试用工资}");
		Tag27.setValue(gz_3);

		String gz_4;
		if (!rs.getString(22).equals("")) {
			gz_4 = rs.getString(22);
		} else {
			gz_4 = " / ";
		}
		DataTag Tag28 = doc.openDataTag("{试用综合补贴}");
		Tag28.setValue(gz_4);

		String gz_5;
		if (!rs.getString(23).equals("")) {
			gz_5 = rs.getString(23);
		} else {
			gz_5 = " / ";
		}
		DataTag Tag29 = doc.openDataTag("{正常含加班工资}");
		Tag29.setValue(gz_5);

		String gz_6;
		if (!rs.getString(24).equals("")) {
			gz_6 = rs.getString(24);
		} else {
			gz_6 = " / ";
		}
		DataTag Tag30 = doc.openDataTag("{试用期含加班工资}");
		Tag30.setValue(gz_6);

		String gz_7;
		gz_7 = " / ";
		if (rs.getString(25).equals("") || rs.getString(25) == "") {
			gz_7 = " / ";
		}
		if (rs.getString(25).equals("客户直接发放")
				|| rs.getString(25) == "客户直接发放") {
			gz_7 = "1";
		}
		if (rs.getString(25).equals("中智代发")
				|| rs.getString(25) == "中智代发") {
			gz_7 = "2";
		}
		if (rs.getString(25).equals("委托发放")
				|| rs.getString(25) == "委托发放") {
			gz_7 = " / ";
		}
		DataTag Tag31 = doc.openDataTag("{工资发放方式}");
		Tag31.setValue(gz_7);

		DataTag Tag32 = doc.openDataTag("{l合同起始日}");
		Tag32.setValue(rs.getString(14));

		DataTag Tag33 = doc.openDataTag("{l合同到期日}");
		Tag33.setValue(rs.getString(15));

		DataTag Tag34 = doc.openDataTag("{当前时间}");
		Tag34.setValue(rs.getString(26));

	}

	poCtrl.setWriter(doc);

	//判断文档是否暂存
	String sql2 = "select count(*),ebct_url from EmBaseCompactTemplate where cid="+cid+" and ebct_compact_type='"
			+ compact_type + "' group by ebct_url";
	ResultSet rs2 = stmt.executeQuery(sql2);
	Integer cont;
	String ofile;
	cont = 0;
	ofile = "";

	while (rs2.next()) {
		cont = rs2.getInt(1);
		ofile = rs2.getString(2);
	}

	//判断文档是否暂存
	String sql3 = "select ebct_url,ebct_name from CompactVer where ebct_state=1 and ecid=1 and ebct_type='"+compact_type+"'";
	ResultSet rs3 = stmt.executeQuery(sql3);
	String vername;
	vername = "";
	while (rs3.next()) {
		vername = rs3.getString(1);
		session.setAttribute("ver_name", null);
		session.setAttribute("ver_name", rs3.getString(2));
	}

	//如果存在文档，将打开暂存文档，否则打开新文档
	if (cont > 0) {
		poCtrl.webOpen("../"+ ofile,
				OpenModeType.docAdmin, "szciic");
		//设置页面的显示标题
		poCtrl.setCaption("雇员合同--非标准合同--"+compact_type);
	} else {
		poCtrl.webOpen("../" + vername, OpenModeType.docAdmin,
				"szciic");
		//设置页面的显示标题
		poCtrl.setCaption("雇员合同--标准合同--"+compact_type);
	}

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
	
		function Save() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			
			alert('暂存成功！');
		}
		function Send() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			window.location = "../EmBaseCompact/EmBaseCompact_AddDocOk.jsp?ebcc_id=<%=request.getParameter("ebcc_id")%>&user=<%=request.getParameter("user")%>&ebcc_tapr_id=<%=request.getParameter("ebcc_tapr_id")%>";
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
