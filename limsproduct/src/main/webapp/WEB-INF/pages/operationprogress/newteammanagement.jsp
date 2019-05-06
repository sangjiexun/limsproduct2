<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  
  <script type="text/javascript">
  </script>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">新建课程教师团队管理</a></li>
			<li class="end"><a href="javascript:void(0)">新建</a></li>
			
		</ul>
	</div>
  </div>
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
  <div class="title">
      <div id="title">新建课程教师团队管理</div>
  </div>
  <form:form action="${pageContext.request.contextPath}/operation/saveteammanagement" method="POST" modelAttribute="teamManagement">
  <div class="new-classroom">

  	<form:input path="id" type="hidden" />
	<td class="label" valign="top" >课程名称<font color=red>*</font></td>
								<td class="label" valign="top"  align="left"><form:select id="course"
										path="schoolCourse.courseNo" class="chzn-select" required="true">
										<form:option value="${teamManagement.schoolCourse.courseName}">${teamManagement.schoolCourse.courseName}</form:option>
										<c:forEach items="${courseName}" var="c">
										<form:option value="${c.courseNo}">${c.courseName }</form:option>
										</c:forEach>
										</form:select>
								</td>
				<td class="label" valign="top" >教学秘书<font color=red>*</font></td>
								<td class="label" valign="top"  align="left"><form:select id="experimentalteaching;"
										path="userByExperimentalteaching.username" class="chzn-select" required="true">
										<form:option value="${userByExperimentalteaching}">${userByExperimentalteaching}</form:option>
										<c:forEach items="${experimentalteaching}" var="e">
										<form:option value="${e.username}">${e.cname}</form:option>
										</c:forEach>
										</form:select>
								</td>					 
  				<td class="label" valign="top" >教师<font color=red>*</font></td>
								<td class="label" valign="top"  align="left"><form:select id="teacher;"
										path="userByTeacher.username" class="chzn-select" required="true">
										<form:option value="${userByTeacher}">${userByTeacher}</form:option>
										<c:forEach items="${teacher}" var="t">
										<form:option value="${t.username}">${t.cname }</form:option>
										</c:forEach>
										</form:select>
								</td>	
  </div>
  <div class="moudle_footer">
      <div class="submit_link">
      <input class="btn" id="save" type="submit" value="确定">
      <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
      </div>
  </div>
  </form:form>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		    var config = {
		      '.chzn-select': {search_contains : true},
		      '.chzn-select-deselect'  : {allow_single_deselect:true},
		      '.chzn-select-no-single' : {disable_search_threshold:10},
		      '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
		      '.chzn-select-width'     : {width:"95%"}
		    }
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    }
		</script>
	<!-- 下拉框的js -->
  </div>
  </div>
  </div>
  </div>
  </div>
  
</body>
</html>

