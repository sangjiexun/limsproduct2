<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="decorator" content="iframe"/>

	<!-- 下拉框的样式 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
	<!-- 下拉的样式结束 -->

	<script type="text/javascript">
        $(document).ready(function(){
            var s=${status};
            for(var i=0;i<=5;i++)
            {
                if(i==s)
                {
                    $("#s"+i).addClass("TabbedPanelsTab selected");
                }
                else
                {
                    $("#s"+i).addClass("TabbedPanelsTab");
                }
            }
        });
        //取消查询
        function cancel()
        {
            window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=${orderBy }";
        }

        //提交审核人数据
        function saveCheckUser()
        {
            if($("#username_check").val()==""){
                alert("老师，您需要先选择指定审核人再提交。")
            }else{
                document.check_user_form.action="${pageContext.request.contextPath}/operation/submitOperationItem?isMine=1";
                document.check_user_form.submit();
            }

        }
        //弹出填写审核人的表单
        function submitItem(id)
        {
            if("${thereIsAHeader}"){

                $("#lp_id").val(id);
                $("#check_user").show();
                $("#check_user").window('open');
            }else{
                alert("您好，目前您所在的中心还未在系统中设置系主任，暂时不能提交审核，请联系相关人员进行设置。")
            }

        }
        //设置项目编号
        function saveCodeCustom()
        {
            document.edit_code_form.action="${pageContext.request.contextPath}/operation/saveCodeCustom";
            document.edit_code_form.submit();
        }
        //修改项目编号
        function editCodeCustom(id, code)
        {
            $("#oi_id").val(id);
            $("#edit_code form table #lpCodeCustom").val(code);
            $("#edit_code").show();
            $("#edit_code").window('open');
        }
        //排序

        var asc=${asc};//声明全局变量asc
        function orderByNumber(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=10";
            }else window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=0";
        }
        function orderByName(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=11";
            }else window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=1";
        }
        function orderByLab(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=12";
            }else window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=2";
        }
        function orderByCourse(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=13";
            }else window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=3";
        }
        function orderByStatus(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=14";
            }else window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=4";
        }
	</script>
	<script type="text/javascript">
        //跳转
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
	</script>
	<script type="text/javascript">
		function getSchoolCourseInfo() {
			$.ajax({
				url:"${pageContext.request.contextPath}/operation/getAllCourseInfos",
				contentType: "application/json;charset=utf-8",
				dataType: "text",
				type: "post",
				success: function (data) {
					$("#course_number").html(data);
					$("#course_number").trigger("liszt:updated");
				}
			});
		}
		window.onload = function () {
			getSchoolCourseInfo();
		};
	</script>

</head>

<body>
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange"/></a></li>
			<li class="end"><a href="javascript:void(0)">我审核的课题</a></li>
		</ul>
	</div>
</div>
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
		<%--<sec:authorize ifNotGranted="ROLE_STUDENT">
        <li class="TabbedPanelsTab" id="s0">
            <a class="iStyle_Feelings_Tree_Leaf" href="${pageContext.request.contextPath}/teaching/coursesite/experimentalmanagement?currpage=1">大纲管理</a>
        </li>
        </sec:authorize>--%>
		<sec:authorize ifNotGranted="ROLE_STUDENT">
			<li class="TabbedPanelsTab" id="s1"><a
					class="iStyle_Feelings_Tree_Leaf"
					href="${pageContext.request.contextPath}/teaching/coursesite/listSchoolCourseInfo?currpage=1">课程库</a>
			</li>
			<li class="TabbedPanelsTab" id="s2"><a
					class="iStyle_Feelings_Tree_Leaf"
					href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=7&orderBy=0">课题库</a>
			</li>
			<li class="TabbedPanelsTab selected" id="s3"><a
					class="iStyle_Feelings_Tree_Leaf"
					href="${pageContext.request.contextPath}/operation/listMyOperationItemAuditing?currpage=1">课题审核</a>
			</li>
		</sec:authorize>
	</ul>
