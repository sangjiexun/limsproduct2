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

<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

<script type="text/javascript">
$(function(){
	var s=${tage};
	 if(1==s){
	 $("#s2").addClass("TabbedPanelsTab selected");
	 $("#s0").addClass("TabbedPanelsTab");
	  $("#s1").addClass("TabbedPanelsTab");
	 }
	  if(2==s){
	 $("#s1").addClass("TabbedPanelsTab selected");
	 $("#s0").addClass("TabbedPanelsTab");
	 $("#s2").addClass("TabbedPanelsTab ");
	 }
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

</script>

</head>

<body>

<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)"><spring:message code="left.softwareInstall.application"/></a></li>
						<c:if test="${isAudit==2}">
						<li class="end"><a href="javascript:void(0)">我的申请</a></li>
						</c:if>
						<c:if test="${isAudit==1}">
							<li class="end"><a href="javascript:void(0)">我的审核</a></li>
						</c:if>
					</ul>
				</div>
			</div>
<div id="panel">			
<div id="TabbedPanels1" class="TabbedPanels">
			<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
				<li class="TabbedPanelsTab" id="s1">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/labRoom/ceshi?currPage=1">软件安装申请</a>
				</li>
				<li class="TabbedPanelsTab" id="s2">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?page=1&tage=0&isaudit=2">我的申请</a>
				</li>
				<li class="TabbedPanelsTab selected" id="s3">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?page=1&tage=0&isaudit=1">我的审核</a>
				</li>
			</ul>
		</div>
</div>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s0"><a href="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?tage=0&page=1&isaudit=${isAudit}">全部</a>
		</li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?tage=2&page=1&isaudit=${isAudit}">审核中</a></li>
		<li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?tage=4&page=1&isaudit=${isAudit}">审核通过</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?tage=3&page=1&isaudit=${isAudit}">审核拒绝</a></li>
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?tage=1&page=1&isaudit=${isAudit}">未审核</a>
		</li>
	</ul>
	<div class="TabbedPanelsTabGroup-box">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
    	<div class="content-box">
            <table class="tb" id="my_show">
                <thead>
                    <tr>
                        <th>申请人</th>
						<th>软件名称</th>
                        <th>安装日期</th>
                        <th><spring:message code="all.trainingRoom.labroom" /></th>
                        <th>申请理由</th>
                        <th>申请日期</th>
                        <th>审核结果</th>
                        <th>操作</th>
                    </tr>
                </thead>
                
                <tbody>
					<c:forEach items="${softwareReserves}" var="current" varStatus="i">
                        <tr>
                        	<td>${current.user.cname}</td>
							<td>${current.name}</td>
                        	<td><fmt:formatDate value="${current.requireTime.time}" pattern="yyyy-MM-dd"/></td>
                        	<td>${current.labRoom.labRoomName}</td>
                        	<td>${current.applyReason}</td>
                        	<td><fmt:formatDate value="${current.createTime.time}" pattern="yyyy-MM-dd"/></td>
                        	<c:if test="${current.approveState==1 }">
                        		<td>未审核</td>
                        	</c:if>
                        	<c:if test="${current.approveState==2 }">
                        		<td>审核中</td>
                        	</c:if>
                        	<c:if test="${current.approveState==3 }">
                        		<td>审核拒绝</td>
                        	</c:if>	
                        	<c:if test="${current.approveState==4 }">
                        		<td>审核通过</td>
                        	</c:if>
                        	<td>
                        	<c:if test="${isAudit eq 1}">
                        	 <c:if test="${current.buttonMark eq 0}">
						     	 <a href="${pageContext.request.contextPath}/SoftwareReservation/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">查看</a>
						     </c:if>
						     <c:if test="${current.buttonMark eq 2}">
						     	 <a href="${pageContext.request.contextPath}/SoftwareReservation/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">系主任审核</a>
						     </c:if>
						     <c:if test="${current.buttonMark eq 3}">
						     	 <a href="${pageContext.request.contextPath}/SoftwareReservation/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}"><spring:message code="all.trainingRoom.labroom"/>管理员审核</a>
						     </c:if>
						     <c:if test="${current.buttonMark eq 4}">
						     	 <a href="${pageContext.request.contextPath}/SoftwareReservation/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">实训中心主任审核</a>
						     </c:if>
						     <c:if test="${current.buttonMark eq 5}">
						     	 <a href="${pageContext.request.contextPath}/SoftwareReservation/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">实训部主任审核</a>
						     </c:if>
						     </c:if>
						     <c:if test="${isAudit eq 2}">
	                        	<c:if test="${current.approveState==1 }">
	                        		<a href="${pageContext.request.contextPath}/SoftwareReservationChange?id=${current.id}&tage=${tage}&page=${page}">修改</a>
	                        		<a href="${pageContext.request.contextPath}/SoftwareReservation/delete?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">删除</a>
	                        	</c:if>
						     <a href="${pageContext.request.contextPath}/SoftwareReservation/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">查看详情</a>
						     </c:if>
			    			</td>
                        </tr>
					</c:forEach>
                </tbody>
            </table>
            
<!-- 分页模块 -->
<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/labRoom/SoftwareReserveList?page=1&tage=${tage }&isaudit=${isAudit }')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/labRoom/SoftwareReserveList?tage=${tage }&isaudit=${isAudit }&page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?page=${page}&tage=${tage }&isaudit=${isAudit }">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?page=${j.index}&tage=${tage }&isaudit=${isAudit }">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/labRoom/SoftwareReserveList?tage=${tage }&isaudit=${isAudit }&page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/labRoom/SoftwareReserveList?tage=${tage }&isaudit=${isAudit }&page=${pageModel.totalPage}')" target="_self">末页</a>
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
<script>
	$(function(){
		var s=${isAudit};
		 if(1==s){
		  $("#s3").addClass("selected");
		  $("#s3").siblings().removeClass("selected");
		 }
		  if(2==s){
		    $("#s2").addClass("selected");
		  	$("#s2").siblings().removeClass("selected");
		 }
	});
</script>

</body>
</html>


