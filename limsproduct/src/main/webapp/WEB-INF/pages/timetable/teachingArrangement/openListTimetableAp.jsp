<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<head>
    <meta name="decorator" content="iframe"/>
    <link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css"/>

    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->

    <script type="text/javascript">
        function licence(courseCode) {
            var classesArray = [];
            $($("#classes option:selected")).each(function () {
                classesArray.push(this.value);
            });
            var weeksArray = [];
            $($("#weeks option:selected")).each(function () {
                weeksArray.push(this.value);
            });
            var teachersArray = [];
            $($("#teachers option:selected")).each(function () {
                teachersArray.push(this.value);
            });
            $.ajax({
                url: "${pageContext.request.contextPath}/timetable/isTimetableApNoSoftWare",
                dataType: "html",
                type: 'POST',
                data: {
                    classes: classesArray.join(","),
                    weeks: $("#weeks").val(),
                    teachers: teachersArray.join(","),
                    weekday: $("#weekday").val(),
                    term:${schoolCourseDetail.schoolTerm.id},
                    courseCode: courseCode
                },
                success: function (resultt) {

                    if (resultt == "false") {
                        alert("时间冲突，请重新选择时间！");
                        $("#weekday").attr('value', "");
                        $("#weekday").trigger("liszt:updated");
                        $("#weeks").attr('value', "");
                        $("#weeks").trigger("liszt:updated");
                        $("#classes").attr('value', "");
                        $("#classes").trigger("liszt:updated");

                    }

                }
            });
        }

        function selectoperationitmes() {
            var ope;
            ope = $('#operationitems').val();
            if (ope != 0) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/timetable/selfTimetable/getSoftWare?operationitems=" + ope,
                    type: 'GET',
                    dataType: "json",
                    success: function (json) {
                        var software = "";
                        var softwarename = [];
                        $.each(json, function (key, values) {
                            softwarename.push(key);
                        });

                        for (var i = 0; i < softwarename.length; i++) {
                            $("#softwares option[value='" + softwarename[i] + "']").attr("selected", "selected");
                        }
                        $("#softwares").chosen();
                        $("#softwares").trigger("liszt:updated");
                    },
                    error: function () {
                        alert("软件选择异常");
                    }
                });

            }
        }

        //得到courseDetail的节次和周次信息
        var startWeek = 1;//开始周的全局变量
        var endWeek = 19;//结束周的全局变量
        var canSubmit = true;

        function checkForm() {
            if($("#labRooms").val()==""||$("#labRooms").val()==null){
                alert("请选择实验室")
            }
            else if($("#classes").val()==""||$("#classes").val()==null){
                alert("请选择节次")
            }else if($("#weeks").val()==""||$("#weeks").val()==null){
                alert("请选择周次")
            }else if($("#teachers").val()==""||$("#teachers").val()==null){
                alert("请选择上课教师")
            }else{
                if (canSubmit==false){
                    alert("请等待本次进度修改")
                    return;
                }
                canSubmit=false;
                document.form.submit();
            }
        }
        //flag提示是否处理调课完成0不提示，1提示
        var flag =${flag};
        if (flag == 1) {
            var sUrl = "${pageContext.request.contextPath}/teachingArrangement/doJudgeTimetableOk?courseCode=${schoolCourseDetailMap.get(0).schoolCourse.courseCode}&currpage=1";
        }
    </script>

    <script type="text/javascript">
        var tableAppId =${tableAppId};//定义本条排课记录id的全局变量
        var buttonflag = 0;

        function showlabroom(coursecode) {
            if (buttonflag == 0) {
                var selfCourseId =${timetableSelfCourse.id};
                var softwareArray = [];
                $($("#softwares option:selected")).each(function () {
                    softwareArray.push(this.value);
                });
                var confinementTime =${confinementTime};
                var classesArray = [];
                $($("#classes option:selected")).each(function () {
                    classesArray.push(this.value);
                });
                // 从后台获取空闲的实验室
                $.ajax({
                    url: "${pageContext.request.contextPath}/timetable/vocationalTimetable/getValidLabRoomsForSkill",
                    dataType: "json",
                    type: 'GET',
                    data: {
                        selfCourseId: selfCourseId,
                        classes: classesArray.join(","),
                        weeks: $("#weeks").val(),
                        weekday: $("#weekday").val(),
                        coursecode: coursecode,
                        softwares: softwareArray.join(","),
                        confinementTime: confinementTime
                    },
                    complete: function (result) {
                        buttonflag = 1;
                        var obj = eval(result.responseText);
                        var result1, result2, result3;
                        for (var i = 0; i < obj.length; i++) {
                            //result2 = result2 + "<option value='" +obj[i].id + "'>" + obj[i].value + "</option>";
                            if (obj[i].status == 1) {
                                result1 += "<tr><td><input type='radio' name='labRooms1' value='" + obj[i].id + "' /></td>";
                                result1 += "<td>" + obj[i].value + "</td>";
                                result1 += "<td>" + obj[i].number + "</td>";
                                result1 += "</tr>";
                            }
                            else if (obj[i].status == 2) {
                                result2 += "<tr><td><input type='radio' name='labRooms2' value='" + obj[i].id + "' /></td>";
                                result2 += "<td>" + obj[i].value + "</td>";
                                result2 += "<td>" + obj[i].number + "</td>";
                                result2 += "</tr>";
                            }
                        }
                        ;
                        $("#labRooms1 tbody").append(result1);
                        $("#labRooms2 tbody").append(result2);
                        $(".labDiv").css("display", "block");
                    }
                });
            } else {
                alert("已生成请勿重复点击！")
            }
        }


        function editAp(id, coursecode) {
            var path = "${pageContext.request.contextPath}/timetable/editTimetableAp?id=" + id + "&coursecode=" + coursecode;
            var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="' + path + '" style="width:100%;height:100%;"></iframe>'
            $('#doApjust').html(con);
            //获取当前屏幕的绝对位置
            var topPos = window.pageYOffset;
            //使得弹出框在屏幕顶端可见
            $('#doApjust').window({left: "0px", top: topPos + "px"});
            $('#doApjust').window('open');
        }
    </script>

    <style type="text/css">
        .labDiv {
            display: none;
        }
    </style>
