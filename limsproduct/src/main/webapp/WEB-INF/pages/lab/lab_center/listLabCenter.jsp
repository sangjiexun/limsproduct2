<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="decorator" content="iframe"/>
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
	<script type="text/javascript">
        //取消查询
        function cancel() {
            window.location.href="${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=1";
        }
        //跳转
        function targetUrl(url) {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
        function showLabCenterAdminDetail(id){
            var index = layer.open({
                type: 2,
                title: '管理员详细信息',
                fix: true,
                maxmin:true,
                shift:-1,
                closeBtn: 1,
                shadeClose: true,
                move:false,
                area: ['1000px', '420px'],
                content: '../labCenter/showLabCenterAdminDetail?currpage=1&labCenterId='+id,
                end: function(){
                }
            });
            layer.full(index);
        }
        function showLabCenterRoomDetail(id){
            var index = layer.open({
                type: 2,
                title: '所有实验室详情',
                fix: true,
                maxmin:true,
                shift:-1,
                closeBtn: 1,
                shadeClose: true,
                move:false,
                area: ['1000px', '420px'],
                content: '../labCenter/showLabCenterRoomDetail?currpage=1&labCenterId='+id,
                end: function(){
                }
            });
            layer.full(index);
        }
        function showLabCenterDeviceDetail(id){
            var index = layer.open({
                type: 2,
                title: '所有设备详情',
                fix: true,
                maxmin:true,
                shift:-1,
                closeBtn: 1,
                shadeClose: true,
                move:false,
                area: ['1000px', '420px'],
                content: '../labCenter/showLabCenterDeviceDetail?currpage=1&labCenterId='+id,
                end: function(){
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
			<li><a href="javascript:void(0)"><spring:message code="left.trainingRoom.infoManagement" /></a></li>
			<li class="end"><a href="javascript:void(0)"><spring:message code="left.trainingCenter.management"/></a></li>
		</ul>
	</div>
</div>

<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
			<li class="TabbedPanelsTab1 selected" id="s1">
				<a href="javascript:void(0)"><spring:message code="all.training.name" />中心列表</a>
			</li>
			<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '5'}">
				<a class="btn btn-new" href="${pageContext.request.contextPath}/labCenter/newLabCenter?page=${page}">新建</a>
			</c:if>
		</ul>
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<%--<div class="title">--%>
						<%--<div id="title"><spring:message code="all.training.name" />中心列表</div>--%>
						<%--<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '5'}">--%>
							<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/labCenter/newLabCenter?page=${page}">新建</a>--%>
						<%--</c:if>--%>
					<%--</div>--%>

					<div class="tool-box">
						<form:form name="queryForm" action="${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=1" method="post" modelAttribute="labCenter">
							<ul>
								<li><spring:message code="all.training.name" />中心名称:<form:input id="lab_name" path="centerName" type="text"/></li>
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
							<th>中心编号</th>
							<th>中心名称</th>
							<th>中心主任</th>
							<th>所属校区</th>
							<th>所属学院</th>
							<th>中心面积</th>
							<th>管理员数目</th>
							<th><spring:message code="all.training.name" />室数目</th>
							<th>总设备数</th>
							<th>总设备价值</th>
							<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
								<th>操作</th>
							</c:if>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${labCenterVOS}" var="curr">
							<tr>
								<td>${curr.centerNo}</td>
								<td>${curr.centerName}</td>
								<td>${curr.centerAdmin}</td>
								<td>${curr.campus}</td>
								<td>${curr.academy}</td>
								<td>${curr.area}</td>
								<td><a href="javascript:void(0" onclick="showLabCenterAdminDetail(${curr.id});">${curr.adminNum}</a></td>
								<td><a href="javascript:void(0" onclick="showLabCenterRoomDetail(${curr.id});">${curr.labNum}</a></td>
								<td><a href="javascript:void(0" onclick="showLabCenterDeviceDetail(${curr.id});">${curr.deviceNum}</a></td>
								<td>${curr.devicePrice}</td>
								<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
									<td>
										<a href="${pageContext.request.contextPath}/labCenter/editLabCenter?labCenterId=${curr.id}&page=${page}">编辑</a>
										<a href="${pageContext.request.contextPath}/labCenter/deleteLabCenter?labCenterId=${curr.id}&page=${page}" onclick="return confirm('若此实训中心与其他模块相关联则无法删除！确定删除？');">删除</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<div class="page" >
						${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=1')" target="_self">首页</a>
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
						第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
						<option value="${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
							<c:if test="${j.index!=pageModel.currpage}">
								<option value="${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${j.index}">${j.index}</option>
							</c:if>
						</c:forEach></select>页
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.lastPage}')" target="_self">末页</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
