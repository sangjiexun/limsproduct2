<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css"/>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <!-- 下拉的样式结束 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <script type="text/javascript">
        //取消查询
        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?tabpage=1&respage=1"
        }

        //跳转
        function targetUrl(url) {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
    </script>
    <style type="text/css">
        td, th, table {
            /*border: 1px solid gray !important*/
        }
    </style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li>
                <a href="javascript:void(0)">
                    实验室课表和预约结果
                </a>
            </li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="tb" style="text-align: center;">
                    <font style="font-size: 24px">实验室课表和预约结果&nbsp;&nbsp;&nbsp; 日期：<fmt:formatDate value="${date.time}" pattern="yyyy年MM月dd日"/>&nbsp;星期${schoolWeekday}</font>
                </div>
                <div class="tool-box">
                    <form:form name="queryForm" action="" method="post">
                        <ul>
                            <li><spring:message code="all.trainingRoom.labroom"/>:</li>
                            <li>
                                <select name="labRoom" class="chzn-select" id="labRoom">
                                    <option value="">请选择</option>
                                    <c:forEach items="${labRooms}" var="lab">
                                        <c:if test="${lab.id == labRoom.id}">
                                            <option value="${labRoom.id}" selected="selected">${labRoom.labRoomName }</option>
                                        </c:if>
                                        <option value="${lab.id}">${lab.labRoomName }</option>
                                    </c:forEach>
                                </select>
                            </li>
                            <li>日期:</li>
                            <li>
                                <input class="easyui-datebox"  id="time" name="time"  value="${time}" type="text"  onclick="new Calendar().show(this);" style="width:100px;" />
                            </li>
                            <li>
                                <input type="button" value="查询" onclick="targetUrl('${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?tabpage=1&respage=1');"/>
                                <input class="cancel-submit" type="button" value="取消查询" onclick="cancel();"/>
                            </li>
                        </ul>
                    </form:form>
                </div>

                <div class="content-box">
                    <div class="title">
                        <div id="title">实验室课表</div>
                    </div>
                    <table class="tb" id="my_show">
                        <thead>
                        <tr>
                            <th>节次</th>
                            <th>实验室名称</th>
                            <th>课程名称</th>
                            <th>任课教师</th>
                            <th>上课班级</th>
                            <th>起止周次</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${labTimetable}" var="curr">
                            <tr>
                                <td>${curr[7]}-${curr[8]}</td>
                                <td>${curr[0]}</td>
                                <td>${curr[3]}</td>
                                <td>${curr[1]}</td>
                                <td>${curr[2]}</td>
                                <td>${curr[5]}-${curr[6]}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="page" >
                        ${tabpageModel.totalRecords}条记录,共${tabpageModel.totalPage}页
                        <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?tabpage=1&respage=${respageModel.currpage}')" target="_self">首页</a>
                        <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?tabpage=${tabpageModel.previousPage}&respage=${respageModel.currpage}')" target="_self">上一页</a>
                        第<select onchange="targetUrl(this.options[this.selectedIndex].value);">
                        <option value="${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?tabpage=${tabpageModel.currpage}&respage=${respageModel.currpage}">${tabpageModel.currpage}</option>
                        <c:forEach begin="${tabpageModel.firstPage}" end="${tabpageModel.lastPage}" step="1" varStatus="j" var="current">
                            <c:if test="${j.index!=tabpageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?tabpage=${j.index}&respage=${respageModel.currpage}">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?tabpage=${tabpageModel.nextPage}&respage=${respageModel.currpage}')" target="_self">下一页</a>
                        <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?tabpage=${tabpageModel.lastPage}&respage=${respageModel.currpage}')" target="_self">末页</a>
                    </div>
                </div>
                <div style="height: 8px">
                </div>
                <div class="content-box">
                    <div class="title">
                        <div id="title">实验室预约结果</div>
                    </div>
                    <table class="tb" id="my_show">
                        <thead>
                        <tr>
                            <th>起止时间</th>
                            <th>实验室名称</th>
                            <th>课程名称</th>
                            <th>任课教师</th>
                            <th>上课班级</th>
                            <th>起止周次</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${labReservation}" var="current" varStatus="i">
                            <tr>
                                <td>${current[7]}-${current[8]}</td>
                                <td>${current[0]}</td>
                                <td>${current[3]}</td>
                                <td>${current[1]}</td>
                                <td>${current[2]}</td>
                                <td>${current[5]}-${current[6]}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="page" >
                        ${respageModel.totalRecords}条记录,共${respageModel.totalPage}页
                        <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?respage=1&tabpage=${tabpageModel.currpage}')" target="_self">首页</a>
                        <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?respage=${respageModel.previousPage}&tabpage=${tabpageModel.currpage}')" target="_self">上一页</a>
                        第<select onchange="targetUrl(this.options[this.selectedIndex].value);">
                        <option value="${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?respage=${respageModel.currpage}&tabpage=${tabpageModel.currpage}">${respageModel.currpage}</option>
                        <c:forEach begin="${respageModel.firstPage}" end="${respageModel.lastPage}" step="1" varStatus="j" var="current">
                            <c:if test="${j.index!=respageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?respage=${j.index}&tabpage=${tabpageModel.currpage}">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?respage=${respageModel.nextPage}&tabpage=${tabpageModel.currpage}')" target="_self">下一页</a>
                        <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labCurrDayTimetableAndReservation?respage=${respageModel.lastPage}&tabpage=${tabpageModel.currpage}')" target="_self">末页</a>
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
        '.chzn-select-width': {width: "100%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
</body>
</html>




