<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe" />
  
  <!-- 下拉框的样式 -->
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
	<!-- 下拉的样式结束 -->
	
	<link href="${pageContext.request.contextPath}/css/cmsSystem/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	
  <script type="text/javascript">

  </script>
 <style>
  
fieldset label {
    display: block;
    float: left;
    font-weight: bold;
    height: 25px;
    line-height: 25px;
    margin: -5px 0 5px;
    padding-left: 10px;
    text-shadow: 0 1px 0 #fff;
    text-transform: uppercase;
    width: 90%;
}  
</style>
</head>
  
<body>
<div class="main_container cf rel w95p ma">
  <!-- 内容栏开始 -->
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
  <!-- 标题 -->
 
    <form:form action="${pageContext.request.contextPath}/asset/saveAllocation?id=${id}" method="POST" >
  <div class="right-content">
	  <div id="TabbedPanels1" class="TabbedPanels">
		  <div class="TabbedPanelsContentGroup">
			  <div class="TabbedPanelsContent">
				  <div class="content-box">
				  	<div class="title">
				  	</div>
					  	<table> 
							<thead>
							<tr>
							    <th>序号</th>
								<th>药品名称</th>
								<th>cas号</th>
								<th>货号</th>
								<th><spring:message code="all.trainingRoom.labroom" /></th>
								<th>药品柜名称</th>
								<th>药品柜编号</th>
								<th>数量</th>
								<th>单位</th>
							</tr>
							</thead>
							
							<tbody>
								<c:forEach items="${allocations}" var="curr" varStatus="i">
									<tr>
									   <%--<td><input type="checkbox" id="flag1" name="flag1" value="${curr.id }" checked="checked"></td>--%>
										<td>${i.count+pageSize*(currpage-1)}</td>
										<td>${curr.asset.chName}[${curr.asset.specifications}]</td>
										<td>${curr.asset.cas}</td>
										<td>${curr.assetCabinetWarehouseAccessRecord.assetCabinetWarehouseAccess.assetAppRecord.styleNumber}</td>
										<td>
											<c:if test="${curr.assetCabinetWarehouse eq null}">${curr.position}</c:if>
											<c:if test="${curr.assetCabinetWarehouse ne null}">${curr.assetCabinetWarehouse.assetCabinet.labRoom.labRoomName}</c:if>
										</td>
										<td>
											<c:if test="${curr.assetCabinetWarehouse eq null}">-</c:if>
											<c:if test="${curr.assetCabinetWarehouse ne null}">${curr.assetCabinetWarehouse.assetCabinet.cabinetName}</c:if>
										</td>
										<td>
											<c:if test="${curr.assetCabinetWarehouse eq null}">-</c:if>
											<c:if test="${curr.assetCabinetWarehouse ne null}">${curr.assetCabinetWarehouse.warehouseName}</c:if>
										</td>
										<td>${curr.quantity}</td>
										<td>${unit[i.count-1]}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				  </div>
			  </div>
		  </div>
	  </div>
  </div>
 <%--  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
	</div>
	<%--<div class="new-classroom"> 
	  <fieldset>
	    <label>是否通过该设备申请：</label>
	    <form:radiobutton path="status" value="1" id="status" name="status"/><label>是</label>
	    <form:radiobutton path="status" value="0" id="status" name="status" /><label>否</label>
	  </fieldset>
	<fieldset>
	
	  <form:hidden path="id"/>
	    <label>审核意见：</label>
	    <form:input path="mem" name="mem" id="mem"/>
	  </fieldset>
	     
	</div>--%>
	<%--<div class="moudle_footer">
        <div class="submit_link">
          <input class="btn" id="save" type="submit" value="确定">
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</div>--%>
	</form:form>
    
    <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	
	<script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/iCheck/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/sweetalert/sweetalert.min.js"></script>
	
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
	    
	    function saveCExpendable(){
	           $.ajax({
	                  url:'${pageContext.request.contextPath}/expendable/saveCExpendable?type='+${type},
	                  type:'POST',
	                 data:$('#edit_form').serialize(),
	                 error:function (request){
	                   alert('请求错误!');
	                 },
	                 success:function(){
	                   parent.location.href="${pageContext.request.contextPath}/expendable/listCExpendable?currpage=1&type="+${type};
                       var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                       parent.layer.closeAll('iframe');
	                 }         
	           });
	        }
	</script>
	<!-- 下拉框的js -->
  </div>
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
