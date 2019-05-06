<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>编辑孙项目列表</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/construction/expand.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js"></script>
    <!--孙项目列表专属表头长度-->
    <style>
        .layui-form-label {
            width: 128px;
        }

        .layui-input-block {
            margin-left: 158px;
        }
    </style>
</head>

<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <c:if test="${isEdit == 1}">
                <legend>编辑孙项目列表</legend>
            </c:if>
            <c:if test="${isEdit != 1}">
                <legend>新建孙项目列表</legend>
            </c:if>
        </fieldset>
        <form class="layui-form" action="" lay-filter="grandsonproject">
            <input  type="hidden" name="id" id="grandSonId" value="${grandSonProject.id}"/>
            <input  type="hidden" id="sonId" value="${sonId}"/>
            <input  type="hidden" id="parentId"  value="${parentId}"/>
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">孙项目名称：</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input" name="projectName" value="${grandSonProject.projectName}" lay-verify="required" autocomplete="on" placeholder="请填写孙项目名称" />
                    </div>
                </div>
            </div>
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">所属父项目：</label>
                    <div class="layui-input-block">
                        <select name="parentProjectId" id="parentProjectId" lay-verify="required" lay-filter="chooseParentProject" lay-search>
                            <c:forEach items="${parentProjects}" var="current">
                                <c:if test="${current.id == parentId}">
                                    <option value="${current.id}" selected="selected">${current.projectName }</option>
                                </c:if>
                                <c:if test="${current.id != parentId}">
                                <option value="${current.id}">${current.projectName}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">所属子项目：</label>
                    <div class="layui-input-block">
                        <select name="sonProjectId" id="sonProjectId" lay-verify="required" lay-filter="chooseSonProject" lay-search>
                            <%--<option value="-1">请先选择父项目!</option>--%>
                            <option value="${grandSonProject.sonProjectId}">${grandSonProject.sonProjectName}</option>
                            <%--<option value="0">子项目名称1</option>--%>
                            <%--<option value="1">子项目名称2</option>--%>
                            <%--<option value="2">子项目名称3</option>--%>
                            <%--<option value="3">子项目名称4</option>--%>
                            <%--<option value="4">子项目名称5</option>--%>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg6 time_item">
                    <label class="layui-form-label">项目实施时间：</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input" id="projectImplementTime" name="projectImplementTime" value="${grandSonProject.projectImplementTime}" lay-verify="required" autocomplete="off" placeholder="请选择孙项目实施时间">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">经费预算：</label>
                    <div class="layui-input-block">
                        <input type="number" class="layui-input" name="budget" value="${grandSonProject.budget}" lay-verify="required" autocomplete="on" placeholder="请填写孙项目经费预算" />
                    </div>
                </div>
            </div>
            <c:if test="${isEdit == 1}">
            <div class="detail_item">
                <%--<div class="layui-row">--%>
                    <%--<div class="layui-col-lg12">--%>
                        <%--<label class="layui-form-label">孙项目名称</label>--%>
                        <%--<div class="layui-input-block">--%>
                            <%--<input type="text" name="projectNameDetail" autocomplete="off" value="${grandSonProject.projectName}" class="layui-input" disabled="disabled" readonly="readonly" />--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-row">--%>
                    <%--<div class="layui-col-lg6">--%>
                        <%--<label class="layui-form-label">所属子项目</label>--%>
                        <%--<div class="layui-input-block">--%>
                            <%--<input type="text" name="sonProjectNameDetail" value="${grandSonProject.sonProjectName}" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-lg6">--%>
                        <%--<label class="layui-form-label">所属父项目</label>--%>
                        <%--<div class="layui-input-block">--%>
                            <%--<input type="text" name="parentProjectNameDetail" value="${grandSonProject.parentProjectName}" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-row">--%>
                    <%--<div class="layui-col-lg6">--%>
                        <%--<label class="layui-form-label">经费预算</label>--%>
                        <%--<div class="layui-input-block">--%>
                            <%--<input type="text" name="budgetDetail" value="${grandSonProject.budget}" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-lg6">--%>
                        <%--<label class="layui-form-label">项目实施时间</label>--%>
                        <%--<div class="layui-input-block">--%>
                            <%--<input type="text" name="projectImplementTimeDetail" value="${grandSonProject.projectImplementTime}" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="layui-row">
                    <div class="layui-col-lg12">
                        <label class="layui-form-label">相关文档</label>
                        <c:if test="${!empty grandSonProject.relatedDocuments}">
                            <c:forEach items="${grandSonProject.relatedDocuments}" var="map">
                                <div class="layui-input-block">
                                    <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty grandSonProject.relatedDocuments}">
                            <div class="layui-input-block"></div>
                        </c:if>
                    </div>
                </div>
                <div class="layui-row">
                    <div class="layui-col-lg12">
                        <label class="layui-form-label">论证报告</label>
                        <c:if test="${!empty grandSonProject.reportDocuments}">
                            <c:forEach items="${grandSonProject.reportDocuments}" var="map">
                                <div class="layui-input-block">
                                    <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty grandSonProject.reportDocuments}">
                            <div class="layui-input-block"></div>
                        </c:if>
                    </div>
                </div>
                <div class="layui-row">
                    <div class="layui-col-lg12">
                        <label class="layui-form-label">采购文件</label>
                        <c:if test="${!empty grandSonProject.purchaseDocuments}">
                            <c:forEach items="${grandSonProject.purchaseDocuments}" var="map">
                                <div class="layui-input-block">
                                    <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty grandSonProject.purchaseDocuments}">
                            <div class="layui-input-block"></div>
                        </c:if>
                    </div>
                </div>
                <div class="layui-row">
                    <div class="layui-col-lg12">
                        <label class="layui-form-label">相关合同</label>
                        <c:if test="${!empty grandSonProject.relatedContracts}">
                            <c:forEach items="${grandSonProject.relatedContracts}" var="map">
                                <div class="layui-input-block">
                                    <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty grandSonProject.relatedContracts}">
                            <div class="layui-input-block"></div>
                        </c:if>
                    </div>
                </div>
                <%--<div class="layui-row">--%>
                    <%--<div class="layui-col-lg12">--%>
                        <%--<label class="layui-form-label">招标纪要实际金额</label>--%>
                        <%--<div class="layui-input-block">--%>
                            <%--<input type="text" class="layui-input" name="tenderActualAmountDetail" value="${grandSonProject.tenderActualAmount}" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="layui-row">
                    <div class="layui-col-lg12">
                        <label class="layui-form-label">验收申请表</label>
                        <c:if test="${!empty grandSonProject.acceptanceDocuments}">
                            <c:forEach items="${grandSonProject.acceptanceDocuments}" var="map">
                                <div class="layui-input-block">
                                    <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty grandSonProject.acceptanceDocuments}">
                            <div class="layui-input-block"></div>
                        </c:if>
                    </div>
                </div>
                <%--<div class="layui-row">--%>
                    <%--<div class="layui-col-lg12">--%>
                        <%--<label class="layui-form-label">实际金额</label>--%>
                        <%--<div class="layui-input-block">--%>
                            <%--<input type="text" class="layui-input" value="${grandSonProject.acceptanceActualAmount}" name="acceptanceActualAmountDetail" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>

            <!--相关文档开始-->
                <c:if test="${grandSonProject.progressStage==1}">
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">相关文档：</label>
                    <div class="layui-input-block mix_uploadbox">
                        <div class="layui-upload">
                            <div class="layui-upload-drag" id="relateddocuments">
                                <i class="layui-icon"></i>
                                <p>点击选择文件，或将文件拖拽到此处</p>
                            </div>
                            <div class="layui-upload-list">
                                <table class="layui-table">
                                    <thead>
                                    <tr>
                                        <th>文件名</th>
                                        <th>大小</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="rdlist"></tbody>
                                </table>
                                <div class="upload_btn">
                                    <button type="button" class="layui-btn" id="rdbtn">开始上传</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                </c:if>
            <!--论证报告开始-->
            <c:if test="${grandSonProject.progressStage==2}">
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">论证报告：</label>
                    <div class="layui-input-block mix_uploadbox">
                        <div class="layui-upload">
                            <div class="layui-upload-drag" id="argumentationreport">
                                <i class="layui-icon"></i>
                                <p>点击选择文件，或将文件拖拽到此处</p>
                            </div>
                            <div class="layui-upload-list">
                                <table class="layui-table">
                                    <thead>
                                    <tr>
                                        <th>文件名</th>
                                        <th>大小</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="arlist"></tbody>
                                </table>
                                <div class="upload_btn">
                                    <button type="button" class="layui-btn" id="arbtn">开始上传</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:if>
            <!--采购文件开始-->
            <c:if test="${grandSonProject.progressStage==3}">
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">采购文件：</label>
                    <div class="layui-input-block mix_uploadbox">
                        <div class="layui-upload">
                            <div class="layui-upload-drag" id="procurementdocuments">
                                <i class="layui-icon"></i>
                                <p>点击选择文件，或将文件拖拽到此处</p>
                            </div>
                            <div class="layui-upload-list">
                                <table class="layui-table">
                                    <thead>
                                    <tr>
                                        <th>文件名</th>
                                        <th>大小</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="pdlist"></tbody>
                                </table>
                                <div class="upload_btn">
                                    <button type="button" class="layui-btn" id="pdbtn">开始上传</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:if>
            <!--相关合同开始-->
            <c:if test="${grandSonProject.progressStage==4}">
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">相关合同：</label>
                    <div class="layui-input-block mix_uploadbox">
                        <div class="layui-upload">
                            <div class="layui-upload-drag" id="relatedcontracts">
                                <i class="layui-icon"></i>
                                <p>点击选择文件，或将文件拖拽到此处</p>
                            </div>
                            <div class="layui-upload-list">
                                <table class="layui-table">
                                    <thead>
                                    <tr>
                                        <th>文件名</th>
                                        <th>大小</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="rclist"></tbody>
                                </table>
                                <div class="upload_btn">
                                    <button type="button" class="layui-btn" id="rcbtn">开始上传</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:if>
            <%--<c:if test="${grandSonProject.progressStage==5&&grandSonProject.progressState!=1&&grandSonProject.progressState!=2}">--%>
            <c:if test="${grandSonProject.progressStage==5}">
            <!--验收申请表开始-->
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">验收申请表：</label>
                    <div class="layui-input-block mix_uploadbox">
                        <div class="layui-upload">
                            <div class="layui-upload-drag" id="acceptanceapplicationform">
                                <i class="layui-icon"></i>
                                <p>点击选择文件，或将文件拖拽到此处</p>
                            </div>
                            <div class="layui-upload-list">
                                <table class="layui-table">
                                    <thead>
                                    <tr>
                                        <th>文件名</th>
                                        <th>大小</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="aaflist"></tbody>
                                </table>
                                <div class="upload_btn">
                                    <button type="button" class="layui-btn" id="aafbtn">开始上传</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:if>
            </c:if>
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg4">
                    <label class="layui-form-label">招标纪要实际金额：</label>
                    <div class="layui-input-block">
                        <input type="number" class="layui-input" name="tenderActualAmount" value="${grandSonProject.tenderActualAmount}" lay-verify="required" autocomplete="on" placeholder="请填写招标纪要实际金额" />
                    </div>
                </div>
            </div>
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg4">
                    <label class="layui-form-label">实际金额：</label>
                    <div class="layui-input-block">
                        <input type="number" class="layui-input" name="acceptanceActualAmount" value="${grandSonProject.acceptanceActualAmount}" lay-verify="required" autocomplete="on" placeholder="请填写实际金额" />
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="saveGrandsonProject">保存</button>
                    <button class="layui-btn" lay-submit lay-filter="submitGrandsonProject">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/construction/editGrandsonProject.js"></script>
</body>

</html>