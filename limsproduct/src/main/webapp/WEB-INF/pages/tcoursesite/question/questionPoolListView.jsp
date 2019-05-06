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

</script>

<script>
$(function(){
   	$("#print").click(function(){
		$("#my_show").jqprint();
	});
});

function newQuestionPool(id,tCourseSiteId){
	if(id!=""){//修改则查询原信息
		$("#questionpoolId").val(id);
		$.ajax({
			url:"${pageContext.request.contextPath}/tcoursesite/question/findQusetionStringById?tCourseSiteId="+tCourseSiteId+"&id="+id,
	       	type:"POST",
	       	async:false,
	       	success:function(data){
				$("#questionTitle").val(data[0]);
				$("#questionType").val(data[1]);
				findQusetionListByTypeAndId(data[1],id,tCourseSiteId);
				$("#description").val(data[2]);
				$("#questionpoolParentId").val(data[3]);
				//$("#questionIsOpen").val(data[4]);
				//$("#sort").val(data[4]);
	       	}
		})
	}else{//新增
		$("#questionpoolId").val("");
		findQusetionListByTypeAndId($("#questionType").val(),id,tCourseSiteId);		
		$("#description").val("");
		$("#questionTitle").val("");
		$("#questionPoolParentId").val("");
		$("#sort").val("");
	}
	
	//绑定change事件
	$("#questionType").change(function(){
		findQusetionListByTypeAndId($("#questionType").val(),id,tCourseSiteId);
	})
    $("#newQuestionPool").window('open');
}

function findQusetionListByTypeAndId(type,id,tCourseSiteId){
	$.ajax({
		url:"${pageContext.request.contextPath}/tcoursesite/question/findQusetionListByTypeAndId?type="+type+"&id="+id+"&tCourseSiteId="+tCourseSiteId,
       	type:"POST",
       	success:function(data){
       		$("#questionpoolParentId").html(data);
       	}
	})
}
	function copyQuestionPool(username,tCourseSiteId){
	$.ajax({
		async:false,
		url:"${pageContext.request.contextPath}/tcoursesite/question/findQusetionStringByUsername?username="+username+"&tCourseSiteId="+tCourseSiteId,
       	type:"POST",
       	success:function(data){ 
       		//alert(data);
       		var _tr = "";
       		$.each(data,function(key,values){
       			_tr +=  "<tr>"+"<td>";
       			_tr +="<input class='l check_box' type='checkbox' id='"+key+"' name='checkname' value= '"+key+"'/>";
       			_tr +="<label class='l mt10' for='"+key+"' >"+values+"</label>";
       			//_tr +="<input class='l check_box' type='checkbox' id=poolid poolid='"+key+"' name='checkname'/>";
       			//_tr +="<label class='l mt10' for='poolid' >"+values+"</label>";
       			_tr +="</td>"+"</tr>";
       		})
       		
       	 	$("#copyQuestionPoolByUserListDetail").html(_tr);
       	}
	})
	$("#copyQuestionPoolByUser").fadeIn(100);
}
	function exportQuestionPoolById(questionpoolId){
	$.ajax({
		async:false,
		url:"${pageContext.request.contextPath}/tcoursesite/question/exportQuestionPoolById?questionId="+questionpoolId,
       	type:"POST",
       	success:function(data){ 
       		alert("导出成功,D盘根目录下！");
       	}
	})
}
	function exportExcelQuestionPoolById(questionId){
	$.ajax({
		async:false,
		url:"${pageContext.request.contextPath}/tcoursesite/question/exportExcelQuestionPoolById?questionId="+questionId,
       	type:"POST",
       	success:function(data){ 
       		alert("导出成功,D盘根目录下！");
       	}
	})
}
</script>



<style>
.tool-box2 th{text-align:left;}
</style>
</head>


