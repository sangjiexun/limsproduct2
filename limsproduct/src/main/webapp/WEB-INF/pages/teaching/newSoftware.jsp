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
    <!-- 文件上传的样式和js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
  <script type="text/javascript">
  function testDuplicated(){
	  var code=$("#code").val();
		$.post('${pageContext.request.contextPath}/software/testDuplicated?code='+code,function(data){
			if(data=="isDuplicated"){
				alert("对不起，编号与现存的编号重复，请核实后重新填写！");
			}else{
				alert("编号可用");
			}
		 });
  }
  function isNumber(){
	   var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/ 
	 　　var nubmer = document.getElementById("price").value;
	 
	　　if (!re.test(nubmer)) {
	 　　　　alert("请输入数字");
	 　　　　document.getElementById(input).value = "";
	 　　　　return false;
	 　　}
  }
  function submitForm(){
      if($("#academy").val()=="") {// 所属学院
          alert("请选择所属学院！");
          return false;
      }
     if($("#rooms").val()=="") {// 所属实验室
          alert("请选择所属实验室！");
          return false;
      }
  }
  function saveDocument(){
      document.form_file_ori.action="${pageContext.request.contextPath}/softwareUseInstallDocumentUpload?id=${software.id}&type=1"
      document.form_file_ori.submit();

  }
  function uploadDocument(){
      $('#searchFile').window('open');
  }
  function saveDocument1(){
      document.form_file_ori1.action="${pageContext.request.contextPath}/softwareUseInstallDocumentUpload?id=${software.id}&type=2"
      document.form_file_ori1.submit();

  }
  function uploadDocument1(){
      $('#searchFile1').window('open');
  }
  </script>
	<style>
		.chzn-container .chzn-results{
			height:100px!important;
		}
		.TabbedPanelsContent .content-box{
			margin:0 0 10px;
			overflow:hidden;
		}
	</style>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.software.management"/></a></li>
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
			 <div id="title">新建软件</div>
		</c:if>
		<c:if test="${isNew eq 0 }">
			 <div id="title">编辑软件</div>
		</c:if>
	</div>
	<form:form action="${pageContext.request.contextPath}/saveSoftware?page=${page}" method="POST" modelAttribute="software">
	<div class="new-classroom">
	  <fieldset>
	    <form:hidden path="id"/>
	    <label>软件编号：</label>
	    <form:input path="code" id="code"  required="true" onchange="testDuplicated();"/>
	  </fieldset>
	  <fieldset>
	    <label>软件名称：</label>
	    <form:input path="name" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>软件版本：</label>
	    <form:input path="edition" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label style="width:300px;">有无软件客户端及安装教程：</label>
		<div style="float:left;width:100%;text-indent:10px;line-height:25px;">
		  <form:radiobutton path="property" value="有" required="true"/>有
		  <form:radiobutton path="property" value="无" required="true"/>无
		</div>
	  </fieldset>
	  <fieldset>
	    <label>系统操作要求：</label>
	    <form:input path="operationRequirement"/>
	  </fieldset>
	  <fieldset>
	    <label>软件架构：</label>
	    <form:input path="framework"/>
	  </fieldset>
	  <fieldset>
	    <label>价格（元）：</label>
	    <form:input id="price" path="price" onBlur="isNumber()"/>
	  </fieldset>
	  <%-- <fieldset>
	    <label>容量：</label>
	    <form:input path="labRoomCapacity"
	    onkeyup="value=value.replace(/[^\d]/g,'') "   
		placeholder="请输入大于零的整数"
	    />
	  </fieldset> --%>
	  
	  <%-- <fieldset>
	    <label>采购日期：</label>
	    <input name="purchaseDate" class="easyui-datebox" value="<fmt:formatDate value='${software.purchaseDate.time}' pattern='yyyy-MM-dd'/>" />
	  </fieldset> --%>
	  <fieldset>
	    <label>供应商：</label>
	    <form:input path="supplier"/>
	  </fieldset>
	  <fieldset>
	    <label>供应商联系方式：</label>
	    <form:input path="supplierTel"/>
	  </fieldset>
	  <fieldset>
	    <label>采购负责人：</label>
	    <form:input path="purchasePerson"/>
	  </fieldset>
	  	<fieldset>
		    <label>所属学院</label>
		    <form:select id="academy" path="schoolAcademy.academyNumber" class="chzn-select" required="true">
		      <form:option value="">请选择</form:option>
		      <form:options items="${listSchoolAdademy}" itemLabel="academyName" itemValue="academyNumber"/>
		    </form:select>
	  	</fieldset>
