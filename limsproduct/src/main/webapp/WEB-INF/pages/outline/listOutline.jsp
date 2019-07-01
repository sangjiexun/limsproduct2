<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>

    <script type="text/javascript">
        function targetUrl(url) {
            document.queryForm.action=url;
            document.queryForm.submit();
        };
        // 取消查询
        function cancel() {
            window.location.href="${pageContext.request.contextPath}/outline/listOutline?currpage=1";
        }
    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.outline.management"/></a></li>
        </ul>
    </div>
</div>

<div id="TabbedPanels1" class="TabbedPanels">
    <div class="TabbedPanelsContentGroup">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)">实验大纲列表</a>
            </li>
            <!-- 超管、中心主任、教师、院系级管理员可以新建 -->
            <c:if test="${sessionScope.selected_role eq 'ROLE_TEACHER' || sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR' || sessionScope.selected_role eq 'ROLE_ACADEMYLEVELM'}">
                <a class="btn btn-new" href="${pageContext.request.contextPath}/outline/editOutline?idKey=0">新建</a>
            </c:if>
        </ul>
        <div class="TabbedPanelsContent">
            <div class="content-box">
                <%--<div class="title">--%>
                    <%--<div id="title">实验大纲列表</div>--%>
                    <%--<!-- 超管、中心主任、教师可以新建 -->--%>
                    <%--<c:if test="${sessionScope.selected_role eq 'ROLE_TEACHER' || sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'}">--%>
                        <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/outline/editOutline?idKey=0">新建</a>--%>
                    <%--</c:if>--%>
                <%--</div>--%>

                <div class="tool-box">
                    <form:form name="queryForm" action="${pageContext.request.contextPath}/outline/listOutline?currpage=1" method="post" modelAttribute="outline">
                        <ul>
                            <li>名称:<form:input path="labOutlineName"/></li>
                            <li>
                                <input type="submit" value="查询"/>
                                <input class="cancel-submit" type="button" value="取消查询" onclick="cancel();"/>
                            </li>
                        </ul>
                    </form:form>
                </div>

                <table class="tb" id="my_show">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>大纲名称</th>
                        <th>课程编号</th>
                        <th>课程名称</th>
                        <th>教师</th>
                        <th>学院</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${outlineList}" var="curr" varStatus="i">
                        <tr>
                            <td>${i.count}</td>
                            <td>${curr.labOutlineName}</td>
                            <td>${curr.schoolCourseInfoByClassId.courseNumber}</td>
                            <td>${curr.schoolCourseInfoByClassId.courseName}</td>
                            <td>
                                <c:if test="${curr.operationUser != null}">
                                    <c:forEach items="${curr.operationUser}" var="tea">${tea.cname}<br/>
                                    </c:forEach>
                                </c:if>
                            </td>
                            <td>${curr.schoolAcademy.academyName}</td>
                            <td>
                                <!-- 是创建者可以编辑或者删除 -->
                                <c:if test="${s.user.username == user.username || yes==1}">
                                    <a href="${pageContext.request.contextPath}/outline/deleteOutline?idKey=${curr.id}" onclick="return confirm('确认删除吗？');">删除</a>
                                    <a href="${pageContext.request.contextPath}/outline/editOutline?idKey=${curr.id}">编辑</a>
                                </c:if>
                                <!-- 不是创建者，但是实验室中心主任和超级管理员和院系级管理员可以编辑或者删除 -->
                                <c:if test="${s.user.username != user.username}">
                                    <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR' || sessionScope.selected_role eq 'ROLE_ACADEMYLEVELM'}">
                                        <a href="${pageContext.request.contextPath}/outline/deleteOutline?idKey=${curr.id}" onclick="return confirm('确认删除吗？');">删除</a>
                                        <a href="${pageContext.request.contextPath}/outline/editOutline?idKey=${curr.id}">编辑</a>
                                    </c:if>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/outline/viewOutline?idKey=${curr.id}"> 查看</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- 分页 -->
                <div class="page" >
                    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/outline/listOutline?currpage=1')" target="_self">首页</a>
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/outline/listOutline?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
                    第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                    <option value="${pageContext.request.contextPath}/outline/listOutline?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                        <c:if test="${j.index!=pageModel.currpage}">
                            <option value="${pageContext.request.contextPath}/outline/listOutline?currpage=${j.index}">${j.index}</option>
                        </c:if>
                    </c:forEach></select>页
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/outline/listOutline?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/outline/listOutline?currpage=${pageModel.lastPage}')" target="_self">末页</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
