<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.*"%>
<%@ page
        import="java.security.Principal" %>
<%@ page
        import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Log Out Redirect</title>
</head>
<body>
<%-- <%
    String add=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String redirectURL =add+"/jsutlimscms";
%> --%>

<%
   	session.invalidate();
	String casLogoutURL = "https://cas.jsut.edu.cn/lyuapServer/logout";
	String redirectURL = casLogoutURL+"?service=lab.jsut.edu.cn/jsutlims";
	response.sendRedirect(redirectURL);
%>

</body>
</html>