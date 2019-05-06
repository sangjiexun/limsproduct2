<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.labappointment-resources"/>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<meta name="decorator" content="iframe"/>
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/labreservation/labreservationmanagerList.js"></script>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
<script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
<!-- 下拉的样式结束 -->

    <!-- 弹窗 -->
  <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
  <script type="text/javascript">
  $(function(){
var s=${isAudit};
 if(1==s){
 $("#s3").addClass("TabbedPanelsTab selected");
 $("#s1").addClass("TabbedPanelsTab");
  $("#s2").addClass("TabbedPanelsTab");
 }
  if(2==s){
 $("#s2").addClass("TabbedPanelsTab selected");
 $("#s1").addClass("TabbedPanelsTab ");
  $("#s2").addClass("TabbedPanelsTab");
 }


	});
//分页
//首页
	function first(url){
		document.form.action=url;
		document.form.submit();
	}
	//末页
	function last(url){
		document.form.action=url;
		document.form.submit();
	}
	//上一页
	function previous(url){
		var currpage=${currpage};
		
		if(currpage==1){
			currpage=1;
		}else{
			currpage=currpage-1;
		}
		//alert("上一页的路径："+url+currpage);
		document.form.action=url+currpage;
		document.form.submit();
	}
	//下一页
	function next(url){
		var totalPage=${pageModel.totalPage};
		var currpage=${currpage};
		if(currpage>=totalPage){
			currpage=totalPage;
		}else{
			currpage=currpage+1
		}
		//alert("下一页的路径："+page);
		document.form.action=url+currpage;
		document.form.submit();
	}
	function cancleQuery(){
		window.location.href="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?currpage=1&tage=0&isaudit=${isAudit}";
	}
	
	
</script>
<style>
.tab_fix th {
    font-weight: normal;
    width: 95px;
    text-align: right;
    white-space: nowrap;
}
.tab_fix td {
    text-align: left;
    white-space: nowrap;
    padding-right: 20px;
}
.combo{
    margin: 0;
    position: relative;
    top: 2px;
    overflow: hidden;
}
.combo_text{
    position:relative;
    top:-5px;
    margin:0 2px;
}
</style>
</head>
<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.open.appointment" /></a></li>
				<li class="end"><a href="javascript:void(0)"><spring:message code="left.instrument.appointment" /></a></li>
			</ul>
		</div>
	</div>
<div id="TabbedPanels1" class="TabbedPanels">
<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
    <li class="TabbedPanelsTab " id="s1"><a href="${pageContext.request.contextPath}/device/listLabRoomDevice?page=1&isReservation=1&isOrder=1"><spring:message code="left.instrument.appointment" /></a></li>
    <li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?tage=0&currpage=1&isaudit=2">我的申请</a></li>
	<c:if test="${sessionScope.selected_role eq 'ROLE_TEACHER' || sessionScope.selected_role eq 'ROLE_CFO' || sessionScope.selected_role eq 'ROLE_LABMANAGER'
	    || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR' || sessionScope.selected_role eq 'ROLE_PREEXTEACHING' || sessionScope.selected_role eq 'ROLE_SUPERADMIN'|| sessionScope.selected_role eq 'ROLE_EQUIPMENTADMIN'}">
        <li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?tage=0&currpage=1&isaudit=1">我的审核</a></li>
				<%--<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?tage=3&currpage=1">未审核</a></li>--%>
        <li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationListBatch?tage=0&currpage=1&isaudit=1">批量审核</a></li>
    </c:if>
        </ul>
	<div class="TabbedPanelsContentGroup">
	<div class="TabbedPanelsContent">
	<div class="content-box">
 	<div class="tool-box">
        <form:form name="form" action="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?currpage=1&tage=${tage}&isaudit=${isAudit}" method="post" modelAttribute="labRoomDevice">
		<table class="tab_fix">
			<%--<li>学期：<form:select path="schoolTerm.id">
	        <option value="">请选择</option>
	        <c:if test="${shoolTerm!=null}">
	        <option value="${shoolTerm.id}" selected="selected">${shoolTerm.termName}</option>
	        </c:if>
			<c:forEach items="${timemap}" var="s">
			<option value="${s.id}">${s.termName}</option>
			</c:forEach>
			</form:select> </li>
			--%>
			<tr>
			<th><spring:message code="all.training.name"/>室名称:</th>
			<td style="width:160px;">
				<form:select class="chzn-select" path="labRoom.id" id="labRoom_id">
                    <form:option value="">请选择</form:option>
                    <form:options items="${rooms}" itemLabel="labRoomName" itemValue="id"/>
				</form:select>		    				    				            
			</td>
			<th>设备名称:</th>
			<td style="width:160px;">
				<form:select path="schoolDevice.deviceNumber" id="schoolDevice_deviceName"  class="chzn-select">
                    <form:option value="">请选择</form:option>
                    <form:options items="${schoolDevice}" itemLabel="deviceName" itemValue="deviceNumber"/>
				</form:select>
			</td> 
			</tr>
			<tr>
			<th>申请时间:</th>
			<td>
				<input  class="easyui-datebox"  id="begintime" name="begintime" value="${begintime}"  type="text"  onclick="new Calendar().show(this);" style="width:100px;" />
			   <span class="combo_text">到</span>
			   <input class="easyui-datebox"  id="begintime1" name="begintime1"  value="${begintime1}" type="text"  onclick="new Calendar().show(this);" style="width:100px;" />
			</td>
			<th>审核状态:</th>
				<td><form:select id="status" name="status" path="">
				<form:option value="">请选择</form:option>
				<form:option value="1">未审核</form:option>
				<form:option value="2">已审核</form:option>
				</form:select>
			</td>
			<td>
			    <input type="submit" value="查询" >
                <input class="cancel-submit" type="button" onclick="cancleQuery()" value="取消">
			</td>
			</tr>
			</table>
		</form:form>
	</div>
    </div>
