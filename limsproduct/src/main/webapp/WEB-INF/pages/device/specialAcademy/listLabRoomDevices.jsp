<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>

<head>
    <title></title>
    <!--[if lt IE 8]>
        <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <![endif]-->
    <!--[if gte IE 8]>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>  
    <![endif]-->
    <meta name="decorator" content="iframe"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="Generator" content="gvsun">
    <meta name="Author" content="lyyyyyy">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link href="" rel="stylesheet" type="text/css">
    <script type="text/javascript" src=""></script>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
	<script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
<!-- 下拉的样式结束 -->	
<script type="text/javascript">
	$(document).ready(function () {
        $.ajax({
            type: "POST",
            url:"${pageContext.request.contextPath}/findLabRoomDeviceByLabRoom",
            data:{'labRoom':$("#labRoom_id").val(),
			       'deviceNumber':$("#schoolDevice_deviceName").val()},
            success:function(data){
                $("#schoolDevice_deviceName").html(data.labRoomDevice);
                $("#schoolDevice_deviceName").trigger("liszt:updated");
            }
        });
    });
</script>
 <script type="text/javascript">
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
		//alert("上一页的路径："+url+page);
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
		//alert("下一页的路径："+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	//取消查询
	function cancelQuery(){
		window.location.href="${pageContext.request.contextPath}/device/listLabRoomDevice?page=1&isReservation=${is_reservation}&isOrder=${isOrder}";
	}
    function getLabRoomDevice(){
        $.ajax({
            type: "POST",
            url:"${pageContext.request.contextPath}/findLabRoomDeviceByLabRoom",
            data:{'labRoom':$("#labRoom_id").val(),
                 'deviceNumber':$("#schoolDevice_deviceName").val()},
            success:function(data){
                $("#schoolDevice_deviceName").html(data.labRoomDevice);
                $("#schoolDevice_deviceName").trigger("liszt:updated");
            }
        });
    }
	var targetId = 0;
	//AJAX验证是否通过安全准入
	function Access(id){
	    targetId = id;
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
		           		}else if(data.indexOf("<td>")>-1){
                            document.getElementById("device_body").innerHTML=data;
                            $("#showAttentions").show();
                            var left = $(document).scrollLeft()+100;
                            var top = $(document).scrollTop()+100;
                            $('#showAttentions').window({left:left+"px", top:top+"px"});
                            $("#showAttentions").window('open');
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
	function trainAccess(id) {
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
        window.location.href="${pageContext.request.contextPath}/device/viewDeviceTrainingRest/" + labRoom_id + "/"+schoolDevice_deviceNumber+"/"+schoolDevice_deviceName+"/"+username+"/${page}/"+ id+"/"+schoolDevice_allowAppointment;
        <%--window.location.href="${pageContext.request.contextPath}/}"--%>
    }

    // 取消（不同意）
    function cancel_att(){
        $("#showAttentions").window('close');
    }

    //阅读注意事项后，去预约
    function listDevicesReservationAfterReading(){
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
        var url = "${pageContext.request.contextPath}/device/doDeviceReservation/" + labRoom_id + "/"+ schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username +"/${page}/" + targetId+"/"+1+"/"+schoolDevice_allowAppointment;
        window.location.href=url;
    }
</script>   
    <style type="text/css">
        .layout_classroom_intro {
            height: 50px;
            font-size: 14px;
            font-weight: bold;
            
            line-height: 50px;
            background: #eee;
            padding: 0 10px;
        }
        .layout_classroom_list{
        	overflow:hidden;
			border: 1px solid #d0d6dc;
			margin-top: 10px;
        }
        .layout_classroom_list li {
            width: 25%;
            float: left;
            margin: 12px 0 23px;
            box-sizing: border-box;
            padding: 0 11px;
        }
        
        .layout_classroom {
            width: 90%;
            margin: auto;
            overflow: hidden;
        }
        
        .layout_classroom_pic {
            width: 100%;
            border: 0px;
        }
        
        li {
            list-style: none;
        }
        a{
            text-decoration: none;
        }
        .layout_classroom a{
            color:#6ba6b4
        }
        .layout_classroom a:hover{
            color:#555
        }
		.content-box .list_form td{
			float: left;
			text-align: left;
			margin: 2px 0 -2px;
		}
		/*.content-box .list_form td:last-child{*/
			/*float: right;*/
		/*}*/
		/*#labRoom_id_chzn,*/
		/*#schoolDevice_deviceName_chzn{*/
			/*width:300px!important;*/
		/*}*/
        
    </style>
</head>

<body>
<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.open.appointment" /></a></li>
				<li class="end"><a href="javascript:void(0)"><spring:message code="left.instrument.appointment" /></a></li>
			</ul>
		</div>
	</div>
<div id="TabbedPanels1" class="TabbedPanels">
<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
		<li class="TabbedPanelsTab selected" id="s1"><a href="${pageContext.request.contextPath}/device/listLabRoomDevice?page=1&isReservation=1&isOrder=1"><spring:message code="left.instrument.appointment" /></a></li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?tage=0&currpage=1&isaudit=2">我的申请</a></li>
	<c:if test="${sessionScope.selected_role eq 'ROLE_TEACHER' || sessionScope.selected_role eq 'ROLE_CFO' || sessionScope.selected_role eq 'ROLE_LABMANAGER'
	    || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR' || sessionScope.selected_role eq 'ROLE_PREEXTEACHING' || sessionScope.selected_role eq 'ROLE_SUPERADMIN'||sessionScope.selected_role eq 'ROLE_EQUIPMENTADMIN'}">
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?tage=0&currpage=1&isaudit=1">我的审核</a></li>
		<li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationListBatch?tage=0&currpage=1&isaudit=1">批量审核</a></li>
	</c:if>
</ul>
<div class="">
<%--<div class="TabbedPanelsTabGroup-box" style="padding:10px 0 0;">--%>
<div class="TabbedPanelsContentGroup">
    <div class="TabbedPanelsContent">   
    <div class="content-box">
  <div class="tool-box">
            <form:form name="queryForm" action="${pageContext.request.contextPath}/device/listLabRoomDevice?page=1&isReservation=${is_reservation}&isOrder=${isOrder}" method="post" modelAttribute="labRoomDevice">
					<table class="list_form">
						<tr>
							<td><spring:message code="all.training.name"/>室名称:
								<form:select  class="chzn-select"  path="labRoom.id" id="labRoom_id" onchange="getLabRoomDevice()">
								<form:option value="" selected="selected">请选择</form:option>
								<%--<!--<form:options items="${rooms}" itemLabel="labRoomName" itemValue="id"/>-->--%>
								<c:forEach items="${rooms}" var="curr">
									<form:option value="${curr.id}">${curr.labRoomName}</form:option>
								</c:forEach>
								</form:select>
							</td> 
							 <td>设备名称:
								<form:select name="schoolDevice" class="chzn-select"  path="schoolDevice.deviceNumber" id="schoolDevice_deviceName">
				                  <form:option value="" selected="selected">全部设备</form:option>
									<c:forEach items="${labRoomDevices}" var="curr">
										<form:option value="${curr.schoolDevice.deviceNumber}" >${curr.schoolDevice.deviceName}(${curr.schoolDevice.deviceNumber})</form:option>
									</c:forEach>
				                </form:select>
							</td> 
							<td>
								<input type="submit" value="查询">
								<input class="cancel-submit" type="button" value="取消" onclick="cancelQuery()"/>
							</td>
						</tr >
				</table>
				</form:form>	
</div>
</div>
<div class="right-content">	
	
	<ul class="layout_classroom_list">
	<c:forEach items="${listLabRoomDevice}" var="labRoomDevice" varStatus="i">
        <li>
            <div class="layout_labRoomDevice">
	                <c:set var="flag" value="0"></c:set>
                	<c:forEach items="${labRoomDevice.commonDocuments}" var="d" varStatus="i">
	                	<c:if test="${d.type==4}">
	                	<c:set var="flag" value="1"></c:set>
	                    	<div class="flag_img_limit">
	                    	    <div>
	                    	        <img src="${pageContext.request.contextPath}/${d.documentUrl}" class="layout_labRoomDevice_pic"/>
	                    	        <div class="software_intro">
						                <p class="soft_name">${labRoomDevice.schoolDevice.deviceName}(${labRoomDevice.schoolDevice.deviceNumber})</p>
						                <div class="list_intro" style="display: none;">						                    
                                            <div>所在<spring:message code="all.trainingRoom.labroom" />室:&nbsp;${labRoomDevice.labRoom.labRoomName}</div>
						                    <div>所在地点:&nbsp;${labRoomDevice.labRoom.systemBuild.systemCampus.campusName}${labRoomDevice.labRoom.systemBuild.buildName}${labRoomDevice.labRoom.systemRoom.roomName}(${labRoomDevice.labRoom.systemRoom.roomNo})
                                            </div>
						                </div>
					                </div>
									<c:if test="${(labRoomDevice.CDictionaryByDeviceStatus.CNumber == 1 || empty labRoomDevice.CDictionaryByDeviceStatus)
									&& (labRoomDevice.CDictionaryByTrainType!=null && labRoomDevice.CDictionaryByTrainType.CNumber==1)}">
										<a class="order_btn2 icon-envelope-alt fa-home"  href="javascript:void(0)" onclick="trainAccess('${labRoomDevice.id}');" title="设备培训" style="width: 50px;height: 40px;"></a>
									</c:if>
					                <c:if test="${(labRoomDevice.CDictionaryByDeviceStatus.CNumber == 1 || empty labRoomDevice.CDictionaryByDeviceStatus) && labRoomDevice.CDictionaryByAllowAppointment.CNumber==1}">
                                        <a class="order_btn icon-envelope-alt fa-home"  href="javascript:void(0)" onclick="Access('${labRoomDevice.id}');" title="设备预约" style="width: 50px;height: 40px;"></a>
                                    </c:if>
	                    	    </div>
	                    	</div>
	                    </c:if>	
					</c:forEach>
					<c:if test="${flag == 0}">
	                    	<div class="flag_img_limit">
	                    	    <div>
	                    	        <img src="${pageContext.request.contextPath}/images/no-img.jpg" class="layout_labRoomDevice_pic"/>
	                    	        <div class="software_intro">
						                <p class="soft_name">${labRoomDevice.schoolDevice.deviceName}(${labRoomDevice.schoolDevice.deviceNumber})</p>
						                <div class="list_intro" style="display: none;">						                    
                                            <div>所在<spring:message code="all.trainingRoom.labroom" />室:&nbsp;${labRoomDevice.labRoom.labRoomName}</div>
						                    <div>所在地点:&nbsp;${labRoomDevice.labRoom.systemBuild.systemCampus.campusName}${labRoomDevice.labRoom.systemBuild.buildName}${labRoomDevice.labRoom.systemRoom.roomName}(${labRoomDevice.labRoom.systemRoom.roomNo})
                                            </div>
						                </div>
					                </div>
										<c:if test="${(labRoomDevice.CDictionaryByDeviceStatus.CNumber == 1 || empty labRoomDevice.CDictionaryByDeviceStatus)
										&& (labRoomDevice.CDictionaryByTrainType!=null && labRoomDevice.CDictionaryByTrainType.CNumber==1)}">
									<a class="order_btn2 icon-envelope-alt fa-home"  href="javascript:void(0)" onclick="trainAccess('${labRoomDevice.id}');" title="设备培训" style="width: 50px;height: 40px;"></a>
										</c:if>
					                <c:if test="${(labRoomDevice.CDictionaryByDeviceStatus.CNumber == 1 || empty labRoomDevice.CDictionaryByDeviceStatus) && labRoomDevice.CDictionaryByAllowAppointment.CNumber==1}">
                                        <a class="order_btn icon-envelope-alt fa-home"  href="javascript:void(0)" onclick="Access('${labRoomDevice.id}');" title="设备预约" style="width: 50px;height: 40px;"></a>
                                    </c:if>
	                    	    </div>
	                    	</div>
	                    </c:if>	
                    <ul class="equipment_introduce">
                       <div style="text-align:center;">规格:&nbsp;${labRoomDevice.schoolDevice.devicePattern}&nbsp;&nbsp;&nbsp;主要技术指标:&nbsp;${labRoomDevice.indicators}</div>
                       <div style="text-align:center;">设备管理员:
                        <c:if test="${not empty labRoomDevice.user }">
                            ${labRoomDevice.user.cname}
                        </c:if>
                        <c:if test="${empty labRoomDevice.user }">
                        <font color="red">未设置设备管理员，暂不能预约！</font>
                        </c:if>
                        </div>
                        <div style="text-align:center;">
							<c:if test="${labRoomDevice.CDictionaryByAllowAppointment.CNumber eq '1'
							               && labRoomDevice.CDictionaryByAllowAppointment.CCategory eq 'c_active'}">
							     【状态】<font style="color:#00b510;">${labRoomDevice.CDictionaryByDeviceStatus.CName}</font>
							</c:if>
							<c:if test="${labRoomDevice.CDictionaryByAllowAppointment.CNumber eq '2'
							               && labRoomDevice.CDictionaryByAllowAppointment.CCategory eq 'c_active'}">
								【状态】<font style="color:#ca9b00;">设备未开放预约</font>
							</c:if>
							<c:if test="${labRoomDevice.CDictionaryByAllowAppointment.CNumber eq null}">
								【状态】<font style="color:#ff0000;">设备未初始化，不可预约</font>
							</c:if>
							<c:if test="${not empty labRoomDevice.applicationAttentions
                                	|| not empty labRoomDevice.labRoom.labRoomAttentions}">
								<a style="color: #299fe7;text-decoration: underline;" att-record='${labRoomDevice.schoolDevice.deviceNumber}'>已阅读协议人员名单</a>
							</c:if>
						</div>
                     </ul>
            </div>
        </li>
	</c:forEach>
    </ul>
	</div>
	</div>
	</div>
				<!-- 分页模块 -->
			<div class="page" >
			        ${totalRecords}条记录,共${pageModel.totalPage}页
			    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/device/listLabRoomDevice?isReservation=1&isOrder=${isOrder}&page=1')" target="_self">首页</a>
				<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/device/listLabRoomDevice?isReservation=1&isOrder=${isOrder}&page=')" target="_self">上一页</a>
				第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
				<option value="${pageContext.request.contextPath}/device/listLabRoomDevice?isOrder=${isOrder}&page=${page}">${page}</option>
				<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
			    <c:if test="${j.index!=page}">
			    <option value="${pageContext.request.contextPath}/device/listLabRoomDevice?isOrder=${isOrder}&page=${j.index}">${j.index}</option>
			    </c:if>
			    </c:forEach></select>页
				<a href="javascript:void(0)"     onclick="next('${pageContext.request.contextPath}/device/listLabRoomDevice?isReservation=1&isOrder=${isOrder}&page=')" target="_self">下一页</a>
			 	<a href="javascript:void(0)"    onclick="last('${pageContext.request.contextPath}/device/listLabRoomDevice?isReservation=1&isOrder=${isOrder}&page=${pageModel.totalPage}')" target="_self">末页</a>
			    </div>
			<!-- 分页模块 -->
	</div>
