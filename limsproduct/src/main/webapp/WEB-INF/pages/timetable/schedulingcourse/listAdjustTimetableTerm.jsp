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
//$(function(){
  //  var con;
//	con=$('#softwares').val();
//	$.ajax({
	//	async: false,
	//	type: "POST",
	//	url: "${pageContext.request.contextPath}/timetable/selfTimetable/isSoftWareInstalled?softwares="+con,
//		dataType:'html',
	//	success:function(data){
	//		if(date.equals("false"))
	//			{
	//			alert("需要进行软件申请");
	//			}
	//	},
	//	error:function(){
	//		alert("软件选择异常");
	//		}
	//});	
//});
	
function licence(){
 var timeid=${timetableSelfCourse.id};
    if (timeid!=-2){	
    var  selfCourseId=timeid;
    }
	var classesArray=[];
	var licences=${licence}+"";
	$($("#classes option:selected")).each(function(){
		classesArray.push(this.value);
	});
	var software;
	software=$('#softwares').val();
	if($.inArray(licences,software)!=-1){
		$.ajax({
	         url:"${pageContext.request.contextPath}/timetable/vocationalTimetable/isSoftWareConfilict",
	         dataType:"html",
	         type:'POST',
	         data:{selfCourseId:selfCourseId,classes:classesArray.join(","),weeks:$("#weeks").val(),weekday:$("#weekday").val(),softwares:$("#softwares").val()},
	         success:function(resultt)
	         {
               if(resultt=="false")
            	   {
            	 
            	   //$("#softwares option[value=${licence}").attr("selected",false);
            	    //  $("#softwares").chosen();
            	     // $("#softwares").trigger("liszt:updated");
            	      alert("时间冲突，请重新选择时间！");
            	      $("#weekday").attr('value',"");
         			 	$("#weekday").trigger("liszt:updated");
         			 	$("#weeks").attr('value',"");
      			 	$("#weeks").trigger("liszt:updated");
      			 	$("#classes").attr('value',"");
         			 	$("#classes").trigger("liszt:updated");
         				 $("#softwares").attr('value',"");
             	      	$("#softwares").trigger("liszt:updated");

            	   }
                
	          }
	});
	}
}

//得到courseDetail的节次和周次信息
var startWeek=1;//开始周的全局变量
var endWeek=19;//结束周的全局变量
var canSubmit=true;
function checkForm(){
	if($("#labRooms").val()==""||$("#labRooms").val()==null){
		 alert("请选择实验室") 
	}//else if($("#items").val()==""||$("#items").val()==null){
		//alert("请选择实验项目")
	//}
	else if($("#classes").val()==""||$("#classes").val()==null){
		alert("请选择节次")
	}else if($("#weeks").val()==""||$("#weeks").val()==null){
		alert("请选择周次")
	}else if($("#teachers").val()==""||$("#teachers").val()==null){
		alert("请选择上课教师")
	}else{
	    if (canSubmit==false){
	    	alert("请等待本次进度修改")
	             return;
	    }
	    canSubmit=false;
	    //document.form.submit();
        var sUrl ="${pageContext.request.contextPath}/timetable/saveAdjustTimetable?currpage=${currpage}";
        if(${tableAppId ne 0}){
            sUrl ="${pageContext.request.contextPath}/timetable/saveAdminTimetable";
        }
        $.ajax({
            url:sUrl,
            dataType:"json",
            type:'GET',
            data:$( '#form').serialize(),
            complete:function(result){
                window.parent.location.reload();
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            }
        });
	}
}
//flag提示是否处理调课完成0不提示，1提示
var flag =${flag};
if(flag==1){
  //var mymes= window.confirm("提交后不能修改；如未排完课，请按取消按钮继续。");
  //if(mymes){
     var sUrl ="${pageContext.request.contextPath}/timetable/doJudgeTimetableOk?courseCode=${schoolCourseDetailMap.get(0).schoolCourse.courseCode}&currpage=1";
     //parent.location.href= sUrl;
  //}
}
</script>

