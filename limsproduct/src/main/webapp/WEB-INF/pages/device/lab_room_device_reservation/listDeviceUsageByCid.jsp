<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="decorator" content="iframe"/>
	
	<!-- 下拉的样式 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
	<!-- 下拉的样式 -->	
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/jquery-1.8.2.min.js"></script>
	<!-- 打印插件的引用 -->
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/common/sortTable.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.tablesorter.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
		
</head>
<body>
	<div class="title">
		<div id="title">设备列表</div>
	</div> 

	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/device/listDeviceUsageByCid?currpage=1" method="post" modelAttribute="labRoomDevice">
			 <ul>
  				<li>学期:
  					<select class="chzn-select" multiple="true" name="termMulti" style="width:165px">
  						<option value="">请选择</option>
	  					<c:forEach items="${schoolTerms }" var="curr">
	  					<c:set var="idString" value=",${curr.id },"></c:set>
		  					<c:if test="${fn:contains(termMulti, idString)}">
		  						<option value="${curr.id }" selected="selected">${curr.termName }</option>
		  					</c:if>	
		  					<c:if test="${fn:indexOf(termMulti, idString)==-1}">
		  						<option value="${curr.id }">${curr.termName }</option>
		  					</c:if>	
	  					</c:forEach>
  					</select>
  				</li>
  				
  				<li>设备名称:
  					<form:select path="schoolDevice.deviceNumber" class="chzn-select" style="width:150px">
				        <form:option value="">请选择</form:option>
				        <form:options items="${devices}" />
				    </form:select>
  				</li>
  				
  				<li>实验室:
				    <form:select path="labRoom.id" class="chzn-select" style="width:150px">
				        <form:option value="">请选择</form:option>
				        <form:options items="${labrooms}" />
				    </form:select>
  				</li>	
  				
  				<li>设备管理员:
				    <form:select path="user.username" class="chzn-select" style="width:150px">
					    <form:option value="">请选择</form:option>
					    <c:forEach var="manageUser" items="${manageUsers}"> 
					    	<form:option value="${manageUser.key}">${manageUser.value}</form:option>
					    </c:forEach>
				    </form:select>
  				</li>

				 <li>
					 <input type="submit" value="查询"/>
					 <input class="cancel-submit" type="button" value="取消" onclick="cancel()"/>
				 </li>
  				</ul>
		</form:form>
	</div>

	<div class="content-box">
	
		<div class="title">
   			<div id="title">设备列表</div>
   			<%--<a class="btn btn-new" href="#" onclick="exportDeviceUsageByCid()">导出</a>
   			--%><input class="btn btn-new" type="button" value="打印" id="print">
   			<a class="btn btn-new" id="update" href="#" onclick="updateDeviceUsageByCid()">更新</a>
   		</div>
	
		<table class="tb" id="my_show">
			<thead>
				<tr>
				  	<th>序号</th>
				    <th>设备名称</th>
				    <th>设备管理员</th>
				    <th>所在实验室</th>
				    <th>使用机时</th>
				    <th>使用次数</th>
				    <th>收费情况</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${labRoomDevices}" var="curr" varStatus="i">
					<tr>
						<td>${i.count+(currpage-1)*pageSize}</td>
						<td>${curr.schoolDevice.deviceName}[${curr.schoolDevice.deviceNumber}]</td>
						<td>${curr.user.cname }[${curr.user.username }]</td> 
						<td>${curr.labRoom.labRoomName}[${curr.labRoom.labRoomNumber}]</td> 
						<td>
							<a href="#" onclick="listDeviceUsageByCid(${curr.schoolDevice.deviceNumber})">
							<fmt:formatNumber value="${curr.schoolDevice.useHours }" type="number"  maxFractionDigits="2"/></a>
						</td>
						<td>
							<a href="#" onclick="listDeviceUsageByCid(${curr.schoolDevice.deviceNumber})">${curr.schoolDevice.useCount }</a>
						</td>
						<td>
							<fmt:formatNumber value="${curr.schoolDevice.devicePrice}" type="currency"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/listDeviceUsageByCid?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/listDeviceUsageByCid?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option selected="selected" value="${pageContext.request.contextPath}/device/listDeviceUsageByCid?currpage=${pageModel.currPage}">${pageModel.currPage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currPage}">
    <option value="${pageContext.request.contextPath}/device/listDeviceUsageByCid?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/listDeviceUsageByCid?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/listDeviceUsageByCid?currpage=${pageModel.lastPage}')" target="_self">末页</a>
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
    
    function cancel(){
		location.href="${pageContext.request.contextPath}/device/listDeviceUsageByCid?currpage=1";
	}
	//跳转
	function targetUrl(url)
	{
	  document.queryForm.action=url;
	  document.queryForm.submit();
	}
	
	// 打印
	$(document).ready(function(){
		$("#print").click(function(){
		$("#my_show").jqprint();
		})
	});
	
	// 导出
	function exportDeviceUsageByCid(){
		document.queryForm.action="${pageContext.request.contextPath}/device/exportDeviceUsageByCid?term=${term}";
		document.queryForm.submit();	
		document.queryForm.action="${pageContext.request.contextPath}/device/listDeviceUsageByCid?currpage=1";
	}
	
	// 导出
	function updateDeviceUsageByCid(){
		
		$("#update").css("background-color","#cccccc");
		
		$.ajax({
			url:"${pageContext.request.contextPath}/device/updateDeviceUsageByCid",
	           type:"POST",
	           success:function(data){//AJAX查询成功
	           		if(data=="success"){
		   				$("#update").css("background-color","#77bace");
		   				alert("更新成功");
	           		} 
	           },
	   			error:function(){
	   				alert("请求失败，请稍后重试");
				}
		});
	}
	
	
	// 二级页面查看
	function listDeviceUsageByCid(deviceNumber){
		document.queryForm.action="${pageContext.request.contextPath }/device/listReservationByDevice?deviceNumber="+deviceNumber+"&page=1&flag=1";
		document.queryForm.submit();	
		document.queryForm.action="${pageContext.request.contextPath}/device/listDeviceUsageByCid?currpage=1";
	}
	
</script>
<!-- 下拉框的js -->
</body>

</html>