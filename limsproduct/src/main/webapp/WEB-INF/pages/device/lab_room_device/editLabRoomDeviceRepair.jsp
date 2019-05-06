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
  <script type="text/javascript">
  function isNumber(){
	   var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/ 
	 　　var nubmer = document.getElementById("price").value;
	 
	　　if (!re.test(nubmer)) {
	 　　　　alert("请输入数字");
	 　　　　document.getElementById(input).value = "";
	 　　　　return false;
	 　　}
  }
  </script>
  <body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">设备维修登记</a></li>
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
      <div id="title">设备维修登记</div>
	</div>
	<form:form action="${pageContext.request.contextPath}/device/saveLabRoomDeviceRepair?labRoomDeviceId=${labRoomDeviceId}" method="POST" modelAttribute="labRoomDeviceRepair">
	<div class="new-classroom">
	<fieldset>
	    <label>设备：${device.schoolDevice.deviceName}</label>
	</fieldset>
	<fieldset>
	    <label>设备编号：${device.schoolDevice.deviceNumber}</label>
	</fieldset>
	<fieldset>
	    <label>所属实训室：${device.labRoom.labRoomName}</label>
	</fieldset>
	<fieldset>
	 <form:hidden path="id" />
	 <label>维修项目：</label>
	    <form:input id="repairProject" path="repairProject" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>维修费用：</label>
	    <form:input id="price" path="repairCost" required="true" onBlur="isNumber()"/>
	  </fieldset>
	  <fieldset>
	  <label>报修人：</label>
	    <form:select id="usernameManager" path="user.username" required ="true"  class="chzn-select">
						      	<form:option value="${device.user.username }">
						      	${device.user.username }${device.user.cname }
						      	</form:option>
						      	<form:option value="">
						      	- - - 请选择- - - 
						      	</form:option>
						      	<c:forEach items="${users}" var="t">
					              <form:option value="${t.key}">[${t.value}]${t.key}</form:option>
					             </c:forEach>
								</form:select>
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
   <c:if test="${flag==1}">
   <fieldset>
   <div class="TabbedPanelsContent">
			
			<div class="content-box">
				<div class="title">
				<div id="title">相关文档</div>
				<a class="btn btn-new" onclick="uploadDocument();">上传文档</a>
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
									<a href="${pageContext.request.contextPath}/device/deleteDeviceRepairDocument?id=${d.id}&labRoomDeviceRepairId=${labRoomDeviceRepair.id}&labRoomDeviceId=${labRoomDeviceId}"  onclick="return confirm('确定要删除吗？')" >删除</a>
								</td>
								</tr>
						    	
						     	</c:forEach>
						     	</table>
		</div>
   </fieldset>
   </c:if>
</div>
	
        <div class="submit_link">
          <input class="btn" id="save" type="submit" value="确定">
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</form:form>
	<!-- 上传图片 --> 				
	<div id="searchFile" class="easyui-window" title="上传文档" closed="true" iconCls="icon-add" style="width:400px;height:200px">
	    <form id="form_file_ori" name="form_file_ori" method="post"
					enctype="multipart/form-data">
           <table  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
            <td>
            <div id="queue"></div> <input id="file_upload_ori"name="file_upload_ori" type="file" multiple="true"> 
            <input type="button" onclick="saveDocument()" value="上传" />
            </td>
          	<!-- <div id="queue"></div>
		    <input id="file_upload" name="file_upload" type="file" multiple="true"> -->
            </tr>   
            </table>
         </form>
     </div>	
     <script type="text/javascript">
	/* var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1"); */
	
	function saveDocument(){
		document.form_file_ori.action="${pageContext.request.contextPath}/device/labRoomDeviceRepairDocumentUpload?id=${labRoomDeviceRepair.id}&labRoomDeviceId=${labRoomDeviceId}"
		document.form_file_ori.submit();

	}
	function uploadDocument(){
		$('#searchFile').window('open');	
	}
	</script>
      <!-- 上传图片结束 --> 
	<%--<div id="searchFile" class="easyui-window" title="上传文档" closed="true" iconCls="icon-add" style="width:400px;height:200px">
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
	--%><!-- 下拉框的js -->
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
