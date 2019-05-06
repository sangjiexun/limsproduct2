<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 文件上传的样式和js开始 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  
<!-- 文件上传的样式和js结束 -->
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  
  <script type="text/javascript">
  //上传学年论文
  function uploadDessistationForYear(id){
	  $("#searchFile").show();
	  $("#searchFile").window('open');
	  $("#file_upload").uploadify({
			'fileTypeExts': "*.doc;*.docx;*.ppt;*.pptx;*.xls;*.xlsx;*.pdf;*.txt",
			'formData':{'id':id},    //传值
			'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
			<%--'uploader':'${pageContext.request.contextPath}/../choseDissertation/uploadDessistationForYear',		//提交的controller和要在火狐下使用必须要加的id--%>
			'uploader':'${pageContext.request.contextPath}/nwuChose/uploadDessistationForYear;jsessionid=<%=session.getId()%>',		//提交的controller和要在火狐下使用必须要加的id
			'buttonText':'选择文件',
			onQueueComplete : function(data) {//当队列中的所有文件全部完成上传时触发	
			//当上传完所有文件的时候关闭对话框并且转到显示界面
			$("#searchFile").window('close');	  
			window.location.href="${pageContext.request.contextPath}/nwuChose/myChoseDisserationForYear";          	
			}
		});
  }
  </script>
<script type="text/javascript">
</script>	

</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">导师互选</a></li>
		<li class="end"><a href="javascript:void(0)">我的学年论文</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">我的学年论文</div>--%>
	<%--</div>--%>
		<form:form name="queryForm" action="${pageContext.request.contextPath}/nwuChose/ChoseDisserationForYearList?currpage=1" method="post" >
		</form:form>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>论题</th>
	    <th>要求</th>
	    <th>完成时间</th>
	    <th>状态</th>
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${dessitationForYears}" var="curr" varStatus="i">
	  <tr>
	    <td>${curr.theme}</td>
	    <td>${curr.requirements}</td>
	    <td><fmt:formatDate pattern='yyyy-MM-dd' value="${curr.finishTime.time}"/></td>
	    <td>
    		<c:if test="${curr.documentId eq null}">未提交</c:if>
    		<c:if test="${curr.documentId ne null}">已提交</c:if>
	    </td>
	    <td>
	       <c:if test="${curr.documentId eq null}">
	       <a onclick="uploadDessistationForYear(${curr.id})">上传</a>
	       </c:if>
	       <c:if test="${curr.documentId ne null}">
	       <a href="${pageContext.request.contextPath}/nwuChose/downloadDessistationForYear?id=${curr.id}">下载</a>
	       </c:if>
	    </td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<div id="searchFile" class="easyui-window" title="请选择" closed="true" iconCls="icon-add" style="width:400px;height:200px">
	    <form id="form_file" method="post" enctype="multipart/form-data">
           <table  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
	            <td>
	          	<div id="queue"></div>
			    <input id="file_upload" name="file_upload" type="file" multiple="true">
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
