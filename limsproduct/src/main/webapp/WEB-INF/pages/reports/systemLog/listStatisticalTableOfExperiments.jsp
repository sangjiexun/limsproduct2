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
	  window.location.href="${pageContext.request.contextPath}/log/listStatisticalTableOfExperiments?currpage=1";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  function goBack() {
      window.history.go(-1);
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
		.content-box thead tr th, .content-box td {
			border-right: 1px solid #e4e5e7;
		}
        .title_p{
            text-align: center;
            font-size: 16px;
        }
		table {
			width:100%;
			border-collapse: collapse;
			border: 1px solid #d0d6dc;
		}
		table thead tr th, table td {
			border-right: 1px solid #e4e5e7;
			border-bottom: 1px solid #e4e5e7;
		}
		table thead tr th, table td {
			padding: 7px 7px;
		}
		#my_show tr td {
			border-bottom: 1px solid #cccccc;
		}
	</style>
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
		  <li class="TabbedPanelsTab1" id="s1"><a href="${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=1">实验计划表</a></li>
		  <%--<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/log/listLabRoom?currpage=1&type=1">仪器借出登记表</a></li>--%>
		  <li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/log/listReceiptOfLowValueConsumables?currpage=1">低值易耗品领用登记单</a></li>
		  <li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/log/listDrugCabinet?currpage=1">药品出库登记表</a></li>
		  <li class="TabbedPanelsTab" id="s5"><a href="${pageContext.request.contextPath}/log/listAsset?currpage=1">化学品领用记录单</a></li>
		  <li class="TabbedPanelsTab" id="s6"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=6">实验通知单</a></li>
		  <li class="TabbedPanelsTab" id="s7"><a href="${pageContext.request.contextPath}/log/listItem?currpage=1&type=7">分组实验通知、教学记录单</a></li>
		  <li class="TabbedPanelsTab selected" id="s8"><a href="${pageContext.request.contextPath}/log/listLabRoom?currpage=1&type=2">实验开出情况统计表</a></li>
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
		<form name="queryForm" action="${pageContext.request.contextPath}/log/listStatisticalTableOfExperiments?currpage=1" method="post">
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
    <p class="title_p">${labRoomName}实验室实验开出情况统计表</p>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th colspan="1">类别</th>
	    <th colspan="3">演示实验</th>
	    <th colspan="3">分组实验</th>
	  </tr>
	  <tr>
		  <th>年级</th>
		  <th>应做个数</th>
		  <th>实做个数</th>
		  <th>开设率</th>
		  <th>应做个数</th>
		  <th>实做个数</th>
		  <th>开设率</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${laboratoryNoticeVO.informationList}" var="curr" varStatus="status">
	  <tr>
	    <td>${curr[0]}</td>
	    <td>${curr[1]}</td>
	    <td>${curr[2]}</td>
	    <td>${curr[3]}%</td>
	    <td>${curr[4]}</td>
	    <td>${curr[5]}</td>
	    <td>${curr[6]}%</td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
		<!--endprint-->
	<!-- 分页[s] -->
	<%--<div class="page" >--%>
        <%--${pageModel.totalRecords}条记录,共${pageModel.totalPage}页--%>
    <%--<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=1')" target="_self">首页</a>--%>
	<%--<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=${pageModel.previousPage}')" target="_self">上一页</a>--%>
	<%--第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">--%>
	<%--<option value="${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=${pageModel.currpage}">${pageModel.currpage}</option>--%>
	<%--<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	--%>
    <%--<c:if test="${j.index!=pageModel.currpage}">--%>
    <%--<option value="${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=${j.index}">${j.index}</option>--%>
    <%--</c:if>--%>
    <%--</c:forEach></select>页--%>
	<%--<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=${pageModel.nextPage}')" target="_self">下一页</a>--%>
 	<%--<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/log/listExperimentalSchedule?currpage=${pageModel.lastPage}')" target="_self">末页</a>--%>
    <%--</div>--%>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
