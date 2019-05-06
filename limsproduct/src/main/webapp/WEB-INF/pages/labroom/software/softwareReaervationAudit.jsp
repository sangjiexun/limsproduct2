<%@page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%--<fmt:setBundle basename="bundles.project-resources"/>--%>
<html>
<head>
<meta name="decorator" content="iframe"/>
 <meta name="decorator" content="none"/> 

<title>Edit <fmt:message key="project.title"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/css/demo.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/locale/easyui-lang-zh_CN.js"></script>
	<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet">
	<link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)">软件安装申请管理</a></li>
						<li class="end"><a href="javascript:void(0)">软件安装申请管理</a></li>
					</ul>
				</div>
			</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
<div class="content-box">
<font style="color: red">注：从左往右按级别审核，只有前一级审核通过，后面才可以审核！每一级审核完成后不可更改结果！！</font>
 <div class="tool-box">
                <form:form name="form">
					<input type="button" onclick="window.history.go(-1)" value="返回">
				</form:form>
			</div>		     
<form:form  modelAttribute="project">
 <table >
         <tr >
             <th>申请安装软件：</th><td>${softwareReserve.name}</td>
             <th>安装实验室：</th> <td>${softwareReserve.labRoom.labRoomName}</td>
             <th>安装时间：</th> <td><fmt:formatDate value="${softwareReserve.requireTime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
             <th>申请日期：</th> <td><fmt:formatDate value="${softwareReserve.createTime.time}" pattern="yyyy-MM-dd"/></td>
             <th>申请人：</th><td>${softwareReserve.user.cname}</td>
         </tr>
	 	<tr>
			<th>系统要求：</th><td>${softwareReserve.requirement}</td>
			<th>光盘(有/无)：</th><td>
				<c:if test="${softwareReserve.cd eq true}">有</c:if>
				<c:if test="${softwareReserve.cd eq false}">无</c:if></td>
			<th>加密狗(有/无)：</th><td>
				<c:if test="${softwareReserve.dongle eq true}">有</c:if>
				<c:if test="${softwareReserve.dongle eq false}">无</c:if></td>
			<th>点数：</th><td>${softwareReserve.point}</td>
			<th>课程：</th><td>${softwareReserve.course}</td>
		</tr>
	 	<tr>
			<th>软件架构：</th><td>${softwareReserve.frame}</td>
			<th>申请理由：</th><td>${softwareReserve.applyReason}</td>
			<th>供应商：</th><td>${softwareReserve.supply}</td>
			<th>供应商联系方式：</th><td>${softwareReserve.supplyPhone}</td>
			<th></th><td></td>
		</tr>

      </table>
<div id="tt" class="easyui-tabs" style="height:910px;">
<div title="系主任审核">
<iframe scrolling="yes" frameborder="0" id="myResponseList" src="${pageContext.request.contextPath}/SoftwareReservation/deanAudit?id=${softwareReserve.id}&tage=${tage}&state=${state}&page=${page}" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
</div>
<div title="实验室管理员审核">
<iframe scrolling="yes" frameborder="0" id="myResponseList" src="${pageContext.request.contextPath}/SoftwareReservation/labRoomAdminAudit?id=${softwareReserve.id}&tage=${tage}&state=${state}&page=${page}" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
</div>
<div title="实训中心主任审核">
<iframe scrolling="yes" frameborder="0" id="myResponseList" src="${pageContext.request.contextPath}/SoftwareReservation/labRoomCenterDirectorAudit?id=${softwareReserve.id}&tage=${tage}&state=${state}&page=${page}" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe></div>
<div title="实训部教学秘书审核">
<iframe scrolling="yes" frameborder="0" id="myResponseList" src="${pageContext.request.contextPath}/SoftwareReservation/labRoomDepartmentDirectorAudit?id=${softwareReserve.id}&tage=${tage}&state=${state}&page=${page}" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe></div>
</div>
</div>
</form:form>
<div class="clear">&nbsp;</div>
</div>

</div>
</div>
</div>
</div>
</body>
</html>