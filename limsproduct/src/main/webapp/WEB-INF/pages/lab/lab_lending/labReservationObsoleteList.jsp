<%@ page import="java.util.List" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式 -->
    <link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css"/>

    <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

    <script type="text/javascript">

        //首页
        function first(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

        //末页
        function last(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

        //上一页
        function previous(url) {
            var page =${page};
            if (page == 1) {
                page = 1;
            } else {
                page = page - 1;
            }
            //alert("上一页的路径："+url+page);
            document.queryForm.action = url + page;
            document.queryForm.submit();
        }

        //下一页
        function next(url) {
            var totalPage =${pageModel.totalPage};
            var page =${page};
            if (page >= totalPage) {
                page = totalPage;
            } else {
                page = page + 1
            }
            //alert("下一页的路径："+page);
            document.queryForm.action = url + page;
            document.queryForm.submit();
        }

        //转到实验室工位预约
        function toStation() {
            window.location.href = "${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?tage=0&currpage=1&isaudit=${isAudit}";
        }

        //取消
        function cancleQuery() {
            window.location.href = "${pageContext.request.contextPath}/labRoomLending/labReservationObsoleteList?page=1&tage=0&isaudit=${isAudit}";
        }
    </script>

</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">课程项目管理</a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="all.trainingRoom.labroom"/>借用申请</a></li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab" id="s1"><a
                    href="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1"><spring:message
                    code="all.trainingRoom.labroom"/>预约</a>
            </li>
            <li class="TabbedPanelsTab" id="s2"><a
                    href="${pageContext.request.contextPath}/labRoomLending/labReservationList?tage=0&page=1&isaudit=2">我的申请</a>
            </li>
            <sec:authorize ifNotGranted="ROLE_STUDENT">
                <li class="TabbedPanelsTab" id="s3"><a
                        href="${pageContext.request.contextPath}/labRoomLending/labReservationList?tage=0&page=1&isaudit=1">我的审核</a>
                </li>
            </sec:authorize>
            <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
                <li class="TabbedPanelsTab selected" id="s4"><a
                        href="${pageContext.request.contextPath}/labRoomLending/labReservationObsoleteList?page=1">作废列表</a>
                </li>
            </c:if>
        </ul>
        <div class="TabbedPanelsTabGroup-box">
            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">
                    <div class="content-box">
                        <div class="tool-box">
                            <form:form name="queryForm"
                                       action="${pageContext.request.contextPath}/labRoomLending/labReservationObsoleteList?page=1&tage=${tage}&isaudit=${isAudit}"
                                       method="post" modelAttribute="labReservation">
                                <ul style="position: absolute;">
                                    <li><spring:message code="all.trainingRoom.labroom"/>:<form:input
                                            path="labRoom.labRoomName"/></li>
                                    <li><input type="submit" value="查询">
                                        <input class="cancel-submit" type="button" onclick="cancleQuery()" value="取消"></li>
                                </ul>
                            </form:form>
                        </div>
                        <table class="tb" id="my_show">
                            <thead>
                            <tr>
                                <th><spring:message code="all.trainingRoom.labroom"/>名称</th>
                                <th>申请人</th>
                                <th>学期</th>
                                <th>使用周</th>
                                <th>星期</th>
                                <th>使用节次</th>
                                <th>审核信息</th>
                                <th>预约信息</th>
                                <th>备注</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${items}" var="current" varStatus="i">

                                <tr>
                                    <td>${current[0]}</td>
                                    <td>${current[1].cname}[${current[1].username}]</td>
                                    <td>${current[2]}</td>
                                    <td>${current[3]}</td>
                                    <td>${current[4]}</td>
                                    <td>${current[5]}</td>
                                    <td>${current[6]}</td>
                                    <td>${current[7]}</td>
                                    <td>${current[8]}</td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>

                        <!-- 分页模块 -->
                        <div class="page">
                            ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                            <a href="javascript:void(0)"
                               onclick="first('${pageContext.request.contextPath}/labRoomLending/labReservationObsoleteList?page=1')"
                               target="_self">首页</a>
                            <a href="javascript:void(0)"
                               onclick="previous('${pageContext.request.contextPath}/labRoomLending/labReservationObsoleteList?page=')"
                               target="_self">上一页</a>
                            第<select
                                onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                            <option value="${pageContext.request.contextPath}/labRoomLending/labReservationObsoleteList?page=${page}">${page}</option>
                            <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                       var="current">
                                <c:if test="${j.index!=page}">
                                    <option value="${pageContext.request.contextPath}/labRoomLending/labReservationObsoleteList?page=${j.index}">${j.index}</option>
                                </c:if>
                            </c:forEach></select>页
                            <a href="javascript:void(0)"
                               onclick="next('${pageContext.request.contextPath}/labRoomLending/labReservationObsoleteList?page=')"
                               target="_self">下一页</a>
                            <a href="javascript:void(0)"
                               onclick="last('${pageContext.request.contextPath}/labRoomLending/labReservationObsoleteList?page=${pageModel.totalPage}')"
                               target="_self">末页</a>
                        </div>
                        <!-- 分页模块 -->
                    </div>

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


