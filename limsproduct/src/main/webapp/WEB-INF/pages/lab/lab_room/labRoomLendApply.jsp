<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
  
  <script type="text/javascript">

	$(function(){
		//alert(state);
		$("#c li").eq(0).css("padding","4px 10px");
		$("#c li").eq(0).css("background-color","#FFF");
		$("#c li").eq(0).css("border-bottom","1px solid #FFF");
		$("#c li").eq(0).css("color","#999");
	});
</script>

<script type="text/javascript">
  function uploadDocument(flag){
	 $('#searchFile').window({top: 300});
	 $('#searchFile').window('open');
	 $('#file_upload').uploadify({
		'formData':{id:'${softwareReserve.id}',flag:flag},    //传值
   		'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
   		'uploader':'${pageContext.request.contextPath}/softwareDocumentUpload;jsessionid=<%=session.getId()%>',   
   		//提交的controller和要在火狐下使用必须要加的id
   		buttonText:'选择附件',
   		onQueueComplete : function(data) {//当队列中的所有文件全部完成上传时触发	
   		//当上传玩所有文件的时候关闭对话框并且转到显示界面
 		$('#searchFile').window('close');
		window.location.href="${pageContext.request.contextPath}/softwareReserve"; 	           	
	}
  });
		
}
  </script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">排课管理</a></li>
		<li class="end"><a href="javascript:void(0)">实训室借用申请</a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
	  <div id="title">实训室借用申请表</div>
	</div>
	
	<form:form id="labLendingForm" action="saveLabRoomLending"  method="POST" modelAttribute="labRoomLending">
		<form:hidden id="lendingstatus" path="lendingStatus" value="1"/>
		<%-- <form:hidden  path="teacher" value="${user.cname}"/> --%>
    	<table class="tb" id="table" >
        <tr>
        	<td>通知声明:</td>
            <td colspan="3"><div><font color="#DF0029" size="3px">请认真填写</font></div></td>       	
        </tr>
 		
 		<tr>
           	<td>借用单位:</td>
	    		<td><form:input path="lendingUnit" required="true" /></td>
       </tr>
 		<tr>
           	<td>申请人:</td>
           	<td>
    			<form:select id="lendingUser" class="chzn-select"  cssStyle="width:170px;" path="userByLendingUser.username">
  					<option Value="${currUser.username}" />${currUser.cname}
  				</form:select>
            </td>
       </tr>
       <tr>
 			<td>借用时间:</td>
            <td>
            <input id="lendingTime" class="Wdate" type="text" name="lendingTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="width:160px;"/></input>
            </td>
 		</tr>
		<tr>
        	<td>借用实训室:</td>
	     	<td>
	    		<form:select id="labRoom" class="chzn-select"  cssStyle="width:170px;" path="labRoom.id" >
   					<form:options items="${labRoomList}" itemLabel="labRoomName" itemValue="id"/>
   				</form:select>
	      	</td>
	    </tr>
	    <tr> 	
           	<td>借用理由:</td>
	    		<td><form:input path="lendingReason" required="true" /></td>
    	</tr>
 		<tr>
        	<td>借用类型:</td>
	     	<td>
	    		<form:select id="lendingType" class="chzn-select"  cssStyle="width:170px;" path="labRoomLendingType.id" >
   					<form:options items="${labRoomLendingTypeList}" itemLabel="name" itemValue="id"/>
   				</form:select>
	      	</td>
	    </tr> 
	    <tr>
        	<td>使用人类型:</td>
	     	<td>
	    		<form:select id="lendingUserType" class="chzn-select"  cssStyle="width:170px;" path="lendingUserType.id" >
   					<form:options items="${lendingUserTypeList}" itemLabel="name" itemValue="id"/>
   				</form:select>
	      	</td>
	    </tr>
	    <tr>
	    	<td>班级：</td>
			<td style="width:160px;">
					<form:select class="chzn-select"  path="schoolClasses.classNumber" id="class">
					<form:option value="">请选择</form:option>
					<form:options items="${schoolClassess}" itemLabel="className" itemValue="classNumber"/>
					</form:select>		    				    				            
			</td> 
	    </tr> 
	    <tr> 	
           	<td>使用人数:</td>
	    	<td><form:input path="lendingUserNumber" required="true" /></td>
    	</tr>
    	<tr> 	
           	<td>借用人电话:</td>
	    	<td><form:input path="lendingUserPhone" required="true" /></td>
    	</tr>
	    <tr>
           	<td>实训室管理员:</td>
           	<td>
    			<form:select id="labRoomAdmin" class="chzn-select"  cssStyle="width:170px;" path="userByLabRoomAdmin.username">
  					<c:forEach items="${user}" var="curr">
  					<option value="${curr.username}" >${curr.cname}</option>
  					</c:forEach>
  				</form:select>
            </td>
       </tr>
       <tr>
 			<td>申请日期:</td>
            <td>
            <input id="applyDate" class="Wdate" type="text" name="applyDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="width:160px;"/></input>
            </td>
 		</tr>		
       	<tr>
      		<td colspan="4" style="border: 1px solid #999;text-align:right;">
          	<input class="btn btn-new" type="submit" value="申请" />
         	</td>
       	</tr>
        </table>
	</form:form>

 <!-- 上传图片 --> 				
	<div id="searchFile" class="easyui-window" title="上传附件" closed="true" iconCls="icon-add" style="width:400px;height:200px;">
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
<script type="text/javascript">
	$(function($) {
		var config = {
      		'.chzn-select'           : {search_contains : true},
      		'.chzn-select-deselect'  : {allow_single_deselect:true},
      		'.chzn-select-no-single' : {disable_search_threshold:10},
      		'.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
      		'.chzn-select-width'     : {width:"95%"}
    	}
   		for (var selector in config) {
      		$(selector).chosen(config[selector]);
    	}	
		
		getLabSoftwareList($("#labId").val());
	});
	//获取后台返回的软件集合并显示在页面
	function getLabSoftwareList(labId){
		$.ajax({
			url: 'getLabSoftwareList?labId='+labId,
			dataType:'json',
			success:function(json){
				var str="";
				if(json.length>0){  
					$.each(json,function(index,array){ 
						str+="<input type='checkbox' style='margin-left:10px;' value="+array['id']+" onclick='selSoftware(this.value)'/>"+array['name']+"&nbsp&nbsp&nbsp";
						
					});
					$("#labSoftwareList").html(str)
				}else{
					$("#labSoftwareList").text("此实训室无软件")
				}
			},
			error:function(){
				alert("数据加载失败");
			}
		});
	}
	//选中select
	function selSoftware(softwareId){
		$("#code").val(softwareId);
		$(":checkbox").each(function(){
			$(this).removeAttr("checked");
		})
		$("input[value='"+softwareId+"']").prop("checked",true)
		$(".chzn-select").trigger("liszt:updated");
	}
	
	function cleanSoftList(){
		$(":checkbox").each(function(){
			$(this).removeAttr("checked");
		})
	}
</script>	
    
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
