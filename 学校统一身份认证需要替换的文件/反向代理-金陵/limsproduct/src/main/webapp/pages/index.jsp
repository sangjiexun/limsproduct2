<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<%--<head>
<title>东华大学实验室管理系统1111</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="screen" />
</head>--%>

<head>

    <meta charset="utf-8">
    <title>实验室智能管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/is_LeftList.css" type="text/css"
          media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/is_Searcher.css" type="text/css"
          media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/is_SystemUI_Allpages.css" type="text/css"
          media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-new.css"/>
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
%>
<%
    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<%
    String uid = request.getHeader("cas_user");
    String out_url = "http://authserver.jit.edu.cn/authserver/login?service=" + url;
    if (uid == null || "".equals(uid)) {
        String cas_logout_url = request.getHeader("cas_logout_url");
        if (cas_logout_url == null) {
            out.println("未获取到认证相关信息头");
        } else {
            response.sendRedirect(cas_logout_url + url);
        }
    } else {
        if(Integer.valueOf(uid.substring(0,6))==0){
            //取后5位
            uid = uid.substring(6);
        }
    }
%>
<body>
<a href="<%=out_url%>">跳转登录</a>
<form style="display:none;" name='loginForm' id="loginForm"
      action='${pageContext.request.contextPath}/j_spring_security_check' method='POST'>
    <input type="text" name="j_username" value="<%=uid%>"/>
    <input type="password" name="j_password" value="<%=uid%>"/>
    <input type="submit" value="submit"/>
</form>
<script type="text/javascript">
    window.onload = function () {
        var loginUser = '<%=uid%>';
        if ('' == loginUser || 'null' == loginUser || null == loginUser) {

        } else {
            // alert("提交表单");
            document.loginForm.submit();
        }
    }
</script>
</body>
</html>