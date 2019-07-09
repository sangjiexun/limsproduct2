<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
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

        //添加项目
        function addItems(lab_id){
            var array = new Array();
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
                $.ajax({
                    url:'${pageContext.request.contextPath}/labRoom/saveLabRoomOperationItem?roomId='+ lab_id +'&array='+array,
                    type:'POST',
                    success:function(){
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);
                    }
                });
            }else {
                alert("请至少选择一条记录");
            }
        }

        //取消查询
        function cancel() {
            window.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=${status}&orderBy=${orderBy }";
        }
    </script>
    <script type="text/javascript">
        //跳转
        function targetUrl(url) {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
    </script>
</head>

<body>
<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
        <a class="btn btn-new" href="javascript:void(0);" onclick="addItems('${lab_id}');">添加</a>
    </ul>
    <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
            <div class="content-box">
                <table class="tb" id="my_show">
                    <thead>
                    <tr>
                        <th><input id="check_all" type="checkbox" onclick="checkAll();"/></th>
                        <th>序号</th>
                        <th>项目编号</th>
                        <th>项目名称</th>
                        <th>学期</th>
                        <th>所属<spring:message code="all.training.name"/>中心</th>
                        <th>中心主任</th>
                        <th>所属课程</th>
                        <th>创建者</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listOperationItem}" var="curr" varStatus="i">
                        <tr>
                            <td><input id="check_${curr.id}" name="items" type="checkbox" value="${curr.id}"/></td>
                            <td>${i.count}</td>
                            <td>${curr.lpCodeCustom}</td>
                            <td>${curr.lpName}</td>
                            <td>${curr.schoolTerm.termName}</td>
                            <td>${curr.labCenter.centerName}</td>
                            <td>${curr.labCenter.userByCenterManager.cname}</td>
                            <td><c:if test="${curr.schoolCourseInfo.courseName ne null}">${curr.schoolCourseInfo.courseName}[${curr.schoolCourseInfo.courseNumber}]</c:if></td>
                            <td>${curr.userByLpCreateUser.cname}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- 分页 -->
                <%--<div class="page" >
                    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/operation/layerOperationItemLims?currpage=1')" target="_self">首页</a>
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/operation/layerOperationItemLims?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
                    第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                    <option value="${pageContext.request.contextPath}/operation/layerOperationItemLims?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                        <c:if test="${j.index!=pageModel.currpage}">
                            <option value="${pageContext.request.contextPath}/operation/layerOperationItemLims?currpage=${j.index}">${j.index}</option>
                        </c:if>
                    </c:forEach></select>页
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/operation/layerOperationItemLims?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/operation/layerOperationItemLims?currpage=${pageModel.lastPage}')" target="_self">末页</a>
                </div>--%>
            </div>
        </div>
    </div>
</div>
<div class="
" style="display:none">
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
