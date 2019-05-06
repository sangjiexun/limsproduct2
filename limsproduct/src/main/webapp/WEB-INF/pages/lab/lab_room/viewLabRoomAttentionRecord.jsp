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

<!-- 打印插件的引用 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<!-- 添加学生对应js -->
<script type="text/javascript">
//跳转
function targetUrl(url)
{
  document.queryForm.action=url;
  document.queryForm.submit();
}

</script>

</head>
<body>
<div class="iStyle_RightInner">

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<div class="tool-box">
	<!-- 查找框  -->
		<form:form name="queryForm" action="" method="post" modelAttribute="student">
			 <ul>
  				
  				<li><input type="hidden" value="查询"/>
			      <input type="hidden" value="取消" onclick="cancel();"/></li>
  				</ul>
		</form:form>
</div>

<table id="listTable"> 
<thead>
	<tr>
		<th>序号</th>
		<th>姓名</th>
		<th>学号</th>
		<th>阅读时间</th>
		<th>状态</th>
	</tr>
</thead>
<tbody>

<c:forEach items="${attentions}" var="attention" varStatus="i">
	<tr>
		<td>${i.count }</td>
		<td>${attention.cname }</td>
		<td>${attention.username }</td>
		<td>
			<fmt:setLocale value="zh_CN"/><fmt:formatDate value="${attention.date.time}" pattern="yyyy年MM月dd日 HH:mm"/>
		</td>
		<td>
			<c:if test="${attention.enable eq 1}">正常</c:if>
			<c:if test="${attention.enable ne 1}">失效</c:if>
		</td>
	</tr>
</c:forEach>
</tbody>
</table>
<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/viewLabRoomAttentionRecordDevice?deviceNumber=' + ${deviceNumber} + '&page==1')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/viewLabRoomAttentionRecordDevice?deviceNumber=' + ${deviceNumber} + '&page==${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option selected="selected" value="${pageContext.request.contextPath}/labRoom/viewLabRoomAttentionRecordDevice?deviceNumber=${deviceNumber}&page==${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/labRoom/viewLabRoomAttentionRecordDevice?deviceNumber=${deviceNumber}&page==${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/viewLabRoomAttentionRecordDevice?deviceNumber=' + ${deviceNumber} + '&page==${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/viewLabRoomAttentionRecordDevice?deviceNumber=' + ${deviceNumber} + '&page==${pageModel.lastPage}')" target="_self">末页</a>
</div>
</div>
</div>
</div>
</div>
</div>
</div>

</body>
</html>