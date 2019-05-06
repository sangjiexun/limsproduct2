<%@page import="java.util.Map"%>
<%@ page
        import="java.security.Principal" %>
<%@ page
        import="java.util.Iterator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Log Out Redirect</title>
</head>
<body>

<%
    String redirect = request.getContextPath() + "/pages/logout-success.jsp";
%>

<%
    session.invalidate();
    String casLogoutURL = request.getHeader("cas_logout_url");
//    String redirectURL = casLogoutURL+"http://sec.jit.edu.cn/jitsoftcms/home";
    String redirectURL = casLogoutURL+"http://sec.jit.edu.cn/jitsoftcms/pages/logout-redirect.jsp";
    //跳转到cms退出界面
//    String redirectURL = "http://sec.jit.edu.cn/jitsoftcms/pages/logout-redirect.jsp";
    response.sendRedirect(redirectURL);
%>

</body>
</html>