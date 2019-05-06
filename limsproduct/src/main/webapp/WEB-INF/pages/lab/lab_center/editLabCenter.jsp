 <%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
 <jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  
  <script type="text/javascript">
  //提交表单
  function submitForm(){
    if($.trim($("#centerNumber").val())=="")
    {
      alert("请填写实验中心编号！");
      return false;
    }
    if($.trim($("#centerName").val())=="")
    {
      alert("请填写实验中心名称！");
      return false;
    }
    if($("#user").val()=="")
    {
      alert("请填写中心主任！");
      return false;
    }
    if($("#campus").val()=="")
    {
      alert("请填写所属校区！");
      return false;
    }
    if($("#academy").val()=="")
    {
      alert("请填写所属学院！");
      return false;
    }
    if($("#building").val()=="")
    {
      alert("请填写所属楼宇！");
      return false;
    }
    document.center_form.action="${pageContext.request.contextPath}/labCenter/saveLabCenter?page="+${page};
    document.center_form.submit();
  }
  </script>
	<style>
		/*.submit_link input,*/
		/*.submit_link #save{*/
			/*margin:0 10px 0 0;*/
		/*}*/
		.validatebox-tip {
			margin: -23px 0px 0 -118px!important;
			width: 120px!important;
		}
		.tab_lab th{
			width:15%;
		}
		.tab_lab td{
			width:35%;
		}
	</style>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management" />
			<li><a href="javascript:void(0)"><spring:message code="left.trainingCenter.management"/></a></li>
			<li class="end"><a href="javascript:void(0)">新建</a></li>
			
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title">新建实验中心</div>
	</div>
	<form:form name="center_form" method="POST" modelAttribute="labCenter">
		<table class="tab_lab" style="margin:10px 0;">
			<tr>
				<th>实验中心名称</th>
				<td colspan="3">
					<form:input path="centerName" class="easyui-validatebox" required="true"/>
				</td>
			</tr>
			<tr>
				<th><form:hidden path="id"/>实验中心编号</th>
				<td>
					<form:input path="centerNumber" class="easyui-validatebox" required="true"/>
				</td>
				<th>中心主任</th>
				<td>
					<form:select id="user" path="userByCenterManager.username" class="chzn-select">
					<form:option value="">请选择</form:option>
					<c:forEach items="${listUser}" var="curr">
					<form:option value="${curr.username}">${curr.cname}[${curr.username}]</form:option>
					</c:forEach>
					</form:select>
				</td>
			</tr>
			<tr>
				<th>所属校区</th>
				<td>
					<form:select id="campus" path="systemCampus.campusNumber" class="chzn-select">
					<form:option value="">请选择</form:option>
						<form:options items="${listSystemCampus}" itemLabel="campusName" itemValue="campusNumber"/>
					</form:select>
				</td>
				<th>所属学院</th>
				<td>
					<form:select id="academy" path="schoolAcademy.academyNumber" class="chzn-select">
					<form:option value="">请选择</form:option>
						<form:options items="${listSchoolAdademy}" itemLabel="academyName" itemValue="academyNumber"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<th>所属楼宇</th>
				<td>
					<form:select id="building" path="systemBuild.buildNumber" class="chzn-select">
						<form:option value="">请选择</form:option>
						<form:options items="${listSystemBuild}" itemLabel="buildName" itemValue="buildNumber"/>
					</form:select>
				</td>
				<th>是否开放</th>
				<td>
					<form:select id="building" path="status" class="chzn-select">
						<form:option value="0">否</form:option>
						<form:option value="1">是</form:option>
					</form:select>
				</td>
			</tr>
		</table>
	<div class="moudle_footer">
        <div class="submit_link" style="margin:5px;">
			<input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)"/>
          <input class="btn" id="save" type="button" onclick="submitForm();" value="确定"/>
        </div>
	</div>
	</form:form>

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
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
