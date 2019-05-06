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

</script>

</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)">课程项目管理</a></li>
						<li class="end"><a href="javascript:void(0)">软件安装申请</a></li>
					</ul>
				</div>
			</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="${pageContext.request.contextPath}/labRoom/labRoomLendList?page=1">全部</a>
		</li>
		<li class="TabbedPanelsTab " tabindex="0">
		<a href="${pageContext.request.contextPath}/labRoom/SoftwareReserveAuditingList?page=1">未审核</a>
		</li>
		<%-- <li class="TabbedPanelsTab" tabindex="0">
		<a href="${pageContext.request.contextPath}/device/rejectedDeviceLendList?page=1">审核中</a>
		</li> --%>
		<li class="TabbedPanelsTab " tabindex="0">
		<a href="${pageContext.request.contextPath}/labRoom/SoftwareReserveAuditedList?page=1">审核通过</a>
		</li>
		<li class="TabbedPanelsTab selected " tabindex="0">
		<a href="${pageContext.request.contextPath}/labRoom/SoftwareReserveAuditRefuseList?page=1">审核拒绝</a>
		</li>
		<%-- <li class="TabbedPanelsTab" tabindex="0">
		<a href="${pageContext.request.contextPath}/device/returnedDeviceLendList?page=1">已归还</a>
		</li> --%>
	</ul>
	<div class="TabbedPanelsTabGroup-box">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
    	<div class="content-box">
    		<div class="title">
    			<div id="title">全部</div>
    			<a class="btn btn-new" href="${pageContext.request.contextPath}/softwareReserve">申请</a>
    		</div>
            <table class="tb" id="my_show"> 
                <thead>
                    <tr>
                        <th>申请人</th>
                        <th>安装时间</th>
                        <th><spring:message code="all.trainingRoom.labroom" /></th>
                        <th>申请理由</th>
                        <th>申请日期</th>
                        <th>审核结果</th>
                        
                    </tr>
                </thead>
                
                <tbody>
                		<c:forEach items="${softwareReserves}" var="current" varStatus="i">
                		
                        <tr>
                        	<td>${current.user.cname}</td>
                        	<td><fmt:formatDate value="${current.requireTime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        	<td>${current.labRoom.labRoomName}</td>
                        	<td>${current.applyReason}</td>
                        	<td><fmt:formatDate value="${current.createTime.time}" pattern="yyyy-MM-dd"/></td>
                        	<c:if test="${current.approveState==1 }">
                        		<td>审核中</td>
                        	</c:if>
                        	<c:if test="${current.approveState==2 }">
                        		<td>审核拒绝</td>
                        	</c:if>
                        	<c:if test="${current.approveState==3 }">
                        		<td>审核通过</td>
                        	</c:if>	
                        </tr>
                		</c:forEach>
                       
                </tbody>
            </table>
            
<!-- 分页模块 -->
<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/labRoomLendList?page=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/labRoomLendList?page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/labRoomLendList?page=${page}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/labRoomLendList?page=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/labRoomLendList?page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/labRoomLendList?page=${pageModel.totalPage}')" target="_self">末页</a>
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


