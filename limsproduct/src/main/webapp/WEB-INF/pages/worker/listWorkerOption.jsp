<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<html>
<head>
  <meta name="decorator" content="iframe"/>
  <script type="text/javascript">
function cancle(){
	window.location.href="${pageContext.request.contextPath}/worker/listWorkerOption?currpage=1";
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
<li><a href="">系统管理</a></li>
<li class="end"><a href="">系统设置</a></li>
</ul>
</div>
</div>

  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">我的课程</a>
		  </li>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">	
  <div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/worker/listWorkerOption?currpage=1" method="post" modelAttribute="workerOption">
			<ul>
			    <li>教师名称</li>
  				<li>
  				  <form:input path="user.cname" id="user.cname"/>
  				</li>
  				<li>可预约工位数</li>
  				<li>
  				  <form:input path="worker" id="worker"/>
  				</li>
  				<li><input type="submit" value="查询"/></li> 
  				<li><input type="button" onclick="cancle();" value="取消查询"/></li> 
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
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab1 selected" id="s1">
			<a href="javascript:void(0)">可预约工位数设置</a>
		</li>
		<a class="btn btn-new" href="${pageContext.request.contextPath}/worker/newWorkerOption">新建</a>
	</ul>
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<%--<div class="title">--%>
					<%--<div class="title">可预约工位数设置--%>
					<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/worker/newWorkerOption">新建</a>--%>
					<%--</div>--%>
				<%--</div>--%>
				<table>
					<thead>
						<tr>
							<th>教师名称</th>
							<th>可预约工位数</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${workerOptions}" var="curr">
							<tr>
								<td>${curr.user.cname}</td>
								<td>${curr.worker}</td>
								<td>
								    <a title="修改" class="btn btn-common" href="${pageContext.request.contextPath}/worker/editWorkerOption?id=${curr.id}" >修改</a>
									<a title="删除" class="btn btn-common" href="${pageContext.request.contextPath}/worker/deleteWorkerOption?id=${curr.id}" onclick="return confirm('是否确定删除？');" >删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="page">
					${totalRecords}条记录 &nbsp;   共${pageModel.totalPage}页 &nbsp; 
					<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/worker/listWorkerOption?currpage=1')" target="_self">首页</a> 
					<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/worker/listWorkerOption?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
					<input type="hidden" value="${pageModel.lastPage}" id="totalpage" />&nbsp; <input type="hidden" value="${currpage}" id="currpage" /> 
					&nbsp;第 
					<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
					   <option value="${pageContext.request.contextPath}/worker/listWorkerOption?currpage=${currpage}">${currpage}</option>
					   <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
			           <c:if test="${j.index!=currpage}">
			           <option value="${pageContext.request.contextPath}/worker/listWorkerOption?currpage=${j.index}">${j.index}</option>
			           </c:if>
			           </c:forEach>
           			</select>页&nbsp;
					 
					<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/worker/listWorkerOption?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
					<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/worker/listWorkerOption?currpage=${pageModel.lastPage}')" target="_self">末页</a>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
<head>
<meta name="decorator" content="iframe" />
</head>