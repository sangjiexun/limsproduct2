<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
  <style type="text/css">
  	fieldset th{
  		text-align:left;
  	}
  </style>
  <script type="text/javascript">
  
  /**
  添加设备
  */
  function openwin(){
  	var name=document.getElementById("deviceName").value;
  	var number=document.getElementById("deviceNumber").value;
  	var deviceAddress=document.getElementById("deviceAddress").value;
  	$.ajax({
  	           url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page=1",
  	           
  	           type:"POST",
  	           success:function(data){//AJAX查询成功
  	                  document.getElementById("body").innerHTML=data;
  	            
  	           }
  	});
  	$("#addDevice").show();
    //获取当前屏幕的绝对位置
    var topPos = window.pageYOffset;
    //使得弹出框在屏幕顶端可见
    $('#addDevice').window({left:"100px", top:topPos+"px"});

    $("#addDevice").window('open');
   }
  
//查询设备
  function querySchoolDevice(){
      
      var name=document.getElementById("deviceName").value;
      var number=document.getElementById("deviceNumber").value;
      //var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
      var deviceAddress=document.getElementById("deviceAddress").value;
      $.ajax({
          
          url:encodeURI(encodeURI("${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page=1")),
          type:"POST", 
          success:function(data){//AJAX查询成功
              document.getElementById("body").innerHTML=data;
          }
      });
  }
  //取消查询设备
  function cancelSchoolDevice(){
      
      document.getElementById("deviceName").value=" ";
      document.getElementById("deviceNumber").value=" ";
      document.getElementById("deviceAddress").value=" ";
      $.ajax({
          url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name=&number=&deviceAddress=&page=1",
          type:"POST",
          
          success:function(data){//AJAX查询成功
              document.getElementById("body").innerHTML=data;
              document.getElementById("deviceName").value="";
          }
      });
  }


  //增加全选功能
  function checkAll()
    {
      if($("#check_all").attr("checked"))
      {
        $(":checkbox").attr("checked", true);
      }
      else
      {
        $(":checkbox").attr("checked", false);
      }
  }


  //首页
  function first(page){
      var name=document.getElementById("deviceName").value;
      var number=document.getElementById("deviceNumber").value;
      //var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
      var deviceAddress=document.getElementById("deviceAddress").value;
      $.ajax({
                 url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,
                 
                 type:"POST",
                 success:function(data){//AJAX查询成功
                        document.getElementById("body").innerHTML=data;
                  
                 }
      });
  }
  //上一页
  function previous(page){
      if(page==1){
              page=1;
          }else{
              page=page-1;
          }
      var name=document.getElementById("deviceName").value;
      var number=document.getElementById("deviceNumber").value;
      //var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
      var deviceAddress=document.getElementById("deviceAddress").value;
      $.ajax({
                 url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,
                 
                 type:"POST",
                 success:function(data){//AJAX查询成功
                        document.getElementById("body").innerHTML=data;
                  
                 }
      });
  }
  //下一页
  function next(page,totalPage){
      if(page>=totalPage){
              page=totalPage;
          }else{
              page=page+1
          }
      var name=document.getElementById("deviceName").value;
      var number=document.getElementById("deviceNumber").value;
      //var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
      var deviceAddress=document.getElementById("deviceAddress").value;
      $.ajax({
                 url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,
                 
                 type:"POST",
                 success:function(data){//AJAX查询成功
                        document.getElementById("body").innerHTML=data;
                  
                 }
      });
  }
  //末页
  function last(page){
      var name=document.getElementById("deviceName").value;
      var number=document.getElementById("deviceNumber").value;
      //var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
      var deviceAddress=document.getElementById("deviceAddress").value;
      $.ajax({
                 url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,
                 
                 type:"POST",
                 success:function(data){//AJAX查询成功
                        document.getElementById("body").innerHTML=data;
                  
                 }
      });
  }
  	
     //2015-09-10新增，为了让新建页面中有添加材料和仪器设备
  
  function addMaterialRecord()
  {
    $("#lpmrId").val(null);
    $("#lpmr_name").val("");
    $("#lpmr_category").val("");
    $("#lpmr_model").val("");
    $("#lpmr_unit").val("");
    $("#lpmr_quantity").val("");
    $("#lpmr_amount").val("");
    $("#lpmr_remark").val("");
  
    $("#editMaterialRecord").show();
    //获取当前屏幕的绝对位置
    var topPos = window.pageYOffset;
    //使得弹出框在屏幕顶端可见
    $('#editMaterialRecord').window({left:"100px", top:topPos+"px"});

    $("#editMaterialRecord").window('open');
  }
  
  function saveMaterialRecord()
  {
  	var idx = $("#idx").val();
  	var lpmr_quantity = $("#lpmr_quantity").val();
  	
   document.form_material.action="${pageContext.request.contextPath}/operation/saveItemMaterialRecordNew";
	if(idx==null || idx=='') {
		alert("请选择材料！");
		return false;
	}else 
	if(lpmr_quantity==null || lpmr_quantity=='') {
		alert("请填写数量！");
		return false;
	}else 
    if(isNaN($("#lpmr_quantity").val())){
			alert("【数量】填写数字");
			return false;
	}else {
	    document.form_material.submit();
	}
    
  }
  
  function editMaterialRecord(mrId)
  {
    $.ajax({
      type:"GET",
      url:"${pageContext.request.contextPath}/operation/ajaxGetMaterialRecord",
      data:{mrId:mrId},
      dataType:"json",
      success:function(data){
        $("#lpmrId").val(mrId);
        $("#lpmr_name").val(data.lpmrName);
        $("#lpmr_category").val(data.lpmrCategory);
        $("#lpmr_model").val(data.lpmrModel);
        $("#lpmr_unit").val(data.lpmrUnit);
        $("#lpmr_quantity").val(data.lpmrQuantity);
        $("#lpmr_amount").val(data.lpmrAmount);
        $("#lpmr_remark").val(data.lpmrRemark);
        
        $("#editMaterialRecord").show();
        $("#editMaterialRecord").window('open');
      }
    });
  }
  
  //全部选择或不选
  function checkAll()
  {
    if($("#check_all").attr("checked"))
    {
      $(":checkbox").attr("checked", true);
    }
    else
    {
      $(":checkbox").attr("checked", false);
    }
  }
  //保存实验项目设备
  function sss()
  {
    var flag = false;  //是否有checkbox被选中
    var category = 1;
    var ids = "";
	var array=new Array();
    $("input[name='CK']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox  
                 if ($(this).attr("checked")) { //判断是否选中
                 	ids += $(this).val()+",";
             		flag = true;
                 }  
             })  
          if(flag)
      	{
      	  if(ids.length > 0)
      	  {
      	  	ids = ids.substring(0, ids.length-1);  //去掉最后的逗号
      	  }
      	  if(category=="")
      	  {
      	    alert("请选择类型！");
      	    return false;
      	  }
      	window.location.href="${pageContext.request.contextPath}/operation/saveItemDevice?itemId=${operationItem.id}&category="+category+"&ids="+ids;
      	}
      	else
      	{
      	  alert("至少选择一个设备！");
      	}
      	
  }
  </script>
  <script type="text/javascript">
  
		
	function delectuploaddocment(s){
	if(confirm( '你真的要删除吗？ ')==false){return   false;}else{ 
	  $.post('${pageContext.request.contextPath}/operation/delectdnolinedocment?idkey='+s+'',function(data){  //serialize()序列化
		   if(data=="ok"){
			   $("#s_"+s+"").empty();
		   }
		 });
		 }
	}
  function getSpec(osel){
	     var content = osel.options[osel.selectedIndex].text;
	      
	      var x,y;
	      for(var i = 0; i<content.length; i++){
	     	 if(content[i] == '['){
	     		 x=i;
	     	 }
	     	 if(content[i] == ']'){
	     		 y=i;
	     		 break;
	     	 }
	      }
	      var unit  = content.substring(x,y+1);
	      unit = unit.replace(/[^a-z^A-Z]/g,'');
	      $("#unit").html(unit);
	  }
  
  
  $(document).ready(function(){
     if(${flagId==10}){
    	 alert("项目卡片保存成功！")
     }
	});
  
  
  //保存新建/编辑 的实训室项目卡
  function saveEditForm(){
	  if($("#lpGuideBookTitle").val()==""){
		  alert("您还未填写“指导书名称”一栏，暂不能保存！")
	  }else{
	  	document.edit_form.submit();
	  }
  }
  </script>
