<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<fmt:setBundle basename="bundles.lab-resources" />
<html ng-app="app">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css"> 
	<link href="${pageContext.request.contextPath}/static/css/lib.css" rel="stylesheet" type="text/css" media="">
	<link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/global.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/chosen.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/pignose.tab.min.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/chosen.jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/tab_slider.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/pignose.tab.min.js"></script>	
	<link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.zh.js"></script>		
	<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
	    --%><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css"> 
	<link href="${pageContext.request.contextPath}/css/cmsSystem/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/iCheck/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/sweetalert/sweetalert.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.cookie.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<meta name="decorator" content="none" />
	<script>
//判断浏览器
var Browser=new Object();
Browser.userAgent=window.navigator.userAgent.toLowerCase();
Browser.ie=/msie/.test(Browser.userAgent);
Browser.Moz=/gecko/.test(Browser.userAgent);
 
//判断是否加载完成
function Imagess(url,imgid,callback){  
  var val=url;
  var img=new Image();
  if(Browser.ie){
    img.onreadystatechange =function(){ 
      if(img.readyState=="complete"||img.readyState=="loaded"){
        callback(img,imgid);
      }
    }    
  }else if(Browser.Moz){
    img.onload=function(){
      if(img.complete==true){
        callback(img,imgid);
      }
    }    
  }  
  //如果因为网络或图片的原因发生异常，则显示该图片
  img.onerror=function(){img.src='http://www.baidu.com/img/baidu_logo.gif'}
  img.src=val;
}
 
//显示图片
function checkimg(obj,imgid){
document.getElementById(imgid).src=obj.src;
}
//初始化需要显示的图片，并且指定显示的位置
window.onload=function initPage()   
{   
    var objLoading = document.getElementById("LoadingBar");   
    if (objLoading != null)   
    {   
        objLoading.style.display = "none";   
    }   
    $("#LoadingBar .fa-spinner").css("display","none")
}  
</script>
	<style type="text/css">
	    .main_container{
	    width:100%;
	    max-width:none;
	    margin:0;
	    }
	    .btn{
	    font-size: 12px;
        margin: 0 3px 0 0;
        padding: 0 4px;
        box-sizing: content-box;
        border:none;
        box-shadow:none;
	    }
		.course_select{
			background:#6d8dd5;
			color:#fff;
		}
		.main_container .fa-spinner{
		position: absolute;
        font-size: 50px;
        color: #e8f1f9;
        text-shadow: 2px 1px 0px rgba(0,0,0,0.2);
        right:29.3%;
        top: 220px;
        z-index:1;
        }
        #LoadingBar{
        position:fixed;
        text-align:center;
        width:100%;
        height:100%;
        background:rgba(0,0,0,0.4);
        z-index:9999;
        }
        #LoadingBar .fa-spinner{
        position: relative;
        font-size: 70px;
        color: #e8f1f9;
        top: 50%;
        z-index:1;
        }
        table th,
        table td{
        font-size:12px;
        }
        table th{
        font-weight:bold;
        }
        table a{
        margin:0 7px 0 0;
        }
        table a:last-child{
        margin:0;
        }
	</style>
</head>

<body>
<div class="navigation">
<div id="navigation">
<ul>
    <li><a href="javascript:void(0)"><spring:message  code="left.timetable.appointment"/></a></li>
    <li class="end"><a href="javascript:void(0)"><spring:message  code="left.timetable.mine"/></a></li>
</ul>
</div>
</div>
<%--<div id="LoadingBar"><i class="fa fa-spinner fa-spin"></i>正在载入，请稍候……</div>--%><%--此处为页面加载--%>
<div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab" id="s1"><a
				  href="${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=1"><spring:message
				  code="left.course.library"/></a></li>
		  <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
			  <li class="TabbedPanelsTab" id="s2"><a
					  href="${pageContext.request.contextPath}/timetable/listCourseDetails1?currpage=1&id=-1&flag=1"><spring:message
					  code="left.timetable.group.theory"/></a></li>
			  <li class="TabbedPanelsTab" id="s3"><a
					  href="${pageContext.request.contextPath}/timetable/listCourseDetails1?currpage=1&id=-1&flag=2"><spring:message
					  code="left.timetable.group.experiment"/></a></li>
		  </c:if>
		  <li class="TabbedPanelsTab selected iStyle_Mark iStyle_ActiveMark" id="s4"><a
				  href="${pageContext.request.contextPath}/timetable/mylistCourseDetails?currpage=1"><spring:message
				  code="left.timetable.mine"/></a></li>
	  </ul>
    <div class="TabbedPanelsContent">  
      <div class="content-box">
      <%--<div class="title">我的排课列表</div>--%>
          <i style="display:none;" class="fa fa-spinner fa-spin"></i>
