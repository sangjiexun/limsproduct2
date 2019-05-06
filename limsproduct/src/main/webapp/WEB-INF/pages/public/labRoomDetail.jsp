<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<fmt:setBundle basename="bundles.lab-resources" />
<head>
	<meta name="decorator" content="iframe" />
<meta name="renderer" content="webkit|ie-stand|ie-comp" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<title></title>
		<meta name="Generator" content="gvsun" />
		<meta name="Author" content="chenyawen" />
		<meta name="Keywords" content="" />
		<meta name="Description" content="" />
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />
		<link type="text/css" rel="stylesheet" href="../../sdulims/css/labinfo.css" />

<!--[if lt IE 8]>
        <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <![endif]-->
		<!--[if gte IE 8]>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>  
    <![endif]-->
		<%--<meta name="renderer" content="webkit|ie-stand|ie-comp" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<title></title>
		<meta name="Generator" content="gvsun" />
		<meta name="Author" content="chenyawen" />
		<meta name="Keywords" content="" />
		<meta name="Description" content="" />
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/labinfo.css" />

--%>
<style type="text/css">
	.lab_msg_detail .lc_limit {
	    font-size: 24px;
	    line-height: 48px;
    }
    .lab_msg_detail div {
	    font-size: 24px;
	    height: 48px;
    }
</style>
</head>
<body> 
<!--导航  -->
<!-- <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">实验室课表</a></li>
			<li class="end"><a href="javascript:void(0)">实验室日课表</a></li>
		</ul>
	</div>
</div> -->
<!--导航结束  -->
			<div class="lab_msg_detail">
				<div class="lab_name">${roomNumber} ${roomName}</div>
				<div>管理员：${cname }</div>
				<div>联系电话：${telephone}</div>
				<div class="lc_limit">简介：${intro}</div>
			</div>
