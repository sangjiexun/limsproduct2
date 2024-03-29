<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false"
         contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<head>
    <meta name="decorator" content="iframe"/>
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
    <!-- 下拉框的样式 -->
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/chosen/chosen.css"/>

    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/js/jquery-easyui/themes/default/easyui.css"/>
    <!-- 下拉的样式结束 -->
    <!-- 打印插件的引用 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <!-- 弹窗 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-easyui/locale/easyui-lang-zh_CN.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>

    <script type="text/javascript">

        function onChangeDate() {
            if($("#labRoom").val() == ""){
                alert("请先填写实验室名称")
                $("#lendingTime").val('');
            }
            if ($("#labRoom").val() != "") {
                if ($("#lendingTime").val() != "") {
                    $.ajax({
                        url:"${pageContext.request.contextPath}/labRoomLending/checkConflict",
                        type:"POST",
                        data:{
                            labRoom: $("#labRoom").val(),
                            lendingTime: $("#lendingTime").val()
                        },
                        success:function (data) {
                            $("#reservationTime").html(data);
                            $("#reservationTime").trigger("liszt:updated");
                        }
                    });
                }
            }
        }
    </script>
    <script type="text/javascript">
        //取消查询
        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/lab/labAnnexList?currpage=1"
        }

        //分页跳转
        function targetUrl(url) {
            document.form.action = url;
            document.form.submit();
        }

        //弹出学生预约框
        function showLabRoomReservation(labRoomId) {
            layer.ready(function () {
                layer.open({
                    type: 2,
                    title: '实训室预约',
                    fix: true,
                    maxmin: true,
                    shift: -1,
                    closeBtn: 1,
                    shadeClose: true,
                    move: false,
                    maxmin: false,
                    area: ['1000px', '420px'],
                    content: '../LabRoomReservation/showLabRoomReservation?labRoomId=' + labRoomId,
                    end: function () {
                    }
                });
            });
        }

        //弹出教师预约框
        function showLabRoomReservationTeacher(labRoomId) {
            layer.ready(function () {
                layer.open({
                    type: 2,
                    title: '教师-实训室预约',
                    fix: true,
                    maxmin: true,
                    shift: -1,
                    closeBtn: 1,
                    shadeClose: true,
                    move: false,
                    maxmin: false,
                    area: ['1000px', '420px'],
                    content: '../LabRoomReservation/showLabRoomReservationTeacher?labRoomId=' + labRoomId,
                    end: function () {
                    }
                });
            });
        }

        //弹出实验室实况框
        function viewNow(labRoomId) {
            layer.ready(function () {
                layer.open({
                    type: 2,
                    title: '实训室实况',
                    fix: true,
                    maxmin: true,
                    shift: -1,
                    closeBtn: 1,
                    shadeClose: true,
                    move: false,
                    maxmin: false,
                    area: ['1000px', '420px'],
                    content: '../LabRoomReservation/showLabRoomNow?labRoomId=' + labRoomId,
                    end: function () {
                    }
                });
            });
        }

        //弹出软件详情
        function showLabRoomSoftware(labRoomId) {
            layer.ready(function () {
                layer.open({
                    type: 2,
                    title: '实训室软件详情',
                    fix: true,
                    maxmin: true,
                    shift: -1,
                    closeBtn: 1,
                    shadeClose: true,
                    move: false,
                    maxmin: false,
                    area: ['1000px', '420px'],
                    content: '../LabRoomReservation/showLabRoomSoftware?labRoomId=' + labRoomId,
                    end: function () {
                    }
                });
            });
        }

        //弹出设备详情
        function showLabRoomDevice(labRoomId) {
            layer.ready(function () {
                layer.open({
                    type: 2,
                    title: '实训室设备详情',
                    fix: true,
                    maxmin: true,
                    shift: -1,
                    closeBtn: 1,
                    shadeClose: true,
                    move: false,
                    maxmin: false,
                    area: ['1000px', '420px'],
                    content: '../LabRoomReservation/showLabRoomDevice?labRoomId=' + labRoomId,
                    end: function () {
                    }
                });
            });
        }

        //学生预约资格判断
        function judgeAccess(labRoomId) {
            $.ajax({
                type: "POST",
                //url: "../labRoom/securityAccess?id=" + labRoomId,
                url: "../labRoom/labSecurityAccess?id=" + labRoomId,
                data: '',
                dataType: 'text',
                success: function (data) {
                    if (data == "errorType2") {
                        alert("您还未通过单独培训!");
                        window.location.reload();
                    } else if (data == "noReservation") {
                        alert("本实验室不提供预约！");
                        window.location.reload();
                    }else if (data == "noSetting") {
                        alert("该实验室未作预约初始化设置，不可预约!");
                        window.location.reload();
                    }else if (data == "needAccess") {
                        var Access = confirm("您还未通过培训,点击确定进行预约培训!");
                        if(Access == true){
                            var labRoom_id = -1;
                            var schoolDevice_deviceNumber = -1;
                            var schoolDevice_deviceName = -1;
                            var username = -1;
                            var labRoom_allowAppointment = -1;

                            var url = "${pageContext.request.contextPath}/labReservation/viewLabRoomTrainingRest/" + labRoom_id + "/" + schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username + "/" + 1 + "/" + labRoomId + "/" + labRoom_allowAppointment;

                            window.location.href = url;
                        }else {
                            window.location.reload();
                        }
                    } else {
                        window.location.href = "${pageContext.request.contextPath}/labRoomLending/showLabRoomLending?labRoomId=" + labRoomId;
                    }
                },
                error: function () {
                    layer.msg('发生未知错误', {icon: 5});
                }
            })
        }

        //教师预约预约资格判断
        function judgeAccessTeacher(labRoomId) {
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/LabRoomReservation/judgeAccess?labRoomId=" + labRoomId,
                data: '',
                dataType: 'text',
                success: function (data) {
                    if (data == "noSetting") {
                        layer.msg('该实训室未进行初始化设置，不可预约', {icon: 5});
                    } else if (data == "noAccess") {
                        layer.msg('该实训室需要参加培训并通过后方可预约', {icon: 5});
                    } else if (data == "noPassTest") {
                        layer.msg('该实训室需要参加网上测试并通过后方可预约', {icon: 5});
                    } else if (data == "noCreditScore") {
                        layer.msg('您的信誉积分低于80分，不可预约', {icon: 5});
                    } else if (data == "noDean") {
                        layer.msg('系统未查到您所属的系主任/系教学秘书，不可预约', {icon: 5});
                    } else {
                        //可预约，跳出弹框
                        showLabRoomReservationTeacher(labRoomId);
                    }
                },
                error: function () {
                    layer.msg('发生未知错误', {icon: 5});
                }
            })
        }

        //实验室设置
        <%--function openSetupLink(labRoomId, currpage) {//将labRoomId page传递到后台--%>
            <%--//alert(labRoomId);--%>
            <%--var url = "${pageContext.request.contextPath}/device/editLabRoomSettingRest/" + labRoomId + "/" + currpage + "/1";--%>

            <%--window.location.href = url;--%>
        <%--}--%>

        //培训预约(编辑--针对老师)
        function editLabRoomTrainingRest(id) {
            var labRoom_id = $("#labRoom_id").val();
            var schoolDevice_deviceNumber = $("#schoolDevice_deviceNumber").val();
            var schoolDevice_deviceName = $("#schoolDevice_deviceName").val();
            var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
            var username = $("#username").val();
            //alert(schoolDevice_deviceName);
            if ($("#labRoom_id").val() == "") {
                labRoom_id = "-1";
            }
            if ($("#schoolDevice_deviceNumber").val() == "") {
                schoolDevice_deviceNumber = "-1";
            }
            if ($("#schoolDevice_deviceName").val() == "") {
                schoolDevice_deviceName = "-1";
            }
            if ($("#username").val() == "") {
                username = "-1";
            }
            if ($("#schoolDevice_allowAppointment").val() == "") {
                schoolDevice_allowAppointment = "-1";
            }
            var url = "${pageContext.request.contextPath}/device/editLabRoomTrainingRest/" + labRoom_id + "/" + schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username + "/${page}/" + id + "/" + schoolDevice_allowAppointment;
            //alert(url);
            window.location.href = url;
        }

        //培训预约(查看--针对学生)
        function viewLabRoomTrainingRest(id) {
            var labRoom_id = -1;
            var schoolDevice_deviceNumber = -1;
            var schoolDevice_deviceName = -1;
            var username = -1;
            var labRoom_allowAppointment = -1;

            var url = "${pageContext.request.contextPath}/labReservation/viewLabRoomTrainingRest/" + labRoom_id + "/" + schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username + "/" + 1 + "/" + id + "/" + labRoom_allowAppointment;

            window.location.href = url;
        }

        //考试
        function findTestListForAdmin(labRoomId) {//将labRoomId deviceNumber deviceName page传递到后台
            //alert("11");
            var page = ${currpage};
            var status = -1;
            var url = "${pageContext.request.contextPath}/teaching/test/labRoomTestList/" + labRoomId + "/" + page + "/" + status;
            window.location.href = url;
        }

        //考试
        function findTestList(labRoomId) {//将labRoomId deviceNumber deviceName page传递到后台
            var page = ${currpage};
            var status = -1;
            var url = "${pageContext.request.contextPath}/teaching/test/labRoomTestListForStudentAndTeacher/" + labRoomId + "/" + page + "/" + status;
            window.location.href = url;
        }

        //使用对象联动班级是否显示 未起效 插件原因
        function showClass() {
            var lendingUserType = $("#lendingUserType").val();
            console.log(lendingUserType + "hjh");
            if (lendingUserType == 717) {
                $("th.class").css('display', '');
                $("td.class").css('display', '');
            } else {
                $("th.class").css('display', 'none');
                $("td.class").css('display', 'none');
            }
        }
    </script>
    <script type="text/javascript">

    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/labreservation/labreservation.js"></script>
    <script type="text/javascript">
        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1";
        }
    </script>
    <style type="text/css">
        #formid table tr td, th {
            text-align: left;
        }

        .btn_reser a {
            display: block;
            width: 20px;
            color: #777;
            line-height: 18px;
            white-space: normal;
            margin: 0 0 0 4px;
            font-weight: normal;
            padding: 0 9px !important;
        }

        .br_selected a {
            color: #fff;
        }

        .tool-box input {
            float: none;
        }

        .content-box .tab_lab {
            width: 100%;
            left: 0;
            margin: -1px;
        }

        .tab_lab {
            width: 100%;
        }

        .tab_lab,
        .tab_lab th,
        .tab_lab td {
            border: 1px solid #e4e5e7;
        }

        .tab_lab th {
            background: #fafafa;
            width: 90px;
            padding: 0 15px 0 0;
            text-align: right;
        }

        .tab_lab td {
            text-align: left;
            padding: 10px;
        }

        .tab_lab td input[type="text"],
        .tab_lab td textarea,
        .tab_lab td .spinner,
        .tab_lab td .combo {
            resize: none;
            border: 1px solid #cdcdcd;
            border-radius: 3px;
            line-height: 22px;
            padding: 0 10px;
            outline: none;
        }

        .tab_lab td .spinner,
        .tab_lab td .combo {
            padding: 1px 0;
        }

        .tab_lab td input[type="text"] {
            width: 124px;
        }

        .tab_lab td .spinner input[type="text"],
        .tab_lab td .combo input[type="text"] {
            border: none;
        }

        .tab_lab td textarea {
            width: 100%;
            box-sizing: border-box;
        }

        .tab_lab td input[type="text"]:focus,
        .tab_lab td textarea:focus {
            border: 1px solid #f3ce7a;
        }

        .tab_fix td {
            text-align: left;
            white-space: nowrap;
            padding-right: 20px;
        }

        .tab_fix td input[type="text"] {
            height: 24px;
            width: 100%;
            box-sizing: border-box;
            /*min-width: 210px;*/
        }

        .tab_fix th {
            font-weight: normal;
            width: 95px;
            text-align: right;
            white-space: nowrap;
        }

    </style>
