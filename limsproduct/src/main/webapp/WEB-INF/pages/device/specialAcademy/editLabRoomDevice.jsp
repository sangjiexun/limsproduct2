<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
    <script type="text/javascript">
        function saveLabRoomDevice(id) {
            if(id==undefined){
                id="";
            }
            $.ajax({
                url:'${pageContext.request.contextPath}/device/savelabRoomDevice?id='+id,
                type:'POST',
                data:$('#queryForm').serialize(),
                async:false,
                error:function (request){
                    alert('请求错误!');
                },
                success:function(){
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                }
            });
        }
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.device.management" /></a></li>
            <li><a href="javascript:void(0)">新建设备</a></li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">
                            新建设备
                        </div>
                    </div>
                    <form:form id="queryForm" name="queryForm"
                            action="${pageContext.request.contextPath}/device/savelabRoomDevice?id=${labRoomDevice.id }"
                            method="POST" modelAttribute="labRoomDevice">
                        <div class="new-classroom">
                            <fieldset>
                                <td><spring:message code="left.trainingRoom.setting"/>名称</td>
                                <form:select id="labRoom_id" path="labRoom.id" class="chzn-select">
                                    <c:forEach items="${labRooms}" var="curr">
                                        <c:if test="${labRoom.id eq curr.id}">
                                            <form:option value="${labRoom.id}"
                                                         selected="selected">${labRoom.labRoomName }[${labRoom.labRoomNumber }]</form:option>
                                        </c:if>
                                        <c:if test="${labRoom.id ne curr.id}">
                                            <form:option
                                                    value="${curr.id}">${curr.labRoomName }[${curr.labRoomNumber }]</form:option>
                                        </c:if>
                                    </c:forEach>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>设备名称</label>
                                <form:select id="schoolDevice_deviceName" path="schoolDevice.deviceNumber"
                                             class="chzn-select">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${listLabRoomDeviceAll}" var="curr">
                                        <form:option
                                                value="${curr.schoolDevice.deviceNumber}">${curr.schoolDevice.deviceName}[${curr.schoolDevice.deviceNumber }]</form:option>
                                    </c:forEach>
                                </form:select>
                            </fieldset>
                                <%--<fieldset>
                                  <label>规格</label>
                                  <form:select id="schoolDevice_devicePattern" path="schoolDevice.devicePattern" class="chzn-select">
                                        <form:option value="">请选择</form:option>
                                            <c:forEach items="${listLabRoomDeviceAll}" var="curr">
                                                <form:option value="${curr.schoolDevice.devicePattern }">${curr.schoolDevice.devicePattern}</form:option>
                                            </c:forEach>
                                        </form:select>
                                </fieldset>
                                --%>
                            <fieldset>
                                <label>主要技术指标</label>
                                <form:textarea   style="width:500px;height:160px" path="indicators"/>
                            </fieldset>
                            <fieldset>
                                <label>设备管理员</label>
                                <form:select id="username" path="user.username" class="chzn-select">
                                   <%-- <form:option value="">请选择</form:option>--%>
                                    <form:options items="${userMap}"/>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>状态</label>
                                <form:select id="CDictionaryByDeviceStatus_CName" path="CDictionaryByDeviceStatus.id"
                                             class="chzn-select">
                                    <form:option value="">请选择</form:option>
                                    <form:option value="592">正常使用</form:option>
                                    <form:option value="593">维修中</form:option>
                                    <form:option value="594">教学使用中</form:option>
                                    <form:option value="595">故障</form:option>
                                    <form:option value="596">借用中</form:option>
                                    <form:option value="597">领用中</form:option>
                                </form:select>
                            </fieldset>
                        </div>
                        <div class="moudle_footer">
                            <div class="submit_link">
                                <input class="btn" id="save" type="button" onclick="saveLabRoomDevice(${labRoomDevice.id});" value="确定">
                                <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
                            </div>
                        </div>
                    </form:form>

                    <!-- 下拉框的js -->
                    <script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
                            type="text/javascript"></script>
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

                        function checkUnique() {
                            var deviceNumber = document.getElementById("deviceNumber").value;
                            $.ajax({
                                url: '${pageContext.request.contextPath}/judgeSchoolDeviceNumberUnique?deviceNumber=' + deviceNumber,
                                type: 'POST',
                                error: function (request) {
                                    alert('请求错误!');
                                },
                                success: function (data) {
                                    if (data != "unique") {
                                        alert("该设备编号已存在!请重新输入！");
                                        document.getElementById("deviceNumber").value = "";
                                    }
                                }
                            });
                        }
                    </script>
                    <!-- 下拉框的js -->
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
