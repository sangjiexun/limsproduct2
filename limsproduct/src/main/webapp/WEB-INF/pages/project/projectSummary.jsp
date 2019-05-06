<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<meta name="decorator" content="iframe"/>
	<!-- 下拉框的样式 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
	<!-- 下拉的样式结束 -->
	<link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
	<!-- 打印开始 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
	<!-- 打印结束 -->
	<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/timetable/lmsReg.css">

	<style type="text/css">
		table{width:100%;}
		.array{width:100%;
			word-break:break-all;}
		.tbh {
			text-align: center;
			background-color: #fff;
			color: #fff;
			font-weight: bold;
			width: 60px;
		}
	</style>
	<script type="text/javascript">
        $(document).ready(function(){
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

	</script>

	<script type="text/javascript">
        $(function(){
            $("#showTimetable").window({
                top: ($(window).width() - 800) * 0.5 ,
                left: ($(window).width() - 1000) * 0.5
            })
            $(".sit_maincontent").css('height',600);
        })
        //如果为查询则提交查询页面，如果为电子表格导出，则导出excel
        function subform(gourl){
            var gourl;
            form.action=gourl;
            form.submit();
        }

        function getLabRoom(){
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/operation/project/findLabRoomByLabCenter",
                data: {'labCenter':$("#labCenter").val()},
                success:function(data){
                    $("#labRoom").html(data.labroom);
                }
            });
        }
        function getSchoolCourseInfo(){
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/operation/project/findSchoolCourseInfoByLabCenter",
                data: {'labCenter':$("#labCenter").val()},
                success:function(data){
                    $("#courseNumber").html(data.coursename);
                }
            });
        }
	</script>
	<script type="text/javascript">
        //取消查询
        function cancel()
        {
            window.location.href="${pageContext.request.contextPath}/operation/project/projectsummary?currpage=1&orderBy=9";
        }
        //跳转
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
        //导出
        function exportProjectSummary(url)
        {
            var roomId=$("#labRoom").val();
            $("#roomId").val(roomId);
            var courseNumber = $("#coursename").val();
            $("#courseNumber").val(courseNumber);
            var acno=$("#acno").val();
            $("#acno").val(acno);
            var termId=$("#schoolTerm").val();
            $("#termId").val(termId);

            document.queryForm.action=url;
            document.queryForm.submit();
        }
	</script>
	<script>
        function start(v){
            try{printWB.ExecWB(v,1);}catch(e){}
            window.close();
        }
	</script>
	<style type="text/css">
		.chzn-choices{
			border: none!important;
		}
	</style>
	<object id="printWB" style="display:none" width=0 height=0 classid="clsid:8856F961-340A-11D0-A96B-00C04FD705A2" VIEWASTEXT></object><body topmargin=0 leftmargin=0 onload="start(7)"></body>
<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="WebBrowser3" width="0" VIEWASTEXT>
</OBJECT>
<script language="#">
function test()
{
document.all.WebBrowser3.ExecWB(7,1);
}
</script>
<INPUT type="button" value="Button" onclick="test()">
</head>

<body bgcolor="#ffffff" text="#000000" onbeforeprint="btnprint.style.display='none';btnback.style.display='none';" onafterprint="btnprint.style.display='';btnback.style.display=''">
  <div class="navigation">
<div id="navigation">
	<ul>
		<li><a href="javascript:void(0)"><spring:message code="left.report.system" /></a></li>
		<li class="end"><a href="javascript:void(0)"><spring:message code="left.report.sitehours" /></a></li>
	</ul>
</div>
</div>

