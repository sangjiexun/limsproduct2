<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<fmt:setBundle basename="bundles.lab-resources" />
<head>
<meta name="decorator" content="iframe" />
<link href="${pageContext.request.contextPath}/css/iconFont.css"
	rel="stylesheet">
<!-- 点击框的样式 -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jquery.coolautosuggest.css" />
<!-- 下拉框的样式 -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.coolautosuggest.js"></script>
<!-- 打印插件的引用 -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
  <script type="text/javascript">
	  function cancel(){
		  window.location.href="${pageContext.request.contextPath}/report/reportLabOpenInfo"
	  }
  </script>

</head>
<body> 
	<!--导航  -->
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.report.system" /></a></li>
				<li class="end"><a href="javascript:void(0)"><spring:message code="left.report.labopen" /></a></li>
			</ul>
		</div>
	</div>
	<!--导航结束  -->
	
	
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<ul class="TabbedPanelsTabGroup">
				<li class="TabbedPanelsTab1 selected" id="s1">
					<a href="javascript:void(0)">实验室开放情况汇总表</a>
				</li>
			</ul>
			<div class="TabbedPanelsContentGroup1">
				<div class="TabbedPanelsContent">
					<%--<div class="tool-box">--%>
						<%--<form id="subform" name='f' action='${pageContext.request.contextPath}/report/reportLabOpenInfo' method='POST'>--%>
						<%--<ul>--%>
							<%--	<li>学期：<select class="chzn-select" id="term" name="term" style="width:180px">
									<c:forEach items="${schoolTerms}" var="current">
										<c:if test="${current.id == term}">
											<option value ="${current.id}" selected>${current.termName} </option>
										</c:if>
										<c:if test="${current.id != term}">
											<option value ="${current.id}" >${current.termName} </option>
										</c:if>
									</c:forEach>
								</select></li>
								<li><input type="submit" value="搜索"></li>
								<li><input type="button" onclick="cancel()" value="取消"></li>
							--%>
						<%--</ul>--%>
						<%--</form>--%>
					<%--</div>--%>
					<div class="content-box">
						<table>
							<thead>
							<tr height="20px">
								<th height="20px" align="center">序号</th>
								<th height="20px" align="center" width="15%">学期</th>
								<th height="20px" align="center">开放活动数</th>
								<th height="20px" align="center">活动人数</th>
								<th height="20px" align="center">开放设备数</th>
								<th height="20px" align="center">开放时数</th>
								<th height="20px" align="center">开放人时数</th>
								<th height="20px" align="center">开放机时数</th>
							</tr>
							</thead>
							<tbody>
							<c:set var="year"></c:set>
							<c:set var="a0" value="0"></c:set>
							<c:set var="a1" value="0"></c:set>
							<c:set var="a2" value="0"></c:set>
							<c:set var="a3" value="0"></c:set>
							<c:set var="a4" value="0"></c:set>
							<c:set var="a5" value="0"></c:set>
							<c:set var="a6" value="0"></c:set>
							<c:set var="a10" value="0"></c:set>
							<c:set var="a11" value="0"></c:set>
							<c:set var="a12" value="0"></c:set>
							<c:set var="a13" value="0"></c:set>
							<c:set var="a14" value="0"></c:set>
							<c:set var="a15" value="0"></c:set>
							<c:set var="a16" value="0"></c:set>
							<c:forEach items="${list}" var="curr" varStatus="i">

								<c:if test="${year!=curr[7]&&i.count!=1 }">
									<tr>
										<td colspan="2" align="center">总计： </td>
										<td>${a1}</td>
										<td>${a2}</td>
										<td>${a3}</td>
										<td>${a4}</td>
										<td>${a5}</td>
										<td>${a6}</td>
									</tr>
									<c:set var="a0" value="0"></c:set>
									<c:set var="a1" value="0"></c:set>
									<c:set var="a2" value="0"></c:set>
									<c:set var="a3" value="0"></c:set>
									<c:set var="a4" value="0"></c:set>
									<c:set var="a5" value="0"></c:set>
									<c:set var="a6" value="0"></c:set>
								</c:if>
								<c:set var="a1" value="${curr[1]+a1 }" ></c:set>
								<c:set var="a2" value="${curr[2]+a2}" ></c:set>
								<c:set var="a3" value="${curr[3]+a3}" ></c:set>
								<c:set var="a4" value="${curr[4]+a4}" ></c:set>
								<c:set var="a5" value="${curr[5]+a5}" ></c:set>
								<c:set var="a6" value="${curr[6]+a6}" ></c:set>
								<tr>
									<td>${i.count}</td>
									<td>${curr[0]}</td>
									<td>${curr[1]}</td>
									<td>${curr[2]}</td>
									<td>${curr[3]}</td>
									<td>${curr[4]}</td>
									<td>${curr[5]}</td>
									<td>${curr[6]}</td>
								</tr>
								<c:if test="${i.count==list.size() }">
									<tr>
										<td colspan="2" align="center">总计： </td>
										<td>${a1}</td>
										<td>${a2}</td>
										<td>${a3}</td>
										<td>${a4}</td>
										<td>${a5}</td>
										<td>${a6}</td>
									</tr>
								</c:if>
								<c:set var="a11" value="${curr[1]+a11 }"></c:set>
								<c:set var="a12" value="${curr[2]+a12 }"></c:set>
								<c:set var="a13" value="${curr[3]+a13 }"></c:set>
								<c:set var="a14" value="${curr[4]+a14 }"></c:set>
								<c:set var="a15" value="${curr[5]+a15 }"></c:set>
								<c:set var="a16" value="${curr[6]+a16 }"></c:set>
								<c:set var="year">${curr[7]}</c:set>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

			</div>
		</div>
	</div>

</body>