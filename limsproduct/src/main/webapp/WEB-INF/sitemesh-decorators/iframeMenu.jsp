<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.application-resources"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
	<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
    <head>
        <title><spring:message code="title.school.name" />实训室管理系统</title>
		<style type="text/css" media="screen">
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/icon.css");
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/gray/easyui.css");
			@import url("${pageContext.request.contextPath}/css/style.css");
			@import url("${pageContext.request.contextPath}/static_limsproduct/css/global_static.css");
		</style>
		<!-- 弹出框插件的引用 -->
		<% if (((HttpServletRequest)pageContext.getRequest()).getServletPath().equals("/index.jsp")){ %>
		<link href="${pageContext.request.contextPath}/css/dashboard.css" rel="stylesheet" type="text/css" /> 
		<% } %>     
		<% if (((HttpServletRequest)pageContext.getRequest()).getServletPath().equals("/pages/login.jsp")){ %>
		<link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css" /> 
		<% } %>		
		<%-- <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" /> --%>
		<decorator:head />
    </head>
     <%
	System.out.println(session.getAttribute("LOGINTYPE"));
%>
	<body style="overflow-y:auto;">	
		<div class="dmrrr">		      
				<% if (((HttpServletRequest)pageContext.getRequest()).getServletPath().equals("/index.jsp")){ %>
				<% }else{ %>
				<decorator:body />
				<% } %>
				<div class="hidden">&nbsp;</div>     
			<!-- end contentwrapper -->   
		</div><!-- end wrapper -->
	</body>
</html>



