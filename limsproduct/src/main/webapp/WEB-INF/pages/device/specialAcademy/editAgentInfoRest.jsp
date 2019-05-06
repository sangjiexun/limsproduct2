<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
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

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->


    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
    <script type="text/javascript">

        /**
         添加硬件
         */
        function addAgent(){
            $("#addAgent").show();
            var topPos = window.pageYOffset;
            //使得弹出框在屏幕顶端可见
            $('#addAgent').window({left:"100px", top:topPos+"10px"});

            $("#addAgent").window('open');
        }
        //开关电源控制
        function openAgent(flag,Uid){
            var myData={
                "flag":flag,
                "insUid":Uid
            }
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/openAgent?flag="+flag+"&insUid="+Uid,
                type:'POST',
                async:false,
                dataType: "json",
                success:function(data){//AJAX查询成功
                    var success="true";
                    if (flag == 1){
                        alert("电源已打开");
                    }
                    if (flag == 0){
                        alert("电源已关闭");
                    }
                    if (flag == 3){
                        alert("已下发预约数据");
                    }
                    if (flag == 4){
                        alert("已回推记录");
                    }
                    if (flag == 5){
                        alert("门禁已打开");
                    }
                },error:function(){
                    alert("发生错误！");
                }
            });
        }
        function changeDoor() {
            var count = 0;
            if($("#harewareModule").val() == "1拖1"){
                count = 1;
            }
            if($("#harewareModule").val() == "1拖4"){
                count = 4;
            }
            if($("#harewareModule").val() == "1拖8"){
                count = 8;
            }
            var doorindex = document.getElementById("doorindex");
            doorindex.options.length = 0;
            doorindex.add(new Option("请选择", "", true, true));
            for(var i = 1; i <= count; i++){
                doorindex.add(new Option(i, i, false, false));
            }

            // 关键点：触发 select 的 `chosen:updated` 事件
            $("#doorindex").trigger('liszt:updated');
            $("#doorindex").chosen();

        }
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/device/editDevice.js"></script>

    <style>
        .tool-box2 th{text-align:left;}
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
                    <span>所在<spring:message code="all.trainingRoom.labroom" />：</span>
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
            <li class="TabbedPanelsTab " tabindex="0">
                <%--<a href="${pageContext.request.contextPath}/device/editDeviceInfo?id=${device.id}">设备详情</a>
                --%><a href="javascript:void(0);" onclick="editDeviceInfoRest(${device.id})">设备详情</a>
            </li>
            <li class="TabbedPanelsTab selected" tabindex="0">
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
                <li class="TabbedPanelsTab" tabindex="0">
                    <a href="javascript:void(0);" onclick="editDeviceAttentionRest(${device.id})">设备安全协议</a>
                </li>
            </c:if>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">

                <div class="content-box">
                    <c:if test="${flag==true}">
                        <div class="edit-content-box">
                            <div class="title">
                                <div id="title">物联硬件</div>
                                <c:if test="${device.labRoomAgent eq null}">
                                <sec:authorize
                                        ifAnyGranted="ROLE_SUPERADMIN,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_DEPARTMENTHEADER,ROLE_COLLEGELEADER">
                                    <a class="btn btn-new" onclick="addAgent();">添加硬件</a>
                                </sec:authorize>
                                </c:if>
                            </div>
                            <div class="edit-content">
                                <table class="tb" id="my_show" style="table-layout:fixed">
                                    <thead>
                                    <tr>
                                        <th>硬件名称</th>
                                        <th>IP</th>
                                        <th style="width:10%">制造商</th>
                                        <th>SN/电表号</th>
                                        <th>服务器</th>
                                        <c:if test="${flag }">
                                            <th>远程</th>
                                            <sec:authorize ifNotGranted="ROLE_CABINETADMIN">
                                                <th>操作</th>
                                            </sec:authorize>
                                        </c:if>
                                    </tr>
                                    </thead>
                                    <c:forEach items="${agentList}" var="agent" varStatus="i">
                                        <tr align="center">
                                            <td>
                                                <c:if test="${(agent.CDictionary.CNumber=='2' || agent.CDictionary.CNumber=='6') && agent.CDictionary.CCategory=='c_agent_type'}">
                                                    <!-- 门禁 -->
                                                    <c:if test="${flag }">
                                                        <a href="${pageContext.request.contextPath}/lab/entranceList?id=${agent.id}&page=1">${agent.CDictionary.CName}</a>
                                                    </c:if>
                                                    <c:if test="${!flag }">
                                                        ${agent.CDictionary.CName}
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${(agent.CDictionary.CNumber!='2' && agent.CDictionary.CNumber!='6' && agent.CDictionary.CCategory=='c_agent_type')}">
                                                    ${agent.CDictionary.CName}
                                                </c:if>
                                            </td>
                                            <td>${agent.hardwareIp}</td>
                                            <td>${agent.manufactor}</td>
                                            <td>${agent.snNo}</td>
                                            <td>${agent.commonServer.serverName}</td>
                                            <c:if test="${flag }">
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${agent.CDictionary.CNumber eq '2'}">
                                                            <a href="javascript:void(0)"
                                                               onclick="opendoor(${agent.id});">开门</a>
                                                        </c:when>
                                                        <c:when test="${agent.CDictionary.CNumber eq '6'}">
                                                            <a href="javascript:void(0)"
                                                               onclick="opendoor_scr(${agent.id});">开门</a>
                                                        </c:when>
                                                        <c:when test="${agent.CDictionary.CNumber eq '3'}">
                                                            <a href="javascript:void(0)"
                                                               onclick="openVideo1('${labRoom.id}','${agent.id}');">开视频</a>
                                                            <sec:authorize
                                                                    ifAnyGranted="ROLE_SUPERADMIN,ROLE_LABMANAGER,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR">
                                                                <br/>
                                                                <c:if test="${yuntai eq true}">
                                                                    <a href="javascript:void(0)"
                                                                       onclick="openVideoSet('${labRoom.id}','${agent.id}');">调视频</a>
                                                                </c:if>
                                                            </sec:authorize>
                                                        </c:when>
                                                        <c:when test="${agent.CDictionary.CNumber eq '4'}">
                                                            <sec:authorize ifNotGranted="ROLE_CABINETADMIN">
                                                                <a href="javascript:void(0)"
                                                                   onclick="openAgent(1,${agent.id})">开电源</a>
                                                                <a href="javascript:void(0)"
                                                                   onclick="openAgent(0,${agent.id})">关电源</a>
                                                            </sec:authorize>
                                                        </c:when>
                                                        <c:otherwise>未定义</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <sec:authorize ifNotGranted="ROLE_CABINETADMIN">
                                                    <td><a
                                                            href="${pageContext.request.contextPath}/device/updateLabRoomAgent?agentId=${agent.id}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&deviceId=${device.id}&username=${username}&page=${page}&schoolDevice_allowAppointment=${schoolDevice_allowAppointment}">修改</a>
                                                        <a onclick="return confirm('确认要删除吗？')"
                                                           href="${pageContext.request.contextPath}/device/deleteLabRoomAgent?agentId=${agent.id}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&deviceId=${device.id}&username=${username}&page=${page}&schoolDevice_allowAppointment=${schoolDevice_allowAppointment}">删除</a>
                                                    </td>
                                                </sec:authorize>
                                            </c:if>
                                        </tr>
                                    </c:forEach>

                                </table>
                            </div>
                        </div>
                    </c:if>
                    <!-- 添加物联硬件 -->
                    <div id="addAgent" class="easyui-window " title="添加物联硬件"
                         align="left" title="" modal="true" maximizable="false"
                         collapsible="false" closed="true" minimizable="false"
                         style="width: 500px; height: 300px;">
                        <form:form
                                action="${pageContext.request.contextPath}/device/saveLabRoomAgent?labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&deviceId=${device.id}&username=${username}&page=${page}&schoolDevice_allowAppointment=${schoolDevice_allowAppointment}"
                                modelAttribute="agent">

                            <table class="tb" id="my_show">
                                <tr>
                                    <td>硬件名称：</td>
                                    <td><form:select path="CDictionary.id" class="chzn-select">
                                        <form:options items="${types}" itemLabel="cName"
                                                      itemValue="id" />
                                    </form:select>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Ip：</td>
                                    <td><form:input path="hardwareIp" id="hardwareIp"
                                                    onchange="testIsIp()" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>制造商：</td>
                                    <td><form:select path="manufactor" class="chzn-select">
                                        <form:option value="">请选择</form:option>
                                        <form:option value="adkfp">adkfp</form:option>
                                        <form:option value="aopu">aopu</form:option>
                                        <form:option value="gvsun">gvsun</form:option>
                                        <form:option value="wiegand">wiegand</form:option>
                                    </form:select></td>
                                </tr>
                                <tr>
                                     <td>规格:</td>
                                     <td>
                                    <form:select id="harewareModule" path="harewareModule"  class="chzn-select" onchange="changeDoor()">
                                        <form:option value="">请选择</form:option>
                                        <form:option value="1拖1">1拖1</form:option>
                                        <form:option value="1拖4">1拖4</form:option>
                                        <form:option value="1拖8">1拖8</form:option>
                                    </form:select>
                                     </td>
                                </tr>
                                <tr>
                                    <td>设备号(门号)：</td>
                                    <td >
                                        <form:select id="doorindex" path="doorindex" class="chzn-select">
                                            <form:option value="">请选择</form:option>
                                            <form:option value="1">1</form:option>
                                            <form:option value="2">2</form:option>
                                            <form:option value="3">3</form:option>
                                            <form:option value="4">4</form:option>
                                            <form:option value="5">5</form:option>
                                            <form:option value="6">6</form:option>
                                            <form:option value="7">7</form:option>
                                            <form:option value="8">8</form:option>

                                        </form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>SN/电表号：</td>
                                    <td><form:input path="snNo" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>物联服务器：</td>
                                    <td><form:select path="commonServer.id"
                                                     class="chzn-select">
                                        <form:options items="${serverList}" itemLabel="serverName"
                                                      itemValue="id" />
                                    </form:select></td>
                                </tr>

                                <tr>
                                    <td><input type="submit" value="提交">
                                    </td>
                                    <td><input type="button" value="取消"></td>
                                </tr>
                            </table>
                        </form:form>
                    </div>
                    <div id="openVIdeo" class="easyui-window " title="查看视频"
                         align="left" title="" modal="true" maximizable="false"
                         collapsible="false" closed="true" minimizable="false"
                         style="width: 1000px; height: 500px;"></div>
                    <!-- 添加物联硬件结束 -->
                    <input type="hidden" id="labRoomId" value="${labRoomId }">
                    <input type="hidden" id="deviceName" value="${deviceName }">
                    <input type="hidden" id="deviceNumber" value="${deviceNumber }">
                    <input type="hidden" id="username" value="${username }">
                    <input type="hidden" id="page" value="${page }">
                    <input type="hidden" id="pageContext" value="${pageContext.request.contextPath }">
                    <input type="hidden" id="schoolDevice_allowAppointment" value="${schoolDevice_allowAppointment}">
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
