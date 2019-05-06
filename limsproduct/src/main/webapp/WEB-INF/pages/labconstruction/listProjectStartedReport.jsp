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
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>

<style type="text/css">

</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
	$("#print").click(function(){
	$("#ssssss").jqprint();
	})
	});
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
function cancelQuery(url){
	window.location.href=url;
}	
function cancel(){
	window.location.href="${pageContext.request.contextPath}/labconstruction/listProjectStartedReport?page=1";
}
//AJAX验证是否通过安全准入
function Access(surl,furl,id){
	var successURL=surl+"&id="+id;
	var failURL=furl+"&deviceId="+id;
	//"${pageContext.request.contextPath}/device/securityAccess?id="+id
	$.ajax({
	           url:"${pageContext.request.contextPath}/device/securityAccess?id="+id,
	           type:"POST",
	           success:function(data){//AJAX查询成功
	           		if(data=="success"){
	                  	//window.location.href="${pageContext.request.contextPath}/device/reservationDevice?id="+id;
	                  	window.location.href=successURL;
	           		}else{
	           			alert("您还未通过安全准入验证！");
	           			//window.location.href="${pageContext.request.contextPath}/device/findAllTrainingByDeviceId?deviceId="+id;
	           			window.location.href=failURL;
	           		}    
	           }
	});
	
}
</script>


</head>

<body>
<div class="iStyle_RightInner">
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.labconstruction.management"/></a></li>
			<li class="end"><a href="javascript:void(0)"><spring:message code="left.labconstruction.started"/></a></li>
		</ul>
	</div>
</div>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab1 selected" id="s1">
            <a href="javascript:void(0)">室建设项目启动报告</a>
        </li>
        <a class="btn btn-new" href="${pageContext.request.contextPath}/labconstruction/exportProjectStartedReport"> 导出</a>
        <input class="btn btn-new" type="button" value="打印" id="print">
    </ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
		        <div class="content-box">
				<%--<div class="title">--%>
				<%--<div id="title"><spring:message code="all.training.name" />室建设项目启动报告</div>--%>
				    <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/labconstruction/exportProjectStartedReport"> 导出</a>--%>
			        <%--<input class="btn btn-new" type="button" value="打印" id="print">--%>
			<%--</div> --%>
		     <div class="tool-box">
		      	<form:form name="queryForm" action="${pageContext.request.contextPath}/labconstruction/listProjectStartedReport?page=1" method="post" modelAttribute="projectStartedReport">
				    <ul>
				    <li>项目名称:<form:input id="projectName" path="projectName"/></li>
				    <li>
                        <input type="submit" value="查询"/>
                        <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
					</li>
					</ul>
				</form:form>		        
		    </div>
                </div>
    	<div class="content-box">
            <table id="ssssss"> 
                <thead>
                    <tr>
                    	<th>申请编号</th>
                        <th>项目名称</th>
                        <th>系（部）</th>
                        <th>申请时间</th>
                        <th>项目来源</th>
                        <th>申请人</th>
                        <th>操作</th>
                    </tr>
                </thead>
                
                <tbody>
                		<c:forEach items="${listProjectStartedReport}" var="current" varStatus="i">
                		<tr>
                        <td>${current.labConstructApp.labConstructAppCode}</td>
                        <td>${current.projectName}</td>
                        <td>${current.schoolAcademy.academyName}</td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${current.labConstructApp.appDate.time}"/>&nbsp;</td>
	                    <td>${current.CProjectSource.name}</td>
	                    <td>${current.labConstructApp.user.cname}</td>
                        <td>
                           <a href="${pageContext.request.contextPath}/labconstruction/viewProjectStartedReport?idKey=${current.id}"   >查看</a>
                           <c:if test="${current.labConstructApp.user.username==loginUser.username}"> 
                              <a href="${pageContext.request.contextPath}/labconstruction/editProjectStartedReport?idKey=${current.id}"   >编辑</a>
                              <a href="${pageContext.request.contextPath}/labconstruction/deleteProjectStartedReport?idKey=${current.id}" onclick="return confirm('确认删除吗？')">删除</a>
                              <a href="${pageContext.request.contextPath}/labconstruction/newProjectAcceptance?idKey=${current.id}"   >验收申请</a>
                           </c:if>
                           <c:if test="${current.labConstructApp.user.username!=loginUser.username}"> 
                             <sec:authorize ifAnyGranted="ROLE_SUPERADMIN">
                                <a href="${pageContext.request.contextPath}/labconstruction/editProjectStartedReport?idKey=${current.id}"   >编辑</a>
                                <a href="${pageContext.request.contextPath}/labconstruction/deleteProjectStartedReport?idKey=${current.id}" onclick="return confirm('确认删除吗？')">删除</a>
                                <a href="${pageContext.request.contextPath}/labconstruction/newProjectAcceptance?idKey=${current.id}"   >验收申请</a>
                             </sec:authorize>
                           </c:if>
                         </td>
                        </tr>
                		</c:forEach>
                </tbody>
            </table>
            
<!-- 分页模块 -->
<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/labconstruction/listProjectStartedReport?page=1')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/labconstruction/listProjectStartedReport?page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/labconstruction/listProjectStartedReport?page=${page}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/labconstruction/listProjectStartedReport?page=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"     onclick="next('${pageContext.request.contextPath}/labconstruction/listProjectStartedReport?page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)"    onclick="last('${pageContext.request.contextPath}/labconstruction/listProjectStartedReport?page=${pageModel.totalPage}')" target="_self">末页</a>
    </div>
<!-- 分页模块 -->
</div>


</div>
</div>
</div>
</div>

<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
--%><script type="text/javascript" src="${pageContext.request.contextPath}/js/is_Icons.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_LeftList.js"></script>
<!--<script type="text/javascript" src="js/is_ControlsDrag.js"></script>-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Downlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Allpages.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Interface.js"></script>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
						    var config = {
						      '.chzn-select': {search_contains : true},
						      '.chzn-select-deselect'  : {allow_single_deselect:true},
						      '.chzn-select-no-single' : {disable_search_threshold:10},
						      '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
						      '.chzn-select-width'     : {width:"95%"}
						    }
						    for (var selector in config) {
						      $(selector).chosen(config[selector]);
						    }
						</script>

<!-- 下拉框的js -->

</body>
</html>


