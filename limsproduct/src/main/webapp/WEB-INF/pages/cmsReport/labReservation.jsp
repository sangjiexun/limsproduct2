<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
    <script type="text/javascript">
        function targetUrl(url) {
            document.queryForm.action=url;
            document.queryForm.submit();
        };
        // 取消查询
        function cancel() {
            window.location.href="${pageContext.request.contextPath}/cmsReport/labResource?currpage=1";
        }
    </script>
</head>

<body>
<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab1 selected" id="s1">
            <a href="javascript:void(0)">实验室开放列表</a>
        </li>
    </ul>
    <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
            <div class="content-box">
                <%--<div class="title">--%>
                    <%--<div id="title">实验室开放列表</div>--%>
                <%--</div>--%>

        <%--        <div class="tool-box">
                    <form:form name="queryForm" action="${pageContext.request.contextPath}/cmsReport/labResource?currpage=1" method="post" modelAttribute="labRoom">
                        <ul>
                            <li>学院：</li>
                            <li><form:select path="schoolAcademy.academyNumber" class="chzn-select">
                                <form:option value="">全校</form:option>
                                <form:options items="${academies}" itemLabel="academyName" itemValue="academyNumber" />
                            </form:select>
                            </li>
                            <li>楼栋：</li>
                            <li><form:select path="systemRoom.systemBuild.buildNumber" class="chzn-select">
                                <form:option value="">全部</form:option>
                                <form:options items="${builds}" itemLabel="buildName" itemValue="buildNumber" />
                            </form:select>
                            </li>
                            <li><spring:message code="all.trainingRoom.labroom"/>：</li>
                            <li><form:select path="id" class="chzn-select">
                                <form:option value="">全部</form:option>
                                <form:options items="${labRoomList}" itemLabel="labRoomName" itemValue="id" />
                            </form:select>
                            </li>
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
                        <th>预约性质</th>
                        <th>周次</th>
                        <th>星期</th>
                        <th>节次</th>
                        <th>预约实验室</th>
                        <th>内容</th>
                        <th>审批状态</th>
                        <%--<th>考勤</th>--%>
                        <%--<th>视频</th>
                        <th>预约记录</th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${labReservations}" var="curr" varStatus="i">
                        <tr>
                            <td>${i.count}</td>
                            <td>${curr.CDictionaryByLabReservetYpe.CName}</td>

                            <td>
                                <c:forEach items="${curr.timetableAppointment.timetableAppointmentSameNumbers}" var="curr1">
                                第${curr1.startWeek}周
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach items="${curr.timetableAppointment.timetableAppointmentSameNumbers}" var="curr1">
                                星期[${curr1.weekday}]
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach items="${curr.timetableAppointment.timetableAppointmentSameNumbers}" var="curr1">
                                第${curr1.startClass}节
                                </c:forEach>
                            </td>
                            <%--</c:forEach>--%>
                            <%--<td></td>--%>
                            <%--<td></td>--%>
                            <%--<td></td>--%>
                            <td>${curr.labRoom.labRoomName}</td>
                            <td>${curr.reservations}</td>
                            <td><c:if test="${curr.auditResults ==1}">通过</c:if>
                                <c:if test="${curr.auditResults ==2}">审核中</c:if>
                                <c:if test="${curr.auditResults ==3}">未审核</c:if>
                                <c:if test="${curr.auditResults ==4}">审核拒绝</c:if>
                            </td>
<%--                            <td>
                                <c:forEach items="${curr.labRoomAgents}" var="agent">
                                    <c:if test="${agent.CDictionary.CCategory eq 'c_agent_type' && agent.CDictionary.CNumber eq 1}">
                                        <!-- 考勤 -->
                                        <a href="${pageContext.request.contextPath}/cmsReport/labEntranceList?agentId=${agent.id}&currpage=1">${agent.CDictionary.CName}</a>
                                    </c:if>
                                </c:forEach>
                            </td>--%>
                                <%--<td>
                                    <c:forEach items="${curr.labRoomAgents}" var="agent">
                                        <c:if test="${agent.CDictionary.CCategory eq 'c_agent_type' && agent.CDictionary.CNumber eq 3}">
                                            <!-- 视频 -->
                                            <a href="${pageContext.request.contextPath}/lab/entranceList?id=${agent.id}&page=1">${agent.CDictionary.CName}</a>
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/outline/viewOutline?idKey=${curr.id}">查看</a>
                                </td>--%>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- 分页 -->
<%--                <div class="page" >
&lt;%&ndash;                    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/cmsReport/labResource?currpage=1')" target="_self">首页</a>
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/cmsReport/labResource?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
                    第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                    <option value="${pageContext.request.contextPath}/cmsReport/labResource?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                        <c:if test="${j.index!=pageModel.currpage}">
                            <option value="${pageContext.request.contextPath}/cmsReport/labResource?currpage=${j.index}">${j.index}</option>
                        </c:if>
                    </c:forEach></select>页
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/cmsReport/labResource?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/cmsReport/labResource?currpage=${pageModel.lastPage}')" target="_self">末页</a>
                </div>&ndash;%&gt;--%>
            </div>
        </div>
    </div>
</div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select': {search_contains: true},
        '.chzn-select-deselect': {allow_single_deselect: true},
        '.chzn-select-no-single': {disable_search_threshold: 10},
        '.chzn-select-no-results': {no_results_text: 'Oops, nothing found!'},
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>
