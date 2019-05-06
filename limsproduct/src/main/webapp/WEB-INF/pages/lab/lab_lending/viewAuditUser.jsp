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
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
<div class="content-box">	
<%--导师未审核时显示--%>
<c:if test="${statue==1}">
 <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
 <tr><td colspan="8"><font style="color: red">未审核</font></td></tr>
  <th>审核人:</th>
  <th>工号：</th>
  <th>部门：</th>
  <th>联系方式：</th>
<c:forEach items="${deanAudit}" var="curr"  varStatus="i">
					       <tr>
					       <td style="vertical-align:middle; text-align:center;">${curr.user.cname}</td>
					       <td style="vertical-align:middle; text-align:center;">${curr.user.username}</td>
					       <td style="vertical-align:middle; text-align:center;">*</td>
					       <td style="vertical-align:middle; text-align:center;">${curr.user.telephone}</td>
					       </tr>
</c:forEach>
</table>
</c:if>
<c:if test="${statue!=1}">
 <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
  <th>审核人:</th>
  <th>工号：</th>
  <th>部门：</th>
  <th>联系方式：</th>
  <th>审核结果：</th>
  <th>审核时间：</th>
<c:forEach items="${deanAudit}" var="curr"  varStatus="i">
					       <tr>
					       <td style="vertical-align:middle; text-align:center;">${curr.user.cname}</td>
					       <td style="vertical-align:middle; text-align:center;">${curr.user.username}</td>
					       <td style="vertical-align:middle; text-align:center;">*</td>
					       <td style="vertical-align:middle; text-align:center;">${curr.user.telephone}</td>
					       <c:if test="${curr.status==1}">
					       <td style="vertical-align:middle; text-align:center;">通过</td>
					       </c:if>
					       <td style="vertical-align:middle; text-align:center;"><fmt:formatDate value="${curr.createDate.time}" pattern="yyyy-MM-dd" /></td>
					       </tr>
</c:forEach>
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
	window.location.href="${pageContext.request.contextPath}/LabRoomReservation/saveDeanAudit?id=${id}&tage=${tage}&state=${state }&currpage=${currpage}&auditResult="+auditResult+"&remark="+remark;
}
</script>
</body>
<!-------------列表结束----------->
</html>