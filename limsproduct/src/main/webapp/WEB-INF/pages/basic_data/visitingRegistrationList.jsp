<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%--<fmt:setBundle basename="bundles.coursearrange-resources"/>--%>
<html>
<head>
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<meta name="decorator" content="iframe"/>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
<script type="text/javascript">
$(function(){
    //设置邮箱需要验证
    $("#email").validatebox();
})
function addRecords(){
         $("#repairRecords").window('open');      
    }

function checkMail(){
	var email = $("#email").val();
	if(email.trim()!=""){
		var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(email)){
			alert('您的邮箱格式不正确');
			return false;
		}
	}
	
}
</script>
</head>
<body>
<div class="iStyle_RightInner">


<c:if test="${flag==1 }">
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.Performance.statement" /></a></li>
				<li class="end"><a href="javascript:void(0)"><spring:message code="left.outsideSchoolVisit.list" /></a></li>
			</ul>
		</div>
	</div>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab1 selected" id="s1">
			<a href="javascript:void(0)">校外参观登记列表</a>
		</li>
		<a class="btn btn-new" href="${pageContext.request.contextPath}/basic_data/newVisitingRegistration?currpage=1&flag=1">新建</a>
	</ul>
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<%--<div class="title">--%>
 <%--<div id="title">校外参观登记列表</div>--%>
	  <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/basic_data/newVisitingRegistration?currpage=1&flag=1">新建</a>--%>
<%--</div>--%>
<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/basic_data/searchVisitingRegistration?flag=1">
			 <ul>
  				<li>登记人:
					<input name="registrant" type="text"/></li>
  				<li>
					<input type="submit" value="查询"/>
			      	<input class="cancel-submit" type="button" value="取消" onclick="window.history.go(-1)"/></li>
  				</ul>
			 
		</form:form>
</div>
</div>
	<div class="content-box">
<table> 
<thead>
<tr>
<th>课时数</th>

<th>日期</th>
<th>参观单位</th>
<th>参观人数</th>
<th>参观实训室</th>
<th>登记人</th>
<th>操作</th>
</tr>
</thead>
<tbody>
<c:forEach items="${visitingRegistrations}" var="curr">
<tr>
<td>${curr.courseHours}</td>
<td><fmt:formatDate value="${curr.date.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<td>${curr.visitingUnit}</td>
<td>${curr.visitorNumber}</td>
<td>${curr.labroomVisiting}</td>
<td>${curr.registrant}</td>
<td><a href="${pageContext.request.contextPath}/basic_data/editVisitingRegistration?flag=1&id=${curr.id}">编辑</a>
<a href="${pageContext.request.contextPath}/basic_data/deleteVisitingRegistration?id=${curr.id}" onclick="return confirm('确定删除？');">删除</a>
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
</c:if>

<c:if test="${flag==2}">
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.Performance.statement" /></a></li>
				<li class="end"><a href="javascript:void(0)"><spring:message code="left.insideSchoolVisit.list" /></a></li>
			</ul>
		</div>
	</div>
<!-- 校内参观登记 -->
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab1 selected" id="s1">
			<a href="javascript:void(0)">校内参观登记列表</a>
		</li>
		<a class="btn btn-new" href="${pageContext.request.contextPath}/basic_data/newVisitingRegistration?currpage=1&flag=2">新建</a>
	</ul>
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<%--<div class="title">--%>
<%--<div id="title">校内参观登记列表</div>--%>
	  <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/basic_data/newVisitingRegistration?currpage=1&flag=2">新建</a>--%>
<%--</div>--%>
<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/basic_data/searchVisitingRegistration?flag=2">
			 <ul>
  				<li>项目名称:
					<input name="projectname" type="text"/></li>
  				<li>
					<input type="submit" value="查询"/>
			      <input class="cancel-submit" type="button" value="取消" onclick="window.history.go(-1)"/></li>
  				</ul>
			 
		</form:form>
</div>
</div>
	<div class="content-box">
<table>
<thead>
<tr>
<th>课程名称</th>
<th>项目名称</th>
<th>教师</th>
<th>班级</th>
<th>参观实训室</th>
<th>课时数</th>
<th>日期</th>
<th>学生人数</th>
<th>操作</th>
</tr>
</thead>
<tbody>
<c:forEach items="${visitingRegistrations}" var="curr">
<tr>
<td>${curr.courseName}</td>
<td>${curr.projectName}</td>
<td>${curr.teacher}</td>
<td>${curr.class_}</td>
<td>${curr.labroomVisiting}</td>
<td>${curr.courseHours}</td>
<td><fmt:formatDate value="${curr.date.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<td>${curr.studentNumber}</td>
<td><a href="${pageContext.request.contextPath}/basic_data/editVisitingRegistration?flag=2&id=${curr.id}">编辑</a>
<a href="${pageContext.request.contextPath}/basic_data/deleteVisitingRegistration?id=${curr.id}" onclick="return confirm('确定删除？');">删除</a>
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
</c:if>
</body>
</html>
