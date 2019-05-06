<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>孙项目详情</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/construction/expand.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js"></script>
    <!--孙项目详情列表专属表头长度-->
    <style>
        .layui-form-label {
            width: 105px;
        }

        .layui-input-block {
            margin-left: 135px;
        }
    </style>
</head>

<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>（孙项目名称）详情</legend>
        </fieldset>
        <form class="layui-form detail_item" action="" lay-filter="detailgrandsonproject">
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">孙项目名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="projectName" autocomplete="off" value="${grandSonProject.projectName}" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">所属子项目</label>
                    <div class="layui-input-block">
                        <input type="text" name="sonProjectName" autocomplete="off" value="${grandSonProject.sonProjectName}" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">所属父项目</label>
                    <div class="layui-input-block">
                        <input type="text" name="parentProjectName" autocomplete="off" value="${grandSonProject.parentProjectName}" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">当前进度</label>
                    <div class="layui-input-block">
                        <input type="text" name="progress" autocomplete="off" value="${grandSonProject.progress}" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">创建人</label>
                    <div class="layui-input-block">
                        <input type="text" name="createUser" autocomplete="off" value="${grandSonProject.createUser}" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">创建人所属部门</label>
                    <div class="layui-input-block">
                        <input type="text" name="unitName" autocomplete="off" value="${grandSonProject.unitName}" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">创建时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="createTime" autocomplete="off" value="${grandSonProject.createTime}" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">经费预算</label>
                    <div class="layui-input-block">
                        <input type="text" name="budget" autocomplete="off" value="${grandSonProject.budget}" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">项目实施时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="projectImplementTime" autocomplete="off" value="${grandSonProject.projectImplementTime}" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
            </div>
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
                    <%--<div class="layui-input-block">--%>
                        <%--<a class="layui-input file_download" href="tagbox.js" download="tagbox.js" title="下载">新提交文件名称.txt</a>--%>
                    <%--</div>--%>
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
                    <%--<div class="layui-input-block">--%>
                        <%--<a class="layui-input file_download" href="tagbox.js" download="tagbox.js" title="下载">新提交文件名称.txt</a>--%>
                    <%--</div>--%>
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
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">招标纪要实际金额</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input" name="tenderActualAmount" autocomplete="off" value="${grandSonProject.tenderActualAmount}" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
            </div>
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
                <div class="layui-row">
                    <div class="layui-col-lg12">
                        <label class="layui-form-label">实际金额</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input" name="acceptanceActualAmount" autocomplete="off" value="${grandSonProject.acceptanceActualAmount}" class="layui-input" disabled="disabled" readonly="readonly" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">结余</label>
                    <div class="layui-input-block">
                        <input type="text" name="dgpsurplus" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/construction/detailGrandsonProject.js"></script>
</body>


</html>