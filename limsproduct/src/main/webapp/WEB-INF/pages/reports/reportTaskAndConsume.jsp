<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta name="decorator" content="iframe" />
<title>实验室任务及耗材统计表</title>
<!-- 下拉框的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
<!-- 打印开始 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<!-- 打印结束 -->
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/timetable/lmsReg.css">

<script type="text/javascript">
var temUrl = "";
//跳转
function targetUrl(url)
{
	temUrl = document.queryForm.action;
	document.queryForm.action=url;
	document.queryForm.submit();
	document.queryForm.action=temUrl;
}
//取消查询
function cancel()
{
  window.location.href="${pageContext.request.contextPath}/report/reportTaskAndConsume?currpage=1";
}

$(document).ready(function(){
      $('#fullview').click(function(){
           $('.sit_sider_bar').animate( { width:'0'}, 500 );
           $('.sit_maincontent').animate( { width:'100%'}, 500 );
           $('.toggle,.toggleLink,#fullview,.sit_footer,.sit_sider_bar > h3').css("display","none");
           $('#fullview1').css("display","inline");
      });
  
      $('#fullview1').click(function(){
           $('.sit_sider_bar').animate( { width:'23%'}, 500 );
           $('.sit_maincontent').animate( { width:'75%'}, 500 );
           $('#fullview1').css("display","none");
           $('.toggle,#fullview,.toggleLink,.sit_footer,.sit_sider_bar > h3 ').css("display","inline");
      });
      
      $('#myPrint').click(function(){
           $('#myShow').jqprint();
      });
});
                              
$(function(){
      var height = $(document).height();
      $(".sit_sider_bar").css('height',height);
      $(".sit_maincontent").css('height',height);
}) ;

</script>
  </head>
  
  <body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
		  <li><a href="javascript:void(0)"><spring:message code="left.report.system" /></a></li>
		  <li class="end"><a href="javascript:void(0)"><spring:message code="left.report.labasset" /></a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">实验室任务及耗材统计表</a>
		  </li>
		  <input class="btn btn-new" onclick="targetUrl('${pageContext.request.contextPath}/report/exportTaskAndConsume')" type="button" value="导出">
		  <input class="btn btn-new" id="myPrint" value="打印" type="button" />
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">实验室任务及耗材统计表</div>--%>
	  <%--<input class="btn btn-new" onclick="targetUrl('${pageContext.request.contextPath}/report/exportTaskAndConsume')" type="button" value="导出">--%>
	  <%--<input class="btn btn-new" id="myPrint" value="打印" type="button" />--%>
	<%--</div>--%>
	
	<%--<table border="0" cellpadding="5" cellspacing="0" bgcolor="#F3EFEE" height="30" width="100%">--%>
	<%--<tbody>--%>
	  <div class="tool-box">
	<form name="queryForm" method="Post" action="${pageContext.request.contextPath}/report/reportTaskAndConsume?currpage=1">
		<ul>
			<li>
			学期:
			  <select class="chzn-select" name="term">
			  	<c:forEach items="${schoolTermMap}" var="item">  
			  		<c:if test="${item.key eq term}">
                		<option value="${item.key }"  selected="selected">${item.value}</option>  
                	</c:if>
			  		<c:if test="${item.key ne term}">
                		<option value="${item.key }">${item.value}</option>  
                	</c:if>
				</c:forEach>
			  </select>
			</li>
			<li>
			<input type="submit" value="查询"/>
			<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li><br>
		</ul>
	</form>
	  </div>
	<%--</tbody>--%>
  <%--</table>--%>
	
