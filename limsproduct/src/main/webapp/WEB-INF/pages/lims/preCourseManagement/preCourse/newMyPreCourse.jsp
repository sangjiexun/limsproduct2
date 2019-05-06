<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <!-- 弹出框插件的引用 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/select2/select2.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/select2/select2-bootstrap4.css" rel="stylesheet">
    <!-- 结束 -->
    <!-- select2的js引用 -->
    <script src="${pageContext.request.contextPath}/select2/select2.full.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"
            type="text/javascript"></script>
    <!-- 结束 -->

    <script type="text/javascript">
        function saveEditForm(){
            //1当前时间已经有课、0没有
            var ishav = 0;
            //星期
            var weekdayArray=getWeekdayArray();
            //周次
            var weekArray=getWeekArray();
            //节次
            var classArray=getClassArray();

            if($("#termId").val()=="" || $("#termId").val()==null){
                alert("请选择学期");
            }else if($("#courseNumber").val()=="" || $("#courseNumber").val()==null){
                alert("请选择课程信息");
            }else if($("#academyNumber").val()=="" || $("#academyNumber").val()==null){
                alert("请选择所属部门");
            }else if($("#stuNumber").val()=="" || $("#stuNumber").val()==null){
                alert("请填写上课人数");
            }else if($("#roomType").val()=="" || $("#roomType").val()==null){
                alert("请选择房间布局类型");
            }else if($("#preSoftwareId").val()=="" || $("#preSoftwareId").val()==null){
                alert("请选择软件");
            }/*else if($("#preLabRoomId").val()=="" || $("#preLabRoomId").val()==null){
                alert("请选择预排课房间");
            }*/ else if (c != 0 || ${isEdit} == 0) {
                if (weekdayArray == "" || weekdayArray == null) {
                    alert("请选择星期");
                } else if (weekArray == "" || weekArray == null) {
                    alert("请选择周次");
                } else if(classArray == "" || classArray == null){
                    alert("请选择节次");
                } else {
                    //判冲
                    $.ajax({
                        url: "${pageContext.request.contextPath}/lims/preCourse/preCoursePunching",
                        dataType: "text",
                        async: false,
                        type: 'POST',
                        data: $('#subForm').serialize(),
                        success: function (result) {
                            if (result == "havTea") {
                                ishav = 1;
                                alert("时间冲突，请重新选择！");
                            }
                        }
                    });
                    if(ishav == 0)
                    {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/lims/preCourse/savePreCourse",
                            type: 'POST',
                            data: $('#subForm').serialize(),
                            complete: function (data) {
                                window.parent.location.reload();
                                window.close();
                            }
                        })
                    }
                }
            }else {
                //判冲
                $.ajax({
                    url: "${pageContext.request.contextPath}/lims/preCourse/preCoursePunching",
                    dataType: "text",
                    async: false,
                    type: 'POST',
                    data: $('#subForm').serialize(),
                    success: function (result) {
                        if (result == "havTea") {
                            ishav = 1;
                            alert("时间冲突，请重新选择！");
                        }
                    }
                });
                if(ishav == 0)
                {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/lims/preCourse/savePreCourse",
                        type: 'POST',
                        data: $('#subForm').serialize(),
                        complete: function (data) {
                            window.parent.location.reload();
                            window.close();
                        }
                    })
                }
            }
        }

        //添加排课时间
        var c = 0;
        function addtime(){
            var data="<tr>"+
                "<td><select id='weekday"+c+"' name='weekday' class='chzn-select'><c:forEach items='${weekdayMap}' var='s'><option value='${s.key}'>${s.value}</option></c:forEach></select></td>"+
                "<td><select id='week"+c+"' name='week"+c+"' class='chzn-select' multiple='multiple'><c:forEach items='${weeksMap}' var='s'><option value='${s.key}'>${s.value}</option></c:forEach></select></td>"+
                "<td><select id='class"+c+"' name='class"+c+"' class='chzn-select' multiple='multiple'><c:forEach items='${classes}' var='s'><option value='${s.id}'>${s.sectionName}</option></c:forEach></select></td>"+
                "<td><a href='javascript:void(0)' onclick='javascript:this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);' >删除</a></td>"+
                "</tr>";
            $("#add_time").append(data);
            c++;
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
            $(".chzn-select").trigger("liszt:updated");

        }

        //获取排课时间星期数组
        function getWeekdayArray(){
            var weekdayArray=new Array();
            for (var i = 0; i < c; i++) {
                if($("#weekday" + i).val() != null && $("#weekday" + i).val() != "") {
                    weekdayArray.push($("#weekday" + i).val()); //将选中的值 添加到 array中
                }else {
                    weekdayArray = null;
                    break;
                }
            }
            return weekdayArray;
        }

        //获取排课时间周次数组
        function getWeekArray(){
            var weekArray=new Array();
            for (var i = 0; i < c; i++) {
                if ($("#week" + i).val() != null && $("#week" + i).val() != "") {
                    weekArray.push($("#week" + i).val()); //将选中的值 添加到 array中
                } else {
                    weekArray = null;
                    break;
                }
            }
            return weekArray;
        }

        //获取排课时间节次数组
        function getClassArray(){
            var classArray=new Array();
            for (var i = 0; i < c; i++) {
                if ($("#class" + i).val() != null && $("#class" + i).val() != "") {
                    classArray.push($("#class" + i).val());//将选中的值 添加到 array中
                } else {
                    classArray = null;
                    break;
                }
            }
            return classArray;
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#tutor').select2({
                width: "89%",
                placeholder: '请输入指导教师...',
                placeholderOption: "first",
                ajax: {
                    url: "${pageContext.request.contextPath}/lims/api/user/apiUserListBySelect",
                    dataType: "json",
                    delay: 250,//延时0.5秒之后再进行请求
                    type: "post",
                    data: function (params) {
                        var query = {
                            search: params.term,
                            userRole: '1'
                        }
                        return query;
                    },
                    results: function (data, page) {
                        return {
                            results: data
                        };
                    }
                }
            });
            $('#teacher').select2({
                width: "89%",
                placeholder: '请输入授课教师...',
                placeholderOption: "first",
                ajax: {
                    url: "${pageContext.request.contextPath}/lims/api/user/apiUserListBySelect",
                    dataType: "json",
                    delay: 250,//延时0.5秒之后再进行请求
                    type: "post",
                    data: function (params) {
                        var query = {
                            search: params.term,
                            userRole: '1'
                        }
                        return query;
                    },
                    results: function (data, page) {
                        return {
                            results: data
                        };
                    }
                }
            });
            $('#courseNumber').select2({
                width: "89%",
                placeholder: '请输入课程信息...',
                placeholderOption: "first",
                ajax: {
                    url: "${pageContext.request.contextPath}/lims/preCourse/apiSchoolCourseInfoBySelect",
                    dataType: "json",
                    delay: 250,//延时0.5秒之后再进行请求
                    type: "post",
                    data: function (params) {
                        var query = {
                            search: params.term,
                            acno: $('#academyNumber').val()
                        }
                        return query;
                    },
                    results: function (data, page) {
                        return {
                            results: data
                        };
                    }
                }
            });
        })
    </script>

