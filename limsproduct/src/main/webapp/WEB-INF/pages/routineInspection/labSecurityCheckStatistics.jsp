<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.user-resources"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <meta name="decorator" content="iframe"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
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
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.safety.inspection.record"/>表</a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)"><spring:message code="left.safety.inspection.record"/>表</a>
            </li>
            <sec:authorize ifNotGranted="ROLE_FULLTIMEMANAGER,ROLE_LABMANAGER">
                <input class="btn btn-new" type="button" value="导出"
                       onclick="exportAll('${pageContext.request.contextPath}/inspection/exportSecurityCheckLabRooms?page=${page}');">
            </sec:authorize>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box">
                        <form:form name="queryForm"
                                   action="${pageContext.request.contextPath}/inspection/labSecurityCheckStatistics?currpage=1 "
                                   method="post" modelAttribute="labSecurityCheck">
                            <ul>
                                <li>
                                    实验中心:
                                    <form:select id="labCenter.id" path="labCenter.id" name="labCenter.id"
                                                 class="chzn-select">
                                        <form:option value="">实验中心</form:option>
                                        <c:forEach items="${labCenters}" var="l">
                                            <form:option value="${l.id}">${l.centerName}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </li>
                                <li style="">
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
                                               <%--onclick="exportAll('${pageContext.request.contextPath}/inspection/exportSecurityCheckLabRooms?page=${page}');">--%>
                                    <%--</sec:authorize>--%>
                                    <%--<input type="button" value="取消" onclick="cancel();"/>--%>
                                    <input type="button" value="查询" onclick="exportAll('${pageContext.request.contextPath}/inspection/labSecurityCheckStatistics?currpage=1');">
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
                            <th style="width:200px;">实验中心</th>
                            <th>第1月</th>
                            <th>第2月</th>
                            <th>第3月</th>
                            <th>第4月</th>
                            <th>第5月</th>
                            <th>第6月</th>
                            <th>第7月</th>
                            <th>第8月</th>
                            <th>第9月</th>
                            <th>第10月</th>
                            <th>第11月</th>
                            <th>第12月</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${securityCheckLabCenters}" var="current" varStatus="i">
                            <tr>
                                <td>${current.schoolAcademy.academyName}</td>
                                <td>${fn:split(current.centerName,',')[0]}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[1]==1}">
                                            <font color="green" ;><strong>√</strong></font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red" ;><strong>×</strong></font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[2]==1}">
                                            <font color="green">√</font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red">×</font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[3]==1}">
                                            <font color="green">√</font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red">×</font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[4]==1}">
                                            <font color="green">√</font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red">×</font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[5]==1}">
                                            <font color="green">√</font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red">×</font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[6]==1}">
                                            <font color="green">√</font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red">×</font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[7]==1}">
                                            <font color="green">√</font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red">×</font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[8]==1}">
                                            <font color="green">√</font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red">×</font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[9]==1}">
                                            <font color="green">√</font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red">×</font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[10]==1}">
                                            <font color="green">√</font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red">×</font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[11]==1}">
                                            <font color="green">√</font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red">×</font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fn:split(current.centerName,'%')[12]==1}">
                                            <font color="green">√</font>
                                        </c:when>
                                        <c:otherwise>
                                            <font color="red">×</font>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!-- 分页开始 -->
                    <div class="page">
                        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                        <a href="javascript:void(0)"
                           onclick="exportAll('${pageContext.request.contextPath}/inspection/labSecurityCheckStatistics?currpage=1&orderBy=${orderBy }')"
                           target="_self">首页</a>
                        <a href="javascript:void(0)"
                           onclick="exportAll('${pageContext.request.contextPath}/inspection/labSecurityCheckStatistics?currpage=${pageModel.previousPage}&orderBy=${orderBy }')"
                           target="_self">上一页</a>
                        第<select
                            onchange="exportAll(this.options[this.selectedIndex].value);">
                        <option value="${pageContext.request.contextPath}/inspection/labSecurityCheckStatistics?currpage=${pageModel.currpage}&orderBy=${orderBy }">${pageModel.currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                   var="current">
                            <c:if test="${j.index!=pageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/inspection/labSecurityCheckStatistics?currpage=${j.index}&orderBy=${orderBy }">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"
                           onclick="exportAll('${pageContext.request.contextPath}/inspection/labSecurityCheckStatistics?currpage=${pageModel.nextPage}&orderBy=${orderBy }')"
                           target="_self">下一页</a>
                        <a href="javascript:void(0)"
                           onclick="exportAll('${pageContext.request.contextPath}/inspection/labSecurityCheckStatistics?currpage=${pageModel.lastPage}&orderBy=${orderBy }')"
                           target="_self">末页</a>
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
