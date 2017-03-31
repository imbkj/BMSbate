<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同发送法务审核处理页面
-->
<%@page import="dal.LoginDal"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,service.WorkflowCore.WfOperateService,impl.WorkflowCore.WfOperateImpl,bll.CoCompact.CoCompact_OperateImpl,dal.LoginDal"
	pageEncoding="utf-8"%>
<%@ include file="../../js/Conn.jsp"%>
<%
	int coco_tapr_id = Integer.parseInt(request
			.getParameter("coco_tapr_id"));
	int coco_id = Integer.parseInt(request.getParameter("coco_id"));
	int userid = Integer.parseInt(request.getParameter("user"));
	LoginDal dal = new LoginDal();
	String user = dal.getUsernameById(userid);
	 //链接数据库操作开始
	 String sql = "insert into update CoCompact set coco_state=1 where coco_id="+coco_id;
	 stmt.executeUpdate(sql); 
	
	 //数据库结束
	/* //任务单流程控制
	 WfFlowControlService wfcs = new WfFlowControlImpl(); //流程控制接口
	
	 wfcs.AddTaskLog(coco_tapr_id, "CoCompact", coco_id, "合同制作", "test", "");
	 int tapr_id=wfcs.PassToNext(coco_tapr_id, coco_id, "test", "", 0);
	 if(tapr_id>0){
	 String sql1 = "update CoCompact set coco_tapr_id="+tapr_id+" where coco_id="+coco_id;
	 stmt.executeUpdate(sql1); 
	 stmt.close();
	 conn.close(); 
	 }*/
	//Object[] obj = {"1", "1", coco_id};
	//执行工作流
	//WfOperateService wf = new WfOperateImpl(new CoCompact_OperateImpl());
	//wf.PassToNext(obj, coco_tapr_id, user, "", 0, "");
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
		window.location.href = '../CoCompact/Compact_AddSelect.zul';
	</script>
</body>
</html>