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
</head>
  
<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)">系统管理</a>
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
	<form:form action="${pageContext.request.contextPath}/system/saveApplySetting" method="POST" modelAttribute="systemPreDay">
	<form:input type="hidden"  id="perdayId" path="id" value="${systemPreDay.id }"/>
    <div class="new-classroom" style="width: 90%;">
      <tr>
        <th>学期<font color="red">*</font>：</th>
        <td>
        <form:select id="schoolTerm" path="schoolTerm.id" class="chzn-select">
  			<form:option value=""></form:option>
  			<c:forEach items="${schoolTerms}" var="curr">
  			<form:option value="${curr.id}">${curr.termName}</form:option>
  			</c:forEach>
  		</form:select>
  		</td>
      </tr>
      <th>提前递交天数</th>
      <td>
      	<form:input class="easyui-validatebox" id="dayNum" path="dayNum" value="${currDay}"/></td>
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
