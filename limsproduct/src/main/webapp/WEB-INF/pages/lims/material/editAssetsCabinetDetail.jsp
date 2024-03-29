<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>新建物资类别</title>
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
        .lable{
            padding: 9px 0px;
            width: 100%!important;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend >新建/编辑存放地点</legend>
        </fieldset>
        <form class="layui-form layui-from-pane detail_item" lay-filter="assetCabinetDetail" id="assetCabinetDetail">
            <input id="id" name="id" type="hidden" value="${id}"/>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">类型※</label>
                    <div class="layui-input-block">
                        <select name="type" id="type" lay-verify="required" lay-filter="type">
                            <option value=""></option>
                            <option value="1">普通存放点</option>
                            <option value="2">智能物品柜</option>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">存放地点</label>
                    <div class="layui-input-block">
                        <input type="text" name="location" id="location" placeholder="请输入" autocomplete="off" class="layui-input" lay-verify="required">
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">仓库编号</label>
                    <div class="layui-input-block">
                        <input type="text" name="cabinetCode" id="cabinetCode" placeholder="请输入" autocomplete="off" class="layui-input" >
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">仓库名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="cabinetName" id="cabinetName" placeholder="请输入" autocomplete="off" class="layui-input" lay-verify="required">
                    </div>
                </div>
            </div>
            <div class="layui-row" id="hardware">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">所属物联服务器</label>
                    <div class="layui-input-block">
                        <select name="serverId" id="serverId" lay-filter="type">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物联IP</label>
                    <div class="layui-input-block">
                        <input type="text" name="hardwareIp" id="hardwareIp" placeholder="请输入" autocomplete="off" class="layui-input" >
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="saveAssetCabinet">保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
        <div id="item">
            <button data-method="addCabinetWareHouse" class="layui-btn layui-btn-xs layui-btn-normal add_btn">新增智能柜门信息</button>
            <table class="layui-hide add_progress" id="cabinetWareHouseList" lay-filter="cabinetWareHouseList"></table>
            <script type="text/html" id="parentbar">
                <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            </script>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/editAssetsCabinetDetail.js"></script>
</body>
</html>