<div class="content-box">
<table >
<thead>
   <th style="width:3%">序号</th>
   <th style="width:15%">预约设备</th>
   <th style="width:20%">申请内容</th>
   <th style="width:7%">申请日期</th>
   <th style="width:8%">申请使用时间</th>
   <th style="width:5%"><spring:message code="all.trainingRoom.labroom" /></th>
   <th style="width:7%">
   <c:if test="${isAudit eq 2}">审核人</c:if>
    <c:if test="${isAudit eq 1}">申请人</c:if>
    </th>
   <th style="width:5%">审核状态</th>
   <th style="width:5%">审核结果</th>
   <th style="width:5%">操作</th>
</tr>
</thead>

<tbody >
<c:forEach items="${listLabRoomDeviceReservation}" var="reservation" varStatus="i">
<tr>
     <td>${i.count+(currpage-1)*pageSize}</td>
     <td>
      <c:if test="${empty reservation.innerDeviceName }">
      	${reservation.labRoomDevice.schoolDevice.deviceName}[(${reservation.labRoomDevice.schoolDevice.deviceNumber})]
      </c:if>
      <c:if test="${not empty reservation.innerDeviceName }">
      	${reservation.innerDeviceName}<font color="red">关联设备</font>
      </c:if>
     </td>
     <td><p>${reservation.content}</p></td>
     <td>
         <fmt:setLocale value="zh_CN"/>
         <fmt:formatDate value="${reservation.begintime.time}" pattern="yyyy-MM-dd"/>
     </td>
     <td>
     	<c:if test="${reservation.begintime.time.day eq reservation.endtime.time.day}">
           <fmt:formatDate value="${reservation.begintime.time}" pattern="HH:mm:ss"/>-<fmt:formatDate value="${reservation.endtime.time}" pattern="HH:mm:ss"/>
      </c:if>
      
      <c:if test="${reservation.begintime.time.day ne reservation.endtime.time.day}">
       	起<fmt:formatDate value="${reservation.begintime.time}" pattern="yyyy/MM/dd HH:mm:ss"/><br>止<fmt:formatDate value="${reservation.endtime.time}" pattern="yyyy/MM/dd HH:mm:ss"/>
      </c:if>
     </td>
     <td>${reservation.labRoomDevice.labRoom.labRoomName}</td>
     <td>
     <c:if test="${isAudit eq 2}">${reservation.remark}</c:if>
     <c:if test="${isAudit eq 1}">${reservation.userByReserveUser.cname}</c:if>
      </td>
      <td>
      <c:choose>
         <c:when test="${reservation.CAuditResult.CNumber eq 2}">已审核</c:when>
         <c:when test="${reservation.CAuditResult.CNumber eq 3}">已审核</c:when>
          <c:when test="${reservation.CAuditResult.CNumber eq 4}">审核过期</c:when>
         <c:otherwise>
             未审核<br>
             <c:if test="${reservation.labRoomDevice.isAuditTimeLimit eq 1 && reservation.userByReserveUser.userRole eq '0'}">审核剩余约${reservation.auditRestTime}小时</c:if>
         </c:otherwise>
         </c:choose>
      </td>
     <td>
     <c:choose>
         <c:when test="${reservation.CAuditResult.CNumber eq 1}">${allResult[reservation.id]}</c:when>
         <c:otherwise>${reservation.CAuditResult.CName}</c:otherwise>
     </c:choose>
     </td>
     <td>
     <c:if test="${reservation.buttonMark eq 0}">
     	  <a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/checkButton?id=${reservation.id}&tage=${tage}&state=${0}&currpage=${currpage}">查看</a>
     </c:if>
     <c:if test="${reservation.buttonMark >= 1}">
     	  <a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/checkButton?id=${reservation.id}&tage=${tage}&state=${1}&currpage=${currpage}">审核</a>
     </c:if>
     <%--<c:if test="${reservation.buttonMark eq 2 && sessionScope.selected_role eq 'ROLE_CFO'}">--%>
     	  <%--<a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/checkButton?id=${reservation.id}&tage=${tage}&state=${reservation.state}&currpage=${currpage}">系主任审核</a>--%>
     <%--</c:if>--%>
     <%--<c:if test="${reservation.buttonMark eq 3 && sessionScope.selected_role eq 'ROLE_LABMANAGER'}">--%>
     	  <%--<a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/checkButton?id=${reservation.id}&tage=${tage}&state=${reservation.state}&currpage=${currpage}">实训室管理员审核</a>--%>
     <%--</c:if>--%>
     <%--<c:if test="${reservation.buttonMark eq 4 && sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'}">--%>
     	  <%--<a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/checkButton?id=${reservation.id}&tage=${tage}&state=${reservation.state}&currpage=${currpage}">实训中心主任审核</a>--%>
     <%--</c:if>--%>
     <%--<c:if test="${reservation.buttonMark eq 5 && sessionScope.selected_role eq 'ROLE_PREEXTEACHING'}">--%>
     	  <%--<a href="${pageContext.request.contextPath}/LabRoomDeviceReservation/checkButton?id=${reservation.id}&tage=${tage}&state=${reservation.state}&currpage=${currpage}">实训部主任审核</a>--%>
     <%--</c:if>--%>
     <!--<c:if test="${reservation.CAuditResult.CNumber eq 2}">
     <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EQUIPMENTADMIN,ROLE_TEACHER">
         <a href="${pageContext.request.contextPath}/device/labRoomReservationCredit?id=${reservation.id}&page=${page}">信誉登记</a>
     </sec:authorize>
     </c:if>-->
     </td>
