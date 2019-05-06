<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE jsp:directive.include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
 <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css"> 
	<link href="${pageContext.request.contextPath}/static/css/lib.css" rel="stylesheet" type="text/css" media="">
	<link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/global.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/chosen.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/pignose.tab.min.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/chosen.jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/tab_slider.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/pignose.tab.min.js"></script>	
	<link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.zh.js"></script>		
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css"> 
	<link href="${pageContext.request.contextPath}/css/cmsSystem/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/iCheck/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/sweetalert/sweetalert.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.cookie.js"></script>    
    
	<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/css/style.css">
	<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
    
	<meta name="decorator" content="none" />

    <script type="text/javascript">
    $(".chosen-select").chosen({
        disable_search_threshold: 10,
        no_results_text: "Oops, nothing found!",
        width: "100"
    });
    	var isOtherPeriod=false;
    	$(document).ready(function() {
    		document.getElementById("norAdmin").style.display="none";
    		});
    	 function changeWeekDay(){// 该方法是用一个按钮进行教务和非教务时间段的切换，其他项目可参考
    		 isOtherPeriod = !isOtherPeriod;// 反转
    		 if(isOtherPeriod){
    			 // 正常转其他   将对应的id和name反转
    			 $("#altButton").val("教务时间段排课");
    			 document.getElementById("norAdmin").style.display="block";
    	         $('#doOtherTimetable').css('display','none');
    	         $("#admin").css('display','none');
    	         $("#saveButton").hide();
    	     }else{
    			 // 其他转正常   将对应的id和name反转
    			 $("#altButton").val("添加其他时间段排课");
    			 $("#norAdmin").css('display','none'); 
    	         $("#admin").css('display','');
    	          $("#saveButton").show();
    	     }
    	 }
    	// 添加排课
    		function addApp(){
    			$('#doOtherTimetable').css('display','');
    		} 
    	 	// 保存按钮
    	    function saveButton(tid){
		    	var weeks = $('#norWeek'+tid).val();
		 		var classes = $('#norclasses'+tid).val().toString();
		 		var weekday = $('#norWeekday'+tid).val()+'';
		 		var labRooms = $('#norLab'+tid).val()+'';
		 		//var items = $('#mulItem'+tid).val()+'';
		 		var teachers = $('#norUser1'+tid).val()+'';
		 		var teachers2 = $('#norUser2'+tid).val()+'';
		 		var norclassType = $('#norclassType'+tid).val();
		 		var content = $('#norCourse'+tid).val()+'';
		    	 		$.ajax({
		    	            url:'${pageContext.request.contextPath}/teaching/coursesite/timetable/judgeClassesIsContinuity',
		    	            type:'POST',
		    	            data:{classes:classes},
		    	            success:function(data){
		    	            	if(data == "yes"){
		    	            		$.ajax({
		    	        	            url:'${pageContext.request.contextPath}/timetable/saveSchoolTimetable',
		    	        	            type:'POST',
		    	        	            data:{term:${term}, classes:classes, labRooms:labRooms, weekday:weekday, 
							                weeks:weeks, teachers:teachers, courseNo:${courseNo}, isOther:1,
							                teachers2:teachers2,classType:norclassType,content:content,courseCopy:null},
		    	        	            error:function (request){
		    	        	              alert('请求错误!');
		    	        	            },
		    	        	            success:function(data){
		    	        	            	if(data != -1){
		    	        	            		$('#doOtherTimetable').css('display','none');
		    	        	                	// 被选的排课内容
		    	        	                    var norWeek = $('#norWeek'+tid).find("option:selected").text().trim();
							                    var norClass = $('#norclasses'+tid).val();
							                    var norWeekday = $('#norWeekday'+tid).find("option:selected").text().trim();
							                    var norLab = $('#norLab'+tid).find("option:selected").text().trim();
							                    var norUser = $('#norUser1'+tid).find("option:selected").text().trim()+" "+$('#norUser2'+tid).find("option:selected").text().trim();
							                    var norCourse = $('#norCourse'+tid).find("option:selected").text().trim();
							                    var typeString =$('#norclassType'+tid).find("option:selected").text().trim();
							                    do_count = do_count+1;
							                    var list_count = do_count+${appointments.size()};
		    	        	                  	// 排课结果呈现
		    	        	                   		var $td1=$("<td>"+norWeek+"</td>");
								                    var $td2=$("<td>"+norWeekday+"</td>");
								                    var $td3=$("<td>"+norClass+"</td>");
								                    var $td4=$("<td>"+norLab+"</td>");
								                    var $td5=$("<td>"+norCourse+"</td>");
								                    var $td6=$("<td>"+norUser+"</td>");
								                    var $td8=$("<td>"+typeString+"</td>");
								                    var $td9=$("<td><a onclick='deleteApp("+data+","+tid+", this)' class='fa fa-trash-o g9 f14 mr5 poi'><a></td>");
								                    var $tr=$("<tr></tr>");
								                    $tr.append($("<td>"+list_count+"</td>"));
								                    $tr.append($td4);
								                    $tr.append($td2);
								                    $tr.append($td3);
								                    $tr.append($td1);
								                    $tr.append($td5);
								                    $tr.append($td6);
								                    $tr.append($td8);
								                    $tr.append($td9);
								                    $("#tr_last").before($tr);
		    	        	                    $("#classReslut").find("tr:last").before($tr);
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
    	 		$('#doOtherTimetable').css('display','none');
    	 	}
    	 	
    	 	// ajax获取判冲后的周次
    	 	$("#labRooms").chosen().change(function(){
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
    	 	
    	 	// 实现选择第一个教师后的后排选中
    	 	function altTeacher(flag, obj){// flag 1 -- 教师1 2 -- 教师2
    	 		if (obj.id == 'mulUser11' || obj.id == 'mulUser21'){
	    	 		$(".timetable_div").each(function(){// 找到所有的div 遍历
	 				   var tid = this.id.replace('doTimetable','');// 找到具体的id
		 				   $("#mulUser"+flag+tid).val($("#mulUser"+flag+"1").val());
		 				   // 强行把显示的值改掉（可能比较傻）
		 				   // 下面的更改
		 				   $("#mulUser"+flag+tid+"_chosen").children("a").children("span").text($("#mulUser"+flag+"1").val());
	 				});
    	 		}
    	 	}
    	 	
    	 	// 保存所有排课
    	 	function save_all(){
    	 	// 进行全部保存之前，要确保所有的select都已经选好了
                var null_count = 0;
                $('#admin .chosen-select').each(function(){// 遍历每一个select的值
                   if(this.id!=null && this.id!='' && this.id!='course_chose'&& $('#'+this.id).val() == null){
                      console.log(this);
                      null_count = null_count+1;
                   }
                });
                if(null_count > 0){
                    alert('页面还有'+null_count+'个空值，请填好再确认');
                    return false;
                }
    	 		// 进行全部排课之前需要提醒老师，是否做排课关联
    	 		if(${appointments.size()==0} && do_count == 0){
    	 			// 目前尚无排好的课程(appointments.size()加载页面前的排课 do_count 加载页面后的排课数量 )
    	 			if(${courseInfo.schoolCourses.size()}>1){
    	 			if(confirm("是否要班级复制")){
	    	 			$("#course_chzn").window({left:"200px", top:"200px"});   
	    	 			$("#course_chzn").window('open'); 
	    	 			}else{
		 				confirm_save_all();
    	 			}
                    }
                    confirm_save_all();
    	 		}else{
                    alert('已有排课预约记录或非教务时间排课');
                    return false;
    	 		}
    	 	}
    	 	// 确认保存所有排课
    	 	function confirm_save_all(){
    	 		// 调用单个保存的方法
    	        $('.timetable_div').each(function(){
    	           var tid = this.id.replace('doTimetable','');
    	           if($('#mulLab'+tid).val()+''=='null' || $('#mulCourse'+tid).val()+''=='null'){
    	        	   // do nothing
    	           }else{
	  	           	saveAdminTimetable(tid,1);
    	           }
    	        });
    	 	}
    	 	
    	 	// 查看排课日历
    	 	function getCourseDate(){
   	 			$("#course_date").window({left:"200px", top:"200px"});   
   	 			$("#course_date").window('open'); 
    	 	}
    </script>    
    <style>
		.tab{
		background:#fcfcfc;
		}
		.timetable_div{
		margin: 15px 0 25px;
        padding: 7px 4px;
        background: #edf0f1;
        box-shadow: 0 0 1px #b0b3b3 inset;
		}
		.timetable_div .chosen-container{
		vertical-align: text-top;
		}
		.timetable_div .chosen-container:nth-child(4),
		.timetable_div .chosen-container:nth-child(8),
		.timetable_div .chosen-container:nth-child(16){
		width:70px!important;
		}
		.timetable_div .chosen-container:nth-child(6){
		width:52px!important;
		}
		.timetable_div .chosen-container:nth-child(2),
		.timetable_div .chosen-container:nth-child(10),
		.timetable_div .chosen-container:nth-child(12),
		.timetable_div .chosen-container:nth-child(14){
		width:87px!important;
		}		
		.chosen-container-multi .chosen-choices li.search-field{
		border: 1px solid #b1b1b1;
        width: 100%;
        margin: 4px 0;
        box-shadow: 1px 1px 2px #c1c1c1 inset;
        border-radius: 3px;
		}
		#classReslut{
		background:#fff;
		}
		#classReslut th{

		}
		#classReslut th,
		#classReslut td{

		font-size: 12px;
        padding: 5px 3px;
		}
		.bgg2 {
        background: #f9f9f9;
        border: 1px solid #e2e2e2;
        box-sizing: border-box;
        }
		 .content-box {
			border: 1px solid #9BA0AF;
			 border-radius: 5px;
			 overflow: visible;
		}

  	</style>
  	<style class="ng-scope">
		.tab{
		background:#fcfcfc;
		}
		.timetable_div{
		box-sizing:border-box;
		width:100%;
		margin: 15px 0 25px;
        padding: 7px 4.5%;
        background: #edf0f1;
        box-shadow: 0 0 1px #b0b3b3 inset;
		}
		.timetable_div .chosen-container{
		box-sizing:border-box;
		vertical-align: text-top;
		}
		.timetable_div .chosen-container:nth-child(2),
		.timetable_div .chosen-container:nth-child(4),
		.timetable_div .chosen-container:nth-child(6),
		.timetable_div .chosen-container:nth-child(8),
		.timetable_div .chosen-container:nth-child(10),
		.timetable_div .chosen-container:nth-child(12),
		.timetable_div .chosen-container:nth-child(13),
		.timetable_div .chosen-container:nth-child(14),
		.timetable_div .chosen-container:nth-child(16){
		width:14.2%!important;
		}		
		.chosen-container-multi .chosen-choices li.search-field{
		border: 1px solid #b1b1b1;
        width: 100%;
        margin: 4px 0;
        box-shadow: 1px 1px 2px #c1c1c1 inset;
        border-radius: 3px;
		}
		#classReslut{
		background:#fff;
		}
		#classReslut th{
		width:13%;
		background:#fafaff;
		white-space:nowrap;
		font-weight:700;
		}
		#classReslut th:first-child,
		#classReslut th:last-child{
		width:4.5%;
		}
		#classReslut th,
		#classReslut td{
		border:1px solid #d8d8d8;
		font-size: 12px;
        padding: 5px 3px;
		}
		.bgg2 {
        background: #f9f9f9;
        border: 1px solid #e2e2e2;
        box-sizing: border-box;
        }
        .btn {
		display:inline-block;
        font-size: 12px;
        margin: 0 3px 0 0;
        padding: 0 4px;
        box-sizing: content-box;
        }        
        .teacher_select{
        display: inline-block;
        width: 12.5%;
        margin: -31px 0 0;
        position: relative;
        top: 31px;
        }
        .timetable_div .teacher_select .chosen-container,
        .timetable_div .teacher_select .chosen-container:nth-child(2){
           width:100%!important;
           margin:0 0 5px;
        }
        .class_copy{
        margin:10px 0;
        }
        .class_copy>span{
        font-size:13px;
        font-weight:bold;
        line-height:25px;
        }
        .class_copy .btn{
        position:relative;
        top:2px;
        }
        .cc_select{
        display:inline;
        margin:5px 0;
        }
        .cc_select .chosen-container{
        width:150px!important;
        }
        #norAdmin .chosen-container-multi .chosen-choices li.search-field input[type="text"]{
        width:100%!Important;
        }
        #norAdmin .other_time{
        margin:3px 2px 0;
        background:#fff;
        cursor:pointer;
        }
  	</style>
 </head>
 <body>
 <div class="iStyle_RightInner">
	 <div class="navigation">
		 <div id="navigation">
			 <ul>
				 <li><a href="javascript:void(0)">排课管理</a>
				 </li>
				 <li class="end">
					 教学进度排课管理
				 </li>
			 </ul>
		 </div>
	 </div>
<div id="course_chzn" class="easyui-window " title="排课复制" align="left" title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 500px; height: 300px;">
	<div class="content-box">
		<table>
			<ul>
				<li>
					<b>选择将要复制的教学班</b>
				</li>
				<li>
				请选择教学班：
					<select id="course_chose" multiple="true" class="chosen-select">
		                <c:forEach items="${courseInfo.schoolCourses}" var="sc">
			                <c:if test="${sc.courseNo ne courseno}">
			                	<option value="${sc.courseNo}">${sc.courseNo}</option>
			                </c:if>
		                </c:forEach>
					</select>
					<input type="button" value="确定复制" onclick="confirm_save_all()">
				</li>
			</ul>
		</table>
	</div>
</div>
<%--    <div class="class_copy">
	    <span>请选择将要复制的教学班：</span>
	    <div id="course_chzn" class="easyui-window " title="排课复制"  title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 50%;height:55px;float:left;">
		    
		    <div class="cc_select">
						<select id="course_chose" class="chosen-select">
							<c:forEach items="${courseInfo.schoolCourses}" var="sc">
								<c:if test="${sc.courseNo ne courseno}">
									<option value="${sc.courseNo}">${sc.courseNo}</option>
								</c:if>
							</c:forEach>
						</select>
						<input class="btn" type="button" value="确定复制" onclick="confirm_save_all()">
	    </div>
	</div> --%>

	<div id="course_date" class="easyui-window " title="排课日历"  title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 50%;height:55px;float:right;padding-top:12px;">
		
		<div class="content-box">
			<table class="tb" id="my_show">
				<thead>
				<tr>
					<th>周次</th>
					<th>星期</th>
					<th>日期</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${schoolWeeks}" var="curr">
					<tr>
						<td>${curr.week}</td>
						<td>${curr.weekday}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${curr.date.time}"/></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

    <div class="tab pignose-tab-mint tab" style="margin:0;margin-top: 5px;border-radius: 5px;overflow: visible;border: 1px solid #9BA0AF;">
                    <%--<ul class="bgg p10 mb20 cf b1">
                        <li class="l mr15">
                            <dl>
                                <dt class="f14">课程名称: </dt>
                                <dd>$s{courseInfo.courseName }</dd>
                            </dl>
                        </li>
                        <li class="l mr15">
                            <dl>
                                <dt class="f14">教务系统排课安排: </dt>
                                <dd>${coursePlan }</dd>
                            </dl>
                        </li>
                        <li class="l mr15">
                            <dl>
                                <dt class="f14">学时:</dt>
                                <dd>总学时：${totalhour}</dd>
                                <dd>实验学时：${courseInfo.theoreticalHours }</dd>
                            </dl>
                        </li>
                        <li class="l mr15">
                            <dl>
                                <dd><a view-student>查看学生名单</a></dd>
                            </dl>
                        </li>
                        <li class="l mr15">
                            <dl>
                                <dt class="f14">上课地点:</dt>
                                <dd></dd>
                            </dl>
                        </li>
                        <li class="l mr15">
                            <dl>
                                <dt class="f14">排课日历:</dt>
                                <dd><a view-date>查看本课程排课日历</a></dd>
                            </dl>
                        </li>
                    </ul>
                    
                    
                    --%>
						<div class="course_scheduling">						
									<div class="title">
			                          <div id="title">排课结果</div>
									</div>
                            <div class=" cf  pa5">
			                            <table  id="classReslut" class="w100p">
			                                <thead>
			                                  <th>序号</th>
			                                  <th>上课地点</th>
                                              <th>周次</th>
			                                  <th>星期</th>
			                                  <th>节次</th>
											  <th>课程内容</th>
											  <th>教师</th>
											  <th>课程形式</th>
			                                  <th>操作</th>
			                                </thead>
			                                <tbody>
			                                	<c:forEach items="${appointments }" var="currapp" varStatus="i">
					                                <tr>
					                                <td>${i.count}</td>
					                                <td>
					                                		<c:forEach var="entry" items="${currapp.timetableLabRelateds}">  
																${entry.labRoom.labRoomName}  
															</c:forEach>
					                                	</td>
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
														${currapp.content}
														<td>
					                                		<c:forEach var="entry" items="${currapp.timetableTeacherRelateds}">  
																${entry.user.cname}[${entry.user.username}]
															</c:forEach>
					                                	</td> 
					                                	  <td>
	                                                  <c:if test="${currapp.classType==0||currapp.classType==null}">
													                    理论
													       </c:if>
													     <c:if test="${currapp.classType==1}">
													                    实验
													       </c:if>
													     <c:if test="${currapp.classType==2}">
													                    示教
													       </c:if>
													     <c:if test="${currapp.classType==3}">
													                 临床实习     
													       </c:if>
													       <c:if test="${currapp.classType==4}">
													                 研讨
													       </c:if>
													          <c:if test="${currapp.classType==5}">
													                 导师组活动
													       </c:if>
													          <c:if test="${currapp.classType==6}">
													                 毕业论文指导
													       </c:if>
													         <c:if test="${currapp.classType==7}">
													               理论+实验
													       </c:if>
													       </td>
					                                	</td>
					                                	<td>
					                                		<a del-app='${currapp.id }' class="fa fa-trash-o g9 f14 mr5 poi"></a>
					                                	</td>
					                                </tr>
			                                	</c:forEach>
				                                <tr id="tr_last">
				                                </tr>
			                                </tbody>
			                            </table>
			                    <div class="">
		                            <div class="cf mt10 mb10">
		                            <!-- 该功能暂时不需要，引掉  -->
		                                <input class="other_time r h25 lh25 b1 br5 plr10 btn" id="altButton" type="button" onclick="changeWeekDay()" value="非教务时间排课">
		                                <input class="other_time r btn" id="saveButton" type="button" onclick="save_all()" value="确认排课">
		                            </div>
		                        </div>
		                        <div id="admin">
                            <c:set var="tid" value="0"></c:set>
									<c:forEach items="${doAdminTimetableDTOList}" var="current" varStatus="k">
	                            <%--<c:forEach items="${schoolCourseDetails }" var="current" varStatus="i">--%>
		                            <%--<c:forEach begin="${current.startWeek }" end="${current.endWeek }" var="currweek">--%>
		                            <!-- 判断是否已经被排课  -->
		                            <c:set var="isDone" value="0"></c:set><!-- 是否排课标志位  1 已排课 0 未排课 -->
		                            <c:forEach items="${appointments }" var="currapp">
		                            	<!-- 如果周次和星期都一致，则已排课 -->
		                            	<c:if test="${current.dATWeek eq currapp.startWeek && current.dATWeekday eq currapp.weekday }">
				                            <c:set var="isDone" value="1"></c:set>
		                            	</c:if>
		                            </c:forEach>
			                            <c:if test="${isDone eq 0}"> <!-- 只呈现未排课的计划 -->
			                            	<c:set var="tid" value="${tid+1}"></c:set> <!--  计算每一行的id -->
			                            	<div class="timetable_div" id="doTimetable${tid}">
												<select data-placeholder="上课地点" id="mulLab${tid}" class="chosen-select" multiple tabindex="4" style="">
												<option value=""><c:forEach items="${labRoomMap}" var="currlab"  varStatus="i"></option>
				                                    <c:if test="${currlab.value eq schoolCourseDetail.classroomType}">
										    	       <option value ="${currlab.key}" selected>${currlab.value} </option>
				                                    </c:if>
				                                    <c:if test="${currlab.value ne schoolCourseDetail.classroomType}">
										    	       <option value ="${currlab.key}">${currlab.value} </option>
				                                    </c:if>
											        </c:forEach>
				                                </select>
												<select class="chosen-select" id="mulWeek${tid}" tabindex="2">
													<option value="${current.dATWeek}">第${current.dATWeek}周</option>
												</select>
												<select class="chosen-select" id="mulWeekday${tid}" tabindex="2">
				                                    <option value="${current.dATWeekday }">星期${current.dATWeekday }</option>
				                                </select> 
				                                <select disabled="disabled" data-placeholder="节次"  id="mulClass${tid}" class="chosen-select" multiple tabindex="4" style="width: 59px;">
					                                <c:forEach begin="${current.dATStartClass }" end="${current.dATEndClass }"  var="currclass">
					                                    <option value="${currclass }" selected>${currclass}</option>
					                                </c:forEach>    
				                                </select>
											<%-- <input  type="text"   value="第${currweek}周" disabled/> --%>

				                                	<select data-placeholder="课程内容" id="mulCourse${tid}" class="chosen-select" multiple tabindex="4" style="width: 150px;">
				                                    <option value=""></option>
				                                    <c:forEach items="${outlineCourses}" var="currcou"  varStatus="i">
				                                    <c:if test="${k.count eq currcou.courseTime}">
										    	       <option value ="${currcou.courseContent}" selected="selected" >${currcou.courseContent} </option>
				                                    </c:if>
				                                    <c:if test="${k.count ne currcou.courseTime}">
										    	       <option value ="${currcou.courseContent}">${currcou.courseContent} </option>
				                                    </c:if>
											        </c:forEach>
				                                </select> 
				                                <div class="teacher_select">
				                                <select data-placeholder="请选择教师1" onchange="altTeacher(1, this)"
													id="mulUser1${tid}" class="chosen-select" tabindex="2"
													class="option_teacher"style="width: 150px;">
													<%--<option value ="${user.username}" selected> ${user.cname}[${user.username}]</option>--%>
                                                        <option value ="${teacherCName}" selected> ${teacherCName}</option>
													<c:forEach items="${timetableTearcherMap}" var="curruser"
														varStatus="i">
														<%--<c:if test="${curruser.key != user.username }">
															<option value="${curruser.key}">${curruser.value}</option>
														</c:if>--%>
                                                        <c:if test="${curruser.key != teacherCName }">
                                                            <option value="${curruser.key}">${curruser.value}</option>
                                                        </c:if>
													</c:forEach>
												</select> 
												<br/>
												<select data-placeholder="请选择教师2" onchange="altTeacher(2, this)"
													id="mulUser2${tid}" class="chosen-select" tabindex="2"
													style="width:150px;">
													<%--<option value ="${user.username}" selected> ${user.cname}[${user.username}]</option>--%>
                                                        <option value ="${teacherCName}" selected> ${teacherCName}</option>
													<c:forEach items="${timetableTearcherMap}" var="curruser"
														varStatus="i">
														<%--<c:if test="${curruser.key != user.username }">
															<option value="${curruser.key}">${curruser.value}</option>
														</c:if>--%>
                                                        <c:if test="${curruser.key != teacherCName }">
                                                            <option value="${curruser.key}">${curruser.value}</option>
                                                        </c:if>
													</c:forEach>
												</select> 
												</div>
												<select class="chosen-select"
													data-placeholder='请选择课程类别：' name="classType"
													id="classType${tid}" style="width:100px" required="true">
													<option value=""></option>
													<c:set var="type" value="0"></c:set> <%--判断是否匹配了对应课程类别--%>
													<c:forEach items="${outlineCourses}" var="currcou"  varStatus="i">
														<c:if test="${k.count eq currcou.courseTime}">
															<c:forEach items="${curriculumNature}" var="ccn"  varStatus="i">
																<c:if test="${currcou.cDictionary.CNumber eq ccn.CNumber}">
																<option value ="${ccn.CNumber}" selected="selected">${ccn.CName} </option>
																</c:if>
																<c:if test="${currcou.cDictionary.CNumber ne ccn.CNumber}">
																	<option value ="${ccn.CNumber}">${ccn.CName} </option>
																</c:if>
															</c:forEach>
															<c:set var="type" value="1"></c:set>
														</c:if>
													</c:forEach>
												<c:if test="${type eq 0}"> <!-- 没有匹配课程类别则显示所有课程类别 -->
													<c:forEach items="${curriculumNature}" var="ccn"  varStatus="i">
														<option value ="${ccn.CNumber}">${ccn.CName} </option>
													</c:forEach>
												</c:if>
												</select> 
												<!-- 单独保存功能是正常的，只是怕客户看到引起歧义，所以引掉 -->
												<%--<a class="btn r" onclick="saveAdminTimetable(${tid}, 0)">保存</a>--%>
				                            </div>
				                        </c:if>    
		                           <%-- </c:forEach>--%>
	                            </c:forEach>
	                            </div>
	                        </div>
                       </div>
                       </div>
	                            <!-- 非教务时间排课，暂时引掉（复旦项目不需要） -->
	                            <div class="TabbedPanelsContent">
	                             <div class="content-box" id="norAdmin" style="margin:25px 0 0;">
		                            <div class="cf title ovh">
		                                <div class="other_time r h25 lh25 b1 br5 plr10" onclick="addApp()">添加排课</div>
		                            </div>
		                            <div id="doOtherTimetable">
		                             	<c:set var="tid" value="${tid+1}"></c:set> <!--  计算每一行的id -->
		                             	<!-- 由于教务保存和非教务保存排课相互影响，在提交教务保存时会同时提交非教务保存的DIV导致400报错 暂时非教务排课保存使用“保存按钮”不使用“确认排课” -->
		                             	<div class="timetable_div_2" id="doTimetable${tid}"style="min-height:72px;">
		                                <select data-placeholder="上课地点" id="norLab${tid}" class="chosen-select" multiple tabindex="4" class="">
	                                    <option value=""></option>
	                                    <c:forEach items="${labRoomMap}" var="currlab"  varStatus="i">
	                                    <c:if test="${currlab.value eq current.classroomType}">
							    	       <option value ="${currlab.key}" selected>${currlab.value} </option>
	                                    </c:if>
	                                    <c:if test="${currlab.value ne current.classroomType}">
							    	       <option value ="${currlab.key}">${currlab.value} </option>
	                                    </c:if>
								        </c:forEach>
				                          </select>
											<select class="chosen-select" id="norWeek${tid}" tabindex="2">
												<option value="0">周次</option>
												<c:forEach items="${schoolweek}" var="currweek">
													<option value="${currweek}">第${currweek}周</option>
												</c:forEach>
											</select>
											<select class="chosen-select" id="norWeekday${tid}" tabindex="2">
		                                    <option value="0">星期</option>
		                                	<c:forEach begin="1" end="7"  var="currday">
			                                    <option value="${currday }">星期${currday }</option>
			                                </c:forEach>
		                                </select>
											<select data-placeholder="节次"  id="norclasses${tid}" class="chosen-select" multiple tabindex="4" style="width: 59px;">
												<c:forEach begin="1" end="13"  var="currclass">
													<option value="${currclass }">${currclass }</option>
												</c:forEach>
											</select>


		                                <select data-placeholder="课程内容" id="norCourse${tid}" class="chosen-select" multiple tabindex="4" style="width: 150px;">
				                                    <option value=""></option>
				                                    <c:forEach items="${outlineCourses}" var="currcou"  varStatus="i">
				                                    <c:if test="${currweek eq currcou.week}">
										    	       <option value ="${currcou.courseContent}" selected="selected" >${currcou.courseContent} </option>
				                                    </c:if>
				                                    <c:if test="${currweek ne currcou.week}">
										    	       <option value ="${currcou.courseContent}">${currcou.courseContent} </option>
				                                    </c:if>
											        </c:forEach>
				                                </select> 
				                                <div class="teacher_select">
		                                   <%--<select data-placeholder="请选择教师1"
													id="norUser1${tid}" class="chosen-select" tabindex="2"
													class="option_teacher"style="width: 150px;">
													<option value ="${user.username}" selected> ${user.cname}[${user.username}]</option>
													<c:forEach items="${timetableTearcherMap}" var="curruser"
														varStatus="i">
														<c:if test="${curruser.key != user.username }">
															<option value="${curruser.key}">${curruser.value}</option>
														</c:if>
													</c:forEach>
												</select> 
												<br/>
												<select data-placeholder="请选择教师2" 
													id="norUser2${tid}" class="chosen-select" tabindex="2"
														style="width: 150px;">
													<option value ="${user.username}" selected> ${user.cname}[${user.username}]</option>
													<c:forEach items="${timetableTearcherMap}" var="curruser"
														varStatus="i">
														<c:if test="${curruser.key != user.username }">
															<option value="${curruser.key}">${curruser.value}</option>
														</c:if>
													</c:forEach>
												</select>

												</div>--%>
													<select class="chosen-select"
													data-placeholder='请选择课程类别：' name="classType"
													id="norclassType${tid}" style="width:100px" required="true">
														<c:set var="type" value="0"></c:set> <%--判断是否匹配了对应课程类别--%>
														<c:forEach items="${outlineCourses}" var="currcou"  varStatus="i">
															<c:if test="${currweek eq currcou.week}">
																<c:forEach items="${curriculumNature}" var="ccn"  varStatus="i">
																	<c:if test="${currcou.cDictionary.CNumber eq ccn.CNumber}">
																	<option value ="${ccn.CNumber}" selected="selected">${ccn.CName} </option>
																	</c:if>
																	<c:if test="${currcou.cDictionary.CNumber ne ccn.CNumber}">
																		<option value ="${ccn.CNumber}">${ccn.CName} </option>
																	</c:if>
																</c:forEach>
																<c:set var="type" value="1"></c:set>
															</c:if>
														</c:forEach>
														<c:if test="${type eq 0}"> <!-- 没有匹配课程类别则显示所有课程类别 -->
															<c:forEach items="${curriculumNature}" var="ccn"  varStatus="i">
																<option value ="${ccn.CNumber}">${ccn.CName} </option>
															</c:forEach>
														</c:if>
												</select>
		                            </div>		                            
												<div style="margin:0 10px 25px 0;text-align:right;">
		                                            <a class="btn" onclick="saveButton(${tid})">保存</a>
		                                            <a class="btn" onclick="cancleButton()">取消</a>
		                                        </div>
		                        </div>
                            </div>
                        </div>
 </div>

<script type="text/javascript">
    $(function () {
        $(".chosen-select").chosen({
            disable_search_threshold: 10,
            no_results_text: "Oops, nothing found!",
            width: "100"
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
    
 // 查看本课程安排日历
    $('[view-date]').each(function(i,e){
         $(e).on('click',function(){
	         layer.open({
             	type: 2,
             	title: '排课日历',
             	maxmin: true,
             	shadeClose: true, 
             	area : ['700px' , '350px'],
             	content: '${pageContext.request.contextPath }/timetable/listSchoolCourseDate?courseCode=${courseCode}'
             })  
	     });                                              
	});
    
  //查看本选课组下的学生
    $('[view-student]').each(function(i,e){
         $(e).on('click',function(){
             layer.open({
             	type: 2,
             	title: '查看本选课组下的学生',
             	maxmin: true,
             	shadeClose: true, 
             	area : ['700px' , '350px'],
             	offset:['300px','300px'],
             	content: '${pageContext.request.contextPath }/timetable/viewCourseStudent?courseCode=${courseno}'
             });  
         });                                              
    });
  	// 删除排课记录
	 $('[del-app]').each(function(i,e){
	    $(e).on('click',function(){
	      var _index=$(this);
	          swal({
                    title: "您确定要删除这条信息吗",
                    text: "删除后将无法恢复，请谨慎操作！",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "删除",
                    closeOnConfirm: false
	    }, function () {
	       $.ajax({ 
	            url: '${pageContext.request.contextPath}/timetable/deleteAppointmentAjaxAndReturn?id='+$(e).attr('del-app'), 
	            type: 'POST'
	        }).done(function(data) {
               swal("操作成功!", "已成功删除数据！", "success");
               $(e).parent().parent().fadeOut(2000);
	        }).error(function(data) { 
	            swal("OMG", "删除操作失败了呢!", "error"); 
	        }); 
	    });
	  });                                              
	});
  	
  	// 全局变量
 	var do_count = 0;// 页面排课标志位
 	// 保存按钮
    function saveAdminTimetable(tid, flag){// flag 0 单独保存  1 批量保存
        var weeks = $('#mulWeek'+tid).val();
 		var classes = $('#mulClass'+tid).val()+'';
 		var weekday = $('#mulWeekday'+tid).val()+'';
 		var labRooms = $('#mulLab'+tid).val()+'';
 		//var items = $('#mulItem'+tid).val()+'';
 		var teachers = $('#mulUser1'+tid).val()+'';
 		var teachers2 = $('#mulUser2'+tid).val()+'';
 		var classType = $('#classType'+tid).val();
 		var content = $('#mulCourse'+tid).val()+'';
 		console.log(weeks);
 		if(flag==0){
	 		if (labRooms=='null'){
	 			alert('请选择实验室');
	 			return false;
	 		}
	 		if (content=='null'){
	 			alert('请选择课程内容');
	 			return false;
	 		}
 		}
 		var myData = {term:"${term}", classes:classes, labRooms:labRooms, weekday:weekday, 
            	weeks:weeks, teachers:teachers, courseNo:${courseNo}, isOther:"0",
            	teachers2:teachers2,classType:classType,content:content,courseCopy:$('#course_chose').val()};
            	console.log(myData);
    	$.ajax({
            url:'${pageContext.request.contextPath}/timetable/saveSchoolTimetable',
            type:'POST',
             dataType:'text',
            data:myData,
            async: false,
            error:function(XMLHttpRequest, textStatus, errorThrown) {
 				alert(XMLHttpRequest.status);
 				alert(XMLHttpRequest.readyState);
 				alert(textStatus);
   				},
            success:function(data){
            	if(data != '-1' && data != 'empty'){
            		$('#course_chzn').window('close');
                	$('#doTimetable'+tid).css('display','none');
                	// 被选的排课内容
                    var mulWeek = $('#mulWeek'+tid).find("option:selected").text().trim();
                    var mulClass = $('#mulClass'+tid).val();
                    var mulWeekday = $('#mulWeekday'+tid).find("option:selected").text().trim();
                    var mulLab = $('#mulLab'+tid).find("option:selected").text().trim();
                    var mulUser = $('#mulUser1'+tid).find("option:selected").text().trim()+" "+$('#mulUser2'+tid).find("option:selected").text().trim();
                    var mulItem = $('#mulCourse'+tid).find("option:selected").text().trim();
                    var typeString = $('#classType'+tid).find("option:selected").text().trim();
                    do_count = do_count+1;
                    var list_count = do_count+${appointments.size()};
                  	// 排课结果呈现
                    var $td1=$("<td>"+mulWeek+"</td>");
                    var $td2=$("<td>"+mulWeekday+"</td>");
                    var $td3=$("<td>"+mulClass+"</td>");
                    var $td4=$("<td>"+mulLab+"</td>");
                    var $td5=$("<td>"+mulUser+"</td>");
                    var $td6=$("<td>"+mulItem+"</td>");
                    var $td7=$("<td>"+typeString+"</td>");
                    var $td8=$("<td><a onclick='deleteApp("+data+","+tid+", this)' class='fa fa-trash-o g9 f14 mr5 poi'><a></td>");
                    var $tr=$("<tr></tr>");
                    $tr.append($("<td>"+list_count+"</td>"));
                    $tr.append($td4);
                    $tr.append($td2);
                    $tr.append($td3);
                    $tr.append($td1);
                    $tr.append($td6);
                    $tr.append($td5);
                    $tr.append($td7);
                    $tr.append($td8);
                    $("#tr_last").before($tr);
                    
            	}else{

            			if(data == 'empty')
            				alert("请检查空值");
            			else{
            			}
                }
            			// 批量保存的时候，不做任何提醒


            	
         }
      });
    }
 	
 	// 删除记录
  	function deleteApp(id, tid , obj){
  		 console.log($(this));
         swal({
               title: "您确定要删除这条信息吗",
               text: "删除后将无法恢复，请谨慎操作！",
               type: "warning",
               showCancelButton: true,
               confirmButtonColor: "#DD6B55",
               confirmButtonText: "删除",
               closeOnConfirm: false
		   }, function () {
		      $.ajax({ 
		           url: '${pageContext.request.contextPath}/timetable/deleteAppointmentAjaxAndReturn?id='+id,
		           type: 'POST'
		       }).done(function(data) { 
		    	   do_count = do_count - 1;// 页面排课标志位
		           swal("操作成功!", "已成功删除数据！", "success"); 
		           $(obj).parent().parent().fadeOut(2000);
		           $('#doTimetable'+tid).css('display','');
		       }).error(function(data) { 
		           swal("OMG", "删除操作失败了呢!", "error"); 
		       }); 
		   });
    }
    
 // 删除记录
  	function deleteOtherApp(id,obj){
  		 console.log($(this));
         swal({
               title: "您确定要删除这条信息吗",
               text: "删除后将无法恢复，请谨慎操作！",
               type: "warning",
               showCancelButton: true,
               confirmButtonColor: "#DD6B55",
               confirmButtonText: "删除",
               closeOnConfirm: false
		   }, function () {
		      $.ajax({ 
		           url: '${pageContext.request.contextPath}/timetable/deleteAppointmentAjax?id='+id,
		           type: 'POST'
		       }).done(function(data) { 
		           swal("操作成功!", "已成功删除数据！", "success"); 
		           $(obj).parent().parent().fadeOut(2000);
		       }).error(function(data) { 
		           swal("OMG", "删除操作失败了呢!", "error"); 
		       }); 
		   });
    }
</script>
</body>
</html>