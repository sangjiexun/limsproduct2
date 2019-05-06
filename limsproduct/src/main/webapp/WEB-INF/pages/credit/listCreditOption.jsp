<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<html>
<head>
  <meta name="decorator" content="iframe"/>
  <script type="text/javascript">
function cancle(){
	window.location.href="${pageContext.request.contextPath}/credit/listCreditOption?currpage=1";
}
</script>
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  

  
  <style>
    .t_style ul li{
      float:left;
    }
  </style>
</head>

<body>
<!-- 导航栏 -->
<div class="navigation">
<div id="navigation">
<ul>
	<li><a href="javascript:void(0)"><spring:message code="left.system.management" /></a></li>
	<li class="end"><a href="javascript:void(0)"><spring:message code="left.system.setting" /></a></li>
</ul>
</div>
</div>

  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">系统设置</a>
		  </li>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">	
  <div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/credit/listCreditOption?currpage=1" method="post" modelAttribute="creditOption">
			<ul>
  				<li>扣分项:
  				  <form:input path="name" id="name" value="${name}"/>
  				</li>
  				<li><input type="submit" value="查询"/>
					<input class="cancel-submit" type="button" onclick="cancle();" value="取消查询"/></li>
  			</ul>
		</form:form>
	</div>
  </div>
  </div>
  </div>
  </div>
  
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		    var config = {
		      '.chzn-select': {search_contains : true},
		      '.chzn-select-deselect'  : {allow_single_deselect:true},
		      '.chzn-select-no-single' : {disable_search_threshold:10},
		      '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
		      '.chzn-select-width'     : {width:"95%"}
		    }
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    }
	</script>
	<!-- 下拉框的js -->
	

<!-- 导航栏 -->
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<%--<div class="title">--%>
					<%--<div class="title">信誉机制设置--%>
					<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_PRESECTEACHING">--%>
					<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/credit/newCreditOption">新建</a>--%>
					<%--</sec:authorize></div>--%>
				<%--</div>--%>
				<table>
					<thead>
						<tr>
							<th>序号</th>
							<th>扣分项</th>
							<th>扣分值</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${creditOptions}" var="current" varStatus="i">
							<tr>
								<td>${i.count}</td>
								<td>${current.name}</td>
								<td>${current.deduction}</td>
								<td>
									<c:if test="${current.status eq 0}">
									<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_PRESECTEACHING">
							            <a title="提交" class="btn btn-common" href="${pageContext.request.contextPath}/credit/submitNewCreditOption?idKey=${current.id}" >提交</a>
										<a title="修改" class="btn btn-common" href="${pageContext.request.contextPath}/credit/editCreditOption?idKey=${current.id}" >修改</a>
										<a title="删除" class="btn btn-common" href="${pageContext.request.contextPath}/credit/deleteCreditOption?idKey=${current.id}" onclick="return confirm('是否确定删除？');" >删除</a>
									</sec:authorize>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>
<head>
<meta name="decorator" content="iframe" />
</head>