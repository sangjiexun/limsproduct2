<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html>
<head>
    <meta name="decorator" content="iframe"/>

    <link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css"/>

    <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

    <script type="text/javascript">

        //跳转
        function targetUrl(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

        //取消
        function cancleQuery() {
            window.location.href = "${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=1";
        }
    </script>

</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.open.appointment" /></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.equipment.use" /></a></li>
        </ul>
    </div>
</div>
<sec:authorize ifNotGranted="ROLE_STUDENT">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
            <li class="TabbedPanelsTab" id="s1">
                <a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1">设备借用</a>
            </li>
            <li class="TabbedPanelsTab" id="s2">
                <a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/deviceLendingApplyList?page=1">我的设备借用申请</a>
            </li>
            <li class="TabbedPanelsTab" id="s3">
                <a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/deviceLendingCheckList?page=1">我的设备借用审核</a>
            </li>
            <sec:authorize ifAnyGranted="ROLE_SUPERADMIN">
                <li class="TabbedPanelsTab selected" id="s4">
                    <a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=1">设备借用作废列表</a>
                </li>
            </sec:authorize>
        </ul>
    </div>
</sec:authorize>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsTabGroup-box">
            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">
                    <div class="content-box">
                        <div class="tool-box">
                            <form:form name="queryForm" method="post" modelAttribute="refuseItemBackup">
                                <ul>
                                    <li>设备名称：<form:input path="labRoomName"/></li>
                                    <li><input type="submit" value="查询" onclick="targetUrl('${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=1')">
                                        <input class="cancel-submit" type="button" onclick="cancleQuery()" value="取消"></li>
                                </ul>
                            </form:form>
                        </div>
                        <table class="tb" id="my_show">
                            <thead>
                            <tr>
                                <th>设备名称</th>
                                <th>申请人</th>
                                <th>借用时间</th>
                                <th>预计归还时间</th>
                                <th>借用原因</th>
                                <th>审核信息</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${items}" var="current" varStatus="i">
                                <tr>
                                    <td>${current[0]}</td>
                                    <td>${current[1].cname}[${current[1].username}]</td>
                                    <td>${current[2]}</td>
                                    <td>${current[3]}</td>
                                    <td>${current[4]}</td>
                                    <td>${current[5]}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <!-- 分页模块 -->
                        <div class="page">
                            ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                            <a href="javascript:void(0)"
                               onclick="targetUrl('${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=1')"
                               target="_self">首页</a>
                            <a href="javascript:void(0)"
                               onclick="targetUrl('${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=${pageModel.previousPage}')"
                               target="_self">上一页</a>
                            第<select
                                onchange="targetUrl(this.options[this.selectedIndex].value);">
                            <option value="${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=${currpage}">${currpage}</option>
                            <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                       var="current">
                                <c:if test="${j.index!=page}">
                                    <option value="${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=${j.index}">${j.index}</option>
                                </c:if>
                            </c:forEach></select>页
                            <a href="javascript:void(0)"
                               onclick="targetUrl('${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=${pageModel.nextPage}')"
                               target="_self">下一页</a>
                            <a href="javascript:void(0)"
                               onclick="targetUrl('${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=${pageModel.totalPage}')"
                               target="_self">末页</a>
                        </div>
                        <!-- 分页模块 -->
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>


