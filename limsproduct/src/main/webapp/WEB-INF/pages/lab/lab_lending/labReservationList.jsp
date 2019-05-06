<%@ page import="java.util.List" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	var s=${isAudit};
	 if(1==s){
	 $("#s3").addClass("TabbedPanelsTab selected");
	 $("#s1").addClass("TabbedPanelsTab");
	  $("#s2").addClass("TabbedPanelsTab");
	 }
	  if(2==s){
	 $("#s2").addClass("TabbedPanelsTab selected");
	 $("#s1").addClass("TabbedPanelsTab ");
	    $("#s3").addClass("TabbedPanelsTab");
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
	//转到实验室工位预约
	function toStation(){
		window.location.href="${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?tage=0&currpage=1&isaudit=${isAudit}";
	}
	//取消
	function cancleQuery(){
		window.location.href="${pageContext.request.contextPath}/labRoomLending/labReservationList?page=1&tage=0&isaudit=${isAudit}";
	}
</script>
<style type="text/css">
	#my_show{
		margin-top: 10px;
	}
</style>
</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)">课程项目管理</a></li>
						<li class="end"><a href="javascript:void(0)"><spring:message code="all.trainingRoom.labroom" />借用申请</a></li>
					</ul>
				</div>
			</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s1"><a
				href="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1"><spring:message
				code="all.trainingRoom.labroom"/>预约</a>
		</li>
		<li class="TabbedPanelsTab" id="s2"><a
				href="${pageContext.request.contextPath}/labRoomLending/labReservationList?tage=0&page=1&isaudit=2">我的申请</a>
		</li>
		<sec:authorize ifNotGranted="ROLE_STUDENT">
			<li class="TabbedPanelsTab" id="s3"><a
					href="${pageContext.request.contextPath}/labRoomLending/labReservationList?tage=0&page=1&isaudit=1">我的审核</a>
			</li>
		</sec:authorize>
        <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
            <li class="TabbedPanelsTab" id="s4"><a
                    href="${pageContext.request.contextPath}/labRoomLending/labReservationObsoleteList?page=1">作废列表</a>
            </li>
        </c:if>
	</ul>
	<div class="TabbedPanelsTabGroup-box">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
    	<div class="content-box">
			<%--工位预约/实验室预约拆分--%>
			<%--<input type="radio" name="isStation" checked="checked"/><spring:message code="all.trainingRoom.labroom" />预约
			<c:if test="${jobReservation eq 'true'}">
				<input type="radio" name="isStation" onclick="toStation()"/>工位预约
			</c:if>--%>
    		<div class="tool-box">
        <form:form name="queryForm" action="${pageContext.request.contextPath}/labRoomLending/labReservationList?page=1&tage=${tage}&isaudit=${isAudit}" method="post" modelAttribute="labReservation">
		<ul style="position: absolute;">
			<li><spring:message code="all.trainingRoom.labroom" />:<form:input path="labRoom.labRoomName"/> </li>
			<li><input type="submit" value="查询" >
				<input class="cancel-submit" type="button" onclick="cancleQuery()" value="取消"></li>
			</ul>
		</form:form>
	</div>
            <table class="tb" id="my_show"> 
                <thead>
                   <tr>
						<th><spring:message code="all.trainingRoom.labroom" />名称</th>
						<th>申请人</th>
						<th>使用日期</th>
						<th>使用时间</th>
					   <th>预约用途</th>
					   <c:if test="${proj_name ne 'shjulims'}">
						   <th>使用对象</th>
						   <th>班级</th>
					   </c:if>
					   <th>预约部门</th>
					   <th>使用人数</th>
					   <th>预约人电话</th>
					   <th>预约原因</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
                </thead>
                
                <tbody>
                		<c:forEach items="${labReservations}" var="current" varStatus="i">
                		
                        <tr>
                        		<td>${current.labRoom.labRoomName}[${current.labRoom.labRoomNumber}]</td>
								<td>${current.user.cname}</td>
								<td><fmt:formatDate value="${current.lendingTime.time}" pattern="yyyy-MM-dd"/></td>
								<td>
									<c:forEach items="${current.labReservationTimeTables}" var="currentTA" varStatus="taI">
										<fmt:formatDate value="${currentTA.startTime.time}" pattern="HH:mm"/>-<fmt:formatDate value="${currentTA.endTime.time}" pattern="HH:mm"/>
									</c:forEach>
								</td>
							<td>${current.CDictionaryByLendingType.CName}</td>
							<c:if test="${proj_name ne 'shjulims'}">
								<td>${current.CDictionaryByLendingUserType.CName}</td>
								<td>${current.schoolClasses.className}</td>
							</c:if>
							<td>${current.lendingUnit}</td>
							<td>${current.number}</td>
							<td>${current.lendingUserPhone}</td>
							<td>${current.lendingReason}</td>
							<c:if test="${auditState.get(i.count-1)==-2 }">
								<td>无审核记录</td>
							</c:if>
                        	<c:if test="${auditState.get(i.count-1)==-1 }">
                        		<td>审核通过</td>
                        	</c:if>
                        	<c:if test="${auditState.get(i.count-1)>0 }">
                        		<%--<td>审核中</td>--%>
								<td>${auditShow.get(i.count-1)}</td>
                        	</c:if>
                        	<c:if test="${auditState.get(i.count-1)==0 }">
                        		<td>审核拒绝</td>
                        	</c:if>
                        	<td>
                        	<c:if test="${isAudit eq 1 && auditState.get(i.count-1)!=-2 }">
								<c:if test="${current.buttonMark eq -1}">
									<a href="${pageContext.request.contextPath}/labRoomLending/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">查看</a>
								</c:if>
                        	 <c:if test="${current.buttonMark eq 0}">
						     	 <a href="${pageContext.request.contextPath}/labRoomLending/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">查看</a>
						     </c:if>
						     <c:if test="${current.buttonMark eq 1}">
						     	 <a href="${pageContext.request.contextPath}/labRoomLending/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">导师审核</a>
						     </c:if>
						     <c:if test="${current.buttonMark eq 2}">
						     	 <a href="${pageContext.request.contextPath}/labRoomLending/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">系主任审核</a>
						     </c:if>
								<c:if test="${current.buttonMark eq 3}">
									<a href="${pageContext.request.contextPath}/labRoomLending/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}"><spring:message code="all.trainingRoom.labroom" />管理员审核</a>
								</c:if>
						     <c:if test="${current.buttonMark eq 4}">
						     	 <a href="${pageContext.request.contextPath}/labRoomLending/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">实训中心主任审核</a>
						     </c:if>
						     <c:if test="${current.buttonMark eq 5}">
						     	 <a href="${pageContext.request.contextPath}/labRoomLending/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">实训部主任审核</a>
						     </c:if>
						     </c:if>
						     <c:if test="${isAudit eq 2 && auditState.get(i.count-1)!=-2}">
	                        	<%--明明是软件安装申请的东西，不知道如何乱入
	                        	<c:if test="${current.auditResults==3}">
	                        		<a href="${pageContext.request.contextPath}/SoftwareReservationChange?id=${current.id}&tage=${tage}&page=${page}">修改</a>
	                        		<a href="${pageContext.request.contextPath}/SoftwareReservation/delete?id=${current.id}&tage=${tage}&state=${current.auditStage}&page=${page}">删除</a>
	                        	</c:if>
						     --%><a href="${pageContext.request.contextPath}/labRoomLending/checkButton?id=${current.id}&tage=${tage}&state=${auditState.get(i.count-1)}&page=${page}">查看详情</a>
						     </c:if>

