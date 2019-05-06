<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>


<html>
<head>
<meta name="decorator" content="iframe"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式 -->	
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/is_LeftList.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/is_Searcher.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/is_SystemUI_Allpages.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
</head>

<div class="navigation">
<div id="navigation">
	<ul>
		<li><a href="javascript:void(0)"><spring:message code="left.labconstruction.management"/></a></li>
		<li class="end"><a href="javascript:void(0)">预算科目管理</a></li>
	</ul>
</div>
</div>


<!-- 结项申报列表 -->

<!-- 查询表单 -->
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">

<div class="title">
	<div id="title">编辑预算科目</div>
</div>   	
    		 		
<form:form action="${pageContext.request.contextPath}/saveCProjectBudget" method="POST" modelAttribute="cProjectBudget">
<fieldset class="introduce-box">
			<legend>基础信息（带<font color=red>*</font>为必填项）<input type="hidden" value="" id="xsd"></legend>
<table cellpadding="0" cellspacing="0" id="viewTable">
<tbody>
    <tr>
      <td class="label" valign="top">预算科目名称：</td>
      <td class="label" valign="top"><form:input id="name" path="name"></form:input></td>
	</tr>
	<form:hidden path="id" value="${id}" />
</tbody>
</table>
</fieldset>    

        <input type="submit" value="确定" />
        <input type="button" value="取消" onclick="window.history.go(-1);" />
</form:form> 
            
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
      '.chzn-select'           : {search_contains:true},
      '.chzn-select-deselect'  : {allow_single_deselect:true},
      '.chzn-select-no-single' : {disable_search_threshold:10},
      '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
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
</body>
</html>