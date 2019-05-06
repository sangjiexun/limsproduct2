<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false"
         contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<head>
    <meta name="decorator" content="iframe"/>
    <link href="${pageContext.request.contextPath}/css/iconFont.css"
          rel="stylesheet">
    <!-- 下拉框的样式 -->
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
    <!-- 打印插件的引用 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <script type="text/javascript">
        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/lab/labAnnexList?currpage=1"
        }

        //跳转
        function targetUrl(url) {
            document.form.action = url;
            document.form.submit();
        }

        function ff(div) {
            if (div == 1) {
                $("#f2").hide();
                $("#f1").show();
            }
            if (div == 2) {
                $("#f1").hide();
                $("#f2").show();
            }

        }
    </script>
    <script type="text/javascript">

        function openCalendar(roomId) {
            window.location.href = "${pageContext.request.contextPath}/lab/labReservationCalendar?roomId=" + roomId;
        }

    </script>
</head>
<body>
<!--导航  -->
<%--<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">实验室课表</a></li>
			<li class="end"><a href="javascript:void(0)">实验室日课表</a></li>
		</ul>
	</div>
</div>--%>
<!--导航结束  -->

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup1">
            <div class="TabbedPanelsContent">
                <div class="tool-box">
                    <ul>
                        <li>实验室日课表：</li>
                    </ul>
                </div>
            </div>
            <div class="content-box">
                <table>
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>课程名称</th>
                        <th>上课时间</th>
                        <th>上课班级</th>
                       <%-- <th>实验项目</th>--%>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${labTimetable}" var="s" varStatus="i">
                        <c:forEach items="${s.timetableAppointment.timetableAppointmentSameNumbers}" var="tasn">
                            <c:if test="${week >= tasn.startWeek and week <= tasn.endWeek}">
                            <tr>
                                <td>
                                        ${i.count+(currpage-1)*pageSize }&nbsp;
                                </td>
                                <td>
                                    <c:if test="${s.timetableAppointment.timetableStyle==7}">
                                        <c:forEach items="${s.timetableAppointment.labReservation}" var="e">
                                            实验室预约
                                        </c:forEach>

                                    </c:if>
                                    <c:if test="${s.timetableAppointment.timetableStyle!=7&&s.timetableAppointment.schoolCourseInfo ne null}">
                                        ${s.timetableAppointment.schoolCourseInfo.courseName}
                                    </c:if>
                                </td>
                                <td>
                                        ${tasn.startClass}-${tasn.endClass}&nbsp;
                                </td>
                                <td>
                                    <c:if test="${s.timetableAppointment.timetableStyle!=7}">
                                        <c:forEach items="${s.timetableAppointment.schoolCourse.schoolClasseses}"
                                                   var="e">
                                            ${e.className}&nbsp;
                                        </c:forEach>
                                        <%--${s.timetableAppointment.schoolCourseInfo.courseName}--%>
                                    </c:if>
                                    <c:if test="${s.timetableAppointment.timetableStyle==7}">
                                        ${s.timetableAppointment.schoolClasses.className}&nbsp;
                                    </c:if>
                                </td>
                                    <%-- <td>${studentNumber}</td>--%>
                            </tr>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>

        <!-- 下拉框的js -->

        <script
                src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>

        <script
                src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
                type="text/javascript" charset="utf-8"></script>

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
        </script>
    </div>
</div>