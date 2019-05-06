<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>


<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
    <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

    <script type="text/javascript">
        $(function () {
            $("#print").click(function () {
                $("#my_show").jqprint();
            });
            $("#lab_name").focus();
            $("#lab_name").bind('keydown', function (e) {

                var key = e.which;

                if (key == 13) {

                    e.preventDefault();
                    document.form.action = "${pageContext.request.contextPath}/selectLabList";
                    document.form.submit();

                }

            });
        });


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
            document.queryForm.action = url + page;
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
            document.queryForm.action = url + page;
            document.queryForm.submit();
        }

        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/visualization/roomList?page=1";
        }
        function getFloor(){
            $.ajax({
                     type: "POST",
                     url:"${pageContext.request.contextPath}/visualization/findFloorNumByBuildNumber",
                     data:{'buildNumber':$("#buildNumber").val()},
                 success:function(data){
                               $("#floor").html(data.floorNum);
                               $("#floor").trigger("liszt:updated");
                     }
             });
             }
    </script>
</head>


<body>

<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.visualization.management"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.trainingRoom.setting"/></a></li>

        </ul>
    </div>
</div>

<!-- 查询表单 -->


<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)">实验室及设备设置</a>
            </li>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <%--<div class="title">--%>
                        <%--<div id="title"><spring:message code="left.trainingRoom.setting"/></div>--%>
                    <%--</div>--%>
                    <div class="tool-box">
                        <form:form name="queryForm"
                                   action="${pageContext.request.contextPath}/visualization/roomList?page=1"
                                   method="post">
                            <ul>

                                <li>楼宇：</li>
                                <li><select id="buildNumber" name="buildNumber" value="${buildNumber}"
                                            class="chzn-select" onchange="getFloor()">
                                    <option value="">请选择</option>
                                    <c:forEach items="${builds}" var="build" varStatus="i">
                                        <c:if test="${buildNumber eq build.buildNumber}">
                                            <option value="${build.buildNumber}" selected> ${build.buildName}</option>
                                        </c:if>
                                        <c:if test="${buildNumber ne build.buildNumber}">
                                            <option value="${build.buildNumber}"> ${build.buildName}</option>
                                        </c:if>
                                    </c:forEach>
                                </select></li>

                                <li>楼层：</li>
                                <li><select id="floor" name="floor" class="chzn-select">
                                    <option value="">请选择</option>
                                    <c:forEach items="${floors}" var="floors" varStatus="i">
                                        <c:if test="${floor eq floors.floorNo}">
                                            <option value="${floors.floorNo}" selected> ${floors.floorName}</option>
                                        </c:if>
                                        <c:if test="${floor ne floors.floorNo}">
                                            <option value="${floors.floorNo}"> ${floors.floorName}</option>
                                        </c:if>
                                    </c:forEach>
                                </select></li>

                                <li><input type="submit" value="查询"/></li>
                                <li><input type="button" value="取消" onclick="cancel();"/></li>
                            </ul>

                        </form:form>
                    </div>
                    <div class="content-box">
                        <table class="tb" id="my_show">
                            <thead>
                            <tr>
                                <th>
                                    <center>序号</center>
                                </th>
                                <th>
                                    <center><spring:message code="all.trainingRoom.labroom"/>及设备名称</center>
                                </th>
                                <th>
                                    <center><spring:message code="all.trainingRoom.labroom"/>及设备编号</center>
                                </th>
                                <th>
                                    <center><spring:message code="all.trainingRoom.labroom"/>及设备地点</center>
                                </th>
                                <th>
                                    <center>操作</center>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${labRooms}" var="current" varStatus="i">
                                <tr>
                                    <td>
                                        <center>${i.count}</center>
                                    </td>
                                    <td>
                                        <center>
                                                ${current.labRoomName}
                                            <c:if test="${current.isUsed!=1}">
                                                <font color=red>（<spring:message code="left.trainingRoom.setting"/>已禁用）</font>
                                            </c:if>
                                        </center>
                                    </td>
                                    <td>
                                        <center>${current.labRoomNumber}</center>
                                    </td>
                                    <td>
                                        <c:if test="${not empty current.systemRoom}">
                                            <center>${current.systemBuild.systemCampus.campusName}<!-- 校区 -->
                                                    ${current.systemBuild.buildName} <!-- 楼栋 -->
                                                    ${current.systemRoom.roomName}(${current.systemRoom.roomNo})
                                                <!-- 房间 -->
                                            </center>
                                        </c:if>
                                    </td>
                                    <td>
                                        <center>
                                            <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR' || sessionScope.selected_role eq 'ROLE_ACADEMYLEVELM'}">
                                                <a href="${pageContext.request.contextPath}/visualization/addLabRoomDevice?id=${current.id}">添加设备</a>&nbsp;&nbsp;
                                                <a href="${pageContext.request.contextPath}/visualization/addLabRoomImage?id=${current.id}">添加图片</a>&nbsp;&nbsp;
                                                <a href="${pageContext.request.contextPath}/visualization/roomImage?id=${current.id}"
                                                   target="_blank">设备定位</a>&nbsp;&nbsp;
                                            </c:if>
                                        </center>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>

                        </table>
                        <div class="page">
                            ${totalRecords}条记录,共${pageModel.totalPage}页
                            <a href="javascript:void(0)"
                               onclick="first('${pageContext.request.contextPath}/visualization/roomList?page=1')"
                               target="_self">首页</a>
                            <a href="javascript:void(0)"
                               onclick="previous('${pageContext.request.contextPath}/visualization/roomList?page=')"
                               target="_self">上一页</a>
                            第<select
                                onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                            <option value="${pageContext.request.contextPath}/visualization/roomList?page=${page}">${page}</option>
                            <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                       var="current">
                                <c:if test="${j.index!=page}">
                                    <option value="${pageContext.request.contextPath}/visualization/roomList?page=${j.index}">${j.index}</option>
                                </c:if>
                            </c:forEach></select>页
                            <a href="javascript:void(0)"
                               onclick="next('${pageContext.request.contextPath}/visualization/roomList?page=')"
                               target="_self">下一页</a>
                            <a href="javascript:void(0)"
                               onclick="last('${pageContext.request.contextPath}/visualization/roomList?page=${pageModel.totalPage}')"
                               target="_self">末页</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 下拉框的js -->
    <script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript"
            charset="utf-8"></script>
    <script type="text/javascript">
        var config = {
            '.chzn-select': {search_contains: true},
            '.chzn-select-deselect': {allow_single_deselect: true},
            '.chzn-select-no-single': {disable_search_threshold: 10},
            '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
            '.chzn-select-width': {width: "95%"}
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
    </script>
    <!-- 下拉框的js -->
</body>
</html>
