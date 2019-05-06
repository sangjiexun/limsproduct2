<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--

<fmt:setBundle basename="bundles.lab-resources"/>
--%>

<html >
<head>
    <meta name="decorator" content="iframe"/>
    <title><fmt:message key="html.title"/></title>

    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>--%>
    <%--<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />--%>

    <!-- 下拉的样式 -->
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />--%>
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />--%>
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />--%>

    <!-- 下拉的样式结束 -->
    <script type="text/javascript">
        function saveOperationItemAudit() {
            var audit = $("input[name='audit']:checked").val();
            var oId = $("#oId").val();
            var auditUsername = $("#auditUsername").val();
            var mem = $("#mem").val();
            var flag = $("#flag").val();
            $.ajax({
                url:"${pageContext.request.contextPath}/operation/saveOperationItemAudit?oId="+oId+"&auditUser="+auditUsername+"&audit="+audit+"&mem="+mem+"&flag="+flag,
                type:"POST",
                success:function(data){//AJAX查询成功
                    if(data=="success"){

                    }
                },
                error:function () {
                    location.reload();
                }
            });

        }
    </script>
</head>
<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">审核信息</a></li>
        </ul>
        <ul>
            <li>
                <a href="javascript:void(0)" onclick="javascript:history.back(-1);">返回</a>
            </li>
        </ul>
    </div>
</div>
<!-- 结项申报列表 -->
<div id="content" >
    <div class="right-content">
        <div id="TabbedPanels1" class="TabbedPanels">
            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">
                    <div class="content-box">
                        <table>
                            <thead>
                            <tr>
                                <th>审核名称</th>
                                <th>审核时间</th>
                                <th>审核人</th>
                                <th>工号</th>
                                <%--<th>所属单位</th>--%>
                                <%--<th>联系方式</th>--%>
                                <th>审核状态</th>
                                <th>审核意见</th>
                            </tr>
                            </thead>
                            <tbody>
                                <%--<c:forEach items="${operItemAuditList}" var="operItemAudit" varStatus="status">--%>
                                    <%--<tr>--%>
                                        <%--<c:if test="${ status.index eq 0}"><td>教研室主任审核</td></c:if>--%>
                                        <%--<c:if test="${ status.index eq 1}"><td><spring:message code="all.trainingRoom.labroom" />管理员审核</td></c:if>--%>
                                        <%--<c:if test="${ status.index eq 2}"><td>实训中心主任审核</td></c:if>--%>
                                        <%--<td><input name="labRoomTimeCreate" value="<fmt:formatDate value='${operItemAudit.createDate.time}' pattern='yyyy-MM-dd'/>" /></td>--%>
                                        <%--<td>${operItemAudit.user.cname}</td>--%>
                                        <%--<td>${operItemAudit.user.username}</td>--%>
                                        <%--&lt;%&ndash;<td></td>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<td></td>&ndash;%&gt;--%>
                                        <%--<td>${operItemAudit.result}</td>--%>
                                    <%--</tr>--%>
                                <%--</c:forEach>--%>
                                <c:forEach items="${auditInfo}" var="info" varStatus="i">
                                    <tr>
                                        <td>${info[1]}审核</td>
                                        <td>${info[3]}</td>
                                        <td>${info[4].cname}</td>
                                        <td>${info[4].username}</td>
                                        <td>${info[2]}</td>
                                        <td>${info[5]}</td>
                                    </tr>
                                </c:forEach>
                                <%--<c:if test="${count eq 1}">--%>
                                    <%--<tr>--%>
                                        <%--<td>实训管理员审核</td>--%>
                                        <%--<td></td>--%>
                                        <%--<td><c:forEach items="${labManagers}" var="labManager">--%>
                                            <%--<span>${labManager.cname}</span>--%>
                                            <%--</c:forEach>--%>
                                        <%--</td>--%>
                                        <%--<td><c:forEach items="${labManagers}" var="labManager">--%>
                                            <%--<span>${labManager.username}</span>--%>
                                        <%--</c:forEach></td>--%>
                                        <%--<td>待审核</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td>实训中心主任审核</td>--%>
                                        <%--<td></td>--%>
                                        <%--<td><c:forEach items="${labCenterManager}" var="labCenterManager">--%>
                                            <%--<span>${labCenterManager.cname}</span>--%>
                                        <%--</c:forEach>--%>
                                        <%--</td>--%>
                                        <%--<td><c:forEach items="${labCenterManager}" var="labCenterManager">--%>
                                            <%--<span>${labCenterManager.username}</span>--%>
                                        <%--</c:forEach></td>--%>
                                        <%--<td>待审核</td>--%>
                                    <%--</tr>--%>
                                <%--</c:if>--%>
                                <%--<c:if test="${count eq 2}">--%>
                                <%--<tr>--%>
                                    <%--<td>实训中心主任审核</td>--%>
                                    <%--<td></td>--%>
                                    <%--<td><c:forEach items="${labCenterManager}" var="labCenterManager">--%>
                                        <%--<span>${labCenterManager.cname}</span>--%>
                                    <%--</c:forEach>--%>
                                    <%--</td>--%>
                                    <%--<td><c:forEach items="${labCenterManager}" var="labCenterManager">--%>
                                        <%--<span>${labCenterManager.username}</span>--%>
                                    <%--</c:forEach></td>--%>
                                    <%--<td>待审核</td>--%>
                                <%--</tr>--%>
                                <%--</c:if>--%>
                        </table>

                    </div>
                    <c:if test="${canAudit}">
                        <div class="content-box">
                            <div class="title">${authCname}审核</div>
                            <table>
                                <tr>
                                    <td>
                                        <input type="radio" name="audit" value="1"/>通过
                                        <input type="radio" name="audit" value="0"/>不通过
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <input type="text" id="mem"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <input type="submit" onclick="saveOperationItemAudit()" value="提交"/>
                                    </td>
                                </tr>
                                <input type="hidden" id="oId" value="${oId}"/>
                                <input type="hidden" id="auditUsername" value="${auditUsername}"/>
                                <input type="hidden" id="flag" value="${flag}"/>
                                </tr>
                            </table>
                        </div>
                    </c:if>
                    <c:if test="${!canAudit && level != 0 && level != -1}">
                        <div class="content-box">
                            <div class="title">下一级审核人</div>
                            <table>
                                <tr>
                                    <td>工号</td>
                                    <td>姓名</td>
                                    <td>联系方式</td>
                                </tr>
                                <c:forEach items="${auditUsers}" var="user" varStatus="i">
                                    <tr>
                                        <td>${user.username}</td>
                                        <td>${user.cname}</td>
                                        <td>${user.telephone}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<!-------------列表结束----------->
</html>