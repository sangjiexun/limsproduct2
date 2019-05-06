<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>物资分类列表</title>
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
            <legend>物资类别</legend>
        </fieldset>
        <a href="javascript:void(0);" onclick="newClassification()" class="layui-btn layui-btn-normal new_btn">新建物资类别</a>
        <div class="demoTable">
            <div class="layui-inline">
                <input class="layui-input" name="keyword" id="cname" autocomplete="off" placeholder="请输入分类名称">
            </div>
            <div class="layui-btn" data-type="reload"  lay-filter="rechar_btn" id="search" >搜索</div>
        </div>
        <table class="layui-hide add_progress" id="assetsClassificationList" lay-filter="classificationToolbar"></table>
        <script type="text/html" id="classificationToolbar">
            <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </script>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/assetsClassificationList.js"></script>
</body>
</html>
