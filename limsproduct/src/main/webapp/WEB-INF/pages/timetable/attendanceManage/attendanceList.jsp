<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html >
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
    <link rel="stylesheet" href="/dhulims/css/iconFont.css">
    <script type="text/javascript">
        // 取消查询
        function cancel(){
            window.location.href="${pageContext.request.contextPath}/attendanceList?currpage=1";
        }
        // 分页跳转
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
    </script>
    <script type="text/javascript">
        function openLayer(url) {
            var index = layer.open({
                type: 2,
                title: '课程考勤',
                maxmin: true,
                shadeClose: true,
                content: url,
            });
            layer.full(index);
        }
    </script>
</head>

<body>

<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">排课管理</a></li>
            <li class="end"><a href="javascript:void(0)">我的考勤</a></li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box">
                        <form name="queryForm" action="${pageContext.request.contextPath}/attendanceList?currpage=1" method="post">
                            <ul>
                                <li>学期:
                                    <select name="schoolTerm" class="chzn-select">
                                        <c:forEach items="${schoolTerms}" var="current">
                                            <c:if test="${current.id == termId}">
                                                <option value="${current.id}" selected>${current.termName} </option>
                                            </c:if>
                                            <c:if test="${current.id != termId}">
                                                <option value="${current.id}">${current.termName} </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li>课程:
                                    <select name="course_no" class="chzn-select">
                                        <option value="">请选择</option>
                                        <c:forEach items="${course_list}" var="current">
                                            <c:if test="${current[0] eq course_no}">
                                                <option value="${current[0]}" selected>${current[1]}[${current[0]}]-${current[2]}</option>
                                            </c:if>
                                            <c:if test="${current[0] ne course_no}">
                                                <option value="${current[0]}">${current[1]}[${current[0]}]-${current[2]} </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li>
                                    <input type="submit" value="查询"/>
                                    <input type="button" class="cancel-submit" value="取消" onclick="cancel();"/></li>
                            </ul>
                        </form>
                    </div>
                    <table  class="tb"  id="my_show">
                        <thead>
                        <tr>
                            <th>选课组</th>
                            <th>学院</th>
                            <th>课程</th>
                            <th>教师</th>
                            <th>上课日期</th>
                            <th>节次</th>
                            <th>学期</th>
                            <th>班级人数</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${courseInfos}" var="t">
                            <tr>
                                <td>${t[0]}</td>
                                <td>${t[1]}</td>
                                <td>${t[2]}</td>
                                <td>${t[3]}</td>
                                <td>${t[4]}</td>
                                <td>第${t[5]}-${t[6]}节</td>
                                <td>${t[7]}</td>
                                <td><a title="查看学生名单" href="javascript:openLayer('${pageContext.request.contextPath}/studentsList/${t[0]}/${t[9]}/${t[10]}/${t[11]}/${t[5]}/${t[6]}')">考勤${t[8]}</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!-- 分页[s] -->
                    <div class="page" >
                        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                        <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/attendanceList?currpage=1')" target="_self">首页</a>
                        <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/attendanceList?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
                        第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                        <option value="${pageContext.request.contextPath}/attendanceList?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                            <c:if test="${j.index!=pageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/attendanceList?currpage=${j.index}">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/attendanceList?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
                        <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/attendanceList?currpage=${pageModel.lastPage}')" target="_self">末页</a>
                    </div>
                    <!-- 分页[e] -->
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