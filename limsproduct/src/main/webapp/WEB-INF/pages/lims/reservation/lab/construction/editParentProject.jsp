<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>填写父项目列表</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/construction/expand.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js"></script>
    <!--父项目列表专属表头长度-->
    <style>
        .layui-form-label {
            width: 85px;
        }

        .layui-input-block {
            margin-left: 115px;
        }
    </style>
</head>

<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>填写父项目列表</legend>
        </fieldset>
        <form class="layui-form" action="" lay-filter="parentproject">
            <input  type="hidden" name="id" value="${parentProject.id}"/>
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg8">
                    <label class="layui-form-label">父项目名称：</label>
                    <div class="layui-input-block">
                        <input type="text" name="projectName" value="${parentProject.projectName}" lay-verify="required" placeholder="请填写父项目名称" autocomplete="on" class="layui-input" />
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">经费预算：</label>
                    <div class="layui-input-block">
                        <input type="number" name="budget" value="${parentProject.budget}" lay-verify="required" placeholder="请填写父项目经费预算" autocomplete="on" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="saveParentProject">保存</button>
                    <button class="layui-btn" lay-submit lay-filter="submitParentProject">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/construction/editParentProject.js"></script>
</body>

</html>