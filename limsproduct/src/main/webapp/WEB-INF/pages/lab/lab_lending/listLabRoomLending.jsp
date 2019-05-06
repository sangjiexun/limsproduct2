<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<head>
    <meta name="decorator" content="iframe"/>
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
    <!-- 下拉框的样式 -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
    <!-- 打印插件的引用 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <!-- 弹窗 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <!-- 弹窗 -->
    <script type="text/javascript">
        //取消查询
        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/labRoomLending/listLabRoomLending?labRoomId=${labRoomId}&currpage=1"
        }

        //跳转
        function targetUrl(url) {
            document.form.action = url;
            document.form.submit();
        }
    </script>
    <script type="text/javascript">
        //导出
        function exportAll(s) {
            document.form.action = s;
            document.form.submit();
        }
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/labreservation/labreservation.js"></script>
</head>
<body>
<!--导航  -->
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">排课管理</a></li>
            <li><a href="javascript:void(0)">课程项目管理</a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.trainingRoom.setting"/>借用</a></li>
        </ul>
    </div>
</div>
<!--导航结束  -->

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup1">
            <div class="TabbedPanelsContent">
                <div class="tool-box">
                    <ul class="TabbedPanelsTabGroup">
                        <li class="TabbedPanelsTab" id="s0"><a
                                href="${pageContext.request.contextPath}/labRoomLending/listLabRoomLending?currpage=1&labRoomId=${labRoomId }">我的<spring:message
                                code="left.trainingRoom.setting"/>借用</a></li>
                        <li class="TabbedPanelsTab" id="s1"><a
                                href="${pageContext.request.contextPath}/labRoomLending/myLabRoomLendingAudit?flag=1">我的借用审核</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="content-box">
                <table>
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th><spring:message code="left.trainingRoom.setting"/>名称</th>
                        <th><spring:message code="left.trainingRoom.setting"/>编号</th>
                        <th>申请人</th>
                        <th>借用日期</th>
                        <th>借用时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${labRoomLendings}" var="s" varStatus="i">
                        <tr>
                            <td>${i.count+(currpage-1)*pageSize }</td>
                            <td>${s.labRoom.labRoomName}</td>
                            <td>${s.labRoom.labRoomNumber}</td>
                            <td>${s.user.cname}</td>
                            <td><fmt:formatDate value="${s.lendingTime.time}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${s.startTime.time}" pattern="HH:mm"/>-<fmt:formatDate
                                    value="${s.endTime.time}" pattern="HH:mm"/></td>
                            <td>
                                <c:if test="${s.auditResults eq 3}">未审核</c:if>
                                <c:if test="${s.auditResults eq 2}">审核中</c:if>
                                <c:if test="${s.auditResults eq 1}">审核通过</c:if>
                                <c:if test="${s.auditResults eq 4}">审核拒绝</c:if></td>
                            <td>
                                <c:if test="${s.auditResults==3}">
                                    <a href="${pageContext.request.contextPath}/labRoomLending/submitLabRoomLending?Id=${s.id}">提交</a>
                                    <a href="${pageContext.request.contextPath}/labRoomLending/editLabRoomLending?id=${s.id}">编辑</a>
                                    <a href="${pageContext.request.contextPath}/labRoomLending/deleteLabRoomLending?id=${s.id}"
                                       onclick="return confirm('确定删除？');">删除</a>
                                </c:if>
                                <c:if test="${s.auditResults!=3}">
                                    <a href="${pageContext.request.contextPath}/labRoomLending/viewLabRoomLendingAudit?id=${s.id}&currpage=1">进度查看</a>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/labRoomLending/labRoomLendingInfoList?idKey=${s.id}&flag=1&step=0">详情</a>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="page">
                    ${totalRecords}条记录 &nbsp; 共${pageModel.totalPage}页 &nbsp;
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/labRoomLending/listLabRoomLending?labRoomId=${labRoomId}&currpage=1')"
                       target="_self">首页</a>
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/labRoomLending/listLabRoomLending?labRoomId=${labRoomId}&currpage=${pageModel.previousPage}')"
                       target="_self">上一页</a>
                    <input type="hidden" value="${pageModel.lastPage}" id="totalpage"/>&nbsp; <input type="hidden"
                                                                                                     value="${currpage}"
                                                                                                     id="currpage"/>
                    &nbsp;第
                    <select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                        <option value="${pageContext.request.contextPath}/system/listUser?currpage=${currpage}">${currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                   var="current">
                            <c:if test="${j.index!=currpage}">
                                <option value="${pageContext.request.contextPath}/labRoomLending/listLabRoomLending?labRoomId=${labRoomId}&currpage=${j.index}">${j.index}</option>
                            </c:if>
                        </c:forEach>
                    </select>页&nbsp;

                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/labRoomLending/listLabRoomLending?labRoomId=${labRoomId}&currpage=${pageModel.nextPage}')"
                       target="_self">下一页</a>
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/labRoomLending/listLabRoomLending?labRoomId=${labRoomId}&currpage=${pageModel.lastPage}')"
                       target="_self">末页</a>
                </div>

            </div>
        </div>

        <!-- 下拉框的js -->

        <script
                src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>

        <script
                src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
                type="text/javascript" charset="utf-8"></script>

        <script type="text/javascript">
            var config = {
                '.chzn-select': {search_contains: true},
                '.chzn-select-deselect': {allow_single_deselect: true},
                '.chzn-select-no-single': {disable_search_threshold: 10},
                '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
                '.chzn-select-width': {width: "95%"}
            }
            for (var selector in config) {
                $(selector).chosen(config[selector]);
            }
        </script>
    </div>
</div>