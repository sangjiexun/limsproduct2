<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%--<fmt:setBundle basename="bundles.labappointment-resources"/>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
<meta name="decorator" content="iframe"/>
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/labreservation/labreservationmanagerList.js"></script>
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
    $("#s3").addClass("TabbedPanelsTab");
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
		window.location.href="${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?currpage=1&tage=0&isaudit=${isAudit}";
	}
	
	//弹出学生生名单
	  function showStudentList(id){
		  layer.ready(function(){
		        layer.open({
		            type: 2,
		            title: '学生名单',
		            fix: true,
		            maxmin:true,
		            shift:-1,
		            closeBtn: 1,
		            shadeClose: true,
		            move:false,
		            maxmin: false,
		            area: ['1000px', '420px'],
		            content: '../LabRoomReservation/showStudentList?id='+id,
		            end: function(){
		            }
		        });
		    });
	  }
	//转到实验室预约
	function fromStation(){
		window.location.href="${pageContext.request.contextPath}/labRoomLending/labReservationList?tage=0&page=1&isaudit=${isAudit}"
	}
  //转到实验室预约
  function openAuditWindow(id, businessId){
      layer.ready(function(){
          var index = layer.open({
              type: 2,
              title: '审核',
              fix: true,
              maxmin:true,
              shift:-1,
              closeBtn: 1,
              shadeClose: true,
              move:false,
              area: ['1000px', '420px'],
              content: '../auditing/auditList?businessUid='+ businessId + '&businessAppUid='+id,
              end: function(){
                  window.location.reload();
              }
          });
          layer.full(index);
      });
  }
</script>

</head>
<body>
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">开放预约 </a></li>
			<li class="end"><a href="javascript:void(0)"><spring:message code="left.station.appointment" />预约</a></li>
		</ul>
	</div>
</div>
<div id="TabbedPanels1" class="TabbedPanels">
<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
    <li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList?currpage=1"><spring:message
            code="all.trainingRoom.station"/>预约</a>
    </li>
    <li class="TabbedPanelsTab" id="s2"><a
            href="${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?tage=0&currpage=1&isaudit=2">我的申请</a>
    </li>
    <sec:authorize ifNotGranted="ROLE_STUDENT">
        <li class="TabbedPanelsTab" id="s3"><a
                href="${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?tage=0&currpage=1&isaudit=1">我的审核</a>
        </li>
    </sec:authorize>
</ul>
	<div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
            <div class="content-box">
                <%--工位预约/实验室预约分拆--%>
                <%--<input type="radio" name="isStation" onclick="fromStation()"/><spring:message
                    code="all.trainingRoom.labroom"/>预约
                <input type="radio" name="isStation" checked="checked"/>工位预约--%>
                <div class="tool-box">
                    <form:form name="form"
                               action="${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?currpage=1&tage=${tage}&isaudit=${isAudit}"
                               method="post" modelAttribute="labRoomStationReservation">
                        <ul style="position: absolute;">
                            <li><spring:message code="all.trainingRoom.labroom"/>:<form:input
                                    path="labRoom.labRoomName"/></li>
                            <li>审核状态:
                                <form:select id="auditStatus" name="auditStatus" path="result">
                                    <%--<c:forEach items="${auditStatus}" var="as">--%>
                                    <c:if test="${auditStatus == 2}">
                                        <option value="2" selected>审核中</option>
                                        <option value="1">审核通过</option>
                                        <%--<option value="3">未审核</option>--%>
                                        <option value="4">审核拒绝</option>
                                        <option value="5">所有</option>
                                    </c:if>
                                    <c:if test="${auditStatus == 1}">
                                        <option value="2">审核中</option>
                                        <option value="1" selected>审核通过</option>
                                        <%--<option value="3">未审核</option>--%>
                                        <option value="4">审核拒绝</option>
                                        <option value="5">所有</option>
                                    </c:if>
                                    <%--<c:if test="${auditStatus == 3}">
                                        <option value="2">审核中</option>
                                        <option value="1">审核通过</option>
                                        <option value="3" selected>未审核</option>
                                        <option value="4">审核拒绝</option>
                                        <option value="5">所有</option>
                                    </c:if>--%>
                                    <c:if test="${auditStatus == 4}">
                                        <option value="2">审核中</option>
                                        <option value="1">审核通过</option>
                                        <%--<option value="3">未审核</option>--%>
                                        <option value="4" selected>审核拒绝</option>
                                        <option value="5">所有</option>
                                    </c:if>
                                    <c:if test="${auditStatus == 5}">
                                        <option value="2">审核中</option>
                                        <option value="1">审核通过</option>
                                        <%--<option value="3">未审核</option>--%>
                                        <option value="4">审核拒绝</option>
                                        <option value="5" selected>所有</option>
                                    </c:if>
                                </form:select>
                            </li>
                            <li><input type="submit" value="查询">
                                <input class="cancel-submit" type="button" onclick="cancleQuery()" value="取消"></li>
                        </ul>
                    </form:form>
                </div>
            </div>
