<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html >
<head>
    <meta name="decorator" content="iframe"/>
    <title><fmt:message key="html.title"/></title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
</head>
<body style="overflow:hidden">
<!-- 结项申报列表 -->
<!-- <div class="tab"> -->
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <%--导师未审核时显示--%>
                    <c:if test="${state le 4 && isAudit == 0 && state gt 0}">
                        <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
                            <tr><td colspan="8"><font style="color: red">未审核</font></td></tr>
                                <tr align="center" >
                                    <td>审核人：</td>
                                    <td>${labCenterManager.cname }</td>
                                    <td>工号:</td>
                                    <td>${labCenterManager.username }</td>
                                    <td>部门:</td>
                                    <td>${labCenterManager.schoolAcademy.academyName }</td>
                                    <td>联系方式:</td>
                                    <td>${labCenterManager.telephone }</td>
                                </tr>
                        </table>
                    </c:if>
                    <%--导师审核时显示--%>
                    <c:if test="${state == 4 && isAudit == 1}">
                        <table>
                            <tr>
                                <td>审核：</td>
                                <td colspan="4">
                                    <input type="radio" name="auditResult3"  value="1" checked="true"/>通过
                                    <input type="radio" name="auditResult3"  value="0" />拒绝
                                </td>
                            </tr>
                            <tr>
                                <td>审核意见：</td><td colspan="4"><input type="text" id="remark3"  /> </td>
                            </tr>
                            <tr>
                                <td colspan="5"><input type="button" value="提交" onclick="savelabCenterManagerAudit()"> </td>
                            </tr>
                        </table>
                    </c:if>
                    <%--导师审核后显示--%>
                    <c:if test="${state gt 4  || state le 1}">
                        <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
                            <tr><td colspan="6">审核信息 </td></tr>

                            <tr align="center" >
                                <th>审核人：</th>
                                <td>${user.cname}[${user.username}]</td>
                                <th>部门:</th>
                                <td>${user.schoolAcademy.academyName }</td>
                                <th>联系方式:</th>
                                <td>${user.telephone }</td>
                            </tr>
                            <tr align="center" >
                                <th>审核结果</th>
                                <td>${result}</td>
                                <th>审核意见:</th>
                                <td>${mem}</td>
                                <th>审核时间:</th>
                                <td><fmt:formatDate value="${createDate.time}" pattern="yyyy-MM-dd"/></td>
                            </tr>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function savelabCenterManagerAudit(){
        var auditResult = $('input:radio[name="auditResult3"]:checked').val();
        var remark = $("#remark3").val();
        $.ajax({
            url:"${pageContext.request.contextPath}/labRoomLending/saveLabCenterManagerAuditforlabRoomlend?id=${id}&tage=${tage}&state=${curStage }&page=${page}&auditResult="+auditResult+"&remark="+remark,
            type:"POST",
            success:function(data){//AJAX查询成功
                window.location.reload();
            }
        });
    }
</script>
</body>
<!-------------列表结束----------->
</html>