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
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script> 
  <script type="text/javascript">
  //提交表单
  function submitBookUpload(){
   /*  if($("#bookName").val()=="")
    {
      alert("请填写书名！");
      return false;
    } */
    
    
    document.center_form.action="${pageContext.request.contextPath}/timetable/saveSchoolCourseDetail";
    document.center_form.submit();
  }
  </script>
</head>
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">课程详情管理</a></li>
			<li class="end"><c:if test="${!isEdit}"><a href="javascript:void(0)">编辑</a></c:if>
			                <c:if test="${isEdit}"><a href="javascript:void(0)">新建</a></c:if></li>
			
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title"><c:if test="${!isEdit}">编辑</c:if><c:if test="${isEdit}">新建</c:if>课程详情信息</div>
	</div>
	<form:form name="center_form" method="POST" modelAttribute="schoolCourseDetail">
	<div class="new-classroom">
	   <fieldset>
	    <label>所属课程</label>
	    <form:select id="courseNumber" path="courseNumber" class="chzn-select">
	      <form:option value="">请选择</form:option>
	      <c:forEach items="${schoolCourseInfoList}" var="curr">
	        <form:option value="${curr.courseNumber}">${curr.courseName}</form:option>
	      </c:forEach> 
	    </form:select>
	  </fieldset>
	  
	  
	  <fieldset>
	    <label>教师</label>
	    <form:select id="user.username" path="user.username" class="chzn-select">
	      <form:option value="">请选择</form:option>
	      <c:forEach items="${teacherList}" var="curr">
	        <form:option value="${curr.username}">${curr.cname}</form:option>
	      </c:forEach> 
	    </form:select>
	  </fieldset>
	  <fieldset>
	    <label>学期</label>
	    <form:select id="schoolTerm.id" path="schoolTerm.id" class="chzn-select">
	      <form:option value="">请选择</form:option>
	      <c:forEach items="${schoolTermList}" var="curr">
	        <form:option value="${curr.id}">${curr.termName}</form:option>
	      </c:forEach> 
	    </form:select>
	  </fieldset>
	   <fieldset>
	    <label>星期几</label>
	    <form:select id="weekday" path="weekday" class="chzn-select">
	      <form:option value="">请选择</form:option>
	      <form:option value="1">1</form:option>
	      <form:option value="2">2</form:option>
	      <form:option value="3">3</form:option>
	      <form:option value="4">4</form:option>
	      <form:option value="5">5</form:option>
	      <form:option value="6">6</form:option>
	      <form:option value="6">7</form:option>
	    </form:select>
	  </fieldset>
	  <fieldset>
	    <form:hidden path="courseDetailNo"/>
	    <label>计划人数：</label>
	    <form:input path="planStudentNumber" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>开始周：</label>
	    <form:input path="startWeek" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>结束周：</label>
	    <form:input path="endWeek" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>节次：</label>
	    <form:input path="totalClasses" class="easyui-validatebox" required="true"/>
	  </fieldset>
	   <fieldset>
	    <label>开始节次：</label>
	    <form:input path="startClass" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>结束节次：</label>
	    <form:input path="endClass" class="easyui-validatebox" required="true"/>
	  </fieldset>
	 <!--  <fieldset> -->
	    <!-- <label>实验室总学时：</label> -->
	    <form:hidden path="experimentalClassHour" class="easyui-validatebox" required="true"/>
	<!--   </fieldset>
	  <fieldset> -->
	    <!-- <label>总学时：</label> -->
	    <form:hidden path="totalHours" class="easyui-validatebox" required="true"/>
	  <!-- </fieldset> -->
	  <form:hidden path="totalWeeks" class="easyui-validatebox" required="true"/>
	</div>
	<div class="moudle_footer">
        <div class="submit_link">
          <input class="btn" id="save" type="button" onclick="submitBookUpload();" value="确定">
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
