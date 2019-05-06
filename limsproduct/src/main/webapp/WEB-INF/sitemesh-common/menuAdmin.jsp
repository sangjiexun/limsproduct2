<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" import="java.util.*" import="java.sql.*" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.user-resources"/>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iStyle_Feelings_Base.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.cookie.js"></script>
</head>

<div class="iStyle_Feelings_Tree_Buttons">
    <div class="iStyle_Feelings_Tree_Trunks"  actived="true" id="menuAdmin">
            <c:forEach items="${myParentNode}" var="p" varStatus="i">
            <div class="iStyle_Feelings_Tree_Trunk">
                <a class="iStyle_Feelings_Tree_TrunkTitle ">
                    <div class="iStyle_Feelings_Tree_Icon"></div>
                        ${p.name}</a>
                <div class="iStyle_Feelings_Tree_Leaves">
                    <c:forEach items="${myChildrenNode[p.identification]}" var="c" varStatus="j">
                        <a class="iStyle_Feelings_Tree_Leaf  <c:if test="${i.count eq 1 and j.count eq 1}"> iStyle_Feelings_Tree_ActiveLeaf</c:if>"
                           href="${pageContext.request.contextPath}${c.hyperlink}" <c:if test="${p.id eq 5}"> target="_blank"</c:if>>${c.name}</a>
                    </c:forEach>
                </div>
            </div>
            </c:forEach>
            <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_ACADEMYLEVELM' || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'}">
                <div class="iStyle_Feelings_Tree_Trunk">
                    <a class="iStyle_Feelings_Tree_TrunkTitle">
                        <div class="iStyle_Feelings_Tree_Icon icon-envelope-alt fa-tachometer"></div>
                        菜单管理</a>
                    <div class="iStyle_Feelings_Tree_Leaves">
                        <a class="iStyle_Feelings_Tree_Leaf"
                           href="${pageContext.request.contextPath}/systemMenu/listAuthorityMenus?currpage=1">菜单权限管理</a>
                    </div>
                </div>
            </c:if>
        <div class="is_Shadow"></div>
        <div class="is_SlideButton"><i class="icon-pause-2"></i></div>
    </div>
    <script type="text/javascript">
        $('#menuAdmin').find("a").click(function () {
            $.cookie("active",1,{"path":"/", expires:30});
            $.cookie("auditSettingTag",1,{"path":"/", expires:30})
        });
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            var classes = [
                "iStyle_Feelings_Tree_Icon icon-building fa-bar-chart",
                "iStyle_Feelings_Tree_Icon icon-envelope-alt fa-eyedropper",
                "iStyle_Feelings_Tree_Icon icon-envelope-alt fa-calendar",
                "iStyle_Feelings_Tree_Icon icon-envelope-alt fa-flask",
                "iStyle_Feelings_Tree_Icon icon-envelope-alt",
                "iStyle_Feelings_Tree_Icon icon-envelope-alt fa-eye",
                "iStyle_Feelings_Tree_Icon icon-envelope-alt fa-tachometer",
                "iStyle_Feelings_Tree_Icon icon-building",
                "iStyle_Feelings_Tree_Icon icon-envelope-alt fa-cog"
            ];
            var divs = $("div[class=iStyle_Feelings_Tree_Icon]");
            for(var i = 0; i < divs.length; i++){
                var num = Math.round(Math.random() * (classes.length - 1));
                $(divs[i]).addClass(classes[num]);
            }
        });
    </script>
</div> 
