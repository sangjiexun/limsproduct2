<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<head>
<meta name="decorator" content="iframe"/>
<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/css/style.css">
<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/css/iconFont.css">
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
<style>
    .tab_lab th{
        text-align:center;
    }
</style>

<script type="text/javascript">
$(document).ready(function(){
    var s=${status};
    switch(s)
    {
	    case -1:
	    	$("#ss0").addClass("TabbedPanelsTab selected");break;
	    case 10:
	    	$("#ss1").addClass("TabbedPanelsTab selected");break;
	    case 1:
	    	$("#ss2").addClass("TabbedPanelsTab selected");break;
	    default:
	    	$("#ss3").addClass("TabbedPanelsTab selected");
    }
});

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
		groupName=$("#groupName6"+timetableId).text()+","+timetableStyle;// 6--自主排课分组
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
*查看学生循环分组情况
*/
function listTimetableGroupCycle(term,weekday,classids,courseCode,labroom, groupId){
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
            
            result2 = result2 + "<option value=''>所有课程列表 </option>";
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
    .tool-box input[type="radio"] {
        float: left;
    }
</style>
<script type="text/javascript">
	function submitQuery() {
		var str = $('input:radio[name="auditStr"]:checked').val();
		if(str == null) {
			$("#auditTag").val(-1);
			document.form.action="${pageContext.request.contextPath}/timetable/timetablevertify?currpage=1&id=-1&status="+${status };
			document.form.submit();
		}else {
			$("#auditTag").val(str);
			document.form.action="${pageContext.request.contextPath}/timetable/timetablevertify?currpage=1&id=-1&status="+${status };
			document.form.submit();
		}
	}
</script>

</head>
<div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange" /></a></li>
		<li class="end"><a href="javascript:void(0)">实践性教学排课审核及发布</a></li>
	  </ul>
	</div>
  </div>
  <%--<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
	<sec:authorize ifNotGranted="ROLE_TEACHER">
		<li class="TabbedPanelsTab" id="s1"><a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/timetable/listTimetableTerm?currpage=1&id=-1">教务排课推送</a></li>
		</sec:authorize>
		<li class="TabbedPanelsTab" id="s2"><a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/timetable/listGeneralTimetable">课表查询</a>
	        </li>
		<li class="TabbedPanelsTab" id="s3"><a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/timetable/listTimetableTrain?currpage=1&id=-1&flag=0">课内实践申请</a></li>
		<li class="TabbedPanelsTab" id="s4"><a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/timetable/listTimetableTrain?currpage=1&id=-1&flag=1">实训专用周安排</a></li>
		<sec:authorize ifNotGranted="ROLE_TEACHER">
		<li class="TabbedPanelsTab selected" id="s5"><a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/timetable/timetablevertify?currpage=1&id=-1&status=-1">实践性教学排课审核及发布</a></li>
	</sec:authorize>
	</ul>
</div>--%>
<div class="iStyle_RightInner">

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">

<%-- <ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="ss0"><a href="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=-1">全部</a></li>
		<li class="TabbedPanelsTab" id="ss1"><a href="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=10">正在进行中</a></li>
		<li class="TabbedPanelsTab" id="ss2"><a href="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=1">已发布</a></li>
		<li class="TabbedPanelsTab" id="ss3"><a href="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=2">未发布</a></li>
</ul> --%>

<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
    <div class="content-box">
        <%--<div class="title">--%>
            <%--<div id="title">排课管理列表</div>--%>
        <%--</div>--%>
<div class="tool-box" style="overflow:initial">
<form:form name="form" method="Post" action="${pageContext.request.contextPath}/timetable/timetablevertify?currpage=1&id=-1&status=${status }" modelAttribute="timetableAppointment">
<ul>
   <li>
   <select class="chzn-select" id="term" name="term" style="width:180px">
       <c:forEach items="${schoolTerms}" var="current">
    	    <c:if test="${current.id == term}">
    	       <option value ="${current.id}" selected>${current.termName} </option>
    	    </c:if>
    	    <c:if test="${current.id != term}">
    	       <option value ="${current.id}" >${current.termName} </option>
    	    </c:if>		
        </c:forEach>
    </select>
   </li>
   <li>
   <form:select class="chzn-select"  path="schoolCourseInfo.courseNumber" id="schoolCourseInfo_courseNumber" cssStyle="width:650px" >
	  <form:option value=""	label="所有课程列表  " />
	   <c:forEach items="${schoolCoursess}" var="cur"  varStatus="i">	
	      <!--添加相关排课的授课老师查询项username，cname  -->
	      <c:if test="${cur.courseNumber ne  courseNumber}">
	      <form:option value ="${cur.courseNumber}" label="课程名称：${cur.courseName}  " />
	      </c:if>
	      <c:if test="${cur.courseNumber eq  courseNumber}">
	      <form:option value ="${cur.courseNumber}" selected="true" label="课程名称：${cur.courseName} " />
	      </c:if>
	  </c:forEach>
   </form:select>&nbsp;
   </li>
   <li>
   		<c:if test="${empty auditStr}"><input type="radio" name="auditStr" value="" checked="checked" /></c:if>
   		<c:if test="${!empty auditStr}"><input type="radio" name="auditStr" value="" /></c:if>全部</li>
   <li><c:if test="${auditStr eq 1}"><input type="radio" name="auditStr" value="1" checked="checked" /></c:if>
   		<c:if test="${auditStr ne 1}"><input type="radio" name="auditStr" value="1" /></c:if>已发布</li>
   <li><c:if test="${auditStr eq 2}"><input type="radio" name="auditStr" value="2" checked="checked" /></c:if>
   		<c:if test="${auditStr ne 2}"><input type="radio" name="auditStr" value="2" /></c:if>待审核</li>
   		<li><c:if test="${auditStr eq 3}"><input type="radio" name="auditStr" value="3" checked="checked" /></c:if>
   		<c:if test="${auditStr ne 3}"><input type="radio" name="auditStr" value="3" /></c:if>审核中</li>
   		<li><c:if test="${auditStr eq 4}"><input type="radio" name="auditStr" value="4" checked="checked" /></c:if>
   		<c:if test="${auditStr ne 4}"><input type="radio" name="auditStr" value="4" /></c:if>已拒绝</li>
   <li><input type="button" onclick="submitQuery(); return false;" value="查询" id="search" /> </li> 
   <li> <a href="${pageContext.request.contextPath}/timetable/timetablevertify?currpage=1&status=${status }&id=-1"><input type="button" value="取消查询"></a></li>  
   </ul>

</form:form>
</div>
<table class="tab_lab">
<thead>
<tr>
   <th width="30px;">序号</th>
   <th width="70px;">选课组编号</th>
   <th width="70px;">课程名称</th>
   <th width="50px;">批次</th> 
   <th width="130px;">实验项目名称</th>
   <th width="30px;">星期</th>
   <th width="45px;">节次</th>
   <th width="45px;">周次</th>
   <th width="60px;">上课教师</th>
   <th width="60px;">指导教师</th>
   <th><spring:message code="all.trainingRoom.labroom" /></th>
   <th width="10%;">学生名单</th>
   <th width="10%;">排课方式</th>
   <sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN">
   <th colspan=2 width="10%;">操作</th>
   </sec:authorize>
</tr>
</thead>
<tbody>
<!-- 判断相同选课组的当前选课组变量 -->
<c:set var="ifRowspan" value="0"></c:set>
<!-- 判断相同批次的当前批次id -->
<c:set var="ifRowspanBatch" value="0"></c:set>

<c:set var="count" value="1"></c:set>
<c:forEach items="${timetableAppointments}" var="current"  varStatus="i">
<!--判断是否仅为教师，如果是则只能查看自己排的课  -->
	
<c:set var="rowspan" value="0"></c:set>
<!--合并相同批次的变量  -->
<c:set var="rowspanBatch" value="0"></c:set>
<tr>
    <!--合并相同选课组的变量计数  -->
    <c:forEach items="${timetableAppointments}" var="current1"  varStatus="j">
         <c:if test="${current1.courseCode==current.courseCode&&current.timetableStyle==current1.timetableStyle }">
            <c:set value="${rowspan + 1}" var="rowspan" />
         </c:if>
     </c:forEach>
     
    <!--合并相同批次的变量计数  -->
     <c:forEach items="${timetableAppointments}" var="current1"  varStatus="j">
         <c:if test="${!current.timetableGroups.isEmpty()&&!current1.timetableGroups.isEmpty() }">
         <c:if test="${current1.timetableGroups.iterator().next().timetableBatch.id==current.timetableGroups.iterator().next().timetableBatch.id }">
            <c:set value="${rowspanBatch + 1}" var="rowspanBatch" />
         </c:if>
         </c:if>
         <c:if test="${current.timetableStyle!=4&&current.timetableStyle!=6 }">
               <c:set value="0" var="rowspanBatch" />
         </c:if>
     </c:forEach> 
     <%-- <c:if test="${rowspan>1&&ifRowspan!=current.courseCode }">
          <c:if test="${current.schoolCourse.state!=0||current.timetableStyle==5||current.timetableStyle==6 }">
          <td  rowspan="${rowspan }"><input type='checkbox' name='VoteOption1' value=1></td>  
          </c:if>
          <c:if test="${current.timetableStyle!=5&&current.timetableStyle!=6&&current.schoolCourse.state==0 }">
          <td  rowspan="${rowspan }"  style="background:red"><input type='checkbox' name='VoteOption1' value=1></td>  
          </c:if>
     </c:if> --%>

     <!--选课组多条排课记录，显示序号  -->
     <c:if test="${rowspan>1&&ifRowspan!=current.courseCode }">
          <c:if test="${current.schoolCourse.state!=0||current.timetableStyle==5||current.timetableStyle==6 }">
          <td  rowspan="${rowspan }">${count}</td>  
          </c:if>
          <c:if test="${current.timetableStyle!=5&&current.timetableStyle!=6&&current.schoolCourse.state==0 }">
          <td  rowspan="${rowspan }"  style="background:red">${count}</td>  
          </c:if>
          <c:set var="count" value="${count+1}"></c:set>
     </c:if>
     <!--选课组单条排课记录，显示序号 -->
     <c:if test="${rowspan==1 }">
          <c:if test="${current.schoolCourse.state!=0||current.timetableStyle==5||current.timetableStyle==6 }">
          <td>${count}</td>  
          </c:if>
          <c:if test="${current.timetableStyle!=5&&current.timetableStyle!=6&&current.schoolCourse.state==0 }">
          <td style="background:red">${count}</td>  
          </c:if>
         <c:set var="count" value="${count+1}"></c:set>
     </c:if>
     <!--选课组多条排课记录，选课组编号 -->
     <c:if test="${rowspan>1&&ifRowspan!=current.courseCode }">
         <c:if test="${current.schoolCourse.state!=0||current.timetableStyle==5||current.timetableStyle==6 }">
          <c:set var="ifRowspanBatch" value="0"></c:set>
          
          <td  rowspan="${rowspan }">${current.courseCode}</td> 
          <!--对应课程名称  -->
          <td rowspan="${rowspan }">${current.schoolCourseInfo.courseName}
          <br>
           	 <c:if test="${current.timetableStyle==4}">
				<a  href='javascript:void(0)' onclick='listTimetableStudents("${current.schoolCourseDetail.courseDetailNo }")'>学生名单</a>
				<a class="btn" href='javascript:void(0)' onclick='manageBatch("${current.schoolCourseDetail.courseDetailNo }")'>分批管理</a>
		     </c:if>
		     <!--自主排课学生名单  -->
		     <c:if test="${current.timetableStyle==6}">zzzzz
				<a href='javascript:void(0)' onclick='listTimetableSelfStudents("${current.timetableSelfCourse.id }")'>学生名单</a>
				<a class="btn" href='javascript:void(0)' onclick='manageBatchSelf("${current.timetableSelfCourse.id }")'>分批管理</a>
		     </c:if>
          </td>   
          </c:if>
          <c:if test="${current.timetableStyle!=5&&current.timetableStyle!=6&&current.schoolCourse.state==0 }">
          <td  rowspan="${rowspan }"  style="background:red">${current.courseCode}</td>  
          <!--对应课程名称  -->
          <td rowspan="${rowspan }">${current.schoolCourseInfo.courseName}</td>  
          </c:if>
     </c:if>
     
     <!--选课组单条排课记录，选课组编号 -->
     <c:if test="${rowspan==1 }">
          <c:if test="${current.schoolCourse.state!=0||current.timetableStyle==5||current.timetableStyle==6 }">
           <td>${current.courseCode}</td>  
          <!--对应课程名称  -->
          <td>${current.schoolCourseInfo.courseName}
          <br>
           	 <c:if test="${current.timetableStyle==4}">
				<a  href='javascript:void(0)' onclick='listTimetableStudents("${current.schoolCourseDetail.courseDetailNo }")'>学生名单</a>
				<a class="btn" href='javascript:void(0)' onclick='manageBatch("${current.schoolCourseDetail.courseDetailNo }")'>分批管理</a>
		     </c:if>
		     <!--自主排课学生名单  -->
		     <c:if test="${current.timetableStyle==6}">
				<a href='javascript:void(0)' onclick='listTimetableSelfStudents("${current.timetableSelfCourse.id }")'>学生名单</a>
		     </c:if>
          </td>    
          </c:if>
          <!--处理教务基础数据变更  -->
          <c:if test="${current.timetableStyle!=5&&current.timetableStyle!=6&&current.schoolCourse.state==0 }">
          <td style="background:red">${current.courseCode}</td>
          <!--对应课程名称  -->
          <td>${current.schoolCourseInfo.courseName}</td>    
          </c:if>
     </c:if>
     
     <c:if test="${rowspanBatch==0}">
     <td>
          <c:if test="${current.timetableStyle!=4&&current.timetableStyle!=6}">
                                      不分批
          </c:if>
          <c:if test="${current.timetableStyle==4||current.timetableStyle==6}">
            <c:if test="${!current.timetableGroups.isEmpty()}">
                ${current.timetableGroups.iterator().next().timetableBatch.batchName}
            </c:if>
            <c:if test="${current.timetableGroups.isEmpty()}">
                <font color=red>分组信息已丢失，请删除排课记录，重新排课</font>
            </c:if>
          </c:if>
          
     </td>
     </c:if>
     
     <c:if test="${rowspanBatch>0&&ifRowspanBatch!=current.timetableGroups.iterator().next().timetableBatch.id }">
     <td rowspan="${rowspanBatch }">
          <c:if test="${current.timetableStyle!=4&&current.timetableStyle!=6 }">
                                      不分批
          </c:if>
          <c:if test="${!current.timetableGroups.isEmpty() }">
             ${current.timetableGroups.iterator().next().timetableBatch.batchName} 
          </c:if>
     </td>
     <c:set var="ifRowspanBatch" value="${current.timetableGroups.iterator().next().timetableBatch.id }"></c:set>
     </c:if> 
     
     <!--对应实验项目  -->
     <td>
        <c:forEach var="entry" items="${current.timetableItemRelateds}">  
           <c:if test="${not empty entry.operationItem}">
           <c:if test="${entry.operationItem.id!=0}">
              <c:out value="${entry.operationItem.lpName}" /><br>
           </c:if>
           <c:if test="${entry.operationItem.id==0}">
              ${current.schoolCourseInfo.courseName}(课程名称)&nbsp;
           </c:if>
           </c:if>
           <c:if test="${empty entry.operationItem}">
              ${current.schoolCourseInfo.courseName}(课程名称)&nbsp;
           </c:if>
        </c:forEach>
        <c:if test="${current.timetableItemRelateds.size()==0}">
           ${current.schoolCourseInfo.courseName}(课程名称)
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
         <c:forEach var="entry" items="${current.timetableAppointmentSameNumbers}"   varStatus="p"> 
         <c:set var="v_param" value="-${entry.startClass}-" ></c:set>   
         <c:if test="${fn:indexOf(sameStart,v_param)<0}">
         <td width="45px;">
             <c:if test="${entry.startClass==entry.endClass}">
             ${entry.startClass }
             </c:if>
             <c:if test="${entry.startClass!=entry.endClass}">
             ${entry.startClass }-${entry.endClass }
             </c:if>
         </td>
         <td width="45px;">
         <c:set var="sameStart" value="${sameStart}-${entry.startClass }-"></c:set>
         <c:forEach var="entry1" items="${current.timetableAppointmentSameNumbers}"  varStatus="q">  
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
         </c:if>
         </c:forEach>
          
     </c:if>


     <!--上课教师  -->
     <td>
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
     <label title="<c:out value="${entry.labRoom.labRoomName}" />"><c:out value="${entry.labRoom.labRoomName}" /></label>
     	<a target="_blank" href="${pageContext.request.contextPath}/timetable/listTeachingLabRoomDevice?timetableId=${current.id}&labRoomId=${entry.labRoom.id}">查看教学用设备</a>	
     <br>  
     </c:forEach>
     </td>

     <td>
     <!--二次排课学生名单  -->
     <c:if test="${current.timetableStyle!=4&&current.timetableStyle!=6&&current.timetableStyle!=5&&current.timetableStyle!=9}">
         <a href='javascript:void(0)' onclick='listTimetableStudents("${current.schoolCourseDetail.courseDetailNo }")'>
         <c:set value="0" var="counta" />
		<c:forEach items="${current.schoolCourseDetail.schoolCourseStudents}" var="schoolCourseStudentSets" varStatus="k">
		    <c:if test="${schoolCourseStudentSets.state==1 }">
		       <c:set value="${counta+1 }" var="counta" />
		    </c:if>
		</c:forEach>${counta} 
         <%-- ${current.schoolCourseDetail.schoolCourseStudents.size()} --%></a>
     </c:if>
     <c:if test="${current.timetableStyle==5}">
         ${current.timetableSelfCourse.timetableCourseStudents.size()}
     </c:if> 
     <c:if test="${current.timetableStyle==4||current.timetableStyle==9||current.timetableStyle==10}">
         <c:forEach items="${current.timetableGroups}" var="varGroup">
              ${varGroup.timetableGroupStudentses.size()}/${varGroup.numbers}<br>
              <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
              <a  href='javascript:void(0)' onclick='listTimetableStudentsGroup("${varGroup.id}","${current.id}","${current.timetableStyle}")'>已选学生名单</a>
              </sec:authorize>
              <c:if test="${varGroup.timetableBatch.ifselect==0}"><!-- 自动选课 -->
             </c:if>
          </c:forEach>         
          
     </c:if>
     <!--自主排课学生名单  -->
     <c:if test="${current.timetableStyle==6}">
         <c:forEach items="${current.timetableGroups}" var="varGroup">
               ${varGroup.timetableGroupStudentses.size()}/${varGroup.numbers}<br>
               <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
               <a href='javascript:void(0)' onclick='listTimetableStudentsGroup("${varGroup.id}","${current.id}",6)'>已选学生名单</a>
              </sec:authorize>
         </c:forEach>         
     </c:if>
     </td>
     <!-- 排课方式-->
     <td id="timetableStyle${current.id}">
        <c:if test="${current.timetableStyle==1}">
              <font color=green>教务直接排课<br></font>
        </c:if>
        <c:if test="${current.timetableStyle==2}">
                           实践性教学排课<br>
        </c:if>
        <c:if test="${current.timetableStyle==3}">
                          二次不分组排课<br>
        </c:if>
        <c:if test="${current.timetableStyle==4}">
                          二次分组排课<br>;
             <c:forEach items="${current.timetableGroups}" var="varGroup">
              <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
              <a id="groupName4${current.id}" href='javascript:void(0)' onclick='listTimetableGroup("${current.schoolCourseDetail.schoolTerm.id}","${current.weekday}","1","${current.courseCode}","-1","${varGroup.id}")'>${varGroup.timetableBatch.batchName}/${varGroup.groupName} </a><br>
              </sec:authorize>
               <sec:authorize  ifNotGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
              <a id="groupName4${current.id}" href='javascript:void(0)' onclick='listTimetableGroup("${current.schoolCourseDetail.schoolTerm.id}","${current.weekday}","1","${current.courseCode}","-1","${varGroup.id}")'>${varGroup.timetableBatch.batchName}/${varGroup.groupName} </a><br>
              </sec:authorize>
             <c:if test="${varGroup.timetableBatch.ifselect==0}">
                                           自动选课<br>
             </c:if>
             <c:if test="${varGroup.timetableBatch.ifselect==1}">
                                           学生选课<br>
             </c:if>        
             </c:forEach>         
                               
        </c:if>
        <c:if test="${current.timetableStyle==5}">
                           自主排课<br>
        </c:if>
        <c:if test="${current.timetableStyle==6}">
                          自主分组排课;<br>
             <c:forEach items="${current.timetableGroups}" var="varGroup">
              <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
              <a id="groupName6${current.id}" href='javascript:void(0)' onclick='listTimetableSelfGroup("${current.timetableSelfCourse.schoolTerm.id}","${current.weekday}","1","${current.timetableSelfCourse.id}","-1")'>${varGroup.timetableBatch.batchName}/${varGroup.groupName}</a><br>
              </sec:authorize>
              <sec:authorize  ifNotGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
              <a id="groupName6${current.id}" href='javascript:void(0)'  >${varGroup.timetableBatch.batchName}/${varGroup.groupName}</a><br>
              </sec:authorize>
             <c:if test="${varGroup.timetableBatch.ifselect==0}">
                                           自动选课<br>
             </c:if>
             <c:if test="${varGroup.timetableBatch.ifselect==1}">
                                           学生选课<br>
             </c:if>        
             </c:forEach>         
                               
        </c:if>
         <c:if test="${current.timetableStyle==7}">
             实验室预约排课<br>
         </c:if>
         <c:if test="${current.timetableStyle==9}">
             循环排课<br>
             <c:forEach items="${current.timetableGroups}" var="varGroup">
                 <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
                     <a id="groupName9${current.id}" href='javascript:void(0)' onclick='listTimetableGroupCycle("${current.schoolCourseDetail.schoolTerm.id}","${current.weekday}","1","${current.courseCode}","-1","${varGroup.id}")'>${varGroup.groupName} </a><br>
                 </sec:authorize>
                 <sec:authorize  ifNotGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
                     <a id="groupName9${current.id}" href='javascript:void(0)' onclick='listTimetableGroupCycle("${current.schoolCourseDetail.schoolTerm.id}","${current.weekday}","1","${current.courseCode}","-1","${varGroup.id}")'>${varGroup.groupName} </a><br>
                 </sec:authorize>
             </c:forEach>
         </c:if>
         <c:if test="${current.timetableStyle==10}">
             循环分批排课<br>
             <c:forEach items="${current.timetableGroups}" var="varGroup">
                 <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
                     <a id="groupName4${current.id}" href='javascript:void(0)' onclick='listTimetableGroupCycle("${current.schoolCourseDetail.schoolTerm.id}","${current.weekday}","1","${current.courseCode}","-1","${varGroup.id}")'>${varGroup.timetableBatch.batchName}/${varGroup.groupName} </a><br>
                 </sec:authorize>
                 <sec:authorize  ifNotGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
                     <a id="groupName4${current.id}" href='javascript:void(0)' onclick='listTimetableGroupCycle("${current.schoolCourseDetail.schoolTerm.id}","${current.weekday}","1","${current.courseCode}","-1","${varGroup.id}")'>${varGroup.timetableBatch.batchName}/${varGroup.groupName} </a><br>
                 </sec:authorize>
             </c:forEach>
         </c:if>
        <c:if test="${current.originalTapid!=null}">
      <%--   <c:forEach items="${originaltap}" var="cur"> --%>
        (由${current.originalTapid}调来)
       <%--  </c:forEach> --%>
        </c:if>
     </td>
     <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
         <c:if test="${current.schoolCourse.state!=0}">
         <td>
         <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
           <a onclick="sureDelete()"  href='${pageContext.request.contextPath}/timetable/deleteAppointment?id=${current.id }&term=${term}&currpage=${page}&courseNumber=${courseNumber}'>删除</a><br>
		   <!--如果已发布  -->
		   <c:if test="${current.status==10}">
		   <c:if test="${current.timetableStyle==2}">
		   <a  href='javascript:void(0)' onclick='doUpdateTimetable2("${current.schoolCourse.courseCode }",${current.id })'>编辑</a>
           </c:if>
		   <c:if test="${current.timetableStyle==3}">
		   <a  href='javascript:void(0)' onclick='doUpdateTimetable3("${current.id }")'>编辑</a><br>
           </c:if>
           <c:if test="${current.timetableStyle==4}">
		   <a  href='javascript:void(0)' onclick='doUpdateTimetable4("${current.schoolCourse.courseCode }","${current.id }")'>编辑</a><br>
           </c:if>
		   <c:if test="${current.timetableStyle==5}">
		   <a  href='javascript:void(0)' onclick='doUpdateTimetable5("${current.timetableSelfCourse.id }",${current.id })'>编辑</a>
           </c:if>
           <c:if test="${current.timetableStyle==6}">
		   <a  href='javascript:void(0)' onclick='doUpdateTimetable6("${current.timetableSelfCourse.id }","${current.id }")'>编辑</a><br>
           </c:if>
           </c:if>
         </sec:authorize>
         </td>
         </c:if>
         <c:if test="${current.schoolCourse.state==0 }">
         <td style="background:red">
         <sec:authorize  ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING">
           <a onclick="sureDelete()" href='${pageContext.request.contextPath}/timetable/deleteAppointment?id=${current.id }&term=${term}&currpage=${page}&courseNumber=${courseNumber}'>删除</a><br>
		       <font color=write><b>  注意(*)：教务计划已变化，<br>请删除本次记录，重新排课</b></font>
         </sec:authorize>
         </td>
         </c:if>
         </sec:authorize>
     
     <c:if test="${rowspan>1&&ifRowspan!=current.courseCode }">
          <td rowspan="${rowspan }">
          <c:if test="${current.status==10 }">
                          进行中<br>
           <c:if test="${current.timetableStyle==2}">
		   <a onclick="history.go(0)" href='${pageContext.request.contextPath}/timetable/doJudgeTimetableOkFromAdmin?courseCode=${current.courseCode}&currpage=${page}'>课程计划完成</a><br>
           </c:if>
           <c:if test="${current.timetableStyle==3}">
		   <a onclick="history.go(0)" href='${pageContext.request.contextPath}/timetable/doNoGroupTimetableOkFromAdmin?term=${term}&courseCode=${current.courseCode}' >课程计划完成</a><br>
           </c:if>
           <c:if test="${current.timetableStyle==4&&current.timetableGroups.iterator().hasNext()}">
		   <a onclick="history.go(0)" href='${pageContext.request.contextPath}/timetable/doReGroupTimetableOkFromAdmin?term=${term}&courseCode=${current.courseCode}&batchId=${current.timetableGroups.iterator().next().timetableBatch.id}'>课程计划完成</a><br>
           </c:if>
           <c:if test="${current.timetableStyle==5}">
		   <a onclick="history.go(0)" href='${pageContext.request.contextPath}/timetable/selfTimetable/doSelfNOGroupTimetableOkFromAdmin?term=${term}&courseCode=${current.courseCode}'>课程计划完成</a><br>
           </c:if>
           <c:if test="${current.timetableStyle==6&&current.timetableGroups.iterator().hasNext()}">
		   <a onclick="history.go(0)" href='${pageContext.request.contextPath}/timetable/selfTimetable/doSelfGroupTimetableOkFromAdmin?term=${term}&courseCode=${current.courseCode}&batchId=${current.timetableGroups.iterator().next().timetableBatch.id}'>课程计划完成</a><br>
           </c:if>
          </c:if>
          <c:if test="${current.status==1 }">
                              已发布
          </c:if>
          <c:if test="${fn:contains('zjcclims',PROJECT_NAME)}"><!-- 浙江建设需要二级审核 -->
              <c:if test="${current.status==2 }">
                  <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_DEPARTMENTHEADER'}">
                      <%-- <a href="${pageContext.request.contextPath}/timetable/doReleaseTimetable?courseCode=${current.courseCode }&flag=${current.timetableStyle }&term=${term }&courseNumber=${courseNumber}" target="_self">审核</a> --%>
                      <a href="javascript:void(0)" onclick="timetableAppAudit('${current.courseCode}',${current.timetableStyle},${current.status},${current.timetableLabRelateds},1)" >审核</a>
                  </c:if>
              </c:if>
              <c:if test="${current.status==3 }">
                  <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
                      <a href="javascript:void(0)" onclick="timetableAppAudit('${current.courseCode}',${current.timetableStyle},${current.status},${current.timetableLabRelateds},2)" >审核</a>
                  </c:if>
                  <c:forEach items="${labRoomAdmin}" var="currr">
                      <c:if test="${currr.key==current.id}">
                          <c:if test="${currr.value eq user.username}">
                              <a href="javascript:void(0)" onclick="timetableAppAudit('${current.courseCode}',${current.timetableStyle},${current.status},${current.timetableLabRelateds},2)" >审核</a>
                          </c:if>
                      </c:if>
                  </c:forEach>
              </c:if>
          </c:if>
          <c:if test="${!fn:contains('zjcclims',PROJECT_NAME)}"><!-- 一级审核 -->
              <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
                  <c:if test="${current.status!=1 && current.status!=4}"><!-- 未审核 -->
                    <a href="javascript:void(0)" onclick="timetableAppAudit('${current.courseCode}',${current.timetableStyle},${current.status},${current.timetableLabRelateds},2)" >审核</a>
                  </c:if>
              </c:if>
          </c:if>
              <c:if test="${current.status==4 }">
                  拒绝
              </c:if>
          </td>
    <c:if test="${fn:contains('zjcclims',PROJECT_NAME)}">
         <td rowspan="8">
             <c:forEach items="${allresults}" var="cu">
                 <c:forEach items="${current.timetableLabRelateds}" var="ccc">
                 <c:if test="${cu.coursecode eq current.courseCode&&cu.labroomId==ccc.labRoom.id}">
                 <p>${cu.user}</p>
                 <a>
                     <c:if test="${cu.auditResult eq '1'}">通过</c:if>
                     <c:if test="${cu.auditResult eq '2'}">拒绝</c:if>
                 </a>
                 <input type="text" style="width:80px;" value="${cu.remark}">
                 </c:if>
                 </c:forEach>
             </c:forEach>
         </td>
    </c:if>
    <c:set var="ifRowspan" value="${current.courseCode }"></c:set>
    </c:if>

     <c:if test="${rowspan==1 }">
         <td>
          <c:if test="${current.status==10 }">
                      进行中<br>
           <c:if test="${current.timetableStyle==2}">
		   <a onclick="history.go(0)" href='${pageContext.request.contextPath}/timetable/doJudgeTimetableOkFromAdmin?courseCode=${current.courseCode}&currpage=1'>课程计划完成</a><br>
           </c:if>
           <c:if test="${current.timetableStyle==3}">
		   <a onclick="history.go(0)" href='${pageContext.request.contextPath}/timetable/doNoGroupTimetableOkFromAdmin?term=${term}&courseCode=${current.courseCode}' >课程计划完成</a><br>
           </c:if>
           <c:if test="${current.timetableStyle==4&&current.timetableGroups.iterator().hasNext()}">
		   <a onclick="history.go(0)" href='${pageContext.request.contextPath}/timetable/doReGroupTimetableOkFromAdmin?term=${term}&courseCode=${current.courseCode}&batchId=${current.timetableGroups.iterator().next().timetableBatch.id}'>课程计划完成</a><br>
           </c:if>
           <c:if test="${current.timetableStyle==5}">
		   <a onclick="history.go(0)" href='${pageContext.request.contextPath}/timetable/selfTimetable/doSelfNOGroupTimetableOkFromAdmin?term=${term}&courseCode=${current.courseCode}'>课程计划完成</a><br>
           </c:if>
           <c:if test="${current.timetableStyle==6&&current.timetableGroups.iterator().hasNext()}">
		   <a onclick="history.go(0)" href='${pageContext.request.contextPath}/timetable/selfTimetable/doSelfGroupTimetableOkFromAdmin?term=${term}&courseCode=${current.courseCode}&batchId=${current.timetableGroups.iterator().next().timetableBatch.id}'>课程计划完成</a><br>
           </c:if>
          </c:if>
          <c:if test="${current.status==1 }">
                              已发布
          </c:if>
          <c:if test="${fn:contains('zjcclims',PROJECT_NAME)}">
              <c:if test="${current.status==2 }">
                  <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_DEPARTMENTHEADER'}">
                      <%-- <a href="${pageContext.request.contextPath}/timetable/doReleaseTimetable?courseCode=${current.courseCode }&flag=${current.timetableStyle }&term=${term }&courseNumber=${courseNumber}" target="_self">审核</a> --%>
                      <a href="javascript:void(0)" onclick="timetableAppAudit('${current.courseCode}',${current.timetableStyle},${current.status},${current.timetableLabRelateds},1)" >审核</a>
                  </c:if>
              </c:if>
              <c:if test="${current.status==3 }">
                  <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
                    <a href="javascript:void(0)" onclick="timetableAppAudit('${current.courseCode}',${current.timetableStyle},${current.status},${current.timetableLabRelateds},2)" >审核</a>
                  </c:if>
                  <c:forEach items="${labRoomAdmin}" var="currr">
                  <c:if test="${currr.key==current.id}">
                  <c:if test="${currr.value eq user.username}">
                      <a href="javascript:void(0)" onclick="timetableAppAudit('${current.courseCode}',${current.timetableStyle},${current.status},${current.timetableLabRelateds},2)" >审核</a>
                  </c:if>
                  </c:if>
                  </c:forEach>
              </c:if>
            </c:if>
            <c:if test="${!fn:contains('zjcclims',PROJECT_NAME)}"><!-- 一级审核 -->
                <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
                    <c:if test="${current.status!=1 && current.status!=4}"><!-- 未审核 -->
                        <a href="javascript:void(0)" onclick="timetableAppAudit('${current.courseCode}',${current.timetableStyle},${current.status},${current.timetableLabRelateds},2)" >审核</a>
                    </c:if>
                </c:if>
            </c:if>
             <c:if test="${current.status==4 }">
                 拒绝
             </c:if>
        </td>
              <c:if test="${fn:contains('zjcclims',PROJECT_NAME)}">
                  <td >
                <c:forEach items="${allresults}" var="cu">
                  <c:forEach items="${current.timetableLabRelateds}" var="ccc">
                 <c:if test="${cu.coursecode eq current.courseCode&&cu.labroomId==ccc.labRoom.id}">
                 <p>${cu.user}</p>
                 <a>
                 <c:if test="${cu.auditResult eq '1'}">
                 通过
                 </c:if>
                            <c:if test="${cu.auditResult eq '2'}">
                 拒绝
                 </c:if>
                 </a>
                 <input type="text" style="width:80px;" value="${cu.remark}">
                 </c:if>
                 </c:forEach>
                </c:forEach>
                  </td>
              </c:if>

    </c:if>
</tr>
</c:forEach> 
</tbody>
</table>
<div class="page" >
   	 共${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetablevertify?currpage=1&id=-1&status=${status}')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetablevertify?currpage=${pageModel.previousPage}&id=-1&status=${status}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/timetable/timetablevertify?currpage=${page}&id=-1&status=${status}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/timetable/timetablevertify?currpage=${j.index}&id=-1&status=${status}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetablevertify?currpage=${pageModel.nextPage}&id=-1&status=${status}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetablevertify?currpage=${pageModel.lastPage}&id=-1&status=${status}')" target="_self">末页</a>
    </div>


</div>
</div>
</div>
</div>
</div>

<div id="searchFile" class="easyui-window" title="直接排课" closed="true" iconCls="icon-add" style="width:850px;height:450px">
<br>
<form:form id="form_lab" action="" method="post"  modelAttribute="timetableAppointment">
当前学期：${schoolTerm.termName} 当前周次：${week }
<!-- schoolCourseDetail的no -->
<br>
<br>
<br>
<hr>
<input type="submit" value="确定">
</form:form>
</div>

<!-- 修改排课信息弹窗 -->
<div id="doUpdateTimetable" class="easyui-window" title="修改排课信息" closed="true"  modal="true" iconCls="icon-add" style="width:1050px;height:500px">
</div>


<!-- 查看学生名单 -->
<div id="doSearchStudents" class="easyui-window" title="查看学生名单" closed="true"  modal="true" iconCls="icon-add" style="width:1000px;height:500px">
</div>

<!-- 分批管理--针对二次排课分批 -->
<div id="manageBatch" class="easyui-window" title="分批管理" closed="true"  modal="true" iconCls="icon-add" style="width:1000px;height:500px">
</div>

<!-- 查看学生名单 -->
<div id="doSearchGroup" class="easyui-window" title="查看学生分组" closed="true"  modal="true" iconCls="icon-add" style="width:1000px;height:500px">
</div>

<!-- 审核 -->
<div id="timetableAppAudit" class="easyui-window" title="排课审核" closed="true" modal="true" iconCls="icon-add" style="width:400px;height:120px">
	<form name="auditForm" action="${pageContext.request.contextPath}/timetable/doReleaseTimetable?courseCode=${current.courseCode }&flag=${current.timetableStyle }" method="post" target="_parent" >
	   	<table>
	        <tr>
	           	<td>审核：</td>
	           	<td colspan="4">
	           		<input type="radio" name="auditResult" value="1" checked="true"/>通过
	           		<input type="radio" name="auditResult" value="0" />拒绝
	            </td>
	       	</tr>
            <tr>
                <td>
                    	审核意见:
                </td>
                <td colspan="5">
                    <input type="text" name="remark" />
                </td>
            </tr>
	        <tr>
	           	<td colspan="6"><input type="button" onclick="submitAudit(); return false;" value="提交"> </td>
	       	</tr>
	 	</table>
	</form>
</div>

<script type="text/javascript">
	var courseCode='';
	var appStyle=0;
	var status=-1;
	var labrelate= 0;
	var tag = -1;
	function timetableAppAudit(courseNo, flag, stat,labrooms,tage) {
	for(var m of labrooms ){
	  labrelate = m;
	}
		courseCode = courseNo;
		appStyle = flag;
		status = stat;
		tag = tage;
		$('#timetableAppAudit').window('open');
	}
	
	// 保存审核结果
	function submitAudit() {
		document.auditForm.action="${pageContext.request.contextPath}/timetable/saveTimetableAudit?courseCode="+courseCode+"&appStyle="+appStyle+"&status="+status+"&labrelate="+labrelate+"&tag="+tag+"&page=${page}";
		document.auditForm.submit();
	}
</script>

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