</div>
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">

			<div class="content-box">
				<div class="title">
					<div id="title">课题审核</div>
					<%--<sec:authorize ifAnyGranted="ROLE_ASSISTANT,ROLE_EXCENTERDIRECTOR" >
                    <!-- <a class="btn btn-new" href="${pageContext.request.contextPath}/operation/newOperationItem?isMine=1&flagId=0">新建</a> -->
                    </sec:authorize>--%>
				</div>

				<div class="tool-box">
					<form:form name="queryForm"
							   action="${pageContext.request.contextPath}/operation/listMyOperationItemAuditing?currpage=1"
							   method="post" modelAttribute="operationItem">
						<ul>
							<li>课题名称:<form:input id="lp_name" path="lpName"/></li>
							<li>学期:
								<form:select path="schoolTerm.id" id="term_id">
									<form:option value="${schoolTerm.id }">${schoolTerm.termName }</form:option>
									<form:options items="${schoolTerms}" itemLabel="termName" itemValue="id"/>
								</form:select>
							</li>
							<li>所属课程:
								<form:select path="schoolCourseInfo.courseNumber" id="course_number">
									<form:option value="">请选择</form:option>
									<c:forEach items="${schoolCourseInfo}" var="sc">
										<form:option value="${sc.courseNumber}">[${sc.courseNumber}]${sc.courseName }</form:option>
									</c:forEach>
								</form:select>
							</li>
							<%--<li>审核状态：</li>--%>
							<%--<li>--%>
								<%--<form:select path="CDictionaryByLpStatusCheck.CNumber" id="lp_status_check">--%>
								<%--<form:option value="">请选择</form:option>--%>
								<%--<form:option value="1">未审核</form:option>--%>
								<%--<form:option value="3">已审核</form:option>--%>
								<%--</form:select>--%>
							<%--<li>--%>
							<li>
								<input type="submit" value="查询"/>
								<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
							</li>
						</ul>
					</form:form>
				</div>
				<table class="tb" id="my_show">
					<thead>
					<tr>
						<th>课题名称</th>
						<th>学期</th>
						<th>所属实验中心</th>
						<th>中心主任</th>
						<%--<th><a href="javascript:void(0);" onclick="orderByLab()";>所属实训室</a></th>--%>
						<th><a href="javascript:void(0);" onclick="orderByCourse()";>所属课程</a></th>
						<th>创建者</th>
						<%--<th>指定审核人</th>--%>
						<th><a href="javascript:void(0);" onclick="orderByStatus()";>审核状态</a></th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${listOperationItem}" var="curr">
						<tr>
							<td>${curr.lpName}</td>
							<td>${curr.schoolTerm.termName}</td>
							<td>${curr.labRoom.labCenter.centerName}</td>
							<td>${curr.labRoom.labCenter.userByCenterManager.cname}</td>
							<%--<td>${curr.labRoom.labRoomName}</td>--%>
							<td>${curr.schoolCourseInfo.courseName}[${curr.schoolCourseInfo.courseNumber}]</td>
							<td>${curr.userByLpCreateUser.cname}</td>
								<%--<td><a href="${pageContext.request.contextPath}/operation/operationItemAndAudit?oId=${curr.id}">审核</a></td>--%>
								<%--<td>${curr.operationItem.userByLpCheckUser.cname}</td>--%>
							<td>
								<c:choose>
									<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 2}">${auditShow[curr.id]}</c:when>
									<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 3}">审核通过</c:when>
									<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 4}">审核拒绝</c:when>
									<c:otherwise>未审核</c:otherwise>
								</c:choose>
							</td>
							<td>
									<%--&lt;%&ndash;教研室主任权限&ndash;%&gt;--%>
								<%--<sec:authorize ifAnyGranted="ROLE_DEPARTMENTHEADER" >--%>
									<%--<c:choose>--%>
										<%--<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 3}">--%>
											<%--<a href="${pageContext.request.contextPath}/operation/viewOperationItem?operationItemId=${curr.id}&&flag=3&status=2">查看</a>--%>
										<%--</c:when>--%>
										<%--<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 4}">--%>
											<%--<a href="${pageContext.request.contextPath}/operation/viewOperationItem?operationItemId=${curr.id}&&flag=3&status=2">查看</a>--%>
										<%--</c:when>--%>
										<%--<c:otherwise>--%>
											<%--<a href="${pageContext.request.contextPath}/operation/operationItemAndAudit?oId=${curr.id}&cid=-1&flag=1">审核</a>--%>
										<%--</c:otherwise>--%>
									<%--</c:choose>--%>
								<%--</sec:authorize>--%>
									<%--&lt;%&ndash;实训室管理员权限&ndash;%&gt;--%>
								<%--<sec:authorize ifAnyGranted="ROLE_LABMANAGER" >--%>
									<%--<c:choose>--%>
										<%--<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 3}">--%>
											<%--<a href="${pageContext.request.contextPath}/operation/viewOperationItem?operationItemId=${curr.id}&&flag=3&status=2">查看</a>--%>
										<%--</c:when>--%>
										<%--<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 4}">--%>
											<%--<a href="${pageContext.request.contextPath}/operation/viewOperationItem?operationItemId=${curr.id}&&flag=3&status=2">查看</a>--%>
										<%--</c:when>--%>
										<%--<c:otherwise>--%>
											<%--<a href="${pageContext.request.contextPath}/operation/operationItemAndAudit?oId=${curr.id}&cid=-1&flag=2">审核</a>--%>
										<%--</c:otherwise>--%>
									<%--</c:choose>--%>
								<%--</sec:authorize>--%>
									<%--&lt;%&ndash;实训室中心主任&ndash;%&gt;--%>
								<%--<sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR" >--%>
									<%--<c:choose>--%>
										<%--<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 3}">--%>
											<%--<a href="${pageContext.request.contextPath}/operation/viewOperationItem?operationItemId=${curr.id}&&flag=3&status=2">查看</a>--%>
										<%--</c:when>--%>
										<%--<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 4}">--%>
											<%--<a href="${pageContext.request.contextPath}/operation/viewOperationItem?operationItemId=${curr.id}&&flag=3&status=2">查看</a>--%>
										<%--</c:when>--%>
										<%--<c:otherwise>--%>
											<%--<a href="${pageContext.request.contextPath}/operation/operationItemAndAudit?oId=${curr.id}&cid=-1&flag=3">审核</a>--%>
										<%--</c:otherwise>--%>
									<%--</c:choose>--%>
								<%--</sec:authorize>--%>
										<a href="${pageContext.request.contextPath}/operation/viewOperationItem?operationItemId=${curr.id}&&flag=3&status=2">查看</a>
										<c:if test="${sessionScope.selected_role eq idAndAuth[curr.id]}">
											<a href="${pageContext.request.contextPath}/operation/operationItemAndAudit?oId=${curr.id}&cid=-1&flag=${idAndLevel[curr.id]}">审核</a>
										</c:if>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<div class="page" >
					${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
					<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/operation/listMyOperationItemAuditing?currpage=1')" target="_self">首页</a>
					<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/operation/listMyOperationItemAuditing?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
					第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
					<option value="${pageContext.request.contextPath}/operation/listMyOperationItemAuditing?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
					<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
						<c:if test="${j.index!=pageModel.currpage}">
							<option value="${pageContext.request.contextPath}/operation/listMyOperationItemAuditing?currpage=${j.index}">${j.index}</option>
						</c:if>
					</c:forEach></select>页
					<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/operation/listMyOperationItemAuditing?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
					<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/operation/listMyOperationItemAuditing?currpage=${pageModel.lastPage}')" target="_self">末页</a>
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
