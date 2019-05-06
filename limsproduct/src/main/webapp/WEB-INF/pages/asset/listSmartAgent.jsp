<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/operation/listSmartAgents";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  function authorize(){
	    var cname=document.getElementById("cname").value;
		var username=document.getElementById("username").value;
		$.ajax({
		           url:encodeURI(encodeURI("${pageContext.request.contextPath}/asset/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&page=1")),
		           type:"POST",
		           success:function(data){//AJAX查询成功
		           		document.getElementById("user_body").innerHTML=data;
		           }
		});
	    $("#addAdmin").show();
	    $("#addAdmin").window('open');
  }
  function queryUser(){
		
		var cname=document.getElementById("cname").value;
		var username=document.getElementById("username").value;
		$.ajax({
		           url:encodeURI(encodeURI("${pageContext.request.contextPath}/asset/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&page=1")),
		           type:"POST",
		           success:function(data){//AJAX查询成功
		                  document.getElementById("user_body").innerHTML=data;
		            
		           }
		});
		  
	}
  function cancleQuery(){
		document.getElementById("cname").value="";
		document.getElementById("username").value="";
		$.ajax({
	        url:"${pageContext.request.contextPath}/asset/findUserByCnameAndUsername?cname=&username=&page=1",
	        type:"POST",
	        success:function(data){//AJAX查询成功
	               document.getElementById("user_body").innerHTML=data;
	         
	        }
	});
	}
//首页
  function firstPage(page){
  	var cname=document.getElementById("cname").value;
  	var username=document.getElementById("username").value;
  	$.ajax({
  	           url:"${pageContext.request.contextPath}/asset/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&page="+page,
  	           type:"POST",
  	           success:function(data){//AJAX查询成功
  	                  document.getElementById("user_body").innerHTML=data;
  	            
  	           }
  	});
  }
  //上一页
  function previousPage(page){
  	if(page==1){
  			page=1;
  		}else{
  			page=page-1;
  		}	
  	var cname=document.getElementById("cname").value;
  	var username=document.getElementById("username").value;
  	$.ajax({
  	          url:"${pageContext.request.contextPath}/asset/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&page="+page,
  	           type:"POST",
  	           success:function(data){//AJAX查询成功
  	                  document.getElementById("user_body").innerHTML=data;
  	            
  	           }
  	});
  }
  //下一页
  function nextPage(page,totalPage){
  	if(page>=totalPage){
  			page=totalPage;
  		}else{
  			page=page+1
  		}	
  	var cname=document.getElementById("cname").value;
  	var username=document.getElementById("username").value;
  	$.ajax({
  	           url:"${pageContext.request.contextPath}/asset/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&page="+page,
  	           type:"POST",
  	           success:function(data){//AJAX查询成功
  	                  document.getElementById("user_body").innerHTML=data;
  	           }
  	});
  }
  //末页
  function lastPage(page){
  	var cname=document.getElementById("cname").value;
  	var username=document.getElementById("username").value;
  	$.ajax({
  	           url:"${pageContext.request.contextPath}/asset/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&page="+page,
  	           type:"POST",
  	           success:function(data){//AJAX查询成功
  	                  document.getElementById("user_body").innerHTML=data;
  	            
  	           }
  	});
  }
</script>	
  
  
  <style>
    .t_style ul li{
      float:left;
    }
  </style>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)"><spring:message code="left.system.management" /></a></li>
		<li class="end"><a href="javascript:void(0)"><spring:message code="left.powerController.list" /></a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">电源控制器列表</a>
		  </li>
		  <a class="btn btn-new" href="${pageContext.request.contextPath}/asset/newSmartAgent">新增</a>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">电源控制器列表</div>--%>
	  <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/asset/newSmartAgent">新增</a>--%>
	<%--</div>--%>
	
	<%--<div class="tool-box">--%>
		<%--<form:form name="queryForm" action="${pageContext.request.contextPath}/asset/listSmartAgents" method="post" modelAttribute="SmartAgent">--%>
	<%----%>
		<%--</form:form>--%>
	<%--</div>--%>
	 
	<table class="tb" id="my_show">
	  <thead>
	  <tr> 
	  	<th>编号</th>
	    <th>设备名称</th>
	    <th>电源控制器IP</th>
	    <th>物联服务器</th> 
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${listSmartAgent}" var="curr" varStatus="i">
	  <tr> 
	 	<td>${curr.serialNo}</td>
	    <td>${curr.deviceName}</td> 
	    <td>${curr.currIp}</td>
	    <td>${curr.dbhost}</td> 
	    <td>
	   		<a href="${pageContext.request.contextPath}/asset/editSmartAgent?id=${curr.serialNo}">编辑</a>
	        <a href="${pageContext.request.contextPath}/asset/deleteSmartAgent?id=${curr.serialNo}" onclick="return confirm('确定删除？');">删除</a>
	        <a href="${pageContext.request.contextPath}/asset/authorize?id=${curr.serialNo}">授权</a>
	        <%--<a class="btn btn-new" href="javascript:void(0)" onclick="authorize();">授权</a>
	    --%></td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table> 
    
  </div>
  </div>
  </div>
  </div>
  <div id="addAdmin" class="easyui-window " title="添加<spring:message code="all.trainingRoom.labroom" />管理员" align="left" title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 600px; height: 500px;">
		<div class="content-box">
		<form id="userForm" method="post" >
			<table class="tb" id="my_show">
				<tr>
					<td>姓名：<input id="cname"/></td>
					<td>工号：<input id="username"/>
						<a onclick="queryUser();" >搜索</a>	
						<a class="cancel-submit" onclick="cancleQuery();" >取消</a>
					</td>
					<td>
						<input type="button" value="添加" onclick="addUser();">
					</td>
				</tr>
			</table>
		<form>
		
		<table id="my_show">
					<thead>
						<tr>
							<th style="width:10% !important">选择</th>
							<th style="width:30% !important">姓名</th>
							<th style="width:30% !important">工号</th>
							<th style="width:30% !important">所属学院</th>
							
						</tr>
					</thead>
											
					<tbody id="user_body">
											
					</tbody>					
		</table>
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
	</script>
	<!-- 下拉框的js -->
</body>
</html>
