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
	   function targetUrl(url)
		  {
		    document.queryForm.action=url;
		    document.queryForm.submit();
		  }
		  //取消查询
		  function cancel()
		  {
		    window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=${orderBy }";
		  }
	    
   	});
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
	  <div id="title">添加学生</div>
	 </div>
	<div class="tool-box">
		<form:form name="queryForm" method="post" modelAttribute="user" action="${pageContext.request.contextPath}/queryOtherBatchStudent?currpage=1"> 
			 <table class="tb" id="my_show">
				<tr>
					<td>学号：<form:input  path="username"/>
					<td>姓名：<form:input  path="cname"/></td>
					<form:hidden path="attendanceTime"/>
					<input type="submit" value="搜索">
					</td> 
					<td>
						<input type="button" value="添加" onclick="addStudent();"/>
						<input type="button" value="取消" onclick="window.history.go(0)"/>
					</td>
				</tr>
			</table> 
		</form:form>  
	</div>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th style="width:10% !important">选择</th>
		<th style="width:30% !important">学号</th>
		<th style="width:30% !important">姓名</th>
		<th style="width:30% !important">所属学院</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${studentList}" var="curr">
	  <tr>
	    <td><input type='checkbox' name='CK_name' value="${curr.username }"/></td>
	    <td>${curr.username}</td>
	    <td>${curr.cname}</td>
	    <td>${curr.schoolAcademy.academyName}</td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
    <div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/queryOtherBatchStudent?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/queryOtherBatchStudent?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="targetUrl(this.options[this.selectedIndex].value);">
	<option value="${pageContext.request.contextPath}/queryOtherBatchStudent?currpage=${pageModel.currpage}&termId=${choseTheme.id }">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/queryOtherBatchStudent?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/queryOtherBatchStudent?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/queryOtherBatchStudent?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>  
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		 //添加非本届学生
	    function addStudent(){
          var array=new Array();
          var flags; //判断是否一个未选   
	      $("input[name='CK_name']:checkbox:checked").each(function() {//遍历所有被选中的name为CK_name的 checkbox
	        flags = true; //只要有一个被选择 设置为 true 
		  });  
          if(flags){  
            $("input[name='CK_name']:checkbox:checked").each(function() { //遍历所有被选中的name为selectFlag的 checkbox  
               array.push($(this).val()); //将选中的值 添加到 array中 
            });  
           //将要所有要添加的数据传给后台处理   
		   window.location.href="${pageContext.request.contextPath}/saveOtherBatchStudent?realAttendanceTime=${realAttendanceTime}&array="+array;
          } 
         else 
         {   
        	alert("请至少选择一条记录");  
         }  
   	 } 
	</script>
	<!-- 下拉框的js -->
</body>
</html>
