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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<!-- 打印开始 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<!-- 打印结束 -->

<!-- 打印、导出组件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/LodopFuncs.js"></script>
<script type="text/javascript">
function print(){
    $('#my_show').jqprint();
}
	// 跳转
	function targetUrl(url)
	{
	  document.queryForm.action=url;
	  document.queryForm.submit();
	}
	// 取消
	function cancelQuery(){
		window.location.href="${pageContext.request.contextPath}/device/listLabRoomDeviceUsageInAppointment?page=1";
	}	

</script>
<style>
        .cf:after{
		    display:block; 
		    content:"gvsun"; 
		    height:0; 
		    clear:both; 
		    overflow:hidden; 
		    visibility:hidden;}
		.tool-box{
		   overflow:visible;
		   clear:both;
		}
		.tool-box input{
			float:none;
			margin:0;
		}
		.reser_box{
			border:1px solid #333;
			margin:10px auto;
			width:98%;
		}
		.tab_fix td{
		    text-align:left;
            white-space:nowrap;
            padding-right:20px;
		}
		.tab_fix td input[type="text"]{
		   height: 24px;
           width: 100%;
           box-sizing: border-box;
           min-width: 80px;
		}
        .tab_fix th{
            font-weight:normal;
            width: 95px;
            text-align: right;
            white-space:nowrap;
        }
        .combo{
            margin:0;
        }
        .sub_tit{
            text-align:center;
        }
        .sub_tit li{
            display:inline-block;
            width:33%;
            float:none;
        }        
</style>
</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)">实训室设备预约</a></li>
						<li class="end"><a href="javascript:void(0)">设备使用报表</a></li>
					</ul>
				</div>
			</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
 <ul class="TabbedPanelsTabGroup">
  
			<%--<li class="TabbedPanelsTab" tabindex="0" id="s0">
			<a href="${pageContext.request.contextPath}/device/listLabRoomDeviceUsage?page=1">设备使用报表</a>
			</li>
		
			<li class="TabbedPanelsTab  selected"  tabindex="0" id="s1">
			<a href="${pageContext.request.contextPath}/device/listLabRoomDeviceUsageInAppointment?page=1">设备教学使用</a>
			</li>
		
	  --%>
	  <li class="TabbedPanelsTab " id="s1"><a href="${pageContext.request.contextPath}/device/listLabRoomDevice?page=1&isOrder=${idOrder}">设备管理</a></li>
		<li class="TabbedPanelsTab " id="s2"><a href="${pageContext.request.contextPath}/device/applyDeviceRepairList?page=1">保修登记</a></li>
		<li class="TabbedPanelsTab selected" id="s3"><a href="${pageContext.request.contextPath}/device/listLabRoomDeviceUsage?page=1">设备使用记录</a></li>
	  </ul>
	   <div>		    
	    <ul class="sub_tit">
		    <li>
		        <a href="${pageContext.request.contextPath}/device/listLabRoomDeviceUsage?page=1">设备预约使用记录</a>
		    </li>
		    <li class="st_selected">
		        <a href="${pageContext.request.contextPath}/device/listLabRoomDeviceUsageInAppointment?page=1">设备教学使用记录</a>
		    </li>
		</ul>
