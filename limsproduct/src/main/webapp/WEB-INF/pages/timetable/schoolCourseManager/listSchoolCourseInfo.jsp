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
    window.location.href="${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=1";
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
			<li><a href="javascript:void(0)"><spring:message  code="left.timetable.appointment"/></a></li>
			<li class="end"><a href="${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=1"><spring:message  code="left.course.library"/></a></li>
		</ul>
	</div>
</div>
  <div class="right-content">
 	 <div id="TabbedPanels1" class="TabbedPanels">
  		<div class="TabbedPanelsContentGroup">
			<ul class="TabbedPanelsTabGroup">
				<li class="TabbedPanelsTab selected iStyle_Mark iStyle_ActiveMark" id="s1"><a
						href="${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=1"><spring:message
						code="left.course.library"/></a></li>
				<c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
					<li class="TabbedPanelsTab" id="s2"><a
							href="${pageContext.request.contextPath}/timetable/listCourseDetails1?currpage=1&id=-1&flag=1"><spring:message
							code="left.timetable.group.theory"/></a></li>
					<li class="TabbedPanelsTab" id="s3"><a
							href="${pageContext.request.contextPath}/timetable/listCourseDetails1?currpage=1&id=-1&flag=2"><spring:message
							code="left.timetable.group.experiment"/></a></li>
				</c:if>
				<li class="TabbedPanelsTab" id="s4"><a
						href="${pageContext.request.contextPath}/timetable/mylistCourseDetails?currpage=1"><spring:message
						code="left.timetable.mine"/></a></li>
				<a class="btn btn-new" href="${pageContext.request.contextPath}/timetable/newSchoolCourseInfo">新建</a>
			</ul>
			<div class="TabbedPanelsContent">
  				<div class="content-box">
					<%--<div class="title">--%>
	 				 	<%--<div id="title">课题组列表</div>--%>
						<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/timetable/newSchoolCourseInfo">新建</a>--%>
					<%--</div>--%>
					<form:form name="queryForm" method="Post" action="${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=1" modelAttribute="schoolCourseInfo">
					<div class="tool-box">
						<ul>
							<li>输入课程名称或课程编号:
							<form:input path="courseNumber" id="courseNumber" />
							</li>
							<li>
								<input type="submit" value="查询"/>
								<input class="cancel-submit" type="button" value="取消查询" onclick="cancel()"/>
							</li>
						</ul>
					</div>
					</form:form>

	<table class="tb" id="my_show">
	  <thead>
	  <tr >
	    <th>序号</th>
	    <th>课程编号</th>
	    <th>课程名称</th>
	    <th>课程性质</th>
	    <th>课程类型</th>
	    <th>总学时</th>
	    <th>学分</th>
	    <sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_SUPERADMIN,ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER">
	    <th>操作</th>
	    </sec:authorize>
	  </tr>
	  </thead>
	  <tbody>
		 <c:forEach items="${schoolCourseInfoList}" varStatus="i" var="curr">
		  <tr>
		    <td>${i.index+1}</td>
		    <td>${curr.courseNumber}</td>
		    <td>${curr.courseName}</td>
		    <td>
				<c:if test="${curr.courseNature eq '1'}">
												通识通修课程
			</c:if>
			<c:if test="${curr.courseNature eq '2'}">
							学科专业课程
			</c:if>
			<c:if test="${curr.courseNature eq '3'}">
							开放选修课程
			</c:if>
			<c:if test="${curr.courseNature eq '4'}">
							其他课程
			</c:if>
			  <td>
			<c:if test="${curr.courseType eq '1'}">
												理论课
			</c:if>
			<c:if test="${curr.courseType eq '2'}">
												实验课
			</c:if>
		  </td>
		    </td>
			  <td>${curr.totalHours}</td>
		   <td>${curr.credits}</td>
		    <sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_SUPERADMIN,ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER">
		    <td>
		        <a href="${pageContext.request.contextPath}/timetable/viewOperationOutline?courseNumber=${curr.courseNumber}">查看</a>
		    	<a href="${pageContext.request.contextPath}/timetable/editSchoolCourseInfo?courseNumber=${curr.courseNumber}">编辑</a>	        	
	        	<a href="${pageContext.request.contextPath}/timetable/deleteSchoolCourseInfo?courseNumber=${curr.courseNumber}" onclick="return confirm('确定删除？');">删除</a>
		   		
		    </td>
		    </sec:authorize>
		  </tr>
		</c:forEach>
	  </tbody>
	</table>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
