<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>孙项目列表</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/construction/expand.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js"></script>
    <style>
        /*孙项目定制进度条高度*/
        tbody .layui-table-cell {
            height: 47px;
            line-height: 47px;
        }
        .tool-box {
            /*display: none;*/
        }
        .layui-table-tool-temp {
            display: none;
        }
    </style>
</head>

<body>
<input id="contextPath" value="${pageContext.request.contextPath}" style="display: none;"/>

<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>孙项目列表</legend>
            <span class="layui-breadcrumb breadcrumb_top" lay-separator="|">
				        <a href="parentProject.html">父项目</a><span lay-separator="">|</span>
					    <a href="sonProject.html">子项目</a><span lay-separator="">|</span>
					    <a href="grandSonProject.html" class="breadcrumb_select">孙项目</a>
					</span>
        </fieldset>
        <%--<span class="layui-badge-rim" id="tagtips" onmouseover="showtagtips('tagtips')">共5个相关孙项目</span>--%>
        <button data-method="newgrandson" class="layui-btn layui-btn-xs layui-btn-normal new_btn">新建一个孙项目</button>
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
            <%--<div class="tag_line">--%>
                <%--<div class="tag_tit">孙项目：</div>--%>
                <%--<div class="layui-tab layui-tab-brief tag_text">--%>
                    <%--<ul class="layui-tab-title">--%>
                        <%--<li class="normal_tag"><span class="layui-this">全部</span></li>--%>
                        <%--<li class="normal_tag"><span>孙项目条件1</span></li>--%>
                        <%--<li class="normal_tag"><span>孙项目条件2</span></li>--%>
                        <%--<li class="normal_tag"><span>孙项目条件3</span></li>--%>
                        <%--<li class="normal_tag"><span>孙项目条件4</span></li>--%>
                        <%--<li class="normal_tag"><span>孙项目条件5</span></li>--%>
                        <%--<li class="item_select">--%>
                            <%--<div class="layui-nav layui-inline tag_nav">--%>
                                <%--<div class="layui-nav-item" lay-unselect="">--%>
                                    <%--<a href="javascript:void(0);">孙项目条件栏目</a>--%>
                                    <%--<dl class="layui-nav-child">--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">孙条件栏目1</a>--%>
                                        <%--</dd>--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">孙条件栏目2</a>--%>
                                        <%--</dd>--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">孙条件栏目3</a>--%>
                                        <%--</dd>--%>
                                    <%--</dl>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li class="item_select">--%>
                            <%--<div class="layui-nav layui-inline tag_nav">--%>
                                <%--<div class="layui-nav-item" lay-unselect="">--%>
                                    <%--<a href="javascript:void(0);">孙项目条件栏目</a>--%>
                                    <%--<dl class="layui-nav-child">--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">孙条件栏目1</a>--%>
                                        <%--</dd>--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">孙条件栏目2</a>--%>
                                        <%--</dd>--%>
                                        <%--<dd>--%>
                                            <%--<a href="javascript:void(0);">孙条件栏目3</a>--%>
                                        <%--</dd>--%>
                                    <%--</dl>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</blockquote>--%>

        <div class="layui-tab layui-tab-brief" lay-filter="grandsonprojectTab">
            <ul class="layui-tab-title">
                <li class="layui-this">全部</li>
                <!--<li>已经审核<span class="layui-badge-dot"></span></li>-->
                <li>已经审核<span class="layui-badge">${audited}</span></li>
                <li>待审核<span class="layui-badge">${auditing}</span></li>
                <li>未审核<span class="layui-badge">${notAudit}</span></li>
            </ul>
            <div class="tool-box">
                <table>
                    <tr>
                        <td>
                            <span class="fl">综合查询：</span>
                            <input id="projectName" name="projectName" value="${projectName}" type="text"  placeholder="多字段查询" />
                        </td>
                        <td>
                            <span class="fl">创建时间：</span>
                            <input type="text" class="layui-input" id="time-range" value="${createTime}" autocomplete="off" placeholder="请选择时间范围">
                        </td>
                    </tr>
                </table>
            </div>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div class="layui-collapse" lay-accordion="" lay-filter="parentsProjectColl">
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
                                    <div class="layui-collapse" lay-accordion="" lay-filter="sonProjectColl">
                                        <c:forEach items="${curr.sonProjects}" var="soncurr" varStatus="soncou">
                                            <div class="layui-colla-item">
                                                <h2 class="layui-colla-title" id="${soncurr.id}">
                                                    <span>${soncurr.projectName}</span>
                                                    <span>所属学院：${soncurr.academyName}</span>
                                                    <span>经费预算：¥${soncurr.budget}</span>
                                                    <span>预算结算时间：${soncurr.budgetBalanceTime}</span>
                                                    <span>项目实施时间：${soncurr.projectImplementTime}</span>
                                                </h2>
                                                <c:if test="${soncou.index == 0}">
                                                    <div class="layui-colla-content layui-show">
                                                </c:if>
                                                <c:if test="${soncou.index != 0}">
                                                    <div class="layui-colla-content">
                                                </c:if>
                                                    <table class="layui-hide add_progress" id="grandsonproject${soncurr.id}" lay-filter="grandsonhead"></table>
                                                    <script type="text/html" id="progress">



                                                        <div class="project_progress">
                                                            {{# if(d.progressStage==1){ }}
                                                            {{# if(d.progressState==1){ }}
                                                            <div class=" complete">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class=" upload">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class=" notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class=" in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==2){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block complete">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==3){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block complete">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==4){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block complete">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==5){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>

                                                            <%--<div class="progress_block complete">--%>
                                                                <%--<i class="layui-icon"></i>--%>
                                                                <%--<span>验收申请表</span>--%>
                                                            <%--</div>--%>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block complete">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                        </div>

                                                    </script>
                                                    <script type="text/html" id="grandsonbar">
                                                        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
                                                        <a class="layui-btn layui-btn-xs" lay-event="edit">项目管理</a>
                                                        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                                                        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="examine">审核</a>
                                                    </script>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="layui-collapse" lay-accordion="" lay-filter="parentsProjectColl">
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
                                    <div class="layui-collapse" lay-accordion="" lay-filter="sonProjectCollAudited">
                                        <c:forEach items="${curr.sonProjects}" var="soncurr" varStatus="soncou">
                                        <div class="layui-colla-item">
                                            <h2 class="layui-colla-title" id="${soncurr.id}">
                                                <span>${soncurr.projectName}</span>
                                                <span>所属学院：${soncurr.academyName}</span>
                                                <span>经费预算：¥${soncurr.budget}</span>
                                                <span>预算结算时间：${soncurr.budgetBalanceTime}</span>
                                                <span>项目实施时间：${soncurr.projectImplementTime}</span>
                                            </h2>
                                            <c:if test="${soncou.index == 0}">
                                            <div class="layui-colla-content layui-show">
                                                </c:if>
                                                <c:if test="${soncou.index != 0}">
                                                <div class="layui-colla-content">
                                                    </c:if>
                                                    <table class="layui-hide add_progress" id="grandsonprojectAudited${soncurr.id}" lay-filter="grandsonhead"></table>
                                                    <script type="text/html" id="progress">



                                                        <div class="project_progress">
                                                            {{# if(d.progressStage==1){ }}
                                                            {{# if(d.progressState==1){ }}
                                                            <div class=" success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class=" upload">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class=" notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class=" in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==2){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==3){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==4){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==5){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>

                                                                <%--<div class="progress_block complete">--%>
                                                                <%--<i class="layui-icon"></i>--%>
                                                                <%--<span>验收申请表</span>--%>
                                                                <%--</div>--%>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                        </div>

                                                    </script>
                                                    <script type="text/html" id="grandsonbar">
                                                        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
                                                        <a class="layui-btn layui-btn-xs" lay-event="edit">项目管理</a>
                                                        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                                                        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="examine">审核</a>
                                                    </script>
                                                </div>
                                            </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                </div>
                <div class="layui-tab-item">
                    <div class="layui-collapse" lay-accordion="" lay-filter="parentsProjectColl">
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
                                    <div class="layui-collapse" lay-accordion="" lay-filter="sonProjectCollAuditing">
                                        <c:forEach items="${curr.sonProjects}" var="soncurr" varStatus="soncou">
                                        <div class="layui-colla-item">
                                            <h2 class="layui-colla-title" id="${soncurr.id}">
                                                <span>${soncurr.projectName}</span>
                                                <span>所属学院：${soncurr.academyName}</span>
                                                <span>经费预算：¥${soncurr.budget}</span>
                                                <span>预算结算时间：${soncurr.budgetBalanceTime}</span>
                                                <span>项目实施时间：${soncurr.projectImplementTime}</span>
                                            </h2>
                                            <c:if test="${soncou.index == 0}">
                                            <div class="layui-colla-content layui-show">
                                                </c:if>
                                                <c:if test="${soncou.index != 0}">
                                                <div class="layui-colla-content">
                                                    </c:if>
                                                    <table class="layui-hide add_progress" id="grandsonprojectAuditing${soncurr.id}" lay-filter="grandsonhead"></table>
                                                    <script type="text/html" id="progress">



                                                        <div class="project_progress">
                                                            {{# if(d.progressStage==1){ }}
                                                            {{# if(d.progressState==1){ }}
                                                            <div class=" success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class=" upload">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class=" notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class=" in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==2){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==3){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==4){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==5){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>

                                                                <%--<div class="progress_block complete">--%>
                                                                <%--<i class="layui-icon"></i>--%>
                                                                <%--<span>验收申请表</span>--%>
                                                                <%--</div>--%>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                        </div>

                                                    </script>
                                                    <script type="text/html" id="grandsonbar">
                                                        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
                                                        <a class="layui-btn layui-btn-xs" lay-event="edit">项目管理</a>
                                                        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                                                        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="examine">审核</a>
                                                    </script>
                                                </div>
                                            </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                </div>
                <div class="layui-tab-item">
                    <div class="layui-collapse" lay-accordion="" lay-filter="parentsProjectColl">
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
                                    <div class="layui-collapse" lay-accordion="" lay-filter="sonProjectCollNotAudit">
                                        <c:forEach items="${curr.sonProjects}" var="soncurr" varStatus="soncou">
                                        <div class="layui-colla-item">
                                            <h2 class="layui-colla-title" id="${soncurr.id}">
                                                <span>${soncurr.projectName}</span>
                                                <span>所属学院：${soncurr.academyName}</span>
                                                <span>经费预算：¥${soncurr.budget}</span>
                                                <span>预算结算时间：${soncurr.budgetBalanceTime}</span>
                                                <span>项目实施时间：${soncurr.projectImplementTime}</span>
                                            </h2>
                                            <c:if test="${soncou.index == 0}">
                                            <div class="layui-colla-content layui-show">
                                                </c:if>
                                                <c:if test="${soncou.index != 0}">
                                                <div class="layui-colla-content">
                                                    </c:if>
                                                    <table class="layui-hide add_progress" id="grandsonprojectNotAudit${soncurr.id}" lay-filter="grandsonhead"></table>
                                                    <script type="text/html" id="progress">



                                                        <div class="project_progress">
                                                            {{# if(d.progressStage==1){ }}
                                                            {{# if(d.progressState==1){ }}
                                                            <div class=" complete">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class=" upload">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class=" notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class=" in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==2){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block complete">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==3){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block complete">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==4){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block complete">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                            {{# if(d.progressStage==5){ }}
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关文档</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>论证报告</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>采购文件</span>
                                                            </div>
                                                            <div class="progress_block success">
                                                                <i class="layui-icon"></i>
                                                                <span>相关合同</span>
                                                            </div>

                                                                <%--<div class="progress_block complete">--%>
                                                                <%--<i class="layui-icon"></i>--%>
                                                                <%--<span>验收申请表</span>--%>
                                                                <%--</div>--%>
                                                            {{# if(d.progressState==1){ }}
                                                            <div class="progress_block complete">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==0){ }}
                                                            <div class="progress_block upload">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==2){ }}
                                                            <div class="progress_block notpass">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# if(d.progressState==3){ }}
                                                            <div class="progress_block in_audit">
                                                                <i class="layui-icon"></i>
                                                                <span>验收申请表</span>
                                                            </div>
                                                            {{# } }}
                                                            {{# } }}
                                                        </div>

                                                    </script>
                                                    <script type="text/html" id="grandsonbar">
                                                        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
                                                        <a class="layui-btn layui-btn-xs" lay-event="edit">项目管理</a>
                                                        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                                                        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="examine">审核</a>
                                                    </script>
                                                </div>
                                            </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/construction/grandSonProject.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/construction/tagbox.js"></script>
        <script>
            $(function(){
                <c:forEach items="${parentProjects}" var="current">
                    <c:forEach items="${current.sonProjects}" var="curr">
                    sonProjectIds.push(${curr.id});
                    </c:forEach>
                </c:forEach>
                console.log(sonProjectIds);
            });
        </script>
</body>

</html>