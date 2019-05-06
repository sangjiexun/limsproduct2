<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>

    <script type="text/javascript">
        function targetUrl(url) {
            // document.queryForm.action=url;
            // document.queryForm.submit();
            window.location.href=url;
        };
        // 取消查询
        function cancel() {
            window.location.href="${pageContext.request.contextPath}/inspect/listInspectSetting?currpage=1";
        }
    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.lab.inspect"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.inspect.set"/></a></li>
        </ul>
    </div>
</div>

<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab1 selected" id="s1">
            <a href="javascript:void(0)">安全检查评价时间设置列表</a>
        </li>
        <a class="btn btn-new" href="${pageContext.request.contextPath}/inspect/newInspectSetting">新建</a>
    </ul>
    <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
            <div class="content-box">
                <%--<div class="title">--%>
                    <%--<div id="title">安全检查评价时间设置列表</div>--%>
                    <%--<!-- 超管、中心主任、教师可以新建 -->--%>
                    <%--&lt;%&ndash;<c:if test="${sessionScope.selected_role eq 'ROLE_TEACHER' || sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'}">--%>
                        <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/outline/editOutline?idKey=0">新建</a>--%>
                    <%--</c:if>&ndash;%&gt;--%>
                    <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/inspect/newInspectSetting">新建</a>--%>
                <%--</div>--%>

                <%--<div class="tool-box">
                    <form:form name="queryForm" action="${pageContext.request.contextPath}/inspect/listInspectSetting?currpage=1" method="post" modelAttribute="labInspect">
                        <ul>
                            <li>项目名称：</li>
                            <li><form:input path="standardName"/></li>
                            <li>
                                <input type="button" value="取消查询" onclick="cancel();"/>
                                <input type="submit" value="查询"/>
                            </li>
                        </ul>
                    </form:form>
                </div>--%>

                <table class="tb" id="my_show">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>学期</th>
                        <th>评价时间</th>
                        <th>批次</th>
                        <th>名称</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%!int i=0;%>
                    <c:forEach items="${inspectSettings}" var="curr" varStatus="s">
                        <tr>
                            <td>${s.count}</td>
                            <td>${curr.semeter}</td>
                            <td>${evaluationTime.get(i)}</td>
                            <c:set var="i" value="${i+1}"/>
                            <td>${curr.title}</td>
                            <td>${curr.comment}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/inspect/editInspectSetting?idKey=${curr.id}">编辑</a>
                                <a href="${pageContext.request.contextPath}/inspect/deleteInspectSetting?idKey=${curr.id}" onclick="return confirm('确认删除吗？');">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- 分页 -->
                <div class="page" >
                    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/inspect/listInspectSetting?currpage=1')" target="_self">首页</a>
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/inspect/listInspectSetting?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
                    第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                    <option value="${pageContext.request.contextPath}/inspect/listInspectSetting?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                        <c:if test="${j.index!=pageModel.currpage}">
                            <option value="${pageContext.request.contextPath}/inspect/listInspectSetting?currpage=${j.index}">${j.index}</option>
                        </c:if>
                    </c:forEach></select>页
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/inspect/listInspectSetting?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/inspect/listInspectSetting?currpage=${pageModel.lastPage}')" target="_self">末页</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
