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
            window.location.href="${pageContext.request.contextPath}/labRoom/initializeTeacherCredit?page=1";
        }
        function cancle(){
            window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoomTeacherCreditManage?page=1";
        }
	</script>
	<style>
		.point_div{
			padding:5px 0;
			font-size:16px;
			font-weight:bold;
		}
		.point_div a{
			font-size:16px;
			color:#ff0000;
			letter-spacing:1px;
			padding:0 5px;
			border-bottom:1px solid #ff0000;
		}
		.point_div a:hover{
			color:#ff0000;
		}
	</style>
</head>
<body>

<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.open.appointment" /></a></li>
			<li><a class="end"><spring:message code="left.credit.score" /></a></li>
		</ul>
	</div>
</div>
<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_PRESECTEACHING,ROLE_LABMANAGER,ROLE_EXCENTERDIRECTOR" >
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<%--<div class="title" style="border: 1px solid #d0d6dc;">--%>
				<%--<div id="title">--%>
					<%--我的信誉积分：${me.creditScore}--%>
				<%--</div>--%>
				<%--<div style="float:right;">--%>
					<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_PRESECTEACHING,ROLE_LABMANAGER,ROLE_EXCENTERDIRECTOR" >--%>
						<%--<a class="btn btn-new" onclick="exportSelect();">一键初始化</a>--%>
					<%--</sec:authorize>--%>
					<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/creditRecord?username=${me.username}">扣分记录</a>--%>
					<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/getCreditOption">扣分标准</a>--%>
				<%--</div>--%>
			<%--</div>--%>
			<ul class="TabbedPanelsTabGroup">
				<li class="TabbedPanelsTab selected" tabindex="0">
					<a href="${pageContext.request.contextPath}/labRoom/listLabRoomTeacherCreditManage?page=1">教师(我的信誉积分：${me.creditScore})</a>
				</li>
				<li class="TabbedPanelsTab " tabindex="0">
					<a href="${pageContext.request.contextPath}/labRoom/listLabRoomStudentCreditManage?page=1">学生</a>
				</li>
				<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_PRESECTEACHING,ROLE_LABMANAGER,ROLE_EXCENTERDIRECTOR" >
					<a class="btn btn-new" onclick="exportSelect();">一键初始化</a>
				</sec:authorize>
				<a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/creditRecord?username=${me.username}">扣分记录</a>
				<a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/getCreditOption">扣分标准</a>
			</ul>

			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="content-box">
						<div class="TabbedPanelsContentGroup">
							<div class="TabbedPanelsContent">
							<div class="content-box">
							<div class="tool-box">
								<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/listLabRoomTeacherCreditManage?page=1" method="post" modelAttribute="user">
									<ul>
										<li>工号:<form:input path="username" id="username" value="${username}"/></li>
										<li>姓名:<form:input path="cname" id="cname" value="${cname}"/></li>
										<li><input type="submit" value="查询"/>
											<input class="cancel-submit" type="button" onclick="cancle();" value="取消查询"/></li>
									</ul>
								</form:form>
							</div>
							</div>
							</div>
						</div>
					</div>

					<div class="TabbedPanelsContent">
						<div class="content-box">
							<table>
								<thead>
								<tr>
									<th width="100px">序号</th>
									<th>工号</th>
									<th>姓名</th>
									<th>信誉积分</th>
									<th>操作</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach items="${users}" var="current" varStatus="i">
									<tr>
										<td>${i.count+pageSize*(page-1)}</td>
										<td>${current.username }</td>
										<td>${current.cname}</td>
										<td>${current.creditScore}</td>
										<td>
											<a href="${pageContext.request.contextPath}/labRoom/modifyCredit?username=${current.username}">扣分</a>
											<a href="${pageContext.request.contextPath}/labRoom/creditRecord?username=${current.username}">扣分记录</a>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="page" >
								${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
							<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoomTeacherCreditManage?page=1')" target="_self">首页</a>
							<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoomTeacherCreditManage?page=${pageModel.previousPage}')" target="_self">上一页</a>
							第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
							<option value="${pageContext.request.contextPath}/labRoom/listLabRoomTeacherCreditManage?page=${pageModel.currpage}">${pageModel.currpage}</option>
							<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
								<c:if test="${j.index!=pageModel.currpage}">
									<option value="${pageContext.request.contextPath}/labRoom/listLabRoomTeacherCreditManage?page=${j.index}">${j.index}</option>
								</c:if>
							</c:forEach></select>页
							<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoomTeacherCreditManage?page=${pageModel.nextPage}')" target="_self">下一页</a>
							<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoomTeacherCreditManage?page=${pageModel.lastPage}')" target="_self">末页</a>
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
</body>
</html>