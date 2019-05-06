<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
   <!-- 下拉框的样式 -->
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 --> 
  
  <!-- 文件上传的样式和js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  
  
  <style>
  	select{
  		width:162px;
  		margin-left:10px;
  	}
  	.chzn-container{
  	width:162px !important;
  	margin-left:10px
  	}
  	.edit-content-box {
    border: 1px solid #9BA0AF;
    border-radius: 5px;
    overflow: visible;
    margin-top: 15px;
}
  </style>
 <script type="text/javascript">
//AJAX验证是否有不规范的字段
	function checkQuantity(){
	var id = ${id}; 
	$.ajax({
        url:"${pageContext.request.contextPath}/asset/checkNewStockQuantity?id="+id,
        type: 'POST',   
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false, 
        
        error: function(request) {
            alert("暂时不能判断该文档是否规范");
        },
        success: function(data) {
        	if(data == 'match'){ 
        		 window.location.href="${pageContext.request.contextPath}/asset/listAccessStock?currpage=1";
        	}else if(data == 'notMatch'){
        		alert("已入库数量加和与药品总量不符，请继续入库或重新确认入库总量!!!");
        	}
        }
    });
	}
	</script>
	 <script type="text/javascript">
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/asset/listAccessStock?currpage=1";
  }
  //跳转
  function targetUrl(url)
{
	location.href = url;
}

function listCabinet(){
	var labRoomId = document.getElementById("labRoom").value; 
	if(labRoomId == "0"){
		var i = document.getElementById("position");
		i.style.display="";
		document.getElementById("warehouseField").style.display="None";
		document.getElementById("cabinetField").style.display="None";
	}
	else{
		var i = document.getElementById("position");
		i.style.display="none";
		$.ajax({
	        url:"${pageContext.request.contextPath}/asset/findAssetCabinetByLabRoomId?labRoomId="+labRoomId,
	        type: 'POST',    
	        async: false,  
	        cache: false,  
	        contentType: false,  
	        processData: false, 
	        success:function(data){
	        	document.getElementById("cabinetField").style.display="";
				$("#cabinet").html(data.assetCabinet);
				$("#cabinet").trigger("liszt:updated");
			}
	    });
	}
	
}
function listWarehouse(){
    var cabinetId = document.getElementById("cabinet").value; 
    $.ajax({
        url:"${pageContext.request.contextPath}/asset/findWarehouseByCabinetId?cabinetId="+cabinetId,
        type: 'POST',    
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false, 
        success:function(data){
        	document.getElementById("warehouseField").style.display="";
            $("#warehouse").html(data.warehouse);
            $("#warehouse").trigger("liszt:updated");
        }
    });
}

$(document).ready(function(){
	document.getElementById("warehouseField").style.display="None";
	document.getElementById("cabinetField").style.display="None";
	 var isClick ='<%=request.getAttribute("isClickCompleteStock")%>';
	 if(isClick == 1){
         var status = '<%=request.getAttribute("status")%>';
         if(status == 0){
             alert("已入库数量加和与药品总量不符，请继续入库或重新确认入库总量!!!");
         }
    }
});

  </script>
</head>

