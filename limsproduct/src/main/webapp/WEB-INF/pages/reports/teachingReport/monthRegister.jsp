<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="decorator" content="iframe"/>
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		//取消查询
		function cancel() {
			window.location.href="${pageContext.request.contextPath}/report/teachingReport/monthRegister?currpage=1";
		}
		//跳转
		function targetUrl(url) {
			document.queryForm.action=url;
			document.queryForm.submit();
		}
		// 打印
		$(document).ready(function(){
			$('#myPrint').click(function(){
				$('#myShow').jqprint();
			});
		});
		//导出excel
		function exportExcel()
		{
			document.queryForm.action = "${pageContext.request.contextPath}/report/teachingReport/exportMonthRegister";
			document.queryForm.submit();
		}
	</script>
</head>

<body>
<div class="navigation">
	<div id="navigation">
		<ul>
			<li class="end"><a href="javascript:void(0)">月报报表</a></li>
		</ul>
	</div>
</div>

<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<div class="tool-box">
						<form name="queryForm" action="${pageContext.request.contextPath}/report/teachingReport/monthRegister?currpage=1" method="post">
							学期：<select class="chzn-select" id="term" name="term">
								<c:forEach items="${schoolTerms}" var="current">
									<c:if test="${current.id == term}">
										<option value ="${current.id}" selected>${current.termName} </option>
									</c:if>
									<c:if test="${current.id != term}">
										<option value ="${current.id}" >${current.termName} </option>
									</c:if>
								</c:forEach>
							</select>
							综合查询：<input id="params" name="params" value="${params}" type="text"/>
							时间区间：<input class="Wdate" id="start_date" name="start_date" type="text" value="${start_date}"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
							<input class="Wdate" id="end_date" name="end_date" type="text" value="${end_date}"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
							<input type="submit" value="查询"/>
							<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
							<input type="button" onclick="exportExcel();" value="导出"/>
							<input type="button" id="myPrint" value="打印"/>
						</form>
					</div>

					<table class="tb" id="myShow">
						<thead>
						<tr>
							<th>序号</th>
							<th>系别</th>
							<th>实验<br>部门</th>
							<th>负责人</th>
							<th><spring:message code="all.trainingRoom.labroom" /><br>名称</th>
							<th>地点</th>
							<th>日期</th>
							<th>课程<br>名称</th>
							<th>实验<br>名称</th>
							<th>上课<br>人数</th>
							<th>上课<br>课时</th>
							<th>上课<br>人时数</th>
							<th>实验<br>类别</th>
							<th>实验<br>类型</th>
							<th>指导<br>教师</th>
							<th>班级</th>
							<th>备注</th>
							<th>考核<br>方法</th>
						</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="curr" varStatus="i">
								<tr>
									<td>${i.count+(pageModel.currpage-1)*pageModel.pageSize}</td>
									<td>${curr[0]}</td>
									<td>${curr[1]}</td>
									<td>${curr[2]}</td>
									<td>${curr[3]}</td>
									<td>${curr[4]}</td>
									<td>${curr[5]}</td>
									<td>${curr[6]}</td>
									<td>${curr[7]}</td>
									<td>${curr[8]}</td>
									<td>${curr[9]}</td>
									<td>${curr[10]}</td>
									<td>${curr[11]}</td>
									<td>${curr[12]}</td>
									<td>${curr[13]}</td>
									<td>${curr[14]}</td>
									<td>${curr[15]}</td>
									<td>${curr[16]}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="page" >
						${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/teachingReport/monthRegister?currpage=1')" target="_self">首页</a>
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/teachingReport/monthRegister?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
						第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
						<option value="${pageContext.request.contextPath}/report/teachingReport/monthRegister?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
							<c:if test="${j.index!=pageModel.currpage}">
								<option value="${pageContext.request.contextPath}/report/teachingReport/monthRegister?currpage=${j.index}">${j.index}</option>
							</c:if>
						</c:forEach></select>页
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/report/teachingReport/monthRegister?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/report/teachingReport/monthRegister?currpage=${pageModel.lastPage}')" target="_self">末页</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