</head>
<body>
<!--导航  -->
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">开放预约 </a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="all.trainingRoom.labroom"/>预约</a></li>
        </ul>
    </div>
</div>
<!--导航结束  -->
<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
        <li class="TabbedPanelsTab selected" id="s1">
            <a href="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1"><spring:message
                    code="all.trainingRoom.labroom"/>预约</a><%--实验室--%></li>
        <li class="TabbedPanelsTab" id="s2"><a
                href="${pageContext.request.contextPath}/labRoomLending/labReservationList?tage=0&page=1&isaudit=2">我的申请</a>
        </li>
        <sec:authorize ifNotGranted="ROLE_STUDENT">
            <li class="TabbedPanelsTab" id="s3"><a
                    href="${pageContext.request.contextPath}/labRoomLending/labReservationList?tage=0&page=1&isaudit=1">我的审核</a>
            </li>
        </sec:authorize>
        <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
            <li class="TabbedPanelsTab" id="s4"><a
                    href="${pageContext.request.contextPath}/labRoomLending/labReservationObsoleteList?page=1">失效记录</a>
            </li>
        </c:if>
    </ul>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsTabGroup-box">
            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">
                    <form id="formid" method="POST" autocomplete="off">
                        <table class="tab_lab">
                            <tr>
                                <th><spring:message code="all.trainingRoom.labroom"/>名称</th>
                                <td>
                                    <select class="chzn-select" id="labRoom" onchange="selectLabRoom();">
                                        <c:if test="${labRoom != null}">
                                            <option value="${labRoom.id}" selected="selected">${labRoom.labRoomName }</option>
                                        </c:if>
                                        <c:if test="${labRoom == null}">
                                            <option id="selectChoose" value="请选择" selected="selected">--请选择--</option>
                                        </c:if>
                                        <c:forEach items="${labRooms}" var="lab">
                                            <option value="${lab.id}">${lab.labRoomName }(${lab.labRoomNumber})</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <th>选择日期</th>
                                <td colspan="3">
                                    <input class="Wdate" id="lendingTime" name="lendingTime" type="text"
                                           value="<fmt:formatDate value="${labReservation.lendingTime.time}" pattern="yyyy-MM-dd"/>"
                                           onclick="WdatePicker({minDate:'%y-%M-{%d}',dateFmt:'yyyy-MM-dd',onpicked:function(){onChangeDate();}})" />
                                    <font class="space"></font>
                                    预约时间&nbsp;:
                                    <select class="chzn-select" name="reservationTime" id="reservationTime" style="" multiple>

                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>预约用途</th>
                                <td>
                                    <select id="lendingType" class="chzn-select" cssStyle="width:200px;">
                                        <option value="${labReservation.CDictionaryByLendingType.id }">${labReservation.CDictionaryByLendingType.CName}</option>
                                        <c:forEach items="${lendingTypes}" var="lendingType">
                                            <option value="${lendingType.id }">${lendingType.CName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <th>使用对象</th>
                                <td>
                                    <select id="lendingUserType" onchange="showClass()" class="chzn-select"
                                            cssStyle="width:200px;">
                                        <option value="${labReservation.CDictionaryByLendingUserType.id }">${labReservation.CDictionaryByLendingUserType.CName}</option>
                                        <c:forEach items="${userTypes}" var="userType">
                                            <option value="${userType.id }">${userType.CName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <th class="class">班级</th>
                                <td class="class">
                                    <select id="class" name="class" class="chzn-select classes" cssStyle="width:200px;">
                                        <option value="${labReservation.schoolClasses.classNumber}">${labReservation.schoolClasses.className}</option>
                                        <c:forEach items="${schoolClassess}" var="schoolClass">
                                            <option value="${schoolClass.classNumber}">${schoolClass.className}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>预约部门<span style="color:red;">*</span></th>
                                <td>
                                    <select id="lendingUnit" name="lendingUnit" class="chzn-select classes" cssStyle="width:200px;">
                                        <option value="${currAcademy.academyName}">${currAcademy.academyName}</option>
                                        <c:forEach items="${schoolAcademyList}" var="schoolAcademy">
                                            <c:if test="${currAcademy.academyNumber ne schoolAcademy.academyNumber}">
                                                <option value="${schoolAcademy.academyName}">${schoolAcademy.academyName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </td>
                                <th>使用人数<span style="color:red;">*</span></th>
                                <td>
                                    <input id="number" name="number" required="true" value="${labReservation.number }"
                                           onkeyup="value=value.replace(/[^\d]/g,'')" type="text"/>
                                </td>
                                <th>预约人电话<span style="color:red;">*</span></th>
                                <td>
                                    <input id="lendingUserPhone" name="lendingUserPhone" required="true"
                                           value="${labReservation.lendingUserPhone }" type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <c:if test="${!fn:contains('zjcclims',PROJECT_NAME)
							or sessionScope.selected_role ne 'ROLE_STUDENT'}">
                                </c:if>
                                <th>预约原因<span style="color:red;">*</span></th>
                                <td colspan="5">
                                    <textarea id="reason" type="text" placeholder="请填写相关事项、议题或内容、主要参加人员等信息"
                                              rows="5">${labReservation.lendingReason }</textarea>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6" style="text-align:right;">
                                    <input type="button" value="提交" class="btn btn-new" onclick="openChooseTeacher()">
                                </td>
                            </tr>

                        </table>
                    </form>

                    <div class="right-content">
                        <div class="content-box">
                            <div class="tool-box">
                                <ul>
                                    <form:form name="form"
                                               action="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1"
                                               method="post" modelAttribute="labRoom">
                                        <li><spring:message code="all.trainingRoom.labroom"/>名称:
                                            <form:input path="labRoomName"/></li>
                                        <li><input type="submit" value="搜索"/>
                                        <%--<li><input type="button" value="打印" id="print"></li>--%>
                                        <%--<li>
                                          <input type="button" value="导出" onclick="exportAll('${pageContext.request.contextPath}/lab/labAnnexListexportall?page=${currpage}');">
                                        </li>--%>
                                        <input class="cancel-submit" type="button" onclick="cancel()" value="取消"></li>
                                    </form:form>
                                </ul>
                            </div>
                        </div>
                        <div class="content-box">
                            <table>
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th><spring:message code="all.trainingRoom.labroom"/>编号</th>
                                    <th><spring:message code="all.trainingRoom.labroom"/>名称</th>
                                    <th><spring:message code="all.trainingRoom.labroom"/>楼层</th>
                                    <c:if test="${PROJECT_NAME eq 'zjcclims'}">
                                        <th><spring:message code="all.trainingRoom.labroom"/>等级</th>
                                    </c:if>
                                    <th><spring:message code="all.trainingRoom.labroom"/>容量</th>
                                    <c:if test="${jobReservation eq 'true'}">
                                        <th>可预约工位数</th>
                                    </c:if>
                                    <th><%-- <fmt:message key="navigation.operate" /> --%>操作
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${listLabRoom}" var="s" varStatus="i">
                                    <tr>
                                        <td>${i.count+(currpage-1)*pageSize }</td>
                                        <td>${s.labRoomNumber}</td>
                                        <td>${s.labRoomName}</td>
                                        <td><c:if test="${s.floorNo ne null}">${s.systemBuild.buildName}(${s.floorNo}楼)</c:if>
                                            <c:if test="${s.floorNo eq null || s.floorNo eq ''}"></c:if></td>
                                        <c:if test="${PROJECT_NAME eq 'zjcclims'}">
                                            <c:if test="${s.labRoomLevel eq 0}">
                                                <td>特级</td>
                                            </c:if>
                                            <c:if test="${s.labRoomLevel eq 1}">
                                                <td>一级</td>
                                            </c:if>
                                            <c:if test="${s.labRoomLevel eq 2}">
                                                <td>二级</td>
                                            </c:if>
                                            <c:if test="${empty s.labRoomLevel}">
                                                <td>未设置</td>
                                            </c:if>
                                        </c:if>
                                        <td>${s.labRoomCapacity}</td>
                                        <c:if test="${jobReservation eq 'true'}">
                                            <td>${s.labRoomWorker}</td>
                                        </c:if>
                                        <td>
                                            <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'
									|| sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'
									|| sessionScope.selected_role eq 'ROLE_TEACHER'
									|| sessionScope.selected_role eq 'ROLE_LABMANAGER'
									|| sessionScope.selected_role eq 'ROLE_EQUIPMENTADMIN'
									|| sessionScope.selected_role eq 'ROLE_ASSISTANT'
									|| (sessionScope.selected_role eq 'ROLE_STUDENT' && !fn:contains('zjcclims',PROJECT_NAME))
									}">
                                                <%-- <a onclick="showLabRoomReservation(${s.id})" href="javascript:void(0)">借用</a>  target="_blank" --%>
                                                <c:if test="${sessionScope.selected_role eq 'ROLE_STUDENT'}">
                                                    <a href="javascript:void(0)"
                                                       onclick="viewLabRoomTrainingRest(${s.id})">预约培训</a>
                                                </c:if>
<%--                                                <a onclick="judgeAccess(${s.id})">预约</a>--%>
                                                <%--<a href="${pageContext.request.contextPath}/labRoomLending/labRoomLendingInfoList?idKey=${s.id}&flag=1&step=0">查看</a>--%>
                                            </c:if>
                                            <c:forEach items="${s.labRoomAdmins}" var="la">
                                                <c:if test="${la.user.username eq user.username}">
                                                    <a  href="javascript:void(0)" onclick="openSetupLink(${s.id},${pageModel.currpage},1)">设置</a>
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="page">
                                ${totalRecords}条记录 &nbsp; 共${pageModel.totalPage}页 &nbsp;
                                <a href="javascript:void(0)"
                                   onclick="targetUrl('${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1')"
                                   target="_self">首页</a>
                                <a href="javascript:void(0)"
                                   onclick="targetUrl('${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=${pageModel.previousPage}')"
                                   target="_self">上一页</a>
                                <input type="hidden" value="${pageModel.lastPage}" id="totalpage"/>&nbsp; <input
                                    type="hidden" value="${currpage}" id="currpage"/>
                                &nbsp;第
                                <select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                                    <option value="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=${currpage}">${currpage}</option>
                                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1"
                                               varStatus="j" var="current">
                                        <c:if test="${j.index!=currpage}">
                                            <option value="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=${j.index}">${j.index}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>页&nbsp;

                                <a href="javascript:void(0)"
                                   onclick="targetUrl('${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=${pageModel.nextPage}')"
                                   target="_self">下一页</a>
                                <a href="javascript:void(0)"
                                   onclick="targetUrl('${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=${pageModel.lastPage}')"
                                   target="_self">末页</a>
                            </div>

                        </div>
                    </div>
                    <div id="check_user" class="easyui-window" closed="true" modal="true" minimizable="true"
                         title="指定审核导师" style="width: 580px;height: 350px;padding: 20px">
                        <div class="content-box">
                            <table>
                                <tr>
                                    <td>选择导师</td>
                                    <td>
                                        <select name="teacher" id="teacher" class="chzn-select">
                                            <option value="">请选择</option>
                                            <c:forEach items="${teacherList}" var="u">
                                                <option value="${u.username}">[${u.username}]${u.cname}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                            <div class="moudle_footer">
                                <div class="submit_link">
                                    <input class="btn" id="save" type="button" onclick="submitForAudit();" value="提交">
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 下拉框的js -->

                    <script
                            src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
                            type="text/javascript"></script>

                    <script
                            src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
                            type="text/javascript" charset="utf-8"></script>

                    <script type="text/javascript">
                        var config = {
                            '.chzn-select': {search_contains: true},
                            '.chzn-select-deselect': {allow_single_deselect: true},
                            '.chzn-select-no-single': {disable_search_threshold: 10},
                            '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
//                            '.chzn-select-width': {width: "95%"}
                        }
                        for (var selector in config) {
                            $(selector).chosen(config[selector]);
                        }
                    </script>
                    <script type="text/javascript">
                        var teacher = "";
                        var labRoomId;
                        var needtutor = 0;
                        var isAjax=false;
                        //保存实训室借用
                        function saveLabRoomLending() {
                            if(isAjax){
                                return false;
                            }
                            // layer.msg('正在提交请稍候...', {icon: 16});
                            if ($("#labRoom").val() == "请选择") {
                                alert("请选择实验室");
                                return false;
                            }
                            if ($("input[name='lendingTime']").val() == "") {
                                alert("请选择日期");
                                return false;
                            }
                            if ($("#reservationTime").val()) {
                            } else {
                                alert("请选择开始时间");
                                return false;
                            }
                            if (!$("#lendingType option:checked").val()) {
                                alert("请选择使用用途");
                                return false;
                            }
                            if (!$("#lendingUserType option:checked").val()) {
                                alert("请选择使用对象");
                                return false;
                            }
                            if ($("#lendingUserType option:checked").text() == "校内学生") {
                                if (!$("#class option:checked").val()) {
                                    alert("请选择班级");
                                    return false;
                                }
                            }

                            if (!$("#lendingUnit").val()) {
                                alert("请输入预约部门");
                                return false;
                            }
                            if (!$("#number").val()) {
                                alert("请输入使用人数");
                                return false;
                            }
                            if (!$("#lendingUserPhone").val()) {
                                alert("请输入预约人电话");
                                return false;
                            }
                            if (!$("#reason").val()) {
                                alert("请输入预约原因");
                                return false;
                            }

                            var labRoomId = $("#labRoom").val();
                            var myData = {
                                'lendingTime': $("input[name='lendingTime']").val(),
                                'startTime': $("#starttime").val(),
                                'endTime': $("#endtime").val(),
                                'reason': $("#reason").val(),
                                'number': $("#number").val(),
                                'lendingUserPhone': $("#lendingUserPhone").val(),
                                'lendingUnit': $("#lendingUnit").val(),
                                'class': $("#class").val(),
                                'lendingType': $("#lendingType").val(),
                                'lendingUserType': $("#lendingUserType").val(),
                                'teacher': teacher,
                                'reservationTime': $("#reservationTime").val()
                            };
                            isAjax=true;
                            $.ajax({
                                type: "POST",
                                url: "${pageContext.request.contextPath}/LabRoomReservation/saveLabReservation?labRoomId=" + labRoomId,
                                data: myData,
//                                async: false,
                                dataType: 'text',
                                success: function (data) {
                                    layer.msg("删除成功", {icon: 1});
                                    console.log("输出："+data+"{fail:保存,noSerial:创建流水单,noStatus:创建审核单,noTeacher:无需教师审核更新,noAudit:无需审核}");
                                    if (data == "fail") {
                                        layer.msg('预约失败,发生未知错误!', {icon: 5});
                                    }else if (data == "noSerial") {
                                        layer.msg('预约失败,操作流水单出错!', {icon: 5});
                                    }else if (data == "noStatus") {
                                        layer.msg('预约失败,创建审核单出错!', {icon: 5});
                                    }else if (data == "noTeacher") {
                                        layer.msg('预约失败,发生未知错误!', {icon: 5});
                                    }else if (data == "noAudit") {
                                        layer.msg("预约无需审核，欢迎使用!", {icon: 1});
                                        window.location.reload();
                                    }else {
                                        layer.msg("预约成功，请等待审核!", {icon: 1});
                                        window.location.href = "${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1"
                                    }
                                },
                                error:function(error){
                                    alert("请求错误");
                                }
                            })
                        }

                        function selectLabRoom() {
                            if ($("#labRoom").val() == "请选择") {
                                return;
                            }
                            labRoomId = parseInt($("#labRoom").val());
                            $.ajax({
                                type: "POST",
                                //url: "../labRoom/securityAccess?id=" + labRoomId,
                                url: "../labRoom/labSecurityAccess?id=" + labRoomId,
                                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                                dataType: 'text',
                                async: false,
                                success: function (data) {
                                    if (data == "errorType2") {
                                        alert("您还未通过单独培训!");
                                        window.location.reload();
                                    } else if (data == "noReservation") {
                                        alert("本实验室不提供预约！");
                                        window.location.reload();
                                    }else if (data == "noSetting") {
                                        alert("该实验室未作预约初始化设置，不可预约!");
                                        window.location.reload();
                                    } else if (data == "needTutor"){
                                        needtutor = 1;
                                    } else if (data == "noNeedTutor"){
                                        needtutor = 0;
                                    } else if (data == "needAccess") {
                                        var Access = confirm("您还未通过培训,点击确定进行预约培训!");
                                        if(Access == true){
                                            var labRoom_id = -1;
                                            var schoolDevice_deviceNumber = -1;
                                            var schoolDevice_deviceName = -1;
                                            var username = -1;
                                            var labRoom_allowAppointment = -1;

                                            var url = "${pageContext.request.contextPath}/labReservation/viewLabRoomTrainingRest/" + labRoom_id + "/" + schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username + "/" + 1 + "/" + labRoomId + "/" + labRoom_allowAppointment;

                                            window.location.href = url;
                                        }else {
                                            window.location.reload();
                                        }
                                    }
                                },
                                error: function (XMLHttpRequest, textStatus, errorThrown) {
                                    alert("请求错误");
                                }
                            })
                        }

                        function submitForAudit() {
                            if ($("#teacher").val() == "") {
                                alert("请选择审核导师！");
                            } else {
                                teacher = $("#teacher").val();
                                saveLabRoomLending();
                            }
                        }

                        function openChooseTeacher() {
                            if (needtutor == 1) {
                                $("#check_user").show();
                                $("#check_user").window('open');
                            } else {
                                saveLabRoomLending();
                            }
                        }
                        //设置
                        function openSetupLink(labRoomId,currpage,type){//将labRoomId page传递到后台
                            var index = layer.open({
                                type: 2,
                                title: '设置',
                                maxmin: true,
                                shadeClose: true,
                                content: "${pageContext.request.contextPath}/device/editLabRoomSettingRest/"+labRoomId+"/"+currpage+"/"+type,
                                end: function(){
                                    window.location.href="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1";
                                }
                            });
                            layer.full(index);
                        }
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>