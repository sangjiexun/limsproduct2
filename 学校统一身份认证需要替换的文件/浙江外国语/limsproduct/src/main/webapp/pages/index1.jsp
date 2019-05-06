<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<head>
    <meta charset="utf-8">
    <title>实验室智能管理系统</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
    <%
        session.setAttribute("LOGINTYPE", "cmsIndex");
    %>

</head>
<body style="display: none">
<script type="text/javascript">

    $(function() {
        var loginUser= '<%=request.getSession().getAttribute("username") %>';
        if(''!=loginUser && loginUser!=null && 'null'!=loginUser){
            //$("#subform").submit();
            document.f.submit();
        }else{
            console.log("userName is null");
        }
    });
</script>
<form id="subform" name='f' action='${pageContext.request.contextPath}/j_spring_security_check' method='POST'>
    <td><input type="hidden" name="j_username"  value="<%=request.getSession().getAttribute("username") %>"/></td>
    <td><input type="hidden" name="j_password" value="<%=request.getSession().getAttribute("password") %>"/></td>
</form>
</body>
</html>