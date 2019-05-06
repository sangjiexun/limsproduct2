<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>


<html>
<head>
    <title></title>
    <meta name="decorator" content="iframe"/>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no"/>
    <style type="text/css" media="screen">
        @import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/icon.css");
        @import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/gray/easyui.css");
        @import url("${pageContext.request.contextPath}/css/style.css");
        @import url("${pageContext.request.contextPath}/static_limsproduct/css/global_static.css");
    </style>
    <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">

    <!-- 文件上传的样式和js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/device/editDevice.js"></script>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
    <script type="text/javascript">
        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/device/listLabRoomDevice?page=1";
        }
    </script>

    <script>
        //定义全局变量

        var needLoan = "${device.CDictionaryByAllowLending.id}";//是否允许出借
        var appointment = "${device.CDictionaryByAllowAppointment.id}";//是否允许预约
        var needAudit = "${device.CDictionaryByIsAudit.id}";//预约是否需要审核
        var needTEACHER = 0;//是否需要导师审核
        var needAllowSecurityAccess = "${device.CDictionaryByAllowSecurityAccess.id}";//是否需要安全准入trainType
        var trainType = "${device.CDictionaryByTrainType.id}";//培训形式
        var isAuditTimeLimit = "${device.isAuditTimeLimit}";//是否有预约审核时间限制
        var needAllAudits = "${needAllAudits}".replace(/\[|]/g,'').replace(/\s+/g,"").split(",");
        //得到各个参数的值
        $(document).ready(function () {
            // 多级审核页面加载时选择已选定的选项
            var needAllAuditStatus = "${needAllAuditStatus}".replace(/\[|]/g,'').replace(/\s+/g,"").split(",");
            if(needAllAudits[0]) {
                for (var i = 0; i < needAllAudits.length; i++) {
                    document.getElementById(needAllAudits[i] + needAllAuditStatus[i]).checked = true;
                    if(needAllAudits[i] == "TEACHER" && needAllAuditStatus[i] == 1){
                        needTEACHER = 1;
                        $("#" + needAllAudits[i]).after($("#isAuditTimeLimit"));
                    }else if(needAllAudits[i] == "TEACHER"){
                        $("#" + needAllAudits[i]).after($("#isAuditTimeLimit"));
                    }
                }
            }

            $("#needLoan1").click(function () {
                needLoan = $("#needLoan1").val();
            });
            $("#needLoan2").click(function () {
                needLoan = $("#needLoan2").val();
            });

            $("#appointment1").click(function () {
                appointment = $("#appointment1").val();
            });
            $("#appointment2").click(function () {
                appointment = $("#appointment2").val();
            });

            $("#needAudit1").click(function () {
                needAudit = $("#needAudit1").val();
            });
            $("#needAudit2").click(function () {
                needAudit = $("#needAudit2").val();
            });

            if($("#TEACHER1").length > 0) {
                $("#TEACHER1").click(function () {
                    needTEACHER = $("#TEACHER1").val();
                });
            }
            if($("#TEACHER2").length > 0) {
                $("#TEACHER2").click(function () {
                    needTEACHER = $("#TEACHER2").val();
                });
            }

            $("#needAllowSecurityAccess1").click(function () {
                needAllowSecurityAccess = $("#needAllowSecurityAccess1").val();
            });
            $("#needAllowSecurityAccess2").click(function () {
                needAllowSecurityAccess = $("#needAllowSecurityAccess2").val();
            });

            $("#trainType1").click(function () {
                trainType = $("#trainType1").val();
            });
            $("#trainType2").click(function () {
                trainType = $("#trainType2").val();
            });
            $("#trainType3").click(function () {
                trainType = $("#trainType3").val();
            });

            $("#isAuditTimeLimit0").change(function () {
                isAuditTimeLimit = $("#isAuditTimeLimit0").val();
            });
            $("#isAuditTimeLimit1").change(function () {
                isAuditTimeLimit = $("#isAuditTimeLimit1").val();
            });

        });

        //保存参数设置
        function saveDeviceSettingRest(id) {//将labRoomId deviceNumber deviceName page传递到后台

            var needLoan1 = needLoan;//是否允许出借
            var appointment1 = appointment;//是否允许预约
            var needAudit1 = needAudit;//预约是否需要审核
            var needAllowSecurityAccess1 = needAllowSecurityAccess;//是否需要安全准入
            var trainType1 = trainType;//培训形式
            var isAuditTimeLimit1 = isAuditTimeLimit;//是否有预约审核时间限制
            var realAllAudits = [];
            if(needAllAudits[0]) {
                for (var i = 0; i < needAllAudits.length; i++) {
                    for (var j = 1; j < 3; j++) {
                        if (document.getElementById(needAllAudits[i] + j).checked) {
                            realAllAudits.push(document.getElementById(needAllAudits[i] + j).value);
                        }
                    }
                }
            }
            if(!needAllAudits[0]) {
                realAllAudits = [0];
            }
            if(realAllAudits.length != needAllAudits.length && needAllAudits[0]){
                alert("请检查是否选完所有选项！");
                return false;
            }

            var academies = $("#selectedSchoolAcademy").val();

            if(academies == undefined || academies.length == 0){
                academies = ["-1"];
            }

            if (needLoan == "") {
                alert("请选择是否借出");
                return false;
            }

            if (appointment == "") {
                alert("请选择是否预约");
                return false;
            }

            if(appointment1==621 && needAudit == "") {
                alert("请选择是否审核");
                return false;
            }

            if (appointment1==621 && needAudit == 621 && needTEACHER == 1 && isAuditTimeLimit==-1) {
                alert("请选择是否需要导师预约审核时间限制");
                return false;
            }

            if (appointment1==621 && appointment1 == 621 && needAllowSecurityAccess == "") {
                alert("请选择是否安全准入");
                return false;
            }

            if (appointment1==621 && needAllowSecurityAccess == 621 && trainType1=="") {
                alert("请选择培训形式");
                return false;
            }

            if( appointment == 622){
                needAudit1 = -1;
                isAuditTimeLimit1 = -1;
                needAllowSecurityAccess1 = -1;
                trainType1 = -1;
                academies = ["-1"];
            }

            if (needAudit == 622) {
                isAuditTimeLimit1 = -1;
            }

            if (needTEACHER == 2 || needTEACHER == 0){
                isAuditTimeLimit1 = -1;
            }

            if (needAllowSecurityAccess == 622 ) {
                trainType1 = -1;
            }

            var url = "${pageContext.request.contextPath}/device/saveDeviceSettingRest/" + ${labRoomId} +"/" + '${deviceNumber}' +"/" + "${deviceName}" + "/" + ${username}+"/"+${page}+
            "/" + needLoan1
            + "/" + appointment1 + "/" + needAudit1 + "/"
            + needAllowSecurityAccess1 + "/" + trainType1 + "/" + isAuditTimeLimit1 + "/" + id + "/" + "${schoolDevice_allowAppointment}" + "/"
            + academies + "/" + realAllAudits ; //  trainType1 默认为   1 -- 集中时间段培训


            window.location.href = url;
        }


        //是否需要审核的联动
        $(document).ready(function () {
            if (${allowAppointment==2} || ${empty isAudit}) {//是否可以预约联动
                document.getElementById("isAudit").style.display = "None";
                document.getElementById("allowSecurityAccess").style.display = "None";
                document.getElementById("trainingType").style.display = "None";
                document.getElementById("isAuditTimeLimit").style.display = "None";
                document.getElementById("selectAcademy").style.display="None";
                if(needAllAudits[0]) {
                    for (var i = 0; i < needAllAudits.length; i++) {
                        document.getElementById(needAllAudits[i]).style.display = "None";
                    }
                }
            } else {
                document.getElementById("isAudit").style.display = "";
                document.getElementById("selectAcademy").style.display="";
                if (${isAudit==2} || ${empty isAudit}) {//是否需要审核联动
                    if(needAllAudits[0]) {
                        for (var i = 0; i < needAllAudits.length; i++) {
                            document.getElementById(needAllAudits[i]).style.display = "None";
                        }
                    }
                } else {
                    if(needAllAudits[0]) {
                        for (var i = 0; i < needAllAudits.length; i++) {
                            document.getElementById(needAllAudits[i]).style.display = "";
                        }
                    }
                }
                // 与是否教师审核联动
                if (needTEACHER == 2 || needTEACHER == 0) {
                    document.getElementById("isAuditTimeLimit").style.display = "None";
                }else {
                    document.getElementById("isAuditTimeLimit").style.display = "";
                }

                document.getElementById("allowSecurityAccess").style.display = "";
                if (${allowSecurityAccess==2} || ${empty allowSecurityAccess}) {//是否需要安全准入联动
                    document.getElementById("trainingType").style.display = "None";
                } else {
                    document.getElementById("trainingType").style.display = "";//此处本来是""，由于不需要进行培训形式选择才引掉
                }
            }

            $("#needAudit1").change(function () {
                if(needAllAudits[0]) {
                    for (var i = 0; i < needAllAudits.length; i++) {
                        document.getElementById(needAllAudits[i]).style.display = "";
                    }
                }
            });
//是否需要安全准入联动
            $("#needAllowSecurityAccess1").change(function () {
                document.getElementById("trainingType").style.display = "";//此处本来是""，由于不需要进行培训形式选择才引掉
            });
            // 当需要教师审核时显示审核限制时间选项
            if($("#TEACHER1").length > 0) {
                $("#TEACHER1").change(function () {
                    document.getElementById("isAuditTimeLimit").style.display = "";
                });
            }


//是否可以预约联动
            $("#appointment1").change(function () {
                document.getElementById("allowSecurityAccess").style.display = "";
                document.getElementById("isAudit").style.display = "";
                document.getElementById("selectAcademy").style.display="";
                if (${isAudit==2} || ${empty isAudit}) {//是否需要审核联动
                    if(needAllAudits[0]) {
                        for (var i = 0; i < needAllAudits.length; i++) {
                            document.getElementById(needAllAudits[i]).style.display = "None";
                        }
                    }
                } else {
                    if(needAllAudits[0]) {
                        for (var i = 0; i < needAllAudits.length; i++) {
                            document.getElementById(needAllAudits[i]).style.display = "";
                        }
                    }
                }
            });
        });

        $(document).ready(function () {
            $("#needAudit2").change(function () {
                document.getElementById("isAuditTimeLimit").style.display = "None";
                if(needAllAudits[0]) {
                    for (var i = 0; i < needAllAudits.length; i++) {
                        document.getElementById(needAllAudits[i]).style.display = "None";
                    }
                }
            });
            if ($("#needAudit2").prop("checked")) {
                $("#needAudit2").change();
            }
            $("#needAllowSecurityAccess2").change(function () {
                document.getElementById("trainingType").style.display = "None";
            });
            if($("#TEACHER2").length > 0) {
                $("#TEACHER2").change(function () {
                    document.getElementById("isAuditTimeLimit").style.display = "None";
                });
            }

            $("#appointment2").change(function () {
                document.getElementById("allowSecurityAccess").style.display = "None";
                document.getElementById("isAudit").style.display = "None";
                document.getElementById("trainingType").style.display = "None";
                document.getElementById("isAuditTimeLimit").style.display = "None";
                document.getElementById("selectAcademy").style.display="None";
                if(needAllAudits[0]) {
                    for (var i = 0; i < needAllAudits.length; i++) {
                        document.getElementById(needAllAudits[i]).style.display = "None";
                    }
                }
            });
        });
    </script>

    <style>
        .tool-box2 th {
            text-align: left;
        }

        table label {
            position: relative;
            top: -6px;
            margin-left: 3px;
        }
    </style>
