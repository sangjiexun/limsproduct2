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
	//首页
	function first(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//末页
	function last(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//上一页
	function previous(url){
		var page=${page};
		if(page==1){
			page=1;
		}else{
			page=page-1;
		}
		//alert("上一页的路径："+url+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	//下一页
	function next(url){
		var totalPage=${pageModel.totalPage};
		var page=${page};
		if(page>=totalPage){
			page=totalPage;
		}else{
			page=page+1
		}
		//alert("下一页的路径："+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	function cancelQuery(){
		window.location.href="${pageContext.request.contextPath}/device/deviceLendList?page=1";
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
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1">可借用设备列表</a>
		</li>
		<li class="TabbedPanelsTab " tabindex="0">
		<a href="${pageContext.request.contextPath}/device/allDeviceLendList?page=1">所有申请</a>
		</li>
		<li class="TabbedPanelsTab selected" tabindex="0">
		<a href="${pageContext.request.contextPath}/device/deviceLendList?page=1">审核中</a>
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
		     <form name="queryForm" action="${pageContext.request.contextPath}/device/deviceLendList?page=1" method="post" modelAttribute="reservation">
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
						设备名称：<input id="deviceName" name="deviceName" value="${deviceName}"/>
						</td>
						<td>
							<input type="submit" value="查询">
							<input type="button" value="取消" onclick="cancelQuery();">
						</td>
					</tr >
			</table>
			</form>
		       
		    </div> 
    	<div class="content-box">
    		<div class="title">
    			<div id="title">提交待审核</div>
			<%--<a class="btn btn-new" href="javascript:void(0)">导出</a>--%>
    		</div>
            <table class="tb" id="my_show"> 
                <thead>
                    <tr>
                    	<th>序号</th>
                        <th>借用设备</th>
                        <th>申请人</th>
                        <th>指导教师</th>
                        <th>申请内容</th>
                        <th>借用日期</th>
                        <%--<th>审核人</th>
                        --%>
                        <th>审核结果</th>
                        <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EQUIPMENTADMIN">
                        <th>操作</th>
                        </sec:authorize>
                    </tr>
                </thead>
                
                <tbody>
                		<c:forEach items="${deviceLendList}" var="reservation" varStatus="i">
                		<%--<c:if test="${reservation.userByLendingUser.username == user.username || user.username == reservation.labRoomDevice.user.username || (ca eq true && cid == reservation.labRoomDevice.labRoom.labAnnex.labCenter.id) }">
                		--%><tr>
                        <td>${i.count}</td>
                        <td>${reservation.labRoomDevice.schoolDevice.deviceName}(${reservation.labRoomDevice.schoolDevice.deviceNumber})</td>
                        <td>${reservation.userByLendingUser.cname}</td>
                        <td>${reservation.userByTeacher.cname}</td>
                        <td>${reservation.content}</td>
                        <td><fmt:formatDate value="${reservation.lendingTime.time}" pattern="yyyy-MM-dd"/> </td>
                        <%--
                        <td>${reservation.labRoomDevice.user.cname }</td>
                        <td>
                        <a href="${pageContext.request.contextPath}/device/auditDepartmentDeviceLending?idKey=${reservation.id}">审核</a>--%>
                      	<td>
                         <%--<c:if test="${reservation.CAuditResult.id ne 4 && reservation.CAuditResult.id ne 5 && reservation.CAuditResult.id ne 6}">  <!-- 非审核取消状态才显示 -->
                         --%>
                         <c:forEach items="${reservation.labRoomDeviceLendingResults}" var="s"> 
                              <c:if test="${s.CDictionary.id==615}">${s.user.cname}(<font color="blue">通过</font>)<br></c:if>
                         </c:forEach>
                         <c:forEach items="${reservation.labRoomDeviceLendingResults}" var="s"> 
                              <c:if test="${s.CDictionary.id==616}">${s.user.cname}(<font color="red">拒绝</font>)<br></c:if>
                         </c:forEach>
                         <%--</c:if>
                         --%></td>
                      	<td>
                        <c:if test="${fn:contains(reservation.labRoomDevice.labRoom.labRoomAdmins,user.username)||fn:contains(labRoomHeads,user.username) ||reservation.userByDepartmentHead.username==user.username}">
	                        <a href="${pageContext.request.contextPath}/device/auditDepartmentDeviceLending?idKey=${reservation.id}">审核</a>
                        </c:if>    
                        </td>  
                        </tr>
                        <%--</c:if>
                		--%></c:forEach>
                       
                </tbody>
            </table>
            
<!-- 分页模块 -->
<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/device/deviceLendList?page=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/device/deviceLendList?page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/device/deviceLendList?page=${page}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/device/deviceLendList?page=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/device/deviceLendList?page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/device/deviceLendList?page=${pageModel.totalPage}')" target="_self">末页</a>
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


