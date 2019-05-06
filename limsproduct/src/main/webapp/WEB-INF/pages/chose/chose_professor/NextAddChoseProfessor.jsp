<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />

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
						<div class="title">
							<a
								class="btn btn-new"
								href="${pageContext.request.contextPath }/nwuChose/ChoseThemeList?currpage=1">返回</a>
							<a class="btn btn-new" href="javascript:void(0)"
								onclick="check()">下一步</a>
						</div>
						<form:form name="form"
							action="${pageContext.request.contextPath }/nwuChose/savaThemeChoseOneStep"
							method="post" modelAttribute="choseTheme">
							<div class="new-classroom">
								<fieldset>
									<label>所属届：</label>
									<form:select path="theYear" class="easyui-validatebox" required="true">
										<form:option value="">请选择</form:option>
										<c:forEach items="${attendanceTimeList }" var="curr">
											<form:option value="${curr}">${curr}</form:option>
										</c:forEach>
									</form:select>
								</fieldset>
								<fieldset>
									<label> 最大学生的数量:</label>
									<form:input path="maxStudent" class="easyui-validatebox"
												required="true" />
								</fieldset>
								<fieldset>
									<label>期望开始时间: </label>
										<input class="easyui-datebox"
										required="true" id="expectStartline" name="expectStartline"
										type="text" onclick="new Calendar().show(this);"
										style="width:100px;" />
								</fieldset>
								<fieldset>
									<label> 期望截止时间: </label>
										<input class="easyui-datebox"
										required="true" id="expectDeadline" name="expectDeadline"
										type="text" onclick="new Calendar().show(this);"
										style="width:100px;" />
								</fieldset>
								<form:hidden path="id" />
							</div>
							<div class="moudle_footer">
								<div class="submit_link"></div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 下拉框的js -->
	<script
		src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
		type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		function check() {
			//获得开始和结束时间
			var expectStartline = $('#expectStartline').datebox('getValue');
			var expectDeadline = $('#expectDeadline').datebox('getValue');
			var startTime = new Date(expectStartline);
			var endTime = new Date(expectDeadline);
			if (endTime < startTime) {
				alert("开始时间超过结束时间");
			} else {
				//获得所填的届
				var theYear = $("#theYear").val();
				$.ajax({
					url : "${pageContext.request.contextPath}/nwuChose/checkUncloseSameOutline?theYear="
							+ theYear,
					type : "post",
					success : function(result) {
						if (result == 0) {
							//跳转到关闭和删除大纲的页面
							var flag = confirm("存在同届的大纲，需要手动操作大纲");
							if (flag) {
								window.location.href = "${pageContext.request.contextPath}/nwuChose/ChoseThemeList?currpage=1";
							}
						} else {
							document.form.submit();//进入新建大纲的下一步
						}
					}
				});
			}
		}
	</script>
	<!-- 下拉框的js -->
</body>
</html>
