<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE jsp:directive.include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
 <head>
	<meta name="decorator" content="none" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css"> 
	<link href="${pageContext.request.contextPath}/css/cmsSystem/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/iCheck/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/sweetalert/sweetalert.min.js"></script>
	<style>	
    .TabbedPanelsContent .content-box.m0{
            border: 1px solid #e4e5e7;
            margin:0;
        }
        .title{
            height:41px;
            line-height:41px;
            border-bottom: 1px solid #e4e5e7;
        }.title .btn-new {
            margin-top: 10px;
        }
	    .content-box{
	        margin:0;
	    }
	    .average_tab{
	        width:100%;
	    }
	    .average_tab th,
	    .average_tab td{
	        width:16.6%;
	        padding:10px 2px!important;
	    }	    
	    .chosen-container{
	        width:100%!important;
	    }
	    .chosen-container-single .chosen-single{
	        padding:0 0 0 2px;
	    }
	    .chosen-container-single .chosen-single span{
	        white-space:pre-line;
	    }
	    .chosen-container .chosen-results{
	        height:95px;
	    }
	    .btn{
	        display:inline-block;
	    }
	    .m0{
	        margin:0!important;
	    }
	</style>
 </head>
 <body>
 <div class="time_box TabbedPanelsContent">
     <div class="content-box m0">
         <div class="title">已安排时间段<a class="btn btn-new" href="javascript:void(0)" onclick="addApp()">添加排课</a></div>
                            <div class="tool_box brl5 cf bgg pa5">
                            	<div class="">
			                        <div class="tool_box brl5 cf ">
			                            <table id="classReslut" class="average_tab">
			                                <thead>
			                                  <th>周次</th>
			                                  <th>星期</th>
			                                  <th>节次</th>
			                                  <th>上课地点</th>
			                                  <th>教师</th>
			                                  <th>实验项目</th>
			                                </thead>
			                                <tbody>
			                                	<c:forEach items="${appointments }" var="currapp">
					                                <tr>
					                                	<td>第${currapp.startWeek }周</td>
					                                	<td>星期${currapp.weekday }</td>
					                                	<td>
					                                	<c:if test="${currapp.timetableAppointmentSameNumbers.size()==0}">
                 												<c:if test="${currapp.startClass==currapp.endClass}">
                 													${currapp.startClass },
                 												</c:if>
                 												<c:if test="${currapp.startClass!=currapp.endClass}">
                 													${currapp.startClass }-${currapp.endClass },
                 												</c:if>
          												</c:if>
          												<c:if test="${currapp.timetableAppointmentSameNumbers.size()>0}">
											             <c:set var="sameStart" value="-"></c:set>
											             <c:forEach var="entry" items="${currapp.timetableAppointmentSameNumbers}"   varStatus="p"> 
											             <c:set var="v_param" value="-${entry.startClass}-" ></c:set>   
											             <c:if test="${fn:indexOf(sameStart,v_param)<0}">
											                 <c:if test="${entry.startClass==entry.endClass}">
											                 ${entry.startClass },
											                 </c:if>
											                 <c:if test="${entry.startClass!=entry.endClass}">
											                 ${entry.startClass }-${entry.endClass },
											                 </c:if>
											             </c:if>
											             <c:set var="sameStart" value="${sameStart}-${entry.startClass }-"></c:set>
											             </c:forEach>
         												</c:if>
					                                	</td>
					                                	<td>
					                                		<c:forEach var="entry" items="${currapp.timetableLabRelateds}">  
																${entry.labRoom.labRoomName}  
															</c:forEach>
					                                	</td>
					                                	<td>
					                                		<c:forEach var="entry" items="${currapp.timetableTeacherRelateds}">  
																${entry.user.cname}[${entry.user.username}]
															</c:forEach>
					                                	</td>
					                                	<td>
					                                		<c:forEach var="entry" items="${currapp.timetableItemRelateds}">  
																${entry.operationItem.lpName}
															</c:forEach>
					                                	</td>
					                                </tr>
			                                	</c:forEach>
				                                <tr id="trLast">
				                                </tr>
			                                </tbody>
			                            </table>
			                        </div>
			                    </div>
	                            <div class="">
		                            <div id="doSelfTimetable">
		                            <table class="average_tab">
		                                <tr>
		                                <td>
											<select data-placeholder="节次" data-placeholder="周次" class="chosen-select" id="weeks" tabindex="2">
											   <option value="0">周次</option>
		                                   </select>
		                                </td>
		                                <td>		                           
		                                <select data-placeholder="星期" class="chosen-select" id="weekday" tabindex="2">
		                                	<c:forEach begin="1" end="6"  var="currday">
			                                    <option value="${currday }">星期${currday }</option>
			                                </c:forEach>
		                                </select>
		                                </td>
		                                <td>
		                                <select data-placeholder="节次"  id="classes" class="chosen-select" multiple tabindex="4" class="">
			                                <c:forEach begin="1" end="13"  var="currclass">
			                                    <option value="${currclass }">${currclass }</option>
			                                </c:forEach>    
		                                </select>
		                                </td>
		                                <td>		                                
		                            	<select data-placeholder="上课地点" id="labRooms" class="chosen-select" multiple tabindex="4" class="">
		                                    <option value=""></option>
		                                    <c:forEach items="${labRoomMap}" var="currlab"  varStatus="i">
								    	       <option value ="${currlab.key}" >${currlab.value} </option>
									        </c:forEach>
		                                </select>
		                                </td>
		                                <td>		                                
		                                <select data-placeholder="请选择教师" id="user" class="chosen-select" tabindex="2" class="">
		                                    <option value ="${user.username}" selected> ${user.cname}[${user.username}]</option>
										    <c:forEach items="${timetableTearcherMap}" var="curruser"  varStatus="i">
										        <c:if test="${curruser.key != user.username }">
										           <option value ="${curruser.key}">${curruser.value}</option>
										        </c:if>
										    </c:forEach>
		                                </select>		                                
		                                <select data-placeholder="请选指导员" id="tutor" class="chosen-select" tabindex="2" class="">
		                                    <option value ="${user.username}" selected> ${user.cname}[${user.username}]</option>
										    <c:forEach items="${timetableTearcherMap}" var="curruser"  varStatus="i">
										        <c:if test="${curruser.key != user.username }">
										           <option value ="${curruser.key}">${curruser.value}</option>
										        </c:if>
										    </c:forEach>
		                                </select>
		                                </td>
		                                <td>		                                
		                                <select data-placeholder="实验项目" id="item" class="chosen-select" multiple tabindex="4" class="">
										    <c:forEach items="${timetableItem}" var="curritem"  varStatus="i">
									           <option value ="${curritem.id}"> ${curritem.lpName}</option>
										    </c:forEach>
		                                </select>
		                                </td>
		                                </tr>
		                                <tr>
		                                <td colspan="6" style="text-align:right;">
		                                <a class="btn r" onclick="saveButton()">保存</a>
		                                <a class="btn r" onclick="cancleButton()">取消</a>
		                                </td>
		                                </tr>
		                                </table>
		                            </div>
		                        </div>
		                        
                            </div>
                        </div>
                    </div>

