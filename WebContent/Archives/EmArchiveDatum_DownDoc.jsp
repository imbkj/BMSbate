<!--
	创建人：陈耀家
	创建时间：2013-10-17
	用途：商调函打印详细页面
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,Util.UserInfo,Controller.Archives.Archive_PrintSDHController"
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
	poCtrl.setCaption("档案商调函打印");
	//添加自定义按钮
	poCtrl.addCustomToolButton("另存为", "Send", 1);
	//poCtrl.addCustomToolButton("导出", "Send", 1);
	//设置保存页面
	//poCtrl.setSaveFilePage("../Archives/SDHSaveFile.jsp");

	Integer eada_id = Integer.parseInt(request.getParameter("eada_id"));
	Integer mp = 0;
	if (request.getParameter("mp") != null) {
		mp = Integer.parseInt(request.getParameter("mp"));
	}

	String sqlstr = "select cid,gid,eada_name name,eada_fileplace,(select case when Substring(convert(varchar(10),MAX(eada_sdh)),1,4)=YEAR(GETDATE()) then MAX(eada_sdh)+1 else convert(varchar(10),YEAR(GETDATE()))+ '001' end eada_sdh from EmArchiveDatum where eada_state=1)"
			+ " ,eada_tapr_id,eada_sdh from EmArchiveDatum where eada_state=1 and  eada_id="
			+ eada_id;
	ResultSet rs = stmt.executeQuery(sqlstr);
	//定义WordDocument对象
	WordDocument doc = new WordDocument();
	Calendar c = Calendar.getInstance();
	int y = c.get(Calendar.YEAR); //年
	int m = c.get(Calendar.MONTH) + 1; //月
	int d = c.get(Calendar.DAY_OF_MONTH); //日
	int taprId = 0;
	Integer sdh = 0;
	String userid = UserInfo.getUserid();
	//定义DataTag对象，向区域赋值
	while (rs.next()) {
		
		if(rs.getInt(7)>0){
			sdh=rs.getInt(7);
		}else{
			sdh = rs.getInt(5);
		}
		
		taprId = rs.getInt(6);
		DataTag deptTag = doc.openDataTag("{no}");
		deptTag.setValue("" + y);

		DataTag userTag = doc.openDataTag("{fid}");
		userTag.setValue(sdh.toString());

		DataTag monthTag = doc.openDataTag("{姓名}");
		monthTag.setValue(rs.getString(3));

		DataTag yearTag = doc.openDataTag("{称呼}");
		yearTag.setValue(rs.getString(4));

		DataTag ytag = doc.openDataTag("{年}");
		ytag.setValue("" + y);

		DataTag mtag = doc.openDataTag("{月}");
		mtag.setValue("" + m);

		DataTag dtag = doc.openDataTag("{日}");
		dtag.setValue("" + d);
	}
	poCtrl.setWriter(doc);

	poCtrl.webOpen("../OfficeFile/Templet/Archives/SDH.doc",
			OpenModeType.docNormalEdit, "szciic");

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>商调函信息</title>
</head>
<body>
	<script type="text/javascript">
		function Send() {
			//1:打开   2:保存   3:另存为   4:打印   5:打印设置   6:文件属性 
			document.getElementById("PageOfficeCtrl1").ShowDialog(3);
			//alert(window.location.search.split("=")[1]);
		}
	</script>
	<form id="form1">
		<div style="width: auto; height: 550px;">
			<po:PageOfficeCtrl id="PageOfficeCtrl1">
			</po:PageOfficeCtrl>
		</div>
	</form>
</body>
</html>
