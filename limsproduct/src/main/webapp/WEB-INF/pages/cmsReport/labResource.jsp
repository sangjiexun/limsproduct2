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
        function linkLab(){
            $.ajax({
                type: "POST",
                url:"${pageContext.request.contextPath}/cmsReport/linkLabByBuild",
                data:{'buildNumber':$("#buildNumber").val()},
                success:function(data){
                    $("#rooms").html(data.roomsValue);
                    $("#rooms").trigger("liszt:updated");
                }
            });
        };
    </script>
</head>

<body>
<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab1 selected" id="s1">
            <a href="javascript:void(0)">实验室资源列表</a>
        </li>
    </ul>
    <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
            <div class="content-box">
                <%--<div class="title">--%>
                    <%--<div id="title">实验室资源列表</div>--%>
                <%--</div>--%>

                <div class="tool-box">
                    <form:form name="queryForm" action="${pageContext.request.contextPath}/cmsReport/labResource?currpage=1" method="post" modelAttribute="labRoom">
                        <ul>
                            <li>学院:
                                <form:select path="schoolAcademy.academyNumber" class="chzn-select">
                                    <form:option value="">全校</form:option>
                                    <form:options items="${academies}" itemLabel="academyName" itemValue="academyNumber" />
                                </form:select>
                            </li>
                            <li>楼栋:
                                <form:select path="systemBuild.buildNumber" id="buildNumber" class="chzn-select" onchange="linkLab()">
                                <form:option value="">全部</form:option>
                                <form:options items="${builds}" itemLabel="buildName" itemValue="buildNumber" />
                            </form:select>
                            </li>
                            <li><spring:message code="all.trainingRoom.labroom"/>:
                                <form:select path="id" class="chzn-select" id="rooms">
                                <form:option value="">全部</form:option>
                                <form:options items="${labRoomList}" itemLabel="labRoomName" itemValue="id" />
                            </form:select>
                            </li>
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
                        <th>学院</th>
                        <th><spring:message code="all.trainingRoom.labroom"/>名称</th>
                        <th>所属楼宇</th>
                        <th>所在位置</th>
                        <th>面积</th>
                        <th>容量</th>
                        <th>是否开放</th>
                        <th>门禁</th>
                        <th>考勤</th>
                        <%--<th>视频</th>
                        <th>预约记录</th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${labRooms}" var="curr" varStatus="i">
                        <tr>
                            <td>${i.count+(currpage-1)*pagesize}</td>
                            <td>${curr.schoolAcademy.academyName}</td>
                            <td><a href="${pageContext.request.contextPath}/cmsReport/labInfo?idKey=${curr.id}&currpage=1">
                                [${curr.labRoomNumber}]${curr.labRoomName}</a></td>
                            <td>${curr.systemBuild.buildName}</td>
                            <td>${curr.systemRoom.roomName}</td>
                            <td>${curr.labRoomArea}</td>
                            <td>${curr.labRoomCapacity}</td>
                            <td><c:if test="${curr.isOpen eq 1}">是</c:if>
                                <c:if test="${curr.isOpen eq 0}">否</c:if></td>
                            <td>
                                <c:forEach items="${curr.labRoomAgents}" var="agent">
                                    <c:if test="${agent.CDictionary.CCategory eq 'c_agent_type' && agent.CDictionary.CNumber eq 2}">
                                        <!-- 门禁 -->
                                        <a href="${pageContext.request.contextPath}/cmsReport/labEntranceList?agentId=${agent.id}&currpage=1">${agent.CDictionary.CName}</a>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach items="${curr.labRoomAgents}" var="agent">
                                    <c:if test="${agent.CDictionary.CCategory eq 'c_agent_type' && agent.CDictionary.CNumber eq 1}">
                                        <!-- 考勤 -->
                                        <a href="${pageContext.request.contextPath}/cmsReport/labEntranceList?agentId=${agent.id}&currpage=1">${agent.CDictionary.CName}</a>
                                    </c:if>
                                </c:forEach>
                            </td>
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
                <div class="page" >
                    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
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
                </div>
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
