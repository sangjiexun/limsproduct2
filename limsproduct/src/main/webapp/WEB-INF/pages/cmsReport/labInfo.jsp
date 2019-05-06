<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
</head>

<body>
<div id="TabbedPanels1" class="TabbedPanels">
    <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
            <div class="content-box">
                <div class="title">
                    实验室详情
                    <a class="btn btn-new" onclick="window.history.go(-1)">返回</a>
                </div>
                <table>
                    <tr>
                        <th><spring:message code="all.trainingRoom.labroom"/>名称</th>
                        <td>${labRoom.labRoomName}</td>
                        <th><spring:message code="all.trainingRoom.labroom"/>编号</th>
                        <td>${labRoom.labRoomNumber}</td>
                        <th><spring:message code="all.trainingRoom.labroom"/>地点</th>
                        <td>${labRoom.labRoomAddress}</td>
                        <th><spring:message code="all.trainingRoom.labroom"/>英文名称</th>
                        <td>${labRoom.labRoomEnName}</td>
                    </tr>
                    <tr>
                        <th><spring:message code="all.trainingRoom.labroom"/>简称</th>
                        <td>${labRoom.labRoonAbbreviation}</td>
                        <th>使用面积</th>
                        <td>${labRoom.labRoomArea}</td>
                        <th><spring:message code="all.trainingRoom.labroom"/>容量</th>
                        <td>${labRoom.labRoomCapacity}</td>
                        <th>规章制度</th>
                        <td>${labRoom.labRoomRegulations}</td>
                    </tr>
                    <tr>
                        <th>是否可用</th>
                        <c:if test="${labRoom.labRoomActive==1}">
                            <td>可用</td>
                        </c:if>
                        <c:if test="${labRoom.labRoomActive==0}">
                            <td>不可用</td>
                        </c:if>
                        <th>是否可预约</th>
                        <c:if test="${labRoom.labRoomReservation==0}">
                            <td>不可预约</td>
                        </c:if>
                        <c:if test="${labRoom.labRoomReservation==1}">
                            <td>可预约</td>
                        </c:if>
                        <th>排课是否判冲</th>
                        <c:if test="${labRoom.isSpecial eq 0 || empty labRoom.isSpecial}">
                            <td>是</td>
                        </c:if>
                        <c:if test="${labRoom.isSpecial eq 1}">
                            <td>否</td>
                        </c:if>
                        <th></th>
                        <td></td>
                        <th></th>
                        <td></td>
                    </tr>
                    <tr>
                        <th><spring:message code="all.trainingRoom.labroom"/>描述</th>
                        <td colspan="6">${labRoom.labRoomIntroduction}</td>
                    </tr>
                    <tr>
                        <th><spring:message code="all.trainingRoom.labroom"/>注意事项</th>
                        <td colspan="6">${labRoom.labRoomRegulations}</td>
                    </tr>
                    <tr>
                        <th>获奖信息</th>
                        <td colspan="6">${labRoom.labRoomPrizeInformation}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
