<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="iframe" />

<!-- 下拉框的样式 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
</head>
<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)">导师互选</a>
				</li>
				<li class="end"><a href="javascript:void(0)">备选导师列表</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="content-box">
						<div class="title">新建</div>
						时间已过或没到
						<!-- 下拉框的js -->
						<script
							src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
							type="text/javascript"></script>
						<script
							src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
							type="text/javascript" charset="utf-8"></script>
						<script type="text/javascript">
						</script>
						<!-- 下拉框的js -->
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>