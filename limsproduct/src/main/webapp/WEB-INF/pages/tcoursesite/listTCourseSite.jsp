<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="iframe" />
<script type="text/javascript">
	function targetUrl(url) {
		document.queryForm.action = url;
		document.queryForm.submit();
	}
    function cancel() {
	    window.location.href="${pageContext.request.contextPath}/teaching/coursesite/listSchoolCourseInfo?currpage=1";
    }
</script>
</head>

<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange" /></a></li>
				<li class="end"><a href="javascript:void(0)"><spring:message code="left.course.library" /></a></li>
			</ul>
		</div>
	</div>
	<sec:authorize ifNotGranted="ROLE_STUDENT">
		<div id="TabbedPanels1" class="TabbedPanels">
			<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
				<%--<li class="TabbedPanelsTab " id="s0">
					<a class="iStyle_Feelings_Tree_Leaf" href="${pageContext.request.contextPath}/teaching/coursesite/experimentalmanagement?currpage=1">大纲管理</a>
				</li>--%>
				<li class="TabbedPanelsTab selected" id="s1">
					<a class="iStyle_Feelings_Tree_Leaf" href="${pageContext.request.contextPath}/teaching/coursesite/listSchoolCourseInfo?currpage=1">课程库</a>
				</li>
				<li class="TabbedPanelsTab" id="s2">
					<a class="iStyle_Feelings_Tree_Leaf" href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=7&orderBy=0">课题库</a>
				</li>
				<li class="TabbedPanelsTab" id="s3">
					<a class="iStyle_Feelings_Tree_Leaf" href="${pageContext.request.contextPath}/operation/listMyOperationItemAuditing?currpage=1">课题审核</a>
				</li>
			</ul>
		</div>
	</sec:authorize>
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="content-box">
						<div class="title">
							<div id="title">课程列表</div>
						</div>

						<div class="tool-box">
							<form:form name="queryForm"
								action="${pageContext.request.contextPath}/teaching/coursesite/listSchoolCourseInfo?currpage=1"
								method="post" modelAttribute="schoolCourseInfo">
								<ul>
									<li>输入课程名称或课程编号:
										<form:input name="courseNumber" path="courseNumber"/>
										</li>
									<li>
										<input type="submit" value="查询" />
										<input class="cancel-submit" type="button" value="取消" onclick="cancel()" />
									</li>
								</ul>

							</form:form>
						</div>

						<table class="tb" id="my_show">
							<thead>
								<tr>
								    <th>课程编号</th>
									<th>课程名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${schoolCourseInfoList}" var="curr">
									<tr>
										<td>${curr.courseNumber}</td>
										<td>${curr.courseName}</td>
										<td>
											<a
											href="${pageContext.request.contextPath}/teaching/coursesite/operationItemList?courseNumber=${curr.courseNumber}&currpage=1">课题列表</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!-- 分页[s] -->
						<div class="page">
							${pageModel.totalRecords}条记录,共${pageModel.totalPage}页 <a
								href="javascript:void(0)"
								onclick="targetUrl('${pageContext.request.contextPath}/teaching/coursesite/listSchoolCourseInfo?currpage=1')"
								target="_self">首页</a> <a href="javascript:void(0)"
								onclick="targetUrl('${pageContext.request.contextPath}/teaching/coursesite/listSchoolCourseInfo?currpage=${pageModel.previousPage}')"
								target="_self">上一页</a> 第<select
								onchange="targetUrl(this.options[this.selectedIndex].value)">
								<option
									value="${pageContext.request.contextPath}/teaching/coursesite/listSchoolCourseInfo?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
								<c:forEach begin="${pageModel.firstPage}"
									end="${pageModel.lastPage}" step="1" varStatus="j"
									var="current">
									<c:if test="${j.index!=pageModel.currpage}">
										<option
											value="${pageContext.request.contextPath}/teaching/coursesite/listSchoolCourseInfo?currpage=${j.index}">${j.index}</option>
									</c:if>
								</c:forEach>
							</select>页 <a href="javascript:void(0)"
								onclick="targetUrl('${pageContext.request.contextPath}/teaching/coursesite/listSchoolCourseInfo?currpage=${pageModel.nextPage}')"
								target="_self">下一页</a> <a href="javascript:void(0)"
								onclick="targetUrl('${pageContext.request.contextPath}/teaching/coursesite/listSchoolCourseInfo?currpage=${pageModel.lastPage}')"
								target="_self">末页</a>
						</div>
						<!-- 分页[e] -->
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
