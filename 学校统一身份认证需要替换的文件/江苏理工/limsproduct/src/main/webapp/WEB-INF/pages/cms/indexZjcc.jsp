<%@page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!doctype html>
<html lang="en">
<head>
    <title><spring:message code="title.html.name"/></title>
    <meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/test">
</head>
<%
    // 登录后卡在checkCenter页面的问题
    session.setAttribute("LOGINTYPE", "cmsIndex");
	// 登录后在实验室页面删除siteUrl会出现来回跳转的问题，所以加上这个跳转
    String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    response.sendRedirect(url+"/test");
%>
<body>
</body>
</html>