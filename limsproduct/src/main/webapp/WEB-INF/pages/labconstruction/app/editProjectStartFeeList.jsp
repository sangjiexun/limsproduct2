<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html >  
<head>
<meta name="decorator" content="iframe"/>  
<title></title>

  
  <!-- 下拉框的样式 -->
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 --> 
  
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>

</head>
<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)"><spring:message code="left.labconstruction.management"/></a></li>
						<li class="end"><a href="javascript:void(0)">编辑经费预算</a></li>
					</ul>
				</div>
			</div>
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="title">
						<div id="title">编辑经费预算	 </div>
					</div>
					 
			 <div class="content-box">
	<form:form action="${pageContext.request.contextPath}/labconstruction/saveUpdateProjectStartFeeList?AppId=${fee.projectStartedReport.id}&flag=${flag}" method="post" modelAttribute="fee" >
		<fieldset class="introduce-box">
					<legend>经费信息</legend>
		<table cellpadding="0" cellspacing="0" id="viewTable">
		<tbody>
		     <tr>
		     	<td>预算科目：</td>
		     	<td>
		     	<form:hidden path="id"/>
		     	<form:select path="CFundAppItem.id" class="chzn-select">
		     	<form:options items="${fundTypes}" itemLabel="name" itemValue="id"/>
		     	</form:select>
		     	</td>
			</tr>
			<tr>
				<td> 金额：</td>
				<td>
		        <form:input path="amount"/>
		        </td>
			</tr>
			<tr>
				<td>说明：</td>
				<td>
		        <form:input path="budgetaryItem"/>
		        </td>
			</tr>
		</tbody>
		</table>
		</fieldset>
		


                     <fieldset>      
					<tr>
					<td colspan="5">
					<input type="submit" value="确定" />
        			<input type="button" value="取消" onclick="window.history.go(-1);" />
					</td>
					</tr>
					
        			</fieldset>
			</form:form>
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