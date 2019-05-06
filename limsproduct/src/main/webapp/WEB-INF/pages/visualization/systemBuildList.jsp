<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>

<html>
<head>
	<meta name="decorator" content="iframe"/>

	<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
	<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

	<script type="text/javascript">

        //首页
        function first(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

        //末页
        function last(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

        //上一页
        function previous(url) {
            var page =${page};
            if (page == 1) {
                page = 1;
            } else {
                page = page - 1;
            }
            //alert("上一页的路径："+url+page);
            document.queryForm.action = url + page;
            document.queryForm.submit();
        }

        //下一页
        function next(url) {
            var totalPage =${pageModel.totalPage};
            var page =${page};
            if (page >= totalPage) {
                page = totalPage;
            } else {
                page = page + 1
            }
            //alert("下一页的路径："+page);
            document.queryForm.action = url + page;
            document.queryForm.submit();
        }

        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/visualization/systemBuildList?page=1";
        }
	</script>
</head>


<body>

<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.visualization.management"/></a></li>
			<li class="end"><a href="javascript:void(0)">楼层图片设置</a></li>
		</ul>
	</div>
</div>

<!-- 查询表单 -->
<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<div class="title">
						<div id="title">楼层图片设置</div>
					</div>
					<div class="tool-box">
						<form:form name="queryForm"
								   action="${pageContext.request.contextPath}/visualization/systemBuildList?page=1"
								   method="post" modelAttribute="systemBuild">
							<ul>

								<li>楼宇名称:<form:input id="buildName" path="buildName"/></li>

								<li>所属校区:
									<form:select id="campusNumber" path="systemCampus.campusNumber" class="chzn-select">
										<form:option value="">请选择</form:option>
										<c:forEach items="${listSystemCampus}" var="lsc">
											<form:option value="${lsc.campusNumber}">${lsc.campusName}</form:option>
										</c:forEach>
									</form:select>
								</li>

								<li>所属学院:
									<form:select id="academyNumber" path="schoolAcademy.academyNumber" class="chzn-select">
										<form:option value="">请选择</form:option>
										<c:forEach items="${listSchoolAcademy}" var="lsa">
											<form:option value="${lsa.academyNumber}">${lsa.academyName}</form:option>
										</c:forEach>
									</form:select>
								</li>

								<li><input type="submit" value="查询"/>
									<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li>
							</ul>
						</form:form>
					</div>
					<div class="content-box">
						<table class="tb" id="my_show">
							<thead>
							<tr>
								<th>
									<center>序号</center>
								</th>
								<th>
									<center>楼宇名称</center>
								</th>
								<th>
									<center>楼宇编号</center>
								</th>
								<th>
									<center>所属校区</center>
								</th>
								<th>
									<center>所属学院</center>
								</th>
								<th>
									<center>拥有楼层数</center>
								</th>
								<th>
									<center>操作</center>
								</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${builds}" var="current" varStatus="i">
								<tr>
									<td>
										<center>${i.count}</center>
									</td>
									<td>
										<center>
												${current.buildName}
										</center>
									</td>
									<td>
										<center>${current.buildNumber}</center>
									</td>
									<td>
										<center>${current.systemCampus.campusName}<!-- 校区 --></center>
									</td>
									<td>
										<center>${current.schoolAcademy.academyName}<!-- 学院 --></center>
									</td>
									<td>
										<center>${current.floorNum}<!-- 楼层数 --></center>
									</td>
									<td>
										<center>
                                            <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR' || sessionScope.selected_role eq 'ROLE_ACADEMYLEVELM'}">
												<a href="${pageContext.request.contextPath}/visualization/getSystemFloorPic?buildNumber=${current.buildNumber}">添加楼层图片</a>&nbsp;&nbsp;
                                            </c:if>
										</center>
									</td>
								</tr>
							</c:forEach>
							</tbody>

						</table>
						<div class="page">
							${totalRecords}条记录,共${pageModel.totalPage}页
							<a href="javascript:void(0)"
							   onclick="first('${pageContext.request.contextPath}/visualization/systemBuildList?page=1')"
							   target="_self">首页</a>
							<a href="javascript:void(0)"
							   onclick="previous('${pageContext.request.contextPath}/visualization/systemBuildList?page=')"
							   target="_self">上一页</a>
							第<select onchange="first(this.options[this.selectedIndex].value)">
							<option value="${pageContext.request.contextPath}/visualization/systemBuildList?page=${page}">${page}</option>
							<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
									   var="current">
								<c:if test="${j.index!=page}">
									<option value="${pageContext.request.contextPath}/visualization/systemBuildList?page=${j.index}">${j.index}</option>
								</c:if>
							</c:forEach></select>页
							<a href="javascript:void(0)"
							   onclick="next('${pageContext.request.contextPath}/visualization/systemBuildList?page=')"
							   target="_self">下一页</a>
							<a href="javascript:void(0)"
							   onclick="last('${pageContext.request.contextPath}/visualization/systemBuildList?page=${pageModel.totalPage}')"
							   target="_self">末页</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>
