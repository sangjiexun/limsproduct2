<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<head>
<meta name="decorator" content="iframe"/>
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />

<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->	


<style type="text/css">
	.labDiv{
		display:none;
	}
</style>

</head>

<div class="iStyle_RightInner">
<div class="navigation">
<div id="navigation">
<ul>
	<li><a href="javascript:void(0)">排课管理</a></li>
	<li class="end">调出</li>
</ul>
</div>
</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<div class="title">调出记录</div>
<table  > 
<thead>
<tr>
  <!--  <th>选择</th> -->
   <th>课程编号</th>
   <th>课程名称</th>
   <th>选课组编号</th>
   <th>实训课题</th>
   <th>星期</th>
   <th width="90px;" colspan=2>
       <table>
       <tr>
          <td width="20px;">节次</td>
          <td width="50px;">周次</td>
       </tr>
       </table>
   </th>
   <th>授课教师</th>
   <c:if test="${selected_labCenter eq 12 }">
   <th>是否允许<br>教学外设备对外开放</th>
   <th><spring:message code="all.trainingRoom.labroom" />（设备）</th>
   </c:if>
   <c:if test="${selected_labCenter ne 12 }">
   <th><spring:message code="all.trainingRoom.labroom" /></th>
   </c:if>

</tr>
</thead>
<tbody>

<tr>
     
     
     
         <td >
         ${timetableAppointmentMap.schoolCourseDetail.schoolCourse.schoolCourseInfo.courseNumber}
         </td>
         <td >
         ${timetableAppointmentMap.schoolCourseDetail.schoolCourse.courseName}
         </td>
         <td >${timetableAppointmentMap.courseCode}</td>
     <td>
             <c:forEach var="entry" items="${timetableAppointmentMap.timetableItemRelateds}">  
             <c:out value="${entry.operationItem.lpName}" />&nbsp;
             <c:if test="${empty entry.operationItem||entry.operationItem.id==0}">
             ${timetableAppointmentMap.schoolCourseDetail.schoolCourse.courseName}(课程名称)
             </c:if>
             </c:forEach>
             <c:if test="${timetableAppointmentMap.timetableItemRelateds.size()==0}">
             ${timetableAppointmentMap.schoolCourseDetail.schoolCourse.courseName}(课程名称)
             </c:if>

     </td>
     <td>${timetableAppointmentMap.weekday}</td>
          
    <td colspan=2>
     <table>
     <c:if test="${timetableAppointmentMap.timetableAppointmentSameNumbers.size()==0}">
     <tr>
             <td width="20px;">
             <c:if test="${timetableAppointmentMap.startClass==timetableAppointmentMap.endClass}">
             ${timetableAppointmentMap.startClass }
             </c:if>
             <c:if test="${timetableAppointmentMap.startClass!=timetableAppointmentMap.endClass}">
             ${timetableAppointmentMap.startClass }-${timetableAppointmentMap.endClass }
             </c:if>
             </td>
             <td width="50px;">
             <c:if test="${timetableAppointmentMap.startWeek==timetableAppointmentMap.endWeek}">
             ${timetableAppointmentMap.startWeek }
             </c:if>
             <c:if test="${timetableAppointmentMap.startWeek!=timetableAppointmentMap.endWeek}">
             ${timetableAppointmentMap.startWeek }-${timetableAppointmentMap.endWeek }
             </c:if>
             </td>
      </tr>
      </c:if>
     <c:if test="${timetableAppointmentMap.timetableAppointmentSameNumbers.size()>0}">
         
         <c:set var="sameStart" value="-"></c:set>
         <c:forEach var="entry" items="${timetableAppointmentMap.timetableAppointmentSameNumbers}"   varStatus="p"> 
         <c:set var="v_param" value="-${entry.startClass}-" ></c:set>   
         <c:if test="${fn:indexOf(sameStart,v_param)<0}">
         <tr>
         <td width="20px;">
             <c:if test="${entry.startClass==entry.endClass}">
             ${entry.startClass }
             </c:if>
             <c:if test="${entry.startClass!=entry.endClass}">
             ${entry.startClass }-${entry.endClass }
             </c:if>
         </td>
         <td width="50px;">
         <c:set var="sameStart" value="${sameStart}-${entry.startClass }-"></c:set>
         <c:forEach var="entry1" items="${timetableAppointmentMap.timetableAppointmentSameNumbers}"  varStatus="q">  
             <c:if test="${entry.startClass==entry1.startClass}">
             <%-- <td>
             [${entry1.startClass }-${entry1.endClass }]
             </td> --%>
             <c:if test="${entry1.startWeek==entry1.endWeek}">
              ${entry1.startWeek }
             </c:if>
             <c:if test="${entry1.startWeek!=entry1.endWeek}">
              ${entry1.startWeek }-${entry1.endWeek }
             </c:if>
             
             </c:if>
         </c:forEach>
         </td>
         </tr>
         </c:if>
         </c:forEach>
          
     </c:if>
     </table>
     </td>

     <td>
     <!--上课教师  -->
      <c:forEach var="entry" items="${timetableAppointmentMap.timetableTeacherRelateds}">  
     <c:out value="${entry.user.cname}" />  
     </c:forEach> 
     </td>
     
     <c:if test="${selected_labCenter eq 12 }"><!-- 纺织中心 -->
	     <!-- 是否允许教学外设备对外开放 -->
         <td>
            <c:if test="${timetableAppointmentMap.deviceOrLab eq 1}">是</c:if>
            <c:if test="${timetableAppointmentMap.deviceOrLab eq 2}">否</c:if>
         </td>
         
        <!--<spring:message code="all.trainingRoom.labroom" />(设备)  -->
         <td>
             <c:forEach var="entry" items="${timetableAppointmentMap.timetableLabRelateds}">  
             <label title="<c:out value="${entry.labRoom.labRoomName}" />"><c:out value="${entry.labRoom.labRoomNumber}" /></label>
             <c:if test="${selected_labCenter eq 12 }"><!-- 纺织中心 -->
                <a target="_blank" href="${pageContext.request.contextPath}/timetable/listTeachingLabRoomDevice?timetableId=${current.id}&labRoomId=${entry.labRoom.id}">查看教学用设备</a>
             </c:if>    
             <br>  
             </c:forEach>
         </td>
	</c:if>
	
	<c:if test="${selected_labCenter ne 12 }"><!-- 非纺织中心 -->
		<td>
			<c:forEach var="entry" items="${timetableAppointmentMap.timetableLabRelateds}">  
				<c:out value="${entry.labRoom.labRoomName}" />  
			</c:forEach>
		</td>	
	</c:if>