</div>

	<div id="showAttentions" class="easyui-window" closed="true" modal="true" minimizable="true" title="安全协议" style="width: 880px;height: 400px;padding: 20px">
		<div class="content-box">
			<table id="my_show">
				<tbody id="device_body">

				</tbody>
			</table>
			<a class="btn btn-new" onclick="cancel_att()">取消</a>
			<a class="btn btn-new" onclick="listDevicesReservationAfterReading()">我已阅读，去预约</a>
		</div>
	</div>

<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
      '.chzn-select'           : {search_contains:true},
      '.chzn-select-deselect'  : {allow_single_deselect:true},
      '.chzn-select-no-single' : {disable_search_threshold:10},
      '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chzn-select-width'     : {width:"100%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
    $('[att-record]').each(function(i,e){
        $(e).on('click',function(){
            layer.open({
                type: 2,
                title: '已阅读人员名单',
                maxmin: true,
                shadeClose: true,
                area : ['1000px' , '500px'],
                content: '${pageContext.request.contextPath}/labRoom/viewLabRoomAttentionRecordDevice?deviceNumber='+$(e).attr('att-record')+'&page=1'
            });
        });
    });
    
</script>
<script type="text/javascript">
				$(".flag_img_limit").hover(
					function() {
						$(this).find(".list_intro").slideToggle(400);
						//alert("s")
					}
				)
			</script>
<!-- 下拉框的js -->

</body>

</html>