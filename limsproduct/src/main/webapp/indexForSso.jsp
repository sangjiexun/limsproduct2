<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.Map"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>   
<meta name="decorator" content="none"/>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>实验（<spring:message code="all.trainingRoom.labroom" />）室建设与教学信息管理系统</title>
<%--<title><spring:message code="title.html.name"/></title>--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/is_LeftList.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/is_Searcher.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/is_SystemUI_Allpages.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
	$(function($) {
		var loginUser = '${username}';
		if(''==loginUser || loginUser == null || loginUser == 'null'){
			// do nothing
		}else{
			$('#j_username').val(loginUser);
            $('#j_password').val(loginUser);
            $("#login").submit();
		}
	});	
</script> 


</head>
<!--header-->
     <form id="login" name='f' action='${pageContext.request.contextPath}/j_spring_security_check' method='POST' style='display:none '> 
  <table  class="sit_login_form">
    <tbody>
      <tr>
  <th><label for="signin_username">用户名</label></th>
  <td><input type="text" id="j_username" name="j_username" value="" /></td>
</tr>
<tr>
  <th><label for="signin_password">密  码</label></th>
  <td><input type="password" id="j_password" name="j_password" value="" /></td>
</tr>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="2" align="center"><br/>
            <input type="submit" value="登录"/>
        </td>
      </tr>
    </tfoot>
  </table>
</form>
</body>
</html>