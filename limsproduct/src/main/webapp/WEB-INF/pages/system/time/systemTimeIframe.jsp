<%@page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.project-resources"/>
<html>
<head>
<meta name="decorator" content="iframe"/>

<title>Edit <fmt:message key="project.title"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/css/demo.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.8.3.min.js"></script><%--
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
	--%><script type="text/javascript" src="${pageContext.request.contextPath}/jquery/locale/easyui-lang-zh_CN.js"></script>
	<%--<link href="${pageContext.request.contextPath}/css/style.css"--%>
	<%--rel="stylesheet">--%>
</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)"><spring:message code="left.system.management" /></a></li>
						<li class="end"><a href="javascript:void(0)"><spring:message code="left.section.management" /></a></li>
					</ul>
				</div>
			</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab1 selected" id="s1">
			<a href="javascript:void(0)">节次管理</a>
		</li>
		<sec:authorize ifAnyGranted="ROLE_PREEXTEACHING,ROLE_SUPERADMIN">
			<a class="btn btn-edit"  href="${pageContext.request.contextPath}/system/newTime?campusNumber=${campusNumber}">新建</a>
		</sec:authorize>
	</ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
<div class="content-box">		     

<div id="tt" class="easyui-tabs" style="height:910px;">

<iframe scrolling="yes" frameborder="0" id="myAtendList" src="${pageContext.request.contextPath}/system/listTime?currpage=1&campusNumber=1" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>



</div>
<div class="clear">&nbsp;</div>
</div>

</div>
</div>
</div>
</div>
</body>
</html>