<%@ page language="java"
	import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*,java.text.*,Model.CoAgencyCompactModel,dal.LoginDal"
	pageEncoding="utf-8"%>
<%@ include file="../js/Conn.jsp"%>
 <jsp:useBean id="cb" class="Controller.CoAgencies.MakeCompactBean"/>
<%
	LoginDal dal = new LoginDal();
	int userid = Integer.parseInt(request.getParameter("userid"));
	String user = dal.getUsernameById(userid);
	String coct_id=request.getParameter("coct_id");
	Model.CoAgencyCompactModel model=cb.getModel(Integer.parseInt(coct_id));
	String[] str=cb.CoAg_CompactBack(model,user);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	alert('退回成功！');
	
	</script>
</body>
</html>