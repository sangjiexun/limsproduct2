<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
  <head>
  <meta name="decorator" content="iframe" />
  <style>
  .tool-box input[type=text]{
	width:100px!important;}
  </style>
<script type="text/javascript">

</script>
<script langauge="javascript">
</script> 
  </head>
  
  <body>
   <div class="right-content">
   <div class="title">
	<div id="title">所有消息列表</div>
</div>
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
	<c:set var="totalValid" value="0"></c:set>
	<c:set var="totalShare" value="0"></c:set>
	<c:set var="totalValidPrice" value="0.00"></c:set>
	<c:set var="totalPrice" value="0.00"></c:set>
	<c:forEach items="${labRoom.labRoomDevices}" var="curr">
		<c:if test="${curr.CDictionaryByDeviceStatus.id!=593&&curr.CDictionaryByDeviceStatus.id!=595}">
			<c:set var="totalValid" value="${totalValid+1}" />
			<c:set var="totalValidPrice" value="${totalValidPrice+curr.schoolDevice.devicePrice}" />
		</c:if>
		<c:if test="${curr.CDictionaryByAllowAppointment.id==621}">
			<c:set var="totalShare" value="${totalShare+1}" />
		</c:if>
		<c:set var="totalPrice" value="${totalPrice+curr.schoolDevice.devicePrice}" />
	</c:forEach>
	<table>
			<thead>
				<tr>
					<th>一、基本情况</th>
				</tr>
			</thead>
			<tbody>
        	<tr>
            	<td>实验实训室：</td>
            	<td>${labRoom.labRoomName}</td>
            	<td>实验室类别：</td>
            	<td>${labRoom.CDictionaryByLabRoomClassification.CName}</td>
            	<td>建设专业/系：</td>
            	<td>${labRoom.schoolAcademy.academyName}</td>
         	</tr>
        	<tr>
            	<td>建设年份：</td>
            	<td><fmt:formatDate value="${labRoom.labRoomTimeCreate.time}" pattern="yyyy-MM-dd"/></td>
            	<td>面积：</td>
            	<td>${labRoom.labRoomArea}</td>
            	<td>工位数：</td>
            	<td>${labRoom.labRoomCapacity}</td>
         	</tr>
        	<tr>
            	<td>累计投资金额：</td>
            	<td>${current[1]}</td>
            	<td></td>
            	<td></td>
            	<td></td>
            	<td></td>
         	</tr>
      	</tbody>
	</table>
	<table>
			<thead>
				<tr>
					<th>二、实验实训室空间利用率</th>
				</tr>
			</thead>
			<tbody>
        	<tr>
            	<td>本专业学生使用人时数：</td>
            	<td>${labTimetable+labOpenHour[0]}</td>
            	<td>其他专业使用人时数：</td>
            	<td>${current[3]}</td>
            	<td>正常教学使用人时数合计：</td>
            	<td align="left">${labTimetable}</td>
         	</tr>
        	<tr>
            	<td>学生竞赛使用人时数：</td>
            	<td>${current[1]}</td>
            	<td>教师科研人时数：</td>
            	<td>${current[3]}</td>
            	<td>其他人时数：</td>
            	<td>${current[3]}</td>
         	</tr>
        	<tr>
            	<td>额定人时数：</td>
            	<td>${current[1]}</td>
            	<td>空间利用率：</td>
            	<td>
					<fmt:formatNumber value="${labTimetable*100/(labRoom.labRoomCapacity*32*13)}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>%
				</td>
            	<td>得分：</td>
            	<td></td>
         	</tr>
      	</tbody>
	</table>
	<table>
			<thead>
				<tr>
					<th>三、设备利用率</th>
				</tr>
			</thead>
			<tbody>
        	<tr>
            	<td>实验实训室设备仪器总数：</td>
            	<td>${labRoom.labRoomDevices.size()}</td>
            	<td>在用仪器设备数：</td>
            	<td>${totalValid}</td>
            	<td>在用率：</td>
            	<td>
					<c:if test="${labRoom.labRoomDevices.size()!=0}">
						<fmt:formatNumber value="${totalValid*100/labRoom.labRoomDevices.size()}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>%
					</c:if>
				</td>
         	</tr>
        	<tr>
            	<td>得分：</td>
            	<td>${current[1]}</td>
            	<td>实验实训室仪器设备总值：</td>
            	<td>${totalPrice}</td>
            	<td>使用仪器设备值：</td>
            	<td>${totalValidPrice}</td>
         	</tr>
        	<tr>
            	<td>仪器设备资产利用率：</td>
            	<td>
					<c:if test="${totalPrice>0.00}">
					    <fmt:formatNumber value="${totalValidPrice*100/totalPrice}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>%
					</c:if>
				</td>
            	<td>得分：</td>
            	<td></td>
            	<td>总分：</td>
            	<td></td>
         	</tr>
      	</tbody>
	</table>
	<table>
			<thead>
				<tr>
					<th>四、实验室对外开放</th>
				</tr>
			</thead>
			<tbody>
        	<tr>
            	<td>外校学生实训(人时数)：</td>
            	<td>${labOpenHour[1]}</td>
            	<td>社会培训(人时数)：</td>
            	<td>${labOpenHour[2]}</td>
            	<td>校外参观(人时数)：</td>
            	<td>${current[3]}</td>
         	</tr>
        	<tr>
            	<td>利用率(人时数/面积)：</td>
            	<td>
					<c:if test="${BigZero ne 0}">
						<fmt:formatNumber value="${labTimetable/(labRoom.labRoomArea)}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>
					</c:if>
				</td>
            	<td>排名：</td>
            	<td>${current[3]}</td>
            	<td>共享使用仪器设备台数：</td>
            	<td>${totalShare}</td>
         	</tr>
        	<tr>
            	<td>共享设备比重：</td>
            	<td>
					<c:if test="${labRoom.labRoomDevices.size()!=0}">
						<fmt:formatNumber value="${totalShare*100/labRoom.labRoomDevices.size()}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>%
					</c:if>
				</td>
            	<td>排名：</td>
            	<td></td>
            	<td>总分：</td>
            	<td></td>
         	</tr>
      	</tbody>
	</table>
</div>
</div>
</div>
</div>
</div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
      '.chzn-select'           : {search_contains:true},
      '.chzn-select-deselect'  : {allow_single_deselect:true},
      '.chzn-select-no-single' : {disable_search_threshold:10},
      '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
</script>
</body>
</html>