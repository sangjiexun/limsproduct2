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
<body style="display:none;">
                <form class="sub_form" name='f' action='${pageContext.request.contextPath}/j_spring_security_check'
                      method='POST'>
                    <input class="login_input" style="margin-bottom:15px;" type="text" name="j_username"
                           placeholder="请输入用户名"/>
                    <input class="login_input" style="margin-bottom:15px;background:none;color:#333;" type="password"
                           name="j_password" placeholder="请输入密码"/>
                    <input class="btnl text" type="submit" value="登录" style="width:286px"/>
                </form>

</div>
<script type="text/javascript">
    document.f.submit();
</script>
</body>

</html>