<script type="text/javascript">
    $(function () {
    	
    	// 课程安排框，默认不显示
    	$('#doSelfTimetable').css('display','none');
    	
        $(".chosen-select").chosen({
            disable_search_threshold: 10,
            no_results_text: "Oops, nothing found!"
        });
        
        // 查看本课程实验项目弹窗
        $('[view-item]').each(function(i,e){
	         $(e).on('click',function(){
		         layer.open({
	             	type: 2,
	             	title: '查看本课程实验项目',
	             	maxmin: true,
	             	shadeClose: true, 
	             	area : ['700px' , '350px'],
	             	content: '${pageContext.request.contextPath }/operation/listOperationItemByCourse?courseNumber=${courseNumber}'
	             })  
		     });                                              
		});
        
    });
    
	// 添加排课
	function addApp(){
		$('#doSelfTimetable').css('display','');
	}

    
 	// 保存按钮
    function saveButton(){

    	var weeks = $('#weeks').val();
 		var classes = $('#classes').val().toString();
        var weekday = $('#weekday').val();
 		var labRooms = $('#labRooms').val().toString();
 		var items = $('#item').val().toString();
 		var teachers = $('#user').val().toString();
 		var teachers2 = $('#tutor').val().toString();
 		$.ajax({
            url:'${pageContext.request.contextPath}/timetable/judgeClassesIsContinuity',
            type:'POST',
            data:{classes:classes},
            error:function (request){
              alert('请求错误!');
            },
            success:function(data){
            	if(data == "yes"){
            		$.ajax({
                        url:'${pageContext.request.contextPath}/timetable/saveGroupTimetable',
                        type:'POST',
                        data:{term:${term}, classes:classes, labRooms:labRooms, weekday:weekday, items:items, 
                        	weeks:weeks, teachers:teachers, teachers2:teachers2,courseNo:${courseNo}, groupId:${groupId}, isAdmin:${isAdmin}},
                        error:function (request){
                          alert('请求错误!');
                        },
                        success:function(data){
                        	if(data != '-1'){
                        		$('#doSelfTimetable').css('display','none');
                            	// 被选的排课内容
                                var weekText = $('#weeks').find("option:selected").text().trim();
                                var classesText = $('#classes').val();
                                var weekdayText = $('#weekday').find("option:selected").text().trim();
                                var labText = $('#labRooms').find("option:selected").text().trim(); 
                                var userText = $('#user').find("option:selected").text().trim();
                                var itemText = $('#item').find("option:selected").text().trim();
                                var tutorText = $('#tutor').find("option:selected").text().trim();
                              	// 排课结果呈现
                                var $td1=$("<td>第"+weekText+"周</td>");
                                var $td2=$("<td>"+weekdayText+"</td>");
                                var $td3=$("<td>"+classesText+"</td>");
                                var $td4=$("<td>"+labText+"</td>");
                                var $td5=$("<td>"+userText+tutorText+"</td>");
                                var $td6=$("<td>"+itemText+"</td>");
                                var $td7=$("");
                                var $tr=$("<tr></tr>");
                                $tr.append($td1);
                                $tr.append($td2);
                                $tr.append($td3);
                                $tr.append($td4);
                                $tr.append($td5);
                                $tr.append($td7);
                                $tr.append($td6);
                                $("#trLast:last").before($tr);
                        	}else{
                        		alert("该时间段已经被其他排课占用，请您添加其他时间段排课");
                        	}
                     	}
                  });
            	}
            	else{
            		alert("请选择连续的节次!");
            	}
         }
      });
    }
 	
 	// 取消按钮
 	function cancleButton(){
 		$('#doSelfTimetable').css('display','none');
 	}
 	
 	// ajax获取判冲后的周次
 	$("#labRooms").chosen().change(function(){
 	// $("#labRooms").chosen().change(function(){
		$("#weeks").html("");
		var values=[];
		$($("#classes option:selected")).each(function(){
			values.push(this.value);
		});
		var labRooms=[];
		$($("#labRooms option:selected")).each(function(){
			labRooms.push(this.value);
		});
		
		$.ajax({
		    url:"${pageContext.request.contextPath}/timetable/reTimetable/getValidWeeksMap",
		    dataType:"json",
		    type:'GET',
		    data:{term:${term},weekday:$("#weekday").val(),labrooms:labRooms.join(","),classes:values.join(",")},
		    complete:function(result)
		    {
		       var obj = eval(result.responseText); 
		       var result2 = "<option value=''>周次</option>";
		       for (var i = 0; i < obj.length; i++) { 
		            result2 = result2 + "<option value='" +obj[i].id + "'>" + obj[i].value + "</option>";
		       }; 
		       $("#weeks").append(result2);
		       $("#weeks").trigger("chosen:updated");
		       result2="";
		    }
		});
		$("#weeks").trigger("chosen:updated");
	});
 	
 	/*如果选择節次，形成关联的选课数据的联动  */
 	$("#classes").chosen().change(function(){
 	$("#weeks").html("");
 	var values=[];
 	$($("#classes option:selected")).each(function(){
 		values.push(this.value);
 	});
 	var labRooms=[];
 	$($("#labRooms option:selected")).each(function(){
 		labRooms.push(this.value);
 	});

 	$.ajax({
 	    url:"${pageContext.request.contextPath}/timetable/reTimetable/getValidWeeksMap",
 	    dataType:"json",
 	    type:'GET',
 	    data:{term:${term},weekday:$("#weekday").val(),labrooms:labRooms.join(","),classes:values.join(",")},
 	    complete:function(result)
 	    {
 	       var obj = eval(result.responseText); 
 	       var result2 = "<option value=''>周次</option>";
 	       for (var i = 0; i < obj.length; i++) { 
 	            //var val = obj.responseText[i]; 
 	            //result2 = result2 + "<option value='" +obj[i].id + "' selected>" + obj[i].value + "</option>";
 	            result2 = result2 + "<option value='" +obj[i].id + "'>" + obj[i].value + "</option>";
 	          }; 
 	       $("#weeks").append(result2);
 	       $("#weeks").trigger("chosen:updated");
 	       result2="";
 	    }
 	});
 	$("#weeks").trigger("chosen:updated");
 	});
 	
 	$("#weekday").chosen().change(function(){
 		$("#weeks").html("");
 		var values=[];
 		$($("#classes option:selected")).each(function(){
 			values.push(this.value);
 		});
 		var labRooms=[];
 		$($("#labRooms option:selected")).each(function(){
 			labRooms.push(this.value);
 		});
 		$.ajax({
 		    url:"${pageContext.request.contextPath}/timetable/reTimetable/getValidWeeksMap",
 		    dataType:"json",
 		    type:'GET',
 		    data:{term:${term},weekday:$("#weekday").val(),labrooms:labRooms.join(","),classes:values.join(",")},
 		    complete:function(result)
 		    {
 		       var obj = eval(result.responseText); 
 		       var result2 = "<option value=''>周次</option>";
 		       for (var i = 0; i < obj.length; i++) { 
                  result2 = result2 + "<option value='" +obj[i].id + "'>" + obj[i].value + "</option>";
 		       }; 
 		       $("#weeks").append(result2);
 		       $("#weeks").trigger("chosen:updated");
 		       result2="";
 		    }
 		});
 		$("#weeks").trigger("chosen:updated");
 		});
 	
</script>

</body>
</html>