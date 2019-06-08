<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>


<html>
<head>
<title></title>
<meta name="decorator" content="iframe"/>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no"/>
<style type="text/css" media="screen">
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/icon.css");
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/gray/easyui.css");
			@import url("${pageContext.request.contextPath}/css/style.css");
			@import url("${pageContext.request.contextPath}/static_limsproduct/css/global_static.css");
</style>
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">

<!-- 文件上传的样式和js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/labroom/editDevice.js"></script>

<!-- 播放器所需文件 -->
	<!-- 此处的JQ版本和文件上传的有冲突，故注掉 -->
	<%-- <script src="${pageContext.request.contextPath}/mediaelement/build/jquery.js"></script> --%>
	
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/me-namespace.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/me-utility.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/me-i18n.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/me-plugindetector.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/me-featuredetection.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/me-mediaelements.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/me-shim.js" type="text/javascript"></script>--%>
	<%----%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/mep-library.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/mep-player.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/mep-feature-playpause.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/mep-feature-progress.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/mep-feature-time.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/mep-feature-speed.js" type="text/javascript"></script>	--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/mep-feature-tracks.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/mep-feature-volume.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/mep-feature-stop.js" type="text/javascript"></script>--%>
	<%--<script src="${pageContext.request.contextPath}/mediaelement/src/js/mep-feature-fullscreen.js" type="text/javascript"></script>--%>
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/mediaelement/src/css/mediaelementplayer.css" />--%>
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/mediaelement/src/css/mejs-skins.css" />	--%>


<script type="text/javascript">

function uploadVideo1(){
	var url = "${pageContext.request.contextPath}/labRoomSetting/editLabRoomVideoRest/" + ${labRoomId} + "/"+ ${currpage}+"/"+${type};
			 $('#searchFile1').window('open');
			 $('#video_upload').uploadify({
				'formData':{id:'${device.id}'},    //传值
	            'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
	            'uploader':'${pageContext.request.contextPath}/labRoomSetting/labRoomVideoUpload;jsessionid=<%=session.getId()%>',		//提交的controller和要在火狐下使用必须要加的id
	            buttonText:'选择文件',
	             onQueueComplete : function(data) {//当队列中的所有文件全部完成上传时触发	
	      			    //当上传完所有文件的时候关闭对话框并且转到显示界面
	            	 $('#searchFile1').window('close');	    
	            	 //window.location.href="${pageContext.request.contextPath}/device/deviceVideo?deviceId="+${device.id};        	
	            	 window.location.href=url;        	
		}
	        });
			
		}
