<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>

<html>
<head>
	<meta name="decorator" content="iframe"/>

	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/resourceContainer/resourceContainer.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/css/resourceContainer/font-awesome.min.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/resourceContainer/resourceContainer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>

	<script type="text/javascript">

        //首页
        function first(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

        //末页
        function last(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

        //上一页
        function previous(url) {
            var page =${page};
            if (page == 1) {
                page = 1;
            } else {
                page = page - 1;
            }
            //alert("上一页的路径："+url+page);
            document.queryForm.action = url + page + '&type=${type}';
            document.queryForm.submit();
        }

        //下一页
        function next(url) {
            var totalPage =${pageModel.totalPage};
            var page =${page};
            if (page >= totalPage) {
                page = totalPage;
            } else {
                page = page + 1
            }
            //alert("下一页的路径："+page);
            document.queryForm.action = url + page + '&type=${type}';
            document.queryForm.submit();
        }

        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/visualization/changeBackground?page=1&type=${type}";
        }
	</script>
	<link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
</head>


<body>

<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.visualization.visual"/></a></li>
			<li class="end"><a href="javascript:void(0)">地图管理</a></li>
		</ul>
	</div>
</div>
<form id="upLoadWindow" style="display: none;" method="post" enctype="multipart/form-data"></form>
<!-- 查询表单 -->
<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<div class="title">
						<div id="title">地图管理</div>
						<select id="backgroundType" style="margin: 6px;" class="chzn-select" onchange="changeType()">
							<%--<option value="">请选择</option>--%>
							<option value="campus">校区</option>
							<option value="build">楼宇</option>
							<option value="room">房间</option>
							<option value="device">设备</option>
						</select>
					</div>
					<div class="tool-box">
						<form:form name="queryForm"
								   action="${pageContext.request.contextPath}/visualization/changeBackground?page=1&type=${type}"
								   method="post" modelAttribute="labRoomDevice">
							<ul>

								<li>楼宇名称:<form:input id="buildName" path="labRoom.labAnnex.labCenter.systemBuild.buildName"/></li>

								<li>所属校区:
									<form:select id="campusNumber" path="labRoom.labAnnex.labCenter.systemCampus.campusNumber" class="chzn-select">
										<form:option value="">请选择</form:option>
										<c:forEach items="${listSystemCampus}" var="lsc">
											<form:option value="${lsc.campusNumber}">${lsc.campusName}</form:option>
										</c:forEach>
									</form:select>
								</li>
								<li>房间:<form:input id="labRoomName" path="labRoom.labRoomName"/></li>
								<li>设备:<form:input id="deviceName" path="schoolDevice.deviceName"/></li>

								<li>所属单位:
									<form:select id="academyNumber" path="labRoom.labAnnex.labCenter.schoolAcademy.academyNumber" class="chzn-select">
										<form:option value="">请选择</form:option>
										<c:forEach items="${listSchoolAcademy}" var="lsa">
											<form:option value="${lsa.academyNumber}">${lsa.academyName}</form:option>
										</c:forEach>
									</form:select>
								</li>

								<li><input type="submit" value="查询"/>
									<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li>
							</ul>
						</form:form>
					</div>
					<div class="content-box">
						<table class="tb" id="my_show">
							<thead>
							<c:if test="${type=='campus'}">
								<tr>
									<th><center>序号</center></th>
									<th><center>校区</center></th>
									<th><center>楼宇数</center></th>
									<%--<th><center>状态</center></th>--%>
									<th><center>操作</center></th>
								</tr>
							</c:if>
							<c:if test="${type=='build'}">
								<tr>
									<th><center>序号</center></th>
									<th><center>所属校区</center></th>
									<th><center>楼宇编号/楼宇名称</center></th>
									<th><center>层数</center></th>
									<th><center>房间数</center></th>
									<%--<th><center>状态</center></th>--%>
									<th><center>操作</center></th>
								</tr>
							</c:if>
							<c:if test="${type=='room'}">
								<tr>
									<th><center>序号</center></th>
									<th><center>所属校区</center></th>
									<th><center>楼宇编号/楼宇名称</center></th>
									<th><center>楼层</center></th>
									<th><center>房间编号</center></th>
									<th><center>名称</center></th>
									<th><center>设备数</center></th>
									<%--<th><center>状态</center></th>--%>
									<th><center>操作</center></th>
								</tr>
							</c:if>
							<c:if test="${type=='device'}">
								<tr>
									<th><center>序号</center></th>
									<th><center>所属校区</center></th>
									<th><center>楼宇编号/楼宇名称</center></th>
									<th><center>楼层</center></th>
									<th><center>房间编号</center></th>
									<th><center>名称</center></th>
									<th><center>设备编号</center></th>
									<th><center>设备名称</center></th>
									<%--<th><center>状态</center></th>--%>
									<%--<th><center>操作</center></th>--%>
								</tr>
							</c:if>
							</thead>
							<tbody>
							<c:forEach items="${commonLists}" var="current" varStatus="i">
								<c:if test="${type=='campus'}">
									<tr>
										<input type="hidden"  id="实验室管理/庚商实验室/${type}(${current.campusNumber})" value="实验室管理/庚商实验室/${type}(${current.campusNumber})"/>
										<td><center>${i.count}</center></td>
										<td><center>${current.campusName}<!-- 校区 --></center></td>
										<td><center>${current.systemBuilds.size()}</center></td>
										<%--<td><center>已标识<!-- 状态 --></center></td>--%>
										<td>
											<center id="actions${i.count-1}">
												<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">--%>
													<%--<a href="${pageContext.request.contextPath}/visualization/getSystemFloorPic?buildNumber=${current.buildNumber}">添加楼层图片</a>&nbsp;&nbsp;--%>
												<%--</sec:authorize>--%>
												<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">--%>
													<%--<a onclick="openUploadWindowByPath('实验室管理/庚商实验室/${type}(${current.campusNumber})')">编辑底图</a>&nbsp;&nbsp;--%>
												<%--</sec:authorize>--%>
												<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">--%>
													<%--<a href="">编辑标识</a>&nbsp;&nbsp;--%>
												<%--</sec:authorize>--%>
											</center>
										</td>
									</tr>
								</c:if>
								<c:if test="${type=='build'}">
									<tr>
										<input type="hidden"  id="实验室管理/庚商实验室/${type}(${current.buildNumber})" value="实验室管理/庚商实验室/${type}(${current.buildNumber})"/>
										<td><center>${i.count}</center></td>
										<td><center>${current.systemCampus.campusName}<!-- 校区 --></center></td>
										<%--<td><center>20</center></td>--%>
										<td><center>${current.buildNumber}/${current.buildName}</center></td>
											<%--<td>--%>
											<%--<center>${current.buildNumber}</center>--%>
											<%--</td>--%>
											<%--<td>--%>
											<%--<center>${current.schoolAcademy.academyName}<!-- 学院 --></center>--%>
											<%--</td>--%>
										<td><center>${current.floorNum}<!-- 层数 --></center></td>
										<%--<td><center>${current.labRoom.labAnnex.labCenter.systemBuild.floorNum}<!-- 房间数 --></center></td>--%>
										<td><center>${current.labRooms.size()}<!-- 房间数 --></center></td>
										<%--<td><center><!-- 状态 --></center></td>--%>
										<td>
											<center id="actions${i.count-1}">
													<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">--%>
													<%--<a href="${pageContext.request.contextPath}/visualization/getSystemFloorPic?buildNumber=${current.buildNumber}">添加楼层图片</a>&nbsp;&nbsp;--%>
													<%--</sec:authorize>--%>
												<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">--%>
													<%--<a onclick="openUploadWindowByPath('实验室管理/庚商实验室/${type}(${current.buildNumber})')">编辑底图</a>&nbsp;&nbsp;--%>
												<%--</sec:authorize>--%>
												<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">--%>
													<%--<a href="">编辑标识</a>&nbsp;&nbsp;--%>
												<%--</sec:authorize>--%>
											</center>
										</td>
									</tr>
								</c:if>
								<c:if test="${type=='room'}">
									<tr>
										<input type="hidden"  id="实验室管理/庚商实验室/${type}(${current.roomNumber})" value="实验室管理/庚商实验室/${type}(${current.roomNumber})"/>
										<td><center>${i.count}</center></td>
										<td><center>${current.campusName}<!-- 校区 --></center></td>
										<%--<td><center>20</center></td>--%>
										<td><center>${current.buildNumber}/${current.buildName}</center></td>
											<%--<td>--%>
											<%--<center>${current.buildNumber}</center>--%>
											<%--</td>--%>
											<%--<td>--%>
											<%--<center>${current.schoolAcademy.academyName}<!-- 学院 --></center>--%>
											<%--</td>--%>
										<%--<td><center>${current.systemBuild.floorNum}<!-- 层数 --></center></td>--%>
										<td><center>${current.floorNo}<!-- 楼层 --></center></td>
										<%--<td><center>${current.systemBuild.floorNum}<!-- 房间数 --></center></td>--%>
										<td><center>${current.roomNo}<!-- 房间编号 --></center></td>
										<td><center>${current.roomName}<!-- 名称 --></center></td>
										<%--<td><center>${current.labRoom.labAnnex.labCenter.systemBuild.floorNum}<!-- 设备数 --></center></td>--%>
										<td><center>${current.deviceNo}<!-- 设备数 --></center></td>
										<%--<td><center><!-- 状态 --></center></td>--%>
										<td>
											<center id="actions${i.count-1}">
													<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">--%>
													<%--<a href="${pageContext.request.contextPath}/visualization/getSystemFloorPic?buildNumber=${current.buildNumber}">添加楼层图片</a>&nbsp;&nbsp;--%>
													<%--</sec:authorize>--%>
												<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">--%>
													<%--<a onclick="openUploadWindowByPath('实验室管理/庚商实验室/${type}(${current.roomNumber})')">编辑底图</a>&nbsp;&nbsp;--%>
												<%--</sec:authorize>--%>
												<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">--%>
													<%--<a href="">编辑标识</a>&nbsp;&nbsp;--%>
												<%--</sec:authorize>--%>
											</center>
										</td>
									</tr>
								</c:if>
								<c:if test="${type=='device'}">
									<tr>
										<input type="hidden"  id="实验室管理/庚商实验室/${type}(${current.id})" value="实验室管理/庚商实验室/${type}(${current.id})"/>
										<td><center>${i.count}</center></td>
										<td><center>${current.labRoom.labAnnex.labCenter.systemCampus.campusName}<!-- 校区 --></center></td>
										<td><center>${current.labRoom.labAnnex.labCenter.systemBuild.buildNumber}/${current.labRoom.labAnnex.labCenter.systemBuild.buildName}</center></td>
										<td><center>${current.labRoom.floorNo}<!-- 楼层 --></center></td>
										<td><center>${current.labRoom.labRoomNumber}<!-- 房间编号 --></center></td>
										<td><center>${current.labRoom.labRoomName}<!-- 名称 --></center></td>
										<td><center>${current.schoolDevice.deviceNumber}<!-- 设备编号 --></center></td>
										<td><center>${current.schoolDevice.deviceName}<!-- 设备名称 --></center></td>
										<%--<td><center><!-- 状态 --></center></td>--%>
										<%--<td>--%>
											<%--<center id="actions${i.count}">--%>
													<%--&lt;%&ndash;<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">&ndash;%&gt;--%>
													<%--&lt;%&ndash;<a href="${pageContext.request.contextPath}/visualization/getSystemFloorPic?buildNumber=${current.buildNumber}">添加楼层图片</a>&nbsp;&nbsp;&ndash;%&gt;--%>
													<%--&lt;%&ndash;</sec:authorize>&ndash;%&gt;--%>
												<%--&lt;%&ndash;<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">&ndash;%&gt;--%>
													<%--&lt;%&ndash;<a onclick="openUploadWindowByPath('实验室管理/庚商实验室/${type}(${current.id})')">编辑底图</a>&nbsp;&nbsp;&ndash;%&gt;--%>
												<%--&lt;%&ndash;</sec:authorize>&ndash;%&gt;--%>
												<%--&lt;%&ndash;<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">&ndash;%&gt;--%>
													<%--&lt;%&ndash;<a href="">编辑标识</a>&nbsp;&nbsp;&ndash;%&gt;--%>
												<%--&lt;%&ndash;</sec:authorize>&ndash;%&gt;--%>
											<%--</center>--%>
										<%--</td>--%>
									</tr>
								</c:if>
							</c:forEach>
							</tbody>

						</table>
						<div class="page">
							${totalRecords}条记录,共${pageModel.totalPage}页
							<a href="javascript:void(0)"
							   onclick="first('${pageContext.request.contextPath}/visualization/changeBackground?page=1&type=${type}')"
							   target="_self">首页</a>
							<a href="javascript:void(0)"
							   onclick="previous('${pageContext.request.contextPath}/visualization/changeBackground?page=')"
							   target="_self">上一页</a>
							第<select onchange="first(this.options[this.selectedIndex].value)">
							<option value="${pageContext.request.contextPath}/visualization/changeBackground?page=${page}&type=${type}">${page}</option>
							<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
									   var="current">
								<c:if test="${j.index!=page}">
									<option value="${pageContext.request.contextPath}/visualization/changeBackground?page=${j.index}&type=${type}">${j.index}</option>
								</c:if>
							</c:forEach></select>页
							<a href="javascript:void(0)"
							   onclick="next('${pageContext.request.contextPath}/visualization/changeBackground?page=')"
							   target="_self">下一页</a>
							<a href="javascript:void(0)"
							   onclick="last('${pageContext.request.contextPath}/visualization/changeBackground?page=${pageModel.totalPage}&type=${type}')"
							   target="_self">末页</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script  type="application/javascript">
    $(function(){
        initDirectoryEngine({
            getHostsUrl:"${pageContext.request.contextPath}/shareApi/getHosts"
		});
        initUploadWindow({
            username: ${createUser},
//            cnameUrl: "../teach/resource/getCnames",
            afterUpLoad:function (form,state,fileid) {
                var directoryId=$("#directoryId").val();
                console.log(fileid);
                $.ajax({
                    url:visualHost+"/saveBackGroundFileId?directoryId="+directoryId + "&fileId=" + fileid,
                    type: "GET",
                    dataType: "text",
					success:function (data) {
//                        var pathTitle = $('#uploadTitle').val();
//						console.log(pathTitle);
//						$('#'+pathTitle).parent().
                        location.reload();
                    }
                });
            }
        });
    });
    $(function(){
        $("#backgroundType").val('${type}');
        var directoryNamesList = new Array();
        var ids = new Array();
        <c:forEach items="${commonLists}" var="current">
        	<c:if test="${type=='campus'}">
				directoryNamesList.push('实验室管理/庚商实验室/${type}(${current.campusNumber})')
				ids.push(${current.campusNumber});
			</c:if>
			<c:if test="${type=='build'}">
				directoryNamesList.push('实验室管理/庚商实验室/${type}(${current.buildNumber})')
        		ids.push(${current.buildNumber});
			</c:if>
			<c:if test="${type=='room'}">
				directoryNamesList.push('实验室管理/庚商实验室/${type}(${current.roomNumber})')
        		ids.push(${current.roomNumber});
			</c:if>
			<c:if test="${type=='device'}">
				directoryNamesList.push('实验室管理/庚商实验室/${type}(${current.id})')
        		ids.push(${current.id});
			</c:if>
        </c:forEach>
        console.log(ids);
        getDirectoryIds({
            directoryNamesList:directoryNamesList,
        	type:2,
			success:function (data) {
				$.ajax({
					url:visualHost+"/getBackgroundurlByDirId?directoryIdArray="+data,
                    type: "GET",
                    dataType: "json",
                    success:function (data) {
                        console.log(data);
                        var booleans = data;
                        var directoryName;
                        for(i=0;i<booleans.length;i++){
                            directoryName='实验室管理/庚商实验室/${type}('+ids[i]+')';
                            if(booleans[i]!=null){
                                $('#actions'+i).append(
                                    <%--'<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">'+--%>
                                    '<a onclick="openUploadWindowByPath(\''+directoryName+'\')">编辑底图</a>&nbsp&nbsp'+
                                    <%--'</sec:authorize>'+--%>
                                    <%--'<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">'+--%>
                                    '<a href="${pageContext.request.contextPath}/visualization/show/visualNew?directoryName='+directoryName+'&type=${type}">编辑标识</a>'
                                    <%--'</sec:authorize>'--%>
                                );
                            }else if (booleans[i]==null){
                                $('#actions'+i).append(
                                    <%--'<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">'+--%>
                                    '<a onclick="openUploadWindowByPath(\''+directoryName+'\')">上传底图</a>&nbsp&nbsp'
                                    <%--'</sec:authorize>'+--%>
                                );
                            }
                        }
                    }
				})
				<%--console.log(data);--%>
				<%--var booleans = [31105,31139,null,31141,null];--%>
				<%--var directoryName;--%>
				<%--for(i=0;i<booleans.length;i++){--%>
                    <%--directoryName='实验室管理/庚商实验室/${type}('+ids[i]+')';--%>
						<%--if(booleans[i]==null){--%>
							<%--$('#actions'+i).append(--%>
								<%--&lt;%&ndash;'<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">'+&ndash;%&gt;--%>
								<%--'<a onclick="openUploadWindowByPath(\''+directoryName+'\')">编辑底图</a>&nbsp&nbsp'+--%>
								<%--&lt;%&ndash;'</sec:authorize>'+&ndash;%&gt;--%>
								<%--&lt;%&ndash;'<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">'+&ndash;%&gt;--%>
								<%--'<a href="${pageContext.request.contextPath}/visualization/show/visualNew?directoryName='+directoryName+'&type=${type}">编辑标识</a>'--%>
								<%--&lt;%&ndash;'</sec:authorize>'&ndash;%&gt;--%>
							<%--);--%>
						<%--}else if (booleans[i]!=null){--%>
                            <%--$('#actions'+i).append(--%>
                                <%--&lt;%&ndash;'<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">'+&ndash;%&gt;--%>
                                <%--'<a onclick="openUploadWindowByPath(\''+directoryName+'\')">上传底图</a>&nbsp&nbsp'--%>
                                <%--&lt;%&ndash;'</sec:authorize>'+&ndash;%&gt;--%>
                            <%--);--%>
						<%--}--%>
				<%--}--%>
            }
		})
    });
    function changeType() {
        var type = $("#backgroundType").val();
        window.location.href="${pageContext.request.contextPath}/visualization/changeBackground?page=1"+ "&type=" + type;
	}
</script>
</body>

</html>
