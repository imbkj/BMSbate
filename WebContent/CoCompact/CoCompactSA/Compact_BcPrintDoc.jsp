<!--
	创建人：张志强
	创建时间：2013-11-22
	用途：公司合同制作详细页面
-->
<%@page import="Controller.systemWindowController"%>
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../../js/Conn.jsp"%>
<%
	System.out.print(request.getParameter("coco_tapr_id"));
	PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath() + "/poserver.do");
	//隐藏Office工具条
	poCtrl.setOfficeToolbars(true);
	//隐藏自定义工具栏
	//poCtrl.setCustomToolbar(false);
	//隐藏菜单
	poCtrl.setMenubar(false);
	//设置页面的显示标题
	poCtrl.setCaption("公司合同");
	//添加自定义按钮
	poCtrl.addCustomToolButton("导出", "Save", 6);
	poCtrl.addCustomToolButton("打印", "Print", 1);
	poCtrl.addCustomToolButton("申请盖章", "Send", 1);
	//设置保存页面
	poCtrl.setSaveFilePage("../CoCompact/CoCompactSA/SaveFile.jsp");

	//链接数据库操作开始
	String sql = "select coco_compactid,isnull(cola_company,''),year(getdate()) year,month(getdate()) month,day(getdate()) day from cocompact a left join CoLatencyClient b on b.cola_id=a.coco_cola_id where coco_id="
			+ request.getParameter("coco_id");
	ResultSet rs = stmt.executeQuery(sql);

	//数据库结束

	//定义WordDocument对象
	WordDocument doc = new WordDocument();

	//定义DataTag对象，向区域赋值
	while (rs.next()) {
		DataTag deptTag = doc.openDataTag("{compact_id}");
		deptTag.setValue(rs.getString(1));

		DataTag userTag = doc.openDataTag("{company}");
		userTag.setValue(rs.getString(2));

		DataTag yearTag = doc.openDataTag("{year}");
		yearTag.setValue(rs.getString(3));

		DataTag monthTag = doc.openDataTag("{month}");
		monthTag.setValue(rs.getString(4));

		DataTag dayTag = doc.openDataTag("{day}");
		dayTag.setValue(rs.getString(5));
	}

	poCtrl.setWriter(doc);

	//判断文档是否暂存
	String sql2 = "select count(*),puof_url from PubOffice where puof_pute_id=1 and puof_tid="
			+ request.getParameter("coco_id") + " group by puof_url";
	System.out.print(sql2);
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
	
		poCtrl.webOpen("../" + ofile,
				OpenModeType.docNormalEdit, "szciic");
	

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	rs.close();
	stmt.close();
	conn.close();
	session.setAttribute("compact_id",null);
	session.setAttribute("compact_id",request.getParameter("coco_id"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>合同信息</title>
</head>
<body>
	<script type="text/javascript">		
	function Print() {
		document.getElementById("PageOfficeCtrl1").PrintPreview();
	}
		rwd=0;
		function Save() {
			document.getElementById("PageOfficeCtrl1").ShowDialog( 3 ); 
			rwd=1;
			window.location = "../CoCompact/CoCompactSA/Compact_BcPrintOk.jsp?coco_id=<%=request.getParameter("coco_id")%>&coco_tapr_id=<%=request.getParameter("coco_tapr_id")%>&user=<%=request.getParameter("user")%>&rwd="+rwd;
		}
		function Send() {
			//1:打开   2:保存   3:另存为   4:打印   5:打印设置   6:文件属性 
			
			rwd=2;
			window.location = "../CoCompact/CoCompactSA/Compact_BcPrintOk.jsp?coco_id=<%=request.getParameter("coco_id")%>&coco_tapr_id=<%=request.getParameter("coco_tapr_id")%>&user=<%=request.getParameter("user")%>&rwd="+rwd;
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
