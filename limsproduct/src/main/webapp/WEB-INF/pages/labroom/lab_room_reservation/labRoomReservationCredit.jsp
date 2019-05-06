<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html>
<head>
<meta name="decorator" content="iframe"/>
<title>无标题文档</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_Icons.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_LeftList.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Downlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Allpages.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Interface.js"></script>
<script type="text/javascript">
function checkAll()
{
	//alert("hh");
  if($("#check_all").attr("checked"))
  {
    $(":checkbox").attr("checked", false);
  }
  else
  {
    $(":checkbox").attr("checked", true);
  }
}
function submitForm()
{
  var remark="";
  remark=$("#remark").val();
  var flag = false;  //是否有checkbox被选中
  var ids = "";
  $("input[name='items']:checked").each(function(){
      ids += $(this).val()+",";
		flag = true;
	});
	
	if(flag)
	{
	  if(ids.length > 0)
	  {
	  	ids = ids.substring(0, ids.length-1);  //去掉最后的逗号
	  }  
	  document.itemsForm.action="${pageContext.request.contextPath}/LabRoomReservation/saveLabRoomDeviceReservationCredit?reservationId="+${reservation.id}+"&itemIds="+ids+"&remark="+remark;
	  document.itemsForm.submit();
	}
	else
	{
	  alert("至少选择一个实验项目！");
	}
}
</script>
</head>
<body>
<div class="iStyle_Conteiner">
<div class="iStyle_RightInner">
<div class="navigation">
<div id="navigation">
<ul>
	<li><a href="javascript:void(0)">实验室设备预约管理</a></li>
	<li class="end">信誉登记管理</li>
</ul>
</div>
</div>
<div class="right-content">	
	<div class="tool-box1">
		<table>
			<tr>
				<th><spring:message code="all.trainingRoom.labroom" />编号</th><td>${reservation.labRoom.labRoomNumber}</td>
				<th><spring:message code="all.trainingRoom.labroom" />名称</th>
				<td>
                 <td>${reservation.labRoom.labRoomName}</td>
                </td>
			</tr>
			<tr>
				<th>申请人</th><td>${reservation.user.cname}[${reservation.user.username}]（${reservation.user.telephone }）</td>
				<th>申请内容</th><td>${reservation.reason}</td>
			</tr>
			<%--<tr>
				<th>申请内容</th><td>${reservation.reservations}</td>
				<th>使用时间</th>
				<td>
				 <c:if test="${reservation.CAuditResult.id!=3 }"> 
				<c:if test="${reservation.CAuditResult.CCategory=='c_audit_result' && reservation.CAuditResult.CNumber!='3' }">
				<fmt:formatDate value="${reservation.begintime.time}" pattern="yyyy-MM-dd HH:mm:ss"/>--<fmt:formatDate value="${reservation.endtime.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</c:if>
				 <c:if test="${reservation.CAuditResult.id==3 }"> 
				<c:if test="${reservation.CAuditResult.CCategory=='c_audit_result' && reservation.CAuditResult.CNumber=='3' }">
				<fmt:formatDate value="${reservation.originalBegin.time}" pattern="yyyy/MM/dd HH:mm:ss"/>--<fmt:formatDate value="${reservation.originalEnd.time}" pattern="yyyy/MM/dd HH:mm:ss"/>
				</c:if>
				</td>
			</tr>
		--%></table>
	</div>
	<div id="TabbedPanels1" class="TabbedPanels">
	<!-- 标题栏  -->
	  <%--<ul class="TabbedPanelsTabGroup">
			<li class="TabbedPanelsTab selected" tabindex="0">
			<a href="${pageContext.request.contextPath}/device/labManagerReservationAudit?id=${reservation.id}"><spring:message code="all.trainingRoom.labroom" />管理员信誉登记</a>
			</li>		
	  </ul>	  
	  --%><!--内容栏  --> 
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
			<c:if test="${empty reservation.labRoomStationReservationCredits}">
					<div class="title"><spring:message code="all.trainingRoom.labroom" />管理员  </div>
						<form name="itemsForm" method="post">
							<table>								
								<tr>							
									<table class="tb" id="my_show">
	  									<thead>
	  										<tr>
	    										<th><input id="check_all" type="checkbox" onclick="checkAll();"/></th>
	    										<th>编号</a></th>
	    										<th>扣分项</a></th>
	    										<th>扣分值</th>
	  										</tr>
	 	 								</thead>
	  										<tbody>
	  										<c:forEach items="${listCreditOption}" var="curr">
	  										<tr>
	    										<td><input id="check_${curr.id}" name="items" type="checkbox" value="${curr.id}"/></td>
	    										<td>${curr.id}</td>
	    										<td>${curr.name}</td>
	    										<td>${curr.deduction}</td>
	  										</tr>
	  										</c:forEach>
	  										</tbody>
									</table>							
								</tr>
								<tr>
									<td>评语</td>
									<td><input type="text" id="remark"> </td>
									<td><input type="button" value="提交" onclick="submitForm();"/></td>
									<td><input type="button" onclick="window.history.go(-1)" value="返回"></td>
								</tr>
							</table>
							</form>	
						</c:if>
						<c:if test="${not empty reservation.labRoomStationReservationCredits}">
							<form name="itemsForm" method="post">
							<table>								
								<tr>							
									<table class="tb" id="my_show">
	  									<thead>
	  										<tr>
	    										<th>编号</a></th>
	    										<th>扣分项</a></th>
	    										<th>扣分值</th>
	  										</tr>
	 	 								</thead>
	  										<tbody>
	  										<c:forEach items="${listCreditOptions}" var="curr">
	  										<tr>
	    										<td>${curr.id}</td>
	    										<td>${curr.name}</td>
	    										<td>${curr.deduction}</td>	    										
	  										</tr>
	  										</c:forEach>				  										
	  										</tbody>
									</table>							
								</tr>
								<tr>
								评语<input type="text" value="${remark}" disabled="disabled" >
								<input type="button" onclick="window.history.go(-1)" value="返回">
								</tr>
							</table>
							</form>
						</c:if>
				</div>
		</div>	
	  </div>
	</div>
	<script type="text/javascript">
	var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
	</script>
</div>
</div>
</div>
</body>
</html>
