<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
<meta name="decorator" content="iframe"/>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式 -->	
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />

<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

<script type="text/javascript">
	//首页
	function first(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//末页
	function last(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//上一页
	function previous(url){
		var page=${page};
		if(page==1){
			page=1;
		}else{
			page=page-1;
		}
		//alert("上一页的路径："+url+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	//下一页
	function next(url){
		var totalPage=${pageModel.totalPage};
		var page=${page};
		if(page>=totalPage){
			page=totalPage;
		}else{
			page=page+1
		}
		//alert("下一页的路径："+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	
function cancelQuery(){
	window.location.href="${pageContext.request.contextPath}/device/deviceRepairList?page=1";
}	
</script>
<style>
	.content-box fieldset {
    margin: 1% 2% 1% 2%;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    background: #F6F6F6;
    border: 1px solid #ccc;
    padding: 1% 5px;
    float: left;
    width: 20%;
    box-sizing: border-box;
}
		.content-box .tab_lab{
	    	width: 100%;
            left: 0;
            margin: -1px;
		}
		.tab_lab{
		width:100%;
		}
		.tab_lab th,
		.tab_lab td{
		    border:1px solid #e4e5e7;
		}
		
		.tab_lab{
		border-left:none;
		}
		.tab_lab th{
		    background:#fafafa;
		    width:90px;
		    padding:0 15px 0 0;
		    text-align:right;
		}
		.tab_lab td{
		    text-align:left;
	    	padding:10px;
		}
		.tab_lab td input[type="text"],
		.tab_lab td textarea,		
		.tab_lab td .spinner,
		.tab_lab td .combo{
		    resize:none;
		    border: 1px solid #cdcdcd;
		    border-radius:3px;
		    line-height:22px;
		    padding:0 10px;
		    outline:none;
		    box-sizing:border-box;
		}
		.tab_lab td .spinner,
		.tab_lab td .combo{
		padding:1px 0;
		}
		.tab_lab td input[type="text"]{
		width:144px;
		}
		.tab_lab td .spinner input[type="text"],
		.tab_lab td .combo input[type="text"]{
		    border:none;
		}
		.tab_lab td textarea{
		    width:100%;
		    box-sizing:border-box;
		}
		.tab_lab td input[type="text"]:focus,
		.tab_lab td textarea:focus{
		    border:1px solid #f3ce7a;
		}
        .space{
        margin:0 12px 0 0;
        }
        .combo{
            margin:0;
        }
</style>



</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management" /></a></li>
						<li class="end"><a href="javascript:void(0)">设备维修记录管理</a></li>
					</ul>
				</div>
			</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<%--<li class="TabbedPanelsTab " tabindex="0">
		<a href="${pageContext.request.contextPath}/device/findAllLabRoomDevice?page=1">全部</a>
		</li>
		<li class="TabbedPanelsTab selected" tabindex="0">
		<a href="${pageContext.request.contextPath}/device/applyDeviceRepairList?page=1">报修记录</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="${pageContext.request.contextPath}/device/passDeviceRepairList?page=1">已修复</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="${pageContext.request.contextPath}/device/rejectedDeviceRepairList?page=1">未修复</a>
		</li>
	--%>
	<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${roomId }&page=1">设备列表</a></li>
		<li class="TabbedPanelsTab selected" id="s2"><a href="${pageContext.request.contextPath}/device/applyDeviceRepairList?page=1&roomId=${roomId }">保修登记</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/device/listLabRoomDeviceUsage?page=1&roomId=${roomId }">设备使用记录</a></li>
	
	</ul>
	
	<div class="TabbedPanelsTabGroup-box">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
		<div class="content-box">
			<form:form action="${pageContext.request.contextPath}/device/saveLabRoomDeviceRepair?labRoomDeviceId=-1" method="POST" modelAttribute="labRoomDeviceRepair">
	<table class="tab_lab">
	    <tr>
	        <th>保修设备</th>
	        <td>
	            <form:select path="labRoomDevice.id" class="chzn-select">
	            <c:forEach items="${labRoomDevices}" var="s">
	    		    <form:option value="${s.id}">${s.schoolDevice.deviceNumber}${s.schoolDevice.deviceName}</form:option>
	            </c:forEach>
	    	    </form:select>
	        </td>
	        <th>报修人</th>
	        <td>
	            <form:select id="usernameManager" path="user.username" required ="true"  class="chzn-select">
					<form:option value="${user.username }">[${user.cname }]${user.username }</form:option>
			       <%-- <form:option value="${device.user.username }">${device.user.username }${device.user.cname }</form:option>
				    <form:option value="">- - - 请选择- - -</form:option>--%>
					<c:forEach items="${users}" var="t">
					    <form:option value="${t.key}">[${t.value}]${t.key}</form:option>
					</c:forEach>
				</form:select>
	        </td>
	        <th>报修时间</th>
	        <td>
	            <input name="repairTime" class="easyui-datebox" value="<fmt:formatDate value='${labRoomDeviceRepair.repairTime.time}' pattern='yyyy-MM-dd'/>" />
  	        </td>
  	        <th>修复时间</th>
  	        <td>
  	          <input name="restoreTime" class="easyui-datebox" value="<fmt:formatDate value='${labRoomDeviceRepair.restoreTime.time}' pattern='yyyy-MM-dd'/>" />
            </td>
	    </tr>
	    <tr>
	        <th>厂家</th>
	        <td colspan="3">	        
	            <form:input path="factory" style="width:100%;"/>
	        </td>
	        <th>厂家联系方式</th>
	        <td>	        
	            <form:input path="factoryPhone"/>
	        </td>
	        <th>维修费用</th>
	        <td>
	            <form:input id="price" path="repairCost" required="true" onBlur="isNumber()"/>
	        </td>
	    </tr>
	    <tr>
	        <th>维修情况描述</th>
	        <td colspan="7">
	            <form:textarea rows="5" path="repairRecords"/>
	        </td>
	    </tr>
	    <tr>
	        <td colspan="8">
                <input class="btn" id="save" type="submit" value="确定">
            </td>
	    </tr>
	</table>
	
	
	
	<%--<div class="new-classroom">
	<fieldset>
	    <label>保修设备：
	    </label>
	    <select class="chzn-select">
	    		<option>xxxxxxxx</option>
	    		<option>xxxxxxxx</option>
	    	</select>
	</fieldset>
	
  	<fieldset>
	  <label>报修人：</label>
	    <form:select id="usernameManager" path="user.username" required ="true"  class="chzn-select">
						      	<form:option value="${device.user.username }">
						      	${device.user.username }${device.user.cname }
						      	</form:option>
						      	<form:option value="">
						      	- - - 请选择- - - 
						      	</form:option>
						      	<c:forEach items="${users}" var="t">
					              <form:option value="${t.key}">[${t.value}]${t.key}</form:option>
					             </c:forEach>
								</form:select>
	  </fieldset>
	  <fieldset style="height:86px">
    <label>报修时间：</label>
    <input name="repairTime" class="easyui-datebox" value="<fmt:formatDate value='${labRoomDeviceRepair.repairTime.time}' pattern='yyyy-MM-dd'/>" />
  	</fieldset>
	   <fieldset style="height:86px">
    <label>修复时间：</label>
    <input name="restoreTime" class="easyui-datebox" value="<fmt:formatDate value='${labRoomDeviceRepair.restoreTime.time}' pattern='yyyy-MM-dd'/>" />
  </fieldset>
	  <fieldset>
	  <label>厂家:</label>
	  <form:input path="factory"/>
   </fieldset>
   <fieldset>
	  <label>厂家联系方式:</label>
	  <form:input path="factoryPhone"/>
	  </fieldset> 	
  <fieldset>
	    <label>维修费用：</label>
	    <form:input id="price" path="repairCost" required="true" onBlur="isNumber()"/>
	  </fieldset>
  --%><%--
  <fieldset>
	 <form:hidden path="id" />
	 <label>维修项目：</label>
	    <form:input id="repairProject" path="repairProject" required="true"/>
	  </fieldset>
 
  --%>
  <%--<fieldset style="width:50%">
	  <label>维修情况描述：</label>
	  <form:textarea style="height:80px;" path="repairRecords"/>
   </fieldset>
</div>
	
        <div class="submit_link">
          <input style="float:left;margin-left:20px;margin-bottom:10px;" class="btn" id="save" type="submit" value="确定">
        </div>
	--%></form:form>
		</div>
		<form:form name="queryForm" action="${pageContext.request.contextPath}/device/applyDeviceRepairList?page=1" method="post" modelAttribute="reservation">
			</form:form> 
		   <%--   <div class="tool-box">
		     <form:form name="queryForm" action="${pageContext.request.contextPath}/device/deviceRepairList?page=1" method="post" modelAttribute="reservation">
				<table class="list_form">
					<tr>
						<td>
							学期：
							<form:select path="">
							<form:options items="${terms}" itemLabel="termName" itemValue="id"/>
							</form:select>
							
						</td>
						<td>
						设备名称：<form:input path=""/>
						</td>
						<td>
							<input type="submit" value="查询">
							<input type="button" value="取消" onclick="cancelQuery();">
						</td>
					</tr >
			</table>
			</form:form>
		       
		    </div> --%>
    	<div class="content-box">
    		<div class="title">
    			<div id="title">报修记录列表</div>
    			<%--<a class="btn btn-new" href="javascript:void(0)">导出</a>
    		--%></div>
            <table class="tb" id="my_show"> 
                <thead>
                    <tr>
                    	<th>序号</th>
                        <th>报修设备</th>
                        <th>报修人</th>
                        <th>维修费用</th>
                        <th>所属实训室</th>
                        <th>厂家</th>
                        <%--
                        <th>硬件故障</th>
                        <th>软件故障</th>
                        <th>分区</th>
                        --%>
                        <th>报修时间</th>
                        <th>修复时间</th>
                        <%--
                        <th>修理记录</th>
                        <th>状态</th>
                        --%>
                        <th>操作</th>
                    </tr>  
                </thead>
                
                <tbody>
                		<c:forEach items="${deviceRepairList}" var="reservation" varStatus="i">
                		<%--<c:if test="${cid == reservation.labRoomDevice.labRoom.labAnnex.labCenter.id }">
                		--%><tr>
                        <td>${i.count}</td>
                        <td>${reservation.labRoomDevice.schoolDevice.deviceName}</td>
                        <td>${reservation.user.cname}</td>
                        <td>${reservation.repairCost}</td>
                        <td>${reservation.labRoomDevice.labRoom.labRoomName}</td>                     
                        <td>${reservation.factory}</td>
                        <%--
                        <td>${reservation.hardwareFailure}</td>
                        <td>${reservation.softwareFailure}</td>
                        <td>${reservation.CDictionaryByPartitionId.CName}</td>
                        --%>
                        <td><fmt:formatDate value="${reservation.repairTime.time}" pattern="yyyy-MM-dd"/> </td>
                        <td><fmt:formatDate value="${reservation.restoreTime.time}" pattern="yyyy-MM-dd"/> </td>
                        <%--
                        <td>
                        <c:if test="${reservation.repairRecords!=''}">
        	                 ${reservation.repairRecords}
        	            </c:if>
        	            <c:if test="${admin eq true || reservation.labRoomDevice.user.username == user.username  }">
			        	 <c:if test="${reservation.CLabRoomDeviceRepairStatus.id!=3}"> 
			        	<c:if test="${reservation.CDictionaryByStatusId.CCategory=='c_lab_room_device_repair_status' && reservation.CDictionaryByStatusId.CNumber!='3'}">
			        	<a href="${pageContext.request.contextPath}/device/repairDevice?idKey=${reservation.id}">维修</a>
			        	</c:if>
			        	</c:if>
        	            </td>
        	            --%>
        				<td>
        					<a href='${pageContext.request.contextPath}/device/labRoomDeviceRepairDetail?labRoomDeviceRepairId=${reservation.id}'>查看</a>
        					<a href='${pageContext.request.contextPath}/device/deleteLabRoomDeviceRepair?labRoomDeviceRepairId=${reservation.id}'>删除</a>
        					<a href='${pageContext.request.contextPath}/editLabRoomDeviceRepairDetail?labRoomDeviceRepairId=${reservation.id}&labRoomDeviceId=${reservation.labRoomDevice.id}'>编辑</a>
        				</td>                  
                        </tr>
                		</c:forEach>
                       
                </tbody>
            </table>
            
<!-- 分页模块 -->
<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/device/applyDeviceRepairList?page=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/device/applyDeviceRepairList?page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/device/applyDeviceRepairList?page=${page}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/device/applyDeviceRepairList?page=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/device/applyDeviceRepairList?page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/device/applyDeviceRepairList?page=${pageModel.totalPage}')" target="_self">末页</a>
    </div>
<!-- 分页模块 -->
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
<!-- 下拉框的js -->

</body>
</html>