<%-- 	  	<fieldset>
	        <label>关联项目：</label>
	        <form:select path="operationItem" id="items" class="chzn-select"  multiple="true" >
		        <c:forEach items="${operationItems}" var="m">
		        	<form:option value="${m.id}" selected="selected">[${m.lpCodeCustom}] ${m.lpName}</form:option>
		        </c:forEach>
	          	<c:forEach items="${listOperationItem}" var="m">
	            	<form:option value="${m.id}">[${m.lpCodeCustom}] ${m.lpName}</form:option>
	          	</c:forEach>
	        </form:select>
	    </fieldset> --%>
	    <fieldset>
	        <label>所属<spring:message code="all.trainingRoom.labroom" />：</label>
	        <form:select path="labRoom" id="rooms" class="chzn-select"  multiple="true" >
		        <c:forEach items="${labRooms}" var="l">
		        	<form:option value="${l.id}" selected="selected">[${l.labRoomNumber}] ${l.labRoomName}</form:option>
		        </c:forEach>
	          	<c:forEach items="${listLabRoom}" var="l">
	            	<form:option value="${l.id}">[${l.labRoomNumber}] ${l.labRoomName}</form:option>
	          	</c:forEach>
	        </form:select>
	    </fieldset>
	    <%--<fieldset>
	    <label>安装说明（上传附件）：</label>
	    <form:input path="installInstruction"/>
	    </fieldset>
	    --%>
	    <c:if test="${isNew eq 0 }">
		<fieldset>
       <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			
			<div class="content-box">
				<div class="title">
				<div id="title">安装说明（上传附件）</div>
				<a class="btn btn-new" onclick="uploadDocument();">上传文档</a>
			</div>
					
						     
								<table>
								<tr>
								<th>序号</th>
								<th>文档名称</th>
								<th>操作</th>
								</tr>
								<c:forEach items="${software.commonDocuments}" var="d" varStatus="i">
								<c:set var="count" value="0" />
								<c:if test="${d.type==1}"><!-- 文档 -->
								<c:set var="count" value="${count+1}" />
								<tr>
								<td>${count}</td>
								<td>${d.documentName}</td>
								<td>
									<a href="${pageContext.request.contextPath}/downloadDocument?id=${d.id}">下载</a> 
									<a href="${pageContext.request.contextPath}/deleteSoftwareDocument?softwareId=${software.id}&id=${d.id}"  onclick="return confirm('确定要删除吗？')" >删除</a>
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
		<fieldset>
       <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			
			<div class="content-box">
				<div class="title">
				<div id="title">使用说明（上传附件）：</div>
				<a class="btn btn-new" onclick="uploadUseDocument();">上传文档</a>
			</div>
					
						     
								<table>
								<tr>
								<th>序号</th>
								<th>文档名称</th>
								<th>操作</th>
								</tr>
								<c:forEach items="${software.commonDocuments}" var="d" varStatus="i">
								<c:set var="count" value="0" />
								<c:if test="${d.type==2}"><!-- 文档 -->
								<c:set var="count" value="${count+1}" />
								<tr>
								<td>${count}</td>
								<td>${d.documentName}</td>
								<td>
									<a href="${pageContext.request.contextPath}/downloadDocument?id=${d.id}">下载</a> 
									<a href="${pageContext.request.contextPath}/deleteSoftwareDocument?softwareId=${software.id}&id=${d.id}"  onclick="return confirm('确定要删除吗？')" >删除</a>
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
	  </c:if>
	    <fieldset>
	        <label>是否有加密狗：</label>
	        <form:select path="dongle" id="dongle" onchange="changes();">
            	<form:option value="0">否</form:option>
            	<form:option value="1">是</form:option>
	        </form:select>
	    </fieldset>
	    <fieldset>
	        <label>是否有光盘：</label>
	        <form:select path="hasCd" id="hasCd" onchange="changes();">
            	<form:option value="0">否</form:option>
            	<form:option value="1">是</form:option>
	        </form:select>
	    </fieldset>
		<c:if test="${isNew eq 1}">
		<label style="padding: 2%; float: right;">
			<font color=red>软件基本信息保存后，可上传安装说明和使用说明。</font>
		</label>
		</c:if>
		<c:if test="${isNew eq 0 }">
			<fieldset>
				<div class="TabbedPanelsContentGroup">
					<div class="TabbedPanelsContent">

						<div class="content-box">
							<div class="title">
								<div id="title">安装说明（上传附件）</div>
								<a class="btn btn-new" onclick="uploadDocument();">上传文档</a>
							</div>


							<table>
								<tr>
									<th>序号</th>
									<th>文档名称</th>
									<th>操作</th>
								</tr>
								<c:forEach items="${software.commonDocuments}" var="d" varStatus="i">
									<c:set var="count" value="0" />
									<c:if test="${d.type==1}"><!-- 文档 -->
										<c:set var="count" value="${count+1}" />
										<tr>
											<td>${count}</td>
											<td>${d.documentName}</td>
											<td>
												<a href="${pageContext.request.contextPath}/downloadDocument?id=${d.id}">下载</a>
												<a href="${pageContext.request.contextPath}/deleteSoftwareDocument?softwareId=${software.id}&id=${d.id}"  onclick="return confirm('确定要删除吗？')" >删除</a>
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</table>
						</div>
					</div>

				</div>
			</fieldset>
			<fieldset>
				<div class="TabbedPanelsContentGroup">
					<div class="TabbedPanelsContent">

						<div class="content-box">
							<div class="title">
								<div id="title">使用说明（上传附件）：</div>
								<a class="btn btn-new" onclick="uploadDocument1();">上传文档</a>
							</div>


							<table>
								<tr>
									<th>序号</th>
									<th>文档名称</th>
									<th>操作</th>
								</tr>
								<c:forEach items="${software.commonDocuments}" var="d" varStatus="i">
									<c:set var="count" value="0" />
									<c:if test="${d.type==2}"><!-- 文档 -->
										<c:set var="count" value="${count+1}" />
										<tr>
											<td>${count}</td>
											<td>${d.documentName}</td>
											<td>
												<a href="${pageContext.request.contextPath}/downloadDocument?id=${d.id}">下载</a>
												<a href="${pageContext.request.contextPath}/deleteSoftwareDocument?softwareId=${software.id}&id=${d.id}"  onclick="return confirm('确定要删除吗？')" >删除</a>
											</td>
										</tr>
									</c:if>

								</c:forEach>
							</table>
						</div>
					</div>

				</div>
			</fieldset>
		</c:if>
	    <div id="points" style="display: none">
		    <fieldset>
		        <label>点数：</label>
		        <form:input path="points"/>
		    </fieldset>
	    </div>
	</div>
	<div class="moudle_footer">
        <div class="submit_link">
          <input class="btn" id="save" type="submit"  onclick="return submitForm()" value="确定" >
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</div>
	</form:form>
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
	  <!-- 上传使用说明 -->
	  <div id="searchFile1" class="easyui-window" title="上传文档" closed="true" iconCls="icon-add" style="width:400px;height:200px">
		  <form id="form_file_ori1" name="form_file_ori1" method="post"
				enctype="multipart/form-data">
			  <table  border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
					  <td>
						  <div id="queue"></div> <input id="file_upload_ori1"name="file_upload_ori1" type="file" multiple="true">
						  <input type="button" onclick="saveDocument1()" value="上传" />
					  </td>
					  <!-- <div id="queue"></div>
                    <input id="file_upload" name="file_upload" type="file" multiple="true"> -->
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
	<script type="text/javascript">
		$(document).ready(function() {
			var changeVal = $("#dongle").val();
			if(changeVal == 1) {
				$("#points").show();
			}
		});
		function changes() {
			var changeVal = $("#dongle").val();
			if(changeVal == 1) {
				$("#points").show();
			}else {
				$("#points").hide();
			}
		}
	</script>
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