</tr>

</tbody>
</table>
<br>

<div class="title">排课内容调整</div>
<form name="form" method="Post" action="${pageContext.request.contextPath}/timetable/saveAdjustMent?currpage=${currpage}"  >


<table > 
<thead>
<tr>
  <!--  <th>选择</th> -->
      <th>实训课题</th>
            <th>选择周次</th>
   <th>选择星期</th>
   <th>选择节次</th>
    <th>选择教师</th>
    <th>新上课地点</th>
     <th>调出原因</th>
    <th>操作</th>
  

</tr>
</thead>
<tbody>
<tr>
<td>
<c:if test="${operationItem.size() eq 0}">
${timetableAppointmentMap.schoolCourseDetail.schoolCourse.courseName}(课程名称)
</c:if>
	<c:if test="${operationItem.size() ne 0}">
     	${operationItem.get(0).lpName}
     <input type="hidden" value="${operationItem.get(0).lpName}" name="ItemName" id="ItemName">
	</c:if>
   </td>
     <td>
   <select class="chzn-select"   data-placeholder='请选择排课周次' name="weeks" id="weeks" style="width:180px" required="true">
   <option value ="${timetableAppointmentMap.startWeek}" selected> ${timetableAppointmentMap.startWeek}</option>
   <option value ="">请选择</option>
   <c:forEach items="${schoolweek}" var="current" >
         <option value ="${current}" > ${current}</option>
   </c:forEach>
   </select>
   </td>
     <td>
   <select class="chzn-select" name="weekday" id="weekday" style="width:120px" value ="${timetableAppointmentMap.weekday}" selected="selected">
     <!--如果星期重复，则去重  -->
          <option value ="1">周一</option>
	      <option value ="2">周二</option>
	      <option value ="3">周三</option>
	      <option value ="4">周四</option>
	      <option value ="5">周五</option>
	      <option value ="6">周六</option>
	      <option value ="7">周日</option>
   </select>
   </td>
  
   <td>
  <select class="chzn-select" multiple="multiple" data-placeholder='请选择节次' name="classes" id="classes" style="width:150px" required="true">
    <c:forEach  items="${selectClass}" var="cur">
    <option value ="${cur}" selected>${cur}</option>
    </c:forEach>
          <option value ="1">第一节</option>
	      <option value ="2">第二节</option>
	      <option value ="3">第三节</option>
	      <option value ="4">第四节</option>
	      <option value ="5">第五节</option>
	      <option value ="6">第六节</option>
	      <option value ="7">第七节</option>
	      <option value ="8">第八节</option>
	      <option value ="9">第九节</option>
	      <option value ="10">第十节</option>
	      <option value ="11">第十一节</option>
	      <option value ="12">第十二节</option>                    
   </select> 
   </td>
   <td>
  <select class="chzn-select"  data-placeholder='请选择授课教师' name="teachers" id="teachers" style="width:150px" required="true">
	  <c:forEach items="${timetableTearcherMap}" var="current"  varStatus="i">
	      <option value ="${current.value}" > ${current.value}</option>
	  </c:forEach>
   </select>
   </td>
   <td>
   <input id="labroom" name="labroom" >
   </td>
   <td><input id="cause" name="cause"></td>
    <td>
   <input type="submit" onclick="closeIframe()" value="确认" >	
   </td>
</tr>

<tr>

<td colspan=4>
<input type="hidden" name="courseDetailNo" value="${schoolCourseDetailMap.get(0).courseDetailNo}">
<input type="hidden" name="courseCode" value="${courseCode}">
<input type="hidden" name="currpage" value="${currpage}">

<!--传递修改的排课表主键  -->
<input type="hidden" value="${tableAppId}" name="id">
</td>


<%-- <td >
(<font color=red><b>请核实不同的星期是否调课完成，如果完成请点击完成调课</b></font>)
</td>
<td align="right">
<a class="btn btn-new" href="${pageContext.request.contextPath}/timetable/doJudgeTimetableOk?courseCode=${schoolCourseDetailMap.schoolCourse.courseCode}&currpage=1" target=_parent><font color=red>调课完成</font></a>
</td> --%>

</tr>
</tbody>
</table>
</form>
</div>
</div>
</div>
</div>
</div>
</div>
<hr>

<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
      '.chzn-select'           : {search_contains:true},
      '.chzn-select-deselect'  : {allow_single_deselect:true},
      '.chzn-select-no-single' : {disable_search_threshold:10},
      '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
    function closeIframe(){
      //window.parent.document.getElementById("doUpdateTimetable").style.display="none";
      window.parent.location.reload();
    }
</script>
<!-- 下拉框的js -->

