<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>


<html>
<head>
    <title></title>
    <meta name="decorator" content="iframe"/>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no"/>
    <style type="text/css" media="screen">
        @import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/icon.css");
        @import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/gray/easyui.css");
        @import url("${pageContext.request.contextPath}/css/style.css");
        @import url("${pageContext.request.contextPath}/static_limsproduct/css/global_static.css");
    </style>
    <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">

    <!-- 文件上传的样式和js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/labroom/editDevice.js"></script>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->


    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

    <style>
        .tool-box2 th {
            text-align: left;
        }

        table label {
            position: relative;
            top: -6px;
            margin-left: 3px;
        }
    </style>
</head>

<body>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">配置项设置</div>
                    </div>
                    <form action="${pageContext.request.contextPath}/audit/saveConfigurationSetting" method="post">
                        <div class="new-classroom">
                            <table id="radioTable">
                                <c:forEach items="${objects}" var="object" varStatus="i">
                                    <tr id="${object[3]}">
                                        <td>${object[2]}:</td>
                                        <td>
                                            <input type="radio" name="${object[3]}" id="${object[3]}1" value="1" onclick="changeExtendStatus('${object[3]}')"
                                                   <c:if test="${object[1] eq 1}">checked</c:if>/>
                                            <label for="${object[3]}1">是</label>
                                            <input type="radio" name="${object[3]}" id="${object[3]}2" value="2" onclick="changeExtendStatus('${object[3]}')"
                                                   <c:if test="${object[1] ne 1}">checked</c:if>/>
                                            <label for="${object[3]}2">否</label>
                                        </td>
                                    </tr>
                                        <c:forEach items="${extendItems[object[3]]}" var="extendItem" varStatus="j">
                                            <tr id="extend${extendItem[3]}" class="${object[3]}" <c:if test="${object[1] ne 1}">style="display: none;"</c:if>>
                                                <td>${extendItem[2]}:</td>
                                                <td>
                                                    <input type="radio" name="extend${extendItem[3]}" id="extend${extendItem[3]}1"
                                                           value="1"
                                                           <c:if test="${extendItem[1] eq 1}">checked</c:if>/>
                                                    <label for="extend${extendItem[3]}1">是</label>
                                                    <input type="radio" name="extend${extendItem[3]}" id="extend${extendItem[3]}2"
                                                           value="2"
                                                           <c:if test="${extendItem[1] ne 1}">checked</c:if>/>
                                                    <label for="extend${extendItem[3]}2">否</label>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                </c:forEach>
                                <tr>
                                    <td colspan="2">
                                        <span style="width: 50px; margin: 20px auto">
                                            <input class="btn" type="submit" value="确定">
                                        </span>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select': {search_contains: true},
        '.chzn-select-deselect': {allow_single_deselect: true},
        '.chzn-select-no-single': {disable_search_threshold: 10},
        '.chzn-select-no-results': {no_results_text: 'Oops, nothing found!'},
        '.chzn-select-width': {width: "100%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<script type="text/javascript">
    function changeExtendStatus(obj) {
        if($('input:radio[name="' + obj + '"]:checked').val() == 1){
            $("." + obj).show();
        }
        if($('input:radio[name="' + obj + '"]:checked').val() == 2){
            $("." + obj).hide();
        }
    }
</script>
<!-- 下拉框的js -->
</body>
</html>
