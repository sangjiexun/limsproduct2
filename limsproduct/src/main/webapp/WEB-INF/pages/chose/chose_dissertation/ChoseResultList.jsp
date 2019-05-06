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
				<li><a href="javascript:void(0)">导师论文互选</a>
				</li>
				<li class="end"><a href="javascript:void(0)">导师论文互选列表</a>
				</li>
			</ul>
		</div>
	</div>

	<div id="TabbedPanels1" class="TabbedPanels">
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
					<div class="title">
						<div id="title">导师论文互选列表</div>
						  <a class="btn btn-new" href="javascript:window.history.back();">返回</a>
					</div>

					<div class="tool-box">
						<form:form name="queryForm"
							action="${pageContext.request.contextPath}/nwuChose/ChoseResultList?themeId=${theme.id}&currpage=1"
							method="post" modelAttribute="choseTheme">
							<%--
			 <ul>
  				<li>实验项目名称： </li>
  				<li><form:input id="lp_name" path="lpName"/></li>
  				<li>学期：</li>
  				<li>
  				  <form:select path="schoolTerm.id" id="term_id">
  				    <form:option value="${schoolTerm.id }">${schoolTerm.termName }</form:option>
  				    <c:forEach items="${schoolTerms}" var="curr">
  				    	<c:if test="${curr.id ne schoolTerm.id }">
	  				    	<form:option value="${curr.id }">${curr.termName }</form:option>
	  				    </c:if>
  				    </c:forEach>
  				  </form:select>
  				</li>
  				<li>所属课程：</li>
  				<li>
	  			    <form:select path="schoolCourseInfo.courseNumber"  id="course_number" class="chzn-select">
	  				    <form:option value="">请选择</form:option>
	  				    <c:forEach items="${schoolCourseInfos}" var="sc">
	  				    	<form:option value="${sc.key}">[${sc.key}]${sc.value }</form:option>
	  				    </c:forEach>
	  				</form:select>
  				</li>
  				<li><input type="submit" value="查询"/>
			      <input type="button" value="取消" onclick="cancel();"/></li>
  				</ul>
			 
		--%>
						</form:form>
					</div>
					<table class="tb" id="my_show">
						<thead>
							<tr>
								<th>编号</th>
								<th>导师编号</th>
								<th>导师姓名</th>
								<th>立题数量</th>
								<th>最终所带学生人数</th>
								<th>学生列表</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${choseProfessors}" var="professor"
								varStatus="i">
								<tr>
									<td>${(pageModel.currpage-1)*pageSize+i.count }</td>
									<td>${professor.user.username}</td>
									<td>${professor.user.cname}</td>
									<td>${professor.expectNumber}</td>
									<td>${professor.finalNumber}</td>
									<td><a href="javascript:void(0)"
										onclick="studentList('${theme.id}','${professor.id }')">学生列表</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="page">
						${pageModel.totalRecords}条记录,共${pageModel.totalPage}页 <a
							href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/choseResultList?themeId=${theme.id}&currpage=1')"
							target="_self">首页</a> <a href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/choseResultList?themeId=${theme.id}&currpage=${pageModel.previousPage}')"
							target="_self">上一页</a> 第<select
							onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
							<option
								value="${pageContext.request.contextPath}/choseDissertation/choseResultList?themeId=${theme.id}&currpage=${pageModel.currpage}">${pageModel.currpage}</option>
							<c:forEach begin="${pageModel.firstPage}"
								end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
								<c:if test="${j.index!=pageModel.currpage}">
									<option
										value="${pageContext.request.contextPath}/choseDissertation/choseResultList?themeId=${theme.id}&currpage=${j.index}">${j.index}</option>
								</c:if>
							</c:forEach>
						</select>页 <a href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/choseResultList?themeId=${theme.id}&currpage=${pageModel.nextPage}')"
							target="_self">下一页</a> <a href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/choseResultList?themeId=${theme.id}&currpage=${pageModel.lastPage}')"
							target="_self">末页</a>
					</div>

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
						<th style="width:30% !important">题目</th>
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
				url : "${pageContext.request.contextPath}/choseDissertation/passStudentListForProfessor?themeId="
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
