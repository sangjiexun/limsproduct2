<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <script type="text/javascript">
  	//跳转
	  function targetUrl(url)
	  {
	    document.queryForm.action=url;
	    document.queryForm.submit();
	  }
  </script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">评教管理</a></li>
		<li class="end"><a href="javascript:void(0)">评教结果列表</a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">评教结果列表</a>
		  </li>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">评教结果列表</div>--%>
	<%--</div>--%>
	
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/evaluation/listEvaluationResult?currpage=1" method="post" modelAttribute="evaluationResult">
			<ul>
			 	<li>学期:
					<form:select path="termId" class="chzn-select">
						<form:option value="">--请选择--</form:option>
						<c:forEach items="${schoolTerms}" var="curr">
							<c:if test="${curr.id eq termId}">
								<form:option value="${curr.id}" selected="selected">${curr.termName}</form:option>
							</c:if>
							<c:if test="${curr.id ne termId}">
								<form:option value="${curr.id}">${curr.termName}</form:option>
							</c:if>
						</c:forEach>
					</form:select>
				</li>
  				<li>
					<input type="submit" value="查询"/>
			    	<input class="cancel-submit" type="button" value="取消" onclick="window.history.go(0)"/>
			    </li>
  			</ul>
		</form:form>
	</div>
	
	<table>
	  <c:forEach items="${objects}" var="curr" varStatus="i">
	  <tr>
	  	<c:forEach begin="0" end="${columns}" step="1" var="col">
	  		<c:if test="${i.count eq 1}">
	  			<th>${curr[col]}</th>
	  		</c:if>
	  		<c:if test="${i.count ne 1}">
		    	<td align="center">${curr[col]}</td>
		    </c:if>
	  	</c:forEach>
	  </tr>
	  </c:forEach>
	</table>
	<!-- 分页[s] -->
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/evaluation/listEvaluationResult?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/evaluation/listEvaluationResult?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/evaluation/listEvaluationResult?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/evaluation/listEvaluationResult?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/evaluation/listEvaluationResult?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/evaluation/listEvaluationResult?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
