<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  <link href="${pageContext.request.contextPath}/css/showLab.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
  function cancel()
  {
	  window.location.href="${pageContext.request.contextPath}/public/listTimetable?currpage=1";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  function btnPrintClick(){  
      window.print();  
  } 
  </script>
</head>
  
<body>

  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <ul class="nav_ul cf">
		<c:if test="${flag eq 1}">
			<li class="nav_li">
				<a href="${pageContext.request.contextPath}/public/labreservationmanage?tage=0&currpage=1&flag=0">实验室预约查询</a>
			</li>
			<li class="nav_li li_selected">
				<a href="${pageContext.request.contextPath}/public/listTimetable?currpage=1">课表查询</a>
			</li>
		</c:if>
		<c:if test="${flag eq 0}">
			<li class="nav_li li_selected">
				<a href="${pageContext.request.contextPath}/public/labreservationmanage?tage=0&currpage=1&flag=0">实验室预约查询</a>
			</li>
			<li class="nav_li">
				<a href="${pageContext.request.contextPath}/public/listTimetable?currpage=1">课表查询</a>
			</li>
		</c:if>
	</ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">

	<div class="tool-box">
		<form name="queryForm" action="${pageContext.request.contextPath}/public/listTimetable?currpage=1" method="post">
			 <ul>
  				<li>实验室名称:</li>
  				<li><input id="labName" name="labName" value="${labName}"/></li>
  				<li>课程名称:</li>
  				<li><input id="courseName" name="courseName" value="${courseName}"/></li>
  				<li>班级:</li>
  				<li><input id="className" name="className" value="${className}"/></li>
  				<li>
  				  
			      <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
			      <input type="submit" value="查询"/>
			      </li>
  				</ul>
			 
		<form>
	</div>
	
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>实验室</th>
	    <th>课程</th>
	    <th>时间安排</th>
	    <th>班级</th>
	    <th>人数</th>	    
	    
	    <>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${listTimetable}" var="curr">
	  <tr>
	    <td>${curr[0]}</td>
	    <td>${curr[1]}</td>
	    <td>${curr[2]}</td>
	    <td>${curr[3]}</td>
	    <td>${curr[4]}</td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<!-- 分页[s] -->
<%-- 	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/public/listTimetable?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/public/listTimetable?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/public/listTimetable?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/public/listTimetable?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/public/listTimetable?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/public/listTimetable?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div> --%>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
    <script type="text/javascript">
  		
            function refresh() 
			{ 
				window.location.reload(); 
			} 
			setTimeout('refresh()',5*60*1000);
  </script>
</body>
</html>