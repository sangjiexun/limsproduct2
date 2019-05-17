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
	  window.location.href="${pageContext.request.contextPath}/log/listAsset?currpage=1";
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
		  <li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/log/listInstrumentLendingegistration?currpage=1">仪器借出登记表</a></li>
		  <li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/log/listReceiptOfLowValueConsumables?currpage=1">低值易耗品领用登记单</a></li>
		  <li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/log/listDrugCabinet?currpage=1">药品出库登记表</a></li>
		  <li class="TabbedPanelsTab" id="s5"><a href="${pageContext.request.contextPath}/log/listAsset?currpage=1">耗材领用记录单</a></li>
		  <li class="TabbedPanelsTab selected" id="s6"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=6">实验通知单</a></li>
		  <li class="TabbedPanelsTab" id="s7"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=7">分组实验通知、教学记录单</a></li>
		  <li class="TabbedPanelsTab" id="s8"><a href="${pageContext.request.contextPath}/log/listStatisticalTableOfExperiments?currpage=1">实验开出情况统计表</a></li>
		  <input class="btn btn-new" type="button" value="打印" onclick="btnPrintClick();"/>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">年度使用绩效评价表</div>--%>
	<%--</div>--%>
	
	<div class="tool-box" style="display: none">
		<form name="queryForm" action="${pageContext.request.contextPath}/log/listAsset?currpage=1" method="post">
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
		<table class="tab_lab_1"  cellspacing="0" cellpadding="0">
			<tr class="tab_lab_2_tr">
				<th>实验课题</th><td colspan="4">${laboratoryNoticeVO.itemName}</td>
				<th>演示或分组</th><td colspan="2">${laboratoryNoticeVO.itemCategory}</td>
			</tr>
			<tr class="tab_lab_2_tr">
				<th>实验时间</th><td colspan="7">${laboratoryNoticeVO.itemTime}</td>
			</tr>
			<tr class="tab_lab_2_tr">
				<th>实验班级</th><td></td>
				<th>学生数</th><td></td>
				<th>分组数</th><td></td>
				<th>授课教师</th><td>${laboratoryNoticeVO.teacher}</td>
			</tr>
			<tr class="tab_lab_1_tr">
				<th colspan="4">仪器、材料或药品名称</th>
				<th>规格</th>
				<th>单位</th>
				<th>领出数量</th>
				<th>归还数量</th>
			</tr>
			<c:forEach items="${laboratoryNoticeVO.informationList}" var="curr" varStatus="status">
			<tr class="tab_lab_1_tr">
				<td colspan="4">${curr[0]}</td>
				<td>${curr[1]}</td>
				<td>${curr[2]}</td>
				<td>${curr[3]}</td>
				<td>${curr[4]}</td>
			</tr>
			</c:forEach>
		</table>

		<!--endprint-->
	<!-- 分页[s] -->
	<%--<div class="page" >--%>
        <%--${pageModel.totalRecords}条记录,共${pageModel.totalPage}页--%>
    <%--<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listAsset?currpage=1')" target="_self">首页</a>--%>
	<%--<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listAsset?currpage=${pageModel.previousPage}')" target="_self">上一页</a>--%>
	<%--第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">--%>
	<%--<option value="${pageContext.request.contextPath}/log/listAsset?currpage=${pageModel.currpage}">${pageModel.currpage}</option>--%>
	<%--<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	--%>
    <%--<c:if test="${j.index!=pageModel.currpage}">--%>
    <%--<option value="${pageContext.request.contextPath}/log/listAsset?currpage=${j.index}">${j.index}</option>--%>
    <%--</c:if>--%>
    <%--</c:forEach></select>页--%>
	<%--<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listAsset?currpage=${pageModel.nextPage}')" target="_self">下一页</a>--%>
 	<%--<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listAsset?currpage=${pageModel.lastPage}')" target="_self">末页</a>--%>
    <%--</div>--%>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
