<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同制作详细页面
-->
<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="../js/Conn.jsp"%>
<%
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
	poCtrl.addCustomToolButton("暂存", "Save", 1);
	poCtrl.addCustomToolButton("发送审核", "Send", 2);
	//设置保存页面
	poCtrl.setSaveFilePage("../CoCompact/SaveFile.jsp");

	String co_id = request.getParameter("coco_id");
	//链接数据库操作开始
	String sql = "select coco_compactid,isnull(cola_company,''),year(getdate()) year,month(getdate()) month,day(getdate()) day,coco_compacttype,coco_compactclass from cocompact a left join CoLatencyClient b on b.cola_id=a.coco_cola_id where coco_id="
			+ co_id;
	ResultSet rs = stmt.executeQuery(sql);
	System.out.println(sql);
	String coco_type = "0";
	String compact_id = "";
	String company = "";
	String year1 = "";
	String month1 = "";
	String day1 = "";
	String co_type="";
	String co_class="";
	while (rs.next()) {
		compact_id = rs.getString(1);
		System.out.println(compact_id);
		company = rs.getString(2);
		year1 = rs.getString(3);
		month1 = rs.getString(4);
		day1 = rs.getString(5);
		co_type=rs.getString("coco_compacttype");
		co_class=rs.getString("coco_compactclass");

		if (rs.getString("coco_compacttype").equals("AF")
				|| rs.getString("coco_compacttype").equals("FS-2")) {
			coco_type = "1";
		} else {
			coco_type = "2";
		}
	}
	System.out.println(coco_type);
	String sql1 = "select * from CoOffer a left join CoOfferList b on b.coli_coof_id=a.coof_id where coli_coco_id="
			+ co_id;
	ResultSet rs1 = stmt.executeQuery(sql1);

	String sb_state = "0";
	String file_state = "0";
	String yg_state = "0";
	String sy_state = "0";
	String tj_state = "0";
	String hd_state = "0";
	String gz_state = "0";
	String zx_state = "0";
	String bx_state = "0";
	int j=0;
	while (rs1.next()) {
		j=j+1;
		if (rs1.getString("coli_name").equals("社会保险服务")) {
			sb_state = "1";
		}

		if (rs1.getString("coli_name").equals("档案管理")) {
			file_state = "1";
		}
		
		if (rs1.getString("coli_name").equals("劳动用工手续办理")) {
			yg_state = "1";
		}
		
		if (rs1.getString("coli_pclass").equals("商业保险")) {
			sy_state = "1";
		}
		
		if (rs1.getString("coli_pclass").equals("体检")) {
			tj_state = "1";
		}
		
		if (rs1.getString("coli_pclass").equals("员工福利")) {
			hd_state = "1";
		}
		
		if (rs1.getString("coli_name").equals("政策咨询")) {
			gz_state = "1";
		}
		
		if (rs1.getString("coli_pclass").equals("财税服务")) {
			zx_state = "1";
		}
		if(rs1.getString("coli_name").equals("报销费用发放")){
			bx_state="1";
		}
	}
