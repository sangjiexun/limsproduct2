<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>

    <script type="text/javascript">
        //取消查询
        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/labAnnex/listLabAnnex?currpage=1";
        }

        //跳转
        function targetUrl(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.trainingRoom.infoManagement"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.trainingAnnex.management"/></a></li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)"><spring:message code="all.trainingRoom.labroom"/>列表</a>
            </li>
            <c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
                <a class="btn btn-new" href="${pageContext.request.contextPath}/labAnnex/newLabAnnex?page=${page}">新建</a>
            </c:if>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <%--<div class="title">--%>
                        <%--<div id="title"><spring:message code="all.trainingRoom.labroom"/>列表</div>--%>
                        <%--<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">--%>
                            <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/labAnnex/newLabAnnex?page=${page}">新建</a>--%>
                        <%--</c:if>--%>
                    <%--</div>--%>

                    <div class="tool-box">
                        <form:form name="queryForm"
                                   action="${pageContext.request.contextPath}/labAnnex/listLabAnnex?currpage=1"
                                   method="post" modelAttribute="labAnnex">
                            <ul>
                                <li><spring:message code="all.trainingRoom.labroom"/>名称:<form:input id="lab_name" path="labName"/></li>
                                <li>
                                    <input type="submit" value="查询"/>
                                    <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
                                </li>
                            </ul>
                        </form:form>
                    </div>

                    <table class="tb" id="my_show">
                        <thead>
                        <tr>
                            <th><spring:message code="all.trainingRoom.labroom"/>名称</th>
                            <th><spring:message code="all.trainingRoom.labroom"/>分室数量</th>
                            <th>学院名称</th>
                            <c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
                                <th>操作</th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listLabAnnex}" var="curr">
                            <tr>
                                <td>${curr.labName}</td>
                                <td>${curr.labNumber}</td>
                                <td>${curr.labCenter.schoolAcademy.academyName}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/labAnnex/getLabAnnex?id=${curr.id}">查看</a>
                                    <c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
                                        <a href="${pageContext.request.contextPath}/labAnnex/editLabAnnex?labAnnexId=${curr.id}&page=${page}">编辑</a>
                                        <a href="${pageContext.request.contextPath}/labAnnex/deleteLabAnnex?labAnnexId=${curr.id}&page=${page}" onclick="return confirm('确定删除？');">删除</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!-- 分页[s] -->
                    <div class="page">
                        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/labAnnex/listLabAnnex?currpage=1')"
                           target="_self">首页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/labAnnex/listLabAnnex?currpage=${pageModel.previousPage}')"
                           target="_self">上一页</a>
                        第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                        <option value="${pageContext.request.contextPath}/labAnnex/listLabAnnex?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                   var="current">
                            <c:if test="${j.index!=pageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/labAnnex/listLabAnnex?currpage=${j.index}">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/labAnnex/listLabAnnex?currpage=${pageModel.nextPage}')"
                           target="_self">下一页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/labAnnex/listLabAnnex?currpage=${pageModel.lastPage}')"
                           target="_self">末页</a>
                    </div>
                    <!-- 分页[e] -->
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
