<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="iframe"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>

    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/bootstrap/css/plugins/bootstrap-table/bootstrap-table.min.css"/>
    <!-- 全局的引用 -->
    <script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 弹出框插件的引用 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <!-- bootstrap的引用 -->
    <link href="${pageContext.request.contextPath}/bootstrap/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" >
    <script src="${pageContext.request.contextPath}/bootstrap/bootstrap-select/js/bootstrap-select.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table.min.js"
            type="text/javascript"></script>

    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/lims/timetable/engineer/edurecourse/eduReCourseList.js"
            type="text/javascript"></script>

    <style>
        .fixed-table-container thead th .sortable{
            background-image:url('${pageContext.request.contextPath}/images/sort.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:30px
        }
        .fixed-table-container thead th .asc{
            background-image:url('${pageContext.request.contextPath}/images/sort_asc.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:10px
        }
        .fixed-table-container thead th .desc{
            background-image:url('${pageContext.request.contextPath}/images/sort_dsc.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:10px
        }
    </style>

</head>

<body>
<div class="iStyle_RightInner">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
            <%--<sec:authorize ifNotGranted="ROLE_TEACHER,ROLE_LABMANAGER">--%>
            <li class="TabbedPanelsTab selected" id="s1">
                <a class="iStyle_Feelings_Tree_Leaf "
                   href="${pageContext.request.contextPath}/lims/timetable/engineer/educourse/eduCourseList">教务排课</a>
            </li>
            <%--</sec:authorize>--%>
            <li class="TabbedPanelsTab" id="s2">
                <a class="iStyle_Feelings_Tree_Leaf "
                   href="${pageContext.request.contextPath}/lims/timetable/engineer/edurecourse/eduReCourseList">自主排课</a>
            </li>
            <li class="TabbedPanelsTab" id="s3">
                <a class="iStyle_Feelings_Tree_Leaf "
                   href="${pageContext.request.contextPath}/lims/timetable/self/selfCourseList?type=PLAN">调停课管理</a>
            </li>
        </ul>
    </div>
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="site-box">
            <div class="site-content">
                <div>
                    <div id="toolbar">
                        <form class="form-inline">
                            <div class="form-group">
                                <label class="sr-only" for="product_line">选择学期</label>
                                <div class="input-group">
                                    <div class="input-group-addon">选择学期</div>
                                    <select id="term" name="term"  class="selectpicker" data-live-search="true" title="请选择">
                                        <c:forEach items="${schoolTerms}" var="current">
                                            <c:if test="${current.id == term}">
                                                <option value="${current.id}" selected>${current.termName} </option>
                                            </c:if>
                                            <c:if test="${current.id != term}">
                                                <option value="${current.id}">${current.termName} </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="sr-only" for="msg_type">综合查询</label>
                                <div class="input-group">
                                    <div class="input-group-addon">综合查询</div>
                                    <input type="text" id="search" name="search" value="" placeholder="多字段查询">
                                </div>
                            </div>
                        </form>
                    </div>
                    <table id="table_list" style="text-align: left;"></table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 直接排课 -->
<div id="doStart" name="doStart" title="直接排课" style="display: none;">
    <br>
    <form:form id="form_lab" action="" method="post" modelAttribute="timetableAppointment">
        当前学期：${schoolTerm.termName} 当前周次：${week }
        <!-- schoolCourseDetail的no -->
        <hr>
        <br>
        <div class="right-content">
            <div id="TabbedPanels1" class="TabbedPanels">
                <div class="TabbedPanelsContentGroup">
                    <div class="TabbedPanelsContent">
                        <div class="tool-box">

                            <table border="0" align="center">
                                <tr>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <!--根据cstaticValue的配置选项确定是否可选设备资源  -->
                                <c:if test="${empty cStaticValue ||cStaticValue.staticValue !='1'}">
                                    <tr>
                                        <td align=left>请选择实验室：</td>
                                        <td>
                                            <select class="selectpicker bla bla bli" multiple data-live-search="true" data-placeholder='请选择实验室：'
                                                    name="labRoom_id" id="labRoom_id" items="${labRoomMap}"  invalidMessage="不能超过30个字符！" style="width:450px" required="true">
                                                <c:forEach items="${labRoomMap}" var="entry">
                                                    <option value="${entry.key}">${entry.value}</option>
                                                </c:forEach>
                                            </select>
                                            <input type="hidden" name="devices" id="devices">
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty cStaticValue &&cStaticValue.staticValue == '1'}">
                                    <tr>
                                        <td align=left>&nbsp;</td>
                                        <td>
                                    </tr>
                                    <tr>
                                        <td align=left>请选择实验室：</td>
                                        <td>
                                            <select class="selectpicker bla bla bli" multiple data-live-search="true" data-placeholder='请选择实验室：' name="labRoom_id"
                                                    id="labRoom_id" items="${labRoomMap}" invalidMessage="不能超过30个字符！"
                                                    style="width:450px" required="true">
                                                <option value="">请选择</option>
                                                <c:forEach items="${labRoomMap}" var="entry">
                                                    <option value="${entry.key}">${entry.value}</option>
                                                </c:forEach>
                                            </select></td>
                                    </tr>
                                    <tr>
                                        <td align=left>&nbsp;</td>
                                        <td>
                                    </tr>
                                    <tr>
                                        <td align=left>请选择实验设备：</td>
                                        <td>
                                            <select class="selectpicker bla bla bli" multiple data-live-search="true" data-placeholder='请选择实验设备：'
                                                    name="devices" id="devices" style="width:450px" required="false">
                                            </select></td>
                                    </tr>
                                </c:if>

                                <tr>
                                    <td align=left>&nbsp;</td>
                                    <td>
                                </tr>
                                <tr>
                                    <td align=left>请选择上课老师：</td>
                                    <td>
                                        <select class="selectpicker bla bla bli" multiple data-live-search="true" data-placeholder='请选择上课老师：'
                                                name="teacherRelated" id="teacherRelated" style="width:450px"
                                                required="true">
                                            <c:forEach items="${timetableTearcherMap}" var="entry">
                                                <option value="${entry.key}">${entry.value}</option>
                                            </c:forEach>
                                        </select>
                                </tr>
                                <tr>
                                    <td align=left>&nbsp;</td>
                                    <td>
                                </tr>
                                <tr>
                                    <td align=left>请选择指导老师：</td>
                                    <td>
                                        <select class="selectpicker bla bla bli" multiple data-live-search="true" data-placeholder='请选择指导老师：'
                                                name="tutorRelated" id="tutorRelated" style="width:450px"
                                                required="true" invalidMessage="不能超过50个字符！">
                                            <c:forEach items="${timetableTearcherMap}" var="entry">
                                                <option value="${entry.key}">${entry.value}</option>
                                            </c:forEach>
                                        </select>
                                </tr>
                                <!--如果是纺织学院，则可以配置实验设备资源  -->
                                <c:if test="${!empty cStaticValue &&cStaticValue.staticValue == '1'}">
                                    <tr>
                                        <td align=left>请选择指导老师：</td>
                                        <td>
                                            <select class="selectpicker bla bla bli" multiple data-live-search="true" data-placeholder='请选择指导老师：'
                                                    name="tutorRelated" id="tutorRelated" style="width:450px"
                                                    required="true" invalidMessage="不能超过50个字符！">
                                                <c:forEach items="${timetableTearcherMap}" var="entry">
                                                    <option value="${entry.key}">${entry.value}</option>
                                                </c:forEach>
                                            </select>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td align=left>&nbsp;</td>
                                    <td>
                                </tr>

                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <br>
        <br>
        <br>
        <input type="button" id="deviceButton" name="deviceButton" value="确定">
    </form:form>
</div>
</body>
</html>

