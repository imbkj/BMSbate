<%@page import="org.omg.CORBA.Request"%>
<%@page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,Model.CoAgencyCompactModel"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
<jsp:useBean id="cb" class="Controller.CoAgencies.MakeCompactBean" />
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
	CoAgencyCompactModel model = cb.getModel(Integer.parseInt(request
			.getParameter("coct_id")));
	//设置页面的显示标题
	poCtrl.setCaption(model.getCoct_type());
	//添加自定义按钮
	poCtrl.addCustomToolButton("保存", "Save", 2);
	poCtrl.addCustomToolButton("打印", "Print", 4);
	poCtrl.addCustomToolButton("导出", "Export", 3);
	poCtrl.addCustomToolButton("下一步", "Next", 2);
	String coct_tarpid = request.getParameter("coct_tarpid");

	String filename = "../CoAgencies/file/wt.doc";
	if (model.getCoct_type().equals("受托合同")) {
		filename = "../CoAgencies/file/st.doc";
	}
	if(model.getCoct_filename()!=null&&!model.getCoct_filename().equals(""))
	{
		filename="../CoAgencies/file/savefile/"+model.getCoct_filename();
	}

	//定义WordDocument对象
	WordDocument doc = new WordDocument();

	//定义DataTag对象，向区域赋值
	DataTag deptTag = doc.openDataTag("{compact_id}");
	deptTag.setValue(model.getCoct_compactid());
	poCtrl.setWriter(doc);

	//如果存在文档，将打开暂存文档，否则打开新文档
	poCtrl.webOpen(filename,
			OpenModeType.docNormalEdit, "szciic");
	//设置保存页面
	poCtrl.setSaveFilePage("../CoAgencies/SaveFile.jsp?coct_id="
			+ model.getCoct_id() + "&userid="
			+ request.getParameter("userid"));

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需
	int k = 0;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>合同信息</title>
</head>
<body>
	<script type="text/javascript">
		//保存
		function Save() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			//window.location = "../CoAgencies/CoAg_MakeCompactOk.jsp";
			//alert('保存成功！');
		}
		//打印
		function Print() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			document.getElementById("PageOfficeCtrl1").PrintPreview();
			//document.getElementById("PageOfficeCtrl1").WebSave();
			//window.location = "../CoAgencies/CoAg_MakeCompactOk.jsp";
		}
		//导出
		function Export() {
			//1:打开   2:保存   3:另存为   4:打印   5:打印设置   6:文件属性 
			document.getElementById("PageOfficeCtrl1").WebSave();
			document.getElementById("PageOfficeCtrl1").ShowDialog(3);
		}
		//下一步
		function Next() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			//window.location = "../CoAgencies/CoAg_MakeCompactOk.jsp";
			window.location = "../CoAgencies/MakeCompactOk.jsp?coct_id=<%=model.getCoct_id()%>&userid=<%=request.getParameter("userid")%>";
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