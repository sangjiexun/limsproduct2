  <%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
 <jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  <style type="text/css" media="screen">
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/icon.css");
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/gray/easyui.css");
			@import url("${pageContext.request.contextPath}/css/style.css");
			@import url("${pageContext.request.contextPath}/static_limsproduct/css/global_static.css");
</style>
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
 <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
<%--<!-- 文件上传的样式和js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/device/editDevice.js"></script>
  --%><!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  
  <script type="text/javascript">
  //提交表单
  function submitForm(){
    if($.trim($("#baseNumber").val())=="")
    {
      alert("请填写基地编号！");
      return false;
    }
    if($.trim($("#baseName").val())=="")
    {
      alert("请填写基地名称！");
      return false;
    }
   
    document.base_form.action="${pageContext.request.contextPath}/labBase/saveLabBase";
    document.base_form.submit();
  }
  
  </script>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">设备报修登记</a></li>
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
      <c:if test="${flag==1 }"><div id="title">设备报修登记</div></c:if>
      <c:if test="${flag!=1 }"><div id="title">设备报修登记</div></c:if>
	</div>
	<form:form name="base_form" method="POST" modelAttribute="labRoomDeviceRepair">
	<div class="new-classroom">
	  <fieldset>
	    <form:hidden path="id"/>
	    <label>设备：</label>
	    <form:input path="labRoomDevice.schoolDevice.deviceName" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  <fieldset>
	 <label>维修项目：</label>
	    <form:input id="repairProject" path="repairProject" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>维修费用：</label>
	    <form:input path="repairCost" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>报修人：</label>
	    <form:input path="user.cname" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  <fieldset>
	  <label>厂家:</label>
	  <form:input path="factory"/>
    </fieldset>	  
	  <fieldset>
	  <label>厂家联系方式:</label>
	  <form:input path="factoryPhone"/>
	  </fieldset> 
	  <fieldset>
    <label>报修时间：</label>
    <input name="repairTime" class="easyui-datebox" value="<fmt:formatDate value='${labRoomDeviceRepair.repairTime.time}' pattern='yyyy-MM-dd'/>" />
  	</fieldset>
  <fieldset>
    <label>修复时间：</label>
    <input name="restoreTime" class="easyui-datebox" value="<fmt:formatDate value='${labRoomDeviceRepair.restoreTime.time}' pattern='yyyy-MM-dd'/>" />
  </fieldset>
  <fieldset>
	  <label>维修情况描述：</label>
	  <form:input path="repairRecords"/>
   </fieldset>
	<fieldset>
   <div class="TabbedPanelsContent">
			
			<div class="content-box">
				<div class="title">
				<div id="title">相关文档</div>
				
			</div>
								<table>
								<tr>
								<th>序号</th>
								<th>文档名称</th>
								<th>操作</th>
								</tr>
								<c:forEach items="${labRoomDeviceRepair.commonDocuments}" var="d" varStatus="i">
								<c:set var="count" value="0" />
							
								<c:set var="count" value="${count+1}" />
								<tr>
								<td>${count}</td>
								<td>${d.documentName}</td>
								<td>
									<a href="${pageContext.request.contextPath}/device/downloadDeviceRepairDocument?id=${d.id}">下载</a> 
								</td>
								</tr>
						    	
						     	</c:forEach>
						     	</table>
		</div>
   </fieldset>
	</div>
	<div class="moudle_footer">
        <div class="submit_link">
          <%--<input class="btn" id="save" type="button" onclick="submitForm();" value="确定">
		  --%><input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</div>
	</form:form>
	<!-- 上传图片 --> 				
	  <div id="searchFile" class="easyui-window" title="上传文档" closed="true" iconCls="icon-add" style="width:400px;height:200px">
	    <form id="form_file" method="post" enctype="multipart/form-data">
           <table  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
            <td>
          	<div id="queue"></div>
		    <input id="file_upload" name="file_upload" type="file" multiple="true">
            </tr>   
            </table>
         </form>
     </div>	
      <!-- 上传图片结束 --> 
	
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
