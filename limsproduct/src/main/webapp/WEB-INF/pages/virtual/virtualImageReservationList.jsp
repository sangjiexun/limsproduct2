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
                $("#s1").removeClass("TabbedPanelsTab selected");
                $("#s1").addClass("TabbedPanelsTab");
                $("#s2").addClass("TabbedPanelsTab");
            }
            if(2==s){
                $("#s2").addClass("TabbedPanelsTab selected");
                $("#s1").removeClass("TabbedPanelsTab selected");
                $("#s1").addClass("TabbedPanelsTab");
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

</head>

<body>
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">开放预约</a></li>
			<li class="end"><a href="javascript:void(0)">虚拟镜像申请</a></li>
		</ul>
	</div>
</div>

<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
			<li class="TabbedPanelsTab selected" id="s1">
				<a href="${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=1">虚拟镜像预约</a></li>
			<li class="TabbedPanelsTab" id="s2"><a
					href="${pageContext.request.contextPath}/virtual/virtualImageReservationList?tage=0&page=1&isaudit=2">我的虚拟镜像申请</a>
			</li>
			<sec:authorize ifNotGranted="ROLE_STUDENT">
				<li class="TabbedPanelsTab" id="s3"><a
						href="${pageContext.request.contextPath}/virtual/virtualImageReservationList?tage=0&page=1&isaudit=1">我的虚拟镜像审核</a>
				</li>
			</sec:authorize>
		</ul>
		<div class="TabbedPanelsTabGroup-box">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="content-box">
						<%--<input type="radio" name="isStation" checked="checked"/><spring:message code="all.trainingRoom.labroom" />预约
						<c:if test="${jobReservation eq 'true'}">
							<input type="radio" name="isStation" onclick="toStation()"/>工位预约
						</c:if>
						<div class="tool-box">
							<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoomLending/labReservationList?page=1&tage=${tage}&isaudit=${isAudit}" method="post" modelAttribute="labReservation">
								<ul style="position: absolute;">
									<li><spring:message code="all.trainingRoom.labroom" />：<form:input path="labRoom.labRoomName"/> </li>
									<li><input type="submit" value="查询" ></li>
									<li><input type="button" onclick="cancleQuery()" value="取消"></li>
								</ul>
							</form:form>
						</div>--%>
						<table class="tb" id="my_show">
							<thead>
							<tr>
								<th>镜像名称</th>
								<th>预约人</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>预约理由</th>
								<th>状态</th>
								<%--<th>操作</th>--%>
								<c:if test="${isAudit eq 2}">
								<th>登录</th>
								</c:if>
							</tr>
							</thead>

							<tbody>
							<c:forEach items="${virtualImageReservations}" var="current" varStatus="i">

								<tr>
									<td>${current.virtualImage.name}</td>
									<td>${current.user.cname}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${current.startTime.time}" /></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${current.endTime.time}" /></td>
									<td>${current.remarks}</td>
									<%--<td>
										<a href="javascript:void(0)"
										   onclick="VirtualLogin('${s.id}')">登陆</a>

									</td>--%>
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
									<%--<td>
										<c:if test="${isAudit eq 1 && auditState.get(i.count-1)!=-2 }">
											<c:if test="${current.buttonMark eq -1}">
												<a href="${pageContext.request.contextPath}/virtual/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">查看</a>
											</c:if>
											<c:if test="${current.buttonMark eq 0}">
												<a href="${pageContext.request.contextPath}/virtual/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">查看</a>
											</c:if>
											<c:if test="${current.buttonMark eq 1}">
												<a href="${pageContext.request.contextPath}/virtual/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">导师审核</a>
											</c:if>
											<c:if test="${current.buttonMark eq 2}">
												<a href="${pageContext.request.contextPath}/virtual/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">系主任审核</a>
											</c:if>
											<c:if test="${current.buttonMark eq 3}">
												<a href="${pageContext.request.contextPath}/virtual/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}"><spring:message code="all.trainingRoom.labroom" />管理员审核</a>
											</c:if>
											<c:if test="${current.buttonMark eq 4}">
												<a href="${pageContext.request.contextPath}/virtual/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">实训中心主任审核</a>
											</c:if>
											<c:if test="${current.buttonMark eq 5}">
												<a href="${pageContext.request.contextPath}/virtual/checkButton?id=${current.id}&tage=${tage}&state=${current.buttonMark}&page=${page}">实训部主任审核</a>
											</c:if>
										</c:if>
										<c:if test="${isAudit eq 2 && auditState.get(i.count-1)!=-2}">
											&lt;%&ndash;明明是软件安装申请的东西，不知道如何乱入
                                            <c:if test="${current.auditResults==3}">
                                                <a href="${pageContext.request.contextPath}/SoftwareReservationChange?id=${current.id}&tage=${tage}&page=${page}">修改</a>
                                                <a href="${pageContext.request.contextPath}/SoftwareReservation/delete?id=${current.id}&tage=${tage}&state=${current.auditStage}&page=${page}">删除</a>
                                            </c:if>
                                         &ndash;%&gt;<a href="${pageContext.request.contextPath}/virtual/checkButton?id=${current.id}&tage=${tage}&state=${auditState.get(i.count-1)}&page=${page}">状态查看</a>
										</c:if>

										&lt;%&ndash;<c:if test="${(sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'
								|| sessionScope.selected_role eq 'ROLE_ACADEMYLEVELM'|| sessionScope.selected_role eq 'ROLE_LABMANAGER' )&& isAudit eq 1}">
											<a href="${pageContext.request.contextPath}/labRoomLending/editLabRoomLending?id=${current.id}&page=${page}&isaudit=${isAudit}&tage=${tage}">编辑</a>
										</c:if>
										<c:if test="${auditState.get(i.count-1)==1&& isAudit eq 2}">
											<a href="${pageContext.request.contextPath}/labRoomLending/editLabRoomLending?id=${current.id}&page=${page}&isaudit=${isAudit}&tage=${tage}">编辑</a>
										</c:if>
										<c:if test="${auditState.get(i.count-1)==1&& isAudit eq 2}">
											<a href="${pageContext.request.contextPath}/labRoomLending/deletelabReservation?id=${current.id}&page=${page}&isaudit=${isAudit}&tage=${tage}" onclick="return confirm('确定删除吗？');">删除</a>
										</c:if>
										<c:if test="${(sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'
								|| sessionScope.selected_role eq 'ROLE_ACADEMYLEVELM'|| sessionScope.selected_role eq 'ROLE_LABMANAGER' )&& isAudit eq 1}">
											<a href="${pageContext.request.contextPath}/labRoomLending/deletelabReservation?id=${current.id}&page=${page}&isaudit=${isAudit}&tage=${tage}" onclick="return confirm('确定删除吗？');">删除</a>
										</c:if>&ndash;%&gt;

									</td>--%>
									<td>
										<jsp:useBean id="now" class="java.util.Date" />
										<c:if test="${isAudit eq 2 && auditState.get(i.count-1)==-1 and current.startTime.time le now and current.endTime.time ge now}">
										<a href="javascript:void(0)"
											   onclick="VirtualLogin('${current.id}')">登录</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>

							</tbody>
						</table>

						<!-- 分页模块 -->
						<div class="page" >
							${totalRecords}条记录,共${pageModel.totalPage}页
							<a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/virtual/virtualImageReservationList?page=1&tage=${tage }&isaudit=${isAudit }')" target="_self">首页</a>
							<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/virtual/virtualImageReservationList?tage=${tage }&isaudit=${isAudit }&page=')" target="_self">上一页</a>
							第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
							<option value="${pageContext.request.contextPath}/virtual/virtualImageReservationList?page=${page}&tage=${tage }&isaudit=${isAudit }">${page}</option>
							<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
								<c:if test="${j.index!=page}">
									<option value="${pageContext.request.contextPath}/virtual/virtualImageReservationList?page=${j.index}&tage=${tage }&isaudit=${isAudit }">${j.index}</option>
								</c:if>
							</c:forEach></select>页
							<a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/virtual/virtualImageReservationList?tage=${tage }&isaudit=${isAudit }&page=')" target="_self">下一页</a>
							<a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/virtual/virtualImageReservationList?tage=${tage }&isaudit=${isAudit }&page=${pageModel.totalPage}')" target="_self">末页</a>
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

    function VirtualLogin(id) {
        url="${pageContext.request.contextPath}/virtual/virtualLogin?virtualImageReservationid="+id;
        window.open(url);
	}

</script>
<!-- 下拉框的js -->

</body>
</html>


