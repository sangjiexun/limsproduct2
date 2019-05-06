<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<html>
<head>
  <meta name="decorator" content="iframe"/>
  <script type="text/javascript">
</script>
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  <style>
    .t_style ul li{
      float:left;
    }
  </style>
</head>

<body>
<!-- 导航栏 -->
<div class="navigation">
<div id="navigation">
<ul>
<li><a href=""><spring:message code="left.system.management" /></a></li>
<li class="end"><a href=""><spring:message code="left.stationNumber.setting" /></a></li>
</ul>
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
<!-- 导航栏 -->
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab1 selected" id="s1">
			<a href="javascript:void(0)">可预约工位数设置</a>
		</li>
	</ul>
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<div class="title">
					<div class="title">可预约工位数设置</div>
				</div>
				<table>
					<thead>
						<tr>
							<th>可预约工位数</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<c:if test="${not empty cDictionary.CName}">
								  ${cDictionary.CName}
								</c:if>
								<c:if test="${empty cDictionary.CName}">
								  未设置
								</c:if>
							</td>
							<td></td>
						</tr>
						<div display="block">
						<tr>
							<td><input type="text" id="reservationCount" onkeyup="value=value.replace(/[^\d]/g,'')"/></td>
							<td>
								<a title="修改" href="#" onclick="saveReservationCount()">修改</a>
							</td>
						</tr>
						</div>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>
<head>
	<script>
		function saveReservationCount() {
            var reservationCount = $("#reservationCount").val();
            if(reservationCount == ""){
                return false;
			}
			window.location.href="${pageContext.request.contextPath}/worker/saveReservationCount?count="+reservationCount;
        }
	</script>
<meta name="decorator" content="iframe" />
</head>