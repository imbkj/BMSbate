<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,
	java.text.*,Model.CoAgencyCompactModel,dal.LoginDal,Util.UserInfo.*"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>
 <jsp:useBean id="cb" class="Controller.CoAgencies.MakeCompactBean"/>
<%
	LoginDal dal = new LoginDal();
	int userid = Integer.parseInt(request.getParameter("userid"));
	String user = dal.getUsernameById(userid);
	String coct_id=request.getParameter("coct_id");
	Model.CoAgencyCompactModel model=cb.getModel(Integer.parseInt(request.getParameter("coct_id")));
	cb.updateComapctFilename(model,user);
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'SaveFile.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
<script type="text/javascript">
alert('保存成功！');
//window.location.href='../CoCompact/Compact_AutSelect.zul';
</script>
</body>
</html>
