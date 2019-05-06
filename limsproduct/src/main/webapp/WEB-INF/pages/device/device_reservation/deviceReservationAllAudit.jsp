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
<c:if test="${curStage le state && isAudit == 0 && curStage != 0 && curStage != -1}">
 <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
 <tr><td colspan="8"><font style="color: red">未审核</font></td></tr>
     <c:forEach items="${auditUser}" var="u" varStatus="i">
         <tr align="center" >
             <td>审核人：</td>
             <td>${u.cname }</td>
             <td>工号:</td>
             <td>${u.username }</td>
             <td>部门:</td>
             <td>${u.schoolAcademy.academyName }</td>
             <td>联系方式:</td>
             <td>${u.telephone }</td>
         </tr>
     </c:forEach>
</table>
</c:if>
<%--导师审核时显示--%>
<c:if test="${curStage == state && isAudit == 1}">
   <table>
        <tr>
           <td>审核：</td>
           <td colspan="4">
           <input type="radio" name="auditResult"  value="1" checked="true"/>通过
           <input type="radio" name="auditResult"  value="2" />拒绝
            </td>
       </tr>
        <tr>
           <td>审核意见：</td><td colspan="4"><input type="text" id="remark"  /> </td>
       </tr>
        <tr> 
           <td colspan="5"><input type="button" value="提交" onclick="saveAudit()"> </td>
       </tr>
 </table>
</c:if>
<%--导师审核后显示--%>
<c:if test="${(curStage gt state  || curStage == 0 || curStage == -1) && result != null}">
   <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
    <tr><td colspan="6">审核信息 </td></tr>
 <tr align="center" >
     <td>审核人：</td>
     <td>${result[0].cname }</td>
     <td>工号:</td>
     <td>${result[0].username }</td>
     <td>部门:</td>
     <td>${result[0].schoolAcademy.academyName }</td>
      <td>联系方式:</td>
     <td>${result[0].telephone }</td>
</tr> 
<tr align="center" >
     <td>审核结果</td>
     	<td>${result[1]}</td>
     <td>审核意见:</td>
     <td>${result[2]}</td>
     <td>审核时间:</td>
     <td>
         <fmt:setLocale value="zh_CN"/><fmt:formatDate value="${result[3].time}" pattern="yyyy-MM-dd HH:mm:ss"/>
    </td>
</tr>  
</table>
</c:if>

    <c:if test="${(curStage gt state  || curStage == 0 || curStage == -1) && result == null}">
    <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
        <tr><td colspan="6"><font style="color: red">无需审核</font></td></tr>
    </table>
    </c:if>
</div>
</div>
</div>
</div>
</div>
<script type="text/javascript">
function saveAudit(){
	var auditResult = $('input:radio[name="auditResult"]:checked').val();
	var remark = $("#remark").val();
	//alert($('input:radio[name="auditResult2"]:checked').val());
	window.location.href="${pageContext.request.contextPath}/LabRoomDeviceReservation/saveAudit?id=${id}&tage=${tage}&state=${state }&currpage=${currpage}&auditResult="+auditResult+"&remark="+remark+"&authName=${authName}";
}
</script>
</body>
<!-------------列表结束----------->
</html>