。<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta name="decorator" content="iframe"/>
 <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">设备借用</a></li>
			<li class="end"><a href="javascript:void(0)">编辑</a></li>
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title">编辑设备借用申请</div>
	</div>
	<form:form action="${pageContext.request.contextPath}/device/saveDeviceLending" method="POST" modelAttribute="labRoomDeviceLending">
	<div class="new-classroom">
	<form:hidden path="id"/>
	  <fieldset>
	    <label>借用人：${labRoomDeviceLending.userByLendingUser.cname }</label>
	    <form:hidden path="userByLendingUser.username"/>
	  </fieldset>
	  <fieldset>
	    <label>借用设备：${labRoomDeviceLending.labRoomDevice.schoolDevice.deviceName}</label>
	    <form:hidden path="labRoomDevice.schoolDevice.deviceNumber"/>
	  </fieldset>
	  <fieldset>
	    <label>借用内容：</label>
	    <form:input path="content"/>
	  </fieldset>
	  <fieldset>
	    <label>配件：</label>
	    <form:input path="parts"/>
	  </fieldset>
	  <fieldset>
    <label>申请时间：</label>
    <from:input name="lendingTime" class="easyui-datebox" value="<fmt:formatDate value='${labRoomDeviceLending.lendingTime.time}' pattern='yyyy-MM-dd'/>" />
  	</fieldset>
  <fieldset>
    <label>结归还时间：</label>
    <from:input name="returnTime" class="easyui-datebox" value="<fmt:formatDate value='${labRoomDeviceLending.returnTime.time}' pattern='yyyy-MM-dd'/>" />
  </fieldset>
	</div>
	
        <div class="submit_link">
          <input class="btn" id="save" type="submit" value="确定">
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</form:form>
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
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
