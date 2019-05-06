<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<fmt:setBundle basename="bundles.projecttermination-resources" />

<html>
<head>
<meta name="decorator" content="iframe" />
<title><fmt:message key="html.title" />
</title>
<!--分页js  -->
 <script type="text/javascript"
	src="${pageContext.request.contextPath}/js/operation/operationoutline.js"></script>
<!--分页js  -->

<!-- 打印插件的引用 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<script type="text/javascript">
//首页
function first(){
	document.form.action="listTeamManagement?currpage=1";
    document.form.submit();
}

function previous(){
	var page=$("#currpage").val();
	if(page==1){
		page=1;
	}else{
		page=page-1;
	}
    document.form.action="listTeamManagement?currpage="+page;
    document.form.submit();
}

function next(){
	var totalpage=$("#totalpage").val();
	var page=parseInt($("#currpage").val());
	if(page==totalpage){
		page=totalpage;
	}else{
		page=page+1;
	}
    document.form.action="listTeamManagement?currpage="+page;
    document.form.submit();
}

function last(){
	var page=$("#totalpage").val();
	var tage=parseInt($("#tage").val());
    document.form.action="listTeamManagement?currpage="+page;
    document.form.submit();
}
	$(document).ready(function(){
	$("#print").click(function(){
	$("#ssssss").jqprint();
	})
	});
	

</script>
<!-- 打印插件的引用 -->

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css"
	rel="stylesheet">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/images/favicon.gif" />
</head>

<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)">课程教师团队管理</a></li>
				<li class="end"><a href="javascript:void(0)">课程教师团队管理</a></li>
			</ul>
		</div>
	</div>

	<!-- 结项申报列表 -->
	<!-- <div class="tab"> -->
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="content-box">
						<div class="title">
							<div id="title">课程教师团队管理</div>
							<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING,ROLE_TEACHER"><a class="btn btn-new"
								href="${pageContext.request.contextPath}/operation/newteammanagement">新建</a>
							</sec:authorize>
						</div>
						<div class="content-box">
							<table  id="ssssss">
								<thead>
									<tr align="left">
										<th class="thead" width="3%">序号</th>
										<th class="thead" width="15%">课程</th>
										<th class="thead" width="15%">教师</th>
										<th class="thead" width="15%">教学秘书</th>
                                        <th class="thead" width="15%">操作</th>
									</tr>
								</thead>
								<tbody>
 
									
									<c:forEach items="${teamManagement}" varStatus="i" var="t">
									
										<tr align="left" ">
											<td>${i.index+1 }</td>
											<td>${t.schoolCourse.courseName}</td>
			                                <td>${t.userByTeacher.cname }</td>
			                                <td>${t.userByExperimentalteaching.cname}</td>
											<td>
											<sec:authorize ifAnyGranted="ROLE_EXPERIMENTALTEACHING,ROLE_SUPERADMIN">
											<a href="${pageContext.request.contextPath}/operation/editteammanagement?idkey=${t.id}">编辑</a> 
											<a href="${pageContext.request.contextPath}/operation/delectteammanagement?idkey=${t.id}" onclick="return confirm('您确认删除吗？')">删除</a>
											</sec:authorize>
											
											<sec:authorize ifAnyGranted="ROLE_TEACHER" ifNotGranted="ROLE_EXPERIMENTALTEACHING,ROLE_SUPERADMIN">
											<a href="${pageContext.request.contextPath}/operation/editteammanagement?idkey=${t.id}">编辑</a> 
											<a href="${pageContext.request.contextPath}/operation/delectteammanagement?idkey=${t.id}" onclick="return confirm('您确认删除吗？')">删除</a>
											</sec:authorize>
											<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_TEACHER">
											
											<!-- <a href="javascript:void(0)"  >下载</a> -->
											</sec:authorize>
											--%></td>
										</tr>
									</c:forEach>
								</tbody>
								<!-- 分页导航 -->

							</table>
							<div class="page">${totalRecords}条记录 &nbsp;
								共${pageModel.totalPage}页 <input type="hidden"
									value="${pageModel.lastPage}" id="totalpage" />&nbsp;
									<input
									type="hidden" value="${currpage}" id="currpage" /> <a href="javascript:void(0)"
									onclick="first();" target="_self">首页</a> <a href="javascript:void(0)"
									onclick="previous();" target="_self">上一页</a> <a href="javascript:void(0)"
									<!-- 跳转到选择/输入的页面 -->
		   第 <select class="chzn-select" onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
		   <option value="${pageContext.request.contextPath}/operation/listTeamManagement?currpage=${currpage}">${currpage}</option>
		   <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
           <c:if test="${j.index!=currpage}">
           <option value="${pageContext.request.contextPath}/operation/listTeamManagement?currpage=${j.index}">${j.index}</option>
           </c:if>
           </c:forEach>
           </select> 页
									
									 <input
									type="hidden" value="${currpage}" id="currpage" /> <a href="javascript:void(0)"
									onclick="next();" target="_self">下一页</a> <a href="javascript:void(0)"
									onclick="last();" target="_self">末页</a>
								</td>
							</div>
						</div>

					</div>
				</div>

			</div>
		</div>
	</div>
</body>
<!-------------列表结束----------->
</html>