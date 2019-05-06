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
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/bootstrap/css/plugins/bootstrap-table/bootstrap-table.min.css"/>
    <!-- jquery的js引用 -->
    <script src="${pageContext.request.contextPath}/jquery/lib/jquery.js" type="text/javascript"></script>
    <!-- 弹出框插件的引用 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/bootstrap/bootstrap.min.css" rel="stylesheet">
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
    <script src="${pageContext.request.contextPath}/js/lims/timetable/engineer/selfcourse/newSelfReGroupTimetableCourse.js"
            type="text/javascript"></script>
    <style type="text/css">
        label {width: 10em;float: left;}
        label.error {float: none;color: red;padding-left: .5em;vertical-align: top;}
        p {clear: both;}
        .submit {margin-left: 12em;}
        .table.table-bordered > tr > td {height: 20px;max-height: 20px;}
        .layui-table-cell {height: 20px  !important;line-height: 20px;}
        .layui-table tr {height: 20px !important;}
        body{
            font-size: 13px;
        }
    </style>

</head>

<body>
<br>
<div style="height:30%;width:90%;margin:0 auto;">
    <div class="iStyle_RightInner">
        <div id="TabbedPanels1" class="TabbedPanels">
            <div class="site-box">
                <div class="site-content">
                    <%--<table id="table_timetable_info" class="table table-bordered table-hover"
                           style="table-layout: fixed;"></table>--%>
                </div>
            </div>
        </div>
    </div>
