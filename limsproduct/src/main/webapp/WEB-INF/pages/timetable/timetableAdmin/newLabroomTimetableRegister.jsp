<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
   <!-- 下拉框的样式 -->
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 --> 
  <script type="text/javascript">

	$(function(){
		//alert(state);
		$("#c li").eq(0).css("padding","4px 10px");
		$("#c li").eq(0).css("background-color","#FFF");
		$("#c li").eq(0).css("border-bottom","1px solid #FFF");
		$("#c li").eq(0).css("color","#999");
	});
</script>

<script type="text/javascript">
 
  </script>
  <style type="text/css">
  	input[type='text']{
  		height:30px;
  		line-height:30px;
  	}
  </style>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)"><spring:message code="left.training.record" /></a></li>
		<li class="end"><a href="javascript:void(0)">新建<spring:message code="all.trainingRoom.labroom"/>使用登记</a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
	  <div id="title">新建<spring:message code="all.trainingRoom.labroom" />使用登记</div>
	</div>
	
	<form:form id="softForm" action="${pageContext.request.contextPath}/timetable/saveLabroomTimetableRegister" onsubmit="return checkFrom();"  method="POST" modelAttribute="labroomTimetableRegister">
    	<table class="tb" id="table" >
 		<tr>
           	<td><spring:message code="all.trainingRoom.labroom" />:</td>
           	<td>
    			<form:select id="labRoomId" class="chzn-select"  cssStyle="width:170px;" path="labRoom.id">
    			<form:options items="${labList}" itemLabel="labRoomName" itemValue="id"/>
  				</form:select>
            </td>
            <td>课程名称:</td>
            <td>
            <form:select id="courseNumber" class="chzn-select"  cssStyle="width:170px;" path="schoolCourseInfo.courseNumber">
            <form:options items="${schoolCourses}" itemLabel="courseName" itemValue="schoolCourseInfo.courseNumber"/>
  				</form:select>
            </td>
       </tr>
		<tr>
        	<td>实训项目:</td>
	     	<td>
	    		<form:select id="teacher" class="chzn-select"  cssStyle="width:170px;" path="programName">
	    		<form:options items="${schoolCourses}" itemLabel="courseName" itemValue="courseName"/>
	    		<form:options items="${operationItems}" itemLabel="lpName" itemValue="lpName"/>
   				</form:select>
	      	</td>
	      	<td>上课班级:</td>
        	<td >
   			<form:select  class="chzn-select"   path="classNumber" > 
	    		<form:options items="${schoolClasses}" itemLabel="className" itemValue="className"/>
 			</form:select>
        	</td>
	    </tr>
	    <tr>
           	<td>上课教师:</td>
           	<td>
    			<form:select id="teacher" class="chzn-select"  cssStyle="width:170px;" path="teacher">
	    		<form:options items="${teachers}" itemLabel="cname" itemValue="cname"/>
  				</form:select>
            </td>
            <td>上课星期:</td>
            <td>
            <form:select id="weekday"   cssStyle="width:170px;" path="weekday">
  			<c:forEach var="x" begin="1" end="7" step="1"> 
				       <form:option value="${x }">星期${x }</form:option>
				</c:forEach>
  			</form:select>
            </td>
       </tr>
       <tr>
           	<td>开始节次:</td>
           	<td>
    			<form:select id="startClass"   cssStyle="width:170px;" path="startClass">
  				<c:forEach var="x" begin="1" end="12" step="1"> 
				       <form:option value="${x }">第${x }节</form:option>
				</c:forEach>
  				</form:select>
            </td>
            <td>结束节次:</td>
            <td>
            <form:select id="endClass"   cssStyle="width:170px;" path="endClass">
				<c:forEach var="x" begin="1" end="12" step="1"> 
				       <form:option value="${x }">第${x }节</form:option>
				</c:forEach>
  			</form:select>
            </td>
       </tr>
        <tr>
           	<td>开始周次:</td>
           	<td>
    			<form:select id="startWeek"   cssStyle="width:170px;" path="startWeek">
	    		<form:options items="${weeks}" itemLabel="week" itemValue="week"/>
  				</form:select>
            </td>
            <td>结束周次:</td>
            <td>
            <form:select id="endWeek"   cssStyle="width:170px;" path="endWeek">
  				<form:options items="${weeks}" itemLabel="week" itemValue="week"/>
  				</form:select>
            </td>
       </tr>
       <tr>
           <td>使用对象：</td>
           <td>
           <form:select path="CDictionaryByObject.id" id="CDictionaryByObject.id">
	           <form:option value =""></form:option>	
	           <form:option value ="724">校内师生</form:option>
	           <form:option value ="725">校外师生</form:option>
	           <form:option value ="726">校外其他人员</form:option>
           </form:select>
           </td> 
           <td>实训室使用用途：</td>
           <td>
           <form:select path="CDictionaryByApplication.id" id="CDictionaryByApplication.id">
	       <form:option value =""></form:option>	
	       <form:option value ="727">教学</form:option>
	       <form:option value ="728">科研</form:option>
	       <form:option value ="729">考试</form:option>
	       <form:option value ="730">鉴定</form:option>
	       <form:option value ="731">培训</form:option>
	       <form:option value ="732">会议</form:option>
	       <form:option value ="733">其他</form:option>
	       </form:select>
           </td>
       </tr>
       <tr>
           <td>使用设备故障情况：</td>
           <td>
           <form:select path="CDictionaryByEquipmentSituation.id" id="CDictionaryByTidySituation.id">
           <form:option value =""></form:option>
           <form:option value ="734">有故障</form:option>
           <form:option value ="735">无故障</form:option>
           </form:select>
           </td> 
           <td>使用后整理归位情况：</td>
           <td>
           <form:select path="CDictionaryByTidySituation.id" id="CDictionaryByTidySituation.id">
	       <form:option value =""></form:option>	
	       <form:option value ="736">已整理</form:option>
           <form:option value ="737">未整理</form:option>
	       </form:select>
           </td>
       </tr>
       <tr>
           	<td>使用的仪器/设备/工具/软件等（硬件）:</td>
           	<td>
    			<form:select id="deviceName" class="chzn-select"  cssStyle="width:170px;" path="deviceName" multiple="multiple">
    			<form:options items="${devices}" itemLabel="deviceName" itemValue="deviceName"/>
  				</form:select>
            </td>
            <td>使用的仪器/设备/工具/软件等（软件）:</td>
            <td>
            <form:select id="softwareName" class="chzn-select"  cssStyle="width:170px;" path="softwareName" multiple="multiple">
            <form:options items="${softwares}" itemLabel="name" itemValue="name"/>
  				</form:select>
            </td>
       </tr>
       <tr>
            <td>备注：</td>
      		<td>
          	<form:textarea path="note" id="note" cols="100" rows="10" align="center"></form:textarea>
         	</td>
       	</tr>
       	<tr>
      		<td colspan="4" style="border: 1px solid #999;text-align:right;">
          	<input class="btn btn-new" type="submit" value="保存" />
         	</td>
       	</tr>
        </table>
	</form:form>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"></script>
	  <script type="text/javascript" src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"></script>
