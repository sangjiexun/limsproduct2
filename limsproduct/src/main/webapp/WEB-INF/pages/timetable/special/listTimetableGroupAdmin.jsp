<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<fmt:setBundle basename="bundles.lab-resources" />
<html ng-app="app">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css"> 
	<%--<link href="${pageContext.request.contextPath}/static/css/lib.css" rel="stylesheet" type="text/css" media="">
	--%><link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/global.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/chosen.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/pignose.tab.min.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/chosen.jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/tab_slider.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/pignose.tab.min.js"></script>	
	<link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.zh.js"></script>		
	<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
    --%>    
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/cmsSystem/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/iCheck/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/sweetalert/sweetalert.min.js"></script>
	<meta name="decorator" content="none" />
	<style>	
	    .content-box .tabb{
	        width:100%;
	        left:0;
	    }
	    .content-box .tabb thead tr th{
	        background:#f9f9f9;
	    }
	    .content-box .tabb tr:nth-child(odd) td,
	    .content-box .tabb tr:nth-child(even) td{
	        background:#fff!important;
	    }
	    .content-box .tabb thead tr th, .content-box .tabb td {
            border:1px solid #e4e5e7!important;
        }
        .fl49{
            float:left;
            width:49.5%!important;
        }
        .fr49{
            float:right;
            width:49.5%!important;
        }
        .ovh_box{
            width:98%;
            margin:0 auto 1%;
            position:relative;
        }
        .right_box{
            position:relative;
            margin:0 0 1%;
            background:#fff;
            z-index:2;
        }
        .orange{
            color: #fe980b;
        }
        .spin{
            display:none;
            position: absolute;
            top: 17px;
            right: 25%;
            margin: 0 -20px 0 0;
            font-size: 40px;
            color: #a8d0f3;
            width: 40px;
            text-align: center;
            height: 40px;
            line-height: 40px;
        }
	</style>
</head>

<body>
<div class="dmrrr">
<div class="right-content">
<div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">预约排课</a></li>
	    <li><a href="javascript:void(0)">我的排课</a></li>
	    <li class="end"><a href="javascript:void(0)">分批排课</a></li>
	  </ul>
	</div>
  </div>
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<div class="title">课程详情及安排
	<a class="btn btn-new" href="${pageContext.request.contextPath}/timetable/mylistCourseDetails?currpage=1">返回</a>
	<%--<a class="btn btn-new course_new" href="${pageContext.request.contextPath}/timetable/newTimetableBatch/${courseNo}/-1/0">新建分批排课</a></div>--%>
	<a class="btn btn-new course_new" href="#/newTimetableBatch/${courseNo}/-1/0">新建分批排课</a></div>
<table class="courseinfo_tab">
        <tr>
            <th>教务系统排课安排</th>
            <td>${coursePlan }</td>
            <th>总学时</th>
            <td>${courseInfo.totalHours }</td>
            <th>实验学时</th>
            <td>${courseInfo.theoreticalHours }</td>
        </tr>
        <tr>
            <th>操作</th>
            <td colspan="5"><a view-student>查看学生名单</a><a view-item>查看本课程实验项目</a></td>
        </tr>
</table>
<div class="ovh_box">
    <i class="fa fa-spinner fa-spin spin"></i>
	<table class="tabb">
		<thead>
		<tr>
		   <th>批次</th>
		   <th>选课时间</th>
		   <th>组名</th>
		   <th>选课形式</th>
		   <th>已选/人数</th>
		   <th>操作</th>
		</tr>
		</thead>
		<tbody>	
		<!-- 判断相同批次的当前批次id -->
		<c:set var="ifRowspanBatch" value="0"></c:set>		
		<c:forEach items="${groups}" var="current"  varStatus="i">
		<!--合并相同批次的变量  -->
		<c:set var="rowspanBatch" value="0"></c:set>  
		   
		<!--合并相同批次的变量计数  -->
		     <c:forEach items="${groups}" var="current1"  varStatus="j">
		         <c:if test="${current1.timetableBatch.id==current.timetableBatch.id }">
		            <c:set value="${rowspanBatch + 1}" var="rowspanBatch" />
		         </c:if>
		     </c:forEach>
		<tr>
			<c:if test="${ifRowspanBatch!=current.timetableBatch.id }">
		     <td rowspan="${rowspanBatch }">${current.timetableBatch.batchName}</td>
		     <c:if test="${current.timetableBatch.ifselect eq 1 }">
			     <td rowspan="${rowspanBatch }">从<fmt:formatDate value="${current.timetableBatch.startDate.time }" pattern="yyyy-MM-dd" /><br>到
			     <fmt:formatDate value="${current.timetableBatch.endDate.time }" pattern="yyyy-MM-dd" />
			     </td>
		     </c:if>
		     <c:if test="${current.timetableBatch.ifselect eq 0 }">
			     <td rowspan="${rowspanBatch }">无需选课</td>
		     </c:if>
			</c:if>
		     <td><a class="course_btn" title="排课/排课记录" href="#/doGroupTimetable/${courseNo}/${current.id}/1">${current.groupName}</a></td>
		     <td>
		     	<c:if test="${current.timetableBatch.ifselect eq 0}">随机</c:if>
		     	<c:if test="${current.timetableBatch.ifselect eq 1}">自选</c:if>
		     </td>
		     <td><a view-group-student='${current.id}' >${current.timetableGroupStudentses.size()}</a>/${current.numbers}</td>
		     <c:if test="${ifRowspanBatch!=current.timetableBatch.id }">
			     <td rowspan="${rowspanBatch }">
				     <c:if test="${current.timetableBatch.ifselect eq 0}">
					     <a title="更新学生名单" class="fa fa-refresh g9 f14 mr5 poi" href='#' onclick='updateTimetableStudentsGroup(${current.id})'></a>
				     </c:if>
				     <a title="编辑分批" href="#/editTimetableBatchGroup/${courseNo}/${current.timetableBatch.id}" class="fa fa-edit g9 f14 mr5 poi"></a>
		    		 <i title="删除分批" data-del='${current.timetableBatch.id}' class="fa fa-trash-o g9 f14 mr5 poi"></i>
			     </td>
		     </c:if>
		     <c:set var="ifRowspanBatch" value="${current.timetableBatch.id }"></c:set>
		</tr>
		</c:forEach> 
		</tbody>
		</table>
