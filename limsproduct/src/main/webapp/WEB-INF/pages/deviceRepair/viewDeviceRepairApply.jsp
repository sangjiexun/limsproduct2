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
    <script src="${pageContext.request.contextPath}/js/SpryTabbedPanels.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/css/SpryTabbedPanels.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.8.3.min.js"></script>
    <!-- 文件上传的样式和js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->


    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

    <style>
        .tool-box2 th {
            text-align: left;
        }
    </style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">实验设备管理</a></li>
            <li class="end"><a href="javascript:void(0)">设备维修申请单查看</a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">

                    <div class="title">
                        <div id="title">设备维修申请单</div>
                        <input class="btn btn-return" style="margin-top: 5px" type="button" value="返回" onclick="window.history.go(-1)">
                    </div>
                    <table class="table table-bordered table-striped with-check" style="
                    word-break:break-all; word-wrap: break-word; text-align: center; vertical-align: center; horiz-align: center">
                        <tr>
                            <th width="10%">物资名称</th>
                            <td width="40%">
                                <label>${deviceRepair.deviceName}</label>
                            </td>
                            <th width="10%">实验室名称</th>
                            <td width="40%">
                                <label>${deviceRepair.labAddress}</label>
                            </td>
                        </tr>
                        <tr>
                            <th>报修人</th>
                            <td>
                                <label>${reportUser.cname}</label>
                            </td>
                            <th>预计金额</th>
                            <td>
                                <label>${deviceRepair.expectAmount}</label>
                            </td>
                        </tr>
                        <tr>
                            <th>报修描述(原因)</th>
                            <td>
                                <p>${deviceRepair.content}</p>
                            </td>
                            <th>设备价格</th>
                            <td>
                                <p>${devicePrice}</p>
                            </td>
                        </tr>
                        <tr>
                            <th>报修时间</th>
                            <td>
                                <fmt:formatDate value="${deviceRepair.createDate.time}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                            <th>创建人</th>
                            <td>
                                <label>${createUser.cname}</label>
                            </td>
                        </tr>
                        <tr>
                            <th>状态</th>
                            <td>
                                <c:if test="${deviceRepair.auditStage eq 0}">未提交</c:if>
                                <c:if test="${deviceRepair.auditStage eq 1}">审核中</c:if>
                                <c:if test="${deviceRepair.auditStage eq 2}">审核通过</c:if>
                                <c:if test="${deviceRepair.auditStage eq 3}">审核拒绝</c:if>
                                <c:if test="${deviceRepair.auditStage eq 4}">已维修</c:if>
                                <c:if test="${deviceRepair.auditStage eq 5}">未维修</c:if>
                                <c:if test="${deviceRepair.auditStage eq 6}">已填写</c:if>
                                <c:if test="${deviceRepair.auditStage eq 7}">已入账</c:if>
                            </td>
                            <c:if test="${deviceRepair.auditStage > 0}">
                                <th>审核状态</th>
                                <td>
                                    ${s}
                                </td>
                            </c:if>
                        </tr>
                        <c:if test="${deviceRepair.auditStage > 3}">
                            <tr>
                                <th>验收备注</th>
                                <td>
                                    <label>${deviceRepair.memo}</label>
                                </td>
                                <th>验收人</th>
                                <td>
                                    <label>${acceptanceUser.cname}</label>
                                </td>
                            </tr>
                            <c:if test="${deviceRepair.auditStage > 5}">
                                <tr>
                                    <th>确认金额</th>
                                    <td>
                                        <label>${deviceRepair.confirmAmount}</label>
                                    </td>
                                    <th>确认内容</th>
                                    <td>
                                        <label>${deviceRepair.confirmContent}</label>
                                    </td>
                                </tr>
                                <tr>
                                    <th>确认人</th>
                                    <td>
                                        <label>${deviceRepair.confirmUser}</label>
                                    </td>
                                    <th>确认时间</th>
                                    <td>
                                        <fmt:formatDate value="${deviceRepair.confirmDate.time}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                </tr>
                            </c:if>
                        </c:if>
                    </table>
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
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>
