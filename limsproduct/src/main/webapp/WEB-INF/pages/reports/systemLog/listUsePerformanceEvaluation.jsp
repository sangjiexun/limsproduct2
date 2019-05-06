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
	  window.location.href="${pageContext.request.contextPath}/log/listUsePerformanceEvaluation?currpage=1";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  function btnPrintClick(){  
      window.print();  
  } 
  </script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
		  <li><a href="javascript:void(0)"><spring:message code="left.system.management" /></a></li>
		  <li class="end"><a href="javascript:void(0)"><spring:message code="left.authority.management" /></a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">年度使用绩效评价表</a>
		  </li>
		  <input class="btn btn-new" type="button" value="打印" onclick="btnPrintClick();"/>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">年度使用绩效评价表</div>--%>
	<%--</div>--%>
	
	<div class="tool-box">
		<form name="queryForm" action="${pageContext.request.contextPath}/log/listUsePerformanceEvaluation?currpage=1" method="post">
			 <ul>
  				<li><spring:message code="all.trainingRoom.labroom" />:<input type="text" id="roomName" name="roomName" value="${roomName}"/></li>
  				<li>
					<input type="submit" value="查询"/>
					<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
  				  	<%--<input type="button" value="打印" onclick="btnPrintClick();"/>--%>
			      </li>
  				</ul>
			 
		<form>
	</div>
	
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th><spring:message code="all.trainingRoom.labroom" /></th>
	    <th>实验室类别</th>
	    <th>建设系</th>
	    <th>建设年份</th>
	    <th>面积</th>
	    <th>工位数</th>
	    <th>累计投资金额</th>
	    <th>操作</th>
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
	    <td><a href="${pageContext.request.contextPath}/LabRoomUseTable?termId=15&roomId=${curr[35] }">查看报表</a></td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<!-- 分页[s] -->
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listUsePerformanceEvaluation?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listUsePerformanceEvaluation?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/log/listUsePerformanceEvaluation?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/log/listUsePerformanceEvaluation?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listUsePerformanceEvaluation?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listUsePerformanceEvaluation?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
