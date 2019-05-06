<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Log Out Redirect</title>
</head>
<body>

<%
    //	String redirectURL = request.getContextPath() + "/";
    String redirectURL = "http://172.31.156.50/tjpulimscms_gx/pages/logout-redirect.jsp";

%>

<%
    session.invalidate();
    response.sendRedirect(redirectURL);
%>

</body>
</html>