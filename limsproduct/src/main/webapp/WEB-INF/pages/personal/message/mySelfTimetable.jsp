<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<fmt:setBundle basename="bundles.equipmentlend-resources" />

<!doctype html>
<html>

<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/SpryTabbedPanels.js">
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/table-head.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/font/css/font-awesome.min.css" />
    <meta name="decorator" content="iframe" />
    <%--<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />--%>
    <link href="${pageContext.request.contextPath}/css/SpryTabbedPanels.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/lib.css">
    <!-- 表头冻结[start] -->
    <script type="text/javascript">

    </script>
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
        body>.content_box.classTable {
            box-sizing: border-box;
            width: 98%;
            left: 1%!important;
            box-sizing: border-box;
            padding: 0 21px;
            margin: -10px 0 0;
        }
        body>.content_box.classTable .tab_th_average {
            width: 100%!important;
        }
        .tab_th_average th{
            width:12.5%!important;
        }
        .tab_lab .tb font {
            font-weight: bold;
            clear: both;
            overflow: hidden;
            display: block;
            color: #5492be;
            padding:15px 0 8px;
        }
        .timetable_btn{
            padding:8px 0 15px;
            border-bottom:1px solid #cdcdcd;
        }
        td.tb>div font:first-child {
            padding: 0 0 8px;
        }
        td.tb>div .timetable_btn:last-child {
            border: none;
            margin: 8px 0 0;
        }
    </style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li>
                <a href="javascript:void(0)">
                    <spring:message code="left.my.workspace" />
                </a>
            </li>
            <li class="end">
                <a href="javascript:void(0)">
                    <spring:message code="left.class.schedule" />
                </a>
            </li>

        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="${pageContext.request.contextPath}/personal/message/mySelfTimetable?tage=0&currpage=1">我的课表(${cname}--${term})</a>
            </li>
            <a class="showClassTable btn btn-new">
                <%--<i class="fa-table fa"></i>--%>切换到我的课程</a>
