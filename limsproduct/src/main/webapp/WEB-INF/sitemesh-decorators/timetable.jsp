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
			@import url("${pageContext.request.contextPath}/css/style.css");
			@import url("${pageContext.request.contextPath}/static_limsproduct/css/global_static.css");
		</style>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/spring/Spring.js"></script>
		<!-- 弹出框插件的引用 -->
		<script src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/layui/layui.js" charset="utf-8"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
		<script src="${pageContext.request.contextPath}/pages/layui/layui/layui/lay/formselect/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/pages/layui/layui/layui/lay/formselect/optionRule.js"></script>
		<script src="${pageContext.request.contextPath}/pages/layui/layui/layui/lay/formselect/formSelects-v4.min.js"></script>

		<link href="${pageContext.request.contextPath}/pages/layui/layui/layui/lay/formselect/formSelects-v4.css" rel="stylesheet" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/expand.css" media="all">
		<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.tablesorter.js"></script> --%>
		<% if (((HttpServletRequest)pageContext.getRequest()).getServletPath().equals("/index.jsp")){ %>
		<link href="${pageContext.request.contextPath}/css/dashboard.css" rel="stylesheet" type="text/css" /> 
		<% } %>     
		<% if (((HttpServletRequest)pageContext.getRequest()).getServletPath().equals("/pages/login.jsp")){ %>
		<link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css" /> 
		<% } %>		
		<%-- <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" /> --%>
		<decorator:head />
    </head>
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



