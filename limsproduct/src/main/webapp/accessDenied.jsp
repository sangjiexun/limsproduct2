<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<!--head-->
<head>
<meta charset="utf-8">
<title>智能管理系统</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/is_LeftList.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/is_Searcher.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/is_SystemUI_Allpages.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css" />
</head>
<!--head-->
<!--header-->
<div class="head">智能管理系统</div>
<!--header-->
<!-- login form-->
<div class="sit_module">
	<div class="sit_title">
		<h3>用户登录</h3>
	</div>
	<div class="module_content">

		<table class="sit_login_form">
			<tbody>
				<tr>
					<th><font  color="#FF0000">您没有授权使用本系统。</font> <a href="${pageContext.request.contextPath}/pages/logout-redirect.jsp" target="_parent">重新认证 </a></th>
				</tr>
			</tbody>
		</table>

		<div class="clear"></div>
	</div>
</div>
<!--footer-->
<!--footer-->
<div class="iStyle_Message">© 2012上海庚商网络信息技术有限公司|@POWER BY GVSUN</div>
<!--footer-->
</body>
</html>