<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>

<html>
<head>
    <meta name="decorator" content="iframe"/>

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/resourceContainer/resourceContainer.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/resourceContainer/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/resourceContainer/resourceContainer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/jquery-3.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-templet.js"></script>
    <link href="${pageContext.request.contextPath}/css/visualEngine/visual-templet.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/visualEngine/visual-core.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">


    <%--<script type="text/javascript">--%>

    <%--//首页--%>
    <%--function first(url) {--%>
    <%--document.queryForm.action = url;--%>
    <%--document.queryForm.submit();--%>
    <%--}--%>

    <%--//末页--%>
    <%--function last(url) {--%>
    <%--document.queryForm.action = url;--%>
    <%--document.queryForm.submit();--%>
    <%--}--%>

    <%--//上一页--%>
    <%--function previous(url) {--%>
    <%--var page =${page};--%>
    <%--if (page == 1) {--%>
    <%--page = 1;--%>
    <%--} else {--%>
    <%--page = page - 1;--%>
    <%--}--%>
    <%--//alert("上一页的路径："+url+page);--%>
    <%--document.queryForm.action = url + page + '&type=${type}';--%>
    <%--document.queryForm.submit();--%>
    <%--}--%>

    <%--//下一页--%>
    <%--function next(url) {--%>
    <%--var totalPage =${pageModel.totalPage};--%>
    <%--var page =${page};--%>
    <%--if (page >= totalPage) {--%>
    <%--page = totalPage;--%>
    <%--} else {--%>
    <%--page = page + 1--%>
    <%--}--%>
    <%--//alert("下一页的路径："+page);--%>
    <%--document.queryForm.action = url + page + '&type=${type}';--%>
    <%--document.queryForm.submit();--%>
    <%--}--%>

    <%--function cancel() {--%>
    <%--window.location.href = "${pageContext.request.contextPath}/visualization/changeBackground?page=1&type=${type}";--%>
    <%--}--%>
    <%--</script>--%>
    <style>
        .TabbedPanelsContent .content_box {
            border: 1px solid #d0d6dc;
        }
        .content-box thead tr th {
            font-weight: 600;
        }
        .tool-box button {
            background: #77bace;
            color: #fff!important;
            border: none;
            font-weight: bold;
            border-radius: 3px;
            padding: 0 10px;
            float: right;
            margin: 10px;
            cursor: pointer;
        }
        .new_property_form {
            margin: 20px;
            line-height: 3;
        }
        table td a, table td span {
            font-weight: normal;
            color: #409eff;
        }
    </style>
</head>


<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.visualization.visual"/></a></li>
            <li class="end"><a href="javascript:void(0)">模板管理</a></li>
        </ul>
    </div>
</div>
<div class="TabbedPanelsContent">
    <div class="content-box">
        <div class="tool-box">
            <input id="back" class="cancel-submit" onclick="goBack()" value="返回" type="button">
            <input id="add" class="" value="新增" type="button"/>
        </div>
        <div class="content-box">
    <table>
        <thead>
        <tr>
            <th width="5%">模板ID</th>
            <th>模板名称</th>
            <th>最低可显示权限</th>
            <th>最低可编辑权限</th>
            <th>创建人</th>
            <th>创建时间期</th>
            <th>最后更新时间</th>
            <th width="10%">操作</th>
        </tr>
        </thead>
        <tbody id="table_manager"></tbody>
    </table>
        </div>
        <div class="page"><div class="page_info"><span id='totalRecords'></span>条记录 • 共<span id='totalPages'></span>页</div><a class="page_btn" onclick='firstPage()'>首页</a><a class="page_btn" onclick='prePage()'>上一页</a><div class="page_select"><span>第</span><input id='goto' type="text" onkeypress='thisPage(event.keyCode||event.which)' /><span>页</span></div><a class="page_btn" onclick='thisPage()'>跳转</a><a class="page_btn" onclick='nexPage()'>下一页</a><a class="page_btn" onclick='lasPage()'>末页</a></div>
    </div>
</div>
<div id="new_templet" class="new_templet">
    <div class="new_templet_title"><div class="new_templet_close" onclick="closeNewtemplet()"><div>×</div></div></div>
    <div id="new_templet_form" class="new_templet_form">
        <br/>
        模板ID :<input name="id" id="new_templet_id" class="newinput-style" style="width:148px;height: 25px;" readonly />
        模板名称 :<input name="dataName" id="new_templet_name" class="newinput-style" type="text" style="height: 25px;" />
        最低可显示权限 : <input name="dataType" id="new_templet_authority" class="newinput-style" type="number" style="height: 25px;" />
        最低可编辑权限 :<input name="dataUrl" id="new_templet_editAuthority" class="newinput-style" type="number" style="height: 25px;" />
        <div id="properties1" class="properties_box"><div class="properties_title">一级权限配置</div><div class="properties_add"><p onclick="openAddProperty(this)">＋</p><div style="display:none"><input oninput="autoSuggestProperty(this)" type="text"/><input type="hidden" /><input type="button" onclick="addProperty(this)" value="确定" /><input type="button" onclick="closeAddProperty(this)" value="取消" /><div class="autoSuggessDiv"></div></div></div></div>
        <div id="properties2" class="properties_box"><div class="properties_title">二级权限配置</div><div class="properties_add"><p onclick="openAddProperty(this)">＋</p><div style="display:none"><input oninput="autoSuggestProperty(this)" type="text"/><input type="hidden" /><input type="button" onclick="addProperty(this)" value="确定" /><input type="button" onclick="closeAddProperty(this)" value="取消" /><div class="autoSuggessDiv"></div></div></div></div>
        <div id="properties3" class="properties_box"><div class="properties_title">三级权限配置</div><div class="properties_add"><p onclick="openAddProperty(this)">＋</p><div style="display:none"><input oninput="autoSuggestProperty(this)" type="text"/><input type="hidden" /><input type="button" onclick="addProperty(this)" value="确定" /><input type="button" onclick="closeAddProperty(this)" value="取消" /><div class="autoSuggessDiv"></div></div></div></div>
        <div id="properties4" class="properties_box"><div class="properties_title">四级权限配置</div><div class="properties_add"><p onclick="openAddProperty(this)">＋</p><div style="display:none"><input oninput="autoSuggestProperty(this)" type="text"/><input type="hidden" /><input type="button" onclick="addProperty(this)" value="确定" /><input type="button" onclick="closeAddProperty(this)" value="取消" /><div class="autoSuggessDiv"></div></div></div></div>
        <div id="properties5" class="properties_box"><div class="properties_title">五级权限配置</div><div class="properties_add"><p onclick="openAddProperty(this)">＋</p><div style="display:none"><input oninput="autoSuggestProperty(this)" type="text"/><input type="hidden" /><input type="button" onclick="addProperty(this)" value="确定" /><input type="button" onclick="closeAddProperty(this)" value="取消" /><div class="autoSuggessDiv"></div></div></div></div>
    </div>
    <div class="new_templet_cancel" onclick="closeNewtemplet()">取消</div><div class="new_templet_accept" onclick="submitNewTemplet()">确定</div>
</div>
<script>
    initDirectoryEngine({
        getHostsUrl:"${pageContext.request.contextPath}/shareApi/getHosts",
        getAuthorizationUrl:"${pageContext.request.contextPath}/shareApi/getAuthorization"
    });
    function goBack() {
        window.history.go(-1);
    }
</script>
</body>

</html>
