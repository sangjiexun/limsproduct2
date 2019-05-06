<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>父项目详情列表</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/construction/expand.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js"></script>
    <!--父项目详情列表专属表头长度-->
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
<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>（父项目名称）详情</legend>
        </fieldset>
        <form class="layui-form detail_item" action="" lay-filter="detailparentproject">
            <input id="parentProId" type="hidden"/>
            <div class="layui-row">
                <div class="layui-col-lg12">
                    <label class="layui-form-label">父项目名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="projectName" value="${parentProject.projectName}" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">创建人</label>
                    <div class="layui-input-block">
                        <input type="text" name="createUser" value="${parentProject.createUser}" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">创建人所属部门</label>
                    <div class="layui-input-block">
                        <input type="text" name="unitName" value="${parentProject.unitName}" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
            </div>
            <div class="layui-row">
                <div class="layui-col-lg6">
                    <label class="layui-form-label">创建时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="createTime" value="${parentProject.createTime}" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">经费预算</label>
                    <div class="layui-input-block">
                        <input type="text" name="budget" value="${parentProject.budget}" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    $(function(){
     <%--showDetail({--%>
         <%--parentProject: ${parentProject},--%>
     <%--})--%>
        <%--console.log(${parentProject});--%>
    });
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/construction/detailParentProject.js"></script>
</body>

</html>