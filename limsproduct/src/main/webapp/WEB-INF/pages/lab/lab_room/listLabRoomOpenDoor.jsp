<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="decorator" content="iframe"/>
	<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
	<!-- 全局的引用 -->
	<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- bootstrap的引用 -->
	<link href="${pageContext.request.contextPath}/bootstrap/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" >
	<script src="${pageContext.request.contextPath}/bootstrap/bootstrap-select/js/bootstrap-select.js"></script>

	<!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
	<script type="text/javascript">
        //跳转
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
	</script>
	<script type="text/javascript">
        // 远程开门
		function openDoorNew(agentId, doorIndex) {
			$.post('${pageContext.request.contextPath}/labRoom/openDoorNew?agentId='+agentId+'&doorIndex='+doorIndex,function(data){  //serialize()序列化
				if(data=="success"){
					alert("门禁已经打开");
				}else{
					alert("开门失败，请检查当网络连接或者再试一次。");
				}
			});
		}
	</script>
	<style>
		.btn {
			line-height: 23px;
			border-radius: 3px;
			font-size: 12px;
			padding: 0 7px;
			outline:none!important;
		}

		.btn:hover,
		.btn:focus {
			color:#fff;
			opacity: 0.8;
		}
		.row {
			margin-top: 10px;
			margin-bottom: 10px;
		}
		.row input[type=button] {
			margin: 0 0 5px;
		}
		.form-control {
			height: auto;
			box-shadow: none;
		}
		.dropdown .btn{
			margin:auto;
		}
		.btn-outline-primary{
			color:#409eff!important;
			background: #fff!important;
			border:1px solid #409eff!important;
		}
		.modal-header{
			background: #f7f7f7;
			border-radius: 6px 6px 0 0;
		}
		.bootstrap-select .dropdown-menu .selected a span.text,
		.bootstrap-select.show-tick .dropdown-menu .selected span {
			color:#fff;
		}
	</style>
</head>

<body>
<script type="text/javascript">
    //门禁
    function opendoor(agentId){
        $.post('${pageContext.request.contextPath}/labRoom/openDoorPython?agentId='+agentId+'',function(data){  //serialize()序列化
            if(data=="sucess"){
                alert("门禁已经打开");
            }else{
                alert("开门失败，请检查当网络连接或者再试一次。");
            }

        });
    }
</script>
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management" /></a></li>
			<li class="end"><a href="javascript:void(0)"><spring:message code="left.training.management"/></a></li>
		</ul>
	</div>
</div>
<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">		</ul>
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<div class="tool-box" style="overflow: hidden">
						<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/listLabRoomOpenDoor?currpage=1" method="post" modelAttribute="labRoom">
							<ul>
								<li>中心/<spring:message code="all.trainingRoom.labroom" />:<form:input id="lab_name" path="labRoomName"/></li>
								<li>
									<input type="submit" value="查询"/>
							</ul>
						</form:form>
					</div>

					<table class="tb" id="my_show">
						<thead>
						<tr>
							<th><spring:message code="all.trainingRoom.labroom" />编号</th>
							<th><spring:message code="all.trainingRoom.labroom" />名称</th>
								<th>楼宇(楼层)</th>
							<th>所属中心</th>
							<c:if test="${project eq 'zjcclims'}">
								<th>等级</th>
							</c:if>
							<th>容量</th>
							<th>使用状态</th>
							<th>预约状态</th>
							<c:if test="${stationNum eq 'true'}"><!-- 有工位相关需求时显示 -->
								<th>可预约工位数</th>
							</c:if>
							<th>操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${listLabRoom}" var="curr">
							<tr>
								<td>${curr.labRoomNumber}</td>
								<td>${curr.labRoomName}</td>
								<td><c:if test="${curr.floorNo ne null}">${curr.systemBuild.buildName}(${curr.floorNo}楼)</c:if>
									<c:if test="${curr.floorNo eq null || curr.floorNo eq ''}"></c:if></td>
								<td>${curr.labCenter.centerName}</td>
								<c:if test="${project eq 'zjcclims'}">
									<td>
										<c:if test="${curr.labRoomLevel==0}">特级</c:if>
										<c:if test="${curr.labRoomLevel==1}">一级</c:if>
										<c:if test="${curr.labRoomLevel==2}">二级</c:if>
									</td>
								</c:if>
								<td>${curr.labRoomCapacity}</td>
								<td>
									<c:choose>
										<c:when test="${curr.labRoomActive==1}">可用</c:when>
										<c:otherwise>不可用</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${curr.labRoomReservation==1}">可预约</c:when>
										<c:otherwise>不可预约</c:otherwise>
									</c:choose>
								</td>
								<c:if test="${stationNum eq 'true'}"><!-- 有工位相关需求时显示 -->
									<td>${curr.labRoomWorker}</td>
								</c:if>
								<td>
									<!-- 判断是否是改实验室的管理员或物联管理员 -->
									<c:forEach items="${curr.labRoomAdmins}" var="admins">
										<c:if test="${admins.typeId==1 || admins.typeId==2}">
											<c:if test="${admins.user.username eq username}">
												<!-- 遍历门禁设备 -->
												<c:forEach items="${curr.labRoomAgents}" var="agent">
													<c:if test="${agent.CDictionary.CNumber=='2' && agent.CDictionary.CCategory=='c_agent_type'}">
														<c:if test="${agent.doorindex ne null}">
															<a href="javascript:void(0)" onclick="openDoorNew(${agent.id},${agent.doorindex});">远程开${agent.doorindex}号门</a>
														</c:if>
														<c:if test="${agent.doorindex eq null}">
															<a href="javascript:void(0)" onclick="openDoorNew(${agent.id},${agent.doorindex});">远程开门</a>
														</c:if>
													</c:if>
												</c:forEach>
											</c:if>
										</c:if>
									</c:forEach>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<!-- 分页[s] -->
					<div class="page" >
						${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=${orderBy }&type=1')" target="_self">首页</a>
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.previousPage}&orderBy=${orderBy }&type=1')" target="_self">上一页</a>
						第<select onchange="targetUrl(this.options[this.selectedIndex].value)">
						<option value="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.currpage}&orderBy=${orderBy }&type=1">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
							<c:if test="${j.index!=pageModel.currpage}">
								<option value="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${j.index}&orderBy=${orderBy }&type=1">${j.index}</option>
							</c:if>
						</c:forEach></select>页
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.nextPage}&orderBy=${orderBy }&type=1')" target="_self">下一页</a>
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.lastPage}&orderBy=${orderBy }&type=1')" target="_self">末页</a>
					</div>
					<!-- 分页[e] -->
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
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
    $("#labright").click(function() { //向右删除下拉框的值   向左增加下拉框的值 的点击事件
        $("#labone option:selected").appendTo("#labtwo");
        $('.selectpicker').selectpicker('refresh');
    });
    $("#lableft").click(function() { //向左删除下拉框的值   向右增加下拉框的值  的点击事件
        $("#labtwo option:selected").appendTo("#labone");
        $('.selectpicker').selectpicker('refresh');
    });
    $("#peopleright").click(function() { //向右删除下拉框的值   向左增加下拉框的值 的点击事件
        $("#peopleone option:selected").appendTo("#peopletwo");
        $('.selectpicker').selectpicker('refresh');
    });
    $("#peopleleft").click(function() { //向左删除下拉框的值   向右增加下拉框的值  的点击事件
        $("#peopletwo option:selected").appendTo("#peopleone");
        $('.selectpicker').selectpicker('refresh');
    });
</script>
</body>
</html>
