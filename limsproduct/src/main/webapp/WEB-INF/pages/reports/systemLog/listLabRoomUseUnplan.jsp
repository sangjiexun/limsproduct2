<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="decorator" content="iframe"/>

	<script type="text/javascript">
		function cancel()
		{
			window.location.href="${pageContext.request.contextPath}/log/listLabRoomUseUnplan?currpage=1&type=${type}";
		}
		//跳转
		function targetUrl(url)
		{
			document.queryForm.action=url;
			document.queryForm.submit();
		}
		function btnPrintClick(){
			$("#my_show").jqprint();
		}
	</script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
		  <c:if test="${type eq 1}">
			  <li class="end"><a href="javascript:void(0)"><spring:message code="left.inPlan.statistics" /></a></li>
		  </c:if>
		  <c:if test="${type eq 2}">
			  <li class="end"><a href="javascript:void(0)"><spring:message code="left.outPlan.statistics" /></a></li>
		  </c:if>
		  <c:if test="${type eq 0}">
			  <li class="end"><a href="javascript:void(0)"><spring:message code="left.trainingStatistics.list" /></a></li>
		  </c:if>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
	  <div class="tool-box">
		  <form name="queryForm" action="${pageContext.request.contextPath}/log/listLabRoomUseUnplan?currpage=1&type=${type}" method="post">
			  <ul>
				  <li>学期:
					  <select class="chzn-select" id="termId" name="termId">
						  <option value="-1">请选择</option>
						  <c:forEach items="${schoolTerms}" var="current">
							  <c:if test="${current.id == term}">
								  <option value ="${current.id}" selected>${current.termName} </option>
							  </c:if>
							  <c:if test="${current.id != term}">
								  <option value ="${current.id}" >${current.termName} </option>
							  </c:if>
						  </c:forEach>
					  </select>
				  </li>
				  <li>综合查询:
					  <input type="text" id="roomName" name="roomName" value="${roomName}"/>
				  </li>
				  <li>按中心统计:
					  <select class="chzn-select" id="center_id" name="center_id">
						  <option value="0">请选择</option>
						  <c:forEach items="${centers}" var="current">
							  <c:if test="${current.id == center_id}">
								  <option value ="${current.id}" selected>${current.centerName} </option>
							  </c:if>
							  <c:if test="${current.id != center_id}">
								  <option value ="${current.id}" >${current.centerName} </option>
							  </c:if>
						  </c:forEach>
					  </select>
				  </li>
				  <li>按基地统计:
					  <select class="chzn-select" id="base_id" name="base_id">
						  <option value="0">请选择</option>
						  <c:forEach items="${bases}" var="current">
							  <c:if test="${current.id == base_id}">
								  <option value ="${current.id}" selected>${current.labName} </option>
							  </c:if>
							  <c:if test="${current.id != base_id}">
								  <option value ="${current.id}" >${current.labName} </option>
							  </c:if>
						  </c:forEach>
					  </select>
				  </li>
				  <input type="submit" value="查询"/>
				  <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
				  <input class="btn btn-new" type="button" value="打印" onclick="btnPrintClick();"/>
				  </li>
			  </ul>
		  </form>
	  </div>

	  <table class="tb" id="my_show">
		  <thead>
		  <tr>
			  <th><spring:message code="all.trainingRoom.labroom" />名称</th>
			  <th>管理员</th>
			  <th>学期</th>
			  <th>人次数</th>
			  <th>课次数</th>
			  <th>课时数</th>
			  <th>人时数</th>
<%--			  <th>操作</th>--%>
		  </tr>
		  </thead>
		  <tbody>
		  <c:forEach items="${details}" var="curr">
			  <tr>
				  <td>${curr[0]}</td>
				  <td>${curr[1]}</td>
				  <td>${curr[2]}</td>
				  <td>${curr[3]}</td>
				  <td>${curr[4]}</td>
				  <td>${curr[5]}</td>
				  <td>${curr[6]}</td>
<%--				  <td>查看</td>--%>
			  </tr>
		  </c:forEach>
		  </tbody>
	  </table>
	<!-- 分页[s] -->
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listLabRoomUseUnplan?currpage=1&type=${type}')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listLabRoomUseUnplan?currpage=${pageModel.previousPage}&type=${type}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/log/listLabRoomUsePlan?currpage=${pageModel.currpage}&type=${type}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/log/listLabRoomUseUnplan?currpage=${j.index}&type=${type}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listLabRoomUseUnplan?currpage=${pageModel.nextPage}&type=${type}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listLabRoomUseUnplan?currpage=${pageModel.lastPage}&type=${type}')" target="_self">末页</a>
    </div>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