<div id="myShow">	
<style type="text/css">
		td{border:solid 1px #4F4F4F !important;}
</style>
<form name="form" method="Post" action="${pageContext.request.contextPath}/report/reportTaskAndConsume?currpage=1">	
	<table class="tb" id="my_show">
	  <thead style="center-content">
		  <tr>
		    <%--<th colspan="14">--%>
		    <%--</th>--%>
		  </tr>
		  <tr>
        	<th style="border:solid 1px #4F4F4F !important;" width= "2% " rowspan="3">序号</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="3">单位编号</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "5% " rowspan="3">单位名称</th> 
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="3">实验室数量</th> 
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="7">教学任务及完成量</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="3">实验材料消耗</th>
		  </tr>
		  <tr>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">课程门数</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">计划学时</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="2">应开实验</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="3">开出实验</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">一次性</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">非一次性</th> 
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">合计</th>
		  </tr>
		  <tr>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% ">个数</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% ">时数</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% ">个数</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% ">时数</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "3% ">开出率%</th>
		  </tr>
	  </thead>
	  
	  <tbody>
	  <c:if test="${taskAndConsumeLists.size() ne 1 }"> <!-- 当选择的是某一个具体中心时没有必要进行这些操作 -->
		  <!-- 计算整个学院的五种类型的分别的总个数和总时数 -->
		  <c:set var="count1College" value="0"></c:set>
		  <c:set var="hour1College" value="0"></c:set>
		  <c:set var="count2College" value="0"></c:set>
		  <c:set var="hour2College" value="0.00"></c:set>
		  <c:set var="count3College" value="0"></c:set>
		  <c:set var="hour3College" value="0.00"></c:set>
		  <c:set var="count4College" value="0"></c:set>
		  <c:set var="hour4College" value="0"></c:set>
		  <c:set var="count5College" value="0"></c:set>
		  <c:set var="hour5College" value="0"></c:set>
		  <c:forEach items="${taskAndConsumeLists}" var="curr" varStatus="i">
		  	<c:set var="count1College" value="${count1College+curr[2] }"></c:set>
		  	<c:set var="hour1College" value="${hour1College+curr[3]}"></c:set>
		  	<c:set var="count2College" value="${count2College+curr[4]}"></c:set>
		  	<c:set var="hour2College" value="${hour2College+curr[5]}"></c:set>
		  	<c:set var="count3College" value="${count3College+curr[6]}"></c:set>
		  	<c:set var="hour3College" value="${hour3College+curr[7]}"></c:set>
		  	<c:set var="count4College" value="${count4College+curr[8]}"></c:set>
		  	<c:set var="hour4College" value="${hour4College+curr[9]}"></c:set>
		  	<c:set var="count5College" value="${count5College+curr[10]}"></c:set>
		  </c:forEach>
	  </c:if>
	  
	  <c:forEach items="${taskAndConsumeLists}" var="curr" varStatus="i">
	  	<c:if test="${taskAndConsumeLists.size() ne 1 }"> <!-- 当选择的是某一个具体中心时没有必要进行这些操作 -->
		  <c:if test="${i.count eq 1}">
		  	<tr>
		  		<td>1</td>
		  		<td>clw201807</td>
		  		<td>clw庚商学院</td>
		  		<td>${count1College }</td>
		  		<td>${hour1College }</td>
		  		<td>${count2College }</td>
		  		<td>${hour2College }</td>
		  		<td>${count3College }</td>
		  		<td>${hour3College }</td>
		  		<td>${count4College }</td>
		  		<td><fmt:formatNumber type="number" value="${hour3College/hour2College*100}" maxFractionDigits="2"/></td>
		  		<td>${hour4College }</td>
		  		<td>${count5College }</td>
		  		<td>${hour4College+count5College }</td>
		  	</tr>
		  	</c:if>
	  	</c:if>
	  	
		  <tr>
	    	<td>
		  		<c:if test="${taskAndConsumeLists.size() eq 1 }">
		  			${i.count+1 }
		  		</c:if>	
		  		<c:if test="${taskAndConsumeLists.size() ne 1 }">
		  			${i.count }
		  		</c:if>	
	  		</td>
	    	<td>${curr[0]}</td>
	    	<td>${curr[1]}</td>
	    	<td>${curr[2]}</td>
            <td>${curr[3]}</td>
            <td>${curr[4]}</td>
		    <td>${curr[5]}</td>
	    	<td>${curr[6]}</td>
	    	<td>${curr[7]}</td>
           	<td>${curr[8]}</td>
           	<td><fmt:formatNumber type="number" value="${curr[9]}" maxFractionDigits="2"/></td>
           	<td>${curr[10]}</td>
            <td>${curr[11]}</td>
            <td>${curr[12]}</td>
		  </tr>
	  </c:forEach>
	  </tbody>
	</table>
</form>
</div>
<div class="page" >
    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/reportTaskAndConsume?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/reportTaskAndConsume?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/report/reportTaskAndConsume?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
	<c:if test="${j.index!=pageModel.currpage}">
	<option value="${pageContext.request.contextPath}/report/reportTaskAndConsume?currpage=${j.index}">${j.index}</option>
	</c:if>
	</c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/report/reportTaskAndConsume?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/report/reportTaskAndConsume?currpage=${pageModel.lastPage}')" target="_self">末页</a>
</div>

  </div>
  </div>
  </div>
  </div>

<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	    var config = {
	      '.chzn-select': {search_contains : true},
	      '.chzn-select-deselect'  : {allow_single_deselect:true},
	      '.chzn-select-no-single' : {disable_search_threshold:10},
	      '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
	      '.chzn-select-width'     : {width:"95%"}
	    }
	    for (var selector in config) {
	      $(selector).chosen(config[selector]);
	    }
</script>
<!-- 下拉框的js -->
 
</body>
</html>