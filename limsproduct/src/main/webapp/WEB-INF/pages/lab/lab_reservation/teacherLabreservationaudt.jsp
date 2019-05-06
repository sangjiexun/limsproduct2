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
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
<div class="content-box">	

<!-- <div class="list_top">
		<ul class="top_tittle">
			<li></li>
		</ul>
		<ul id="list-nav" >
			
		</ul>
		<ul class="new_bulid">
			<li class="new_bulid_1"><a onclick="window.history.go(-1)">返回</a></li>
		</ul>
 </div> -->


   <!--   <td>一、基本信息</td></tr> -->
<c:if test="${man>0}">
 <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
 <tr align="center" >
     <td>周次:</td>
     <td><c:forEach items="${l.week }"  var="a" >
	     <c:if test="${a=='563' }">第一周，</c:if>   
		<c:if test="${a=='564' }">第二周，</c:if>
		<c:if test="${a=='565' }">第三周，</c:if>
		<c:if test="${a=='566' }">第四周，</c:if>
		<c:if test="${a=='567' }">第五周，</c:if>
		<c:if test="${a=='568' }">第六周，</c:if>
		<c:if test="${a=='569' }">第七周，</c:if>
		<c:if test="${a=='570' }">第八周，</c:if>
		<c:if test="${a=='571' }">第九周，</c:if>
		<c:if test="${a=='572' }">第十周，</c:if>
		<c:if test="${a=='573' }">第十一周，</c:if>
		<c:if test="${a=='574' }">第十二周，</c:if>
		<c:if test="${a=='575' }">第十三周，</c:if>
		<c:if test="${a=='576' }">第十四周，</c:if>
		<c:if test="${a=='577' }">第十五周，</c:if>
		<c:if test="${a=='578' }">第十六周，</c:if>
		<c:if test="${a=='579' }">第十七周，</c:if>
		<c:if test="${a=='580' }">第十八周，</c:if>
		<c:if test="${a=='581' }">第十九周，</c:if>
		<c:if test="${a=='582' }">第二十周，</c:if>
     </c:forEach></td> 
     <td>星期:</td>
     <td><c:forEach items="${l.day}"  var="d" >
     	<c:if test="${d=='1'}">星期一，</c:if>
   		<c:if test="${d=='2' }">星期二，</c:if>
   		<c:if test="${d=='3' }">星期三，</c:if>
   		<c:if test="${d=='4' }">星期四，</c:if>
   		<c:if test="${d=='5' }">星期五，</c:if>
   		<c:if test="${d=='6' }">星期六，</c:if>
   		<c:if test="${d=='7' }">星期日，</c:if>
     </c:forEach></td> 
     <td>节次:</td>
     <td><c:forEach items="${l.time}"  var="f" >${f },</c:forEach></td> 
</tr>  
<tr>
     <td>预约内容</td>
     <td colspan="3">${l.start}</td>  
</tr>
<tr>
    <td>预约<spring:message code="all.trainingRoom.labroom" /></td>
    <td>${l.lab}</td>  
</tr>
</table>
<c:if test="${fn:contains(lab.labRoom.labRoomAdmins,user.username)}">
<c:if test="${l.cont==2 || l.cont==3}">
 <form:form  action="${pageContext.request.contextPath}/labReservation/auditsavelabreservtion?idkey=${l.id}&tage=${tage}"  method="post"  modelAttribute="labReservationAudit" target="_parent" >
   <table>
      <%-- <c:if test="${lab.CLabReservationType.id==1}"> --%>
      <c:if test="${lab.CDictionaryByLabReservetYpe.CCategory=='c_lab_reservation_type' && lab.CDictionaryByLabReservetYpe.CNumber=='1'}">
       <tr>
          <%-- <td>预约类型：</td><td colspan="4">${lab.CLabReservationType.name}</td> --%>
          <td>预约类型：</td><td colspan="4">${lab.CDictionaryByLabReservetYpe.CName}</td>
       </tr>
        <tr>
           <%-- <td>审核：</td><td colspan="4"><form:radiobutton path="COpreationAuditResults.id"  value="4" />拒绝  <form:radiobutton path="COpreationAuditResults.id"  value="1" />通过 </td> --%>
           <td>审核：</td><td colspan="4"><form:radiobutton path="CDictionary.id"  value="560" />拒绝  <form:radiobutton path="CDictionary.id"  value="561" />通过 </td>
       </tr>
        <tr>
           <td>审核意见：</td><td colspan="4"><form:input path="comments" /> </td>
       </tr>
       </c:if>
       <%-- <c:if test="${lab.CLabReservationType.id==2}"> --%>
      <%-- <c:if test="${lab.CDictionaryByLabReservetYpe.CCategory=='c_lab_reservation_type' && lab.CDictionaryByLabReservetYpe.CNumber=='2'}">--%>
       
        <tr>
           <td colspan="5"><input type="submit" value="提交"> </td>
       </tr>
 </table>
   </form:form>
</c:if>
</c:if>
</c:if>
<c:if test="${man==0 }">
   <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
    <tr><td colspan="6">审核人信息 </td></tr>
 <c:forEach items="${admins}" var="s">
 <tr align="center" >
     <td>审核人：</td>
     <td>${s.user.cname}</td> 
     <td>工号:</td>
     <td>${s.user.username}</td> 
     <td>部门:</td>
     <td>${s.user.schoolAcademy.academyName}</td> 
      <td>联系方式:</td>
     <td>${s.user.telephone}</td> 
</tr>  
</c:forEach>

</table>

</c:if>
</div>
</div>
</div>
</div>

</div>
</body>
<!-------------列表结束----------->
</html>