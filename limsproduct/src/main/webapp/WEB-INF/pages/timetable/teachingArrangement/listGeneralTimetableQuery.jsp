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
 
 .topselect{
 		float:left;
 		width:150px;
 		margin:10px auto;
 }
 .topselect  input{
    /* width: 70px; */
    padding: 0 8px;
    height: 22px;
    line-height:22px;
    background: #77bace;
    color: #333;
    border-radius: 7px;
    border: 0;
    margin-left:10px;
 }
</style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange"/></a></li>
            <li><a href="javascript:void(0)"><spring:message code="left.timetable.teachingArrangement"/></a></li>
            <li class="end"><a href="javascript:void(0)">课表查询</a></li>
        </ul>
    </div>
</div>
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
        <sec:authorize ifNotGranted="ROLE_TEACHER,ROLE_LABMANAGER">
            <li class="TabbedPanelsTab" id="s1"><a class="iStyle_Feelings_Tree_Leaf "
                                                   href="${pageContext.request.contextPath}/teachingArrangement/listAcademicTimetableTerm?currpage=1&id=-1">教务排课推送</a>
            </li>
        </sec:authorize>
        <li class="TabbedPanelsTab selected" id="s2"><a class="iStyle_Feelings_Tree_Leaf "
                                               href="${pageContext.request.contextPath}/teachingArrangement/listGeneralTimetableQuery">课表查询</a>
        </li>
        <li class="TabbedPanelsTab" id="s3"><a class="iStyle_Feelings_Tree_Leaf "
                                                        href="${pageContext.request.contextPath}/teachingArrangement/listPracticalTimetableTrain?currpage=1&id=-1&flag=0">课内实践申请</a>
        </li>
        <sec:authorize ifNotGranted="ROLE_TEACHER,ROLE_LABMANAGER">
            <li class="TabbedPanelsTab" id="s4"><a class="iStyle_Feelings_Tree_Leaf "
                                                   href="${pageContext.request.contextPath}/teachingArrangement/listPracticalTimetableTrain?currpage=1&id=-1&flag=1">实训专用周安排</a>
            </li>
        </sec:authorize>
        <sec:authorize ifNotGranted="ROLE_STUDENT">
            <li class="TabbedPanelsTab" id="s5"><a class="iStyle_Feelings_Tree_Leaf "
                                                   href="${pageContext.request.contextPath}/teachingArrangement/timetablevertifyRelease?currpage=1&id=-1&status=-1">实践性教学排课审核及发布</a>
            </li>
        </sec:authorize>
	</ul>
</div>
<div class="main_container cf rel w95p ma mb60">
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
<form action="${pageContext.request.contextPath}/teachingArrangement/listGeneralTimetableQuery" method="post">
<table border="0" cellpadding="5" cellspacing="0" bgcolor="#F3EFEE" height="30" width="100%">
<tbody>
<tr>
    <td>
    <div class="topselect"style="width:200px">
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
    </div>
     <div class="topselect" style="width:180px">
    <select id="roomId" size="1" name="roomId" class="chzn-select">
        <option value="-1"	selected >所有<spring:message code="all.trainingRoom.labroom" /></option>
    	<c:forEach items="${labRoomMap}" var="current" varStatus="i">
    	    <c:if test="${current.key == roomId}">
    	       <option value ="${current.key}" selected>${current.value} </option>
    	    </c:if>
    	    <c:if test="${current.key != roomId}">
    	       <option value ="${current.key}" >${current.value} </option>
    	    </c:if>		
        </c:forEach>
    </select>
    </div>
        <c:if test="${sessionScope.selected_role ne 'ROLE_TEACHER'}">
    <div class="topselect">
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
    </div>
        </c:if>
    <div class="topselect">
    <select id="schoolClass" name="schoolClass" class="chzn-select" cssStyle="width:200px;" >
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
     </div>
    
    <div class="topselect  ">
	    <input value="查询" type="submit">
	    <a href="${pageContext.request.contextPath}/teachingArrangement/listGeneralTimetableQuery"><input class="cancel-submit" type="button" value="取消查询"></a>
	    <input type="hidden" value=-1 id="courseCode" name="courseCode">
	</div>
    </td>    
</tr>
</tbody>
</table>
<input type="hidden" name="term" value="${term }" >
<input type="hidden" name="roomId" value="${labroom}" >
<input type="hidden" name="week" value="${week }" > 
<input type="hidden" name="courseCode" value="${courseCode }" >
<input type="hidden" name="teacherId" value="${teacherId }" >

