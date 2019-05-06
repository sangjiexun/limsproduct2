<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <script type="text/javascript">
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/timetable/listSchoolCourseDetail?currpage=1";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  </script>
</head>

<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">排课管理</a></li>
		<li class="end"><a href="javascript:void(0)">课程详情列表</a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
	  <div id="title">课程详情列表</div>
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/timetable/newSchoolCourseDetail">新建</a>
	</div>
	<div class="tool-box">
		<form:form name="queryForm" method="Post" action="${pageContext.request.contextPath}/timetable/listSchoolCourseDetail?currpage=1"	modelAttribute="schoolCourse">
	<%-- <ul>
	   <li>学期：
       <select class="chzn-select" id="term" name="term" style="width:180px">
       <c:forEach items="${schoolTerms}" var="current">
    	    <c:if test="${current.id == term}">
    	       <option value ="${current.id}" selected>${current.termName} </option>
    	    </c:if>
    	    <c:if test="${current.id != term}">
    	       <option value ="${current.id}" >${current.termName} </option>
    	    </c:if>		
        </c:forEach>
        </select>
        </li>
		<li>选课组： 
		<form:select class="chzn-select"	path="schoolCourse.courseDetailNo" id="schoolCourse_courseDetailNo"	cssStyle="width:500px">
				<form:option value="-1"	label="所有选课组" />
				<c:forEach items="${schedulingCourseAllMap}" var="current"	varStatus="i">
					<form:option value="${current.courseDetailNo}"	label="${current.schoolTerm.termName}; ${current.schoolCourseInfo.courseNumber}; ${current.userByTeacher.cname}:${current.userByTeacher.username};${current.courseName}; 选课组编号：${current.courseCode}; 学时数：${current.schoolCourseInfo.totalHours} }" />
				</c:forEach>
		</form:select></li>
		<li><input type="submit" value="查询"></li>
		<li><a href="${pageContext.request.contextPath}/timetable/listTimetableTerm?currpage=1&id=-1"><input type="button" value="取消查询"></a></li>

	</ul> --%>
	</form:form>
	</div>
	
	
	<table class="tb" id="my_show">
	  <thead>
	  <tr >
	    <th>序号</th>
	    <th>所属课程</th>
	    <th>计划人数</th>
	    <th>学期</th>
	    <th>周数</th>
	    <th>星期几</th>
	    <th>开始周</th>
	    <th>结束周</th>
	    <th>节数</th>
	    <th>开始节次</th>
	    <th>结束节次</th>
	    <th>教师</th>
	    <th>实验总学时</th>
	  <!--   <th>是否为临时课程</th>
	    <th>次课程是否已经预约完</th> -->
	    <th>总学时</th>
	    <sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_SUPERADMIN,ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER">
	    <th>操作</th>
	    </sec:authorize>
	  </tr>
	  </thead>
	  <tbody>
		 <c:forEach items="${schoolCourseDetailList}" varStatus="i" var="curr">
		  <tr>
		    <td>${i.index+1}</td>
		    <td>${curr.courseNumber}</td>  
		    <td>${curr.planStudentNumber}</td>		    
		    <td>${curr.schoolTerm.termName}</td>		    
		    <td>${curr.totalWeeks}</td>	
		    <td>${curr.weekday}</td>	
		    <td>${curr.startWeek}</td>	
		    <td>${curr.endWeek}</td>	
		    <td>${curr.totalClasses}</td>	
		    <td>${curr.startClass}</td>	
		    <td>${curr.endClass}</td>	
		    <td>${curr.user.cname}</td>	
		    <td>${curr.experimentalClassHour}</td>	
		   <%--  <td>${curr.ifTemporary}</td>	
		    <td>${curr.ifAppointment}</td>	 --%>
		    <td>${curr.totalHours}</td>	
		    <sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_SUPERADMIN,ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER">
		    <td>
		    	<a href="${pageContext.request.contextPath}/timetable/editSchoolCourseDetail?courseDetailNo=${curr.courseDetailNo}">编辑</a>
	        	<a href="${pageContext.request.contextPath}/timetable/deleteSchoolCourseDetail?courseDetailNo=${curr.courseDetailNo}" onclick="return confirm('确定删除？');">删除</a>
		   		
		    </td>
		    </sec:authorize>
		  </tr>
		</c:forEach>
	  </tbody>
	</table>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourseDetail?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourseDetail?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/timetable/listSchoolCourseDetail?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/timetable/listSchoolCourseDetail?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourseDetail?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourseDetail?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
