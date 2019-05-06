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
	<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet">
	<link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)">我的实验项目</a></li>
						<li class="end"><a href="javascript:void(0)">实验项目进度查看</a></li>
					</ul>
				</div>
			</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
<div class="content-box">
<font style="color: red">注：从左往右按级别审核，只有前一级审核通过，后面才可以审核！每一级审核完成后不可更改结果！！</font>
 <div class="tool-box">
                <form:form name="form">
					<input type="button" onclick="window.history.go(-1)" value="返回">
				</form:form>
			</div>		     
<form:form  modelAttribute="project">
 <table >
         <tr >
             <td>实验名称：</td><td>${operationItem.lpName}</td>
             <td>学期：</td> <td>${operationItem.schoolTerm.termName}</td>
             <td>实验室：</td> <td>${operationItem.labRoom.labRoomName}</td>
             <td>申请日期：</td> <td><fmt:formatDate value="${operationItem.createdAt.time}" pattern="yyyy-MM-dd"/></td>
             <td>申请人：</td><td>${operationItem.userByLpCreateUser.cname}</td>
         </tr>
      </table>
<div id="tt" class="easyui-tabs" style="height:910px;">
<div title="系主任审核">
<iframe scrolling="yes" frameborder="0" id="myResponseList" src="${pageContext.request.contextPath}/operation/viewAuditUser?id=${operationItem.id}&currpage=1&flag=1" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
</div>
<div title="实验室管理员审核">
<iframe scrolling="yes" frameborder="0" id="myResponseList" src="${pageContext.request.contextPath}/operation/viewAuditUser?id=${operationItem.id}&currpage=1&flag=2" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
</div>
<div title="实训中心主任审核">
<iframe scrolling="yes" frameborder="0" id="myResponseList" src="${pageContext.request.contextPath}/operation/viewAuditUser?id=${operationItem.id}&currpage=1&flag=3" style="width:100%;height:100%;" scrolling="no"  onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
</div>
</div>
</form:form>
<div class="clear">&nbsp;</div>
</div>

</div>
</div>
</div>
</div>
</body>
</html>