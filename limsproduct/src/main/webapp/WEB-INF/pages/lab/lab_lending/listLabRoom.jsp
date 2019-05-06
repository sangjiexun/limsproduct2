<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<head>
    <meta name="decorator" content="iframe"/>
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
    <!-- 下拉框的样式 -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
    <!-- 打印插件的引用 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <!-- 弹窗 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <!-- 弹窗 -->
    <script type="text/javascript">
        //取消查询
        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/labRoomLending/listLabRoom?currpage=1"
        }

        //跳转
        function targetUrl(url) {
            document.form.action = url;
            document.form.submit();
        }
    </script>
    <script type="text/javascript">
        //导出
        function exportAll(s) {
            document.form.action = s;
            document.form.submit();
        }
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/labreservation/labreservation.js"></script>
    <script type="text/javascript">
        //弹出学生预约框
        function showLabRoomReservation(labRoomId) {
            layer.ready(function () {
                layer.open({
                    type: 2,
                    title: '实训室借用',
                    fix: true,
                    maxmin: true,
                    shift: -1,
                    closeBtn: 1,
                    shadeClose: true,
                    move: false,
                    maxmin: false,
                    area: ['1000px', '420px'],
                    content: '../labRoomLending/showLabRoomLending?labRoomId=' + labRoomId,
                    end: function () {
                    }
                });
            });
        }
    </script>
</head>
<body>
<!--导航  -->
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">排课管理</a></li>
            <li><a href="javascript:void(0)">课程项目管理</a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.trainingRoom.setting"/>借用</a></li>
        </ul>
    </div>
</div>
<!--导航结束  -->

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup1">
            <div class="TabbedPanelsContent">
                <div class="tool-box">
                    <ul>
                        <form:form name="form"
                                   action="${pageContext.request.contextPath}/labRoomLending/listLabRoom?currpage=1"
                                   method="post" modelAttribute="labRoom">
                            <li><spring:message code="left.trainingRoom.setting"/>名称：</li>
                            <li><form:input path="labRoomName"/></li>
                            <li><input type="button"
                                       onclick="exportAll('${pageContext.request.contextPath}/labRoomLending/listLabRoom?currpage=1');"
                                       value="搜索"></li>
                            <%--<li><input type="button" value="打印" id="print"></li>--%>
                            <%--<li>
                              <input type="button" value="导出" onclick="exportAll('${pageContext.request.contextPath}/lab/labAnnexListexportall?page=${currpage}');">
                            </li>--%>
                            <li><input type="button" onclick="cancel()" value="取消"></li>
                        </form:form>
                    </ul>
                </div>
            </div>
            <div class="content-box">
                <table>
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th><spring:message code="left.trainingRoom.setting"/>名称
                        </th>
                        <th><spring:message code="left.trainingRoom.setting"/>编号
                        </th>
                        <th><spring:message code="left.trainingRoom.setting"/>地址</th>
                        <th>可预约工位数</th>
                        <th><%-- <fmt:message key="navigation.operate" /> --%>操作
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listLabRoom}" var="s" varStatus="i">
                        <tr>
                            <td>${i.count+(currpage-1)*pageSize }</td>
                            <td>${s.labRoomName}</td>
                            <td>${s.labRoomNumber}</td>
                            <td>${s.labRoomAddress}</td>
                            <td>${s.labRoomWorker}</td>
                            <td><sec:authorize
                                    ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_TEACHER,ROLE_LABMANAGER,ROLE_EQUIPMENTADMIN,ROLE_ASSISTANT">
                                <%-- <a onclick="showLabRoomReservation(${s.id})" href="javascript:void(0)">借用</a>  target="_blank" --%>
                                <a href="${pageContext.request.contextPath}/labRoomLending/showLabRoomLending?labRoomId=${s.id}">借用</a>
                                <%--<a href="${pageContext.request.contextPath}/labRoomLending/labRoomLendingInfoList?idKey=${s.id}&flag=1&step=0">查看</a>
                                --%></sec:authorize>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="page">
                    ${totalRecords}条记录 &nbsp; 共${pageModel.totalPage}页 &nbsp;
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/labRoomLending/listLabRoom?currpage=1')"
                       target="_self">首页</a>
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/labRoomLending/listLabRoom?currpage=${pageModel.previousPage}')"
                       target="_self">上一页</a>
                    <input type="hidden" value="${pageModel.lastPage}" id="totalpage"/>&nbsp; <input type="hidden"
                                                                                                     value="${currpage}"
                                                                                                     id="currpage"/>
                    &nbsp;第
                    <select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                        <option value="${pageContext.request.contextPath}/system/listUser?currpage=${currpage}">${currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                   var="current">
                            <c:if test="${j.index!=currpage}">
                                <option value="${pageContext.request.contextPath}/labRoomLending/listLabRoom?currpage=${j.index}">${j.index}</option>
                            </c:if>
                        </c:forEach>
                    </select>页&nbsp;

                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/labRoomLending/listLabRoom?currpage=${pageModel.nextPage}')"
                       target="_self">下一页</a>
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/labRoomLending/listLabRoom?currpage=${pageModel.lastPage}')"
                       target="_self">末页</a>
                </div>

            </div>
        </div>

        <!-- 下拉框的js -->

        <script
                src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>

        <script
                src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
                type="text/javascript" charset="utf-8"></script>

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
</div>