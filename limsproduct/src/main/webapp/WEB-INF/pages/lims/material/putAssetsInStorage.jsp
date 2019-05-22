<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>发起物资入库详情</title>
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
            <legend >发起物资入库详情</legend>
        </fieldset>
        <form class="layui-form detail_item" action="" lay-filter="assetsApplyDetail">
            <input id="applyId" name="applyId" type="hidden" value="${id}"/>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">入库日期</label>
                    <div class="layui-input-block">
                        <input type="text" name="date"  autocomplete="on" class="layui-input" disabled="disabled"/>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">申购人</label>
                    <div class="layui-input-block">
                        <input type="text" name="applicantUserName"   autocomplete="on" class="layui-input" disabled="disabled"/>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">学院</label>
                    <div class="layui-input-block">
                        <select name="academyNumber" id="academyNumber"  disabled="disabled" readonly="readonly">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">部门</label>
                    <div class="layui-input-block">
                        <select name="department" id="department"   readonly="readonly">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物资类别</label>
                    <div class="layui-input-block">
                        <select name="goodsCategory" id="goodsCategory"  disabled="disabled" readonly="readonly">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">所选物资金额</label>
                    <div class="layui-input-block">
                        <input type="text" id= "totalPrice" name="totalPrice" disabled="disabled" />
                    </div>
                </div>
            </div>
            <%--<div class="layui-row">--%>
                <%--&lt;%&ndash;<div class="layui-col-lg6">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<label class="layui-form-label">发票号</label>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<div class="layui-input-block">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<input type="text" id= "invoiceNumber" name="invoiceNumber" />&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="layui-col-lg6">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<label class="layui-form-label">物品柜</label>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<div class="layui-input-block">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<select name="cabinet" id="cabinet" lay-verify="required" >&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<option value=""></option>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</select>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--</div>--%>
            <div class="layui-col-lg12">
                <label class="layui-form-label">入库单</label>
                <div class="layui-input-block mix_uploadbox">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn" id="assetsImage">
                            <i class="layui-icon">&#xe67c;</i>上传入库单图片
                        </button>
                        <div class="layui-upload-list">
                            <table class="layui-table">
                                <thead>
                                <tr>
                                    <th>文件名</th>
                                    <th>大小</th>
                                    <th>状态</th>
                                    <th>缩略图</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="paclist"></tbody>
                            </table>
                            <div class="upload_btn">
                                <button type="button" class="layui-btn" id="pacbtn">开始上传</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="putAssetsInStorage">发起入库</button>
                </div>
            </div>
        </form>
        <table class="layui-hide add_progress" id="assetsList" lay-filter="assetsList"></table>
        <script type="text/html" id="parentbar">
            <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="choose">添加物品柜等信息</a>
        </script>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/putAssetsInStorage.js"></script>
</body>

</html>