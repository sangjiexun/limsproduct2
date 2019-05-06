<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/device/editDevice.js"></script>
<style>
.tool-box2 th{text-align:left;}
</style>
</head>


<body>
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
	<div id="TabbedPanels1" class="TabbedPanels">
	 <ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" tabindex="0">
		<%--<a href="${pageContext.request.contextPath}/device/editDeviceInfo?id=${device.id}">设备详情</a>
		--%><a href="javascript:void(0);" onclick="editDeviceInfoRest(${device.id})">设备详情</a>
		</li>
		<%-- <c:if test="${device.CActiveByAllowSecurityAccess.id == 1}"> --%>
		<c:if test="${(device.CDictionaryByAllowSecurityAccess.CCategory =='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber == '1' && device.CDictionaryByTrainType.CNumber == '1')}">
		<li class="TabbedPanelsTab" tabindex="0">
		<%--<a href="${pageContext.request.contextPath}/device/deviceTraining?deviceId=${device.id}">培训计划</a>
		--%><a href="javascript:void(0);" onclick="editDeviceTrainingRest(${device.id})">培训计划</a>
		</li>
		</c:if>
		<li class="TabbedPanelsTab" tabindex="0">
		<%--<a href="${pageContext.request.contextPath}/device/editDeviceSetting?id=${device.id}">参数设置</a>
		--%><a href="javascript:void(0);" onclick="editDeviceSettingRest(${device.id})">参数设置</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<%--<a href="${pageContext.request.contextPath}/device/deviceImage?deviceId=${device.id}">相关图片</a>
		--%><a href="javascript:void(0);" onclick="editDeviceImageRest(${device.id})">相关图片</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0"><%--
		<a href="${pageContext.request.contextPath}/device/deviceVideo?deviceId=${device.id}">相关视频</a>
		--%><a href="javascript:void(0);" onclick="editDeviceVideoRest(${device.id})">相关视频</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<%--<a href="${pageContext.request.contextPath}/device/deviceDocument?deviceId=${device.id}">相关文档</a>
		--%><a href="javascript:void(0);" onclick="editDeviceDocumentRest(${device.id})">相关文档</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<%--<a href="${pageContext.request.contextPath}/device/dimensionalCode?deviceId=${device.id}">二维码</a>
		--%><a href="javascript:void(0);" onclick="editDeviceDimensionalCodeRest(${device.id})">二维码</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<%--<a href="${pageContext.request.contextPath}/tcoursesite/question/findQuestionList?tCourseSiteId=${device.id}">题库</a>
		--%><a href="#" onclick="findQuestionList(${device.id})">题库</a>
		</li>
		<li class="TabbedPanelsTab selected" tabindex="0">
		<a href="#" onclick="findTestList(${device.id})">考试</a>			
		</li>
	</ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			
			<div class="content-box">
				<div class="title">
				<div id="title">考试</div>
				<a class="btn btn-new"  herf="#" onclick="openSetupLink();">返回</a>
				<a class="btn btn-new" href="${pageContext.request.contextPath}/teaching/test/newTest?deviceId=${device.id}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&username=${username}">新增考试</a>
				</div>											     
				<table  id="my_show">
				<thead>
				    <tr>                   
				        <th>序号</th>
				        <th>标题</th>
				        <th>创建时间</th>
				        <th>状态</th>
				        <%--<th>已提交</th>
				        --%><th>开始日期</th>
				        <th>过期日期</th>
				        <th>评分方式</th>
				        <th>已提交学生成绩表</th>
				    </tr>
				</thead>
				<tbody>
				<c:forEach items="${testList}" var="current"  varStatus="i">
				<tr>
					<td>
				        ${i.count }
				        <%--<a href="${pageContext.request.contextPath}/teaching/test/creatNewText?testId=${current.id}" >
					       		<button>生成试卷</button>
					       	</a>
				    --%></td>
				    <td>${current.title}<br>
				    <c:if test="${current.status==0}">
				    		<a href="${pageContext.request.contextPath}/teaching/test/editTest?deviceId=${deviceId}&testId=${current.id}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&username=${username}" >编辑考试</a>|
					        <a onclick="history.go(0)" href="${pageContext.request.contextPath}/teaching/test/deployTest?deviceId=${device.id}&testId=${current.id}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&username=${username}">发布考试</a>|
					        <a onclick="return confirm('是否确认删除测试？')" href="${pageContext.request.contextPath}/teaching/test/deleteTestById?deviceId=${device.id}&testId=${current.id}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&username=${username}">删除考试</a>					      
				    </c:if>
				    </td>
				    <td><fmt:formatDate pattern="yyyy-MM-dd" value="${current.createdTime.time}" type="both"/></td>
				    <td>
					    <c:if test="${current.status==1}">
					                      已发布
					   	</c:if>
					    <c:if test="${current.status==0}">
					                      未发布
					    </c:if>				    
				    </td><%--
				       
				    <td>
				    	<mytag:JspSecurity realm="check" menu="exam">
					       	<a href="${pageContext.request.contextPath}/teaching/test/testGradingList?testId=${current.id}" onclick="return checkSubmitNumber(${current.tAssignGradeSubmitCount})">
					       		${current.tAssignGradeSubmitCount }
					       	</a>
						</mytag:JspSecurity>       	
				    </td>
				    --%><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${current.TAssignmentControl.startdate.time}" type="both"/></td>
				    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${current.TAssignmentControl.duedate.time}" type="both"/></td>
				    <td><font >0--${current.TAssignmentAnswerAssign.score }</font></td>
				    <td><a href="${pageContext.request.contextPath}/teaching/test/testGradingList?deviceId=${device.id}&testId=${current.id}" target="_blank">查看</a></td>
				</tr>
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
