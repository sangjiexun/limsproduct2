<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>


<html>
<head>
    <title></title>
    <meta name="decorator" content="iframe"/>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no"/>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/device/editDevice.js"></script>

    <style>
        .tool-box2 th{text-align:left;}
        .wangEditor-container{
            width: 98%;
            margin:auto;
            height: 210px;
            max-height: 210px;
        }
        .wangEditor-container .wangEditor-textarea-container{
            height:179px!important;
        }
        .submit_btn1{
            float:right;
            margin:0 10px 15px;
        }
        .submit_btn2{
            float:right;
            margin:0 1% 15px 0;
        }
        .content-box fieldset{
            margin: 0;
            border: none;
            width: 100%!important;
            padding: 15px 0 10px;
        }
    </style>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->


    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
    <script type="text/javascript">


        function showImage(image) {
            //alert(image.src);
            //$("#img").src=image.src;
            document.getElementById("img").src = image.src;
            $('#showImage').window({
                top: 1200
            });
            $('#showImage').window('open');
        }
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/device/editDevice.js"></script>



    <style>
        .tool-box2 th {
            text-align: left;
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
                <%--<a href="${pageContext.request.contextPath}/device/editDeviceInfo?id=${device.id}">设备详情</a>
                --%><a href="javascript:void(0);" onclick="editDeviceInfoRest(${device.id})">设备详情</a>
            </li>
            <li class="TabbedPanelsTab" tabindex="0">
                <a href="javascript:void(0);" onclick="editAgentInfoRest(${device.id})">物联硬件</a>
            </li>
            <%-- <c:if test="${device.CActiveByAllowSecurityAccess.id == 1}"> --%>
            <c:if test="${device.CDictionaryByAllowSecurityAccess.CCategory=='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber=='1'}">
                <li class="TabbedPanelsTab" tabindex="0">
                        <%--<a href="${pageContext.request.contextPath}/device/deviceTraining?deviceId=${device.id}">培训计划</a>
                        --%><a href="javascript:void(0);" onclick="editDeviceTrainingRest(${device.id})">培训计划</a>
                </li>
            </c:if>
            <li class="TabbedPanelsTab" tabindex="0">
                <%--<a href="${pageContext.request.contextPath}/device/editDeviceSetting?id=${device.id}">参数设置</a>
                --%><a href="javascript:void(0);" onclick="editDeviceSettingRest(${device.id})">参数设置</a>
            </li>
            <li class="TabbedPanelsTab" tabindex="0">
                <%--<a href="${pageContext.request.contextPath}/device/deviceImage?deviceId=${device.id}">相关图片</a>
                --%><a href="javascript:void(0);" onclick="editDeviceImageRest(${device.id})">相关图片</a>
            </li>
            <li class="TabbedPanelsTab" tabindex="0"><%--
		<a href="${pageContext.request.contextPath}/device/deviceVideo?deviceId=${device.id}">相关视频</a>
		--%><a href="javascript:void(0);" onclick="editDeviceVideoRest(${device.id})">相关视频</a>
            </li>
            <li class="TabbedPanelsTab" tabindex="0">
                <%--<a href="${pageContext.request.contextPath}/device/deviceDocument?deviceId=${device.id}">相关文档</a>
                --%><a href="javascript:void(0);" onclick="editDeviceDocumentRest(${device.id})">相关文档</a>
            </li>
            <li class="TabbedPanelsTab" tabindex="0">
                <%--<a href="${pageContext.request.contextPath}/device/dimensionalCode?deviceId=${device.id}">二维码</a>
                --%><a href="javascript:void(0);" onclick="editDeviceDimensionalCodeRest(${device.id})">二维码</a>
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
                <li class="TabbedPanelsTab selected" tabindex="0">
                    <a href="javascript:void(0);" onclick="editDeviceAttentionRest(${device.id})">设备安全协议</a>
                </li>
            </c:if>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">

                <div class="content-box">
                    <div class="title">
                        <div id="title">设备注意事项</div>
                        <a class="btn btn-new" herf="#" onclick="openSetupLink();">返回</a>
                    </div>

                    <form:form
                            action="${pageContext.request.contextPath}/device/saveDeviceReservationInfo?deviceId=${device.id}"
                            method="post" modelAttribute="device">
                        <div class="new-classroom" style="overflow:hidden;">

                            <fieldset class="introduce-box">
                                <form:textarea id="applicationAttentions" class="inputContent"
                                               path="applicationAttentions"/>
                            </fieldset>
                            <sec:authorize
                                    ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_LABMANAGER,ROLE_EQUIPMENTADMIN,ROLE_LABMANAGER">
                                <input class="submit_btn2" type="button" value="返回" onclick="openSetupLink()"/>
                                <input class="submit_btn1" type="button" onclick="saveDeviceAttentionRest(${device.id})"
                                       value="提交"/>
                            </sec:authorize>

                        </div>
                    </form:form>

                    <input type="hidden" id="labRoomId" value="${labRoomId }">
                    <input type="hidden" id="deviceName" value="${deviceName }">
                    <input type="hidden" id="deviceNumber" value="${deviceNumber }">
                    <input type="hidden" id="username" value="${username }">
                    <input type="hidden" id="page" value="${page }">
                    <input type="hidden" id="pageContext" value="${pageContext.request.contextPath }">

                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
    </script>
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
        '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
<script>
    $(function(){
        //初始化编辑器
        editor = new wangEditor("applicationAttentions");
        editor.create();
    })
</script>
<script src="http://cdn.bootcss.com/wangeditor/2.1.20/js/wangEditor.js"></script>
 <link href="http://cdn.bootcss.com/wangeditor/2.1.20/css/wangEditor.css" rel="stylesheet">
</body>
</html>