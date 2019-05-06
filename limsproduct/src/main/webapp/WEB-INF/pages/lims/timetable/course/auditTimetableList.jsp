<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="iframe"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/timetable/course/auditTimetableList.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
</head>
<body style="overflow:hidden">
<!-- 结项申报列表 -->
<!-- <div class="tab"> -->
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <%--未审核时显示--%>
                    <c:if test="${curStage <= state && isAudit == 0 && curStage > 0}">
                        <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
                            <tr><td colspan="8"><font style="color: red">审核人列表</font></td></tr>
                            <c:forEach items="${isAuditUser}" var="isAuditUser" varStatus="i">
                                <tr>
                                    <td>审核人：</td>
                                    <td>${isAuditUser.cname }</td>
                                    <td>工号:</td>
                                    <td>${isAuditUser.username }</td>
                                    <td>部门:</td>
                                    <td>${isAuditUser.schoolAcademy.academyName }</td>
                                    <td>联系方式:</td>
                                    <td>${isAuditUser.telephone }</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                    <%--审核时显示--%>
                    <c:if test="${curStage == state && isAudit == 1}">
                        <table>
                            <tr>
                                <td>审核：</td>
                                <td colspan="4">
                                    <input type="radio" name="auditResult"  value="1" checked="true"/>通过
                                    <input type="radio" name="auditResult"  value="0" />拒绝
                                    <input type="hidden" id="businessAppUid" value="${businessAppUid}" />
                                    <input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
                                    <input type="hidden" id="businessType" value="${businessType}" />
                                </td>
                            </tr>
                            <tr>
                                <td>审核意见：</td><td colspan="4"><input type="text" id="remark"  /> </td>
                            </tr>
                            <tr>
                                <td colspan="5"><input type="button" value="提交" onclick="saveTimetableAudit()"> </td>
                            </tr>
                        </table>
                    </c:if>
                    <%--审核后显示--%>
                    <c:if test="${curStage gt state  || curStage < 1}">
                        <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
                            <tr><td colspan="6">审核信息 </td></tr>

                            <tr align="center" >
                                <th>审核人：</th>
                                <td>${userInfo[0].cname}[${userInfo[0].username}]</td>
                                <th>部门:</th>
                                <td>${userInfo[0].schoolAcademy.academyName }</td>
                                <th>联系方式:</th>
                                <td>${userInfo[0].telephone }</td>
                            </tr>
                            <tr align="center" >
                                <th>审核结果</th>
                                <td>${userInfo[1]}</td>
                                <th>审核意见:</th>
                                <td>${userInfo[2]}</td>
                                <th>审核时间:</th>
                                <td><fmt:formatDate value="${userInfo[3]}" pattern="yyyy-MM-dd"/></td>
                            </tr>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