<script type="text/javascript">
	function checkRequired(){
	  if($("#lpName").val() == ""){
		  alert("请填写实验名称!");
	  }
	  else if($("#lpDepartmentHours").val() == ""){
		  alert("请填写实验学时!");
	  }
	  else if($("#lpStudentNumber").val() == ""){
		  alert("请填写实验者人数!");
	  }
	  else if($("#labRoomId").val() == ""){
		  alert("请选择所属实训室!");
	  }
	  else if( $("#schoolCourseInfo").val() == ""){
		  alert("请选择所属课程!");
	  }
	  else if($("#outline").val() == ""){
		  alert("请选择所属大纲!");
	  } 
	  else{
		  document.edit_form.action="${pageContext.request.contextPath}/operation/saveOperationItem?toMyList=${isMine}&flagId=${flagId}";
		  document.edit_form.submit();
	  }
  }
</script>
<style>
	fieldset label {
	    display: block;
	    float: left;
	    font-weight: bold;
	    height: 25px;
	    line-height: 25px;
	    margin: -5px 0 5px;
	    padding-left: 10px;
	    text-shadow: 0 1px 0 #fff;
	    text-transform: uppercase;
	    width: 90%;
	}  
</style>
</head>
  
<body>
  	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange"/></a></li>
				<li><a href="javascript:void(0)"><spring:message code="left.item.operation"/>实训项目</a></li>
				<li class="end"><a href="javascript:void(0)">
					<c:if test="${flagId==1 || flagId==10 }">项目编辑</c:if>
					<c:if test="${flagId==0 }">新增项目</c:if></a></li>
			</ul>
		</div>
	</div>

	  
 
  <div class="content-box">
		<div class="title">
			<c:if test="${flagId==1 || flagId==10 }"><div id="title">项目编辑</div></c:if>
			<c:if test="${flagId==0 }"><div id="title">新增项目</div></c:if>
		</div>
		<div class="tool-box">
			<form:form name="edit_form" id="edit_form" action="${pageContext.request.contextPath}/operation/saveOperationItem?toMyList=${isMine}&flagId=${flagId}" method="POST" modelAttribute="operationItem">
				<table id="tab">
					<tr>
						<c:if test="${flagId==1 || flagId==10 }">
							<form:hidden path="id"></form:hidden>
						</c:if>
						<th style="width: 100px;">实验名称：</th>
						<td><form:input id="lpName" path="lpName" style="width: 200px; float:left;" /></td>
						<th style="width: 100px;">学时数(数字)：</th>
						<td><form:input path="lpDepartmentHours" id="lpDepartmentHours" required="true" onkeyup="value=value.replace(/[^\d]/g,'') " style="width: 200px; float:left;" />
						</td>
						<th style="width: 100px;">所属课程：</th>
						<td>
						<form:select path="schoolCourseInfo.courseNumber" 
						class="chzn-select" id="schoolCourseInfo" required="true" style="width: 200px;">
						     <form:option value="">请选择</form:option>
						     <c:forEach items="${schoolCourseInfos}" var="c">
								 <c:if test="${c.courseNumber == courseNumber}">
									 <form:option value ="${c.courseNumber}" selected="true">[${c.courseNumber}]${c.courseName}</form:option>
								 </c:if>
							     <c:if test="${operationItem.schoolCourseInfo.courseNumber ne c.courseNumber}">
							     	<form:option value="${c.courseNumber}">[${c.courseNumber}]${c.courseName}</form:option>
							     </c:if>
							     <c:if test="${operationItem.schoolCourseInfo.courseNumber eq c.courseNumber}">
							     	<form:option value="${c.courseNumber}">
							     	[${c.courseNumber}]${c.courseName}</form:option>
							     </c:if>
						     </c:forEach>
						</form:select>
						</td>
					</tr>
					<tr>
						<th>面向专业：</th>
						<td>
							<form:select path="lpMajorFit" class="chzn-select" id="lpMajorFit" style="width: 200px;">
							<form:option value="">请选择</form:option>
		                       <c:forEach items="${majorList}" var="m">
		        	              <form:option value="${m.MNumber}">[${m.MNumber}]${m.MName}</form:option>
		                       </c:forEach>
		                       <c:forEach items="${majors}" var="m">
								   <c:if test="${m.MNumber eq schoolMajorsa}">
									   <form:option value ="${m.MNumber}" selected="true">[${m.MNumber}]${m.MName}</form:option>
								   </c:if>
								   <c:if test="${m.MNumber ne schoolMajorsa}">
		                          <form:option value="${m.MNumber}">[${m.MNumber}]${m.MName}</form:option>
								   </c:if>
		                       </c:forEach>
		                    </form:select>
	                    </td>
						<th>关联软件：</th>
						<td>
							<form:select path="software" class="chzn-select" id="software" >
							<form:option value="">请选择</form:option>
		                        <c:forEach items="${softwares}" var="m">
		        	               <form:option value="${m.id}">[${m.code}] ${m.name}</form:option>
		                        </c:forEach>
	          	                <c:forEach items="${listSoftware}" var="m">
	            	               <form:option value="${m.id}">[${m.code}] ${m.name}</form:option>
	          	                </c:forEach>
	                        </form:select>
						</td>
                        <td style="width: 100px;">实验总学时：</td>
						<td><form:input id="lpDepartmentHoursTotal" path="lpDepartmentHoursTotal" style="width: 200px; float:left;" /></td>
                        	<%--<input class="btn" id="save" type="button" onclick="checkRequired()" value="下一步"></td>--%>
					</tr>
					<tr>
						<th style="width: 100px;">实验者人数：</th>
						<td><form:input id="lpStudentNumber" path="lpStudentNumber" style="width: 200px; float:left;" /></td>
						<th style="width: 100px;">实验套数：</th>
						<td><form:input id="lpSetNumber" path="lpSetNumber" style="width: 200px; float:left;" /></td>
						<th style="width: 100px;">每组人数：</th>
						<td><form:input id="lpStudentNumberGroup" path="lpStudentNumberGroup" style="width: 200px; float:left;" /></td>
					</tr>
					<tr>
						<th style="width: 100px;">实验性质：</th>
						<td>
							<form:select path="CDictionaryByLpCategoryNature.id" class="chzn-select" id="labProjectNature" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${labProjectNature}" var="ln">
									<form:option value="${ln.id}">${ln.CName}</form:option>
								</c:forEach>
							</form:select>
						</td>
						<th style="width: 100px;">实验者类型：</th>
						<td>
							<form:select path="CDictionaryByLpCategoryStudent.id" class="chzn-select" id="labProjectStudent" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${labProjectStudent}" var="ls">
									<form:option value="${ls.id}">${ls.CName}</form:option>
								</c:forEach>
							</form:select>
						</td>
						<th style="width: 100px;">变动状态：</th>
						<td>
							<form:select path="CDictionaryByLpStatusChange.id" class="chzn-select" id="labProjectChange" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${labProjectChange}" var="lc">
									<form:option value="${lc.id}">${lc.CName}</form:option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
					<tr>
						<th style="width: 100px;">开放实验：</th>
						<td>
							<form:select path="CDictionaryByLpCategoryPublic.id" class="chzn-select" id="labProjectPublic" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${labProjectPublic}" var="lp">
									<form:option value="${lp.id}">${lp.CName}</form:option>
								</c:forEach>
							</form:select>
						</td>
						<th style="width: 100px;">获奖等级：</th>
						<td>
							<form:select path="CDictionaryByLpCategoryRewardLevel.id" class="chzn-select" id="labProjectRewardLevel" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${labProjectRewardLevel}" var="lrl">
									<form:option value="${lrl.id}">${lrl.CName}</form:option>
								</c:forEach>
							</form:select>
						</td>
						<th style="width: 100px;">实验要求：</th>
						<td>
							<form:select path="CDictionaryByLpCategoryRequire.id" class="chzn-select" id="labProjectRequire" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${labProjectRequire}" var="lr">
									<form:option value="${lr.id}">${lr.CName}</form:option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
					<tr>
						<th style="width: 100px;">学期：</th>
						<td>
							<form:select path="schoolTerm.id" class="chzn-select" id="schoolTerm" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${schoolTerms}" var="st">
									<form:option value="${st.id}">${st.termName}</form:option>
								</c:forEach>
							</form:select>
						</td>
						<%--<th style="width: 100px;">实验中心：</th>
						<td>
							<form:select path="labCenter.id" class="chzn-select" id="labCenter" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${labCenters}" var="lc">
									<form:option value="${lc.id}">${lc.centerName}</form:option>
								</c:forEach>
							</form:select>
						</td>--%>

						<th style="width: 100px;">所属实验室：</th>
						<td>
							<form:select path="labRoom.id" class="chzn-select" id="labRoom" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${labRooms}" var="lb">
									<form:option value="${lb.id}">${lb.labRoomName}</form:option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
					<tr>
						<th style="width: 100px;">所属学科：</th>
						<td>
							<form:select path="systemSubject12.SNumber" class="chzn-select" id="systemSubject12" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${subjects}" var="sb">
									<form:option value="${sb.SNumber}">${sb.SName}</form:option>
								</c:forEach>
							</form:select>
						</td>
						<th style="width: 100px;">所属专业：</th>
						<td>
							<form:select path="schoolMajor.majorNumber" class="chzn-select" id="systemMajor12" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${majorList}" var="m">
									<form:option value="${m.majorNumber}">[${m.majorNumber}]${m.majorName}</form:option>
								</c:forEach>
								<c:forEach items="${majors}" var="m">
									<form:option value="${m.majorNumber}">[${m.majorNumber}]${m.majorName}</form:option>
								</c:forEach>
							</form:select>
						</td>
						<th style="width: 100px;">主讲教师：</th>
						<td>
							<form:select path="userByLpTeacherSpeakerId.username" class="chzn-select" id="userByLpTeacherSpeakerId" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${users}" var="u">
									<form:option value="${u.username}">${u.cname}</form:option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
					<tr>
						<th style="width: 100px;">辅导教师：</th>
						<td>
							<form:select path="userByLpTeacherAssistantId.username" class="chzn-select" id="userByLpTeacherAssistantId" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${users}" var="u">
									<form:option value="${u.username}">${u.cname}</form:option>
								</c:forEach>
							</form:select>
						</td>
						<th style="width: 100px;">实验类别：</th>
						<td>
							<form:select path="CDictionaryByLpCategoryMain.id" class="chzn-select" id="labProjectMain" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${labProjectMain}" var="lm">
									<form:option value="${lm.id}">${lm.CName}</form:option>
								</c:forEach>
							</form:select>
						</td>
						<th style="width: 100px;">实验类型：</th>
						<td>
							<form:select path="CDictionaryByLpCategoryApp.id" class="chzn-select" id="labProjectApp" >
								<form:option value="">请选择</form:option>
								<c:forEach items="${labProjectApp}" var="la">
									<form:option value="${la.id}">${la.CName}</form:option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
					<tr>
						<th style="width: 100px;">考核方法：</th>
						<td><form:input id="lpAssessmentMethods" path="lpAssessmentMethods" style="width: 200px; float:left;" /></td>
						<th style="width: 100px;">项目简介：</th>
						<td><form:input id="lpIntroduction" path="lpIntroduction" style="width: 200px; float:left;" /></td>
						<th style="width: 100px;">课前准备：</th>
						<td><form:input id="lpPreparation" path="lpPreparation" style="width: 200px; float:left;" /></td>
					</tr>
				</table>
			</form:form>
			<c:if test="${flagId==0}">
				<input class="btn" id="save" type="button" onclick="submitOperationItemAll()" value="提交">
			</c:if>

			<!-- 设备仪器开始  -->
		
		<fieldset class="introduce-box" style="float:none;">
		<label>仪器设备</label>
		<table id="listTable" width="50%" cellpadding="0">
			<tr>
				<c:if test="${flagId==0}">
					<td>
						<font color=red>请您提交保存后，再对仪器设备进行添加。</font>
					</td>
				</c:if>
				<c:if test="${flagId==1 || flagId==10 }">
				
				<div id="TabbedPanels1" class="TabbedPanels">
					  <div class="TabbedPanelsContentGroup">
					  <div class="TabbedPanelsContent">
					    <li><input type="button" onclick="openwin();" value="批量添加"></li>
					  <form id="allForm" action="${pageContext.request.contextPath}/operation/saveOperationItemAll?isMine=${isMine}" method="POST" >
					    <div class="content-box">
					    <table>
					      <thead>
					      <tr>
					        <th>序号</th>
					        <th>设备编号</th>
					        <th>设备名称</th>
					        <th>规格</th>
					        <th>型号</th>
					        <th>单价</th>
					        <th>操作</th>
					      </tr>
					      </thead>
					      <tbody>
					      <c:forEach items="${listItemDevice}" var="curr" varStatus="i">
					      <tr>
							  <input type="hidden" class="selectedDevice" name="selectedDevice" value="${curr.schoolDevice.deviceNumber}">
					        <td>${i.count}</td>
					        <td>${curr.schoolDevice.deviceNumber}</td>
					        <td>${curr.schoolDevice.deviceName}</td>
					        <td>${curr.schoolDevice.deviceFormat}</td>
					        <td>${curr.schoolDevice.devicePattern}</td>
					        <td>${curr.schoolDevice.devicePrice}</td>
					        <td>
					          <a href="${pageContext.request.contextPath}/operation/deleteItemDevice?itemDeviceId=${curr.id}&itemId=${operationItem.id}" onclick="return confirm('确认要删除吗？')">删除</a>
					        </td>
					      </tr>
					      </c:forEach>
					      </tbody>
					    </table>
					    </div>
					  </form>
					  </div>
					  </div>
					  </div>
					  
					  <div id="addDevice" class="easyui-window" closed="true" modal="true" minimizable="true" title="实训室设备" style="width: 1000px;height: 500px;padding: 20px">
					  <div class="content-box">
								<form:form id="queryForm" method="post"
									modelAttribute="schoolDevice">
									<table>
										<tr>
											<td>设备名称: <form:input id="deviceName" path="deviceName" />
											</td>
											<td>设备编号: <input type="text" id="deviceNumber"
												placeholder="请输入数字" maxlength="40" validType="length[0,9]" />
											</td>
											<td>设备地点: <form:input id="deviceAddress"
													path="deviceAddress" /></td>
											<td><input type="button" value="搜索"
												onclick="querySchoolDevice();"></td>

											<td><input type="button" value="取消"
												onclick="cancelSchoolDevice()"></td>

											<td><input type="button" value="添加" onclick="sss();">
											</td>
										</tr>
									</table>
								</form:form>
								<table class="eq" id="my_show">
									<thead>
										<tr>
											<th style="width:10% !important">设备编号</th>
											<th style="width:15% !important">设备名称</th>
											<th style="width:20% !important">设备型号</th>
											<th style="width:10% !important">保管员</th>
											<th style="width:10% !important">设备规格</th>
											<th style="width:10% !important">设备价格</th>
											<th style="width:20% !important">设备地点</th>
											<th><input id="check_all" type="checkbox"
												onclick="checkAll();" />
											</th>
										</tr>
									</thead>

									<tbody id="body">

									</tbody>
								</table>
							</div>
					  </div>
				</c:if>
	</tr>
	</table>
	</fieldset>

			<!-- 材料使用开始  -->
			<fieldset class="introduce-box" style="float:none;">
				<label>材料使用</label>
				<table id="listTable" width="50%" cellpadding="0">
					<tr>
						<c:if test="${flagId==0}">
							<td>
								<font color=red>请您提交保存后，再对实验材料进行添加。</font>
							</td>
						</c:if>
						<c:if test="${flagId==1 || flagId==10 }">

							<div class="TabbedPanels">
								<div class="TabbedPanelsContentGroup">
									<div class="TabbedPanelsContent">
										<li><input type="button" onclick="addMaterialRecord();" value="添加"></li>
										<form id="" action="" method="POST" >
											<div class="content-box">
												<table>
													<thead>
													<tr>
														<th>序号</th>
														<th>材料名称</th>
														<th>材料类型</th>
														<th>型号/规格</th>
														<th>单位</th>
														<th>数量</th>
														<th>操作</th>
													</tr>
													</thead>
													<tbody>
													<c:forEach items="${listItemMaterialRecord}" var="curr" varStatus="i">
														<tr>
															<td>${i.count}</td>
															<td>${curr.assetCabinetWarehouseAccessRecord.asset.chName}</td>
															<td>
																<c:if test="${curr.assetCabinetWarehouseAccessRecord.asset.category eq 0}">
																	试剂
																</c:if>
																<c:if test="${curr.assetCabinetWarehouseAccessRecord.asset.category eq 1}">
																	耗材
																</c:if>
															</td>
															<td>${curr.assetCabinetWarehouseAccessRecord.asset.specifications}</td>
															<td>${curr.assetCabinetWarehouseAccessRecord.asset.unit}</td>
															<td>${curr.lpmrQuantity}</td>
															<td>
																<%--<a onclick="deleteItemMaterialRecord(${curr.lpmrId})">删除</a>--%>
																	<a href="${pageContext.request.contextPath}/operation/deleteItemMaterialRecord?mrId=${curr.lpmrId}&itemId=${operationItem.id}" onclick="return confirm('确认要删除吗？')">删除</a>
															</td>
														</tr>
													</c:forEach>
													</tbody>
												</table>
											</div>
										</form>
									</div>
								</div>
							</div>
							<div id="editMaterialRecord" class="easyui-window" closed="true" modal="true" minimizable="true" title="材料使用记录" style="width: 600px;height: 500px;padding: 20px">
								<div class="content-box">
									<form:form name="form_material" method="post" modelAttribute="operationItemMaterialRecord" >
										<table class="color_tb">
											<tr>
												<td>材料名称</td>
												<td><form:hidden path="lpmrId" />
													<form:hidden path="operationItem.id" id="item_id" value="${operationItem.id}"/>
													<form:select id="id" path="assetCabinetWarehouseAccessRecord.id" class="chzn-select">
														<form:option value="">请选择</form:option>
														<c:forEach items="${assetRecords}" var="curr">
															<form:option value="${curr.id}">${curr.asset.chName}[${curr.asset.specifications}]</form:option>
														</c:forEach>
													</form:select>
												</td>
											</tr>
											<tr>
												<td>数量</td><td><form:input path="lpmrQuantity" id="lpmr_quantity" /></td>
											</tr>
											<tr>
												<td>备注</td><td><form:textarea path="lpmrRemark" id="lpmr_remark" /></td>
											</tr>
										</table>
										<div class="moudle_footer">
											<div class="submit_link">
												<input class="btn" type="button" onclick="saveMaterialRecord();" value="确定">
											</div>
										</div>
									</form:form>
								</div>
							</div>
						</c:if>
					</tr>
				</table>
			</fieldset>

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
	<!-- 设备仪器结束  -->
	<!-- 保存整个实训室项目卡 -->
	<c:if test="${flagId==1 || flagId==10 }">
	<input class="btn" id="save" type="button" onclick="saveOperationItemAll()" value="保存">
	</c:if>
	</div>
	<!-- 下拉框的js -->
<script	src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
var config = {
	'.chzn-select' : {
		search_contains : true
	},
	'.chzn-select-deselect' : {
		allow_single_deselect : true
	},
	'.chzn-select-no-single' : {
		disable_search_threshold : 10
	},
	'.chzn-select-no-results' : {
		no_results_text : 'Oops, nothing found!'
	},
	'.chzn-select-width' : {
		width : "95%"
	}
}
for ( var selector in config) {
	$(selector).chosen(config[selector]);
}
//提交保存 保存添加的设备关联
	function saveOperationItemAll() {
    	//alert($(".selectedDevice").val());
    	//return false;
		if($(".selectedDevice").val()){
		    //$("#allForm").submit();
            $("#edit_form").submit();
		}else{
		    alert("必须添加一个设备！！！");
		    return false;
		}
    }
//提交
function submitOperationItemAll() {
    $("#edit_form").submit();
}
</script>
<!-- 下拉框的js -->
</body>


</html>
