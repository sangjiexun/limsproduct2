<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<fmt:setBundle basename="bundles.lab-resources" />
<html ng-app="app">
<head>
	<head>
		<meta name="decorator" content="iframe" />
		<link href="${pageContext.request.contextPath}/css/iconFont.css"
			  rel="stylesheet">
		<!-- 下拉框的样式 -->
		<link type="text/css" rel="stylesheet"
			  href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
		<link type="text/css" rel="stylesheet"
			  href="${pageContext.request.contextPath}/chosen/chosen.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
		<!-- 下拉的样式结束 -->
		<!-- 打印插件的引用 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
		<script
				src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
				type="text/javascript"></script>

		<script
				src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
				type="text/javascript" charset="utf-8"></script>
		<script>
        //取消查询
        function cancel(){
           window.location.href="${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=1&id=-1";
        }
		/*
		*查看学生名单
		*/
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
        </script>
		<!-- 下拉的样式 -->
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/chosen/chosen.css" />
		<!-- 下拉的样式结束 -->
</head>

<body>
<div class="iStyle_RightInner">
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange" /></a>
				</li>
				<li class="end"><a href="javascript:void(0)">
					<spring:message code="left.teaching.progressSchedule" />
				</a>
				</li>
			</ul>
		</div>
	</div>
<%--<div id="LoadingBar"><i class="fa fa-spinner fa-spin"></i>正在载入，请稍候……</div>--%><%--此处为页面加载--%>
<input type="hidden" id="courseCode" value="">
	<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
	<div class="TabbedPanelsContent">
	<div class="tool-box" style="overflow:initial">
	<form:form name="queryForm" method="Post" action="${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=1">
		<table style="width:100%;">
			<tr>
				<td width="10%">
		<select class="chzn-select" id="term" name="term" style="width:180px">
		      	<option value="">学期</option>
		       <c:forEach items="${schoolTerms}" var="current">
		    	    <c:if test="${current.id eq term}">
		    	       <option value ="${current.id}" selected="selected">${current.termName} </option>
		    	    </c:if>
		    	    <c:if test="${current.id ne term}">
		    	       <option value ="${current.id}" >${current.termName} </option>
		    	    </c:if>
		        </c:forEach>
	        </select>
				</td>
				<td width="20%">
			<select class="chzn-select" name="courseNo" style="width:180px">
				<option value="">课程名</option>
				<c:forEach items="${courseDetailAllCourse}" var="current"	varStatus="i">
					<c:if test="${current[1] eq courseNo && not empty current[0]}">
						<option value="${current[1] }" selected="selected">${current[0] }[${current[1] }]</option>
					</c:if>
					<c:if test="${current[1] ne courseNo}">
						<option value="${current[1] }">${current[0] }[${current[1] }]</option>
					</c:if>
				</c:forEach>
			</select>
				</td>
				<td width="20%">
			<select class="chzn-select" name="teacher" style="width:180px">
				<option value="">教师</option>
				<c:forEach items="${courseDetailAll}" var="current"	varStatus="i">
					<c:if test="${current[0] eq teacherUsername}">
						<option value="${current[0] }" selected="selected">${current[1] }[${current[0] }]</option>
					</c:if>
					<c:if test="${current[0] ne teacherUsername}">
						<option value="${current[0] }">${current[1] }[${current[0] }]</option>
					</c:if>
				</c:forEach>
			</select>
				</td>
		<!-- <a class="btn r" href="#/listSelfSchoolCourseInfo/1">自主</a> -->
		<%--<input class="btn r" type="submit" value="查询">
		<a class="btn r" onclick="cancel()" href="#">取消</a>--%>
				<td width="30%"></td>
				<td width="20%">
		<input type="button" value="取消" onclick="cancel()" />
		<input type="submit" value="查询" />
				</td>
			</tr>
		</table>
		<span text="${user.username}"></span>
	</form:form>
</div>
	<div class="content-box">
	<div class="title">教学进度排课管理列表</div>
	<table>
	<thead>
	<tr>
		<th width="10%">课程名称</th>
		<th width="10%">课程代码</th>
		<%--<th width="10%">开课类别</th>--%>
		<th width="10%">学分</th>
		<%--<th width="10%">系</th>--%>
		<%--<th width="10%">学生专业</th>--%>
		<th width="10%">学生容量</th>
		<th width="15%">负责教师</th>
		<th width="15%">排课方式</th>
	</tr>
	</thead>
	<tbody class="tbbgb">
	<c:forEach items="${courseDetailPage}" var="current" varStatus="i">
	
	<tr>
		<td>${current[0]}</td>
		<td>${current[1]}</td>
		<%--<td>${current[2]}</td>
		<td>${current[3]}</td>--%>
		<%--<td>${current[7]}</td>--%>
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
		<td>
		<a class="btn l" href="${pageContext.request.contextPath}/timetable/doAdminTimetable/${current[1]}/${current[8]}">教学进度排课</a>
				<%--<c:if test="${current[10] eq 0}">
				<a class="btn l" href="${pageContext.request.contextPath}/timetable/listTimetableGroupAdmin?courseNo=${current[1]}">分组</a>
			</c:if> 
			<c:if test="${current[10] ne 0}">
				分组排课已完成
			</c:if> --%>
		</td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
</div>
<div class="page" >
	${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
	<a onclick="targetUrl('${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=${pageModel.firstPage}&term=${term }')" target="_self">首页</a>				    
	<a onclick="targetUrl('${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=${pageModel.previousPage}&term=${term }')" target="_self">上一页</a>
	 第
	<select onchange="targetUrl(this.options[this.selectedIndex].value)">
	    <option value="${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=${page}&term=${term }">${page}</option>
	    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
	        <c:if test="${j.index!=page}">
	        <option value="${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=${j.index}&term=${term }">${j.index}</option>
	        </c:if>
	    </c:forEach>
	</select>页
	<a onclick="targetUrl('${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=${pageModel.nextPage}&term=${term }')" target="_self">下一页</a>
	<a onclick="targetUrl('${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=${pageModel.lastPage}&term=${term }')" target="_self">末页</a>
</div>
		<!-- 查看学生名单 -->
		<div id="doSearchStudents" class="easyui-window" title="查看学生名单" modal="true"	closed="true" iconCls="icon-add" style="width:1000px;height:500px">
		</div>

</div>
<div ng-view class="ng-page"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/node_modules/angular/angular.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/node_modules/angular-route/angular-route.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app.js"></script>
<script type="text/javascript">
	$(".chzn-select").chosen({
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
		location.href="${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=1";
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
	    	

function clearNg(){
	$(".ng-page").html('');
	$(".main_container .fa-spinner").css("display","");
	$(".box_change").removeClass("w100p").addClass("w40p");
}
</script>
<!-- 下拉框的js -->

</body>
</html>