<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">药品溶液管理</a></li>
		<li class="end"><a href="javascript:void(0)">药品入库</a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels"> 
  <div class="TabbedPanelsContentGroup">
  
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
	  <div id="title">药品入库</div>
	</div>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>药品名称</th>
	    <th>药品属性</th>
	    <th>入库总数量</th>
	    <th>入库时间</th> 
	    <th>入账时间</th> 
	  </tr>
	  </thead>
	  <tbody>
	  <tr>
	    <td>${assetCabinetWarehouseAccess.asset.chName}[${assetCabinetWarehouseAccess.asset.specifications}]</td>  
	    <td>
	    <c:if test="${assetCabinetWarehouseAccess.ifPublic eq 1}">公用</c:if>
	    <c:if test="${assetCabinetWarehouseAccess.ifPublic eq 0}">私用</c:if>
	    </td> 
	    <td>${assetCabinetWarehouseAccess.cabinetQuantity}</td> 
	    <td><fmt:formatDate value="${assetCabinetWarehouseAccess.stockDate.time}" pattern="yyyy-MM-dd" /></td>
	    <td><fmt:formatDate value="${assetCabinetWarehouseAccess.createDate.time}" pattern="yyyy-MM-dd" /></td>
	  </tr>
	  </tbody>
	</table>
  </div>
  <div class="content-box">
	<div class="title">
	  <div id="title">药品位置具体信息</div>
	  <a class="btn btn-new" onclick="cancel()">返回</a>
	  <a class="btn btn-new" onclick="checkQuantity()">入库完成</a>
	 <a class="btn btn-new" onclick="addLocation();">添加</a>
	 
	
	</div>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>药品名称</th>
	    <th>位置选择</th>
	    <th>药品柜名称</th>
	    <th>编号</th> 
	    <th>放置数量</th> 
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${accessRecords}" var="curr" varStatus="i">
	  <tr>
	    <td>${curr.asset.chName}[${curr.asset.specifications}]</td>  
	    <c:if test = "${curr.assetCabinetWarehouse ne null}"><td>${curr.assetCabinetWarehouse.assetCabinet.labRoom.labRoomName}</td> 
	    <td>${curr.assetCabinetWarehouse.assetCabinet.cabinetName}</td> 
	    <td>${curr.assetCabinetWarehouse.warehouseName}</td></c:if>
	     <c:if test = "${curr.assetCabinetWarehouse eq null}"><td>${curr.position}</td>
	     <td>--</td>
	     <td>--</td>
	     </c:if>
	    <td>${curr.cabinetQuantity}</td>
	    <td><a href="${pageContext.request.contextPath}/asset/deleteLocationMessage?id=${curr.id}" onclick="return confirm('确定删除？');">删除</a></td>
	  </tr>
	  </c:forEach>
	  </tbody>
	  
	</table>
	
	<div id="addLocation" class="easyui-window " title="添加" align="left" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 500px; height: 250px;">
					<form:form action="${pageContext.request.contextPath}/asset/saveLoctaionMessage?id=${id}" modelAttribute="accessRecord">
						<table>
							<tr>
						    <label>药品名称：</label>
  							${assetCabinetWarehouseAccess.asset.chName}[${assetCabinetWarehouseAccess.asset.specifications}]<br>
						    </tr>
							<tr>
							<div id="labRoomField">
								<label>存放位置：</label>
  							<li>
  								<form:select id="labRoom" name="labRoom" path="assetCabinetWarehouse.assetCabinet.labRoom.id" class="chzn-select" onChange="listCabinet()">
  								<form:option value="">请选择</form:option>
  								<form:option value="0">--自定义--</form:option>
  								<c:forEach items="${labRooms}" var="curr">
  									<form:option value="${curr.id}">${curr.labRoomName}</form:option>
  								</c:forEach>
  								</form:select>
  							</li>
  							</div>
  							<form:input id="position" path="position" style="display:none"/>
							</tr>
							<tr>
							<div id="cabinetField">
								<label>药品柜名称：</label>
  							<li>
  								<form:select id="cabinet" name="cabinet" path="assetCabinetWarehouse.assetCabinet.id" class="chzn-select" onChange="listWarehouse()">
  								</form:select>
  							</li>
  							</div>
							</tr>
							
							<tr >
							<div id="warehouseField">
								<label>药品柜编号：</label>
  							<li>
  								<form:select id="warehouse" name="warehouse" path="assetCabinetWarehouse.id" class="chzn-select" >
  								</form:select>
  							</li>
  							</div>
							</tr> 
							
							
							<tr>
								<td>存放数量：</td>
								<td><form:input path="cabinetQuantity" required="true"/>
								</td>
							</tr>
							<tr>
								<td><input type="submit" value="保存">
								</td>
							</tr>
						</table>
					</form:form>
				</div>
		
	</div>
  </div>
  </div>
  </div>
  </div>
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
	 <script type="text/javascript">
	//弹窗
		function addLocation(){
			$("#addLocation").show();
		    $("#addLocation").window('open');  
		}
	
	</script>
</body>
</html>
