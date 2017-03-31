<!--
	创建人：彭耀
	创建时间：2014-1-15
	用途：公积金打印清册名单
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.excelwriter.*,java.util.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>

<%!public static String gethjNo(String name) {
		String s = "0";
		if (name.equals("深户")) {
			s = "01";
		} else if (name.equals("非深户城镇")) {
			s = "02";
		} else if (name.equals("非深户农村")) {
			s = "03";
		} else {
			s = "04";
		}
		return s;
	}
	public static String getdegreeNo(String name) {
		String s = "0";
		if (name.equals("博士学位")) {
			s = "01";
		} else if (name.equals("硕士学位")) {
			s = "02";

		} else if (name.equals("学士学位")) {
			s = "03";
		} else {
			s = "04";
		}

		return s;
	}

	public static String getzcNo(String name) {
		String s = "0";
		if (name.equals("正高职称")) {
			s = "010";
		} else if (name.equals("副高职称")) {
			s = "020";

		} else if (name.equals("中级职称")) {
			s = "030";
		} else if (name.equals("初级职称")) {
			s = "040";
		} else {
			s = "050";
		}
		return s;
	}

	public static String getcellValue(String obj) {
		if (obj != null && !obj.toString().equals("")) {
			return obj.toString();
		} else {
			return "";
		}
	}%>
<%
	session.setAttribute("exfileName", null);
	session.setAttribute("exfileName",
			request.getParameter("exfileName"));
	PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath() + "/poserver.do");
	poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
	poCtrl.setSaveFilePage("Save.jsp"); // 设置 save.jsp 用来保存文档。
	//隐藏Office工具条
	poCtrl.setOfficeToolbars(false);
	//隐藏自定义工具栏
	//poCtrl.setCustomToolbar(false);
	//隐藏菜单
	poCtrl.setMenubar(false);
	//设置页面的显示标题
	poCtrl.setCaption("申报列表");
	//添加自定义按钮
	poCtrl.addCustomToolButton("另存", "Saveas", 1);
	poCtrl.addCustomToolButton("打印", "print", 6);

	String cn = request.getParameter("className");
	String sql = "select emhc_id,ownmonth,gid,emhc_name,emhc_idcard,emhc_computerid,emhc_companyid,emhc_houseid,emhc_degree,emhc_title,emhc_radix,emhc_hj,emhc_mobile,emhc_wifename,emhc_wifeidcard from EmHouseChange where emhc_id in ("
			+ request.getParameter("id") + ")";

	ResultSet rs = stmt.executeQuery(sql);
	String fileName = "";
	//定义Workbook对象
	Workbook workBook = new Workbook();
	//定义Sheet对象，"Sheet1"是打开的Excel表单的名称
	Sheet sheet = workBook.openSheet("Sheet1");
	System.out.println(cn);
	if (cn.equals("New")) {
		fileName = "EmHouseNew.xls";
		Table table = sheet.openTable("A4:N4");
		while (rs.next()) {
			table.getDataFields().get(0)
					.setValue(getcellValue(rs.getString("emhc_name")));
			table.getDataFields().get(1).setValue("01");
			table.getDataFields()
					.get(2)
					.setValue(getcellValue(rs.getString("emhc_idcard")));
			table.getDataFields()
					.get(3)
					.setValue(
							getcellValue(rs
									.getString("emhc_computerid")));
			table.getDataFields()
					.get(4)
					.setValue(
							getdegreeNo(getcellValue(rs
									.getString("emhc_degree"))));
			table.getDataFields()
					.get(5)
					.setValue(
							getzcNo(getcellValue(rs
									.getString("emhc_title"))));
			table.getDataFields().get(6)
					.setValue(getcellValue(rs.getString("ownmonth")));
			table.getDataFields().get(7)
					.setValue(getcellValue(rs.getString("emhc_radix")));
			table.getDataFields()
					.get(8)
					.setValue(
							gethjNo(getcellValue(rs
									.getString("emhc_hj"))));
			table.getDataFields()
					.get(9)
					.setValue(getcellValue(rs.getString("emhc_mobile")));
			if (getcellValue(rs.getString("emhc_wifename")).equals("")) {
				table.getDataFields().get(10).setValue("01");
			} else {
				table.getDataFields().get(10).setValue("02");
			}
			table.getDataFields()
					.get(11)
					.setValue(
							getcellValue(rs.getString("emhc_wifename")));
			table.getDataFields()
					.get(12)
					.setValue(
							getcellValue(rs
									.getString("emhc_wifeidcard")));
			table.getDataFields().get(13)
					.setValue(getcellValue(rs.getString("gid")));
			table.nextRow();

		}
		table.close();
	} else if (cn.equals("Transfer")) {
		Table table = sheet.openTable("A3:E3");
		fileName = "EmHouseTransfer.xls";
		while (rs.next()) {

			table.getDataFields()
					.get(0)
					.setValue(
							getcellValue(rs.getString("emhc_houseid")));

			table.getDataFields()
					.get(1)
					.setValue(getcellValue(rs.getString("emhc_idcard")));
			table.getDataFields().get(2)
					.setValue(getcellValue(rs.getString("emhc_radix")));
			table.getDataFields()
					.get(3)
					.setValue(
							getcellValue(rs.getString("emhc_companyid")));
			table.getDataFields().get(4)
					.setValue(getcellValue(rs.getString("emhc_name")));

			table.nextRow();
		}
		table.close();
	} else if (cn.equals("Open")) {
		Table table = sheet.openTable("A3:B3");
		fileName = "EmHouseOpen.xls";
		while (rs.next()) {
			table.getDataFields()
					.get(0)
					.setValue(
							getcellValue(rs.getString("emhc_houseid")));
			table.getDataFields().get(1)
					.setValue(getcellValue(rs.getString("emhc_name")));
			table.nextRow();
		}
		table.close();
	} else if (cn.equals("Stop")) {
		Table table = sheet.openTable("A3:C3");
		fileName = "EmHouseStop.xls";
		while (rs.next()) {
			table.getDataFields()
					.get(0)
					.setValue(
							getcellValue(rs.getString("emhc_houseid")));
			table.getDataFields()
					.get(1)
					.setValue(getcellValue(rs.getString("emhc_idcard")));
			table.getDataFields().get(2)
					.setValue(getcellValue(rs.getString("emhc_name")));
			table.nextRow();
		}
		table.close();
	} else if (cn.equals("Salay")) {
		Table table = sheet.openTable("A2:B2");
		fileName = "EmHouseSalay.xls";
		while (rs.next()) {
			table.getDataFields()
					.get(0)
					.setValue(
							getcellValue(rs.getString("emhc_houseid")));
			table.getDataFields().get(1)
					.setValue(getcellValue(rs.getString("emhc_radix")));
			table.nextRow();
		}
		table.close();
	}

	poCtrl.setWriter(workBook);
	poCtrl.webOpen("../OfficeFile/Templet/EmHouse/" + fileName,
			OpenModeType.xlsNormalEdit, "szciic");
	poCtrl.setTagId("PageOfficeCtrl1"); //此行必须
	
	rs.close();
	stmt.close();
	conn.close();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript">
	function AfterDocumentOpened() {
	document.getElementById("PageOfficeCtrl1").WebSave();
	}

	function Saveas() {
		document.getElementById("PageOfficeCtrl1").ShowDialog(3);
	}
</script>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>

<body>
	<div style="width: auto; height: 480px;">
		<po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
	</div>
</body>
</html>
