<%@page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!doctype html>
<html lang="en">
<head>
    <title><spring:message code="title.html.name"/></title>
    <meta name="Generator" content="gvsun">
    <meta name="Author" content="lyyyyyy">
    <meta name="Keywords" content="">
    <meta name="decorator" content="none">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
</head>
<%
    // 登录后卡在checkCenter页面的问题
    session.setAttribute("LOGINTYPE", "cmsIndex");
    // 登录后在实验室页面删除siteUrl会出现来回跳转的问题，所以加上这个跳转
        String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        response.sendRedirect(url+"/pages/index.jsp");
%>
<body style="display: none">
</body>
</html>