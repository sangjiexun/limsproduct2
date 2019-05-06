<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>编辑物资名录</title>
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
            <legend >新建/编辑物资名录</legend>
        </fieldset>
        <form class="layui-form detail_item" action="" lay-filter="assetsDetail">
            <input id="id" name="id" type="hidden" value="${id}"/>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">物品分类※</label>
                    <div class="layui-input-block">
                        <select name="kind" id="kind" lay-verify="required" lay-filter="kind">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物品名称※</label>
                    <div class="layui-input-block">
                        <input type="text" name="name"  lay-verify="required" placeholder="请填写物品名称" autocomplete="on" class="layui-input" />
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">型号与规格※</label>
                    <div class="layui-input-block">
                        <input type="text" name="type" lay-verify="required" placeholder="请填写物品型号与规格" autocomplete="on" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">单位※</label>
                    <div class="layui-input-block">
                        <input type="text" name="unit"  lay-verify="required" placeholder="请填写物品单位" autocomplete="on" class="layui-input" />
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">参考单价</label>
                    <div class="layui-input-block">
                        <input type="text" name="price"  placeholder="请填写参考单价" autocomplete="on" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">推荐厂家</label>
                    <div class="layui-input-block">
                        <input type="text" name="factory"  placeholder="请填写推荐厂家" autocomplete="on" class="layui-input" />
                    </div>
                </div>
                <div class="layui-col-lg6" >
                    <label class="layui-form-label">功能描述</label>
                    <div class="layui-input-block">
                        <input type="text" name="function"  placeholder="请填写功能描述" autocomplete="on" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6" id="cas">
                    <label class="layui-form-label">cas号※</label>
                    <div class="layui-input-block">
                        <input type="text" name="cas"  placeholder="请填写cas号" autocomplete="on" class="layui-input" />
                    </div>
                </div>
            </div>
            <div class="layui-col-lg12">
                <label class="layui-form-label">物资图片</label>
                <div class="layui-input-block mix_uploadbox">
                    <div class="layui-upload">
                        <button type="button" class="layui-btn" id="assetsImage">
                            <i class="layui-icon">&#xe67c;</i>上传图片
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
                    <button class="layui-btn" lay-submit lay-filter="saveAssetsDetail">保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/editAssetsDetail.js"></script>
</body>

</html>