<div class="content-box">
<table >
<thead>
   <th>序号</th>
   <th>预约<spring:message code="all.trainingRoom.labroom" /></th>
   <th style="width:25%">内容</th>
   <th >申请人</th>
   <th >预约工位数</th>
   <th >学生名单</th>
   <th >预约日期</th>
   <th >预约时间</th>
   <th >审核人情况</th>
   <th >审批状态</th>
   <th colspan="2">操作</th>  
</tr>
</thead>

<tbody >
<c:forEach items="${listLabRoomStationReservation}" var="s" varStatus="i">
<tr>
     <td >${i.count+(currpage-1)*pageSize}</td>
     <td>${s.labRoom.labRoomName}</td>
     <td><p>${s.reason}</p></td>
     <td>${s.user.cname}</td>
     <td>${s.stationCount}</td>
     <td><a onclick="showStudentList(${s.id})" title="查看学生名单">查看学生名单</a></td>
     <td><fmt:formatDate value="${s.reservation.time}" pattern="yyyy-MM-dd"/></td>
     <td><fmt:formatDate value="${s.startTime.time}" pattern="HH:mm"/>至<fmt:formatDate value="${s.endTime.time}" pattern="HH:mm"/></td>
     <td>${auditItems[i.count-1]}</td>
     <td>
     	 <c:if test="${s.result==1}">审核通过</c:if>
         <c:if test="${s.result==2}">审核中</c:if>
         <c:if test="${s.result==4}">审核拒绝</c:if>
         <c:if test="${s.result==3}">未审核</c:if> 
     </td>
     <td>
     <c:if test="${s.buttonMark eq 0}">
     	 <%--<a href="javascript:void(0)" onclick="openAuditWindow('${s.id}', '<c:if test="${isGraded}">${s.labRoom.labRoomLevel}</c:if>StationReservation');">查看</a>--%>
         <a href="javascript:void(0)" onclick="openAuditWindow('${s.id}', ${s.labRoom.id});">查看</a>
     </c:if>
     <c:if test="${s.buttonMark ne 0}">
     	 <%--<a href="javascript:void(0)" onclick="openAuditWindow('${s.id}', '<c:if test="${isGraded}">${s.labRoom.labRoomLevel}</c:if>StationReservation');">审核</a>--%>
         <a href="javascript:void(0)" onclick="openAuditWindow('${s.id}',${s.labRoom.id});">审核</a>
     </c:if>
     <c:if test="${s.result==1}">
     <%--实验室管理员才可看到--%>
     <c:forEach items="${s.labRoom.labRoomAdmins}" var="admin">
     <c:if test="${admin.user.username eq user.username}">
         <a href="${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationCredit?id=${s.id}">信誉登记</a>
     </c:if>
     </c:forEach>
     </c:if>
     </td>
</tr>
</c:forEach>
</table>

<div class="page"> 
					  共 ${totalRecords}条记录, 总页数:${pageModel.totalPage}页 <input type="hidden" value="${pageModel.lastPage}" id="totalpage" />&nbsp;
                  <input type="hidden" value="${currpage}" id="currpage" /><input type="hidden" value="${tage}" id="tage" />
                      <a href="javascript:void(0)" onclick="first('${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?tage=${tage}&currpage=1&isaudit=${isAudit}')" target="_self">首页</a>				    
                      <a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?tage=${tage}&isaudit=${isAudit}'+'&currpage=')" target="_self">上一页</a>
                      <a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?tage=${tage}&isaudit=${isAudit}'+'&currpage=')" target="_self">下一页</a>
                      <a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?tage=${tage}&isaudit=${isAudit}&currpage=${pageModel.totalPage}')" target="_self">末页</a>				   
               </div>	
</div></div></div>
</body>
</html -->