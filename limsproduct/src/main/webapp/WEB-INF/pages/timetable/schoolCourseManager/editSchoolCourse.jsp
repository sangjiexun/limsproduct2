 <%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
 <jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script> 
  <script type="text/javascript">
  //提交表单
  function submitBookUpload(){
   /*  if($("#bookName").val()=="")
    {
      alert("请填写书名！");
      return false;
    } */
    
    
    document.center_form.action="${pageContext.request.contextPath}/timetable/saveSchoolCourse?isEdit=${isEdit}&flag=${flag}";
    document.center_form.submit();
  }
  </script>
</head>
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">选课程管理</a></li>
			<li class="end"><c:if test="${!isEdit}"><a href="javascript:void(0)">编辑</a></c:if>
			                <c:if test="${isEdit}"><a href="javascript:void(0)">新建</a></c:if></li>
			
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title"><c:if test="${!isEdit}">编辑</c:if><c:if test="${isEdit}">新建</c:if>选课程信息</div>
      <input type="button" onclick="back()" value="返回" />
	</div>
	<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<div class="TabbedPanelsContentGroup1">
			<div class="TabbedPanelsContent">	
		<form:form name="editform" action="${pageContext.request.contextPath}/timetable/saveSchoolCourse?isEdit=${isEdit}&flag=${flag}" method="post" modelAttribute="schoolCourse">
			<div class="content-box">
				<table> 
				 <input type="hidden" name="flag" id="flag" value="${flag}" />
				  <input type="hidden" name="courseNo" id="courseNo" value="${courseNo}" />
				 
					<tr>
						<th>课程库课程</th>
						<td>
							<form:select class="chzn-select" path="schoolCourseInfo.courseNumber" style="width:200px" required="true">
							    <c:forEach items="${schoolCourseInfoList}" var="current" >
							         <form:option value ="${current.courseNumber}"> ${current.courseName}</form:option>
							         </c:forEach>
						     </form:select>
						</td>
					
					 <th>课程类型</th>
						<td>
						<c:if test="${flag==1}">
							<input
							id="coursetypes" name="coursetypes" style="width:145px" 
							value="理论课" readonly />
							<form:input type="hidden" path="courseType" value="1" />
							</c:if>
							<c:if test="${flag==2}">
							<input
							id="coursetypes" name="coursetypes" style="width:145px" 
							value="实验课" readonly />
							<form:input type="hidden" path="courseType" value="2" />
							</c:if>
						</td>
						
						 <th>学期</th>
						<td>
							<select class="chzn-select" id="schoolTermd" name="schoolTermd" style="width:200px" required="true" >
							    <c:forEach items="${schoolTermList}" var="current"  varStatus="i">
							    <c:if test="${schoolCourse.schoolTerm.id eq current.id}">
							         <option value ="${current.id}" selected> ${current.termName}</option>
							         </c:if>
							        <c:if test="${schoolCourse.schoolTerm.id ne current.id}">
							         <option value ="${current.id}" > ${current.termName}</option>
							         </c:if>
							    </c:forEach>
						     </select>
						</td>
					</tr>
					
					<tr>	
					   <th>选课组名称</th>
							<td>
							<form:input
							path="courseName" name="courseName" style="width:145px" />
							
						</td>
						<th>上课地点</th>
						<td>
							<select class="chzn-select" id="labRoomId" name="labRoomId" style="width:200px" required="true">
							    <c:forEach items="${labRooms}" var="current"  varStatus="i">
							    <c:if test="${schoolCourse.labRoom.id eq current.id}">
							         <option value ="${current.id}" selected> ${current.labRoomName}</option>
							         </c:if>
							        <c:if test="${schoolCourse.labRoom.id ne current.id}">
							         <option value ="${current.id}" > ${current.labRoomName}</option>
							         </c:if>
							    </c:forEach>
						     </select>
						</td>
						<th>课程负责教师</th>
						<td>
						 <select name="teacher" id="teacher" class="chzn-select"  style="width:200px" required="true">
										<c:forEach items="${teacheres}" var="d">
										  <c:if test="${schoolCourse.userByTeacher.username eq d.username}">
							        	 <option value ="${d.username}" selected> ${d.cname}</option>
							         		</c:if>
							         		  <c:if test="${schoolCourse.userByTeacher.username  ne d.username}">
											<option value="${d.username}">${d.cname}</option>
											</c:if>
											</c:forEach>
										
								   </select>
								   <input  type="button" value="添加" onclick="addcourse()" />   
								   </td>
								  
						</tr>
					  <%--  <th>开始周次</th>
						<td>
						<input
							id="startWeek" name="startWeek" style="width:145px" value="${Sweek}" />
						</td>
						  <th>结束周次</th>
						<td>
						<input
							id="endWeek" name="endWeek" style="width:145px" value="${Eweek}" />
						</td>
					</tr>
					<tr>	
						<th>星期</th>
						<td><input id="weekday" name="weekday" style="width:145px"  value="${Wday}"  />
						</td>
						<th>开始节次</th>
						<td><input id="startClass" name="startClass" style="width:145px" value="${Sclass}"  />
						</td>
						<th>结束节次</th>
						<td><input id="endClass" name="endClass" style="width:145px" value="${Eclass}" />
						</td> --%>
					
						<c:forEach items="${schoolCourseDetails}" var="cur">
						<tr>
						<td>开始周:${cur.startWeek}</td>
						<td>结束周:${cur.endWeek}</td>
						<td>星期:${cur.weekday}</td>
						<td>开始节次:${cur.startClass}</td>
						<td>结束节次:${cur.endClass}</td>
						<td><input type="button" value="删除" onclick="deldetail('${cur.courseDetailNo}')"/></td>
						</tr>
						</c:forEach>
						<table id="two_file">
						 </table>
						 <tr>
						 <input type="hidden" id="count" name="count" value="${count}" />
						</tr>
								
					
				<%-- 	<tr>
						<th>学生名单(请先保存选课组)</th>
						<td colspan="3"><textarea rows="" cols=""
							name="student" id="student" placeholder="输入学生学号，逗号请用英文符号：10059898,10068656(已经添加学生编号(${courseStudents}))"
							style="width: 450px;height: 50px;padding: 5px" ></textarea>
							<a class="a-no4 mr20 mt75" onclick="addStudent();">点击添加</a>
							</td>
							<td><div>
								<input type="button"  value="保存学生" onclick="saveStudent()" />
						</div> </td>
					</tr> --%>
						<table id="listTable" width="50%" cellpadding="0">
						<tr>
						    <c:if test="${!isEdit}">
						<td>学生名单</td>
						<td><a class="btn btn-common" href='javascript:void(0)'	onclick='newStudents()'>选择添加</a>
						</td>
						</c:if>
						<c:if test="${isEdit}">
						<td colspan=2><font color=red>提交保存后，进行学生选课名单添加。</font></td>
						</c:if>
						</tr>
						<tr>
							<td colspan=2>
							<%request.setAttribute("n","\n");%>
						<c:if test="${!isEdit}">
						<textarea name="students" id="students" cols="104" /><c:forEach items="${schoolCourseStudents}" var="current">
						${current.userByStudentNumber.username }</c:forEach></textarea>
						<td><font color=red>如果编辑选课组请将框内学生名单删除，再保存否则会产生重复名单！</font></td>
						</c:if>
							</td>
						</tr>
						
						
						<tr>
							<td>
						<input type="button" onclick="savecourse()" value="保存选课组" />
						</td>
						</tr>
						</table>
					
				</table>
				
			</div>	
		</form:form>
		
		</div></div></div></div>
		
		<!-- 弹出选择学生窗口 -->
		<div id="newStudents" class="easyui-window" title="选择添加学生" modal="true" dosize="true" maximizable="true" collapsible="true" minimizable="false" closed="true" iconcls="icon-add" style="width:800px; height:600px;">
			<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
			
			<div class="content-box">
			<form:form action="" method="post">
			<fieldset class="introduce-box">
		         <legend>年级信息</legend>
		         <div>
		         <table id="listTable" width="85%" cellpadding="0">
		          <tr><td><b>选择年级</b></td><tr>
		          <tr>
		         	<td>
		         	<c:forEach items="${grade }" var="s" varStatus="i">
		         	<c:if test="${s.grade>'2010' }">
					 <a class='btn btn-common' href='javascript:void(0)' onclick='getSchoolClasses(${s.grade})' >${s.grade}</a>	
					</c:if>
					</c:forEach></td>
		         </tr>
		         </table>
		         </div>
		         <div id="schoolClasses"></div>
		         <div id="schoolClassesUser"></div>
			</fieldset>
			</form:form>
			</div>
			</div>
			</div>
			
		</div>
	<!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		    var config = {
		      '.chzn-select': {search_contains : true},
		      '.chzn-select-deselect'  : {allow_single_deselect:true},
		      '.chzn-select-no-single' : {disable_search_threshold:10},
		      '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
		      '.chzn-select-width'     : {width:"95%"}
		    }
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    }
		    /* // 选择的学生
		    if(${courseStudents}!=-1){
		    document.getElementById("student").value="${courseStudents}";
		    } */
		    
		    var count =0;
		     function addStudent(){
		    	queryUser(1);
			    $('#addAdmin').window({left:"100px", top:"100px"});
			    $("#addAdmin").window('open');
		    }
		    
		    			function queryUser(page){
				if (page!=0) {
					var cname=document.getElementById("cname").value;
					var username=document.getElementById("username").value;
				}else {
					page=1;
					document.getElementById("cname").value="";
					document.getElementById("username").value="";
					var cname="";
					var username="";
				}
				$.ajax({
				           url:"${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&roomId=${labId}&page="+page,// 排除已存在于该实验室的管理员
				           type:"POST",
				           success:function(data){//AJAX查询成功
				                  document.getElementById("user_body").innerHTML=data;
				           }
				});
			}
		    
			function addUser(){
				var usernameString="";
				var flag; //判断是否一个未选   
				$("input[name='CK_name']:checkbox").each(function() { //遍历所有的name为CK_name的 checkbox  
					if ($(this).prop("checked")) { //判断是否选中    
						flag = true; //只要有一个被选择 设置为 true  
						usernameString+=$(this).val()+","; //将选中的值 添加到 array中 
					}  
				})
				usernameString = usernameString.substring(0,usernameString.length-1);
				
				var setUsernames;
				if($('#student').val() == null || $('#student').val() == ''){
					setUsernames = usernameString;
				}else{
					setUsernames = $('#student').val()+','+usernameString;
				}
				
				$('#student').val(setUsernames);
				$("#addAdmin").window('close');
			}
			
			function saveStudent(){
			var students = $('#students').val();
			window.location.href="${pageContext.request.contextPath}/timetable/saveStudent?courseNo="+'${courseNo}'+"&students="+students;
			}
			
			
			function back(){
			window.location.href="${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=1&id=-1&flag="+${flag};
			}
			
			
			
			//弹出选择学生窗口
