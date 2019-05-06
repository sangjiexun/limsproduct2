<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="now" class="java.util.Date" />
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>


<html>
<head>
<title></title>
<meta name="decorator" content="iframe"/>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no"/>
<style type="text/css" media="screen">
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/icon.css");
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/gray/easyui.css");
			@import url("${pageContext.request.contextPath}/css/style.css");
			@import url("${pageContext.request.contextPath}/static_limsproduct/css/global_static.css");
</style>
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">

<!-- 文件上传的样式和js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/labRoom/editDeivce.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#print").click(function(){
	$("#my_show").jqprint();
	});
});	
</script>

<script>
//新增或编辑考试文件夹
function newTestFolder(deviceId,folderId,status){
	for(var i=0;i<23;i++){
		array[i]=0;
		}
	//$('.searchable-select').remove();
	$("#editTestFolder").window('open');
	if(status==1){
		alert("此考试已发布,确定要修改？");
		//return false;
	}
	if(folderId != -1){
		$.ajax({
			async:false, 
    		type: "POST",
    		url: $("#contextPath").val()+"/tcoursesite/editTestFolder",
    		data: {'folderId':folderId},
    		dataType:'json',
    		success:function(data){
    			$.each(data,function(key,values){  
    				//alert(key+"="+values);
    				//document.getElementById(key).innerHTML=values;
    				
    				if(key=="trString"){
    					$("#itemBody").children().last().before(values);
    				}else{
    					$("#"+key).val(""+values);
    				}
    				
    			 }); 
    		},
    		error:function(){
    			alert("信息错误！");
    			}
    	});
	}else{
		var myDate = nowTime();
		$("#testTitle").val("");
		$("#testId").val(null);
		$("#testAnswerAssignId").val(null);
		$("#testControlId").val(null);
		$("#testTAssignmentControl_startdate").val(myDate);
		$("#testTAssignmentControl_duedate").val(myDate);
		$("#testContent").val(null);
		$("#testStatus").val(0);
		$("#testCreatedTime").val(myDate);
		$("#testScoreTest").val(100);
		$("#testContent").val("");
	}

}
//保存考试之前的验证
function checkForm(){
	var result = 0;
	var questionIdArray = new Array();
	var typeArray = new Array();
	var quantityArray = new Array();
	var scoreArray = new Array();
	$("input[name='itemTypeTest']").each(function(i,value){
		typeArray[i] = $(this).val().trim();
	})
	if (typeArray.length==0) {
		alert("请添加组成试卷的试题！");
		return false;
	}
	$("input[name='itemQuantityTest']").each(function(i,value){
		if ($(this).val().trim()==""||isNaN($(this).val().trim())) {
			alert("试题数量不能为空且必须为数字");
			$(this)[0].focus();
			result = 1;
			return false;
		}
		quantityArray[i] = $(this).val().trim();
	})
	if (result == 1) {
		return false;
	}
	$("input[name='itemScoreTest']").each(function(i,value){
		if ($(this).val().trim()==""||isNaN($(this).val().trim())) {
			alert("试题分值不能为空且必须为数字");
			$(this)[0].focus();
			result = 1;
			return false;
		}
		scoreArray[i] = $(this).val().trim();
	})
	if (result == 1) {
		return false;
	}
	$.ajax({
		url:$("#contextPath").val()+'/teaching/test/checkItemQuantity?questionIdArray='+questionIdArray+'&typeArray='+typeArray+'&quantityArray='+quantityArray,
		type:'post',
		async:false,  // 设置同步方式
        cache:false,
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(data){
			if(data['result']!=""){
				alert(data['result']);
				result = 1;
			}
		}
	}); 
	if (result == 1) {
		return false;
	}
	var totalScore = 0;
	 $.each(quantityArray,function(i,value){
	 	totalScore += quantityArray[i]*scoreArray[i];
	 })
	 if(totalScore!=Number($("#testScoreTest").val().trim())){
	 	alert("已添加试题分值为"+totalScore+"分，与考试分值不符，请检查！");
	 	return false;
	 }
}
</script>


<style>
.tool-box2 th{text-align:left;}
</style>
</head>