<%--								<c:if test="${(sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'
								|| sessionScope.selected_role eq 'ROLE_ACADEMYLEVELM'|| sessionScope.selected_role eq 'ROLE_LABMANAGER' )&& isAudit eq 1}">
									<a href="${pageContext.request.contextPath}/labRoomLending/editLabRoomLending?id=${current.id}&page=${page}&isaudit=${isAudit}&tage=${tage}">编辑</a>
								</c:if>--%>
								<c:if test="${auditState.get(i.count-1)==1&& isAudit eq 2}">
									<a href="${pageContext.request.contextPath}/labRoomLending/editLabRoomLending?id=${current.id}&page=${page}&isaudit=${isAudit}&tage=${tage}">编辑</a>
								</c:if>
								<c:if test="${auditState.get(i.count-1)==1&& isAudit eq 2}">
								<a href="${pageContext.request.contextPath}/labRoomLending/deletelabReservation?id=${current.id}&page=${page}&isaudit=${isAudit}&tage=${tage}" onclick="return confirm('确定撤回吗？');">撤回</a>
								</c:if>
<%--								<c:if test="${(sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'
								|| sessionScope.selected_role eq 'ROLE_ACADEMYLEVELM'|| sessionScope.selected_role eq 'ROLE_LABMANAGER' )&& isAudit eq 1}">
								<a href="${pageContext.request.contextPath}/labRoomLending/deletelabReservation?id=${current.id}&page=${page}&isaudit=${isAudit}&tage=${tage}" onclick="return confirm('确定删除吗？');">删除</a>
								</c:if>--%>
								<c:if test="${(sessionScope.selected_role eq 'ROLE_LABMANAGER') && isAudit eq 1 && (auditState.get(i.count-1)==-1 || auditState.get(i.count-1)==0)}">
									<c:forEach items="${current.labRoom.labRoomAdmins}" var="la">
										<c:if test="${la.user.username eq user.username}">
											<a href="javascript:void(0)"
											   onclick="obsoleteLabReservation('${current.id}')">作废</a>
										</c:if>
									</c:forEach>
								</c:if>

			    			</td>
                        </tr>
                		</c:forEach>
                       
                </tbody>
            </table>
            
<!-- 分页模块 -->
<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/labRoomLending/labReservationList?page=1&tage=${tage }&isaudit=${isAudit }')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/labRoomLending/labReservationList?tage=${tage }&isaudit=${isAudit }&page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/labRoomLending/labReservationList?page=${page}&tage=${tage }&isaudit=${isAudit }">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/labRoomLending/labReservationList?page=${j.index}&tage=${tage }&isaudit=${isAudit }">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/labRoomLending/labReservationList?tage=${tage }&isaudit=${isAudit }&page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/labRoomLending/labReservationList?tage=${tage }&isaudit=${isAudit }&page=${pageModel.totalPage}')" target="_self">末页</a>
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

<script type="text/javascript">
	function obsoleteLabReservation(id) {
		if(confirm("确定作废吗")) {
			$.ajax({
				url: "${pageContext.request.contextPath}/labRoomLending/obsoleteLabReservation?labReservationId=" + id,
				contentType: "application/json;charset=utf-8",
				dataType: "text",
				type: "post",
				async: false,
				success: function (data) {
					if (data == "success") {
						alert("已作废");
						window.location.reload();
					} else {
						alert("作废失败");
					}
				},
				error: function () {
					alert("作废失败");
				}
			});
		}
	}
</script>

</body>
</html>


