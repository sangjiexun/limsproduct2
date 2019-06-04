<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/operation/listOperationItemLims.js"></script>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <!-- 导入弹出框进度条的样式 -->
    <link href="${pageContext.request.contextPath}/css/cms/importalertstyle.css" rel="stylesheet" type="text/css">
    <style class="cp-pen-styles">
        .warning {
            position: relative;
            background-color: #F8F8FF;
            border: 1px solid #6DA807;
            border-radius: 10px;
            /*box-shadow: 0.8vw 1vh 3vh rgba(109, 168, 7, 0.8);*/
            background-size: 3em 3em;
            background-image: linear-gradient(-45deg, transparent 0em, transparent 0.8em, #96D923 0.9em, #96D923 2.1em, transparent 2.1em, transparent 2.9em, #96D923 3.1em);
            -webkit-animation: warning-animation 750ms infinite linear;
            -moz-animation: warning-animation 750ms infinite linear;
            animation: warning-animation 750ms infinite linear;
        }
        @-webkit-keyframes warning-animation {
            0% {
                background-position: 0 0;
            }
            100% {
                background-position: 3em 0;
            }
        }
        @-moz-keyframes warning-animation {
            0% {
                background-position: 0 0;
            }
            100% {
                background-position: 3em 0;
            }
        }
        @keyframes warning-animation {
            0% {
                background-position: 0 0;
            }
            100% {
                background-position: 3em 0;
            }
        }
        .warning:before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            height: 100%;
            border-radius: 10px;
            background-image: linear-gradient(to bottom, #6DA807, rgba(171, 226, 77, 0.6) 15%, transparent 60%, #6DA807);
        }
        body, html {
            position: absolute;
            top: 0;
            left: 0;
            bottom: 0;
            right: 0;
            height:60%;
        }
        body {
            background-repeat: no-repeat;
            background-position: center;
            /*background-image: radial-gradient(circle, #F8F8FF, #F8F8FF);*/
        }
        .container {
            width: 80vw;
            margin: 10vh auto 0;
        }
        .container .warning {
            /*height: 10vh;*/
            width: 478px;
            height: 15px;
        }
        .tool-box{
            /*overflow: hidden;*/
        }
        .clear{
            clear: both;
        }
    </style>
    <!-- 导入弹出框的样式结束 -->

    <script type="text/javascript">
        function checkAll() {
            if($("#check_all").attr("checked")) {
                $(":checkbox").attr("checked", true);
            }else {
                $(":checkbox").attr("checked", false);
            }
        }

        //批量删除
        function batchDelete(){
            var array=new Array();
            var flag=false; //判断是否一个未选
            $("input[name='items']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
                if ($(this).is(':checked')) { //判断是否选中
                    flag = true; //只要有一个被选择 设置为 true
                }
            })

            if (flag) {
                $("input[name='items']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
                    if ($(this).is(':checked')) { //判断是否选中
                        array.push($(this).val()); //将选中的值 添加到 array中
                    }
                })
                window.location.href="${pageContext.request.contextPath}/operation/batchDeleteOperationItem?&array="+array+"&status=${status}";
            }else {
                alert("请至少选择一条记录");
            }
        }

        $(document).ready(function(){
            var s=${status};
            for(var i=1;i<=3;i++) {
                if(i==s) {
                    $("#s"+i).addClass("TabbedPanelsTab selected");
                }else {
                    $("#s"+i).addClass("TabbedPanelsTab");
                }
            }
        });
        //取消查询
        function cancel() {
            window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=${orderBy }";
        }

        //设置项目编号
        function saveCodeCustom() {
            document.edit_code_form.action="${pageContext.request.contextPath}/operation/saveCodeCustom?status=${status}";
            document.edit_code_form.submit();
        }

        //排序
        var asc=${asc};//声明全局变量asc
        function orderByNumber(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=10";
            }else window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=0";
        }
        function orderByName(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=11";
            }else window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=1";
        }
        function orderByLab(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=12";
            }else window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=2";
        }
        function orderByCourse(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=13";
            }else window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=3";
        }
        function orderByStatus(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=14";
            }else window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=4";
        }
    </script>
    <script type="text/javascript">
        /*所有rest方法*/
        //审核or编辑
        function viewOperationItemRest(id){
            var lp_name = $("#lp_name").val();
            var term_id = $("#term_id").val();
            var course_number = $("#course_number").val();
            var lp_create_user = $("#lp_create_user").val();
            if($("#lp_name").val()==""){
                lp_name ="-1";
            }
            if($("#term_id").val()==""){
                term_id ="-1";
            }
            if($("#course_number").val()==""){
                course_number ="-1";
            }
            if($("#lp_create_user").val()==""){
                lp_create_user ="-1";
            }
            var url = "${pageContext.request.contextPath}/operation/viewOperationItemLims/${status}/" + id;
            //page 后面的1  flag
            window.location.href=url;
        }
