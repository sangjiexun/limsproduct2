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
        function cancel()
        {
            window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=9&type=1";
        }
        //跳转
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }


        //弹窗--规章制度
        function showRegulations(id){
            $("#showRegulations").show();
            //使得弹出框在屏幕顶端可见
            $('#showRegulations').window({left:"100px", top:"100px"});
            $("#showRegulations").window('open');

            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/getLabRoomDetail?roomId="+id,// 排除已存在于该实训室的管理员
                type:"POST",
                success:function(data){//AJAX查询成功
                    document.getElementById("labRoom_body").innerHTML=data;
                }
            });
        }
        //排序

        var asc=${asc};//声明全局变量asc
        function orderByNumber(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=10&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=0&type=1";
        }
        function orderByName(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=11&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=1&type=1";
        }
        function orderByLabCenter(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=12&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=2&type=1";
        }
        function orderByCapacity(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=13&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=3&type=1";
        }
        function orderByArea(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=14&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=4&type=1";
        }
        function orderByActive(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=15&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=5&type=1";
        }
        function orderByReservation(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=16&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=6&type=1";
        }
        function orderByRoomAddress(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=17&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=7&type=1";
        }
        function orderByLevel(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=18&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=8&type=1";
        }

	</script>
	<script type="text/javascript">
		// 提交批量设置管理员
		function subMultiple() {
			var type_code = $("#type_code").val();
			var labtwo = $("#labtwo").val();
			var peopletwo = $("#peopletwo").val();
            if($("#type_code").val()==''){
                alert("请选择管理员类别！");
            }else if($("#labtwo").val()=='' || $("#labtwo").val()==null) {
                alert("请至少选择一个实验室！");
			}else if($("#peopletwo").val()=='' || $("#peopletwo").val()==null) {
                alert("请至少选择一个管理员！");
			}
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/saveMultipleManager",// 保存批量添加的管理员
                type:"POST",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
				data:{"type_code":type_code,"labtwo":labtwo,"peopletwo":peopletwo},
                dataType: 'text',
				// async:false,
                success:function(data){
                    alert(data);
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
    //设置
    function openSetupLink(labRoomId,currpage,type){//将labRoomId page传递到后台
        var index = layer.open({
            type: 2,
            title: '设置',
            maxmin: true,
            shadeClose: true,
            content: "${pageContext.request.contextPath}/device/editLabRoomSettingRest/"+labRoomId+"/"+currpage+"/"+type,
			end: function(){
				window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=9&type=1";
			}
        });
        layer.full(index);
    }

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
    function getLabRoom(id,page) {
        var index = layer.open({
            type: 2,
            title: '查看',
            maxmin: true,
            shadeClose: true,
            content: "${pageContext.request.contextPath}/labRoom/getLabRoom?id="+id+"&currpage="+page+"&type=${type}",
        });
        layer.full(index);
    }
    function editLabRoom(id) {
        var index = layer.open({
            type: 2,
            title: '编辑',
            maxmin: true,
            shadeClose: true,
            content: "${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId="+id+"&type="+${type}+"&page=${page}",
            end:function(){
                document.queryForm.action="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${page}&orderBy=${orderBy}&type=1";
                document.queryForm.submit();
            }
        });
        layer.full(index);
    }
    function newLabRoom() {
        var index = layer.open({
            type: 2,
            title: '新建',
            maxmin: true,
            shadeClose: true,
            content: "${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId=0&type="+${type}+"&page=${page}",
            end:function(){
                document.queryForm.action="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${page}&orderBy=${orderBy}&type=1";
                document.queryForm.submit();
            }
        });
        layer.full(index);
    }
    function  deleteLabRoom(id) {
        var massage = confirm("确定删除?")
		if(massage==true){$.ajax({
            url:"${pageContext.request.contextPath}/labRoom/deleteLabRoom?labRoomId="+id+"&type=${type}&page=${page}",
            type:"POST",
            success:function(data){//AJAX查询成功
                document.queryForm.action="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${page}&orderBy=${orderBy}&type=1";
                document.queryForm.submit();
            },
            error:function(){
                alert("无法删除");
            }
        });}
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
<%--<div id="TabbedPanels1" class="TabbedPanels">
      <ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
              <li class="TabbedPanelsTab selected" id="s1"><a
                  class="iStyle_Feelings_Tree_Leaf"
                  href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=9"><spring:message code="all.trainingRoom.labroom" />列表</a>
              </li>
          <sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_LABMANAGER" >
              <li class="TabbedPanelsTab" id="s2"><a
                  class="iStyle_Feelings_Tree_Leaf"
                  href="${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=1">&nbsp;&nbsp;&nbsp;&nbsp;机房电脑使用记录</a>
              </li>
			</sec:authorize>
		</ul>
	</div>--%>
<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
			<%--<li class="TabbedPanelsTab1 selected" id="s1">--%>
				<%--<a href="javascript:void(0)"><spring:message code="all.trainingRoom.labroom" />列表</a>--%>
			<%--</li>--%>
			<c:if test="${authLevel eq '1' || authLevel eq '3' || authLevel eq '5'}">
				<a class="btn btn-new" onclick="newLabRoom()">新建</a>
			</c:if>
			<c:if test="${authLevel lt 6 && authLevel ne 0}">
				<button class="btn btn-new" data-toggle="modal" data-target="#myModal">批量设置管理员</button>
			</c:if>
		</ul>
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<div class="tool-box" style="overflow: hidden">
						<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=${orderBy }&type=1" method="post" modelAttribute="labRoom">
							<ul>
								<li>中心/<spring:message code="all.trainingRoom.labroom" />:<form:input id="lab_name" path="labRoomName"/></li>
								<c:if test="${baseManage eq 'true'}">
								<li>所属基地:<form:input id="lab_base" path="labBase.labName"/></li>
                                </c:if>
								<li>可预约工位数:
									<select name="searchflg" id="searchflg" class="search_flg">
										<option value="">-请选择-</option>
										<option value="1" <c:if test="${'1' eq searchflg}">selected</c:if>>等于</option>
										<option value="2" <c:if test="${'2' eq searchflg}">selected</c:if>>大于</option>
										<option value="3" <c:if test="${'3' eq searchflg}">selected</c:if>>小于</option>
									</select>
								</li>
								<li><input type="text" id="worker" name="worker" value="${worker}" style="float: none;width:50px;"></li>
								<li>面积:
									<select name="searchflg1" id="searchflg1" class="search_flg">
										<option value="">-请选择-</option>
										<option value="1" <c:if test="${'1' eq searchflg1}">selected</c:if>>等于</option>
										<option value="2" <c:if test="${'2' eq searchflg1}">selected</c:if>>大于</option>
										<option value="3" <c:if test="${'3' eq searchflg1}">selected</c:if>>小于</option>
									</select>
								</li>
								<li><input type="text" id="area" name="area" value="${area}" style="float: none; width:50px;"></li>
								<li>
									<input type="submit" value="查询"/>
									<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li>
							</ul>
						</form:form>
					</div>

					<div id="showRegulations" class="easyui-window" closed="true" modal="true" minimizable="true" title="规章制度详情" style="width: 580px;height: 250px;padding: 20px">
						<div class="content-box">
							<table id="my_show">
								<tbody id="labRoom_body">

								</tbody>
							</table>
						</div>
					</div>

					<table class="tb" id="my_show">
						<thead>
						<tr>
							<th><a href="javascript:void(0);" onclick="orderByNumber()"><spring:message code="all.trainingRoom.labroom" />编号</a></th>
							<th><a href="javascript:void(0);" onclick="orderByName()"><spring:message code="all.trainingRoom.labroom" />名称</a></th>
								<th>楼宇(楼层)</th>
								<th><a href="javascript:void(0);" onclick="">所属中心</a></th>
							<c:if test="${project eq 'zjcclims'}">
								<th><a href="javascript:void(0);" onclick="orderByLevel()">等级</a></th>
							</c:if>
							<th><a href="javascript:void(0);" onclick="orderByCapacity()">容量</a></th>
							<th><a href="javascript:void(0);" onclick="orderByActive()">使用状态</a></th>
							<th><a href="javascript:void(0);" onclick="orderByReservation()">预约状态</a></th>
							<c:if test="${stationNum eq 'true'}"><!-- 有工位相关需求时显示 -->
								<th>可预约工位数</th>
							</c:if>
							<th><a href="javascript:void(0);" onclick="">操作</a></th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${labRoomVOList}" var="curr">
							<tr>
								<td>${curr.labRoomNumber}</td>
								<td>${curr.labRoomName}</td>
								<%--<td><c:if test="${curr.floorNo ne null}">${curr.systemBuild.buildName}(${curr.floorNo}楼)</c:if>
									<c:if test="${curr.floorNo eq null || curr.floorNo eq ''}"></c:if></td>--%>
								<td>${curr.buildFloor}</td>
								<td>${curr.centerName}</td>
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
								<%--<td>
									<c:choose>
										<c:when test="${curr.labRoomReservation==1}">可预约</c:when>
										<c:otherwise>不可预约</c:otherwise>
									</c:choose>
								</td>--%>
                                <td>${curr.reservationrStatus}</td>
								<c:if test="${stationNum eq 'true'}"><!-- 有工位相关需求时显示 -->
									<td>${curr.labRoomWorker}</td>
								</c:if>
								<td>
										<%--<a href="javascript:void(0)" onclick="opendoor(${curr.id});">远程开门</a>--%>
									<a href='javascript:getLabRoom(${curr.id},${pageModel.currpage})' >查看</a>
									<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5' || sessionScope.auth_level eq '6'}">
										<c:if test="${curr.labRoomActive==1}">
											<a  href="javascript:void(0)" onclick="openSetupLink(${curr.id},${pageModel.currpage},${type})">设置</a>
										</c:if>
										<a onclick="editLabRoom(${curr.id})">编辑</a>
										<c:if test="${sessionScope.auth_level ne '6'}"><!-- 分室管理员不给删除操作 -->
											<a onclick="deleteLabRoom(${curr.id});">删除</a>
										</c:if>
										<c:if test="${newServer eq 'true'}">
											<a href="${pageContext.request.contextPath}/lab/entranceListAll?page=1&labRoomId=${curr.id}">门禁记录</a>
										</c:if>
										<%--<a href="javascript:void(0);" onclick="showRegulations(${curr.id})">规章制度</a>--%>
									</c:if><br>

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
<!--批量设置管理员弹出框-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header bg-light">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h5 class="modal-title">批量设置管理员</h5>
			</div>
			<div class="modal-body" style="height: auto;">
				<div class="row">
					<div class="col-md-12">
						<select id="type_code" name="type_code" class="form-control selectpicker" title="请选择管理员类别">
							<option value="">请选择</option>
							<option value="1">实验室管理员</option>
							<option value="2">物联管理员</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-5">
						<select id="labone" class="form-control selectpicker show-tick" data-first-option="false" data-live-search="true" style="height: 200px;" multiple title="请选择实验室">
							<c:forEach items="${labRoomList}" var="curr">
								<option value="${curr.id}">${curr.labRoomName}[${curr.labRoomNumber}]</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-2 text-center">
						<input class="btn btn-outline-primary btn-sm" type="button" id="labright" value=">>"><br>
						<input class="btn btn-outline-primary btn-sm" type="button" id="lableft" value="<<">
					</div>
					<div class="col-md-5">
						<select id="labtwo" class="form-control show-tick" multiple="multiple" style="height: 100px !important; display: block !important;">
							<%--<option value="333">333</option>--%>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-5">
						<select id="peopleone" class="form-control selectpicker show-tick" data-first-option="false" data-live-search="true" style="height: 200px;" multiple title="请选择人员">
							<c:forEach items="${userList}" var="curr">
								<option value="${curr.username}">${curr.cname}[${curr.username}]</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-2 text-center">
						<input class="btn btn-outline-primary btn-sm" type="button" id="peopleright" value=">>"><br>
						<input class="btn btn-outline-primary btn-sm" type="button" id="peopleleft" value="<<">
					</div>
					<div class="col-md-5">
						<select id="peopletwo" class="form-control show-tick" multiple="multiple" style="height: 100px !important; display: block !important;">
							<%--<option value="333">333</option>--%>
						</select>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm" data-dismiss="modal" onclick="subMultiple();">提交</button>
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
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
