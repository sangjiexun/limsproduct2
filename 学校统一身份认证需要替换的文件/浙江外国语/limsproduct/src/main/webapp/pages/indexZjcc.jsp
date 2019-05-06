<%@page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="com.wiscom.is.IdentityFactory, com.wiscom.is.IdentityManager" %>
<%@page import="java.net.URLDecoder" %>
<!doctype html>
<html lang="en">
<head>
    <title>实验室智能管理系统</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
</head>
<%
    String is_config = request.getRealPath("/client.properties");
	//先检查当前请求cookie中是否存在 SSOToken（即 iPlanetDirectoryPro）
    Cookie all_cookies[] = request.getCookies();
    Cookie myCookie;
	String mcki="";
    String decodedCookieValue = null;
    if (all_cookies != null) {
        for(int i=0; i< all_cookies.length; i++) {
            myCookie = all_cookies[i];
			mcki +=myCookie.getName()+"/"+myCookie.getValue()+"; ";
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
	
    if ( curUser.length()==0){
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
        }
    });
	
</script>
<form id="subform" name='f' action='${pageContext.request.contextPath}/loginByUsername' method='POST'>
    <td><input type="hidden" name="username"  value="<%=curUser %>"/></td>
</form>
</body>
</html>