<script type="text/javascript">
var tableAppId=${tableAppId};//定义本条排课记录id的全局变量
	var buttonflag=0;
	function showlabroom(coursecode){
	if(buttonflag==0){
	var  selfCourseId=${timetableSelfCourse.id};
	var softwareArray=[];
	$($("#softwares option:selected")).each(function(){
		softwareArray.push(this.value);
	});
	var confinementTime=${confinementTime};
	var classesArray=[];
	$($("#classes option:selected")).each(function(){
		classesArray.push(this.value);
	});
	// 从后台获取空闲的实验室
	$.ajax({
	         url:"${pageContext.request.contextPath}/timetable/vocationalTimetable/getValidLabRoomsForSkill",
	         dataType:"json",
	         type:'GET',
	         data:{selfCourseId:selfCourseId,classes:classesArray.join(","),weeks:$("#weeks").val(),weekday:$("#weekday").val(),coursecode:coursecode,softwares:softwareArray.join(","),confinementTime:confinementTime},
	         complete:function(result)
	         {
	             buttonflag = 1;
	        	 var obj = eval(result.responseText); 
	             var result1,result2,result3;
	            for (var i = 0; i < obj.length; i++) { 
	             	//result2 = result2 + "<option value='" +obj[i].id + "'>" + obj[i].value + "</option>";
	             	if(obj[i].status == 1){
	             		result1+="<tr><td><input type='radio' name='labRooms1' value='"+obj[i].id+"' /></td>";
	             		result1+="<td>"+obj[i].value+"</td>"
	             		result1+="<td>"+obj[i].number+"</td>"
	             		result1+="</tr>"
	             	}
	             	else if(obj[i].status==2){
	             		result2+="<tr><td><input type='radio' name='labRooms2' value='"+obj[i].id+"' /></td>";
	             		result2+="<td>"+obj[i].value+"</td>"
	             		result2+="<td>"+obj[i].number+"</td>"
	             		result2+="</tr>"
	             	}
	             /* 	else{
	             		result3+="<tr><td><input type='radio' name='labRooms3' value='"+obj[i].id+"' /></td>";
	             		result3+="<td>"+obj[i].value+"</td>"
	             		result3+="<td>"+obj[i].number+"</td>"
	             		result3+="</tr>"
	             	} */
	             }; 
	             $("#labRooms1 tbody").append(result1)
	             $("#labRooms2 tbody").append(result2)
	           /*   $("#labRooms3 tbody").append(result3) */
	             $(".labDiv").css("display","block")
	             //$("#labRooms").append(result2);
	             //$("#labRooms").trigger("liszt:updated");
	            // result2="";
	            // $("#labRooms").append("").multiselect("refresh");
	          }
	});
	}else{
	alert("已生成请勿重复点击！")}
}




</script>
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
	<li class="end">教务调整管理</li>
</ul>
</div>
</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<div class="title">教务调整排课</div>
<table  > 
<thead>
<tr>
  <!--  <th>选择</th> -->
   <th>课程编号</th>
   <th>课程名称</th>
   <th>选课组编号</th>
   <th>实验项目</th>
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
             ${timetableAppointmentMap.startClass }-${current.endClass }
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
<c:if test="${tableAppId eq 0 }">
<form id="form" name="form" method="Post" action="${pageContext.request.contextPath}/timetable/saveAdjustTimetable?currpage=${currpage}"  >
</c:if>
<c:if test="${tableAppId ne 0  }">
<form id="form" name="form" method="Post" action="${pageContext.request.contextPath}/timetable/saveAdminTimetable" target=_parent>
</c:if>
<table > 
<thead>
<tr>
  <!--  <th>选择</th> -->
      <th>选择项目</th>
            <th>选择周次</th>
   <th>选择星期</th>
   <th>选择节次</th>
    <c:if test="${softwareEnable eq 1}">
            <th>选择软件</th>
    </c:if>
             <th>选择教师</th>
    <th>选择实验室</th>
  