</form>
<div class="edit-content">
		
       
					</div>
<div id="myShow">
<div class="dm_left">
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

<table class="dm_right" cellspacing="1">
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
<c:forEach var="class" varStatus="cStatus" begin="1" end="9">
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
           <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第九节<br><fmt:formatDate value='${systemtime["9"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["9"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        <c:if test="${cStatus.index==6}">
           <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第十节<br><fmt:formatDate value='${systemtime["10"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["10"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        <c:if test="${cStatus.index==7}">
           <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第十一节<br><fmt:formatDate value='${systemtime["11"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["11"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        <c:if test="${cStatus.index==8}">
           <td class="tbl tbct"  style="width:60px"><span id="className12-13"><br>第十二节<br><fmt:formatDate value='${systemtime["12"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["12"].endDate.time}' pattern="HH:mm:ss"/></span></td>  
        </c:if>
        
        <c:forEach begin="1" end="7"  varStatus="iWeek">
        <td class="tb" valign="top" do-labReservation='${iWeek.count},${cStatus.index}'>
               <!-- 二次排课显示 -->
               <c:forEach var="ltimetable" items="${labTimetable}" varStatus="lStatus">
                    <c:if test="${ltimetable.timetableAppointment.weekday==iWeek.count&&ltimetable.timetableAppointment.timetableAppointmentSameNumbers.size()==0&&ltimetable.timetableAppointment.enabled}">
                    <c:if test="${cStatus.index<=4}">
                        <c:if test="${ltimetable.timetableAppointment.startClass<=cStatus.index*2-1&&ltimetable.timetableAppointment.endClass>=cStatus.index*2-1||ltimetable.timetableAppointment.startClass>=cStatus.index*2&&ltimetable.timetableAppointment.endClass<=cStatus.index*2}">
                            <c:if test="${ltimetable.timetableAppointment.timetableStyle!=5&&ltimetable.timetableAppointment.timetableStyle!=6 }">
                            <!-- 不分批处理 -->
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state!=0 }">
                            <hr><font color="blue">${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}</font>
                            </c:if>
                            <c:if test="${ltimetable.timetableAppointment.schoolCourse.state==0 }">
                            <hr><font color="blue">${ltimetable.timetableAppointment.schoolCourse.schoolCourseInfo.courseName}</font>
                            </c:if>
                            </a><br>
                            </c:if>
                                                                                      实验室：${ltimetable.labRoom.labRoomName}<br>
                                                                                            班级：${ltimetable.timetableAppointment.schoolClasses.className }<br>       
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
                    <c:if test="${cStatus.index>4}">
                        <c:if test="${ltimetable.timetableAppointment.startClass<=cStatus.index+4&&ltimetable.timetableAppointment.endClass>=cStatus.index+4}">
                            <c:if test="${ltimetable.timetableAppointment.timetableStyle!=5 &&ltimetable.timetableAppointment.timetableStyle!=6 }">
                            
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

                            </c:if>
                                                                                       实验室：${ltimetable.labRoom.labRoomAddress}&nbsp;${ltimetable.labRoom.labRoomName};<br>
                             <c:forEach var="tTimetable" items="${ltimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                           教师： ${tTimetable.user.cname}
                            </c:forEach><br>
                            班级：${ltimetable.timetableAppointment.schoolClasses.className }<br>
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
                                 班级：${ltimetable.timetableAppointment.schoolClasses.className }<br>                                                              
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
                            </c:if>
                    <c:if test="${cStatus.index>4}">
                            <c:set var="sameStart" value="-"></c:set>
                            <c:forEach var="entry" items="${ltimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p"> 
                            <c:if test="${entry.startClass<=cStatus.index+4&&entry.endClass>=cStatus.index+4}">
                            <c:if test="${ltimetable.timetableAppointment.timetableStyle!=5&&ltimetable.timetableAppointment.timetableStyle!=6 }">
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
                                 班级：${ltimetable.timetableAppointment.schoolClasses.className }<br>                                                             
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
                    </c:if>
                    </c:if>
               </c:forEach> 
        
               <!-- 自主排课显示 -->
                <c:forEach var="labSelfTimetable" items="${labSelfTimetable}" varStatus="lStatus">
                    <c:if test="${labSelfTimetable.timetableAppointment.weekday==iWeek.count&&labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers.size()==0&&labSelfTimetable.timetableAppointment.enabled}">
                    <c:if test="${cStatus.index<=4}">
                        <c:if test="${labSelfTimetable.timetableAppointment.startClass<=cStatus.index*2-1&&labSelfTimetable.timetableAppointment.endClass>=cStatus.index*2-1||labSelfTimetable.timetableAppointment.startClass>=cStatus.index*2&&labSelfTimetable.timetableAppointment.endClass<=cStatus.index*2}">
                            <%-- <c:if test="${labSelfTimetable.timetableAppointment.timetableStyle==5 }">
                            <font color=red><b>[${labSelfTimetable.labRoom.labRoomNumber}&nbsp;${labSelfTimetable.labRoom.labRoomName}]</b></font><br>
                            </c:if> --%>
                            
                            <!-- 自主排课 -->
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableStyle==5 ||labSelfTimetable.timetableAppointment.timetableStyle==6 }">
                            <!-- 不分批处理 -->
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableGroups.size()==0 }">
                            <input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="${labSelfTimetable.timetableAppointment.timetableSelfCourse.courseCode}-${labSelfTimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}">
                            <br>
                            </c:if>
                            </c:if>
                            
                                                                                   实验室：${labSelfTimetable.labRoom.labRoomName}<br>
                                                                        教师：<c:forEach var="tTimetable" items="${labSelfTimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                            ${tTimetable.user.cname}
                            </c:forEach> <br>
                             班级：${ltimetable.timetableAppointment.schoolClasses.className }<br>
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
                             <!--分批排课状况--><br>
                        </c:if>
                    </c:if>
                    <c:if test="${cStatus.index>4}">
                        <c:if test="${labSelfTimetable.timetableAppointment.startClass<=cStatus.index+4&&labSelfTimetable.timetableAppointment.endClass>=cStatus.index+4}">
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableStyle==5 ||labSelfTimetable.timetableAppointment.timetableStyle==6 }">
                            
                            <!-- 分批处理 -->
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableGroups.size()>0 }">
                            <input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="[${labSelfTimetable.labRoom.labRoomNumber}&nbsp;${labSelfTimetable.labRoom.labRoomName}]">
                            <br>
                            </c:if>
                             <!-- 不分批处理 -->
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableGroups.size()==0 }">
                            <input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="[${labSelfTimetable.labRoom.labRoomNumber}&nbsp;${labSelfTimetable.labRoom.labRoomName}]">
                            <br>
                            </c:if>

                            </c:if>
                            ${labSelfTimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}<br>
                             <c:forEach var="tTimetable" items="${labSelfTimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                           ${tTimetable.user.cname}
                            </c:forEach>  <br>
                             班级：${ltimetable.timetableAppointment.schoolClasses.className }<br>
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
                             <br>
                        </c:if>
                    </c:if>
                    </c:if>
                 
                    <c:if test="${labSelfTimetable.timetableAppointment.weekday==iWeek.count&&labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers.size()>0&&labSelfTimetable.timetableAppointment.enabled}">
                    <c:if test="${cStatus.index<=4}">
                            <c:set var="sameStart" value="-"></c:set>
                            <c:forEach var="entry" items="${labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p"> 
                            <c:if test="${entry.startClass<=cStatus.index*2-1&&entry.endClass>=cStatus.index*2-1||entry.startClass>=cStatus.index*2&&entry.endClass<=cStatus.index*2}">
                        
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableStyle==5 ||labSelfTimetable.timetableAppointment.timetableStyle==6 }">
                            <!-- 显示周次节次 -->
                            <c:set var="v_param" value="-${entry.startClass}-" ></c:set>   
                            <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                            <!-- 分批处理 -->
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableGroups.size()>0 }">
                            <input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="${labSelfTimetable.timetableAppointment.timetableSelfCourse.courseCode}-${labSelfTimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}">
                            <br>
                            </c:if>
                             <!-- 不分批处理 -->
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableGroups.size()==0 }">
                            <input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="${labSelfTimetable.timetableAppointment.timetableSelfCourse.courseCode}-${labSelfTimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}">
                            <br>
                            </c:if>
                                                                                                实验室：${labSelfTimetable.labRoom.labRoomNumber}&nbsp;${labSelfTimetable.labRoom.labRoomName}<br>
                               <c:forEach var="tTimetable" items="${labSelfTimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                                     	${tTimetable.user.cname}
                                </c:forEach><br>  
                                 班级：${ltimetable.timetableAppointment.schoolClasses.className }<br>                                                               
                                <c:if test="${entry.startClass==entry.endClass}">
                                                                                                         节次：${entry.startClass }
                                </c:if>
                                <c:if test="${entry.startClass!=entry.endClass}">
                                                                                                                          节次： ${entry.startClass }-${entry.endClass }
                                </c:if>
                            <br>
                            <c:set var="sameStart" value="${sameStart}-${entry.startClass }-"></c:set>
                                                                                                  周次：<c:forEach var="entry1" items="${labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers}"  varStatus="q">  
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
                            </c:if>
                    <c:if test="${cStatus.index>4}">
                            <c:set var="sameStart" value="-"></c:set>
                            <c:forEach var="entry" items="${labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers}"   varStatus="p"> 
                            <c:if test="${entry.startClass<=cStatus.index+4&&entry.endClass>=cStatus.index+4}">
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableStyle==5 ||labSelfTimetable.timetableAppointment.timetableStyle==6 }">
                            <!-- 显示周次节次 -->
                            <c:set var="v_param" value="-${entry.startClass}-" ></c:set>   
                            <c:if test="${fn:indexOf(sameStart,v_param)<0}">
                            <!-- 分批处理 -->
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableGroups.size()>0 }">
                            <input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="${labSelfTimetable.timetableAppointment.timetableSelfCourse.courseCode}-${labSelfTimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}">
                            <br>
                            </c:if>
                             <!-- 不分批处理 -->
                            <c:if test="${labSelfTimetable.timetableAppointment.timetableGroups.size()==0 }">
                            <input type="button" onclick="alert('请到自主排课模块处理自主排课业务！')" style="color:#CC3333" value="${labSelfTimetable.timetableAppointment.timetableSelfCourse.courseCode}-${labSelfTimetable.timetableAppointment.timetableSelfCourse.schoolCourseInfo.courseName}">
                            <br>
                            </c:if>
                                                                                                实验室：${labSelfTimetable.labRoom.labRoomNumber}&nbsp;${labSelfTimetable.labRoom.labRoomName}<br>
                               <c:forEach var="tTimetable" items="${labSelfTimetable.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                                                                                           ${tTimetable.user.cname}
                                </c:forEach><br> 
                                 班级：${ltimetable.timetableAppointment.schoolClasses.className }<br>                                                                
                                <c:if test="${entry.startClass==entry.endClass}">
                                                                                                         节次：${entry.startClass }
                                </c:if>
                                <c:if test="${entry.startClass!=entry.endClass}">
                                                                                                                          节次： ${entry.startClass }-${entry.endClass }
                                </c:if>
                            <br>
                            <c:set var="sameStart" value="${sameStart}-${entry.startClass }-"></c:set>
                                                                                                  周次：<c:forEach var="entry1" items="${labSelfTimetable.timetableAppointment.timetableAppointmentSameNumbers}"  varStatus="q">  
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
<script type="text/javascript">
$(document).ready(function(){
      $('#fullview').click(function(){
           $('.sit_sider_bar').animate( { width:'0'}, 500 );
           $('.sit_maincontent').animate( { width:'100%'}, 500 );
           $('.toggle,.toggleLink,#fullview,.sit_footer,.sit_sider_bar > h3').css("display","none");
           $('#fullview1').css("display","inline");
      });
  
      $('#fullview1').click(function(){
           $('.sit_sider_bar').animate( { width:'23%'}, 500 );
           $('.sit_maincontent').animate( { width:'75%'}, 500 );
           $('#fullview1').css("display","none");
           $('.toggle,#fullview,.toggleLink,.sit_footer,.sit_sider_bar > h3 ').css("display","inline");
      });
      
      $('#myPrint').click(function(){
           $('#myShow').jqprint();
      });
});
                              
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
	
		subform('${pageContext.request.contextPath}/teachingArrangement/listGeneralTimetableQuery?roomId='+${roomId}+'&week='+week);
	});

	//如果为查询则提交查询页面，如果为电子表格导出，则导出excel
		function subform(gourl){ 
		 form.action=gourl;
		 form.submit();
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