</tr>
</c:forEach> 
</table>
   <div class="page">
	共 ${totalRecords}条记录, 总页数:${pageModel.totalPage}页 <input type="hidden" value="${pageModel.lastPage}" id="totalpage" />&nbsp;
   <input type="hidden" value="${currpage}" id="currpage" /><input type="hidden" value="${tage}" id="tage" />
       <a href="javascript:void(0)" onclick="first('${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?tage=${tage}&currpage=1&isaudit=${isAudit}')" target="_self">首页</a>				    
       <a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?tage=${tage}&isaudit=${isAudit}'+'&currpage=')" target="_self">上一页</a>
       <a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?tage=${tage}&isaudit=${isAudit}'+'&currpage=')" target="_self">下一页</a>
       <a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?tage=${tage}&isaudit=${isAudit}&currpage=${pageModel.totalPage}')" target="_self">末页</a>				   
   </div>
</div></div></div></div>

    <!-- 下拉框的js -->
    <script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        var config = {
            '.chzn-select'           : {search_contains:true},
            '.chzn-select-deselect'  : {allow_single_deselect:true},
            '.chzn-select-no-single' : {disable_search_threshold:10},
            '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
            '.chzn-select-width'     : {width:"100%"}
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
        $('[att-record]').each(function(i,e){
            $(e).on('click',function(){
                layer.open({
                    type: 2,
                    title: '已阅读人员名单',
                    maxmin: true,
                    shadeClose: true,
                    area : ['1000px' , '500px'],
                    content: '${pageContext.request.contextPath}/labRoom/viewLabRoomAttentionRecordDevice?deviceNumber='+$(e).attr('att-record')+'&page=1'
                });
            });
        });

    </script>
    <script type="text/javascript">
        $(".flag_img_limit").hover(
            function() {
                $(this).find(".list_intro").slideDown(400);
                //alert("s")
            },
            function() {
                $(this).find(".list_intro").slideUp(400);
            }
        )
    </script>
    <!-- 下拉框的js -->

</body>
</html -->