<body>
	<%--
	<div class="tool-box1">
		<table>
			<tr>
				<th>设备编号</th><td>${device.schoolDevice.deviceNumber}</td>
				<th>设备名称</th><td>${device.schoolDevice.deviceName}</td>
			</tr>
			<tr>
				<th>所在实验室</th><td>${device.labRoom.labRoomName}</td>
				<th>生产国别</th><td>${device.schoolDevice.deviceCountry}</td>
			</tr>
			<tr>
				<th>仪器型号</th><td>${device.schoolDevice.devicePattern}</td>
				<th>生产厂家</th><td>${device.schoolDevice.manufacturer}</td>
			</tr>
		</table>
	</div>
	--%>
	<div id="TabbedPanels1" class="TabbedPanels">
	<%--<ul class="TabbedPanelsTabGroup">
	<li class="TabbedPanelsTab" tabindex="0">
		<c:if test="${device.CDictionaryByAllowSecurityAccess.CCategory=='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber=='1' && device.CDictionaryByTrainType.CNumber == '1'}">
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editDeviceTrainingRest(${device.id})">培训计划</a>
		</li>
		</c:if>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="openSetupLink(${device.id},${currpage })">参数设置</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomImageRest(${device.id},${currpage })">相关图片</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomVideoRest(${device.id},${currpage })">相关视频</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomDocumentRest(${device.id},${currpage })">相关文档</a>
		</li>
		<c:if test="${(device.CDictionaryByAllowSecurityAccess.CCategory =='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber == '1' && device.CDictionaryByTrainType.CNumber == '3')}">
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="${pageContext.request.contextPath}/tcoursesite/question/findQuestionList?tCourseSiteId=${device.id}"target="_blank">题库</a>
		<a href="#" onclick="findLabRoomQuestionList(${device.id})">题库</a>
		</li>
		<li class="TabbedPanelsTab selected" tabindex="0">
		<a href="#" onclick="viewfindTestList(${device.id})">考试</a>
		</li>
		</c:if>
	</ul>
	  --%><div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			
			<div class="content-box">
				<div class="title">
				<div id="title">考试</div>
				<%--<a class="btn btn-new"  herf="#" onclick="openSetupLink();">返回</a>
				--%><a class="btn btn-new"  href="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1" >返回</a>
				</div>											     
				<table  id="my_show">
				<thead>
				    <tr>                   
				        
				        <th>标题</th>
				     	<th>开始日期</th>
				        <th>过期日期</th>
				        <th>考试</th>
				    </tr>
				</thead>
				<tbody>
				<c:forEach items="${testList}" var="current"  varStatus="i">
				<c:if test="${current.status==1}">
				<c:if test="${now> current.TAssignmentControl.startdate.time&&now< current.TAssignmentControl.duedate.time}">
				<tr>
					
				    <td>${current.title}
				    </td>
				    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${current.TAssignmentControl.startdate.time}" type="both"/></td>
				    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${current.TAssignmentControl.duedate.time}" type="both"/></td>
				    <c:if test="${current.submitTimeForStudent==0&&isCreate==0}">
				    <td><a href="${pageContext.request.contextPath}/teaching/test/beginLabRoomTest?testId=${current.id}&labRoomId=${labRoomId}" >开始考试</a></td>
					</c:if>
					<c:if test="${current.submitTimeForStudent>0&&isCreate==0}">
					<td><a href="${pageContext.request.contextPath}/teaching/test/findTestDetail?testId=${current.id}" >考试详情</a></td>
					</c:if>
				</tr>
				</c:if>
				</c:if>
				</c:forEach>
				</tbody>
				<!-- 分页导航 -->
				</table>
				<input type="hidden" id="labRoomId" name="labRoomId" value="${labRoomId}">
			<input type="hidden" id="deviceName" name="deviceName" value="${deviceName}">
			<input type="hidden" id="deviceNumber" name="deviceNumber" value="${deviceNumber}">
			<input type="hidden" id="username"  name="username"value="${username}">
			<input type="hidden" id="page"  name="page"value="${page}">
    		<input type="hidden" id="pageContext" name="pageContext" value="${pageContext.request.contextPath }">
				</div>
		</div>	
	  </div>
	</div>

</body>
</html>