function newStudents() {
    var sessionId=$("#sessionId").val();
    //获取当前屏幕的绝对位置
    var topPos = window.pageYOffset;
    //使得弹出框在屏幕顶端可见
    $('#newStudents').window({left:"0px", top:topPos+"px"}); 
    $("#newStudents").window('open');
}

//ajax查询班级用户列表  
getSchoolClassesUser = function(classNumber){ 
	$.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/timetable/selfTimetable/getSchoolClassesUser",
		data: {'classNumber':classNumber},
		dataType:'json',
		success:function(data){
			var jslength=1;
			var currLine=1;
			var allLine=1;
			for(var js2 in data){jslength++;}
			if(jslength==0){alert("本周无课程数据");}else{}

			var tableStr="<table id='listTable' width='80%' cellpadding='0'><tr><td><b>选择学生</b></td><td colspan=3><input class='btn btn-primary btn-lg' type='button' onclick='putSchoolClassesUser()' value='提交'/></td></tr>";//新建html字符
			$.each(data,function(key,values){  
			   if(currLine%4==0){
		           tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td><tr>";
			   }else  if(currLine%4==1){
			       tableStr = tableStr + "<tr><td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
			   }else  if(currLine%4==2){
			       tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
			   }else if(currLine%4==3){
			       tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
			   }
			   //currLine=currLine%4;
			   jslength=jslength+1;
			   currLine = currLine +1;
			   allLine =allLine+1;
			 }); 
			 if(currLine%4==0){
			   tableStr = tableStr + "</table>";
			 }else if(currLine%4==1){
			   tableStr = tableStr + "<td>&nbsp;</td><td>&nbsp;</td><td&nbsp;></td></tr></table>";
			 }else if(currLine%4==2){
			   tableStr = tableStr + "<td>&nbsp;</td><td>&nbsp;</td></tr></table>";
			 }else if(currLine%4==3){
			   tableStr = tableStr + "<td>&nbsp;</td></tr></table>";
			 }
			
			 document.getElementById('schoolClassesUser').innerHTML=tableStr; 	
		},
		error:function(){
			//alert("加载课表失败!");
			}
	});
}
	
	//ajax查询用户的班级表  
