<%@page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@page import="com.wiscom.is.IdentityFactory, com.wiscom.is.IdentityManager" %>
<%@page import="java.net.URLDecoder" %>
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
    /*Object username = request.getSession().getAttribute("username");
    if(username!=null){
        String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        response.sendRedirect(url+"/test");
    }*/
%>
<%
    String is_config = request.getRealPath("/client.properties");
    Cookie all_cookies[] = request.getCookies();
    Cookie myCookie;
    String decodedCookieValue = null;
    if (all_cookies != null) {
        for(int i=0; i< all_cookies.length; i++) {
            myCookie = all_cookies[i];
            if( myCookie.getName().equals("iPlanetDirectoryPro") ) {
                decodedCookieValue = URLDecoder.decode(myCookie.getValue(),"GB2312");
            }
        }
    }

    IdentityFactory factory = IdentityFactory.createFactory( is_config );
    IdentityManager im = factory.getIdentityManager();

    String curUser = "";
    if (decodedCookieValue != null ) {
        curUser = im.getCurrentUser( decodedCookieValue );
    }
    if ( curUser.length()==0 ){
        String gotoURL = HttpUtils.getRequestURL(request).toString();

        String loginURL = im.getLoginURL() +"?goto=" + java.net.URLEncoder.encode(gotoURL);
        %>
        <%response.sendRedirect(loginURL);%>
        <%
    }
%>
<body style="display: none">
<script type="text/javascript">
    $(function($) {
        var loginUser='<%=curUser %>';
        if(''!=loginUser && loginUser!=null && 'null'!=loginUser){
            $("#subform").submit();
        }else{
            console.log("userName is null");
//            alert("userName is null");
//            location.href='http://my.dgpt.edu.cn/wps/portal';
        }
    });
</script>
<form id="subform" name='f' action='${pageContext.request.contextPath}/j_spring_security_check' method='POST'>
    <td><input type="hidden" name="j_username"  value="<%=curUser %>"/></td>
    <td><input type="hidden" name="j_password" value="<%=curUser %>" /></td>
</form>
</body>
</html>