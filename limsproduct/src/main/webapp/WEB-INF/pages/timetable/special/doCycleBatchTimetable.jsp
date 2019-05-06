<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE jsp:directive.include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
 <head>
	<meta name="decorator" content="none" />
	<link href="${pageContext.request.contextPath}/static/css/lib.css" rel="stylesheet" type="text/css" media="">
	<link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/global.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	
	<link href="${pageContext.request.contextPath}/chosen/chosen.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css"> 
	<link href="${pageContext.request.contextPath}/css/cmsSystem/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/iCheck/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/sweetalert/sweetalert.min.js"></script>
	
 </head>
 <style type="text/css">
 	#weekday_chzn,#classes_chzn{
 		width:150px !important;
 	}
 	input[type="checkbox"], input[type="radio"] {
  /*visibility: hidden;*/
  width: 14px;
  height: 14px;
    
  margin: 0;
  padding: 0; }
 	
 </style>
 <body>
<div class=" cf ">
    <div class="tab pignose-tab-mint tab">
        <ul>
            <li>
                <div class="">
                    <div class="course_scheduling">
                        <div>
                            <div class="tool_box brl5 cf bgg pa5">
                            	<div class="">
                            	<h2 class="pa5">填写分批数</h2>
                            	<input id="batch" name="batch" />
			                        <h2 class="pa5">实验项目(实验项目不能重复)</h2>
			                        <div class="tool_box brl5 cf ">
			                            <table id="classReslut" class="w100p">
			                                <thead>
			                                  <th>序号</th>
			                                  <th>实验项目</th>
			                                  <th>上课地点</th>
			                                  <th>教师</th>
			                                  <th>研究生</th>
			                                </thead>
			                                <tbody>
			                                	<c:forEach items="${cycleTimetableBycourseNo}" var="currapp" varStatus="status">
					                                <tr>
					                                	<td>${status.index+1}</td>
					                                	<td>${currapp.operationItem.lpName}</td>
					                                	<td>${currapp.labRoom.labRoomName}</td>
					                                	<td>
					                                	${currapp.userByTeacher.cname}
					                                	</td>
					                                	<td>
					                                	${currapp.userByTutor.cname}
					                                	</td>
					                                </tr>
			                                	</c:forEach>
				                                <tr>
				                                </tr>
			                                </tbody>
			                            </table>
			                        </div>
			                    </div>
	                            <div class="">
		                            <div class="cf mt10">
		                             <c:if test="${empty istimetable}">
		                                <div class="other_time r h25 lh25 b1 br5 plr10" onclick="addApp()">添加排课预约</div>
		                                  <a class="btn r" onclick="confirmButton()">确认</a>
		                                  </c:if>
		                            </div>
		                            <div id="doSelfTimetable">
		                                <select data-placeholder="实验项目" id="items" class="chosen-select" multiple tabindex="4" class="">
										    <c:forEach items="${timetableItem}" var="curritem"  varStatus="i">
									           <option value ="${curritem.id}"> ${curritem.lpName}</option>
										    </c:forEach>
		                                </select>
		                                	<select data-placeholder="上课地点" id="labRooms" class="chosen-select" multiple tabindex="4" class="">
		                                    <option value=""></option>
		                                    <c:forEach items="${labRoomMap}" var="currlab"  varStatus="i">
								    	       <option value ="${currlab.key}" >${currlab.value} </option>
									        </c:forEach>
		                                </select>
		                                <select data-placeholder="请选择教师" id="user" class="chosen-select" tabindex="2" class="">
		                                   <%--  <option value ="${user.username}" selected> ${user.cname}[${user.username}]</option> --%>
										    <c:forEach items="${timetableTearcherMap}" var="curruser"  varStatus="i">
										        <c:if test="${curruser.key != user.username }">
										           <option value ="${curruser.key}"> ${curruser.value}</option>
										        </c:if>
										    </c:forEach>
		                                </select>
		                                  <select data-placeholder="请选指导员" id="tutor" class="chosen-select" tabindex="2" class="">
		                                   <%--  <option value ="${user.username}" selected> ${user.cname}[${user.username}]</option> --%>
										    <c:forEach items="${studentMap}" var="curruser"  varStatus="i">
										        <c:if test="${curruser.key != user.username }">
										           <option value ="${curruser.key}"> ${curruser.value}</option>
										        </c:if>
										    </c:forEach>
		                                </select>
		                                <a class="btn r" onclick="cancleButton()">取消</a>
		                                <a class="btn r" onclick="saveButton()">保存</a>
		                            </div>
		                        </div>
		                           <h2 class="pa5">循环分批分组分组</h2>
		                           <table id="group" class="w100p">
			                                <thead>
			                                  <th>序号</th>
			                                   <th>批次</th>         
			                                  <th>组名</th>
			                                  <th>人数</th>
			                                  <th><input type="button"value="编辑分组" onclick="editGroup()"/></th>
			                                </thead>
			                                <tbody>
			                                	<c:forEach items="${timetableGroupBycourseNo}" var="currapp" varStatus="status">
					                                <tr>
					                                	<td>${status.index+1}</td>
					                                	<td>${currapp.timetableBatch.batchName}</td>
					                                	<td>${currapp.groupName}</td>
					                                	<td>${currapp.numbers}</td>
					                                </tr>
			                                	</c:forEach>
				                                <tr>
				                                </tr>
			                                </tbody>
			                            </table>
			                               <form:form action="${pageContext.request.contextPath}/timetable/saveEditGroup?courseNo=${courseNo}&courseCode=${courseCode}&type=2" id="edit_form"  method="post" >
			                                  <table id="editgroup" class="w100p">
			                                <thead>
			                                  <th>序号</th>
			                                   <th>批次</th>         
			                                  <th>组名</th>
			                                  <th>人数</th>
			                                </thead>
			                                <tbody>
			                                		<c:forEach items="${timetableGroupBycourseNo}" var="currapp" varStatus="status">
					                                <tr>
					                                	<td>${status.index+1}</td>
					                                	<td>${currapp.timetableBatch.batchName}</td>
					                                	<td><input id="groupName" name="groupName"  value="${currapp.groupName}" /></td>
					                                	<td><input id="number" name="number"  value="${currapp.numbers}" /></td>
					                                	<input type="hidden" id="groupId" name="groupId"  value="${currapp.id}" />
					                                </tr>
			                                	</c:forEach>
			                                	<input type="hidden" id="groupCount" name="groupCount"  value="${cyccount}" />
			                                	<tr>
			                                	<td><input type="submit"value="保存" /></td>
			                                	</tr>
			                                </tbody>
			                            </table>
			                            </form:form>
			                             <h2 class="pa5">周次选择(选择周次数量与组数和项目数相同)</h2>
			                             <ul id="weeks">
			                             <c:forEach items="${schoolweek}" var="currapp">
			                             <li class="l mlr10">
			                             	<input type="checkbox" checked="checked" id="${currapp}"/>
			                             	 <label for="${currapp }">第${currapp }周</label>
			                             </li>
			                             </c:forEach>
			                             </ul>
			                              <c:if test="${empty istimetable}">
			                             <a class="btn r" onclick="saveTimetable()">确认排课</a>
			                             </c:if>
			                             <c:if test="${!empty istimetable}">
			                              <a class="btn r" onclick="showTimetable()">查看排课</a>
			                              </c:if>
                            </div>
                        </div>
                    </div>
                </div>
              
            </li>
        </ul>
    </div>
