<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>查看物资类别详情</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/construction/expand.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js"></script>
    <style>
        .layui-form-label{
            width: 120px;
        }
        .layui-input-block {
            margin-left: 150px;
            min-height: 36px
        }
        .big{
            width: 1197px!important;
        }
    </style>
</head>

<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend >查看物资类别详情</legend>
        </fieldset>
        <form class="layui-form detail_item" action="" lay-filter="assetsClassificationDetail">
            <input id="id" name="id" type="hidden" value="${id}"/>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物品类别中文名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="cname"  lay-verify="required"  autocomplete="on" class="layui-input" disabled="disabled"/>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物资类别英文名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="ename"  lay-verify="required"  autocomplete="on" class="layui-input" disabled="disabled"/>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">申领审核层级</label>
                    <div class="layui-input-block big">
                        <input type="text"  name="applyAudit"  lay-verify="required"  autocomplete="on" class="layui-input big" disabled="disabled"/>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">入库审核层级</label>
                    <div class="layui-input-block big">
                        <input type="text" name="storageAudit"  lay-verify="required"  autocomplete="on" class="layui-input big" disabled="disabled"/>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">领用审核层级</label>
                    <div class="layui-input-block big">
                        <input type="text" name="receiveAudit"  lay-verify="required"  autocomplete="on" class="layui-input big" disabled="disabled"/>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">是否走借用流程</label>
                    <div class="layui-input-block">
                        <input type="text" name="isNeedReturn"  lay-verify="required"  autocomplete="on" class="layui-input" disabled="disabled"/>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <input type="text" name="info"  lay-verify="required"  autocomplete="on" class="layui-input" disabled="disabled"/>
                    </div>
                </div>
            </div>
        </form>
        <table class="layui-hide add_progress" id="assetsList" lay-filter="assetsList"></table>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/assetsClassificationDetail.js"></script>
</body>

</html>