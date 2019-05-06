<%@page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<head>
<meta name="decorator" content="iframe"/>
<!-- 下拉框的样式 -->
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
	<!-- 下拉的样式结束 -->
	
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/validator.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/iCheck/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/sweetalert/sweetalert.min.js"></script>

	<style>
		fieldset label {
		    display: block;
		    float: left;
		    font-weight: bold;
		    height: 20px;
		    line-height: 25px;
		    margin: -5px 0 5px;
		    padding-left: 10px;
		    text-shadow: 0 1px 0 #fff;
		    text-transform: uppercase;
		    width: 90%;
		}  
	</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
<script type="text/javascript"> 
 $(function(){
$(".datebox :text").attr("readonly","readonly"); 
  $('#year_name').blur(function(){
            var name = $('#year_name').val();
            $.trim(name);
            $('#code').val(name.substr(0,4));
            //$.messager.alert('错误','输入有误!<br>请严格按照格式输入！');
        });
}); 

function checkYear(){
	var inputValue = document.getElementById('year_name');
 		 $.ajax({
 	           type:"POST",
 	           url:"${pageContext.request.contextPath}/basic_data/checkYear",
 	           data:{yearName:inputValue.value},
 	           success:function(data){
 	        	  if(data=="ok"){        	  
 	        	 alert(inputValue.value +"已存在!");
 	        	 return false;
 	        	  }
 	           }       
 		 });
	}
	function saveNewYear(){
	           $.ajax({
	                 url:'${pageContext.request.contextPath}/basic_data/saveNewYear',
	                 type:'POST',
	                 data:$('#edit_form').serialize(),
	                 error:function (request){
	                   alert('请求错误!');
	                 },
	                 success:function(){
	                   	parent.location.href="${pageContext.request.contextPath}/basic_data/listYear?currpage=1";
                    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    	parent.layer.closeAll('iframe');
	                 }         
	           });
	     }
</script>
</head>
<!-- 导航栏 -->
<body>
<div class="main_container cf rel w95p ma">
<!-- 导航栏 -->
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<div class="title">新建学年
				</div>  
	     <!-- 表单 -->
                <form:form id="edit_form" action="${pageContext.request.contextPath}/basic_data/saveNewYear"  name="myForm" method="POST" modelAttribute="schoolYear">
                <div class="new-classroom">

               <fieldset>
 	                <form:hidden path="id" value="${idKey }"/> 
                    <label>学年名称<font color=red>*</font></label>
			        <td><form:input class="easyui-validatebox" id="year_name" path="yearName" required="true"  validType="length[0,25]" invalidMessage="不能超过25个字符！" />
			         例：2010-2011学年</td>
			  </fieldset>
			  
               <fieldset>
		            <label>学年码<font color=red>*</font></label>
                    <form:input id="code" path="code" required="true" readonly="true"/></br>例：2010-2011学年,学年码是2010</td>
              </fieldset>

             <fieldset>
		            <label>学年开始时间<font color=red>*</font></label>
			         <td><input id="year_start" name="yearStart" value="${date}" onclick="new Calendar().show(this);"  readonly="readonly"  required="true"/></td>
             </fieldset>
         
            <fieldset>
                    <label>学年结束时间<font color=red>*</font></label>
                    <td><input id="year_end" name="yearEnd" value="${date}" onclick="new Calendar().show(this);" readonly="readonly"  required="true"/></td>
           </fieldset>

          </div>
 
    <div class="moudle_footer">
        <div class="submit_link">
         <input class="btn" id="save" type="button" onclick="saveNewYear();" value="提交">
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
	    
	
	</script>
</div>
</div>
</div>
</div>
</div>
</body>
