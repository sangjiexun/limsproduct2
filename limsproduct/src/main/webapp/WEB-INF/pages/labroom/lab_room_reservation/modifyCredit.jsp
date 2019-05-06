<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html >  
<head> 
<meta name="decorator" content="iframe"/> 
<title><fmt:message key="html.title"/></title>

  <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
  
  <!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />

<!-- 下拉的样式结束 -->
  
</head>
<body>
<div class="navigation">
<div id="navigation">
<ul>
	<li><a href="javascript:void(0)">信誉积分管理</a></li>
	</ul>
</div>
</div>
<!-- 结项申报列表 -->
<div id="content" >
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
	<form:form action="${pageContext.request.contextPath}/labRoom/saveCreditUser" id="myForm" method="POST" modelAttribute="user">

      <table id="viewTable" cellspacing="0" cellpadding="0">
      	
			<tr>
				<td class="label" >姓名 </td>
			    <td>${user.cname }</td>
    		    <form:hidden path="username"/>
    		</tr>
    		<tr>
				<td class="label" >信誉积分 </td>
			    <%--<td>< path="creditScore"/></td>--%>
				<td>${user.creditScore }</td>
				<td><select name="reservationsType" id="reservationsType" class="chzn-select">
					<c:forEach items="${labReservations}" var="current" varStatus="i">
						<option value="labReservation+${current.id }">${current.id } ${current.labRoom.labRoomName }
								<fmt:formatDate value="${current.lendingTime.time }" pattern="yyyy-MM-dd"/>
								<fmt:formatDate value="${current.startTime.time }" pattern="HH:mm"/> -
								<fmt:formatDate value="${current.endTime.time }" pattern="HH:mm"/>
								${current.lendingReason }</option>
					</c:forEach>
					<c:forEach items="${labRoomDeviceReservations}" var="current" varStatus="i">
						<option value="labRoomDeviceReservation+${current.id }">${current.id } ${current.labRoomDevice.schoolDevice.deviceName }
							<fmt:formatDate value="${current.time.time }" pattern="yyyy-MM-dd"/>
							<fmt:formatDate value="${current.begintime.time }" pattern="HH:mm"/> -
							<fmt:formatDate value="${current.endtime.time }" pattern="HH:mm"/>
								${current.content }</option>
					</c:forEach>
					<c:forEach items="${labRoomStationReservations}" var="current" varStatus="i">
						<option value="labRoomStationReservation+${current.id }">${current.id } ${current.labRoom.labRoomName}
							<fmt:formatDate value="${current.time.time }" pattern="yyyy-MM-dd"/>
							<fmt:formatDate value="${current.begintime.time }" pattern="HH:mm"/> -
							<fmt:formatDate value="${current.endtime.time }" pattern="HH:mm"/> ${current.reason }</option>
					</c:forEach>
				</select></td>
    		</tr>
	  </table>
		<table>
			<c:forEach items="${creditOptions}" var="current" varStatus="i">
				<c:if test="${i.index%2==0}">
					<tr style="background: #f2f2f2">
			     		<td style="float:left;padding-left:50px;">${current.name }</td>
			     		<td>${current.deduction }<input type="checkbox" style="margin-left: 50px;" name="deduction" value="${current.id }"></td>
		    		</tr>
				</c:if>
				<c:if test="${i.index%2==1}">
					<tr style="background: #fff">
			     		<td style="float:left;padding-left:50px;">${current.name }</td>
			     		<td>${current.deduction }<input type="checkbox" style="margin-left: 50px;" name="deduction" value="${current.id }"></td>
		    		</tr>
				</c:if>
			</c:forEach>
		</table>
		<input type="hidden" name="sumDeduction" id="sumDeduction"/> 
		<div style="float: right;padding: 20px;">
			<%--<a>本次扣分15分 信誉积分累计55分</a>--%>
			<input type ="button" onclick="getSelect()" value="提交"/>
			<a class="btn btn-common" onclick="window.history.go(-1)" href="javascript:void(0)">取消
			</a>

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
    
    function getSelect(){
      var obj = document.getElementsByName("deduction");//选择所有name="interest"的对象，返回数组  
      var s='';//如果这样定义var s;变量s中会默认被赋个null值
      for(var i=0;i<obj.length;i++){
         if(obj[i].checked) //取到对象数组后，我们来循环检测它是不是被选中
         s+=obj[i].value+',';  //如果选中，将value添加到变量s中  
      }
      $("#sumDeduction").val(s);
      $("#myForm").submit();
    }
    
</script>
<!-- 下拉框的js -->

</form:form>			
</div>
</div>
</div>
</div>
</div>
</div>
</body>			

<!-------------列表结束----------->
</html>