<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
</head>
  
<body>
  	<div class="navigation">
	<div id="navigation">
		<ul>
		    <li><a href="javascript:void(0)"><spring:message code="left.trainingRoom.infoManagement"/></a></li>
			<li><a href="javascript:void(0)"><spring:message code="left.trainingAnnex.management"/></a></li>
			<c:if test="${isNew eq 1 }">
			<li class="end"><a href="javascript:void(0)">新建</a></li>
			</c:if>
			
			<c:if test="${isNew eq 0 }">
			<li class="end"><a href="javascript:void(0)">编辑</a></li>
			</c:if>
		</ul>
	</div>
  	</div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <c:if test="${isNew eq 1 }">
      <div id="title">新建<spring:message code="all.trainingRoom.labroom"/></div>
      </c:if> 
      
      <c:if test="${isNew eq 0 }">
      <div id="title">编辑<spring:message code="all.trainingRoom.labroom"/></div>
      </c:if>
	</div>
	<form:form action="${pageContext.request.contextPath}/labAnnex/saveLabAnnex?page=${page}" method="POST" modelAttribute="labAnnex">
	<div class="new-classroom">
	  <fieldset>
	  <form:hidden path="id"/>
	    <label><spring:message code="all.trainingRoom.labroom"/>名称：<font color="red">*</font></label>
	    <form:input path="labName" required="true"/>
	  </fieldset>
	  <%--<fieldset>
	    <label><spring:message code="all.trainingRoom.labroom"/>简称：<font color="red">*</font></label>
	    <form:input path="labShortName" required="true"/>
	  </fieldset>--%>
	  <fieldset>
	    <label><spring:message code="all.trainingRoom.labroom"/>英文名称：<font color="red">*</font></label>
	    <form:input path="labEnName" required="true"/>
	  </fieldset>
	  <%--<fieldset>
		  <label> 所属学科：<font color="red">*</font></label>
		  <form:select path="labSubject" class="chzn-select">
			  <c:forEach items="${subject12s}" var="subject">
				  <form:option value="${subject.SNumber}">${subject.SName}</form:option>
			  </c:forEach>
		  </form:select>
	  </fieldset>--%>
	  <fieldset>
	     <label> <spring:message code="all.trainingRoom.labroom"/>类别：<font color="red">*</font></label>
		<form:select path="CDictionaryByLabAnnex.id" class="chzn-select" required="true">
		<form:option value="">请选择</form:option>
		<form:options items="${CDictionary}" itemLabel="CName" itemValue="id"/>
		</form:select>
       </fieldset>
	   <%--<fieldset>
	    <label>管理机构：<font color="red">*</font></label>
	    <form:input path="belongDepartment" required="true"/>
	  </fieldset>--%>
	  <fieldset>
	    <label>所属中心：<font color="red">*</font></label>
		  <form:select path="labCenter.id" class="chzn-select">
			  <form:options items="${listLabCenter}" itemLabel="centerName" itemValue="id"/>
		  </form:select>
	  </fieldset>
	   <%--<fieldset>
	   <label>联系方式：<font color="red">*</font></label>
	    <form:input path="contact" required="true"/>
	  </fieldset>--%>
	  <fieldset>
	    <label><spring:message code="all.trainingRoom.labroom"/>简介：<font color="red">*</font></label>
	    <form:textarea path="labDescription" style="width:1000px;height:100px" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>规章制度：<font color="red">*</font></label>
	    <form:textarea path="labAttention" style="width:1000px;height:100px" required="true"/>
	    <label>获奖信息：<font color="red">*</font></label>
	    <form:textarea path="awardInformation" style="width:1000px;height:100px" required="true"/>
	  </fieldset>
	</div>
	<div class="moudle_footer">
        <div class="submit_link">
			<input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
          	<input class="btn" id="save" type="submit" value="确定">
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