<%--            <li class="TabbedPanelsTab1" id="s2">
                <a href="${pageContext.request.contextPath}/timetable/timetableChangeAuditList?page=1&tage=0&isaudit=2">我的申请</a>
            </li>
            <li class="TabbedPanelsTab1" id="s3">
                <a href="${pageContext.request.contextPath}/timetable/timetableChangeAuditList?page=1&tage=0&isaudit=1">我的审核</a>
            </li>--%>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <%--<div class="title classTable" style="display:none;">我的课表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;${cname}--${term}--%>
                        <%--<a class="showClassTable btn btn-new">--%>
                            <%--&lt;%&ndash;<i class="fa-table fa"></i>&ndash;%&gt;切换到我的课程</a>--%>
                    <%--</div>--%>
                    <div class="content_box classTable" style="display:none;">
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
                            <c:set var="academyCourseCode" value=""></c:set>
                            <c:set var="commonLab" value=""></c:set>
                            <c:set var="selfCourseCode" value=""></c:set>
                            <c:forEach var="class" varStatus="cStatus" begin="1" end="12">
                                <tr>
                                    <c:if test="${cStatus.index==1}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">1</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==2}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">2</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==3}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">3</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==4}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">4</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==5}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">5</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==6}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">6</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==7}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">7</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==8}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">8</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==9}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">9</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==10}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">10</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==11}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">11</span></td>
                                    </c:if>
                                    <c:if test="${cStatus.index==12}">
                                        <td class="tbl tbct" style="width:60px"><span id="className12-13">12</span></td>
                                    </c:if>
                                    <c:forEach begin="1" end="7" varStatus="iWeek">
                                        <td class="tb" ondblclick="showTimetable('4','${iWeek.count}','${cStatus.index}')" valign="top">
                                            <div style="font-size:10px;line-height:15px;">
                                                <c:set var="academyCourseCode" value=""></c:set>
                                                <c:set var="commonTeacher" value=""></c:set>
                                                <c:set var="commonLab" value=""></c:set>
                                                <c:set var="selfCourseCode" value=""></c:set>
                                                <!--教务选课组  -->
                                                <c:if test="${role==0}">
                                                    <c:set var="arrayValue" value="" />
                                                    <c:forEach var="ta" items="${timetableAppointment}" varStatus="lStatus">
                                                        <c:if test="${ta.status==1}">
                                                            <c:if test="${ta.weekday==iWeek.count}">
                                                                <!--周次的逗号分隔串 -->
                                                                <c:if test="${ta.timetableAppointmentSameNumbers.size()>0}">
                                                                    <c:forEach var="tas"
                                                                               items="${ta.timetableAppointmentSameNumbers}">
                                                                        <c:if test="${tas.startClass<=cStatus.index&&tas.endClass>=cStatus.index}">
                                                                            <c:if test="${academyCourseCode!=ta.schoolCourseDetail.schoolCourse.courseCode}">
                                                                                <c:if test="${!empty academyCourseCode}">
                                                                                    <!-- 冒泡算法排序 -->
                                                                                    <!-- 定义数组 -->
                                                                                    <c:set var="arrayValue"
                                                                                           value="${arrayValue},"/>
                                                                                    <c:set var="array"
                                                                                           value="${fn:split(arrayValue, ',')}"/>
                                                                                    <!-- 数组长度 -->
                                                                                    <c:set var="length1"
                                                                                           value="${fn:length(array)}"/>
                                                                                    <c:forEach var="i" begin="0"
                                                                                               end="${length1}">
                                                                                        <c:forEach var="j" begin="0"
                                                                                                   end="${length1-i}">
                                                                                            <c:if test="${fn:split(array[j], '-')[0]-fn:split(array[j+1], '-')[0]>0&&j<length1-1}">

                                                                                                <!-- 中间变量：一次循环的最小值和最大值 -->
                                                                                                <c:set var="max"
                                                                                                       value=",${array[j]},"/>
                                                                                                <c:set var="min"
                                                                                                       value=",${array[j+1]},"/>
                                                                                                <!-- 字符串替换生成新数组（jstl无数组赋值方法） -->
                                                                                                <c:set var="arrayValue"
                                                                                                       value="${fn:replace(arrayValue,min,',-1,')}"/>
                                                                                                <c:set var="arrayValue"
                                                                                                       value="${fn:replace(arrayValue,max,min )}"/>
                                                                                                <c:set var="arrayValue"
                                                                                                       value="${fn:replace(arrayValue,',-1,', max)}"/>
                                                                                                <c:set var="array"
                                                                                                       value="${fn:split(arrayValue, ',')}"/>
                                                                                            </c:if>
                                                                                        </c:forEach>
                                                                                    </c:forEach>
                                                                                    <!-- 显示排序好的周次 -->
                                                                                    <c:forEach var="week"
                                                                                               items="${array}">
                                                                                        ${week}
                                                                                    </c:forEach>
                                                                                    <c:set var="arrayValue" value=""/>
                                                                                    <br>
                                                                                </c:if>
                                                                                <div style="width:45px;white-space: nowrap;text-overflow:ellipsis;overflow: hidden;"
                                                                                     title="${ta.schoolCourseDetail.schoolCourse.courseName}">
                                                                                        ${ta.schoolCourseDetail.schoolCourse.courseName}
                                                                                </div>

                                                                                <c:forEach var="aa"
                                                                                           items="${ta.timetableTeacherRelateds}"
                                                                                           varStatus="tStatus">
                                                                                    <label title="${aa.user.cname}">${aa.user.cname}</label>
                                                                                </c:forEach>
                                                                                <!--排课记录是连续的，子表无记录 -->
                                                                                <c:if test="${tas.startWeek==tas.endWeek}">
                                                                                    周次：
                                                                                    <c:set var="arrayValue"
                                                                                              value="${arrayValue},${tas.startWeek}"/>
                                                                                    <br>
                                                                                </c:if>
                                                                                <c:if test="${tas.startWeek!=tas.endWeek}">
                                                                                    周次：
                                                                                    <c:set var="arrayValue"
                                                                                           value="${arrayValue},${tas.startWeek}-${tas.endWeek }"/>
                                                                                    <br>
                                                                                </c:if>
                                                                                节次：${tas.startClass}-${tas.endClass}
                                                                                <br>
                                                                            </c:if>
                                                                            <!--如果是相同的选课组，则合并周次显示 -->

                                                                            <!--实训室变量 -->
                                                                            <c:set var="tempLab" value=""/>
                                                                            <c:forEach var="lab"
                                                                                       items="${ta.timetableLabRelateds}">
                                                                                <c:set var="tempLab"
                                                                                       value="${tempLab},${lab.labRoom.id},"/>
                                                                            </c:forEach>

                                                                            <!--教师变量 -->
                                                                            <c:set var="tempTeacher" value=""/>
                                                                            <c:forEach var="teacher"
                                                                                       items="${ta.timetableTeacherRelateds}">
                                                                                <c:set var="tempTeacher"
                                                                                       value="${tempTeacher},${lab.user.username},"/>
                                                                            </c:forEach>
                                                                            <c:if test="${fn:contains('fdulims',PROJECT_NAME)}">
                                                                                <br>
                                                                                <c:if test="${livePathMap[ta.id] eq null}">
                                                                                    <label>无直播</label>
                                                                                </c:if>
                                                                                <c:if test="${livePathMap[ta.id] ne null}">
                                                                                    <a href="${pageContext.request.contextPath}/timetable/playTimetableLive?livePath=${livePathMap[ta.id]}&name=${ta.schoolCourseDetail.schoolCourse.courseName}" target="_blank">直播</a>
                                                                                </c:if>
                                                                            <br>
                                                                                点播：<c:forEach items="${httpPathMap[ta.id]}" var="httpPath" varStatus="httpI">
                                                                                <a href="${httpPath}" target="_blank">资源${httpI.count}</a>
                                                                            </c:forEach><br>
                                                                            </c:if>

                                                                            <c:if test="${academyCourseCode==ta.schoolCourseDetail.schoolCourse.courseCode&&tempLab !=commonLab}">
                                                                                <!--排课记录是连续的，子表无记录 -->
                                                                                <c:if test="${tas.startWeek==tas.endWeek}">
                                                                                    周次：
                                                                                    <c:set var="arrayValue"
                                                                                           value="${arrayValue},${tas.startWeek}"/>
                                                                                    <br>
                                                                                </c:if>
                                                                                <c:if test="${tas.startWeek!=tas.endWeek}">
                                                                                    周次：
                                                                                    <c:set var="arrayValue"
                                                                                           value="${arrayValue},${tas.startWeek}-${tas.endWeek }"/>
                                                                                    <br>
                                                                                </c:if>
                                                                                节次：${tas.startClass}-${tas.endClass}
                                                                                <br>

                                                                            </c:if>
                                                                            <c:set var="academyCourseCode"
                                                                                   value="${ta.schoolCourseDetail.schoolCourse.courseCode}"></c:set>
                                                                            <!--实训室变量 -->
                                                                            <c:set var="commonLab" value=""/>
                                                                            <c:forEach var="lab"
                                                                                       items="${ta.timetableLabRelateds}">
                                                                                <c:set var="commonLab"
                                                                                       value="${commonLab}${lab.labRoom.id},"/>
                                                                            </c:forEach>

                                                                            <!--教师变量 -->
                                                                            <c:set var="commonTeacher" value=""/>
                                                                            <c:forEach var="teacher"
                                                                                       items="${ta.timetableTeacherRelateds}">
                                                                                <c:set var="commonTeacher"
                                                                                       value="${commonTeacher}${lab.user.username},"/>
                                                                            </c:forEach>
                                                                            <c:if test="${fn:contains('fdulims',PROJECT_NAME)}">
                                                                                <br>
                                                                                <c:if test="${livePathMap[ta.id] eq null}">
                                                                                    <label>无直播</label>
                                                                                </c:if>
                                                                                <c:if test="${livePathMap[ta.id] ne null}">
                                                                                <a href="${pageContext.request.contextPath}/timetable/playTimetableLive?livePath=${livePathMap[ta.id]}&name=${ta.schoolCourseDetail.schoolCourse.courseName}" target="_blank">直播</a>
                                                                                </c:if>
                                                                            <br>
                                                                                点播：<c:forEach items="${httpPathMap[ta.id]}" var="httpPath" varStatus="httpI">
                                                                                <a href="${httpPath}" target="_blank">资源${httpI.count}</a>
                                                                            </c:forEach><br>
                                                                            </c:if>

                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:if>
                                                            </c:if>
                                                        </c:if>
                                                        <%-- </c:if> --%>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${role==1}">
                                                    <!--教务推送课程  -->
                                                    <c:forEach var="ta" items="${timetableTeacherRelated}" varStatus="lStatus">
                                                        <c:if test="${ta.timetableAppointment.status==1}">
                                                            <c:if test="${ta.timetableAppointment.weekday==iWeek.count}">
                                                                <!--周次的逗号分隔串 -->
                                                                <c:if test="${ta.timetableAppointment.timetableAppointmentSameNumbers.size()>0}">
                                                                    <c:forEach var="tas"
                                                                               items="${ta.timetableAppointment.timetableAppointmentSameNumbers}">
                                                                        <c:if test="${tas.startClass<=cStatus.index&&tas.endClass>=cStatus.index}">
                                                                            <c:if test="${ta.timetableAppointment.timetableSelfCourse eq null && ta.timetableAppointment.timetableStyle ne 7}">
                                                                                <font color="green">
                                                                                    课程名称：${ta.timetableAppointment.schoolCourse.courseName}</font>
                                                                                <c:set var="thisCourseName" value="${ta.timetableAppointment.schoolCourse.courseName}"/>
                                                                            </c:if>
                                                                            <c:if test="${ta.timetableAppointment.timetableSelfCourse ne null}">
                                                                                <font color="green">
                                                                                    课程名称：${ta.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}</font>
                                                                                <c:set var="thisCourseName" value="${ta.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}"/>
                                                                            </c:if>
                                                                            <c:if test="${ta.timetableAppointment.timetableStyle eq 7}">
                                                                                <font color="green">
                                                                                    实验室预约</font>
                                                                                <c:set var="thisCourseName" value="实验室预约"/>
                                                                            </c:if>
                                                                            教师：${ta.user.cname}<br>
                                                                            <!--排课记录是连续的，子表无记录 -->
                                                                            <c:if test="${tas.startWeek==tas.endWeek}">
                                                                                周次：${tas.startWeek }
                                                                                <br>
                                                                            </c:if>
                                                                            <c:if test="${tas.startWeek!=tas.endWeek}">
                                                                                周次：${tas.startWeek }-${tas.endWeek }
                                                                                <br>
                                                                            </c:if>
                                                                            节次：${tas.startClass}-${tas.endClass}
                                                                            <br>
                                                                            <c:set var="sameLabRoom" value="-"/>
                                                                            <c:forEach var="cur"
                                                                                       items="${ta.timetableAppointment.timetableLabRelateds}">
                                                                                <c:if test="${!fn:contains(sameLabRoom, cur.labRoom.id)}">
                                                                                    实验室：${cur.labRoom.labRoomName}
                                                                                    <c:set var="sameLabRoom" value="-${cur.labRoom.id}-"/>
                                                                                </c:if>
                                                                            </c:forEach>
                                                                            <c:if test="${fn:contains('fdulims',PROJECT_NAME)}">
                                                                                <br>
                                                                                <c:if test="${livePathMap[ta.timetableAppointment.id] eq null}">
                                                                                    <label>无直播</label>
                                                                                </c:if>
                                                                                <c:if test="${livePathMap[ta.timetableAppointment.id] ne null}">
                                                                                <a href="${pageContext.request.contextPath}/timetable/playTimetableLive?livePath=${livePathMap[ta.timetableAppointment.id]}&name=${thisCourseName}" target="_blank">直播</a>
                                                                                </c:if>
                                                                            <br>
                                                                                点播：<c:forEach items="${httpPathMap[ta.timetableAppointment.id]}" var="httpPath" varStatus="httpI">
                                                                                <a href="${httpPath}" target="_blank">资源${httpI.count}</a>
                                                                            </c:forEach><br>
                                                                            </c:if>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:if>
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>

                                                <c:set var="arrayValue" value="" />
                                                <c:set var="academyCourseCode" value=""></c:set>
                                                <c:set var="commonLab" value=""></c:set>
                                                <c:set var="commonTeacher" value=""></c:set>
                                                <c:set var="selfCourseCode" value=""></c:set>

                                                <!--自建选课组排课  -->
                                                <c:forEach var="ta" items="${selfTimetableAppointment}" varStatus="lStatus">
                                                    <c:if test="${ta.status==1}">
                                                        <c:if test="${ta.weekday==iWeek.count}">
                                                            <!--分组排课  -->
                                                            <c:if test="${ta.timetableStyle==6||ta.timetableStyle==5}">
                                                                <c:if test="${ta.timetableAppointmentSameNumbers.size()>0}">
                                                                    <c:forEach var="tas"
                                                                               items="${ta.timetableAppointmentSameNumbers}">
                                                                        <c:if test="${tas.startClass<=cStatus.index&&tas.endClass>=cStatus.index}">
                                                                            <c:if test="${selfCourseCode!=ta.timetableSelfCourse.courseCode}">
                                                                                <c:if test="${!empty selfCourseCode}">
                                                                                    <!-- 冒泡算法排序 -->
                                                                                    <!-- 定义数组 -->
                                                                                    <c:set var="arrayValue"
                                                                                           value="${arrayValue},"/>
                                                                                    <c:set var="array"
                                                                                           value="${fn:split(arrayValue, ',')}"/>
                                                                                    <!-- 数组长度 -->
                                                                                    <c:set var="length1"
                                                                                           value="${fn:length(array)}"/>
                                                                                    <c:forEach var="i" begin="0"
                                                                                               end="${length1}">
                                                                                        <c:forEach var="j" begin="0"
                                                                                                   end="${length1-i}">

                                                                                            <c:if test="${fn:split(array[j], '-')[0]-fn:split(array[j+1], '-')[0]>0&&j<length1-1}">
                                                                                                <!-- 中间变量：一次循环的最小值和最大值 -->
                                                                                                <c:set var="max"
                                                                                                       value=",${array[j]},"/>
                                                                                                <c:set var="min"
                                                                                                       value=",${array[j+1]},"/>
                                                                                                <!-- 字符串替换生成新数组（jstl无数组赋值方法） -->
                                                                                                <c:set var="arrayValue"
                                                                                                       value="${fn:replace(arrayValue,min,',-1,')}"/>
                                                                                                <c:set var="arrayValue"
                                                                                                       value="${fn:replace(arrayValue,max,min )}"/>
                                                                                                <c:set var="arrayValue"
                                                                                                       value="${fn:replace(arrayValue,',-1,', max)}"/>
                                                                                                <c:set var="array"
                                                                                                       value="${fn:split(arrayValue, ',')}"/>
                                                                                            </c:if>
                                                                                        </c:forEach>
                                                                                    </c:forEach>
                                                                                    <!-- 显示排序好的周次 -->
                                                                                    <c:forEach var="week"
                                                                                               items="${array}">
                                                                                        ${week}
                                                                                    </c:forEach>
                                                                                    <c:set var="arrayValue" value=""/>
                                                                                    <br>
                                                                                </c:if>
                                                                                ${ta.timetableSelfCourse.schoolCourseInfo.courseName}&nbsp;
                                                                                <!--排课记录是连续的，子表无记录 -->
                                                                                <c:if test="${tas.startWeek==tas.endWeek}">
                                                                                    周次：
                                                                                    <c:set var="arrayValue"
                                                                                           value="${arrayValue},${tas.startWeek}"/>
                                                                                    <br>
                                                                                </c:if>
                                                                                <c:if test="${tas.startWeek!=tas.endWeek}">
                                                                                    周次：
                                                                                    <c:set var="arrayValue"
                                                                                           value="${arrayValue},${tas.startWeek}-${tas.endWeek }"/>
                                                                                    <br>
                                                                                </c:if>
                                                                                节次：${tas.startClass}-${tas.endClass}
                                                                                <br>

                                                                            </c:if>
                                                                            <%-- <br>${commonLab} --%>
                                                                            <c:set var="commonLab" value=""/>
                                                                            <c:set var="commonTeacher" value=""></c:set>
                                                                            <c:set var="tempLab" value=""></c:set>
                                                                            <c:forEach var="lab"
                                                                                       items="${ta.timetableLabRelateds}">
                                                                                <c:set var="tempLab"
                                                                                       value="${tempLab},${lab.labRoom.id},"/>
                                                                            </c:forEach>

                                                                            <c:set var="tempTeacher" value=""></c:set>
                                                                            <c:forEach var="teacher"
                                                                                       items="${ta.timetableTeacherRelateds}">
                                                                                <c:set var="tempTeacher"
                                                                                       value="${tempTeacher},${teacher.user.username},"/>
                                                                            </c:forEach>

                                                                            <c:if test="${selfCourseCode eq ta.timetableSelfCourse.courseCode&&tempLab !=commonLab}">
                                                                                <!--排课记录是连续的，子表无记录 -->
                                                                                <c:if test="${tas.startWeek==tas.endWeek}">
                                                                                    周次：${tas.startWeek }
                                                                                    <br>
                                                                                </c:if>
                                                                                <c:if test="${tas.startWeek!=tas.endWeek}">
                                                                                    周次：${tas.startWeek }-${tas.endWeek }
                                                                                    <br>
                                                                                </c:if>
                                                                                节次：${tas.startClass}-${tas.endClass}
                                                                                <br>

                                                                            </c:if>
                                                                            <c:if test="${fn:contains('fdulims',PROJECT_NAME)}">
                                                                                <br>
                                                                                <c:if test="${livePathMap[ta.id] eq null}">
                                                                                    <label>无直播</label>
                                                                                </c:if>
                                                                                <c:if test="${livePathMap[ta.id] ne null}">
                                                                                <a href="${pageContext.request.contextPath}/timetable/playTimetableLive?livePath=${livePathMap[ta.id]}&name=${ta.timetableSelfCourse.schoolCourseInfo.courseName}" target="_blank">直播</a>
                                                                                </c:if>
                                                                            <br>
                                                                                点播：<c:forEach items="${httpPathMap[ta.id]}" var="httpPath" varStatus="httpI">
                                                                                <a href="${httpPath}" target="_blank">资源${httpI.count}</a>
                                                                            </c:forEach><br>
                                                                            </c:if>

                                                                            <c:set var="selfCourseCode"
                                                                                   value="${ta.timetableSelfCourse.courseCode}"></c:set>

                                                                            <!--实训室变量 -->
                                                                            <c:set var="commonLab" value=""/>
                                                                            <c:forEach var="lab"
                                                                                       items="${ta.timetableLabRelateds}">
                                                                                <c:set var="commonLab"
                                                                                       value="${commonLab},${lab.labRoom.id},"/>
                                                                            </c:forEach>

                                                                            <c:set var="commonTeacher" value=""/>
                                                                            <c:forEach var="teacher"
                                                                                       items="${ta.timetableTeacherRelateds}">
                                                                                <c:set var="commonTeacher"
                                                                                       value="${commonTeacher},${teacher.user.username},"/>
                                                                            </c:forEach>

                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:if>
                                                            </c:if>
                                                            <%-- </c:if> --%>
                                                        </c:if>
                                                    </c:if>

                                                </c:forEach>

                                                <!-- 实验室预约 -->
                                                <c:set var="sameStart" value="-"></c:set>
                                                <c:forEach var="ltimetable" items="${timetableLabReservation}" varStatus="">
                                                    <c:if test="${ltimetable.timetableAppointment.weekday==iWeek.count}">
                                                <c:forEach var="entry" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p">
                                                    <c:if test="${entry.startClass<=cStatus.index&&entry.endClass>=cStatus.index}">
                                                        <c:if test="${ltimetable.timetableAppointment.timetableStyle==7}">
                                                            <!-- 显示周次节次 -->
                                                            <c:set var="v_param" value="-${entry.startClass}-" ></c:set>
                                                            <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                                                <hr><font color="green">实验室预约</font><br>
                                                                实验室：&nbsp;${ltimetable.labRoom.labRoomName}(${ltimetable.labRoom.labRoomNumber}) <br>
                                                                <c:if test="${entry.startClass==entry.endClass}">
                                                                    节次：${entry.startClass }
                                                                </c:if>
                                                                <c:if test="${entry.startClass!=entry.endClass}">
                                                                    节次： ${entry.startClass }-${entry.endClass }
                                                                </c:if>
                                                                <br>
                                                                <c:set var="sameStart" value="${sameStart}-${entry.startClass }-"></c:set>
                                                                周次：<c:forEach var="entry1" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"  varStatus="q">
                                                                <c:if test="${entry.startClass==entry1.startClass}">
                                                                    <c:if test="${entry1.startWeek==entry1.endWeek}">
                                                                        ${entry1.startWeek }
                                                                    </c:if>
                                                                    <c:if test="${entry1.startWeek!=entry1.endWeek}">
                                                                        ${entry1.startWeek }-${entry1.endWeek }
                                                                    </c:if>
                                                                </c:if>
                                                            </c:forEach><br>
                                                                创建者：<label>
                                                                <c:forEach items="${ltimetable.timetableAppointment.timetableTeacherRelateds}" var="creator">
                                                                    ${creator.user.cname}
                                                                </c:forEach>
                                                                </label><br>
                                                                人数：<label>${ltimetable.timetableAppointment.groupCount}</label><br>
                                                            </c:if>
                                                        </c:if>
                                                    </c:if>
                                                </c:forEach>
                                                    </c:if>
                                                </c:forEach>
                                                <!-- 实验室预约 -->

                                                <!-- 冒泡算法排序 -->
                                                <!-- 定义数组  -->
                                                <c:set var="arrayValue" value="${arrayValue}," />
                                                <c:set var="array" value="${fn:split(arrayValue, ',')}" />
                                                <!-- 数组长度 -->
                                                <c:set var="length1" value="${fn:length(array)}" />
                                                <c:forEach var="i" begin="0" end="${length1}">
                                                    <c:forEach var="j" begin="0" end="${length1-i}">
                                                        <c:if test="${fn:split(array[j], '-')[0]-fn:split(array[j+1], '-')[0]>0&&j<length1-1}">
                                                            <!-- 中间变量：一次循环的最小值和最大值 -->
                                                            <c:set var="max" value=",${array[j]}," />
                                                            <c:set var="min" value=",${array[j+1]}," />
                                                            <!-- 字符串替换生成新数组（jstl无数组赋值方法） -->
                                                            <c:set var="arrayValue" value="${fn:replace(arrayValue,min,',-1,')}" />
                                                            <c:set var="arrayValue" value="${fn:replace(arrayValue,max,min )}" />
                                                            <c:set var="arrayValue" value="${fn:replace(arrayValue,',-1,', max)}" />
                                                            <c:set var="array" value="${fn:split(arrayValue, ',')}" />
                                                        </c:if>
                                                    </c:forEach>
                                                </c:forEach>
                                                <!-- 显示排序好的周次 -->
                                                <c:forEach var="week" items="${array}">
                                                    ${week}
                                                </c:forEach>
                                            </div>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <%--<div class="title courseTable">我的课程&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;${cname}--${term}--%>
                        <%--<a class="showClassTable btn btn-new">--%>
                            <%--&lt;%&ndash;<i class="fa-table fa"></i>&ndash;%&gt;切换到我的课表</a>--%>
                    <%--</div>--%>
                    <table class="tab_lab courseTable" style="margin:10px 0;">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>课程编号</th>
                            <th>课程名称</th>
                            <th>实验项目名称</th>
                            <th width="30px;">星期</th>
                            <th width="45px;">节次</th>
                            <th width="45px;">周次</th>
                            <th>上课教师</th>
                            <th>指导教师</th>
                            <th><spring:message code="all.trainingRoom.labroom" /></th>
                            <th>审核状态</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="ifRowspan" value="0"></c:set>
                        <c:set var="count" value="1"></c:set>
                        <c:forEach items="${timetableAppointment}" var="current" varStatus="i">
                            <c:forEach items="${current.timetableAppointmentSameNumbers}" var="tas" varStatus="p">
                            <c:set var="rowspan" value="0"></c:set>
                            <tr>
                                <c:forEach items="${timetableAppointment}" var="current1" varStatus="j">
                                    <c:forEach var="entry1" items="${current1.timetableAppointmentSameNumbers}" varStatus="q">
                                    <c:if test="${current1.schoolCourseDetail.schoolCourse.courseCode==current.schoolCourseDetail.schoolCourse.courseCode }">
                                        <c:set value="${rowspan + 1}" var="rowspan" />
                                    </c:if>
                                    <c:if test="${null==current.schoolCourseDetail.schoolCourse.courseCode }">
                                        <c:set value="${1}" var="rowspan" />
                                    </c:if>
                                    </c:forEach>
                                </c:forEach>
                                <c:if test="${rowspan>1&&ifRowspan!=current.schoolCourseDetail.schoolCourse.courseCode }">
                                    <td rowspan="${rowspan }">${count}</td>
                                    <c:set var="count" value="${count+1}"></c:set>
                                </c:if>
                                <c:if test="${rowspan==1 }">
                                    <td>${count}</td>
                                    <c:set var="count" value="${count+1}"></c:set>
                                </c:if>
                                <c:if test="${rowspan>1&&ifRowspan!=current.schoolCourseDetail.schoolCourse.courseCode }">
                                    <td rowspan="${rowspan }">${current.schoolCourseDetail.schoolCourse.schoolCourseInfo.courseNumber}</td>
                                    <td rowspan="${rowspan }">${current.schoolCourseDetail.schoolCourse.courseName}</td>
                                </c:if>

                                <c:if test="${rowspan==1 }">
                                    <td>${current.schoolCourseDetail.schoolCourse.schoolCourseInfo.courseNumber}</td>
                                    <td>${current.schoolCourseDetail.schoolCourse.courseName}</td>
                                </c:if>
                                <td>
                                    <c:forEach var="entry" items="${current.timetableItemRelateds}">
                                        <c:if test="${empty entry.operationItem||entry.operationItem.id==0}">
                                            <%--${current.schoolCourseDetail.schoolCourse.courseName}(课程名称)--%>
                                            —

                                        </c:if>
                                        <c:if test="${not empty entry.operationItem&&entry.operationItem.id!=0 }">
                                            ${entry.operationItem.lpName}&nbsp;&nbsp;&nbsp;
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${current.timetableItemRelateds.size()==0}">
                                        <%--${current.schoolCourseDetail.schoolCourse.courseName}(课程名称)--%>
                                        —
                                    </c:if>
                                </td>

                                <!--对应星期  -->
                                <td>${current.weekday}</td>
                                <!--对应节次  -->
                                <c:if test="${current.timetableAppointmentSameNumbers.size()==0}">
                                    <td width="45px;">
                                        <c:if test="${current.startClass==current.endClass}">
                                            ${current.startClass }
                                        </c:if>
                                        <c:if test="${current.startClass!=current.endClass}">
                                            ${current.startClass }-${current.endClass }
                                        </c:if>
                                    </td>
                                    <td width="45px;">
                                        <c:if test="${current.startWeek==current.endWeek}">
                                            ${current.startWeek }
                                        </c:if>
                                        <c:if test="${current.startWeek!=current.endWeek}">
                                            ${current.startWeek }-${current.endWeek }
                                        </c:if>
                                    </td>
                                </c:if>
                                <c:if test="${current.timetableAppointmentSameNumbers.size()>0}">
                                    <c:set var="sameStart" value="-"></c:set>
                                    <%--<c:forEach var="entry" items="${current.timetableAppointmentSameNumbers}" varStatus="p">--%>
                                        <c:set var="v_param" value="-${tas.startClass}-"></c:set>
                                        <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                            <td width="45px;">
                                                <c:if test="${tas.startClass==tas.endClass}">
                                                    ${tas.startClass }
                                                </c:if>
                                                <c:if test="${tas.startClass!=tas.endClass}">
                                                    ${tas.startClass }-${tas.endClass }
                                                </c:if>
                                            </td>
                                            <td width="45px;">
                                                <c:set var="sameStart" value="${sameStart}-${tas.startClass }-"></c:set>
                                                <%--<c:forEach var="entry1" items="${current.timetableAppointmentSameNumbers}" varStatus="q">--%>
                                                    <%--<c:if test="${entry.startClass==entry1.startClass}">--%>

                                                        <c:if test="${tas.startWeek==tas.endWeek}">
                                                            ${tas.startWeek }
                                                        </c:if>
                                                        <c:if test="${tas.startWeek!=tas.endWeek}">
                                                            ${tas.startWeek }-${tas.endWeek }
                                                        </c:if>

                                                    <%--</c:if>--%>
                                               <%-- </c:forEach>--%>
                                            </td>
                                        </c:if>
                                    <%--</c:forEach>--%>

                                </c:if>
                                <td>
                                    <!--上课教师  -->
                                    <c:forEach var="entry" items="${current.timetableTeacherRelateds}">
                                        <c:out value="${entry.user.cname}" />
                                    </c:forEach>
                                </td>
                                <td>
                                    <!--指导教师  -->
                                    <c:forEach var="entry" items="${current.timetableTutorRelateds}">
                                        <c:out value="${entry.user.cname}" />
                                    </c:forEach>
                                </td>
                                <td>
                                    <!--相关实训室  -->
                                    <c:forEach var="entry" items="${current.timetableLabRelateds}">
                                        <c:out value="${entry.labRoom.labRoomName}" /><br>
                                    </c:forEach>
                                    <c:set var="ifRowspan" value="${current.schoolCourseDetail.schoolCourse.courseCode }"></c:set>
                                </td>
                                <td>
                                    <!--审核状态  -->
                                    <c:if test="${current.status == 1}">已发布</c:if>
                                    <c:if test="${current.status == 2}">一级待审核</c:if>
                                    <c:if test="${current.status == 3}">二级待审核</c:if>
                                    <c:if test="${current.status == 4}">审核拒绝</c:if>
                                    <c:if test="${current.status == 10}">未发布</c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </c:forEach>
                        <c:forEach items="${selfTimetableAppointment}" var="current" varStatus="i">
                        <c:forEach items="${current.timetableAppointmentSameNumbers}" var="tas" varStatus="p">
                            <c:set var="rowspan" value="0"></c:set>
                            <tr>
                                <c:forEach items="${selfTimetableAppointment}" var="current1" varStatus="j">
                                    <c:forEach var="entry1" items="${current1.timetableAppointmentSameNumbers}" varStatus="q">
                                    <c:if test="${current1.timetableSelfCourse.courseCode==current.timetableSelfCourse.courseCode }">
                                        <c:set value="${rowspan + 1}" var="rowspan" />
                                    </c:if>
