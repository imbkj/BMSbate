<!--
	创建人：彭耀
	创建时间：2013-12-18
	用途：人事商调函打印跳转
-->
<%@page import="dal.LoginDal"%>
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,service.WorkflowCore.WfOperateService,impl.WorkflowCore.WfOperateImpl,bll.Archives.Archive_PrintSDHImpl,dal.LoginDal"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>
<%
	int tapr_id = Integer.parseInt(request.getParameter("taprid"));
	int eada_id = Integer.parseInt(request.getParameter("eadaid"));
	Integer mp = 0;
	if (request.getParameter("mp") != null) {
		mp = Integer.parseInt(request.getParameter("mp"));
	}
	int sdh = Integer.parseInt(request.getParameter("sdh"));
	int userid = Integer.parseInt(request.getParameter("userid"));
	LoginDal dal = new LoginDal();
	String user = dal.getUsernameById(userid);

	Object[] obj = {sdh, eada_id};
	//执行工作流
	WfOperateService wf = new WfOperateImpl(new Archive_PrintSDHImpl());
	wf.PassToNext(obj, tapr_id, user, "", 0, "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
	<script type="text/javascript">
		alert('保存成功！');
		window.location.href = "../Taskflow/Task_ContentList.zul?id=<%=request.getParameter("taclId")%>";
	</script>
</body>
</html>