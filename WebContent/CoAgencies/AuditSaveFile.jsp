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
	// 格式化日期
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	// 格式化日期(产生文件名)
	String newfilename = sdf.format(Calendar.getInstance().getTime()) + ".doc";
	FileSaver fs = new FileSaver(request, response);
	//如果存在文档，将打开暂存文档，否则打开新文档
	fs.saveToFile(request.getSession().getServletContext()
		.getRealPath("CoAgencies/file/savefile")
		+ "/" + newfilename);
	//Model.CoAgencyCompactModel model=cb.getModel(Integer.parseInt(request.getParameter("coct_id")));
	//链接数据库操作开始
	Model.CoAgencyCompactModel model=cb.getModel(Integer.parseInt(request.getParameter("coct_id")));
	fs.close();
	//String[] str=cb.updateComapctFilename(model,user,newfilename);
	cb.SaveComapctFilename(model,user,newfilename);
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>