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
						<li><a href="javascript:void(0)"><spring:message code="all.trainingRoom.labroom" />借用</a></li>
						<%--<li class="end"><a href="javascript:void(0)"><spring:message code="left.trainingRoom.setting"/>借用进度查看</a></li>--%>
						<li class="end"><a href="javascript:void(0)"><spring:message code="all.trainingRoom.labroom"/>借用进度查看</a></li>
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
             <td><spring:message code="all.trainingRoom.labroom" />名称：</td><td>${labReservation.labRoom.labRoomName}</td>
             <td><spring:message code="all.trainingRoom.labroom" />编号：</td> <td>${labReservation.labRoom.labRoomNumber}</td>
             <td>申请人：</td> <td>${labReservation.user.cname}</td>
             <td>借用日期：</td> <td><fmt:formatDate value="${labReservation.lendingTime.time}" pattern="yyyy-MM-dd"/></td>
             <td>借用时间：</td>
			 <td>
				 <c:forEach items="${labReservation.labReservationTimeTables}" var="currentTA" varStatus="taI">
					 <fmt:formatDate value="${currentTA.startTime.time}" pattern="HH:mm"/>-<fmt:formatDate value="${currentTA.endTime.time}" pattern="HH:mm"/>
				 </c:forEach>
			 </td>
			 <td>预约人电话：</td><td>${labReservation.lendingUserPhone}</td>
         </tr>
	 <tr >
		 <td>预约用途：</td><td>${labReservation.CDictionaryByLendingType.CName}</td>
		 <td>使用对象：</td><td>${labReservation.CDictionaryByLendingUserType.CName}</td>
		 <td>班级：</td><td>${labReservation.schoolClasses.className}</td>
		 <td>预约部门：</td><td>${labReservation.lendingUnit}</td>
		 <td>使用人数：</td><td>${labReservation.number}</td>
		 <td>预约原因：</td><td>${labReservation.lendingReason}</td>
	 </tr>
      </table>
<div id="tt" class="easyui-tabs" style="height:910px;">
	<c:forEach items="${isOpens}" var="isOpen" varStatus="i">
		<c:if test="${isOpen eq 1}">
			<div title="${titles.get(i.count-1)}">
				<iframe scrolling="yes" frameborder="0" id="myResponseList" src="${pageContext.request.contextPath}/labRoomLending/labReservationAllAudit?id=${labReservation.id}&tage=${tage}&state=${i.count}&page=${page}" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
			</div>
		</c:if>
	</c:forEach>
</div>
</form:form>
<div class="clear">&nbsp;</div>
</div>

</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">
	$(document).ready(function () {
		if(${noLabReservation eq 1}){
			alert("该预约已作废");
			window.history.back();
		}
	});
</script>
</html>