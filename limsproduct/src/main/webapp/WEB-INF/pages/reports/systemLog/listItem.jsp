<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
	<link rel="stylesheet" type="text/css" media="screen,print" href="css/print.css" />
  <script type="text/javascript">
  function cancel()
  {
	  window.location.href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=${type}";
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
		  <li class="end"><a href="javascript:void(0)">药品出库登记表</a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1" id="s1"><a href="${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=1">实验计划表</a></li>
		  <%--<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/log/listLabRoom?currpage=1&type=1">仪器借出登记表</a></li>--%>
		  <li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/log/listReceiptOfLowValueConsumables?currpage=1">低值易耗品领用登记单</a></li>
		  <li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/log/listDrugCabinet?currpage=1">药品出库登记表</a></li>
		  <li class="TabbedPanelsTab" id="s5"><a href="${pageContext.request.contextPath}/log/listAsset?currpage=1">化学品领用记录单</a></li>
		  <c:if test="${type == 6}">
			  <li class="TabbedPanelsTab selected" id="s6"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=6">实验通知单</a></li>
			  <li class="TabbedPanelsTab" id="s7"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=7">分组实验通知、教学记录单</a></li>
		  </c:if>
		  <c:if test="${type == 7}">
			  <li class="TabbedPanelsTab" id="s6"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=6">实验通知单</a></li>
			  <li class="TabbedPanelsTab selected" id="s7"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=7">分组实验通知、教学记录单</a></li>
		  </c:if>
		  <li class="TabbedPanelsTab" id="s8"><a href="${pageContext.request.contextPath}/log/listLabRoom?currpage=1&type=2">实验开出情况统计表</a></li>
		  <%--<input class="btn btn-new" type="button" value="打印" onclick="btnPrintClick();"/>--%>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">年度使用绩效评价表</div>--%>
	<%--</div>--%>
	
	<div class="tool-box">
		<form name="queryForm" action="${pageContext.request.contextPath}/log/listItem?currpage=1&type=${type}" method="post">
			 <ul>
					 <li>学期:
						 <select id="term" name="term" class="chzn-select" style="width:160px;">
							 <option value="">请选择</option>
							 <c:forEach items="${schoolTermList}" var="curr">
								 <c:if test="${curr.id eq selectedTermId}">
									 <option value="${curr.id }" selected="selected">${curr.termName}</option>
								 </c:if>
								 <c:if test="${curr.id ne selectedTermId}">
									 <option value="${curr.id }">${curr.termName }</option>
								 </c:if>
							 </c:forEach>
						 </select>
					 </li>
					 <li>实验内容:
						 <input type="text" id="lpname" name="lpname" value="${selectedLpname}"/>
					 </li>
				 	<li>所属课程:
						 <input type="text" id="lpcourse" name="lpcourse" value="${selectedLpcourse}"/>
					 </li>
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
		<th>实验内容</th>
	    <th>实验类型</th>
	    <th>计划时间</th>
	    <th>学期</th>
	    <th>所属课程</th>
		<th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${operationItemList}" var="curr" varStatus="status">
		  <tr>
			<td>${curr.lpName}</td>
			<td>${curr.getCDictionaryByLpCategoryApp().getCName()}</td>
			<td>${curr.getPlanWeek()}</td>
			<td>${curr.getSchoolTerm().getTermName()}</td>
            <td>${curr.getSchoolCourseInfo().getCourseName()}</td>
			<%--<td><a href="${pageContext.request.contextPath}/log/listItemClasses?currpage=1&itemId=${curr.id}">查看实验课次</a></td>--%>
			<c:if test="${type == 6}">
				<td><a href="${pageContext.request.contextPath}/log/listItemClasses?currpage=1&itemId=${curr.id}">查看实验课次</a></td>
			</c:if>
			<c:if test="${type == 7}">
				<td><a href="${pageContext.request.contextPath}/log/listTeachingRecordSheet?currpage=1&itemId=${curr.id}">查看教学记录单</a></td>
			</c:if>
		  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<!-- 分页[s] -->
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listItem?currpage=1&type=${type}')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listItem?currpage=${pageModel.previousPage}&type=${type}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/log/listItem?currpage=${pageModel.currpage}&type=${type}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/log/listItem?currpage=${j.index}&type=${type}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listItem?currpage=${pageModel.nextPage}&type=${type}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listItem?currpage=${pageModel.lastPage}&type=${type}')" target="_self">末页</a>
    </div>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
