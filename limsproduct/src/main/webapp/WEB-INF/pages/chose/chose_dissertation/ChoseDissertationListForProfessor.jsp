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
	  <div id="title">导师论文互选列表</div>
	  	<a class="btn btn-new" href="${pageContext.request.contextPath}/choseDissertation/choseThemeListForProfessor?currpage=1">返回</a>
	  </div>
	
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/nwuChose/ChoseThemeListForProfessor?currpage=1" method="post" modelAttribute="choseTheme"><%--
			 <ul>
  				<li>实验项目名称： </li>
  				<li><form:input id="lp_name" path="lpName"/></li>
  				<li>学期：</li>
  				<li>
  				  <form:select path="schoolTerm.id" id="term_id">
  				    <form:option value="${schoolTerm.id }">${schoolTerm.termName }</form:option>
  				    <c:forEach items="${schoolTerms}" var="curr">
  				    	<c:if test="${curr.id ne schoolTerm.id }">
	  				    	<form:option value="${curr.id }">${curr.termName }</form:option>
	  				    </c:if>
  				    </c:forEach>
  				  </form:select>
  				</li>
  				<li>所属课程：</li>
  				<li>
	  			    <form:select path="schoolCourseInfo.courseNumber"  id="course_number" class="chzn-select">
	  				    <form:option value="">请选择</form:option>
	  				    <c:forEach items="${schoolCourseInfos}" var="sc">
	  				    	<form:option value="${sc.key}">[${sc.key}]${sc.value }</form:option>
	  				    </c:forEach>
	  				</form:select>
  				</li>
  				<li><input type="submit" value="查询"/>
			      <input type="button" value="取消" onclick="cancel();"/></li>
  				</ul>
			 
		--%></form:form>
	</div>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	   <th>编号</th>
	   <th>题目</th>
	   <th>选报人数</th>
	   <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${list}" var="curr" varStatus="i">
	  <tr>
	  	<td>${i.index+1}</td>
	  	<td>${curr[1]}</td>
	  	<td>${curr[3]}</td>
	  	<input type="hidden" value="${curr[2] }" name="student"/>
	  	<input type="hidden" value="${curr[4] }" name="dissertationId"/>
	  	<input type="hidden" value="${curr[5] }" name="aduitResult"/>
	    <td name="operator">
	    </td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/disserationListForProfessor?currpage=1&batchId=${batchId }')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/disserationListForProfessor?currpage=${pageModel.previousPage}&batchId=${batchId }')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/choseDissertation/disserationListForProfessor?currpage=${pageModel.currpage}&batchId=${batchId}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/choseDissertation/disserationListForProfessor?currpage=${j.index}&batchId=${batchId}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/disserationListForProfessor?currpage=${pageModel.nextPage}&batchId=${batchId }')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/disserationListForProfessor?currpage=${pageModel.lastPage}&batchId=${batchId }')" target="_self">末页</a>
    </div>
    
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function(){
			var students=$("input[name='student']");
			var dissertationIds=$("input[name='dissertationId']");
			var oper=$("td[name='operator']");
			
			for(var i=0;i<students.length;i++){
				//alert(students.eq(i).val());
				if(students.eq(i).val()==''){
					 var content='<a href="${pageContext.request.contextPath}/choseDissertation/professorAduitListForCD?batchId=${batchId}&themeId=${themeId}&currpage=1&dissertationId='+dissertationIds.eq(i).val()+'">我的审核</a>';
		    		 oper.eq(i).html(content);
				}
				else{
					var content='<a href="${pageContext.request.contextPath}/choseDissertation/findDissertationToStudent?batchId=${batchId}&themeId=${themeId}&dissertationId='+dissertationIds.eq(i).val()+'">审核结果</a>';
		    		 oper.eq(i).html(content);
				}
			}
		})
	</script>
	<!-- 下拉框的js -->
</body>
</html>
