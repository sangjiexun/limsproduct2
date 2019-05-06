<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>子项目列表</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/construction/expand.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js"></script>
</head>

<body>
<input id="contextPath" value="${pageContext.request.contextPath}" style="display: none;"/>

<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>子项目列表</legend>
            <span class="layui-breadcrumb breadcrumb_top" lay-separator="|">
				        <a href="parentProject.html" class="">父项目</a><span lay-separator="">|</span>
					    <a href="sonProject.html" class="breadcrumb_select">子项目</a><span lay-separator="">|</span>
					    <a href="grandSonProject.html">孙项目</a>
					</span>
        </fieldset>
        <%--<span class="layui-badge-rim" id="tagtips" onmouseover="showtagtips('tagtips')">共5个相关子项目</span>--%>
        <button data-method="newson" class="layui-btn layui-btn-xs layui-btn-normal new_btn">新建一个子项目</button>
        <%--<div class="tagbox_toggle_btn">--%>
            <%--<span>收起筛选<i class="layui-icon layui-icon-up"></i></span>--%>
            <%--<span class="layui-hide">显示筛选<i class="layui-icon layui-icon-down"></i></span>--%>
        <%--</div>--%>
        <%--<blockquote class="layui-elem-quote layui-quote-nm tag_box">--%>
            <%--<div class="tag_line">--%>
                <%--<div class="tag_tit">父项目：</div>--%>
                <%--<div class="layui-tab layui-tab-brief tag_text">--%>
                    <%--<ul class="layui-tab-title">--%>
                        <%--<li class="normal_tag"><span class="layui-this">全部</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件1</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件2</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件3</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件4</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件5</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件6</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件7</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件8</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件9</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件10</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件11</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件12</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件13</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件14</span></li>--%>
                        <%--<li class="normal_tag"><span>父项目条件15</span></li>--%>
                        <%--<li class="item_select">--%>
                            <%--<div class="layui-nav layui-inline tag_nav">--%>
                                <%--<div class="layui-nav-item" lay-unselect="">--%>
                                    <%--<a href="javascript:void(0);">父项目条件栏目</a>--%>
                                    <%--<dl class="layui-nav-child">--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">父条件栏目1</a>--%>
                                        <%--</dd>--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">父条件栏目2</a>--%>
                                        <%--</dd>--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">父条件栏目3</a>--%>
                                        <%--</dd>--%>
                                    <%--</dl>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="tag_line">--%>
                <%--<div class="tag_tit">子项目：</div>--%>
                <%--<div class="layui-tab layui-tab-brief tag_text">--%>
                    <%--<ul class="layui-tab-title">--%>
                        <%--<li class="normal_tag"><span class="layui-this">全部</span></li>--%>
                        <%--<li class="normal_tag"><span>子项目条件1</span></li>--%>
                        <%--<li class="normal_tag"><span>子项目条件2</span></li>--%>
                        <%--<li class="normal_tag"><span>子项目条件3</span></li>--%>
                        <%--<li class="normal_tag"><span>子项目条件4</span></li>--%>
                        <%--<li class="normal_tag"><span>子项目条件5</span></li>--%>
                        <%--<li class="item_select">--%>
                            <%--<div class="layui-nav layui-inline tag_nav">--%>
                                <%--<div class="layui-nav-item" lay-unselect="">--%>
                                    <%--<a href="javascript:void(0);">子项目条件栏目</a>--%>
                                    <%--<dl class="layui-nav-child">--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">子条件栏目1</a>--%>
                                        <%--</dd>--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">子条件栏目2</a>--%>
                                        <%--</dd>--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">子条件栏目3</a>--%>
                                        <%--</dd>--%>
                                    <%--</dl>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</blockquote>--%>
        <div class="layui-collapse" lay-accordion="" lay-filter="sonProjectColl">
            <c:forEach items="${parentProjects}" var="curr" varStatus="cou">
                <div class="layui-colla-item">
                    <h2 class="layui-colla-title" id="${curr.id}">
                        <span>${curr.projectName}</span>
                        <span>预算：¥${curr.budget}</span>
                    </h2>
                    <c:if test="${cou.index == 0}">
                        <div class="layui-colla-content layui-show">
                    </c:if>
                    <c:if test="${cou.index != 0}">
                        <div class="layui-colla-content">
                    </c:if>
                        <table class="layui-hide add_progress" id="sonproject${curr.id}" lay-filter="sonhead"></table>
                        <script type="text/html" id="sonbar">
                            <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
                            <a class="layui-btn layui-btn-xs" lay-event="edit">项目管理</a>
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="newgrandson">新建孙项目</a>
                        </script>
                    <c:if test="${cou.index != 0}">
                        </div>
                    </c:if>
                    <c:if test="${cou.index == 0}">
                        </div>
                    </c:if>
                    <%--</div>--%>
                </div>
            </c:forEach>
            <%--<div class="layui-colla-item">--%>
                <%--<h2 class="layui-colla-title">--%>
                    <%--<span>父项目名称</span>--%>
                    <%--<span>预算：¥00.00</span>--%>
                <%--</h2>--%>
                <%--<div class="layui-colla-content layui-show">--%>
                    <%--<table class="layui-hide add_progress" id="sonproject" lay-filter="sonhead"></table>--%>
                    <%--<script type="text/html" id="sonbar">--%>
                        <%--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>--%>
                        <%--<a class="layui-btn layui-btn-xs" lay-event="edit">项目管理</a>--%>
                        <%--<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>--%>
                        <%--<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="newgrandson">新建孙项目</a>--%>
                    <%--</script>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-colla-item">--%>
                <%--<h2 class="layui-colla-title">--%>
                    <%--<span>父项目名称2</span>--%>
                    <%--<span>预算：¥00.00</span>--%>
                <%--</h2>--%>
                <%--<div class="layui-colla-content">--%>
                    <%--<p>22222</p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-colla-item">--%>
                <%--<h2 class="layui-colla-title">--%>
                    <%--<span>父项目名称3</span>--%>
                    <%--<span>预算：¥00.00</span>--%>
                <%--</h2>--%>
                <%--<div class="layui-colla-content">--%>
                    <%--<p>33333</p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-colla-item">--%>
                <%--<h2 class="layui-colla-title">--%>
                    <%--<span>父项目名称4</span>--%>
                    <%--<span>预算：¥00.00</span>--%>
                <%--</h2>--%>
                <%--<div class="layui-colla-content">--%>
                    <%--<p>44444</p>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/construction/sonProject.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/construction/tagbox.js"></script>
<script>
    $(function(){
        <c:forEach items="${parentProjects}" var="current">
        parentProjectIds.push(${current.id});
        </c:forEach>
        console.log(parentProjectIds);
    });
</script>
</body>

</html>