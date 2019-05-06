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
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" />
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/lib.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script> 
  <script type="text/javascript">
  //提交表单
  function submitBookUpload(){
   /*  if($("#bookName").val()=="")
    {
      alert("请填写书名！");
      return false;
    } */
    
    
    document.center_form.action="${pageContext.request.contextPath}/timetable/saveSchoolCourseInfo";
    document.center_form.submit();
  }
  </script>
<style>
	#labRoomId_chzn{
		width:200px !important;
	}
	.chzn-container{
		width:200px !important;
	}
</style>
</head>
<body>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title"><c:if test="${!isEdit}">编辑</c:if><c:if test="${isEdit}">新建</c:if>课程信息</div>
       <button class="r mt7 btn" type="button" onclick="back()">
		   <%--<i class="fa fa-save mr5"></i>--%>
		   返回</button>
      <button class="r mt7 btn" type="button" onclick="savecourseInfo()">
		  <%--<i class="fa fa-save mr5"></i>--%>
		  保存</button>
	</div>
	<form:form action="${pageContext.request.contextPath}/timetable/saveSchoolCourseInfo" id="courseInfoform"
	name="courseInfoform" method="POST" modelAttribute="schoolCourseInfo">
	<div class="new-classroom">
	  <div class="new_exp_block">
						<span class="new_tit">课程基本信息</span>
						<table class="experimental_list_tab" cellspacing="0">
							<tr>
								<th>课程编号</th>
								<th>课程名称</th>
								<th>英文名称</th>
								<th>学分</th>
								<th>总学时</th>
								<th>实验学时</th>
								<th>课程类型</th>
							</tr>
							<tr>
								<td class="tc"><form:input path="courseNumber" class="easyui-validatebox" required="true"/></td>
								<td class="tc"><form:input path="courseName" class="easyui-validatebox" required="true"/></td>
								<td class="tc"><form:input path="courseEnName" class="easyui-validatebox" required="true"/></td>
								<td class="tc"><form:input path="credits" class="easyui-validatebox" required="true"/></td>
								<td class="tc"><form:input path="totalHours" class="easyui-validatebox" required="true"/></td>
								<td class="tc"><form:input path="theoreticalHours" class="easyui-validatebox" required="true"/></td>
								<td class="tc"> <form:select id="courseType" path="courseType" class="chzn-select">
											      <form:option value="">请选择</form:option>
											        <form:option value="1">理论课</form:option>
											        <form:option value="2">实验课</form:option>
											    </form:select></td>
							</tr>
						</table>
						<table class="experimental_list_tab" cellspacing="0">
							<tr>
								
								<th>课程性质</th>
								<th>适用专业</th>
								<th>开课学期</th>
								<th>开课院系</th>
								<th>课程负责人</th>
							</tr>
								<td class="tc"> <form:select id="courseNature" path="courseNature" class="chzn-select">
											      <form:option value="">请选择</form:option>
											        <form:option value="1">通识通修课程</form:option>
											        <form:option value="2">学科专业课程</form:option>
											        <form:option value="3">开放选修课程</form:option>
											        <form:option value="4">其他课程</form:option>
											    </form:select></td>
							<td class="tc">
									 <%--<select id="schoolMajor" name="schoolMajor.majorNumber" class="chzn-select" multiple="multiple" >--%>
									 <select id="schoolMajor1" name="schoolMajor1" class="chzn-select" multiple="multiple" >
										<c:forEach items="${schoolMajorSet}" var="d">
										   <c:forEach items="${schoolMajorSet2}" var="curr">
										   <c:if test="${curr.majorNumber eq d.majorNumber }">
										  <option value="${d.majorNumber}" selected="selected">${d.majorName}</option>
										   </c:if> 
										   </c:forEach>
											<option value="${d.majorNumber}">${d.majorName}</option>
										</c:forEach>
								   </select>
								</td>
										<td class="tc">
									 <select id="schoolTerm" name="schoolTerm" class="chzn-select" multiple="multiple" >
										 <c:forEach items="${schoolTerms}" var="d">
											 <c:forEach items="${courseSchoolTerms}" var="curr">
												 <c:if test="${curr.id eq d.id}">
													 <option value="${d.id}" selected="selected">${d.termName}</option>
												 </c:if>
											 </c:forEach>
											 <option value="${d.id}">${d.termName}</option>
										 </c:forEach>
								   </select>
								</td>
							<td class="tc"><input name="labCenter" class="easyui-validatebox" required="true" value="${academyName}"/></td>
							<td class="tc">
									 <select id="teacher" name="teacher" class="chzn-select" multiple="multiple" >
										<c:forEach items="${teacheres}" var="d">
										  <c:forEach items="${users}" var="curr">
										   <c:if test="${curr.username eq d.username}">
										  <option value="${d.username}" selected="selected">${d.cname}</option>
										   </c:if> 
										   </c:forEach>
											<option value="${d.username}">${d.cname}</option>
										</c:forEach>
								   </select>
								</td>
							<tr>
							</tr>
						</table>
						<div class="w100p ovh mt30">
							<div class="edit_block l">
								<div>课程的教学目标与任务</div>
								<form:textarea placeholder="请输入内容" path="goal" required="true"></form:textarea>
							</div>
							<div class="edit_block r">
								<div>考核方式</div>
								<form:textarea placeholder="请输入内容"  path="inspectionWay"></form:textarea>
							</div>
							<div class="edit_block l">
								<div>课程基本内容</div>
								<form:textarea placeholder="请输入内容"  path="content"></form:textarea>
							</div>
							<div class="edit_block r">
								<div>推荐教材与参考资料</div>
								<form:textarea placeholder="请输入内容"  path="resources"></form:textarea>
							</div>
						</div>
						
						<div class="edit_bottom">
							<span><button class="btn-big" onclick="uploaddocment()" type="button">上传附件</button></span>
							<fieldset class="introduce-box">
						<input type="hidden" value="" id="xsd">
						 <table>
							<tr >
								 <td>文档名称</td>
								 <input type="hidden" name="docment" id="docment" />
							 </tr>
							 <c:forEach items="${schoolCourseInfo.commonDocuments}" var="curr">
							 	<tr id="s_${curr.id}">
							 		<td>${curr.documentName }</td>
							 		<td><input type="button" onclick="delectuploaddocment(${curr.id})" value="删除"></td>
							 	</tr>
							 </c:forEach>
						</table> 
						 <table>
							  <tbody id="doc"></tbody>
						 </table>	
					</fieldset>
							<button class="r mtb5 btn-big">
								<%--<i class="fa fa-save mr5"></i>--%>
								保存</button>
						</div>
					</div>
	  
	</div>
	<!-- <div class="moudle_footer">
        <div class="submit_link">
          <input class="btn" id="save" type="button" onclick="submitBookUpload();" value="确定">
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</div> -->
	</form:form>
	<div class="new_exp_block">
						<span class="new_tit">课程内容及基本要求</span>
						<table class="experimental_list_tab" cellspacing="0">
							<tr>
								<th>实验名称</th>
								<th>实验学时</th>
								<th>所属实验室</th>
								<th>基本要求</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${items }" var="curr">
							<c:if test="${curr.CDictionaryByLpStatusCheck.id eq 545}">
								<tr id="showItem${curr.id }">
									<td class="tc">${curr.lpName}</td>
									<td class="tc">${curr.lpDepartmentHours}</td>
									<td class="tc">
										${curr.labRoom.labRoomName}
									</td>
									<td class="tc">
										${curr.lpPurposes}
									</td>
									<td class="tc">
										<a class="fa fa-edit mlr2p blue" onclick="showEditItem(${curr.id})"></a>
										<a class="fa fa-times mlr2p blue" href="${pageContext.request.contextPath}/timetable/deleteOperationItem?operationItemId=${curr.id }"onclick="return confirm('您确认删除吗？')"></a>
										<%-- <a class="fa fa-arrow-up mlr2p blue" href="${pageContext.request.contextPath}/newoperation/upOrder?id=${curr.id }"></a>
										<a class="fa fa-arrow-down mlr2p blue" href="${pageContext.request.contextPath}/newoperation/downOrder?id=${curr.id }"></a> --%>
									</td>
								</tr>
								</c:if>
								<form action="${pageContext.request.contextPath}/timetable/saveEditOperationItem?itemId=${curr.id }"
								 method="post" modelAttribute="operationItem" name="editForm${curr.id }">
								<tr id="editItem${curr.id }" style="display:none">
									<%-- <td id="addCode${curr.id }">${curr.lpCodeCustom}</td> --%>
									<td><input id="lpName${curr.id }" class="easyui-validatebox" required="true" value="${curr.lpName}" name="lpName${curr.id }"/></td>
									<td class="tc"><input name="lpDepartmentHours${curr.id }" id="lpDepartmentHours${curr.id }" class="easyui-validatebox" required="true"
							        onkeyup="value=value.replace(/[^\d]/g,'') "   
									placeholder="请输入大于零的整数"
							         value="${curr.lpDepartmentHours}"/></td>
									<td class="tc">
										<select name="labRoomId${curr.id }" id="labRoomId${curr.id }" class="chzn-select">
								          <option value="">- - - -请选择- - - -</option>
								          <c:forEach items="${labRooms}" var="l">
								          	<c:if test="${l.id eq curr.labRoom.id }"><option value="${l.id}" selected="selected">${l.labRoomName}-${l.labRoomAddress }</option></c:if>
								            <c:if test="${l.id ne curr.labRoom.id }"><option value="${l.id}">${l.labRoomName}-${l.labRoomAddress }</option></c:if>
								          </c:forEach>
								        </select>
									</td>
									<td class="tc">
										<textarea  class="requirement_edit" id="lpPurposes${curr.id }" name="lpPurposes${curr.id }" placeholder="请输入内容">${curr.lpPurposes}</textarea>
									</td>
									<td class="tc">
										<button class="fa fa-save mlr2p blue" ></button>
									</td>
								</tr>
								</form>
							</c:forEach>
							<c:if test="${!isEdit}">
								<form:form action="${pageContext.request.contextPath}/timetable/saveOperationItemforxidlims?courseNumber=${schoolCourseInfo.courseNumber}"
								 method="post" modelAttribute="operationItem" name="addForm">
								<tr id="addItem" style="display:none">
									<%-- <td id="addCode">${itemCode } <form:input type="hidden" path="lpCodeCustom" value="${itemCode }"/></td> --%>
									<td><form:input path="lpName" id="lpName" class="easyui-validatebox" required="true"/></td>
									<td class="tc"><form:input path="lpDepartmentHours" id="lpDepartmentHours" class="easyui-validatebox" required="true"
							        onkeyup="value=value.replace(/[^\d]/g,'') "   
									placeholder="请输入大于零的整数"
							        /></td>
									<td class="tc">
										<form:select path="labRoom.id" id="labRoomId" name="labRoomId" class="chzn-select">
								          <form:option value="">- - - -请选择- - - -</form:option>
								          <c:forEach items="${labRooms}" var="l">
								            <form:option value="${l.id}">${l.labRoomName}-${l.labRoomAddress }</form:option>
								          </c:forEach>
								        </form:select>
									</td>
									<td class="tc">
										<form:textarea path="lpPurposes" class="requirement_edit" placeholder="请输入内容"/>
									</td>
									<td class="tc">
										<button class="fa fa-save mlr2p blue" ></button>
									</td>
								</tr>
								</form:form>
							</c:if>
						</table>
						<div class="edit_bottom">
							<a class="fa fa-plus f20 r hbb poi" onclick="showAddItem()" title="新增内容"></a>
						</div>
					</div>
					
					<div id="searchFile" class="easyui-window" title="上传附件" closed="true" iconCls="icon-add" style="width:400px;height:200px">
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
	<!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
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
			$(this).css("width","200px");
		});
	});
		    
		function uploaddocment(){
		//未保存主表时候先不给上传文件写死
 			 var info = ${courseupload};
 			 //获取当前屏幕的绝对位置
             var topPos = window.pageYOffset;
             //使得弹出框在屏幕顶端可见
             $('#searchFile').window({left:"0px", top:topPos+"px"});
			 $('#searchFile').window('open');
			 
			 $('#file_upload').uploadify({
				'formData':{courseNumber:info},    //传值
	            'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
	            'uploader':'${pageContext.request.contextPath}/operation/uploaddnolinedocment?courseNumber='+info+';'+'jsessionid=<%=session.getId()%>',		//提交的controller和要在火狐下使用必须要加的id
	            buttonText:'选择文件',
		       'onUploadSuccess' : function(file, data, response) {
					   $("#doc").append(data); 
    		 },
    		 onQueueComplete : function(data) {
    		   var ss="";
    		   
    		    $("tr[id*='s_']").each(function(){
		         var eval1=$(this).attr("id");
		          var str1= eval1.substr(eval1.indexOf("_")+1 ,eval1.lenght);  
		         var vals1=str1+"_"+$(this).attr("value");
		        
		         ss+=str1+",";
		         });
    		   
	            $("#docment").attr("value",ss); 
	             $('#searchFile').window('close');	
    		 }
	        });
			
		}
		function showAddItem(){
				$("#addItem").show();
			}
			    
		function delectuploaddocment(s){
		if(confirm( '你真的要删除吗？ ')==false){return   false;}else{ 
		  $.post('${pageContext.request.contextPath}/operation/delectdnolinedocment?idkey='+s+'',function(data){  //serialize()序列化
				   if(data=="ok"){
				   $("#s_"+s+"").empty();
				   
				   }
				
			 });
			 }
		}
		function showEditItem(id){
				$("#showItem"+id).hide();
				$("#editItem"+id).show();
			}

        function savecourseInfo() {
            $.ajax({
                type: "POST",//方法类型
                url: "${pageContext.request.contextPath}/timetable/saveSchoolCourseInfo",//url
                data: $('#courseInfoform').serialize(),
                success: function (result) {
                    back();
                }
            });
            //document.courseInfoform.submit();
        }
			
			function back(){
			//location.href="${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=1";
				history.go(-1);
				location.relode();
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
