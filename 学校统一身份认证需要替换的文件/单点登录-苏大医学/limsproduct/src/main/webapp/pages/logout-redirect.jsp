<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<title>Log Out Redirect</title>
</head>
<body>

<%
	//	String redirectURL = request.getContextPath() + "/";
	String redirectURL = "http://ids1.suda.edu.cn/amserver/UI/Logout?goto=";
	redirectURL += request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath() + "/";

%>

<%
	session.invalidate();
	response.sendRedirect(redirectURL);
%>

</body>
</html>