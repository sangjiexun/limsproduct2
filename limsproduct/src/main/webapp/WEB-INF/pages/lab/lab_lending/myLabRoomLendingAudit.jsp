<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式 -->
    <link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css"/>

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

    <script type="text/javascript">
        $(function () {
            var s =${tage};
            if (1 == s) {
                $("#s1").addClass("TabbedPanelsTab selected");
                $("#s0").addClass("TabbedPanelsTab");
                $("#s2").addClass("TabbedPanelsTab");
                $("#s3").addClass("TabbedPanelsTab");
                $("#s4").addClass("TabbedPanelsTab");
            }
            if (3 == s) {
                $("#s3").addClass("TabbedPanelsTab selected");
                $("#s0").addClass("TabbedPanelsTab");
                $("#s1").addClass("TabbedPanelsTab ");
                $("#s2").addClass("TabbedPanelsTab");
                $("#s5").addClass("TabbedPanelsTab");
                $("#s4").addClass("TabbedPanelsTab");
            }
            if (4 == s) {
                $("#s4").addClass("TabbedPanelsTab selected");
                $("#s0").addClass("TabbedPanelsTab");
                $("#s1").addClass("TabbedPanelsTab ");
                $("#s2").addClass("TabbedPanelsTab");
                $("#s3").addClass("TabbedPanelsTab");

            }
            if (2 == s) {
                $("#s2").addClass("TabbedPanelsTab selected");
                $("#s0").addClass("TabbedPanelsTab");
                $("#s1").addClass("TabbedPanelsTab ");
                $("#s3").addClass("TabbedPanelsTab");
                $("#s4").addClass("TabbedPanelsTab");
            }
            if (0 == s) {
                $("#s0").addClass("TabbedPanelsTab selected");
                $("#s2").addClass("TabbedPanelsTab");
                $("#s1").addClass("TabbedPanelsTab ");
                $("#s3").addClass("TabbedPanelsTab");
                $("#s4").addClass("TabbedPanelsTab");
            }

        });

        //首页
        function first(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

        //末页
        function last(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

        //上一页
        function previous(url) {
            var page =${page};
            if (page == 1) {
                page = 1;
            } else {
                page = page - 1;
            }
            //alert("上一页的路径："+url+page);
            document.queryForm.action = url + page;
            document.queryForm.submit();
        }

        //下一页
        function next(url) {
            var totalPage =${pageModel.totalPage};
            var page =${page};
            if (page >= totalPage) {
                page = totalPage;
            } else {
                page = page + 1
            }
            //alert("下一页的路径："+page);
            document.queryForm.action = url + page;
            document.queryForm.submit();
        }

    </script>

</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.trainingRoom.setting"/>借用申请</a></li>
            <li class="end"><a href="javascript:void(0)">我的审核</a></li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab" id="s4"><a
                    href="${pageContext.request.contextPath}/labRoomLending/myLabRoomLendingAudit?flag=1">审核通过</a></li>
            <li class="TabbedPanelsTab" id="s3"><a
                    href="${pageContext.request.contextPath}/labRoomLending/myLabRoomLendingAudit?flag=2">审核拒绝</a></li>
            <li class="TabbedPanelsTab" id="s1"><a
                    href="${pageContext.request.contextPath}/labRoomLending/myLabRoomLendingAudit?flag=3">待审核</a>
            </li>
        </ul>
        <div class="TabbedPanelsTabGroup-box">
            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">
                    <div class="content-box">
                        <div class="title">
                        </div>
                        <table class="tb" id="my_show">
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
                            <c:forEach items="${labReservationAudit}" var="s" varStatus="i">
                                <tr>
                                        <%-- <td>${i.count+(currpage-1)*pageSize }</td> --%>
                                    <td></td>
                                    <td>${s.labReservation.labRoom.labRoomName}</td>
                                    <td>${s.labReservation.labRoom.labRoomNumber}</td>
                                    <td>${s.labReservation.user.cname}</td>
                                    <td><fmt:formatDate value="${s.labReservation.lendingTime.time}"
                                                        pattern="yyyy-MM-dd"/></td>
                                    <td><fmt:formatDate value="${s.labReservation.startTime.time}"
                                                        pattern="HH:mm"/>-<fmt:formatDate
                                            value="${s.labReservation.endTime.time}" pattern="HH:mm"/></td>
                                    <td>
                                        <c:if test="${s.labReservation.auditResults eq 3}">未审核</c:if>
                                        <c:if test="${s.labReservation.auditResults eq 2}">审核中</c:if>
                                        <c:if test="${s.labReservation.auditResults eq 1}">审核通过</c:if>
                                        <c:if test="${s.labReservation.auditResults eq 4}">审核拒绝</c:if></td>
                                    <td>
                                        <c:if test="${s.labReservation.auditResults eq 2}">
                                            <a href="${pageContext.request.contextPath}/labRoomLending/labRoomLendingInfoList?idKey=${s.labReservation.id}&flag=2&step=${s.labReservation.auditStage}">审核</a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>

                        <!-- 分页模块 -->
                        <div class="page">
                            ${totalRecords}条记录,共${pageModel.totalPage}页
                            <a href="javascript:void(0)"
                               onclick="first('${pageContext.request.contextPath}/labRoom/SoftwareReserveList?page=1&tage=${tage }&isaudit=${isAudit }')"
                               target="_self">首页</a>
                            <a href="javascript:void(0)"
                               onclick="previous('${pageContext.request.contextPath}/labRoom/SoftwareReserveList?tage=${tage }&isaudit=${isAudit }&page=')"
                               target="_self">上一页</a>
                            第<select
                                onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                            <option value="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?page=${page}&tage=${tage }&isaudit=${isAudit }">${page}</option>
                            <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                       var="current">
                                <c:if test="${j.index!=page}">
                                    <option value="${pageContext.request.contextPath}/labRoom/SoftwareReserveList?page=${j.index}&tage=${tage }&isaudit=${isAudit }">${j.index}</option>
                                </c:if>
                            </c:forEach></select>页
                            <a href="javascript:void(0)"
                               onclick="next('${pageContext.request.contextPath}/labRoom/SoftwareReserveList?tage=${tage }&isaudit=${isAudit }&page=')"
                               target="_self">下一页</a>
                            <a href="javascript:void(0)"
                               onclick="last('${pageContext.request.contextPath}/labRoom/SoftwareReserveList?tage=${tage }&isaudit=${isAudit }&page=${pageModel.totalPage}')"
                               target="_self">末页</a>
                        </div>
                        <!-- 分页模块 -->
                    </div>

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


