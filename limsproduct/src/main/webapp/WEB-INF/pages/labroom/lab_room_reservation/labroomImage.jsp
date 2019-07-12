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

<script type="text/javascript">

 
function showImage(image){
	//alert(image.src);
	//$("#img").src=image.src;
	document.getElementById("img").src=image.src;
	$('#showImage').window({
		        top: 500   
		     });
	$('#showImage').window('open');
}


function uploadDocument1(currpage) {//此上传方法未用到
    var url = "${pageContext.request.contextPath}/labRoomSetting/editLabRoomImageRest/" + ${labRoomId} +"/" + currpage + "/" +${type};
    $('#searchFile').window('open');
    $('#file_upload').uploadify({
        'fileSizeLimit'  : '3MB',//设置文件大小限制
        'formData': {id: '${device.id}',type: 4},    //传值
        'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',
        'uploader': '${pageContext.request.contextPath}/visualization/uploadImageForLabRoom;jsessionid=<%=session.getId()%>',		//提交的controller和要在火狐下使用必须要加的id
        buttonText: '选择文件',
        onQueueComplete: function (data) {//当队列中的所有文件全部完成上传时触发

             	console.log(data);
				if (data.uploadSize > 3*1024*1024) {
				    alert("上传图片大于3M，请重新上传！")
                }else {
                //当上传玩所有文件的时候关闭对话框并且转到显示界面
                $('#searchFile').window('close');
                //window.location.href="${pageContext.request.contextPath}/device/deviceImage?deviceId="+${device.id};
                window.location.href = url;
				}
        }
    });

}
function uploadDocument(){
    $('#searchFile').window('open');
}
function saveDocument(){
    var fileSize =  document.getElementById('file_upload_ori').files[0];
    if(fileSize.size>1048576*3){//文件大小限制3MB
        alert("文件过大，上传文件大小限制为3MB，请重新上传");
	}else{
        document.form_file_ori.action="${pageContext.request.contextPath}/labRoom/uploadImageForLabRoom?labRoomId=${labRoomId}&currpage=${currpage}&type=${type}"
        document.form_file_ori.submit();
	}
}
function closeMyWindow(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
</script>


<style>
.tool-box2 th{text-align:left;}
</style>
</head>


<body>
<div class="tit-box">
	${device.labRoomName}
</div>
<div class="right-content">	
	<div id="TabbedPanels1" class="TabbedPanels">
	 <ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="openSetupLink(${device.id},${currpage },${type})">实验室预约设置</a>
		</li>
		 <li class="TabbedPanelsTab" tabindex="0">
			 <a href="javascript:void(0);" onclick="openStationReserSetting(${device.id},${currpage },${type})">工位预约设置</a>
		 </li>
		 <c:if test="${device.CDictionaryByAllowSecurityAccess.CCategory=='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber=='1' && device.labRoomReservation.toString() == '1'}">
			 <li class="TabbedPanelsTab" tabindex="0">
				 <a href="javascript:void(0);" onclick="editLabRoomTrainingRest(${device.id},${currpage },${type})">准入管理</a>
			 </li>
		 </c:if>
		 <c:if test="${isOpen == 1}">
			 <li class="TabbedPanelsTab" tabindex="0">
				 <a href="javascript:void(0);" onclick="editLabRoomOpenSettingRest(${device.id},${currpage },${type})">开放设置</a>
			 </li>
		 </c:if>
		<li class="TabbedPanelsTab selected" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomImageRest(${device.id},${currpage },${type})">相关图片</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomVideoRest(${device.id},${currpage },${type})">相关视频</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomDocumentRest(${device.id},${currpage },${type})">相关文档</a>
		</li>
		<%--<c:if test="${(device.CDictionaryByAllowSecurityAccess.CCategory =='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber == '1' && device.CDictionaryByTrainType.CNumber == '3')}">
		<li class="TabbedPanelsTab" tabindex="0">
		&lt;%&ndash;<a href="${pageContext.request.contextPath}/tcoursesite/question/findQuestionList?tCourseSiteId=${device.id}"target="_blank">题库</a>
		&ndash;%&gt;<a href="#" onclick="findLabRoomQuestionList(${device.id})">题库</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="#" onclick="findLabRoomTestList(${device.id})">考试</a>
		</li>
		</c:if>--%>
	</ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			
			<div class="content-box">
				<div class="title">
				<div id="title">实验室图片</div>
				<a class="btn btn-new"  onclick="closeMyWindow()">返回</a>
				</div>
					<fieldset class="introduce-box">
						     <label>实验室图片</label>
								<ul class="img-box">
								<c:forEach items="${device.commonDocuments}" var="d">
								<c:if test="${d.type==4}">
						    	<li><img alt="" src="${pageContext.request.contextPath}/${d.documentUrl}" width="200px" height="150px" onclick="showImage(this);">
						    	<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_EQUIPMENTADMIN,ROLE_LABMANAGER">
						    	<%--<a class="btn btn-common"  href="${pageContext.request.contextPath}/device/deleteDeviceDocument?id=${d.id}">删除</a>
						    	--%>
						    	
						    	<a class="btn btn-common"  href="javascript:deleteDeviceDocumentRest(${d.id});" onclick="return confirm('确定要删除吗？')">删除</a>
						    	</sec:authorize>
						    	 </li>
						    	</c:if>
						    	
						     	</c:forEach>
						     	</ul>
						     	<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_EQUIPMENTADMIN,ROLE_LABMANAGER">
						     	<input class="btn btn-common" type="button" onclick="uploadDocument()" value="上传图片"/>
						     	</sec:authorize>
					 </fieldset>
  			<input type="hidden" id="labRoomId" value="${labRoomId }">
			<input type="hidden" id="page" value="${currpage}">
				<input type="hidden" id="type" value="${type}">
    		<input type="hidden" id="pageContext" value="${pageContext.request.contextPath }">     		
			</div>
		</div>
		  <!-- 上传图片 -->
		  <div id="searchFile" class="easyui-window" title="上传文档" closed="true" iconCls="icon-add" style="width:400px;height:200px">
			  <p>大小限制：3MB</p>
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
	  </div>
	</div>
</div>

</body>
</html>
