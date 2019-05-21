<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">

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
<!--header-->
<%
    session.setAttribute("LOGINTYPE", "cmsIndex");
%>
<script type="text/javascript">
    function sub(url) {
        var username = document.getElementById("j_username").value;
        document.getElementById("j_password").value = username;
        submit(url);
    }
    function submit(url){
        document.subform.action=url;
        document.subform.submit();
    }
</script>
<body style="background-image:url(${pageContext.request.contextPath}/images/bg.jpg);">

<div class="sit_module">
    <div class="sit_title"><h3>用户登录</h3></div>
    <div class="module_content">
        <form id="subform" name='subform' method='POST'>
            <table class="sit_login_form">
                <tbody>
                <tr>
                    <th><label for="signin_username">用户名</label></th>
                    <td><input class="username" type="text" id = "j_username" name="j_username" value=""/></td>
                </tr>
                <tr style="display: none">
                    <th><label for="signin_password">密 码</label></th>
                    <td><input class="pwd" type="password" id = "j_password" name="j_password" value=""/></td>
                </tr>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="2" align="center"><br/>
                        <input class="btn-submit" type="submit" onclick="sub('${pageContext.request.contextPath}/j_spring_security_check')" value="登录"/>

                    </td>
                </tr>
                </tfoot>
            </table>
        </form>
    </div>
</div>
</body>
</html>