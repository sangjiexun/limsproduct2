<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <script type="text/javascript">
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  

  </script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management" /></a></li>
		<li class="end"><a href="javascript:void(0)"><spring:message code="left.trainingTeam.management" /></a></li>
		  <li class="end"><a href="javascript:void(0)">详情</a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
	  <div id="title"><spring:message code="all.trainingRoom.labroom" />人员培训进修登记列表</div>
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/listLabWorker?currpage=1">返回</a>
	</div>

	<div id="showRegulations" class="easyui-window" closed="true" modal="true" minimizable="true" title="规章制度详情" style="width: 580px;height: 250px;padding: 20px">
	  <div class="content-box">
	    <table id="my_show">
				<tbody id="labRoom_body">
					
				</tbody>
		</table>
	  </div>
  	</div>
	
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	     <th>序号</th>
	     <th>培训人员</th>
	     <th>主办单位</th>
	     <th>开始时间</th>
	     <th>结束时间</th>
	     <th>成绩</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${listLabWorkerTraining}" var="curr">
	  <tr>
	    <td>${curr.id}</td>
		<td>${curr.labWorker.lwName}</td>
		<td>${curr.organizer}</td>
		<td><fmt:formatDate value="${curr.beginTime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td><fmt:formatDate value="${curr.endTime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		
		<td>${curr.score}</td>
	    <%-- <td>
	      <a href="${pageContext.request.contextPath}/labRoom/labWorkerTrainingDetail?flag=1&&labWorkerTrainingId=${curr.id}">查看</a>
	      <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER" >
	      <a href="${pageContext.request.contextPath}/labRoom/labWorkerTrainingDetail?flag=0&&labWorkerTrainingId=${curr.id}">编辑</a>
	      </sec:authorize>
	      <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER" >
	      <a href="${pageContext.request.contextPath}/labRoom/deleteLabWorkerTraining?labWorkerTrainingId=${curr.id}" onclick="return confirm('确定删除？');">删除</a>
	      </sec:authorize>
	    </td> --%>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<!-- 分页[s] -->
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabWorkerTraining?currpage=1&labWorkerId=${labWorkerId}')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabWorkerTraining?currpage=${pageModel.previousPage}&labWorkerId=${labWorkerId}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/labRoom/listLabWorkerTraining?currpage=${pageModel.currpage}&labWorkerId=${labWorkerId}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/labRoom/listLabWorkerTraining?currpage=${j.index}&labWorkerId=${labWorkerId}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabWorkerTraining?currpage=${pageModel.nextPage}&labWorkerId=${labWorkerId}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabWorkerTraining?currpage=${pageModel.lastPage}&labWorkerId=${labWorkerId}')" target="_self">末页</a>
    </div>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
