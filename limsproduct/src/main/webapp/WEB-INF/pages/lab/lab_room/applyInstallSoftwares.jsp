
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
<!-- 下拉框的样式 -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/chosen.css" />
	
	<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/js/jquery-easyui/themes/default/easyui.css" />
<!-- 下拉的样式结束 -->
  <script type="text/javascript">
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/labRoom/ceshi?currPage=1";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  //导出
  function subform(gourl) {
		var gourl;
		queryForm.action = gourl;
		queryForm.submit();
	}
  //导入
  function importSoft(){
  	$('#importSoft').window({width:500,left:"30px",top:"30px"});
  	$("#importSoft").window('open');
  } 
  </script>
  
  <script type="text/javascript">
	//选中当前的标签页
	$(function(){
		/* var type=${type};
		$("#c li").eq(type-1).css("padding","4px 10px");
		$("#c li").eq(type-1).css("background-color","#FFF");
		$("#c li").eq(type-1).css("border-bottom","1px solid #FFF");
		$("#c li").eq(type-1).css("color","#999"); */
		
		$("#campus").val(${lab.campus})
		$("#toDepartment").val(${lab.toDepartment})
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
		/*window.location.href="${pageContext.request.contextPath}/softwareReserve";*/
	}
  	});
  }
</script>
<script type="text/javascript">
	function cleanSoftList(){
		$(":checkbox").each(function(){
			$(this).removeAttr("checked");
		})
	}
</script>
<style type="text/css">
	th {
		text-align: center;
	}
</style>
</head>

<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange" /></a></li>
		<li class="end"><a href="javascript:void(0)"><spring:message code="left.softwareInstall.application" /></a></li>
	  </ul>
	</div>
  </div>
  <div id="TabbedPanels1" class="TabbedPanels">
			<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
				<li class="TabbedPanelsTab selected" id="s1">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/labRoom/ceshi?currPage=1">软件安装申请</a>
				</li>
				<li class="TabbedPanelsTab" id="s2">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?page=1&tage=0&isaudit=2">我的申请</a>
				</li>
				<li class="TabbedPanelsTab" id="s3">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?page=1&tage=0&isaudit=1">我的审核</a>
				</li>
			</ul>
		</div>
  <div class="content-box">
  	<form:form id="softForm" action="${pageContext.request.contextPath}/saveSoftwareReserve"  method="POST" modelAttribute="softwareReserve">
		<form:hidden id="approve_state" path="approveState" value="1"/>
		<form:hidden path="teacher" value="${user.cname}"/>
    	<table class="tb" id="table" >
        <tr>
        	<td>通知声明:<font color="#DF0029" size="3px">请认真填写</font></td>
            <td colspan="3"></td>       	
        </tr>
		<tr>
        	<td>安装<spring:message code="all.trainingRoom.labroom" />:</td>
	     	<td>
	    		<form:select path="labRoom.id" id="labId" class="chzn-select" style="width:170px;" onchange="getLabSoftwareList(this.value)">
   					<form:options items="${labList}" itemLabel="labRoomName" itemValue="id"/>
   				</form:select>
	      	</td>
				<%--<td>软件名称:</td>
              <td >
                   <form:select class="chzn-select" style="width:170px;" path="code" data-placeholder="请选择添加软件..." onchange="cleanSoftList()">
                       <c:forEach items="${softwareList}" var="soft">
                           <option value="${soft.id}">${soft.name }&nbsp;&nbsp;(${soft.code })</option>
                         </c:forEach>
                   </form:select>
              </td>--%>
       		<td>软件名称:<font style="color: red">*</font></td>
            <td ><form:input path="name" /></td>
	    </tr>
 	
       	<tr>
            <td>系统要求:</td>
	    	<td><form:input type="text" path="requirement" /></td>
           	<td>光盘（有/无）</td>
           	<td>
           		<form:radiobutton path="cd" value="true" required="true"/>有
           		<form:radiobutton path="cd" value="false" required="true" checked="true"/>无
           	</td>			
        </tr>	
		<tr>
			<td>加密狗（有/无）</td>
           	<td>
		        <form:radiobutton path="dongle" onclick="showPoint(1)" value="true" required="true" checked="true"/>有
           		<form:radiobutton path="dongle" onclick="showPoint(0)" value="false" required="true"/>无
           	</td>
       		<td id="point">点数：<font style="color: red">*</font></td>
       		<td id="point">
       			<input type="text" name="point" path="point" onkeyup="value=value.replace(/[^\d]/g,'')"/>
       		</td>
		</tr>
		
 		<tr>
 			<td>课程:</td>
            <td>
            	<form:select  class="chzn-select" cssStyle="width:170px;" data-placeholder="请选择课程" path="course" >
   					<c:forEach items="${courses }" var="course">
   						<form:option value="${course.courseName}" >${course.courseName}[${course.courseCode}]</form:option>
   					</c:forEach>
   				</form:select>
            </td>
            <td>安装时间:<font style="color: red">*</font></td>
            <td><input class="Wdate" type="text" name="require_time" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:160px;"/></td>
 		</tr>
 		<tr>
 			<td>软件架构:</td>
            <td>
            	<form:input path="frame" required="true" />
            </td>
            <td>申请理由:</td>
            <td>
            	<form:input path="applyReason" required="true" />
            </td>
 		</tr>
 	
 		<tr>
 			<td>供应商:</td>
            <td>
            	<form:input path="supply" required="true" />
            </td>
            <td>供应商联系方式:</td>
            <td>
            	<form:input path="supplyPhone" required="true" maxlength="15" />
            </td>
 		</tr>
 	
 		<%--<tr>
 			<td>安装说明:</td>
            <td>
           		<div id="TabbedPanels1" class="TabbedPanels">
					<div class="TabbedPanelsContentGroup">
						<div class="TabbedPanelsContent">
						
							<input type="button" onclick="uploadDocument(1);" value="上传附件">
							
						</div>
					</div>
				</div>
            </td>
            <td>使用说明:</td>
            <td>
           		<div id="TabbedPanels1" class="TabbedPanels">
					<div class="TabbedPanelsContentGroup">
						<div class="TabbedPanelsContent">
						
							<input type="button" onclick="uploadDocument(2);" value="上传附件">
							
						</div>
					</div>
				</div>
            </td>
 		</tr>--%>
 		
       	<tr>
      		<td colspan="4" style="text-align:right;">
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"></script>
	<!-- 下拉框的js -->
