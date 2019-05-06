<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<meta name="decorator" content="iframe"/>
	<!-- 下拉的样式 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
	<!-- 下拉的样式结束 -->

	<script langauge="javascript">
        //如果为查询则提交查询页面，如果为电子表格导出，则导出excel
        function subform(gourl){
            var gourl;
            form.action=gourl;
            form.submit();
        }
	</script>
	<script type="text/javascript">
        // 基表4 弹出框
        function exportTxt(type){
            $("#exportTxt").show();
            $("#type_txt").val(type);
            $("#exportTxt").window('open');
        }

        function targetUrl() {
            // 获取基表编号
			var type = $("#type_txt").val();
            // 导出TXT
			if(type==11) {
                document.txt_form.action="${pageContext.request.contextPath}/basic_data/exportReportTxt";
			}else if(type==21) {
				document.txt_form.action="${pageContext.request.contextPath}/basic_data/exportSchoolDeviceChangeTxt";
			}else if(type==31) {
				document.txt_form.action="${pageContext.request.contextPath}/basic_data/exportSchoolDeviceValueTxt";
			}else if(type==41) {
			    document.txt_form.action="${pageContext.request.contextPath}/dataReport/reportOperationItemTxt";
			}else if(type==12) {// 导出Excel
                document.txt_form.action="${pageContext.request.contextPath}/basic_data/exportReport";
            }else if(type==22) {
                document.txt_form.action="${pageContext.request.contextPath}/basic_data/exportSchoolDeviceChange";
            }else if(type==32) {
                document.txt_form.action="${pageContext.request.contextPath}/basic_data/exportSchoolDeviceValue";
            }else if(type==42) {
                document.txt_form.action="${pageContext.request.contextPath}/dataReport/reportProjectSummaryExcel";
            }
            document.txt_form.submit();
            // 关闭弹出框
            $("#exportTxt").window('close', true)
        }
	</script>

	<script language="javascript" src="${pageContext.request.contextPath}/js/building/buildingListDetail.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery/js/jquery.jsonSuggest-2.js" ></script>
</head>
<body>
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">系统报表</a></li>
			<li class="end"><a href="javascript:void(0)">实验室信息统计基表</a></li>
		</ul>
	</div>
</div>

<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<div class="tool-box">
						<table>
							<tbody>
							<tr>
								<td class="label" valign="middle" width="50%">基表一：教学科研仪器设备表</td>
								<td>
									<a onclick="exportTxt(11);">导出txt</a>
									<a onclick="exportTxt(12);">导出Excel</a>
									<%--<a href="${pageContext.request.contextPath}/basic_data/schoolDeviceReport?currpage=1">查看</a>--%>
								</td>
							</tr>
							<tr>
								<td class="label" valign="middle" width="50%">基表二：教学科研仪器设备增减变动情况表</td>
								<td>
									<a onclick="exportTxt(21);">导出txt</a>
									<a onclick="exportTxt(22);">导出Excel</a>
									<%--<a href="${pageContext.request.contextPath}/basic_data/schoolDeviceChange">查看</a>--%>
								</td>
							</tr>
							<tr>
								<td class="label" valign="middle" width="50%">基表三：贵重仪器设备表</td>
								<td>
									<a onclick="exportTxt(31);">导出txt</a>
									<a onclick="exportTxt(32);">导出Excel</a>
									<%--<a href="${pageContext.request.contextPath}/basic_data/schoolDeviceValue?currpage=1">查看</a>--%>
								</td>
							</tr>
							<tr>
								<td class="label" valign="middle" width="50%">基表四：教学实验项目表 </td>
								<td>
									<a onclick="exportTxt(41);">导出txt</a>
									<a onclick="exportTxt(42);">导出Excel</a>
								</td>
							</tr>
							<tr>
								<td class="label" valign="middle" width="50%">基表五：专任实验室人员表 </td>
								<td>
									<a href="${pageContext.request.contextPath}/dataReport/reportLabWorkerTxt">导出txt</a>
									<a href="${pageContext.request.contextPath}/dataReport/reportLabWorkerExcel">导出Excel</a>
								</td>
							</tr>
							<tr>
								<td class="label" valign="middle" width="50%">基表六：实验室基本情况表</td>
								<td>
									<a href="${pageContext.request.contextPath}/basic_data/exportLabBasicTxt">导出txt</a>
									<a href="${pageContext.request.contextPath}/basic_data/exportLabBasic">导出Excel</a>
									<%--<a href="${pageContext.request.contextPath}/basic_data/labBasic?currpage=1">查看</a>--%>
								</td>
							</tr>
							<tr>
								<td class="label" valign="middle" width="50%">基表七：实验室经费情况表</td>
								<td>
									<a href="${pageContext.request.contextPath}/basic_data/exportLabRoomReportFundTxt">导出txt</a>
									<a href="${pageContext.request.contextPath}/basic_data/exportLabRoomReportFund">导出Excel</a>
									<%--<a href="${pageContext.request.contextPath}/basic_data/labRoomReportFund?currpage=1">查看</a>--%>
								</td>
							</tr>
							</tbody>
						</table>
					</div>
					</table>
				</div>

				<!-- 弹窗 -->
				<div id="exportTxt" class="easyui-window " title="请选择学年" align="left" title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 400px; height: 100px;">
					<div class="content-box">
						<form name="txt_form" action="" method="post">
						<table class="tb" id="my_show">
							<ul>
								<li>
									<input name="type_txt" id="type_txt" type="hidden" />
									<select name="yearCodeForTxt" id="yearCodeForTxt"  style="width:200px">
										<c:forEach items="${yearCodes}" var="item">
											<c:if test="${item.key eq yearCode}">
												<option value="${item.key }" selected="selected">${item.value}</option>
											</c:if>
											<c:if test="${item.key ne yearCode}">
												<option value="${item.key }">${item.value}</option>
											</c:if>
										</c:forEach>
									</select>
									<a class="btn btn-new" href="javascript:void(0);" onclick="targetUrl()">导出</a>
								</li>
							</ul>
						</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>

</body>
</html>