function uploadVideo(){
    $('#searchFile').window('open');
}
function saveVideo(){
    var fileSize =  document.getElementById('file_upload_ori').files[0];
    if(fileSize.size>1048576*100){//文件大小限制100MB
        alert("文件过大，上传文件大小限制为100MB，请重新上传");
    }else{
    	document.form_file_ori.action="${pageContext.request.contextPath}/labRoom/uploadVideoForLabRoom?labRoomId=${labRoomId}&currpage=${currpage}&type=${type}"
    	document.form_file_ori.submit();
	}
   // /* var url = "${pageContext.request.contextPath}/device/editDeviceDocumentRest/" + ${labRoomId} + "/"+ ${deviceNumber} + "/" + "${deviceName}" +"/"+ ${username}+"/"+${page}+"/" + +${device.id}+ "/"+"${schoolDevice_allowAppointment}";
	//window.location.href=url;  */
}
function closeMyWindow(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/zjcclims/device/editDevice.js"></script>

<style>
.tool-box2 th{text-align:left;}
</style>
</head>


<body>

<div class="right-content">	
	<div id="TabbedPanels1" class="TabbedPanels">
	 <ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="openSetupLink(${device.id},${currpage },${type})">参数设置</a>
		</li>
		 <li class="TabbedPanelsTab" tabindex="0">
			 <a href="javascript:void(0);" onclick="openStationReserSetting(${device.id},${currpage },${type})">工位预约审核设置</a>
		 </li>
		 <c:if test="${device.CDictionaryByAllowSecurityAccess.CCategory=='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber=='1' && device.labRoomReservation.toString() == '1'}">
			 <li class="TabbedPanelsTab" tabindex="0">
				 <a href="javascript:void(0);" onclick="editLabRoomTrainingRest(${device.id},${currpage },${type})">准入管理</a>
			 </li>
		 </c:if>
		 <c:if test="${device.labRoomReservation.toString() == '1'}">
			 <li class="TabbedPanelsTab" tabindex="0">
				 <a href="javascript:void(0);" onclick="editLabRoomOpenSettingRest(${device.id},${currpage },${type})">开放设置</a>
			 </li>
		 </c:if>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomImageRest(${device.id},${currpage },${type})">相关图片</a>
		</li>
		<li class="TabbedPanelsTab selected" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomVideoRest(${device.id},${currpage },${type})">相关视频</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomDocumentRest(${device.id},${currpage },${type})">相关文档</a>
		</li>
<%--		<c:if test="${(device.CDictionaryByAllowSecurityAccess.CCategory =='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber == '1' && device.CDictionaryByTrainType.CNumber == '3')}">
		<li class="TabbedPanelsTab" tabindex="0">
		&lt;%&ndash;<a href="${pageContext.request.contextPath}/tcoursesite/question/findQuestionList?tCourseSiteId=${device.id}"target="_blank">题库</a>
		&ndash;%&gt;<a href="" onclick="findLabRoomQuestionList(${device.id})">题库</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="" onclick="findLabRoomTestList(${device.id})">考试</a>
		</li>
		</c:if>--%>
	</ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			
			<div class="content-box">
				<div class="title">
				<div id="title">实验室视频</div>
					<a class="btn btn-new"  onclick="closeMyWindow()">返回</a>
				</div>
					<fieldset class="introduce-box">
						     <label>实验室相关视频</label>
							<ul class="img-box">
						     <c:forEach items="${device.commonVideos}" var="video" varStatus="i">
						      <li>
						      <div id="video-container">
							  <video width="640" height="360" id="player1" controls="controls" preload="none" poster="">
							  <source src="${pageContext.request.contextPath}/${video.videoUrl}"  type="video/mp4" /> 	
							  <track kind="subtitles" src="${pageContext.request.contextPath}/mediaelement/media/mediaelement.srt" srclang="en"  ></track>
							  </video>
							  </div>
							  <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_EQUIPMENTADMIN,ROLE_LABMANAGER">
							  <%--<a class="btn btn-common" href="${pageContext.request.contextPath}/device/deleteLabRoomVideo?id=${video.id}">删除</a>
						      --%>
						      <a class="btn btn-common"  href="javascript:deleteLabRoomVideoRest(${video.id})" onclick="return confirm('确定要删除吗？')">删除</a>
						      </sec:authorize>
						      </li>
						     </c:forEach>
								
								</ul>
								<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_EQUIPMENTADMIN,ROLE_LABMANAGER">
								<input type="button" onclick="uploadVideo()" value="上传视频"/>
								</sec:authorize>
					 </fieldset>
  			<input type="hidden" id="labRoomId" value="${labRoomId }">
            <input type="hidden" id="page" value="${currpage }">
				<input type="hidden" id="type" value="${type}">
    		<input type="hidden" id="pageContext" value="${pageContext.request.contextPath }">
			</div>
		</div>
		<!-- 上传视频 -->
		  <!-- 上传图片 -->
		  <div id="searchFile" class="easyui-window" title="上传视频" closed="true" iconCls="icon-add" style="width:400px;height:200px">
			  <p>大小限制：100MB （文件格式：MP4）</p>
			  <form id="form_file_ori" name="form_file_ori" method="post"
					enctype="multipart/form-data">
				  <table  border="0" align="center" cellpadding="0" cellspacing="0">
					  <tr>
						  <td>
							  <div id="queue"></div> <input id="file_upload_ori"name="file_upload_ori" type="file" multiple="true">
							  <input type="button" onclick="saveVideo()" value="上传" />
						  </td>
						  <!-- <div id="queue"></div>
                        <input id="file_upload" name="file_upload" type="file" multiple="true"> -->
					  </tr>
				  </table>
			  </form>
		  </div>
		  <!-- 上传图片结束 -->
		  <!-- 上传视频结束 -->
	  </div>
	</div>
	
</div>

</body>
</html>
