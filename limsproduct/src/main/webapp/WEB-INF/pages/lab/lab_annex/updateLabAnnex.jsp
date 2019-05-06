 <%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
 <jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
 
 <html >  
<head>
<meta name="decorator" content="iframe"/>
<!-- 下拉框的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

<!-- 文件上传的样式和js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  

<script type="text/javascript">
function uploadDocument(){
			 $('#searchFile').window({top: 1000});
			 $('#searchFile').window('open');
			 $('#file_upload').uploadify({
				'formData':{id:'${labAnnex.id}'},    //传值
	            'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
	            'uploader':'${pageContext.request.contextPath}/labAnnex/appointment/imageUpload;jsessionid=<%=session.getId()%>',		//提交的controller和要在火狐下使用必须要加的id
	            buttonText:'选择文件',
	             onQueueComplete : function(data) {//当队列中的所有文件全部完成上传时触发	
	      			    //当上传玩所有文件的时候关闭对话框并且转到显示界面
	            	 $('#searchFile').window('close');	
	            	 window.location.href="${pageContext.request.contextPath}/labAnnex/appointment/updateLabAnnex?id="+${labAnnex.id};
	            	     	
		}
	        });
			
		}
	function uploadVideo(){
			 $('#searchFile1').window({top: 1000});
			 $('#searchFile1').window('open');
			 $('#video_upload').uploadify({
				'formData':{id:'${labAnnex.id}'},    //传值
	            'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
	            'uploader':'${pageContext.request.contextPath}/labAnnex/appointment/videoUpload;jsessionid=<%=session.getId()%>',		//提交的controller和要在火狐下使用必须要加的id
	            buttonText:'选择文件',
	             onQueueComplete : function(data) {//当队列中的所有文件全部完成上传时触发	
	      			    //当上传完所有文件的时候关闭对话框并且转到显示界面
	            	 $('#searchFile1').window('close');	 
	            	 window.location.href="${pageContext.request.contextPath}/labAnnex/appointment/updateLabAnnex?id="+${labAnnex.id};
		}
	        });
			
		}
function showImage(image){
	//alert(image.src);
	//$("#img").src=image.src;
	document.getElementById("img").src=image.src;
	$('#showImage').window({
		        top: 1000   
		     });
	$('#showImage').window('open');
}		
</script>
</head>
<body>
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">实验室及预约管理</a></li>
			<li><a href="javascript:void(0)">实验室管理</a></li>
			<li class="end"><a href="javascript:void(0)">修改</a></li>
			
		</ul>
	</div>
</div>
<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<div class="title">
				<div id="title">修改实验室</div>
				
				</div> 
			<form:form action="${pageContext.request.contextPath}/labAnnex/appointment/saveUpdateLabAnnex" method="POST" modelAttribute="labAnnex">
				<div class="new-classroom">
					<fieldset>
						    <label>实验室名称：</label>
						       <form:hidden path="id"/>
							   <form:input path="labName" class=""  required="true"/> 
					</fieldset>		

			  		<fieldset>
						     <label>实验室简称：</label>
							 <form:input path="labShortName" />
					 </fieldset>
					 <fieldset>
						     <label>实验室英文名称：</label>
							 <form:input path="labEnName" />
					 </fieldset>
					  <fieldset>
						     <label>所属学科：</label>
							 <form:input path="labSubject" />
					 </fieldset>
					  <fieldset>
						     <label> 实验室类别：</label>
							<form:select path="CLabAnnexType.id" class="chzn-select">
							<form:options items="${CLabAnnexTypes}" itemLabel="name" itemValue="id"/>
							</form:select>
					 </fieldset>
					  <fieldset>
						     <label> 管理机构:</label>
							 <form:input path="belongDepartment" />
					 </fieldset>
					   <fieldset>
						     <label>联系方式:</label>
							 <form:input path="contact" />
					 </fieldset>
					 <fieldset>
						     <label>所属中心:</label>
							 <form:select path="labCenter.id" class="chzn-select">
							 <form:options items="${labCenters}" itemLabel="centerName" itemValue="id"/>
							 </form:select>
					 </fieldset>
					  <fieldset class="introduce-box">
						     <label> 实验室简介:</label>
							 <form:textarea path="labDescription" />
					 </fieldset>
					 <fieldset class="introduce-box">
						     <label>规章制度:</label>
							 <form:textarea path="labAttention" />
					 </fieldset>
					 <fieldset class="introduce-box">
						     <label> 获奖信息: </label>
							 <form:textarea path="awardInformation" />
					 </fieldset>
				
					<fieldset class="introduce-box">
						     <label>图片: </label>
						     <ul class="img-box">
						     <c:forEach items="${Images}" var="image" varStatus="i">
						     
						      <li><img alt="" src="${pageContext.request.contextPath}/${image.documentUrl}" width="200px" height="150px" onclick="showImage(this);"> 
						      		<a class="btn btn-common" href="${pageContext.request.contextPath}/labAnnex/labAnnex/appointment/deleteLabAnnexDocument?id=${image.id}">删除</a>
						      </li>
						     
						     </c:forEach>
						     </ul>
							 <input type="button" onclick="uploadDocument()" value="上传图片"/>
					 </fieldset>
					 <fieldset class="introduce-box">
						     <label>视频: </label>
						     <ul class="img-box">
						     <c:forEach items="${videos}" var="video" varStatus="i">
						     
						      <li>
						      <embed src="${pageContext.request.contextPath}/${video.videoUrl}" autostart="true" loop="true" width="450" height="400"></embed>
						      		<a class="btn btn-common" href="${pageContext.request.contextPath}/labAnnex/appointment/deleteLabAnnexVideo?id=${video.id}">删除</a>
						      </li>
						     
						     </c:forEach>
						     </ul>
							 <input type="button" onclick="uploadVideo()" value="上传视频"/>
					 </fieldset>
				
				
				</div>
				<div class="moudle_footer">
			        <div class="submit_link">
			          <input class="btn" id="save" type="submit" value="确定">
					<input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
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
			

		</form:form>
<div id="showImage" class="easyui-window" title="查看图片" closed="true" iconCls="icon-add" style="width:620px;height:340px">
		<center>
	   <img id="img" alt="" src="" width="600" height="300" border="0">
	   </center>
     </div>				
		<div id="searchFile" class="easyui-window" title="上传图片" closed="true" iconCls="icon-add" style="width:400px;height:200px">
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
  <div id="searchFile1" class="easyui-window" title="上传视频" closed="true" iconCls="icon-add" style="width:400px;height:200px">
	    <form id="form_ video" method="post" enctype="multipart/form-data">
           <table  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
            <td>
          	<div id="queue">（文件格式：MP4）</div>
		    <input id="video_upload" name="file_upload" type="file" multiple="true">
            </tr>   
            </table>
         </form>
     </div>   
		
		</div>
		</div>
		</div>
	</div>
</div>

</body>


</html>