<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
			<li class="TabbedPanelsTab1 selected" id="s1">
				<a href="javascript:void(0)">实践教学课时细化实验实训项目汇总表</a>
			</li>
			<input class="btn btn-new" onclick="exportProjectSummary('${pageContext.request.contextPath}/operation/project/exportProjectSummary')" type="button" value="导出">
			<input class="btn btn-new" id="myPrint" value="打印" type="button" />
		</ul>
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<%--<div class="title">--%>
						<%--<div id="title">实践教学课时细化实验实训项目汇总表</div>--%>
						<%--<input class="btn btn-new" onclick="exportProjectSummary('${pageContext.request.contextPath}/operation/project/exportProjectSummary')" type="button" value="导出">--%>
						<%--<input class="btn btn-new" id="myPrint" value="打印" type="button" />--%>
						<%--<!-- <a class="btn btn-new" href="#"><INPUT TYPE="button" value="打 印" id="btnprint" onclick="self.print();"></a> -->--%>
					<%--</div>--%>

					<%--<table>--%>
						<%--<tbody>--%>
						<%--<tr>--%>
							<%--<td>--%>
					<div class="tool-box">
								<form name="queryForm" action="" method="post">
									<ul>
										<li>
										<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_ASSETMANAGEMENT,ROLE_DEAN,ROLE_SCHOOLLEADER">
											实验中心:
											<select onchange="getLabRoom();getSchoolCourseInfo();" name="labCenter" id="labCenter" class="chzn-select" style=" width:120px;">
												<option value="0">全部实验中心</option>
												<c:forEach items="${labCenterMap}" var="center">
													<c:if test="${selectedCenter eq center.key}">
														<option value="${center.key}" selected="selected">${center.value}</option>
													</c:if>
													<c:if test="${selectedCenter ne center.key}">
														<option value="${center.key}">${center.value}</option>
													</c:if>
												</c:forEach>
											</select>
										</sec:authorize>
										</li>
										<li>
										实验室名称:
										<select id="labRoom" name="labRoom" style=" width:200px;">
											<option value = "">全部实验室</option>
											<c:forEach items="${labRoomMap}" var="curr">
												<c:if test="${roomSelected eq curr.key}">
													<option value="${curr.key}" selected="selected">[${curr.key}]${curr.value }</option>
												</c:if>
												<c:if test="${roomSelected ne curr.key}">
													<option value="${curr.key}">[${curr.key}]${curr.value }</option>
												</c:if>
											</c:forEach>
										</select>
										</li>
										<li>
										学期:
										<select class="chzn-select" id="schoolTerm" name="schoolTerm" multiple="multiple" data-placeholder="默认当前,查询其他学期请选择">
											<option value="${schoolTerm.id}">${schoolTerm.termName}</option>
											<c:forEach items="${schoolTermMap}" var="term">
												<option value="${term.key}">${term.value}</option>
											</c:forEach>
											<c:forEach items="${termSelectedList}" var="terms">
												<option value="${terms.key}" selected="selected">${terms.value}</option>
											</c:forEach>
										</select>
										</li>
										<li>
										课程：
										<select id="courseNumber" name="courseNumber" style=" width:200px;">
											<option value="">全部课程</option>courseSelected
											<c:forEach items="${schoolCourseInfoMap}" var="curr">
												<c:if test="${courseSelected eq curr.key}">
													<option value="${curr.key}" selected="selected">[${curr.key}]${curr.value }</option>
												</c:if>
												<c:if test="${courseSelected ne curr.key}">
													<option value="${curr.key}">[${curr.key}]${curr.value }</option>
												</c:if>
											</c:forEach>
										</select>
										</li>
										<li>
										<input type="button" onclick="targetUrl('${pageContext.request.contextPath}/operation/project/projectsummary?currpage=1')" value="查询"/>
										<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li>
									</ul>
									<input type="hidden" value="" name="roomId" id="roomId">
									<input type="hidden" value="" name="acno" id="acno">
									<input type="hidden" value="" name="courseNumber" id="courseNumber">
									<input type="hidden" value="" name="termId" id="termId">

								</form>
				</div>
				</div>
				<div class="content-box">
							<%--</td>--%>
						<%--</tr>--%>
						<%--</tbody>--%>
					<%--</table>--%>
					<div id="myShow">
						<style type="text/css">
							/*td{border:solid 1px #add9c0 !important;}*/
						</style>
						<table class="tb" id="my_show">
							<thead style="center-content">
							<tr>
								<%--<th colspan="34">--%>
								<%--</th>--%>
							</tr>
							<tr>
								<th class="tbh" width=4%>序号</th>
								<th class="tbh" width=10%>课程编号</th>
								<th class="tbh" width=10%>课程名称</th>
								<th class="tbh" width=5%>实验名称</th>
								<th class="tbh" width=5%>实验编号</th>
								<th class="tbh" width=5%>实验学时数</th>
								<th class="tbh" width=10%>面向专业</th>
								<th class="tbh" width=8%>实验要求</th>
								<th class="tbh" width=5%>实验类型</th>
								<th class="tbh" width=5%>实验类别</th>
								<th class="tbh" width=5%>实验所属学科</th>
								<th class="tbh" width=5%>实验者类别</th>
								<th class="tbh" width=5%>实验者人数</th>
								<th class="tbh" width=5%>每组人数</th>
								<th class="tbh" width=10%>实验室名称</th>
								<th class="tbh" width=5%>实验室编号</th>
							</tr>
							</thead>

							<tbody>
							<c:forEach items="${ProjectSummaries}" var="curr" varStatus="i">
								<tr><!-- 实验编号、实验名称、实验类别、实验类型、实验所属学科、实验要求、实验者类别、实验者人数、每组人数、实验学时数、实验室编号、实验室名称、课程编号、课程名称、面向专业 -->
									<td style="border:solid 1px #add9c0 !important;">${i.count}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.schoolCourseInfo.courseNumber}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.schoolCourseInfo.courseName}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.lpName}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.lpCodeCustom}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.lpDepartmentHours}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.schoolMajor.majorName}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.CDictionaryByLpCategoryRequire.CName}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.CDictionaryByLpCategoryApp.CName}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.CDictionaryByLpCategoryMain.CName}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.systemSubject12.SName}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.CDictionaryByLpCategoryStudent.CName}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.lpStudentNumber}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.lpStudentNumberGroup}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.labRoom.labRoomName}</td>
									<td style="border:solid 1px #add9c0 !important;">${curr.labRoom.labRoomNumber}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="page" >
						${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/operation/project/projectsummary?currpage=1&status=${status}')" target="_self">首页</a>
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/operation/project/projectsummary?currpage=${pageModel.previousPage}&status=${status}')" target="_self">上一页</a>
						第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
						<option value="${pageContext.request.contextPath}/operation/project/projectsummary?currpage=${pageModel.currpage}&status=${status}">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
							<c:if test="${j.index!=pageModel.currpage}">
								<option value="${pageContext.request.contextPath}/operation/project/projectsummary?currpage=${j.index}&status=${status}">${j.index}</option>
							</c:if>
						</c:forEach></select>页
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/operation/project/projectsummary?currpage=${pageModel.nextPage}&status=${status}')" target="_self">下一页</a>
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/operation/project/projectsummary?currpage=${pageModel.lastPage}&status=${status}')" target="_self">末页</a>
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
