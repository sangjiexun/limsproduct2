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
    <form:form id="edit_form" action="${pageContext.request.contextPath}/basic_data/saveLabRoomReportBasic?labRoom=${labRoomId}" method="POST" modelAttribute="labRoomReportBasic">
    <div class="new-classroom">
      
      <fieldset>
        <form:hidden path="id"/>
        <label>教师获奖与成果--国家级：</label>
        <form:input path="labPrizeNation" id="labPrizeNation" class="easyui-validatebox" required="true"/>
      </fieldset>
      
       <fieldset>
        <label>教师获奖与成果--省部级：</label>
        <form:input path="labPrizeProvince" id="labPrizeProvince" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>教师获奖与成果--专利：</label>
        <form:input path="labPrizePatent" id="labPrizePatent" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>学生获奖与成果：</label>
        <form:input path="labPrizeStudent" id="labPrizeStudent" class="easyui-validatebox" required="true"/>
      </fieldset>
     
     <fieldset>
        <label>论文和教材情况三大检索收录--教学：</label>
        <form:input path="labPaperSciTeaching" id=" labPaperSciTeaching" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>论文和教材情况三大检索收录--科研：</label>
        <form:input path="labPaperSciResearch" id="labPaperSciResearch" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>论文和教材情况核心刊物--教学：</label>
        <form:input path="labPaperKeyTeaching" id="labPaperKeyTeaching" class="easyui-validatebox" required="true"/>
      </fieldset>
  
        <fieldset>
        <label>论文和教材情况核心刊物--科研：</label>
        <form:input path="labPaperKeyResearch" id="labPaperKeyResearch" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>论文和教材情况--实验教材：</label>
        <form:input path="labPaperBook" id="labPaperBook" class="easyui-validatebox" required="true"/>
      </fieldset>
      
      <fieldset>
        <label>科研及社会服务情况--科研项目省部级以上：</label>
        <form:input path="labResearchNation" id="labResearchNation" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>科研及社会服务情况--科研项目其他：</label>
        <form:input path="labResearchOther" id="labResearchOther" class="easyui-validatebox" required="true"/>
      </fieldset>   
     
     <fieldset>
        <label>科研及社会服务情况--社会服务项目：</label>
        <form:input path="labService" id="labService" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>科研及社会服务情况-教研项目省部级以上：</label>
        <form:input path="labTeachingNation" id="labTeachingNation" class="easyui-validatebox" required="true"/>
      </fieldset>   
	 
	 <fieldset>
        <label>科研及社会服务情况--教研项目其他：</label>
        <form:input path="labTeachingOther" id="labTeachingOther" class="easyui-validatebox" required="true"/>
      </fieldset> 
      
    <fieldset>
        <label>毕业设计和论文--研究生：</label>
        <form:input path="labGraduateMaster" id="labGraduateMaster" class="easyui-validatebox" required="true"/>
      </fieldset>
      
        <fieldset>
        <label>毕业设计和论文--本科生：</label>
        <form:input path="labGraduateBachelor" id="labGraduateBachelor" class="easyui-validatebox" required="true"/>
      </fieldset>   
	 
	 <fieldset>
        <label>毕业设计和论文--专科生：</label>
        <form:input path="labGraduateOther" id="labGraduateOther" class="easyui-validatebox" required="true"/>
      </fieldset> 
      
    <fieldset>
        <label>开放实验--实验个数--校内：</label>
        <form:input path="labOpenItemCountInner" id="labOpenItemCountInner" class="easyui-validatebox" required="true"/>
      </fieldset>   
	 
	 <fieldset>
        <label>开放实验--实验个数--校外：</label>
        <form:input path="labOpenItemCountOutter" id="labOpenItemCountOutter" class="easyui-validatebox" required="true"/>
      </fieldset> 
    
    <fieldset>
        <label>开放实验--实验人数--校内：</label>
        <form:input path="labOpenItemStudentInner" id="labOpenItemStudentInner" class="easyui-validatebox" required="true"/>
      </fieldset>   
	 
	 <fieldset>
        <label>开放实验--实验人数--校外：</label>
        <form:input path="labOpenItemStudentOutter" id="labOpenItemStudentOutter" class="easyui-validatebox" required="true"/>
      </fieldset> 
     
     <fieldset>
        <label>开放实验--实验人时数--校内：</label>
        <form:input path="labOpenItemHourInner" id="labOpenItemHourInner" class="easyui-validatebox" required="true"/>
      </fieldset>   
	 
	 <fieldset>
        <label>开放实验--实验人时数--校外：</label>
        <form:input path="labOpenItemHourOutter" id="labOpenItemHourOutter" class="easyui-validatebox" required="true"/>
      </fieldset>     
    
      <fieldset>
        <label>兼任人员数：</label>
        <form:input path="labPartTime" id="labPartTime" class="easyui-validatebox" required="true"/>
      </fieldset> 
     
     <fieldset>
        <label>实验教学运行费用总计：</label>
        <form:input path="labCostTotal" id="labCostTotal" class="easyui-validatebox" required="true"/>
      </fieldset>   
	 
	 <fieldset>
        <label>实验教学运行费用教学实验材料消耗费：</label>
        <form:input path="labCostTeaching" id="labCostTeaching" class="easyui-validatebox" required="true"/>
      </fieldset>      
   
    
    <div class="moudle_footer">
        <div class="submit_link">
       <input class="btn" id="save" type="button" onclick="saveLabRoomReportBasic()" value="保存"/> 
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
		     function saveLabRoomReportBasic(){
	           $.ajax({
	                  url:'${pageContext.request.contextPath}/basic_data/saveLabRoomReportBasic?labRoomId=${labRoomId}',
	                  type:'POST',
	                 data:$('#edit_form').serialize(),
	                 error:function (request){
	                   alert('请求错误!');
	                 },
	                 success:function(){  
	                   parent.location.href="${pageContext.request.contextPath}/basic_data/labRoomReportBasic?currpage=1";
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
