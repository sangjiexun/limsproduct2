<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->

    <script type="text/javascript">
        //取消查询
        function cancel()
        {
            window.location.href="${pageContext.request.contextPath}/inspect/listInspectGrading?currpage=1";
        }
        //跳转
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">实验室评价</a></li>
            <li class="end"><a href="javascript:void(0)">评价列表</a></li>
        </ul>
    </div>
</div>

<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab1 selected" id="s1">
            <a href="javascript:void(0)">安全检查评价</a>
        </li>
        <a class="btn btn-new" href="${pageContext.request.contextPath}/inspect/newInspectGrading?idKey=0&roomId=0">新建</a>
    </ul>
    <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
            <div class="content-box">
                <div class="title">
                    <div id="title">实验室评价</div>
                    <a class="btn btn-new" href="${pageContext.request.contextPath}/inspect/newInspectGrading?idKey=0&roomId=0">新建</a>
                </div>
                <!-- 查询开始  -->
                <div class="tool-box">
                    <form:form id="form_act" name="queryForm" action="${pageContext.request.contextPath}/inspect/listInspectGrading?currpage=1" method="post" modelAttribute="commonDocument">
                        <table >
                            <tbody>
                            <tr>
                                <td>
                                    实验室名称:
                                    <form:input id="lab_room_name" path="labRoom.labRoomName"/>
                                </td>
                                <%--<td>--%>
                                    <%--<form:input id="lab_room_name" path="labRoom.labRoomName"/>--%>
                                <%--</td>--%>
                                <td>
                                    <span style="float:left;margin:2px 0 0;">操作时间：</span>
                                    <input class="easyui-datebox" id="starttime" name="starttime" type="text" onclick="new Calendar().show(this);" style="width:100px;" />
                                    <span style="float: left;position:relative;margin:0 2px;">到</span>
                                    <input  class="easyui-datebox" id="endtime" name="endtime" type="text"  onclick="new Calendar().show(this);" style="width:100px;" />
                                </td>
                                <%--<td>--%>
                                    <%--<input class="easyui-datebox" id="starttime" name="starttime" type="text" onclick="new Calendar().show(this);" style="width:100px;" />--%>
                                    <%--&nbsp;到&nbsp;<input  class="easyui-datebox" id="endtime" name="endtime" type="text"  onclick="new Calendar().show(this);" style="width:100px;" />--%>
                                <%--</td>--%>
                                <td>
                                    <input type="submit" value="查询"/>
                                    <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
                                </td>
                                <%--<td>--%>
                                    <%--<input type="button" value="取消" onclick="cancel();"/>--%>
                                <%--</td>--%>
                            </tr>
                            </tbody>
                        </table>
                    </form:form>
                </div>
                <table class="tb" id="my_show">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>实验室名称</th>
                        <th>检查名称</th>
                        <th>姓名</th>
                        <th>工号</th>
                        <th>学院</th>
                        <th>上传时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listLabRoomComments}" var="curr">
                        <tr>
                            <td>${curr[0]}</td>
                            <td>${curr[1]}</td>
                            <td>${curr[2]}</td>
                            <td>${curr[3]}</td>
                            <td>${curr[4]}</td>
                            <td>${curr[5]}</td>
                            <td><fmt:formatDate value="${curr[6].time}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <!-- 操作开始  -->
                            <td>
                                <a href="${pageContext.request.contextPath}/inspect/viewInspectGrading?idKey=${curr[0] }">图片详情</a>
                                <a href="${pageContext.request.contextPath}/inspect/deleteInspectGrading?idKey=${curr[0] }" onclick="return confirm('确认要删除吗？')">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="page" >
                    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/inspect/listInspectGrading?currpage=1')" target="_self">首页</a>
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/inspect/listInspectGrading?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
                    第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                    <option value="${pageContext.request.contextPath}/inspect/listInspectGrading?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                        <c:if test="${j.index!=pageModel.currpage}">
                            <option value="${pageContext.request.contextPath}/inspect/listInspectGrading?currpage=${j.index}">${j.index}</option>
                        </c:if>
                    </c:forEach></select>页
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/inspect/listInspectGrading?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/inspect/listInspectGrading?currpage=${pageModel.lastPage}')" target="_self">末页</a>
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
        '.chzn-select': {search_contains : true},
        '.chzn-select-deselect'  : {allow_single_deselect:true},
        '.chzn-select-no-single' : {disable_search_threshold:10},
        '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
        '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>
