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
	
function cancelQuery(){
	window.location.href="${pageContext.request.contextPath}/device/allLendableDeviceList?page=1";
}	
function targetUrl(url)
{
  window.location.href=url;
}
function cancelQuery(){
	window.location.href="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1";
}
</script>

</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)">实验设备管理</a></li>
						<li class="end"><a href="javascript:void(0)">设备借用管理</a></li>
					</ul>
				</div>
			</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		</li>
		<li class="TabbedPanelsTab selected" tabindex="0">
		<a href="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1">可借用设备列表</a>
		</li>
		<li class="TabbedPanelsTab " tabindex="0">
		<a href="${pageContext.request.contextPath}/device/allDeviceLendList?page=1">所有申请</a>
		</li>
		<li class="TabbedPanelsTab " tabindex="0">
		<a href="${pageContext.request.contextPath}/device/deviceLendList?page=1">提交待审核</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="${pageContext.request.contextPath}/device/passDeviceLendList?page=1">审核通过</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="${pageContext.request.contextPath}/device/rejectedDeviceLendList?page=1">审核拒绝</a>
		</li>
		
	</ul>
	<div class="TabbedPanelsTabGroup-box">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
		<div class="tool-box">
		     <form name="queryForm" action="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1" method="post" modelAttribute="reservation">
				<table class="list_form">
					<tr><%--
						<td>
							学期：
							<form:select path="">
							<form:options items="${terms}" itemLabel="termName" itemValue="id"/>
							</form:select>
							
						</td>
						--%>
						<td>
						设备名称:<input type="text" id="deviceName" name="deviceName" value="${deviceName}"/>
						</td>
						<td>
							<input type="submit" value="查询">
							<input type="button" value="取消" onclick="cancelQuery();">
						</td>
					</tr >
			</table></form>	       
		   </div>
    	<div class="content-box">
    		<div class="title">
    			<div id="title">可借用设备列表</div>
    		</div>
            <table class="tb" id="my_show"> 
                <thead>
                    <tr>
                    	<th>设备编号</th>
                        <th>设备名称</th>
                        <th>设备型号</th>
                        <th>实验室名称</th>
                        <th>设备管理员</th>
                        <th>操作</th>
                    </tr>
                </thead>
                
                <tbody>
                		<c:forEach items="${allLendableDeviceList}" var="curr">
                		<tr>
                        <td>${curr.schoolDevice.deviceNumber}</td>
                        <td>${curr.schoolDevice.deviceName}</td>
                        <td>${curr.schoolDevice.devicePattern}</td>
                        <td>${curr.labRoom.labRoomName}</td>
                        <td>${curr.user.cname}</td>
                        <td><a class="btn btn-common" href='${pageContext.request.contextPath}/device/deviceLendApply?idKey=${curr.id}'>申请借用</a></td>
                        </tr>
                		</c:forEach>
                       
                </tbody>
            </table>
            <!-- 分页模块 -->
<div class="page" >
    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页		    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=${pageModel.lastPage}')" target="_self">末页</a>
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


