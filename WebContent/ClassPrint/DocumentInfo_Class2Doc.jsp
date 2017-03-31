<!--
	创建人：suhongyuan
	创建时间：2016-5-11
	用途：深圳市住房公积金个人账户异地转入申请表
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
    String sql="select cohf_bankgj,cohf_houseid,cohf_company,emhu_houseid,emhu_name,emhu_idcard "
    		 +" from embase a "
    		 +" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null "
    		 +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id and coli_name='住房公积金服务' "
    		 +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
    		 +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_shebao!='独立开户' "
    		 +" inner join cobase f on a.cid=f.cid "
    		 +" inner join login g on f.coba_client=g.log_name and log_inure=1 "
    		 +" inner join (select m.cohf_bankgj,cohf_houseid,cohf_company,emhu_houseid,emhu_name,emhu_idcard,GID,Emhu_IfStop "
    		 +" from EmHouseUpdate k inner join CoHousingFund m on k.emhu_companyid=m.cohf_houseid ) i on a.gid=i.GID and i.Emhu_IfStop=0 "
    		 +" where a.gid="+Integer.parseInt(request.getParameter("gid"))+" and a.emba_state in(1,2,5) ";
	ResultSet rs = stmt.executeQuery(sql);
	System.out.println(sql);
	//定义WordDocument对象
	WordDocument doc = new WordDocument();
	if(rs.next()){
    	request.setAttribute("info","存在打印预览数据，请选择操作！");
    	/**DataTag deptTag = doc.openDataTag("{收款单位开户银行及账号}");
		*/
		DataTag b1 = doc.openDataTag("{1}");
		DataTag b2 = doc.openDataTag("{2}");
		DataTag b3 = doc.openDataTag("{3}");
		DataTag b4 = doc.openDataTag("{4}");
		DataTag b5 = doc.openDataTag("{5}");
		DataTag b6 = doc.openDataTag("{6}");
		DataTag b7 = doc.openDataTag("{7}");
		b1.setValue("□");
		b2.setValue("□");
		b3.setValue("□");
		b4.setValue("□");
		b5.setValue("□");
		b6.setValue("□");
		b7.setValue("□");
		if(rs.getString(1).subSequence(0, 4).equals("工商银行")||rs.getString(1).subSequence(0, 2).equals("工行")){
			b1.setValue("√");
			//deptTag.setValue("工商银行深圳红围支行:4000021229200546349");
		}else if(rs.getString(1).subSequence(0, 4).equals("建设银行")||rs.getString(1).subSequence(0, 2).equals("建行")){
			b2.setValue("√");
			//deptTag.setValue("建设银行深圳分行:44201501100052523456");
		}else if(rs.getString(1).subSequence(0, 4).equals("招商银行")||rs.getString(1).subSequence(0, 2).equals("招行")){
			b7.setValue("√");
			//deptTag.setValue("招商银行深圳分行:755917515810666");
		}else if(rs.getString(1).subSequence(0, 4).equals("中国银行")||rs.getString(1).subSequence(0, 2).equals("中行")){
			b3.setValue("√");
			//deptTag.setValue("中国银行深圳上步支行:771857930564");
		}else if(rs.getString(1).subSequence(0, 4).equals("中信银行")){
			b4.setValue("√");
			//deptTag.setValue("中信银行深圳香蜜湖支行:7442710196000000164");
		}else if(rs.getString(1).subSequence(0, 4).equals("兴业银行")){
			b5.setValue("√");
			//deptTag.setValue("兴业银行深圳科技园支行:338040100100220261");
		}else if(rs.getString(1).subSequence(0, 4).equals("交通银行")||rs.getString(1).subSequence(0, 2).equals("交行")){
			b6.setValue("√");
			//deptTag.setValue("交通银行深圳华侨城支行:443066333018150030581");
		}else{
			
		}
		
		DataTag b8 = doc.openDataTag("{转入单位住房公积金号}");
		b8.setValue(rs.getString("cohf_houseid"));
		DataTag b9= doc.openDataTag("{转入单位名称}");
		b9.setValue(rs.getString("cohf_company"));
		DataTag b10 = doc.openDataTag("{深圳市住房公积金管理中心个人住房公积金账号}");
		if(rs.getString("emhu_houseid")!=null){
			b10.setValue(rs.getString("emhu_houseid"));
		}else{
			b10.setValue("  ");
		}
		DataTag b11 = doc.openDataTag("{姓名}");
		b11.setValue(rs.getString("emhu_name"));
		DataTag b12 = doc.openDataTag("{身份证号码}");
		b12.setValue(rs.getString("emhu_idcard"));
    }else{
    	request.setAttribute("info","不存在打印预览数据，请关闭弹出打印预览对话框!");
    }
	//数据库结束

	poCtrl.setWriter(doc);


	//如果存在文档，将打开暂存文档，否则打开新文档

	poCtrl.webOpen("../ClassPrint/class2.doc",
				OpenModeType.docAdmin, "szciic");
		//设置页面的显示标题
	poCtrl.setCaption("深圳市住房公积金个人账户异地转入申请表");


	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	rs.close();
	stmt.close();
	conn.close();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>深圳市住房公积金个人账户异地转入申请表</title>
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