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
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/font/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
	<script src="${pageContext.request.contextPath}/layui/layui.js"></script>
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
<script langauge="javascript">
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        //时间范围选择
        laydate.render({
            elem: '#reservationTime'
            ,type: 'time'
            ,range: true //或 range: '~' 来自定义分割字符
            ,trigger : 'click'
        });
    });
	function subform(gourl){ 
		 form.action=gourl;
		 form.submit();
	}
</script> 
<style type="text/css">
	.layui-input {
		display: inline;
	}
	.layui-laydate .layui-this {
		background-color: #409eff!important;
		color: #fff!important;
	}
    .layui-laydate-list.laydate-time-list > li{width:50% !important;}
    .layui-laydate-list.laydate-time-list > li:last-child{display:none !important;}
	.f40{
		font-size:40px;
	}
	.l{
		float:left;
		padding:17px;
	}
	.cg{
		color:grey;
	}
	.cgn{
		color:#1d9d74;
	}
	.station_type span{
		height:14px;
		width:14px;
		display:inline-block;
	}
	.bg{
		background:grey;
	}
	.bgn{
		background:#1d9d74;
	}
	/*clear float*/
.cf:after{
    display:block; 
    content:"gvsun"; 
    height:0; 
    clear:both; 
    overflow:hidden; 
    visibility:hidden;}
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
						<td><font style="color: red">请选择准确时间以查询剩余的工位数量</font></td>
						<th>选择日期</th>
						<td>
							<input  class="easyui-datebox"  id="lendingTime" name="lendingTime"  type="text"  onclick="new Calendar().show(this);"/>
						</td>
						<th>预约时间</th>
						<td>
							<%--<input  class="easyui-timespinner"  id="starttime" name="starttime" type="text"  style="width:80px;"--%>
    <%--required="required" />--%>
								<input type="text" class="layui-input test-item" name="reservationTime" id="reservationTime" placeholder=" - ">
						</td>
						<%--<th>预约结束时间</th>--%>
						<%--<td>--%>
							<%--<input  class="easyui-timespinner"  id="endtime" name="endtime"  type="text" style="width:80px;"--%>
    <%--required="required"/>--%>
						<%--</td> --%>
						<td><a onclick="findRestStations(${labRoom.id})">查询</a></td>
					</tr>
								
					
				</table>
				<div class="station_type cf">
					<div class="l" style="margin-left:40%;">
						<span class="bg"></span>占用
					</div>
					<div class="l">
						<span class="bgn"></span>空闲
					</div>
					
				</div>
				<div class="station_container">
					<c:if test="${labRoom.labRoomCapacity != 0}">
						<c:forEach begin="1" end="${labRoom.labRoomCapacity }" var="i">
							<div class="l ">
								<i class="fa fa-laptop f40 cg"></i>
							</div>
						</c:forEach>
					</c:if>
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
	//console.log($("input[name='reservationtime']") .val());
//	 if($("input[name='reservationtime']") .val() == ""){
//		 alert("请选择日期");
//		 return false;
//	 }
     if($("input[name='lendingTime']") .val() == ""){
         alert("请选择日期");
         return false;
     }
//	 if($("#starttime").val()){
//	 }else{
//		 alert("请选择开始时间");
//		 return false;
//	 }
//	 if($("#endtime").val()){
//	 }else{
//		 alert("请选择结束时间");
//		 return false;
//	 }
//	 if($("#starttime").val() > $("#endtime").val()){
//		 alert("开始时间不可大于结束时间");
//		 return false;
//	 }
//
     if ($("#reservationTime").val()) {
     } else {
         alert("请选择时间");
         return false;
     }
	 var myData = {
//		 'reservationTime':$("input[name='reservationtime']") .val(),
//		 'startTime':$("#starttime").val(),
//		 'endTime':$("#endtime").val()
         'lendingTime': $("input[name='lendingTime']").val(),
         'reservationTime': $("#reservationTime").val()
	 }
	 $.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/LabRoomReservation/findRestStations?labRoomId="+labRoomId,
		data: myData,
		dataType:'json',
		success:function(data){
			for(var i=0;i<data;i++){
				$(".station_container").find("div").eq(i).find("i").removeClass("cg").addClass("cgn");
			}
		},
		error:function(){
  			alert("查询失败！后台出了点问题！");
  		} 
 	})
 }
//保存<spring:message code="all.trainingRoom.labroom" />预约
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
	 if(dean == "1"){
		 if($('#dean option:selected').val()){
		 }else{
			 alert("请选择审核系主任");
			 flag = 0;
			 return false;
		 }
	 }else{
		 dean1 = -1;
	 }
	 
	 var myData = {
		 'reservationTime':$("input[name='reservationtime']") .val(),
		 'startTime':$("#starttime").val(),
		 'endTime':$("#endtime").val(),
		 'reason':$("#reason").val(),
		 'students':$("#students").val(),
		 'dean':dean1
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
				alert("预约成功，二级实验室无需审核");
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