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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-property.js"></script>
    <link href="${pageContext.request.contextPath}/css/visualEngine/visual-property.css" rel="stylesheet" type="text/css">
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
            <li class="end"><a href="javascript:void(0)">配置管理</a></li>
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
            <th width="5%"><center>配置ID</center></th>
            <th width="15%"><center>个性化</center></th>
            <th><center>数据名称</center></th>
            <th><center>数据类型</center></th>
            <th><center>数据URL连接</center></th>
            <th width="5%"><center>数据排序</center></th>
            <th width="10%"><center>操作</center></th>
        </tr>
        </thead>
        <tbody id="table_manager"></tbody>
    </table>
</div>
    <div id="foot"></div>
</div>

</div>
<!-- 弹出框，添加修改功能 -->
<div id="new_property" class="new_property">
    <div class="new_property_title"><div class="new_property_close" onclick="closeNewProperty()"><div>×</div></div></div>
    <form id="new_property_form" class="new_property_form">
        <p>
            配置ID :
            <input name="id" id="new_property_id" class="newinput-style" style="width:148px;height: 25px;" readonly />
        </p>
        <p>
            数据名称 :
            <input name="dataName" id="new_property_dataName" class="newinput-style" type="text" style="height: 25px;" />
        </p>

        <p>
            数据类型 :
            <input name="dataType" id="new_property_dataType" class="newinput-style" type="text" style="height: 25px;" />
        </p>

        <p>
            数据URL连接 :
            <input name="dataUrl" id="new_property_dataUrl" class="newinput-style" type="text" style="height: 25px;" />
        </p>

        <p>
            数据排序 :
            <input name="dataSort" id="new_property_dataSort" class="newinput-style" type="number" style="height: 25px;" />
        </p>
    </form>
    <div class="new_property_cancel" onclick="closeNewProperty()">取消</div><div class="new_property_accept" onclick="submitNewProperty()">确定</div>
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
