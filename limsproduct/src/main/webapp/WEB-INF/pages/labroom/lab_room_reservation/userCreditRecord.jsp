<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>


<html>
<head>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<meta name="decorator" content="iframe"/>
 <script type="text/javascript">

 </script> 
</head>
<body>

<div class="navigation">
<div id="navigation">
	<ul>
	<li><a href="javascript:void(0)"><spring:message code="left.credit.score"/></a></li>
	<li><a class="end">扣分记录</a></li>
	</ul>
</div>
</div>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<div class="title">
	<a class="btn btn-common" onclick="window.history.go(-1)" href="javascript:void(0)" style="float:right;display:block;margin-top:5px;">返回</a> 
</div>

<table> 
<thead>
<tr>
  <!--  <th>选择</th> -->
<!--   <th width="10%">序号</th> -->
  <th width="10%">预约实验室</th>
  <th width="10%">申请人姓名</th>	
  <!-- <th width="10%">申请内容</th>  -->
  <!-- <th width="10%">日期</th> -->
  <th width="10%">扣分项</th>
  <th width="10%">扣分</th>	
  <th width="10%">评语</th>
</tr>
</thead>
<tbody>
<c:forEach items="${listLabRoomStationReservationCredit}" var="current" varStatus="i">
				
				<tr>
<!-- 					<td>${i.index+1}</td> -->
					<td>${current.labRoomStationReservation.labRoom.labRoomName}</td>
					<td>${current.labRoomStationReservation.user.cname}</td>
					<%-- <td>${current.labRoomStationReservation.content}</td> --%>
					<%-- <td><fmt:formatDate value="${current.labRoomStationReservation.time.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
					<td>${current.creditOption.name}</td>
					<td>${current.creditOption.deduction}</td>
					<td>${current.remark}</td>
				</tr>

</c:forEach> 
<c:forEach items="${listLabRoomDeviceReservationCredit}" var="current1" varStatus="j">
				<tr>
					<td>${current1.labRoomDeviceReservation.labRoomDevice.labRoom.labRoomName}</td>
					<td>${current1.labRoomDeviceReservation.userByReserveUser.cname}</td>
					<td>${current1.creditOption.name}</td>
					<td>${current1.creditOption.deduction}</td>
					<td>${current1.remark}</td>
				</tr>
</c:forEach>
<c:forEach items="${listLabRoomReservationCredit}" var="current2" varStatus="k">
				<tr>
					<td>${current2.labReservation.labRoom.labRoomName}</td>
					<td>${current2.labReservation.user.cname}</td>
					<td>${current2.creditOption.name}</td>
					<td>${current2.creditOption.deduction}</td>
					<td>${current2.remark}</td>
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
</div>
</div>
</body>
</html>