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
    window.location.href="${pageContext.request.contextPath}/report/listSchoolCourseDetail?currpage=1";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  </script>
  	<script type="text/javascript">
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
				<li><a href="javascript:void(0)">基础报表</a>
				</li>
				<li class="end"><a href="javascript:void(0)">选课组列表</a>
				</li>
			</ul>
		</div>
	</div>

	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="content-box">
						<div class="title">
							<div id="title">实验教学计划表</div>
						</div>
						<div class="tool-box">
							<form:form name="queryForm" action="${pageContext.request.contextPath}/report/listSchoolCourseDetail?currpage=1" method="post" modelAttribute="schoolCourseDetail">
			    				<li>学期：
			    				<form:select class="chzn-select" path="schoolTerm.id" >
								<c:forEach items="${termList}" var="current">
						    	    <c:if test="${current.id == termId}">
						    	       <form:option value ="${current.id}" selected="selected">${current.termName}</form:option>
						    	    </c:if>
						    	    <c:if test="${current.id != termId}">
						    	       <form:option value ="${current.id}">${current.termName}</form:option>
						    	    </c:if>
						        </c:forEach>
						        </form:select>
						        课程名称：
			    				<form:select class="chzn-select" path="schoolCourse.courseNo" >
								<form:option value="">--请选择--</form:option>
								<c:forEach items="${listCourseName}" var="current">
						    	    <c:if test="${current[2] == courseNo}">
						    	       <form:option value ="${current[2]}" selected="selected">${current[1]}</form:option>
						    	    </c:if>
						    	    <c:if test="${current[2] != courseNo}">
						    	       <form:option value ="${current[2]}">${current[1]}</form:option>
						    	    </c:if>
						        </c:forEach>
						        </form:select>
						        <input type="button" value="取消" onclick="cancel();"/>
			    				<input type="submit" value="查询"/></li>
							</form:form>
						</div> 

						<table class="tb" id="my_show">
							<thead>
								<tr>
									<th>选课组编号</th>
									<th>课程名称</th>
									<%--<th>班级</th>--%>
									<th>班级</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${listObj}" var="curr">
								<tr>
									<td>${curr[2]}</td>
									<td>${curr[1]}</td>
									<%--<td>${curr[3]}</td>--%>
									<td>${curr[3]}</td>
									<td><a href="${pageContext.request.contextPath}/report/listExperimentTeachingPlan?courseDetailNo=${curr[15]}&&course_number=${curr[0]}&&termId=${curr[14]}">查看</a></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<!-- 分页[s] -->
						<div class="page" >
					        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
					    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/listSchoolCourseDetail?currpage=1')" target="_self">首页</a>			    
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/listSchoolCourseDetail?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
						第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
						<option value="${pageContext.request.contextPath}/report/listSchoolCourseDetail?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
					    <c:if test="${j.index!=pageModel.currpage}">
					    <option value="${pageContext.request.contextPath}/report/listSchoolCourseDetail?currpage=${j.index}">${j.index}</option>7
					    </c:if>
					    </c:forEach></select>页
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/report/listSchoolCourseDetail?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
					 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/report/listSchoolCourseDetail?currpage=${pageModel.lastPage}')" target="_self">末页</a>
					    </div>
					    <!-- 分页[e] -->
					</div>
  				</div>
  			</div>
  		</div>
  	</div>
</body>
</html>
