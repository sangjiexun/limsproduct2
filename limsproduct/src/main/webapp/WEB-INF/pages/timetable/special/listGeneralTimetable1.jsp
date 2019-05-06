<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html >
<head>
<meta name="decorator" content="iframe"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/timetable/lmsReg.css">

<link href="${pageContext.request.contextPath}/css/cmsSystem/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/iCheck/icheck.min.js"></script>
<script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
<script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/sweetalert/sweetalert.min.js"></script>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/appointment_schedule.css" />

<!-- 打印开始 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<!-- 打印结束 -->
<!-- 下拉框的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
<style type="text/css">
	table{width:100%;}
	.array{width:100%;
		word-break:break-all;}
		td{border:solid 1px #add9c0;}
 br
 {mso-data-placement:same-cell;}
 
 .topselect{
 		float:left;
 		width:150px;
 		margin:10px auto;
 }
 .topselect  input{
    /* width: 70px; */
    padding: 0 8px;
    height: 22px;
    line-height:22px;
    background: #77bace;
    color: #333;
    border-radius: 7px;
    border: 0;
    margin-left:10px;
 }
</style>
</head>

<body>
<div class="main_container cf rel w95p ma mb60">
<!--userPermission -->
<div class="sit_module width_quarter" style="position: absolute;height: 500px;margin-top: 0px;display: none">
   
    <div class="message_list" style="height: 450px">
        <div style="border: 0px;margin:0px auto;width:98%;height:90%;">
        </div>
    </div>
</div>
<!-- right maincontent -->
<div class="sit_maincontent" style="width: 99%; height: 800px;">

<div id="myShow">

<table class="dm_right" cellspacing="1">
<tbody>
<tr>
    <th class="tbh" width="10%">周次</th>
    <c:forEach items="${cycleTimetableBycourseNo}" var="current">
    <th class="tbh" width="10%">${current.operationItem.lpName}</th>
    </c:forEach>
    
</tr>
<c:forEach var="currWeek" varStatus="cStatus" items="${listWeek}">
    <!-- 判断奇数偶数，以确定日历背景样式 -->
    <c:if test="${cStatus.count%2!=0}">
    <tr>
    </c:if>
    <c:if test="${cStatus.count%2==0}">
    <tr style="background:#FEFFDA;" >
    </c:if>

         <td class="tbl tbct"  style="width:60px">第${currWeek}周</td>  
       
        <c:forEach items="${cycleTimetableBycourseNo}" var="currItem"  varStatus="iWeek">
        <td class="tb" valign="top" do-labReservation='${iWeek.count},${cStatus.index}'>
                <c:forEach var="itemRelated" items="${itemRelated}" varStatus="lStatus">
                   <c:if test="${itemRelated.operationItem.id==currItem.operationItem.id}">
                        <c:if test="${itemRelated.timetableAppointment.startWeek==currWeek}">
                       		<%--  项目：${itemRelated.operationItem.lpName}<br>
                       		 周次：${itemRelated.timetableAppointment.startWeek}<br> --%>
                          <%-- 	 
                                                                         实验室：<c:forEach var="rTimetable" items="${itemRelated.timetableAppointment.timetableLabRelateds}" varStatus="tStatus">
                            ${rTimetable.labRoom.labRoomName}
                            </c:forEach><br>
                                                                        教师：<c:forEach var="tTimetable" items="${itemRelated.timetableAppointment.timetableTeacherRelateds}" varStatus="tStatus">
                            ${tTimetable.user.cname}
                            </c:forEach> <br> --%>
                              <c:forEach items="${itemRelated.timetableAppointment.timetableGroups}" var="curr">
                                  <c:choose>
                                 <c:when test="${isBatch==0}">
                                 <c:if test="${curr.timetableBatch.ifselect==3}">
                                 ${curr.timetableBatch.batchName}
                                 </c:if>
                                 </c:when>
                                 <c:otherwise>
                                  <c:if test="${curr.timetableBatch.ifselect==4}">
                                 ${curr.timetableBatch.batchName}
                                 </c:if>
                                 </c:otherwise>
                                 </c:choose>
                                </c:forEach><br>
                            	<c:forEach items="${itemRelated.timetableAppointment.timetableGroups}" var="curr">
                            	 <c:choose>
                            	   <c:when test="${isBatch==0}">
                                 <c:if test="${curr.timetableBatch.ifselect==3}">
                                 ${curr.groupName}
                                 </c:if>
                                 </c:when>
                                  <c:otherwise>
                                   <c:if test="${curr.timetableBatch.ifselect==4}">
                                 ${curr.groupName}
                                 </c:if>
                                  </c:otherwise>
                                 </c:choose>
                                </c:forEach>
                             <br>
                        </c:if>
                    </c:if>
               </c:forEach> 
        </td>
        </c:forEach>
    </tr> 
</c:forEach>
</table>
<input  type=button value=返回 onclick="window.history.go(-1)">
</div>
</div>
</div>
<script type="text/javascript">
/* $(document).ready(function(){
      $('#fullview').click(function(){
           $('.sit_sider_bar').animate( { width:'0'}, 500 );
           $('.sit_maincontent').animate( { width:'100%'}, 500 );
           $('.toggle,.toggleLink,#fullview,.sit_footer,.sit_sider_bar > h3').css("display","none");
           $('#fullview1').css("display","inline");
      });
  
      $('#fullview1').click(function(){
           $('.sit_sider_bar').animate( { width:'23%'}, 500 );
           $('.sit_maincontent').animate( { width:'75%'}, 500 );
           $('#fullview1').css("display","none");
           $('.toggle,#fullview,.toggleLink,.sit_footer,.sit_sider_bar > h3 ').css("display","inline");
      });
      
      $('#myPrint').click(function(){
           $('#myShow').jqprint();
      });
});
                              
$(function(){
      var height = $(document).height();
      $(".sit_sider_bar").css('height',height);
      $(".sit_maincontent").css('height',height);
}) ;

$(function(){
    $("#showTimetable").window({
        top: ($(window).width() - 800) * 0.5 ,
        left: ($(window).width() - 1000) * 0.5   
    })
    $(".sit_maincontent").css('height',600);
})

	

	//如果为查询则提交查询页面，如果为电子表格导出，则导出excel
		function subform(gourl){ 
		 form.action=gourl;
		 form.submit();
		}
	//导出excel
	function exportExcel()
	{
		document.form.action = "${pageContext.request.contextPath}/report/teachingReport/exportWeekRegister";
		document.form.submit();
	}
	
	
	
	$(".week_box").click(
		function() {
			$(this).addClass("week_box_select").siblings().removeClass("week_box_select");
		}
	); */
</script>
<!-- 下拉框的js -->
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
</body>
</html>