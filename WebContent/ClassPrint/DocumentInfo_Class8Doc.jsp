<!--
	创建人：suhongyuan
	创建时间：2016-5-18
	用途：开具户口无法接收证明
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
//     String sql="select top 1 coba_company,emba_name,coab_name,h.emhu_houseid,h.emhu_idcard,i.Ownmonth,cast(year(getdate()) as varchar(4)) y,cast(month(getdate()) as varchar(4)) m,cast(day(getdate()) as varchar(4)) d  "
//     		 +" from embase a "
//     		 +" inner join coglist b on a.gid=b.gid and b.cgli_stopdate is null "
//     		 +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id   "
//     		 +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
//     		 +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_wttype=1 "
//     		 +" inner join cobase f on a.cid=f.cid "
//     		 +" inner join CoAgencyBase g on e.cabc_id=g.coab_id "
    		 
//     		 +" inner join emhouse i on a.gid=i.GID where a.gid="+Integer.parseInt(request.getParameter("gid"))+" order by i.Ownmonth desc";
	
    String sql="select top 1 coba_company,emba_name,coab_name,h.Esiu_ComputerID,h.esiu_idcard,i.Ownmonth,cast(year(getdate()) as varchar(4)) y, "
    		+" cast(month(getdate()) as varchar(4)) m,cast(day(getdate()) as varchar(4)) d from embase a  "
    		+" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null "
    		+" inner join CoOfferList c on b.cgli_coli_id=c.coli_id and coli_name='社会保险服务' "
    		+" inner join CoOffer d on c.coli_coof_id=d.coof_id "
    		+" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_shebao!='独立开户'  "
    		+" inner join cobase f on a.cid=f.cid "
    		+" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=6 "
    		+" inner join EmShebaoUpdate h on a.gid=h.GID and Esiu_IfStop=0 " 
    		+" inner join CoAgencyBase y on e.cabc_id=y.coab_id  "
    		+" left join emshebao i on a.gid=i.GID "
    		+" where a.gid="+Integer.parseInt(request.getParameter("gid"))+" and a.emba_state in(1,2,5) order by i.Ownmonth ";
	ResultSet rs = stmt.executeQuery(sql);
	System.out.println(sql);
	//定义WordDocument对象
	WordDocument doc = new WordDocument();
	
	//定义DataTag对象，向区域赋值
	if(rs.next()){
    	request.setAttribute("info","存在打印预览数据，请选择操作！");
    	DataTag deptTag = doc.openDataTag("{委托机构}");
		deptTag.setValue(rs.getString(3));
		DataTag deptTag1 = doc.openDataTag("{员工公司名称}");
		deptTag1.setValue(rs.getString(1));
		DataTag deptTag2 = doc.openDataTag("{员工}");
		deptTag2.setValue(rs.getString(2));
		DataTag deptTag3 = doc.openDataTag("{身份证}");
		if(rs.getString(5)!=null){
			deptTag3.setValue(rs.getString(5));
		}else{
			deptTag3.setValue("  ");
		}
		DataTag deptTag4 = doc.openDataTag("{Y}");
		deptTag4.setValue(rs.getString(7));
		DataTag deptTag5 = doc.openDataTag("{M}");
		deptTag5.setValue(rs.getString(8));
		DataTag deptTag6 = doc.openDataTag("{D}");
		deptTag6.setValue(rs.getString(9));
    }else{
    	request.setAttribute("info","不存在打印预览数据，请关闭弹出打印预览对话框!");
    }

	//数据库结束

	poCtrl.setWriter(doc);


	//如果存在文档，将打开暂存文档，否则打开新文档

	poCtrl.webOpen("../ClassPrint/class8.doc",
				OpenModeType.docAdmin, "szciic");
		//设置页面的显示标题
	poCtrl.setCaption("开具户口无法接收证明");


	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	rs.close();
	stmt.close();
	conn.close();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>开具户口无法接收证明</title>
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