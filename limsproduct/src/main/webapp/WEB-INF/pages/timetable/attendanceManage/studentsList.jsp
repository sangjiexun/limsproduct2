<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html >
<head>
    <meta name="decorator" content="iframe"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
    <script type="text/javascript">
        function saveAttendance(url) {
            $.ajax({
                url: url,
                type:"GET",
                dataType:'text',
                success:function(data){//AJAX查询成功
                    window.location.reload();
                }
            });
        }
    </script>
</head>

<body>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">考勤列表</div>
                    </div>
                    <div class="content-box">
                        <table  class="tb"  id="my_show">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>姓名</th>
                                <th>学号</th>
                                <th>机考情况</th>
                                <th>手动考勤</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list}" var="t" varStatus="i">
                                <tr>
                                    <td>${i.count}</td>
                                    <td>${t.userByUserNumber.cname}</td>
                                    <td>${t.userByUserNumber.username}</td>
                                    <td><c:if test="${t.attendanceMachine eq 0}">×</c:if>
                                        <c:if test="${t.attendanceMachine ne 0}">√</c:if></td>
                                    <td><c:if test="${t.actualAttendance eq 0}">×</c:if>
                                        <c:if test="${t.actualAttendance ne 0}">√</c:if></td>
                                    <td><c:if test="${t.actualAttendance eq 0}">
                                        <a href="javascript:saveAttendance('${pageContext.request.contextPath}/saveAttendance/${t.id}')">手动考勤</a></c:if>
                                        <c:if test="${t.actualAttendance ne 0}">已考勤</c:if>
                                        <c:if test="${t.attendanceMachine ne 0}">已刷卡</c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>