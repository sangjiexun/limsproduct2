<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html >
<head>
    <meta name="decorator" content="iframe"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/timetable/lmsReg.css">

    <link href="${pageContext.request.contextPath}/css/cmsSystem/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/iCheck/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/sweetalert/sweetalert.min.js"></script>

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/appointment_schedule.css" />

    <!-- 打印开始 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <!-- 打印结束 -->
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <style type="text/css">
        table{width:100%;}
        .array{width:100%;
            word-break:break-all;}
        td{border:solid 1px #add9c0;}
        br
        {mso-data-placement:same-cell;}
        .tool-box ul li{
            min-width: 180px;
        }
    </style>

    <script type="text/javascript">
        function exportTimetable(){
            document.queryForm.action = "${pageContext.request.contextPath}/timetable/exportTimetable";
            document.queryForm.submit();
        }
        function query(){
            document.queryForm.action = "${pageContext.request.contextPath}/timetable/listGeneralTimetable";
            document.queryForm.submit();
        }
    </script>
    <script>
        window.onscroll=function(){
            var t=document.documentElement.scrollTop||document.body.scrollTop;
            var timetable_left=document.getElementById("timetable_left");
            if(t>= 108){
                timetable_left.className = "timetable_leftp0";
            }else{
                timetable_left.className = "timetable_left";
            }
        }
    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li>
                <a href="javascript:void(0)">
                    我的工作平台
                </a>
            </li>
            <li class="end">
                <a href="javascript:void(0)">课表查询</a>
            </li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box title">
                        <form name="queryForm" action="${pageContext.request.contextPath}/timetable/listGeneralTimetable" method="post">
                            <ul>
                                        <li>
                                            学期:
                                            <select id="term" name="term" class="chzn-select">
                                                <c:forEach items="${schoolTerm}" var="current">
                                                    <c:if test="${current.id == term}">
                                                        <option value ="${current.id}" selected>${current.termName}</option>
                                                    </c:if>
                                                    <c:if test="${current.id != term}">
                                                        <option value ="${current.id}" >${current.termName}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </li>
                                        <li>
                                            实验室:
                                            <select id="roomId" size="1" name="roomId" class="chzn-select">
                                                <option value="-1" selected >所有<spring:message code="all.trainingRoom.labroom" /></option>
                                                <c:forEach items="${labRoomMap}" var="current" varStatus="i">
                                                    <c:if test="${current.key == roomId}">
                                                        <option value ="${current.key}" selected>${current.value} </option>
                                                    </c:if>
                                                    <c:if test="${current.key != roomId}">
                                                        <option value ="${current.key}" >${current.value} </option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </li>
                                        <li>教师:
                                            <select id="teahcer"  name="teahcer" class="chzn-select" >
                                                <option value=""	selected >所有老师</option>
                                                <c:forEach items="${teacheres}" var="current"  varStatus="i">
                                                    <c:if test="${current.username==teacherId }">
                                                        <option value ="${current.username}" selected>${current.cname} </option>
                                                    </c:if>
                                                    <c:if test="${current.username!=teacherId }">
                                                        <option value ="${current.username}" >${current.cname} </option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </li>
                                        <li>
                                            班级:
                                            <select id="schoolClass" name="schoolClass" class="chzn-select">
                                                <option value="">所有班级</option>
                                                <c:forEach items="${schoolClassess}" var="schoolClass">
                                                    <c:if test="${classNumber eq schoolClass.classNumber}">
                                                        <option value="${schoolClass.classNumber}" selected="selected">${schoolClass.className}</option>
                                                    </c:if>
                                                    <c:if test="${classNumber ne schoolClass.classNumber}">
                                                        <option value="${schoolClass.classNumber}">${schoolClass.className}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </li>
                                        <li>
                                            <input onclick="query()" value="查询" type="button">
                                            <a href="${pageContext.request.contextPath}/timetable/listGeneralTimetable"><input class="cancel-submit" type="button" value="取消"/></a>
                                            <a href="javascript:void(0)" onclick="exportTimetable()"><input type="button" value="导出"/></a>
                                            <input type="hidden" value=-1 id="courseCode" name="courseCode">
                                        </li>
                            </ul>
                            <input type="hidden" name="term" value="${term }" />
                            <%--<input type="hidden" name="roomId" value="${labroom}" />--%>
                            <input type="hidden" name="week" value="${week }" />
                            <input type="hidden" name="courseCode" value="${courseCode }" />
                            <input type="hidden" name="teacherId" value="${teacherId }" />
                        </form>
                    </div>
<div class="main_container">
    <!-- right maincontent -->
    <div class="sit_maincontent">

        <div id="myShow">
            <div id="timetable_left" class="timetable_left">
                <form name="form" action="" method="post">

                    <div class="week_box <c:if test='${week==-1 }'>week_box_select</c:if>">
                        <div class="wb_f ">全部周次</div>
                    </div>
                    <c:forEach items="${weeksMap}" var="current">
                        <c:set var="schoolweekdatestart" value="${term }-${current.key }-1"></c:set>
                        <c:set var="schoolweekdateend" value="${term }-${current.key }-7"></c:set>
                        <c:if test="${week!=current.key }">
                            <div class="week_box">
                                <div class="wb_f">第${current.key}周</div>
                                <fmt:formatDate value='${schoolweeek[schoolweekdatestart].time}' pattern="yyyyMMdd"/>-<fmt:formatDate value='${schoolweeek[schoolweekdateend].time}' pattern="yyyyMMdd"/>
                            </div>
                        </c:if>
                        <c:if test="${week==current.key }">
                            <div class="week_box week_box_select">
                                <div class="wb_f">第${current.key}周</div>
                                <fmt:formatDate value='${schoolweeek[schoolweekdatestart].time}' pattern="yyyyMMdd"/>-<fmt:formatDate value='${schoolweeek[schoolweekdateend].time}' pattern="yyyyMMdd"/>
                            </div>
                        </c:if>
                    </c:forEach>
                </form>
            </div>
            <div class="timetable_right">
            <table class="tab_border tab_timetable">
                <thead>
                <tr>
                    <th>节次</th>
                    <th id="weekdayName1">星期一<br><c:set var="schoolweekdate" value="${termId }-${week }-1"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                    <th id="weekdayName2">星期二<br><c:set var="schoolweekdate" value="${termId }-${week }-2"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                    <th id="weekdayName3">星期三<br><c:set var="schoolweekdate" value="${termId }-${week }-3"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                    <th id="weekdayName4">星期四<br><c:set var="schoolweekdate" value="${termId }-${week }-4"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                    <th id="weekdayName5">星期五<br><c:set var="schoolweekdate" value="${termId }-${week }-5"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                    <th id="weekdayName6">星期六<br><c:set var="schoolweekdate" value="${termId }-${week }-6"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                    <th id="weekdayName0">星期日<br><c:set var="schoolweekdate" value="${termId }-${week }-7"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="class" varStatus="cStatus" begin="1" end="9">
                <!-- 判断奇数偶数，以确定日历背景样式 -->
                <c:if test="${cStatus.count%2!=0}">
                <tr>
                    </c:if>
                    <c:if test="${cStatus.count%2==0}">
                <tr>
                    </c:if>

                    <c:if test="${cStatus.index==1}">
                        <th><span id="className12-13">第一&nbsp~&nbsp二节<font><fmt:formatDate value='${systemtime["1"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["2"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                    </c:if>
                    <c:if test="${cStatus.index==2}">
                        <th><span id="className12-13">第三&nbsp~&nbsp四节<font><fmt:formatDate value='${systemtime["3"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["4"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                    </c:if>
                    <c:if test="${cStatus.index==3}">
                        <th><span id="className12-13">第五&nbsp~&nbsp六节<font><fmt:formatDate value='${systemtime["5"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["6"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                    </c:if>
                    <c:if test="${cStatus.index==4}">
                        <th><span id="className12-13">第七&nbsp~&nbsp八节<font><fmt:formatDate value='${systemtime["7"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["8"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                    </c:if>
                    <c:if test="${cStatus.index==5}">
                        <th><span id="className12-13">第九节<font><fmt:formatDate value='${systemtime["9"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["9"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                    </c:if>
                    <c:if test="${cStatus.index==6}">
                        <th><span id="className12-13">第十节<font><fmt:formatDate value='${systemtime["10"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["10"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                    </c:if>
                    <c:if test="${cStatus.index==7}">
                        <th><span id="className12-13">第十一节<font><fmt:formatDate value='${systemtime["11"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["11"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                    </c:if>
                    <c:if test="${cStatus.index==8}">
                        <th><span id="className12-13">第十二节<font><fmt:formatDate value='${systemtime["12"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["12"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                    </c:if>

                    <c:forEach begin="1" end="7"  varStatus="iWeek">
                        <td class="tb" valign="top" do-labReservation='${iWeek.count},${cStatus.index}'>
                            <!-- 二次排课显示 -->
                            <c:forEach var="ltimetable" items="${labTimetable}" varStatus="lStatus">

                                <!-- 对应星期的显示 -->
                                <c:if test="${ltimetable.timetableAppointment.weekday==iWeek.count&&ltimetable.timetableAppointment.timetableAppointmentSameNumbers.size()>0&&ltimetable.timetableAppointment.enabled}">

                                    <!-- 第一到八节 -->
                                    <c:if test="${cStatus.index<=4}">

                                        <!-- 其他情况 -->
                                        <c:set var="sameStart" value="-"></c:set>
                                        <c:forEach var="entry" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p">
                                            <c:if test="${entry.startClass<=cStatus.index*2-1&&entry.endClass>=cStatus.index*2-1||entry.startClass>=cStatus.index*2&&entry.endClass<=cStatus.index*2}">
                                                <c:if test="${ltimetable.timetableAppointment.timetableStyle!=5 &&ltimetable.timetableAppointment.timetableStyle!=6 && ltimetable.timetableAppointment.timetableStyle!=7 }">
                                                    <!-- 显示周次节次 -->
                                                    <c:set var="v_param" value="-${entry.startClass}-" ></c:set>
                                                    <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                                        <!-- 分批处理 -->
                                                        <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()>0 }">
                                                            <a href='javascript:void(0)' onclick='listTimetableGroup("${ltimetable.timetableAppointment.schoolCourse.schoolTerm.id}","${ltimetable.timetableAppointment.weekday}","1","${ltimetable.timetableAppointment.schoolCourse.courseCode}","-1")'>
                                                                <c:if test="${ltimetable.timetableAppointment.schoolCourse.state!=0 }">
                                                                    <hr><font color="blue">${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}</font>
                                                                </c:if>
                                                                <c:if test="${ltimetable.timetableAppointment.schoolCourse.state==0 }">
                                                                    <hr><font color="blue">${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}</font>
                                                                </c:if>

                                                            </a><br>
                                                        </c:if>
                                                        <!-- 不分批处理 -->
                                                        <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()==0 }">
                                                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state!=0 }">
                                                                <hr><font color="blue">${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}</font>
                                                            </c:if>
                                                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state==0 }">
                                                                <hr><font color="blue">${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}</font>
                                                            </c:if>
                                                            <br>
                                                        </c:if>
                                                        实验室：${ltimetable.labRoom.labRoomAddress}&nbsp;${ltimetable.labRoom.labRoomName}<br>
                                                        <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                                                            教师：${tTimetable.user.cname}
                                                        </c:forEach> <br>
                                                        <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTutorRelateds}" varStatus="tStatus">
                                                            <c:if test="${tStatus.count == 1}">指导教师：</c:if><c:if test="${tStatus.count != 1}">,</c:if>${tTimetable.user.cname}
                                                        </c:forEach> <br>
                                                        班级：<c:forEach items="${ltimetable.timetableAppointment.schoolCourse.schoolClasseses}" var="s">
                                                                   ${s.className }&nbsp;&nbsp;
                                                              </c:forEach><br>
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
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                        <!-- 其他情况 -->

                                        <!-- 自主排课 -->
                                        <c:set var="sameStart" value="-"></c:set>
                                        <c:forEach var="entry" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p">
                                            <c:if test="${entry.startClass<=cStatus.index*2-1&&entry.endClass>=cStatus.index*2-1||entry.startClass>=cStatus.index*2&&entry.endClass<=cStatus.index*2}">

                                                <c:if test="${ltimetable.timetableAppointment.timetableStyle==5 ||ltimetable.timetableAppointment.timetableStyle==6 }">
                                                    <!-- 显示周次节次 -->
                                                    <c:set var="v_param" value="-${entry.startClass}-" ></c:set>
                                                    <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                                        <!-- 分批处理 -->
                                                        <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()>0 }">
                                                            <input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="[${ltimetable.timetableAppointment.timetableSelfCourse.courseCode}]${ltimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}">
                                                            <br>
                                                        </c:if>
                                                        <!-- 不分批处理 -->
                                                        <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()==0 }">
                                                            <input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="[${ltimetable.timetableAppointment.timetableSelfCourse.courseCode}]${ltimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}">
                                                            <br>
                                                        </c:if>
                                                        实验室：${ltimetable.labRoom.labRoomAddress}&nbsp;${ltimetable.labRoom.labRoomName}<br>
                                                        <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                                                            ${tTimetable.user.cname}
                                                        </c:forEach><br>
                                                        <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTutorRelateds}" varStatus="tStatus">
                                                            <c:if test="${tStatus.count == 1}">指导教师：</c:if><c:if test="${tStatus.count != 1}">,</c:if>${tTimetable.user.cname}
                                                        </c:forEach> <br>
                                                        <%--班级：<c:forEach items="${ltimetable.timetableAppointment.schoolCourse.schoolClasseses}" var="s">
                                                        ${s.className }&nbsp;&nbsp;自主排课没有固定班级
                                                         </c:forEach><br>--%>
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
                                                        <br>
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                        <!-- 自主排课 -->

                                        <!-- 实验室预约 -->
                                        <c:set var="sameStart" value="-"></c:set>
                                        <c:forEach var="entry" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p">
                                            <c:if test="${entry.startClass<=cStatus.index*2-1&&entry.endClass>=cStatus.index*2-1||entry.startClass>=cStatus.index*2&&entry.endClass<=cStatus.index*2}">
                                                <c:if test="${ltimetable.timetableAppointment.timetableStyle==7 }">
                                                    <!-- 显示周次节次 -->
                                                    <c:set var="v_param" value="-${entry.startClass}-" ></c:set>
                                                    <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                                        <hr><font color="green">实验室预约</font><br>
                                                        实验室：${ltimetable.labRoom.labRoomAddress}&nbsp;${ltimetable.labRoom.labRoomName}<br>
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
                                                        <br>
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                        <!-- 实验室预约 -->

                                    </c:if>
                                    <!-- 第一到八节 -->

                                    <!-- 第八节之后 -->
                                    <c:if test="${cStatus.index>4}">

                                        <!-- 其他情况 -->
                                        <c:set var="sameStart" value="-"></c:set>
                                        <c:forEach var="entry" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p">
                                            <c:if test="${entry.startClass<=cStatus.index+4&&entry.endClass>=cStatus.index+4}">
                                                <c:if test="${ltimetable.timetableAppointment.timetableStyle!=5&&ltimetable.timetableAppointment.timetableStyle!=6 && ltimetable.timetableAppointment.timetableStyle!=7 }">
                                                    <!-- 显示周次节次 -->
                                                    <c:set var="v_param" value="-${entry.startClass}-" ></c:set>
                                                    <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                                        <!-- 分批处理 -->
                                                        <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()>0 }">
                                                            <a href='javascript:void(0)' onclick='listTimetableGroup("${ltimetable.timetableAppointment.schoolCourse.schoolTerm.id}","${ltimetable.timetableAppointment.weekday}","1","${ltimetable.timetableAppointment.schoolCourse.courseCode}","-1")'>
                                                                <c:if test="${ltimetable.timetableAppointment.schoolCourse.state!=0 }">
                                                                    <hr><font color="blue">${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}</font>
                                                                </c:if>
                                                                <c:if test="${ltimetable.timetableAppointment.schoolCourse.state==0 }">
                                                                    <hr><font color="blue">${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}</font>
                                                                </c:if>

                                                            </a><br>
                                                        </c:if>
                                                        <!-- 不分批处理 -->
                                                        <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()==0 }">
                                                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state!=0 }">
                                                                <hr><font color="blue">${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}</font>
                                                            </c:if>
                                                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state==0 }">
                                                                <hr><font color="blue">${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}</font>
                                                            </c:if>
                                                            <br>
                                                        </c:if>
                                                        实验室：${ltimetable.labRoom.labRoomAddress}&nbsp;${ltimetable.labRoom.labRoomName}<br>
                                                        <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                                                            教师：${tTimetable.user.cname}
                                                        </c:forEach> <br>
                                                        <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTutorRelateds}" varStatus="tStatus">
                                                            <c:if test="${tStatus.count == 1}">指导教师：</c:if><c:if test="${tStatus.count != 1}">,</c:if>${tTimetable.user.cname}
                                                        </c:forEach> <br>
                                                        班级：<c:forEach items="${ltimetable.timetableAppointment.schoolCourse.schoolClasseses}" var="s">
                                                        ${s.className }&nbsp;&nbsp;
                                                          </c:forEach><br>
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
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                        <!-- 其他情况 -->

                                        <!-- 自主排课 -->
                                        <c:set var="sameStart" value="-"></c:set>
                                        <c:forEach var="entry" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p">
                                            <c:if test="${entry.startClass<=cStatus.index+4&&entry.endClass>=cStatus.index+4}">
                                                <c:if test="${ltimetable.timetableAppointment.timetableStyle==5 ||ltimetable.timetableAppointment.timetableStyle==6 }">
                                                    <!-- 显示周次节次 -->
                                                    <c:set var="v_param" value="-${entry.startClass}-" ></c:set>
                                                    <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                                        <!-- 分批处理 -->
                                                        <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()>0 }">
                                                            <input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="[${ltimetable.timetableAppointment.timetableSelfCourse.courseCode}]${ltimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}">
                                                            <br>
                                                        </c:if>
                                                        <!-- 不分批处理 -->
                                                        <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()==0 }">
                                                            <input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="[${ltimetable.timetableAppointment.timetableSelfCourse.courseCode}]${ltimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}">
                                                            <br>
                                                        </c:if>
                                                        实验室：${ltimetable.labRoom.labRoomAddress}&nbsp;${ltimetable.labRoom.labRoomName}<br>
                                                        <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                                                            ${tTimetable.user.cname}
                                                        </c:forEach><br>
                                                        <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTutorRelateds}" varStatus="tStatus">
                                                            <c:if test="${tStatus.count == 1}">指导教师：</c:if><c:if test="${tStatus.count != 1}">,</c:if>${tTimetable.user.cname}
                                                        </c:forEach> <br>
                                                        <%--班级：<c:forEach items="${ltimetable.timetableAppointment.schoolCourse.schoolClasseses}" var="s">
                                                                ${s.className }&nbsp;&nbsp;自主排课没有固定班级
                                                              </c:forEach><br>--%>
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
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                        <!-- 自主排课 -->

                                        <!-- 实验室预约 -->
                                        <c:set var="sameStart" value="-"></c:set>
                                        <c:forEach var="entry" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p">
                                            <c:if test="${entry.startClass<=cStatus.index+4&&entry.endClass>=cStatus.index+4}">
                                                <c:if test="${ltimetable.timetableAppointment.timetableStyle==7}">
                                                    <!-- 显示周次节次 -->
                                                    <c:set var="v_param" value="-${entry.startClass}-" ></c:set>
                                                    <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                                        <hr><font color="green">实验室预约</font><br>
                                                        实验室：${ltimetable.labRoom.labRoomAddress}&nbsp;${ltimetable.labRoom.labRoomName}<br>
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
                                        <!-- 实验室预约 -->

                                    </c:if>
                                    <!-- 第八节之后 -->

                                </c:if>
                                <!-- 对应星期的显示 -->

                            </c:forEach>
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
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        $('#fullview').click(function(){
            $('.toggle,.toggleLink,#fullview,.sit_footer,.sit_sider_bar > h3').css("display","none");
            $('#fullview1').css("display","inline");
        });

        $('#fullview1').click(function(){
            $('#fullview1').css("display","none");
            $('.toggle,#fullview,.toggleLink,.sit_footer,.sit_sider_bar > h3 ').css("display","inline");
        });

        $('#myPrint').click(function(){
            $('#myShow').jqprint();
        });
    });

    $(function(){
        $("#showTimetable").window({
            top: ($(window).width() - 800) * 0.5 ,
            left: ($(window).width() - 1000) * 0.5
        })
    })

    // 周次点击
    $('.wb_f').click(function(){
        var weekhtml = $(this).html();
        var week;
        if(weekhtml=="全部周次"){
            week=-1;
        }
        else{
            week = weekhtml.replace(/[\u4E00-\u9FA5]/g,'');
        }

        subform('${pageContext.request.contextPath}/timetable/listGeneralTimetable?roomId=${roomId}&week='+week);
    });

    //如果为查询则提交查询页面，如果为电子表格导出，则导出excel
    function subform(gourl){
        queryForm.action=gourl;
        queryForm.submit();
    }
    //导出excel
    function exportExcel()
    {
        document.form.action = "${pageContext.request.contextPath}/report/teachingReport/exportWeekRegister";
        document.form.submit();
    }

    $(".week_box").click(
        function() {
            $(this).addClass("week_box_select").siblings().removeClass("week_box_select");
        }
    );
</script>
<%--综合课表悬停效果--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/timetableEffect.js"></script>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select': {search_contains : true},
        '.chzn-select-deselect'  : {allow_single_deselect:true},
        '.chzn-select-no-single' : {disable_search_threshold:10},
        '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
        '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>