</div>
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
		<div class="content-box">
		     <div class="tool-box">
		     <form:form name="queryForm" action="${pageContext.request.contextPath}/device/listLabRoomDeviceUsageInAppointment?page=1" method="post" >
				<table class="tab_fix">
					<tr>						
						 <th>设备名称：</th>
						 <td>
							<select id="deviceName" name="deviceName" class="chzn-select">
							<option value="">请选择</option>
							<c:forEach var="curr" items="${devices}"> 
							<c:if test="${deviceName eq curr.labRoomDevice.schoolDevice.deviceName }">
								<option value="${curr.labRoomDevice.schoolDevice.deviceName}" selected="selected">${curr.labRoomDevice.schoolDevice.deviceName }</option>
							</c:if>
							<c:if test="${deviceName ne curr.labRoomDevice.schoolDevice.deviceName }">
								<option value="${curr.labRoomDevice.schoolDevice.deviceName}" >${curr.labRoomDevice.schoolDevice.deviceName }</option>
							</c:if>
							</c:forEach> 
							</select> 
						</td> 
						<th>设备编号：</th>
						<td>
							<select id="deviceNumber" name="deviceNumber" class="chzn-select">
							<option value="">请选择</option>
							<c:forEach var="curr" items="${devices}"> 
							<c:if test="${deviceNumber eq curr.labRoomDevice.schoolDevice.deviceNumber }">
							<option value="${curr.labRoomDevice.schoolDevice.deviceNumber}" selected="selected">${curr.labRoomDevice.schoolDevice.deviceNumber }</option>
							</c:if>
							<c:if test="${deviceNumber ne curr.labRoomDevice.schoolDevice.deviceNumber }">
							<option value="${curr.labRoomDevice.schoolDevice.deviceNumber}" >${curr.labRoomDevice.schoolDevice.deviceNumber }</option>
							</c:if>
							</c:forEach>
							</select> 
						</td>    
						 <th>课程名称：</th>
						 <td colspan="3">
							<select id="courseName" name="courseName" class="chzn-select">
							<option value="">请选择</option>
							<c:forEach var="curr" items="${courses}"> 
							<c:if test="${courseName eq curr[12] }">
							<option value="${curr[12]}"  selected="selected">${curr[12] }</option>
							</c:if>
							<c:if test="${courseName ne curr[12] }" >
							<option value="${curr[12]}">${curr[12] }</option>
							</c:if>
							</c:forEach>
							</select>							
						</td>  
						</tr>
						<tr>
						<th>实验项目名称：</th>
						<td>
							<select id="itemName" name="itemName" class="chzn-select">
							<option value="">请选择</option>
							<c:forEach var="curr" items="${items}"> 
							<c:if test="${curr.operationItem.lpName eq itemName}">
								<option value="${curr.operationItem.lpName}" selected="selected">${curr.operationItem.lpName }</option>
							</c:if>
							<c:if test="${curr.operationItem.lpName ne itemName}">
								<option value="${curr.operationItem.lpName}" >${curr.operationItem.lpName }</option>
							</c:if>
							</c:forEach>
							</select>
						</td> 
 						<th>上课教师：</th>
 						<td>
							<select id="teacherName" name="teacherName" class="chzn-select">
							<option value="">请选择</option>
							<c:forEach var="curr" items="${teachers}"> 
							<c:if test="${teacherName eq curr.user.cname }">
								<option value="${curr.user.cname}" selected="selected" >${curr.user.cname }[${curr.user.username }]</option>
							</c:if>
							<c:if test="${teacherName ne curr.user.cname }">
								<option value="${curr.user.cname}">${curr.user.cname }[${curr.user.username }]</option>
							</c:if>
							</c:forEach>
							</select>
						</td>	 
						<th>学期：</th>
						<td>
							<select id="term" name="term" class="chzn-select">
							<option value="">请选择</option>
							<c:forEach var="curr" items="${schoolTerms}"> 
							<c:if test="${term eq curr.id }">
								<option value="${curr.id}" selected="selected" >${curr.termName}</option>
							</c:if>
							<c:if test="${term ne curr.id}">
								<option value="${curr.id}">${curr.termName}</option>
							</c:if>
							</c:forEach>
							</select>
						</td>	 
						<td style="text-align:right;">
							<input type="button" onclick="query()" value="查询"/>
							<input type="button" value="取消" onclick="cancelQuery();"/>
						</td>
					</tr>
					<%--<tr>	
 						
					</tr >
				--%></table>
			</form:form>
		    </div>    
		    </div>
    	<div class="content-box">
    		<div class="title">
    			<div id="title">设备使用列表</div>
    			<a class="btn btn-new" onclick="exportLabRoomDeviceUsageInAppointment()">导出</a>
    			<a class="btn btn-new" onclick="print()" >打印</a>
    		</div>
            <table class="tb" id="my_show"> 
                <thead>
                    <tr>
                    	<th style="width:3%">序号</th>
                        <th style="width:8%">设备名称</th>
                        <th style="width:8%">课程名称</th>
                        <th style="width:5%">实验项目</th>
                        <th style="width:10%">星期</th>
                        <th style="width:5%">节次</th>
                        <th style="width:5%">周次</th>
                        <th style="width:5%">使用机时/小时</th>
                        <th style="width:10%">上课教师</th>
                        <th style="width:8%"><spring:message code="all.trainingRoom.labroom" /></th>
                        
                    </tr>
                </thead>
                
                <tbody>
                		<c:forEach items="${listLabRoomDeviceUsageInAppointments}" var="curr" varStatus="i">
                		<tr>
                        <td>${i.count+(page-1)*pageSize}</td>
                        <td>${curr[2]}</td>
                        <td>${curr[12]}</td>
                        <td>${curr[10]}</td>
                        <td>${curr[4]}</td>
                        <td>${curr[6]}</td>
                        <td>${curr[5]}</td>
                        <td>${curr[14]}</td>
                        <td>${curr[8]}</td>
                        <td>${curr[9]}</td>
                        
                        </tr>
                		</c:forEach>
                       
                </tbody>
            </table>
            
	<!-- 分页模块 -->
		<div class="page" >
	        ${totalRecords}条记录,共${pageModel.totalPage}页
	    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/listLabRoomDeviceUsageInAppointment?page=1')" target="_self">首页</a>			    
		<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/listLabRoomDeviceUsageInAppointment?page=${pageModel.previousPage}')" target="_self">上一页</a>
		第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
		<option selected="selected" value="${pageContext.request.contextPath}/device/listLabRoomDeviceUsageInAppointment?page=${page}">${page}</option>
		<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
	    <c:if test="${j.index!=page}">
	    <option value="${pageContext.request.contextPath}/device/listLabRoomDeviceUsageInAppointment?page=${j.index}">${j.index}</option>
	    </c:if>
	    </c:forEach></select>页
		<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/listLabRoomDeviceUsageInAppointment?page=${pageModel.nextPage}')" target="_self">下一页</a>
	 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/listLabRoomDeviceUsageInAppointment?page=${pageModel.lastPage}')" target="_self">末页</a>
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
    function exportLabRoomDeviceUsageInAppointment(){
    	document.queryForm.action = "${pageContext.request.contextPath}/device/exportLabRoomDeviceUsageInAppointment";
		document.queryForm.submit();
    }
    function query(){
    	document.queryForm.action = "${pageContext.request.contextPath}/device/listLabRoomDeviceUsageInAppointment?page=1";
		document.queryForm.submit();
    }
</script>
<!-- 下拉框的js -->

</body>
</html>


