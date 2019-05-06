<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.equipmentlend-resources"/>

<!doctype html>
<html>

<head>
    <meta name="decorator" content="iframe"/>
    <style>
        .content_box td {
            border-left: 1px dotted #ccc
        }

        #fixedtop th {
            height: 41px;
            lien-height: 41px;
        }
    </style>
    <!-- 表头冻结[end] -->
    <style>
        .tab_lab th {
            text-align: center;
        }

        body > .content_box.classTable {
            box-sizing: border-box;
            width: 98%;
            left: 1% !important;
            box-sizing: border-box;
            padding: 0 21px;
            margin: -10px 0 0;
        }

        body > .content_box.classTable .tab_th_average {
            width: 100% !important;
        }

        .tab_th_average th {
            width: 12.5% !important;
        }

        .tab_lab .tb font {
            font-weight: bold;
            clear: both;
            overflow: hidden;
            display: block;
            color: #5492be;
            padding: 15px 0 8px;
        }

        .timetable_btn {
            padding: 8px 0 15px;
            border-bottom: 1px solid #cdcdcd;
        }

        td.tb > div font:first-child {
            padding: 0 0 8px;
        }

        td.tb > div .timetable_btn:last-child {
            border: none;
            margin: 8px 0 0;
        }
    </style>
</head>
<body>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div>
                        <form action="${pageContext.request.contextPath}/timetable/allTimetableAppointment" method="post">
                            <select name="academyNumber">
                                <option value="-1">全部学院</option>
                                <c:forEach items="${schoolAcademies}" var="academy" varStatus="i">
                                    <c:if test="${academy.academyNumber eq academyNumber}">
                                        <option value="${academy.academyNumber}" selected>${academy.academyName}</option>
                                    </c:if>
                                    <c:if test="${academy.academyNumber ne academyNumber}">
                                        <option value="${academy.academyNumber}">${academy.academyName}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <select name="labRoom">
                                <option value="-1">全部实验室</option>
                                <c:forEach items="${labRooms}" var="l" varStatus="i">
                                    <c:if test="${l.id eq labRoom}">
                                        <option value="${l.id}" selected>${l.labRoomName}</option>
                                    </c:if>
                                    <c:if test="${l.id ne labRoom}">
                                        <option value="${l.id}">${l.labRoomName}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <input type="submit" value="查询">
                        </form>
                    </div>
                    <div class="content_box classTable">
                        <table class="tab_lab tab_th_average" style="margin:10px 0;">
                            <thead>
                            <tr>
                                <th>节次</th>
                                <th id="weekdayName1">星期一</th>
                                <th id="weekdayName2">星期二</th>
                                <th id="weekdayName3">星期三</th>
                                <th id="weekdayName4">星期四</th>
                                <th id="weekdayName5">星期五</th>
                                <th id="weekdayName6">星期六</th>
                                <th id="weekdayName0">星期日</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="class" varStatus="cStatus" begin="1" end="12">
                                <tr>
                                    <c:if test="${cStatus.index==1}">
                                        <td class="tbl tbct" style="width:60px"><span id="className1">1</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==2}">
                                        <td class="tbl tbct" style="width:60px"><span id="className2">2</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==3}">
                                        <td class="tbl tbct" style="width:60px"><span id="className3">3</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==4}">
                                        <td class="tbl tbct" style="width:60px"><span id="className4">4</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==5}">
                                        <td class="tbl tbct" style="width:60px"><span id="className5">5</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==6}">
                                        <td class="tbl tbct" style="width:60px"><span id="className6">6</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==7}">
                                        <td class="tbl tbct" style="width:60px"><span id="className7">7</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==8}">
                                        <td class="tbl tbct" style="width:60px"><span id="className8">8</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==9}">
                                        <td class="tbl tbct" style="width:60px"><span id="className9">9</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==10}">
                                        <td class="tbl tbct" style="width:60px"><span id="className10">10</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==11}">
                                        <td class="tbl tbct" style="width:60px"><span id="className11">11</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==12}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12">12</span></td>
                                    </c:if><c:forEach begin="1" end="7" varStatus="iWeek">
                                    <td class="tb" ondblclick="showTimetable('4','${iWeek.count}','${cStatus.index}')"
                                        valign="top">
                                        <div style="font-size:10px;line-height:15px;">
                                            <c:set var="academyCourseCode" value=""/>
                                            <c:set var="commonTeacher" value=""/>
                                            <c:set var="commonLab" value=""/>
                                            <c:set var="selfCourseCode" value=""/>
                                            <!--教务选课组  -->
                                            <c:set var="arrayValue" value=""/>
                                            <c:forEach var="ta" items="${timetableAppointment}" varStatus="lStatus">
                                                <c:if test="${ta.weekday==iWeek.count}">
                                                    <!--周次的逗号分隔串 -->
                                                    <c:if test="${ta.timetableAppointmentSameNumbers.size()>0}">
                                                        <c:forEach var="tas"
                                                                   items="${ta.timetableAppointmentSameNumbers}">
                                                            <c:if test="${tas.startClass<=cStatus.index&&tas.endClass>=cStatus.index}">
                                                                <span style="font-weight: bold">课程名称：</span>${ta.schoolCourse.courseName}${ta.timetableSelfCourse.schoolCourseInfo.courseName}<br>
                                            <span style="font-weight: bold">授课教师：</span>
                                                                <c:forEach items="${ta.timetableTeacherRelateds}" var="ttr" varStatus="i">
                                                                    ${ttr.user.cname}
                                                                    <c:if test="${!i.last}">
                                                                        ,
                                                                    </c:if>
                                                                </c:forEach><br>
                                                                <span style="font-weight: bold">指导教师：</span>
                                                                <c:forEach items="${ta.timetableTutorRelateds}" var="ttur" varStatus="i">
                                                                    ${ttur.user.cname}
                                                                    <c:if test="${!i.last}">
                                                                        ,
                                                                    </c:if>
                                                                </c:forEach><br>
                                                                <c:if test="${tas.startWeek==tas.endWeek}">
                                                                    <span style="font-weight: bold">周次：</span>${tas.startWeek }
                                                                    <br>
                                                                </c:if>
                                                                <c:if test="${tas.startWeek!=tas.endWeek}">
                                                                    <span style="font-weight: bold">周次：</span>${tas.startWeek }-${tas.endWeek }
                                                                    <br>
                                                                </c:if>
                                                                <span style="font-weight: bold">节次：</span>${tas.startClass}-${tas.endClass}
                                                                <br>
                                                                <c:set var="sameLabRoom" value="-"/>
                                                                <c:forEach var="cur"
                                                                           items="${ta.timetableLabRelateds}">
                                                                    <c:if test="${!fn:contains(sameLabRoom, cur.labRoom.id)}">
                                                                    <span style="font-weight: bold">实验室：</span>${cur.labRoom.labRoomName}
                                                                        <c:set var="sameLabRoom" value="-${cur.labRoom.id}-"/>
                                                                    </c:if>
                                                                </c:forEach><br>
                                                                <span style="font-weight: bold">状态：</span>
                                                                <c:choose>
                                                                    <c:when test="${ta.status eq 1}">
                                                                        <span style="color: green">已发布</span>
                                                                    </c:when>
                                                                    <c:when test="${ta.status eq 2}">
                                                                        <span style="color: yellowgreen">待审核</span>
                                                                    </c:when>
                                                                    <c:when test="${ta.status eq 3}">
                                                                        <span style="color: blue">待发布</span>
                                                                    </c:when>
                                                                    <c:when test="${ta.status eq 5}">
                                                                        <span style="color: orange">审核中</span>
                                                                    </c:when>
                                                                    <c:when test="${ta.status eq 10}">
                                                                        <span style="color: grey">排课中</span>
                                                                    </c:when>
                                                                    <c:when test="${ta.status eq 15}">
                                                                        <span style="color: purple">调课前数据</span>
                                                                    </c:when>
                                                                    <c:when test="${ta.status eq 11 and ta.adjustStatus eq 1}">
                                                                        <span style="color: purple">调课后数据</span>
                                                                    </c:when>
                                                                    <c:when test="${ta.status eq 11 and ta.adjustStatus eq 0}">
                                                                        <span style="color: purple">停课中</span>
                                                                    </c:when>
                                                                    <c:when test="${ta.status eq 12 and ta.adjustStatus==16}">
                                                                        <span style="color: purple">停课审核中</span>
                                                                    </c:when>
                                                                    <c:when test="${ta.status eq 12 and ta.adjustStatus==1}">
                                                                        <span style="color: purple">调课审核中</span>
                                                                    </c:when>
                                                                    <c:when test="${ta.status eq 16}">
                                                                        <span style="color: purple">已停课</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span style="font-weight: bold">无</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <br>
                                                                <br>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                </c:if>
                                                <%-- </c:if> --%>
                                            </c:forEach>
                                        </div>
                                    </td>
                                </c:forEach>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