</div>
 <script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"></script>
  <script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"></script>
<script type="text/javascript">
	//排课的周次信息
	if(${selectedWeeks}.length>0){
	var selectedWeeks = ${selectedWeeks};
	$("input[type='checkbox']").prop("checked",false);
	for(var i=0;i<selectedWeeks.length;i++){
	        $("input:checkbox[id='"+selectedWeeks[i]+"']").prop("checked",true);
	  }
	}
	// 循环排课保存
	function saveTimetable(){
	var selectedweek=[];
	$($("#weeks input:checked")).each(function(){
		selectedweek.push(this.id)
	})
	//保证周次数量和实验项目数量一致
	if(selectedweek.length==${cyccount}){
	var weeks =selectedweek.join(",");
		$.ajax({
               url:'${pageContext.request.contextPath}/timetable/saveCycleTimetableAppointment',
               type:'POST',
               data:{weeks:weeks, courseNo:${courseNo},courseCode:${courseCode},flag:2},
               error:function (request){
                 alert('请求错误!');
               },
               success:function(data){
               	if(data != '-1'){
               		location.href="${pageContext.request.contextPath}/timetable/listGeneralTimetable1?courseNo="+${courseNo}+"&isBatch=1";
               	}else{
               		alert("该时间段已经被其他排课占用，请您添加其他时间段排课");
               	}
            	}
         });  
	}else{
	alert("周次选择数量不一致");
	}
	}
     
    $(function () {
    	// 课程安排框，默认不显示
    	$('#doSelfTimetable').css('display','none');
    	$('#editgroup').css('display','none');
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
    
	// 添加排课
	function addApp(){
		$('#doSelfTimetable').css('display','');
		//清空div中select框里的选中值
		$("#items option").each(function (i) {
                $(this).prop("selected", "");
            		}
            		);
            $("#items").trigger("liszt:updated");
            $("#labRooms option").each(function (i) {
                 $(this).prop("selected", "");
            		}
            		);
            $("#labRooms").trigger("liszt:updated");
            $("#user option").each(function (i) {
                 $(this).prop("selected", "");
           		 }
           		 );
            $("#user").trigger("liszt:updated");
            $("#tutor option").each(function (i) {
                 $(this).prop("selected", "");
           	 		}
           	 		);
            $("#tutor").trigger("liszt:updated");
	}
 function showTimetable(){
     window.location.href="${pageContext.request.contextPath}/timetable/listGeneralTimetable1?&courseNo="+${courseNo}+"&weeks="+weeks+"&isBatch=1";
    }
    
 	// 保存按钮
    function saveButton(){
        var count=0;
 		var labRoom = $('#labRooms').val().toString();
 		var items = $('#items').val().toString();
 		var teachers = $('#user').val().toString();
 		var teachers2 = $('#tutor').val().toString();
   		$.ajax({
               url:'${pageContext.request.contextPath}/timetable/saveCycleTimetable',
               type:'POST',
               data:{labRoom:labRoom, item:items, 
               	 teachers:teachers, teachers2:teachers2,courseNo:${courseNo}},
               error:function (request){
                 alert('请求错误!');
               },
               success:function(data){
               	if(data != '-1'){
               		$('#doSelfTimetable').css('display','none');
                   	// 被选的排课内容
                   	var countText =count+1
                       var labText = $('#labRooms').find("option:selected").text().trim(); 
                       var userText = $('#user').find("option:selected").text().trim();
                       var itemText = $('#items').find("option:selected").text().trim();
                       var tutorText = $('#tutor').find("option:selected").text().trim();
                     	// 排课结果呈现
                     	var $td1=$("<td>"+countText+"</td>");
                       var $td2=$("<td>"+labText+"</td>");
                       var $td3=$("<td>"+userText+"</td>");
                       var $td4=$("<td>"+itemText+"</td>");
                       var $td5=$("<td>"+tutorText+"</td>");
                       var $tr=$("<tr></tr>");
                       $tr.append($td1);
                       $tr.append($td4);
                       $tr.append($td2);
                       $tr.append($td3);
                       $tr.append($td5);
                       $("#classReslut").find("tr:last").before($tr);
                        $("#items option[value="+data+"]").remove(); //删除Select中Value=data 的Option
               			$("#items").trigger("liszt:updated");
                       //$("tr:last").before($tr);
               	}else{
               		alert("该时间段已经被其他排课占用，请您添加其他时间段排课");
               	}
            	}
         });
}
     
     // 保存按钮
     function confirmButton(){
     var batchCount=$("#batch").val();
     window.location.href="${pageContext.request.contextPath}/timetable/saveBatchCycleGroup?courseCode="+${courseCode}+"&courseNo="+${courseNo}+"&batchCount="+batchCount;
     }
    /* function confirmButton(){
        var count=0;
         //获得分批数量
		var batchCount=$("#batch");
		
            		$.ajax({
                        url:'${pageContext.request.contextPath}/timetable/saveCycleGroup',
                        type:'POST',
                        data:{courseCode:${courseCode},courseNo:${courseNo},batchCount:batchCount},
                        error:function (request){
                          alert('请求错误!');
                        },
                        success:function(data){
                        	if(data != null){
                        	//循环排课分组呈现
                        	for(var i=0;i<data.length;i++){
                              	var $td1=$("<td>"+data[i].count+"</td>");
                                var $td2=$("<td>"+data[i].groupId+"</td>");
                                var $td3=$("<td>"+data[i].groupName+"</td>");
                                var $td4=$("<td>"+data[i].groupNumbers+"</td>");
                                var $tr=$("<tr></tr>");
                                $tr.append($td1);
                                $tr.append($td2);
                                $tr.append($td3);
                                $tr.append($td4);
                                $("#classReslut").find("tr:last").before($tr);
                                $("tr:last").before($tr);
                        	}
                     	  }
                     	}
                  });
         } */
     
 	
 	// 取消按钮
 	function cancleButton(){
 		$('#doSelfTimetable').css('display','none');
 	}
 	// 编辑按钮
 	function editGroup(){
 		$('#group').css('display','none');
 		$('#editgroup').css('display','');
 	}
 	
</script>

</body>
</html>