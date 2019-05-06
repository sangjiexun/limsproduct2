 <%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html>
<head>
	<meta name="decorator" content="iframe"/>
	<!-- 下拉框的样式 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
	<!-- 下拉的样式结束 -->
	<!-- 打印开始 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
	<!-- 打印结束 -->
	<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/timetable/lmsReg.css">
	<script type="text/javascript">
        //取消查询
        function cancel()
        {
            window.location.href="${pageContext.request.contextPath}/report/reportSupervise?currpage=1";
        }
        //跳转
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
        //导出
        function exportSupervise()
        {
            document.queryForm.action="${pageContext.request.contextPath}/report/exportSupervise";
            document.queryForm.submit();
        }
	</script>
	<style type="text/css">
		#my_show {
			border: 1px solid #dddddd;
		}
	</style>
</head>
<body>
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.report.system" /></a></li>
			<li class="end"><a href="javascript:void(0)">督导教务实验课表</a></li>
		</ul>
	</div>
</div>
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab1 selected" id="s1">
			<a href="javascript:void(0)">督导教务实验课表</a>
		</li>
		<input class="btn btn-new" type="button" value="导出" onclick="exportSupervise();"/>
	</ul>
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">

			<div class="content-box">
				<%--<div class="title">--%>
					<%--<div id="title">督导教务实验课表</div>--%>
				<%--</div>--%>
				<div class="tool-box">
					<table border="0" cellpadding="5" cellspacing="0" bgcolor="#F3EFEE" height="30" width="100%">
						<tbody>
						<form name="queryForm" method="Post" action="${pageContext.request.contextPath}/report/reportSupervise?currpage=1">
							<ul>
								<li>学期：
									<select class="chzn-select" name="term" id="term">
                                        <option value="" selected>请选择</option>
										<c:forEach items="${schoolTerm}" var="item">
											<c:if test="${item.id eq term}">
												<option value="${item.id }" selected="selected">${item.termName}</option>
											</c:if>
											<c:if test="${item.id ne term}">
												<option value="${item.id }">${item.termName}</option>
											</c:if>
										</c:forEach>
									</select>
								</li>
								<li>课程名称:
									<input type="text" id="courseName" name="courseName" value="${courseName }">
								</li>
								<li>教师姓名:
									<input type="text" id="cName" name="cName" value="${cName }">
								</li>
                                <li>所属学院：
                                    <select class="chzn-select" name="academy" id="academy">
                                        <option value="-1" selected>请选择</option>
                                        <c:forEach items="${academyList}" var="item">
                                            <c:if test="${item.academyNumber eq currAcademy}">
                                                <option value="${item.academyNumber }" selected="selected">${item.academyName}</option>
                                            </c:if>
                                            <c:if test="${item.academyNumber ne currAcademy}">
                                                <option value="${item.academyNumber }">${item.academyName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
								<li>
									<input type="submit" value="查询"/>
									<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
									<%--<input type="button" value="导出" onclick="exportSupervise();"/>--%>
								</li>
							</ul>
						</form>
						</tbody>
					</table>
				</div>

				<div id="myShow">
					<table class="tb" id="my_show" style="
                    word-break:break-all; word-wrap: break-word; text-align: center; vertical-align: center; horiz-align: center">
						<thead style="center-content">
						<tr>
							<th>序号</th>
							<th>系部</th>
							<th>课程名称</th>
							<th>性质</th>
							<th>教师姓名</th>
							<th>课程学分</th>
							<th>总周次</th>
							<th>总课时</th>
							<th>人数</th>
							<th>理论课时</th>
							<th>上课时间</th>
							<th>上课地点</th>
							<th>教学班组成</th>
							<th>实验室课时</th>
							<th>实验上课时间</th>
							<th>实验上课地点</th>
							<th>实验室具体地点</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${list}" var="l" varStatus="i">
							<tr>
								<td>${pageModel.currpage*10+i.index-9}</td><%--序号--%>
								<td>${l[2]}</td><%--系部--%>
								<td>${l[3]}</td><%--课程名称--%>
								<td>${l[4]}</td><%--性质--%>
								<td>${l[5]}</td><%--教师姓名--%>
								<td>${l[16]}</td><%--课程学分--%>
								<td>${l[15]}</td><%--总周次--%>
								<td>${l[17]}</td><%--总课时--%>
								<td>${l[6]}</td><%--人数--%>
								<td>${l[18]}</td><%--理论课时--%>
								<td>${l[9]}</td><%--上课时间--%>
								<td>${l[10]}</td><%--上课地点--%>
								<td>${l[8]}</td><%--教学班组成--%>
								<td>${l[19]}</td><%--实验室课时--%>
								<td>${l[12]}</td><%--实验上课时间--%>
								<td>${l[11]}</td><%--实验上课地点--%>
								<td>${l[13]}</td><%--实验室具体地点--%>
							</tr>
						</c:forEach>

						</tbody>
					</table>
					<!-- 分页开始 -->
					<div class="page">
						${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/reportSupervise?currpage=1')" target="_self">首页</a>
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/reportSupervise?currpage=${pageModel.previousPage}')"
						   target="_self">上一页</a>
						第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
						<option value="${pageContext.request.contextPath}/report/reportSupervise?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
								   var="current">
							<c:if test="${j.index!=pageModel.currpage}">
								<option value="${pageContext.request.contextPath}/report/reportSupervise?currpage=${j.index}">${j.index}</option>
							</c:if>
						</c:forEach></select>页
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/reportSupervise?currpage=${pageModel.nextPage}')"
						   target="_self">下一页</a>
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/reportSupervise?currpage=${pageModel.lastPage}')"
						   target="_self">末页</a>
					</div>
					<!-- 分页结束 -->
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
        '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->

</body>
</html>