<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>材料入库单</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/item.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/html2canvas.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/jsPdf.debug.js"></script>
    <style>
        .layui-form-label {
            width: 100px;
        }

        .layui-input-block {
            margin-left: 130px;
        }
        .layui-input-block input{
            margin-top: 7px;
        }
    </style>
</head>

<body>
<button id="renderPdf">导出到PDF</button>
<input type="hidden" id="appId" value="${id}">
<div class="title">
    <span>材料入库单</span>
</div>
<div class="top">
    <div class="intro">
        <label>售出单位:</label>
        <span id="supplier"></span>
    </div>
    <div class="intro">
        <label>采购日期:</label>
        <span id="applyDate"></span>
    </div>
    <div class="intro">
        <label>入库日期:</label>
        <span id="auditDate"></span>
    </div>
</div>
<table border="2">
    <thead>
    <tr>
        <th>名称</th>
        <th>型号及规格</th>
        <th>单位</th>
        <th>数量</th>
        <th>单价</th>
        <th>金额</th>
        <th>发票号</th>
        <th>备注</th>
    </tr>
    </thead>
    <tbody id="itemList"></tbody>
</table>
<div class="bottom">
    <div class="intro">
        <label>科长（审核）:</label>
        <span ></span>
    </div>
    <div class="intro">
        <label>验收:</label>
        <span id="auditUser"></span>
    </div>
    <div class="intro">
        <label>采购人:</label>
        <span id="appUser"></span>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/assetsInStorageCheckList.js"></script>
</body>

</html>