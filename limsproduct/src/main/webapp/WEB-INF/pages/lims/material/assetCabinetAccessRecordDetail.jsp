<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf8">
	<title>物资申购列表</title>
	<meta name="decorator" content="timetable"/>
	<meta name="contextPath" content="${pageContext.request.contextPath}"/>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
</head>

<body>
<input id="contextPath" value="${pageContext.request.contextPath}" style="display: none;"/>

<div class="layui-layout layui-layout-admin">
	<div class="layui-main">
		<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
			<legend>物资出入库记录列表</legend>
		</fieldset>
		<input type="hidden" id="id" value="${id}"/>
 		<table class="layui-hide add_progress" id="assetCabinetAccessRecordList" lay-filter="assetCabinetAccessRecordList"></table>
		<script type="text/html" id="assetCabinetAccessRecordDetailToolBar">
			<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看详情</a>
		</script>
		<script type="text/html" id="zh">
			{{#  if(d.recordType=='InStorage'){ }}
			物资入库
			{{# }else if(d.recordType=='Receive'){ }}
			物资领用
			{{# }  else if(d.recordType=='ReceiveReturn'){ }}
			物资归还
			{{#  } }}
		</script>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/assetCabinetAccessRecordDetail.js" ></script>
</body>

</html>