</head>

<div class="iStyle_RightInner">
    <div class="navigation">
        <div id="navigation">
            <ul>
                <li><a href="javascript:void(0)">排课管理</a></li>
                <li class="end">进度安排</li>
            </ul>
        </div>
    </div>
    <!-- 课程进度编辑 -->
    <div id="doApjust" class="easyui-window" title="课程进度" closed="true" modal="true" iconCls="icon-add"
         style="width:1000px;height:600px"></div>
    <div class="right-content">
        <div id="TabbedPanels1" class="TabbedPanels">
            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">
                    <div class="content-box">
                        <div class="title">课程信息</div>
                        <table>
                            <thead>
                            <tr>
                                <th>所属学期</th>
                                <th>课程名称</th>
                                <th>班级</th>
                                <th>教师</th>
                            </tr>
                            </thead>
                            <tbody>
                            <td>${schoolCourseDetail.schoolTerm.termName}</td>
                            <td>${schoolCourseDetail.courseName}</td>
                            <td>${schoolCourseDetail.courseClassName}</td>
                            <td>${schoolCourseDetail.user.cname}</td>
                            </tbody>
                        </table>
                        <br>

                        <div class="title">已批准课题</div>
                        <table>
                            <thead>
                            <tr>

                                <th><spring:message code="all.training.name" />课题</th>
                                <th>星期</th>
                                <th width="90px;" colspan=2>
                                    <table>
                                        <tr>
                                            <td width="20px;">节次</td>
                                            <td width="50px;">周次</td>
                                        </tr>
                                    </table>
                                </th>
                                <th>授课教师</th>
                                <th><spring:message code="all.trainingRoom.labroom"/></th>
                                <th>软件</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${timetableAppointmentMap}" var="current" varStatus="i">
                                <tr>
                                    <td>
                                        <c:forEach var="entry" items="${current.timetableItemRelateds}">
                                            <c:if test="${empty entry.operationItem||entry.operationItem.id==0}">
                                                ${schoolCourseDetail.courseName}(课程名称)
                                            </c:if>
                                            <c:if test="${not empty entry.operationItem&&entry.operationItem.id!=0}">
                                                <c:out value="${entry.operationItem.lpName}"/>&nbsp;
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${current.timetableItemRelateds.size()==0}">
                                            ${schoolCourseDetail.courseName}(课程名称)
                                        </c:if>
                                    </td>
                                    <td>${current.weekday}</td>

                                    <td colspan=2>
                                        <table>
                                            <c:if test="${current.timetableAppointmentSameNumbers.size()==0}">
                                                <tr>
                                                    <td width="20px;">
                                                        <c:if test="${current.startClass==current.endClass}">
                                                            ${current.startClass }
                                                        </c:if>
                                                        <c:if test="${current.startClass!=current.endClass}">
                                                            ${current.startClass }-${current.endClass }
                                                        </c:if>
                                                    </td>
                                                    <td width="50px;">
                                                        <c:if test="${current.startWeek==current.endWeek}">
                                                            ${current.startWeek }
                                                        </c:if>
                                                        <c:if test="${current.startWeek!=current.endWeek}">
                                                            ${current.startWeek }-${current.endWeek }
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${current.timetableAppointmentSameNumbers.size()>0}">

                                                <c:set var="sameStart" value="-"></c:set>
                                                <c:forEach var="entry"
                                                           items="${current.timetableAppointmentSameNumbers}"
                                                           varStatus="p">
                                                    <c:set var="v_param" value="-${entry.startClass}-"></c:set>
                                                    <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                                        <tr>
                                                            <td width="20px;">
                                                                <c:if test="${entry.startClass==entry.endClass}">
                                                                    ${entry.startClass }
                                                                </c:if>
                                                                <c:if test="${entry.startClass!=entry.endClass}">
                                                                    ${entry.startClass }-${entry.endClass }
                                                                </c:if>
                                                            </td>
                                                            <td width="50px;">
                                                                <c:set var="sameStart"
                                                                       value="${sameStart}-${entry.startClass }-"></c:set>
                                                                <c:forEach var="entry1"
                                                                           items="${current.timetableAppointmentSameNumbers}"
                                                                           varStatus="q">
                                                                    <c:if test="${entry.startClass==entry1.startClass}">
                                                                        <%-- <td>
                                                                        [${entry1.startClass }-${entry1.endClass }]
                                                                        </td> --%>
                                                                        <c:if test="${entry1.startWeek==entry1.endWeek}">
                                                                            ${entry1.startWeek }
                                                                        </c:if>
                                                                        <c:if test="${entry1.startWeek!=entry1.endWeek}">
                                                                            ${entry1.startWeek }-${entry1.endWeek }
                                                                        </c:if>

                                                                    </c:if>
                                                                </c:forEach>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                </c:forEach>

                                            </c:if>
                                        </table>

                                        <!--上课教师  -->
                                    <td>
                                        <c:forEach var="entry" items="${current.timetableTeacherRelateds}">
                                            <c:out value="${entry.user.cname}"/>
                                        </c:forEach>
                                    </td>

                                    <c:if test="${selected_labCenter ne 12 }"><!-- 非纺织中心 -->
                                    <td>
                                        <c:forEach var="entry" items="${current.timetableLabRelateds}">
                                            <c:out value="${entry.labRoom.labRoomName}"/>
                                        </c:forEach>
                                    </td>
                                    </c:if>

                                    <td>
                                        <c:forEach var="cur" items="${software}">
                                            <c:if test="${cur.key==current.id}">
                                                <c:out value="${cur.value}"/>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <a href='${pageContext.request.contextPath}/teachingArrangement/deleteTimeTableAppointment?id=${current.id }&courseCode=${current.courseCode}'>删除</a>
                                    </td>
                                </tr>

                            </c:forEach>
                            </tbody>
                        </table>
                        <br>

                        <div class="title">排课</div>
                        <c:if test="${tableAppId eq 0 }">
                        <form name="form" method="Post"
                              action="${pageContext.request.contextPath}/teachingArrangement/saveTimetableAp?currpage=${currpage}">
                            </c:if>
                            <c:if test="${tableAppId ne 0  }">
                            <form name="form" method="Post"
                                  action="${pageContext.request.contextPath}/timetable/saveAdminTimetable"
                                  target=_parent>
                                </c:if>
                                <table>
                                    <thead>
                                    <tr>
                                        <th>选择<spring:message code="all.training.name"/>课题</th>
                                        <th>选择周次</th>
                                        <th>选择星期</th>
                                        <th>选择节次</th>
                                        <th>选择教师</th>
                                        <th>选择软件</th>
                                        <th>选择实验室</th>


                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <select class="chzn-select" data-placeholder='请选择' name="operationitems"
                                                    id="operationitems" style="width:180px" required="true"
                                                    onchange="selectoperationitmes()">
                                                <option value="">请选择</option>
                                                <c:forEach items="${operationitems}" var="current">

                                                    <option value="${current.id}"> ${current.lpName}</option>

                                                </c:forEach>
                                                <option value="0">${schoolCourseDetail.courseName}</option>
                                            </select>
                                        </td>

                                        <td>
                                            <select class="chzn-select" data-placeholder='请选择排课周次' name="weeks"
                                                    id="weeks" style="width:180px" required="true">
                                                <option value="">请选择</option>
                                                <c:forEach items="${schoolweek}" var="current">
                                                    <option value="${current}"> ${current}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="chzn-select" name="weekday" id="weekday" style="width:120px">
                                                <!--如果星期重复，则去重  -->
                                                <option value="">请选择</option>
                                                <option value="1">周一</option>
                                                <option value="2">周二</option>
                                                <option value="3">周三</option>
                                                <option value="4">周四</option>
                                                <option value="5">周五</option>
                                                <option value="6">周六</option>
                                                <option value="7">周日</option>
                                            </select>
                                        </td>

                                        <td>
                                            <select class="chzn-select" multiple="multiple" data-placeholder='请选择节次'
                                                    name="classes" id="classes" style="width:150px" required="true">
                                                <option value="">请选择</option>
                                                <option value="1">第一节</option>
                                                <option value="2">第二节</option>
                                                <option value="3">第三节</option>
                                                <option value="4">第四节</option>
                                                <option value="5">第五节</option>
                                                <option value="6">第六节</option>
                                                <option value="7">第七节</option>
                                                <option value="8">第八节</option>
                                                <option value="9">第九节</option>
                                                <option value="10">第十节</option>
                                                <option value="11">第十一节</option>
                                                <option value="12">第十二节</option>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="chzn-select" multiple="multiple" data-placeholder='请选择授课教师'
                                                    name="teachers" id="teachers" style="width:100px" required="true">
                                                <c:forEach items="${timetableTearcherMap}" var="current" varStatus="i">
                                                    <c:if test="${current.key == courseCodes.userByTeacher.username}">
                                                        <option value="${current.key}"
                                                                selected> ${current.value}</option>
                                                    </c:if>
                                                    <c:if test="${current.key != courseCodes.userByTeacher.username }">
                                                        <option value="${current.key}"> ${current.value}</option>
                                                    </c:if>
                                                </c:forEach>
                                                <c:forEach items="${timetableMap}" var="cur" varStatus="i">
                                                    <option value="${cur.key}"> ${cur.value}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="chzn-select" multiple="multiple" data-placeholder='请选软件'
                                                    name="softwares" id="softwares" style="width:100px" required="true"
                                                    onchange="licence('${courseCode}')">
                                                <c:forEach items="${softwareMap}" var="current">
                                                    <option value="${current.key}"> ${current.value}</option>
                                                </c:forEach>
                                            </select>
                                        </td>

                                        <td>
                                            <input type="button" name="labRooms" id="labRooms"
                                                   onclick="showlabroom('${courseCode}')" value="生成可选实验室">
                                        </td>
                                    </tr>


                                    <tr>
                                        <td colspan=6>
                                            <input type="hidden" name="courseDetailNo"
                                                   value="${schoolCourseDetailMap.get(0).courseDetailNo}">
                                            <input type="hidden" name="courseCode" value="${courseCode}">
                                            <input type="hidden" name="currpage" value="${currpage}">

                                            <!--传递修改的排课表主键  -->
                                            <input type="hidden" value="${tableAppId}" name="id">
                                            <a style="float:right;width:44px;height:26px;" onclick="checkForm()">保存</a>
                                        </td>

                                        <c:if test="${timetableAppointmentMap.size()>0}">
                                            <td align="right">
                                                <a class="btn btn-new"
                                                   href="${pageContext.request.contextPath}/teachingArrangement/doJudgeTimetableOk?courseCode=${schoolCourseDetailMap.get(0).schoolCourse.courseCode}&currpage=1"
                                                   target=_parent><font color=red>提交</font></a>
                                            </td>
                                        </c:if>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="labDiv">
                                    <div class="title" style="width:50%;float:left;padding:0;text-align:center;">
                                        完全匹配的实验室
                                    </div>
                                    <div class="title" style="width:50%;float:left;padding:0;text-align:center;">
                                        容量不足的实验室
                                    </div>
                                    <table style="width:49%;float:left;margin-right:1%;" id="labRooms1">

                                        <thead>
                                        <tr>
                                            <td></td>
                                            <td>实验室</td>
                                            <td>实验室容量</td>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                    <table style="width:49%;float:left" id="labRooms2">

                                        <thead>
                                        <tr>
                                            <td></td>
                                            <td>实验室</td>
                                            <td>实验室容量</td>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<hr>

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
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }

</script>
<!-- 下拉框的js -->

