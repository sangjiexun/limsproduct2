<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html>
<head>
    <meta name="decorator" content="iframe"/>
    <title><fmt:message key="html.title"/></title>
    <!-- <meta name="decorator" content="iframe"/> -->

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

    <!-- 下拉框的样式 -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->

    <!-- 文件上传的样式和js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>

    <script type="text/javascript">
        $(function () {
            $("#Pop_content").window({
                top: ($(window).width() - 800) * 0.5,
                left: ($(window).width() - 1000) * 0.5
            })

            $(".listTable").css('height', 600);

            $("#courseName").change(function () {
                var courseName = $("#courseName").find("option:selected").text();
                $("#schoolCourseInfo").attr("value", courseName);
            });

            $("#schoolMajorsa").change(function () {
                var schoolMajorsa = $("#schoolMajorsa").find("option:selected").text();
                $("#lpMajorFit").attr("value", schoolMajorsa);
            });

            $("#searchterm").change(function () {
                var searchterm = $("#searchterm").find("option:selected").text();
                $("#schoolTerm").attr("value", searchterm);
            });
        })

        function newwindor() {

            $("#Pop_content").show();
            //获取当前屏幕的绝对位置
            var topPos = window.pageYOffset;
            //使得弹出框在屏幕顶端可见
            $('#Pop_content').window({left: "20%", top: "38%", height: 400});
            $("#Pop_content").window('open');

            var nameop = "";
            $.ajax({
                url: "${pageContext.request.contextPath}/teaching/coursesite/getitem",
                data: {nameop: nameop},
                type: "POST",
                success: function (data) {
                    $("#npo").html("");
                    $("#npo").append(data);
                    //  $("#npo").replace(data);

                }
            });


        }

        function addproject() {
            $("#projectitrms").attr("value", "");

            var projectitems = "";
            var c = document.getElementById("Pop_content").getElementsByTagName("input");
            for (var i = 0; i < c.length; i++) {
                if (c[i].type == "checkbox" && c[i].checked) {
                    projectitems += c[i].value + ",";
                }
            }

            $.post('${pageContext.request.contextPath}/teaching/coursesite/getuserprojectitems', {projectitems: projectitems}, function (data) {  //serialize()序列化
                $("#ds").after(data);
            });

            $("#projectitrms").attr("value", projectitems);
            alert("添加成功！");
            $('#Pop_content').window('close');
        }


        $(function () {
            $("#userSubmit").click(function () {
                var nameop = $("#nameop").val();
                $.ajax({
                    url: "${pageContext.request.contextPath}/teaching/coursesite/getitem",
                    data: {nameop: nameop},
                    type: "POST",
                    success: function (data) {
                        $("#npo").html("");
                        $("#npo").append(data);

                    }
                });

            });
        });

        function sunb() {

            var jie = [];
            $("#commencementnaturemap option:selected").each(function () {
                jie.push(this.value);
            });
            var sss = [];

            if (jie.length == 0) {
                alert("请选择课程性质！");
                return false;
            }
            ;

            var arrangement = document.getElementsByName("arrangement");
            if (arrangement != null) {
                if (arrangement.length > 0) {
                    if ($('#cname').val() == null || $('#cname').val() == "" || $('#content').val() == null || $('#content').val() == "") {
                        alert("请填写内容");
                        return false;
                    }
                }

            }

//判断教学课时
            var theoryCourseHour = $("#theoryCourseHour").val();
            var experimentCourseHour = $("#experimentCourseHour").val();
            if (theoryCourseHour == "" || experimentCourseHour == "") {
                alert("请填写课时");
                return false;

            }

        }

        function del(s) {
            $("#" + s + "").remove();
            var d = $("#projectitrms").val();
            var a = d.replace(s + ",", "");
            $("#projectitrms").attr("value", "");
            $("#projectitrms").attr("value", a);
        }
    </script>
    <script type="text/javascript">
        function uploaddocment() {

            //获取当前屏幕的绝对位置
            var topPos = window.pageYOffset;
            //使得弹出框在屏幕顶端可见
            $('#searchFile').window({left: "0px", top: topPos + "px"});
            $('#searchFile').window('open');

            $('#file_upload').uploadify({
                'formData': {id: 1},    //传值
                'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',
                'uploader': '${pageContext.request.contextPath}/teaching/coursesite/uploaddnolinedocment;jsessionid=<%=session.getId()%>',		//提交的controller和要在火狐下使用必须要加的id
                buttonText: '选择文件',
                'onUploadSuccess': function (file, data, response) {

                    $("#doc").append(data);
                },
                onQueueComplete: function (data) {
                    var ss = "";

                    $("tr[id*='s_']").each(function () {
                        var eval1 = $(this).attr("id");
                        var str1 = eval1.substr(eval1.indexOf("_") + 1, eval1.lenght);
                        var vals1 = str1 + "_" + $(this).attr("value");

                        ss += str1 + ",";
                    });

                    $("#docment").attr("value", ss);
                    $('#searchFile').window('close');
                }
            });

        }

        function delectuploaddocment(s) {
            if (confirm('你真的要删除吗？ ') == false) {
                return false;
            } else {
                $.post('${pageContext.request.contextPath}/teaching/coursesite/delectdnolinedocment?idkey=' + s + '', function (data) {  //serialize()序列化
                    if (data == "ok") {
                        $("#s_" + s + "").empty();

                    }

                });
            }
        }


        //拼凑HTML，添加课程内容
        //count记录当前共有几条进度安排
        var count =${fn:length(operationOutlineCourses)};
        //newItemCount记录新增项目时须将项目添加到第几条
        var newItemCount = 0;

        function addcourse() {
            var courseName = $("#courseName").val();
            var schoolMajorsa = $("#schoolMajorsa").val();
            var searchterm = $("#searchterm").val();
            if (courseName == null || courseName == "" || schoolMajorsa == null || schoolMajorsa == "" || searchterm == null || searchterm == "") {
                alert("请先选择课程、面向专业和所属学期");
                return;
            }
            count = count + 1;
            $.ajax({
                url: "${pageContext.request.contextPath}/teaching/coursesite/getcourse?count=" + count,
                type: 'GET',
                dataType: "html",
                success: function (json) {
                    $("#two_file").append(json);
                },
                error: function () {
                    alert("课程添加异常");
                }
            });
        }

        //选择理论则手动输入内容，选择实验等则选择实验项目
        function showItems(courseCount) {
            //选取课程性质
            var cname = $("#cou" + courseCount).find("#cname").find("option:selected").text();
            //var cname =$("#cou"+courseCount).children("td").eq(1).find("#cname").find("option:selected").text();
            //选取课程编号
            var courseName = $("#courseName").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/teaching/coursesite/showItems",
                type: "POST",
                dataType: "html",
                data: {cname: cname, courseName: courseName, courseCount: courseCount},
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: function (json) {
                    $("#cou" + courseCount).children("td").eq(2).empty();
                    $("#cou" + courseCount).children("td").eq(2).append(json);
                },
                error: function () {
                    alert("课程添加异常");
                }
            });

        }

        function createNewItem(courseCount) {
            //更新学期内容
            var searchterm = $("#searchterm").find("option:selected").text();
            $("#schoolTerm").attr("value", searchterm);
            var contentItem = $("#cou" + courseCount).find("#content").find("option:selected").val();
            var topPos = window.pageYOffset;
            if (contentItem == -1) {
                //确定是第几条新增的项目
                newItemCount = courseCount;
                //使得弹出框在屏幕顶端可见
                $('#newOperationItem').window({left: "20px", top: topPos + "px"});
                $("#newOperationItem").window('open');
            }
        }

        function checkRequired() {
            if ($("#lpName").val() == "") {
                alert("请填写课题名称!");
                return;
            }
            if ($("#lpDepartmentHours").val() == "") {
                alert("请填写实验学时!");
                return;
            }
            if ($("#schoolCourseInfo").val() == "") {
                alert("请选择所属课程!");
                return;
            }
            if ($("#lpMajorFit").val() == "") {
                alert("请选择面向专业!");
                return;
            }
            if ($("#schoolTerm").val() == "") {
                alert("请选择学期!");
                return;
            }
            var lpName = $("#lpName").val();
            var lpDepartmentHours = $("#lpDepartmentHours").val();
            var schoolCourseInfo = $("#courseName").find("option:selected").val();
            var lpMajorFit = $("#schoolMajorsa").find("option:selected").val();
            var searchTerm = $("#searchterm").find("option:selected").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/operation/saveOperationItemFromNewOperation",
                type: "POST",
                data: {
                    lpName: lpName,
                    lpDepartmentHours: lpDepartmentHours,
                    schoolCourseInfo: schoolCourseInfo,
                    lpMajorFit: lpMajorFit,
                    searchTerm: searchTerm
                },
                success: function (data) {
                    var couList = $("tr[id^=cou]");
                    $.each(couList, function (i, e) {
                        if (e.id == "cou" + newItemCount) {
                            $("<option value =" + data.operationItemLpName + " selected>" + data.operationItemLpName + "</option>").insertBefore($(this).children("td").eq(2).find("#content").children(":last-child"));
                        } else {
                            $("<option value =" + data.operationItemLpName + ">" + data.operationItemLpName + "</option>").insertBefore($(this).children("td").eq(2).find("#content").children(":last-child"));
                        }
                    });
                },
                error: function () {
                    alert("新建错误");
                }
            });
            $("#newOperationItem").window('close');
        }

        //修改课程内容
        function editcourse(id, courseCount) {
            //更新数据至新建项目表格
            var courseName = $("#courseName").find("option:selected").text();
            $("#schoolCourseInfo").attr("value", courseName);
            var schoolMajorsa = $("#schoolMajorsa").find("option:selected").text();
            $("#lpMajorFit").attr("value", schoolMajorsa);
            var searchterm = $("#searchterm").find("option:selected").text();
            $("#schoolTerm").attr("value", searchterm);
            //获取课程名称
            var courseName = $("#courseName").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/teaching/coursesite/editcourse?courseCount=" + courseCount + "&id=" + id + "&courseName=" + courseName,
                type: 'GET',
                dataType: "html",
                success: function (json) {
                    $("tr[id=cou" + courseCount + "]").html(json);
                    /*$("tr[id="+id+"]").remove();*/
                },
                error: function () {
                    alert("课程修改异常");
                }
            });
        }

        //删除课程内容
        function delcourse(c) {
            // $("#cou"+c).hide();
            $("tr[id=cou" + c + "]").remove();
            count = count - 1;
            var couList = $("tr[id^=cou]");
            $.each(couList, function (i, e) {
                if (parseInt(e.id.substring(3)) > c) {
                    e.id = "cou" + (parseInt(e.id.substring(3)) - 1);
                }
                var idnum = parseInt(e.id.substring(3));
                $(this).children("td").eq(0).find("#courseTime").attr("value", idnum);
                $($(this).children("td").eq(3).find("#week")).find("option[value='" + idnum + "']").attr("selected", true);
            });

        }

        function deleteThisOperationOutlineCourse(id) {
            $.ajax({
                url: "${pageContext.request.contextPath}/teaching/coursesite/deleteThisOperationOutlineCourse?id=" + id,
                type: "POST",
                success: function (data) {//AJAX查询成功
                    if (data == "true") {
                        $("#cou"+count).remove();
                        alert("删除成功");
                        /*window.location.reload();*/
                    }
                }
            });
        }

        function importCourse() {
            $('#importCourse').window({width: 500, left: "30px", top: "30px"});
            $("#importCourse").window('open');
        }

        function getCourseName() {
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/teaching/coursesite/findCourseNameByCourseNumber",
                data: {'courseNumber': $("#courseNumber").val()},
                success: function (data) {
                    $("#courseName").html(data.courseName);
                    $("#courseName").trigger("liszt:updated");
                }
            });
        }
    </script>
    <script type="text/javascript">
        /*任课教师/课程负责人 选项中包含‘其他’时的输入框变化效果*/
        $(document).ready(function () {
            /*编辑时 如果其他教师栏有内容，则默认显示输入框*/
            if (${operationOutline.extraTeacher != null}) {
                $("#extraTeacher").show();
            }
            $("#operationOutlineTeacher").change(function () {
                var teachers = $("#operationOutlineTeacher").val();
                if (teachers != null && teachers != "") {
                    if (teachers.indexOf("-1") > -1) {
                        $("#extraTeacher").show();
                    } else {
                        $("#extraTeacher").hide();
                    }
                } else {
                    $("#extraTeacher").hide();
                }
            });
        });
    </script>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif"/>

    <style type=text/css>
        /*.chzn-container, .chzn-container-single {*/
            /*width: 200px !important;*/
        /*}*/
    </style>

