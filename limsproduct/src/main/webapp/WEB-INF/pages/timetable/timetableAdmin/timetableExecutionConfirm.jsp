<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<head>
<meta name="decorator" content="iframe"/>
<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/css/style.css">
<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/css/iconFont.css">
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">
//跳转
function targetUrl(url)
{
  document.form.action=url;
  document.form.submit();
}

</script>

<!--直接排课  -->
<script>
/*
*调整排课弹出窗口--针对二次排课不分批（style=3）
*/
function doUpdateTimetable3(id){
var sessionId=$("#sessionId").val();
var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/doAdminTimetable?id=' + id + '" style="width:100%;height:100%;"></iframe>'
$('#doUpdateTimetable').html(con);  
//获取当前屏幕的绝对位置
var topPos = window.parent.pageYOffset;
//使得弹出框在屏幕顶端可见
$('#doUpdateTimetable').window({left:"0px", top:topPos+"px"});
$('#doUpdateTimetable').window('open');
}

/*
*调整排课弹出窗口--针对二次排课分批（style=4）
*/
function doUpdateTimetable4(courseCode,id){
var sessionId=$("#sessionId").val();
var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/doGroupReTimetable?term=${term}&courseCode='+ courseCode
+'&appointment='+id+'" style="width:100%;height:100%;"></iframe>'
$('#doUpdateTimetable').html(con);  
//获取当前屏幕的绝对位置
var topPos = window.parent.pageYOffset;
//使得弹出框在屏幕顶端可见
$('#doUpdateTimetable').window({left:"0px", top:topPos+"px"});
$('#doUpdateTimetable').window('open');
}

/*
*调整排课弹出窗口--针对自主排课分批（style=6）
*/
function doUpdateTimetable6(courseCode,id){
var sessionId=$("#sessionId").val();
var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/selfTimetable/doGroupSelfTimetable?term=${term}&courseCode='+ courseCode
+'&appointment='+id+'" style="width:100%;height:100%;"></iframe>'
$('#doUpdateTimetable').html(con);  
//获取当前屏幕的绝对位置
var topPos = window.parent.pageYOffset;
//使得弹出框在屏幕顶端可见
$('#doUpdateTimetable').window({left:"0px", top:topPos+"px"});
$('#doUpdateTimetable').window('open');
}

/*
 *调整排课弹出窗口--针对教务排课--调整排课（style=2）
 */
function doUpdateTimetable2(courseCode,id) {
	var sessionId = $("#sessionId").val();
	var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/openAdjustTimetable?currpage=${pageModel.nextPage-1}&flag=0&courseCode='+ courseCode
			+'&tableAppId='+id+'" style="width:100%;height:100%;"></iframe>'
		$('#doUpdateTimetable').html(con);
		//获取当前屏幕的绝对位置
        var topPos = window.pageYOffset;
        //使得弹出框在屏幕顶端可见
        $('#doUpdateTimetable').window({left:"0px", top:topPos+"px"});
		$('#doUpdateTimetable').window('open');
}

/*
 *调整排课弹出窗口--针对自主排课不分批（style=5）
 */
function doUpdateTimetable5(courseCode,id) {
	var sessionId = $("#sessionId").val();
	var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/selfTimetable/doIframeNoGroupSelfTimetable?term=${term}&weekday=0&classids=0&courseCode='+ courseCode
			+'&labroom=0&tableAppId='+id+'" style="width:100%;height:100%;"></iframe>'
		$('#doUpdateTimetable').html(con);
		//获取当前屏幕的绝对位置
        var topPos = window.pageYOffset;
        //使得弹出框在屏幕顶端可见
        $('#doUpdateTimetable').window({left:"0px", top:topPos+"px"});
		$('#doUpdateTimetable').window('open');
}

/*
*查看二次选课，课程学生名单
*/
function listTimetableStudents(courseDetailNo){
var sessionId=$("#sessionId").val();
var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/openSearchStudent?courseDetailNo=' + courseDetailNo + '" style="width:100%;height:100%;"></iframe>'
$('#doSearchStudents').html(con);  
//获取当前屏幕的绝对位置
var topPos = window.parent.pageYOffset;
//使得弹出框在屏幕顶端可见
$('#doSearchStudents').window({left:"0px", top:topPos+"px"});
$('#doSearchStudents').window('open');
}

