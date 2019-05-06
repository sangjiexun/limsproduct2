<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>物资记录详情</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/construction/expand.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js"></script>
    <style>
        .layui-form-label {
            width: 100px;
            margin-left:auto;
            margin-right:auto;
            text-align:center;
        }
        .layui-input-block {
            margin-left: 130px;
        }
        .layui-input-block input{
            margin-top: 7px;
            margin-left:auto;
            margin-right:auto;
            text-align:center;
        }
    </style>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>物资记录详情</legend>
        </fieldset>

        <form class="layui-form detail_item" id="assetCabinetRecordDetail" lay-filter="assetCabinetRecordDetail">
            <input id="type" name="type" type="hidden" value="${type}" />
            <input id="assetId" name="assetId" type="hidden" value="${assetId}"/>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物资名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="cname" autocomplete="off" class="layui-input" readonly="readonly" />
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">CAS号</label>
                    <div class="layui-input-block">
                        <input type="text" name="cas" autocomplete="off" class="layui-input" readonly="readonly" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg4">
                    <label class="layui-form-label">物资类别</label>
                    <div class="layui-input-block">
                        <input type="text" name="categroy" autocomplete="off" class="layui-input" readonly="readonly" />
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">计量单位</label>
                    <div class="layui-input-block">
                        <input type="text" name="unit" autocomplete="off" class="layui-input" readonly="readonly" />
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">物资规格</label>
                    <div class="layui-input-block">
                        <input type="text" name="specifications" autocomplete="off" class="layui-input" readonly="readonly" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">生产厂家</label>
                    <div class="layui-input-block">
                        <input type="text" name="factory" autocomplete="off" class="layui-input"  readonly="readonly" />
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">参考单价</label>
                    <div class="layui-input-block">
                        <input type="text" name="price" autocomplete="off" class="layui-input" readonly="readonly" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">功能描述</label>
                    <div class="layui-input-block">
                        <input type="textarea " name="function" autocomplete="off" class="layui-textarea" readonly="readonly"/>
                    </div>
                </div>
            </div>
        </form>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top:25px">
            <legend style="font-size: 16px!important;">物资数量信息</legend>
        </fieldset>
        <div class="layui-btn-group layui-hide" id="add">
            <a class="layui-btn layui-btn-sm" id="addAssetRecord">添加</a>
        </div>
        <table class="layui-hide" id="assetCabinetRecordDetailTable"  lay-filter="assetCabinetRecordDetailTable"></table>
        <script type="text/html" id="assetCabinetRecordDetailToolBar">
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        </script>
    </div>
</div>

<div class="layui-layout layui-layout-admin" id="addForm" style="display: none">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>物资记录</legend>
        </fieldset>
        <form class="layui-form detail_item" id="addAssetCabinetRecord" lay-filter="addAssetCabinetRecord">
            <input id="id" name="id" type="hidden" value="" />
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">物资名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="cname" autocomplete="off" class="layui-input" readonly="readonly" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">物品柜</label>
                    <div class="layui-input-block">
                        <select name="cabinetId" id="cabinetId" lay-verify="required"  lay-filter="cabinetId" lay-search="">
                            <option value="">搜索选择物品柜</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">物资数量</label>
                    <div class="layui-input-block">
                        <input type="text" name="stockNumber" id="stockNumber" autocomplete="off" class="layui-input" placeholder="请输入物资数量" lay-verify="required|number|quantity"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="saveAssetsCabinetRecord" >保存</button>
                    <%--<button type="reset" class="layui-btn layui-btn-primary">重置</button>--%>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/assetCabinetRecordDetail.js"></script>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/addAssetCabinetRecord.js"></script>--%>
</body>
</html>
