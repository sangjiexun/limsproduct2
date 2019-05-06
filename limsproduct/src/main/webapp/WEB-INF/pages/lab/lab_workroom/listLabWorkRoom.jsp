<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="decorator" content="iframe"/>
	<meta name="contextPath" content="${pageContext.request.contextPath}"/>
	<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
	<link type="text/css" rel="stylesheet"
		  href="${pageContext.request.contextPath}/bootstrap/css/plugins/bootstrap-table/bootstrap-table.min.css"/>
	<!-- 全局的引用 -->
	<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- bootstrap的引用 -->
	<link href="${pageContext.request.contextPath}/bootstrap/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" >
	<script src="${pageContext.request.contextPath}/bootstrap/bootstrap-select/js/bootstrap-select.js"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"
			type="text/javascript"></script>
	<link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/js/LabWorlRoom.js"
			type="text/javascript"></script>
	<style>
		.fixed-table-container thead th .sortable{
			background-image:url('${pageContext.request.contextPath}/images/sort.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:30px
		}
		.fixed-table-container thead th .asc{
			background-image:url('${pageContext.request.contextPath}/images/sort_asc.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:10px
		}
		.fixed-table-container thead th .desc{
			background-image:url('${pageContext.request.contextPath}/images/sort_dsc.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:10px
		}
	</style>
	<script type="text/javascript">
        //取消查询
        function cancel()
        {
            window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=9&type=2";
        }
        //跳转
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
        //设置
        function openSetupLink(labRoomId,currpage,type){//将labRoomId page传递到后台
			var index = layer.open({
				type: 2,
				title: '设置',
				maxmin: true,
				shadeClose: true,
				content: "${pageContext.request.contextPath}/device/editLabRoomSettingRest/"+labRoomId+"/"+currpage+"/"+type,
			});
			layer.full(index);
        }
	</script>
	<script type="text/javascript">
		function getWorkRoom(id, currpage, type){
			var index = layer.open({
				type: 2,
				title: '查看',
				maxmin: true,
				shadeClose: true,
				content: "${pageContext.request.contextPath}/labRoom/getLabRoom?id=" + id + "&currpage=" + currpage + "&type= " + type
			});
			layer.full(index);
		}

		function editWorkRoom(id, page, type) {
			var index = layer.open({
				type: 2,
				title: '编辑',
				maxmin: true,
				shadeClose: true,
				content: "${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId=" + id + "&type=" + type + "&page=" + page,
				end:function(){
					document.queryForm.action="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=" + page + "&orderBy=${orderBy}&type=" + type;
					document.queryForm.submit();
				}
			});
			layer.full(index);
		}
	</script>
</head>

<body>
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management"/></a></li>
			<li class="end"><a href="javascript:void(0)"><spring:message code="left.studio.management"/></a></li>
		</ul>
	</div>
</div>

<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
			<li class="TabbedPanelsTab1 selected" id="s1">
				<a href="javascript:void(0)">工作室列表</a>
			</li>
			<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
				<a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId=0&type=2&page=${page}">新建</a>
			</c:if>
		</ul>
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<%--<div class="title">--%>
						<%--<div id="title">工作室列表</div>--%>
						<%--<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">--%>
							<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId=0&type=2&page=${page}">新建</a>--%>
						<%--</c:if>--%>
					<%--</div>--%>
					<div class="tool-box">
						<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=${orderBy }&type=2" method="post" modelAttribute="labRoom">
							<ul>
								<li>工作室名称:<form:input path="labRoomName"/></li>
								<li>
									<input type="submit" value="查询"/>
									<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
								</li>
							</ul>
						</form:form>
					</div>
					<table class="tb" id="my_show">
						<thead>
						<tr>
							<th>工作室编号</th>
							<th>工作室名称</th>
							<th>工作室地点</th>
							<th>所属中心</th>
							<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5' || sessionScope.auth_level eq '6'}">
								<th>操作</th>
							</c:if>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${listLabWorkRoom}" var="curr">
							<tr>
								<td>${curr.labRoomNumber}</td>
								<td>${curr.labRoomName}</td>
								<td>${curr.labRoomAddress}</td>
								<td>${curr.labCenter.centerName}</td>
								<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5' || sessionScope.auth_level eq '6'}">
									<td>
										<a  href="javascript:void(0)" onclick="openSetupLink('${curr.id}','${pageModel.currpage}','${type}')">设置</a>
										<a href="javascript:void(0)" onclick="getWorkRoom('${curr.id}','${pageModel.currpage}','${type}')">查看</a>
										<a href="javascript:void(0)" onclick="editWorkRoom('${curr.id}','${pageModel.currpage}','${type}')">编辑</a>
										<a href="${pageContext.request.contextPath}/labRoom/deleteLabRoom?labRoomId=${curr.id}&type=2&page=${page}" onclick="return confirm('确定删除？');">删除</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<div class="page" >
						${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=${orderBy }&type=2')" target="_self">首页</a>
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.previousPage}&orderBy=${orderBy }&type=2')" target="_self">上一页</a>
						第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
						<option value="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.currpage}&orderBy=${orderBy }&type=2">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
							<c:if test="${j.index!=pageModel.currpage}">
								<option value="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${j.index}&orderBy=${orderBy }&type=2">${j.index}</option>
							</c:if>
						</c:forEach></select>页
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.nextPage}&orderBy=${orderBy }&type=2')" target="_self">下一页</a>
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.lastPage}&orderBy=${orderBy }&type=2')" target="_self">末页</a>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