<div class="tool_box cf bgg pa5">
	<form:form name="queryForm" method="Post" action="${pageContext.request.contextPath}/timetable/mylistCourseDetails?currpage=1">
	       <select class="chosen-select" id="term" name="term" tabindex="2">
		       <c:forEach items="${schoolTerms}" var="current">
		    	    <c:if test="${current.id eq term}">
		    	       <option value ="${current.id}" selected="selected">${current.termName} </option>
		    	    </c:if>
		    	    <c:if test="${current.id ne term}">
		    	       <option value ="${current.id}" >${current.termName} </option>
		    	    </c:if>
		        </c:forEach>
	        </select>
			<select class="chosen-select" name="courseNo" tabindex="2">
				<option value="">课程名</option>
				<c:forEach items="${courseName}" var="current"	varStatus="i">
					<c:if test="${current.key eq courseNo && not empty current.value}">
						<option value="${current.key }" selected="selected">${current.value }</option>
					</c:if>
					<c:if test="${current.key ne courseNo}">
						<option value="${current.key}">${current.value}</option>
					</c:if>
				</c:forEach>
			</select>
		<!-- <a class="btn r" href="#/listSelfSchoolCourseInfo/1">自主</a> -->
		<a class="btn r" onclick="cancel()" href="#">取消</a>
		<input class="btn r" type="submit" value="查询">
	</form:form>
</div>
   
<!-- 	<div class="cf lh20 mb5">
        <div class="l w33p tc t_e">教务处推送理实一体课</div>
        <div class="l w33p tc t">教务处推送实验课</div>
        <div class="l w33p tc o">自主排课</div>
    </div> -->
	<table>
	<thead>
	<tr>
		<th>序号</th>
		<th>课程名称</th>
		<th>选课组名称</th>
		<th>课程代码</th>
		<th>学分</th>
		<th>学生容量</th>
		<th>负责教师</th>
		<th>排课状态</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${courseDetailPage}" var="current" varStatus="i">
	
	<tr>
		<td>${i.count}</td>
		<td>${current[10]}</td>
		<td>${current[0]}</td>
		<td>${current[1]}</td>
		<td>${current[2]}</td>
		<!-- 学生名单  -->
	<td><a href='javascript:void(0)'
		onclick='listTimetableStudents("${current[1]}")'
		>名单：
		<c:choose>
		<c:when test="${not empty current[7]}">
		${current[7]}
		</c:when>
		<c:otherwise>
		0
		</c:otherwise>
		</c:choose>
		</a></td>
		<td>${current[4]}[${current[5]}]</td>
		<td>${currentStates[i.count-1]}</td>
		<td>
		<c:if test="${currentStates[i.count-1] eq '未排课'}">
				<a class="" href="${pageContext.request.contextPath}/timetable/doAdminTimetable/${current[8]}">教务</a>
				<a class="" href="${pageContext.request.contextPath}/timetable/listTimetableGroupAdmin?courseNo=${current[8]}">分组</a>
				<a class="" href="${pageContext.request.contextPath}/timetable/listTimetableCycle?courseNo=${current[8]}&courseCode=${current[8]}">循环排课</a>
		</c:if>
			<c:if test="${currentStates[i.count-1] eq '教务排课中'}">
				<a class="" href="javascript:void(0)" onclick="openWindow('${current[8]}')">教务排课</a>
				<a class="" href="javascript:void(0)" onclick="confirmTimetableAppointment('${current[8]}')">确认排课</a>
				<a class="" href="javascript:void(0)" onclick="removeTimetableAppointment('${current[8]}')">取消排课</a>
				<a class="" href="${pageContext.request.contextPath}/timetable/doAdminTimetable/${current[8]}">编辑</a>
			</c:if>
			<c:if test="${currentStates[i.count-1] eq '分组排课中'}">
				<a class="" href="javascript:void(0)" onclick="openWindow('${current[8]}')">分组排课</a>
				<a class="" href="javascript:void(0)" onclick="confirmTimetableAppointment('${current[8]}')">确认排课</a>
				<a class="" href="javascript:void(0)" onclick="removeTimetableAppointment('${current[8]}')">取消排课</a>
				<a class="" href="${pageContext.request.contextPath}/timetable/listTimetableGroupAdmin?courseNo=${current[8]}">编辑</a>
			</c:if>
			<c:if test="${currentStates[i.count-1] eq '循环排课中'}">
				<a class="" href="javascript:void(0)" onclick="openWindow('${current[8]}')">循环排课</a>
				<a class="" href="javascript:void(0)" onclick="confirmTimetableAppointment('${current[8]}')">确认排课</a>
				<a class="" href="javascript:void(0)" onclick="removeTimetableAppointment('${current[8]}')">取消排课</a>
				<a class="" href="${pageContext.request.contextPath}/timetable/listTimetableCycle?courseNo=${current[8]}&courseCode=${current[8]}">编辑</a>
			</c:if>
			<c:if test="${currentStates[i.count-1] eq '已确认'}">
				<label>已确认</label>
			</c:if>
		</td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
