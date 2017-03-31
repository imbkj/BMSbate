<!--
	创建人：suhongyuan
	创建时间：2016-12-22
	用途：缴纳证明
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
    String sql="select a.gid,a.cid,emba_name,emba_idcard,b.coba_company,c.coab_name,d.sb_s_month,d.sb_e_month,e.gjj_s_month,e.gjj_e_month,year(GETDATE()) as y,MONTH(GETDATE()) as m, day(GETDATE()) as d from embase a "
    		+" left join cobase b on a.cid=b.CID "
    		+" left join ( "
    		+"  select a.gid,e.coab_name from coglist a inner join CoOfferList b on a.cgli_coli_id=b.coli_id "
    		+"  inner join CoOffer c on b.coli_coof_id=c.coof_id "
    		+"  inner join CoCompact d on c.coof_coco_id=d.coco_id "
    		+"  inner join StAgencyBase_view e on d.cabc_id=e.coab_id "
    		+"  where coli_name in('社会保险服务','住房公积金服务','人事基础服务费') and gid="+Integer.parseInt(request.getParameter("gid"))+" "
    		+"  group by a.gid,e.coab_name "
    		+" )c on a.gid=c.gid "
    		+" left join "
    		+" (select gid,MIN(ownmonth)sb_s_month,MAX(ownmonth)sb_e_month "
    		+" from "
    		+" ( "
    		+" select GID,Ownmonth from emshebao where GID="+Integer.parseInt(request.getParameter("gid"))+" "
    		+" union all "
    		+" select gid,ownmonth from EmShebaoUpdate where Esiu_IfStop=0 and GID="+Integer.parseInt(request.getParameter("gid"))+" "
    		+" )a "
    		+" group by GID "
    		+" )d on a.gid=d.gid "
    		+" left join "
    		+" ( "
    		+" select gid,MIN(ownmonth)gjj_s_month,MAX(ownmonth)gjj_e_month "
    		+" from "
    		+" ( "
    		+" select GID,Ownmonth from emhouse where GID="+Integer.parseInt(request.getParameter("gid"))+" "
    		+" union all "
    		+" select gid,ownmonth from EmhouseUpdate where Emhu_IfStop=0 and GID="+Integer.parseInt(request.getParameter("gid"))+" "
    		+" )b "
    		+" group by GID "
    		+" )e on a.gid=e.gid "
    		+" where a.gid="+Integer.parseInt(request.getParameter("gid"))+" ";	
	ResultSet rs = stmt.executeQuery(sql);
	System.out.println(sql);
	//定义WordDocument对象
	WordDocument doc = new WordDocument();
	
	//定义DataTag对象，向区域赋值
	if(rs.next()){
    	request.setAttribute("info","存在打印预览数据，请选择操作！");
		DataTag deptTag = doc.openDataTag("{委托公司}");
		if(rs.getString("coab_name")!=null&&rs.getString("coab_name")!=""){
			deptTag.setValue(rs.getString("coab_name"));
		}else{
			deptTag.setValue("   ");
		}
		DataTag deptTag1 = doc.openDataTag("{所属公司}");
        if(rs.getString("coba_company")!=null&&rs.getString("coba_company")!=""){
        	deptTag1.setValue(rs.getString("coba_company"));
		}else{
			deptTag1.setValue("   ");
		}
		DataTag deptTag2 = doc.openDataTag("{姓名}");
        if(rs.getString("emba_name")!=null&&rs.getString("emba_name")!=""){
        	deptTag2.setValue(rs.getString("emba_name"));
		}else{
			deptTag2.setValue("   ");
		}
        DataTag deptTag3 = doc.openDataTag("{身份证}");
        if(rs.getString("emba_idcard")!=null&&rs.getString("emba_idcard")!=""){
        	deptTag3.setValue(rs.getString("emba_idcard"));
		}else{
			deptTag3.setValue("   ");
		}
		DataTag deptTag4 = doc.openDataTag("{社保}");
		if(rs.getString("sb_s_month")!=null&&rs.getString("sb_s_month")!=""&&rs.getString("sb_e_month")!=null&&rs.getString("sb_e_month")!=""){
			deptTag4.setValue("自"+rs.getString("sb_s_month").substring(0, rs.getString("sb_s_month").length()-2)+"年"+rs.getString("sb_s_month").substring(rs.getString("sb_s_month").length()-2,rs.getString("sb_s_month").length())+"月至"+rs.getString("sb_e_month").substring(0, rs.getString("sb_e_month").length()-2)+"年"+rs.getString("sb_e_month").substring(rs.getString("sb_e_month").length()-2,rs.getString("sb_e_month").length())+"月在深圳办理了社会保险的缴纳手续。");
		}else{
			deptTag4.setValue(" ");
		}
		DataTag deptTag5 = doc.openDataTag("{公积金}");
		if(rs.getString("gjj_s_month")!=null&&rs.getString("gjj_s_month")!=""&&rs.getString("gjj_e_month")!=null&&rs.getString("gjj_e_month")!=""){
			deptTag5.setValue("自"+rs.getString("gjj_s_month").substring(0, rs.getString("gjj_s_month").length()-2)+"年"+rs.getString("gjj_s_month").substring(rs.getString("gjj_s_month").length()-2,rs.getString("gjj_s_month").length())+"月至"+rs.getString("gjj_e_month").substring(0, rs.getString("gjj_e_month").length()-2)+"年"+rs.getString("gjj_e_month").substring(rs.getString("gjj_e_month").length()-2,rs.getString("gjj_e_month").length())+"月在深圳办理了住房公积金的缴纳手续。");
		}else{
			deptTag5.setValue(" ");
		}
		DataTag deptTag6 = doc.openDataTag("{year}");
		deptTag6.setValue(rs.getString("y"));
		DataTag deptTag7 = doc.openDataTag("{moth}");
		deptTag7.setValue(rs.getString("m"));
		DataTag deptTag8 = doc.openDataTag("{day}");
		deptTag8.setValue(rs.getString("d"));
    }else{
    	request.setAttribute("info","不存在打印预览数据，请关闭弹出打印预览对话框!");
    }
	//数据库结束

	poCtrl.setWriter(doc);


	//如果存在文档，将打开暂存文档，否则打开新文档

	poCtrl.webOpen("../ClassPrint/class21.doc",
				OpenModeType.docAdmin, "szciic");
		//设置页面的显示标题
	poCtrl.setCaption("缴纳证明");


	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	rs.close();
	stmt.close();
	conn.close();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>缴纳证明</title>
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