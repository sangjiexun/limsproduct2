<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" isELIgnored="false"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<html>
<head>
        <meta name="decorator" content="iframe"/>
        <!-- 下拉的样式 -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
        <!-- 下拉的样式 -->
        <link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />

        <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
        <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

        <script type="text/javascript">
        /*    //首页
            function first(url){
                document.queryForm.action=url;
                document.queryForm.submit();
            }
            //末页
            function last(url){
                document.queryForm.action=url;
                document.queryForm.submit();
            }
            //上一页
            function previous(url){
                var page=${page};
                if(page==1){
                    page=1;
                }else{
                    page=page-1;
                }
                //alert("上一页的路径："+url+page);
                document.queryForm.action=url+page;
                document.queryForm.submit();
            }
            //下一页
            function next(url){
                var totalPage=${pageModel.totalPage};
                var page=${page};
                if(page>=totalPage){
                    page=totalPage;
                }else{
                    page=page+1
                }
                //alert("下一页的路径："+page);
                document.queryForm.action=url+page;
                document.queryForm.submit();
            }*/
        </script>

    </head>

<body>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">

        <div class="TabbedPanelsTabGroup-box">
            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">
                    <div class="content-box">
                        <%--<input type="radio" name="isStation" checked="checked"/><spring:message code="all.trainingRoom.labroom" />预约
                        <c:if test="${jobReservation eq 'true'}">
                            <input type="radio" name="isStation" onclick="toStation()"/>工位预约
                        </c:if>
                        <div class="tool-box">
                            <form:form name="queryForm" action="${pageContext.request.contextPath}/labRoomLending/labReservationList?page=1&tage=${tage}&isaudit=${isAudit}" method="post" modelAttribute="labReservation">
                                <ul style="position: absolute;">
                                    <li><spring:message code="all.trainingRoom.labroom" />：<form:input path="labRoom.labRoomName"/> </li>
                                    <li><input type="submit" value="查询" ></li>
                                    <li><input type="button" onclick="cancleQuery()" value="取消"></li>
                                </ul>
                            </form:form>
                        </div>--%>
                        <table class="tb" id="my_show">
                            <thead>
                            <tr>
                                <th>镜像名称</th>
                                <th>预约人</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>预约理由</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${virtualImageReservations}" var="current" varStatus="i">

                                <tr>
                                    <td>${current.virtualImageName}</td>
                                    <td>${current.userName}</td>
                                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${current.startTime.time}" /></td>
                                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${current.endTime.time}" /></td>
                                    <td>${current.remarks}</td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>

                        <!-- 分页模块 -->
                        <div class="page" >
                            ${totalRecords}条记录,共${pageModel.totalPage}页
                            <a href="${pageContext.request.contextPath}/virtual/layerListVirtualImageReservation?imageId=${imageId}&currpage=1" target="_self">首页</a>
                            <a href="${pageContext.request.contextPath}/virtual/layerListVirtualImageReservation?imageId=${imageId}&currpage=${pageModel.previousPage}" target="_self">上一页</a>
                            第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                            <option value="${pageContext.request.contextPath}/virtual/layerListVirtualImageReservation?imageId=${imageId}&currpage=${page}">${page}</option>
                            <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                                <c:if test="${j.index!=page}">
                                    <option value="${pageContext.request.contextPath}/virtual/layerListVirtualImageReservation?imageId=${imageId}&currpage=${j.index}">${j.index}</option>
                                </c:if>
                            </c:forEach></select>页
                            <a href="${pageContext.request.contextPath}/virtual/layerListVirtualImageReservation?imageId=${imageId}&currpage=${pageModel.nextPage}" target="_self">下一页</a>
                            <a href="${pageContext.request.contextPath}/virtual/layerListVirtualImageReservation?imageId=${imageId}&currpage=${pageModel.totalPage}" target="_self">末页</a>
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

    function VirtualLogin(id) {
        url="${pageContext.request.contextPath}/virtual/virtualLogin?virtualImageReservationid="+id;
        window.open(url);
    }

    function VirtualLoginCitrix(id) {
        url="${pageContext.request.contextPath}/virtual/virtualLoginCitrix?virtualImageReservationid="+id;
        window.open(url);
    }

</script>
<!-- 下拉框的js -->

</body>
</html>


