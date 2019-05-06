<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%><jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
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
    <form:form id="edit_form" action="${pageContext.request.contextPath}/basic_data/saveLabRoomReportFund" method="POST" modelAttribute="labRoomReportFunds">
    <div class="new-classroom">
    
      <fieldset style="width:18.8%">
        <label>所属学院：</label>
        <form:select path="schoolAcademy.academyNumber" id="schoolAcademy" required="true">
          <form:option value="">- - - -请选择- - - -</form:option>
          <form:options items="${subjects}" itemLabel="academyName" itemValue="academyNumber"/> 
        </form:select>
      </fieldset>
      
      <fieldset>
        <form:hidden path="id"/>
        <label>实验室个数：</label>
        <form:input path="labCount" id="labCount" class="easyui-validatebox" required="true"/>
      </fieldset>
      
       <fieldset>
        <label>实验室房屋使用面积：</label>
        <form:input path="labArea" id="labArea" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>经费投入（总计）：</label>
        <form:input path="labFundTotal" id="labFundTotal" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>仪器设备购置经费(总计)：</label>
        <form:input path="labDeviceFundTotal" id="labDeviceFundTotal" class="easyui-validatebox" required="true"/>
      </fieldset>
     
     <fieldset>
        <label>仪器设备购置经费(教学)：</label>
        <form:input path="labDeviceFundTeaching" id="labDeviceFundTeaching" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>仪器设备维护经费(总计)：</label>
        <form:input path="labDeviceRepairTotal" id="labDeviceRepairTotal" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>仪器设备维护费(教学)：</label>
        <form:input path="labDeviceRepairTeaching" id="labDeviceRepairTeaching" class="easyui-validatebox" required="true"/>
      </fieldset>
  
        <fieldset>
        <label>实验教学运行经费(总计)：</label>
        <form:input path="labTeachFundTotal" id="labTeachFundTotal" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>实验教学运行经费(材料消耗)：</label>
        <form:input path="labTeachFundExpendable" id="labTeachFundExpendable" class="easyui-validatebox" required="true"/>
      </fieldset>
      
      <fieldset>
        <label>实验室建设经费：</label>
        <form:input path="labConstructionFund" id="labConstructionFund" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>实验教学研究与改革经费：</label>
        <form:input path="labResearchFund" id="labResearchFund" class="easyui-validatebox" required="true"/>
      </fieldset>   
     
     <fieldset>
        <label>其他经费：</label>
        <form:input path="otherFund" id="otherFund" class="easyui-validatebox" required="true"/>
      </fieldset>
      

     
   
    
    <div class="moudle_footer">
        <div class="submit_link">
       <input class="btn" id="save" type="button" onclick="saveLabRoomReportFund()" value="保存"/> 
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
		     function saveLabRoomReportFund(){
	           $.ajax({
	                  url:'${pageContext.request.contextPath}/basic_data/saveLabRoomReportFund',
	                  type:'POST',
	                 data:$('#edit_form').serialize(),
	                 error:function (request){
	                   alert('请求错误!');
	                 },
	                 success:function(){  
	                   parent.location.href="${pageContext.request.contextPath}/basic_data/labReportFund?currpage=1";
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
