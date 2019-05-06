<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<jsp:useBean id="pConfig" class="net.zjcclims.web.common.PConfig"></jsp:useBean>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
	<meta name="decorator" content="iframe"/>
	<meta name="contextPath" content="${pageContext.request.contextPath}"/>
	<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
	<link type="text/css" rel="stylesheet"
		  href="${pageContext.request.contextPath}/bootstrap/css/plugins/bootstrap-table/bootstrap-table.min.css"/>
	<!-- 全局的引用 -->
	<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 弹出框插件的引用 -->
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
			type="text/javascript"></script>
	<!-- bootstrap的引用 -->
	<link href="${pageContext.request.contextPath}/bootstrap/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" >
	<script src="${pageContext.request.contextPath}/bootstrap/bootstrap-select/js/bootstrap-select.js"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/lims/forms/dataBase/getTest.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
	<style>
		.fixed-table-container thead th .sortable{
			background-image:url('${pageContext.request.contextPath}/images/sort.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:30px
		}
		.fixed-table-container thead th .asc{
			background-image:url('${pageContext.request.contextPath}/images/sort_asc.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:10px
		}
		.fixed-table-container thead th .desc{
			background-image:url('${pageContext.request.contextPath}/images/sort_dsc.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:10px
		}
	</style>

</head>

<body>
<div class="iStyle_RightInner">
	<div id="TabbedPanels1" class="TabbedPanels">
		<div class="site-box">
			<div class="site-content">
				<div>
					<div id="toolbar">
						<form class="form-inline">
							<input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
							<div class="form-group">
								<label class="sr-only" for="product_line">选择学期</label>
								<div class="input-group">
									<div class="input-group-addon">选择学期</div>
									<input type="hidden" id="termId" value="${termId}" />
									<input type="hidden" id="businessType" value="${businessType}" />
									<select id="term" name="term" class="form-control">
										<c:forEach items="${schoolTerms}" var="current">
											<c:if test="${current.id == termId}">
												<option value="${current.id}" selected>${current.termName} </option>
											</c:if>
											<c:if test="${current.id != termId}">
												<option value="${current.id}">${current.termName} </option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="sr-only" for="msg_type">综合查询</label>
								<div class="input-group">
									<div class="input-group-addon">综合查询</div>
									<input type="text" id="search" name="search" value="" placeholder="多字段查询">
								</div>
							</div>
						</form>
					</div>
					<table id="table_list" style="text-align: left;"></table>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>