// 二次排课--分批管理（更新学生名单）
function manageBatch(courseDetailNo){
	var sessionId=$("#sessionId").val();
	var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/manageBatch?courseDetailNo=' + courseDetailNo + '" style="width:100%;height:100%;"></iframe>'
	$('#manageBatch').html(con);  
	//获取当前屏幕的绝对位置
	var topPos = window.parent.pageYOffset;
	//使得弹出框在屏幕顶端可见
	$('#manageBatch').window({left:"0px", top:topPos+"px"});
	$('#manageBatch').window('open');
}

// 自主排课--分批管理（更新学生名单）
function manageBatchSelf(selfCourseId){
	var sessionId=$("#sessionId").val();
	var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/manageBatchSelf?selfCourseId=' + selfCourseId + '" style="width:100%;height:100%;"></iframe>'
	$('#manageBatch').html(con);  
	//获取当前屏幕的绝对位置
	var topPos = window.parent.pageYOffset;
	//使得弹出框在屏幕顶端可见
	$('#manageBatch').window({left:"0px", top:topPos+"px"});
	$('#manageBatch').window('open');
}


/*
*查看自主选课，课程学生名单
*/
function listTimetableSelfStudents(courseCode){
var sessionId=$("#sessionId").val();
var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/selfTimetable/listTimetableCourseStudents?id=' + courseCode + '" style="width:100%;height:100%;"></iframe>'
$('#doSearchStudents').html(con);  
//获取当前屏幕的绝对位置
var topPos = window.parent.pageYOffset;
//使得弹出框在屏幕顶端可见
$('#doSearchStudents').window({left:"0px", top:topPos+"px"});
$('#doSearchStudents').window('open');
}
//分组名单
function listTimetableStudentsGroup(id,timetableId,timetableStyle){
	var groupName;
	if(timetableStyle==4){
		
		groupName=$("#groupName4"+timetableId).text()+","+4;// 4--二次排课分组
	}else{
		groupName=$("#groupName6"+timetableId).text()+","+6;// 6--自主排课分组
	}
var sessionId=$("#sessionId").val();
var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/openSearchStudentGroup?id=' + id + '&timetableId='+ timetableId + '&groupName='+groupName+'" style="width:100%;height:100%;"></iframe>'
$('#doSearchStudents').html(con);  
//获取当前屏幕的绝对位置
var topPos = window.parent.pageYOffset;
//使得弹出框在屏幕顶端可见
$('#doSearchStudents').window({left:"0px", top:topPos+"px"});
$('#doSearchStudents').window('open');
}

/*
*查看学生二次分组情况
*/
function listTimetableGroup(term,weekday,classids,courseCode,labroom, groupId){
var sessionId=$("#sessionId").val();
var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/listTimetableGroup?term=' + term +'&weekday='+weekday+'&classids='+classids+'&courseCode='+courseCode+'&labroom='+labroom+'&groupId='+groupId+'" style="width:100%;height:100%;"></iframe>'
$('#doSearchGroup').html(con);  
//获取当前屏幕的绝对位置
var topPos = window.parent.pageYOffset;
//使得弹出框在屏幕顶端可见
$('#doSearchGroup').window({left:"0px", top:topPos+"px"});
$('#doSearchGroup').window('open');
}

/*
*查看学生自主分组情况
*/
function listTimetableSelfGroup(term,weekday,classids,courseCode,labroom){
var sessionId=$("#sessionId").val();
var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/selfTimetable/doIframeGroupSelfTimetable?term=' + term +'&weekday='+weekday+'&classids='+classids+'&courseCode='+courseCode+'&labroom='+labroom+'" style="width:100%;height:100%;"></iframe>'
$('#doSearchGroup').html(con);  
//获取当前屏幕的绝对位置
var topPos = window.parent.pageYOffset;
//使得弹出框在屏幕顶端可见
$('#doSearchGroup').window({left:"0px", top:topPos+"px"});
$('#doSearchGroup').window('open');
}
</script>

