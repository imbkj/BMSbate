<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同打印处理页面
-->
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,service.WorkflowCore.WfOperateService,impl.WorkflowCore.WfOperateImpl,bll.CoCompact.CoCompact_OperateImpl,dal.LoginDal"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>
<%
	int coco_tapr_id = Integer.parseInt(request
			.getParameter("coco_tapr_id"));
	int coco_id = Integer.parseInt(request.getParameter("coco_id"));
	int userid = Integer.parseInt(request.getParameter("user"));
	int rwd = Integer.parseInt(request.getParameter("rwd"));
	int coco_autst = Integer.parseInt(request.getParameter("coco_autst"));
	LoginDal dal = new LoginDal();
	String user = dal.getUsernameById(userid);
	/* //链接数据库操作开始
	 String sql = "update CoCompact set coco_state=3 where coco_id="+request.getParameter("coco_id");
	 stmt.executeUpdate(sql); 

	 //数据库结束
	
	 //任务单流程控制
	 WfFlowControlService wfcs = new WfFlowControlImpl(); //流程控制接口
	
	 wfcs.AddTaskLog(coco_tapr_id, "CoCompact", coco_id, "合同打印", "test", "");
	 int tapr_id=wfcs.PassToNext(coco_tapr_id, coco_id, "test", "", 0);
	 if(tapr_id>0){
	 String sql1 = "update CoCompact set coco_tapr_id="+tapr_id+" where coco_id="+coco_id;
	 stmt.executeUpdate(sql1); 
	 stmt.close();
	 conn.close();
	 } */
	 try{
		 
		//获取公司编号
			String sql = "select coco_cola_id from cocompact where coco_id="+coco_id;
			ResultSet rs = stmt.executeQuery(sql);
			int cid=0;
			while (rs.next()) {
				cid = rs.getInt(1);
			}
		 
	//if (rwd == 2) {
		Object[] obj = {"5", "4", coco_id,rwd,coco_autst};
		//执行工作流，走二次审核流程
		WfOperateService wf = new WfOperateImpl(
				new CoCompact_OperateImpl());
		wf.PassToNext(obj, coco_tapr_id, user, "", cid, "");
	//} else {
	//	Object[] obj = {"8", "3", coco_id,""};
		//执行工作流，走盖章流程
	//	WfOperateService wf = new WfOperateImpl(
	//			new CoCompact_OperateImpl());
	//	String[] m=wf.SkipToN(obj, coco_tapr_id,7, user, "",cid, "");
	//}
	}catch(Exception e){
		e.printStackTrace();
	}
	 
	 String coco_state = request.getParameter("coco_state");
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
		
		window.location.href='../CoCompact/Compact_DoListfw.zul';

	</script>
</body>
</html>