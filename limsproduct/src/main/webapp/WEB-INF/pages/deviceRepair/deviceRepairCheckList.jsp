<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
	<meta name="decorator" content="iframe"/>
	<meta name="contextPath" content="${pageContext.request.contextPath}"/>
	<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
	<link type="text/css" rel="stylesheet"
		  href="${pageContext.request.contextPath}/bootstrap/css/plugins/bootstrap-table/bootstrap-table.min.css"/>
	<!-- 全局的引用 -->
	<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式 -->	
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />

	<!-- bootstrap的引用 -->
	<link href="${pageContext.request.contextPath}/bootstrap/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" >
	<script src="${pageContext.request.contextPath}/bootstrap/bootstrap-select/js/bootstrap-select.js"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/lims/device/deviceRepair/myDeviceRepairApplyList.js"
			type="text/javascript"></script>
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
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<style>
.tool-box td span {
    white-space: nowrap;
}
.tool-box input[type=text]{
	width:100px!important;
}
.combo{
    margin: 0;
    position: relative;
    top: 2px;
    overflow: hidden;
}
.tb_btm{
    text-align:left;
}
.tb_btm input{
    float:none;
}
</style>
</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<ul>
							<li><a href="javascript:void(0)">实验设备管理</a></li>
							<li class="end"><a href="javascript:void(0)">设备报修列表</a></li>
						</ul>
					</ul>
				</div>
			</div>
			<sec:authorize ifNotGranted="ROLE_STUDENT">
<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
			<c:if test="${sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR' && proj_name eq 'ycitlims'}">
				<li class="TabbedPanelsTab" id="s1">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/allStandardDeviceList">设备维修</a>
				</li>
				<li class="TabbedPanelsTab" id="s2">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/viewMyDeviceRepairApply">我的设备维修申请</a>
				</li>
			</c:if>
			<c:if test="${proj_name ne 'ycitlims'}">
				<li class="TabbedPanelsTab" id="s1">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/allStandardDeviceList">设备维修</a>
				</li>
				<li class="TabbedPanelsTab" id="s2">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/viewMyDeviceRepairApply">我的设备维修申请</a>
				</li>
			</c:if>
			<li class="TabbedPanelsTab selected" id="s3">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/deviceRepairCheckList">我的设备维修审核</a>
			</li>
			<li class="TabbedPanelsTab" id="s4">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/deviceRepairConfirmList">我的设备维修确认</a>
			</li>
			<li class="TabbedPanelsTab" id="s5">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/deviceRepairViewList">所有设备维修查看</a>
			</li>
		</ul>
	</div></sec:authorize>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="sub_tit">
		<li <c:if test="${auditStage eq 1}">class="st_selected" </c:if> tabindex="0">
		<a href="${pageContext.request.contextPath}/deviceRepair/deviceRepairCheckList">待审核</a>
		</li>
		<li <c:if test="${auditStage eq 2}">class="st_selected" </c:if> tabindex="0">
		<a href="${pageContext.request.contextPath}/deviceRepair/passDeviceRepairList">审核通过</a>
		</li>
		<li <c:if test="${auditStage eq 3}">class="st_selected" </c:if> tabindex="0">
		<a href="${pageContext.request.contextPath}/deviceRepair/rejectedDeviceRepairList">审核拒绝</a>
		</li>
		
	</ul>
	<div class="TabbedPanelsContentGroup">
    <div class="TabbedPanelsContent">
		<input id="auditStage" value="${auditStage}" type="hidden"/>
		<div class="content-box">
			<div id="TabbedPanels1" class="TabbedPanels">
				<div class="site-box">
					<div class="site-content">
						<div>
							<div id="toolbar">
								<form class="form-inline">
									<div class="form-group">
										<label class="sr-only" for="msg_type">综合查询</label>
										<div class="input-group">
											<div class="input-group-addon">综合查询</div>
											<input type="text" id="search" name="search" value="" placeholder="多字段查询">
										</div>
									</div>
								</form>
							</div>
							<table id="table_list" class="table table-bordered table-striped with-check" style="word-break:break-all; word-wrap:break-all;"></table>
						</div>
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


