<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="decorator" content="iframe"/>
<!-- 下拉框的样式 -->
	<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->

<script type="text/javascript">
	function check(){
		var schoolTerm = document.getElementById("schoolTerm");
		var dayNum = document.getElementById("dayNum");
		var process = document.getElementById("process");
		if(schoolTerm.value == ""){
			alert("学期不能为空！");
			schoolTerm.focus();
			return false;
		}else if(dayNum.value == ""){
			alert("提前天数不能为空！");
			dayNum.focus();
			return false;
		}else if(process.value == ""){
			alert("流程不能为空！");
			process.focus();
			return false;
		}
		return true;
	}
</script>

</head>
<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.applicationSubmittedTime.setting"/></a>
				</li>
				<li class="end"><a href="javascript:void(0)">新建申请递交时间</a>
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
							<div id="title">
							<li>申请递交时间设置</li>
							</div>
						</div>
	<!-- 表单开始 -->
	<form:form id="my_form"  onsubmit ="return check()" action="${pageContext.request.contextPath}/system/saveApplySetting" method="POST" modelAttribute="systemPreDay">
    <div class="new-classroom" style="width: 90%">
      <tr>
        <th>学期<font color="red">*</font>：</th>
        <td>
        <form:select id="schoolTerm" path="schoolTerm.id" class="chzn-select">
  			<form:option value="">请选择</form:option>
  			<c:forEach items="${schoolTerms}" var="curr">
  			<form:option value="${curr.id}">${curr.termName}</form:option>
  			</c:forEach>
  		</form:select>
  		</td>
      </tr>
      &nbsp;&nbsp;&nbsp;
      <tr>
      <th>提前递交天数<font color="red">*</font>：</th>
      <td>
      	<form:input class="easyui-validatebox" id="dayNum" path="dayNum"/></td>
      	</tr>
      <tr>
      &nbsp;&nbsp;&nbsp;
      	<th>流程<font color="red">*</font>：</th>
        <td>
        <form:select id="process" path="process" class="chzn-select">
  			<form:option value="">请选择</form:option>
  			<form:option value='0'><spring:message code="all.trainingRoom.labroom" />预约</form:option>
  			<form:option value='1'>设备预约</form:option>
  			<form:option value='2'>软件安装申请</form:option>
  			<form:option value='3'><spring:message code="all.trainingRoom.labroom" />借用</form:option>
  			<form:option value='4'>设备借用</form:option>
  		</form:select>
  		</td>
      </tr>
	</div>
    <div class="submit_link">
          <input class="btn" id="save" type="submit" value="提交" />
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
    </div>
    </form:form>
					</div>
  				</div>
  			</div>
  		</div>
  	</div>
</body>
</html>
