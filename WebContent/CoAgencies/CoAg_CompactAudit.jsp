<%@page import="org.omg.CORBA.Request"%>
<%@page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,Model.CoAgencyCompactModel,dal.LoginDal"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
 <jsp:useBean id="cb" class="Controller.CoAgencies.MakeCompactBean"/>
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
	CoAgencyCompactModel model=cb.getModel(Integer.parseInt(request.getParameter("coct_id")));
	//设置页面的显示标题
	poCtrl.setCaption(model.getCoct_type());
	//添加自定义按钮
	poCtrl.addCustomToolButton("退回", "back", 0);
	poCtrl.addCustomToolButton("审核", "Save", 2);
	//设置保存页面
	LoginDal dal = new LoginDal();
	int userid = Integer.parseInt(request.getParameter("userid"));
	String user = dal.getUsernameById(userid);
	poCtrl.setSaveFilePage("SaveFile.jsp");
	String coct_tarpid=request.getParameter("coct_tarpid");
	//判断文档是否暂存
	//如果存在文档，将打开暂存文档，否则打开新文档
	poCtrl.webOpen("../CoAgencies/file/savefile/"+model.getCoct_filename(),
			OpenModeType.docNormalEdit, "szciic");
	//设置保存页面
	poCtrl.setSaveFilePage("../CoAgencies/AuditSaveFile.jsp?coct_id="+model.getCoct_id()+"&userid="+request.getParameter("userid"));

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需
	

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
			window.location = "../CoAgencies/CoAg_CompactAuditOk.jsp?coct_id=<%=model.getCoct_id()%>&userid=<%=request.getParameter("userid")%>";
		}
		function Send() {
			//1:打开   2:保存   3:另存为   4:打印   5:打印设置   6:文件属性 
			document.getElementById("PageOfficeCtrl1").WebSave();
			window.location = "../CoAgencies/CoAg_MakeCompactOk.jsp";
		}
		
		function back() {
			//1:打开   2:保存   3:另存为   4:打印   5:打印设置   6:文件属性 
			document.getElementById("PageOfficeCtrl1").WebSave();
			window.location = "../CoAgencies/CoAg_BackCompactOk.jsp?coct_id=<%=model.getCoct_id()%>&userid=<%=request.getParameter("userid")%>";
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