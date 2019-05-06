<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="iframe"/>

<!-- 下拉框的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
<style>
    .content-box td{
        padding:0;
    }
    .list_form td{
        height:50px;
        line-height:50px;
        padding:0 0 10px 0;
    }
</style>
  
  
<script type="text/javascript">
//跳转
function targetUrl(url)
{
  document.queryForm.action=url;
  document.queryForm.submit();
}

//取消查询
function cancel(){
	window.location.href="${pageContext.request.contextPath}/log/listOperationLog?currpage=1";
}
//全选
function checkAll()
{
  if($("#check_all").attr("checked"))
  {
    $(":checkbox").attr("checked", true);
  }
  else
  {
    $(":checkbox").attr("checked", false);
  }
}
function submitForm()
{
  var flag = false;  //是否有checkbox被选中
  var ids = "";
  $("input[name='items']:checked").each(function(){
      ids += $(this).val()+",";
		flag = true;
	});
	
	if(flag)
	{
	  if(ids.length > 0)
	  {
	  	ids = ids.substring(0, ids.length-1);  //去掉最后的逗号
	  }
	  location.href="${pageContext.request.contextPath}/log/deleteOperationLog?logIds="+ids;
	}
	else
	{
	  alert("您还没有勾选呦");
	}
}
</script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
		<li class="end"><a href="javascript:void(0)"><spring:message code="left.innovativeProduction.list"/></a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
     <form:form action="${pageContext.request.contextPath}/basic_data/saveRegistrationAchievements" method="POST" modelAttribute="innovationAchievementRegistration">
	<div class="new-classroom">
	<%-- <fieldset>
	    <label>姓名：</label>
	    <form:input path="username" required="true"/>
	  </fieldset>
	<fieldset> --%>
	<table>
<tbody>	
			<tr>
			<form:hidden path="id"/>
			<label>姓名：</label>
	        <form:input path="cname" required="true"/>
			</tr>
			<tr>
			<label>所使用的实训室：</label>
	        <form:select path="labRoomName">
	        <form:options items="${labRoom}" itemLabel="labRoomName" itemValue="labRoomName"/>
	        </form:select>
			</tr>
			<tr>
			<label>创新成果名称：</label>
	        <form:input path="innovationName" required="true"/>
			</tr>
			<tr>
			<label>分值：</label>
	        <form:input path="score" required="true"/>
			</tr>
</tbody>
</table>
	</div>
	<div class="moudle_footer">
        <div class="submit_link">
          <input class="btn" id="save" type="submit" value="确定">
		  <input class=" btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</div>
	</form:form>
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
