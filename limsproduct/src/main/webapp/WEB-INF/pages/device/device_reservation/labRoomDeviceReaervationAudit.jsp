<%@page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.project-resources"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
						<li><a href="javascript:void(0)">开放预约</a></li>
						<c:if test="${state ==0}">
							<li class="end"><a href="javascript:void(0)">大型仪器设备预约查看</a></li>
						</c:if>
						<c:if test="${state !=0}">
							<li class="end"><a href="javascript:void(0)">大型仪器设备预约审核管理</a></li>
						</c:if>
					</ul>
				</div>
			</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<c:if test="${state !=0}">
					<font style="color: red">注：从左往右按级别审核，只有前一级审核通过，后面才可以审核！每一级审核完成后不可更改结果！！</font>
				</c:if>
				<div class="tool-box">
					<form:form name="form">
						<input type="button" onclick="window.history.go(-1)" value="返回">
					</form:form>
				</div>
				<form:form modelAttribute="project">
				<table>
					<tr>
						<td>实验室:${labRoomDeviceReservation.labRoomDevice.labRoom.labRoomName}</td>

						<td>预约日期:<fmt:setLocale value="zh_CN"/><fmt:formatDate
								value="${labRoomDeviceReservation.begintime.time}" pattern="yyyy-MM-dd"/></td>

						<td>预约时间:<fmt:formatDate value="${labRoomDeviceReservation.begintime.time}"
												 pattern="HH:mm:ss"/>至<fmt:formatDate
								value="${labRoomDeviceReservation.endtime.time}" pattern="HH:mm:ss"/></td>

						<td>申请人:${labRoomDeviceReservation.userByReserveUser.cname}</td>
						<td>申请人联系方式:${labRoomDeviceReservation.phone}</td>
						<td>申请原因:${labRoomDeviceReservation.content}</td>


					</tr>
				</table>
				<div id="tt" class="easyui-tabs" style="height:910px;">
					<%--<c:if test="${labRoomDevice.CDictionaryByTeacherAudit.CName eq '是' && labRoomDeviceReservation.userByTeacher ne null}">--%>
						<%--<div title="教师">--%>
							<%--<iframe scrolling="yes" frameborder="0" id="myAtendList"--%>
									<%--src="${pageContext.request.contextPath}/LabRoomDeviceReservation/teacherAudit?id=${labRoomDeviceReservation.id}&tage=${tage}&state=${state}&currpage=${currpage}"--%>
									<%--style="width:100%;height:100%;" scrolling="no"--%>
									<%--onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>--%>
						<%--</div>--%>
					<%--</c:if>--%>
					<%--<c:if test="${labRoomDevice.dean eq 1}">--%>
						<%--<div title="系主任/系教学秘书审核">--%>
							<%--<iframe scrolling="yes" frameborder="0" id="myResponseList"--%>
									<%--src="${pageContext.request.contextPath}/LabRoomDeviceReservation/deanAudit?id=${labRoomDeviceReservation.id}&tage=${tage}&state=${state}&currpage=${currpage}"--%>
									<%--style="width:100%;height:100%;" scrolling="no"--%>
									<%--onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>--%>
						<%--</div>--%>
					<%--</c:if>--%>
					<%--<c:if test="${labRoomDevice.CDictionaryByLabManagerAudit.CName eq '是' }">--%>
						<%--<div title="实验室管理员审核">--%>
							<%--<iframe scrolling="yes" frameborder="0" id="myResponseList1"--%>
									<%--src="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomAdminAudit?id=${labRoomDeviceReservation.id}&tage=${tage}&state=${state}&currpage=${currpage}"--%>
									<%--style="width:100%;height:100%;" scrolling="no"--%>
									<%--onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>--%>
						<%--</div>--%>
					<%--</c:if>--%>
					<%--<c:if test="${labRoomDevice.trainingCenterDirector eq 1}">--%>
						<%--<div title="实训中心主任审核">--%>
							<%--<iframe scrolling="yes" frameborder="0" id="myResponseList2"--%>
									<%--src="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomCenterDirectorAudit?id=${labRoomDeviceReservation.id}&tage=${tage}&state=${state}&currpage=${currpage}"--%>
									<%--style="width:100%;height:100%;" scrolling="no"--%>
									<%--onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>--%>
						<%--</div>--%>
					<%--</c:if>--%>
					<%--<c:if test="${labRoomDevice.trainingDepartmentDirrector eq 1}">--%>
						<%--<div title="实训部主任审核">--%>
							<%--<iframe scrolling="yes" frameborder="0" id="myResponseList3"--%>
									<%--src="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDepartmentDirectorAudit?id=${labRoomDeviceReservation.id}&tage=${tage}&state=${state}&currpage=${currpage}"--%>
									<%--style="width:100%;height:100%;" scrolling="no"--%>
									<%--onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>--%>
						<%--</div>--%>
					<%--</c:if>--%>
						<c:forEach items="${auditItems}" varStatus="i" var="audit">
							<div title="${audit[0]}">
								<iframe scrolling="yes" frameborder="0" id="myAtendList"
										src="${pageContext.request.contextPath}/LabRoomDeviceReservation/deviceReservationAllAudit?id=${labRoomDeviceReservation.id}&tage=${tage}&state=${audit[1]}&currpage=${currpage}&authName=${audit[2]}"
										style="width:100%;height:100%;" scrolling="no"
										onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
							</div>
						</c:forEach>
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