<div class="right_box fr49" ng-view></div>
</div>
</div>
</div>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/node_modules/angular/angular.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/node_modules/angular-route/angular-route.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app.js"></script>
<script type="text/javascript">
$(function () {
    $(".slide_box").tabslider({
        handler: function (s) {}
    })
    
 	// 查看本课程实验项目
    $('[view-item]').each(function(i,e){
         $(e).on('click',function(){
	         layer.open({
             	type: 2,
             	title: '查看本课程实验项目',
             	maxmin: true,
             	shadeClose: true, 
             	area : ['700px' , '350px'],
             	content: '${pageContext.request.contextPath }/operation/listOperationItemByCourse?courseNumber=${courseNumber}'
             })  
	     });                                              
	});
    
 	// 查看本选课组下的学生
    $('[view-student]').each(function(i,e){
         $(e).on('click',function(){
	         layer.open({
             	type: 2,
             	title: '查看本选课组下的学生',
             	maxmin: true,
             	shadeClose: true, 
             	area : ['700px' , '350px'],
             	content: '${pageContext.request.contextPath }/timetable/viewCourseStudent?courseCode=${courseNo}'
             })  
	     });                                              
	});
    
 	// 查看分组名单
    $('[view-group-student]').each(function(i,e){
         $(e).on('click',function(){
	         layer.open({
             	type: 2,
             	title: '查看本选课组下的学生',
             	maxmin: true,
             	shadeClose: true, 
             	area : ['700px' , '350px'],
             	content: '${pageContext.request.contextPath }/timetable/viewCourseGroupStudent?id='+$(e).attr('view-group-student'),
             	end: function () {
                    parent.location.reload();
                }
             })  
	     });                                              
	});
});

//更新分组名单
function updateTimetableStudentsGroup(groupId){
    $.ajax({
        url:"${pageContext.request.contextPath}/timetable/updateTimetableStudentsGroup?groupId=" + groupId ,
        type:"post",
        success:function(data){
            if(data=="success"){
                alert("学生名单更新完成！")
                window.location.reload();
            }else{
                alert("未能更新！")
            }
        }
    });
}
// 删除分批
$('[data-del]').each(function(i,e){
   $(e).on('click',function(){
     var _index=$(this);
         swal({
                title: "您确定要删除这条信息吗",
                text: "删除后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                cancelButtonText:"取消",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "删除",
                closeOnConfirm: false
   }, function () {
      $.ajax({ 
           url: '${pageContext.request.contextPath}/timetable/deleteTimetableBatch?batchId='+$(e).attr('data-del'), 
           // type: 'DELETE'
           type: 'POST'
       }).done(function(data) {
           location.reload();
       }).error(function(data) { 
           swal("OMG", "删除操作失败了呢!", "error"); 
       }); 
   });
 });                                              
});
$(".course_btn").click(function() {
	$(this).addClass("orange");
	$(this).parents().parents().siblings("tr").find(".course_btn").removeClass("orange");
	$(".spin").fadeIn(400);
	$(".tabb").addClass("fl49");
	$(".right_box").addClass("fr49");
	$(".batch_box").hide();
	$(".time_box").show();
});
$(".course_new").click(function() {
	$(".spin").fadeIn(400);
	$(".tabb").addClass("fl49");
	$(".right_box").addClass("fr49");
	$(".time_box").hide();
	$(".batch_box").show();
});
</script>
</body>
</html>

