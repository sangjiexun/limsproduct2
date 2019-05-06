<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>

    <script type="text/javascript">
        function cancel()
        {
            window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=9&type=3";
        }
        //跳转
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
        //设置
        function openSetupLink(labRoomId,currpage,type){//将labRoomId page传递到后台
            var index = layer.open({
                type: 2,
                title: '设置',
                maxmin: true,
                shadeClose: true,
                content: "${pageContext.request.contextPath}/device/editLabRoomSettingRest/"+labRoomId+"/"+currpage+"/"+type,
            });
            layer.full(index);
        }
    </script>
    <script type="text/javascript">
        function getMeetingRoom(id, currpage, type){
            var index = layer.open({
                type: 2,
                title: '查看',
                maxmin: true,
                shadeClose: true,
                content: "${pageContext.request.contextPath}/labRoom/getLabRoom?id=" + id + "&currpage=" + currpage + "&type= " + type
            });
            layer.full(index);
        }

        function editMeetingRoom(id, page, type) {
            var index = layer.open({
                type: 2,
                title: '编辑',
                maxmin: true,
                shadeClose: true,
                content: "${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId=" + id + "&type=" + type + "&page=" + page,
                end:function(){
                    document.queryForm.action="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=" + page + "&orderBy=${orderBy}&type=" + type;
                    document.queryForm.submit();
                }
            });
            layer.full(index);
        }
    </script>
    <style type="text/css">
        .tool-box{
            height: 72px;
        }
    </style>
</head>

<body>
<script type="text/javascript">

</script>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management" /></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.meeting.management"/></a></li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)"><spring:message code="left.meeting.management" />列表</a>
            </li>
            <c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
                <a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId=0&type=3&page=${page}">新建</a>
            </c:if>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <%--<div class="title">--%>
                        <%--<div id="title"><spring:message code="left.meeting.management" />列表</div>--%>
                        <%--<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">--%>
                            <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId=0&type=3&page=${page}">新建</a>--%>
                        <%--</c:if>--%>
                    <%--</div>--%>

                    <div class="tool-box">
                        <form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=${orderBy }&type=3" method="post" modelAttribute="labRoom">
                            <ul>
                                <li>会议室名称:<form:input id="lab_name" path="labRoomName"/></li>
                                <li>会议室编号:<form:input id="labRoomNumber" path="labRoomNumber"/></li>
                                <li>会议室地点:<form:input id="labRoomAddress" path="labRoomAddress"/></li>
                                <li>会议室容量:<form:input id="labRoomCapacity" path="labRoomCapacity"/></li>
                                <li>有无多媒体:
                                    <form:select id="CDictionaryByIsMultimedia" path="CDictionaryByIsMultimedia.CNumber" class="chzn-select">
                                        <form:option value="">请选择</form:option>
                                        <form:option value="1">有</form:option>
                                        <form:option value="2">无</form:option>
                                    </form:select>
                                </li>
                                <li>
                                    <input type="submit" value="查询"/>
                                    <input type="button" class="cancel-submit" value="取消" onclick="cancel();"/></li>
                            </ul>
                        </form:form>
                    </div>

                    <div id="showRegulations" class="easyui-window" closed="true" modal="true" minimizable="true" title="规章制度详情" style="width: 580px;height: 250px;padding: 20px">
                        <div class="content-box">
                            <table id="my_show">
                                <tbody id="labRoom_body">

                                </tbody>
                            </table>
                        </div>
                    </div>

                    <table class="tb" id="my_show">
                        <thead>
                        <tr>
                            <th><a >会议室名称</a></th>
                            <th><a >会议室编号</a></th>
                            <th><a >会议室地点</a></th>
                            <th><a >容量</a></th>
                            <th><a >使用面积</a></th>
                            <th><a >当前状态</a></th>
                            <th><a >操作</a></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listLabMeetingRoom}" var="curr">
                            <tr>
                                    <%--<td>${curr.labCenter.centerName}</td>--%>
                                <td>${curr.labRoomName}</td>
                                <td>${curr.labRoomNumber}</td>
                                <td>${curr.labRoomAddress}</td>
                                <td>${curr.labRoomCapacity}</td>
                                <td>${curr.labRoomArea}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${curr.labRoomActive==1}">可用</c:when>
                                        <c:otherwise>不可用</c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${curr.labRoomReservation==1}">可预约</c:when>
                                        <c:otherwise>不可预约</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                        <%--<a href="javascript:void(0)" onclick="opendoor(${curr.id});">远程开门</a>--%>
                                            <a href="javascript:void(0)" onclick="getMeetingRoom('${curr.id}','${pageModel.currpage}','${type}')">查看</a>
                                    <c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5' || sessionScope.auth_level eq '6'}">
                                        <a  href="javascript:void(0)" onclick="openSetupLink('${curr.id}','${pageModel.currpage}','${type}')">设置</a>
                                        <a href="javascript:void(0)" onclick="editMeetingRoom('${curr.id}','${pageModel.currpage}','${type}')">编辑</a>
                                        <%-- <a href="${pageContext.request.contextPath}/labRoom/deleteLabRoom?labRoomId=${curr.id}" onclick="return confirm('确定删除？');">删除</a> --%>
                                        <a href="${pageContext.request.contextPath}/labRoom/deleteLabRoom?labRoomId=${curr.id}&type=3&page=${page}" onclick="return confirm('确定删除？');">删除</a>
                                        <%--<a href="javascript:void(0);" onclick="showRegulations(${curr.id})">规章制度</a>--%>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!-- 分页[s] -->
                    <div class="page" >
                        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                        <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=${orderBy }&type=3')" target="_self">首页</a>
                        <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.previousPage}&orderBy=${orderBy }&type=3')" target="_self">上一页</a>
                        第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                        <option value="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.currpage}&orderBy=${orderBy }&type=3">${pageModel.currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                            <c:if test="${j.index!=pageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${j.index}&orderBy=${orderBy }&type=3">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.nextPage}&orderBy=${orderBy }&type=3')" target="_self">下一页</a>
                        <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.lastPage}&orderBy=${orderBy }&type=3')" target="_self">末页</a>
                    </div>
                    <!-- 分页[e] -->
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
