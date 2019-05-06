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
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">期望学生数量</a></li>
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title">填写期望学生数量</div>
	</div>
	<form:form name="form" action="${pageContext.request.contextPath }/nwuChose/saveExpectNumber" method="post" modelAttribute="professor" onsubmit="return checkMaxStudent('${maxStudent}')">
	<div class="new-classroom">
	  <form:hidden path="choseTheme.id"/>
	  <form:hidden path="user.username"/>
	  <fieldset>
	    <label>期望学生数量：</label>
	  	<form:input path="expectNumber" id="expectNumber" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  </div>
	<div class="moudle_footer">
        <div class="submit_link">
            <input class="btn" id="save" type="submit" value="保存" >
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
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
	    function checkMaxStudent(maxStudent){
	   	   var m=document.getElementById("expectNumber").value;
		    if(m-maxStudent>0){
		    alert("超过最大学生的数量");
		    return false;
		   }
		   else{
		    return true;
		   } 
	    
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