<!-- 下拉框的js -->
<script type="text/javascript">
	$(function($) {
		var config = {
      		'.chzn-select'           : {search_contains : true,width:"auto"},
      		'.chzn-select-deselect'  : {allow_single_deselect:true},
      		'.chzn-select-no-single' : {disable_search_threshold:10},
      		'.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
      		'.chzn-select-width'     : {}
    	}
   		for (var selector in config) {
      		$(selector).chosen(config[selector]);
    	}	
		
		$(".chzn-container").each(function(){
			$(this).css("width","200px");
		});
	});
	//选中select
	function selSoftware(softwareId){
		$("#code").val(softwareId);
		$(":checkbox").each(function(){
			$(this).removeAttr("checked");
		})
		$("input[value='"+softwareId+"']").prop("checked",true)
		$(".chzn-select").trigger("liszt:updated");
	}
	
	function cleanSoftList(){
		$(":checkbox").each(function(){
			$(this).removeAttr("checked");
		})
	}
	function checkFrom(){
		var labRoomId = $("#labRoomId").val();
		var courseNumber = $("#courseNumber").val();
		var startClass = $("#startClass").val();
		var endClass = $("#endClass").val();
		var startWeek = $("#startWeek").val();
		var endWeek = $("#endWeek").val();
		if(labRoomId == ""){
			alert("请提交完整表单!");
			return false;
		}else if(courseNumber == ""){
			alert("请提交完整表单!");
			return false;
		}else if(startClass == ""){
			alert("请提交完整表单!");
			return false;
		}else if(endClass == ""){
			alert("请提交完整表单!");
			return false;
		}else if(startWeek == ""){
			alert("请提交完整表单!");
			return false;
		}else if(endWeek == ""){
			alert("请提交完整表单!");
			return false;
		}else if(startWeek > endWeek){
			alert("开始周不可大于结束周！");
			return false;
		}else if(startClass > endClass){
			alert("开始节次不可大于结束节次！");
			return false;
		}
		return true;
	}
</script>	
    
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
