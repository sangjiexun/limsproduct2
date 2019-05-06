<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="none"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <!-- 样式的引用 -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/bootstrap/css/plugins/bootstrap-table/bootstrap-table.min.css"/>
    <!-- jquery的js引用 -->
    <script src="${pageContext.request.contextPath}/jquery/lib/jquery.js" type="text/javascript"></script>
    <!-- 弹出框插件的引用 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/select2/select2.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/select2/select2-bootstrap4.css" rel="stylesheet">

    <!-- select2的js引用 -->
    <script src="${pageContext.request.contextPath}/select2/select2.full.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"
            type="text/javascript"></script>

    <script src="${pageContext.request.contextPath}/jquery/jquery.validate.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jquery/messages_zh.js" type="text/javascript"></script>
    <!-- 页面业务的js引用 -->
    <script src="${pageContext.request.contextPath}/js/lims/timetable/course/copyGroupTimetable.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
    <style type="text/css">
        label {
            width: 10em;
            float: left;
        }

        label.error {
            float: none;
            color: red;
            padding-left: .5em;
            vertical-align: top;
        }

        p {
            clear: both;
        }

        .submit {
            margin-left: 12em;
        }

        .table.table-bordered > tr > td {
            height: 20px;
            max-height: 20px;
        }

        .layui-table-cell {
            height: 20px !important;
            line-height: 20px;
        }

        .layui-table tr {
            height: 20px !important;
        }
        .dmrrr{overflow: hidden;}
        body {
            font-size: 13px;
        }
        .custom-control-label{
            font-size: 14px;
        }
        .form-group,
        .fixed-table-toolbar .bars{
            margin-top:0;
            margin-bottom:0;
        }
        .table > tbody + tbody {
            border-top: 1px solid #ddd;
        }
        .bootstrap-table .table{
            border-bottom:none;
        }
        th{
            whith:12.5%;
            background: #f9f9f9;
        }
        select{
            line-height: 28px;
            height: 28px;
            padding: 0 0 0 2px;
        }
        select,
        option{
            white-space: pre-line;
        }
        .select2-container--default .select2-selection--multiple .select2-selection__rendered{
            padding: 0 3px;
        }
        .select2-container--default .select2-selection--multiple .select2-selection__choice{
            padding: 0 3px;
            margin-right:3px;
        }
        .select2-container--default .select2-selection--multiple .select2-selection__clear{
            margin-right:3px!important;
        }
        .select2-container--default.select2-container--focus .select2-selection--multiple {
            border: solid #77bace 1px;
            background: #fffdf4;
        }
        .btn_cog {
            margin: 21px 0 0;
        }
        .btn_cog .btn {
            color: #fff;
            height: auto;
            background: #77bace;
            padding: 6px 20px;
        }
        .btn_cog .btn:hover {
            opacity: 0.9;
        }
    </style>

</head>

<body>
<%--<div style="height:30%;width:90%;margin:0 auto;">
    <div class="iStyle_RightInner">
        <div id="TabbedPanels1" class="TabbedPanels">
            <div class="site-box">
                <div class="site-content">
                    <table id="table_timetable_info" class="table table-bordered table-hover"
                           style="table-layout: fixed;"></table>
                </div>
            </div>
        </div>
    </div>
