<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<head>
    <meta name="decorator" content="iframe"/>
    <link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
    <!--直接排课  -->

    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
</head>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.system.management" /></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.authority.management" /></a></li>
        </ul>
    </div>
</div>
<div class="iStyle_RightInner">

    <div class="right-content">
        <div id="TabbedPanels1" class="TabbedPanels">
            <ul class="TabbedPanelsTabGroup">
                <li class="TabbedPanelsTab1 selected" id="s1">
                    <a href="javascript:void(0)">用户权限组列表</a>
                </li>
            </ul>
            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">

                    <div class="content-box">
                        <%--<div class="title">用户权限组列表</div>--%>
                        <table>
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>用户组标识</th>
                                <th>用户组名称</th>
                                <th>用户组人数</th>
                                <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
                                    <th>操作</th>
                                </c:if>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${AllUserAuthority}" var="a"  varStatus="i">
                                <tr>
                                    <td>${i.count}</td>
                                    <td>${a.authorityName}</td>
                                    <td>${a.authorityCname}</td>
                                    <td>
                                        <a href='${pageContext.request.contextPath}/userAuthorityMange/listUserAuthorityDetail?page=1&Id=${a.authorityId}'>${a.authorityNumber}</a>
                                    </td>
                                    <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
                                        <td><a class="btn btn-common" href="javascript:void(0)" onclick="openEditMenu('${a.authorityName}')">编辑</a></td>
                                    </c:if>
                                </tr>
                            </c:forEach>


                            </tbody>

                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select'           : {search_contains:true},
        '.chzn-select-deselect'  : {allow_single_deselect:true},
        '.chzn-select-no-single' : {disable_search_threshold:10},
        '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
        '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }

</script>
<!-- 下拉框的js -->
<script>
    function openEditMenu(name) {
        window.location.href = "${pageContext.request.contextPath}/systemMenu/editAuthorityMenu?name=" + name;
    }
</script>

