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
            <legend >新建/编辑物资类别</legend>
        </fieldset>
        <form class="layui-form layui-from-pane detail_item" lay-filter="assetClassificationDetail" id="assetClassificationDetail">
            <input id="id" name="id" type="hidden" value="${id}"/>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物资类别中文名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="cname" id="cname" placeholder="请输入" autocomplete="off" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物资类别英文名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="ename" id="ename" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <%--申领审核--%>
            <div class="layui-row" >
                <div class="layui-col-lg12">
                    <label class="layui-form-label lable">申购审核层级设置</label>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">需要几级审核</label>
                    <div class="layui-input-block">
                        <select name="applyAudit" lay-filter="showAuditLevelApply" id="auditLevelApply">
                            <option value="-2">请选择</option>
                            <option value="1">一级审核</option>
                            <option value="2">二级审核</option>
                            <option value="3">三级审核</option>
                            <option value="4">四级审核</option>
                            <option value="5">五级审核</option>
                        </select>
                    </div>
                </div>
            </div>
             <div class="layui-row" id="auditLevelApplyName" style="display: none">
                <div class="layui-input-inline">
                    <label class="layui-form-label">一级审核</label>
                    <div class="layui-input-block">
                        <select name="applyName1" lay-filter="aihao" id="applyName1">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">二级审核</label>
                    <div class="layui-input-block">
                        <select name="applyName2" lay-filter="aihao" id="applyName2">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">三级审核</label>
                    <div class="layui-input-block">
                        <select name="applyName3" lay-filter="aihao" id="applyName3">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">四级审核</label>
                    <div class="layui-input-block">
                        <select name="applyName4" lay-filter="aihao" id="applyName4">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">五级审核</label>
                    <div class="layui-input-block">
                        <select name="applyName5" lay-filter="aihao" id="applyName5">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
            </div>
            <%--入库审核--%>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label lable">入库审核层级设置</label>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">需要几级审核</label>
                    <div class="layui-input-block">
                        <select name="storageAudit" lay-filter="showAuditLevelStorage" id="auditLevelStorage">
                            <option value="-2">请选择</option>
                            <option value="1">一级审核</option>
                            <option value="2">二级审核</option>
                            <option value="3">三级审核</option>
                            <option value="4">四级审核</option>
                            <option value="5">五级审核</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-row" id="auditLevelStorageName" style="display: none">
                <div class="layui-input-inline">
                    <label class="layui-form-label">一级审核</label>
                    <div class="layui-input-block">
                        <select name="storageName1" lay-filter="aihao" id="storageName1">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">二级审核</label>
                    <div class="layui-input-block">
                        <select name="storageName2" lay-filter="aihao" id="storageName2">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">三级审核</label>
                    <div class="layui-input-block">
                        <select name="storageName3" lay-filter="aihao" id="storageName3">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">四级审核</label>
                    <div class="layui-input-block">
                        <select name="storageName4" lay-filter="aihao" id="storageName4">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">五级审核</label>
                    <div class="layui-input-block">
                        <select name="storageName5" lay-filter="aihao" id="storageName5">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
            </div>
            <%--领用审核--%>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label lable"vertical-align="middle">领用审核层级设置</label>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg-12">
                    <label class="layui-form-label">需要几级审核</label>
                    <div class="layui-input-block">
                        <select name="receiveAudit" lay-filter="showAuditLevelReceive" id="auditLevelReceive">
                            <option value="-2">请选择</option>
                            <option value="1">一级审核</option>
                            <option value="2">二级审核</option>
                            <option value="3">三级审核</option>
                            <option value="4">四级审核</option>
                            <option value="5">五级审核</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-row" id="auditLevelReceiveName" style="display: none">
                <div class="layui-input-inline">
                    <label class="layui-form-label">一级审核</label>
                    <div class="layui-input-block">
                        <select name="receiveName1" lay-filter="aihao" id="receiveName1">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">二级审核</label>
                    <div class="layui-input-block">
                        <select name="receiveName2" lay-filter="aihao" id="receiveName2">
                            <option value="-2">请选择</option>
                            <option value="TEACHER">导师</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">三级审核</label>
                    <div class="layui-input-block">
                        <select name="receiveName3" lay-filter="aihao" id="receiveName3">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">四级审核</label>
                    <div class="layui-input-block">
                        <select name="receiveName4" lay-filter="aihao" id="receiveName4">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
                <div class="layui-input-inline">
                    <label class="layui-form-label">五级审核</label>
                    <div class="layui-input-block">
                        <select name="receiveName5" lay-filter="aihao" id="receiveName5">
                            <option value="-2">请选择</option>
                            <option value="ASSETMANAGER">设备科</option>
                            <option value="OPEARTIONSECURITYMANAGEMENT">运行与安全管理科</option>
                            <option value="EXCENTERDIRECTOR">实验中心主任</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="llayui-col-lg12 layui-form-text">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea placeholder="请输入内容" class="layui-textarea" id="info" name="info"></textarea>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">是否走借用流程</label>
                    <div class="layui-input-block">
                        <input type="checkbox" name="isReturn" id="isNeedReturn" lay-skin="switch">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="saveAssetClassification">保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/newAssetsClassification.js"></script>
</body>
</html>
