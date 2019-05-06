<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<fmt:setBundle basename="bundles.user-resources"/>
<jsp:useBean id="now" class="java.util.Date" />   
<head>
<meta name="decorator" content="iframe" />
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->	
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
</head>
<body>
<div class="iStyle_RightInner">
<div class="navigation">
<div id="navigation">
<ul>
	<li><a href="javascript:void(0)">考试记录</a></li>
</ul>
</div>
</div>
</div>
<div id="contentarea">
<div id="content">
    <div class="title">
        <div id="title">考试记录列表</div>
		<a class="btn btn-new" href="javascript:void(0)" onclick="window.history.go(-1)">返回</a>
	</div>
<div class="content-box">
<table  id="my_show">
<thead>
    <tr>                   
        <th>考试名称</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>学生姓名</th>
        <th>学生成绩</th>
        <th>记录时间</th>
    </tr>
</thead>
<tbody>
<c:forEach items="${tGradeRecord}" var="curr">
<tr>
	<td>${curr.TGradeObject.title}</td>
    <td><fmt:formatDate value="${curr.TGradeObject.startTime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    <td><fmt:formatDate value="${curr.TGradeObject.dueTime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    <td>${curr.user.cname}</td>
    <td>${curr.points}</td>
    <td><fmt:formatDate value="${curr.recordTime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
</c:forEach>
</tbody>
<!-- 分页导航 -->

</table>
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
</script>
<!-- 下拉框的js -->
</body>
</html>

