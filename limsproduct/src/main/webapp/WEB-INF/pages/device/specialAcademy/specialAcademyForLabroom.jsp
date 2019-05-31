<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
<meta name="decorator" content="iframe"/>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式 -->	
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>

<script type="text/javascript">
	//首页
	function first(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//末页
	function last(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//上一页
	function previous(url){
		var page=${page};
		if(page==1){
			page=1;
		}else{
			page=page-1;
		}
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	//下一页
	function next(url){
		var totalPage=${pageModel.totalPage};
		var page=${page};
		if(page>=totalPage){
			page=totalPage;
		}else{
			page=page+1
		}
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
function cancelQuery(){
	if(${tag==0}){
		window.location.href="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId="+${roomId }+"&page=1";
	}
	if(${tag==1}){
		window.location.href="${pageContext.request.contextPath}/device/listLabRoomDevice?page=1";
	}
}		

//AJAX验证是否通过安全准入
function Access(id){
	$.ajax({
	           url:"${pageContext.request.contextPath}/device/securityAccess?id="+id,
	           type:"POST",
	           success:function(data){//AJAX查询成功
	           		if(data=="success"){
	           			var labRoom_id = $("#labRoom_id").val();
	           		    var schoolDevice_deviceNumber = $("#schoolDevice_deviceNumber").val();
	           		    var schoolDevice_deviceName =$("#schoolDevice_deviceName").val();
	           		    var username = $("#username").val();
	           		 	var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
	           		    //alert(schoolDevice_deviceName);
	           			if($("#labRoom_id").val()==""){
	           			  labRoom_id ="-1";
	           			}
	           			if($("#schoolDevice_deviceNumber").val()==""){
	           			  schoolDevice_deviceNumber ="-1";
	           			}
	           			if($("#schoolDevice_deviceName").val()==""){
	           			  schoolDevice_deviceName ="-1";
	           			}
	           			if($("#username").val()==""){
	           				username ="-1";
	           			}
	           			if($("#schoolDevice_allowAppointment").val()==""){
	           				schoolDevice_allowAppointment ="-1";
	           			}
	           			var url = "${pageContext.request.contextPath}/device/doDeviceReservation/" + labRoom_id + "/"+ schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username +"/${page}/" + id+"/"+1+"/"+schoolDevice_allowAppointment;
	           			//alert(url);
	           			window.location.href=url;
	           			//alert("您已经通过安全准入验证"+data);
	                  	//window.location.href="${pageContext.request.contextPath}/device/reservationDevice?id="+id;
	           		}else if(data=="error"){
	           			alert("您还未通过培训,请先预约培训!");
	           			//window.location.href="${pageContext.request.contextPath}/device/findAllTrainingByDeviceId?deviceId="+id;
	           		}else if(data=="errorType2"){
	           			alert("您还未通过单独培训!");
	           			//window.location.href="${pageContext.request.contextPath}/device/findAllTrainingByDeviceId?deviceId="+id;
	           		}else if(data=="errorType3"){
	           			alert("您还未通过网上答题培训,请先参加网上答题培训!");
	           			findTestList(id);
	           			//window.location.href="${pageContext.request.contextPath}/device/findAllTrainingByDeviceId?deviceId="+id;
	           		}else if(data=="noSetting"){
	           			alert("设备未进行初始化设置，不可预约！");
	           		}else if(data=="noDean"){
	           			alert("系统未找到您所属的系主任/系教学秘书，不可预约！");
	           		}
	           		//else if(data=="error"){
	           		//	alert("请联系设备管理员，单独培训！")
	           		//}
	           		else{
	           			alert("该设备还未添加设备管理员，暂不能预约，请联系相关人员进行添加！")
	           		}     
	           }
	});
	
}


//参数设置
function deviceParameters(id){
	window.location.href="${pageContext.request.contextPath}/device/deviceParameters?deviceId="+id+"&modelId=338";
 }
//开放设置
function deviceOpenSetting(id){
	window.location.href="${pageContext.request.contextPath}/device/deviceOpenSetting?deviceId="+id+"&modelId=339";
}

function generateDimensionalCode(id){
	$.ajax({
	           url:"${pageContext.request.contextPath}/device/generateDimensionalCode?lab_id="+id ,
	           type:"POST",
	           success:function(data){//AJAX查询成功
	           		if(data=="success"){
	           			alert("批量生成二维码完成");
	           		}else{
	           			alert("生成二维码过程出错，请检查网络情况");
	           		}    
	           }
	});
	
} 
</script>
<script>
	// 上传导入文件
	function  saveLabRoomDevice(){
		var formData = new FormData();
		formData.append("file", document.getElementById("file").files[0]);
		//将要所有要添加的数据传给后台处理
		$.ajax({
			url:"${pageContext.request.contextPath}/device/importLabRoomDevice?roomId=${roomId }",
			type:"POST",
			async:false,
			cache: false,
			contentType: false,
			processData: false,
			dataType: 'text',
			data:formData,
			success:function(saveResult)
			{
				alert(saveResult);
				window.location.href="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId}&page=1";
			},
			error:function () {
				alert("请求错误");
			}
		});
	}
</script>

<script>
function openSetupLink1(id){
    var labRoom_id = $("#labRoom_id").val();
    var schoolDevice_deviceNumber = $("#schoolDevice_deviceNumber").val();
    var schoolDevice_deviceName =$("#schoolDevice_deviceName").val();
    var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
    var username = $("#username").val();
    //alert(labRoom_id);
	if($("#labRoom_id").val()==""){
	  labRoom_id ="-1";
	}
	if($("#schoolDevice_deviceNumber").val()==""){
	  schoolDevice_deviceNumber ="-1";
	}
	if($("#schoolDevice_deviceName").val()==""){
	  schoolDevice_deviceName ="-1";
	}
	if($("#username").val()==""){
		username ="-1";
	}
	if($("#schoolDevice_allowAppointment").val()==""){
		schoolDevice_allowAppointment ="-1";
	}
	var url = "${pageContext.request.contextPath}/device/editDeviceInfoRest/" + labRoom_id + "/"+ schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username +"/${page}/" + id+"/"+schoolDevice_allowAppointment;
	//alert(url);
	window.location.href=url;
}

//培训预约(编辑--针对老师)
function editDeviceTrainingRest(id){
    var labRoom_id = $("#labRoom_id").val();
    var schoolDevice_deviceNumber = $("#schoolDevice_deviceNumber").val();
    var schoolDevice_deviceName =$("#schoolDevice_deviceName").val();
    var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
    var username = $("#username").val();
    //alert(schoolDevice_deviceName);
	if($("#labRoom_id").val()==""){
	  labRoom_id ="-1";
	}
	if($("#schoolDevice_deviceNumber").val()==""){
	  schoolDevice_deviceNumber ="-1";
	}
	if($("#schoolDevice_deviceName").val()==""){
	  schoolDevice_deviceName ="-1";
	}
	if($("#username").val()==""){
		username ="-1";
	}
	if($("#schoolDevice_allowAppointment").val()==""){
		schoolDevice_allowAppointment ="-1";
	}
	var url = "${pageContext.request.contextPath}/device/editDeviceTrainingRest/"+ labRoom_id + "/"+ schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username +"/${page}/" + id+"/"+schoolDevice_allowAppointment;
	//alert(url);
	window.location.href=url;
}

//培训预约(查看--针对学生)
function viewDeviceTrainingRest(id){
    var labRoom_id = $("#labRoom_id").val();
    var schoolDevice_deviceNumber = $("#schoolDevice_deviceNumber").val();
    var schoolDevice_deviceName =$("#schoolDevice_deviceName").val();
    var username = $("#username").val();
    var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
	if($("#labRoom_id").val()==""){
	  labRoom_id ="-1";
	}
	if($("#schoolDevice_deviceNumber").val()==""){
	  schoolDevice_deviceNumber ="-1";
	}
	if($("#schoolDevice_deviceName").val()==""){
	  schoolDevice_deviceName ="-1";
	}
	if($("#username").val()==""){
		username ="-1";
	}
	if($("#schoolDevice_allowAppointment").val()==""){
		schoolDevice_allowAppointment ="-1";
	}

	var url = "${pageContext.request.contextPath}/device/viewDeviceTrainingRest/" + labRoom_id + "/"+ schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username +"/${page}/" + id+"/"+schoolDevice_allowAppointment;
    //alert(url);
	window.location.href=url;
}
//查看设备详情（传参版）
function viewDeviceInfoRest(id){
    var labRoom_id = $("#labRoom_id").val();
    var schoolDevice_deviceNumber = $("#schoolDevice_deviceNumber").val();
    var schoolDevice_deviceName =$("#schoolDevice_deviceName").val();
    var username = $("#username").val();
    var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
	if($("#labRoom_id").val()==""){
	  labRoom_id ="-1";
	}
	if($("#schoolDevice_deviceNumber").val()==""){
	  schoolDevice_deviceNumber ="-1";
	}
	if($("#schoolDevice_deviceName").val()==""){
	  schoolDevice_deviceName ="-1";
	}
	if($("#username").val()==""){
		username ="-1";
	}
	if($("#schoolDevice_allowAppointment").val() == ""){
		schoolDevice_allowAppointment="-1";
	}
	var url = "${pageContext.request.contextPath}/device/viewDeviceInfoRest/" + labRoom_id + "/"+ schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username +"/${page}/" + id+"/"+schoolDevice_allowAppointment;
	//alert(url);
	window.location.href=url;
}

/*
 *查看设备信息
 */
function listDeviceInfo(id) {
	var sessionId = $("#sessionId").val();
	var con = '<iframe scrolling="yes" id="message1" frameborder="0"  src="${pageContext.request.contextPath}/device/viewDeviceInfo?id='
			+ id
			+ '" style="width:100%;height:100%;"></iframe>'
	$('#listDeviceInfo').html(con);
	$("#listDeviceInfo").show();
	//获取当前屏幕的绝对位置
    var topPos = window.pageYOffset;
    //使得弹出框在屏幕顶端可见
    $('#listDeviceInfo').window({left:"px", top:topPos+"px"});
	$('#listDeviceInfo').window('open');
}
//考试
function findTestList(deviceId){//将labRoomId deviceNumber deviceName page传递到后台
	var labRoomId = $("#labRoom_id").val();
	var deviceName = $("#schoolDevice_deviceName").val();
	var deviceNumber = $("#schoolDevice_deviceNumber").val();
	var username = $("#username").val();
	//var username = '-1';
	var page = ${page};
	var status = -1;
	if($("#labRoom_id").val()==""){
		labRoomId ="-1";
	}
	if($("#schoolDevice_deviceNumber").val()==""){
		deviceName ="-1";
	}
	if($("#schoolDevice_deviceName").val()==""){
		deviceNumber ="-1";
	}
	if($("#username").val()==""){
		username ="-1";
	}
	var url = "${pageContext.request.contextPath}/teaching/test/testListForStudentAndTeacher/" + labRoomId + "/"+ deviceNumber + "/" + deviceName +"/"+username+ "/"+page+"/" + deviceId+"/"+status;
	window.location.href=url;
}
//考试
function findTestListForAdmin(deviceId){//将labRoomId deviceNumber deviceName page传递到后台
	var labRoomId = $("#labRoom_id").val();
	var deviceName = $("#schoolDevice_deviceName").val();
	var deviceNumber = $("#schoolDevice_deviceNumber").val();
	var username = $("#username").val();
	//var username = '-1';
	var page = ${page};
	var status = -1;
	if($("#labRoom_id").val()==""){
		labRoomId ="-1";
	}
	if($("#schoolDevice_deviceNumber").val()==""){
		deviceName ="-1";
	}
	if($("#schoolDevice_deviceName").val()==""){
		deviceNumber ="-1";
	}
	if($("#username").val()==""){
		username ="-1";
	}
	var url = "${pageContext.request.contextPath}/teaching/test/testList/" + labRoomId + "/"+ deviceNumber + "/" + deviceName +"/"+username+ "/"+page+"/" + deviceId+"/"+status;
	window.location.href=url;
}
function openSetupLink(id) {
    var labRoom_id = $("#labRoom_id").val();
    var schoolDevice_deviceNumber = $("#schoolDevice_deviceNumber").val();
    var schoolDevice_deviceName =$("#schoolDevice_deviceName").val();
    var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
    var username = $("#username").val();
    //alert(labRoom_id);
    if($("#labRoom_id").val()==""){
        labRoom_id ="-1";
    }
    if($("#schoolDevice_deviceNumber").val()==""){
        schoolDevice_deviceNumber ="-1";
    }
    if($("#schoolDevice_deviceName").val()==""){
        schoolDevice_deviceName ="-1";
    }
    if($("#username").val()==""){
        username ="-1";
    }
    if($("#schoolDevice_allowAppointment").val()==""){
        schoolDevice_allowAppointment ="-1";
    }
    var index = layer.open({
        type: 2,
        title: '设置',
        maxmin: true,
        shadeClose: true,
        content: "${pageContext.request.contextPath}/device/editDeviceInfoRest/" + labRoom_id + "/"+ schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username +"/${page}/" + id+"/"+schoolDevice_allowAppointment,
    });
    layer.full(index);
}
// 删除设备
function  deleteLabRoomDevice(id) {
    $.ajax({
        url:"${pageContext.request.contextPath}/device/deleteLabRoomDevice?id="+id+"&flg=1",
        type:"POST",
        success:function(data){//AJAX查询成功
            document.queryForm.action="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=1";
            document.queryForm.submit();
        },
        error:function(){
            alert("占用中，无法删除");

        }
    });
}

// 设备详情
function labRoomDeviceInfo(id) {
	var index = layer.open({
		type:2,
		title:'详情',
		maxmin:true,
		shadeClose:true,
		content:"${pageContext.request.contextPath}/device/viewDeviceDetails/" + id ,
	});
	layer.full(index);
}
function newLabRoomDevice() {
    var index = layer.open({
        type: 2,
        title: '新建',
        maxmin: true,
        shadeClose: true,
        content: "${pageContext.request.contextPath}/device/newLabRoomDevice?roomId=${roomId }",
        end:function(){
            document.queryForm.action="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=1";
            document.queryForm.submit();
        }
    });
    layer.full(index);
}
function roomChange(){
    var roomId = document.getElementById("labRoom_id").value;
    //alert(roomId);
    var url = "${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId="+roomId+"&page=1";
    window.location.href=url;
}
</script>

<script>
//导入
function importLabRoomDevice(){
	$('#importLabRoomDevice').window({width:500,left:"30px",top:"30px"});
	$("#importLabRoomDevice").window('open');
} 
</script>
<style>
.tab_fix{
    width: 98%;
    position: relative;
    left: 1%;
}
.tab_fix th {
    font-weight: normal;
    width: 95px;
    text-align: right;
    white-space: nowrap;
}
.tab_fix td {
    text-align: left;
    white-space: nowrap;
    padding-right: 20px;
    border-bottom: 1px solid #e4e5e7;
    padding: 10px 20px 10px 7px!important;
}
.tab_fix td input[type="text"] {
    height: 24px;
    width: 100%;
    box-sizing: border-box;
    /*min-width: 210px;*/
}
.tab_fix td select{
    width:100%;
}
.chzn-container{
    width:100%!important;
}
.content-box .strip_tab{
    width:100%;
    border:none;
    left:0;
}
.content-box .strip_tab td{
    border:none;
    border-bottom: 1px solid #e4e5e7;
    background:#fff!important;
}
.content-box .strip_tab tr:nth-child(even)>td {
    background: #f9f9f9!important;
}
.content-box .strip_tab tr:nth-child(even)>.equipment_img {
    background: #fff!important;
}
.equipment_img{
    padding:0!important;
}
.equipment_img div {
    width:215px;
    height:215px;
    background:#f9f9f9;
    border-right:1px solid #e4e5e7;
    position:relative;
}
.content-box .strip_tab tr:nth-child(even) .equipment_img div{
    background: #fff!important;
    
}
.equipment_img img {
    position:absolute;
    left:0;
    right:0;
    top:0;
    bottom:0;
    margin:auto;
    max-width: 90%;
    max-height: 95%;
    min-width: 100px;
    min-height: 100px;
}
.equipment_box {
    padding-left: 25px;
    text-align: left;
}
.equipment_name {
    font-size: 17px;
    color: #353535;
}
.tool-box{
    overflow:visible;
    clear:both;
}
.title font{
    font-size:12px;
    color:#777;
    font-weight:normal;
}
</style>
</head>

<body>
<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="all.trainingRoom.labroom" />基础信息管理</a></li>
				<li class="end"><a href="javascript:void(0)">设备列表</a></li>
			</ul>
		</div>
	</div>
    <div >
	 <ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
		<li class="TabbedPanelsTab selected" id="s1"><a href="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=1">设备列表</a></li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/device/applyDeviceRepairList?page=1&roomId=${roomId }">保修登记</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/device/listLabRoomDeviceUsage?page=1&roomId=${roomId }">设备使用记录</a></li>
	</ul>   
	<div class="TabbedPanelsTabGroup-box">
	<div class="TabbedPanelsContent">
	<div class="content-box">		
        <div class="tool-box">
            <form:form name="queryForm" action="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=1" method="post" modelAttribute="labRoomDevice">
				<table class="tab_fix">
					<tr>
						<th>切换<spring:message code="all.trainingRoom.labroom" />：</th>
							<td>
								<form:select class="chzn-select" path="labRoom.id" id="labRoom_id" onchange="roomChange()">
								<form:option value="">请选择</form:option>
								<form:options items="${rooms}" itemLabel="labRoomName" itemValue="id"/>
								</form:select>		    				    				            
							</td>
							<th>设备编号：</th>
							<td>
								<form:select class="chzn-select"  path="schoolDevice.deviceNumber" id="schoolDevice_deviceNumber">
								<form:option value="">请选择</form:option>
								<form:options cssStyle="width:200px;" items="${listLabRoomDeviceAll}" itemLabel="schoolDevice.deviceNumber" itemValue="schoolDevice.deviceNumber"/>
								</form:select>
							</td> 
				     
							<th>设备名称：</th>
							<td>
								<form:select class="chzn-select"  path="schoolDevice.deviceName" id="schoolDevice_deviceName">
								<form:option value="">请选择</form:option>
								<c:forEach items="${listLabRoomDeviceAll}" var="curr">
								<form:option cssStyle="width:200px;" value="${curr.schoolDevice.deviceName }">${curr.schoolDevice.deviceName}[${curr.schoolDevice.deviceNumber }]</form:option>
								</c:forEach>
								</form:select>
							</td>
							</tr>
							<tr> 
							<th style="border-bottom:none;">设备开放：</th>
							<td>
								<form:select class="chzn-select"  path="CDictionaryByAllowAppointment.CNumber" id="schoolDevice_allowAppointment">
								<form:option value="">请选择</form:option>
								<form:option cssStyle="width:200px;" value="1">是</form:option>
								<form:option cssStyle="width:200px;" value="2">否</form:option>
								</form:select>
							</td>  
							<th style="border-bottom:none;">设备管理员：</th>
							<td>
								<form:select class="chzn-select"  path="user.username" id="username">
								<form:option value="">请选择</form:option>
								<form:options cssStyle="width:200px;" items="${userMap}"/>
								</form:select>
							</td> 
							<%--<td colspan="2" style="text-align:right;">--%>
							<td colspan="2" >
								<input type="button" value="取消" onclick="cancelQuery();">
								<input type="submit" value="查询">
							</td>
					</tr >
				</table>
				</form:form>	
        </div>
    </div>
        <div class="content-box">
            <div class="title" style="display: block">
            设备列表<font>（只有添加了设备管理员，设备才可以出借）</font>
            <a class="btn btn-new" href="${pageContext.request.contextPath}/device/exportLabRoomDevice?roomId=${roomId }&page=1">导出</a>
			<c:if test="${proj_name ne 'zjcclims'}">
				<a class="btn btn-new" onclick="importLabRoomDevice()">导入</a>
				<a class="btn btn-new" onclick='newLabRoomDevice()'>新建</a>
			</c:if>

            <a class="btn btn-new"  href="javascript:void(0)" onclick="generateDimensionalCode(${roomId});">生成所有的二维码</a>
            </div>
            <%-- <div class="page" >
		        ${totalRecords}条记录,共${pageModel.totalPage}页
		    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=1')" target="_self">首页</a>			    
			<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=')" target="_self">上一页</a>
			第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
			<option value="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=${page}">${page}</option>
			<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
		    <c:if test="${j.index!=page}">
		    <option value="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=${j.index}">${j.index}</option>
		    </c:if>
		    </c:forEach></select>页
			<a href="javascript:void(0)"     onclick="next('${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=')" target="_self">下一页</a>
		 	<a href="javascript:void(0)"    onclick="last('${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=${pageModel.totalPage}')" target="_self">末页</a>
		    </div> --%>

			<!-- 	列表方式 -->
			<table class="tb" style="text-align: center;"> 
		    <thead>
		    	<tr>
					<th>序号</th>
		        	<th>资产编号</th>
		        	<th>名称</th>
		        	<th>型号规格</th>
					<th>品牌</th>
		        	<th>所属<spring:message code="all.trainingRoom.labroom" /></th>
		        	<th>价格</th>
		        	<th>设备状态</th>
					<th>设备管理员</th>
					<th>备注</th>
		        	<th>操作</th>
		    	</tr>
			</thead>
			<tbody>
		   	<c:forEach items="${listLabRoomDevice}" var="labRoomDevice" varStatus="i">	
		    	<tr>
					<td>${i.index+1}</td>
		           	<td>${labRoomDevice.schoolDevice.deviceNumber}</td>
		           	<td>${labRoomDevice.schoolDevice.deviceName}</td>
		           	<td>${labRoomDevice.schoolDevice.devicePattern}</td>
					<td>${labRoomDevice.schoolDevice.deviceBrand}</td>
					<td>${labRoomDevice.labRoom.systemRoom.roomName}</td>
		         	<td>${labRoomDevice.schoolDevice.devicePrice}</td>
		         	<td>${labRoomDevice.CDictionaryByDeviceStatus.CName}</td>
					<td>${labRoomDevice.user.cname}(${labRoomDevice.user.username})</td>
					<td>${labRoomDevice.schoolDevice.memo}</td>
		           	<td>
						<div>
							<a class="btn btn-new" href="javascript:void(0)" onclick="deleteLabRoomDevice(${labRoomDevice.id})"; >删除</a>
							<a class="btn btn-new" href="javascript:void(0)" onclick="labRoomDeviceInfo(${labRoomDevice.id})"; >详情</a>
							<c:if test="${is_reservation eq 1}">
								<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_LABMANAGER,ROLE_EQUIPMENTADMIN,ROLE_TEACHER,ROLE_ASSISTANT,ROLE_STUDENT">
									<c:if test="${(labRoomDevice.CDictionaryByDeviceStatus.CNumber == 1 || empty labRoomDevice.CDictionaryByDeviceStatus) && labRoomDevice.CDictionaryByAllowAppointment.CNumber==1}">
										<a class="btn btn-new"  href="javascript:void(0)" onclick="Access('${labRoomDevice.id}');"  >预约</a>
									</c:if>
		                        		<%--<c:if test="${labRoomDevice.CDictionaryByDeviceStatus.CNumber == 1 && labRoomDevice.CDictionaryByAllowLending.CNumber==1 && labRoomDevice.user.username != null }">
		                        		<a class="btn btn-new"  href="${pageContext.request.contextPath}/device/deviceLendApply?idKey=${labRoomDevice.id}"> 出借</a>
		                        		</c:if>--%>
									<c:if test="${labRoomDevice.CDictionaryByDeviceStatus.CNumber == 4 && labRoomDevice.user.username != null  }">
										<a class="btn btn-new"   href="${pageContext.request.contextPath}/device/newDeviceService?td=${labRoomDevice.id}"> 报修</a>
									</c:if>
								</sec:authorize>
		                        		
								<a class="btn btn-new" onclick="viewDeviceInfoRest(${labRoomDevice.id})" href="javascript:void(0)"  title="查看">查看</a>
								<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_TEACHER,ROLE_EXPERIMENTALTEACHING,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_LABMANAGER,ROLE_EQUIPMENTADMIN,ROLE_STUDENT">
									<c:if test="${(labRoomDevice.CDictionaryByAllowSecurityAccess.CCategory =='c_active' && labRoomDevice.CDictionaryByAllowSecurityAccess.CNumber == 1 && labRoomDevice.CDictionaryByTrainType.CNumber == 1)}">
										<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EQUIPMENTADMIN">
											<a class="btn btn-new" href="javascript:void(0)" onclick="editDeviceTrainingRest(${labRoomDevice.id})";>培训申请</a>
		                                </sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_STUDENT,ROLE_TEACHER">
											<sec:authorize ifNotGranted="ROLE_SUPERADMIN,ROLE_EQUIPMENTADMIN">
												<a class="btn btn-new" href="javascript:void(0)" onclick="viewDeviceTrainingRest(${labRoomDevice.id})";>培训申请</a>
											</sec:authorize>
										</sec:authorize>
									</c:if>
									<c:if test="${(labRoomDevice.CDictionaryByAllowSecurityAccess.CCategory =='c_active' && labRoomDevice.CDictionaryByAllowSecurityAccess.CNumber == '1' && labRoomDevice.CDictionaryByTrainType.CNumber == '3')}">
										<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_TEACHER,ROLE_EXPERIMENTALTEACHING,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_LABMANAGER,ROLE_EQUIPMENTADMIN">
											<a class="btn btn-new" href="javascript:void(0)" onclick="findTestListForAdmin(${labRoomDevice.id})";>考试设置</a>
											<a class="btn btn-new" href="javascript:void(0)" onclick="findTestList(${labRoomDevice.id})";>考试</a>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_STUDENT">
											<sec:authorize ifNotGranted="ROLE_SUPERADMIN,ROLE_TEACHER,ROLE_EXPERIMENTALTEACHING,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_LABMANAGER,ROLE_EQUIPMENTADMIN">
												<a class="btn btn-new" href="javascript:void(0)" onclick="findTestList(${labRoomDevice.id})";>考试</a>
											</sec:authorize>
										</sec:authorize>
									</c:if>
								</sec:authorize>
							</c:if>
		                              
							<c:if test="${is_reservation eq 0}">
		                              <%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_TEACHER">
		                              <a class="btn btn-new" href="${pageContext.request.contextPath}/device/listInnerSameDevice?deviceId=${labRoomDevice.id}">关联设备</a>
		                              </sec:authorize>--%>
								<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR">
									<a class="btn btn-new" href="javascript:void(0)" onclick="openSetupLink(${labRoomDevice.id})" title="设置">设置</a>
								</sec:authorize>
								<sec:authorize ifNotGranted="ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_TEACHER">
		                        			<%--<c:if test="${labRoomDevice.user.username eq user.username}">
		                        			<a class="btn btn-new" href="${pageContext.request.contextPath}/device/listInnerSameDevice?deviceId=${labRoomDevice.id}">关联设备</a>
			                        		</c:if>--%>
			                        		<%--<spring:message code="all.trainingRoom.labroom" />室管理员--%>
									<c:if test="${labRoomDevice.user.username ne user.username}">
										<c:forEach items="${labRoomDevice.labRoom.labRoomAdmins}" var="admin" varStatus="i">
					                        		<%--<c:if test="${admin.user.username eq user.username && admin.typeId eq 1}">
				                        			<a class="btn btn-new" href="${pageContext.request.contextPath}/device/listInnerSameDevice?deviceId=${labRoomDevice.id}">关联设备</a>
			                        				</c:if>--%>
										</c:forEach>
									</c:if>
								</sec:authorize>
								<sec:authorize ifNotGranted="ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR">
									<c:if test="${labRoomDevice.user.username eq user.username}">
										<a class="btn btn-new" href='javascript:openSetupLink(${labRoomDevice.id})' title="设置">设置</a>
									</c:if>
			                        		<%--<spring:message code="all.trainingRoom.labroom" />室管理员--%>
									<c:if test="${labRoomDevice.user.username ne user.username}">
										<c:forEach items="${labRoomDevice.labRoom.labRoomAdmins}" var="admin" varStatus="i">
											<c:if test="${admin.user.username eq user.username && admin.typeId eq 1}">
												<a class="btn btn-new" href='javascript:openSetupLink(${labRoomDevice.id})' title="设置">设置</a>
											</c:if>
										</c:forEach>
									</c:if>
								</sec:authorize>
							</c:if>
		                               <%--<a class="btn btn-new" href='javascript:edit(${labRoomDevice.id})'>编辑</a>--%>
						</div>
		         	</td>
		    	</tr>
		  	</c:forEach>
			</tbody>
			</table>


            <div class="page" >
		        ${totalRecords}条记录,共${pageModel.totalPage}页
		    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=1')" target="_self">首页</a>			    
			<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=')" target="_self">上一页</a>
			第<select onchange="first(this.options[this.selectedIndex].value)">
			<option value="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=${page}">${page}</option>
			<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
		    <c:if test="${j.index!=page}">
		    <option value="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=${j.index}">${j.index}</option>
		    </c:if>
		    </c:forEach></select>页
			<a href="javascript:void(0)"     onclick="next('${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=')" target="_self">下一页</a>
		 	<a href="javascript:void(0)"    onclick="last('${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=${pageModel.totalPage}')" target="_self">末页</a>
		    </div>
        </div>
    </div>
    </div>
    </div>
    
    <!-- 查看设备信息 -->
	<div id="listDeviceInfo" class="easyui-window" title="查看设备信息" modal="true"	closed="true" iconCls="icon-add" style="width:1000px;height:600px">
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
</script>

<!-- 下拉框的js -->
<div id= "importLabRoomDevice"  class ="easyui-window panel-body panel-body-noborder window-body" title= "导入模版" modal="true" dosize="true" maximizable= "true" collapsible ="true" minimizable= "false" closed="true" iconcls="icon-add" style=" width: 600px; height :400px;">
        <form:form name="importForm" action="${pageContext.request.contextPath}/device/importLabRoomDevice?roomId=${roomId }" enctype ="multipart/form-data">
	         <br>
	         <input type="file" id="file" name ="file" required="required" />
	         <input type="button" onclick="saveLabRoomDevice()"  value ="开始上传" />
	         <br>
	         <br><hr><br>
		<a href="${pageContext.request.contextPath}/pages/importSample/labRoomDevice.xls">点击此处</a>，下载范例<br><br><hr><br>
		示例图片：<br>
		<img src="${pageContext.request.contextPath}/images/importSample/labRoomDevice.png" heigth ="100%" width="100%" />
        </form:form>
</div>
</body>
</html>


