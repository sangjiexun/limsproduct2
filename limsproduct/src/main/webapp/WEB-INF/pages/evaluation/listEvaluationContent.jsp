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
	//提交评教内容时判断总分
	function submitContent(count){
		var msg=confirm("只能提交一次，是否确认提交？");
		if(msg==true){
			document.queryForm.action = "${pageContext.request.contextPath}/evaluation/submitEvaluationContent?idKey="+${termId};
			document.queryForm.submit();
		}else{
		   	return false;
		}
	}
</script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">评教</a></li>
		<li class="end"><a href="javascript:void(0)">评教项目列表</a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">评教项目列表</a>
		  </li>
		  <c:if test="${status==0}">
			  <a class="btn btn-new" href="${pageContext.request.contextPath}/evaluation/newEvaluationContent">新建</a>
		  </c:if>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">评教项目列表</div>--%>
	  <%--<c:if test="${status==0}">--%>
	  	<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/evaluation/newEvaluationContent">新建</a>--%>
	  <%--</c:if>--%>
	<%--</div>--%>
	
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/evaluation/listEvaluationContent?currpage=1" method="post" modelAttribute="evaluationContent">
			<ul>
			 	<li>学期:
					<form:select path="schoolTerm.id" class="chzn-select">
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
		<div class="time_control">
			本学期评教起止时间:<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${evaluationSet.startTime.time}"/>—<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${evaluationSet.endTime.time}"/>
		</div>
	</div>
	
	<table>
	  <tr>
	    <c:if test="${evaluationSet ne null && evaluationSet.status ne 1}">
	    	<td>
	    		<a href="${pageContext.request.contextPath}/evaluation/editEvaluationSet?idKey=${evaluationSet.id}">修改</a>
	    		<a href="${pageContext.request.contextPath}/evaluation/submitEvaluationSet?idKey=${evaluationSet.id}" onclick="return confirm('提交后不可更改，确认提交？');">提交</a>
	    	</td>
	    </c:if>
	    <c:if test="${evaluationSet eq null}">
	    	<%-- <td><a href="${pageContext.request.contextPath}/evaluation/newEvaluationSet?idKey=${termId}">设置</a></td> --%>
	    </c:if>
	  </tr>
	</table>
	
	<table>
	  <thead>
		  <tr>
		    <th>序号</th>
		    <th>单项内容</th>
		    <th>单项分值</th>
		    <th>备注</th>
		    <th>操作</th>
		  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${evaluationContents}" var="curr" varStatus="i">
		  <tr>
		    <td>${i.count}</td>
		    <td>${curr.options}</td>
		    <td>${curr.scores}</td>
		    <td>${curr.memo}</td>
		    <td>
		    	<c:if test="${curr.status eq 0}">
		      		<a href="${pageContext.request.contextPath}/evaluation/editEvaluationContent?idKey=${curr.id}">编辑</a>
		      		<a href="${pageContext.request.contextPath}/evaluation/deleteEvaluationContent?idKey=${curr.id}" onclick="return confirm('确定删除？');">删除</a>
		      	</c:if>
		      	<c:if test="${curr.status ne 0}">
		      		本学期已设置完毕
		      	</c:if>
		    </td>
	  	  </tr>
	  </c:forEach>
		<tr>
			<c:if test="${status==0}">
       			<td colspan="5" align="center">
       				<c:if test="${totalcount eq 100}">
          				<input type="button" onclick="submitContent(${totalcount});" value="提交" style="width: 200px;height: 25px;" />
          			</c:if>
          			<c:if test="${totalcount ne 100}">
          				总计${totalcount}分(总计100方可提交)
          			</c:if>
           		</td>
           	</c:if>
         </tr>
	  </tbody>
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
