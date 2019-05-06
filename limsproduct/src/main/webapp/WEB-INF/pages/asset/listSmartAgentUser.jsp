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
		           <%--url:encodeURI(encodeURI("${pageContext.request.contextPath}/asset/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&page=1")),--%>
		           url:"${pageContext.request.contextPath}/asset/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&page=1",
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
		           <%--url:encodeURI(encodeURI("${pageContext.request.contextPath}/asset/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&page=1")),--%>
		           url:"${pageContext.request.contextPath}/asset/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&page=1",
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
  function addUser(){
      var array=new Array();
      var flag; //判断是否一个未选   
      $("input[name='CK_name']:checkbox").each(function() { //遍历所有的name为CK_name的 checkbox  
                  if ($(this).attr("checked")) { //判断是否选中    
                      flag = true; //只要有一个被选择 设置为 true  
                  }  
              })  

      if (flag) {  
         $("input[name='CK_name']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox  
                      if ($(this).attr("checked")) { //判断是否选中
                          array.push($(this).val()); //将选中的值 添加到 array中 
                      }  
                  })  
         //将要所有要添加的数据传给后台处理   
		   window.location.href="${pageContext.request.contextPath}/asset/saveSmartAgentUser?id=${id}&array="+array; 
      } else {   
      	alert("请至少选择一条记录");  
      	}  
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
	    <li><a href="javascript:void(0)">电源控制器</a></li>
		<li class="end"><a href="javascript:void(0)">电源控制器授权用户</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
	  <div id="title">电源控制器授权用户</div>
	  <a class="btn btn-new" href="javascript:void(0)" onclick="authorize();">添加</a>
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/asset/listSmartAgents">返回</a>
	</div>
	
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/asset/listSmartAgents" method="post" modelAttribute="SmartAgent">
	
		</form:form>
	</div>
	 
	<table class="tb" id="my_show">
	  <thead>
	  <tr> 
	  	<th>用户名</th>
	    <th>名字</th> 
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${userList}" var="curr" varStatus="i">
	  <tr> 
	 	<td>${curr.username}</td>
	    <td>${curr.cname}</td> 
	    <td>
	        <a href="${pageContext.request.contextPath}/asset/deleteSmartAgentUser?serialNo=${id}&id=${curr.id}" onclick="return confirm('确定删除？');">删除</a>
	        </td>
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
						<a onclick="cancleQuery();" >取消</a>
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
