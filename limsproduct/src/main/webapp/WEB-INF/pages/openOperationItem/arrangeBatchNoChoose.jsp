<%@ page import="java.util.Date" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
    <meta name="decorator" content="none"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/bootstrap/css/plugins/bootstrap-table/bootstrap-table.min.css"/>

    <!-- 全局的引用 -->
    <script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- jquery的js引用 -->
    <script src="${pageContext.request.contextPath}/jquery/lib/jquery.js" type="text/javascript"></script>
    <!-- 弹出框插件的引用 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"
            type="text/javascript"></script>
    <!-- bootstrap的引用 -->
    <link href="${pageContext.request.contextPath}/bootstrap/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" >
    <script src="${pageContext.request.contextPath}/bootstrap/bootstrap-select/js/bootstrap-select.js"></script>
    <script src="${pageContext.request.contextPath}/js/operation/arrange.js"
            type="text/javascript"></script>

    <link href="${pageContext.request.contextPath}/select2/select2.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/select2/select2-bootstrap4.css" rel="stylesheet">

    <!-- select2的js引用 -->
    <script src="${pageContext.request.contextPath}/select2/select2.full.js" type="text/javascript"></script>

    <script src="${pageContext.request.contextPath}/jquery/jquery.validate.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jquery/messages_zh.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>

    <%--时间插件引用--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/timer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>

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
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <style>
        .chzn-container{
            width: 100%!important;
        }
        .popup_box table th,
        .popup_box table td{
            width:16.6%;
        }
        .fixed-table-toolbar .btn-group>.btn-group:last-child>.btn i, .fixed-table-toolbar .btn-group>.btn-group:last-child>.btn span {
            position: relative;
            top: -6px;
        }
    </style>
    <style type="text/css">
        label {width: 10em;float: left;}
        label.error {float: none;color: red;padding-left: .5em;vertical-align: top;}
        p {clear: both;}
        .submit {margin-left: 12em;}
        .table.table-bordered > tr > td {height: 20px;max-height: 20px;}
        .layui-table-cell {height: 20px  !important;line-height: 20px;}
        .layui-table tr {height: 20px !important;}
        /*.select2-container .select2-container*/
        body{
            font-size: 13px;
        }

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
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsTabGroup-box">
            <div class="TabbedPanelsContent">
                <input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
                <input type="hidden" id="selected_role" value="${sessionScope.selected_role}" />
                <input type="hidden" id="PROJECT_NAME" value="${PROJECT_NAME}" />
                <div style="width:95%;">
                    <input type="button" id="submitButton" name="submitButton" value=" 确定 " class="btn btn-primary btn-lg"
                           style="float:right">
                </div>
                <form autocomplete="off" id="form_operation" name="form_operation" action="${pageContext.request.contextPath}/openOperationItem/saveArrangeData" method="POST">
                    <input id="type" name="type" type="hidden" value="${type}" />
                    <input id="term" name="term" type="hidden" value="${termId}"/>
                    <input id="termId" name="termId" type="hidden" value="${termId}"/>
                    <input id="courseCount" name="courseCount" type="hidden" value="${courseCount}"/>
                    <input id="courseNumber" name="courseNumber" type="hidden" value="${courseNumber}"/>
                    <input id="operId" name="operId" type="hidden" value="${id}"/>
                    <input id="title" name="title" type="hidden" value="${title}"/>
                    <input type="hidden" id="courseCode" name="courseCode" value="code-${user.schoolAcademy.academyNumber}-${maxId}" >
                    <table border="0" align="center" style="width:100%;border-collapse:separate; border-spacing:0px 10px;">
                        <tr style="overflow: hidden">
                            <td align=left width="12%"><h3>学院信息<font color="red"> *</font>：</h3></td>
                            <td width="39%">
                                <select id="academyNumber" name="academyNumber" required>
                                </select>
                                <label for="academyNumber"></label>
                            </td>
                            <td align=left width="12%"><h3>教师信息<font color="red"> *</font>：</h3></td>
                            <td width="39%">
                                <select id="teacher" name="teacher" required>
                                </select>
                                <label for="teacher"></label>
                            </td>
                        </tr>
                        <tr>
                            <td align=left width="12%"><h3>学生名单<font color="red"> *</font>：</h3></td>
                            <td colspan="3">
                                <textarea name="students" id="students"  style="width:95%;word-break: break-all;" rows="10"></textarea>
                                <script type="text/javascript">
                                    var str="";
                                    <c:forEach items="${timetableSelfCourse.timetableCourseStudents}" var="current">
                                    <c:set var="curr" value=""></c:set>
                                    <c:set var="curr" value="\"${curr.concat(current.user.username).concat(';')}\n\""></c:set>
                                    str = str+${curr};
                                    </c:forEach>
                                    document.getElementById("students").value=str;
                                </script>
                                <label for="students"></label><br>
                                <a href="javascript:void(0)" onclick="newStudents();">添加学生</a>
                            </td>

                        </tr>


                        <tr>
                            <td align=left></td>
                            <td>
                            </td>
                        </tr>
                    </table>
                </form>
                <div id="TabbedPanels1" class="TabbedPanels">
                    <div class="site-box">
                        <div class="site-content">
                            <div>
                                <div id="toolbar">
                                    <form class="form-inline">
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
        </div>
    </div>
    <!-- 弹出选择学生窗口 -->
    <div id="newStudents" class="easyui-window" title="选择添加学生" modal="true" dosize="true" maximizable="true" collapsible="true" minimizable="false" closed="true" iconcls="icon-add" style="width:1100px; height:900px;display: none;">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">

                <div class="content-box">
                    <form:form action="" method="post">
                        <fieldset class="introduce-box">
                            <legend>年级信息</legend>
                            <div>
                                <table id="listTable" width="85%" cellpadding="0">
                                    <tr><td><b>选择年级</b></td><tr>
                                    <tr>
                                        <td>
                                            <c:forEach items="${grade }" var="s" varStatus="i">
                                                <c:if test="${s.classGrade>'2010' }">
                                                    <a class='btn btn-common' href='javascript:void(0)' onclick='getSchoolClasses(${s.classGrade})' >${s.classGrade}</a>
                                                </c:if>
                                            </c:forEach></td>
                                    </tr>
                                </table>
                            </div>
                            <div id="schoolClasses"></div>
                            <div id="schoolClassesUser"></div>
                        </fieldset>
                    </form:form>
                </div>
            </div>
            <input type="hidden" id="pageContext" value="${pageContext.request.contextPath }">
        </div>

    </div>
</div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select'           : {search_contains:true},
        '.chzn-select-deselect'  : {allow_single_deselect:true},
        '.chzn-select-no-single' : {disable_search_threshold:10},
        '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
        '.chzn-select-width'     : {width:"95%"}
    };
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>

<!-- 下拉框的js -->
</body>
</html>


