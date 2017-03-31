<!--
	创建人：suhongyuan
	创建时间：2016-5-12
	用途：派遣证明
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
    String sql="select  distinct emba_name,emba_idcard , cast(year(getdate()) as varchar(4)) y,cast(month(getdate()) as varchar(4)) m, " 
    		+" cast(day(getdate()) as varchar(4)) d,year(emba_indate) ye, month(emba_indate) mo,day(emba_indate) da,emba_sex " 
    		+" from embase a  " 
    		+" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null " 
    		+" inner join CoOfferList c on b.cgli_coli_id=c.coli_id "  
    		+" inner join CoOffer d on c.coli_coof_id=d.coof_id " 
    		+" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_compacttype in ('af','fs-2')  " 
    		+" inner join cobase f on a.cid=f.cid " 
    		+" inner join login g on f.coba_client=g.log_name and log_inure=1 "  
    		+" inner join EmShebaoUpdate h on a.gid=h.GID and Esiu_IfStop=0 "  
    		+" inner join EmBaseCompact j on a.gid=j.gid and ebco_filing_date is not null " 
    		+" where a.gid="+Integer.parseInt(request.getParameter("gid"))+" and a.emba_state in(1,2,5) ";
	ResultSet rs = stmt.executeQuery(sql);
	System.out.println(sql);
	//定义WordDocument对象
		WordDocument doc = new WordDocument();
		
	//定义DataTag对象，向区域赋值
	if(rs.next()){
    	request.setAttribute("info","存在打印预览数据，请选择操作！");
    	DataTag deptTag = doc.openDataTag("{姓名}");
		deptTag.setValue(rs.getString(1));
		DataTag Tag1 = doc.openDataTag("{身份证号码}");
		Tag1.setValue(rs.getString(2));
		DataTag Tag2 = doc.openDataTag("{Y}");
		Tag2.setValue(rs.getString(3));
		DataTag Tag3 = doc.openDataTag("{M}");
		Tag3.setValue(rs.getString(4));
		DataTag Tag4 = doc.openDataTag("{D}");
		Tag4.setValue(rs.getString(5));
		DataTag Tag5 = doc.openDataTag("{year}");
		if(rs.getString(6)!=null){
			Tag5.setValue(rs.getString(6));
		}else{
			Tag5.setValue("  ");
		}
		DataTag Tag6 = doc.openDataTag("{moth}");
		if(rs.getString(7)!=null){
			Tag6.setValue(rs.getString(7));
		}else{
			Tag6.setValue("  ");
		}
		DataTag Tag7 = doc.openDataTag("{day}");
		if(rs.getString(8)!=null){
			Tag7.setValue(rs.getString(8));
		}else{
			Tag7.setValue("  ");
		}
		DataTag Tag8 = doc.openDataTag("{性别}");
		Tag8.setValue(rs.getString(9));
		
    }else{
    	request.setAttribute("info","不存在打印预览数据，请关闭弹出打印预览对话框!");
    }
	//数据库结束

	poCtrl.setWriter(doc);


	//如果存在文档，将打开暂存文档，否则打开新文档

	poCtrl.webOpen("../ClassPrint/class5.doc",
				OpenModeType.docAdmin, "szciic");
		//设置页面的显示标题
	poCtrl.setCaption("派遣证明");


	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	rs.close();
	stmt.close();
	conn.close();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>派遣证明</title>
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