<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <style type="text/css">
        fieldset input[type=text], fieldset textarea {
            width: 70%;
        }
        .combo {
            margin: 0 0px;
        }
    </style>
</head>
<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.open.appointment"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="all.trainingRoom.labroom"/>预约申请</a></li>
        </ul>
    </div>
</div>
<!--消息内容弹出框-->
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <form id="formid" method="POST">
                        <div class="new-classroom">
                            <%--<input id="labRid" name="labRid" value="${id}" style="display:none"/>--%>
                            <input id="labRid" name="labRid" value="${id}" type="hidden"/>
                            <fieldset>
                                <label><spring:message code="all.trainingRoom.labroom"/>名称:</label>
                                <label style="width: 180px;">${labRoom.labRoomName}</label>
                            </fieldset>
                            <fieldset>
                                <label>选择日期:</label>
                                <input class="Wdate" id="lendingTime" name="lendingTime" type="text"
                                       value="<fmt:formatDate value="${labReservation.lendingTime.time}" pattern="yyyy-MM-dd"/>"
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){onChangeDate();}})"/>
                            </fieldset>
                            <fieldset>
                                <label>预约时间:</label>
                                <select class="chzn-select" name="reservationTime" id="reservationTime" style="width:200px !important;" multiple>

                                </select>
                            </fieldset>
                            <fieldset>
                                <label>预约用途:</label>
                                <select id="lendingType" class="chzn-select" cssStyle="width:200px;">
                                    <option value="${labReservation.CDictionaryByLendingType.id }">${labReservation.CDictionaryByLendingType.CName}</option>
                                    <c:forEach items="${lendingTypes}" var="lendingType">
                                        <option value="${lendingType.id }">${lendingType.CName}</option>
                                    </c:forEach>
                                </select>
                            </fieldset>
                            <fieldset>
                                <label>使用对象:</label>
                                <select id="lendingUserType" onchange="showClass()" class="chzn-select" cssStyle="width:200px;" >
                                    <c:forEach items="${userTypes}" var="userType">
                                        <c:if test="${userType.id eq labReservation.CDictionaryByLendingUserType.id}">
                                            <option value="${userType.id }" selected>${userType.CName}</option>
                                        </c:if>
                                        <c:if test="${userType.id ne labReservation.CDictionaryByLendingUserType.id}">
                                            <option value="${userType.id }">${userType.CName}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </fieldset>
                                <fieldset class="class"
                                          <c:if test="${labReservation.CDictionaryByLendingUserType.CNumber ne '1' or
                                          labReservation.CDictionaryByLendingUserType.CCategory ne 'lab_room_lending_user_type'}">
                                              style="display: none;"</c:if>
                                >
                                <label>班级:</label>
                                <select id="class" name="class" class="chzn-select" cssStyle="width:200px;" >
                                    <c:forEach items="${schoolClassess}" var="schoolClass">
                                        <c:if test="${schoolClass.classNumber eq labReservation.schoolClasses.classNumber}">
                                            <option value="${schoolClass.classNumber}" selected>${schoolClass.className}</option>
                                        </c:if>
                                        <c:if test="${schoolClass.classNumber ne labReservation.schoolClasses.classNumber}">
                                            <option value="${schoolClass.classNumber}">${schoolClass.className}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </fieldset>
                            <fieldset>
                                <label>预约部门:</label>
                                <select id="lendingUnit" name="lendingUnit" class="chzn-select classes" cssStyle="width:200px;">
                                    <option value="${currAcademy.academyName}">${currAcademy.academyName}</option>
                                    <c:forEach items="${schoolAcademyList}" var="schoolAcademy">
                                        <option value="${schoolAcademy.academyName}">${schoolAcademy.academyName}</option>
                                    </c:forEach>
                                </select>
                            </fieldset>
                            <fieldset>
                                <label>使用人数:</label>
                                <input id="number" name="number" required="true" value="${labReservation.number }"/>
                            </fieldset>
                            <fieldset>
                                <label>预约人电话:</label>
                                <input id="lendingUserPhone" name="lendingUserPhone" required="true"  value="${labReservation.lendingUserPhone }"/>
                            </fieldset>
                            <c:if test="${teacherAudit eq '是' }">
                                <fieldset>
                                    <label>选择审核导师:</label>
                                    <select   id="teacher" class="chzn-select" data-placeholder="Choose a Country" style="width:350px;" tabindex="1">
                                        <c:forEach items="${teacherList}" var="s">
                                            <option value="${s.username}">${s.cname}</option>
                                        </c:forEach>
                                    </select>
                                </fieldset>
                            </c:if>
                            <fieldset>
                                <label>预约原因:</label>
                                <textarea id="reason" type="text" style="height: 100px;width: 400px;" value="">${labReservation.lendingReason }</textarea>
                            </fieldset>
                        </div>
                        <div class="moudle_footer">
                            <div class="submit_link">
                                <input class="btn btn-new" type="button" value="申请" onclick="saveLabRoomLending(${labRoom.id})">
                                <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
                            </div>
                        </div>
                    </form>
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
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //保存实训室预约
    function saveLabRoomLending(labRoomId){
        if($("input[name='lendingTime']") .val() == ""){
            alert("请选择日期");
            return false;
        }
        if($("#reservationTime").val()){
        }else{
            alert("请选择预约时间");
            return false;
        }
        var myData = {
            'labRid':$("#labRid").val(),
            'lendingTime':$("input[name='lendingTime']") .val(),
            'startTime':$("#starttime").val(),
            'endTime':$("#endtime").val(),
            'reason':$("#reason").val(),
            'number':$("#number").val(),
            'lendingUserPhone':$("#lendingUserPhone").val(),
            'lendingUnit':$("#lendingUnit").val(),
            'class':$("#class").val(),
            'lendingType':$("#lendingType").val(),
            'lendingUserType':$("#lendingUserType").val(),
            'teacher':$("#teacher").val(),
            'reservationTime': $("#reservationTime").val()
        };
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/LabRoomReservation/saveLabReservation?labRoomId="+labRoomId,
            data: myData,
            dataType:'text',
            success:function(data){
                console.log(data);
                if(data=="reserved"){
                    alert("预约失败，您选的时间段已经被预约！");
                }else if(data=="lent") {
                    alert("预约失败，您选的时间段已经被占用！");
                }else if(data=="editSuccess"){
                    // layer.alert("编辑预约成功，请等待审核!", {icon: 6,time:5000});
                    alert("编辑预约成功，请等待审核!");
                    window.location.href="${pageContext.request.contextPath}/labRoomLending/labReservationList?page=${page}&isaudit=${isaudit}&tage=${tage}"
                }else{
                    alert("预约成功，请等待审核！");
                    window.location.href="${pageContext.request.contextPath}/labRoomLending/labReservationList?page=${page}&isaudit=${isaudit}&tage=${tage}"
                }
            }
        })
    }
</script>
<script type="text/javascript">
    function onChangeDate() {
        if ($("#lendingTime").val() != "") {
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoomLending/checkConflict",
                type:"POST",
                data:{
                    labRid:$("#labRid").val(),
                    labRoom: ${labRoom.id},
                    lendingTime: $("#lendingTime").val()
                },
                success:function (data) {
                    $("#reservationTime").html(data);
                    $("#reservationTime").trigger("liszt:updated");
                }
            });
        }
    }

    //使用对象联动班级是否显示 未起效 插件原因
    function showClass() {
        var lendingUserType = $("#lendingUserType").val();
        console.log(lendingUserType + "hjh");
        if (lendingUserType == 717) {
            $("fieldset.class").css('display', '');
        } else {
            $("fieldset.class").css('display', 'none');
        }
    }
</script>
<script type="text/javascript">

    $(document).ready(function() {
        onChangeDate();
    })
</script>
</body>
</html>