<%--                                    <c:if test="${null==current.schoolCourseDetail.schoolCourse.courseCode }">--%>
                                    <c:if test="${null==current.timetableSelfCourse.courseCode }">
                                        <c:set value="${1}" var="rowspan" />
                                    </c:if>
                                    </c:forEach>
                                </c:forEach>
                                <c:if test="${rowspan>1&&ifRowspan!=current.timetableSelfCourse.courseCode }">
                                    <td rowspan="${rowspan }">${count}</td>
                                    <c:set var="count" value="${count+1}"></c:set>
                                </c:if>
                                <c:if test="${rowspan==1 }">
                                    <td>${count}</td>
                                    <c:set var="count" value="${count+1}"></c:set>
                                </c:if>
                                <c:if test="${rowspan>1&&ifRowspan!=current.timetableSelfCourse.courseCode }">
                                    <td rowspan="${rowspan }">${current.timetableSelfCourse.schoolCourseInfo.courseNumber}</td>
                                    <td rowspan="${rowspan }">${current.timetableSelfCourse.schoolCourseInfo.courseName}</td>
                                </c:if>

                                <c:if test="${rowspan==1 }">
                                    <td>${current.timetableSelfCourse.schoolCourseInfo.courseNumber}</td>
                                    <td>${current.timetableSelfCourse.schoolCourseInfo.courseName}</td>
                                </c:if>
                                <td>
                                    <c:forEach var="entry" items="${current.timetableItemRelateds}">
                                        <c:if test="${empty entry.operationItem||entry.operationItem.id==0 }">
                                            ${current.timetableSelfCourse.schoolCourseInfo.courseName}(课程名称)
                                        </c:if>
                                        <c:if test="${not empty entry.operationItem&&entry.operationItem.id!=0 }">
                                            ${entry.operationItem.lpName}&nbsp;&nbsp;&nbsp;
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${current.timetableItemRelateds.size()==0}">
                                        ${current.timetableSelfCourse.schoolCourseInfo.courseName}(课程名称)
                                    </c:if>
                                </td>

                                <!--对应星期  -->
                                <td>${current.weekday}</td>
                                <!--对应节次  -->
                                <td colspan=2 width="90px;">
                                    <table>
                                        <c:if test="${current.timetableAppointmentSameNumbers.size()==0}">
                                            <tr>
                                                <td width="45px;">
                                                    <c:if test="${current.startClass==current.endClass}">
                                                        ${current.startClass }
                                                    </c:if>
                                                    <c:if test="${current.startClass!=current.endClass}">
                                                        ${current.startClass }-${current.endClass }
                                                    </c:if>
                                                </td>
                                                <td width="45px;">
                                                    <c:if test="${current.startWeek==current.endWeek}">
                                                        ${current.startWeek }
                                                    </c:if>
                                                    <c:if test="${current.startWeek!=current.endWeek}">
                                                        ${current.startWeek }-${current.endWeek }
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${current.timetableAppointmentSameNumbers.size()>0}">

                                            <c:set var="sameStart" value="-"></c:set>
                                            <%--<c:forEach var="entry" items="${current.timetableAppointmentSameNumbers}" varStatus="p">--%>
                                                <c:set var="v_param" value="-${tas.startClass}-"></c:set>
                                                <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                                    <tr>
                                                        <td width="45px;">
                                                            <c:if test="${tas.startClass==tas.endClass}">
                                                                ${tas.startClass }
                                                            </c:if>
                                                            <c:if test="${tas.startClass!=tas.endClass}">
                                                                ${tas.startClass }-${tas.endClass }
                                                            </c:if>
                                                        </td>
                                                        <td width="45px;">
                                                            <c:set var="sameStart" value="${sameStart}-${entry.startClass }-"></c:set>
                                                            <%--<c:forEach var="entry1" items="${current.timetableAppointmentSameNumbers}" varStatus="q">--%>
                                                                <c:if test="${tas.startClass==tas.startClass}">

                                                                    <c:if test="${tas.startWeek==tas.endWeek}">
                                                                        ${tas.startWeek }
                                                                    </c:if>
                                                                    <c:if test="${tas.startWeek!=tas.endWeek}">
                                                                        ${tas.startWeek }-${tas.endWeek }
                                                                    </c:if>

                                                                </c:if>
                                                            <%--</c:forEach>--%>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            <%--</c:forEach>--%>

                                        </c:if>
                                    </table>
                                </td>
                                <td>
                                    <!--上课教师  -->
                                    <c:forEach var="entry" items="${current.timetableTeacherRelateds}">
                                        <c:out value="${entry.user.cname}" />
                                    </c:forEach>
                                </td>
                                <td>
                                    <!--指导教师  -->
                                    <c:forEach var="entry" items="${current.timetableTutorRelateds}">
                                        <c:out value="${entry.user.cname}" />
                                    </c:forEach>
                                </td>
                                <td>
                                    <!--相关实训室  -->
                                    <c:forEach var="entry" items="${current.timetableLabRelateds}">
                                        <c:out value="${entry.labRoom.labRoomName}" /><br>
                                    </c:forEach>
                                    <c:set var="ifRowspan" value="${current.timetableSelfCourse.courseCode }"></c:set>
                                </td>
                                <td>
                                    <!--审核状态  -->
                                    <c:if test="${current.status == 1}">已发布</c:if>
                                    <c:if test="${current.status == 2}">一级待审核</c:if>
                                    <c:if test="${current.status == 3}">二级待审核</c:if>
                                    <c:if test="${current.status == 4}">审核拒绝</c:if>
                                    <c:if test="${current.status == 10}">未发布</c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </c:forEach>
                        <c:forEach items="${timetableLabReservation}" var="current" varStatus="i">
                            <c:forEach items="${current.timetableAppointment.timetableAppointmentSameNumbers}" var="tas" varStatus="p">
                                <c:set var="rowspan" value="0"></c:set>
                                <tr>
                                    <c:forEach items="${timetableLabReservation}" var="current1" varStatus="j">
                                        <c:forEach var="entry1" items="${current1.timetableAppointment.timetableAppointmentSameNumbers}" varStatus="q">
                                            <c:if test="${current1.timetableAppointment.id==current.timetableAppointment.id }">
                                                <c:set value="${rowspan + 1}" var="rowspan" />
                                            </c:if>
                                            <c:if test="${null==current.timetableAppointment.id }">
                                                <c:set value="${1}" var="rowspan" />
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                    <c:if test="${rowspan>1&&ifRowspan!=current.timetableAppointment.id }">
                                        <td rowspan="${rowspan }">${count}</td>
                                        <c:set var="count" value="${count+1}"></c:set>
                                    </c:if>
                                    <c:if test="${rowspan==1 }">
                                        <td>${count}</td>
                                        <c:set var="count" value="${count+1}"></c:set>
                                    </c:if>
                                    <c:if test="${rowspan>1&&ifRowspan!=current.timetableAppointment.id }">
                                        <td rowspan="${rowspan }"></td>
                                        <td rowspan="${rowspan }">实验室预约</td>
                                    </c:if>

                                    <c:if test="${rowspan==1 }">
                                        <td></td>
                                        <td>实验室预约</td>
                                    </c:if>
                                    <td>
                                    </td>

                                    <!--对应星期  -->
                                    <td>${current.timetableAppointment.weekday}</td>
                                    <!--对应节次  -->
                                    <td colspan=2 width="90px;">
                                        <table>
                                            <c:if test="${current.timetableAppointment.timetableAppointmentSameNumbers.size()==0}">
                                                <tr>
                                                    <td width="45px;">
                                                        <c:if test="${current.timetableAppointment.startClass==current.timetableAppointment.endClass}">
                                                            ${current.timetableAppointment.startClass }
                                                        </c:if>
                                                        <c:if test="${current.timetableAppointment.startClass!=current.timetableAppointment.endClass}">
                                                            ${current.timetableAppointment.startClass }-${current.timetableAppointment.endClass }
                                                        </c:if>
                                                    </td>
                                                    <td width="45px;">
                                                        <c:if test="${current.timetableAppointment.startWeek==current.timetableAppointment.endWeek}">
                                                            ${current.timetableAppointment.startWeek }
                                                        </c:if>
                                                        <c:if test="${current.timetableAppointment.startWeek!=current.timetableAppointment.endWeek}">
                                                            ${current.timetableAppointment.startWeek }-${current.timetableAppointment.endWeek }
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${current.timetableAppointment.timetableAppointmentSameNumbers.size()>0}">

                                                <c:set var="sameStart" value="-"></c:set>
                                                <%--<c:forEach var="entry" items="${current.timetableAppointment.timetableAppointmentSameNumbers}" varStatus="p">--%>
                                                <c:set var="v_param" value="-${tas.startClass}-"></c:set>
                                                <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                                    <tr>
                                                        <td width="45px;">
                                                            <c:if test="${tas.startClass==tas.endClass}">
                                                                ${tas.startClass }
                                                            </c:if>
                                                            <c:if test="${tas.startClass!=tas.endClass}">
                                                                ${tas.startClass }-${tas.endClass }
                                                            </c:if>
                                                        </td>
                                                        <td width="45px;">
                                                            <c:set var="sameStart" value="${sameStart}-${entry.startClass }-"></c:set>
                                                                <%--<c:forEach var="entry1" items="${current.timetableAppointment.timetableAppointmentSameNumbers}" varStatus="q">--%>
                                                            <c:if test="${tas.startClass==tas.startClass}">

                                                                <c:if test="${tas.startWeek==tas.endWeek}">
                                                                    ${tas.startWeek }
                                                                </c:if>
                                                                <c:if test="${tas.startWeek!=tas.endWeek}">
                                                                    ${tas.startWeek }-${tas.endWeek }
                                                                </c:if>

                                                            </c:if>
                                                                <%--</c:forEach>--%>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <%--</c:forEach>--%>

                                            </c:if>
                                        </table>
                                    </td>
                                    <td>
                                        <!--上课教师  -->
                                        <c:forEach var="entry" items="${current.timetableAppointment.timetableTeacherRelateds}">
                                            <c:out value="${entry.user.cname}" />
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <!--指导教师  -->
                                        <c:forEach var="entry" items="${current.timetableAppointment.timetableTutorRelateds}">
                                            <c:out value="${entry.user.cname}" />
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <!--相关实训室  -->
                                        <c:forEach var="entry" items="${current.timetableAppointment.timetableLabRelateds}">
                                            <c:out value="${entry.labRoom.labRoomName}" /><br>
                                        </c:forEach>
                                        <c:set var="ifRowspan" value="${current.timetableAppointment.id }"></c:set>
                                    </td>
                                    <td>
                                        <!--审核状态  -->
                                        <c:if test="${current.timetableAppointment.status == 1}">已发布</c:if>
                                        <c:if test="${current.timetableAppointment.status == 2}">一级待审核</c:if>
                                        <c:if test="${current.timetableAppointment.status == 3}">二级待审核</c:if>
                                        <c:if test="${current.timetableAppointment.status == 4}">审核拒绝</c:if>
                                        <c:if test="${current.timetableAppointment.status == 10}">未发布</c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!-- 修改排课信息弹窗 -->
                    <div id="doUpdateTimetable" class="easyui-window" title="修改排课信息" closed="true" modal="true" iconCls="icon-add" style="width:1050px;height:500px">
                    </div>
                    <script type="text/javascript">
                        $(".showClassTable").click(function() {
                            if($(".classTable").css("display") == "none") {
                                $(".classTable").css("display", "block")
                                $(".courseTable").css("display", "none")
                                $("#s2").css("display", "none")
                                $("#s3").css("display", "none")
                                var tablefix = new iStyle_TableFix({
                                    操作对象: '.content_box',
                                    隐藏原有元素: true,
                                    左侧悬浮: false,
                                    行合并模式: true, //在表格有跨行合并的单元格时启用，此模式IE下运行速度较慢
                                    左侧悬浮列数: 2,
                                    左悬浮id: 'fixedleft',
                                    位移修正: -4,
                                    顶部悬浮: true,
                                    顶部悬浮行数: 1,
                                    顶部悬浮id: 'fixedtop',
                                    顶部位置修正: -5,
                                    顶部宽度自适应: true
                                });
                            } else {
                                $(".classTable").css("display", "none")
                                $(".courseTable").css("display", "block")
                                $("#s2").css("display", "block")
                                $("#s3").css("display", "block")
                            }
                        })

                        /*
                         *调整排课弹出窗口--针对教务排课--调整排课（style=2）
                         */
                        function doUpdateTimetable2(courseCode, id) {
                            var sessionId = $("#sessionId").val();
                            var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/openAdjustTimetable?currpage=${pageModel.nextPage-1}&flag=0&courseCode=' + courseCode +
                                '&tableAppId=' + id + '" style="width:100%;height:100%;"></iframe>'
                            $('#doUpdateTimetable').html(con);
                            //获取当前屏幕的绝对位置
                            var topPos = window.pageYOffset;
                            //使得弹出框在屏幕顶端可见
                            $('#doUpdateTimetable').window({
                                left: "0px",
                                top: topPos + "px"
                            });
                            $('#doUpdateTimetable').window('open');
                        }
                        /*
                         *调整排课弹出窗口--针对二次排课不分批（style=3）
                         */
                        function doUpdateTimetable3(id) {
                            var sessionId = $("#sessionId").val();
                            var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/doAdminTimetable?id=' + id + '" style="width:100%;height:100%;"></iframe>'
                            $('#doUpdateTimetable').html(con);
                            //获取当前屏幕的绝对位置
                            var topPos = window.parent.pageYOffset;
                            //使得弹出框在屏幕顶端可见
                            $('#doUpdateTimetable').window({
                                left: "0px",
                                top: topPos + "px"
                            });
                            $('#doUpdateTimetable').window('open');
                        }
                        /*
                         *调整排课弹出窗口--针对二次排课分批（style=4）
                         */
                        function doUpdateTimetable4(courseCode, id) {
                            var sessionId = $("#sessionId").val();
                            var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/doGroupReTimetable?term=${term}&courseCode=' + courseCode +
                                '&appointment=' + id + '" style="width:100%;height:100%;"></iframe>'
                            $('#doUpdateTimetable').html(con);
                            //获取当前屏幕的绝对位置
                            var topPos = window.parent.pageYOffset;
                            //使得弹出框在屏幕顶端可见
                            $('#doUpdateTimetable').window({
                                left: "0px",
                                top: topPos + "px"
                            });
                            $('#doUpdateTimetable').window('open');
                        }
                        /*
                         *调整排课弹出窗口--针对自主排课不分批（style=5）
                         */
                        function doUpdateTimetable5(courseCode, id) {
                            var sessionId = $("#sessionId").val();
                            var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/selfTimetable/doIframeNoGroupSelfTimetable?term=${term}&weekday=0&classids=0&courseCode=' + courseCode +
                                '&labroom=0&tableAppId=' + id + '" style="width:100%;height:100%;"></iframe>'
                            $('#doUpdateTimetable').html(con);
                            //获取当前屏幕的绝对位置
                            var topPos = window.pageYOffset;
                            //使得弹出框在屏幕顶端可见
                            $('#doUpdateTimetable').window({
                                left: "0px",
                                top: topPos + "px"
                            });
                            $('#doUpdateTimetable').window('open');
                        }
                        /*
                         *调整排课弹出窗口--针对自主排课分批（style=6）
                         */
                        function doUpdateTimetable6(courseCode, id) {
                            var sessionId = $("#sessionId").val();
                            var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/selfTimetable/doGroupSelfTimetable?term=${term}&courseCode=' + courseCode +
                                '&appointment=' + id + '" style="width:100%;height:100%;"></iframe>'
                            $('#doUpdateTimetable').html(con);
                            //获取当前屏幕的绝对位置
                            var topPos = window.parent.pageYOffset;
                            //使得弹出框在屏幕顶端可见
                            $('#doUpdateTimetable').window({
                                left: "0px",
                                top: topPos + "px"
                            });
                            $('#doUpdateTimetable').window('open');
                        }
                        /*
                         *调出排课弹出窗口
                         */
                        function adjustment(courseCode, id) {
                            if(confirm("确定调出课程?")) {
                                var sessionId = $("#sessionId").val();
                                var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/adjustment?currpage=${pageModel.nextPage-1}&flag=0&courseCode=' + courseCode +
                                    '&tableAppId=' + id + '" style="width:100%;height:100%;"></iframe>'
                                $('#doUpdateTimetable').html(con);
                                //获取当前屏幕的绝对位置
                                var topPos = window.pageYOffset;
                                //使得弹出框在屏幕顶端可见
                                $('#doUpdateTimetable').window({
                                    left: "0px",
                                    top: topPos + "px"
                                });
                                $('#doUpdateTimetable').window('open');
                            } else {
                                return false;
                            }
                        }
                    </script>
                </div>
            </div>
        </div>
</body>

</html>