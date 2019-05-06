<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<meta name="decorator" content="iframe" />
	<script langauge="javascript">
        function subform(gourl){
            form.action=gourl;
            form.submit();
        }
	</script>
</head>
<body>
<!--消息内容弹出框-->
<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<div class="title">
						<div id="title">所有设备列表</div>
					</div>
					<div class="tool-box" style="display:none;">
						<form:form id="form_act" name="form" action="${pageContext.request.contextPath}/labCenter/showLabCenterDeviceDetail?labCenterId=${labCenterId}&currpage=1 " method="post">
							<table >
								<tbody>
								</tbody>
							</table>
						</form:form>
					</div>
					<div id="contentarea">
						<div id="content">
							<div class="content-box" style="border:none;margin:0;">
								<table>
									<thead>
									<tr>
										<th>序号</th>
										<th>设备名</th>
										<th>仪器型号</th>
										<th>所属实验室</th>
									</tr>
									</thead>
									<tbody>
									<c:forEach items="${labCenterDevice}" var="current" varStatus="i">
										<tr>
											<td>${i.count}</td>
											<td>${current.schoolDevice.deviceName}</td>
											<td>${current.schoolDevice.devicePattern}</td>
											<td>${current.labRoom.labRoomName}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<div class="page">
									共${totalRecords}&nbsp;条记录 总页数${pageModel.totalPage}&nbsp; <input type="hidden" value="${pageModel.totalPage}" id="totalpage" />
									<a onclick="$('#form_act').attr('action','showLabCenterDeviceDetail?labCenterId=${labCenterId}&currpage=1').submit();">首页</a>
									<a onclick="$('#form_act').attr('action','showLabCenterDeviceDetail?labCenterId=${labCenterId}&currpage=${pageModel.previousPage}').submit();">上一页</a>
									<a onclick="$('#form_act').attr('action','showLabCenterDeviceDetail?labCenterId=${labCenterId}&currpage=${pageModel.nextPage}').submit();" >下一页</a>
									<a onclick="$('#form_act').attr('action','showLabCenterDeviceDetail?labCenterId=${labCenterId}&currpage=${pageModel.lastPage}').submit();">末页</a>
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
        '.chzn-select'      : {search_contains:true},
        '.chzn-select-deselect' : {allow_single_deselect:true},
        '.chzn-select-no-single' : {disable_search_threshold:10},
        '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
        '.chzn-select-width'   : {width:"95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>