<div class="page" >
	${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
	<a onclick="targetUrl('${pageContext.request.contextPath}/timetable/mylistCourseDetails?currpage=${pageModel.firstPage}&term=${term}')" target="_self">首页</a>				    
	<a onclick="targetUrl('${pageContext.request.contextPath}/timetable/mylistCourseDetails?currpage=${pageModel.previousPage}&term=${term}')" target="_self">上一页</a>
	 第
	<select onchange="targetUrl(this.options[this.selectedIndex].value)">
	    <option value="${pageContext.request.contextPath}/timetable/mylistCourseDetails?currpage=${page}&term=${term}">${page}</option>
	    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
	        <c:if test="${j.index!=page}">
	        <option value="${pageContext.request.contextPath}my/timetable/mylistCourseDetails?currpage=${j.index}&term=${term}">${j.index}</option>
	        </c:if>
	    </c:forEach>
	</select>页
	<a onclick="targetUrl('${pageContext.request.contextPath}/timetable/mylistCourseDetails?currpage=${pageModel.nextPage}&term=${term}')" target="_self">下一页</a>
	<a onclick="targetUrl('${pageContext.request.contextPath}/timetable/mylistCourseDetails?currpage=${pageModel.lastPage}&term=${term}')" target="_self">末页</a>
</div>
<!-- 查看学生名单 -->
 <div id="doSearchStudents" class="easyui-window" title="查看学生名单" modal="true"	closed="true" iconCls="icon-add" style="width:1000px;height:500px">
</div> 

</div>
		<div ng-view class="ng-page" style=""></div>
</div>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/node_modules/angular/angular.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/node_modules/angular-route/angular-route.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app.js"></script>
<script type="text/javascript">
   
	$(".chosen-select").chosen({
	    disable_search_threshold: 10,
	    no_results_text: "Oops, nothing found!",
	    width: "100"
	});
	$(".theory_experiment").find("td:first").addClass("t_e");
	$(".theory_experiment").click(function () {
	    $(this).addClass("theory_experiment_icon").siblings().removeClass("theory_experiment_icon theory_icon oneself_icon")
	});
	$(".theory").find("td:first").addClass("t");
	$(".theory").click(function () {
	    $(this).addClass("theory_icon").siblings().removeClass("theory_experiment_icon theory_icon oneself_icon")
	})
	$(".oneself").find("td:first").addClass("o");
	$(".oneself").click(function () {
	    $(this).addClass("oneself_icon").siblings().removeClass("theory_experiment_icon theory_icon oneself_icon")
	})
	$(".tbbgb a").click(function(){
		$(this).addClass("btn_chose").siblings().removeClass("btn_chose");
		$(this).parent().parent().siblings().find("a").removeClass("btn_chose");
		$(this).parent().parent().addClass("course_select").siblings().removeClass("course_select");
		$(this).parent().parent().attr("style","background:#6d8dd5");
		$(this).parent().parent().siblings().attr("style","");
	});
	$(".dat").click(function () {
	    $(this).parent().parent().parent().parent().parent().parent(".box_change").removeClass("w100p").addClass("w40p");
	})
	// 跳转页面
	function targetUrl(url){
	    document.queryForm.action=url;
	    document.queryForm.submit();
	}
	
	// 取消查询
	function cancel(){
		location.href='${pageContext.request.contextPath}/timetable/mylistCourseDetails?currpage=1';
	}
	
	$('[course-del]').each(function(i,e){
	    $(e).on('click',function(){
	      var _index=$(this);
	          swal({
	                 title: "您确定要删除这条信息吗",
	                 text: "删除后将无法恢复，请谨慎操作！",
	                 type: "warning",
	                 showCancelButton: true,
	                 confirmButtonColor: "#DD6B55",
	                 confirmButtonText: "删除",
	                 closeOnConfirm: false
	    }, function () { 
	       $.ajax({ 
	            url: "${pageContext.request.contextPath}/timetable/selfTimetable/special/deleteTimetableSelfCourse?courseCode="+$(e).attr('course-del'), 
	            type: 'DELETE' 
	        }).done(function(data) { 
	            location.reload();
	        }).error(function(data) { 
	            swal("OMG", "删除操作失败了呢!", "error"); 
	        }); 
	    });
	  });                                              
	}); 
	$(".om_list").click(
			function(){
				var sel =$(this).index()
				//console.log(sel);
				var s="kongsd";			
				$(".ouy_menu").find(".om_list").eq(sel).addClass("selected").siblings().removeClass("selected");
				$(".ouy_menu").find(".om_list").eq(sel).find("a").addClass("cc");
				$.cookie("mysel",sel,{"path":"/", expires:30});
				var cookie_sel = $.cookie("mysel");
				console.log(document.cookie);
				
			}
		)
	  $(".add_sub").on("mouseover", function () {
	                    
	                    var sd=$(this).find("ul").attr("data_eq")
	                    
	                    $(this).find("ul li").on("click",function(){
	                        //alert("3")
	                        var i=$(this).index();
	                        var $ul = $("ul:not('.sub_ul')", this);
	                        var $li = $(".menu_list", $ul);
	                        $(this).addClass("sub_selected").siblings().removeClass("sub_selected");
	                        $li.eq(sd).addClass("selected").siblings().removeClass("selected");
	                        
	                        $.cookie("myselchil",i,{"path":"/", expires:30});
	                        
	            			var cookie_selchil = $.cookie("myselchil");
	                       
	                        
	                    })
	                    
	                    
	                });
	    $(function(){
	    	var dt = new Date(); 
	    	dt.setSeconds(dt.getSeconds() + 60); 
	    	document.cookie = "cookietest=1; expires=" + dt.toGMTString(); 
	    	var cookiesEnabled = document.cookie.indexOf("cookietest=") != -1; 		
	    		var cookie_sel = $.cookie("mysel");
	    		var cookie_selchil = $.cookie("myselchil");
	    		console.log(document.cookie);
	    		if(cookie_sel==null){ 
	    			$(".ouy_menu").find(".om_list").eq(0).addClass("selected").siblings().removeClass("selected");
	    			
	    		}else{ 
	    		
	    			$(".ouy_menu").find(".om_list").eq(cookie_sel).addClass("selected").siblings().removeClass("selected");
	    			var $subul = $(".ouy_menu").find(".om_list").eq(cookie_sel).find(".sub_menu").html();
	                $(".add_sub").html($subul).show();
	                if(cookie_selchil!=null){
	                $(".add_sub").find(".sub_list").eq(cookie_selchil).addClass("sub_selected").siblings().removeClass("sub_selected");
	                }
	    	}
	    		
	    		
	    	})
	    	
	    	/*
 *查看学生名单*/
 function listTimetableStudents(courseDetailNo) {
	var sessionId = $("#sessionId").val();
	var con = '<iframe scrolling="yes" id="message1" frameborder="0"  src="${pageContext.request.contextPath}/timetable/openSearchStudent?courseDetailNo='
			+ courseDetailNo
			+ '" style="width:100%;height:100%;"></iframe>'
	$('#doSearchStudents').html(con);
	$("#doSearchStudents").show();
	//获取当前屏幕的绝对位置
    var topPos = window.pageYOffset;
    //使得弹出框在屏幕顶端可见
    $('#doSearchStudents').window({left:"px", top:topPos+"px"});
	$('#doSearchStudents').window('open');
} 
/*function clearNg(){
	$(".ng-page").html('');
	$(".main_container .fa-spinner").css("display","");
	$(".box_change").removeClass("w100p").addClass("w40p");
}*/
    function confirmTimetableAppointment(courseNo) {
        if(!confirm("确认提交吗，提交后不可更改？"))
            return false;
        $.ajax({
            url: "${pageContext.request.contextPath}/timetable/submitTimetableAppointment?courseNo="+courseNo,
            type: 'POST',
			dataType: 'text',
			success:function (data) {
				if(data == "success"){
				    alert("确认排课成功！");
				    window.location.reload();
                }else{
                    alert("确认排课失败！");
                }
            }
        });
    }
    function removeTimetableAppointment(courseNo) {
        if(!confirm("取消排课吗，取消后所做更改将会放弃？"))
            return false;
        $.ajax({
            url: "${pageContext.request.contextPath}/timetable/removeTimetableAppointment?courseNo="+courseNo,
            type: 'POST',
            dataType: 'text',
            success:function (data) {
                if(data == "success"){
                    alert("取消排课成功！");
                    window.location.reload();
                }else{
                    alert("取消排课失败！");
                }
            }
        });

    }
    function openWindow(courseNo) {
        $.ajax({
            url: "${pageContext.request.contextPath}/timetable/timetableWay?courseNo=" + courseNo,
            type: 'POST',
            dataType: 'text',
            success:function (data) {
                data = decodeURIComponent(data);
                var index = layer.open({
                    type: 0,
                    title: '查看排课方式',
                    maxmin: true,
                    shadeClose: true,
                    area: "auto",
                    content: data
                });
            }
        });
    }

</script>
<!-- 下拉框的js -->

</body>
</html>

