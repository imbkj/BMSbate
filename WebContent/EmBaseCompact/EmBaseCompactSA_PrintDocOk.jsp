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
	int ebcu_tapr_id = Integer.parseInt(request
			.getParameter("ebcu_tapr_id"));
	int ebcc_id = Integer.parseInt(request.getParameter("ebcc_id"));
	int userid = Integer.parseInt(request.getParameter("user"));
	LoginDal dal = new LoginDal();
	String user = dal.getUsernameById(userid);
	//链接数据库操作开始
	//String sql = "update EmBaseCompactUpdate set ebcu_upstate=2 where ebcu_id="+ebcc_id;
	//stmt.executeUpdate(sql); 

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
	Object[] obj = {"3", ebcc_id,user};
	
	//获取公司编号
	String sql = "select cid from EmBaseCompactUpdate a left join EmBaseCompact b on b.ebco_id=a.ebcu_ebco_id where ebcu_id="+ebcc_id;
	ResultSet rs = stmt.executeQuery(sql);
	int cid=0;
	while (rs.next()) {
		cid = rs.getInt(1);
	}
	
	//执行工作流
	WfOperateService wf = new WfOperateImpl(new EmBaseCompactSA_OperateImpl());
	wf.PassToNext(obj, ebcu_tapr_id, user, "", cid, "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		alert('操作成功！');
		//window.location.href = '../EmBaseCompact/EmBaseCompactSA_PrintList.zul';
	</script>
</body>
</html>