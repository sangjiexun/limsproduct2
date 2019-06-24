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
	<script src="${pageContext.request.contextPath}/js/lims/timetable/self/newSelfCourse.js"
			type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
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
	<input type="hidden" id="user_academyNumber" name="user_academyNumber" value="${user.schoolAcademy.academyNumber}">
	<input type="hidden" id="user_username" name="user_username" value="${user.username}">
	<input type="hidden" id="selfId" name="selfId" value="${timetableSelfCourse.id}">
	<form id="form_lab" action="" method="post">
		<input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
		<input type="hidden" id="acno" value="${acno}" />
		<br>
		<div style="width:95%;">
			<h3><b>新建教学班</b></h3>
			<c:if test="${  empty timetableSelfCourse.courseCode}">编号:code-${user.schoolAcademy.academyNumber}-${maxId}</c:if>
			<c:if test="${not  empty timetableSelfCourse.courseCode}">编号:${timetableSelfCourse.courseCode}</c:if>
			<input type="hidden" id="courseCode" name="courseCode" value="code-${user.schoolAcademy.academyNumber}-${maxId}" >
			<input type="button" id="submitButton" name="submitButton" value=" 确定 " class="btn btn-primary btn-lg"
				   style="float:right">
		</div>

		<!-- schoolCourseDetail的no -->
		<hr>
		<table border="0" align="center" style="width:100%;border-collapse:separate; border-spacing:0px 10px;">
			<tr style="overflow: hidden">
				<td align=left width="12%"><h3>学期信息<font color="red"> *</font>：</h3></td>
				<td>
					<select id="term" name="term" required>
						<c:if test="${not empty timetableSelfCourse.schoolTerm}">
							<option value="${timetableSelfCourse.schoolTerm.id}" selected>${timetableSelfCourse.schoolTerm.termName}</option>
						</c:if>
					</select>
					<label for="term"></label>
				</td>
				<td align=left width="10%"><h3>课程信息<font color="red"> *</font>：</h3></td>
				<td>
					<select id="courseNumber" name="courseNumber"  required>
						<c:if test="${not empty timetableSelfCourse.schoolCourseInfo}">
							<option value="${timetableSelfCourse.schoolCourseInfo.courseNumber}" selected>${timetableSelfCourse.schoolCourseInfo.courseName}(编号：${timetableSelfCourse.schoolCourseInfo.courseNumber})</option>
						</c:if>
					</select>
					<label for="courseNumber"></label><br>
					<a href="${pageContext.request.contextPath}/timetable/newSchoolCourseInfo" onclick="newSchoolCourseInfo(); return false;">新建课程</a>
				</td>
			</tr>

			<tr style="overflow: hidden">
				<td align=left width="12%"><h3>学院信息<font color="red"> *</font>：</h3></td>
				<td width="39%">
					<select id="academyNumber" name="academyNumber" required>
						<c:if test="${not empty timetableSelfCourse.schoolAcademy}">
							<option value="${timetableSelfCourse.schoolAcademy.academyNumber}" selected>${timetableSelfCourse.schoolAcademy.academyName}(${timetableSelfCourse.schoolAcademy.academyNumber})</option>
						</c:if>
					</select>
					<label for="academyNumber"></label>
				</td>
				<td align=left width="12%"><h3>教师信息<font color="red"> *</font>：</h3></td>
				<td width="39%">
					<select id="teacher" name="teacher" required>
						<c:if test="${not empty timetableSelfCourse.user}">
							<option value="${timetableSelfCourse.user.username}" selected>${timetableSelfCourse.user.cname}(学号：${timetableSelfCourse.user.username};学院：${timetableSelfCourse.schoolAcademy.academyName})</option>
						</c:if>
					</select>
					<label for="teacher"></label>
				</td>
			</tr>

			<tr style="overflow: hidden">
				<td align=left width="12%"><h3>选课人数<font color="red"> *</font>：</h3></td>
				<td colspan="3" >
					<input id="courseCount" name="courseCount"  type="text" value="${timetableSelfCourse.courseCount}" required digits style="width:95%;">
					<label for="courseCount"></label>
				</td>
			</tr>

			<tr>
				<td align=left width="12%"><h3>学生名单<font color="red"> *</font>：</h3></td>
				<td colspan="3">
					<textarea name="students" id="students"  style="width:95%;word-break: break-all;" rows="10"></textarea>
					<script type="text/javascript">
						    var str=""
                            <c:forEach items="${timetableSelfCourse.timetableCourseStudents}" var="current">
                                <c:set var="curr" value=""></c:set>
                                <c:set var="curr" value="\"${curr.concat(current.user.username).concat(';')}\n\""></c:set>
                                str = str+${curr};
							</c:forEach>
                            document.getElementById("students").value=str;
					</script>
					<label for="students"></label><br>
					<a href="javascript:void(0)" onclick="newStudents();">添加学生</a>
				</td>

			</tr>


			<tr>
				<td align=left></td>
				<td>
				</td>
			</tr>
		</table>
		<hr/>
		<br>
	</form>
</div>

<!-- 弹出选择学生窗口 -->
<div id="newStudents" class="easyui-window" title="选择添加学生" modal="true" dosize="true" maximizable="true" collapsible="true" minimizable="false" closed="true" iconcls="icon-add" style="width:1100px; height:900px;display: none;">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">

			<div class="content-box">
				<form:form action="" method="post">
					<fieldset class="introduce-box">
						<legend>年级信息</legend>
						<div>
							<table id="listTable" width="85%" cellpadding="0">
								<tr><td><b>选择年级</b></td><tr>
								<tr>
									<td>
										<c:forEach items="${grade }" var="s" varStatus="i">
											<c:if test="${s.yearCode>'2010' }">
												<a class='btn btn-common' href='javascript:void(0)' onclick='getSchoolClasses(${s.yearCode})' >${s.yearCode}</a>
											</c:if>
										</c:forEach></td>
								</tr>
							</table>
						</div>
						<div id="schoolClasses"></div>
						<div id="schoolClassesUser"></div>
					</fieldset>
				</form:form>
			</div>
		</div>
		<input type="hidden" id="pageContext" value="${pageContext.request.contextPath }">
	</div>

</div>
</body>
</html>

