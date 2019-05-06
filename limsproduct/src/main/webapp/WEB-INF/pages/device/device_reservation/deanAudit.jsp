<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<%--导师未审核时显示--%>
<c:if test="${state le 2 && isAudit == 0}">
 <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
 <tr><td colspan="8"><font style="color: red">未审核</font></td></tr>
 <tr align="center" >
     <td>审核人：</td>
     <td>${teacher.cname }</td> 
     <td>工号:</td>
     <td>${teacher.username }</td> 
     <td>部门:</td>
     <td>${teacher.schoolAcademy.academyName }</td> 
      <td>联系方式:</td>
     <td>${teacher.telephone }</td>
</tr>  
</table>
</c:if>
<%--导师审核时显示--%>
<c:if test="${state == 2 && isAudit == 1}">
   <table>
        <tr>
           <td>审核：</td>
           <td colspan="4">
           <input type="radio" name="auditResult2"  value="1" checked="true"/>通过
           <input type="radio" name="auditResult2"  value="2" />拒绝
            </td>
       </tr>
        <tr>
           <td>审核意见：</td><td colspan="4"><input type="text" id="remark2"  /> </td>
       </tr>
        <tr> 
           <td colspan="5"><input type="button" value="提交" onclick="saveDeanAudit()"> </td>
       </tr>
 </table>
</c:if>
<%--导师审核后显示--%>
<c:if test="${state gt 2 }">
   <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
    <tr><td colspan="6">审核信息 </td></tr>
 <tr align="center" >
     <td>审核人：</td>
     <td>${lStationReservationResult.user.cname }</td> 
     <td>工号:</td>
     <td>${lStationReservationResult.user.username }</td> 
     <td>部门:</td>
     <td>${lStationReservationResult.user.schoolAcademy.academyName }</td> 
      <td>联系方式:</td>
     <td>${lStationReservationResult.user.telephone }</td> 
</tr> 
<tr align="center" >
     <td>审核结果</td>
     <c:if test="${lStationReservationResult.CDictionaryByCTrainingResult.CNumber eq '1'}">
     	<td>通过</td> 
     </c:if>
     <c:if test="${lStationReservationResult.CDictionaryByCTrainingResult.CNumber eq '2'}">
     	<td><font style="color: red">不通过</font></td> 
     </c:if>
     <td>审核意见:</td>
     <td>${lStationReservationResult.remark}</td> 
     <td>审核时间:</td>
     <td><fmt:setLocale value="zh_CN"/><fmt:formatDate value="${lStationReservationResult.auditTime.time}" pattern="yyyy-MM-dd"/></td>
</tr>  
</table>
</c:if>
</div>
</div>
</div>
</div>
</div>
<script type="text/javascript">
function saveDeanAudit(){
	var auditResult = $('input:radio[name="auditResult2"]:checked').val();
	var remark = $("#remark2").val();
	//alert($('input:radio[name="auditResult2"]:checked').val());
	window.location.href="${pageContext.request.contextPath}/LabRoomDeviceReservation/saveDeanAudit?id=${id}&tage=${tage}&state=${state }&currpage=${currpage}&auditResult="+auditResult+"&remark="+remark;
}
</script>
</body>
<!-------------列表结束----------->
</html>