<!--学期查询条件同步 -->
<script  type="text/javascript">
$(document).ready(function(){
$("#term").chosen().change(function(){

$.ajax({
         url:"${pageContext.request.contextPath}/timetable/getAdminCourseCodeList",
         dataType:"json",
         contentType: "application/x-www-form-urlencoded; charset=utf-8", 
         type:'GET',
         data:{term:$("#term").val()},
         complete:function(result)
         {
            var obj = eval(result.responseText); 
            var result2;
            $("#schoolCourseInfo_courseNumber").empty();
            
            result2 = result2 + "<option value='-1'>所有课程列表 </option>";
            for (var i = 0; i < obj.length; i++) { 
                 result2 = result2 + "<option value='" +obj[i].courseNumber + "'>" + obj[i].value + "</option>";
            }
            $("#schoolCourseInfo_courseNumber").append(result2);
            $("#schoolCourseInfo_courseNumber").trigger("liszt:updated");
            result2="";
          }
});
$("#schoolCourseInfo_courseNumber").trigger("liszt:updated");
});
});
//确定是否删除
function sureDelete(){
    if(confirm( '删除后，排课状态还原，发布、分组信息，学生选课信息还原！确定删除此条记录？')==false){
       return   false;
    }
} 
</script>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->	
<style type="text/css">
	.chzn-container{width: 95% !important}
	.content-box table tr{height:55px;}
	.content-box table thead tr{height:auto !important}
	/* .content-box table td{border-left:1px dotted #eee;} */

</style>

</head>
<div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">排课管理</a></li>
		<li class="end"><a href="javascript:void(0)">排课管理</a></li>
	  </ul>
	</div>
  </div>
<div class="iStyle_RightInner">

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">

<ul class="TabbedPanelsTabGroup" style="margin-bottom:5px;">
		<li class="TabbedPanelsTab" ><a href="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=-1&isposted=${isPosted}">全部</a></li>
		<li class="TabbedPanelsTab selected"><a href="${pageContext.request.contextPath}/timetable/timetableAdminIframeConfirm?currpage=1&id=-1&status=-1&isposted=${isPosted}">上课确认</a></li>
</ul>
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="tool-box">
<%--<form:form name="form" method="Post" action="${pageContext.request.contextPath}/timetable/timetableAdminIframeConfirm?currpage=1&id=-1&status=${status }&isposted=${isPosted}" modelAttribute="timetableAppointment">
<ul>
   <li>
   <form:select class="chzn-select"  path="schoolCourseInfo.courseNumber" id="schoolCourseInfo_courseNumber" cssStyle="width:650px" >
	  <form:option value=""	label="所有课程列表  " />
	  <c:forEach items="${timetableAppointmentAll}" var="current"  varStatus="i">	
	      <!--添加相关排课的授课老师查询项username，cname  -->
	      <c:set var="searchChar" value="" />
	      <c:forEach items="${current.timetableTeacherRelateds}" var="current1" >	
	         <c:set var="searchChar" value="${searchChar}[${current1.user.cname}${current1.user.username}]" />  
	      </c:forEach>
	      <c:if test="${current.schoolCourseInfo.courseNumber ne  courseNumber}">
	      <form:option value ="${current.schoolCourseInfo.courseNumber}" label="课程名称：${current.schoolCourseInfo.courseName} 选课组编号：${current.courseCode}  课程安排：星期${current.weekday} ${current.startClass}-${current.endClass}[${current.startWeek}-${current.endWeek}授课教师：${searchChar } " />  
	      </c:if>
	      <c:if test="${current.schoolCourseInfo.courseNumber eq  courseNumber}">
	      <form:option value ="${current.schoolCourseInfo.courseNumber}" selected="true" label="课程名称：${current.schoolCourseInfo.courseName} 选课组编号：${current.courseCode}  课程安排：星期${current.weekday} ${current.startClass}-${current.endClass}[${current.startWeek}-${current.endWeek}授课教师：${searchChar } " />  
	      </c:if>
	  </c:forEach>
   </form:select>&nbsp;
   </li> 
   <li><input type="submit" value="查询" id="search" /> </li> 
   <li> <a href="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&status=${status }&id=-1&isposted=${isPosted}"><input type="button" value="取消查询"></a></li>  
   </ul>

