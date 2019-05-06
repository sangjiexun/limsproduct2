<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
  	<meta name="decorator" content="iframe"/>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script> 	 	
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
	
	 <!-- 下拉框的样式 -->
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
	<style type="text/css">
		textarea {
			height: 240px;
		}
		table tr th, table tr td {
			text-align: left;
		}
	</style>
  	<script>	
	function cancel(){
		window.location.href="${pageContext.request.contextPath}/specialExamination?currpage=1";
	}
	
	//提交-复查 	
  	function revieExam(sEId){ 		
		if($("#reviewExam").val()=="" || $("#reviewExam").val()==null ){
			alert("复查内容未填写！")
		}else if($("#reviewTime").val()=="" || $("#reviewTime").val()==null){
		    alert("请填写复查时间！");
		}else{
					document.queryForm2.action = "${pageContext.request.contextPath}/saveReviewExamineSpecialExamination?sEId="+sEId;
					document.queryForm2.submit();
		}
	}
	</script>
  	
</head>
  
<body>
<div class="navigation">
	<div id="navigation">
	  <ul>
		  <li><a href="javascript:void(0)"><spring:message code="left.lab.safety.inspect"/></a></li>
		  <li class="end"><a href="javascript:void(0)"><spring:message code="left.special.inspection"/></a></li>
	  </ul>
	</div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
  	<div class="TabbedPanelsContent">
  	<div class="content-box">
  </div>

 <div  id="examineDiv" style="width: 1000px;height: 400px;padding: 20px;border:1px solid #000;">
	<table border="0" cellpadding="5" cellspacing="100"   height="30" width="100%">
	<tbody>
	<form:form name="queryForm2" action="" method="post" enctype ="multipart/form-data">
		<tr>
	    	<td style="white-space:nowrap">
	  				时间：<input  style="border:0px" type="text" readonly="true" size="30" id="riTime" value="${specialExamination.schoolTerm.termName}"/>				
			</td>
			<td style="white-space:nowrap">
					学院：<input  style="border:0px" type="text" readonly="true" size="30" id="riCenterName" value="${specialExamination.labRoom.labAnnex.labCenter.schoolAcademy.academyName}"/>					
			</td>
			<td style="white-space:nowrap">
				检查人：<input  style="border:0px" type="text" readonly="true" size="30" id="riCnam" value="${specialExamination.user.cname}"/>
	  		</td>

		</tr>
		<tr>
			<td style="white-space:nowrap">
				复查时间：<input class="Wdate" id="reviewTime" name="reviewTime" type="text"
							value="<fmt:formatDate value="${reviewTime.time}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({maxDate:'%y-%M-{%d}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			</td>
			<td>
	  				检查类型：${specialExamination.checkType}
			</td>
			<td>
	  				检查项：${specialExamination.checkItem}
			</td>

			<%--<td>--%>

			<%--</td>--%>
		</tr>
		<tr>
			<td>
				参加检查人员：${specialExamination.participant}
			</td>
		</tr>
		<tr>
			<td colspan="1">
				  <br/>      <div style="float:left" >  检查内容：</div> <div style="float:left;" > <textarea readonly="ture" rows="20" cols="40"  id="riCheckContent" value="123">${specialExamination.checkContent}</textarea></div>
			</td>
			<td>
			<br/>      <div style="float:left" >  整改：</div> <div style="float:left;" > <textarea readonly="ture" rows="20" cols="40"  id="riCheckContent" value="123">${specialExamination.rectification}</textarea></div>
			</td>
			<td>
			<br/>      <div style="float:left" >  复查：</div> <div style=" float:left" > <textarea id="reviewExam" rows="20" cols="40" name="reviewExam"></textarea></div>	
			</td>
			</tr>
		<tr>	
			<td style="white-space:nowrap">
			</td>			
			<td>
			</td>
			<td>
			</td>
			<td>
		    </td> 			
		</tr>
		<tr>
		<br>
			<td>
		    </td>
		    <td>
		    </td>  
		    <td>
		    </td>
		    <td>
		    <%--<input type="button" class="btn-return" value="返回" onclick="cancel()"/>--%>
		    <%--<input type="button" class="btn-big" value="提交" onclick="revieExam(${sEId})"/>--%>
		    </td>
				
		</tr>
		</form:form>
	</tbody>
	</table>
	 <div style="margin-top: 10px">
		 <input type="button" class="btn-return" value="返回" onclick="cancel()"/>
		 <input type="button" class="btn-big" value="提交" onclick="revieExam(${sEId})"/>
	 </div>
	<div>
  </div>
  </div>
  
 
  
</body>
</html>
