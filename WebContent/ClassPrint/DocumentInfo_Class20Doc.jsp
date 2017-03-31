<!--
	创建人：suhongyuan
	创建时间：2016-5-12
	用途：离职证明（外包）
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../js/Conn.jsp"%>
<%
	PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath() + "/poserver.do");
	//隐藏Office工具条
	poCtrl.setOfficeToolbars(false);
	//隐藏自定义工具栏
	//poCtrl.setCustomToolbar(true);

	//隐藏菜单
	poCtrl.setMenubar(false);
	
	poCtrl.addCustomToolButton("打印", "Print", 1);
	//添加自定义按钮
	//poCtrl.addCustomToolButton("暂存", "Save", 1);
	//poCtrl.addCustomToolButton("发送审核", "Send", 2);
	//设置保存页面
	//poCtrl.setSaveFilePage("../EmBaseCompact/SaveFile.jsp");

	//链接数据库操作开始
    String sql="select distinct a.gid,emba_name,emba_idcard,coba_company,ebco_maturity_date,isnull(ebco_working_station,'') ebco_working_station, "
    		+" cast(year(ebco_incept_date) as varchar(4)) y,cast(month(ebco_incept_date) as varchar(4)) m,cast(day(ebco_incept_date)as varchar(4)) d, "
    		+" cast(year(getdate()) as varchar(4)) yn,cast(month(getdate()) as varchar(4)) mn,cast(day(getdate()) as varchar(4)) dn, "
    		+" year(ebco_maturity_date) ye, month(ebco_maturity_date) mo,day(ebco_maturity_date) da from embase a  "
    		+" inner join coglist b on a.gid=b.gid  "
    		+" inner join CoOfferList c on b.cgli_coli_id=c.coli_id  "
    		+" inner join CoOffer d on c.coli_coof_id=d.coof_id "
    		+" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9  "
    		+" inner join cobase f on a.cid=f.cid "
    		+" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=8 "
    		+" inner join EmBaseCompact j on a.gid=j.gid "
    		+" where a.gid="+Integer.parseInt(request.getParameter("gid"))+" and a.emba_state=0  ";	
	ResultSet rs = stmt.executeQuery(sql);
	System.out.println(sql);
	//定义WordDocument对象
	WordDocument doc = new WordDocument();
	
	//定义DataTag对象，向区域赋值
	if(rs.next()){
    	request.setAttribute("info","存在打印预览数据，请选择操作！");

		DataTag deptTag = doc.openDataTag("{姓名}");
		deptTag.setValue(rs.getString("emba_name"));
		DataTag Tag1 = doc.openDataTag("{身份证号码}");
		Tag1.setValue(rs.getString("emba_idcard"));
		DataTag Tag2 = doc.openDataTag("{year}");
		if(rs.getString("y")!=null){
			Tag2.setValue(rs.getString("y"));
		}else{
			Tag2.setValue(" ");
		}
		DataTag Tag3 = doc.openDataTag("{moth}");
		if(rs.getString("m")!=null){
			Tag3.setValue(rs.getString("m"));
		}else{
			Tag3.setValue(" ");
		}
		DataTag Tag4 = doc.openDataTag("{day}");
		if(rs.getString("d")!=null){
			Tag4.setValue(rs.getString("d"));
		}else{
			Tag4.setValue(" ");
		}
		DataTag Tag5 = doc.openDataTag("{公司}");
		Tag5.setValue(rs.getString("coba_company"));
		DataTag Tag6 = doc.openDataTag("{职位}");
		Tag6.setValue(rs.getString("ebco_working_station"));

		DataTag Tag8 = doc.openDataTag("{year1}");
		if(rs.getString("ye")!=null){
			Tag8.setValue(rs.getString("ye"));
		}else{
			Tag8.setValue(" ");
		}
		DataTag Tag9 = doc.openDataTag("{moth1}");
		if(rs.getString("mo")!=null){
			Tag9.setValue(rs.getString("mo"));
		}else{
			Tag9.setValue(" ");
		}
		DataTag Tag10 = doc.openDataTag("{day1}");
		if(rs.getString("da")!=null){
			Tag10.setValue(rs.getString("da"));
		}else{
			Tag10.setValue(" ");
		}
		DataTag Tag11 = doc.openDataTag("{Y}");
		Tag11.setValue(rs.getString("yn"));
		DataTag Tag12 = doc.openDataTag("{M}");
		Tag12.setValue(rs.getString("mn"));
		DataTag Tag13 = doc.openDataTag("{D}");
		Tag13.setValue(rs.getString("dn"));
    }else{
    	request.setAttribute("info","不存在打印预览数据，请关闭弹出打印预览对话框!");
    }
	//数据库结束

	poCtrl.setWriter(doc);


	//如果存在文档，将打开暂存文档，否则打开新文档

	poCtrl.webOpen("../ClassPrint/class20.doc",
				OpenModeType.docAdmin, "szciic");
		//设置页面的显示标题
	poCtrl.setCaption("离职证明（外包）");


	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	rs.close();
	stmt.close();
	conn.close();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>离职证明（外包）</title>
</head>
<body>
<input id="info" value="${info}" type="hidden"/>
<script type="text/javascript">
	function Print() {
		document.getElementById("PageOfficeCtrl1").PrintPreview();
	}
	//alert(document.getElementById("info").value);
</script>
<form id="form1">
		<div style="width: auto; height: 700px;">
			<po:PageOfficeCtrl id="PageOfficeCtrl1">
			</po:PageOfficeCtrl>
		</div>
</form>
</body>
</html>