/*        //查看详情--材料
        function listItemMaterialRecordRest(id){
            var lp_name = $("#lp_name").val();
            var term_id = $("#term_id").val();
            var course_number = $("#course_number").val();
            var lp_create_user = $("#lp_create_user").val();
            if($("#lp_name").val()==""){
                lp_name ="-1";
            }
            if($("#term_id").val()==""){
                term_id ="-1";
            }
            if($("#course_number").val()==""){
                course_number ="-1";
            }
            if($("#lp_create_user").val()==""){
                lp_create_user ="-1";
            }
            var url = "${pageContext.request.contextPath}/operation/viewOperationItemLims/${status}/" + id;
            //page后面的0--isMine
            window.location.href=url;
        }*/
    </script>
    <script type="text/javascript">
        function newOperationItem() {
            window.location.href="${pageContext.request.contextPath}/operation/editOperationItemLims?itemId=-1&page=${page}";
        }
        //跳转
        function targetUrl(url) {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
        //弹出填写审核人的表单
        function submitItem(id) {
            $.ajax({
                url:'${pageContext.request.contextPath}/operation/submitOperationItemLims?page=${page}',
                type:'POST',
                data:{id: id},
                success:function(){
                    window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=${page}&status=1&orderBy=9";
                }
            });
        }
        // 项目导入
        function submitForm() {
            var flag = false;  //是否有checkbox被选中
            var ids = "";
            $("input[name='items']:checked").each(function(){
                ids += $(this).val()+",";
                flag = true;
            });

            if(flag)
            {
                if(ids.length > 0)
                {
                    ids = ids.substring(0, ids.length-1);  //去掉最后的逗号
                }
                if($("#term").val()=="")
                {
                    alert("请选择导入学期！");
                    return false;
                }
                document.queryForm.action="../operation/importOperationItem?termId="+$("#term").val()+"&itemIds="+ids;
                document.queryForm.submit();
            }
            else
            {
                alert("至少选择一个实验项目！");
            }
        }
        //学期批量导入
        function allSubmitForm() {

            if($("#term").val()=="")
            {
                alert("请选择导入学期！");
                return false;
            }
            var select = document.getElementById("term");
            var index = select.selectedIndex;
            var options = select.options;
            var toTerm = options[index].text;

            var selectf = document.getElementById("term_id");
            var indexf = selectf.selectedIndex;
            var optionsf = selectf.options;
            var fTerm = optionsf[indexf].text;

            tag = confirm("您是准备将" +fTerm+ "的实验项目全部导入到 " +toTerm+ "中吗？");
            if(tag == true) {
                $('.black_overlay').css("display", "block");
                $('.login_frame').css("display", "block");
                /*document.queryForm.action = "../operation/importAllOperationItem?termId=" + $("#term").val() + "&itemId=" + $("#term_id").val();
                document.queryForm.submit();*/
                $.ajax({
                    url: "../operation/importAllOperationItem?termId=" + $("#term").val() + "&itemId=" + $("#term_id").val(),
                    type: 'POST',
                    async: true,
                    success: function () {
                            $("#hide").attr("style"," display: block;");
                            $("#hideing").attr("style"," display: none;");
                        }
                });
            }
        }
        function listItemMaterialRecordRest(id) {
            var index = layer.open({
                type: 2,
                title: '详情',
                maxmin: true,
                shadeClose: true,
                content: "${pageContext.request.contextPath}/operation/viewOperationItemLims/${status}/" + id,
            });
            layer.full(index);
        }
    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.item.operation"/></a></li>
        </ul>
    </div>
</div>

<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=1&orderBy=${orderBy}">我的项目</a></li>
        <%--<c:if test="${sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'}">--%>
        <li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=2&orderBy=${orderBy }">我的审核</a></li>
        <%--</c:if>--%>
        <li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=3&orderBy=${orderBy}">全部项目</a></li>
        <c:if test="${(fn:contains('zjcclims',PROJECT_NAME) && (
                                    sessionScope.selected_role eq 'ROLE_SUPERADMIN'||
                                    sessionScope.selected_role eq 'ROLE_LABMANAGER'||
                                    sessionScope.selected_role eq 'ROLE_DEPARTMENTHEADER'||
                                    sessionScope.selected_role eq 'ROLE_TEACHER'))||
                                    (!fn:contains('zjcclims',PROJECT_NAME))}">
            <c:if test="${status eq 3}">
                <a class="btn btn-new" href="javascript:void(0);" onclick="batchDelete();">批量删除</a>
            </c:if>
            <c:if test="${status eq 1}">
                <a class="btn btn-new" href="javascript:void(0);" onclick="newOperationItem();">新建</a>
            </c:if>
        </c:if>
    </ul>
    <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
            <div class="content-box">
                <%--<div class="title">--%>
                    <%--<div id="title">实验项目列表</div>--%>
                    <%--<c:if test="${(fn:contains('zjcclims',PROJECT_NAME) && (--%>
                                    <%--sessionScope.selected_role eq 'ROLE_SUPERADMIN'||--%>
                                    <%--sessionScope.selected_role eq 'ROLE_LABMANAGER'||--%>
                                    <%--sessionScope.selected_role eq 'ROLE_DEPARTMENTHEADER'||--%>
                                    <%--sessionScope.selected_role eq 'ROLE_TEACHER'))||--%>
                                    <%--(!fn:contains('zjcclims',PROJECT_NAME))}">--%>
                    <%--<c:if test="${status eq 3}">--%>
                        <%--<a class="btn btn-new" href="javascript:void(0);" onclick="batchDelete();">批量删除</a>--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${status eq 1}">--%>
                        <%--<a class="btn btn-new" href="javascript:void(0);" onclick="newOperationItem();">新建</a>--%>
                    <%--</c:if>--%>
                    <%--</c:if>--%>
                <%--</div>--%>

                <div class="tool-box">
                    <form:form name="queryForm" action="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=${orderBy }" method="post" modelAttribute="operationItem">
                        <ul>
                            <li>名称:
                                <form:input id="lp_name" path="lpName"/></li>
                            <li>学期:
                                <form:select class="chzn-select" path="schoolTerm.id" id="term_id" name="term_id">
                                <option value="-1" >全部</option>
                                <c:forEach items="${schoolTerms}" var="curr">
                                    <c:if test="${curr.id eq termId}">
                                        <form:option value="${curr.id}" selected="selected">${curr.termName}</form:option>
                                    </c:if>
                                    <c:if test="${curr.id ne termId}">
                                        <form:option value="${curr.id}">${curr.termName}</form:option>
                                    </c:if>
                                </c:forEach>
                            </form:select>
                            </li>
                            <li>所属课程:
                                <form:select path="schoolCourseInfo.courseNumber"  id="course_number" class="chzn-select">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${schoolCourseInfos}" var="sc">
                                        <form:option value="${sc.key}">[${sc.key}]${sc.value }</form:option>
                                    </c:forEach>
                                </form:select>
                            </li>
                            <c:if test="${status ne 1}">
                                <li>创建者:
                                    <form:select path="userByLpCreateUser.username"  id="lp_create_user" class="chzn-select">
                                        <form:option value="">请选择</form:option>
                                        <form:options items="${users}"/>
                                    </form:select>
                                </li>
                            </c:if>
                            <li>状态:
                                <form:select class="chzn-select" path="CDictionaryByLpStatusCheck.id">
                                <form:option value="">请选择</form:option>
                                <c:forEach items="${cDictionaries}" var="curr">
                                    <c:if test="${status!=1 && curr.CName ne '草稿'}">
                                        <form:option value="${curr.id}">${curr.CName}</form:option>
                                    </c:if>
                                    <c:if test="${status==1}">
                                        <form:option value="${curr.id}">${curr.CName}</form:option>
                                    </c:if>
                                </c:forEach>
                                <%--<form:options items="${cDictionaries}" itemLabel="CName" itemValue="id"/>--%>
                            </form:select>
                            </li>
                            <li>
                                <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
                                <input type="submit" value="查询"/>
                            </li>

                        </ul>
                        <ul>
                            <c:if test="${status eq 3}">
                                <li>
                                    <select id="term" name="term" class="chzn-select">
                                        <option value="">请选择导入学期</option>
                                        <c:forEach items="${schoolTerms}" var="t">
                                            <option value="${t.id}">${t.termName}</option>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li><input type="button" value="导入全部" onclick="allSubmitForm();"/>
                                    <input type="button" value="导入选中" onclick="submitForm();return confirm('确认导入吗？');"/></li>
                            </c:if>
                        </ul>
                        <div class="clear"></div>
                    </form:form>
                </div>

                <table class="tb" id="my_show">
                    <thead>
                    <tr>
                        <c:if test="${status eq 3}">
                            <th><input id="check_all" type="checkbox" onclick="checkAll();"/></th>
                        </c:if>
                        <th>序号</th>
                        <th><a href="javascript:void(0);" onclick="orderByNumber()";>实验编号</a></th>
                        <th><a href="javascript:void(0);" onclick="orderByName()";>实验名称</a></th>
                        <th>学期</th>
                        <th>所属<spring:message code="all.training.name"/>中心</th>
                        <th>中心主任</th>
                        <th><a href="javascript:void(0);" onclick="orderByLab()";>所属<spring:message code="all.training.name"/>室</a></th>
                        <th><a href="javascript:void(0);" onclick="orderByCourse()";>所属课程</a></th>
                        <th>创建者</th>
                        <%--<th>指定审核人</th>--%>
                        <th><a href="javascript:void(0);" onclick="orderByStatus()";>审核状态</a></th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listOperationItem}" var="curr" varStatus="i">
                        <tr>
                            <c:if test="${status eq 3}">
                                <td><input id="check_${curr.id}" name="items" type="checkbox" value="${curr.id}"/></td>
                            </c:if>
                            <td>${i.count}</td>
                            <td>${curr.lpCodeCustom}</td>
                            <td>${curr.lpName}</td>
                            <td>${curr.schoolTerm.termName}</td>
                            <td>${curr.labCenter.centerName}</td>
                            <td>${curr.labCenter.userByCenterManager.cname}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${curr.labRoom ne null}">${curr.labRoom.labRoomName}</c:when>
                                    <c:otherwise>
                                        <c:if test="${curr.labRooms ne null}">
                                            <c:forEach items="${curr.labRooms}" var="lab">${lab.labRoomName}
                                                <br></c:forEach>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:if test="${curr.schoolCourseInfo.courseName ne null}">${curr.schoolCourseInfo.courseName}[${curr.schoolCourseInfo.courseNumber}]</c:if></td>
                            <td>${curr.userByLpCreateUser.cname}</td>
                            <%--<td>${curr.userByLpCheckUser.cname}</td>--%>
                            <td>
                                <c:if test="${curr.CDictionaryByLpStatusCheck.CNumber ne 2 }">
                                    ${curr.CDictionaryByLpStatusCheck.CName}
                                </c:if>
                                <c:if test="${curr.CDictionaryByLpStatusCheck.CNumber eq 2 }">
                                    ${auditShow[curr.id]}
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${(fn:contains('zjcclims',PROJECT_NAME) && (
                                    sessionScope.selected_role eq 'ROLE_SUPERADMIN'||
                                    sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'||
                                    sessionScope.selected_role eq 'ROLE_LABMANAGER'||
                                    sessionScope.selected_role eq 'ROLE_DEPARTMENTHEADER'||
                                    sessionScope.selected_role eq 'ROLE_TEACHER'))||
                                    (!fn:contains('zjcclims',PROJECT_NAME))}">
                                <%--<a title="打印" href="#" onclick="printOperationItem(${curr.id});" id="btn">打印</a>--%>
                                <c:if test="${draft.id==curr.CDictionaryByLpStatusCheck.id}">
                                    <c:if test="${curr.userByLpCreateUser.username==currUser.username || fn:contains(curr.labRoom.labRoomAdmins,currUser.username)}">
                                        <a href="javascript:void(0)" onclick="submitItem(${curr.id});">提交</a>
                                        <a href="${pageContext.request.contextPath}/operation/editOperationItemLims?itemId=${curr.id}&page=${page}">编辑</a>
                                        <a href="${pageContext.request.contextPath}/operation/deleteOperationItemLims?operationItemId=${curr.id}&status=${status}&page=${page}" onclick="return confirm('确定删除？');">删除</a>
                                    </c:if>
                                </c:if>
                                <c:if test="${curr.CDictionaryByLpStatusCheck.id==toCheck.id &&
                                 idAndAuth[curr.id] eq sessionScope.selected_role &&
                                  status != 1}">
                                    <!-- 可以审核&是审核人&不在‘我的项目’栏 -->
                                    <a href="javascript:void(0);" onclick="viewOperationItemRest(${curr.id})">审核</a>
                                </c:if>
                                <c:if test="${curr.CDictionaryByLpStatusCheck.id==checkYes.id}">
                                   <%-- <c:if test="${sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR' || sessionScope.selected_role eq 'ROLE_EXPERIMENTALTEACHING'
                                            || sessionScope.selected_role eq 'ROLE_SUPERADMIN' || sessionScope.selected_role eq 'ROLE_ASSETMANAGEMENT'}">--%>
                                    <c:if test="${sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'}">
                                        <a href="${pageContext.request.contextPath}/operation/editOperationItemLims?itemId=${curr.id}&page=${page}">编辑</a>
                                    </c:if>
                                </c:if>
                                <a href="javascript:void(0);" onclick="listItemMaterialRecordRest(${curr.id});">详情</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- 分页 -->
                <div class="page" >
                    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=${orderBy }')" target="_self">首页</a>
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=${pageModel.previousPage}&status=${status}&orderBy=${orderBy }')" target="_self">上一页</a>
                    第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                    <option value="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=${pageModel.currpage}&status=${status}&orderBy=${orderBy }">${pageModel.currpage}</option>
                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                        <c:if test="${j.index!=pageModel.currpage}">
                            <option value="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=${j.index}&status=${status}&orderBy=${orderBy }">${j.index}</option>
                        </c:if>
                    </c:forEach></select>页
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=${pageModel.nextPage}&status=${status}&orderBy=${orderBy }')" target="_self">下一页</a>
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=${pageModel.lastPage}&status=${status}&orderBy=${orderBy }')" target="_self">末页</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="black_overlay" style="display:none">
    <div class="login_frame" style="display:none">
        <div class="title">
            <div >实验项目导入
                <li><a id="hideing" type="button" style="display: block;">
                    <h1>正在导入实验项目...</h1></a></li></div>
        </div>
        <div class="container">
            <div class="warning">
            </div>
        </div>
        <div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
        </div>
        <div class="content" >
            <li><a class="btn btn-new" id="hide" type="button" style="display: none;"
                   href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=1&orderBy=9">
                导入完成，返回实验项目列表</a></li>
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
