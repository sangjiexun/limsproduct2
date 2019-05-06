<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta name="decorator" content="iframe" />
<script langauge="javascript">
</script> 
 </head>
<body>
  <div class="right-content">
  <div class="title">
	<div id="title">${labRoom.labRoomName}</div>
	
  </div>
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
	<div class="edit-content-box">
	<div class="edit-content">
		<table class="tb" id="my_show">
			<thead>
				<tr>
					<th width="5%">序号</th>
					<th width="20%">设备编号</th>
					<th width="20%">设备名称</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${labDeviceList}" var="labDevice" varStatus="i">
					<tr align="center">
						<td>${i.count}</td>
						<td>${labDevice.schoolDevice.deviceNumber}</td>
						<td>${labDevice.schoolDevice.deviceName}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</div>
</div>
</div>
</div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
  var config = {
   '.chzn-select'      : {search_contains:true},
   '.chzn-select-deselect' : {allow_single_deselect:true},
   '.chzn-select-no-single' : {disable_search_threshold:10},
   '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
   '.chzn-select-width'   : {width:"95%"}
  }
  for (var selector in config) {
   $(selector).chosen(config[selector]);
  }
</script>
</body>
</html>