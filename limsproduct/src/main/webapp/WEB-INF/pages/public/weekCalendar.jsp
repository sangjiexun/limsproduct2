<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html >
<head>
<meta name="decorator" content="iframe"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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

<style type="text/css">
	table{width:100%;}
	.array{width:100%;
		word-break:break-all;}
		td{border:solid 1px #add9c0;}
 br
 {mso-data-placement:same-cell;}
</style>
</head>

<body>
<div class="main_container cf rel w95p ma mb60">
<!--userPermission -->
<div class="sit_module width_quarter" style="position: absolute;height: 500px;margin-top: 0px;display: none">
    <div class="sit_title">
        <h3 class="tabs_involved">权限列表</h3>  
    </div>
    <div class="message_list" style="height: 450px">
        <div style="border: 0px;margin:0px auto;width:98%;height:90%;">
        </div>
    </div>
</div>
<!-- right maincontent -->
<div class="sit_maincontent" style="width: 99%; height: 800px;">
<%--<form action="${pageContext.request.contextPath}/classBrand/weekCalendar?roomId=${roomId}&flag=2" method="post">
<table border="0" cellpadding="5" cellspacing="0" bgcolor="#F3EFEE" height="30" width="100%">
<tbody>
<tr>
    <td>
    <select id="term" name="term">
       <c:forEach items="${schoolTerm}" var="current">
    	    <c:if test="${current.id == term}">
    	       <option value ="${current.id}" selected>${current.termName} </option>
    	    </c:if>
    	    <c:if test="${current.id != term}">
    	       <option value ="${current.id}" >${current.termName}</option>
    	    </c:if>		
        </c:forEach>
    </select>
    <select id="roomId" size="1" name="labId"  >
        <option value="-1"	selected >所有实训室</option>
    	<c:forEach items="${labRoomMap}" var="current"  varStatus="i">
    	    <c:if test="${current.id == roomId}">
    	       <option value ="${current.id}" selected>${current.labRoomName}${current.labRoomNumber}</option>
    	    </c:if>
    	    <c:if test="${current.id != roomId}">
    	       <option value ="${current.id}" >${current.labRoomName}${current.labRoomNumber}</option>
    	    </c:if>		
        </c:forEach>
    </select>
    <input value="查询" type="submit">&nbsp;&nbsp;&nbsp;
    <a href="${pageContext.request.contextPath}/classBrand/weekCalendar?roomId=${roomId}&flag=2"><input type="button" value="取消查询"></a>
    <input type="hidden" value=-1 id="courseCode" name="courseCode">

    </td>    
</tr>
</tbody>
</table>
<input type="hidden" name="term" value="${term }" >
<input type="hidden" name="roomId" value="${labroom}" >
<input type="hidden" name="week" value="${week }" > 
<input type="hidden" name="courseCode" value="${courseCode }" >
<input type="hidden" name="teacherId" value="${teacherId }" >

</form>--%>
<div class="edit-content">
		
       
					</div>
<div id="myShow">
<table class="dm_right0" cellspacing="1">
<tbody>
<tr>
    <th class="tbh" width="10%">节次</th>
    <th id="weekdayName1" class="tbh" width="11%">星期一<br><c:set var="schoolweekdate" value="${termId }-${week }-1"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
    <th id="weekdayName2" class="tbh" width="11%">星期二<br><c:set var="schoolweekdate" value="${termId }-${week }-2"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
    <th id="weekdayName3" class="tbh" width="11%">星期三<br><c:set var="schoolweekdate" value="${termId }-${week }-3"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
    <th id="weekdayName4" class="tbh" width="11%">星期四<br><c:set var="schoolweekdate" value="${termId }-${week }-4"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
    <th id="weekdayName5" class="tbh" width="11%">星期五<br><c:set var="schoolweekdate" value="${termId }-${week }-5"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
    <th id="weekdayName6" class="tbh" width="11%">星期六<br><c:set var="schoolweekdate" value="${termId }-${week }-6"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
    <th id="weekdayName0" class="tbh" width="11%">星期日<br><c:set var="schoolweekdate" value="${termId }-${week }-7"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
</tr>
<c:forEach var="class" varStatus="cStatus" begin="1" end="5">
    <!-- 判断奇数偶数，以确定日历背景样式 -->
    <c:if test="${cStatus.count%2!=0}">
    <tr>
    </c:if>
    <c:if test="${cStatus.count%2==0}">
    <tr style="background:#FEFFDA;" >
    </c:if>
    
        <c:if test="${cStatus.index==1}">
           <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第一节&nbsp~&nbsp第二节<br><fmt:formatDate value='${systemtime["1"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["2"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        <c:if test="${cStatus.index==2}">
           <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第三节&nbsp~&nbsp第四节<br><fmt:formatDate value='${systemtime["3"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["4"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        <c:if test="${cStatus.index==3}">
          <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第五节&nbsp~&nbsp第六节<br><fmt:formatDate value='${systemtime["5"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["6"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        <c:if test="${cStatus.index==4}">
          <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第七节&nbsp~&nbsp第八节<br><fmt:formatDate value='${systemtime["7"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["8"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        <c:if test="${cStatus.index==5}">
           <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第九节&nbsp~&nbsp第十节<br><fmt:formatDate value='${systemtime["9"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["10"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        <%--<c:if test="${cStatus.index==6}">
           <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第十节<br><fmt:formatDate value='${systemtime["10"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["10"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        <c:if test="${cStatus.index==6}">
           <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第十一节<br><fmt:formatDate value='${systemtime["11"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["11"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        <c:if test="${cStatus.index==7}">
           <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第十二节<br><fmt:formatDate value='${systemtime["12"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["12"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        <c:if test="${cStatus.index==8}">
           <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第十三节<br><fmt:formatDate value='${systemtime["13"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["13"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>--%>
        
        <c:forEach begin="1" end="7"  varStatus="iWeek">
        <td class="tb" valign="top" do-labReservation='${iWeek.count},${cStatus.index}'>
               <!-- 二次排课显示 -->
               <c:forEach var="ltimetable" items="${labTimetable}" varStatus="lStatus">
                    <c:if test="${ltimetable.timetableAppointment.weekday==iWeek.count&&ltimetable.timetableAppointment.timetableAppointmentSameNumbers.size()==0&&ltimetable.timetableAppointment.enabled}">
                    <c:if test="${cStatus.index<=5}">
                        <c:if test="${ltimetable.timetableAppointment.startClass<=cStatus.index*2-1&&ltimetable.timetableAppointment.endClass>=cStatus.index*2-1||ltimetable.timetableAppointment.startClass>=cStatus.index*2&&ltimetable.timetableAppointment.endClass<=cStatus.index*2}">
                            <c:if test="${ltimetable.timetableAppointment.timetableStyle!=5&&ltimetable.timetableAppointment.timetableStyle!=6 }">
                            <!-- 不分批处理 -->
                            <font color=green><b>
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}</b>
                            <br>
                            </c:if>
                                                                                      ${ltimetable.labRoom.labRoomName}<br>
                             <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTeacherRelateds}">
                                                                               教师：${tTimetable.user.cname}
                               </c:forEach>
                           <br>
                             <!-- 显示周次节次 -->
                            <c:if test="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers.size()==0}">
                                <c:if test="${ltimetable.timetableAppointment.startClass==ltimetable.timetableAppointment.endClass}">
                                                                                                     节次：${ltimetable.timetableAppointment.startClass }
                                </c:if>
                                <c:if test="${ltimetable.timetableAppointment.startClass!=ltimetable.timetableAppointment.endClass}">
                                                                                                     节次：${ltimetable.timetableAppointment.startClass }-${ltimetable.timetableAppointment.endClass }
                                </c:if>
                                <c:if test="${ltimetable.timetableAppointment.startWeek==ltimetable.timetableAppointment.endWeek}">
                                 &nbsp;周次：${ltimetable.timetableAppointment.startWeek }
                                </c:if>
                                <c:if test="${ltimetable.timetableAppointment.startWeek!=ltimetable.timetableAppointment.endWeek}">
                                &nbsp;周次：${ltimetable.timetableAppointment.startWeek }-${ltimetable.timetableAppointment.endWeek }
                                </c:if>
                                <br>
                             </c:if><br>
                        </c:if>
                    </c:if>
                    <c:if test="${cStatus.index>5}">
                        <c:if test="${ltimetable.timetableAppointment.startClass<=cStatus.index+5&&ltimetable.timetableAppointment.endClass>=cStatus.index+5}">
                            <c:if test="${ltimetable.timetableAppointment.timetableStyle!=5 &&ltimetable.timetableAppointment.timetableStyle!=6 }">
                            <!-- 分批处理 -->
                            <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()>0 }">
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state!=0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state==0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <br>
                            </c:if>
                            
                            <!-- 不分批处理 -->
                            <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()==0 }">
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state!=0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state==0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <br>
                            </c:if>
                            </c:if>

                                                                                       ${ltimetable.labRoom.labRoomName};<br>
                             <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                            	教师:${tTimetable.user.cname}
                            </c:forEach><br>
                             <!-- 显示周次节次 -->
                            <c:if test="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers.size()==0}">
                                <c:if test="${ltimetable.timetableAppointment.startClass==ltimetable.timetableAppointment.endClass}">
                                                         节次：${ltimetable.timetableAppointment.startClass }
                                </c:if>
                                <c:if test="${ltimetable.timetableAppointment.startClass!=ltimetable.timetableAppointment.endClass}">
                                                         节次：${ltimetable.timetableAppointment.startClass }-${ltimetable.timetableAppointment.endClass }
                                </c:if>
                                <c:if test="${ltimetable.timetableAppointment.startWeek==ltimetable.timetableAppointment.endWeek}">
                                 &nbsp;周次：${ltimetable.timetableAppointment.startWeek }
                                </c:if>
                                <c:if test="${ltimetable.timetableAppointment.startWeek!=ltimetable.timetableAppointment.endWeek}">
                                &nbsp;周次：${ltimetable.timetableAppointment.startWeek }-${ltimetable.timetableAppointment.endWeek }
                                </c:if>
                                <br>
                             </c:if>
                            <br>
                        </c:if>
                    </c:if>
                    </c:if>
                 
                    <c:if test="${ltimetable.timetableAppointment.weekday==iWeek.count&&ltimetable.timetableAppointment.timetableAppointmentSameNumbers.size()>0&&ltimetable.timetableAppointment.enabled}">
                    <c:if test="${cStatus.index<=4}">
                            <c:set var="sameStart" value="-"></c:set>
                            <c:forEach var="entry" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p"> 
                            <c:if test="${entry.startClass<=cStatus.index*2-1&&entry.endClass>=cStatus.index*2-1||entry.startClass>=cStatus.index*2&&entry.endClass<=cStatus.index*2}">
                            <c:if test="${ltimetable.timetableAppointment.timetableStyle!=5 &&ltimetable.timetableAppointment.timetableStyle!=6 }">
                                <c:if test="${ltimetable.timetableAppointment.timetableStyle!=7}">
                            <!-- 显示周次节次 -->
                            <c:set var="v_param" value="-${entry.startClass}-" ></c:set>   
                            <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                            <!-- 分批处理 -->
                            <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()>0 }">
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state!=0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state==0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <br>
                            </c:if>
                             <!-- 不分批处理 -->
                            <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()==0 }">
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state!=0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state==0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <br>
                            </c:if>
                                                                                                ${ltimetable.labRoom.labRoomName}<br>
                               <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                               		教师：${tTimetable.user.cname}
                                </c:forEach> <br>                                                               
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

                                <!-- 实验室预约 -->
                                <c:if test="${ltimetable.timetableAppointment.timetableStyle==7}">
                                <!-- 显示周次节次 -->
                                    <c:set var="v_param" value="-${entry.startClass}-" ></c:set>
                                <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                <!-- 分批处理 -->
                                <span style="color: red">实验室预约</span>
                                <br>
                                <span style="color: red">地点：${ltimetable.labRoom.labRoomName}</span><br>
                                <span style="color: red">
                                <c:if test="${entry.startClass==entry.endClass}">
                                节次：${entry.startClass }
                                </c:if>
                                <c:if test="${entry.startClass!=entry.endClass}">
                                节次： ${entry.startClass }-${entry.endClass }
                                </c:if>
                                </span>
                                <br>
                                    <c:set var="sameStart" value="${sameStart}-${entry.startClass }-"></c:set>
                                <span style="color: red">
                                周次：<c:forEach var="entry1" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"  varStatus="q">
                                <c:if test="${entry.startClass==entry1.startClass}">
                                <c:if test="${entry1.startWeek==entry1.endWeek}">
                                    ${entry1.startWeek }
                                </c:if>
                                <c:if test="${entry1.startWeek!=entry1.endWeek}">
                                    ${entry1.startWeek }-${entry1.endWeek }
                                </c:if>
                                </c:if>
                                </c:forEach>
                                </span><br>
                                <span style="color: red">申请人：
                                <c:forEach items="${ltimetable.timetableAppointment.timetableTeacherRelateds}" var="timetableTeacherRelated">
                                    ${timetableTeacherRelated.user.cname}
                                </c:forEach>
                                </span><br>
                                <span style="color: red">人数：${ltimetable.timetableAppointment.groupCount}</span><br>
                                </c:if>
                                </c:if>

                            </c:if>
                            </c:if>
                            </c:forEach>
                            </c:if>
                    <c:if test="${cStatus.index>4}">
                            <c:set var="sameStart" value="-"></c:set>
                            <c:forEach var="entry" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p"> 
                            <c:if test="${entry.startClass<=cStatus.index+4&&entry.endClass>=cStatus.index+4}">
                            <c:if test="${ltimetable.timetableAppointment.timetableStyle!=5&&ltimetable.timetableAppointment.timetableStyle!=6 }">
                                <c:if test="${ltimetable.timetableAppointment.timetableStyle!=7 }">
                            <!-- 显示周次节次 -->
                            <c:set var="v_param" value="-${entry.startClass}-" ></c:set>   
                            <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                            <!-- 分批处理 -->
                            <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()>0 }">
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state!=0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state==0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <br>
                            </c:if>
                             <!-- 不分批处理 -->
                            <c:if test="${ltimetable.timetableAppointment.timetableGroups.size()==0 }">
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state!=0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state==0 }">
                            ${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}
                            </c:if>
                            <br>
                            </c:if>
                                                                                                ${ltimetable.labRoom.labRoomName}<br>
                               <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                               		教师：${tTimetable.user.cname}
                                </c:forEach> <br>                                                                
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

                                <c:if test="${ltimetable.timetableAppointment.timetableStyle==7 }">
                                <!-- 显示周次节次 -->
                                    <c:set var="v_param" value="-${entry.startClass}-" ></c:set>
                                <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                                <!-- 分批处理 -->
                                <span style="color: red">实验室预约</span>
                                <br>
                                <span style="color: red">地点：${ltimetable.labRoom.labRoomName}</span><br>
                                <span style="color: red">
                                <c:if test="${entry.startClass==entry.endClass}">
                                节次：${entry.startClass }
                                </c:if>
                                <c:if test="${entry.startClass!=entry.endClass}">
                                节次： ${entry.startClass }-${entry.endClass }
                                </c:if>
                                </span>
                                <br>
                                    <c:set var="sameStart" value="${sameStart}-${entry.startClass }-"></c:set>
                                <span style="color: red">
                                周次：<c:forEach var="entry1" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"  varStatus="q">
                                <c:if test="${entry.startClass==entry1.startClass}">
                                <c:if test="${entry1.startWeek==entry1.endWeek}">
                                    ${entry1.startWeek }
                                </c:if>
                                <c:if test="${entry1.startWeek!=entry1.endWeek}">
                                    ${entry1.startWeek }-${entry1.endWeek }
                                </c:if>
                                </c:if>
                                </c:forEach>
                                </span><br>
                                <span style="color: red">申请人：
                                <c:forEach items="${ltimetable.timetableAppointment.timetableTeacherRelateds}" var="timetableTeacherRelated">
                                    ${timetableTeacherRelated.user.cname}
                                </c:forEach>
                                </span><br>
                                <span style="color: red">人数：${ltimetable.timetableAppointment.groupCount}</span><br>
                                </c:if>
                                </c:if>

                                </c:if>
                            </c:if>
                            </c:forEach>
                    </c:if>
                    </c:if>
               </c:forEach> 
        
               <!-- 自主排课显示 -->
                <c:forEach var="labSelfTimetable" items="${labSelfTimetable}" varStatus="lStatus">
                
                    <c:if test="${labSelfTimetable.timetableAppointment.weekday==iWeek.count&&labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers.size()==0&&labSelfTimetable.timetableAppointment.enabled}">
                    <c:if test="${cStatus.index<=5&&labSelfTimetable.timetableAppointment.weekday==iWeek.count}">
                        <c:if test="${labSelfTimetable.timetableAppointment.startClass<=cStatus.index*2-1&&labSelfTimetable.timetableAppointment.endClass>=cStatus.index*2-1||labSelfTimetable.timetableAppointment.startClass>=cStatus.index*2&&labSelfTimetable.timetableAppointment.endClass<=cStatus.index*2}">
                        	<c:if test="${labSelfTimetable.timetableAppointment.timetableStyle!=7 }">
                             	<!-- 自主排课 -->
                             	<c:if test="${labSelfTimetable.timetableAppointment.timetableStyle==5 ||labSelfTimetable.timetableAppointment.timetableStyle==6 }">
	                             	<!-- 不分批处理 -->
	                             	<c:if test="${labSelfTimetable.timetableAppointment.timetableGroups.size()==0 }">
	                             		<input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="${labSelfTimetable.timetableAppointment.timetableSelfCourse.courseCode}-${labSelfTimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}">
	                             		<br>
                             		</c:if>
                             	</c:if>
                            	<font color=blue><b> ${labSelfTimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}</b><br>
                                                                                    	${labSelfTimetable.labRoom.labRoomName}<br>
                               		<c:forEach var="tTimetable" items="${labSelfTimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                                		教师：${tTimetable.user.cname}
                                 	</c:forEach> <br>
                              		<!-- 显示周次节次 -->
                             		<c:if test="${labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers.size()==0}">
                                     	<c:if test="${labSelfTimetable.timetableAppointment.startClass==labSelfTimetable.timetableAppointment.endClass}">
                                                                                                         	 节次：${labSelfTimetable.timetableAppointment.startClass }
                                     	</c:if>
                                     	<c:if test="${labSelfTimetable.timetableAppointment.startClass!=labSelfTimetable.timetableAppointment.endClass}">
                                                                                                         	 节次：${labSelfTimetable.timetableAppointment.startClass }-${labSelfTimetable.timetableAppointment.endClass }
                                     	</c:if>
                                     	<c:if test="${labSelfTimetable.timetableAppointment.startWeek==labSelfTimetable.timetableAppointment.endWeek}">
                                      		&nbsp;周次：${labSelfTimetable.timetableAppointment.startWeek }
                                     	</c:if>
                                     	<c:if test="${labSelfTimetable.timetableAppointment.startWeek!=labSelfTimetable.timetableAppointment.endWeek}">
                                     		&nbsp;周次：${labSelfTimetable.timetableAppointment.startWeek }-${labSelfTimetable.timetableAppointment.endWeek }
                                     	</c:if>
                                     	<br>
                                  	</c:if>
                                </font>
                              	<!--分批排课状况--><br>
                     		</c:if>
		                    <c:if test="${labSelfTimetable.timetableAppointment.timetableStyle==7 }">
		                    	<c:forEach var="labRe" items="${labSelfTimetable.timetableAppointment.labReservation}">                    	
		                           	<font color=red><b>${labRe.eventName}</b><br> 
		                                                                                    ${labRe.labRoom.labRoomName}<br>
		                                                                                   指导老师：${labRe.teacher.cname}<br>
		                             	<!-- 显示周次节次 -->
		                            	<c:if test="${labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers.size()==0}">
		                                	<c:if test="${labSelfTimetable.timetableAppointment.startClass==labSelfTimetable.timetableAppointment.endClass}">
		                                                                                                     节次：${labSelfTimetable.timetableAppointment.startClass }
		                                	</c:if>
		                                	<c:if test="${labSelfTimetable.timetableAppointment.startClass!=labSelfTimetable.timetableAppointment.endClass}">
		                                                                                                     节次：${labSelfTimetable.timetableAppointment.startClass }-${labSelfTimetable.timetableAppointment.endClass }
		                                	</c:if>
		                                	<c:if test="${labSelfTimetable.timetableAppointment.startWeek==labSelfTimetable.timetableAppointment.endWeek}">
		                                 		&nbsp;周次：${labSelfTimetable.timetableAppointment.startWeek }
		                                	</c:if>
		                                	<c:if test="${labSelfTimetable.timetableAppointment.startWeek!=labSelfTimetable.timetableAppointment.endWeek}">
		                                		&nbsp;周次：${labSelfTimetable.timetableAppointment.startWeek }-${labSelfTimetable.timetableAppointment.endWeek }
		                                	</c:if><br>
		                             	</c:if>
		                            </font>
		                        </c:forEach>
		                    </c:if>
                        </c:if>
                    </c:if>
                    </c:if>
                    
                    <c:if test="${cStatus.index>5&&labSelfTimetable.timetableAppointment.weekday==iWeek.count}">
                        <c:if test="${labSelfTimetable.timetableAppointment.startClass<=cStatus.index+5&&labSelfTimetable.timetableAppointment.endClass>=cStatus.index+5}">
                             <c:if test="${labSelfTimetable.timetableAppointment.timetableStyle!=7 }">
                                 <c:if test="${labSelfTimetable.timetableAppointment.timetableStyle==5 ||labSelfTimetable.timetableAppointment.timetableStyle==6 }">
                                     <!-- 分批处理 -->
                                     <c:if test="${labSelfTimetable.timetableAppointment.timetableGroups.size()>0 }">
                                         <br>
                                     </c:if>
                                     <!-- 不分批处理 -->
                                     <c:if test="${labSelfTimetable.timetableAppointment.timetableGroups.size()==0 }">
                                         <br>
                                     </c:if>
                                     <font color=blue><b>
                                             ${labSelfTimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}</b><br>
                                         <c:forEach var="tTimetable" items="${labSelfTimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                                             教师：${tTimetable.user.cname}
                                         </c:forEach>  <br>
                                         <!-- 显示周次节次 -->
                                         <c:if test="${labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers.size()==0}">
                                             <c:if test="${labSelfTimetable.timetableAppointment.startClass==labSelfTimetable.timetableAppointment.endClass}">
                                                 节次：${labSelfTimetable.timetableAppointment.startClass }
                                             </c:if>
                                             <c:if test="${labSelfTimetable.timetableAppointment.startClass!=labSelfTimetable.timetableAppointment.endClass}">
                                                 节次：${labSelfTimetable.timetableAppointment.startClass }-${labSelfTimetable.timetableAppointment.endClass }
                                             </c:if>
                                             <c:if test="${labSelfTimetable.timetableAppointment.startWeek==labSelfTimetable.timetableAppointment.endWeek}">
                                                 &nbsp;周次：${labSelfTimetable.timetableAppointment.startWeek }
                                             </c:if>
                                             <c:if test="${labSelfTimetable.timetableAppointment.startWeek!=labSelfTimetable.timetableAppointment.endWeek}">
                                                 &nbsp;周次：${labSelfTimetable.timetableAppointment.startWeek }-${labSelfTimetable.timetableAppointment.endWeek }
                                             </c:if>
                                             <br>
                                         </c:if>
                                     </font>
                                     <br>
                                 </c:if>
                             </c:if>
                             <c:if test="${labSelfTimetable.timetableAppointment.timetableStyle==7 }">
                   				 <c:forEach var="labRe" items="${labSelfTimetable.timetableAppointment.labReservation}">
                            <font color=red><b>${labRe.eventName}</b><br>
                                                                                   ${labRe.labRoom.labRoomName}<br>
                                                                                   指导老师：${labRe.teacher.cname}<br>
                             <!-- 显示周次节次 -->
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers.size()==0}">
                                <c:if test="${labSelfTimetable.timetableAppointment.startClass==labSelfTimetable.timetableAppointment.endClass}">
                                                                                                     节次：${labSelfTimetable.timetableAppointment.startClass }
                                </c:if>
                                <c:if test="${labSelfTimetable.timetableAppointment.startClass!=labSelfTimetable.timetableAppointment.endClass}">
                                                                                                     节次：${labSelfTimetable.timetableAppointment.startClass }-${labSelfTimetable.timetableAppointment.endClass }
                                </c:if>
                                <c:if test="${labSelfTimetable.timetableAppointment.startWeek==labSelfTimetable.timetableAppointment.endWeek}">
                                 &nbsp;周次：${labSelfTimetable.timetableAppointment.startWeek }
                                </c:if>
                                <c:if test="${labSelfTimetable.timetableAppointment.startWeek!=labSelfTimetable.timetableAppointment.endWeek}">
                                &nbsp;周次：${labSelfTimetable.timetableAppointment.startWeek }-${labSelfTimetable.timetableAppointment.endWeek }
                                </c:if>
                                <br>
                             </c:if>
                             </font>
                             </c:forEach>
                        </c:if>
                        </c:if>
                    </c:if>
                 	
                 	<c:if test="${labSelfTimetable.timetableAppointment.weekday==iWeek.count&&labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers.size()>0&&labSelfTimetable.timetableAppointment.enabled}">
                    	<c:if test="${cStatus.index<=5}">
                            <c:set var="sameStart" value="-"></c:set>
                        </c:if>
                    	<c:if test="${cStatus.index>5}">
                            <c:set var="sameStart" value="-"></c:set>
                    	</c:if>
                    </c:if>
               </c:forEach> 
        </td>
        </c:forEach>
    </tr> 
</c:forEach>
</table>
</div>
</div>
</div>
<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">红色：实验室预约</font>&nbsp;&nbsp;&nbsp;<font color="blue">蓝色：自主排课 </font>&nbsp;&nbsp;&nbsp;<font color="green">绿色：教务排课</font> </b>
<script type="text/javascript">
$(function(){
      var height = $(document).height();
      $(".sit_sider_bar").css('height',height);
      $(".sit_maincontent").css('height',height);
}) ;

$(function(){
    $("#showTimetable").window({
        top: ($(window).width() - 800) * 0.5 ,
        left: ($(window).width() - 1000) * 0.5   
    })
    $(".sit_maincontent").css('height',600);
})
	
	// 定义全局变量来传递参数
	var labId = ${roomId};
	var termId = ${termId};
	var weekId = ${week};
	var cid = ${cid};
	
	
	$(".week_box").click(
		function() {
			$(this).addClass("week_box_select").siblings().removeClass("week_box_select");
		}
	);
</script>
</body>
</html>