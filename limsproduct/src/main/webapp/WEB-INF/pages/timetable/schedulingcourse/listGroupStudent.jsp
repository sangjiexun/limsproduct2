<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<html>
<head>
<meta name="decorator" content="iframe"/>
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />

<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->	
</head>
<body>
<div class="iStyle_RightInner">

<div class="navigation">
<div id="navigation">
<ul>
	<li><a href="javascript:void(0)">排课管理</a></li>
	<%-- <li class="end"><a href="${pageContext.request.contextPath}/timetable/listTimetableTerm?id=-1">查看学生选课情况</a></li> --%>
</ul>
</div>
</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<div class="title">学生分组列表
<div style="float:right">
   <a href="#" class="btn" onclick="addGroupStudents()">添加学生名单</a>
</div>
</div>
<table> 
<thead>
<tr>
   <th>序号</th>
   <th>学生编号</th>
   <th>学生姓名</th>
    <th>学生班级</th>
   <th>学院名称</th>
</tr>
</thead>
<tbody>
<c:forEach items="${groupStudents}" var="current"  varStatus="i">
<tr>
    <td>${i.count}</td>
    <td>${current.user.username}</td>
    <td>${current.user.cname}</td>
       <td>${current.user.schoolClasses.className}</td>
    <td>${current.user.schoolAcademy.academyName }</td>
</tr>
</c:forEach> 
</tbody>
</table>
</div>
</div>
</div>
</div>
</div>
</div>
<!-- 增加学生名单 -->
<div id="doSearchStudents" class="easyui-window" title="查看学生名单" modal="true"	closed="true" iconCls="icon-add" style="width:1000px;height:500px;">
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

<script type="text/javascript">

   / *添加学生名单*/
    function addGroupStudents() {
        var con = '<iframe scrolling="yes" id="message1" frameborder="0"  src="${pageContext.request.contextPath}/timetable/viewGroupStudent?groupId=${groupId}&flag=1'
            + '" style="width:100%;height:100%;"></iframe>'
        $('#doSearchStudents').html(con);
        $("#doSearchStudents").show();
        //获取当前屏幕的绝对位置
        var topPos = window.pageYOffset;
        //使得弹出框在屏幕顶端可见
        $('#doSearchStudents').window({left:"px", top:topPos+"px"});
        $('#doSearchStudents').window('open');
    }
</script>
</body>
</html>