</div>
<div style="height:70%;width:90%;margin:0 auto;">
    <form id="form_lab" action="" method="post">
        <input type="hidden" id="academyNumber" name="academyNumber" value="${academyNumber}">
        <input type="hidden" id="selfId" name="selfId" value="${selfId}">
        <input type="hidden" id="courseNumber" name="courseNumber" value="${courseNumber}">
        <input type="hidden" id="timetableStyle" name="selfId" value="${timetableStyle}">
        <input type="hidden" id="groupId" name="groupId" value="${groupId}">
        <input type="hidden" id="courseDetailNo" name="courseDetailNo">
        <input type="hidden" id="term" name="term" value="${term}">
        <input type="hidden" id="sameNumberId" name="sameNumberId" value="${timetableAppointmentSameNumber.id}">
        <br>
        <div style="width:95%;">
            <h3><b>
                <c:if test="${empty timetableAppointmentSameNumber}">开始排课选择</c:if>
                <c:if test="${!empty timetableAppointmentSameNumber}">编辑排课选择</c:if>
            </b></h3>
            软件筛选<input type="checkbox" name="select_check" value="SOFTWARE" onclick="checkSelected()" >
            <input type="button" id="submitButton" name="submitButton" value=" 确定 " class="btn btn-primary btn-lg"
                   style="float:right">
        </div>

        <!-- schoolCourseDetail的no -->
        <hr>
        <table border="0" align="center" style="width:100%;border-collapse:separate; border-spacing:0px 10px;">
            <tr style="overflow: hidden">
                <td align=left width="12%"><h3>授课教师<font color="red"> *</font>：</h3></td>
                <td>
                    <select id="teacherRelated" name="teacherRelated" multiple="multiple" required>
                        <c:if test="${!empty timetableAppointmentSameNumber}">
                            <c:forEach items="${timetableAppointmentSameNumber.timetableAppointment.timetableTeacherRelateds}" var="curr">
                                <option value ="${curr.user.username}" selected="selected">${curr.user.cname}(学号：${curr.user.username};学院：${curr.user.schoolAcademy.academyName})</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <label for="teacherRelated"></label>
                </td>
                <td align=left width="10%"><h3>指导教师：</h3></td>
                <td>
                    <select id="tutorRelated" name="tutorRelated" multiple="multiple">
                        <c:if test="${!empty timetableAppointmentSameNumber}">
                            <c:forEach items="${timetableAppointmentSameNumber.timetableAppointment.timetableTutorRelateds}" var="curr">
                                <option value ="${curr.user.username}" selected="selected">${user.cname}(学号：${curr.user.username};学院：${curr.user.schoolAcademy.academyName})</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </td>
            </tr>
            <tr style="overflow: hidden">
                <td align=left width="12%"><h3>星期<font color="red"> *</font>：</h3></td>
                <td width="39%">
                    <select id="weekday" name="weekday" onchange="doValidProperty()" required>
                        <c:if test="${!empty timetableAppointmentSameNumber}">
                            <c:if test="${timetableAppointmentSameNumber.timetableAppointment.weekday==1}">
                                <option value ="${timetableAppointmentSameNumber.timetableAppointment.weekday}" selected="selected">星期一</option>
                            </c:if>
                            <c:if test="${timetableAppointmentSameNumber.timetableAppointment.weekday==2}">
                                <option value ="${timetableAppointmentSameNumber.timetableAppointment.weekday}" selected="selected">星期二</option>
                            </c:if>
                            <c:if test="${timetableAppointmentSameNumber.timetableAppointment.weekday==3}">
                                <option value ="${timetableAppointmentSameNumber.timetableAppointment.weekday}" selected="selected">星期三</option>
                            </c:if>
                            <c:if test="${timetableAppointmentSameNumber.timetableAppointment.weekday==4}">
                                <option value ="${timetableAppointmentSameNumber.timetableAppointment.weekday}" selected="selected">星期四</option>
                            </c:if>
                            <c:if test="${timetableAppointmentSameNumber.timetableAppointment.weekday==5}">
                                <option value ="${timetableAppointmentSameNumber.timetableAppointment.weekday}" selected="selected">星期五</option>
                            </c:if>
                            <c:if test="${timetableAppointmentSameNumber.timetableAppointment.weekday==7}">
                                <option value ="${timetableAppointmentSameNumber.timetableAppointment.weekday}" selected="selected">星期六</option>
                            </c:if>
                            <c:if test="${timetableAppointmentSameNumber.timetableAppointment.weekday==7}">
                                <option value ="${timetableAppointmentSameNumber.timetableAppointment.weekday}" selected="selected">星期日</option>
                            </c:if>
                        </c:if>
                    </select>
                    <label for="weekday"></label>
                </td>
                <td align=left width="10%"><h3>选择节次<font color="red"> *</font>：</h3></td>
                <td width="39%">
                    <select id="classes" name="classes" multiple="multiple" onchange="doValidProperty()" required>
                        <c:if test="${!empty timetableAppointmentSameNumber}">
                            <c:forEach var="curr" begin="${timetableAppointmentSameNumber.startClass}" end="${timetableAppointmentSameNumber.endClass}">
                                <option value ="${curr}" selected="selected">${curr}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <label for="classes"></label>

                </td>
            </tr>
            <tr style="display:none" id="tr_soft">
                <td align=left width="12%" ><h3>选择软件<font color="red"> *</font>：</h3></td>
                <td colspan="3">
                    <select id="soft_id" name="soft_id" multiple="multiple" >
                        <c:if test="${!empty timetableAppointmentSameNumber}">
                            <c:forEach items="${timetableAppointmentSameNumber.timetableAppointment.timetableSoftwareRelateds}" var="curr">
                                <option value ="${curr.software.id}" selected="selected">${curr.software.name}(学号：${curr.software.code})</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <label for="soft_id"></label>
                </td>
            </tr>
            <tr>
                <td align=left width="12%"><h3>实验项目：</h3></td>
                <td colspan="3">
                    <select id="items" name="items" multiple="multiple">
                        <c:if test="${!empty timetableAppointmentSameNumber}">
                            <c:forEach items="${timetableAppointmentSameNumber.timetableAppointment.timetableItemRelateds}" var="curr">
                                <option value ="${curr.operationItem.id}" selected="selected">${curr.operationItem.lpName}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <label for="items"></label>
                </td>
            </tr>
            <tr>
                <td align=left width="12%"><h3>实验室<font color="red"> *</font>：</h3></td>
                <td colspan="3">
                    <select id="labRoom_id" name="labRoom_id" multiple="multiple" required>
                        <c:if test="${!empty timetableAppointmentSameNumber}">
                            <c:forEach items="${timetableAppointmentSameNumber.timetableAppointment.timetableLabRelateds}" var="curr">
                                <option value ="${curr.labRoom.id}" selected="selected">${curr.labRoom.labRoomName}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <label for="labRoom_id"></label>
                </td>

            </tr>
            <tr>
                <td align=left width="12%"><h3>选择周次<font color="red"> *</font>：</h3></td>
                <td colspan="3">
                    <select id="weeks" name="weeks" multiple="multiple" required>
                        <c:if test="${!empty timetableAppointmentSameNumber}">
                            <c:forEach var="curr" begin="${timetableAppointmentSameNumber.startWeek}" end="${timetableAppointmentSameNumber.endWeek}">
                                <option value ="${curr}" selected="selected">${curr}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <label for="weeks"></label>
                </td>
            </tr>
            <tr>
                <td align=left></td>
                <td>
                </td>
            </tr>
        </table>
        <hr/>
        <br>
    </form>
</div>
</body>
</html>

