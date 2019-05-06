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
			<li><a href="javascript:void(0)">培训进修登记</a></li>
			<li class="end"><a href="javascript:void(0)">新建</a></li>
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title">新建培训进修登记</div>
	</div>
	<form:form action="${pageContext.request.contextPath}/labRoom/saveLabWorkerTraining?labWorkerId=${labWorkerId}" method="POST" modelAttribute="labWorkerTraining">
	<div class="new-classroom">
	<input type="hidden" path="id" name="id"/>
	 <fieldset>
	    <label>培训类型：</label>
	    <form:select path="CDictionary.id" class="chzn-select">
      	<form:options items="${trainingTypes}" itemLabel="CName" itemValue="id"/>
    	</form:select>
      </fieldset>
	  
	  <fieldset>
	    <label>主办单位：</label>
	    <form:input path="organizer"/>
	  </fieldset>
	  <fieldset>
	    <label>学习内容：</label>
	    <form:input path="learnContent"/>
	  </fieldset>
	  <fieldset>
	    <label>成绩：</label>
	    <form:input path="score"/>
	  </fieldset>
	  <fieldset>
    <label>开始时间：</label>
    <from:input name="beginTime" class="easyui-datebox" value="<fmt:formatDate value='${labWorkerTraining.beginTime.time}' pattern='yyyy-MM-dd'/>" />
  	</fieldset>
  <fieldset>
    <label>结束时间：</label>
    <from:input name="endTime" class="easyui-datebox" value="<fmt:formatDate value='${labWorkerTraining.endTime.time}' pattern='yyyy-MM-dd'/>" />
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
