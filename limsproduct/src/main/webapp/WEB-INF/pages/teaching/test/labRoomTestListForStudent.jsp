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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/labroom/editDevice.js"></script>
<!-- 下拉框的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
<script type="text/javascript"> 
 
</script>
  
<style>
.tool-box2 th{text-align:left;}
</style>
</head>


<body>

<div class="right-content">	
	<div id="TabbedPanels1" class="TabbedPanels">
	   <ul class="TabbedPanelsTabGroup">
		<c:if test="${device.CDictionaryByAllowSecurityAccess.CCategory=='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber=='1'}">
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomTrainingRest(${device.id},${currpage })">培训计划</a>
		</li>
		</c:if>
		<li class="TabbedPanelsTab " tabindex="0">
		<a href="javascript:void(0);" onclick="editDeviceSettingRest(${device.id})">参数设置</a>
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
		<%--<c:if test="${(device.CDictionaryByAllowSecurityAccess.CCategory =='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber == '1' && device.CDictionaryByTrainType.CNumber == '3')}">
		<li class="TabbedPanelsTab" tabindex="0">
		&lt;%&ndash;<a href="${pageContext.request.contextPath}/tcoursesite/question/findQuestionList?tCourseSiteId=${device.id}"target="_blank">题库</a>
		&ndash;%&gt;<a href="#" onclick="findLabRoomQuestionList(${device.id})">题库</a>
		</li>
		<li class="TabbedPanelsTab selected" tabindex="0">
		<a href="#" onclick="findLabRoomTestList(${device.id})">考试</a>
		</li>
		</c:if>--%>
	</ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			
			<div class="content-box">
				<div class="title">
				<div id="title">考试</div>
				<a class="btn btn-new"  href="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1" >返回</a>
				<a class="btn btn-new" href="${pageContext.request.contextPath}/teaching/test/newLabRoomTest?labRoomId=${device.id}">新增考试</a>
				</div>											     
				<table  id="my_show">
				<thead>
				    <tr>                   
				        <th>序号</th>
				        <th>标题</th>
				        <th>创建时间</th>
				        <th>状态</th>
				        <th>开始日期</th>
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
				    		<a href="${pageContext.request.contextPath}/teaching/test/editLabRoomTest?labRoomId=${labRoomId}&testId=${current.id}">编辑考试</a>|
					        <a onclick="history.go(0)" href="${pageContext.request.contextPath}/teaching/test/deployLabRoomTest?deviceId=${device.id}&testId=${current.id}&labRoomId=${labRoomId}&deviceNumber=${deviceNumber}&deviceName=${deviceName}&username=${username}">发布考试</a>|
					        <a onclick="return confirm('是否确认删除测试？')" href="${pageContext.request.contextPath}/teaching/test/deleteLabRoomTestById?labRoomId=${labRoomId}&testId=${current.id}">删除考试</a>					      
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
				    </td>
				    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${current.TAssignmentControl.startdate.time}" type="both"/></td>
				    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${current.TAssignmentControl.duedate.time}" type="both"/></td>
				    <td><font >0--${current.TAssignmentAnswerAssign.score }</font></td>
				    <td><a href="${pageContext.request.contextPath}/teaching/test/testGradingList?deviceId=${device.id}&testId=${current.id}" target="_blank">查看</a></td>
				</tr>
				</c:forEach>
				</tbody>
				<!-- 分页导航 -->
				</table>
				<input type="hidden" id="labRoomId" name="labRoomId" value="${labRoomId}">
				<input type="hidden" id="page"  name="page"value="${page}">
    			<input type="hidden" id="pageContext" name="pageContext" value="${pageContext.request.contextPath }">
			</div>
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
		</script>
		<!-- 下拉框的js -->						
</body>
</html>
