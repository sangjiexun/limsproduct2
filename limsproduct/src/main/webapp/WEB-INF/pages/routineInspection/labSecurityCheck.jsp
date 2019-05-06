<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>


<html>
<head>
    <meta name="decorator" content="iframe"/>

    <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->

    <script type="text/javascript">
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
            document.queryForm.action = url + page;
            document.queryForm.submit();
        }

        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/labSecurityCheck?currpage=1";
        }

        //新建
        function newLabS() {
            window.location.href = "${pageContext.request.contextPath}/newCheckDetails?currpage=1";
        }

        //提交\查看
        function dosubmitCheck(checkId, types) {
            window.location.href = "${pageContext.request.contextPath}/submitCheckDetails?checkId=" + checkId + "&types=" + types;
        }
    </script>
</head>


<body>

<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.lab.safety.inspect"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.safety.inspection"/></a></li>
        </ul>
    </div>
</div>

<!-- 查询表单 -->
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)"><spring:message code="left.safety.inspection"/></a>
            </li>
            <input class="btn btn-new" type="button" value="新建" onclick="newLabS();"/>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box">
                        <form:form name="queryForm"
                                   action="${pageContext.request.contextPath}/labSecurityCheck?currpage=1" method="post"
                                   modelAttribute="labSecurityCheck">
                            <ul>
                                <li>
                                    实验中心:
                                    <form:select id="labCenter.id" path="labCenter.id" name="labCenter.id"
                                                 class="chzn-select">
                                        <form:option value="">实验中心</form:option>
                                        <c:forEach items="${labCenters}" var="l">
                                            <form:option value="${l.id}">${l.centerName}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </li>

                                <li>
                                    时间:
                                    <form:select id="month" path="month" name="month" class="chzn-select">
                                        <form:option value="">时间</form:option>
                                        <c:forEach items="${month}" var="m">
                                            <form:option value="${m}">${termName}第${m}月</form:option>
                                        </c:forEach>
                                    </form:select>
                                </li>


                                <li>
                                    <input type="submit" value="查询"/>
                                    <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
                                    <%--<input type="button" value="新建" onclick="newLabS();"/>--%>
                                </li>
                                <%--<sec:authorize ifNotGranted="ROLE_FULLTIMEMANAGER">FULLTIMEMANAGER
                                    <li><input type="button" value="新建" onclick="newLabS();"/></li>
                                </sec:authorize>--%>
                            </ul>

                        </form:form>
                    </div>
                    <div class="content-box">
                        <table class="tb" id="my_show">
                            <thead style="center-content">
                            <tr>
                                <th>时间</th>
                                <th>学院</th>
                                <th>实验中心</th>
                                <th>类型</th>
                                <th>检查人</th>
                                <th>操作</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${labSecurityChecks}" var="curr">
                                <tr>
                                    <td>${curr.schoolTerm.termName}第${curr.month}月</td>
                                    <td>${curr.labCenter.schoolAcademy.academyName}</td>
                                    <td>${curr.labCenter.centerName}</td>
                                    <td>第${curr.typeTable}类</td>
                                    <td>${curr.user.cname}</td>
                                    <td>
                                        <sec:authorize
                                                ifAnyGranted="ROLE_SUPERADMIN,ROLE_FULLTIMEMANAGER,ROLE_EXCENTERDIRECTOR">
                                            <c:if test="${curr.typeAuditing!='4'}">
                                                <%--<input type="button" value="查看" onclick="dosubmitCheck(${curr.id},1);"/>--%>
                                                <a href="javascript:void(0)" onclick="dosubmitCheck(${curr.id},1);">查看</a>
                                            </c:if>
                                            <c:if test="${curr.typeAuditing=='4'}">
                                                <%--<input type="button" value="查看" onclick="dosubmitCheck(${curr.id},2);"/>--%>
                                                <a href="javascript:void(0)" onclick="dosubmitCheck(${curr.id},2);">查看</a>
                                            </c:if>
                                            <c:if test="${curr.typeAuditing=='4'}">
                                                <%--<input type="button" value="提交" onclick="dosubmitCheck(${curr.id},2);"/>--%>
                                                <a href="javascript:void(0)" onclick="dosubmitCheck(${curr.id},2);">提交</a>
                                            </c:if>
                                        </sec:authorize>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>


                        <div class="page">
                            ${totalRecords}条记录,共${pageModel.totalPage}页
                            <a href="javascript:void(0)"
                               onclick="first('${pageContext.request.contextPath}/labSecurityCheck?currpage=1')"
                               target="_self">首页</a>
                            <a href="javascript:void(0)"
                               onclick="previous('${pageContext.request.contextPath}/labSecurityCheck?currpage=')"
                               target="_self">上一页</a>
                            第<select
                                onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                            <option value="${pageContext.request.contextPath}/labSecurityCheck?currpage=${page}">${page}</option>
                            <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                       var="current">
                                <c:if test="${j.index!=page}">
                                    <option value="${pageContext.request.contextPath}/labSecurityCheck?currpage=${j.index}">${j.index}</option>
                                </c:if>
                            </c:forEach></select>页
                            <a href="javascript:void(0)"
                               onclick="next('${pageContext.request.contextPath}/labSecurityCheck?currpage=')"
                               target="_self">下一页</a>
                            <a href="javascript:void(0)"
                               onclick="last('${pageContext.request.contextPath}/labSecurityCheck?currpage=${pageModel.totalPage}')"
                               target="_self">末页</a>
                        </div>
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
        '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>