getSchoolClasses = function(grade){ 		
	$.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/timetable/selfTimetable/getSchoolClasses",
		data: {'grade':grade},
		dataType:'json',
		success:function(data){
			var jslength=1;
			var currLine=1;
			for(var js2 in data){jslength++;}
			if(jslength==0){alert("本周无课程数据");}else{}
			var tableStr="<table id='listTable' width='80%' cellpadding='0'><tr><td colspan=3><b>选择班级</b></td></tr>";//新建html字符
			$.each(data,function(key,values){  
			   if(currLine%3==0){
		           tableStr = tableStr + "<td><a class='btn btn-common' href='javascript:void(0)' onclick=\"getSchoolClassesUser('"+ key +"')\">" + values + "</a></td><tr>";
			   }else  if(currLine%3==1){
			       tableStr = tableStr + "<tr><td><a class='btn btn-common' href='javascript:void(0)' onclick=\"getSchoolClassesUser('"+ key +"')\">" + values + "</a></td>";
			   }
			   else  if(currLine%3==2){
			       tableStr = tableStr + "<td><a class='btn btn-common' href='javascript:void(0)' onclick=\"getSchoolClassesUser('"+ key +"')\">" + values + "</a></td>";
			   }
			   currLine=currLine+1
			   jslength=jslength+1;
			 }); 
			 tableStr = tableStr + "</tr></table>";
			 document.getElementById('schoolClasses').innerHTML=tableStr; 	
		},
		error:function(){
			//alert("加载课表失败!");
			}
	});
}
//ajax查询班级用户列表  
function putSchoolClassesUser(){ 		
	 var obj = document.getElementsByName("username");
	 var s='';//如果这样定义var s;变量s中会默认被赋个null值
     for(var i=0;i<obj.length;i++){
         if(obj[i].checked) //取到对象数组后，我们来循环检测它是不是被选中
         s+=obj[i].value+'\n';   //如果选中，将value添加到变量s中    
     }
     var str = $('#students').val() +'\n' +s;
     $('#students').val(str);
     $("#newStudents").window('close');
}

//拼凑HTML，添加课程内容
function addcourse() { 	
	count=count+1;
   $.ajax({
	url:"${pageContext.request.contextPath}/timetable/getcourse?count="+count,
	type:'GET',
	dataType:"html",
    success:function(json){
       $("#two_file").append(json);
	    	},
			error:function(){
				alert("课程添加异常");
				}
		});	
} 
//删除课程内容
function delcourse(c) { 
   $("tr[id=cou"+c+"]").remove();
} 

	function savecourse(){

				document.editform.action="${pageContext.request.contextPath}/timetable/saveSchoolCourse?count="+count+"&isEdit=${isEdit}&flag=${flag}";
			document.editform.submit();
			}
   function deldetail(coursedetailNo){
       location.href="${pageContext.request.contextPath}/timetable/deleteCoursedetail?coursedetailNo="+coursedetailNo+"&courseNo=${courseNo}";
   }
		</script>
	<!-- 下拉框的js -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
