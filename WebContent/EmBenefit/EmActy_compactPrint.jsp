<!--
	创建人：彭耀
	创建时间：2014-3-19
	用途：公司合同打印详细页面
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,Util.UserInfo,org.zkoss.zk.ui.Executions"
	pageEncoding="UTF-8"%>
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
	poCtrl.setCaption("供应商合同");
	//添加自定义按钮

	Integer taprId = 0;
	Integer audit = 0;
	Integer daid = Integer.parseInt(request.getParameter("daid"));
	Integer look = Integer.parseInt(request.getParameter("look"));
	Integer taclId = Integer.parseInt(request.getParameter("taclId"));

	session.setAttribute("EAuser", UserInfo.getUsername());
	session.setAttribute("EAcompact_id", daid);

	if (request.getParameter("taprId") != null && !request.getParameter("taprId").equals("")) {
			taprId = Integer.parseInt(request.getParameter("taprId"));
	}
	
	if (request.getParameter("audit") != null && !request.getParameter("audit").equals("")) {
		audit = Integer.parseInt(request.getParameter("audit"));
}
	
	if (look.equals(2) || look.equals(5)) {
		poCtrl.addCustomToolButton("打印", "Save", 6);
		poCtrl.addCustomToolButton("发送法务审核", "Send", 1);
	} else if (look.equals(3)) {
		poCtrl.addCustomToolButton("暂存", "SaveAs", 3);
		poCtrl.addCustomToolButton("审核", "Audit", 2);
		poCtrl.addCustomToolButton("退回", "back", 2);
	}
	

	//设置保存页面
	poCtrl.setSaveFilePage("../EmBenefit/SaveAutFile.jsp");

	//判断文档是否暂存
	String sql2 = "select count(*),puof_url from PubOffice where puof_type=9 and puof_tid="
			+ daid + " group by puof_url";

	System.out.println(sql2);
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
	if (cont > 0) {
		poCtrl.webOpen("../OfficeFile/UpLoad/EmBenefit/" + ofile,
				OpenModeType.docNormalEdit, "szciic");
	} else {
		poCtrl.webOpen("../OfficeFile/Templet/EmBenefit/compact.doc",
				OpenModeType.docNormalEdit, "szciic");
	}

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需
	String user2 = UserInfo.getUsername();
	stmt.close();
	conn.close();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>合同信息</title>
</head>
<body>
	<script type="text/javascript">
		document.getElementById("PageOfficeCtrl1").ShowRevisions =true;
	
		function Save() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			document.getElementById("PageOfficeCtrl1").PrintPreview();
		}
		function Send() {
			//1:打开   2:保存   3:另存为   4:打印   5:打印设置   6:文件属性 
			document.getElementById("PageOfficeCtrl1").WebSave();
			document.getElementById("PageOfficeCtrl1").ShowDialog( 3 ); 
			window.location = "../EmBenefit/EmActy_compactPrintOK.jsp?daid=<%=request.getParameter("daid")%>&id=<%=request.getParameter("taprId")%>&state=3&c=2&taclId=<%=request.getParameter("taclId")%>";
		}
		
		
		function Audit(){
			document.getElementById("PageOfficeCtrl1").WebSave();
			window.location = "../EmBenefit/EmActy_compactPrintOK.jsp?daid=<%=request.getParameter("daid")%>&id=<%=request.getParameter("taprId")%>&state=4&c=3&taclId=<%=request.getParameter("taclId")%>";

		}
		
		function back(){
			document.getElementById("PageOfficeCtrl1").WebSave();
			//window.location = "../EmBenefit/EmActy_compactPrintOK.jsp?daid=<%=request.getParameter("daid")%>&id=<%=request.getParameter("taprId")%>&state=5&c=5&taclId=<%=request.getParameter("taclId")%>";
			window.location.href = "../EmBenefit/EmActy_BackReason.zul?daid=<%=request.getParameter("daid")%>&id=<%=request.getParameter("taprId")%>&state=5&c=5&taclId=<%=request.getParameter("taclId")%>";
		}
		

		function SaveAs() {
			document.getElementById("PageOfficeCtrl1").WebSave();
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
