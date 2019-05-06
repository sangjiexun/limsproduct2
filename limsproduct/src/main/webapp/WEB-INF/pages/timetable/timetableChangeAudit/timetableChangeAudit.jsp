<%@page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.project-resources"/>
<html>
<head>
	<meta name="decorator" content="iframe"/>
	<meta name="decorator" content="none"/>

	<title>Edit <fmt:message key="project.title"/></title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/css/demo.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/locale/easyui-lang-zh_CN.js"></script>
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
	<style>
		.tabs-panels{
			border:none;
		}
		.tabs-container{
			width:98%;
			background: none;
		}
		.tabs-header{
			padding: 0;
			background: no-repeat;
			border: none;
		}
		.tabs{
			padding:0;
			border-bottom:1px solid #bfc7d0;
		}
		.tabs li{
			border-radius:0;
			border: 1px solid #d9d9d9;
			border-bottom:none;
			margin-bottom: 0;
		}
		.tabs li a.tabs-inner{
			color: #333;
			background: #f5f5f5;
			border-radius:0;
		}
		.tabs li.tabs-selected {
			border: 1px solid #b1b1b1;
		}
		.tabs li.tabs-selected a.tabs-inner {
			color: #333;
			font-weight: normal;
		}
	</style>
</head>

<body>
<div class="dmrrr">
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)">排课管理</a></li>
				<li class="end"><a href="javascript:void(0)"><spring:message code="all.training.lab" /></a></li>
			</ul>
		</div>
	</div>
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="content-box">
						<div class="title">
						    <font style="color: red">注：从左往右按级别审核，只有前一级审核通过，后面才可以审核！每一级审核完成后不可更改结果！！</font>
						    <form:form name="form">
								<a href="javascript:void(0);" class="btn btn-new" onclick="window.history.go(-1)">返回</a>
							</form:form>
						</div>
						<form:form  modelAttribute="project">
						<%--<table >
							<tr >
							    <td>申请调课项目名称：</td><td>${appointmentChange.name}</td>
							</tr>
						</table>--%>
						<div id="tt" class="easyui-tabs">
							<c:if test="${appointmentChange.flag eq 1}">
								<div title="系主任审核">
									<iframe scrolling="yes" frameborder="0" id="myResponseList" src="${pageContext.request.contextPath}/timetableChangeAudit/deanAudit?id=${appointmentChange.id}&tage=${tage}&state=${state}&page=${page}" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
								</div>
							</c:if>
							    <div title="实验室管理员审核">
								    <iframe scrolling="yes" frameborder="0" id="myResponseList" src="${pageContext.request.contextPath}/timetableChangeAudit/labRoomAdminAudit?id=${appointmentChange.id}&tage=${tage}&state=${state}&page=${page}" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
							    </div>
							<c:if test="${appointmentChange.flag eq 1 }">
						    	<div title="实训部秘书审核">
							    	<iframe scrolling="yes" frameborder="0" id="myResponseList" src="${pageContext.request.contextPath}/timetableChangeAudit/labRoomDepartmentDirectorAudit?id=${appointmentChange.id}&tage=${tage}&state=${state}&page=${page}" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe></div>
						        </div>
						    </c:if>
					    </div>
					</form:form>
					<div class="clear">&nbsp;</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</body>
</html>