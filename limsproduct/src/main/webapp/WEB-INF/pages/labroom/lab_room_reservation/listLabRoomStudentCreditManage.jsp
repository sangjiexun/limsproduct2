<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>


<html>
<head>
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<meta name="decorator" content="iframe"/>
 <script type="text/javascript">
	//跳转
	function targetUrl(url)
	{
	  document.queryForm.action=url;
	  document.queryForm.submit();
	}	
	function exportSelect(){
		window.location.href="${pageContext.request.contextPath}/labRoom/initializeStudentCredit?page=1";
	}
	function cancle(){
		window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoomStudentCreditManage?page=1";
	}
 </script> 
</head>
<body>

<div class="navigation">
<div id="navigation">
	<ul>
	<li><a href="javascript:void(0)">实验预约管理</a></li>
	<li><a class="end">信誉积分管理</a></li>
	</ul>
</div>
</div>
<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_PRESECTEACHING" >
<div class="right-content">
<%--<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<div class="title">
<div id="title">信誉积分管理列表</div>
</div>
--%>
<div id="TabbedPanels1" class="TabbedPanels1">
<%--<div class="title">--%>
<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_PRESECTEACHING" >--%>
		<%--<a class="btn btn-new" onclick="exportSelect();">一键初始化</a>--%>
		<%--</sec:authorize>--%>
<%--</div>--%>
   <ul class="TabbedPanelsTabGroup">
		</li>
		<li class="TabbedPanelsTab " tabindex="0">
		<a href="${pageContext.request.contextPath}/labRoom/listLabRoomTeacherCreditManage?page=1">教师</a>
		</li>
		<li class="TabbedPanelsTab selected " tabindex="0">
		<a href="${pageContext.request.contextPath}/labRoom/listLabRoomStudentCreditManage?page=1">学生</a>
		</li>
	   <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_PRESECTEACHING" >
		   <a class="btn btn-new" onclick="exportSelect();">一键初始化</a>
	   </sec:authorize>
	</ul>

<%--<div class="TabbedPanelsTabGroup-box">--%>
<div class="">
	<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">

<div id="TabbedPanels1" class="TabbedPanels1">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">	
  <div class="tool-box">
<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/listLabRoomStudentCreditManage?page=1" method="post" modelAttribute="user">
			<ul>
  				<li>学号:
  				  <form:input path="username" id="username" value="${username}"/>
  				</li>
  				<li>姓名:
  				  <form:input path="cname" id="cname" value="${cname}"/>
  				</li>
  				<li><input type="submit" value="查询"/>
					<input class="cancel-submit" type="button" onclick="cancle();" value="取消查询"/></li>
  			</ul>
</form:form>	
</div>
</div>
</div>
</div>
</div>
	<div class="content-box">
		<table>
			<thead>
			<tr>
				<!--  <th>选择</th> -->
				<th width="15%">序号</th>
				<th width="15%">学号</th>
				<th  width="10%" >姓名</th>
				<th  width="10%">信誉积分</th>
				<th>操作</th>
			</tr>
			</thead>
			<tbody>
<c:forEach items="${users}" var="current" varStatus="i">
				
				<tr>
					<td>${i.count+pageSize*(page-1)}</td>
					<td>${current.username}</td>
					<td>
							${current.cname}
					</td>
					<td>
							${current.creditScore}
					</td>
					<td>
						<a href="${pageContext.request.contextPath}/labRoom/modifyCredit?username=${current.username}">修改</a>
						<a href="${pageContext.request.contextPath}/labRoom/creditRecord?username=${current.username}">扣分记录</a>
					</td>
				</tr>

</c:forEach> 

</tbody>
</table>
    <div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoomStudentCreditManage?page=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoomStudentCreditManage?page=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/labRoom/listLabRoomStudentCreditManage?page=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/labRoom/listLabRoomStudentCreditManage?page=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoomStudentCreditManage?page=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoomStudentCreditManage?page=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
  </div>
  </div>
  </div>
  </div>
</div>
</div>

</sec:authorize>
</div>
</div>
</div>
</body>
</html>