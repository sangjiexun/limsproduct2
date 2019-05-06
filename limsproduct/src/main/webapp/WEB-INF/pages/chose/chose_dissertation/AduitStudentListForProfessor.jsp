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
	    <li><a href="javascript:void(0)">导师论文互选</a></li>
		<li class="end"><a href="javascript:void(0)">导师论文互选列表</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
    <%--<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s0"><a href="${pageContext.request.contextPath}/nwuChose/ChoseThemeList?currpage=1">全部</a></li>
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=1&orderBy=${orderBy }">草稿</a></li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=2&orderBy=${orderBy }">审核中</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=3&orderBy=${orderBy }">审核通过</a></li>
		<li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=4&orderBy=${orderBy }">审核拒绝</a></li>
	</ul>
  --%><div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  
  <div class="content-box">
    <div class="title">
	  <div id="title">导师论文互选列表</div>
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/choseDissertation/disserationListForProfessor?batchId=${batchId }&currpage=1&themeId=${themeId}">返回</a>
	</div>
	<div class="tool-box">
	</div>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	   <th>学号</th>
	   <th>姓名</th>
	   <th>班级</th>
	   <c:if test="${flag==1}">
	   	 <th>操作</th>
	   </c:if>
	   
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${choseDissertationRecordList}" var="curr" varStatus="i">
	  <tr>
	  	<input type="hidden" value="${curr.id}" name="recordId"/>
	    <td name="username">${curr.user.username}</td>
	    <td>${curr.user.cname}</td>
	    <td>${curr.user.schoolClasses.className}</td>
	    <td>
	   	  	<%-- <input type="radion" name="state" value="${pageContext.request.contextPath}/saveAduitResult?recordId=${curr.id}&aduitResult=1 "/>--%>
	    	<input type="radio" name="state"/>
	    </td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<a class="btn btn-new" href="javascript:void(0)" onclick="sub()">提交</a>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/professorAduitListForCD?currpage=1&batchId=${batchId }')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/professorAduitListForCD?currpage=${pageModel.previousPage}&batchId=${batchId }')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/choseDissertation/professorAduitListForCD?currpage=${pageModel.currpage}&batchId=${batchId}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/choseDissertation/professorAduitListForCD?currpage=${j.index}&batchId=${batchId}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/professorAduitListForCD?currpage=${pageModel.nextPage}&batchId=${batchId }')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/professorAduitListForCD?currpage=${pageModel.lastPage}&batchId=${batchId }')" target="_self">末页</a>
    </div>
	
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		function sub(){
			var radio = document.getElementsByName("state");
			var usernames = document.getElementsByName("username");
			var recordId = document.getElementsByName("recordId"); 
			var recordId;
			var username;
            for (var i=0; i<radio.length; i++){  
	            if (radio[i].checked) {  
	             	recordId=recordId[i].value;
	             	username=usernames[i].innerHTML;
	            }
	           
            } 
            if(recordId==null ||recordId==''){
            	alert("至少选择一条");
            }
            else{
            	 window.location.href="${pageContext.request.contextPath}/choseDissertation/saveAduitResult?batchId=${batchId}&recordId="+recordId+"&username="+username;  
            }
		}
	</script>
</body>
</html>
