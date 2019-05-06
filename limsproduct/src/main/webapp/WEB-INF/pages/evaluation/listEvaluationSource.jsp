<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
  <script type="text/javascript">
	//跳转
	  function targetUrl(url)
	  {
	    document.queryForm.action=url;
	    document.queryForm.submit();
	  }
	  // 打开查看窗口
	  function openWindow(url) {
	  	layer.open({
	  		type: 2,
            title: '查看',
            maxmin: true,
            shadeClose: true, 
            area : ['700px' , '350px'],
            content: url
	  	})
	  }
	  // sheet栏
	  $(document).ready(function(){
	  	var s=${type};
 		if(1==s){
	 		$("#s1").addClass("TabbedPanelsTab selected");
	 		$("#s0").addClass("TabbedPanelsTab");
 		}
  		if(0==s){
 			$("#s0").addClass("TabbedPanelsTab selected");
 			$("#s1").addClass("TabbedPanelsTab");
 		}
	  })
  </script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">评教管理</a></li>
		<li class="end"><a href="javascript:void(0)">学生评教列表</a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s0"><a href="${pageContext.request.contextPath}/evaluation/listStudentEvaluation?currpage=1&type=0">未评</a></li>
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/evaluation/listStudentEvaluation?currpage=1&type=1">已评</a></li>
	</ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">学生评教</div>--%>
	<%--</div>--%>
	
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/evaluation/listStudentEvaluation?currpage=1&type=${type}" method="post" modelAttribute="evaluationSource">
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
    	<c:if test="${oks ne 0}">
	  	<tr style="color: red;">
	    	<th>本学期评教起止时间</th>
	    	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${evaluationSet.startTime.time}"/>——<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${evaluationSet.endTime.time}"/></td>
	  	</tr>
	  	<table>
		  <thead>
		  <tr>
		    <th>序号</th>
		    <th>课程</th>
		    <th>教师</th>
		    <th>操作</th>
		  </tr>
		  </thead>
		  <tbody>
		  <c:forEach items="${evaluationSources}" var="curr" varStatus="i">
		  <tr>
		    <td>${i.count}</td>
		    <td>${curr.schoolCourseInfo.courseName}</td>
		    <td>${curr.user.cname}</td>
		    <td>
		    	<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
		    	<c:if test="${contents eq 0}">尚未设置评教内容</c:if>
		    	<c:if test="${contents ne 0}">
		    		<c:if test="${type eq 0}">
		    			<c:if test="${oks eq 1}">
		    				<a href="${pageContext.request.contextPath}/evaluation/studentEvaluation?idKey=${curr.id}">评教</a>
		    			</c:if>
		    			<c:if test="${oks eq 2}">
		    				评教时间已过
		    			</c:if>
		    			<c:if test="${oks eq 3}">
		    				评教尚未开始
		    			</c:if>
		    		</c:if>
		    		<c:if test="${type eq 1}">
		    			<a onclick="openWindow('${pageContext.request.contextPath}/evaluation/viewStudentEvaluation?idKey=${curr.id}')" href="#">查看</a>
		    		</c:if>
		    	</c:if>
		    </td>
		  </tr>
		  </c:forEach>
		  </tbody>
		</table>
    	</c:if>
	    <c:if test="${oks eq 0}">
	    <tr style="color: red;">管理员尚未设置评教时间</tr>
	    </c:if>
	</table>
	
	<!-- 分页[s] -->
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/evaluation/listEvaluationContent?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/evaluation/listEvaluationContent?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/evaluation/listEvaluationContent?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/evaluation/listEvaluationContent?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/evaluation/listEvaluationContent?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/evaluation/listEvaluationContent?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
