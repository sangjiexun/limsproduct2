<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>编辑子项目列表</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/multiple-select.css" media="all">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/construction/expand.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js"></script>
    <!--子项目列表专属表头长度-->
    <style>
        .layui-form-label {
            width: 100px;
        }

        .layui-input-block {
            margin-left: 130px;
        }
        .multiSelect{
            display: none;
        }
    </style>
</head>

<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>编辑子项目列表</legend>
        </fieldset>
        <form class="layui-form" action="" lay-filter="sonproject">
            <div class="layui-row layui-col-space10 layui-form-item">
                <input  type="hidden" name="parentProjectId" value="${parentId}"/>
                <input  type="hidden" name="id" value="${sonProject.id}"/>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">子项目名称：</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input" value="${sonProject.projectName}" id="projectName" name="projectName" lay-verify="required" autocomplete="on" placeholder="请填写子项目名称" />
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">所属父项目：</label>
                    <div class="layui-input-block">
                        <select name="parentProjectId" id="parentProjectId" lay-verify="required" lay-search>
                            <c:forEach items="${parentProjects}" var="current">
                                <c:if test="${current.id == parentId}">
                                    <option value="${current.id}" selected="selected">${current.projectName }</option>
                                </c:if>
                                <option value="${current.id}">${current.projectName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg4">
                    <label class="layui-form-label">所属学院：</label>
                    <div class="layui-input-block">
                        <select name="academyNumber" id="academyNumber" lay-verify="required" lay-search multiple lay-tools lay-omit>
                            <c:forEach items="${academies}" var="current">
                                <c:if test="${current.academyNumber == sonProject.academyName}">
                                    <option value="${current.academyNumber}" selected="selected">${current.academyName }</option>
                                </c:if>
                                <option value="${current.academyNumber}">${current.academyName}</option>
                            </c:forEach>
                            <%--<option value=""></option>--%>
                            <%--<option value="5">学院1</option>--%>
                            <%--<option value="6">学院2</option>--%>
                            <%--<option value="7">学院3</option>--%>
                            <%--<option value="8">学院4</option>--%>
                            <%--<option value="9">学院5</option>--%>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg4">
                    <label class="layui-form-label">经费预算：</label>
                    <div class="layui-input-block">
                        <input type="number" class="layui-input" value="${sonProject.budget}" id="budget" name="budget" lay-verify="required" autocomplete="on" placeholder="请填写子项目经费预算" />
                    </div>
                </div>
                <div class="layui-col-lg4 date_item">
                    <label class="layui-form-label">预算结算时间：</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input" name="budgetBalanceTime" id="budgetBalanceTime" value="${sonProject.budgetBalanceTime}" lay-verify="required" autocomplete="off" placeholder="请选择子项目预算结算时间"/>
                    </div>
                </div>
                <div class="layui-col-lg4 time_item">
                    <label class="layui-form-label">项目实施时间：</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input" name="projectImplementTime" id="projectImplementTime" value="${sonProject.projectImplementTime}" lay-verify="required" autocomplete="off" placeholder="请选择子项目实施时间"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="saveSonProject">保存</button>
                    <button class="layui-btn" lay-submit lay-filter="submitSonProject">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/construction/editSonProject.js"></script>
</body>

</html>