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
	  <a class="btn btn-new" href="javascript:void(0)" onclick="window.history.go(-1)" >返回</a>
	  <%-- <a class="btn btn-new"  href="${pageContext.request.contextPath }/findSelectStudentByUser?currpage=1&themeId=${themeId }" >下一步</a> --%>
	  <a class="btn btn-new"  href="javaScript:void(0)" onclick="checkNoExpectNumber()">下一步</a>
	</div>
	<div class="tool-box">
		<form:form name="queryForm" method="post" action="${pageContext.request.contextPath}/editChoseTheme?currpage=1&themeId=${themeId }" modelAttribute="choseProfessor">
			 <ul>
			 	<li>
			 		用户姓名:<form:input path="user.cname"/> 
			 		        <form:hidden path="choseTheme.id"/>
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
	    <th>导师编号</th>
	    <th>导师姓名</th>
	    <th>期望学生数量</th>
	    <!-- <th>最终数量</th> -->
	    <th>类型</th>
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${choseProfessorList}" var="curr" varStatus="i">
	  <tr>
	    <td>${(pageModel.currpage-1)*pageSize+i.count }</td>
	    <td>${curr.user.username}</td>
	    <td>${curr.user.cname}</td>
	    <td>${curr.expectNumber}</td>
	   <%--  <td>${curr.finalNumber}</td> --%>
	    <td>${curr.type==1?"导师互选":"毕业论文互选"}</td>
	    <td>
	    	<c:if test="${curr.expectNumber eq null }">
	    		<input class="btn_blue" type="button" value="修改" onclick="addExpectNumber(${curr.id},${curr.choseTheme.maxStudent })"/>
	    	</c:if>
	    	<c:if test="${curr.expectNumber ne null }">
	    		<input class="btn_blue" type="button" value="修改" onclick="addExpectNumber(${curr.id},${curr.choseTheme.maxStudent },${curr.expectNumber})"/>
	    	</c:if>
	    	<input class="btn_blue" type="button" value="删除" onclick="deleteChoseProfessorForAdmin(${curr.id})"/>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
    <div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseProfessorList?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseProfessorList?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="targetUrl(this.options[this.selectedIndex].value);">
	<option value="${pageContext.request.contextPath}/choseProfessorList?currpage=${pageModel.currpage}&termId=${choseTheme.id }">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/choseProfessorList?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseProfessorList?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseProfessorList?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
  </div>
  </div>
  </div>
  </div>
  <div id="addExpectNumber" class="easyui-window " title="填写期望学生数量" align="left" title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 600px; height: 500px;">
		<div class="content-box">
		  <form id="userForm" method="post" > 
			 
				<tr>
					<td>数量：<input id="expectNumber"/></td>
					</td> 
					<td>
						<input type="button" value="添加" onclick="addUser();">
						<input type="button" value="取消" onclick="window.history.go(0)">
					</td>
				</tr>
		</form>  
		
			</div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
    function queryUser(){
		var cname=document.getElementById("cname").value;
		var username=document.getElementById("username").value;
		$.ajax({
		           url:"${pageContext.request.contextPath}/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&currpage=1",
		           type:"POST",
		           success:function(data){//AJAX查询成功
		                  document.getElementById("user_body").innerHTML=data;
		           }
		});
    }
	function addExpectNumber(professorId,maxStudent,expectNumber){
		var content=prompt('请输入',expectNumber);
	    if(content!=null && content!=""){
	    	if(content<=0){
	    		alert("最小为1");
	    	}
	    	else{
	    		if(content<=maxStudent){
	    		  $.ajax({
	    		  url:"${pageContext.request.contextPath}/nwuChose/saveExpectNumberForAdmin?id="+professorId+"&expectNumber="+content,
	    		  type:"post",
	    		  success:function(){
	    		  window.location.reload();
	    		  }
	    	    });
	    	   }
	    	   else{
	    		alert("超过最大的学生数量");
	    	   }
	    	}
	    }
	}
	//检查是否有未填期望的学生数量
	function checkNoExpectNumber(){
		$.ajax({
			url:"${pageContext.request.contextPath}/nwuChose/findNoExpectNumberBythemeId?themeId=${themeId}",
			type:"post",
			success:function(result){
				if(result=="success"){
					window.location.href="${pageContext.request.contextPath}/nwuChose/newChoseThemeFourStep?id=${themeId}";
				}else{
					alert("必须填写完所有导师期望学生数量");
				}
			}
		});
	}
	function deleteChoseProfessorForAdmin(professorId){
		var flag=confirm("确定要删除吗？");
			if(flag){
				window.location.href="${pageContext.request.contextPath}/nwuChose/deleteChoseProfessorForAdmin?professorId="+professorId;
			}
	}
	</script>
	<!-- 下拉框的js -->
</body>
</html>
