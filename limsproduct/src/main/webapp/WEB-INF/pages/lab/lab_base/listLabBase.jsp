<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="decorator" content="iframe"/>

	<script type="text/javascript">
        //取消查询
        function cancel()
        {
            window.location.href="${pageContext.request.contextPath}/labBase/listLabBase?currpage=1";
        }
        //跳转
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
        function showLabBaseRoomDetail(id){
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
                content: '../labBase/showLabBaseRoomDetail?currpage=1&labBaseId='+id,
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
			<li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management"/></a></li>
			<li class="end"><a href="javascript:void(0)"><spring:message code="left.base.management"/></a></li>
		</ul>
	</div>
</div>

<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
			<li class="TabbedPanelsTab1 selected" id="s1">
				<a href="javascript:void(0)">基地列表</a>
			</li>
			<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
				<a class="btn btn-new" href="${pageContext.request.contextPath}/labBase/newLabBase?page=${page}">新建</a>
			</c:if>
		</ul>
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<%--<div class="title">--%>
						<%--<div id="title">基地列表</div>--%>
						<%--<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">--%>
							<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/labBase/newLabBase?page=${page}">新建</a>--%>
						<%--</c:if>--%>
					<%--</div>--%>

					<div class="tool-box">
						<form:form name="queryForm" action="${pageContext.request.contextPath}/labBase/listLabBase?currpage=1" method="post" modelAttribute="labAnnex">
							<ul>
								<li><spring:message code="all.training.name"/>基地名称:<form:input path="labName"/></li>
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
							<th>基地编号</th>
							<th>基地名称</th>
							<th>基地面积</th>
							<th><spring:message code="all.training.name" />室数目</th>
							<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
								<th>操作</th>
							</c:if>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${listLabAnnex}" var="curr">
							<tr>
								<td>${curr.labNumber}</td>
								<td>${curr.labName}</td>
								<td>${curr.labCapacity}</td>
								<td><a href="javascript:void(0)" onclick="showLabBaseRoomDetail(${curr.id});">${fn:length(curr.labBases)}</a></td>
								<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
									<td>
										<a href="${pageContext.request.contextPath}/labBase/editLabBase?labBaseId=${curr.id}&page=${page}">编辑</a>
										<c:if test="${fn:length(curr.labBases)==0}">
											<a href="${pageContext.request.contextPath}/labBase/deleteLabBase?labBaseId=${curr.id}&page=${page}" onclick="return confirm('确定删除？');">删除</a>
										</c:if>
									</td>
								</c:if>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<div class="page" >
						${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labBase/listLabBase?currpage=1')" target="_self">首页</a>
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labBase/listLabBase?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
						第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
						<option value="${pageContext.request.contextPath}/labBase/listLabBase?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
							<c:if test="${j.index!=pageModel.currpage}">
								<option value="${pageContext.request.contextPath}/labBase/listLabBase?currpage=${j.index}">${j.index}</option>
							</c:if>
						</c:forEach></select>页
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labBase/listLabBase?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labBase/listLabBase?currpage=${pageModel.lastPage}')" target="_self">末页</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