</head>
<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">大纲管理</a></li>
            <c:if test="${isNew eq 1 }">
                <li class="end"><a href="javascript:void(0)">新建大纲</a></li>
            </c:if>
            <c:if test="${isNew eq 0 }">
                <li class="end"><a href="javascript:void(0)">编辑大纲</a></li>
            </c:if>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="title">
                    <div id="title">课程大纲</div>
                    <a class="btn btn-return" onclick="window.history.go(-1)">返回</a>
                </div>

                <div class="content-box">
                    <form:form action="${pageContext.request.contextPath}/teaching/coursesite/saveoperationoutline"
                               method="post" modelAttribute="operationOutline" onsubmit="return sunb();">
                        <fieldset class="introduce-box">
                            <form:hidden path="id"/>
                            <legend>基本信息<input type="hidden" value="" id="xsd"></legend>
                            <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter">
                                <tr align="center">

                                    <td class="label" valign="top">课程名称<font color=red>*</font></td>
                                    <td class="label" valign="top" align="left"><form:select id="courseName"
                                                                                             path="schoolCourseInfoByClassId.courseNumber"
                                                                                             class="chzn-select"
                                                                                             required="true">
                                        <form:option
                                                value="${operationOutline.schoolCourseInfoByClassId.courseNumber}">${operationOutline.schoolCourseInfoByClassId.courseName}</form:option>
                                        <c:forEach items="${schoolCourseInfoLists}" var="s">
                                            <form:option
                                                    value="${s.courseNumber}">${s.courseName}${s.courseNumber}</form:option>
                                        </c:forEach>
                                        <%--<form:options items="${schoolCourseInfoMap2}" />
                                    --%></form:select>
                                    </td>

                                    <td class="label" valign="top">英文名称</td>
                                    <td class="label" valign="top"><form:input path="enName"/></td>

                                </tr>
                                <tr align="center">

                                    <td class="label" valign="top" width="40px">课程学分</td>
                                    <td class="label" valign="top" width="40px" align="left"><form:input
                                            path="inputCredit"></form:input>
                                    </td>
                                    <td class="label" valign="top" width="40px">周学时<font color=red>*</font></td>
                                    <td class="label" valign="top" width="40px" align="left">
                                        <form:input style="width: 50px !important;"
                                                    name="theoryCourseHour" path="theoryCourseHour"></form:input><span
                                            style="float:left">+</span><form:input
                                            style="width: 50px !important;" name="experimentCourseHour"
                                            path="experimentCourseHour"></form:input>（理论课+实验课）
                                    </td>

                                    <td class="label" valign="top" width="40px">面向专业<font color=red>*</font></td>
                                    <td class="label" valign="top" width="40px" align="left">
                                        <select id="schoolMajorsa" name="schoolMajorsa" class="chzn-select"
                                                multiple="multiple">
                                            <c:forEach items="${schoolmajer}" var="d">
                                                <c:forEach items="${majorsEdit}" var="curr">
                                                    <c:if test="${d.majorNumber eq curr.majorNumber}">
                                                        <option value="${d.majorNumber}"
                                                                selected="selected">${d.majorName}</option>
                                                    </c:if>
                                                </c:forEach>
                                                <option value="${d.majorNumber}">${d.majorName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr align="center">
                                    <td class="label" valign="top" width="40px">任课教师/课程负责人<font color=red>*</font></td>
                                    <td class="label" valign="top" width="40px" align="left">
                                        <select id="operationOutlineTeacher" name="operationOutlineTeacher"
                                                class="chzn-select" multiple="multiple">
                                            <option value="-1">其他</option>
                                            <c:forEach items="${allTeachers}" var="d">
                                                <c:forEach items="${operationUser}" var="curr">
                                                    <c:if test="${d.username eq curr.username}">
                                                        <option value="${d.username}"
                                                                selected="selected">${d.cname}</option>
                                                    </c:if>
                                                </c:forEach>
                                                <option value="${d.username}">${d.cname}</option>
                                            </c:forEach>
                                        </select>
                                        <form:input type="hidden" path="extraTeacher" style="display: none"
                                                    id="extraTeacher"></form:input>
                                    </td>
                                    <td class="label" valign="top" width="40px">开课学院</td>
                                    <td class="label" valign="top" width="40px" align="left"><form:select
                                            path="schoolAcademy.academyNumber" class="chzn-select">
                                        <option value="" selected="selected">请选择</option>
                                        <form:options items="${operationstartschooleMap}"/>
                                    </form:select>
                                    </td>

                                    <td class="label" valign="top" width="40px">课程性质<font color=red>*</font></td>
                                    <td class="label" valign="top" width="40px" align="left"><select
                                            id="commencementnaturemap" name="commencementnaturemap"
                                            class="chzn-select">
                                        <c:forEach items="${commencementnaturemap}" var="s">
                                            <c:forEach items="${property}" var="curr">
                                                <c:if test="${curr.id eq s.id }">
                                                    <option value="${s.id}" selected="selected">${s.CName}</option>
                                                </c:if>
                                            </c:forEach>
                                            <option value="${s.id}">${s.CName}</option>
                                        </c:forEach>
                                    </select></td>
                                </tr>

                                <tr align="center">
                                    <td class="label" valign="top" width="40px">预修课程</td>

                                    <td class="label" valign="top" width="40px" align="left" colspan="5">
                                        必修&nbsp
                                        <select name="requiredCourse"
                                                class="chzn-select" multiple="multiple">
                                            <c:forEach items="${schoolCourseInfoMap}" var="d">
                                                <c:forEach items="${bixiuList}" var="curr">
                                                    <c:if test="${d.key eq curr.courseNumber}">
                                                        <option value="${d.key}" selected="selected">${d.value}</option>
                                                    </c:if>
                                                </c:forEach>
                                                <option value="${d.key}">${d.value}</option>
                                            </c:forEach>
                                                <%-- <c:forEach items="${schoolCourseInfoMap}" var="current">
                                               <option value ="${current.key}" >${current.value}</option>
                                        </c:forEach> --%>
                                        </select>
                                        </br>


                                        <c:forEach items="${xuanxiuListList}" var="xuanxiuList">
                                            <div class="add_elective" style="margin: 10px 0; float: left;width: 921px;">
                                                选修&nbsp


                                                <c:forEach items="${xuanxiuList}" var="xuanxiu" varStatus="i">
                                                    <c:if test="${i.index eq 0}">
                                                        <select name="electiveCourse"
                                                                class="chzn-select">
                                                            <c:forEach items="${schoolCourseInfoMap}" var="current">
                                                                <c:if test="${xuanxiu.courseNumber eq current.key }">
                                                                    <option value="${current.key}"
                                                                            selected="selected">${current.value}</option>
                                                                </c:if>
                                                                <option value="">请选择</option>
                                                                <option value="${current.key}">${current.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </c:if>
                                                </c:forEach>
                                                &nbsp或&nbsp

                                                <c:forEach items="${xuanxiuList}" var="xuanxiu" varStatus="i">
                                                    <c:if test="${i.index eq 1}">
                                                        <select name="electiveCourse"
                                                                class="chzn-select">
                                                            <c:forEach items="${schoolCourseInfoMap}" var="current">
                                                                <c:if test="${xuanxiu.courseNumber eq current.key }">
                                                                    <option value="${current.key}"
                                                                            selected="selected">${current.value}</option>
                                                                </c:if>
                                                                <option value="">请选择</option>
                                                                <option value="${current.key}">${current.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </c:if>
                                                </c:forEach>
                                                &nbsp或&nbsp
                                                <c:forEach items="${xuanxiuList}" var="xuanxiu" varStatus="i">
                                                    <c:if test="${i.index eq 2}">
                                                        <select name="electiveCourse"
                                                                class="chzn-select">
                                                            <c:forEach items="${schoolCourseInfoMap}" var="current">
                                                                <c:if test="${xuanxiu.courseNumber eq current.key }">
                                                                    <option value="${current.key}"
                                                                            selected="selected">${current.value}</option>
                                                                </c:if>
                                                                <option value="">请选择</option>
                                                                <option value="${current.key}">${current.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </c:if>
                                                </c:forEach>
                                                &nbsp或&nbsp
                                                <c:forEach items="${xuanxiuList}" var="xuanxiu" varStatus="i">
                                                    <c:if test="${i.index eq 3}">
                                                        <select name="electiveCourse"
                                                                class="chzn-select">
                                                            <c:forEach items="${schoolCourseInfoMap}" var="current">
                                                                <c:if test="${xuanxiu.courseNumber eq current.key }">
                                                                    <option value="${current.key}"
                                                                            selected="selected">${current.value}</option>
                                                                </c:if>
                                                                <option value="">请选择</option>
                                                                <option value="${current.key}">${current.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </c:if>
                                                </c:forEach>
                                                <input type="button" value="删除"
                                                       style="float:right; text-align: center;border: none;height: 22px;width: 70px !important;border-radius: 4px;background: #5e82d5;color: #fff;margin: 3px 0;margin-right:200px;"
                                                       onclick="remove(this)">

                                            </div>
                                        </c:forEach>
                                        <input id="add_myList" class="btn_add" type="button" value="添加选修"
                                               onclick="myFunction()">
                                        <script>
                                            function remove(obj) {
                                                $(obj).parent().remove();
                                            }
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label" valign="top" width="40px">学期<font color=red>*</font></td>
                                    <td class="label" valign="top" width="40px" align="left">
                                        <select id="searchterm" name="searchterm" path="schoolTerm.id">
                                            <c:forEach items="${schoolTerm}" var="current">
                                                <c:if test="${current.id eq term}">
                                                    <option value="${current.id}"
                                                            selected="selected">${current.termName}</option>
                                                </c:if>
                                                <c:if test="${current.id ne term}">
                                                    <option value="${current.id}">${current.termName}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>


                                <tr align="center">
                                    <td class="label" valign="top">教学目的和基本要求</td>

                                    <td colspan="3"><form:textarea
                                            path="basicRequirementsCourse" cols="104"/>
                                    </td>
                                </tr align="center">
                                <tr>
                                    <td class="label" valign="top">课程基本内容</td>

                                    <td colspan="3"><form:textarea path="basicContentCourse"
                                                                   cols="104"/></td>
                                </tr>

                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>教材和教学参考资料<input type="hidden" value="" id="xsd"></legend>
                            <table>
                                <tr>
                                    <td>
                                            <form:textarea path="useMaterials"
                                                           cols="104"/>
                                </tr>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">

                            <legend>教学进度安排<input type="hidden" value="" id="kc"></legend>
                            <table id="two_file">
                                <c:forEach items="${operationOutlineCourses}" var="o" varStatus="i">
                                    <tr id="cou${i.count}" name="arrange" >
                                        <td>课次：${o.courseTime}
                                        </td>
                                        <td>课程性质：${o.cDictionary.CName }
                                        </td>
                                        <td>课程内容：${o.courseContent }
                                        </td>
                                        <td>周次：${o.week}
                                        </td>
                                        <td><a onclick="editcourse(${o.id},${i.count})">修改</a>
                                        <td><a onclick="deleteThisOperationOutlineCourse(${o.id},${i.count})">删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>

                            <table>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><c:if
                                            test="${isNew eq 0 }"><%--<a onclick="importCourse()">导入</a>--%></c:if></td>
                                    <td><input class="btn_add" type="button" value="添加" onclick="addcourse()">
                                    </td>
                                </tr>
                                <tbody id="doc"></tbody>
                            </table>

                        </fieldset>

                        <tr>
                            <td colspan="5"></td>
                        </tr>
                        <tr>
                            <input type="hidden" id="projectitrms" name="projectitrms"/>
                            <td></td>
                            <td><input class="btn-submit" type="submit" value="提交">
                            </td>
                            <td></td>
                            <td colspan="3"></td>
                        </tr>

                    </form:form>
                </div>

                <div id="importCourse" class="easyui-window panel-body panel-body-noborder window-body" title="导入模版"
                     modal="true" dosize="true" maximizable="true" collapsible="true" minimizable="false" closed="true"
                     iconcls="icon-add" style=" width: 600px; height :400px;">
                    <form:form name="importForm"
                               action="${pageContext.request.contextPath}/teaching/coursesite/importCourse?idKey=${operationOutline.id }"
                               enctype="multipart/form-data">
                        <br>
                        <input type="file" id="file" name="file" required="required"/>
                        <input type="submit" value="开始上传"/>
                        <br><br>
                        <hr>
                        <br>
                        <a href="${pageContext.request.contextPath}/pages/importSample/courseExample.xlsx">点击此处</a>，下载范例<br><br><hr><br>
                        示例图片：<br>
                        <img src="${pageContext.request.contextPath}/images/importSample/course.png" heigth="100%"
                             width="100%"/>
                    </form:form>
                </div>


                <div id="newOperationItem" class="easyui-window" title="添加实验项目" closed="true" iconCls="icon-add"
                     style="width:1100px;height:450px;">
                    <div class="title">
                        <div id="title">新增课题</div>
                    </div>
                    <div class="tool-box" style="overflow:initial">
                        <table id="tab">
                            <tr>
                                <th style="width: 100px;">课题名称：</th>
                                <td><input id="lpName" style="width: 300px; float:left;"/></td>
                                <td></td>
                                <th style="width: 100px;">学时数(数字)：</th>
                                <td><input id="lpDepartmentHours" required="true"
                                           onkeyup="value=value.replace(/[^\d]/g,'') "
                                           style="width: 300px; float:left;"/></td>
                            </tr>
                            <tr>
                                <th style="width: 100px;">所属课程：</th>
                                <td><input id="schoolCourseInfo" style="width: 300px; float:left;"/></td>
                                <td></td>
                                <th style="width: 100px;">面向专业：</th>
                                <td><input id="lpMajorFit" style="width: 300px; float:left;"/></td>
                                <td></td>
                            </tr>
                            <tr>
                                <th style="width: 100px;">所属学期：</th>
                                <td><input id="schoolTerm" style="width: 300px; float:left;"/></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><input class="btn" id="save" type="button" onclick="checkRequired()" value="保存">
                                </td>
                            </tr>
                        </table>
                    </div>
                    <!-- </div> -->
                    <!-- 下拉框的js -->
                    <div id="myList1" class="add_elective"
                         style="display:none;margin: 10px 0; float: left;width: 921px;">
                        选修&nbsp
                        <!-- 不要在意这些select的path  前端只是举个例子  -->
                        <select name="electiveCourse"
                                path="schoolCourseInfoByClassId.courseNumber" class="chzn-select">
                            <option value="" selected="selected">请选择</option>
                            <c:forEach items="${schoolCourseInfoMap}" var="current">
                                <option value="${current.key}">${current.value}</option>
                            </c:forEach>
                        </select>


                        &nbsp或&nbsp
                        <select name="electiveCourse"
                                path="schoolCourseInfoByClassId.courseNumber" class="chzn-select">
                            <option value="" selected="selected">请选择</option>
                            <c:forEach items="${schoolCourseInfoMap}" var="current">
                                <option value="${current.key}">${current.value}</option>
                            </c:forEach>
                        </select>
                        &nbsp或&nbsp
                        <select name="electiveCourse"
                                path="schoolCourseInfoByClassId.courseNumber" class="chzn-select">
                            <option value="" selected="selected">请选择</option>
                            <c:forEach items="${schoolCourseInfoMap}" var="current">
                                <option value="${current.key}">${current.value}</option>
                            </c:forEach>
                        </select>
                        &nbsp或&nbsp
                        <select name="electiveCourse"
                                path="schoolCourseInfoByClassId.courseNumber" class="chzn-select">
                            <option value="" selected="selected">请选择</option>
                            <c:forEach items="${schoolCourseInfoMap}" var="current">
                                <option value="${current.key}">${current.value}</option>
                            </c:forEach>
                        </select>

                    </div>
                    <script
                            src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
                            type="text/javascript"></script>

                    <script
                            src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
                            type="text/javascript" charset="utf-8"></script>

                    <script type="text/javascript">

                        var config = {

                            '.chzn-select': {},

                            '.chzn-select-deselect': {allow_single_deselect: true},

                            '.chzn-select-no-single': {disable_search_threshold: 10},

                            '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},

                            '.chzn-select-width': {width: "95%"}

                        }

                        for (var selector in config) {

                            $(selector).chosen(config[selector]);

                        }
                        var k = 2;

                        function myFunction() {
                            var itm = document.getElementById("myList1");
                            var cln = itm.cloneNode(true);
                            cln.setAttribute('id', "");
                            var $div = $(document.createElement("div"));
                            var content = "<input type='button' value='删除' style='float:right; text-align: center;border: none;height: 22px;width: 70px !important;border-radius: 4px;background: #5e82d5;color: #fff;margin: 3px 0;margin-right:200px' onclick='remove(this)'>";
                            var $cln = $(cln);
                            $cln.css("display", "")
                            $cln.append(content);
                            //$div.append(content);
                            $div.append($cln);
                            $div.attr("id", "myList" + k);
                            var chzn = $div.find(".chzn-container.chzn-container-single");
                            chzn.remove();
                            $("#add_myList").before($div);
                            $("#myList" + k + " .chzn-select").chosen({});
                            k++;
                        }
                    </script>
</body>
</html>