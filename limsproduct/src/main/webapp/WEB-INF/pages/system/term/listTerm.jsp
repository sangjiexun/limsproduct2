<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<head>
<meta name="decorator" content="iframe" />
</head>
<!-- 导航栏 -->
<div class="navigation">
<div id="navigation">
<ul>
<li><a href=""><spring:message code="left.system.management" /></a></li>
<li class="end"><a href="${pageContext.request.contextPath}/system/listTerm?currpage=1"><spring:message code="left.term.management" /></a></li>
</ul>
</div>
</div>
<!-- 导航栏 -->
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab1 selected" id="s1">
			<a href="javascript:void(0)">学期管理</a>
		</li>
		<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
		<a class="btn btn-new" href="${pageContext.request.contextPath}/system/newTerm">新建</a>
	</sec:authorize>
	</ul>
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">

			<div class="content-box">
				<%--<div class="title">--%>
					<%--<div class="title">学期管理--%>
						<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">--%>
							<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/system/newTerm">新建</a>--%>
						<%--</sec:authorize></div>--%>
				<%--</div>--%>
				<table>
					<thead>
					<tr>
						<th>序号</th>
						<th>学期名称</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>学年</th>
						<th>学期代号</th>
						<sec:authorize ifAnyGranted="ROLE_PREEXTEACHING,ROLE_SUPERADMIN">
							<th>操作</th>
						</sec:authorize>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${terms}" var="current" varStatus="i">
						<tr>
							<td>${i.count}</td>
							<td>${current.termName}</td>
							<td><fmt:formatDate value="${current.termStart.time}"
												pattern="yyyy-MM-dd" />
							</td>
							<td><fmt:formatDate value="${current.termEnd.time}"
												pattern="yyyy-MM-dd" />
							</td>
							<td>${current.yearCode}</td>
							<td>${current.termCode}</td>
							<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
								<td><c:choose>
									<c:when test="${fn:length(current.schoolWeeks)==0}">
										<a title="修改" class="btn btn-common" href="${pageContext.request.contextPath}/system/editTerm?idKey=${current.id}" >修改</a>
										<a title="删除" class="btn btn-common" href="${pageContext.request.contextPath}/system/deleteTerm?idKey=${current.id}" onclick="return confirm('是否确定删除？');" >删除</a>
										<a title="生成周次" class="btn btn-common"
										   href="${pageContext.request.contextPath}/system/createWeek?id=${current.id}" onclick="return confirm('生成周次后将不能修改学期日期，是否确定？');">生成周次</a>
									</c:when>
									<c:otherwise>已生成周次</c:otherwise>
								</c:choose>&nbsp;&nbsp;</td>
							</sec:authorize>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>


</div>
