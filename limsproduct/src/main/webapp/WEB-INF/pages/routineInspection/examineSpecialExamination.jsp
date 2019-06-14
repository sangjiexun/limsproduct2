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
	<style type="text/css">
		textarea {
			height: 240px;
		}
		table tr th, table tr td {
			text-align: left;
		}
	</style>
  <!-- 下拉的样式结束 --> 
	
  	<script>	
	function cancel(){
		window.location.href="${pageContext.request.contextPath}/specialExamination?currpage=1";
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
		<tr>
	    	<td style="white-space:nowrap">
	  				时间：<input  style="border:0px" type="text" readonly="true" size="30" id="riTime" value="${specialExamination.schoolTerm.termName}"/>				
			</td>
			<td style="white-space:nowrap">
					学院：<input  style="border:0px" type="text" readonly="true" size="30" id="riCenterName" value="${specialExamination.labAnnex.labCenter.schoolAcademy.academyName}"/>
			</td>
			<td style="white-space:nowrap">
				检查人：<input  style="border:0px" type="text" readonly="true" size="30" id="riCnam" value="${specialExamination.user.cname}"/>
	  		</td>
	  		<td style="white-space:nowrap">
				<%--实验室：<input  style="border:0px" type="text" readonly="true" size="30" id="riRoomName" value="${specialExamination.labRoom.labRoomName}"/>--%>
	    	</td>

		</tr>
		<tr>
			<td style="white-space:nowrap">
	  				检查类型：${specialExamination.checkType}
			</td>
			<td style="white-space:nowrap">
	  				检查项：${specialExamination.checkItem}
			</td>
			<td style="white-space:nowrap">
				参加检查人员：${specialExamination.participant}
			</td>
			<td>

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
			<br/>      <div style="float:left" >  复查：</div> <div style=" float:left" > <textarea  readonly="ture" id="reviewExam" rows="20" cols="40" name="reviewExam">${specialExamination.reviewExam}</textarea></div>	
			</td>
			<td>
			<a href="${pageContext.request.contextPath}/tcoursesite/showFileForspeE?id=${specialExamination.id}" target="_blank">查看PDF文件</a>
			</td>
		</tr>
		<tr>	
			<td style="white-space:nowrap"><%--
				生成链接：${requestURL}				  
			--%></td>			
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
		    <input class="btn-return" type="button" value="返回" onclick="cancel()"/>
		    </td>
				
		</tr>
		
	</tbody>
	</table>
	<div>
  </div>
  </div>
  
 
  
</body>
</html>
