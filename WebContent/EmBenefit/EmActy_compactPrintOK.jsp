<!--
	创建人：彭耀
	创建时间：2014-3-19
	用途：公司合同打印处理页面
-->
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,service.WorkflowCore.WfOperateService,impl.WorkflowCore.WfOperateImpl,bll.EmBenefit.EmActy_compactAddImpl,bll.CoCompact.CoCompact_OperateImpl,dal.LoginDal,Model.EmActyCompactModel,java.text.*,bll.EmBenefit.EmActy_compactBll"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>
<%
	int taprId = Integer.parseInt(request.getParameter("id"));
	int id = Integer.parseInt(request.getParameter("daid"));

	int state = Integer.parseInt(request.getParameter("state"));
	int taclId = Integer.parseInt(request.getParameter("taclId"));
	String user = session.getAttribute("EAuser").toString();

	EmActyCompactModel eacm = new EmActyCompactModel();
	
	if (Integer.valueOf(request.getParameter("c")) == 2) {
		eacm.setEaco_id(id);
		eacm.setEaco_state(3);
		WfOperateService wf = new WfOperateImpl(
				new EmActy_compactAddImpl());
		if (taclId > 0) {
			eacm.setEaco_tapr_id(taprId);
			Object[] obj = {2, eacm};
			wf.PassToNext(obj, taprId, user, "", 0, "");
			
		} else {
			Object[] obj = {2, eacm};
			EmActy_compactBll bll = new EmActy_compactBll();
			EmActyCompactModel em = new EmActyCompactModel();
			em.setEaco_id(id);
			List<EmActyCompactModel> list = bll.getList(em, false);
			wf.AddTaskToNext(obj, "供应商合同", list.get(0).getEaco_name() + "("
					+ list.get(0).getEaco_compactid() + ")", 76,
					user, "", 0, "");
			list = bll.getList(em, false);
			wf.PassToNext(obj, list.get(0).getEaco_tapr_id(), user, "", 0, "");
		}

	} else if (Integer.valueOf(request.getParameter("c")) == 3) {
		eacm.setEaco_id(id);
		eacm.setEaco_state(state);
		eacm.setEaco_tapr_id(taprId);
		Object[] obj = {request.getParameter("c"), eacm};
		//执行工作流
		WfOperateService wf = new WfOperateImpl(
				new EmActy_compactAddImpl());
		wf.PassToNext(obj, taprId, user, "", 0, "");
	} else if (Integer.valueOf(request.getParameter("c")) == 4) {
		eacm.setEaco_id(id);
		eacm.setEaco_state(5);
		eacm.setEaco_tapr_id(taprId);
		Object[] obj = {4, eacm};
		//执行工作流
		WfOperateService wf = new WfOperateImpl(
				new EmActy_compactAddImpl());
		wf.ReturnToPrev(obj, taprId, user, "");
	}
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
		
		if("<%=request.getParameter("taclId")%>">0){
			window.location.href = "../Taskflow/Task_ContentList.zul?id=<%=request.getParameter("taclId")%>";
		}else{
			window.location.href = "../EmBenefit/Emacty_compactList.zul";
		}
	</script>
</body>
</html>