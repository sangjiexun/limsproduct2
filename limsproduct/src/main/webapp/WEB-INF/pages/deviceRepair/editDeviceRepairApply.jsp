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
    <script src="${pageContext.request.contextPath}/js/SpryTabbedPanels.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/css/SpryTabbedPanels.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.8.3.min.js"></script>
    <!-- 文件上传的样式和js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->


    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

    <style>
        .tool-box2 th {
            text-align: left;
        }
    </style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">实验设备管理</a></li>
            <li class="end"><a href="javascript:void(0)">设备维修申请单编辑</a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">

                    <div class="title">
                        <div id="title">设备维修申请单</div>
                    </div>
                    <form:form id="myForm"
                               action="${pageContext.request.contextPath}/deviceRepair/saveDeviceRepairApply"
                               method="POST"
                               modelAttribute="deviceRepair">
                        <table>
                            <input id="batchDeviceEles" name="batchDeviceEles" type="hidden" />
                            <input id="batchLabRoomEles" name="batchLabRoomEles" type="hidden" />
                            <form:input path="id" id="deviceRepairId" name="deviceRepairId" type="hidden" />
                            <tr>
                                <th>物资名称</th>
                                <td>
                                    <select class="chzn-select" id="deviceName" name="assetName" style="width:180px"
                                            onchange="changeAsset()">
                                        <option value="" selected>填写物资</option>
                                        <c:forEach items="${devices}" var="current">
                                            <c:if test="${current.schoolDevice.deviceName ne deviceRepair.deviceName || current.labRoom.labRoomName ne deviceRepair.labAddress}" >
                                                <option value="${current.schoolDevice.deviceNumber}">${current.schoolDevice.deviceName}[${current.schoolDevice.deviceNumber}] </option>
                                            </c:if>
                                            <c:if test="${current.schoolDevice.deviceName eq deviceRepair.deviceName && current.labRoom.labRoomName eq deviceRepair.labAddress}" >
                                                <option value="${current.schoolDevice.deviceNumber}" selected>${current.schoolDevice.deviceName}[${current.schoolDevice.deviceNumber}]  </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <form:input path="deviceName" type="text" name="assetName" id="assetName"/>
                                </td>
                                <th>实验室名称</th>
                                <td>
                                    <select class="chzn-select" id="labRoomName" name="labRoomName" style="width:180px"
                                            onchange="changeLabRoom()">
                                        <option value="" selected>填写实验室</option>
                                        <c:forEach items="${labRooms}" var="current">
                                            <c:if test="${current.labRoomName ne deviceRepair.labAddress}" >
                                                <option value="${current.id}">${current.labRoomName} </option>
                                            </c:if>
                                            <c:if test="${current.labRoomName eq deviceRepair.labAddress}" >
                                                <option value="${current.id}" selected>${current.labRoomName} </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <form:input path="labAddress" type="text" name="labRoomName" id="placeName"/>
                                </td>
                            </tr>
                            <tr>
                                <th>报修人</th>
                                <td>
                                    <form:select class="chzn-select" id="reportUser" path="reportUser">
                                        <option value="">请选择</option>
                                        <c:forEach items="${users}" var="user" varStatus="u">
                                            <c:if test="${user.username eq username}">
                                                <option value="${user.username}" selected>${user.cname}</option>
                                            </c:if>
                                            <c:if test="${user.username ne username}">
                                                <option value="${user.username}">${user.cname}</option>
                                            </c:if>
                                        </c:forEach>
                                    </form:select>
                                </td>
                                <th>预计金额</th>
                                <td>
                                    <form:input path="expectAmount" type="number" id="expectAmount" />
                                </td>
                            </tr>
                            <tr>
                                <th>报修描述(原因)</th>
                                <td colspan="3">
                                    <form:textarea path="content" id="content" name="content" rows="5"></form:textarea>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" style="text-align:right;">
                                    <input class="btn btn-new" type="button" value="提交" onclick="beforeSubmit()"/>
                                </td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
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
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<script type="text/javascript">
    var type = 2;
    $(document).ready(function () {
        if(${deviceRepair.type eq 1}){
            $("#assetName").val("");
            $("#assetName").attr({"disabled":"disabled"});
            type = 1;
        }
        if($("#labRoomName").val() != ""){
            $("#placeName").val("");
            $("#placeName").attr({"disabled":"disabled"});
        }
    });

    /**
     * 表单发送前的方法
     */
    function beforeSubmit() {
        //获取sessionstorage中的设备编号
        var selectedDeviceStr = $("#deviceName").val() + "_" + 1;
        if(selectedDeviceStr == null || selectedDeviceStr == '_1' || selectedDeviceStr == ''){
            selectedDeviceStr = $("#assetName").val() + "_" + type;
            if(selectedDeviceStr == null || selectedDeviceStr == '_2' || selectedDeviceStr == '_1'){
                layer.msg('您还没有选择任何设备，请选择!', {
                    icon: 1,
//                shade: [0.8, '#393D49'] // 透明度  颜色
                });
                return false;
            }
        }
        var selectLabRoom = $("#labRoomName").val() + "_" + 1;
        if(selectLabRoom == null || selectLabRoom == '_1'){
            selectLabRoom = $("#placeName").val() + "_" + 2;
            if(selectLabRoom == null || selectLabRoom == '_2'){
                layer.msg('您还没有选择任何实验室，请选择!');
                return false;
            }
        }
        $("#batchDeviceEles").val(selectedDeviceStr);
        $("#batchLabRoomEles").val(selectLabRoom);
        if($("#reportUser").val() == ""){
            layer.msg("请选择一个报修人!");
            return false;
        }
        if($("#expectAmount").val() == ""){
            layer.msg("您还没有填写预计金额，请填写!");
            return false;
        }
        $("#myForm").submit();
    }
    function changeLabRoom(){
        if($("#labRoomName").val() == "") {
            $("#placeName").removeAttr("disabled");
            $("#deviceName").html("<option value=\"\" selected>填写物资</option>");
            $("#deviceName").trigger("liszt:updated");
            $("#deviceName").chosen();
            changeAsset();
        }
        else {
            $("#placeName").val("");
            $("#placeName").attr({"disabled": "disabled"});
            $.ajax({
                url: "${pageContext.request.contextPath}/deviceRepair/getLabRoomDevices",
                type: "post",
                dataType: "text",
                data: {
                    labAddress: $("#labRoomName").val()
                },
                success: function (data) {
                    $("#deviceName").html(data);
                    $("#deviceName").trigger("liszt:updated");
                    $("#deviceName").chosen();
                    changeAsset();
                }
            });
        }
    }
    function changeAsset() {
        if($("#deviceName").val() == "") {
            $("#assetName").removeAttr("disabled");
            type = 2;
        }
        else {
            $("#assetName").val("");
            $("#assetName").attr({"disabled": "disabled"});
            type = 1;
        }
    }
</script>
<!-- 下拉框的js -->
</body>
</html>
