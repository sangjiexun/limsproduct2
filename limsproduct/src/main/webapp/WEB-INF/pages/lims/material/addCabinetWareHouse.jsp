<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>选择物品柜</title>
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
            <legend >选择物品柜</legend>
        </fieldset>
        <form class="layui-form detail_item" action="" lay-filter="addAssets">
            <input type="hidden" name="id" id="id" value="${id}" />
            <input type="hidden" name="cabinetId" id="cabinetId" value="${cabinetId}" />
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物品柜门编号</label>
                    <div class="layui-input-block">
                        <input type="text" name="wareHouseCode" id="wareHouseCode"   autocomplete="on" class="layui-input" />
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物品柜门名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="wareHouseName" id="wareHouseName" autocomplete="on" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="saveCabinetWareHouse">确定</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/addCabinetWareHouse.js"></script>
</body>

</html>