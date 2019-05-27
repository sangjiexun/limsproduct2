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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
    <!-- jquery的js引用 -->
    <script src="${pageContext.request.contextPath}/jquery/lib/jquery.js" type="text/javascript"></script>
    <!-- select2的js引用 -->
    <script src="${pageContext.request.contextPath}/select2/select2.full.js"></script>
    <!-- jquery的页面验证 -->
    <script src="${pageContext.request.contextPath}/jquery/jquery.validate.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jquery/messages_zh.js" type="text/javascript"></script>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="/limsproduct/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="/limsproduct/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <!-- 页面业务的js引用 -->
    <script src="${pageContext.request.contextPath}/js/lims/timetable/course/judgeTimetableConflictByStudent.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
    <script src="${pageContext.request.contextPath}/js/lims/reservation/lab/optionRule.js"></script>
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <script src="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/external/jquery/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
    <link href="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/jquery-ui.structure.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/jquery-ui.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/jquery-ui.theme.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/jquery-ui.structure.css" rel="stylesheet">
    <style type="text/css">
        label { width: 10em; float: left; }
        label.error { float: none; color: red; padding-left: .5em; vertical-align: top; }
        p { clear: both; }
        .submit { margin-left: 12em; }
        .layui-btn{
            box-sizing: border-box;
            height: 24px;
            line-height: 23px;
            border-radius: 3px;
            font-size: 12px;
            padding: 0 7px;
            margin: 0;
            background: #409eff;
            color: #fff;
            font-weight: normal;
            border: none;
        }
        .layui-btn-danger{
            background-color: #f56c6c;
        }
        .layui-form-checkbox{
            height: 24px;
            line-height: 23px;
            padding-right: 25px;
            margin-bottom: 5px;
        }
        .layui-form-checkbox span {
            padding: 0 7px;
            font-size: 12px;
        }
        .layui-form-checkbox i {
            height: 24px;
        }
        .layui-form-checked span,.layui-form-checked span:hover, .layui-form-checked:hover span{
            background-color: #409eff;
        }
        .layui-form-checked i, .layui-form-checked:hover i {
            color: #409eff;
        }
        caption {
            text-align: center;
            caption-side: top;
        }
        .tab_stu thead tr th, .tab_stu td {
            padding: 7px 7px;
        }
        .tab_stu th {
            background: #fafafa;
        }
        .tab_stu th, .tab_stu td {
            border: 1px solid #e4e5e7!important;
        }
        .lab_stu thead tr th, .lab_stu td {
            padding: 7px 7px;
        }
        .lab_stu th {
            background: #fafafa;
        }
        .lab_stu th, .lab_stu td {
            border: 1px solid #e4e5e7!important;
        }
        #tab_stu {
            moz-user-select: -moz-none;
            -moz-user-select: none;
            -o-user-select:none;
            -khtml-user-select:none;
            -webkit-user-select:none;
            -ms-user-select:none;
            user-select:none;
            margin: auto;
        }
        #lab_stu {
            moz-user-select: -moz-none;
            -moz-user-select: none;
            -o-user-select:none;
            -khtml-user-select:none;
            -webkit-user-select:none;
            -ms-user-select:none;
            user-select:none;
            margin: auto;
        }
        .tab_stu {
            margin-left: 5%;
            margin-bottom: 50px;
            margin-right: 5%;
            width: 70%;
        }
        .lab_stu {
            margin-left: 5%;
            margin-bottom: 50px;
            margin-right: 5%;
            width: 70%;
        }
        #table_student1,#table_student2 {
            text-align: center;
        }
        #feedback { font-size: 1.4em; }
        /*#tab_stu .check_box .ui-selecting:not(.not_check) { background: #FECA40; }*/
        #tab_stu .ui-selecting:not(.not_check) { background: #409effcc; }
        /*#tab_stu .check_box .ui-selected:not(.not_check) { background: #F39814; color: white; }*/
        /*#tab_stu .check_box .ui-selected:not(.not_check) { background: #F39814; color: white; }*/
        #tab_stu .ui-selected:not(.not_check){ background: #409eff; color: white; }
        #tab_stu .ui-selected:not(.not_check) span{ color: white; }
        /*#tab_stu { list-style-type: none; margin: 0; padding: 0; width: 450px; }*/
        /*#tab_stu td { margin: 3px; padding: 1px; float: left; width: 100px; height: 80px; font-size: 4em; text-align: center; }*/
    </style>

