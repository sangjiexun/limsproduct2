<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html>
<head>
    <title>Title</title>
    <meta name="decorator" content="iframe"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">

</head>
<body>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="new-classroom">
                        <fieldset>
                            <label>设备编号：</label>${device.schoolDevice.deviceNumber}
                        </fieldset>
                        <fieldset>
                            <label>设备名称：</label>${device.schoolDevice.deviceName}
                        </fieldset>
                        <fieldset>
                            <label>设备型号：</label>${device.schoolDevice.devicePattern}
                        </fieldset>
                        <fieldset>
                            <label>设备规格：</label>${device.schoolDevice.deviceFormat}
                        </fieldset>
                        <fieldset>
                            <label>设备状态：</label>${device.schoolDevice.CDictionaryByUseStatus.CName}
                        </fieldset>
                        <fieldset>
                            <label>设备价格：</label>${device.schoolDevice.devicePrice}
                        </fieldset>
                        <fieldset>
                            <label>所属<spring:message code="all.trainingRoom.labroom"/>：</label>${device.labRoom.labRoomName}
                        </fieldset>
                        <fieldset>
                            <label>设备供应商：</label>${device.schoolDevice.deviceSupplier}
                        </fieldset>
                        <fieldset>
                            <label>国别：${device.schoolDevice.deviceCountry}
                        </fieldset>
                        <fieldset>
                            <label>使用方向：</label>${device.schoolDevice.deviceUseDirection}
                        </fieldset>
                        <fieldset>
                            <label>购买日期：</label><fmt:formatDate value="${current.schoolDevice.deviceAccountedDate.time}" pattern="yyyy-MM-dd"/>
                        </fieldset>
                        <fieldset>
                            <label>入账日期：</label><fmt:formatDate value="${current.schoolDevice.deviceAccountedDate.time}" pattern="yyyy-MM-dd"/>
                        </fieldset>
                        <fieldset>
                            <label>存放地点：</label>${device.schoolDevice.deviceAddress}
                        </fieldset>
                        <fieldset>
                            <label>领用人：</label>${device.user.cname}
                        </fieldset>
                        <fieldset>
                            <label>保管人：</label>${device.schoolDevice.userByKeepUser.cname}
                        </fieldset>
                        <fieldset>
                            <label>生产厂家：</label>${device.schoolDevice.manufacturer}
                        </fieldset>
                        <fieldset>
                            <label>序列号：</label>${device.schoolDevice.sn}
                        </fieldset>
                        <fieldset>
                            <label>资产类别：</label>${device.schoolDevice.categoryMain}
                        </fieldset>
                        <fieldset>
                            <label>资产类型：</label>${device.schoolDevice.categoryType}
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
