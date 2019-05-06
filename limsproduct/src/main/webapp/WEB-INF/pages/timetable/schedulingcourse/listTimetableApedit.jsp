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

<script type="text/javascript">

//得到courseDetail的节次和周次信息
var startWeek=1;//开始周的全局变量
var endWeek=19;//结束周的全局变量
var canSubmit=true;
function checkForm(){
	
	    if (canSubmit==false){
	    	alert("请等待本次进度修改")
	             return;
	    }
	    canSubmit=false;
	    document.form.submit();
	}


</script>



</head>

<div class="iStyle_RightInner">
<div class="navigation">
<div id="navigation">
<ul>
	<li class="end">进度安排</li>
</ul>
</div>
</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<div class="title">进度安排编辑</div>

<form name="form" method="Post" action="${pageContext.request.contextPath}/timetable/saveTimetableApedit?currpage=${currpage}">

<table > 
<thead>
<tr>
  <!--  <th>选择</th> -->
   <th>教室</th>
   <!-- <th>选择实验室设备</th> 暂时不用，隐掉-->
   <th>上课时间</th>
   <th>上课教师</th>
   <th>教学内容</th>
   <th>课程类别</th>
</tr>
</thead>
<tbody>
<tr>
  <!--  <th>选择</th> -->
   <td>
   <c:forEach items="${timetableAppointmentAp.timetableLabRelateds}" var="cur">
  ${cur.labRoom.labRoomName}
   </c:forEach>
    </select>
   </td><%--
   
    <td>
     <select class="chzn-select" multiple="multiple" data-placeholder='请选择实验设备：'  name="devices" id="devices" style="width:180px" required="false">
	</select>
   </td>  暂时不用，隐掉
   
   --%>
   <td>
  星期 ${timetableAppointmentAp.schoolCourseDetail.weekday};
  ${timetableAppointmentAp.schoolCourseDetail.startClass}-${timetableAppointmentAp.schoolCourseDetail.endClass}节;
  ${timetableAppointmentAp.schoolCourseDetail.startWeek}-${timetableAppointmentAp.schoolCourseDetail.endWeek}周
   </td>
   <td>
   ${timetableAppointmentAp.schoolCourseDetail.user.cname}
   </td>
 <td ><input name="content"style="width: 100px;height: 15px;padding: 5px" /></td>
  <td>
   <select class="chzn-select" data-placeholder='请选择课程类别：' name="classType" id="classType" style="width:100px" required="true">
	      <option value ="0">理论</option>
	      <option value ="1">实验</option>
	      <option value ="2">示教</option>
	      <option value ="3">临床实习</option>
	      <option value ="4">研讨</option>
	      <option value ="5">导师组活动</option>
	      <option value ="6">毕业论文指导</option>
	      <option value ="7">理论+实验</option>
   </select>
   </td>
</tr>

	
<tr>
<td colspan=4>
<input type="hidden" name="courseDetailNo" value="${schoolCourseDetailMap.get(0).courseDetailNo}">
<input type="hidden" name="courseCode" value="${coursecode}">
<input type="hidden" name="currpage" value="${currpage}">
<input type="hidden" name="taid" value="${timetableAppointmentAp.id}">
<!--传递修改的排课表主键  -->
<input type="hidden" value="${tableAppId}" name="id">


<input type="button" onclick="checkForm()" value="提交">&nbsp;&nbsp;
</td>


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
    
</script>
<!-- 下拉框的js -->

