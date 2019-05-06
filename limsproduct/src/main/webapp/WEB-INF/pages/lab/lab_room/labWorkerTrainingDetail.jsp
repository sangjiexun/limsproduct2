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
  </script>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">实训室队伍培训登记</a></li>
			<c:if test="${flag==1}">
			<li class="end"><a href="javascript:void(0)">查看</a></li>
			</c:if>
			<c:if test="${flag==0}">
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
      <div id="title">实训室队伍培训登记</div>
	</div>
	<c:if test="${flag==1}"> 
	<form:form action="${pageContext.request.contextPath}/labRoom/saveLabWorkerTraining?labWorkerId=${labWorkerId}" name="base_form" method="POST" modelAttribute="labWorkerTraining">
	<div class="new-classroom">
	  <fieldset>
	    <form:hidden path="id"/>
	    <label>主办单位：</label>
	    <form:input path="organizer" class="easyui-validatebox" required="true" disabled="true"/>
	  </fieldset>
	  <fieldset>
	    <label>成绩：</label>
	    <form:input path="score" class="easyui-validatebox" required="true" disabled="true"/>
	  </fieldset>
	  <fieldset>
	    <label>学习内容：</label>
	    <form:input path="learnContent" class="easyui-validatebox" required="true" disabled="true"/>
	  </fieldset>
	  <fieldset>
	    <label>开始时间：</label>
	    <input name="beginTime" class="easyui-datebox" value="<fmt:formatDate value='${labWorkerTraining.beginTime.time}' pattern='yyyy-MM-dd'/>" disabled="true"/>
	  </fieldset>
	  <fieldset>
	    <label>结束时间：</label>
	    <input name="endTime" class="easyui-datebox" value="<fmt:formatDate value='${labWorkerTraining.endTime.time}' pattern='yyyy-MM-dd'/>" disabled="true"/>
	  </fieldset>
	  <fieldset>
       <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			
			<div class="content-box">
				<div class="title">
				<div id="title">相关文档</div>
 				<%--<a class="btn btn-new" onclick="uploadDocument();">上传文档</a>
			--%></div>
					
						     
								<table>
								<tr>
								<th>序号</th>
								<th>文档名称</th>
								<th>操作</th>
								</tr>
								<c:forEach items="${labWorkerTraining.commonDocuments}" var="d" varStatus="i">
								<c:set var="count" value="0" />
								<c:if test="${d.type==1}"><!-- 文档 -->
								<c:set var="count" value="${count+1}" />
								<tr>
								<td>${count}</td>
								<td>${d.documentName}</td>
								<td>
									<a href="${pageContext.request.contextPath}/device/downloadDocument?id=${d.id}">下载</a> 
									<a href="${pageContext.request.contextPath}/labRoom/deleteLabWorkerTrainingDocument?id=${d.id}&labWorkerTrainingId=${labWorkerTraining.id}"  onclick="return confirm('确定要删除吗？')" >删除</a>
									<%--<a href="${pageContext.request.contextPath}/${d.documentUrl}" target="_blank">查看</a>
								--%></td>
								</tr>
								</c:if>
						    	
						     	</c:forEach>
						     	</table>
				
			</div>
		</div>
			
	  </div>
	  </fieldset>
	</div>
	<div class="moudle_footer">
        <div class="submit_link">
          <input class="btn" id="save" type="submit" value="确定">
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</div>
	</form:form>
	</c:if>
	<c:if test="${flag==0}"> 
	<form:form action="${pageContext.request.contextPath}/labRoom/saveLabWorkerTraining?labWorkerId=${labWorkerId}" name="base_form" method="POST" modelAttribute="labWorkerTraining">
	<div class="new-classroom">
	  <fieldset>
	    <form:hidden path="id"/>
	    <label>主办单位：</label>
	    <form:input path="organizer" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>成绩：</label>
	    <form:input path="score" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>学习内容：</label>
	    <form:input path="learnContent" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>开始时间：</label>
	    <input name="beginTime" class="easyui-datebox" value="<fmt:formatDate value='${labWorkerTraining.beginTime.time}' pattern='yyyy-MM-dd'/>"/>
	  </fieldset>
	  <fieldset>
	    <label>结束时间：</label>
	    <input name="endTime" class="easyui-datebox" value="<fmt:formatDate value='${labWorkerTraining.endTime.time}' pattern='yyyy-MM-dd'/>"/>
	  </fieldset>
	  <fieldset>
       <div class="TabbedPanelsContentGroup">
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
								<c:forEach items="${labWorkerTraining.commonDocuments}" var="d" varStatus="i">
								<c:set var="count" value="0" />
								<c:if test="${d.type==1}"><!-- 文档 -->
								<c:set var="count" value="${count+1}" />
								<tr>
								<td>${count}</td>
								<td>${d.documentName}</td>
								<td>
									<a href="${pageContext.request.contextPath}/labRoom/downloadLabWorkerTrainingDocument?id=${d.id}">下载</a> 
									<a href="${pageContext.request.contextPath}/labRoom/deleteLabWorkerTrainingDocument?id=${d.id}&labWorkerTrainingId=${labWorkerTraining.id}"  onclick="return confirm('确定要删除吗？')" >删除</a>
									<%--<a href="${pageContext.request.contextPath}/${d.documentUrl}" target="_blank">查看</a>
								--%></td>
								</tr>
								</c:if>
						    	
						     	</c:forEach>
						     	</table>
			</div>
		</div>
			
	  </div>
	  </fieldset>
	</div>
	<div class="moudle_footer">
        <div class="submit_link">
          <input class="btn" id="save" type="submit" value="确定">
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</div>
	</form:form>
	</c:if>
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
		 </script>
       <script type="text/javascript">
	/* var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1"); */
	
	function saveDocument(){
		document.form_file_ori.action="${pageContext.request.contextPath}/labRoom/labWorkerTrainingDocumentUpload?id=${labWorkerTraining.id}"
		document.form_file_ori.submit();

	}
	function uploadDocument(){
		$('#searchFile').window('open');	
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
