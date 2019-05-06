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
	<li><a class="end">扣分标准</a></li>
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
<thead>
<tr>
  <th width="10%">扣分项</th>
  <th width="10%">扣分</th>	
</tr>
</thead>
<tbody>
<c:forEach items="${creditOptions}" var="current" varStatus="i">
				
				<tr>
					<td>${current.name}</td>
					<td>${current.deduction}</td>
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