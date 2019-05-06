<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="iframe"/>
    <script>
        /*
        * 新建 0
        * 编辑 1
        */
        function newPreSoftware(isEdit,id) {
            if(isEdit == 0) {
                var index = layer.open({
                    type: 2,
                    title: '新建',
                    maxmin: true,
                    shadeClose: true,
                    area: ['1100px', '500px'],
                    content: '${pageContext.request.contextPath}/lims/preCourse/newPreSoftware'
                });
                layer.full(index);
            }else {
                var index = layer.open({
                    type: 2,
                    title: '编辑',
                    maxmin: true,
                    shadeClose: true,
                    area: ['1100px', '500px'],
                    content: '${pageContext.request.contextPath}/lims/preCourse/editPreSoftware?id='+id
                });
                layer.full(index);
            }
        }
    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">预排课管理</a></li>
            <li class="end"><a href="javascript:void(0)">预排课房间管理</a></li>
        </ul>
    </div>
</div>
<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
        <li class="TabbedPanelsTab" id="s1">
            <a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/lims/preCourse/preRoomTypeList?currpage=1">房间布局类型</a>
        </li>
        <li class="TabbedPanelsTab" id="s2">
            <a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/lims/preCourse/preCapacityRangeList?currpage=1">容量范围</a>
        </li>
        <li class="TabbedPanelsTab selected" id="s3">
            <a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/lims/preCourse/preSoftwareList?currpage=1">所有软件</a>
        </li>
    </ul>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        所有软件
                        <input class="btn btn-new" type="button" value="新建" onclick="newPreSoftware(0,-1)"/>
                    </div>

                    <table>
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>软件名称</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${preSoftwares}" var="current" varStatus="i">
                            <tr>
                                <td>${current.id}</td>
                                <td>${current.name}</td>
                                <td>
                                    <a onclick="newPreSoftware(1,${current.id})">编辑</a>
                                    <a href="${pageContext.request.contextPath}/lims/preCourse/deletePreSoftware?id=${current.id}">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

