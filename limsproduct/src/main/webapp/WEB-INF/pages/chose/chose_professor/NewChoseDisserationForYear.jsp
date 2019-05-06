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
    if($.trim($("#student").val())=="")
    {
      alert("请选择学生！");
      return false;
    }
    if($.trim($("#theYear").val())=="")
    {
      alert("请填写届！");
      return false;
    }
    var finishTime=$('#finishTime').datebox('getValue');
    document.center_form.action="${pageContext.request.contextPath}/nwuChose/SaveChoseDisserationForYear?finishTime="+finishTime;
    document.center_form.submit();
  }
  </script>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">新建学年论文</a></li>
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title">新建学年论文</div>
	</div>
	<form:form name="center_form" method="POST" modelAttribute="dessitationForYear">
	<div class="new-classroom">
		<form:hidden path="id"/>
		<form:hidden path="state"/>
		<form:hidden path="studentCname"/>
		<form:hidden path="documentId"/>
		<form:hidden path="teacher"/>
		<fieldset>
		    <label>论题：</label>
		    <form:input path="theme" id="theme" />
		</fieldset>
		<fieldset>
		    <label>要求：</label>
		    <form:input path="requirements" id="requirements" />
		</fieldset>
		
		<fieldset>
		    <label>届：</label>
		    <form:input path="theYear" id="theYear"/>
		</fieldset>
		
	  <fieldset>
		<label>学生：</label>
		<form:select path="student" id="student">
		<form:option value="">请选择</form:option>
		<c:forEach items="${mystudentsForYear}" var="curr">
			<form:option value="${curr.username}">${curr.cname }</form:option>
		</c:forEach>
		</form:select>
	  </fieldset>
	  
		<fieldset>
		    <label>完成时间：</label>
		    <form:input  class="easyui-datebox" id="finishTime" path="finishTime" name="finishTime"  type="text"  onclick="new Calendar().show(this);" style="width:100px;" />
		</fieldset>
	</div>
	<div class="moudle_footer">
        <div class="submit_link">
          <input class="btn" id="save" type="button" onclick="submitForm();" value="确定">
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
