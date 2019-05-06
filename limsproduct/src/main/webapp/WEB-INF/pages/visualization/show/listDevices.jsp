<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta name="decorator" content="iframe" />
<script langauge="javascript">
	function subform(gourl){ 
		 form.action=gourl;
		 form.submit();
	}
</script> 
 </head>
<body>
 <!--消息内容弹出框-->
  <div class="right-content">
  <div class="title">
	<div id="title">所有设备列表</div>
</div>
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
		<div class="content-box">	
			<div class="tool-box" style="display:none;">
				<form:form id="form_act" name="form" action="${pageContext.request.contextPath}/visualization/listDevices?id=${id}page=1" method="post">
					<table >
						<tbody>
						</tbody>
					</table>
				</form:form>
			</div>
		</div>
<div class="content-box">				
<div id="contentarea">
<div id="content">
<div class="content-box">
	<table >
		<thead>
			<tr>
				<th style="vertical-align:middle; text-align:center;">设备编号</th>
                <th style="vertical-align:middle; text-align:center;">设备名称</th>
                <th style="vertical-align:middle; text-align:center;">品牌</th>
                <th style="vertical-align:middle; text-align:center;">型号</th>
                <th style="vertical-align:middle; text-align:center;">规格</th>
                <th style="vertical-align:middle; text-align:center;">单价</th>
                <th style="vertical-align:middle; text-align:center;">领用人</th>
                <th style="vertical-align:middle; text-align:center;">领用单位</th>
                <th style="vertical-align:middle; text-align:center;">购置日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${labRoomDeviceInfo}" var="labRoomDevice"  varStatus="i">
			       <tr>
			       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.deviceNumber}</td>
			       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.deviceName}</td>
			       <td style="vertical-align:middle; text-align:center;">*</td>
			       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.devicePattern}</td>
			       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.deviceFormat}</td>
			       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.devicePrice}</td>
			       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.userByUserNumber.cname}</td>
			       <td style="vertical-align:middle; text-align:center;">*</td>
			       <td style="vertical-align:middle; text-align:center;"><fmt:formatDate value="${labRoomDevice.schoolDevice.deviceBuyDate.time}" pattern="yyyy-MM-dd" /></td>
			       </tr>
			 </c:forEach>
	   	</tbody>
	</table>
	<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
         <a href="${pageContext.request.contextPath}/visualization/listDevices?page=1&id=${id }" target="_self">首页</a>			    
	<a href="${pageContext.request.contextPath}/visualization/listDevices?page=${pageModel.previousPage}&id=${id }" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/visualization/listDevices?page=${page}&id=${id}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/visualization/listDevices?page=${j.index}&id=${id}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
 	<a href="${pageContext.request.contextPath}/visualization/listDevices?page=${pageModel.nextPage}&id=${id }" target="_self">下一页</a>
 	<a href="${pageContext.request.contextPath}/visualization/listDevices?page=${pageModel.lastPage}&id=${id }" target="_self">末页</a>
    </div>
</div>
</div>
</div>
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
   '.chzn-select'      : {search_contains:true},
   '.chzn-select-deselect' : {allow_single_deselect:true},
   '.chzn-select-no-single' : {disable_search_threshold:10},
   '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
   '.chzn-select-width'   : {width:"95%"}
  }
  for (var selector in config) {
   $(selector).chosen(config[selector]);
  }
</script>
<!-- 下拉框的js -->
</body>
</html>