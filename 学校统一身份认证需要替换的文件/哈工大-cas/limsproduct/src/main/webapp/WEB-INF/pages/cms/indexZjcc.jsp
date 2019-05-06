<%@ page import="org.jasig.cas.client.util.AssertionHolder" %>
<%@ page import="org.jasig.cas.client.validation.Assertion" %>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
</head>
<%
    // 登录后卡在checkCenter页面的问题
    session.setAttribute("LOGINTYPE", "cmsIndex");
    // 登录后在实验室页面删除siteUrl会出现来回跳转的问题，所以加上这个跳转
    Object username = request.getSession().getAttribute("username");
    if(username!=null){
        String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        response.sendRedirect(url+"/test");
    }
    Assertion assertion = AssertionHolder.getAssertion();
    String curUser = assertion.getPrincipal().getName();
%>
<script type="text/javascript">
    $(function() {
        var loginUser='<%=curUser %>';
        if(''!=loginUser && loginUser!=null && 'null'!=loginUser){
            $("#subform").submit();
        }else{
            console.log("userName is null");
        }
    });
</script>
<body style="display: none">
<form id="subform" name="subform" action='${pageContext.request.contextPath}/j_spring_security_check' method='POST'>
    <td><input type="hidden" name="j_username" value="<%=curUser %>"/></td>
    <td><input type="hidden" name="j_password" value="<%=curUser %>"/></td>
</form>
</body>
</html>