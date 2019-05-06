<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta name="decorator" content="iframe" />
<script type="text/javascript" src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<script langauge="javascript">
	function subform(gourl){ 
		 form.action=gourl;
		 form.submit();
	}
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
						<th>选择日期</th>
						<td>
							<input  class="easyui-datebox"  id="reservationtime" name="reservationtime"  type="text"  onclick="new Calendar().show(this);"/>
						</td>
						<th>预约开始时间</th>
						<td>
							<input  class="easyui-timespinner"  id="starttime" name="starttime" type="text"  style="width:80px;"
    required="required" />
						</td>
						<th>预约结束时间</th>
						<td>
							<input  class="easyui-timespinner"  id="endtime" name="endtime"  type="text" style="width:80px;"
    required="required"/>
						</td> 
					</tr>
					<tr>
					<td><a onclick="findRestStations(${labRoom.id})">查询</a></td>
					<td>剩余工位数：<input id="restStations" type="text" readonly="readonly"/><font style="color: red">请选择准确时间以查询剩余的工位数量</font></td>
					</tr>
					<tr>
						<th>预约原因</th>
						<td>
							<textarea id="reason" type="text" style="height: 150px;width: 300px;"></textarea>
						</td>
						<c:if test="${labRoom.labRoomLevel eq 1 and teacherAudit eq '是' }">
						<th>选择审核导师</th>
						<td>
						<%-- <select id="teacher" style="height: 25px;width: 80px;">
						<c:forEach items="${teacherList}" var="s">
						<option value="${s.username}">${s.cname}</option>
						</c:forEach>
						</select> --%>
						<select   id="teacher" class="chzn-select" data-placeholder="Choose a Country" style="width:350px;" tabindex="1"> 
							<c:forEach items="${teacherList}" var="s">
								<option value="${s.username}">${s.cname}</option>
							</c:forEach>
						</select>
	
				    	</td>
						</c:if>
						<%--<c:if test="${dean eq 1}">系主任这一级的刷选逻辑修改了，不需要自己选
						<th>选择审核系主任</th>
						<td>
						<select id="dean" style="height: 25px;width: 80px;">
							<c:forEach items="${deanList}" var="s">
								<option value="${s.username}">${s.cname}</option>
							</c:forEach>
						</select>
						 <select class="chzn-select" data-placeholder="Choose a Country" style="width:350px;" tabindex="1"> 
							<c:forEach items="${deanList}" var="s">
								<option value="${s.username}">${s.cname}</option>
							</c:forEach>
						</select> 
						</td>
						</c:if>--%>
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
  //查询剩余工位数
 function findRestStations(labRoomId){
	 //alert($('#dean option:selected').val());
	 //console.log($("input[name='reservationtime']") .val());
	 if($("input[name='reservationtime']").val() == ""){
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
	 //var dean = "${dean}";
	 var teacherAudit = "${teacherAudit}";
	 var dean1 = $('#dean option:selected').val();
	 var teacherAudit1 = $('#teacher option:selected').val();
	 if(flag == 1 ){//
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
	 if($("#starttime").val() > $("#endtime").val()){
		 alert("开始时间不可大于结束时间");
		 flag = 0;
		 return false;
	 }
	 if("${labRoom.labRoomLevel}" =="1" && teacherAudit == "是"){
		 if($('#teacher option:selected').val()){
		 }else{
			 alert("请选择审核导师");
			 flag = 0;
			 return false;
		 }
	 }else{
		 teacherAudit1 = -1;
	 }
	 
	 /*if(dean == "1"){
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
		 'teacher':teacherAudit1,
		 //'dean':dean1
	 }
	 $.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/LabRoomReservation/saveLabRoomReservation?labRoomId="+labRoomId,
		data: myData,
		dataType:'text',
		success:function(data){
			console.log(data);
			if(data=="over"){
				alert("预约失败，您选的时间段工位数不够！");
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