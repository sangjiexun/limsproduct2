<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html >  
<head>
 <meta name="decorator" content="iframe"/>
<title><fmt:message key="html.title"/></title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
</head>
<body style="overflow:hidden">



<!-- 结项申报列表 -->
<!-- <div class="tab"> -->
<div id="content" >
<div id="tablewrapper" class="content-box">

<div class="list_top">
		<ul class="top_tittle">
			<li></li>
		</ul>
		<ul id="list-nav" >
			
		</ul>
		<ul class="new_bulid">
			
		</ul>
 </div>


	
<div class="content-box">

		<fieldset class="introduce-box">
								<legend>审核信息</legend>

 <table id="listTable"   >
 	<c:if test="${reservation.labRoomDevice.CDictionaryByIsAudit.id==622}"><!-- 不需要审核 -->
 	该设备预约不需要审核
 	</c:if>
 	<c:if test="${reservation.labRoomDevice.CDictionaryByIsAudit.id==621}"><!-- 需要审核 -->
 		<c:if test="${reservation.labRoomDevice.CDictionaryByTeacherAudit.id==622}"><!-- 不需要老师审核 -->
 			该设备预约不需要老师审核
 		</c:if>
 		<c:if test="${reservation.labRoomDevice.CDictionaryByTeacherAudit.id==621}"><!-- 需要老师审核 -->
 			<c:if test="${user.username==reservation.userByReserveUser.username}"><!-- 当前登录人为预约人 -->
	 			<c:if test="${empty reservationResult}"><!-- 审核为空 -->
			 	暂未审核
			 	</c:if>
			 	
			 	<c:if test="${not empty reservationResult}"><!-- 审核不为空 -->
			 	<tr align="center" >
			     <th>审核人</th>
			     <td >${reservationResult.user.cname}</td> 
			      <th>审核结果</th>
			     <td>${reservationResult.CTrainingResult.name}</td>  
			   	</tr> 
			   	<tr align="center" >
			     <th>备注</th>
			     <td>${reservationResult.remark}</td>  
			   	</tr>   
			 	</c:if>
 			</c:if>
 			<c:if test="${user.username==reservation.userByTeacher.username}"><!-- 当前登录人为审核人（老师） -->
 			
 			<form:form action="${pageContext.request.contextPath}/device/saveTeacherAudit?id=${reservation.id}" method="post" modelAttribute="reservationResult">
						<table>
							<tr>
								<td>审核意见</td>
								<td><form:textarea path="remark"/> </td>
							</tr>
							<tr>
								<td>是否通过</td>
								<td>
								<form:radiobuttons path="CTrainingResult.id" items="${results}" itemLabel="name" itemValue="id"/>
								</td>
							</tr>
							<!-- 只有老师未审核的才能提交 -->
							<c:if test="${reservation.stage==0}">
							<tr>
								<td>
								<input class="btn-big" type="submit" value="提交">
								<input class="btn-return" type="button" value="取消">
								</td>
								
							</tr>
							</c:if>
						</table>
						</form:form>
 			</c:if>
 			<c:if test="${flag==true}"><!-- 当前登录人为审核人（实训室管理员） -->
 				<c:if test="${empty reservationResult}"><!-- 审核为空 -->
			 	暂未审核
			 	</c:if>
			 	
			 	<c:if test="${not empty reservationResult}"><!-- 审核不为空 -->
			 	<tr align="center" >
			     <th>审核人</th>
			     <td >${reservationResult.user.cname}</td> 
			      <th>审核结果</th>
			     <td>${reservationResult.CTrainingResult.name}</td>  
			   	</tr> 
			   	<tr align="center" >
			     <th>备注</th>
			     <td>${reservationResult.remark}</td>  
			   	</tr>   
			 	</c:if>
 			</c:if>
	 	</c:if>
 	
 	</c:if>
 	
 	
 	
 
 </table>
 </fieldset>
 
 </div>
 
</div><!-- </div> -->

</div>
</body>
<!-------------列表结束----------->
</html>