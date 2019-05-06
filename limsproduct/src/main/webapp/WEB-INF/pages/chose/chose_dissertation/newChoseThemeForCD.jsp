<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  
  <script type="text/javascript">
  </script>
<script type="text/javascript">
</script>	

</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">毕业论文互选大纲</a></li>
		<li class="end"><a href="javascript:void(0)">毕业论文互选大纲列表</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
    <%--<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s0"><a href="${pageContext.request.contextPath}/ChoseThemeList?currpage=1">全部</a></li>
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=1&orderBy=${orderBy }">草稿</a></li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=2&orderBy=${orderBy }">审核中</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=3&orderBy=${orderBy }">审核通过</a></li>
		<li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=4&orderBy=${orderBy }">审核拒绝</a></li>
	</ul>
  --%><div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  
  <div class="content-box">
    <div class="title">
	  <div id="title">毕业论文互选大纲列表</div>
	  <a class="btn btn-new" onclick="submitForm()">下一步</a>
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/choseDissertation/ChoseDissertationThemeList?currpage=1">返回</a>
	</div>
	
	<div class="tool-box">
		<form:form id="myForm" name="queryForm" action="${pageContext.request.contextPath}/choseDissertation/fristSaveChoseThemeForCD" method="post" modelAttribute="choseTheme">
			<ul>
				<li>所属届：</li>
				<li>
					<form:select path="theYear" class="easyui-validatebox" required="true">
						<form:option value="">请选择</form:option>
						<c:forEach items="${attendanceTimeList }" var="curr">
						   <form:option value="${curr}">${curr}</form:option>
						</c:forEach>
					</form:select>
				</li>
  				<li>立题开始时间： </li>
  				<li><input  class="easyui-datebox" id="startline"  name="startline"  type="text"  onclick="new Calendar().show(this);" style="width:100px;" /></li>
  				<li>立题结束时间： </li>
  				<li><input  class="easyui-datebox" id="endline"  name="endline"  type="text"  onclick="new Calendar().show(this);" style="width:100px;" /></li>
  				<li>每位老师出题下限： </li>
  				<li><form:input type="text" id="maxStudent" name="maxStudent" path="maxStudent"  onkeyup="value=value.replace(/[^\d]/g,'')" /></li>
  				<!-- <li>完成时间： </li>
	  	        <li><input class="easyui-datebox" id="finishTime"  name="finishTime"  type="text"  onclick="new Calendar().show(this);" style="width:100px;" /></li> -->
  			</ul>
		</form:form>
	</div>
  </div>
  </div>
  </div>
  </div>
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
	 //提交表单
	 function submitForm(){
		 var startlineStr = $('#startline').datebox('getValue');
		 var endlineStr = $('#endline').datebox('getValue');
		 var maxStudent = document.getElementById("maxStudent").value;
		 var theYear = document.getElementById("theYear").value;
		 if(startlineStr=="" || endlineStr=="" || maxStudent==""||theYear==""){
			 alert("请填完整表单！！！");
			 return false;
		 }
		 else{
		 	var startline=new Date(startlineStr);
		 	var endline=new Date(endlineStr);
		 	
		   if(endline<startline){
		   	alert("立题结束时间超过开始时间");
		   }
	 	   else{
	 		$.ajax({
				url:"${pageContext.request.contextPath}/choseDissertation/checkSameTheYearChoseTheme?theYear="+theYear,
				type:"post",
				success:function(result){
					if(result==0){
						//跳转到关闭和删除大纲的页面
						var flag=confirm("存在同届的大纲，需要手动操作大纲");
						if(flag){
						   window.location.href="${pageContext.request.contextPath}/choseDissertation/ChoseDissertationThemeList?currpage=1";
						}
					}
					else{
						document.queryForm.submit();//进入新建大纲的下一步
					}
				}
			});
	 	  }
		 }
	 }
	</script>
</body>
</html>
