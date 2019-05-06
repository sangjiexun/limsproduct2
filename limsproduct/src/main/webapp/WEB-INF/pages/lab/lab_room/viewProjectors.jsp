<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html >
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
    <link rel="stylesheet" href="/dhulims/css/iconFont.css">
</head>

<body>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="content-box">
                        <table  class="tb"  id="my_show">
                            <thead>
                            <tr>
                                <th>所在实验室</th>
                                <th>投影机编号</th>
                                <th>投影机型号</th>
                                <th>灯泡使用时长</th>
                                <th>灯泡使用上限</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${queryHQLs}" var="t">
                                <tr>
                                    <td>${labRoom.labRoomName}</td>
                                    <td>${t[1]}</td>
                                    <td>${t[2]}</td>
                                    <td>${t[3]}</td>
                                    <td>${t[4]}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
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