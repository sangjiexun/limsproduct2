<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="iframe" />

<!-- 下拉框的样式 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
</head>

<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)">导师互选</a>
				</li>
				<li class="end"><a href="javascript:void(0)">我的学生--导师</a>
				</li>
			</ul>
		</div>
	</div>

	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
			<li class="TabbedPanelsTab1 selected" id="s1">
				<a href="javascript:void(0)">我的学生--导师</a>
			</li>
		</ul>
		<%--<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s0"><a href="${pageContext.request.contextPath}/ChoseThemeList?currpage=1">全部</a></li>
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=1&orderBy=${orderBy }">草稿</a></li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=2&orderBy=${orderBy }">审核中</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=3&orderBy=${orderBy }">审核通过</a></li>
		<li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=4&orderBy=${orderBy }">审核拒绝</a></li>
	</ul>
  --%>
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">

				<div class="content-box">
					<%--<div class="title">--%>
						<%--<div id="title">导师互选列表</div>--%>
					<%--</div>--%>
					<%--<div class="tool-box">--%>
					<%--</div>--%>
					<table class="tb" id="my_show">
						<thead>
							
							<tr>
								<th>学号</th>
								<th>姓名</th>
								<th>届</th>
								<!-- <th>操作</th> -->
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${users}" var="user" varStatus="i">
								<tr>
									<td>${user.username }</td>
									<td>${user.cname}</td>
									<td>${user.attendanceTime}</td>
									<!--  <td>
	      <a href="javascript:void(0)">编辑</a>
	      <a href="javascript:deleteOperationItemRest();" onclick="return confirm('确定删除？');">删除</a>
	      <a href="javascript:void(0)">添加学生</a>
	    </td> -->
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="studentList" class="easyui-window " title="学生列表" align="left"
		title="" modal="true" maximizable="false" collapsible="false"
		closed="true" minimizable="false" style="width: 600px; height: 500px;">
		<div class="content-box">
			<table id="my_show">
				<thead>
					<tr>
						<th style="width:30% !important">学号</th>
						<th style="width:30% !important">姓名</th>
						<th style="width:30% !important">所属学院</th>
					</tr>
				</thead>
				<tbody id="user_body">
				</tbody>
			</table>
		</div>
	</div>
	<!-- 下拉框的js -->
	<script
		src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
		type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		function studentList(themeId, professorId) {
			$.ajax({
				url : "${pageContext.request.contextPath}/passStudentListForProfessor?themeId="
						+ themeId + "&professorId=" + professorId,
				type : "POST",
				success : function(data) {//AJAX查询成功
					document.getElementById("user_body").innerHTML = data;
				}
			});
			$("#studentList").show();
			$("#studentList").window('open');
		}
	</script>
	<!-- 下拉框的js -->
</body>
</html>
