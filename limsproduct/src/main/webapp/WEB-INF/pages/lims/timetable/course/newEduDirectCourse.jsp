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
    <link href="${pageContext.request.contextPath}/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/select2/select2.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/select2/select2-bootstrap4.css" rel="stylesheet">
    <!-- jquery的js引用 -->
    <script src="${pageContext.request.contextPath}/jquery/lib/jquery.js" type="text/javascript"></script>
    <!-- select2的js引用 -->
    <script src="${pageContext.request.contextPath}/select2/select2.full.js"></script>
    <!-- jquery的页面验证 -->
    <script src="${pageContext.request.contextPath}/jquery/jquery.validate.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jquery/messages_zh.js" type="text/javascript"></script>
    <!-- 页面业务的js引用 -->
    <script src="${pageContext.request.contextPath}/js/lims/timetable/course/newEduDirectCourse.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
    <style type="text/css">
        label { width: 10em; float: left; }
        label.error { float: none; color: red; padding-left: .5em; vertical-align: top; }
        p { clear: both; }
        .submit { margin-left: 12em; }
    </style>
    <script>
        var auditOrNot = ${auditOrNot};
    </script>
</head>

<body>
<br>

<form id="form_lab" action="" method="post" >
    <input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
    <input type="hidden" id="academyNumber" name="academyNumber" value="${academyNumber}">
    <input type="hidden" id="courseNo" name="courseNo" value="${courseNo}">
    <h3>开始直接排课</h3>
    软件筛选<input type="checkbox" name="select_check" value="SOFTWARE" onclick="checkSelected()" >
    <!-- schoolCourseDetail的no -->
    &emsp;<input type="button" id="submitButton" name="deviceButton" value=" 确定 " class="btn btn-primary"  style="float:right">
    <hr>
    <br>
    <table border="0" align="center" style="width:100%;">
        <tr>
            <td width="5%"></td>
            <td width="15%"></td>
            <td width="80%"></td>
        </tr>
        <tr style="display:none" id="tr_soft">
            <td width="5%"></td>
            <td align=left width="17%" ><h5>选择软件<font color="red"> *</font>：</h5></td>
            <td >
                <select id="soft_id" name="soft_id" multiple="multiple" >
                </select>
                <label for="soft_id"></label>
            </td>
        </tr>
        <tr>
            <td width="5%"></td>
            <td align=left width="17%"><h5>请选择实验室<font color="red"> *</font>：</h5></td>
            <td>
                <select id="labRoom_id" name="labRoom_id"  multiple="multiple" required>
                </select>
                <label for="labRoom_id"></label>
            </td>
        </tr>
<c:if test="${virtual eq 'true'}">
        <tr>
            <td width="5%"></td>
            <td align=left width="17%"><h5>虚拟镜像：</h5></td>
            <td>
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
            <td width="5%"></td>
            <td align=left width="17%"><h5>请选择授课教师<font color="red"> *</font>：</h5></td>
            <td>
                <select id="teacherRelated" name="teacherRelated"  multiple="multiple" required>
                    <option value ="${schoolCourse.userByTeacher.username}" selected="selected">${schoolCourse.userByTeacher.cname}(学号：${schoolCourse.userByTeacher.username};学院：${schoolCourse.schoolAcademy.academyName})</option>
                </select>
                <label for="teacherRelated"></label>
            </td>
        </tr>
        <tr>
            <td width="5%"></td>
            <td align=left width="17%"><h5>请选择指导教师：</h5></td>
            <td>
                <select id="tutorRelated" name="tutorRelated"  multiple="multiple">
                </select>
            </td>
        </tr>
        <tr>
            <td align=right> </td>
            <td>
             </td>
        </tr>
    </table>
    <hr/>
</form>

</body>
</html>

