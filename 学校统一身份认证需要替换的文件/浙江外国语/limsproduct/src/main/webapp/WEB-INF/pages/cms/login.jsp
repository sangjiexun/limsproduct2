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
    <link href="${pageContext.request.contextPath}/css/cms/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cms/media-queries.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>

    <!-- Slider Plugin -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/index/jquery.glide.js"></script>
    <link href="${pageContext.request.contextPath}/css/cms/bdsstyle.css" rel="stylesheet" type="text/css">
    <%session.setAttribute("LOGINTYPE", "cmsIndex"); %>
</head>
<body>
<div class="black_overlay">
    <div class="login_frame">
        <img src="images/system_pic/${PROJECT_NAME}_login.png" height="60" alt=""/>
        <div class="login">
            <div class="holder">
                <form class="sub_form" name='f' action='${pageContext.request.contextPath}/loginZjcc'
                      method='POST'>
                    <input class="login_input" style="margin-bottom:15px;" type="text" name="username"
                           placeholder="请输入用户名"/>
                    <input class="login_input" style="margin-bottom:15px;background:none;color:#333;" type="password"
                           name="password" placeholder="请输入密码"/>
                    <input class="btnl text" type="submit" value="登录" style="width:286px"/>
                </form>
            </div>
        </div>
    </div>
</div>
</body>

</html>