<body>
<div class="right-content">	
	<%--<div class="tool-box1">
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
	--%><div id="TabbedPanels1" class="TabbedPanels">
	 <ul class="TabbedPanelsTabGroup">
		<%--<li class="TabbedPanelsTab" tabindex="0">
		<a href="#" onclick="viewDeviceInfoRest(${device.id})";>设备详情</a></li>
		<c:if test="${device.CActiveByAllowSecurityAccess.id == 1}">
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="#" onclick="viewDeviceTrainingRest(${device.id})";>培训计划</a>
		</li>
		</c:if>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="#" onclick="viewDeviceSettingRest(${device.id})";>参数详情</a></li>
		<li class="TabbedPanelsTab" tabindex="0">
        <a href="#" onclick="Access(${device.id})";>设备预约</a></li>
		<li class="TabbedPanelsTab" tabindex="0"><a href="#" onclick="viewDeviceReservationRest(${device.id},${currpage })";>使用情况</a></li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="#" onclick="viewDeviceImageRest(${device.id})";>相关图片</a></li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="#" onclick="viewDeviceVideoRest(${device.id})";>相关视频</a></li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="#" onclick="viewDeviceDocumentRest(${device.id})";>相关文档</a></li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="#" onclick="viewDimensionalCodeRest(${device.id})";>二维码</a></li>
		<li class="TabbedPanelsTab" tabindex="0">
        <a href="#" onclick="viewDeviceReservationRestAll(${device.id},1)";>汇总统计</a></li>
        <c:if test="${(selected_labCenter ge 25 && selected_labCenter le 28) || selected_labCenter eq 36}"> <!-- 化工学院 -->
			<li class="TabbedPanelsTab" tabindex="0">
			<a href="#" onclick="viewDeviceAttentionRest(${device.id})">注意事项</a>
			</li>
		</c:if>
		--%><li class="TabbedPanelsTab selected" tabindex="0">
		<a href="#" onclick="viewfindQuestionList(${device.id})">题库</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="#" onclick="viewfindTestList(${device.id})">考试</a>
		</li>
	</ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			
			<div class="content-box">
				<div class="title">
				<div id="title">题库</div>
				<%--<a class="btn btn-new"  herf="#" onclick="openSetupLink();">返回</a>
				--%><c:if test="${flag==1}">
				<a class="btn btn-new" onclick="newQuestionPool('',${device.id});">新增题库</a>
				</c:if>
			</div>
					
						     
								<table>
								<tr>
								 <th>名称</th>
                                 <th>创建人</th>
                                 <th>创建时间</th>
                                 <th>分类信息</th>
                                 <th>题库类别</th>
                                 <c:if test="${flag==1}">
                                 <th>操作</th>
                                 </c:if>
								</tr>
								  <c:forEach items="${tAssignmentQuestionpools }" var="questionPool" varStatus="i">
	                               <c:if test="${questionPool.type != 1 && questionPool.TAssignmentQuestionpool == null}">
	                                <tr>
	                                    <td><a href="${pageContext.request.contextPath }/tcoursesite/question/findTAssignmentItemsByQuestionId?tCourseSiteId=${device.id }&currpage=1&id=${questionPool.questionpoolId}" target="_blank">${questionPool.title }</a></td>
	                                    <td>${questionPool.user.cname }</td>
	                                    <td><fmt:formatDate value="${questionPool.createdTime.time }" pattern="yyyy-MM-dd"/></td>
	                                    <td>${questionPool.description }</td>
	                                    <td>设备题库</td>
	                                    <td>
	                                    	<c:if test="${flag==1}">
	                                        <a href="${pageContext.request.contextPath }/tcoursesite/question/deleteQuestionPoolById?tCourseSiteId=${device.id}&questionId=${questionPool.questionpoolId}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&username=${username}" onclick="return confirm('删除该题库会同时删除下面的试题,确认删除该题库？')">
	                                        	删除</a>
	                                        <a href="javascript:void(0)" onclick="newQuestionPool(${questionPool.questionpoolId},${device.id})">
	                                        	编辑</a>
	                                        <a href="javascript:void(0)" onclick="exportQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Txt导出 </a>
	                                        <a href="javascript:void(0)" onclick="exportExcelQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Excel导出 </a>             
	                                        </c:if>
	                                    </td>
	                                </tr>
	                                <c:forEach items="${questionPool.TAssignmentQuestionpools }" var="questionPool1" varStatus="j">
	                                	<tr>
		                                    <td><a href="${pageContext.request.contextPath }/tcoursesite/question/findTAssignmentItemsByQuestionId?tCourseSiteId=${device.id }&currpage=1&id=${questionPool1.questionpoolId}" target="_blank">${questionPool1.title }</a></td>
		                                    <td>${questionPool1.user.cname }</td>
		                                    <td><fmt:formatDate value="${questionPool1.createdTime.time }" pattern="yyyy-MM-dd"/></td>
		                                    <td>${questionPool1.description }</td>
		                                    <td>设备题库</td>
		                                    <td>
	                                    	<c:if test="${flag==1}">
	                                        <a href="${pageContext.request.contextPath }/tcoursesite/question/deleteQuestionPoolById?tCourseSiteId=${device.id}&questionId=${questionPool.questionpoolId}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&username=${username}" onclick="return confirm('删除该题库会同时删除下面的试题,确认删除该题库？')">
	                                        	删除</a>
	                                        <a href="javascript:void(0)" onclick="newQuestionPool(${questionPool.questionpoolId},${device.id})">
	                                        	编辑</a>
	                                        <a href="javascript:void(0)" onclick="exportQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Txt导出 </a>
	                                        <a href="javascript:void(0)" onclick="exportExcelQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Excel导出 </a>             
	                                        </c:if>
	                                    </td>
		                                </tr>
		                                <c:forEach items="${questionPool1.TAssignmentQuestionpools }" var="questionPool2" varStatus="k">
		                                	<tr>
			                                    <td><a href="${pageContext.request.contextPath }/tcoursesite/question/findTAssignmentItemsByQuestionId?tCourseSiteId=${device.id }&currpage=1&id=${questionPool2.questionpoolId}" target="_blank">${questionPool2.title }</a></td>
			                                    <td>${questionPool2.user.cname }</td>
			                                    <td><fmt:formatDate value="${questionPool1.createdTime.time }" pattern="yyyy-MM-dd"/></td>
			                                    <td>${questionPool2.description }</td>
			                                    <td>设备题库</td>
			                                    <td>
			                                    	<td>
	                                    	<c:if test="${flag==1}">
	                                        <a href="${pageContext.request.contextPath }/tcoursesite/question/deleteQuestionPoolById?tCourseSiteId=${device.id}&questionId=${questionPool.questionpoolId}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&username=${username}" onclick="return confirm('删除该题库会同时删除下面的试题,确认删除该题库？')">
	                                        	删除</a>
	                                        <a href="javascript:void(0)" onclick="newQuestionPool(${questionPool.questionpoolId},${device.id})">
	                                        	编辑</a>
	                                        <a href="javascript:void(0)" onclick="exportQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Txt导出 </a>
	                                        <a href="javascript:void(0)" onclick="exportExcelQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Excel导出 </a>             
	                                        </c:if>
	                                    </td>
			                                    </td>
			                                </tr>
		                                </c:forEach>
	                                </c:forEach>
	                               </c:if>
                               </c:forEach>
                                <c:forEach items="${tAssignmentQuestionpools }" var="questionPool" varStatus="i">
	                               <c:if test="${questionPool.type == 1 && questionPool.TAssignmentQuestionpool == null}">
	                                <tr>
	                                    <td><a href="${pageContext.request.contextPath }/tcoursesite/question/findTAssignmentItemsByQuestionId?tCourseSiteId=${device.id }&currpage=1&id=${questionPool.questionpoolId}" target="_blank">${questionPool.title }</a></td>
	                                    <td>${questionPool.user.cname }</td>
	                                    <td><fmt:formatDate value="${questionPool.createdTime.time }" pattern="yyyy-MM-dd"/></td>
	                                    <td>${questionPool.description }</td>
	                                    <td>公共题库</td>
	                                    <td>
	                                    	<c:if test="${flag==1}">
	                                        <a href="${pageContext.request.contextPath }/tcoursesite/question/deleteQuestionPoolById?tCourseSiteId=${device.id}&questionId=${questionPool.questionpoolId}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&username=${username}" onclick="return confirm('删除该题库会同时删除下面的试题,确认删除该题库？')">
	                                        	删除</a>
	                                        <a href="javascript:void(0)" onclick="newQuestionPool(${questionPool.questionpoolId},${device.id})">
	                                        	编辑</a>
	                                        <a href="javascript:void(0)" onclick="exportQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Txt导出 </a>
	                                        <a href="javascript:void(0)" onclick="exportExcelQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Excel导出 </a>             
	                                        </c:if>
	                                    </td>
	                                </tr>
	                                <c:forEach items="${questionPool.TAssignmentQuestionpools }" var="questionPool1" varStatus="j">
	                                	<tr>
		                                    <td><a href="${pageContext.request.contextPath }/tcoursesite/question/findTAssignmentItemsByQuestionId?tCourseSiteId=${device.id }&currpage=1&id=${questionPool1.questionpoolId}" target="_blank">${questionPool1.title }</a></td>
		                                    <td>${questionPool1.user.cname }</td>
		                                    <td><fmt:formatDate value="${questionPool1.createdTime.time }" pattern="yyyy-MM-dd"/></td>
		                                    <td>${questionPool1.description }</td>
		                                    <td>公共题库</td>
		                                    <td>
	                                    	<c:if test="${flag==1}">
	                                        <a href="${pageContext.request.contextPath }/tcoursesite/question/deleteQuestionPoolById?tCourseSiteId=${device.id}&questionId=${questionPool.questionpoolId}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&username=${username}" onclick="return confirm('删除该题库会同时删除下面的试题,确认删除该题库？')">
	                                        	删除</a>
	                                        <a href="javascript:void(0)" onclick="newQuestionPool(${questionPool.questionpoolId},${device.id})">
	                                        	编辑</a>
	                                        <a href="javascript:void(0)" onclick="exportQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Txt导出 </a>
	                                        <a href="javascript:void(0)" onclick="exportExcelQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Excel导出 </a>             
	                                        </c:if>
		                                    </td>
		                                </tr>
		                                <c:forEach items="${questionPool1.TAssignmentQuestionpools }" var="questionPool2" varStatus="k">
		                                	<tr>
			                                    <td><a href="${pageContext.request.contextPath }/tcoursesite/question/findTAssignmentItemsByQuestionId?tCourseSiteId=${device.id }&currpage=1&id=${questionPool2.questionpoolId}" target="_blank">${questionPool2.title }</a></td>
			                                    <td>${questionPool2.user.cname }</td>
			                                    <td><fmt:formatDate value="${questionPool1.createdTime.time }" pattern="yyyy-MM-dd"/></td>
			                                    <td>${questionPool2.description }</td>
			                                    <td>公共题库</td>
			                                   <td>
	                                    	<c:if test="${flag==1}">
	                                        <a href="${pageContext.request.contextPath }/tcoursesite/question/deleteQuestionPoolById?tCourseSiteId=${device.id}&questionId=${questionPool.questionpoolId}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&username=${username}" onclick="return confirm('删除该题库会同时删除下面的试题,确认删除该题库？')">
	                                        	删除</a>
	                                        <a href="javascript:void(0)" onclick="newQuestionPool(${questionPool.questionpoolId},${device.id})">
	                                        	编辑</a>
	                                        <a href="javascript:void(0)" onclick="exportQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Txt导出 </a>
	                                        <a href="javascript:void(0)" onclick="exportExcelQuestionPoolById(${questionPool.questionpoolId})">
	                                        	Excel导出 </a>             
	                                        </c:if>
	                                    </td>
			                                </tr>
		                                </c:forEach>
	                                </c:forEach>
	                               </c:if>
                               </c:forEach>
						     	</table>
				
  			<input type="hidden" id="labRoomId" name="labRoomId" value="${labRoomId}">
			<input type="hidden" id="deviceName" name="deviceName" value="${deviceName}">
			<input type="hidden" id="deviceNumber" name="deviceNumber" value="${deviceNumber}">
			<input type="hidden" id="username"  name="username"value="${username}">
			<input type="hidden" id="page"  name="page"value="${page}">
    		<input type="hidden" id="pageContext" name="pageContext" value="${pageContext.request.contextPath }">
      	
			</div>
		</div>
		<!-- 新增题库 -->
					<div id="newQuestionPool" class="easyui-window" title="导入题库" closed="true" modal="true" minimizable="true" style="width:600px;height: 300px;padding:20px">
						<form:form action="${pageContext.request.contextPath}/tcoursesite/question/saveQuestionPool?tCourseSiteId=${device.id }" method="POST" modelAttribute="tAssignmentQuestionpool">
								 <div class="add_module cf f14">
					<input type="hidden" id="labRoomId" name="labRoomId" value="${labRoomId}"/>
					<input type="hidden" id="deviceName" name="deviceName" value="${deviceName}"/>
					<input type="hidden" id="deviceNumber" name="deviceNumber" value="${deviceNumber}"/>
					<input type="hidden" id="username"  name="username" value="${username}"/>
                	<form:hidden path="questionpoolId" id="questionpoolId"/>
                    <div class="cf w100p  mt10 mb20">
                        <div class="l w15p lh25">名称</div>
                        <div class="l w45p">
                            <form:input path="title" id="questionTitle" required="true" class="w100p lh25 br3 b1" type="text" />
                        </div>

                    </div>
                    
                    <div class="cf w100p  mt10 mb20">
                        <div class="l w15p lh25">类别信息</div>
                        <div class="l w45p">
                            <form:input path="description" id="description"  class="w100p lh25 br3 b1" type="text" />
                        </div>

                    </div>
                    
                    <div class="cf w100p  mt10 mb20">
                        <div class="l w15p lh25">题库类型</div>
                        <div class="l w45p">
							<form:select  path="type" id="questionType" class="w100p lh25 br3 b1">
								<form:option value="2">设备题库</form:option>
								<form:option value="1">公共题库</form:option>
							</form:select>
                        </div>

                    </div>
                    <%--<div class="cf w100p  mt10 mb20">
                        <div class="l w15p lh25">题库设置</div>
                        <div class="l w45p">
							<form:select  path="isOpen" id="questionIsOpen" class="w100p lh25 br3 b1">							
								<form:option value="2" >测试专用</form:option>
								<form:option value="1" >开放练习</form:option>								
							</form:select>
                        </div>

                    </div>
                    
                    --%><div class="cf w100p  mt10 mb20">
                        <div class="l w15p">父类别</div>
                        <div class="l w45p">
                            <form:select path="TAssignmentQuestionpool.questionpoolId" id="questionpoolParentId" class="w100p lh25 br3 b1">
								<form:option value="">请选择</form:option>
							</form:select>
                        </div>
                    </div>
                </div>
                <div class="cf tc">
                    <input type="submit" class="btn bgb l mt10 poi  plr20 br3 wh" />
                </div>
						</form:form>
					</div>
		<!--  新增题库结束 -->	
	  </div>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/device/viewDeivce.js"></script>
</body>
</html>
