<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同发送法务审核处理页面
-->
<%@page import="dal.LoginDal"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,service.WorkflowCore.WfOperateService,impl.WorkflowCore.WfOperateImpl,bll.EmBaseCompact.EmBaseCompactSA_OperateImpl,dal.LoginDal,java.text.SimpleDateFormat"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>
<%
	java.util.Date date1;
	String str1 = "";
	if (!request.getParameter("emba1").equals("0")) {
		SimpleDateFormat df = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		date1 = df.parse(request.getParameter("emba1"));
		df = new SimpleDateFormat("yyyy-MM-dd");
		str1 = df.format(date1);// 获得格式化后的日期字符串
	}

	java.util.Date date2;
	String str2 = "";

		str2 = request.getParameter("emba2");// 获得格式化后的日期字符串


	java.util.Date date3;
	String str3 = "";
	if (!request.getParameter("emba3").equals("0")) {
		SimpleDateFormat df = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		date3 = df.parse(request.getParameter("emba3"));
		df = new SimpleDateFormat("yyyy-MM-dd");
		str3 = df.format(date3);// 获得格式化后的日期字符串
	}

		String str4="";
		str4 = request.getParameter("emba4");// 获得格式化后的日期字符串


	java.util.Date date5;
	String str5 = "";
	if (!request.getParameter("emba5").equals("0")) {
		SimpleDateFormat df = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		date5 = df.parse(request.getParameter("emba5"));
		df = new SimpleDateFormat("yyyy-MM-dd");
		str5 = df.format(date5);// 获得格式化后的日期字符串
	}

	//int coco_tapr_id = Integer.parseInt(request.getParameter("coco_tapr_id"));
	int ebcc_id = Integer.parseInt(request.getParameter("ebcc_id"));
	int userid = Integer.parseInt(request.getParameter("user"));
	LoginDal dal = new LoginDal();
	String user = dal.getUsernameById(userid);
	//链接数据库操作开始
	/* String sql = "insert into EmBaseCompactUpdate (ebcu_ebco_id,ebcu_incept_date_F, ebcu_incept_date_L, ebcu_maturity_date_F, ebcu_maturity_date_L, ebcu_wage_F,ebcu_wage_L,ebcu_work_place_F,ebcu_work_place_L, ebcu_working_station_F, ebcu_working_station_L, ebcu_incept_indate,ebcu_maturity_indate, ebcu_wage_indate, ebcu_work_place_indate, ebcu_working_station_indate,ebcu_addname,ebcu_addtime, ebcu_incept_state, ebcu_maturity_state, ebcu_wage_state, ebcu_work_place_state, ebcu_working_station_state,ebcu_upstate) values ("
			+ ebcc_id
			+ ","
			+ str1
			+ ","
			+ str2
			+ ","
			+ str3
			+ ","
			+ str4
			+ ",'"
			+ request.getParameter("emba7")
			+ "','"
			+ request.getParameter("emba8")
			+ "','"
			+ request.getParameter("emba10")
			+ "','"
			+ request.getParameter("emba11")
			+ "','"
			+ request.getParameter("emba13")
			+ "','"
			+ request.getParameter("emba14")
			+ "',"
			+ str5
			+ ","
			+ str6
			+ ","
			+ str7
			+ ","
			+ str8
			+ ","
			+ str9 + ",'"+user+"',CONVERT(varchar(100), GETDATE(), 120),0,0,0,0,0,0)"; */
	//stmt.executeUpdate(sql);
	//String sql2 = "update EmBaseCompact set ebco_state=1 from EmBaseCompactChange where ebcc_ebco_id=ebco_id and ebcc_id="
	//+ ebcc_id;
	//stmt.executeUpdate(sql2);
	//数据库结束
	/*
	//任务单流程控制
	WfFlowControlService wfcs = new WfFlowControlImpl(); //流程控制接口
	
	wfcs.AddTaskLog(coco_tapr_id, "CoCompact", coco_id, "合同制作", "test", "");
	int tapr_id=wfcs.PassToNext(coco_tapr_id, coco_id, "test", "", 0);
	if(tapr_id>0){
	String sql1 = "update CoCompact set coco_tapr_id="+tapr_id+" where coco_id="+coco_id;
	stmt.executeUpdate(sql1); 
	stmt.close();
	conn.close(); 
	}*/
	Object[] obj = {"1", ebcc_id, str1, str2, str3, str4, str5,
			request.getParameter("emba6"),
			request.getParameter("emba7"),
			request.getParameter("emba8"),
			request.getParameter("emba9"),
			request.getParameter("emba10"),
			request.getParameter("emba11"), user};
	//执行工作流

	//获取公司编号
	String sql = "select cid from EmBaseCompactChange where ebcc_id="
			+ ebcc_id;
	ResultSet rs = stmt.executeQuery(sql);
	int cid = 0;
	while (rs.next()) {
		cid = rs.getInt(1);
	}

	//WfOperateService wf = new WfOperateImpl(new CoCompact_OperateImpl());
	//wf.PassToNext(obj, coco_tapr_id, user, "", 0, "");
	WfOperateService wf = new WfOperateImpl(
			new EmBaseCompactSA_OperateImpl());
	String[] message = wf.AddTaskToNext(obj, "劳动合同", "劳动合同变更", 12,
			user, "", cid, "");
	System.out.println(message[1]);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		alert('发送成功！');
		//window.location.href = '../Embase/Embase_editlist.zul';
	</script>
</body>
</html>