System.out.println(file_state);
	//数据库结束

	//定义WordDocument对象
	WordDocument doc = new WordDocument();

	//定义DataTag对象，向区域赋值
	
	int i=0;

	if (coco_type.equals("1")) {
		DataTag deptTag = doc.openDataTag("{compact_id}");
		deptTag.setValue(compact_id.toString());

		DataTag userTag = doc.openDataTag("{company}");
		userTag.setValue(company);

		DataTag yearTag = doc.openDataTag("{year}");
		yearTag.setValue(year1);

		DataTag monthTag = doc.openDataTag("{month}");
		monthTag.setValue(month1);

		DataTag dayTag = doc.openDataTag("{day}");
		dayTag.setValue(day1);
	} else {
		DataTag deptTag = doc.openDataTag("{compact_id}");
		deptTag.setValue(compact_id.toString());

		DataTag userTag = doc.openDataTag("{company}");
		userTag.setValue(company);
		
		if (sb_state.equals("1")) {
			i=i+1;
			DataTag sbTag = doc.openDataTag("{sb_state}");
			sbTag.setValue(i+"、	社会保险以及住房公积金的申报和管理");
			DataTag hc1Tag = doc.openDataTag("{hc1}");
			hc1Tag.setValue("\r\n");
			DataTag sb1Tag = doc.openDataTag("{sb_1}");
			sb1Tag.setValue("（1）	乙方将参照甲方确定的员工投保所在地政府公布的险种及费率,同时根据甲方确定的保险种类和费用标准为甲方员工在当地有关管理机构办理养老保险、医疗保险、工伤保险、失业保险、生育保险以及住房公积金等福利保险项目的日常缴费等手续；");
			DataTag hc2Tag = doc.openDataTag("{hc2}");
			hc2Tag.setValue("\r\n");
			DataTag sb2Tag = doc.openDataTag("{sb_2}");
			sb2Tag.setValue("（2）	为甲方员工办理劳动保障卡（政府费用由甲方承担）；");
			DataTag hc3Tag = doc.openDataTag("{hc3}");
			hc3Tag.setValue("\r\n");
			DataTag sb3Tag = doc.openDataTag("{sb_3}");
			sb3Tag.setValue("（3）	根据甲方要求，为甲方员工办理养老保险的转移及社保档案的变更；");
			DataTag hc4Tag = doc.openDataTag("{hc4}");
			hc4Tag.setValue("\r\n");
			DataTag sb4Tag = doc.openDataTag("{sb_4}");
			sb4Tag.setValue("（4）	如甲方员工发生工伤，按照政策规定为甲方员工办理工伤认定及工伤待遇的申报手续；");
			DataTag hc5Tag = doc.openDataTag("{hc5}");
			hc5Tag.setValue("\r\n");
			DataTag sb5Tag = doc.openDataTag("{sb_5}");
			sb5Tag.setValue("（5）	为符合条件的甲方员工办理异地医疗报备及医疗费用报销；");
			DataTag hc6Tag = doc.openDataTag("{hc6}");
			hc6Tag.setValue("\r\n");
			DataTag sb6Tag = doc.openDataTag("{sb_6}");
			sb6Tag.setValue("（6）	按照政策规定出具与本代理服务相关的证明。");
			DataTag hc7Tag = doc.openDataTag("{hc7}");
			hc7Tag.setValue("\r\n");
			
			DataTag sb_feeTag = doc.openDataTag("{sb_fee}");
			sb_feeTag.setValue("社会保险费（包括应由甲方承担的部分及甲方代扣的应由员工个人承担的部分）");
			DataTag house_feeTag = doc.openDataTag("{house_fee}");
			house_feeTag.setValue("住房公积金");
			DataTag fh1Tag = doc.openDataTag("{fh1}");
			fh1Tag.setValue("、");
			if(i!=j){
				DataTag fh2Tag = doc.openDataTag("{fh2}");
				fh2Tag.setValue("、");
			}
		}else{
			DataTag sbTag = doc.openDataTag("{sb_state}");
			sbTag.setValue("");
			DataTag hc1Tag = doc.openDataTag("{hc1}");
			hc1Tag.setValue("");
			DataTag sb1Tag = doc.openDataTag("{sb_1}");
			sb1Tag.setValue("");
			DataTag hc2Tag = doc.openDataTag("{hc2}");
			hc2Tag.setValue("");
			DataTag sb2Tag = doc.openDataTag("{sb_2}");
			sb2Tag.setValue("");
			DataTag hc3Tag = doc.openDataTag("{hc3}");
			hc3Tag.setValue("");
			DataTag sb3Tag = doc.openDataTag("{sb_3}");
			sb3Tag.setValue("");
			DataTag hc4Tag = doc.openDataTag("{hc4}");
			hc4Tag.setValue("");
			DataTag sb4Tag = doc.openDataTag("{sb_4}");
			sb4Tag.setValue("");
			DataTag hc5Tag = doc.openDataTag("{hc5}");
			hc5Tag.setValue("");
			DataTag sb5Tag = doc.openDataTag("{sb_5}");
			sb5Tag.setValue("");
			DataTag hc6Tag = doc.openDataTag("{hc6}");
			hc6Tag.setValue("");
			DataTag sb6Tag = doc.openDataTag("{sb_6}");
			sb6Tag.setValue("");
			DataTag hc7Tag = doc.openDataTag("{hc7}");
			hc7Tag.setValue("");
			
			DataTag sb_feeTag = doc.openDataTag("{sb_fee}");
			sb_feeTag.setValue("");
			DataTag house_feeTag = doc.openDataTag("{house_fee}");
			house_feeTag.setValue("");
			DataTag fh1Tag = doc.openDataTag("{fh1}");
			fh1Tag.setValue("");
			DataTag fh2Tag = doc.openDataTag("{fh2}");
			fh2Tag.setValue("");
			
		}
		if (file_state.equals("1")) {
			i=i+1;
			DataTag fileTag = doc.openDataTag("{file_state}");
			fileTag.setValue(i+"、	人事档案管理");
			DataTag hc8Tag = doc.openDataTag("{hc8}");
			hc8Tag.setValue("\r\n");
			DataTag file1Tag = doc.openDataTag("{file_1}");
			file1Tag.setValue("乙方根据甲方的委托要求，按政策规定为甲方指定员工（或深圳户籍员工）（请选择）提供综合的人事档案管理服务，内容包括：");
			DataTag hc9Tag = doc.openDataTag("{hc9}");
			hc9Tag.setValue("\r\n");
			DataTag file2Tag = doc.openDataTag("{file_2}");
			file2Tag.setValue("（1）	接收和调转甲方员工的档案和行政关系；");
			DataTag hc10Tag = doc.openDataTag("{hc10}");
			hc10Tag.setValue("\r\n");
			DataTag file3Tag = doc.openDataTag("{file_3}");
			file3Tag.setValue("（2）	保留原有身份，连续计算工龄；");
			DataTag hc11Tag = doc.openDataTag("{hc11}");
			hc11Tag.setValue("\r\n");
			DataTag file4Tag = doc.openDataTag("{file_4}");
			file4Tag.setValue("（3）	整理归档材料；");
			DataTag hc12Tag = doc.openDataTag("{hc12}");
			hc12Tag.setValue("\r\n");
			DataTag file5Tag = doc.openDataTag("{file_5}");
			file5Tag.setValue("（4）	协助甲方员工的技术职称评定（与此相关的评审费及职称考试费等政府费用由员工本人承担）；");
			DataTag hc13Tag = doc.openDataTag("{hc13}");
			hc13Tag.setValue("\r\n");
			DataTag file6Tag = doc.openDataTag("{file_6}");
			file6Tag.setValue("（5）	根据档案的记载并结合政策规定出具相关证明；");
			DataTag hc14Tag = doc.openDataTag("{hc14}");
			hc14Tag.setValue("\r\n");
			DataTag file7Tag = doc.openDataTag("{file_7}");
			file7Tag.setValue("（6）	协助甲方办理离职员工的档案调转等相应手续；");
			DataTag hc15Tag = doc.openDataTag("{hc15}");
			hc15Tag.setValue("\r\n");
			DataTag file8Tag = doc.openDataTag("{file_8}");
			file8Tag.setValue("（7）	在未得到甲方确认的情况下，不办理甲方员工的档案调转。");
			DataTag hc16Tag = doc.openDataTag("{hc16}");
			hc16Tag.setValue("\r\n");
			
			DataTag sb_feeTag = doc.openDataTag("{file_fee}");
			sb_feeTag.setValue("档案费");
			if(i!=j){
				DataTag fh3Tag = doc.openDataTag("{fh3}");
				fh3Tag.setValue("、");
			}
		}else{
			DataTag fileTag = doc.openDataTag("{file_state}");
			fileTag.setValue("");
			DataTag hc8Tag = doc.openDataTag("{hc8}");
			hc8Tag.setValue("");
			DataTag file1Tag = doc.openDataTag("{file_1}");
			file1Tag.setValue("");
			DataTag hc9Tag = doc.openDataTag("{hc9}");
			hc9Tag.setValue("");
			DataTag file2Tag = doc.openDataTag("{file_2}");
			file2Tag.setValue("");
			DataTag hc10Tag = doc.openDataTag("{hc10}");
			hc10Tag.setValue("");
			DataTag file3Tag = doc.openDataTag("{file_3}");
			file3Tag.setValue("");
			DataTag hc11Tag = doc.openDataTag("{hc11}");
			hc11Tag.setValue("");
			DataTag file4Tag = doc.openDataTag("{file_4}");
			file4Tag.setValue("");
			DataTag hc12Tag = doc.openDataTag("{hc12}");
			hc12Tag.setValue("");
			DataTag file5Tag = doc.openDataTag("{file_5}");
			file5Tag.setValue("");
			DataTag hc13Tag = doc.openDataTag("{hc13}");
			hc13Tag.setValue("");
			DataTag file6Tag = doc.openDataTag("{file_6}");
			file6Tag.setValue("");
			DataTag hc14Tag = doc.openDataTag("{hc14}");
			hc14Tag.setValue("");
			DataTag file7Tag = doc.openDataTag("{file_7}");
			file7Tag.setValue("");
			DataTag hc15Tag = doc.openDataTag("{hc15}");
			hc15Tag.setValue("");
			DataTag file8Tag = doc.openDataTag("{file_8}");
			file8Tag.setValue("");
			DataTag hc16Tag = doc.openDataTag("{hc16}");
			hc16Tag.setValue("");
			DataTag hc17Tag = doc.openDataTag("{hc17}");
			hc17Tag.setValue("");
			DataTag hc18Tag = doc.openDataTag("{hc18}");
			hc18Tag.setValue("");
			DataTag sb_feeTag = doc.openDataTag("{file_fee}");
			sb_feeTag.setValue("");
			DataTag fh3Tag = doc.openDataTag("{fh3}");
			fh3Tag.setValue("");
		}
		if (yg_state.equals("1")) {
			i=i+1;
			DataTag ygTag = doc.openDataTag("{yg_state}");
			ygTag.setValue(i+"、	劳动用工手续办理");
			DataTag hc17Tag = doc.openDataTag("{hc17}");
			hc17Tag.setValue("\r\n");
			DataTag yg1Tag = doc.openDataTag("{yg_1}");
			yg1Tag.setValue("根据政策的规定,为甲方办理合法的劳动用工手续，包括深圳户籍员工的用工登记及非深圳户籍员工居住证的办理（相关费用由甲方承担）；");
			DataTag hc18Tag = doc.openDataTag("{hc18}");
			hc18Tag.setValue("\r\n");
		}else{
			DataTag ygTag = doc.openDataTag("{yg_state}");
			ygTag.setValue("");
			DataTag yg1Tag = doc.openDataTag("{yg_1}");
			yg1Tag.setValue("");
		}
		if (sy_state.equals("1")) {
			i=i+1;
			DataTag syTag = doc.openDataTag("{sy_state}");
			syTag.setValue(i+"、	商业保险手续办理");
			DataTag hc19Tag = doc.openDataTag("{hc19}");
			hc19Tag.setValue("\r\n");
			DataTag sy1Tag = doc.openDataTag("{sy_1}");
			sy1Tag.setValue("参照保险公司公布的险种及费率，按照甲方选择的险种及保险方案，为甲方员工代办商业保险投保及理赔手续，包含：代为按时缴纳商业保险费用以及跟进相应的索赔工作；");
			DataTag hc20Tag = doc.openDataTag("{hc20}");
			hc20Tag.setValue("\r\n");
			
			
			DataTag sy_feeTag = doc.openDataTag("{sy_fee}");
			sy_feeTag.setValue("商业保险费");
			if(i!=j){
			DataTag fh4Tag = doc.openDataTag("{fh4}");
			fh4Tag.setValue("、");
			}
		}else{
			DataTag syTag = doc.openDataTag("{sy_state}");
			syTag.setValue("");
			DataTag hc19Tag = doc.openDataTag("{hc19}");
			hc19Tag.setValue("");
			DataTag sy1Tag = doc.openDataTag("{sy_1}");
			sy1Tag.setValue("");
			DataTag hc20Tag = doc.openDataTag("{hc20}");
			hc20Tag.setValue("");
			
			DataTag sy_feeTag = doc.openDataTag("{sy_fee}");
			sy_feeTag.setValue("");
			DataTag fh4Tag = doc.openDataTag("{fh4}");
			fh4Tag.setValue("");
		}
		if (tj_state.equals("1")) {
			i=i+1;
			DataTag tjTag = doc.openDataTag("{tj_state}");
			tjTag.setValue(i+"、	组织员工年度体检");
			DataTag hc25Tag = doc.openDataTag("{hc21}");
			hc25Tag.setValue("\r\n");
			DataTag tj1Tag = doc.openDataTag("{tj_1}");
			tj1Tag.setValue("根据甲乙双方约定的标准，组织甲方员工年度（或入职）体检；");
			DataTag hc22Tag = doc.openDataTag("{hc22}");
			hc22Tag.setValue("\r\n");
			
			DataTag tj_feeTag = doc.openDataTag("{tj_fee}");
			tj_feeTag.setValue("体检费");
			if(i!=j){
				DataTag fh6Tag = doc.openDataTag("{fh6}");
				fh6Tag.setValue("、");
				}
		}else{
			DataTag tjTag = doc.openDataTag("{tj_state}");
			tjTag.setValue("");
			DataTag hc25Tag = doc.openDataTag("{hc21}");
			hc25Tag.setValue("");
			DataTag tj1Tag = doc.openDataTag("{tj_1}");
			tj1Tag.setValue("");
			DataTag hc22Tag = doc.openDataTag("{hc22}");
			hc22Tag.setValue("");
			DataTag tj_feeTag = doc.openDataTag("{tj_fee}");
			tj_feeTag.setValue("");
			DataTag gz_feeTag = doc.openDataTag("{gz_fee}");
			gz_feeTag.setValue("");
			DataTag fh6Tag = doc.openDataTag("{fh6}");
			fh6Tag.setValue("");
		}
		if (hd_state.equals("1")) {
			i=i+1;
			DataTag hdTag = doc.openDataTag("{hd_state}");
			hdTag.setValue(i+"、	安排员工活动");
			DataTag hc23Tag = doc.openDataTag("{hc23}");
			hc23Tag.setValue("\r\n");
			DataTag hd1Tag = doc.openDataTag("{hd_1}");
			hd1Tag.setValue("根据甲方双方约定的标准,乙方为甲方员工不定期组织各类活动；");
			DataTag hc24Tag = doc.openDataTag("{hc24}");
			hc24Tag.setValue("\r\n");
			
			DataTag hd_feeTag = doc.openDataTag("{hd_fee}");
			hd_feeTag.setValue("员工活动费");
			if(i!=j){
				DataTag fh7Tag = doc.openDataTag("{fh7}");
				fh7Tag.setValue("、");
				}
		}else{
			DataTag hdTag = doc.openDataTag("{hd_state}");
			hdTag.setValue("");
			DataTag hc23Tag = doc.openDataTag("{hc23}");
			hc23Tag.setValue("");
			DataTag hd1Tag = doc.openDataTag("{hd_1}");
			hd1Tag.setValue("");
			DataTag hc24Tag = doc.openDataTag("{hc24}");
			hc24Tag.setValue("");
			
			DataTag hd_feeTag = doc.openDataTag("{hd_fee}");
			hd_feeTag.setValue("甲方员工税前（后）（请选择）工资总额");
			DataTag fh7Tag = doc.openDataTag("{fh7}");
			fh7Tag.setValue("、");
		}
		if (gz_state.equals("1")) {
			i=i+1;
			DataTag gzTag = doc.openDataTag("{gz_state}");
			gzTag.setValue(i+"、	薪资个税、费用报销（如有）代理");
			DataTag hc25Tag = doc.openDataTag("{hc25}");
			hc25Tag.setValue("\r\n");
			DataTag gz1Tag = doc.openDataTag("{gz_1}");
			gz1Tag.setValue("甲方委托乙方提供薪资个税代理服务，服务费用标准见附件二，双方的权利义务见附件三。");
			DataTag hc26Tag = doc.openDataTag("{hc26}");
			hc26Tag.setValue("\r\n");
			
			DataTag gz_feeTag = doc.openDataTag("{gz_fee}");
			gz_feeTag.setValue("甲方员工税前（后）（请选择）工资总额");
			if(i!=j){
				DataTag fh5Tag = doc.openDataTag("{fh5}");
				fh5Tag.setValue("、");
				}
		}else{
			DataTag gzTag = doc.openDataTag("{gz_state}");
			gzTag.setValue("");
			DataTag hc25Tag = doc.openDataTag("{hc25}");
			hc25Tag.setValue("");
			DataTag gz1Tag = doc.openDataTag("{gz_1}");
			gz1Tag.setValue("");
			DataTag hc26Tag = doc.openDataTag("{hc26}");
			hc26Tag.setValue("");
			
			DataTag gz_feeTag = doc.openDataTag("{gz_fee}");
			gz_feeTag.setValue("");
			DataTag fh5Tag = doc.openDataTag("{fh5}");
			fh5Tag.setValue("");
		}
		if (zx_state.equals("1")) {
			i=i+1;
			DataTag zxTag = doc.openDataTag("{zx_state}");
			zxTag.setValue(i+"、	相关政策法规的咨询，具体包括：");
			DataTag hc27Tag = doc.openDataTag("{hc27}");
			hc27Tag.setValue("\r\n");
			DataTag zx1Tag = doc.openDataTag("{zx_1}");
			zx1Tag.setValue("（1）	与本合同服务项目相关的政策法规咨询；");
			DataTag hc28Tag = doc.openDataTag("{hc28}");
			hc28Tag.setValue("\r\n");
			DataTag zx2Tag = doc.openDataTag("{zx_2}");
			zx2Tag.setValue("（2）	相关新政策的及时通知。");
			DataTag hc29Tag = doc.openDataTag("{hc29}");
			hc29Tag.setValue("\r\n");
		}else{
			DataTag zxTag = doc.openDataTag("{zx_state}");
			zxTag.setValue("");
			DataTag hc27Tag = doc.openDataTag("{hc27}");
			hc27Tag.setValue("");
			DataTag zx1Tag = doc.openDataTag("{zx_1}");
			zx1Tag.setValue("");
			DataTag hc28Tag = doc.openDataTag("{hc28}");
			hc28Tag.setValue("");
			DataTag zx2Tag = doc.openDataTag("{zx_2}");
			zx2Tag.setValue("");
			DataTag hc29Tag = doc.openDataTag("{hc29}");
			hc29Tag.setValue("");
		}
		if (bx_state.equals("1")) {
			DataTag bx_feeTag = doc.openDataTag("{bx_fee}");
			bx_feeTag.setValue("报销费用");
			if(i!=j){
				DataTag fh7Tag = doc.openDataTag("{fh6}");
				fh7Tag.setValue("、");
				}
		}else{
			DataTag bx_feeTag = doc.openDataTag("{bx_fee}");
			bx_feeTag.setValue("");
			DataTag fh7Tag = doc.openDataTag("{fh6}");
			fh7Tag.setValue("");
		}
	}

	poCtrl.setWriter(doc);

	//判断文档是否暂存
	String sql2 = "select count(*),puof_url from PubOffice where puof_pute_id=2 and puof_tid="
			+ co_id + " group by puof_url";
	ResultSet rs2 = stmt.executeQuery(sql2);
	System.out.println(sql2);
	Integer cont;
	String ofile;
	cont = 0;
	ofile = "";
	String sql3 = "";
	while (rs2.next()) {
		cont = rs2.getInt(1);
		ofile = rs2.getString(2);
	}
	//System.out.println("xxxxx");
	//System.out.println(coco_type);

		sql3 = "select ebct_url,ebct_name from CompactVer where ebct_state=1 and ecid=2 and ebct_type='"+co_class+"'";

	ResultSet rs3 = stmt.executeQuery(sql3);
	System.out.println(sql3);
	String covername;
	covername = "";
	while (rs3.next()) {
		covername = rs3.getString(1);
		session.setAttribute("cover_name", null);
		session.setAttribute("cover_name", rs3.getString(2));
	}

	//如果存在文档，将打开暂存文档，否则打开新文档
	if (cont > 0) {
		poCtrl.webOpen("../OfficeFile/DownLoad/CoCompact/" + ofile,
				OpenModeType.docAdmin, "szciic");
		System.out.println("../OfficeFile/DownLoad/CoCompact/" + ofile);
	} else {
		poCtrl.webOpen("../" + covername, OpenModeType.docAdmin,
				"szciic");
	}

	poCtrl.setTagId("PageOfficeCtrl1");//此行必需

	rs.close();
	stmt.close();
	conn.close();
	session.setAttribute("compact_id", null);
	session.setAttribute("compact_id", co_id);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>合同信息</title>
</head>
<body  load="ShowRevisions()">
	<script type="text/javascript">
		function Save() {
			document.getElementById("PageOfficeCtrl1").WebSave();
			
		}
		
		function ShowRevisions() {
            document.getElementById("PageOfficeCtrl1").ShowRevisions = true;
        }
		
		function Send() {
			document.getElementById("PageOfficeCtrl1").WebSave();

			window.location = "../CoCompact/Compact_SendOk.jsp?coco_id=<%=request.getParameter("coco_id")%>&coco_tapr_id=<%=request.getParameter("coco_tapr_id")%>&user=<%=request.getParameter("user")%>";
		}
		
		function changeCode(code){
			if(code !=null && code !="" && code !="null"){
				code=encodeURI(code);
			}
			return code;
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
