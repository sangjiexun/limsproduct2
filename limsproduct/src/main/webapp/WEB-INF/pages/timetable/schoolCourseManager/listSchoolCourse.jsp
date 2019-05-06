<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
    <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" />
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/lib.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script> 
  <script type="text/javascript">
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/timetable/listSchoolCourse?currpage=1";
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
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
	  <div id="title">选课程列表</div>
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/timetable/newSchoolCourse?flag=${flag}">新建</a>
	</div>
	<div class="tool-box">
		<form:form name="queryForm" method="Post" action="${pageContext.request.contextPath}/timetable/listSchoolCourse?currpage=1"	modelAttribute="schoolCourse">
	 <ul>
	   <li>学期：
       <select class="chzn-select" id="term" name="term" style="width:180px">
         <option value ="" >请选择</option>
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
		<select class="chzn-select"	name="schoolCourseNo"	id="schoolCourseNo" cssStyle="width:500px">
				<option value=""	label="请选择" />
				<c:forEach items="${AllSchoolCourse}" var="current" varStatus="i">
				<c:if test="${current.courseNo == courseNo}">
				<option value="${current.courseNo}" selected>${current.courseName}</option>
				</c:if>
				<c:if test="${current.courseNo != courseNo}">
				<option value="${current.courseNo}">${current.courseName}</option>
				</c:if>
				</c:forEach>
		<select></li> 
		<li><input type="submit" value="查询"></li>
		<li><a href="${pageContext.request.contextPath}/timetable/listTimetableTerm?currpage=1&id=-1"><input type="button" value="取消查询"></a></li>

	</ul> 
	</form:form>
	</div>
	
	
	<table class="tb" id="my_show">
	  <thead>
	  <tr >
	    <th>序号</th>
	    <th>所属课程名称</th>
	    <th>教师</th>
	    <th>选课课程名称</th>
	    <th>学期</th>
	    <sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_SUPERADMIN,ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER">
	    <th>操作</th>
	    </sec:authorize>
	  </tr>
	  </thead>
	  <tbody>
		 <c:forEach items="${schoolCourseList}" varStatus="i" var="curr">
		  <tr>
		    <td>${i.index+1}</td>
		    <td>${curr.schoolCourseInfo.courseName}</td>   
		    <td>${curr.userByTeacher.cname}</td>		    
		    <td>${curr.courseName}</td>		    
		    <td>${curr.schoolTerm.termName}</td>	
		    <sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_SUPERADMIN,ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER">
		    <td>
		    	<a href="${pageContext.request.contextPath}/timetable/editSchoolCourse?courseNo=${curr.courseNo}">编辑</a>
	        	<a href="${pageContext.request.contextPath}/timetable/deleteSchoolCourse?courseNo=${curr.courseNo}" onclick="return confirm('确定删除？');">删除</a>
		   		
		    </td>
		    </sec:authorize>
		  </tr>
		</c:forEach>
	  </tbody>
	</table>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourse?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourse?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/timetable/listSchoolCourse?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/timetable/listSchoolCourse?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourse?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourse?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
	$(function($) {
		var config = {
      		'.chzn-select'           : {search_contains : true,width:"auto"},
      		'.chzn-select-deselect'  : {allow_single_deselect:true},
      		'.chzn-select-no-single' : {disable_search_threshold:10},
      		'.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
      		'.chzn-select-width'     : {}
    	}
   		for (var selector in config) {
      		$(selector).chosen(config[selector]);
    	}	
		
		$(".chzn-container").each(function(){
			$(this).css("width","200px");
		});
	});
	</script>
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
