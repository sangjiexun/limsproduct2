<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf8">
	<title>实验室预约单</title>
	<meta name="decorator" content="timetable"/>
	<meta name="contextPath" content="${pageContext.request.contextPath}"/>
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="ie=edge" />
	<!-- 全局的引用 -->
	<!-- layui多选，首先引入css, 和js, 唯一依赖: jQuery -->
   	<script src="${pageContext.request.contextPath}/js/lims/reservation/lab/optionRule.js"></script>
   	<script src="${pageContext.request.contextPath}/plugins/dateviewer_day/js/bootstrap/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
   	<script src="${pageContext.request.contextPath}/plugins/jsUtils.js" type="text/javascript" charset="utf-8"></script>
   	<%--<script src="${pageContext.request.contextPath}/plugins/jquery-1.11.1.min.js" type="text/javascript" charset="utf-8"></script>--%>
   	<script src="${pageContext.request.contextPath}/plugins/dateviewer_day/js/WdatePicker.js" type="text/javascript" charset="utf-8"></script>
   	<script src="${pageContext.request.contextPath}/plugins/dateviewer_day/js/jquery.mousewheel.min.js" type="text/javascript" charset="utf-8"></script>
   	<script src="${pageContext.request.contextPath}/plugins/dateviewer_day/js/dateLine.js" type="text/javascript" charset="utf-8"></script>
   	<script src="${pageContext.request.contextPath}/plugins/dateviewer_day/js/datecanlendar.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/dateviewer_day/js/bootstrap/css/bootstrap-datetimepicker.min.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/dateviewer_day/css/index.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lims/basic.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/dateviewer_day/css/order.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/dateviewer_day/css/list.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/dateviewer_day/css/new2015.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/dateviewer_day/css/styles.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/dateviewer_day/js/bootstrap/css/bootstrap.min.css" />
	<style type="text/css">
		.layui-breadcrumb .breadcrumb_select {
			color: #409eff!important;
		}
		.layui-form-checkbox {
			height: 24px;
			line-height: 23px;
			margin-right: 10px;
			padding-right: 24px;
		}
		.layui-form-checkbox span {
			padding: 0 10px;
			height: 95%;
			font-size: 12px;
			border-radius: 2px 0 0 2px;
			/*background-color: #d2d2d2;*/
			border: 1px solid #d2d2d2;
			background-color: white;
			color: #989191;
			overflow: hidden;
			white-space: nowrap;
			text-overflow: ellipsis;
		}
		.layui-form-checkbox:hover span {
			background-color: #c2c2c2;
			color: #fff;
		}
		.layui-form-checked span, .layui-form-checked:hover span {
			background-color: #5FB878;
			color: #fff;
		}
		.layui-form-checkbox i {
			width: 24px;
			height: 23px;
		}
		.layui-field-title {
			margin: 10px 0 20px;
			border-width: 0 0 0;
		}
		.layui-btn-sm {
			height: 24px;
			line-height: 23px;
			padding: 0px 7px;
			font-size: 12px;
		}
		@media screen and (min-width: 1100px){
			.layui-col-lg4 {
				width: auto;
			}
		}
	</style>
	<style type="text/css">
		#secSchedule table td,
		#secSchedule table th {
			border: 1px solid #bbb;
		}

		.pub_select div span:first-child {
			display: inline-block;
			width: 95px;
		}

		.orderDiv {
			width: 93%;
			padding: 0;
			margin-top: 20px;
		}

		.timeDiv {
			width: 13%;
		}

		body {
			min-width: 1000px !important;
		}

		.chzn-container-single {
			height: 30px;
			line-height: 30px !important;
		}

		.pub_select div input {
			float: none;
		}

		#notNeedRepair>div {
			width: 100%;
			clear: both;
			padding: 7px 0;
			min-height: 34px;
		}

		.notice p {
			color: #ff5500 !important;
		}
		.dropdown-menu{
			top: 252.8px!important;
		}
	</style>
</head>

