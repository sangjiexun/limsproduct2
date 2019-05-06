<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
  <head>
  <meta name="decorator" content="iframe" />  
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/jquery-1.8.2.min.js"></script>
	<!-- 下拉框的样式 -->
	<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/style.css" /> --%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
	<!-- 下拉的样式结束 -->
	  <link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
	<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/timetable/lmsReg.css">
	<!-- 打印开始 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<!-- 打印结束 -->
<script type="text/javascript">
	 
</script>
<script langauge="javascript">
//如果为查询则提交查询页面，如果为电子表格导出，则导出excel
function subform(gourl){ 
 var gourl;
 form.action=gourl;
 form.submit();
}

function cancel(){
	location.href = '${pageContext.request.contextPath}/report/operationItemStudentAndHours?flag=-1';
}

function search(){
	var terms = $("#term").val();
	if(terms != null)
	{
		document.form.action="${pageContext.request.contextPath}/report/operationItemStudentAndHours?flag=0";
		document.form.submit();
	}
	else
	{
		alert("请选择学期！");
	}
}
function print(){
    $('#myShow').jqprint();
}
//导出excel
function exportExcel()
{
	document.form.action = "${pageContext.request.contextPath}/report/exportOperationItemStudentAndHours?flag=${flag}";
	document.form.submit();
}
</script> 
  </head>
  
  <body>
  <div class="navigation">
	  <div id="navigation">
		  <ul>
			  <li><a href="javascript:void(0)"><spring:message code="left.report.system" /></a></li>
			  <li class="end"><a href="javascript:void(0)"><spring:message code="left.report.month" /></a></li>
		  </ul>
	  </div>
  </div>
  <!--消息内容弹出框-->
<div id="msg" class="easyui-window" title="消息" closed="true" iconCls="icon-add" style="width:450px;">
	<table class="tb" style="margin:10px"> 
	<tr id="magContent">
	
	</tr>	
	</table>
	<br>
	<button class="btn" onClick="location.reload();" style="margin:10px;">关闭</button>
</div>
  
  
  
   <div class="right-content">
   <%--<div class="title">--%>
	<%--<div id="title">实验学时数汇总表</div>--%>
<%--</div>--%>
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab1 selected" id="s1">
			<a href="javascript:void(0)">实验学时数汇总表</a>
		</li>
		<a class="btn btn-new" onclick="exportExcel();">导出</a>
		<input class="btn btn-new"  onclick="print()" id="myPrint" value="打印" type="button" />
	</ul>
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
		<div class="content-box">	
		<%--<div class="title">--%>
	  <%--<div id="title">实验学时数汇总表</div>--%>
	  <%--<a class="btn btn-new" onclick="exportExcel();">导出</a>--%>
	  <%--<input class="btn btn-new"  onclick="print()" id="myPrint" value="打印" type="button" /> --%>
<%--</div>--%>
			<div class="tool-box">
				<form:form name="form" action = "${pageContext.request.contextPath}/report/operationItemStudentAndHours?flag=0" method="post">
					<ul>
						<li>学期:
						<select id="term" name="term" class="chzn-select" style="width:400px;" multiple="multiple">
						<c:forEach items="${allTerms}" var="curr">
						<c:set var="isSelected" value="0"></c:set>
							<c:forEach items="${selectedTerms}" var="sel">
								<c:if test="${curr.id eq sel}">
									<c:set var="isSelected" value="1"></c:set>
								</c:if>
							</c:forEach>
							<c:if test="${isSelected eq 1 }">
								<option value="${curr.id }" selected="selected">${curr.termName }</option>
							</c:if>
							<c:if test="${isSelected ne 1 }">
								<option value="${curr.id }">${curr.termName }</option>
							</c:if>
						</c:forEach>
						</select>
						</li>
						<li><input type="button" onclick="search()" value="查询"/>
							<input class="cancel-submit" type="button" onclick="cancel()" value="取消查询"/></li>
					</ul>
					</form:form>
			</div>
		</div>
<div id="myShow" class="content-box">				
<div id="contentarea">
<div id="content">
<div class="content-box">
	<table cellpadding="1" cellspacing="1">  <!--  valign="center" cellpadding="5" cellspacing="0" align="center" width="100%" style="word-wrap:break-all" -->
			<thead>
				<tr>
					<th class="tbh" width="4%">序号</th>
					<th class="tbh" width="4%">中心</th>
					<th class="tbh" width="4%">人次数</th>
					<th class="tbh" width="4%">人时数</th>
					<th class="tbh" width="4%">实验项目数</th>
				</tr>
			</thead>
		<tbody>
					<c:set var="totalStudent" value="0"></c:set>
					<c:set var="totalHour" value="0"></c:set>
					<c:set var="totalItem" value="0"></c:set>
		<c:forEach items="${studentAndHours}" var="current"  varStatus="i">	
					<c:set var="totalStudent" value="${totalStudent+current[1] }"></c:set>
					<c:set var="totalHour" value="${totalHour+current[2] }"></c:set>
					<c:set var="totalItem" value="${totalItem+current[3] }"></c:set>
        	<tr>
            	<td>${i.count}</td>
            	<td>${current[0]}</td>
            	<td>${current[1] }</td>
            	<td>${current[2]}</td>
            	<td>${current[3]}</td>
         	</tr>
        </c:forEach>
        	<tr>
            	<td>合计</td>
            	<td>clw庚商学院</td>
            	<td>${totalStudent }</td>
            	<td>${totalHour }</td>
            	<td>${totalItem }</td>
         	</tr>
      	</tbody>
	</table>
</div>
</div>
</div>

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
      '.chzn-select'           : {search_contains:true},
      '.chzn-select-deselect'  : {allow_single_deselect:true},
      '.chzn-select-no-single' : {disable_search_threshold:10},
      '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
    
</script>
<!-- 下拉框的js -->
</body>
</html>