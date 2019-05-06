<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <!-- 下拉的样式结束 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
    <script type="text/javascript">
        function cancel() {
            window.location.href="${pageContext.request.contextPath}/CurrentWeekTimetable"
        }
    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li>
                <a href="javascript:void(0)">
                    课程列表
                </a>
            </li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)">学院本周课程</a>
            </li>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <%--<div class="title">--%>
                        <%--<div id="title">学院本周课程</div>--%>
                    <%--</div>--%>

                    <div class="tool-box">
                        <form:form name="queryForm" action="${pageContext.request.contextPath}/CurrentWeekTimetable" method="post" >
                            <ul>
                                <li>学院</li>
                                <li>
                                    <select id="academyName" name="academyName" class="chzn-select" style="width:400px;">
                                        <c:forEach items="${schoolAcademies}" var="curr">
                                            <c:if test="${curr.academyNumber eq acno}">
                                                <option value="${curr.academyNumber }" selected="selected">${curr.academyName }</option>
                                            </c:if>
                                            <c:if test="${curr.academyNumber  ne acno}">
                                                <option value="${curr.academyNumber  }">${curr.academyName }</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li>周次</li>
                                <li>
                                    <select name="weeks" class="chzn-select">
                                        <option value="">请选择</option>
                                        <c:forEach begin="1" end="20" var="w">
                                            <c:if test="${SchoolWeek eq w}">
                                            <option value="${w}" selected="selected">${weeks[w]}</option>
                                            </c:if>
                                            <c:if test="${SchoolWeek ne w}">
                                                <option value="${w}" >${weeks[w]}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>


                                </li>

                                <li>
                                    <input type="submit" value="查询"/>
                                    <input class="cancel-submit" type="button" value="取消查询" onclick="cancel();"/>
                                </li>
                            </ul>
                        </form:form>
                    </div>
                    <div class="tb" style="text-align: center;">
                        <font style="font-size: 24px">${schoolAcademy.academyName}&nbsp;第${SchoolWeek}周课表&nbsp;&nbsp;&nbsp; 日期：<fmt:formatDate value="${FirstDay.time}" pattern="yyyy年MM月dd日"/>至<fmt:formatDate value="${LastDay.time}" pattern="yyyy年MM月dd日"/></font>
                    </div>
                    <table class="tb" id="my_show">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>日期</th>
                            <th>周次</th>
                            <th>星期</th>
                            <th>课程名称</th>
                            <th>实验项目</th>
                            <th>实验室</th>
                            <th>上课时间</th>
                            <th>授课教师</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${timetableAppointment}" var="current" varStatus="i">
                            <tr>
                                <td>${i.count}</td>
                                <td>${current[12]}</td>
                                <td>${current[9]}</td>
                                <td>${current[8]}</td>
                                <td>${current[6]}</td>
                                <td>${current[19]}</td>
                                <td>${current[13]}</td>
                                <td><c:if test="${current[10]==current[11]}">
                                    ${current[10]}
                                    </c:if>
                                    <c:if test="${current[10]!=current[11]}">
                                        ${current[10]}-${current[11]}
                                    </c:if>
                                </td>
                                <td>${current[15]}</td>
                                <td></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <%--                    <div class="page" >
                                            ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                                            <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=1')" target="_self">首页</a>
                                            <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
                                            第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                                            <option value="${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                                            <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                                                <c:if test="${j.index!=pageModel.currpage}">
                                                    <option value="${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${j.index}">${j.index}</option>
                                                </c:if>
                                            </c:forEach></select>页
                                            <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
                                            <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.lastPage}')" target="_self">末页</a>
                                        </div>--%>
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
        '.chzn-select-width'     : {width:"100%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
</body>
</html>



