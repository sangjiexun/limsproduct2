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
  <style type="text/css">
  	fieldset th{
  		text-align:left;
  	}
  </style>
  <script type="text/javascript">
  //获取教师数据
  function ajaxGetUser(id){
    $.ajax({
      type:"GET",
      data:{academyNumber:${schoolAcademy.academyNumber},role:"1"},  //role=1是教师
      url:"${pageContext.request.contextPath}/operation/ajaxGetUser",
      dataType:"json",
      success:function(data){
        $.each(data, function(i, obj){
          $("#"+id).append("<option value='"+obj.username+"'>"+obj.cname+"["+obj.username+"]</option>");
        })
        
        $("#"+id).trigger("liszt:updated");
        alert("获取成功！");
      }
    });
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
<div class="main_container cf rel w95p ma">
  
  <!-- 内容栏开始 -->
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    
  <!-- 表单 -->    
    <form:form id="edit_form" action="${pageContext.request.contextPath}/basic_data/saveSchoolDeviceValues?deviceNumber=${deviceNumber}&yearId=${yearId}" method="POST" modelAttribute="schoolDeviceUses">
    <div class="new-classroom">
      
      <fieldset>
        <form:hidden path="id"/>
        <label>测样数：</label>
        <form:input path="sampleCount" id="sampleCount" class="easyui-validatebox" required="true"/>
      </fieldset>
      
       <fieldset>
        <label>培训人员数（学生）：</label>
        <form:input path="trainNumberStudent" id="trainNumberStudent" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>培训人员数（教师）：</label>
        <form:input path="trainNumberTeacher" id="trainNumberTeacher" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>培训人员数（其他）：</label>
        <form:input path="trainNumberOther" id="trainNumberOther" class="easyui-validatebox" required="true"/>
      </fieldset>
     
     <fieldset>
        <label>教学实验项目数：</label>
        <form:input path="itemTeaching" id="itemTeaching" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>科研项目数：</label>
        <form:input path="itemResearch" id="itemResearch" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>社会服务项目数：</label>
        <form:input path="itemSocial" id="itemSocial" class="easyui-validatebox" required="true"/>
      </fieldset>
  
        <fieldset>
        <label>获奖情况（国家级）：</label>
        <form:input path="prizeNation" id="prizeNation" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>获奖情况（省部级）：</label>
        <form:input path="prizeProvince" id="prizeProvince" class="easyui-validatebox" required="true"/>
      </fieldset>
      
      <fieldset>
        <label>发明专利教师：</label>
        <form:input path="patentTeacher" id="patentTeacher" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>发明专利学生：</label>
        <form:input path="patentStudent" id="patentStudent" class="easyui-validatebox" required="true"/>
      </fieldset>   
     
     <fieldset>
        <label>论文情况--三大检索：</label>
        <form:input path="paperSci" id="paperSci" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>论文情况--核心刊物：</label>
        <form:input path="paperKey" id="paperKey" class="easyui-validatebox" required="true"/>
      </fieldset>   
	 
	 
      
  
      
      <fieldset style="width:18.8%">
        <label>负责人姓名：</label>
        <form:select path="user.username" id="user" required="true">
          <form:option value="">- - - -请选择- - - -</form:option>
          <form:options items="${users}" itemLabel="cname" itemValue="username"/>
        </form:select>
      </fieldset>
   
     
     
    
      
   
    
    <div class="moudle_footer">
        <div class="submit_link">
       <input class="btn" id="save" type="button" onclick="saveSchoolDeviceValues()" value="保存"/> 
        <!--  <input class="btn" id="save" type="submit"  value="保存"/> -->
         </div>
    </div>
    </div>
    </form:form>
    
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
		    //保存
		     function saveSchoolDeviceValues(){
	           $.ajax({
	                  url:'${pageContext.request.contextPath}/basic_data/saveSchoolDeviceValues?deviceNumber=${deviceNumber }&yearId=${yearId }',
	                  type:'POST',
	                 data:$('#edit_form').serialize(),
	                 error:function (request){
	                   alert('请求错误!');
	                 },
	                 success:function(){  
	                   parent.location.href="${pageContext.request.contextPath}/basic_data/schoolDeviceUse?currpage=1";
                       var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                       parent.layer.closeAll('iframe');
	                 }         
	           });
	        }
	</script>
	<!-- 下拉框的js -->
  </div>
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