</div>--%>
<div class="dmrrr">
    <div class="iStyle_RightInner">
        <div class="navigation">
            <div id="navigation">
                <ul>
                    <li><a href="javascript:void(0)">复制排课选择</a></li>
                    <li><a href="javascript:void(0)" class="end">${destinationGroup.groupName}</a></li>
                </ul>
            </div>
        </div>
        <div id="TabbedPanels1" class="TabbedPanels">
            <input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
            <form id="form_lab" action="${pageContext.request.contextPath}/lims/timetable/engineer/edurecourse/saveCopyTimetableGroup" method="post">
                <input type="hidden" id="academyNumber" name="academyNumber" value="${academyNumber}">
                <input type="hidden" id="courseNo" name="courseNo" value="${courseNo}">
                <input type="hidden" id="courseNumber" name="courseNumber" value="${courseNumber}">
                <input type="hidden" id="timetableStyle" name="timetableStyle" value="${timetableStyle}">
                <input type="hidden" id="groupId" name="groupId" value="${groupId}">
                <input type="hidden" id="destinationId" name="destinationId" value="${groupId}">
                <input type="hidden" id="sourceId" name="sourceId" value="${groupId}">
                <input type="hidden" id="term" name="term" value="${term}">
                <input type="hidden" id="timetableAppointmentIds" name="timetableAppointmentIds" value="${timetableAppointmentIds}">
                <c:set var="flag" value="0" />
                <c:forEach items="${timetableAppointments}" var="timetableAppointment" varStatus="i">
                    <c:if test="${timetableAppointment.timetableSoftwareRelateds.size() > 0}">
                        <c:set var="flag" value="${flag+1}"/>
                    </c:if>
                </c:forEach>
                <div class="fixed-table-toolbar">
                    <div class="bars pull-left">
                        <div class="form-group">
                            <div class="custom-control custom-radio">
                                <input type="checkbox" class="custom-control-input" id="select_check" name="select_check" value="SOFTWARE" onclick="checkSelected()" <c:if test="${flag > 0}">checked</c:if>/>
                                <label class="custom-control-label" for="select_check">软件筛选</label>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- schoolCourseDetail的no -->
                <div class="bootstrap-table">
                    <div class="fixed-table-container">
                        <div class="fixed-table-body">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th><div class="th-inner ">授课教师<font color="red"> *</font></div></th>
                                    <th><div class="th-inner ">指导教师</div></th>
                                    <th><div class="th-inner ">星期<font color="red"> *</font></div></th>
                                    <th><div class="th-inner ">选择节次<font color="red"> *</font></div></th>
                                    <th id="td_soft" <c:if test="${flag == 0}">style="display: none;"</c:if>><div class="th-inner ">选择软件<font color="red"> *</font></div></th>
                                    <th><div class="th-inner ">实验项目</div></th>
                                    <th><div class="th-inner ">实验室</div></th>
                                    <th><div class="th-inner ">选择周次</div></th>
                                </tr>
                                </thead>
                                <c:forEach items="${timetableAppointments}" var="timetableAppointment" varStatus="i">
                                    <tbody>
                                    <tr style="overflow: hidden">
                                        <c:set var="courseDetailValue" value="" />
                                        <c:forEach items="${schoolCourse.schoolCourseDetails}" var="curr" varStatus="vi">
                                            <c:choose>
                                                <c:when test="${vi.last}">
                                                    <c:set var="courseDetailValue" value="${courseDetailValue}${curr.courseDetailNo}" />
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="courseDetailValue" value="${courseDetailValue}${curr.courseDetailNo}," />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <input type="hidden" name="courseDetailNo${timetableAppointment.id}" id="courseDetailNo${timetableAppointment.id}" value="${timetableAppointment.schoolCourseDetail.courseDetailNo}" />

                                        <td>
                                            <select id="teacherRelated${timetableAppointment.id}" name="teacherRelated${timetableAppointment.id}" multiple="multiple" required>
                                                <c:forEach
                                                        items="${timetableAppointment.timetableTeacherRelateds}"
                                                        var="curr">
                                                    <option value="${curr.user.username}"
                                                            selected="selected">${curr.user.cname}(${curr.user.username})_${curr.user.schoolAcademy.academyName}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select id="tutorRelated${timetableAppointment.id}" name="tutorRelated${timetableAppointment.id}" multiple="multiple">
                                                <c:forEach
                                                        items="${timetableAppointment.timetableTutorRelateds}"
                                                        var="curr">
                                                    <option value="${curr.user.username}"
                                                            selected="selected">${user.cname}(学号:${curr.user.username};)&nbsp;学院:${curr.user.schoolAcademy.academyName}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select id="weekday${timetableAppointment.id}" name="weekday${timetableAppointment.id}" required>
                                                <c:if test="${timetableAppointment.weekday==1}">
                                                    <option value="${timetableAppointment.weekday}"
                                                            selected="selected">星期一
                                                    </option>
                                                </c:if>
                                                <c:if test="${timetableAppointment.weekday==2}">
                                                    <option value="${timetableAppointment.weekday}"
                                                            selected="selected">星期二
                                                    </option>
                                                </c:if>
                                                <c:if test="${timetableAppointment.weekday==3}">
                                                    <option value="${timetableAppointment.weekday}"
                                                            selected="selected">星期三
                                                    </option>
                                                </c:if>
                                                <c:if test="${timetableAppointment.weekday==4}">
                                                    <option value="${timetableAppointment.weekday}"
                                                            selected="selected">星期四
                                                    </option>
                                                </c:if>
                                                <c:if test="${timetableAppointment.weekday==5}">
                                                    <option value="${timetableAppointment.weekday}"
                                                            selected="selected">星期五
                                                    </option>
                                                </c:if>
                                                <c:if test="${timetableAppointment.weekday==7}">
                                                    <option value="${timetableAppointment.weekday}"
                                                            selected="selected">星期六
                                                    </option>
                                                </c:if>
                                                <c:if test="${timetableAppointment.weekday==7}">
                                                    <option value="${timetableAppointment.weekday}"
                                                            selected="selected">星期日
                                                    </option>
                                                </c:if>
                                            </select>
                                        </td>
                                        <td>
                                            <select id="classes${timetableAppointment.id}" name="classes${timetableAppointment.id}" multiple="multiple" required>
                                                <c:forEach var="curr" items="${timetableAppClasses[timetableAppointment.id]}">
                                                    <option value="${curr}" selected="selected">${curr}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td id="td_soft_id${timetableAppointment.id}"
                                            <c:if test="${flag == 0}">style="display: none;"</c:if>
                                        >
                                            <select id="soft_id${timetableAppointment.id}" name="soft_id${timetableAppointment.id}" multiple="multiple">
                                                <c:forEach
                                                        items="${timetableAppointment.timetableSoftwareRelateds}"
                                                        var="curr">
                                                    <option value="${curr.software.id}"
                                                            selected="selected">${curr.software.name}(学号：${curr.software.code})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select id="items${timetableAppointment.id}" name="items${timetableAppointment.id}" multiple="multiple">
                                                <c:forEach
                                                        items="${timetableAppointment.timetableItemRelateds}"
                                                        var="curr">
                                                    <option value="${curr.operationItem.id}"
                                                            selected="selected">${curr.operationItem.lpName}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select id="labRoom_id${timetableAppointment.id}" name="labRoom_id${timetableAppointment.id}" multiple="multiple" required>
                                                <c:forEach
                                                        items="${timetableAppointment.timetableLabRelateds}"
                                                        var="curr">
                                                    <option value="${curr.labRoom.id}"
                                                            selected="selected">${curr.labRoom.labRoomName}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select id="weeks${timetableAppointment.id}" name="weeks${timetableAppointment.id}" multiple="multiple" required>
                                                <c:forEach var="curr" items="${timetableAppWeeks[timetableAppointment.id]}" >
                                                    <option value="${curr}" selected="selected">${curr}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                    </tbody>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="btn_cog">
                    <input type="button" id="submitTimetableBtn" name="submitTimetableBtn"  onclick="submitTimetable()" value=" 确定 " class="btn pull-right"/>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>