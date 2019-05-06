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
						<li class="end"><a href="javascript:void(0)">编辑参与人员</a></li>
					</ul>
				</div>
			</div>
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="title">
						<div id="title">编辑人员	 </div>
						<a class="btn btn-return" onclick="window.history.go(-1)">返回</a>
					</div>
					 
			 <div class="content-box">
	<form:form action="${pageContext.request.contextPath}/labconstruction/saveUpdateLabConstructUser?AppId=${user.labConstructApp.id}" name="subForm" method="post" modelAttribute="user" >
		<fieldset class="introduce-box">
					<legend>人员信息</legend>
		<table cellpadding="0" cellspacing="0" id="viewTable">
		<tbody>
		     <tr>
		     	<td>姓名：</td>
		     	<td>
		     	<form:hidden path="id"/>
		     	<form:input path="cname"/>
		     	</td>
			 </tr>
			 <tr>
		     	<td>性别：</td>
		     	<td>
		     	<form:input path="sex"/>
		     	</td>
			 </tr>
			 <tr>
		     	<td>年龄：</td>
		     	<td>
		     	<form:input path="age"/>
		     	</td>
			 </tr>
			 <tr>
		     	<td>职务：</td>
		     	<td>
		     	<form:input path="position"/>
		     	</td>
			 </tr>
			 <tr>
		     	<td>职称：</td>
		     	<td>
		     	<form:input path="jobTitle"/>
		     	</td>
			 </tr>
			<tr>
				<td> 负责内容：</td>
				<td>
		        <form:input path="responsibilityContent"/>
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