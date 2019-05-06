<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
    <script type="text/javascript">
        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/labRoom/listLabDoorRecord?currpage=1";
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
            <li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management"/></a></li>
            <li class="end"><a href="javascript:void(0)">门禁管理</a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)">门禁进出记录列表</a>
            </li>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box" style="overflow: hidden">
                        <form name="queryForm" action="${pageContext.request.contextPath}/labRoom/listLabDoorRecord?currpage=1"
                              method="post">
                            <ul>
                                <li>学号:<input type="text" name="username" value="${username}"/></li>
                                <li>姓名:<input type="text" name="cname" value="${cname}"/></li>
                                <li>门禁刷卡时间：</li>
                                <li><input class="easyui-datebox" id="startDate" value="${startDate}" name="startDate"
                                           onclick="new Calendar().show(this);" readonly="readonly"/>
                                </li>
                                <li>到：</li>
                                <li><input class="easyui-datebox" id="endDate" value="${endDate}" name="endDate"
                                           onclick="new Calendar().show(this);" readonly="readonly"/></li>
                                <li>实验室名称查询:</li>
                                <li><input type="text" id="labRoomName" name="labRoomName" value="${labRoomName}"
                                           style="float: none; width:50px;"></li>
                                <li>
                                    <input type="submit" value="查询"/>
                                    <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li>
                            </ul>
                        </form>
                    </div>
                    <table class="tb" id="my_show">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>姓名</th>
                            <th>学号</th>
                            <th>学院</th>
                            <th>班级</th>
                            <th>专业</th>
                            <th>实验室名称(编号)</th>
                            <th>时间</th>
                            <th>状态</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listLabDoorRecords}" var="curr" varStatus="i">
                            <tr>
                                <td>${i.count}</td>
                                <td>${curr[0]}</td>
                                <td>${curr[1]}</td>
                                <td>${curr[2]}</td>
                                <td>${curr[3]}</td>
                                <td>${curr[4]}</td>
                                <td>${curr[5]}</td>
                                <td>${curr[6]}</td>
                                <td>${curr[7]}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!-- 分页[s] -->
                    <div class="page">
                        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabDoorRecord?currpage=1')"
                           target="_self">首页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabDoorRecord?currpage=${pageModel.previousPage}')"
                           target="_self">上一页</a>
                        第<select onchange="targetUrl(this.options[this.selectedIndex].value)">
                        <option value="${pageContext.request.contextPath}/labRoom/listLabDoorRecord?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                   var="current">
                            <c:if test="${j.index!=pageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/labRoom/listLabDoorRecord?currpage=${j.index}">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabDoorRecord?currpage=${pageModel.nextPage}')"
                           target="_self">下一页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabDoorRecord?currpage=${pageModel.lastPage}')"
                           target="_self">末页</a>
                    </div>
                    <!-- 分页[e] -->
                    <!-- 下拉框的js -->
                    <script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
                            type="text/javascript"></script>
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
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
