<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<html>
<head>
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<meta name="decorator" content="iframe"/>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
<script type="text/javascript">
function cancleSubmit(){
	window.location.href="${pageContext.request.contextPath}/basic_data/registrationAchievementsList?currpage=1";
}
</script>
</head>
<body>
<div class="iStyle_RightInner">

<div class="navigation">
<div id="navigation">
<ul>
	<li><a href="javascript:void(0)"><spring:message code="left.Performance.statement" /></a></li>
	<li class="end"><a href="javascript:void(0)"><spring:message code="left.innovativeProduction.list" /></a></li>
</ul>
</div>
</div>


<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab1 selected" id="s1">
			<a href="javascript:void(0)">创新成果登记列表</a>
		</li>
		<a class="btn btn-new" href="${pageContext.request.contextPath}/basic_data/newRegistrationAchievements?currpage=1">新建</a>
	</ul>
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<%--<div class="title">--%>
<%--<div id="title">创新成果登记列表</div>--%>
	  <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/basic_data/newRegistrationAchievements?currpage=1">新建</a>--%>
<%--</div>--%>
<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/basic_data/searchRegistrationAchievements">
			 <ul>
  				<li>创新成果名称:
					<input value="${innovationname}" name="innovationname" id="innovationname" type="text"/>
				</li>
  				<li>
					<input type="submit" value="查询"/>
			      	<input class="cancel-submit" type="button" value="取消" onclick="cancleSubmit()"/>
				</li>
  				</ul>
			 
		</form:form>
</div>
</div>
	<div class="content-box">
<table> 
<thead>
<tr>
<th>姓名</th>
<th>所使用的实验室</th>
<th>创新成果名称</th>
<th>分值</th>
<th>操作</th>
</tr>
</thead>
<tbody>
<c:forEach items="${InnovationAchievementRegistrations}" var="curr">
<tr>
<td>${curr.cname}</td>
<td>${curr.labRoomName}</td>
<td>${curr.innovationName}</td>
<td>${curr.score}</td>
<td><a href="${pageContext.request.contextPath}/basic_data/editRegistrationAchievement?id=${curr.id}">编辑</a>
<a href="${pageContext.request.contextPath}/basic_data/deleteRegistrationAchievements?id=${curr.id}" onclick="return confirm('确定删除？');">删除</a>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</div>
</div>
</div>
</div>
</div>

<div id="repairRecords" class="easyui-window" title="修改个人信息" modal="true" closed="true" iconCls="icon-add" style="width:1000px;height:200px">
<form:form action="${pageContext.request.contextPath}/personal/saveMyInfo" method="POST" onsubmit="return checkMail()" modelAttribute="user">	
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="tool-box">
<table>

</table>

</div>
<input id="save" type="submit" value="保存">
</div>
</div>
</div>
</div>
</form:form>

</div>
</body>
</html>
