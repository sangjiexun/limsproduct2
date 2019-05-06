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
        function cancel()
        {
            window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=9&type=4";
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
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=10&type=4";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=0&type=4";
        }
        function orderByName(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=11&type=4";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=1&type=4";
        }
        function orderByLabCenter(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=12&type=4";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=2&type=4";
        }
        function orderByCapacity(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=13&type=4";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=3&type=4";
        }
        function orderByArea(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=14&type=4";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=4&type=4";
        }
        function orderByActive(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=15&type=4";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=5&type=4";
        }
        function orderByReservation(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=16&type=4";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=6&type=4";
        }
        function orderByRoomAddress(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=17&type=4";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=7&type=4";
        }
        function orderByLevel(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=18&type=4";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=8&type=4";
        }

    </script>
    <script type="text/javascript">
        function getClassRoom(id, currpage, type){
            var index = layer.open({
                type: 2,
                title: '查看',
                maxmin: true,
                shadeClose: true,
                content: "${pageContext.request.contextPath}/labRoom/getLabRoom?id=" + id + "&currpage=" + currpage + "&type= " + type
            });
            layer.full(index);
        }

        function editClassRoom(id, page, type) {
            var index = layer.open({
                type: 2,
                title: '编辑',
                maxmin: true,
                shadeClose: true,
                content: "${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId=" + id + "&type=" + type + "&page=" + page,
                end:function(){
                    document.queryForm.action="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=" + page + "&orderBy=${orderBy}&type=" + type;
                    document.queryForm.submit();
                }
            });
            layer.full(index);
        }
    </script>

    <style type="text/css">
        .tool-box {
            overflow: hidden;
        }
        .tool-box ul{
            width: 75%;
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
</script>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management" /></a></li>
            <li class="end"><a href="javascript:void(0)">教室管理</a></li>
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
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)">教室列表</a>
            </li>
            <c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
                <a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId=0&type=4&page=${page}">新建</a>
            </c:if>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <%--<div class="title">--%>
                        <%--<div id="title">教室列表</div>--%>
                        <%--<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">--%>
                            <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId=0&type=4&page=${page}">新建</a>--%>
                        <%--</c:if>--%>
                    <%--</div>--%>

                    <div class="tool-box" style="overflow: hidden">
                        <form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=${orderBy }&type=4" method="post" modelAttribute="labRoom">
                            <ul>
                                <li>教室名称:<form:input id="lab_name" path="labRoomName"/></li>
                                <li>教室编号:<form:input id="labRoomNumber" path="labRoomNumber"/></li>
                                <c:if test="${baseManage eq 'true'}">
                                    <li>所属基地:<form:input id="lab_base" path="labBase.labName"/></li>
                                </c:if>
                                <li>所属中心:<form:select id="labCenter" path="labCenter.id" class="chzn-select" >
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${listLabCenter}" var="l">
                                        <form:option value="${l.id}">${l.centerName}</form:option>
                                    </c:forEach>
                                    </form:select>
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
                            <%--<th><a href="javascript:void(0);" onclick="orderByLabCenter()">所属实验中心</a></th>--%>
                            <th><a href="javascript:void(0);" onclick="orderByNumber()">教室编号</a></th>
                            <th><a href="javascript:void(0);" onclick="orderByName()">教室名称</a></th>
                            <%--<th><a href="javascript:void(0);" onclick="orderByRoomAddress()">房间号</a></th>
                            --%>
                            <th>楼宇(楼层)</th>
                            <c:if test="${project eq 'zjcclims'}">
                                <th><a href="javascript:void(0);" onclick="orderByLevel()">等级</a></th>
                            </c:if>
                            <th><a href="javascript:void(0);" onclick="orderByCapacity()">容量</a></th>
                            <%--<th><a href="javascript:void(0);" onclick="orderByArea()">使用面积</a></th>
                            --%><%--<th>房间号</th>--%>
                            <th><a href="javascript:void(0);" onclick="orderByActive()">使用状态</a></th>
                            <th><a href="javascript:void(0);" onclick="orderByReservation()">预约状态</a></th>
                            <%--
                             <th><a href="javascript:void(0);" onclick="orderByLabAnnex()">所属实训室</a></th>
                             --%><%--<th>所属实训中心</th>
								 <th>所属基地</th>
								 <th>可预约工位数</th>
								--%>
                            <th><a href="javascript:void(0);" onclick="">所属中心</a></th>
                            <th><a href="javascript:void(0);"  onclick="" >门禁状态查询</a> </th>
                            <th><a href="javascript:void(0);" onclick="">操作</a></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listClassRoom}" var="curr">
                            <tr>
                                    <%--<td>${curr.labCenter.centerName}</td>--%>
                                <td>${curr.labRoomNumber}</td>
                                <td>${curr.labRoomName}</td>
                                    <%--<td>${curr.labRoomAddress}</td>
                                    --%>
                                <td>${labRoom.systemBuild.buildName}(${labRoom.floorNo}楼)</td>
                                <c:if test="${project eq 'zjcclims'}">
                                    <td>
                                        <c:if test="${curr.labRoomLevel==0}">特级</c:if>
                                        <c:if test="${curr.labRoomLevel==1}">一级</c:if>
                                        <c:if test="${curr.labRoomLevel==2}">二级</c:if>
                                    </td>
                                </c:if>
                                <td>${curr.labRoomCapacity}</td>
                                    <%--<td>${curr.labRoomArea}</td>
                                    --%><%--<td>${curr.systemRoom}</td>
	    --%><td>
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
                                    <%--<td>${curr.labCenter.centerName}</td>
                                    <td>${curr.labBase.baseName}</td>
                                    <td>${curr.labRoomWorker}</td>
                                    --%>
                                <td>${curr.labCenter.centerName}</td>
                                <td>
                                    <c:forEach items="${curr.labRoomAgents}" var="curr1">
                                        <c:choose>
                                            <c:when test="${curr1.hardwareStatus==1}">门关闭</c:when>
                                            <c:when test="${curr1.hardwareStatus==2}">门开启</c:when>
                                        </c:choose>
                                    </c:forEach>
                                </td>
                                <td>
                                        <%--<a href="javascript:void(0)" onclick="opendoor(${curr.id});">远程开门</a>--%>
                                    <%--<a href="${pageContext.request.contextPath}/labRoom/getLabRoom?id=${curr.id}&currpage=${pageModel.currpage}&type=${type}">查看</a>--%>
                                                <%--修改为弹框--%>
                                    <a href="javascript:void(0)" onclick="getClassRoom('${curr.id}', '${pageModel.currpage}', '${type}')">查看</a>
                                    <c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5' || sessionScope.auth_level eq '6'}">
                                        <c:if test="${curr.labRoomActive==1}">
                                            <a  href="javascript:void(0)" onclick="openSetupLink(${curr.id},${pageModel.currpage},${type})">设置</a>
                                        </c:if>
                                        <a href="javascript:void(0)" onclick="editClassRoom(${curr.id},${pageModel.currpage},${type})">编辑</a>
                                        <%-- <a href="${pageContext.request.contextPath}/labRoom/deleteLabRoom?labRoomId=${curr.id}" onclick="return confirm('确定删除？');">删除</a> --%>
                                        <a href="${pageContext.request.contextPath}/labRoom/deleteLabRoom?labRoomId=${curr.id}&type=4&page=${page}" onclick="return confirm('确定删除？');">删除</a>
                                        <%--<a href="javascript:void(0);" onclick="showRegulations(${curr.id})">规章制度</a>--%>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!-- 分页[s] -->
                    <div class="page" >
                        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                        <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=${orderBy }&type=4')" target="_self">首页</a>
                        <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.previousPage}&orderBy=${orderBy }&type=4')" target="_self">上一页</a>
                        第<select onchange="targetUrl(this.options[this.selectedIndex].value)">
                        <option value="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.currpage}&orderBy=${orderBy }&type=4">${pageModel.currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                            <c:if test="${j.index!=pageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${j.index}&orderBy=${orderBy }&type=4">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.nextPage}&orderBy=${orderBy }&type=4')" target="_self">下一页</a>
                        <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=${pageModel.lastPage}&orderBy=${orderBy }&type=4')" target="_self">末页</a>
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
</body>
</html>

