<%@page import="java.net.URLEncoder" %>
<%@page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<title>Log Out Redirect</title>
</head>
<body>

<%
	String casLogoutUrl = "https://login.sufe.edu.cn/cas/logout?service=";
//	String redirectURL = request.getContextPath() + "/pages/logout-success.jsp";
	String redirectURL = casLogoutUrl + request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() + "/home";
%>

<%
	session.invalidate();
	response.sendRedirect(redirectURL);
%>

</body>
</html>