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
  $(document).ready(function(){
	});
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=${orderBy }";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  </script>
<script type="text/javascript">
</script>	

</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">导师互选</a></li>
		<li class="end"><a href="javascript:void(0)">备选导师列表</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
    <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  
  <div class="content-box">
    <div class="title">
	  <div id="title">导师互选列表</div>
	  	<a class="btn btn-new" href="javascript:void(0);" onclick="addStudent()">添加</a>
	  	<a class="btn btn-new" href="${pageContext.request.contextPath }/nwuChose/ChoseThemeList?currpage=1" >返回</a>
		<%-- <a  class="btn btn-new" href="${pageContext.request.contextPath }/releaseChoseTheme?themeId=${themeId}">发布</a> --%>
		<a  class="btn btn-new" href="javaScript:void(0)" onclick="check(${themeId})">发布</a>
	</div>
	<div class="tool-box">
		<form:form name="queryForm" method="post" action="${pageContext.request.contextPath}/nwuChose/findSelectStudentByUser?currpage=1&flag=${flag}&attendanceTime=${attendanceTime}&themeId=${themeId}" modelAttribute="user">
			 <ul>
			 	<li>
			 		姓名:<form:input path="cname"/> 
			 		<%--<form:hidden path="attendanceTime" value="${attendanceTime}"/>--%>
			 	</li>
  				<li>
			    	<input type="submit" value="查询">
			    </li>
  			</ul>
		</form:form>
	</div>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>编号</th>
	    <th>学生编号</th>
	    <th>学生姓名</th>
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${studentList}" var="curr" varStatus="i">
	  <tr>
	    <td>${(pageModel.currpage-1)*pageSize+i.count }</td>
	    <td>${curr.username}</td>
	    <td>${curr.cname}</td>
	    <td>
	    
	    	<c:if test="${curr.choseUser.realAttendanceTime ne null }">
	    		<a href="${pageContext.request.contextPath }/nwuChose/deleteOtherBatchStudent?username=${curr.username}&attendanceTime=${attendanceTime}&themeId=${themeId}" onclick="return confirm('确定删除？');">删除</a>
	    	</c:if>
	    </td>
	   
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
    <div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/findSelectStudentByUser?currpage=1&flag=${flag}&attendanceTime=${attendanceTime}&themeId=${themeId}')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/findSelectStudentByUser?currpage=${pageModel.previousPage}&flag=${flag}&attendanceTime=${attendanceTime}&themeId=${themeId}')" target="_self">上一页</a>
	第<select onchange="targetUrl(this.options[this.selectedIndex].value);">
	<option value="${pageContext.request.contextPath}/nwuChose/findSelectStudentByUser?currpage=${pageModel.currpage}&termId=${choseTheme.id }&flag=${flag}&attendanceTime=${attendanceTime}&themeId=${themeId}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/nwuChose/findSelectStudentByUser?currpage=${j.index}&flag=${flag}&attendanceTime=${attendanceTime}&themeId=${themeId}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/findSelectStudentByUser?currpage=${pageModel.nextPage}&flag=${flag}&attendanceTime=${attendanceTime}&themeId=${themeId}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/findSelectStudentByUser?currpage=${pageModel.lastPage}&flag=${flag}&attendanceTime=${attendanceTime}&themeId=${themeId}')" target="_self">末页</a>
    </div>
  </div>
  </div>
  </div>
  </div>
  
  <div id="addStudent" class="easyui-window " title="添加学生" align="left" title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 600px; height: 500px;">
		<div class="content-box">
		  <form id="userForm" method="post"> 
			 <table class="tb" id="my_show">
				<tr>
					<td>姓名：<input id="Cname"/></td>
					<td>工号：<input id="Username"/>
					<a onclick="queryUser()" >搜索</a>	
					</td> 
					<td>
						<input type="button" value="添加" onclick="saveStudent();">
						<input type="button" value="取消" onclick="window.history.go(0)">
						
					</td>
				</tr>
			</table> 
		</form>  
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
	    /**
		添加学生
		*/
		function addStudent(){
		   var cname=document.getElementById("Cname").value;
		   var username=document.getElementById("Username").value;
			$.ajax({
		        url:"${pageContext.request.contextPath}/nwuChose/findOtherBatchStudentByuser?cname="+cname+"&username="+username+"&currpage=1&attendanceTime=${attendanceTime}",
		        type:"POST",
		        success:function(data){//AJAX查询成功
		        		document.getElementById("user_body").innerHTML=data;
		        }
			});
		    $("#addStudent").show();
		    $("#addStudent").window('open');   
		    
		 }
		 //首页
		function firstPage(page){
		 var cname=document.getElementById("Cname").value;
		 var username=document.getElementById("Username").value;
			$.ajax({
		           url:"${pageContext.request.contextPath}/nwuChose/findOtherBatchStudentByuser?cname="+cname+"&username="+username+"&currpage=1&attendanceTime=${attendanceTime}",
		           type:"POST",
		           success:function(data){//AJAX查询成功
		                  document.getElementById("user_body").innerHTML=data;
		            
		           }
			});
		}
		 //上一页
		function previousPage(page){
		 var cname=document.getElementById("Cname").value;
		 var username=document.getElementById("Username").value;
			if(page==1){
					page=1;
				}else{
					page=page-1;
				}	
			$.ajax({
		          url:"${pageContext.request.contextPath}/nwuChose/findOtherBatchStudentByuser?cname="+cname+"&username="+username+"&currpage="+page+"&attendanceTime=${attendanceTime}",
		           type:"POST",
		           success:function(data){//AJAX查询成功
		                  document.getElementById("user_body").innerHTML=data;
		            
		           }
			});
		}
		//下一页
		function nextPage(page,totalPage){
		 var cname=document.getElementById("Cname").value;
		 var username=document.getElementById("Username").value;
			if(page>=totalPage){
					page=totalPage;
				}else{
					page=page+1
				}	
		
			$.ajax({
		           url:"${pageContext.request.contextPath}/nwuChose/findOtherBatchStudentByuser?cname="+cname+"&username="+username+"&currpage="+page+"&attendanceTime=${attendanceTime}",
		           type:"POST",
		           type:"POST",
		           success:function(data){//AJAX查询成功
		                  document.getElementById("user_body").innerHTML=data;
		           }
			});
		}
		//末页
		function lastPage(page){
		var cname=document.getElementById("Cname").value;
		var username=document.getElementById("Username").value;
			$.ajax({
		           url:"${pageContext.request.contextPath}/nwuChose/findOtherBatchStudentByuser?cname="+cname+"&username="+username+"&currpage="+page+"&attendanceTime=${attendanceTime}",
		           type:"POST",
		           type:"POST",
		           success:function(data){//AJAX查询成功
		                  document.getElementById("user_body").innerHTML=data;
		            
		           }
			});
		}
	  function saveStudent(){
        var array=new Array();
        var flags; //判断是否一个未选   
		$("input[name='CK_name']:checkbox:checked").each(function() {//遍历所有被选中的name为CK_name的 checkbox
	        flags = true; //只要有一个被选择 设置为 true 
		});  
        if (flags) {  
           $("input[name='CK_name']:checkbox:checked").each(function() { //遍历所有被选中的name为selectFlag的 checkbox  
               array.push($(this).val()); //将选中的值 添加到 array中 
           });  
           //将要所有要添加的数据传给后台处理   
		   window.location.href="${pageContext.request.contextPath}/nwuChose/saveOtherBatchStudent?array="+array+"&attendanceTime=${attendanceTime}&themeId=${themeId}";
        } else {   
        	alert("请至少选择一条记录");  
        	}  
   	} 
    function queryUser(){
		var cname=document.getElementById("Cname").value;
		var username=document.getElementById("Username").value;
		$.ajax({
	           url:"${pageContext.request.contextPath}/nwuChose/findOtherBatchStudentByuser?cname="+cname+"&username="+username+"&currpage=1&attendanceTime=${attendanceTime}",
	           type:"POST",
	           success:function(data){//AJAX查询成功
	                  document.getElementById("user_body").innerHTML=data;
	           }
		});
    } 
    //发布前判断导师互选大纲的注意事项是否存在
   function check(themeId){
   		$.ajax({
   			url:"${pageContext.request.contextPath}/nwuChose/checkIFRelease?themeId="+themeId,
   			type:"post",
   			success:function(result){
   				//存在-可以发布
   				if(result=="success"){
   					window.location.href="${pageContext.request.contextPath}/nwuChose/releaseChoseTheme?themeId=${themeId}&studentNumber=${pageModel.totalRecords}";
   				}
   				//不存在-不可以发布，需要去创建注意事项
   				else{
   					alert("导师互选大纲的注意事项不存在");
   				}
   			}
   		});
   		
    }
	</script>
	<!-- 下拉框的js -->
</body>
</html>
