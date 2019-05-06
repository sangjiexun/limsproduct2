<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE jsp:directive.include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="decorator" content="none" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css">
    <link href="${pageContext.request.contextPath}/static/css/lib.css" rel="stylesheet" type="text/css" media="">
    <link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/css/global.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/css/chosen.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/css/pignose.tab.min.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/chosen.jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/tab_slider.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/pignose.tab.min.js"></script>
    <link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.zh.js"></script>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
        --%><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css">
    <link href="${pageContext.request.contextPath}/css/cmsSystem/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/iCheck/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/sweetalert/sweetalert.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.cookie.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <script type="text/javascript">
        $(".chosen-select").chosen({
            disable_search_threshold: 10,
            no_results_text: "Oops, nothing found!",
            width: "100"
        });
        var isOtherPeriod=false;
        $(document).ready(function() {
            document.getElementById("norAdmin").style.display="none";
        });
        function changeWeekDay(){// 该方法是用一个按钮进行教务和非教务时间段的切换，其他项目可参考
            isOtherPeriod = !isOtherPeriod;// 反转
            if(isOtherPeriod){
                // 正常转其他   将对应的id和name反转
                $("#altButton").val("教务时间段排课");
                document.getElementById("norAdmin").style.display="block";
                $('#doOtherTimetable').css('display','none');
                $("#admin").css('display','none');

            }else{
                // 其他转正常   将对应的id和name反转
                $("#altButton").val("添加其他时间段排课");
                $("#norAdmin").css('display','none');
                $("#admin").css('display','');
            }
        }
        // 添加排课
        function addApp(){
            $('#doOtherTimetable').css('display','');
        }
        // 保存按钮
        function saveButton(){
            var weeks = $('#weeks').val();
            var classes = $('#classes').val().toString();
            var weekday = $('#weekday').val();
            var labRooms = $('#labRooms').val().toString();
            var items = $('#item').val().toString();
            var teachers = $('#user').val().toString();
            $.ajax({
                url:'${pageContext.request.contextPath}/timetable/judgeClassesIsContinuity',
                type:'POST',
                data:{classes:classes},
                error:function (request){
                    alert('请求错误!');
                },
                success:function(data){
                    if(data == "yes"){
                        $.ajax({
                            url:'${pageContext.request.contextPath}/timetable/saveSchoolTimetable',
                            type:'POST',
                            data:{term:${term}, classes:classes, labRooms:labRooms, weekday:weekday, items:items,
                                weeks:weeks, teachers:teachers, courseNo:${courseNo},isOther:1},
                            error:function (request){
                                alert('请求错误!');
                            },
                            success:function(data){
                                if(data != -1){
                                    $('#doOtherTimetable').css('display','none');
                                    // 被选的排课内容
                                    var weekText = $('#weeks').find("option:selected").text().trim();
                                    var classesText = $('#classes').val();
                                    var weekdayText = $('#weekday').find("option:selected").text().trim();
                                    var labText = $('#labRooms').find("option:selected").text().trim();
                                    var userText = $('#user').find("option:selected").text().trim();
                                    var itemText = $('#item').find("option:selected").text().trim();
                                    // 排课结果呈现
                                    var $td1=$("<td>第"+weekText+"周</td>");
                                    var $td2=$("<td>"+weekdayText+"</td>");
                                    var $td3=$("<td>"+classesText+"</td>");
                                    var $td4=$("<td>"+labText+"</td>");
                                    var $td5=$("<td>"+userText+"</td>");
                                    var $td6=$("<td>"+itemText+"</td>");
                                    var $td7=$("<td>其他</td>");
                                    var $td8=$("<td><a onclick='deleteOtherApp("+data+", this)' class='fa fa-trash-o g9 f14 mr5 poi'><a></td>");
                                    var $tr=$("<tr></tr>");
                                    $tr.append($td4);
                                    $tr.append($td6);
                                    $tr.append($td2);
                                    $tr.append($td3);
                                    $tr.append($td1);
                                    $tr.append($td5);
                                    $tr.append($td7);
                                    $tr.append($td8);
                                    $("tr:last").before($tr);
                                    $("#classReslut").find("tr:last").before($tr);
                                }else{
                                    alert("该时间段已经被其他排课占用，请您添加其他时间段排课");
                                }
                            }
                        });
                    }
                    else{
                        alert("请选择连续的节次!");
                    }
                }
            });
        }

        // 取消按钮
        function cancleButton(){
            $('#doOtherTimetable').css('display','none');
        }

        // ajax获取判冲后的周次
        $("#labRooms").chosen().change(function(){
            alert("coming");
            $("#weeks").html("");
            var values=[];
            $($("#classes option:selected")).each(function(){
                values.push(this.value);
            });
            var labRooms=[];
            $($("#labRooms option:selected")).each(function(){
                labRooms.push(this.value);
            });

            $.ajax({
                url:"${pageContext.request.contextPath}/timetable/reTimetable/getValidWeeksMap",
                dataType:"json",
                type:'GET',
                data:{term:${term},weekday:$("#weekday").val(),labrooms:labRooms.join(","),classes:values.join(",")},
                complete:function(result)
                {
                    var obj = eval(result.responseText);
                    var result2 = "<option value=''>周次</option>";
                    for (var i = 0; i < obj.length; i++) {
                        result2 = result2 + "<option value='" +obj[i].id + "'>" + obj[i].value + "</option>";
                    };
                    $("#weeks").append(result2);
                    $("#weeks").trigger("chosen:updated");
                    result2="";
                }
            });
            $("#weeks").trigger("chosen:updated");
        })

        /*如果选择節次，形成关联的选课数据的联动  */
        $("#classes").chosen().change(function(){
            $("#weeks").html("");
            var values=[];
            $($("#classes option:selected")).each(function(){
                values.push(this.value);
            });
            var labRooms=[];
            $($("#labRooms option:selected")).each(function(){
                labRooms.push(this.value);
            });

            $.ajax({
                url:"${pageContext.request.contextPath}/timetable/reTimetable/getValidWeeksMap",
                dataType:"json",
                type:'GET',
                data:{term:${term},weekday:$("#weekday").val(),labrooms:labRooms.join(","),classes:values.join(",")},
                complete:function(result)
                {
                    var obj = eval(result.responseText);
                    var result2 = "<option value=''>周次</option>";
                    for (var i = 0; i < obj.length; i++) {
                        //var val = obj.responseText[i];
                        //result2 = result2 + "<option value='" +obj[i].id + "' selected>" + obj[i].value + "</option>";
                        result2 = result2 + "<option value='" +obj[i].id + "'>" + obj[i].value + "</option>";
                    };
                    $("#weeks").append(result2);
                    $("#weeks").trigger("chosen:updated");
                    result2="";
                }
            });
            $("#weeks").trigger("chosen:updated");
        });

        $("#weekday").chosen().change(function(){
            $("#weeks").html("");
            var values=[];
            $($("#classes option:selected")).each(function(){
                values.push(this.value);
            });
            var labRooms=[];
            $($("#labRooms option:selected")).each(function(){
                labRooms.push(this.value);
            });
            $.ajax({
                url:"${pageContext.request.contextPath}/timetable/reTimetable/getValidWeeksMap",
                dataType:"json",
                type:'GET',
                data:{term:${term},weekday:$("#weekday").val(),labrooms:labRooms.join(","),classes:values.join(",")},
                complete:function(result)
                {
                    var obj = eval(result.responseText);
                    var result2 = "<option value=''>周次</option>";
                    for (var i = 0; i < obj.length; i++) {
                        result2 = result2 + "<option value='" +obj[i].id + "'>" + obj[i].value + "</option>";
                    };
                    $("#weeks").append(result2);
                    $("#weeks").trigger("chosen:updated");
                    result2="";
                }
            });
            $("#weeks").trigger("chosen:updated");
        });
    </script>
