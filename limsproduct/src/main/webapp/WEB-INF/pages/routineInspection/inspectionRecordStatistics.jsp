<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <meta name="decorator" content="iframe"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->

    <script type="text/javascript">
        function exportAll(s) {
            document.queryForm.action = s;
            document.queryForm.submit();
        }
    </script>

</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.lab.safety.inspect"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.routine.inspect.record"/>表</a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)"><spring:message code="left.routine.inspect.record"/>表</a>
            </li>
            <sec:authorize ifNotGranted="ROLE_FULLTIMEMANAGER,ROLE_LABMANAGER">
                <input class="btn btn-new" type="button" value="导出"
                       onclick="exportAll('${pageContext.request.contextPath}/inspection/exportGeneralCheckLabRooms?page=${page}');">
            </sec:authorize>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box">
                        <form:form name="queryForm"
                                   action="${pageContext.request.contextPath}/inspection/inspectionStatistics?currpage=1 "
                                   method="post" modelAttribute="routineInspection">
                            <ul>
                                <li>
                                    实验室:
                                    <form:select class="chzn-select" path="labRoom.id" id="labRoomId"
                                                 name="labRoom.id" style="width:150px">
                                        <form:option value="">实验室</form:option>
                                        <c:forEach items="${labRooms}" var="current">
                                            <form:option
                                                    value="${current.id}">${current.labRoomName} </form:option>
                                        </c:forEach>
                                    </form:select>
                                </li>
                                <li>
                                    学期:
                                    <form:select class="chzn-select" path="schoolTerm.id" id="schoolTerm.id"
                                                 name="schoolTerm.id" style="width:180px">
                                        <form:option value="">学期</form:option>
                                        <c:forEach items="${terms}" var="current">
                                            <form:option value="${current.id}">${current.termName} </form:option>
                                        </c:forEach>
                                    </form:select>
                                </li>
                                <li>
                                    <%--<sec:authorize ifNotGranted="ROLE_FULLTIMEMANAGER,ROLE_LABMANAGER">--%>
                                        <%--<input type="button" value="导出"--%>
                                               <%--onclick="exportAll('${pageContext.request.contextPath}/inspection/exportGeneralCheckLabRooms?page=${page}');">--%>
                                    <%--</sec:authorize>--%>
                                    <input type="button" value="查询" onclick="exportAll('${pageContext.request.contextPath}/inspection/inspectionStatistics?currpage=1');">
                                </li>
                            </ul>
                        </form:form>
                    </div>
                </div>
                <div class="content-box">
                    <table class="tb" id="my_show">
                        <thead style="center-content">
                        <tr>
                            <th>学院</th>
                            <th style="width:200px;">实验室</th>
                            <c:forEach items="${list}" var="list" varStatus="i">
                                <th>第${list.week}周</th>
                            </c:forEach>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${inspectionLabRooms}" var="current" varStatus="i">
                            <tr>
                                <td>${current.labAnnex.labCenter.schoolAcademy.academyName}</td>
                                <td>${fn:split(current.labRoomName,',')[0]}</td>
                                <c:forEach items="${list}" var="list" varStatus="i">
                                    <td>
                                        <c:choose>
                                            <c:when test="${fn:split(current.labRoomName,'%')[list.week]==1}">
                                                <font color="green">√</font>
                                            </c:when>
                                            <c:otherwise>
                                                <font color="red">×</font>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </c:forEach>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!-- 分页开始 -->
                    <div class="page">
                        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                        <a href="javascript:void(0)" onclick="exportAll('${pageContext.request.contextPath}/inspection/inspectionStatistics?currpage=1&orderBy=${orderBy }')"
                           target="_self">首页</a>
                        <a href="javascript:void(0)" onclick="exportAll('${pageContext.request.contextPath}/inspection/inspectionStatistics?currpage=${pageModel.previousPage}&orderBy=${orderBy }')"
                           target="_self">上一页</a>
                        第<select
                            onchange="exportAll(this.options[this.selectedIndex].value);">
                        <option value="${pageContext.request.contextPath}/inspection/inspectionStatistics?currpage=${pageModel.currpage}&orderBy=${orderBy }">${pageModel.currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                   var="current">
                            <c:if test="${j.index!=pageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/inspection/inspectionStatistics?currpage=${j.index}&orderBy=${orderBy }">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)" onclick="exportAll('${pageContext.request.contextPath}/inspection/inspectionStatistics?currpage=${pageModel.nextPage}&orderBy=${orderBy }')"
                           target="_self">下一页</a>
                        <a href="javascript:void(0)" onclick="exportAll('${pageContext.request.contextPath}/inspection/inspectionStatistics?currpage=${pageModel.lastPage}&orderBy=${orderBy }')"
                           target="_self">末页</a>
                    </div>
                    <!-- 分页结束 -->

                </div>
                <div id="x2" style="display:none;">
                    <table class="tb" id="my_show">
                        <thead style="center-content">
                        <tr>
                            <th>学院</th>
                            <th>实验室</th>
                            <th>第1月</th>
                            <th>第2月</th>
                            <th>第3月</th>
                            <th>第4月</th>
                            <th>第5月</th>
                            <th>第6月</th>
                        </tr>
                        </thead>
                        <tbody>
                        <td>环境学院</td>
                        <td>知达楼A302</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>

                        </tr>
                        </tbody>
                    </table>


                    <!-- 分页开始 -->
                    <div class="page">
                        条记录,共页
                        <a href="#">首页</a>
                        <a href="#">上一页</a>
                        第<select>
                        <option>1</option>
                    </select>页
                        <a href="#">下一页</a>
                        <a href="#">末页</a>
                    </div>
                </div>

                <!-- 分页结束 -->
            </div>
            <script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
                    type="text/javascript"></script>
            <script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript"
                    charset="utf-8"></script>
            <script type="text/javascript">
                var config = {
                    '.chzn-select': {search_contains: true},
                    '.chzn-select-deselect': {allow_single_deselect: true},
                    '.chzn-select-no-single': {disable_search_threshold: 10},
                    '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
                    '.chzn-select-width': {width: "95%"}
                }
                for (var selector in config) {
                    $(selector).chosen(config[selector]);
                }
            </script>

        </div>


        <div>
        </div>
    </div>
</div>
<!-- 下拉框的js -->

</div>
</body>
</html>
