<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  <!-- 文件上传的样式和js开始 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  
<!-- 文件上传的样式和js结束 -->
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  <script type="text/javascript">
  //上传论文
  <%-- function uploadDessistationForYear(id){
	  $("#searchFile").show();
	  $("#searchFile").window('open');
	  $("#file_upload").uploadify({
			'fileTypeExts': "*.doc;*.docx;*.ppt;*.pptx;*.xls;*.xlsx;*.pdf;*.txt",
			'formData':{'id':id},    //传值
			'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
			'uploader':'${pageContext.request.contextPath}/uploadDessistationForYear;jsessionid=<%=session.getId()%>',		//提交的controller和要在火狐下使用必须要加的id
			buttonText:'选择文件',
			onQueueComplete : function(data) {//当队列中的所有文件全部完成上传时触发	
			//当上传完所有文件的时候关闭对话框并且转到显示界面
			$("#searchFile").window('close');	  
			window.location.href="${pageContext.request.contextPath}/myChoseDisserationForYear";          	
			}
		});
  } --%>
   //上传论文
  function uploadDessistation(){
	  $("#searchFile").show();
	  $("#searchFile").window('open');
	  	<%-- $("#file_upload").uploadify({
			'fileTypeExts': "*.doc;*.docx;*.ppt;*.pptx;*.xls;*.xlsx;*.pdf;*.txt",
			'formData':{id:id},
			'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
			//提交的controller和要在火狐下使用必须要加的id
			'uploader':'${pageContext.request.contextPath}/choseDissertation/uploadDessistation;jsessionid=<%=session.getId()%>',		
			buttonText:'选择文件',
			onQueueComplete : function(data) {//当队列中的所有文件全部完成上传时触发	
			//当上传完所有文件的时候关闭对话框并且转到显示界面
			$("#searchFile").window('close');	  
			window.location.href="${pageContext.request.contextPath}/choseDissertation/myDissertationForStudent";          	
			}
		
	  }) --%>
	  
  }
  </script>

<script type="text/javascript">
</script>	
</head>
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">论文互选</a></li>
		<li class="end"><a href="javascript:void(0)">我的论文</a></li>
	  </ul>
	</div>
  </div>
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">我的论文</a>
		  </li>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">我的论文</div>--%>
	<%--</div>--%>
		<form:form name="queryForm" action="${pageContext.request.contextPath}/nwuChose/ChoseDisserationForYearList?currpage=1" method="post" >
		</form:form>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>论题</th>
	    <!-- <th>要求</th> -->
	    <th>完成时间</th>
	    <th>状态</th>
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:if test="${user.choseUser.choseDissertation ne null}">
	  <tr>
	    <td>${user.choseUser.choseDissertation.tittle}</td>
	    <td><fmt:formatDate pattern='yyyy-MM-dd' value="${user.choseUser.choseDissertation.finishTime.time}"/></td>
	    <td>
    		<c:if test="${user.choseUser.documentId eq null}">未提交</c:if>
    		<c:if test="${user.choseUser.documentId ne null}">已提交</c:if>
	    </td>
	    <td>
	       <c:if test="${user.choseUser.documentId eq null}">
	       <a onclick="uploadDessistation()">上传
	       </a>
	       </c:if>
	       <c:if test="${user.choseUser.documentId ne null}">
	       <a href="${pageContext.request.contextPath}/choseDissertation/downloadFile">下载</a>
	       </c:if>
	    </td>
	  </tr>
	  </c:if>
	  </tbody>
	</table>
	
	
	
	<div id="searchFile" class="easyui-window" title="请选择" closed="true" iconCls="icon-add" style="width:400px;height:200px">
				    <!-- <form id="form_file" method="post" enctype="multipart/form-data">
			           <table  border="0" align="center" cellpadding="0" cellspacing="0">
			            <tr>
				            <td>
				          	<div id="queue"></div>
						    <input id="file_upload" name="file_upload" type="file" multiple="true">
						    </td>
			            </tr>   
			            </table>
			         </form> -->
			         <form action="${pageContext.request.contextPath}/choseDissertation/uploadDessistation" method="post" enctype="multipart/form-data">
		                    <table  border="0" align="center" cellpadding="0" cellspacing="0">
			                 <tr>
				            <td>
				          	<div id="queue"></div>
				          	<input type="hidden" name="id" value="${user.choseUser.choseDissertation.id }"/>
						    <input id="file" name="file" type="file"/>
						    <input type="submit" value="提交"/>
						    </td>
			            </tr>   
		</table>
	</form>
 </div>
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<!-- 下拉框的js -->
</body>
</html>