</head>
<body>
<div class="cf">
    <div class="tab pignose-tab-mint tab">
        <ul>
            <li>
                课程详情及安排
                <div style="float: right">
                    <a  class="btn r" onclick="back()">返回</a>
                </div>
                <div class="">
                    <ul class="bgg p10 mb20 cf b1">
                        <li class="l mr15">
                            <dl>
                                <dt class="f14">课程名称: </dt>
                                <dd>${courseInfo.courseName }</dd>
                            </dl>
                        </li>
                        <li class="l mr15">
                            <dl>
                                <dt class="f14">教务系统排课安排: </dt>
                                <dd>${coursePlan }</dd>
                            </dl>
                        </li>
                        <li class="l mr15">
                            <dl>
                                <dt class="f14">学时:</dt>
                                <dd>总学时：${courseInfo.totalHours }</dd>
                                <dd>实验学时：${courseInfo.theoreticalHours }</dd>
                            </dl>
                        </li>
                        <li class="l mr15">
                            <dl>
                                <dd><a view-student>查看学生名单</a></dd>
                            </dl>
                        </li>
                        <li class="l mr15">
                            <dl>
                                <dt class="f14">实验项目:</dt>
                                <dd><a view-item>查看本课程实验项目</a></dd>
                            </dl>
                        </li>
                    </ul>
                    <div class="course_scheduling">
                        <div>
                            <span>此课程为实验课程，请选择相应的时间和实验室安排实验课</span>
                            <div class="tool_box brl5 cf bgg pa5">
                                <div class="">
                                    <h2 class="pa5">排课结果</h2>
                                    <div class="tool_box brl5 cf ">
                                        <table  id="classReslut" class="w100p">
                                            <thead>
                                            <th>上课地点</th>
                                            <th>实验项目</th>
                                            <th>星期</th>
                                            <th>节次</th>
                                            <th>周次</th>
                                            <th>教师</th>
                                            <th>时间段</th>
                                            <th>操作</th>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${appointments }" var="currapp">
                                                <tr>
                                                    <td>
                                                        <c:forEach var="entry" items="${currapp.timetableLabRelateds}">
                                                            ${entry.labRoom.labRoomName}
                                                        </c:forEach>
                                                    </td>
                                                    <td>
                                                        <c:forEach var="entry" items="${currapp.timetableItemRelateds}">
                                                            ${entry.operationItem.lpName}
                                                        </c:forEach>
                                                    </td>
                                                    <td>星期${currapp.weekday }</td>
                                                    <td>
                                                        <c:if test="${empty currapp.timetableAppointmentSameNumbers}">
                                                            <c:if test="${currapp.startClass==currapp.endClass}">
                                                                ${currapp.startClass },
                                                            </c:if>
                                                            <c:if test="${currapp.startClass!=currapp.endClass}">
                                                                ${currapp.startClass }-${currapp.endClass },
                                                            </c:if>
                                                        </c:if>
                                                        <c:if test="${not empty currapp.timetableAppointmentSameNumbers}">
                                                            <c:set var="sameStart" value="-"></c:set>
                                                            <c:forEach var="entry" items="${currapp.timetableAppointmentSameNumbers}"   varStatus="p">
                                                                <c:set var="v_param" value="-${entry.startClass}-" ></c:set>
                                                                <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                                                    <c:if test="${entry.startClass==entry.endClass}">
                                                                        ${entry.startClass },
                                                                    </c:if>
                                                                    <c:if test="${entry.startClass!=entry.endClass}">
                                                                        ${entry.startClass }-${entry.endClass },
                                                                    </c:if>
                                                                </c:if>
                                                                <c:set var="sameStart" value="${sameStart}-${entry.startClass }-"></c:set>
                                                            </c:forEach>
                                                        </c:if>
                                                    </td>
                                                    <td>第${currapp.startWeek }周</td>
                                                    <td>
                                                        <c:forEach var="entry" items="${currapp.timetableTeacherRelateds}">
                                                            ${entry.user.cname}[${entry.user.username}]
                                                        </c:forEach>
                                                    </td>
                                                    <td>
                                                        <c:if test="${currapp.timetableStyle eq 2}">
                                                            教务安排
                                                        </c:if>
                                                        <c:if test="${currapp.timetableStyle eq 3}">
                                                            其他
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <a del-app='${currapp.id }' class="fa fa-trash-o g9 f14 mr5 poi"></a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="">
                                    <div class="cf mt10">
                                        <input class="other_time r h25 lh25 b1 br5 plr10" id="altButton" type="button" onclick="changeWeekDay()" value="非教务时间段排课">
                                    </div>
                                </div>
                                <div id="admin">
                                    <c:set var="tid" value="0"></c:set>
                                    <c:forEach items="${schoolCourseDetails }" var="current" varStatus="i">
                                        <c:forEach begin="${current.startWeek }" end="${current.endWeek }" var="currweek">
                                            <!-- 判断是否已经被排课  -->
                                            <c:set var="isDone" value="0"></c:set><!-- 是否排课标志位  1 已排课 0 未排课 -->
                                            <c:forEach items="${appointments }" var="currapp">
                                                <!-- 如果周次和星期都一致，则已排课 -->
                                                <c:if test="${currweek eq currapp.startWeek && current.weekday eq currapp.weekday }">
                                                    <c:set var="isDone" value="1"></c:set>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${isDone eq 0}"> <!-- 只呈现未排课的计划 -->
                                                <c:set var="tid" value="${tid+1}"></c:set> <!--  计算每一行的id -->
                                                <div id="doTimetable${tid}">
                                                    <select data-placeholder="上课地点" id="mulLab${tid}" class="chosen-select" multiple tabindex="4" class="">
                                                        <option value=""></option>
                                                        <c:forEach items="${labRoomMap}" var="currlab"  varStatus="i">
                                                            <option value ="${currlab.id}" >${currlab.labRoomName}${currlab.labRoomNumber} </option>
                                                        </c:forEach>
                                                    </select>
                                                    <select data-placeholder="实验项目" id="mulItem${tid}" class="chosen-select" multiple tabindex="4" class="">
                                                        <c:forEach items="${timetableItem}" var="curritem"  varStatus="i">
                                                            <option value ="${curritem.id}"> ${curritem.lpName}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <select class="chosen-select" id="mulWeekday${tid}" tabindex="2">
                                                        <option value="${current.weekday }">星期${current.weekday }</option>
                                                    </select>
                                                    <select disabled="disabled" data-placeholder="节次"  id="mulClass${tid}" class="chosen-select" multiple tabindex="4" class="">
                                                        <c:forEach begin="${current.startClass }" end="${current.endClass }"  var="currclass">
                                                            <option value="${currclass }" selected>${currclass}</option>
                                                        </c:forEach>
                                                    </select>
                                                        <%-- <input  type="text"   value="第${currweek}周" disabled/> --%>
                                                    <select class="chosen-select" id="mulWeek${tid}" tabindex="2">
                                                        <option value="${currweek}">第${currweek}周</option>
                                                    </select>
                                                    <select data-placeholder="请选择教师" id="mulUser${tid}" class="chosen-select" tabindex="2" class="">
                                                        <option value ="${user.username}" selected> ${user.cname}[${user.username}]</option>
                                                        <c:forEach items="${timetableTearcherMap}" var="curruser"  varStatus="i">
                                                            <c:if test="${curruser.key != user.username }">
                                                                <option value ="${curruser.key}"> ${curruser.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>

                                                    <a class="btn r" onclick="saveAdminTimetable(${tid})">保存</a>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </div>
                                <div id="norAdmin">
                                    <div class="cf mt10">
                                        <div class="other_time r h25 lh25 b1 br5 plr10" onclick="addApp()">添加排课</div>
                                    </div>
                                    <div id="doOtherTimetable">
                                        <select data-placeholder="上课地点" id="labRooms" class="chosen-select" multiple tabindex="4" class="">
                                            <option value=""></option>
                                            <c:forEach items="${labRoomMap}" var="currlab"  varStatus="i">
                                                <option value ="${currlab.id}" >${currlab.labRoomName}${currlab.labRoomNumber} </option>
                                            </c:forEach>
                                        </select>

                                        <select data-placeholder="实验项目" id="item" class="chosen-select" multiple tabindex="4" class="">
                                            <c:forEach items="${timetableItem}" var="curritem"  varStatus="i">
                                                <option value ="${curritem.id}"> ${curritem.lpName}</option>
                                            </c:forEach>
                                        </select>

                                        <select class="chosen-select" id="weekday" tabindex="2">
                                            <option value="0">星期</option>
                                            <c:forEach begin="1" end="6"  var="currday">
                                                <option value="${currday }">星期${currday }</option>
                                            </c:forEach>
                                        </select>

                                        <select data-placeholder="节次"  id="classes" class="chosen-select" multiple tabindex="4" class="">
                                            <c:forEach begin="1" end="13"  var="currclass">
                                                <option value="${currclass }">${currclass }</option>
                                            </c:forEach>
                                        </select>

                                        <select data-placeholder="周次" class="chosen-select" id="weeks" tabindex="2">
                                            <option value="0">周次</option>
                                        </select>

                                        <select data-placeholder="请选择教师" id="user" class="chosen-select" tabindex="2" class="">
                                            <option value ="${user.username}" selected> ${user.cname}[${user.username}]</option>
                                            <c:forEach items="${timetableTearcherMap}" var="curruser"  varStatus="i">
                                                <c:if test="${curruser.key != user.username }">
                                                    <option value ="${curruser.key}"> ${curruser.value}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                        <a class="btn r" onclick="cancleButton()">取消</a>
                                        <a class="btn r" onclick="saveButton()">保存</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $(".chosen-select").chosen({
            disable_search_threshold: 10,
            no_results_text: "Oops, nothing found!",
            width: "100"
        });

        // 查看本课程实验项目弹窗
        $('[view-item]').each(function(i,e){
            $(e).on('click',function(){
                layer.open({
                    type: 2,
                    title: '查看本课程实验项目',
                    maxmin: true,
                    shadeClose: true,
                    area : ['700px' , '350px'],
                    content: '${pageContext.request.contextPath }/operation/listOperationItemByCourse?courseNumber=${courseNumber}'
                })
            });
        });

    });
    //查看本选课组下的学生
    $('[view-student]').each(function(i,e){
        $(e).on('click',function(){
            layer.open({
                type: 2,
                title: '查看本选课组下的学生',
                maxmin: true,
                shadeClose: true,
                area : ['700px' , '350px'],
                offset:['300px','300px'],
                content: '${pageContext.request.contextPath }/timetable/viewCourseStudent?courseCode=${courseno}'
            });
        });
    });
    // 删除排课记录
    $('[del-app]').each(function(i,e){
        $(e).on('click',function(){
            var _index=$(this);
            swal({
                title: "您确定要删除这条信息吗",
                text: "删除后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "删除",
                closeOnConfirm: false
            }, function () {
                $.ajax({
                    url: '${pageContext.request.contextPath}/timetable/deleteAppointmentAjax?id='+$(e).attr('del-app'),
                    type: 'POST'
                }).done(function(data) {
                    location.reload();
                }).error(function(data) {
                    swal("OMG", "删除操作失败了呢!", "error");
                });
            });
        });
    });


    // 保存按钮
    function saveAdminTimetable(tid){

        var weeks = $('#mulWeek'+tid).val();
        var classes = $('#mulClass'+tid).val().toString();
        var weekday = $('#mulWeekday'+tid).val().toString();
        var labRooms = $('#mulLab'+tid).val().toString();
        var items = $('#mulItem'+tid).val().toString();
        var teachers = $('#mulUser'+tid).val().toString();
        console.log(${courseNo});
        $.ajax({
            url:'${pageContext.request.contextPath}/timetable/saveSchoolTimetable1',
            type:'POST',
            data:{term:${term}, classes:classes, labRooms:labRooms, weekday:weekday, items:items,
                weeks:weeks, teachers:teachers, courseNo:${courseNo}, isOther:0},
            error:function (request){
                alert('请求错误!');
            },
            success:function(data){
                if(data != '-1'){
                    $('#doTimetable'+tid).css('display','none');
                    // 被选的排课内容
                    var mulWeek = $('#mulWeek'+tid).find("option:selected").text().trim();
                    var mulClass = $('#mulClass'+tid).val();
                    var mulWeekday = $('#mulWeekday'+tid).find("option:selected").text().trim();
                    var mulLab = $('#mulLab'+tid).find("option:selected").text().trim();
                    var mulUser = $('#mulUser'+tid).find("option:selected").text().trim();
                    var mulItem = $('#mulItem'+tid).find("option:selected").text().trim();

                    // 排课结果呈现
                    var $td1=$("<td>"+mulWeek+"</td>");
                    var $td2=$("<td>"+mulWeekday+"</td>");
                    var $td3=$("<td>"+mulClass+"</td>");
                    var $td4=$("<td>"+mulLab+"</td>");
                    var $td5=$("<td>"+mulUser+"</td>");
                    var $td6=$("<td>"+mulItem+"</td>");
                    var $td7=$("<td>教务安排</td>");
                    var $td8=$("<td><a onclick='deleteApp("+data+","+tid+", this)' class='fa fa-trash-o g9 f14 mr5 poi'><a></td>");
                    var $tr=$("<tr></tr>");
                    $tr.append($td4);
                    $tr.append($td6);
                    $tr.append($td2);
                    $tr.append($td3);
                    $tr.append($td1);
                    $tr.append($td5);
                    $tr.append($td7);
                    $tr.append($td8);
                    $("tr:last").before($tr);
                }else{
                    alert("该时间段已经被其他排课占用，请您添加其他时间段排课");
                }

            }
        });
    }

    // 删除记录
    function deleteApp(id, tid , obj){
        console.log($(this));
        swal({
            title: "您确定要删除这条信息吗",
            text: "删除后将无法恢复，请谨慎操作！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            closeOnConfirm: false
        }, function () {
            $.ajax({
                url: '${pageContext.request.contextPath}/timetable/deleteAppointmentAjax?id='+id,
                type: 'POST'
            }).done(function(data) {
                swal("操作成功!", "已成功删除数据！", "success");
                $(obj).parent().parent().fadeOut(2000);
                $('#doTimetable'+tid).css('display','');
            }).error(function(data) {
                swal("OMG", "删除操作失败了呢!", "error");
            });
        });
    }

    // 删除记录
    function deleteOtherApp(id,obj){
        console.log($(this));
        swal({
            title: "您确定要删除这条信息吗",
            text: "删除后将无法恢复，请谨慎操作！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            closeOnConfirm: false
        }, function () {
            $.ajax({
                url: '${pageContext.request.contextPath}/timetable/deleteAppointmentAjax?id='+id,
                type: 'POST'
            }).done(function(data) {
                swal("操作成功!", "已成功删除数据！", "success");
                $(obj).parent().parent().fadeOut(2000);
            }).error(function(data) {
                swal("OMG", "删除操作失败了呢!", "error");
            });
        });
    }

    function back(){
        window.history.back(-1);
    }

</script>
<script type="text/javascript">
    $("#labRooms").chosen().change(function () {
        $("#weeks").html("");
        var values = [];
        $($("#classes option:selected")).each(function () {
            values.push(this.value);
        });
        var labRooms = [];
        $($("#labRooms option:selected")).each(function () {
            labRooms.push(this.value);
        });

        $.ajax({
            url: "${pageContext.request.contextPath}/timetable/reTimetable/getValidWeeksMap",
            dataType: "json",
            type: 'GET',
            data: {term:${term}, weekday: $("#weekday").val(), labrooms: labRooms.join(","), classes: values.join(",")},
            complete: function (result) {
                var obj = eval(result.responseText);
                var result2 = "<option value=''>周次</option>";
                for (var i = 0; i < obj.length; i++) {
                    result2 = result2 + "<option value='" + obj[i].id + "'>" + obj[i].value + "</option>";
                }
                ;
                $("#weeks").append(result2);
                $("#weeks").trigger("chosen:updated");
                result2 = "";
            }
        });
        $("#weeks").trigger("chosen:updated");
    })
</script>
</body>
</html>