<script type="text/javascript">
	$(function($) {
		var config = {
      		'.chzn-select'           : {search_contains : true,width:"auto"},
      		'.chzn-select-deselect'  : {allow_single_deselect:true},
      		'.chzn-select-no-single' : {disable_search_threshold:10},
      		'.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
      		'.chzn-select-width'     : {}
    	}
   		for (var selector in config) {
      		$(selector).chosen(config[selector]);
    	}	
		
		$(".chzn-container").each(function(){
//			$(this).css("width","200px");
		});
	});
	//加密狗联动点数
	  function showPoint(flag){
		  if(flag==1){
			  $("tr #point").css('display','');
		  }else{
			  $("tr #point").css('display','none');
		  }
	  }
</script>
	
  </div>
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
	  <div id="title">教学软件列表</div>
	  <sec:authorize ifAnyGranted="ROLE_SUPERADMIN" >
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/newSoftware">新建</a>
	  </sec:authorize>
	</div>
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/ceshi?currPage=1" method="post" modelAttribute="software">
			 <ul>
  				<li>软件名称:
					<form:input id="software_name" path="name"/></li>
  				<li>所属实验室:<form:input id="room_name" path="labRoom"/></li>
  				<li>
					<input type="submit" value="查询"/>
			      <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
			      <sec:authorize ifAnyGranted="ROLE_SUPERADMIN" >
  				<input type="button" value="导出" onclick="subform('${pageContext.request.contextPath}/exportSoftList?currPage=${currPage}');">
					<a onclick="importSoft()"><input type="button" value="导入"></a></li>
				</sec:authorize>
  				</ul>
			 
		</form:form>
	</div>
	<table class="tb" style="text-align: center;"> 
    <thead>
    	<tr>
    		<th>编号</th>
        	<th>软件名称</th>
        	<th>软件版本</th>
        	<th>所属实训室</th>
        	<th>有无加密狗</th>
        	<th>点数</th>
        	<th>供应商</th>
        	<th>供应商联系方式</th>
        	<th>操作</th>
    	</tr>
	</thead>
	<tbody>
   	<c:forEach items="${listSoftware}" var="current" varStatus="c">	
    	<tr>
    		<td>${(currPage-1)*pageSize+c.count }</td>
           	<td>${current.name}</td>
           	<td>${current.edition}</td>
           	<td>
<%--   				<c:forEach items="${current.labRooms}" var="lab" varStatus="labStat">--%>
<%--   				<span>${lab.labRoomName }</span>--%>
<%--   				</c:forEach>	--%>
				${labRoomNames[current.labRoom]}
           	</td>
         	<td><c:if test="${current.dongle == 1}">有</c:if>
         		<c:if test="${current.dongle == 0}">无</c:if></td>
         	<td>${current.points}</td>
         	<td>${current.supplier}</td>
          	<td>${current.supplierTel}</td>
           	<td>
               	<a href="${pageContext.request.contextPath}/softwareInfo?idkey=${current.id}">查看</a>
               	<sec:authorize ifAnyGranted="ROLE_SUPERADMIN" >
               	<a href="${pageContext.request.contextPath}/editSoftware?softwareId=${current.id}">编辑</a>
               	<a href="${pageContext.request.contextPath}/deleteSoftware?softwareId=${current.id}" onclick="return confirm('确定删除？');">删除</a>
               	</sec:authorize>
         	</td>
    	</tr>
  	</c:forEach>
	</tbody>
	</table>
	<!-- 分页样式 -->
	<%--<div class="pagination">--%>
	<div class="page">
	${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/ceshi?currPage=1')" target="_self">首页</a>					    
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/ceshi?currPage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/labRoom/ceshi?currPage=${pageModel.currPage}&orderBy=${orderBy }">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currPage}">
    <option value="${pageContext.request.contextPath}/labRoom/ceshi?currPage=${j.index}&orderBy=${orderBy }">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/ceshi?currPage=${pageModel.nextPage}')" target="_self">下一页</a>
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/ceshi?currPage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>  
	<!-- 分页样式 -->
    
  </div>
  </div>
  </div>
  </div>
  </div>
  <div id= "importSoft"  class ="easyui-window panel-body panel-body-noborder window-body" title= "导入模版" modal="true" dosize="true" maximizable= "true" collapsible ="true" minimizable= "false" closed="true" iconcls="icon-add" style=" width: 600px; height :400px;">
        <form:form name="importForm" action="${pageContext.request.contextPath}/importSoft" enctype ="multipart/form-data">
	         <br>
	         <input type="file" id="file" name ="file" required="required" />
	         <input type="submit"  value ="开始上传" />
	         <br><br><hr><br>
		<a href="${pageContext.request.contextPath}/pages/importSample/softExample.xls">点击此处</a>，下载范例<br><br><hr><br>
		示例图片：<br>
		<img src="${pageContext.request.contextPath}/images/importSample/softExample.png" heigth ="100%" width="100%" />
        </form:form>
</div>
</body>

</html>


