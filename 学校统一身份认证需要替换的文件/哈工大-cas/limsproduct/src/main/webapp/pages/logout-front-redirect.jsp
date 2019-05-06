<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
	<title>Log Out Redirect</title>
</head>
<body>

<%
	String redirectURL = "https://sso.hitsz.edu.cn:7002/cas/logout?service=";
	redirectURL += request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath() + "/";
%>

<%
	session.invalidate();
	response.sendRedirect(redirectURL);
%>

</body>
</html>