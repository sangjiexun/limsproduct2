<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.labappointment-resources"/>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
<meta name="decorator" content="iframe"/>
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/showLab.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/labreservation/labreservationmanagerList.js"></script>
<!-- 打印插件的引用 -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
  <script type="text/javascript">
  
  	
	function zln(s){
     if(confirm( '你真的要删除吗？ ')==false){
       return   false;
    }else{
     window.location.href="${pageContext.request.contextPath}/labreservation/labreationdelectitem?idkey="+s;
    }

}
  
	$(document).ready(function(){
	$("#print").click(function(){
	$("#list_top").jqprint();
	});

	
var s=${tage};
 if(1==s){
 $("#s1").addClass("TabbedPanelsTab selected");
 $("#s0").addClass("TabbedPanelsTab");
  $("#s2").addClass("TabbedPanelsTab");
    $("#s3").addClass("TabbedPanelsTab");
    $("#s4").addClass("TabbedPanelsTab");
 }
  if(3==s){
 $("#s3").addClass("TabbedPanelsTab selected");
 $("#s0").addClass("TabbedPanelsTab");
 $("#s1").addClass("TabbedPanelsTab ");
  $("#s2").addClass("TabbedPanelsTab");
    $("#s5").addClass("TabbedPanelsTab");
    $("#s4").addClass("TabbedPanelsTab");
 }
  if(4==s){
 $("#s4").addClass("TabbedPanelsTab selected");
 $("#s0").addClass("TabbedPanelsTab");
 $("#s1").addClass("TabbedPanelsTab ");
  $("#s2").addClass("TabbedPanelsTab");
    $("#s3").addClass("TabbedPanelsTab");
   
 }
  if(2==s){
 $("#s2").addClass("TabbedPanelsTab selected");
 $("#s0").addClass("TabbedPanelsTab");
 $("#s1").addClass("TabbedPanelsTab ");
    $("#s3").addClass("TabbedPanelsTab");
    $("#s4").addClass("TabbedPanelsTab");
 }
   if(0==s){
 $("#s0").addClass("TabbedPanelsTab selected");
 $("#s2").addClass("TabbedPanelsTab");
 $("#s1").addClass("TabbedPanelsTab ");
    $("#s3").addClass("TabbedPanelsTab");
    $("#s4").addClass("TabbedPanelsTab");
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
	function cancel()
	  {
		  window.location.href="${pageContext.request.contextPath}/public/labreservationmanage?currpage=1&tage=${tage}";
	  }
</script>
<style type="text/css">
	.content-box thead tr th, .content-box td {
		border:none;
		padding: 7px 7px;
		border-bottom: 1px solid #e4e5e7;
	}
</style>
 <!-- 打印插件的引用 -->
 <link href="${pageContext.request.contextPath}/css/iconFont.css"
	rel="stylesheet">
</head>
<body>

<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<c:if test="${flag eq 1}">
			<li class="TabbedPanelsTab ">
				<a href="${pageContext.request.contextPath}/public/labreservationmanage?tage=0&currpage=1">实验室预约查询</a>
			</li>
			<li class="TabbedPanelsTab selected">
				<a href="${pageContext.request.contextPath}/public/listTimetable?currpage=1">课表查询</a>
			</li>
		</c:if>
		<c:if test="${flag eq 0}">
			<li class="TabbedPanelsTab selected">
				<a href="${pageContext.request.contextPath}/public/labreservationmanage?tage=0&currpage=1">实验室预约查询</a>
			</li>
			<li class="TabbedPanelsTab ">
				<a href="${pageContext.request.contextPath}/public/listTimetable?currpage=1">课表查询</a>
			</li>
		</c:if>
	</ul>
<%--<ul class="nav_ul cf">--%>
		<%--<c:if test="${flag eq 1}">--%>
			<%--<li class="nav_li ">--%>
				<%--<a href="${pageContext.request.contextPath}/public/labreservationmanage?tage=0&currpage=1">实验室预约查询</a>--%>
			<%--</li>--%>
			<%--<li class="nav_li li_selected">--%>
				<%--<a href="${pageContext.request.contextPath}/public/listTimetable?currpage=1">课表查询</a>--%>
			<%--</li>--%>
		<%--</c:if>--%>
		<%--<c:if test="${flag eq 0}">--%>
			<%--<li class="nav_li li_selected">--%>
				<%--<a href="${pageContext.request.contextPath}/public/labreservationmanage?tage=0&currpage=1">实验室预约查询</a>--%>
			<%--</li>--%>
			<%--<li class="nav_li">--%>
				<%--<a href="${pageContext.request.contextPath}/public/listTimetable?currpage=1">课表查询</a>--%>
			<%--</li>--%>
		<%--</c:if>--%>
	<%--</ul>--%>
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
	<div class="content-box">
			<div class="tool-box">
                <form:form name="form" action="${pageContext.request.contextPath}/public/labreservationmanage?currpage=1&tage=${tage}" method="post" modelAttribute="labReservation">
				<ul style="position: absolute;">
					<%--
					<li>学期：<form:select path="remarks">
			        <option value="">请选择</option>
			        <c:if test="${shoolTerm!=null}">
			        <option value="${shoolTerm.id}" selected="selected">${shoolTerm.termName}</option>
			        </c:if>
					<c:forEach items="${timemap}" var="s">
					<option value="${s.id}">${s.termName}</option>
					</c:forEach>
					</form:select> </li>
					--%>
					<li>实验室:<form:input path="eventName" style="text-align:left"/> </li>
					<li>星期:<form:input path="timetableAppointment.weekday" style="text-align:left"/></li>
					<li><input type="button" onclick="expers('${pageContext.request.contextPath}/public/labreservationmanage?currpage=1&tage=${tage}')"  value="查询">
						<input class="cancel-submit" type="button" onclick="cancel()"  value="取消查询"></li>
					
				</ul>
				</form:form>
			</div>
	</div>
<div class="content-box">
<table >
<thead>
   <th>序号</th>
   <th>实验室编号</th>
   <th>实验室</th>
   <th>开始时间</th>
   <th>结束时间</th>
</tr>
</thead>

<tbody >
<c:forEach items="${labReservations}" var="s" varStatus="i">
<tr>
     <td >${i.count+(currpage-1)*pageSize}</td>
     <td >${s.labRoom.labRoomNumber}</td>
     <td>${s.lab}</td>
     <td>${s.startTime}</td>
     <td>${s.endTime}</td>
     
     

</tr>
</c:forEach>

<%-- <tr> 
                    <td colspan="8" align="left" >
					  共 ${totalRecords}条记录, 总页数:${pageModel.totalPage}页 <input type="hidden" value="${pageModel.lastPage}" id="totalpage" />&nbsp;
                  <input type="hidden" value="${currpage}" id="currpage" /><input type="hidden" value="${tage}" id="tage" />
                      <a href="javascript:void(0)" onclick="first('${pageContext.request.contextPath}/public/labreservationmanage?tage=${tage}&currpage=1')" target="_self">首页</a>				    
                      <a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/public/labreservationmanage?tage=${tage}'+'&currpage=')" target="_self">上一页</a>
                      <a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/public/labreservationmanage?tage=${tage}'+'&currpage=')" target="_self">下一页</a>
                      <a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/public/labreservationmanage?tage=${tage}&currpage=${pageModel.totalPage}')" target="_self">末页</a>				   
                  </td>
                </tr>	 --%>

</table>
</div></div></div></div>
</body>
</html -->