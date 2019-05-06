<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf8">
		<title>实验室预约列表</title>
		<meta name="decorator" content="timetable"/>
		<meta name="contextPath" content="${pageContext.request.contextPath}"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
		<style>
			.layui-table-cell:first-child {
				height: auto;
			}
			.layui-table-tool{
				width: auto;
				padding: 0;
				position: absolute;
				right: 25px;
				top: 112px;
			}
		</style>
	</head>

	<body>
	<div class="iStyle_RightInner">

		<div class="navigation">
			<div id="navigation">
				<ul>
					<li class="end"><a href="javascript:void(0)">微服务实验室预约</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
		<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
			<ul class="layui-tab-title">
				<li>
					<a href="${pageContext.request.contextPath}/lims/reservation/lab/labRoomList">微服务实验室预约</a>
				</li>
				<li class="layui-this"><a href="${pageContext.request.contextPath}/lims/reservation/lab/labReservationList">我的预约</a></li>
				<li><a href="${pageContext.request.contextPath}/lims/reservation/lab/labReservationAuditList">我的审核</a></li>
            </ul>
		</div>

		<div class="layui-layout layui-layout-admin">
			<%--<div class="layui-main">--%>
			<div>
				<input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
                <input type="hidden" id="username" value="${username}" />
				<div class="tool-box">
					<table>
						<tr>
							<td>
								<span class="fl">综合查询：</span>
								<input id="search" name="search" type="text"  placeholder="多字段查询"/>
							</td>
						</tr>
					</table>
				</div>
				<table class="layui-hide" id="LAY_table_user" lay-filter="LAY_table_user"></table>
				<script type="text/html" id="parentbar">
					<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
					<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="reverberation">预约</a>
				</script>
			</div>
		</div>
				</div>
			</div>
		</div>
	</div>
					<script type="text/html" id="toolbarDemo">
			<div class="layui-btn-container">
				<button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
				<button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
				<button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
			</div>
		</script>
		<script type="text/html" id="indexTpl">
			{{d.LAY_TABLE_INDEX+1}}
		</script>

		<script src="${pageContext.request.contextPath}/js/lims/reservation/lab/labReservationList.js" charset="utf-8"></script>
		<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
	</body>

</html>