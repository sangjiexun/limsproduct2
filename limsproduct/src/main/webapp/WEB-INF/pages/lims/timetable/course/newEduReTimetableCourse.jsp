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
    <script src="${pageContext.request.contextPath}/js/lims/timetable/course/newEduReTimetableCourse.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>

    <script src="${pageContext.request.contextPath}/js/lims/timetable/course/judgeTimetableConflictByStudent.js"
            type="text/javascript"></script>
    <%--<link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">--%>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="/limsproduct/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="/limsproduct/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <script src="${pageContext.request.contextPath}/js/lims/reservation/lab/optionRule.js"></script>
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <%--<script src="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/external/jquery/jquery.js"></script>--%>
    <script src="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
    <link href="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/jquery-ui.structure.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/jquery-ui.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/jquery-ui.theme.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static_limsproduct/jquery-ui-1.11.4.custom/jquery-ui.structure.css" rel="stylesheet">

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
        .chzn-container-single .chzn-single {
            width:160px;
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
<div style="height:30%;width:90%;margin:0 auto;">
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
</div>
<div style="height:70%;width:90%;margin:0 auto;">
    <form id="form_lab" action="" method="post">
        <input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
        <input type="hidden" id="academyNumber" name="academyNumber" value="${academyNumber}">
        <input type="hidden" id="courseNo" name="courseNo" value="${courseNo}">
        <input type="hidden" id="courseNumber" name="courseNumber" value="${courseNumber}">
        <input type="hidden" id="timetableStyle" name="courseNo" value="${timetableStyle}">
        <input type="hidden" id="groupId" name="groupId" value="${groupId}">
        <input type="hidden" id="term" name="term" value="${term}">
        <br>
        <div style="width:95%;">
            <h3><b>开始排课选择</b></h3>
            软件筛选<input type="checkbox" name="select_check" value="SOFTWARE" onclick="checkSelected()" >
            <input type="button" id="judgeTimetable" name="judgeTimetable" value=" 学生判冲模式 " class="btn btn-primary btn-lg"
                   style="float:right;">
            <input type="button" id="eduReTimetable" name="eduReTimetable" value=" 二次不分批排课 " class="btn btn-primary btn-lg"
                   style="float:right;display: none;">
            <input type="button" id="submitButton" name="submitButton" value=" 确定 " class="btn btn-primary btn-lg"
                   style="float:right;margin-right: 10px;">

        </div>

        <!-- schoolCourseDetail的no -->
        <hr>
        <div id="eduReTimetableCourse">
        <table border="0" align="center" style="width:100%;border-collapse:separate; border-spacing:0px 10px;">
            <tr>
                <td align=left width="12%"><h3>相关计划：</h3></td>
                <td colspan="3">
                    <select id="courseDetailNo" name="courseDetailNo" required>
                        <c:forEach items="${schoolCourse.schoolCourseDetails}" var="curr">
                            <option value ="${curr.courseDetailNo}" >教师：${curr.user.cname}&ensp;&ensp;星期${curr.weekday}[${curr.startClass}-${curr.endClass}]节[${curr.startWeek}-${curr.endWeek}]周</option>
                        </c:forEach>
                    </select>
                    <label for="courseDetailNo"></label>
                </td>
            </tr>
            <tr style="overflow: hidden">
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
                <td align=left width="10%"><h3>星期<font color="red"> *</font>：</h3></td>
                <td width="39%">
                    <select id="weekday" name="weekday" onchange="doValidProperty()"  required>
                        <c:if test="${fn:length(schoolCourse.schoolCourseDetails)>0}">
                            <c:if test="${schoolCourse.schoolCourseDetails.iterator().next().weekday==1}">
                                <option value ="${schoolCourse.schoolCourseDetails.iterator().next().weekday}" selected="selected">星期一</option>
                            </c:if>
                            <c:if test="${schoolCourse.schoolCourseDetails.iterator().next().weekday==2}">
                                <option value ="${schoolCourse.schoolCourseDetails.iterator().next().weekday}" selected="selected">星期二</option>
                            </c:if>
                            <c:if test="${schoolCourse.schoolCourseDetails.iterator().next().weekday==3}">
                                <option value ="${schoolCourse.schoolCourseDetails.iterator().next().weekday}" selected="selected">星期三</option>
                            </c:if>
                            <c:if test="${schoolCourse.schoolCourseDetails.iterator().next().weekday==4}">
                                <option value ="${schoolCourse.schoolCourseDetails.iterator().next().weekday}" selected="selected">星期四</option>
                            </c:if>
                            <c:if test="${schoolCourse.schoolCourseDetails.iterator().next().weekday==5}">
                                <option value ="${schoolCourse.schoolCourseDetails.iterator().next().weekday}" selected="selected">星期五</option>
                            </c:if>
                            <c:if test="${schoolCourse.schoolCourseDetails.iterator().next().weekday==6}">
                                <option value ="${schoolCourse.schoolCourseDetails.iterator().next().weekday}" selected="selected">星期六</option>
                            </c:if>
                            <c:if test="${schoolCourse.schoolCourseDetails.iterator().next().weekday==7}">
                                <option value ="${schoolCourse.schoolCourseDetails.iterator().next().weekday}" selected="selected">星期日</option>
                            </c:if>
                        </c:if>
                    </select>
                    <label for="weekday"></label>
                </td>
                <td align=left width="10%"><h3>选择节次<font color="red"> *</font>：</h3></td>
                <td width="39%">
                    <select id="classes" name="classes" multiple="multiple" onchange="doValidProperty()" required>
                        <%--<c:forEach items="${schoolCourse.schoolCourseDetails}" var="curr">--%>
                        <%--<c:forEach var="curr1" begin="${curr.startClass}" end="${curr.endClass}">--%>
                        <%--<option value ="${curr1}" selected="selected">${curr1}</option>--%>
                        <%--</c:forEach>--%>
                        <%--</c:forEach>--%>

                    </select>
                    <label for="classes"></label>
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
                <td align=left width="12%"><h3>实验项目：</h3></td>
                <td colspan="3">
                    <select id="items" name="items" multiple="multiple">
                    </select>
                    <label for="items"></label>
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
                <td align=left width="12%"><h3>选择周次<font color="red"> *</font>：</h3></td>
                <td colspan="3">
                    <select id="weeks" name="weeks" multiple="multiple" required>
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
                <td align=left></td>
                <td>
                </td>
            </tr>
        </table>
        </div>

    </form>
        <div id="judgeTimetableCourse" style="display: none">
            <form class="layui-form" action="" lay-filter="timetable_tab">
                <input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
                <%--<input type="hidden" id="academyNumber" value="${academyNumber}">--%>
                <input type="hidden" id="courseNo" name="courseNo" value="${courseNo}">
                <input type="hidden" id="term" value="${term}">
                <input type="hidden" id="timetableStyle" value="3">
                <input type="hidden" id="status" value="10">
                <%--<h3>开始直接排1课</h3>--%>
                <%--软件筛选<input type="checkbox" name="select_check" value="SOFTWARE" onclick="checkSelected()" >--%>
                <!-- schoolCourseDetail的no -->
                <%--&emsp;<input type="button" id="submitButton" name="deviceButton" value=" 确定 " class="btn btn-primary"  style="float:right">--%>
                <%--<hr>--%>
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
        </div>


        <hr/>
        <br>

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

