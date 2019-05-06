<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<head>
<meta name="decorator" content="iframe"/>
<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/css/style.css">
<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/css/iconFont.css">
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />

<style type="text/css">
	.chzn-container{width: 95% !important}
	.content-box table tr{height:55px;}
	.content-box table thead tr{height:auto !important}
	/* .content-box table td{border-left:1px dotted #eee;} */

</style>

</head>
<body>

<div class="main_container cf rel w95p ma">
<div class="iStyle_RightInner">

<div>
    <fieldset class="introduce-box">
    <legend>选课组信息</legend>
        <table id="listTable" width="50%" cellpadding="0">
            <tr>
	            <c:if test="${not empty schoolCourse.courseCode}">
	                <th>选课组编号：</th><td>${schoolCourse.courseCode}</td>
	                <th>课程名称：</th><td>${schoolCourse.schoolCourseInfo.courseName}</td>
	                <th>学期：</th><td>${schoolCourse.schoolTerm.termName}</td>
	            </c:if>
	            <c:if test="${not empty selfCourse.id}">    
	                <th>选课组编号：</th><td>${selfCourse.courseCode}</td>
	                <th>课程名称：</th><td>${selfCourse.schoolCourseInfo.courseName}</td>
	                <th>学期：</th><td>${selfCourse.schoolTerm.termName}</td>
	            </c:if>
            </tr>
        </table>
    </fieldset>
</div>
<br>

<div>
	<li>顺延周数:
		<select id="gapWeek">
			<option value="0">请选择</option>
			<option value="1">第1周</option>
			<option value="2">第2周</option>
			<option value="3">第3周</option>
			<option value="4">第4周</option>
			<option value="5">第5周</option>
			<option value="6">第6周</option>
			<option value="7">第7周</option>
			<option value="8">第8周</option>
			<option value="9">第9周</option>
			<option value="10">第10周</option>
			<option value="11">第11周</option>
			<option value="12">第12周</option>
			<option value="13">第13周</option>
			<option value="14">第14周</option>
			<option value="15">第15周</option>
			<option value="16">第16周</option>
		</select>&nbsp;&nbsp;&nbsp;
		<input type="button" value="确定" onclick="judgeAlterTimetable();"/>
	</li>
</div>

<div class="content-box">
<table> 
<thead>
<tr>
   <th class="header" width="50px;">选择</th> 
   <th class="header" width="50px;">批次</th> 
   <th class="header" width="130px;">实验项目名称</th>
   <th class="header" width="30px;">星期</th>
   <th class="header" width="100px;" colspan=2>
       <table>
       <tr>
          <td width="45px;">节次</td>
          <td width="45px;">周次</td>
       </tr>
       </table>
   </th>
   <th class="header" width="60px;">上课教师</th>
   <th class="header" width="60px;">指导教师</th>
   <th class="header">实验室</th>
   
</tr>
</thead>
<tbody>
<!-- 判断相同批次的当前批次id -->
<c:set var="ifRowspanBatch" value="0"></c:set>
<c:set var="count" value="1"></c:set>
<c:forEach items="${timetableAppointments}" var="current"  varStatus="i">
<!--判断是否仅为教师，如果是则只能查看自己排的课  -->
	
<!--合并相同批次的变量  -->
<c:set var="rowspanBatch" value="0"></c:set>
<tr>
    <td><input type="checkbox"  name="CK"  value="${current.id }"/></td>
     
    <!--合并相同批次的变量计数  -->
     <c:forEach items="${timetableAppointments}" var="current1"  varStatus="j">
         <c:if test="${current.timetableStyle==4 || current.timetableStyle==6 }">
	         <c:if test="${!current.timetableGroups.isEmpty()&&!current1.timetableGroups.isEmpty() }">
		         <c:if test="${current1.timetableGroups.iterator().next().timetableBatch.id==current.timetableGroups.iterator().next().timetableBatch.id }">
		            <c:set value="${rowspanBatch + 1}" var="rowspanBatch" />
		         </c:if>
	         </c:if>
         </c:if>
     </c:forEach> 


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
	          <c:if test="${!current.timetableGroups.isEmpty() }">
	             ${current.timetableGroups.iterator().next().timetableBatch.batchName} 
	          </c:if>
	          <c:if test="${current.timetableGroups.isEmpty()}">
	                <font color=red>分组信息已丢失，请删除排课记录，重新排课</font>
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
     
     <!--对应节次周次  -->
     <td colspan=2 width="90px;" >
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
			         <c:forEach var="entry" items="${current.timetableAppointmentSameNumbers}"   varStatus="p"> 
			         	<c:set var="v_param" value="-${entry.startClass}-" ></c:set>   
				         <c:if test="${fn:indexOf(sameStart,v_param)<0}">
					         <tr>
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
     <!--相关实验分室  -->
     <c:forEach var="entry" items="${current.timetableLabRelateds}">  
     <label title="<c:out value="${entry.labRoom.labRoomName}" />"><c:out value="${entry.labRoom.labRoomNumber}" /></label>
     <c:if test="${selected_labCenter eq 12 }"><!-- 纺织中心 -->
     	<a target="_blank" href="${pageContext.request.contextPath}/timetable/listTeachingLabRoomDevice?timetableId=${current.id}&labRoomId=${entry.labRoom.id}">查看教学用设备</a>
     </c:if>	
     <br>  
     </c:forEach>
     </td>
</tr>
</c:forEach> 
</tbody>
</table>

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
	}
	
	// 顺延前的判断
	function judgeAlterTimetable(){
		
		var array=new Array();// 排课id组成的数组
		var gapWeek = $("#gapWeek").val();
        var flag; //判断是否一个未选   
        $("input[name='CK']:checkbox").each(function() {  
            if ($(this).prop("checked")) { //判断是否选中    
                array.push($(this).val()); //将选中的值 添加到 array中 
                flag = true; //只要有一个被选择 设置为 true  
            }  
        })  

        if (flag && gapWeek!=0) { 
			$.ajax({
				url:"${pageContext.request.contextPath}/timetable/timetableAdmin/judgeAlterTimetable?appointmentIds="+array+"&gapWeek="+gapWeek,
				type:"POST",
				error:function(request){
		        	alert('请求错误!');
		        },
				success:function(data){
					if(data=="success"){
						// location.href="${pageContext.request.contextPath}/timetable/timetableAdmin/alterTimetable?appointmentIds="+array+"&gapWeek="+gapWeek;
						alterTimetable(array,gapWeek);
					}else{
						// TO DO
						alert(decodeURI(data));
					}
				}
			});
        } else {   
        	alert("请选择需要顺延排课记录和顺延周数");  
        } 
	}
	
	function alterTimetable(array, gapWeek){
		$.ajax({
			url:"${pageContext.request.contextPath}/timetable/timetableAdmin/alterTimetableByGapWeek?appointmentIds="+array+"&gapWeek="+gapWeek,
			type:"POST",
			error:function(request){
	        	alert('请求错误!');
	        },
			success:function(data){
				if(data=="success"){
					location.reload();
				}
			}
		});
	}
	
</script>
<!-- 下拉框的js -->
</div>
</div>
</body>
