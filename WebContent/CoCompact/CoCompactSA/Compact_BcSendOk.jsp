<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同发送法务审核处理页面
-->
<%@page import="Controller.systemWindowController"%>
<%@page import="dal.LoginDal"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,service.WorkflowCore.WfOperateService,impl.WorkflowCore.WfOperateImpl,bll.CoCompact.CoCompactSA.CoCompactSA_OperateImpl,dal.LoginDal"
	pageEncoding="utf-8"%>
<%@ include file="../../js/Conn.jsp"%>
<%
	int coco_tapr_id = Integer.parseInt(request
			.getParameter("coco_tapr_id"));
	int coco_id = Integer.parseInt(request.getParameter("coco_id"));
	int userid = Integer.parseInt(request.getParameter("user"));
	LoginDal dal = new LoginDal();
	String user = dal.getUsernameById(userid);
	
	//获取公司编号
			String sql = "select cid from CoCompactSA a left join CoCompact b on b.coco_id=a.ccsa_coco_id where ccsa_id="+coco_id;
			ResultSet rs = stmt.executeQuery(sql);
			int cid=0;
			while (rs.next()) {
				cid = rs.getInt(1);
			}
	 //链接数据库操作开始
	 //String sql = "update CoCompactSA set ccsa_state=1,ccsa_auditingdate=getdate() where ccsa_id="+coco_tapr_id;
	 //System.out.print(sql);
	 //stmt.executeUpdate(sql); 
	
	 /*//数据库结束
	 //任务单流程控制s
	 WfFlowControlService wfcs = new WfFlowControlImpl(); //流程控制接口
	
	 wfcs.AddTaskLog(ccsa_tapr_id, "CoCompactSA", ccsa_id, "合同补充协议审核", "test", "");
	 int tapr_id=wfcs.PassToNext(ccsa_tapr_id, ccsa_id, "test", "", 0);
	 if(tapr_id>0){
	 String sql1 = "update CoCompactSA set ccsa_tapr_id="+tapr_id+" where ccsa_id="+ccsa_id;
	 stmt.executeUpdate(sql1); 
	 stmt.close();
	 conn.close(); 
	 }*/
	Object[] obj = {"1", "1", coco_id};
	//执行工作流
	WfOperateService wf = new WfOperateImpl(new CoCompactSA_OperateImpl());
	wf.PassToNext(obj, coco_tapr_id, user, "", cid, "");
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		alert('审核成功！');
		//window.location.href = 'Compact_BcAutSelect.zul';
	</script>
</body>
</html>