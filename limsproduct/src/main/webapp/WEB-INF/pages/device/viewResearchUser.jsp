<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html>
<head>
<meta name="decorator" content="iframe"/>
<title>无标题文档</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
<%--<script src="${pageContext.request.contextPath}/js/SpryTabbedPanels.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/css/SpryTabbedPanels.css" rel="stylesheet" type="text/css" />
--%><script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(".panel-tool-close").click(
		function(){
			alert(1);
		});
//定义全局变量
var result=1;//审核结果,默认是1--通过
$(document).ready(function(){
	
	if(${refresh==1}){
		parent.location.href="${pageContext.request.contextPath}/device/passReservationList?page=1";
	}
	
	if(${refresh==2}){
		parent.location.href="${pageContext.request.contextPath}/device/rejectedReservationList?page=1";
	}

});
//跳转
function targetUrl(url)
{  
	location.href = url;
}  

</script>
</head>
<body>

<div class="iStyle_Conteiner">
<div class="iStyle_RightInner">

		<!-- 添加设备页面 结束-->
			<!-- 添加管理员 -->
	<div id="addResearchUser">
		<div class="content-box">
		
		
		<table id="my_show">
					<thead>
						<tr>
							<th style="width:30% !important">姓名</th>
							<th style="width:30% !important">工号</th>
							<th style="width:30% !important">所属学院</th>
							<th style="width:30% !important">操作</th>
						</tr>
					</thead>
						
					<tbody id="user_body">
					<c:forEach items="${users }" var="curr" varStatus="i">
					<tr>
						<td>${curr.cname }</td>
						<td>${curr.username }</td>
						<td>${curr.schoolAcademy.academyName }</td>
						<td>
							<a href="${pageContext.request.contextPath}/device/deleteUserInResearchProject?id=${id}&username=${curr.username}" onclick="return confirm('确定删除？');">删除</a>
						</td>
					</tr>	
					</c:forEach> 
					</tbody>
					
			</table>
			</div>

</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_Icons.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_LeftList.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Downlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Allpages.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Interface.js"></script>
</body>
</html>
