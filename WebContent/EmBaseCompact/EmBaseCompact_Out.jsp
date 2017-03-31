<!--
	创建人：张志强
	创建时间：2013-10-17
	用途：公司合同退回处理页面
-->
<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,service.WorkflowCore.WfOperateService,impl.WorkflowCore.WfOperateImpl,bll.EmBaseCompact.EmBaseCompact_OperateImpl,dal.LoginDal"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<table>
		<tr>
			<td>退回原因：</td>
		</tr>
		<tr>
			<td><textarea id="out_remark" rows="3" cols="30"></textarea></td>
		</tr>
		<tr>
			<td><button
					onclick="window.location = '../EmBaseCompact/EmBaseCompact_Out.jsp?ebcc_tapr_id=<%=request.getParameter("ebcc_tapr_id")%>&ebcc_id=<%=request.getParameter("ebcc_id")%>&user=<%=request.getParameter("user")%>&sub=1&remark='+out_remark.value ">提交</button><button
					onclick=" javascript:history.go(-1)">返回</button></td>
		</tr>
	</table>
	<%
	//链接数据库操作开始
	//String sql = "update CoCompact set coco_state=7 where coco_id="+request.getParameter("coco_id");
	//stmt.executeUpdate(sql); 
	//stmt.close();
	//conn.close();
	if (request.getParameter("sub").equals("1")) {
	int ebcc_tapr_id = Integer.parseInt(request.getParameter("ebcc_tapr_id"));
	int ebcc_id = Integer.parseInt(request.getParameter("ebcc_id"));
	int userid = Integer.parseInt(request.getParameter("user"));
	LoginDal dal = new LoginDal();
	String user = dal.getUsernameById(userid);

	Object[] obj = {"10", ebcc_id, request.getParameter("remark")};
	//执行工作流
	WfOperateService wf = new WfOperateImpl(new EmBaseCompact_OperateImpl());
	String[] m=wf.ReturnToPrev(obj, ebcc_tapr_id,user,request.getParameter("remark"));

	//数据库结束
	%>
	<script type="text/javascript">
		alert('退回成功！');
		window.location.href = '../Taskflow/Task_ContentList.zul?id=0';
	</script>
	<%
		}
	%>
</body>
</html>