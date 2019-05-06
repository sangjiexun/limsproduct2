<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta name="decorator" content="iframe" />
<!-- 弹窗 -->
  <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
<script langauge="javascript">
	function subform(gourl){ 
		 form.action=gourl;
		 form.submit();
	}
</script> 
<style>
	table tr td {
    text-align: left;
	}
</style>
 </head>
<body>
 <!--消息内容弹出框-->
  <div class="right-content">
  <div class="title">
	<div id="title">${labRoom.labRoomName}</div>
	
  </div>
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
		
<div class="content-box">				
<div id="contentarea">
<div id="content">
<div class="content-box">
	<form id="formid" method="POST">
			<div class="content-box">
				<table>
					<tr>
						<input type="hidden" id="deviceType" name="deviceType">
						<td colspan="2">选择日期
							<input  class="easyui-datebox"  id="reservationtime" name="reservationtime"  type="text"  onclick="new Calendar().show(this);"/>
						预约开始时间
							<input  class="easyui-timespinner"  id="starttime" name="starttime" type="text"  style="width:80px;"
    required="required" />
						预约结束时间
							<input  class="easyui-timespinner"  id="endtime" name="endtime"  type="text" style="width:80px;"
    required="required"/>
						</td> 
					</tr>
					<tr>
					<th><a onclick="findRestStations(${labRoom.id})">查询</a></th>
					<td>剩余工位数：<input id="restStations" type="text" readonly="readonly"/><font style="color: red">请选择准确时间以查询剩余的工位数量</font></td>
					</tr>
					<tr>
						<th>预约原因</th>
						<td>
							<textarea id="reason" type="text" style="height: 100px;width: 400px;"></textarea>
						</td>
						<%--<c:if test="${dean eq 1}">系主任这一级的刷选逻辑修改了，不需要自己选
						<th>选择审核系主任</th>
						<td>
						<select id="dean" style="height: 25px;width: 80px;">
						<c:forEach items="${deanList}" var="s">
						<option value="${s.username}">${s.cname}</option>
						</c:forEach>
						</select>
						</td>
						</c:if>
					--%></tr>
					<tr>
									<th style="min-width:120px;">添加学生<a class="btn btn-common" href='javascript:void(0)'	onclick='newStudents()'>选择添加</a>
									<br/>(或输入学生学号，以逗号分开)
									</th>
									<td><textarea rows="" cols=""
										name="students" id="students"
										style="width: 400px;height: 60px;padding: 5px"></textarea></td>
								</tr>
				</table>
				<div>
					<input type="button" value="提交" onclick="saveLabRoomReservation(${labRoom.id})">
				</div>
			</div>
	</form>	
</div>
</div>
</div>
</div>
</div>
</div>
</div>
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
         	<c:if test="${s.grade>2010 }">
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
//保存预约的标志位
var flag = 0;
  var config = {
   '.chzn-select'      : {search_contains:true},
   '.chzn-select-deselect' : {allow_single_deselect:true},
   '.chzn-select-no-single' : {disable_search_threshold:10},
   '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
   '.chzn-select-width'   : {width:"95%"}
  }
  for (var selector in config) {
   $(selector).chosen(config[selector]);
  }
//ajax查询班级用户列表  
  function putSchoolClassesUser(){ 		
  	 var obj = document.getElementsByName("username");
  	 var s='';//如果这样定义var s;变量s中会默认被赋个null值
       for(var i=0;i<obj.length;i++){
           if(obj[i].checked) //取到对象数组后，我们来循环检测它是不是被选中
           s+=obj[i].value+",";   //如果选中，将value添加到变量s中    
       }
       var str = $('#students').val() +"," +s;
       $('#students').val(s);
       $("#newStudents").window('close');
  }
