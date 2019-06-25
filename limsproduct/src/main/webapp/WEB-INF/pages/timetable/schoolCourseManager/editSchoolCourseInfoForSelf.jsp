<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
	<meta name="decorator" content="none"/>
	<meta name="contextPath" content="${pageContext.request.contextPath}"/>
	<!-- 样式的引用 -->
	<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
	<link type="text/css" rel="stylesheet"
		  href="${pageContext.request.contextPath}/bootstrap/css/plugins/bootstrap-table/bootstrap-table.min.css"/>
	<!-- jquery的js引用 -->
	<script src="${pageContext.request.contextPath}/jquery/lib/jquery.js" type="text/javascript"></script>
	<!-- 弹出框插件的引用 -->
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
			type="text/javascript"></script>
	<link href="${pageContext.request.contextPath}/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/select2/select2.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/select2/select2-bootstrap4.css" rel="stylesheet">

	<!-- select2的js引用 -->
	<script src="${pageContext.request.contextPath}/select2/select2.full.js"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"
			type="text/javascript"></script>

	<script src="${pageContext.request.contextPath}/jquery/jquery.validate.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/jquery/messages_zh.js" type="text/javascript"></script>
	<!-- 页面业务的js引用 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
	<script type="text/javascript">
		function submitCourseInfo() {
			var courseName = $("#courseName").val();
			var courseNumber = $("#courseNumber").val();
			$.ajax({
				url:"${pageContext.request.contextPath}/timetable/saveSchoolCourseInfoForSelf",
				type:"POST",
				datatype:"text",
				data: {courseName:courseName, courseNumber: courseNumber},
				success: function (json) { }
			});
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}
	</script>
	<style type="text/css">
		label {width: 10em;float: left;}
		label.error {float: none;color: red;padding-left: .5em;vertical-align: top;}
		p {clear: both;}
		.submit {margin-left: 12em;}
		.table.table-bordered > tr > td {height: 20px;max-height: 20px;}
		.layui-table-cell {height: 20px  !important;line-height: 20px;}
		.layui-table tr {height: 20px !important;}
		/*.select2-container .select2-container*/
		body{
			font-size: 13px;
		}
	</style>

</head>

<body>
<br>
<div style="height:30%;width:90%;margin:0 auto;">
	<div class="iStyle_RightInner">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="site-box">
			</div>
		</div>
	</div>
</div>
<div style="height:70%;width:90%;margin:0 auto;">
	<form id="form_lab" action="" method="post">
		<br>
		<hr>
		<form action="${pageContext.request.contextPath}/timetable/saveSchoolCourseInfo" id="newForm" method="POST">
			<table border="0" align="center" style="width:100%;border-collapse:separate; border-spacing:0px 10px;">
				<tr>
					<th align=left width="10%"><h3>课程名称<font color="red"> *</font>：</h3></th>
					<td>
						<input id="courseName" class="easyui-validatebox" required="true"/>
					</td>
				</tr>
				<tr>
					<th align=left width="12%"><h3>课程编号：</h3></th>
					<td>
						<input id="courseNumber" type="hidden" value="${course_number}" />
						<label>${course_number}</label>
					</td>
				</tr>
				<tr>
					<th align=left width="12%"><h3>开课院系：</h3></th>
					<td width="39%">
						<input type="hidden" value="${schoolAcademy.academyNumber}" />
						<label>${schoolAcademy.academyName}</label>
					</td>
				</tr>
				<tr>
					<td align=left></td>
					<td>
					</td>
				</tr>
			</table>
		</form>
		<hr/>
		<input type="submit" onclick="submitCourseInfo(); return false;" value=" 确定 " class="btn btn-primary btn-lg" style="float:right">
		<br>
	</form>
</div>

</body>
</html>