</head>

<body>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <form:form id="subForm" name="subForm" action="" method="POST" modelAttribute="preTimetableAppointment">
                        <div class="title">
                            基础信息
                        </div>
                        <div class="new-classroom">
                            <form:hidden path="id"/>
                            <fieldset>
                                <label>学期：<font color="red">*</font></label>
                                <form:select id="termId" path="schoolTerm.id" class="chzn-select">
                                    <c:forEach items="${schoolTerms}" var="current">
                                        <c:if test="${current.id == term.id}">
                                            <form:option value="${current.id}" selected="selected">${current.termName} </form:option>
                                        </c:if>
                                        <c:if test="${current.id != term.id}">
                                            <form:option value="${current.id}">${current.termName} </form:option>
                                        </c:if>
                                    </c:forEach>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>所属部门：<font color="red">*</font></label>
                                <form:select id="academyNumber" path="schoolAcademy.academyNumber" class="chzn-select">
                                    <form:option value="${schoolAcademy.academyNumber}" selected="selected">${schoolAcademy.academyName}</form:option>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>课程信息：<font color="red">*</font></label>
                                <form:select id="courseNumber" path="schoolCourseInfo.courseNumber">
                                    <c:if test="${schoolCourseInfo != null}">
                                        <form:option value="${schoolCourseInfo.courseNumber}"
                                                     selected="selected">${schoolCourseInfo.courseName}（${schoolCourseInfo.courseNumber}） </form:option>
                                    </c:if>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>授课教师：<font color="red">*</font></label>
                                <form:select id="teacher" path="userByTeacher.username">
                                    <c:if test="${currUser != null}">
                                        <form:option value="${currUser.username}"
                                                     selected="selected">${currUser.cname}（学号：${currUser.username}; 学院：${currUser.schoolAcademy.academyName}） </form:option>
                                    </c:if>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>指导教师：</label>
                                <form:select id="tutor" path="userByTutor.username">
                                    <c:if test="${tutor != null}">
                                        <form:option value="${tutor.username}"
                                                     selected="selected">${tutor.cname}（学号：${tutor.username}; 学院：${tutor.schoolAcademy.academyName}） </form:option>
                                    </c:if>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>上课人数：<font color="red">*</font></label>
                                <form:input id="stuNumber" path="stuNumber"/>
                            </fieldset>
                            <fieldset>
                                <label>房间布局类型：<font color="red">*</font></label>
                                <form:select id="roomType" path="preRoomType.id" class="chzn-select">
                                    <form:options items="${preRoomTypes}" itemLabel="roomType" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>需要软件：<font color="red">*</font></label>
                                <form:select id="preSoftwareId" path="preSoftware.id" class="chzn-select">
                                    <form:options items="${preSoftwares}" itemLabel="name" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <%--<fieldset>
                                <label>预排课房间：<font color="red">*</font></label>
                                <select id="preLabRoomId" name="preLabRoomId" class="chzn-select" multiple="multiple">
                                    <c:forEach items="${preLabRooms}" var="s">
                                        <c:forEach items="${preLabRoom}" var="curr">
                                            <c:if test="${curr.id == s.id }">
                                                <option value="${s.id}" selected="selected">${s.roomName}</option>
                                            </c:if>
                                        </c:forEach>
                                            <option value="${s.id}">${s.roomName}</option>
                                    </c:forEach>
                                </select>
                            </fieldset>--%>
                        </div>
                        <div class="title">
                            预排课时间
                        </div>
                        <div class="new-classroom">
                            <fieldset style="width: 90%">
                                <table>
                                    <tr>
                                        <td>预排课时间添加</td>
                                        <td><input class="btn" type="button"onclick="addtime();" value="添加">
                                    </tr>
                                    <table>
                                        <thead>
                                        <tr>
                                            <td>星期</td>
                                            <td>周次</td>
                                            <td>节次</td>
                                            <td>操作</td>
                                        </tr>
                                        </thead>
                                        <tbody id="add_time">
                                        <c:if test="${preTimetableSchedules != null}">
                                            <c:forEach items="${preTimetableSchedules}" var="p">
                                                <tr>
                                                    <td>星期${p.startWday}</td>
                                                    <td>${p.startWeek}-${p.endWeek}周</td>
                                                    <td>${p.startClass}-${p.endClass}节</td>
                                                    <td><a href="${pageContext.request.contextPath}/lims/preCourse/deletePreTimetableSchedule?id=${p.id}">删除</a> </td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>

                                        </tbody>
                                    </table>
                                </table>
                            </fieldset>
                        </div>
                    </form:form>
                    <div class="moudle_footer">
                        <div class="submit_link">
                            <input class="btn btn-big" type="submit" value="保存" onclick="saveEditForm();">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 下拉框的js -->
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

