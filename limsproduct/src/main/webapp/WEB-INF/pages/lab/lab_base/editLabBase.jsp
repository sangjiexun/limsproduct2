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
  //提交表单
  function submitForm(){
    if($.trim($("#labNumber").val())=="")
    {
      alert("请填写基地编号！");
      return false;
    }
    if($.trim($("#labName").val())=="")
    {
      alert("请填写基地名称！");
      return false;
    }
   
    document.base_form.action="${pageContext.request.contextPath}/labBase/saveLabBase?page="+${page};
    document.base_form.submit();
  }
  </script>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.base.management"/></a></li>
			<c:if test="${flag==1 }"><li class="end"><a href="javascript:void(0)">编辑</a></li></c:if>
		    <c:if test="${flag!=1 }">
			<li class="end"><a href="javascript:void(0)">新建</a></li>
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
      <c:if test="${flag==1 }"><div id="title">编辑基地信息</div></c:if>
      <c:if test="${flag!=1 }">
	  <div id="title">新建基地信息</div>
	  </c:if>
	</div>
	<form:form name="base_form" method="POST" modelAttribute="labAnnex">
	<div class="new-classroom">
	  <fieldset>
	    <form:hidden path="id"/>
	    <label><spring:message code="all.training.name"/>基地编号：</label>
	    <form:input path="labNumber" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  
	  <fieldset>
	    <label>基地名称：</label>
	    <form:input path="labName" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  
	  <fieldset>
	    <label>基地面积：</label>
	    <form:input path="labCapacity" class="easyui-validatebox" required="true"/>
	  </fieldset>
		<fieldset>
			<label>所属学院：</label>
			<%--<form:select  path="belongDepartment" class="chzn-select">
				<form:option value="" >请选择</form:option>
				<form:options items="${listSchoolAdademy}" itemLabel="academyName" itemValue="academyNumber" />
			</form:select>--%>
			<select id="academy" name="belongDepartment" class="chzn-select">
				<option value="">请选择【${labAnnex.belongDepartment}】</option>
				<c:forEach items="${listSchoolAdademy}" var="cur">
					<c:if test="${cur.academyNumber eq labAnnex.belongDepartment}">
						<option value="${cur.academyNumber}" selected="selected">${cur.academyName}</option>
					</c:if>
					<c:if test="${cur.academyNumber ne labAnnex.belongDepartment}">
						<option value="${cur.academyNumber}">${cur.academyName}</option>
					</c:if>
				</c:forEach>
			</select>
		</fieldset>

	</div>
	<div class="moudle_footer">
        <div class="submit_link">
			<input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
          	<input class="btn" id="save" type="button" onclick="submitForm();" value="确定">
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
