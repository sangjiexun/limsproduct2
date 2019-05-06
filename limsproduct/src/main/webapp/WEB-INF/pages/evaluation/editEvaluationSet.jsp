<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<html>
<head>
	<meta name="decorator" content="iframe"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<!-- 下拉框的样式 -->
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  	<!-- 下拉的样式结束 -->
	
</head>
<body>
	<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">评教管理</a></li>
			<li class="end"><a href="javascript:void(0)">评教时间设置</a></li>
		</ul>
	</div>
  	</div>
  	<div class="right-content">
  		<div id="TabbedPanels1" class="TabbedPanels">
  			<div class="TabbedPanelsContentGroup">
  				<div class="TabbedPanelsContent">
  					<div class="content-box">
  						<div class="title">
					      	<div id="title">评教时间设置</div>
					  	</div>
					  	<form:form action="${pageContext.request.contextPath}/evaluation/saveEvaluationSet" method="POST" modelAttribute="evaluationSet">
					  		<div class="new-classroom">
					  			<fieldset>
					  				<form:hidden path="id"/>
					  				<label style="width:300px;">所属学期：${schoolTerm.termName}</label>
					  				<form:hidden path="termId" value="${schoolTerm.id}" />
					  			</fieldset>
					  			<fieldset>
					  				<label>开始时间：</label>
					  				<input id="time" class="Wdate" type="text" name="startT" 
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',  minDate:'now()'})" style="width:140px;" 
											value="<fmt:formatDate value="${evaluationSet.startTime.time}" 
											pattern="yyyy-MM-dd HH:mm:ss" />" readonly />
					  				<%-- <input type="text" name="startTime" onclick="new Calendar().show(this);" value="${evaluationSet.startTime.time}" required="true" /> --%>
					  			</fieldset>
					  			<fieldset>
					  				<label>结束时间：</label>
					  				<input id="time" class="Wdate" type="text" name="endT" 
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',  minDate:'now()'})" style="width:140px;" 
											value="<fmt:formatDate value="${evaluationSet.endTime.time}" 
											pattern="yyyy-MM-dd HH:mm:ss" />" readonly />
					  				<%-- <input type="text" name="endTime" onclick="new Calendar().show(this);" value="${evaluationSet.endTime.time}" required="true" /> --%>
					  			</fieldset>
					  		</div>
					  		<div class="moudle_footer">
						      	<div class="submit_link">
						      		<input class="btn btn-new" type="submit"></input>
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