<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>实验室智能管理系统</title>
<meta name="decorator" content="none">
<meta name="Keywords" content="实验室智能管理系统">
<meta name="Description" content="实验室智能管理系统">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="Generator" content="gvsun">
<meta name="Author" content="lyyyyyy">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<link style="type/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/index/global_min.css">
</head>

<body >
	<form id="loginForm" name="loginForm"  action="${userAuthorizationUri}" method="post">
		<%-- <input type="hidden" name="accessTokenUri" id="accessTokenUri" value="${accessTokenUri}"> --%>
		<input type="hidden" name="redirect_uri" id="redirect_uri" value="${applicationHost}/authorization_code_callback">
		<%-- <input type="userInfoUri" name="userInfoUri" id="userInfoUri" value="${userInfoUri}"> --%>
		<input type="hidden" name="client_id" id="client_id" value="${client_id}">
		<%-- <input type="secretKey" name="secretKey" id="secretKey" value="${secretKey}"> --%>
		<input type="hidden" name="scope" id="scope" value="${scope}">
		<input type="hidden" name="response_type" id="response_type" value="code">
		<input type="hidden" name="state" id=state value="xvvvf">
		<%--<input type="hidden" name="grant_type" id="grant_type" value="grant_type"> 
	--%></form>
	<script language=JavaScript> 
	   document.loginForm.submit();  
	</script> 
</body>

</html>