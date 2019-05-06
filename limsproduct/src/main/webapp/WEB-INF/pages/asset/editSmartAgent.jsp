<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  <script type="text/javascript">
  </script>
	<style type="text/css">
		.moudle_footer {
			background-color: white;
		}
	</style>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.powerController.list"/></a></li>
			<c:if test="${flag==0}"><li class="end"><a href="javascript:void(0)">新建</a></li></c:if>
			<c:if test="${flag==1}"><li class="end"><a href="javascript:void(0)">编辑</a></li></c:if>
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
    <c:if test="${flag==0}">
      <div id="title">新建电源控制器</div>
	</c:if>
	<c:if test="${flag==1}">
      <div id="title">编辑电源控制器</div>
	</c:if>
	</div>
	<form:form action="${pageContext.request.contextPath}/asset/saveSmartAgent" method="POST" modelAttribute="smartAgent">
	<div>
	  <fieldset>
	    <label>编号：</label>
	    <c:if test="${flag==0}">
	    <form:input path="serialNo" required="true"/>
	    </c:if>
	    <c:if test="${flag==1}">
      	<form:input path="serialNo" required="true" readonly="true" style="background:#CCCCCC"/>
		</c:if>
	  </fieldset>
	  <fieldset>
	    <label>设备名称：</label>
	    <form:input path="deviceName" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>电源控制器ip：</label>
	    <form:input path="currIp" required="true"/>
	  </fieldset>
	  <fieldset>
	  	<label>物联服务器：</label>
	    <form:select path="dbhost" required="true" class="chzn-select">
	    <form:options items="${server}" itemLabel="serverName" itemValue="serverIp"/>
	    </form:select>
	  </fieldset>
	</div>
	<div class="moudle_footer">
        <div class="submit_link">
          <input class="btn" id="save" type="submit" value="确定">
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
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
