<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <head>
        <meta name="decorator" content="iframe"/>
        <title><fmt:message key="html.title"/></title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
    </head>
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
                                </td>
                            </tr>
                            <tr>
                                <td>审核意见：</td><td colspan="4"><input type="text" id="remark"  /> </td>
                            </tr>
                            <tr>
                                <td colspan="5"><input type="button" value="提交" onclick="saveCourseTimetableAudit()"> </td>
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
<script type="text/javascript">
    function saveCourseTimetableAudit(){
        var auditResult = $('input:radio[name="auditResult"]:checked').val();
        var remark = $("#remark").val();
        var arr = {};
        arr.businessType = '${businessType}';
        arr.businessUid = '${businessUid}';
        arr.businessAppUid = '${businessAppUid}';
        arr.curStage = ${curStage};
        arr.auditResult = auditResult;
        arr.remark = remark;
        var arrStr = JSON.stringify(arr);
        $.ajax({
            url:"${pageContext.request.contextPath}/auditing/saveAudit",
            contentType: "application/json;charset=utf-8",
            type:"POST",
            data: arrStr,
            success:function(data){
                var completeUrl = "";
                var businessType = '${businessType}';
                var businessAppUid = '${businessAppUid}';
                switch (businessType) {
                    case "StationReservation":
                    case "1StationReservation":
                    case "2StationReservation":
                        completeUrl = "${pageContext.request.contextPath}/LabRoomReservation/updateStationStatus";
                        break;
                    default:
                }
                $.ajax({
                    url: completeUrl,
                    type: "POST",
                    data: {
                        businessAppUid: businessAppUid,
                        businessType: businessType,
                        auditResult: data
                    },
                    success: function (result) {
                    }
                });
                switch (data) {
                    case "pass":
                        alert("审核通过");
                        parent.location.reload();
                        break;
                    case "fail":
                        alert("审核已拒绝");
                        var index = parent.parent.layer.getFrameIndex(parent.name);
                        parent.parent.layer.close(index);
                        break;
                    case "":
                        alert("保存失败");
                        break;
                    default:
                        parent.location.reload();
                }
            },
            error:function () {
                alert("保存失败");
            }
        });
    }
</script>
</body>
</html>