<body>
<div class="layui-layout layui-layout-admin">
	<div class="layui-main">
		<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
			<%--<legend>填写预约</legend>--%>
			<span class="layui-breadcrumb breadcrumb_top" lay-separator="|">
				<a href="javascript:void(0);" class="breadcrumb_select section_btn">节次模式</a>
                <a href="javascript:void(0);" class="date_btn">日期模式</a>
                <a href="javascript:void(0);" class="view_btn">图表模式</a>
			</span>
		</fieldset>
		<div id="date_section_form">
		<form class="layui-form" action="" lay-filter="cappointment_tab">
			<input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
			<input type="hidden" id="dateorsection" value="${dateorsection}" />
			<input type="hidden" id="labRoomId" value="${labRoomId}" />

			<div class="layui-row layui-col-space10 layui-form-item">
				<div class="layui-col-lg4">
					<label class="layui-form-label">当前学期：</label>
					<div class="layui-input-block">
						<input style="width: 220px" id="term" type="text" name="term" placeholder="" autocomplete="off" class="layui-input layui-btn-disabled" disabled="true" readOnly="true">
					</div>
				</div>
				<div class="layui-col-lg4 day_item">
					<label class="layui-form-label">选择星期：</label>
					<div class="layui-input-block">
						<select name="weekday" id="weekday" lay-filter="weekday" lay-search>
						</select>
					</div>
				</div>
				<div class="layui-col-lg4 date_item">
					<label class="layui-form-label">日期：</label>
					<div class="layui-input-block">
						<input type="text" class="layui-input test-item" id="date-range"  autocomplete="off" placeholder="请选择日期范围">
					</div>
				</div>
			</div>

			<%--<div class="layui-row layui-col-space10 layui-form-item week_item" id="week_div" style="display:none;">--%>
			<div class="layui-row layui-col-space10 layui-form-item week_item" id="week_div">
				<div class="layui-col-lg12">
					<label class="layui-form-label">选择周次：</label>
					<div class="layui-input-block">
						<div class="layui-form">
							<div class="layui-btn layui-btn-sm" id="week_all">全选</div>
							<div class="layui-btn layui-btn-sm" id="week_opposite">反选</div>
							<div class="layui-btn layui-btn-sm layui-btn-danger" id="week_none">全不选</div>
							<b>（注意：请先选择周次，周次确定后再选节次；选择周次过程中，节次选择会被重置！）</b>
						</div>
						<div id="week_box" class="layui-form" style="margin:10px auto 0">

						</div>
					</div>
				</div>
			</div>
			<div class="layui-row layui-col-space10 layui-form-item section_item">
				<div class="layui-col-lg12">
					<label class="layui-form-label">选择节次：</label>
					<div class="layui-input-block">
						<div class="layui-form" style="color:red">
							<div class="layui-btn layui-btn-sm" id="section_all">全选</div>
							<div class="layui-btn layui-btn-sm" id="section_opposite">反选</div>
							<div class="layui-btn layui-btn-sm layui-btn-danger" id="section_none">全不选</div>

						</div>
						<div id="section_box" class="layui-form" style="margin:10px auto 0">
						</div>
					</div>
				</div>
			</div>

			<div class="layui-row layui-col-space10 layui-form-item">
				<%--<div class="layui-col-lg4 date_item">--%>
					<%--<label class="layui-form-label">日期：</label>--%>
					<%--<div class="layui-input-block">--%>
							<%--<input type="text" class="layui-input test-item" id="date-range"  autocomplete="off" placeholder="请选择日期范围">--%>
					<%--</div>--%>
				<%--</div>--%>
				<div class="layui-col-lg12 time_item">
					<label class="layui-form-label">时间：</label>
					<div class="layui-input-block">
						<div class="layui-form">
							<div class="layui-btn layui-btn-sm" id="time_all">全选</div>
							<div class="layui-btn layui-btn-sm" id="time_opposite">反选</div>
							<div class="layui-btn layui-btn-sm layui-btn-danger" id="time_none">全不选</div>
						</div>
							<%--<input type="text" class="layui-input" id="time-range" autocomplete="off" placeholder="请选择时间范围">--%>
								<div id="time_box" class="layui-form" style="margin:10px auto 0">
								</div>
					</div>
				</div>

			</div>
			<div class="layui-row layui-col-space10 layui-form-item">
				<%--<div class="layui-col-lg4">--%>
					<%--<label class="layui-form-label">教学设备：</label>--%>
					<%--<div class="layui-input-block">--%>
						<%--<input type="checkbox" name="outside" lay-skin="switch" id="outside" lay-filter="outside-sw" lay-text="允许对外开放|不允许对外开放">--%>
						<%--<!--<input type="radio" name="" value="" title="允许对外开放">--%>
						<%--<input type="radio" name="" value="" title="不允许对外开放" checked>-->--%>
					<%--</div>--%>
				<%--</div>--%>
				<div class="layui-col-lg4">
					<label class="layui-form-label">活动类别：</label>
					<div class="layui-input-block">
						<select id="activity" name="activity" lay-verify="required" lay-filter="activity" lay-search>
						</select>
					</div>
				</div>
				<div class="layui-col-lg4">
					<label class="layui-form-label">活动对象：</label>
					<div class="layui-input-block">
						<select id="userType" name="userType" lay-verify="required" lay-filter="activity" lay-search>
						</select>
					</div>
				</div>
					<div class="layui-col-lg4">
						<label class="layui-form-label">活动人数：</label>
						<div class="layui-input-block" id="number_select" style="display: block">
							<select id="activitynumber_select" lay-filter="activitynumber" lay-search>
								<option value="1-5">1-5</option>
								<option value="6-10">6-10</option>
								<option value="11-30">11-30</option>
							</select>
						</div>
						<div class="layui-input-block" id="number_input" style="display: none">
							<input type="number" id="activitynumber_input" placeholder="请填写活动人数" autocomplete="off" class="layui-input">
						</div>
						<a onclick="changeTypeNum()">切换填写模式(区间/精确)</a>
						<input type="hidden" name="activitynumber"/>
					</div>
			</div>
			<div class="layui-row layui-col-space10 layui-form-item">
				<div class="layui-col-lg4">
					<label class="layui-form-label">教学设备：</label>
					<div class="layui-input-block">
						<input type="checkbox" name="outside" lay-skin="switch" id="outside" lay-filter="outside-sw" lay-text="允许对外开放|不允许对外开放">
						<!--<input type="radio" name="" value="" title="允许对外开放">
						<input type="radio" name="" value="" title="不允许对外开放" checked>-->
					</div>
				</div>
				<div class="layui-col-lg4">
					<label class="layui-form-label">联系方式：</label>
					<div class="layui-input-block">
						<input type="tel" name="telephone" id="telephone" lay-verify="required" placeholder="请填写联系方式" autocomplete="off" class="layui-input">
					</div>
				</div>
				<%--<div class="layui-col-lg4">--%>
					<%--<label class="layui-form-label">活动人数：</label>--%>
					<%--<div class="layui-input-block">--%>
						<%--<input type="number" name="activitynumber" lay-verify="required" placeholder="请填写活动人数" autocomplete="off" class="layui-input">--%>
					<%--</div>--%>
				<%--</div>--%>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">预约原因：</label>
				<div class="layui-input-block">
					<textarea name="reason" id="reason" lay-verify="required" placeholder="请填写预约内容" class="layui-textarea"></textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">内容：</label>
				<div class="layui-input-block">
					<textarea name="content" id="content"  placeholder="请填写备注" class="layui-textarea"></textarea>
				</div>
			</div>


			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit lay-filter="reservationSubmit">立即提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
	</div>
		<div id="view_reservation">
			<div class="app_main">
				<div class="app_header">
					<%--<div class="app_title">--%>
						<%--工位预约--%>
					<%--</div>--%>
					<div class="describe_title">
						202xxxx实验室，共100个工位
					</div>
					<div class="app_describe">
						<div class="describe_img">
							<img src="https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1184428518,159584777&fm=58&bpow=1024&bpoh=680">
						</div>
						<div class="describe">
							<div>注意事项:</div>
							<div>这段描述很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长</div>
						</div>
					</div>
				</div>
				<!-- 时间跨度默认1.0小时 可设置0.5小时 -->
				<input type="hidden" id="timeLineInterval" value="1.0" />
				<input id="usernames" name="usernames" type="hidden" value="admin" />
				<div class=" mt20">
					<div class=" mt20  w100p pb20 pt2">
						<div class="user_info w98p mlra mb20 f14">
							<div>
								<div class="content-box" style="width:100%;margin-bottom:10px;">
									<div class="mt20 cf">
										<div class="w74p l calendar_box" style="width:70%;">
											<div class="iStyle_DateConteiner">

												<!--	<div class="iStyle_Years"></div>-->
												<div id="flightlistnav" style="float:left; width:91%;">
													<ul id="lowlistnav" class="navcols_ul clx" style="width:100%;">
														<li class="navcols_on"></li>

														<!-- 左箭头-->
														<li id="prev" method="prevweek" class="navcols firstcols" style="width:5.5%;">
															<div class="f_l_na f_l_na_l" style="display:block;">
																<em class="enable"></em>
															</div>
														</li>
														<!-- 显示从今天开始的七天日期-->
														<li class="navcols">
															<a rel="nofollow" style="text-decoration: none">
																<div method="seachflightbyday" class="f_l_na" style="width:100%;">
																</div>
															</a>
														</li>
														<li class="navcols">
															<a rel="nofollow" style="text-decoration: none">
																<div method="seachflightbyday" class="f_l_na" style="width:100%;">
																</div>
															</a>
														</li>
														<li class="navcols">
															<a rel="nofollow" style="text-decoration: none">
																<div method="seachflightbyday" class="f_l_na" style="width:100%;">
																</div>
															</a>
														</li>
														<li class="navcols">
															<a rel="nofollow" style="text-decoration: none">
																<div method="seachflightbyday" class="f_l_na" style="width:100%;">
																</div>
															</a>
														</li>
														<li class="navcols">
															<a rel="nofollow" style="text-decoration: none">
																<div method="seachflightbyday" class="f_l_na" style="width:100%;">
																</div>
															</a>
														</li>
														<li class="navcols">
															<a rel="nofollow" style="text-decoration: none">
																<div method="seachflightbyday" class="f_l_na" style="width:100%;">
																</div>
															</a>
														</li>
														<li class="navcols">
															<a rel="nofollow" style="text-decoration: none">
																<div method="seachflightbyday" class="f_l_na" style="width:100%;">
																</div>
															</a>
														</li>
														<!--右箭头-->
														<li id="next" method="nextweek" style="width:5.5%;">
															<div class="f_l_na f_l_na_r" style="display:block;">
																<em class="enable"></em>
															</div>
														</li>
													</ul>
												</div>

												<!-- 日历图标显示-->
												<div class="input-group date form_date col-md-3" style="width:30px" data-date="" data-date-format="dd MM yyyy"
													 data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
													<input id="#dateInput" style="width:auto;display:none;" class="form-control" size="16" type="text" value=""
														   readonly="readonly" />
													<span id="dateChose" style="padding:9px 12px!important;width:auto;border-left:1px solid #ccc;border-bottom-left-radius: 4px;border-top-left-radius:4px;"
														  class="input-group-addon">
													<span class="glyphicon glyphicon-calendar"></span>
												</span>
												</div>
											</div>
											<!--预约框-->
											<div class="orderDiv cf" id="orderDiv">
											</div>
										</div>

										<form action="" method="post" name="searchForm" id="searchForm" style="width: 30%;float: right;">
											<div class="w35p r reservation_box">
												<div class="public_message">
													<div class="pub_select">
														<input type="hidden" name="configId" id="configId" value="" />
														<div class="sample">
															<input type="hidden" value="" id="labRoomAdminUsername" name="labRoomAdminUsername" />
															<input type="hidden" id="authority" name="authority" value="" />
														</div>
														<div>
															<span class="pr5">预计时间:</span>
															<input id="searchDate" name="searchDate" value="2019-06-18" />
														</div>
														<div>
															<span class="pr5">预计时长:</span>
															<span id="duration">0小时</span>
															<input type="hidden" value="0" id="durationHours" />
														</div>
														<div id="time_list" style="display:none;">
														</div>
														<div>
															<span class="pr5">预约人:</span>
															<span>admin</span>
															<input value="" type="hidden" />
														</div>
														<div>
															<span class="pr5">说明:</span>
															<textarea placeholder="说明:" style="width:100%;"></textarea>
														</div>
													</div>
													<div class="w100p ovh">
														<button class="r mt5 mb10 mr5p search cancel" type="button">取消</button>
														<button class="r mt5 mb10 mr5p search" type="button">提交</button>
													</div>
												</div>
											</div>
										</form>
										<script src="${pageContext.request.contextPath}/plugins/dateviewer_day/js/orderDiv.js" type="text/javascript" charset="utf-8"></script>
									</div>
								</div>

							</div>


						</div>
					</div>
				</div>
				</div>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/lims/reservation/lab/newLabReservation.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/plugins/dateviewer_day/js/bootstrap/js/bootstrap.min.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/plugins/dateviewer_day/js/bootstrap/js/bootstrap-datetimepicker.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/plugins/dateviewer_day/js/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/lims/reservation/lab/doInstrumentMachineApp.js" charset="utf-8"></script>
</body>
</html>