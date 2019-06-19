<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.cookie.js"></script>
    <script type="text/javascript">
        // 显示各级审核选框
        function showAuditLevel() {
            var auditLevel = $("#auditLevel").val();
            for (var i = 0; i < auditLevel; i++) {
                $($("#AuditLevels").children().get(i)).css("display", "");
            }
            for (var i = auditLevel; i < $("#AuditLevels").children().size(); i++) {
                $($("#AuditLevels").children().get(i)).css("display", "none");
            }
        };

        // 保存设置
        function saveProjectAudit(data) {
            <c:if test="${businessName eq 'DeviceRepair'}">
            saveConfirmAuthority(1);
            saveConfirmAuthority(2);
            saveConfirmAuthority(3);
            </c:if>
            <c:if test="${businessName eq 'EquipmentLendingAudit'}">
            saveConfirmAuthority(4)
            </c:if>
            var auditLevel = $("#auditLevel").val();
            for (var i = auditLevel; i < $("#AuditLevels").children().size(); i++) {
                $($("#AuditLevels").children().get(i)).find("select").val("-2");
            }
            var auditLevelConfig = "";
            var array = new Array();
            if ($("#levelFirstAudit").val() != -2) {
                array.push($("#levelFirstAudit").val())
            }
            if ($("#levelSecondAudit").val() != -2) {
                array.push($("#levelSecondAudit").val())
            }
            if ($("#levelThirdAudit").val() != -2) {
                array.push($("#levelThirdAudit").val())
            }
            if ($("#levelFourthAudit").val() != -2) {
                array.push($("#levelFourthAudit").val())
            }
            if ($("#levelFifthAudit").val() != -2) {
                array.push($("#levelFifthAudit").val())
            }
            auditLevelConfig = array.join(",");
            var range = $("#selectedSchoolAcademy").val();
            if (range == null && ${businessName ne 'OperationItem' and businessName ne 'TimetableAudit' and businessName ne 'VirtualImageReservation' and businessName ne 'SelfTimetableAudit'
             and businessName ne 'AdjustTimetableAudit' and businessName ne 'CloseTimetableAudit' and businessName ne 'OperationItemNewAudit' and businessName ne 'StationReservation'
             and businessName ne 'CancelLabRoomReservation'}) {
                alert("确认权限已保存，请选择开放范围！");
                window.location.reload();
                return false;
            }
            if (${businessName eq 'OperationItem' or businessName eq 'TimetableAudit'or businessName eq 'VirtualImageReservation' or businessName eq 'SelfTimetableAudit'
             or businessName eq 'AdjustTimetableAudit' or businessName eq 'CloseTimetableAudit' or businessName eq 'OperationItemNewAudit'
             or businessName eq 'CancelLabRoomReservation'}) {
                range = "";
            }
            var rangeStr = range.toString();
            var type = data;
            var myData = {
                "auditLevelConfig": auditLevelConfig,
                "type": type,
                "range": rangeStr,
                "allType": ${allType}
            };
            $.ajax({
                url: "../audit/saveAuditLevelConfig",
                type: 'POST',
                async: false,
                data: myData,
                success: function (data) {
                    if (data == 'success') {
                        alert("保存设置成功");
                        location.reload();
                    }
                }
            });
        }

        function deleteAudit(acno, type) {
            var myData = {
                "type": type,
                "deleteNumber": acno
            };
            $.ajax({
                url: "../audit/deleteAudit",
                type: 'POST',
                async: false,
                data: myData,
                success: function (data) {
                    if (data == 'success') {
                        alert("删除成功");
                        location.reload();
                    }
                }
            });
        }
    </script>
    <script type="text/javascript">
        // 保存确认权限
        function saveConfirmAuthority(type) {
            var data;
            if (type == 1) {
                data = {
                    authName: $("#acceptance").val(),
                    saveType: "RepairAcceptance"
                }
            } else if (type == 2) {
                data = {
                    authName: $("#write").val(),
                    saveType: "RepairWrite"
                }
            } else if (type == 3) {
                data = {
                    authName: $("#record").val(),
                    saveType: "RepairRecord"
                }
            } else if (type == 4) {
                data = {
                    authName: $("#return").val(),
                    saveType:"EquipmentReturn"
                }
            }
            $.ajax({
                url: "../audit/saveConfirmAuthority",
                type: "POST",
                async: false,
                data: data,
                success: function (data) {
                },
                error: function () {
                    if (type == 1) {
                        alert("保存验收权限失败！");
                    } else if (type == 2) {
                        alert("保存填写权限失败！");
                    } else if (type == 3) {
                        alert("保存入账权限失败！");
                    } else if (type == 4) {
                        alert("保存归还权限失败！")
                    }
                }
            });
        }
    </script>
    <script type="text/javascript">
        function changeGrade() {
            window.location.href="${pageContext.request.contextPath}/audit/auditSetting?flag=11&grade="+$("#selectedGraded").val();
        }
    </script>
    <style>
        .popup_btns {
            padding: 20px 1%;
        }

        .chzn-container {
            width: 100% !important;
        }

        .chzn-container-single .chzn-single {
            box-shadow: none;
            border: 1px solid #cdcdcd;
        }

        .chzn-container-multi .chzn-choices {
            line-height: 23px;
            background-image: none;
            box-shadow: none;
            border-radius: 3px;
            border: 1px solid #cdcdcd;
        }

        .chzn-container-multi .chzn-choices li.search-choice {
            margin: 1px 0 1px 5px;
            padding: 2px 20px 4px 5px;
        }

        .chzn-container-multi .chzn-choices li.search-field input[type="text"] {
            white-space: nowrap;
        }

        .chzn-container .chzn-drop {
            font-weight: normal;
            box-shadow: none;
        }
    </style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.system.management"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.audit.setting"/></a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab selected iStyle_Mark iStyle_ActiveMark" id="s1" onclick="changeTag(1)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=1">排课审核设置</a>
            </li>
            <li class="TabbedPanelsTab" id="s2" onclick="changeTag(2)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=2"><spring:message
                    code="all.trainingRoom.labroom"/>预约审核设置</a></li>
            <li class="TabbedPanelsTab" id="s3" onclick="changeTag(3)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=3"><spring:message
                    code="all.training.name"/>项目审核设置</a></li>
            <li class="TabbedPanelsTab" id="s4" onclick="changeTag(4)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=4">设备维修设置</a>
            </li>
            <li class="TabbedPanelsTab" id="s5" onclick="changeTag(5)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=5">设备预约审核设置</a>
            </li>
            <li class="TabbedPanelsTab" id="s6" onclick="changeTag(6)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=6">虚拟镜像预约审核设置</a>
            </li>
            <li class="TabbedPanelsTab" id="s7" onclick="changeTag(7)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=7">自主排课审核设置</a>
            </li>
            <li class="TabbedPanelsTab" id="s8" onclick="changeTag(8)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=8">调课审核设置</a>
            </li>
            <li class="TabbedPanelsTab" id="s9" onclick="changeTag(9)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=9">停课审核设置</a>
            </li>
            <li class="TabbedPanelsTab" id="s10" onclick="changeTag(10)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=10">实验项目（新）设置</a>
            </li>
            <li class="TabbedPanelsTab" id="s11" onclick="changeTag(11)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=11">工位预约审核设置</a>
            </li>
            <li class="TabbedPanelsTab" id="s12" onclick="changeTag(12)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=12">取消预约审核设置</a>
            </li>
            <li class="TabbedPanelsTab" id="s13" onclick="changeTag(13)"><a
                    href="${pageContext.request.contextPath}/audit/auditSetting?flag=13">设备借用审核设置</a>
            </li>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <table class="tab_lab" style="margin:10px 0 0;">
                        <%--<c:if test="${isGraded}">
                            <tr>
                                <th>实验室等级</th>
                                <td colspan="3">
                                    <select id="selectedGraded" onchange="changeGrade()">
                                            <option value="1" <c:if test="${grade eq 1}">selected</c:if>>一级</option>
                                            <option value="2" <c:if test="${grade eq 2}">selected</c:if>>二级</option>
                                    </select>
                                </td>
                            </tr>
                        </c:if>--%>
                        <c:if test="${businessName ne 'OperationItem' and businessName ne 'TimetableAudit' and businessName ne 'VirtualImageReservation' and businessName ne 'SelfTimetableAudit'
                         and businessName ne 'AdjustTimetableAudit' and businessName ne 'CloseTimetableAudit' and businessName ne 'OperationItemNewAudit'
                         and businessName ne 'CancelLabRoomReservation'}">
                            <tr>
                                <th>开放范围</th>
                                <td colspan="3">
                                    <select class="chzn-select" multiple id="selectedSchoolAcademy">
                                        <%--<c:if test="${isAll ne 1}">
                                            <option value="-1">全校</option>
                                        </c:if>--%>
                                        <c:forEach items="${schoolAcademyList}" var="schoolAcademy" varStatus="i">
                                            <option value="${schoolAcademy.academyNumber}">${schoolAcademy.academyName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </c:if>
                        <span id="isneedAudit" name="isneedAudit">
                        <tr>
                            <th rowspan="2">需要几级审核</th>
                            <td>
                                <select id="auditLevel" class="chzn-select" onchange="showAuditLevel()">
                                    <option value="-2">请选择</option>
                                    <c:if test="${level eq 1}">
                                        <option value="1" selected>一级审核</option>
                                    </c:if>
                                    <c:if test="${level ne 1}">
                                        <option value="1">一级审核</option>
                                    </c:if>
                                    <c:if test="${level eq 2}">
                                        <option value="2" selected="selected">二级审核</option>
                                    </c:if>
                                    <c:if test="${level ne 2}">
                                        <option value="2">二级审核</option>
                                    </c:if>
                                    <c:if test="${level eq 3}">
                                        <option value="3" selected>三级审核</option>
                                    </c:if>
                                    <c:if test="${level ne 3}">
                                        <option value="3">三级审核</option>
                                    </c:if>
                                    <c:if test="${level eq 4}">
                                        <option value="4" selected>四级审核</option>
                                    </c:if>
                                    <c:if test="${level ne 4}">
                                        <option value="4">四级审核</option>
                                    </c:if>
                                    <c:if test="${level eq 5}">
                                        <option value="5" selected>五级审核</option>
                                    </c:if>
                                    <c:if test="${level ne 5}">
                                        <option value="5">五级审核</option>
                                    </c:if>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <!-- 审核等级设置开始 -->
                                <div id="AuditLevels">
                                        <span id="AuditLevel1" style="display: none">
                                            <label>一级审核：</label>
                                            <div style="display: inline-block;margin-left: 0;"
                                                 class="layui-input-block">
                                                <select id="levelFirstAudit">
                                                    <option value="-2">请选择</option>
                                                    <c:forEach items="${authorities}" var="a" varStatus="i">
                                                        <option value="${a.authorityName}">${a.cname}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </span>
                                    <span id="AuditLevel2" style="display: none">
                                            <label>二级审核：</label>
                                            <div style="display: inline-block;margin-left: 0;"
                                                 class="layui-input-block">
                                                <select id="levelSecondAudit">
                                                    <option value="-2">请选择</option>
                                                    <c:forEach items="${authorities}" var="a" varStatus="i">
                                                        <option value="${a.authorityName}">${a.cname}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </span>
                                    <span id="AuditLevel3" style="display: none">
                                            <label>三级审核：</label>
                                            <div style="display: inline-block;margin-left: 0;"
                                                 class="layui-input-block">
                                                <select id="levelThirdAudit">
                                                    <option value="-2">请选择</option>
                                                    <c:forEach items="${authorities}" var="a" varStatus="i">
                                                        <option value="${a.authorityName}">${a.cname}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </span>
                                    <span id="AuditLevel4" style="display: none">
                                            <label>四级审核：</label>
                                            <div style="display: inline-block;margin-left: 0;;"
                                                 class="layui-input-block">
                                                <select id="levelFourthAudit">
                                                    <option value="-2">请选择</option>
                                                    <c:forEach items="${authorities}" var="a" varStatus="i">
                                                        <option value="${a.authorityName}">${a.cname}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </span>
                                    <span id="AuditLevel5" style="display: none">
                                            <label>五级审核：</label>
                                            <div style="display: inline-block;margin-left: 0;"
                                                 class="layui-input-block">
                                                <select id="levelFifthAudit">
                                                    <option value="-2">请选择</option>
                                                    <c:forEach items="${authorities}" var="a" varStatus="i">
                                                        <option value="${a.authorityName}">${a.cname}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </span>
                                </div>
                                <!-- 审核等级设置结束 -->
                            </td>
                        </tr>

                <c:if test="${businessName eq 'DeviceRepair'}">
                            <tr>
                                <th>确认权限</th>
                                <td>
                                    <span id="acceptanceSpan">
                                            <label>验收：</label>
                                            <div style="display: inline-block;margin-left: 0;"
                                                 class="layui-input-block">
                                    <select name="acceptance" id="acceptance">
                                            <option value="">请选择</option>
                                        <c:forEach items="${authorities}" var="a" varStatus="i">
                                            <c:if test="${acceptanceVal eq a.authorityName}">
                                                <option value="${a.authorityName}" selected>${a.cname}</option>
                                            </c:if>
                                            <c:if test="${acceptanceVal ne a.authorityName}">
                                                <option value="${a.authorityName}">${a.cname}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                            </div>
                                        </span>
                                    <span id="writeSpan">
                                            <label>填写：</label>
                                            <div style="display: inline-block;margin-left: 0;"
                                                 class="layui-input-block">
                                    <select name="write" id="write">
                                            <option value="">请选择</option>
                                        <c:forEach items="${authorities}" var="a" varStatus="i">
                                            <c:if test="${writeVal eq a.authorityName}">
                                                <option value="${a.authorityName}" selected>${a.cname}</option>
                                            </c:if>
                                            <c:if test="${writeVal ne a.authorityName}">
                                                <option value="${a.authorityName}">${a.cname}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                            </div>
                                        </span>
                                    <span id="recordSpan">
                                            <label>入账：</label>
                                            <div style="display: inline-block;margin-left: 0;"
                                                 class="layui-input-block">
                                    <select name="record" id="record">
                                            <option value="">请选择</option>
                                        <c:forEach items="${authorities}" var="a" varStatus="i">
                                            <c:if test="${recordVal eq a.authorityName}">
                                                <option value="${a.authorityName}" selected>${a.cname}</option>
                                            </c:if>
                                            <c:if test="${recordVal ne a.authorityName}">
                                                <option value="${a.authorityName}">${a.cname}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                                <label><span style="color: red;">此处为全校设置，请谨慎修改</span> </label>
                                            </div>
                                        </span>
                                </td>
                            </tr>
                </c:if>
                <c:if test="${businessName eq 'EquipmentLendingAudit'}">
                    <tr>
                                <th>确认权限</th>
                                <td>
                                    <span id="returnSpan">
                                            <label>归还：</label>
                                            <div style="display: inline-block;margin-left: 0;"
                                                 class="layui-input-block">
                                    <select name="return" id="return">
                                            <option value="">请选择</option>
                                        <c:forEach items="${authorities}" var="a" varStatus="i">
                                            <c:if test="${returnVal eq a.authorityName}">
                                                <option value="${a.authorityName}" selected>${a.cname}</option>
                                            </c:if>
                                            <c:if test="${acceptanceVal ne a.authorityName}">
                                                <option value="${a.authorityName}">${a.cname}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                                <label><span style="color: red;">此处为全校设置，请谨慎修改</span> </label>
                                            </div>
                                        </span>
                                </td>
                    </tr>
                </c:if>
                        </span>
                    </table>
                    <div class="popup_btns">
                        <input type="button" class="btn" onclick="saveProjectAudit('${businessName}');
                                " value="新增<c:if test="${businessName eq 'DeviceRepair'}">并保存</c:if>"/>
                    </div>
                    <table>
                        <thead>
                        <tr>
                            <th>学院</th>
                            <th>审核等级</th>
                            <th>审核权限</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${isAll eq 1}">
                            <tr>
                                <td>${showList['-1'][0]}</td>
                                <td>${showList['-1'][1]}</td>
                                <td>${showList['-1'][2]}</td>
                                <td><a href="javascript:void(0)" onclick="deleteAudit('-1','${businessName}')">删除</a>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${isAll eq 2}">
                            <tr>
                                <td>${showList[''][0]}</td>
                                <td>${showList[''][1]}</td>
                                <td>${showList[''][2]}</td>
                                <td><a href="javascript:void(0)" onclick="deleteAudit('','${businessName}')">删除</a>
                                </td>
                            </tr>
                        </c:if>
                        <c:forEach items="${selectedAcno}" var="a">
                            <tr>
                                <td>${showList[a.academyNumber][0]}</td>
                                <td>${showList[a.academyNumber][1]}</td>
                                <td>${showList[a.academyNumber][2]}</td>
                                <td><a href="javascript:void(0)"
                                       onclick="deleteAudit('${a.academyNumber}','${businessName}')">删除</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <c:if test="${businessName eq 'DeviceRepair'}">
                <div class="content-box">
                    <div class="title">
                        已保存设备维修确认权限
                        <label><span style="color: red;">此处为全校设置，请谨慎修改</span> </label>
                    </div>
                    <table>
                        <tr>
                            <th><span style="color: blue">验收权限：</span></th>
                            <td>
                                <c:forEach items="${authorities}" var="a" varStatus="i">
                                    <c:if test="${acceptanceVal eq a.authorityName}">
                                        <option value="${a.authorityName}" selected>${a.cname}</option>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <th><span style="color: blue">填写权限：</span></th>
                            <td>
                                <c:forEach items="${authorities}" var="a" varStatus="i">
                                    <c:if test="${writeVal eq a.authorityName}">
                                        <option value="${a.authorityName}" selected>${a.cname}</option>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <th><span style="color: blue">入账权限：</span></th>
                            <td>
                                <c:forEach items="${authorities}" var="a" varStatus="i">
                                    <c:if test="${recordVal eq a.authorityName}">
                                        <option value="${a.authorityName}" selected>${a.cname}</option>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
            </div>
            </c:if>
                <c:if test="${businessName eq 'EquipmentLendingAudit'}">
                    <div class="content-box" >
                        <div class="title">
                            已保存设备借用审核确认权限
                            <label><span style="color: red;">此处为全校设置，请谨慎修改</span> </label>
                        </div>
                        <table>
                            <tr>
                                <th><span style="color: blue">归还权限：</span></th>
                                <td>
                                    <c:forEach items="${authorities}" var="a" varStatus="i">
                                        <c:if test="${returnVal eq a.authorityName}">
                                            <option value="${a.authorityName}" selected>${a.cname}</option>
                                        </c:if>
                                    </c:forEach>
                                </td>


                            </tr>
                        </table>
                    </div>
                </c:if>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    $(function () {
        showAuditLevel();
        // 已设置的审核权限
        var array = "${auditConfigLevel}".replace(/ /g, '').replace('[', '').replace(']', '').split(',');
        // 遍历已设置的审核权限并赋值
        $.each(array, function (index, value) {
            $("#AuditLevel" + (index + 1)).find("select").val(value);
        });
        // 原来的判断修改为如下
        // 标签栏数量，增加标签栏时需修改此值
        var titleNum = 13;
        // 遍历所有标签栏修改样式
        for (var i = 1; i <= titleNum; i++) {
            if($.cookie("auditSettingTag") == i){
                $("#s" + i).addClass("selected iStyle_Mark iStyle_ActiveMark");
            }else{
                $("#s" + i).removeClass("selected iStyle_Mark iStyle_ActiveMark");
            }
        }
    });

    function changeTag(id) {
        $.cookie("auditSettingTag", id, {"path": "/", expires: 30});
    }
</script>
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
</script>
<!-- 下拉框的js -->

</body>
</html>
