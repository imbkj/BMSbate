<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script src="<%=request.getContextPath()%>/js/jquery.min.js" language="JavaScript"></script>
<script type="text/javascript">
	//页面初始化设置    jquery-ajax
	$(document).ready(function(){
		$.ajax({
			type: "POST",
			url: "<%=request.getContextPath()%>/SendHtmlServlet",
			cache: false,
			async:false,
			success: function(msg) {
				var contents=eval("("+msg+")");
				for(var i = 1; i < contents.length; i++){
					if(i % 2 == 0){
						continue;
					}
					var name = contents[i -1].name;
					var amount = contents[i -1].amount;
					var name1 = contents[i].name;
					var amount1 = contents[i].amount;
					var option = "<tr><td bgcolor='#FFFFFF'>"+name+"</td><td bgcolor='#FFFFFF'>"+amount+"</td><td bgcolor='#FFFFFF'>"+ name1+"</td><td bgcolor='#FFFFFF'>"+amount1+"</td></tr>";
					$("#mytable").append(option);
					
				}
				if(contents.length % 2 != 0){
					var name = contents[contents.length -1].name;
					var amount = contents[contents.length -1].amount;
					var option = "<tr><td bgcolor='#FFFFFF' >"+name+"</td><td bgcolor='#FFFFFF' colspan='3'>"+amount+"</td></tr>";
					$("#mytable").append(option);
				}
			}
		});
	});
</script>
<body>
	<div >
		<table id="mytable" border="0" cellpadding="6" cellspacing="1" bgcolor="#000000" >
		<tr>
			<th bgcolor='#FFFFFF'>项目名</th>
			<th bgcolor='#FFFFFF'>金额</th>
			<th bgcolor='#FFFFFF'>项目名</th>
			<th bgcolor='#FFFFFF'>金额</th>
		<tr>
			
		</table>
	</div>
</body>
</html>