</tr>
</thead>
<tbody>
<tr>
  <!--  <th>选择</th> -->
  <%--
    <td>
     <select class="chzn-select" multiple="multiple" data-placeholder='请选择实验设备：'  name="devices" id="devices" style="width:180px" required="false">
	</select>
   </td>  暂时不用，隐掉
   
   --%><td>
     <select class="chzn-select" multiple="multiple" data-placeholder='请选择实验项目：'  name="operationitems" id="operationitems" style="width:150px" required="true" >
	  <%--<option value ="${operationItem.get(0).id}" selected> ${operationItem.get(0).lpName}</option>--%>
	<c:forEach items="${timetableItemMap}" var="current"  varStatus="i">	
	      <option value ="${current.key}"> ${current.value}</option>
	  </c:forEach>
	</select>
   </td>
     <td>
   <select class="chzn-select"   data-placeholder='请选择排课周次' name="weeks" id="weeks" style="width:180px" required="true">
   <option value ="">请选择</option>
   <c:forEach items="${schoolweek}" var="current" >
     
         <option value ="${current}" > ${current}</option>

   </c:forEach>
   </select>
   </td>
     <td>
   <select class="chzn-select" name="weekday" id="weekday" style="width:120px">
     <!--如果星期重复，则去重  -->
     <option value ="">请选择</option>
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
    <option value ="">请选择</option>
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
<c:if test="${softwareEnable eq 1}">
  <td>
  <select class="chzn-select" multiple="multiple" data-placeholder='请选软件' name="softwares" id="softwares" style="width:100px" required="true" onchange="licence()">
	  <c:forEach items="${software}" var="current2"  >
	      <option value ="${current2.id}" selected> ${current2.name}</option>
	  </c:forEach>
	
	  <c:forEach items="${softwareMap}" var="current"  >
	      <option value ="${current.key}" > ${current.value}</option>
	  </c:forEach>
   </select>
   </td>
</c:if>
   <td>
  <select class="chzn-select" multiple="multiple" data-placeholder='请选择授课教师' name="teachers" id="teachers" style="width:150px" required="true">
	  <option value ="${schoolCourseDetailMap.get(0).user.username}" selected>${schoolCourseDetailMap.get(0).user.cname}:${schoolCourseDetailMap.get(0).user.username};${schoolCourseDetailMap.get(0).schoolAcademy.academyName}</option>
	  <c:forEach items="${timetableTearcherMap}" var="current"  varStatus="i">
	      <option value ="${current.key}" > ${current.value}</option>
	  </c:forEach>
   </select>
   </td>
    <td>
   <input type="button" name="labRooms" id="labRooms" onclick="showlabroom('${courseCode}')" value="生成可选实验室" >	
   </td>
</tr>
<%-- <c:if test="${selected_labCenter==12 }">
	<tr>
    	<td align=left>是否允许教学外设备对外开放：</td>
    	<td>
    		<input type="radio" id="allowUse" name="group1" onclick="showDevice()">是</input>
    		<input type="radio" id="forbidUse" name="group1" onclick="showDevice()">否</input>
    	</td>
    </tr>
    
	<tr  id="labRoomDevices">
	<td align=left>请选择实训室设备：</td>
	<td colspan=6>
		<select class="chzn-select" data-placeholder='请选择实训室设备：' multiple="multiple"	name="labRoomDevice_id" id="labRoomDevice_id" items=""	invalidMessage="不能超过30个字符！" style="width:450px"	required="true">
			<c:forEach items="" var="entry">
				<option value=""></option>
			</c:forEach>
		</select> 
	</td>
	</tr>
	</c:if> --%>
<tr>

<td colspan=4>
<input type="hidden" name="courseDetailNo" value="${schoolCourseDetailMap.get(0).courseDetailNo}">
<input type="hidden" name="courseCode" value="${courseCode}">
<input type="hidden" name="currpage" value="${currpage}">

<!--传递修改的排课表主键  -->
<input type="hidden" value="${tableAppId}" name="id">


<%--<input type="button" onclick="checkForm()" value="提交">--%>
    <a type="submit" value="提交" onclick="checkForm()"><input type="button" value="提交"></a>&nbsp;&nbsp;
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
<div class="labDiv">
<div class="title" style="width:50%;float:left;padding:0;text-align:center;">完全匹配的实验室</div>
<!-- <div class="title" style="width:31%;float:left;">需要安装软件的实验室</div> -->
<div class="title" style="width:50%;float:left;padding:0;text-align:center;">容量不足的实验室</div>
<table style="width:49%;float:left;margin-right:1%;" id="labRooms1" >
	
		<thead>
			<tr>
				<td></td>
				<td>实验室</td>
				<td>实验室容量</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<table style="width:49%;float:left" id="labRooms2">
		
		<thead>
			<tr>
				<td></td>
				<td>实验室</td>
				<td>实验室容量</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
<!-- 
	<table style="width:33%;float:left" id="labRooms2">
		
		<thead>
			<tr>
				<td></td>
				<td>实验室</td>
				<td>实验室容量</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table> -->

</div>
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

