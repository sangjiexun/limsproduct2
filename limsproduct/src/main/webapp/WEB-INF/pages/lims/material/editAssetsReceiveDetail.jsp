<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>新建/编辑物资申领详情</title>
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
            <legend >新建/编辑物资申领详情</legend>
        </fieldset>
        <form class="layui-form detail_item" action="" lay-filter="assetsReceiveDetail">
            <input id="id" name="id" type="hidden" value="${id}"/>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">申领物资类别</label>
                    <div class="layui-input-block">
                        <select name="goodsCategory" id="goodsCategory" lay-verify="required"  lay-filter="goodsCategory">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">申领发起日期</label>
                    <div class="layui-input-block">
                        <input type="text" name="applicationTime"  lay-verify="required"  autocomplete="on" class="layui-input" disabled="disabled"/>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">申领人</label>
                    <div class="layui-input-block">
                        <input type="text" name="username"  lay-verify="required"  autocomplete="on" class="layui-input" disabled="disabled"/>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">学院</label>
                    <div class="layui-input-block">
                        <select name="academyNumber" id="academyNumber" lay-verify="required" >
                            <option value=""></option>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">部门</label>
                    <div class="layui-input-block">
                        <select name="department" id="department"  >
                            <option value=""></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-row" id="useAndReturn">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">申领使用时间</label>
                    <div class="layui-input-block">
                        <input type="text" id= "beginTime" name="beginTime"   placeholder="yyyy-MM-dd HH:mm:ss" autocomplete="on" class="layui-input" />
                    </div>
                </div>
                <div class="layui-col-lg6" id="return">
                    <label class="layui-form-label">预计归还时间</label>
                    <div class="layui-input-block">
                        <input type="text" id= "endTime" name="endTime"  placeholder="yyyy-MM-dd HH:mm:ss" autocomplete="on" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">申领用途</label>
                    <div class="layui-input-block">
                        <textarea type="text" name="purpose" autocomplete="off" class="layui-textarea" placeholder="请填写申领用途"></textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="saveAssetsReceiveDetail">保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
        <div id="item">
        <button data-method="addAssetsReceiveItem" class="layui-btn layui-btn-xs layui-btn-normal add_btn">新增物资申领条目</button>
        <table class="layui-hide add_progress" id="assetsList" lay-filter="assetsList"></table>
        <script type="text/html" id="parentbar">
            <%--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>--%>
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </script>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/editAssetsReceiveDetail.js"></script>
</body>

</html>