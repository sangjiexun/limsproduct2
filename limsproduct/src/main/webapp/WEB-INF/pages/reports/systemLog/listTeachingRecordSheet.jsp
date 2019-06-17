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
	  window.location.href="${pageContext.request.contextPath}/log/listItemClasses?currpage=1";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  function btnPrintClick(){  
//      window.print();
      var bdhtml = window.document.body.innerHTML;
      var sprnstr = "<!--startprint-->";
      var eprnstr = "<!--endprint-->";
      var prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr) + 17);
      prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));
      window.document.body.innerHTML = prnhtml;
      window.print();
	  // 还原界面
      window.document.body.innerHTML = bdhtml
      window.location.reload()

  }
  function goBack() {
      window.location.href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=7";
  }
  </script>
	<style type="text/css">
		.content-box .tab_lab_1 {
			margin: 10px 0 0;
		}
		.tab_lab_1 th {
			text-align: right;
			height: 40px;
			background: #fafafa;
			padding: 0 15px 0 0;
		}
		.tab_lab_1 th, .tab_lab_1 td {
			border: 1px solid #e4e5e7!important;
		}
		.tab_lab_1 td {
			text-align: left;
			vertical-align: text-top;
			background: #fff!important;
			padding: 10px;
		}
		.tab_lab_1_tr th{
			text-align: center;
			height: 40px;
		}
		.tab_lab_1_tr td {
			text-align: center;
			vertical-align: text-top;
			background: #fff!important;
			padding: 10px;
		}
		.tab_lab_2_tr th{
			height: 40px;
		}
		.title_p{
			text-align: center;
			font-size: 16px;
		}
	</style>
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
		  <li class="TabbedPanelsTab" id="s6"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=6">实验通知单</a></li>
		  <li class="TabbedPanelsTab selected" id="s7"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=7">分组实验通知、教学记录单</a></li>
		  <li class="TabbedPanelsTab" id="s8"><a href="${pageContext.request.contextPath}/log/listLabRoom?currpage=1&type=2">实验开出情况统计表</a></li>
		  <input class="btn btn-new" type="button" value="打印" onclick="btnPrintClick();"/>
		  <input class="btn btn-new" type="button" value="返回" onclick="goBack();"/>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">年度使用绩效评价表</div>--%>
	<%--</div>--%>
	
	<div class="tool-box" style="display: none">
		<form name="queryForm" action="${pageContext.request.contextPath}/log/listItemClasses?currpage=1" method="post">
			 <ul>
  				<%--<li><spring:message code="all.trainingRoom.labroom" />:<input type="text" id="roomName" name="roomName" value="${roomName}"/></li>--%>
  				<%--<li>--%>
					<%--<input type="submit" value="查询"/>--%>
					<%--<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>--%>
  				  	<%--&lt;%&ndash;<input type="button" value="打印" onclick="btnPrintClick();"/>&ndash;%&gt;--%>
			      <%--</li>--%>
  				</ul>

		<form>
	</div>

		<!--startprint-->
		<p class="title_p">${laboratoryNoticeVO.term}</p>
		<p class="title_p">${laboratoryNoticeVO.grade}&nbsp;${laboratoryNoticeVO.title}</p>
		<table class="tab_lab_1"  cellspacing="0" cellpadding="0">
			<tr class="tab_lab_2_tr">
				<th colspan="2">填单日期</th><td colspan="2"></td>
				<th>实验日期</th><td colspan="2"></td>
			</tr>
			<tr class="tab_lab_2_tr">
				<th colspan="2">实验题目</th><td colspan="5">${laboratoryNoticeVO.itemName}</td>
			</tr>
			<tr class="tab_lab_2_tr">
				<th colspan="2">实验仪器、材料及药品</th><td colspan="5">${laboratoryNoticeVO.deviceAndAsset}</td>
			</tr>
			<tr class="tab_lab_1_tr">
				<th width="5%">班级</th>
				<th width="10%">日期</th>
				<th width="5%">节次</th>
				<th width="5%">授课教师</th>
				<th width="7.5%">授课教师(签字)</th>
				<th width="15%">实验情况</th>
				<th width="10%">备注</th>
			</tr>
			<c:forEach items="${laboratoryNoticeVO.informationList}" var="curr" varStatus="status">
			<tr class="tab_lab_1_tr">
				<td>${curr[3]}</td>
				<td>${curr[0]}</td>
				<%--<td></td>--%>
				<td>${curr[1]}</td>
                <td>${curr[2]}</td>
				<td> </td>
				<td>
					<label><input name="Fruit" type="radio" value="" />好 </label>
					<label><input name="Fruit" type="radio" value="" />一般 </label>
					<label><input name="Fruit" type="radio" value="" />其他 </label>
				</td>
				<td> </td>
			</tr>
			</c:forEach>
		</table>

		<!--endprint-->
		<!-- 分页[s] -->
	<%--<div class="page" >--%>
        <%--${pageModel.totalRecords}条记录,共${pageModel.totalPage}页--%>
    <%--<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listItemClasses?currpage=1')" target="_self">首页</a>--%>
	<%--<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listItemClasses?currpage=${pageModel.previousPage}')" target="_self">上一页</a>--%>
	<%--第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">--%>
	<%--<option value="${pageContext.request.contextPath}/log/listItemClasses?currpage=${pageModel.currpage}">${pageModel.currpage}</option>--%>
	<%--<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">--%>
    <%--<c:if test="${j.index!=pageModel.currpage}">--%>
    <%--<option value="${pageContext.request.contextPath}/log/listItemClasses?currpage=${j.index}">${j.index}</option>--%>
    <%--</c:if>--%>
    <%--</c:forEach></select>页--%>
	<%--<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listItemClasses?currpage=${pageModel.nextPage}')" target="_self">下一页</a>--%>
 	<%--<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listItemClasses?currpage=${pageModel.lastPage}')" target="_self">末页</a>--%>
    <%--</div>--%>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