</form:form>--%>
</div>
<div class="content-box">
<div class="title">
	  <div id="title">排课管理列表</div>
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/timetable/newLabroomTimetableRegister">新建<spring:message code="all.trainingRoom.labroom" />使用登记</a>
</div>
<table> 
<thead>
<tr>
   <th width="30px;">序号</th>
   <th width="70px;">选课组编号</th>
   <th width="70px;">课程名称</th>
   <th width="130px;">实验项目名称</th>
   <th width="30px;">星期</th>
   <th width="45px;">节次</th>
   <th width="45px;">周次</th>
   <th width="60px;">上课教师</th>
   <th><spring:message code="all.trainingRoom.labroom" /></th>
   <th width="10%;">操作</th>
</tr>
</thead>
<tbody>

<c:forEach items="${labroomTimetableRegisters}" var="v"  varStatus="i">
<tr>
	<td>${i.count+(page-1)*pageSize}</td>
	<td>${v[1]}</td>
	<td>${v[2]}</td>
	<td>${v[3]}</td>
	<td>${v[4]}</td>
	<td>${v[5]}-${v[6]}</td>
	<td>${v[7]}-${v[8]}</td>
	<td>${v[14]}</td>
	<td>${v[15]}</td>
	<td>
	     <c:if test="${v[16] eq 1}">
         <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
          <c:if test="${empty v[17]}">
           <a href='${pageContext.request.contextPath}/timetable/sendMessageAppointment?id=${v[0]}&term=${term}&currpage=${page}&courseNumber=${courseNumber}&flag=${v[16]}'>推送消息</a>
         </c:if>
         </sec:authorize>
         </c:if>                                       
	</td>
	
	<td>${v[16]}
	<c:if test="${v[16] eq 1}">
         <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
         <c:if test="${empty v[17]}">
           <a href='${pageContext.request.contextPath}/timetable/confirmAppointment?id=${v[0]}&term=${term}&currpage=${page}&courseNumber=${courseNumber}&flag=${v[16]}'>上课确认</a>
         </c:if>
         <c:if test="${not empty v[17]}">
           已上课
         </c:if>
         </sec:authorize>
    </c:if>
	<c:if test="${v[16] eq 2}">
         <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING,ROLE_TEACHER">
         <c:if test="${empty v[17]}">
           <a href='${pageContext.request.contextPath}/timetable/confirmAppointment?id=${v[0]}&term=${term}&currpage=${page}&courseNumber=${courseNumber}&flag=${v[16]}'>上课确认</a>
         </c:if>
         <c:if test="${not empty v[17]}">
           已上课
         </c:if>
         </sec:authorize>
    </c:if>
    </td> 
</tr>
</c:forEach> 
</tbody>
</table>
<div class="page" >
   	 共${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetableAdminIframeConfirm?currpage=1&id=-1&status=${status}&isposted=${isPosted}')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetableAdminIframeConfirm?currpage=${pageModel.previousPage}&id=-1&status=${status}&isposted=${isPosted}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/timetable/timetableAdminIframeConfirm?currpage=${page}&id=-1&status=${status}&isposted=${isPosted}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/timetable/timetableAdminIframeConfirm?currpage=${j.index}&id=-1&status=${status}&isposted=${isPosted}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetableAdminIframeConfirm?currpage=${pageModel.nextPage}&id=-1&status=${status}&isposted=${isPosted}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetableAdminIframeConfirm?currpage=${pageModel.lastPage}&id=-1&status=${status}&isposted=${isPosted}')" target="_self">末页</a>
    </div>


</div>
</div>
</div>
</div>
</div>

<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
var config = {
	'.chzn-select' : {
		search_contains : true
	},
	'.chzn-select-deselect' : {
		allow_single_deselect : true
	},
	'.chzn-select-no-single' : {
		disable_search_threshold : 10
	},
	'.chzn-select-no-results' : {
		no_results_text : 'Oops, nothing found!'
	},
	'.chzn-select-width' : {
		width : "95%"
	}
}
for ( var selector in config) {
	$(selector).chosen(config[selector]);
}</script>
<!-- 下拉框的js -->
