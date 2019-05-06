<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="iframe"/>
</head>

<body>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                        <div class="new-classroom">
                            <fieldset>
                                <label><spring:message code="all.trainingRoom.labroom"/>名称：</label>
                                ${preLabRoom.roomName}
                            </fieldset>
                            <fieldset>
                                <label>所属部门：</label>${schoolAcademy.academyName}
                            </fieldset>
                            <fieldset>
                                <label>容量：</label>${preLabRoom.preCapacityRange.capaType}(${preLabRoom.preCapacityRange.capaRange}人)
                            </fieldset>
                            <fieldset>
                                <label>房间布局类型：</label>${preLabRoom.preRoomType.roomType}
                            </fieldset>
                            <fieldset>
                                <label>软件：</label>
                                        <c:forEach items="${preSoftwareAll}" var="curr">
                                            ${curr.name}<br>
                                        </c:forEach>
                            </fieldset>
                        </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

