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
	  window.location.href="${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=1";
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
  function btnExport() {
      window.location.href="${pageContext.request.contextPath}/log/exportListExperimentalSchedule";
  }
  </script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
		  <li><a href="javascript:void(0)"><spring:message code="left.system.management" /></a></li>
		  <li class="end"><a href="javascript:void(0)">开放项目相关报表</a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1"><a href="javascript:void(0)">实验计划表</a></li>
		  <li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/log/listLabRoom?currpage=1&type=1">仪器借出登记表</a></li>
		  <li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/log/listReceiptOfLowValueConsumables?currpage=1">低值易耗品领用登记单</a></li>
		  <li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/log/listDrugCabinet?currpage=1">药品出库登记表</a></li>
		  <li class="TabbedPanelsTab" id="s5"><a href="${pageContext.request.contextPath}/log/listAsset?currpage=1">耗材领用记录单</a></li>
		  <li class="TabbedPanelsTab" id="s6"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=6">实验通知单</a></li>
		  <li class="TabbedPanelsTab" id="s7"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=7">分组实验通知、教学记录单</a></li>
		  <li class="TabbedPanelsTab" id="s8"><a href="${pageContext.request.contextPath}/log/listLabRoom?currpage=1&type=2">实验开出情况统计表</a></li>
		  <input class="btn btn-new" type="button" value="导出" onclick="btnExport();"/>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">年度使用绩效评价表</div>--%>
	<%--</div>--%>
	
	<div class="tool-box">
		<form name="queryForm" action="${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=1" method="post">
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
	    <th>序号</th>
	    <th>实验内容</th>
	    <th>实验物资</th>
	    <%--<th>仪器设备</th>--%>
	    <th>实验类型</th>
	    <th>计划时间</th>
	    <th>学期</th>
	    <th>备注</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${experimentalScheduleVOs}" var="curr" varStatus="status">
	  <tr>
	    <td>${status.index + 1}</td>
	    <td>${curr.itemName}</td>
	    <td>${curr.itemAssets}</td>
	    <%--<td>${curr.itemDecvices}</td>--%>
	    <td>${curr.itemCategory}</td>
	    <td>${curr.planTime}</td>
	    <td>${curr.termName}</td>
	    <td></td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<!-- 分页[s] -->
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=1')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