</head>

<body>
<br>

<form class="layui-form" action="" lay-filter="timetable_tab">
    <input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
    <%--<input type="hidden" id="academyNumber" value="${academyNumber}">--%>
    <input type="hidden" id="courseNo" name="courseNo" value="${courseNo}">
    <input type="hidden" id="term" value="${term}">
    <h3>开始直接排1课</h3>
    <%--软件筛选<input type="checkbox" name="select_check" value="SOFTWARE" onclick="checkSelected()" >--%>
    <!-- schoolCourseDetail的no -->
    <%--&emsp;<input type="button" id="submitButton" name="deviceButton" value=" 确定 " class="btn btn-primary"  style="float:right">--%>
    <hr>
    <br>
    <table border="0" align="center" style="width:100%;">
        <tr>
            <td align=left width="12%"><h3>选择节次：</h3></td>
            <td colspan="3">
                <div class="layui-input-block" style="margin: 0">
                    <div class="layui-form" style="color:red">
                        <div class="layui-btn layui-btn-sm" id="section_all">全选</div>
                        <div class="layui-btn layui-btn-sm" id="section_opposite">反选</div>
                        <div class="layui-btn layui-btn-sm layui-btn-danger" id="section_none">全不选</div>
                        <%--<b>（注意：请先选择节次，节次确定后再选周次；选择节次过程中，周次选择会被重置！）</b>--%>
                    </div>
                    <div id="section_box" class="layui-form" style="margin:10px auto 0">
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td align=left width="12%"><h3>选择周次：</h3></td>
            <td colspan="3">
                <div class="layui-input-block" style="margin: 0">
                    <div class="layui-form">
                        <div class="layui-btn layui-btn-sm" id="week_all">全选</div>
                        <div class="layui-btn layui-btn-sm" id="week_opposite">反选</div>
                        <div class="layui-btn layui-btn-sm layui-btn-danger" id="week_none">全不选</div>
                    </div>
                    <div id="week_box" class="layui-form" style="margin:10px auto 0">

                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td align=left width="12%"><h3>选择星期：</h3></td>
            <td colspan="3">
                <div class="layui-input-block" style="margin: 0">
                    <div class="layui-form">
                        <div class="layui-btn layui-btn-sm" id="weekday_all">全选</div>
                        <div class="layui-btn layui-btn-sm" id="weekday_opposite">反选</div>
                        <div class="layui-btn layui-btn-sm layui-btn-danger" id="weekday_none">全不选</div>
                    </div>
                    <div id="weekday_box" class="layui-form" style="margin:10px auto 0">

                    </div>
                </div>
            </td>
        </tr>
    </table>
    <div class="layui-form-item" style="float: right;">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="timetableSubmit">查看判冲结果</button>
            <%--<button type="reset" class="layui-btn layui-btn-primary">重置</button>--%>
        </div>
    </div>
    <hr/>
</form>
<div id="table_student1" style="">

</div>
<hr/>
<div id="table_student2" style="">

</div>

<!-- 下拉框的js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.searchableSelect.js"></script>
<link href="${pageContext.request.contextPath}/css/jquery.searchableSelect.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select': {search_contains : true},
        '.chzn-select-deselect'  : {allow_single_deselect:true},
        '.chzn-select-no-single' : {disable_search_threshold:10},
        '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
        '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>