</head>


<body>

<div class="right-content">
    <div class="tool-box1">
        <table class="equip_tab">
            <tr>
                <td>
                    <span>设备编号：</span>
                    <p>${device.schoolDevice.deviceNumber}</p>
                </td>
                <td>
                    <span>设备名称：</span>
                    <p class="equip_name">${device.schoolDevice.deviceName}</p>
                </td>
                <td>
                    <span>仪器型号：</span>
                    <p>${device.schoolDevice.devicePattern}</p>
                </td>
            </tr>
            <tr>
                <td>
                    <span>所在<spring:message code="all.trainingRoom.labroom"/>：</span>
                    <p>${device.labRoom.labRoomName}</p>
                </td>
                <td>
                    <span>生产国别：</span>
                    <p>${device.schoolDevice.deviceCountry}</p>
                </td>
                <td>
                    <span>生产厂家：</span>
                    <p>${device.schoolDevice.manufacturer}</p>
                </td>
            </tr>
        </table>
    </div>
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab" tabindex="0">
                <a href="javascript:void(0);" onclick="editDeviceInfoRest(${device.id})">设备详情</a>
            </li>
            <li class="TabbedPanelsTab" tabindex="0">
                <a href="javascript:void(0);" onclick="editAgentInfoRest(${device.id})">物联硬件</a>
            </li>
            <%-- <c:if test="${device.CActiveByAllowSecurityAccess.id == 1}"> --%>
            <c:if test="${device.CDictionaryByAllowSecurityAccess.CCategory=='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber=='1'}">
                <li class="TabbedPanelsTab" tabindex="0">
                    <a href="javascript:void(0);" onclick="editDeviceTrainingRest(${device.id})">培训计划</a>
                </li>
            </c:if>
            <li class="TabbedPanelsTab selected" tabindex="0">
                <a href="javascript:void(0);" onclick="editDeviceSettingRest(${device.id})">参数设置</a>
            </li>
            <li class="TabbedPanelsTab" tabindex="0">
                <a href="javascript:void(0);" onclick="editDeviceImageRest(${device.id})">相关图片</a>
            </li>
            <li class="TabbedPanelsTab" tabindex="0">
                <a href="javascript:void(0);" onclick="editDeviceVideoRest(${device.id})">相关视频</a>
            </li>
            <li class="TabbedPanelsTab" tabindex="0">
                <a href="javascript:void(0);" onclick="editDeviceDocumentRest(${device.id})">相关文档</a>
            </li>
            <li class="TabbedPanelsTab" tabindex="0">
                <a href="javascript:void(0);" onclick="editDeviceDimensionalCodeRest(${device.id})">二维码</a>
            </li>
            <c:if test="${(device.CDictionaryByAllowSecurityAccess.CCategory =='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber == '1' && device.CDictionaryByTrainType.CNumber == '3')}">
                <li class="TabbedPanelsTab" tabindex="0">
                        <%--<a href="${pageContext.request.contextPath}/tcoursesite/question/findQuestionList?tCourseSiteId=${device.id}"target="_blank">题库</a>
                        --%><a href="#" onclick="findQuestionList(${device.id})">题库</a>
                </li>
                <li class="TabbedPanelsTab" tabindex="0">
                    <a href="#" onclick="findTestList(${device.id})">考试</a>
                </li>
            </c:if>
            <c:if test="${1 eq 1}"> <!-- 化工学院 -->
                <li class="TabbedPanelsTab" tabindex="0">
                    <a href="javascript:void(0);" onclick="editDeviceAttentionRest(${device.id})">设备安全协议</a>
                </li>
            </c:if>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">参数设置</div>
                        <c:if test="${flag }">
                            <a class="btn btn-new" href="#" onclick="LabRoomSettingLink();" >编辑实验室相关配置项</a>
                        </c:if>
                        <a class="btn btn-new" herf="#" onclick="closeMyWindow()">返回</a>
                    </div>
                    <form:form action="${pageContext.request.contextPath}/device/saveDeviceSetting" method="post"
                               modelAttribute="device">
                        <div class="new-classroom">
                            <table>
                                <tr id="allowLending">
                                    <td>是否允许借用:</td>
                                    <td>
                                        <c:forEach items="${CActives}" var="active" varStatus="i">
                                            <form:radiobutton path="CDictionaryByAllowLending.id" value="${active.id}"
                                                              id="needLoan${i.count}"/><label
                                                for="needLoan${i.count}">${active.CName}</label>
                                        </c:forEach>
                                    </td>
                                </tr>

                                <tr id="allowAppointment">
                                    <td>是否允许预约:</td>
                                    <td>
                                        <c:forEach items="${CActives}" var="active" varStatus="i">
                                            <%-- <form:radiobutton path="CActiveByAllowAppointment.id" value="${active.id}" id="appointment${i.count}" /><label for="appointment${i.count}">${active.name}</label> --%>
                                            <form:radiobutton path="CDictionaryByAllowAppointment.id"
                                                              value="${active.id}" id="appointment${i.count}"/><label
                                                for="appointment${i.count}">${active.CName}</label>
                                        </c:forEach>
                                    </td>
                                </tr>


                                <tr id="isAudit">
                                    <td>预约是否需要审核:</td>
                                    <td>
                                        <c:forEach items="${CActives}" var="activ" varStatus="i">
                                            <%-- <form:radiobutton id="needAudit${i.count}" path="CActiveByIsAudit.id" value="${activ.id}"  /><label for="needAudit${i.count}">${activ.name}</label> --%>
                                            <form:radiobutton id="needAudit${i.count}" path="CDictionaryByIsAudit.id"
                                                              value="${activ.id}"/><label
                                                for="needAudit${i.count}">${activ.CName}</label>
                                        </c:forEach>
                                    </td>
                                </tr>
                                <tr id="isAuditTimeLimit">
                                    <td>是否需要导师预约审核时间限制:</td>
                                    <td>
                                        <form:radiobutton path="isAuditTimeLimit" value="1"
                                                          id="isAuditTimeLimit1"/><label
                                            for="isAuditTimeLimit0">是</label>
                                        <form:radiobutton path="isAuditTimeLimit" value="0"
                                                          id="isAuditTimeLimit0"/><label
                                            for="isAuditTimeLimit1">否</label>
                                    </td>
                                </tr>
                                <c:forEach items="${needAllAudits}" var="needAllAudit" varStatus="i">
                                    <tr id="${needAllAudit}">
                                        <td>是否需要${authNames[i.count-1]}审核:</td>
                                        <td>
                                            <input type="radio" name="${needAllAudit}" id="${needAllAudit}1" value="1"/>
                                            <label for="${needAllAudit}1">是</label>
                                            <input type="radio" name="${needAllAudit}" id="${needAllAudit}2" value="2"/>
                                            <label for="${needAllAudit}2">否</label>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <tr id="allowSecurityAccess">
                                    <td>是否需要安全准入:</td>
                                    <td>
                                        <c:forEach items="${CActives}" var="activ" varStatus="i">
                                            <%-- <form:radiobutton path="CActiveByAllowSecurityAccess.id" value="${activ.id}" id="needAllowSecurityAccess${i.count}"/>${activ.name} --%>
                                            <form:radiobutton path="CDictionaryByAllowSecurityAccess.id"
                                                              value="${activ.id}"
                                                              id="needAllowSecurityAccess${i.count}"/>${activ.CName}
                                        </c:forEach>
                                    </td>
                                </tr>

                                <tr id="trainingType">
                                    <td>培训形式:</td>
                                    <td>
                                        <c:forEach items="${CTrainingTypes}" var="type" varStatus="i">
                                            <%-- <form:radiobutton path="CTrainingType.id" value="${type.id}" id="trainType${i.count}"/><label for="trainType${i.count}">${type.name}</label> --%>
                                            <form:radiobutton path="CDictionaryByTrainType.id" value="${type.id}"
                                                              id="trainType${i.count}"/><label
                                                for="trainType${i.count}">${type.CName}</label>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </form:form>
                    <div class="TabbedPanels" id="selectAcademy">
                        <table class="tab_lab" style="margin:10px 0 0;">
                            <tr>
                                <th>开放范围</th>
                                <td width="90%">
                                    <select class="chzn-select" multiple id="selectedSchoolAcademy"
                                            name="selectedSchoolAcademy">
                                        <c:forEach items="${schoolAcademyList}" var="schoolAcademy" varStatus="i">
                                            <c:if test="${selectedSchoolAcademies.contains(schoolAcademy)}">
                                                <option value="${schoolAcademy.academyNumber}"
                                                        selected>${schoolAcademy.academyName}</option>
                                            </c:if>
                                            <c:if test="${!selectedSchoolAcademies.contains(schoolAcademy)}">
                                                <option value="${schoolAcademy.academyNumber}">${schoolAcademy.academyName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="width: 200px; margin: 20px auto">
                        <input type="button" onclick="saveDeviceSettingRest(${device.id});" value="确定">
                        <input type="button" onclick="closeMyWindow();" value="取消">
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="labRoomId" value="${labRoomId }">
        <input type="hidden" id="deviceName" value="${deviceName }">
        <input type="hidden" id="deviceNumber" value="${deviceNumber }">
        <input type="hidden" id="username" value="${username }">
        <input type="hidden" id="page" value="${page }">
        <input type="hidden" id="pageContext" value="${pageContext.request.contextPath }">
        <input type="hidden" id="schoolDevice_allowAppointment" value="${schoolDevice_allowAppointment}">
    </div>
</div>
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
        '.chzn-select-width': {width: "100%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
    function closeMyWindow(){
        var index=parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>
