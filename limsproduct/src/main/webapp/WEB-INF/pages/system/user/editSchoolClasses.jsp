<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 

<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<html>
<head>
<!-- 下拉框的样式 -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
<meta name="decorator" content="iframe"/>
 <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function subform(gourl) {
		form.action = gourl;
		form.submit();
	}
	
	function checkName(){
    $.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/checkClassesName",
		data: {'className':$("#className").val()},
		success:function(data){
		var a = new Array();
		a=data.split(",");
		for(var i=0;i<a.length;i++){
		if(a[i]==2){
		alert("您输入的班级名称已存在，请重新输入");
				}
			} 
		}
	}); 
}
	
	function checkNumber(){
    $.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/checkClassesNumber",
		data: {'classNumber':$("#classNumber").val()},
		success:function(data){
		var a = new Array();
		a=data.split(",");
		for(var i=0;i<a.length;i++){
		if(a[i]==1){
		alert("您输入的班级编号已存在，请重新输入");
				}
			} 
		}
	}); 
}
	
	function getSchoolAcademy(){
	$.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/findSchoolMajorBySchoolAcademy",
		data: {'schoolAcademy':$("#schoolAcademy").val()},
		success:function(data){
		$("#schoolMajor").html(data);
		}
	}); 
	
	}
</script>
</head>
  <body>
  <form:form id="form" modelAttribute="schoolClasses" >
    <div class="content-box">
						    <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="list_form" >
						      <form:hidden path="id"/>
						      	<tbody>
									<tr>		
										<td style="text-align:right">班级编号</td>
									    	<td><font style="color:#f00">*</font> <form:input id="classNumber" path="classNumber" required="true" readonly="true"/>
									    	</td>
									</tr>  
									  <tr>			
										<td style="text-align:right">班级名称</td>
									    	<td><font style="color:#f00">*</font><form:input  id="className" path="className" onblur="checkName();" required="true" /></td>
									</tr>
									  <tr>			
										<td style="text-align:right">入学年份</td>
									    	<td><form:input   path="classGrade" /></td>
									</tr>
								<tr>			
										<td style="text-align:right">所属学院</td>
									    	<td><form:select id="schoolAcademy" path="academyNumber"  onchange="getSchoolAcademy();"  >
   										<form:option value="" label ="--请选择--"/>
  											<form:options items="${academy}" />
  											</form:select>
											</td>
									</tr> 
					<tr>			
										<td style="text-align:right">所属专业</td>
									    	<td><form:select id="schoolMajor"  path="majorNumber" >
   										<form:option value="" label ="--请选择--"/>
  											<form:options items="${major}" />
  											</form:select>
											</td>
									</tr> 
									 <%--  <tr>			
										<td style="text-align:right">班级人数</td>
									    	<td><form:input   path="classStudentsNumber" /></td>
									</tr> --%>
								</tbody>
							</table>
		<div class="moudle_footer">
			<div class="submit_link">
				<input class="btn btn-big" type="button" value="保存"  onclick="subform('${pageContext.request.contextPath}/saveSchoolClasses')"/>
				<input class="btn btn-return" type="button" value="取消" onclick="window.history.go(-1)" href="#"/>
			</div>
		</div>
						</div>
  
   <%--<input type="button" value="保存"  onclick="subform('${pageContext.request.contextPath}/saveSchoolClasses')"/>--%>
  <%--<input type="button" value="取消" onclick="window.history.go(-1)" href="#"/>--%>
  
  </form:form>
  </body>
</html>
