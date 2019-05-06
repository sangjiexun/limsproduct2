<%@ page import="com.wiscom.is.IdentityFactory" %>
<%@ page import="com.wiscom.is.IdentityManager" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Log Out Redirect</title>
</head>
<body>

<%
    String is_config = request.getRealPath("/client.properties");
    IdentityFactory factory = IdentityFactory.createFactory( is_config );
    IdentityManager im = factory.getIdentityManager();
    //    String redirectURL = request.getContextPath() + "/";
    String gotoURL = HttpUtils.getRequestURL(request).toString();
    String redirectURL = im.getLogoutURL() +"?goto=" + java.net.URLEncoder.encode(gotoURL);
%>

<%
    session.invalidate();
    response.sendRedirect(redirectURL);
%>

</body>
</html>