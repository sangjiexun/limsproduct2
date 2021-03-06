<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf8">
	<title>物资名录列表</title>
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
			<legend>物资名录</legend>
		</fieldset>
		<button data-method="newAssets" class="layui-btn layui-btn-xs layui-btn-normal new_btn">新建物资名录</button>
		<button data-method="newAssetsApply" class="layui-btn layui-btn-xs layui-btn-normal apply_btn">新建物资申购</button>
		<blockquote class="layui-elem-quote">
				<div class="layui-inline">
					<input class="layui-input" id="keywords" autocomplete="off" placeholder="cas号、物资名称或分类"/>
				</div>
			<button class="layui-btn search_btn" data-method="search">搜索</button>
			<button class="layui-btn layui-btn-primary reload_btn" data-method="reload">取消</button>
			<%--<div class="layui-form" style="display:inline-block">--%>
				<%--<label class="layui-form-label">物资类别</label>--%>
				<%--<div class="layui-input-block">--%>
					<%--<select name="kind" id="kind"  lay-filter="kind">--%>
						<%--<option value=""></option>--%>
					<%--</select>--%>
				<%--</div>--%>
			<%--</div>--%>
		</blockquote>
		<table class="layui-hide add_progress" id="assetsList" lay-filter="assetsList"></table>
		<script type="text/html" id="parentbar">
			<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
			<%--<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>--%>
		</script>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/listAssets.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/construction/tagbox.js"></script>
</body>

</html>