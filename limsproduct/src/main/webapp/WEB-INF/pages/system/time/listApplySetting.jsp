<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
    <!-- 文件上传的样式和js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  
  <style type="text/css">
  	fieldset th{
  		text-align:left;
  	}
  </style>
	
  	<script type="text/javascript">
		function targetUrl(url)
		{
    		document.form.action=url;
    		document.form.submit();
  		}
		
  	</script>
  	
  	<script type="text/javascript">
  	function newSet(){
		window.location.href = "${pageContext.request.contextPath}/system/newApplySetting";
	}
  	function PrintClick(){
  		window.print();
  	}
  	function exportAll(){
  		document.form.action = "${pageContext.request.contextPath}/system/exportApplySetting";
  		document.form.submit();
  	}
  	</script>
</head>
  
<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.system.management" /></a>
				</li>
				<li class="end"><a href="javascript:void(0)"><spring:message code="left.applicationSubmittedTime.setting" /></a>
				</li>
			</ul>
		</div>
	</div>

	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<ul class="TabbedPanelsTabGroup">
				<li class="TabbedPanelsTab1 selected" id="s1">
					<a href="javascript:void(0)">排课申请的时间列表</a>
				</li>
				<input class="btn btn-new" type="button" value="新建" onclick="newSet()"/>
				<input class="btn btn-new" id="print" type="button" value="打印" onclick="PrintClick()">
				<input class="btn btn-new" type="button" value="导出" onclick="exportAll();">
			</ul>
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="content-box">
						<%--<div class="title">--%>
							<%--<div id="title">--%>
							<%--<form:form name="form" action="${pageContext.request.contextPath}/system/listApplySetting" method="POST" modelAttribute="systemPreDay">--%>
							<%--<ul>--%>
							<%--排课申请的时间列表--%>
							<%--<input class="btn btn-new" type="button" value="新建" onclick="newSet()"/>--%>
							<%--<input class="btn btn-new" id="print" type="button" value="打印" onclick="PrintClick()">--%>
							<%--<input class="btn btn-new" type="button" value="导出" onclick="exportAll();">--%>
							<%--</ul>--%>
							<%--</form:form>--%>
							<%--</div>--%>
						<%--</div>--%>

						<table class="tb" id="my_show">
							<thead>
								<tr>
									<th>学期</th>
									<th>提前递交天数</th>
									<th>流程</th>
									<th>创建时间</th>
									<th>创建人</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${systemPreDay}" var="curr">
									<tr>
										<td>${curr.schoolTerm.termName}</td>
										<td>${curr.dayNum}</td>
										<td>
											<c:if test="${curr.process eq '0'}"><spring:message code="all.trainingRoom.labroom" />预约</c:if>
											<c:if test="${curr.process eq '1'}">设备预约</c:if>
											<c:if test="${curr.process eq '2'}">软件安装申请</c:if>
											<c:if test="${curr.process eq '3'}"><spring:message code="all.trainingRoom.labroom" />借用</c:if>
											<c:if test="${curr.process eq '4'}">设备借用</c:if>
										</td>
										<td><fmt:formatDate value="${curr.createTime.time}" pattern="yyyy-MM-dd"/></td>
										<td>${curr.user.cname}</td>
										<td>
										<a href="${pageContext.request.contextPath}/system/editApplySetting?id=${curr.id}" >修改</a>
										<a href="${pageContext.request.contextPath}/system/deleteApplySetting?id=${curr.id}" onclick="return confirm('确定删除？');">删除</a>
										</td>
										
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!-- 分页[s] -->
						<div class="page" >
					        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
					    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/system/listApplySetting?currpage=1')" target="_self">首页</a>			    
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/system/listApplySetting?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
						第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
						<option value="${pageContext.request.contextPath}/system/listApplySetting?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
					    <c:if test="${j.index!=pageModel.currpage}">
					    <option value="${pageContext.request.contextPath}/system/listApplySetting?currpage=${j.index}">${j.index}</option>
					    </c:if>
					    </c:forEach></select>页
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/system/listApplySetting?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
					 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/system/listApplySetting?currpage=${pageModel.lastPage}')" target="_self">末页</a>
					    </div>
					    <!-- 分页[e] -->
					</div>
  				</div>
  			</div>
  		</div>
  	</div>
</body>
</html>
