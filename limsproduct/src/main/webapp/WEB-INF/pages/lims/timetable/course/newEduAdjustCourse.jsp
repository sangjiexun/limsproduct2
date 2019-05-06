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
    <script src="${pageContext.request.contextPath}/js/lims/timetable/course/newEduAdjustCourse.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
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
    <table id="table_timetable_info" > </table>
</div>
<div style="height:70%;width:90%;margin:0 auto;">
    <form id="form_lab" action="" method="post" >
        <input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
        <input type="hidden" id="academyNumber" name="academyNumber" value="${academyNumber}">
        <input type="hidden" id="courseNo" name="courseNo" value="${courseNo}">
        <input type="hidden" id="courseNumber" name="courseNumber" value="${courseNumber}">
        <input type="hidden" id="courseDetailNo" name="courseDetailNo">
        <input type="hidden" id="term" name="term" value="${term}">
        <br>
        <div style="width:95%;">
            <h3><b>开始排课选择</b></h3>
            软件筛选<input type="checkbox" name="select_check" value="SOFTWARE" onclick="checkSelected()" >
            <input type="button" id="submitButton" name="submitButton" value=" 确定 " class="btn btn-primary btn-lg" style="float:right">
        </div>

            <!-- schoolCourseDetail的no -->
        <hr>
        <table border="0" align="center" style="width:100%;border-collapse:separate; border-spacing:0px 10px;">
            <td align=left width="12%"><h3>授课教师<font color="red"> *</font>：</h3></td>
            <td>
                <select id="teacherRelated" name="teacherRelated" multiple="multiple" required>
                    <option value ="${schoolCourse.userByTeacher.username}" selected="selected">${schoolCourse.userByTeacher.cname}(学号：${schoolCourse.userByTeacher.username};学院：${schoolCourse.schoolAcademy.academyName})</option>
                </select>
                <label for="teacherRelated"></label>
            </td>
            <td align=left width="10%"><h3>指导教师：</h3></td>
            <td>
                <select id="tutorRelated" name="tutorRelated" multiple="multiple">
                </select>
            </td>
            </tr>

            <tr style="overflow: hidden">
                <td align=left width="12%"><h3>实验项目：</h3></td>
                <td width="39%">
                    <select id="items" name="items" multiple="multiple">
                    </select>
                    <label for="weeks"></label>
                </td>
                <td align=left width="10%"><h3>星期<font color="red"> *</font>：</h3></td>
                <td width="39%">
                    <select id="weekday" name="weekday" required>
                    </select>
                    <label for="weekday"></label>
                </td>
            </tr>
            <tr style="display:none" id="tr_soft">
                <td align=left width="12%" ><h3>选择软件<font color="red"> *</font>：</h3></td>
                <td colspan="3">
                    <select id="soft_id" name="soft_id" multiple="multiple" >
                    </select>
                    <label for="soft_id"></label>
                </td>
            </tr>
            <tr>
                <td align=left width="12%"><h3>实验室<font color="red"> *</font>：</h3></td>
                <td colspan="3">
                    <select id="labRoom_id" name="labRoom_id" multiple="multiple" required>
                    </select>
                    <label for="labRoom_id"></label>
                </td>

            </tr>
            <tr>
                <td align=left width="12%"><h3>请选择节次<font color="red"> *</font>：</h3></td>
                <td colspan="3">
                    <select id="classes" name="classes"  multiple="multiple" required >
                    </select>
                    <label for="classes"></label>

                </td>
            </tr>
            <tr>
                <td align=left width="12%"><h3>请选择周次<font color="red"> *</font>：</h3></td>
                <td colspan="3">
                    <select id="weeks" name="weeks"  multiple="multiple" required >
                    </select>
                    <label for="weeks"></label>
                </td>
            </tr>
<c:if test="${virtual eq 'true'}">
            <tr>
                <td align=left width="12%"><h3>虚拟镜像：</h3></td>
                <td colspan="3">
                    <select id="virtualId" name="virtualId">
                        <option value="">请选择虚拟镜像...</option>
                        <c:forEach var="curr" items="${virtualImageList}">
                            <option value="${curr.id}">${curr.name}</option>
                        </c:forEach>
                    </select>
                    <label for="virtualId"></label>
                </td>
            </tr>
</c:if>
            <tr>
                <td align=right> </td>
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