//ajax查询班级用户列表  
  function getSchoolClassesUser(classNumber){ 		
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
  function getSchoolClasses(grade){ 		
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
  		           tableStr = tableStr + "<td><a class='btn btn-common' href='javascript:void(0)' onclick='getSchoolClassesUser("+ key +")'>" + values + "</a></td><tr>";
  			   }else  if(currLine%3==1){
  			       tableStr = tableStr + "<tr><td><a class='btn btn-common' href='javascript:void(0)' onclick='getSchoolClassesUser("+ key +")'>" + values + "</a></td>";
  			   }
  			   else  if(currLine%3==2){
  			       tableStr = tableStr + "<td><a class='btn btn-common' href='javascript:void(0)' onclick='getSchoolClassesUser("+ key +")'>" + values + "</a></td>";
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
//弹出选择学生窗口
  function newStudents() {
      var sessionId=$("#sessionId").val();
      //获取当前屏幕的绝对位置
      var topPos = window.pageYOffset;
      //使得弹出框在屏幕顶端可见
      $('#newStudents').window({left:"0px", top:topPos+"px"}); 
      $("#newStudents").window('open');
  }
  //查询剩余工位数
 function findRestStations(labRoomId){
	//console.log($("input[name='reservationtime']") .val());
	 if($("input[name='reservationtime']") .val() == ""){
		 alert("请选择日期");
		 return false;
	 }
	 if($("#starttime").val()){
	 }else{
		 alert("请选择开始时间");
		 return false;
	 }
	 if($("#endtime").val()){
	 }else{
		 alert("请选择结束时间");
		 return false;
	 }
	 if($("#starttime").val() > $("#endtime").val()){
		 alert("开始时间不可大于结束时间");
		 return false;
	 }
	 
	 var myData = {
		 'reservationTime':$("input[name='reservationtime']") .val(),
		 'startTime':$("#starttime").val(),
		 'endTime':$("#endtime").val()
	 }
	 $.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/LabRoomReservation/findRestStations?labRoomId="+labRoomId,
		data: myData,
		dataType:'json',
		success:function(data){
			$("#restStations").val(data);
		},
		error:function(){
  			alert("查询失败！后台出了点问题！");
  		} 
 	})
 }
//保存实训室预约
function saveLabRoomReservation(labRoomId){
	//console.log($("input[name='reservationtime']") .val());
	var dean = "${dean}";
	var dean1 = $('#dean option:selected').val();
	if(flag == 1 ){
		 alert("正在保存预约，请勿重复提交！");
		 return false;
	 }
	//更改标志位
	flag = 1;
	 if($("input[name='reservationtime']") .val() == ""){
		 alert("请选择日期");
		 flag = 0;
		 return false;
	 }
	 if($("#starttime").val()){
	 }else{
		 alert("请选择开始时间");
		 flag = 0;
		 return false;
	 }
	 if($("#endtime").val()){
	 }else{
		 alert("请选择结束时间");
		 flag = 0;
		 return false;
	 }
	 if($("#students").val() == ""){
		 alert("学生不可为空");
		 flag = 0;
		 return false;
	 }
	 if($("#starttime").val() > $("#endtime").val()){
		 alert("开始时间不可大于结束时间");
		 flag = 0;
		 return false;
	 }
	 /*if(dean == "1"){-----------------系主任这一级的刷选逻辑修改了，不需要自己选
		 if($('#dean option:selected').val()){
		 }else{
			 alert("请选择审核系主任");
			 flag = 0;
			 return false;
		 }
	 }else{
		 dean1 = -1;
	 }*/
	 
	 var myData = {
		 'reservationTime':$("input[name='reservationtime']") .val(),
		 'startTime':$("#starttime").val(),
		 'endTime':$("#endtime").val(),
		 'reason':$("#reason").val(),
		 'students':$("#students").val(),
		 //'dean':dean1
	 }
	 $.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/LabRoomReservation/saveLabRoomReservationTeacher?labRoomId="+labRoomId,
		data: myData,
		dataType:'text',
		success:function(data){
			console.log(data);
			if(data=="over"){
				alert("预约失败，您选的时间段工位数不够！");
			}else if(data=="overMax"){
                alert("预约失败，超过系统设置的最大可预约数！");
            }else if(data=="success1"){
				alert("预约成功，等待相关人员审核");
				flag = 0;
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.close(index);//关闭弹窗
			}else if(data=="success2"){
				alert("预约成功，二级实训室无需审核");
				flag = 0;
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.close(index);//关闭弹窗
			}else{
				layer.msg('预约失败！'+data+'不是合法学号', {icon: 5});
			}
			flag = 0;
		},
		error:function(){
  			alert("预约失败！后台出了点问题！");
  			flag = 0;
  		}
 	})
}


</script>
<!-- 下拉框的js -->
</body>
</html>