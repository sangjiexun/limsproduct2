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
		var activeStarttime = document.getElementById("activeS");
		var activeFinishtime = document.getElementById("activeF");
		var deadLine = document.getElementById("deadL");
		if(schoolTerm.value == ""){
			alert("学期不能为空！");
			schoolTerm.focus();
			return false;
		}
		//else if(activeStarttime.value == ""){
		//	alert("开始时间不能为空！");
		//	activeStarttime.focus();
		//	return false;
		//}
		//else if(activeFinishtime.value == ""){
		//	alert("结束时间不能为空！");
		//	activeFinishtime.focus();
		//	return false;
		//}
		//else if(deadLine.value == ""){
		//	alert("截止时间不能为空！");
		//	deadLine.focus();
		//	return false;
		//}
		else return true;
	}
</script>

</head>
  
<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.applicationOfCourseArrangement.setting"/></a>
				</li>
				<li class="end"><a href="javascript:void(0)">排课申请时间设置</a>
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
							<li>排课申请的时间列表</li>
							</div>
						</div>
	<!-- 表单开始 -->
	<form:form onsubmit="return check();" action="${pageContext.request.contextPath}/timetable/saveTimetableSetting" method="POST" modelAttribute="schoolTermActive">
    <div class="new-classroom">
      <li>
        <label>学期<font color="red">*</font>：</label>
        <form:select id="schoolTerm" path="schoolTerm.id" class="chzn-select">
  			<form:option value="">请选择</form:option>
  			<c:forEach items="${schoolTerms}" var="curr">
  			<form:option value="${curr.id}">${curr.termName}</form:option>
  			</c:forEach>
  		</form:select>
      </li>
      <br/>
      <li>
        <label>开始时间<font color="red">*</font>：</label>
        <input class="easyui-datebox"  id="activeS" name="activeStarttime" onclick="new Calendar().show(this);"  readonly="readonly"/>
      </li>
      <br/>
      <li>
        <label>结束时间<font color="red">*</font>：</label>
        <input class="easyui-datebox"  id="activeF" name="activeFinishtime" onclick="new Calendar().show(this);"  readonly="readonly"/>
      </li>
      <br/>
      <li>
        <label>截止时间<font color="red">*</font>：</label>
        <input class="easyui-datebox"  id="deadL" name="deadLine" onclick="new Calendar().show(this);"  readonly="readonly"/>
      </li>
	</div>
    <div class="submit_link">
          <input class="btn" id="save" type="submit" value="提交">
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
