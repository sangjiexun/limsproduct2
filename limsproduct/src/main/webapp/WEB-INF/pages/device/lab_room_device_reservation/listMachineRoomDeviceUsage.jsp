<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
<meta name="decorator" content="iframe"/>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式 -->	
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

<script type="text/javascript">

function targetUrl(url)
{
  window.location.href=url;
}
//取消查询
function cancel()
{
	  window.location.href="${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=1";
}
</script>

</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)">机房设备使用记录</a></li>
					</ul>
				</div>
			</div>
<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">			
				<li class="TabbedPanelsTab" id="s1"><a
					class="iStyle_Feelings_Tree_Leaf"
					href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=9">&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="all.trainingRoom.labroom"/>列表</a>
				</li>
			<sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_LABMANAGER" >
				<li class="TabbedPanelsTab selected" id="s2"><a
					class="iStyle_Feelings_Tree_Leaf"
					href="${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=1">&nbsp;&nbsp;&nbsp;&nbsp;机房电脑使用记录</a>
				</li>
			</sec:authorize>
		</ul>
	</div>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsTabGroup-box">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
    	<div class="content-box">
    		<div class="title">
    			<div id="title">机房电脑使用列表</div>
    		</div>
    		<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=1" method="post" modelAttribute="viewUseOfLc">
			 <ul>
  				<li>机器名称： </li>
  				<li><form:input id="machinename" path="machinename"/></li>
  				<li>使用人： </li>
  				<li>
  				<form:select path="username" id="username">
					<form:option value=""></form:option>
					<c:forEach items="${allViewUseOfLcLists}" var="curr" varStatus="a">
					    <form:option value="${curr.username}">${listUser[a.count-1].cname}</form:option>
					</c:forEach>
				</form:select>
  				</li>
  				<li>使用时间：</li>
  				<li>
				<li>
				<form:select path="" id="begintime" name="begintime">
					<form:option value=""></form:option>
					<c:forEach items="${allViewUseOfLcLists}" var="curr">
					    <form:option value="${curr.begintime.time}"><fmt:formatDate value="${curr.begintime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></form:option>
					</c:forEach>
				</form:select>
				</li>
				<li>到</li>
				<li>
				<form:select path="" id="endtime" name="endtime">
					<form:option value=""></form:option>
					<c:forEach items="${allViewUseOfLcLists}" var="curr">
					    <form:option value="${curr.endtime.time}"><fmt:formatDate value="${curr.endtime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></form:option>
					</c:forEach>
				</form:select>
				</li>
				</li>
				<li><spring:message code="all.trainingRoom.labroom" />：</li>
				<li>
				<form:select path="labRoomName" id="labRoomName">
					<form:option value=""></form:option>
					<c:forEach items="${allViewUseOfLcLists}" var="curr">
					    <form:option value="${curr.labRoomName}">${curr.labRoomName}</form:option>
					</c:forEach>
				</form:select></li>
				<li>
			      <input type="button" value="取消" onclick="cancel();"/><input type="submit" value="查询"/></li>
  				</ul>
			 
		</form:form>
	</div>
            <table class="tb" id="my_show"> 
                <thead>
                    <tr>
                    	<th>机器名称</th>
                        <th>使用人</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>所在机房</th>
                    </tr>
                </thead>
                
                <tbody>
                		<c:forEach items="${allViewUseOfLcList}" var="curr" varStatus="a">
                		<tr>
                        <td>${curr.machinename}</td>
                        <td>${listUser[a.count-1].cname}</td>
                        <td><fmt:formatDate value="${curr.begintime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${curr.endtime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${curr.labRoomName}</td>
                        </tr>
                		</c:forEach>
                       
                </tbody>
            </table>
            <!-- 分页模块 -->
<div class="page" >
    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页		    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.lastPage}')" target="_self">末页</a>
</div>
<!-- 分页模块 -->
</div>

</div>
</div>
</div>
</div>
</div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
      '.chzn-select'           : {search_contains:true},
      '.chzn-select-deselect'  : {allow_single_deselect:true},
      '.chzn-select-no-single' : {disable_search_threshold:10},
      '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
    
</script>
<!-- 下拉框的js --> 
</body>
</html>


