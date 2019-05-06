<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <meta name="decorator" content="iframe"/>
    <link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css"/>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css"/>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <!-- 下拉的样式结束 -->

    <!-- 弹窗 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <!-- 打印开始 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <!-- 打印结束 -->
    <!-- 打印、导出组件 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/LodopFuncs.js"></script>

    <script type="text/javascript">
        //分页
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
        //取消查询
        function cancleQuery() {
            window.location.href = "${pageContext.request.contextPath}/report/reportLabRoomDeviceUsed?page=1";
        }
        //打印
        function print(){
            $('#my_show').jqprint();
        }
        //导出
        function exportLabRoomDeviceUsed(){
            document.queryForm.action = "${pageContext.request.contextPath}/report/exportReportLabRoomDeviceUsed";
            document.queryForm.submit();
        }

    </script>
</head>
<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.report.system" /></a></li>
            <li class="end"><a href="javascript:void(0)">设备使用报表</a></li>
        </ul>
    </div>
</div>
<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab1 selected" id="s1">
            <a href="javascript:void(0)">设备使用报表</a>
        </li>
        <a class="btn btn-new" onclick="exportLabRoomDeviceUsed()">导出</a>
        <a class="btn btn-new" onclick="print()">打印</a>
    </ul>
    <%--<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">设备使用报表</ul>--%>
    <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
            <div class="content-box">
                <div class="tool-box">
                    <form:form name="queryForm"
                               action="${pageContext.request.contextPath}/report/reportLabRoomDeviceUsed?page=1"
                               method="post" modelAttribute="reservation">
                        <table class="tab_fix">
                            <tr>
                                <td>
                                    学期:
                                    <form:select path="schoolTerm.id" class="chzn-select">
                                        <form:option value="">请选择</form:option>
                                        <form:options items="${terms}" itemLabel="termName" itemValue="id"/>
                                    </form:select>

                                </td>
                                <td>
                                    设备名称:<form:input path="labRoomDevice.schoolDevice.deviceName"/>
                                </td>
                                <td>
                                    使用人:
                                    <form:select path="userByReserveUser.username" class="chzn-select">
                                        <form:option value="">请选择</form:option>
                                        <c:forEach var="reserveUser" items="${reserveUsers}">
                                            <form:option
                                                    value="${reserveUser.key}">[${reserveUser.key}]${reserveUser.value}</form:option>
                                        </c:forEach>
                                    </form:select>

                                </td>
                                <td>
                                    导师:
                                    <form:select path="userByTeacher.username" class="chzn-select">
                                        <form:option value="">请选择</form:option>
                                        <c:forEach var="teacher" items="${teachers}">
                                            <form:option
                                                    value="${teacher.key}">[${teacher.key}]${teacher.value}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </td>


                            </tr>
                            <tr>
                                <td>
                                    设备管理员:
                                    <form:select path="labRoomDevice.user.username" class="chzn-select">
                                        <form:option value="">请选择</form:option>
                                        <c:forEach var="manageUser" items="${manageUsers}">
                                            <form:option
                                                    value="${manageUser.key}">[${manageUser.key}]${manageUser.value}</form:option>
                                        </c:forEach>
                                    </form:select>

                                </td>
                                <td><span>申请时间:</span>
                                    <input class="easyui-datebox" id="begintime" name="begintime" value="${begintime}"
                                           type="text" onclick="new Calendar().show(this);" style="width:100px;"/>
                                    <span class="combo_text">到</span>
                                    <input class="easyui-datebox" id="endtime" name="endtime"
                                           value="${endtime}" type="text" onclick="new Calendar().show(this);"
                                           style="width:100px;"/>
                                </td>
                                <td>
                                    实验室:
                                    <form:select path="labRoomDevice.labRoom.id" class="chzn-select">
                                        <form:option value="">请选择</form:option>
                                        <form:options items="${labrooms}"/>
                                    </form:select>
                                </td>
                                <td>
                                    使用机时:
                                    <form:input type="text" path="reserveHours" style="width: 50px!important;"/>小时
                                </td>
                                <td>
                                    <input type="button" onclick="targetUrl('${pageContext.request.contextPath}/report/reportLabRoomDeviceUsed?page=1')" value="查询">
                                    <input class="cancel-submit" type="button" onclick="cancleQuery()" value="取消">
                                </td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
            <div class="content-box">
                <%--<div class="title">--%>
                    <%--<div id="title">设备使用列表</div>--%>
                    <%--<a class="btn btn-new" onclick="exportLabRoomDeviceUsed()">导出</a>--%>
                    <%--<a class="btn btn-new" onclick="print()">打印</a>--%>
                <%--</div>--%>
                <table id="my_show">
                    <thead>
                    <tr>
                        <th style="width:3%">序号</th>
                        <th style="width:8%">预约设备</th>
                        <th style="width:8%">申请人</th>
                        <th style="width:5%">指导教师</th>
                        <th style="width:10%">使用内容</th>
                        <th style="width:5%">使用机时</th>
                        <th style="width:5%">日期</th>
                        <th style="width:10%">使用时间</th>
                        <th style="width:8%">设备管理员</th>
                        <th style="width:8%">实验室</th>
                        <th style="width:6%">收费情况</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${reservationList}" var="reservation" varStatus="i">
                        <tr>
                            <td>${i.count+(page-1)*pageSize}</td>
                            <td>
                                <c:if test="${empty reservation.innerDeviceName }">
                                    ${reservation.labRoomDevice.schoolDevice.deviceName}[(${reservation.labRoomDevice.schoolDevice.deviceNumber})]
                                </c:if>
                                <c:if test="${not empty reservation.innerDeviceName }">
                                    ${reservation.innerDeviceName}<font color="red">关联设备</font>
                                </c:if>
                            </td>
                            <td>${reservation.userByReserveUser.cname}[${reservation.userByReserveUser.username}]</td>
                            <td>${reservation.userByTeacher.cname}<a hidden="${reservation.userByTeacher.username}"></a>
                            </td>
                            <td><p>${reservation.content}</p></td>
                            <td><fmt:formatNumber value="${reservation.reserveHours}" type="number"
                                                  maxFractionDigits="2"/>小时
                            </td>
                            <td>
                                <fmt:formatDate value="${reservation.begintime.time}" pattern="yyyy-MM-dd"/>
                            </td>
                            <td>
                                <c:if test="${reservation.begintime.time.day eq reservation.endtime.time.day}">
                                    <fmt:formatDate value="${reservation.begintime.time}"
                                                    pattern="HH:mm:ss"/>-<fmt:formatDate
                                        value="${reservation.endtime.time}" pattern="HH:mm:ss"/>
                                </c:if>

                                <c:if test="${reservation.begintime.time.day ne reservation.endtime.time.day}">
                                    起<fmt:formatDate value="${reservation.begintime.time}"
                                                     pattern="yyyy/MM/dd HH:mm:ss"/><br>止<fmt:formatDate
                                        value="${reservation.endtime.time}" pattern="yyyy/MM/dd HH:mm:ss"/>
                                </c:if>
                            </td>
                            <td>${reservation.labRoomDevice.user.cname}[${reservation.labRoomDevice.user.username}]</td>
                            <td>${reservation.labRoomDevice.labRoom.labRoomName}</td>
                            <td>
                                <c:if test="${not empty reservation.labRoomDevice.CDictionaryByDeviceCharge.CName}">
                                    ${reservation.labRoomDevice.CDictionaryByDeviceCharge.CName}<br>
                                    <c:if test="${not empty reservation.labRoomDevice.price }">
                                        <fmt:formatNumber
                                                value="${reservation.labRoomDevice.price*reservation.reserveHours}"
                                                type="currency"/>
                                    </c:if>
                                    <c:if test="${empty reservation.labRoomDevice.price }">
                                        0元
                                    </c:if>
                                </c:if>
                                    <%-- <c:if test="${empty reservation.labRoomDevice.CDeviceCharge.name}"> --%>
                                <c:if test="${empty reservation.labRoomDevice.CDictionaryByDeviceCharge.CName}">
                                    <font color="red">标准未设置</font>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- 分页模块 -->
                <div class="page">
                    ${totalRecords}条记录,共${pageModel.totalPage}页
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/report/reportLabRoomDeviceUsed?page=1')"
                       target="_self">首页</a>
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/report/reportLabRoomDeviceUsed?page=${pageModel.previousPage}')"
                       target="_self">上一页</a>
                    第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                    <option selected="selected"
                            value="${pageContext.request.contextPath}/report/reportLabRoomDeviceUsed?page=${page}">${pageModel.currpage}</option>
                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                               var="current">
                        <c:if test="${j.index!=pageModel.currPage}">
                            <option value="${pageContext.request.contextPath}/report/reportLabRoomDeviceUsed?page=${j.index}">${j.index}</option>
                        </c:if>
                    </c:forEach></select>页
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/report/reportLabRoomDeviceUsed?page=${pageModel.nextPage}')"
                       target="_self">下一页</a>
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/report/reportLabRoomDeviceUsed?page=${pageModel.lastPage}')"
                       target="_self">末页</a>
                </div>
                <!-- 分页模块 -->

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
        '.chzn-select-width': {width: "100%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }

</script>
<!-- 下拉框的js -->

</body>
</html -->