<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <style>
        .chzn-container{
            width: 100%!important;
        }
        .popup_box table th,
        .popup_box table td{
            width:16.6%;
        }
    </style>
    <script type="text/javascript">
        //分页跳转
        function targetUrl(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }
        function edit(id) {
            var index = layer.open({
                type: 2,
                title: '夏令营活动-编辑',
                maxmin: true,
                shadeClose: true,
                content: "${pageContext.request.contextPath}/performance/editPerformance?id="+id+"&type=5",
            });
            layer.full(index);
        }
        //取消查询
        function cancel()
        {
            window.location.href="${pageContext.request.contextPath}/performance/listPerformance?currpage=1&type=5";
        }
        function save() {
            $.ajax({
                url:'${pageContext.request.contextPath}/performance/savePerformance?type=5',
                type:'POST',
                data:$('#queryForm').serialize(),
                error:function (request){
                    alert('请求错误!');
                },
                success:function(){
                    window.location.reload();
                }
            });
        }
    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0);">个人绩效</a></li>
            <li class="end"><a href="javascript:void(0);">夏令营活动</a></li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)">夏令营活动</a>
            </li>
            <div class="btn btn-new popup_show">新建</div>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <%--<div class="title">--%>
                        <%--<div id="title">夏令营活动</div>--%>
                        <%--<div class="btn btn-new popup_show">新建</div>--%>
                    <%--</div>--%>
<form:form name="queryForm" action="${pageContext.request.contextPath}/performance/listPerformance?currpage=1&type=5" method="post" modelAttribute="individualPerformance">
                    <div class="tool-box">
                            <ul>
                                <li>学期:
                                    <form:select path="yearCode" >
                                        <option value="">请选择</option>
                                        <c:forEach items="${schoolYear}" var="s">
                                            <form:option value="${s.yearCode}">${s.yearCode}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </li>
                                <li>
                                    <input type="submit" value="查询"/>
                                    <input class="cancel-submit" type="button" value="取消" onclick="cancel()"/>
                                </li>
                            </ul>
                    </div>
</form:form>
                </div>
                <div class="content-box">
                    <table>
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>年度</th>
                            <th>活动项目名称</th>
                            <th>教师</th>
                            <th>选课学生人数</th>
                            <th>职称</th>
                            <th>备注</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
<c:forEach items="${individualPerformanceList}" var="current"  varStatus="i">
                        <tr>
                            <td>${i.count}</td>
                            <td>${current.yearCode}</td>
                            <td>${current.theme}</td>
                            <td>${current.otherTeacher}</td>
                            <td>${current.additionNum}</td>
                            <td>${current.positional}</td>
                            <td>${current.memo}</td>
                            <td>
                                <a href="javascript:edit(${current.id});">编辑</a>
                                <a href="${pageContext.request.contextPath}/performance/delectPerformance?id=${current.id}&type=5" onclick="return confirm('确认要删除吗？')">删除</a>
                            </td>
                        </tr>
</c:forEach>
                        </tbody>
                    </table>
                    <!-- 分页[s] -->
                    <div class="page" >
                        ${totalRecords}条记录 &nbsp; 共${pageModel.totalPage}页 &nbsp;
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/performance/listPerformance?currpage=1&type=5')"
                           target="_self">首页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/performance/listPerformance?currpage=${pageModel.previousPage}&type=5')"
                           target="_self">上一页</a>
                        <input type="hidden" value="${pageModel.lastPage}" id="totalpage"/>&nbsp; <input
                            type="hidden" value="${currpage}" id="currpage"/>
                        &nbsp;第
                        <select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                            <option value="${pageContext.request.contextPath}/performance/listPerformance?currpage=${currpage}&type=5">${currpage}</option>
                            <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1"
                                       varStatus="j" var="current">
                                <c:if test="${j.index!=currpage}">
                                    <option value="${pageContext.request.contextPath}/performance/listPerformance?currpage=${j.index}&type=5">${j.index}</option>
                                </c:if>
                            </c:forEach>
                        </select>页&nbsp;

                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/performance/listPerformance?currpage=${pageModel.nextPage}&type=5')"
                           target="_self">下一页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/performance/listPerformance?currpage=${pageModel.lastPage}&type=5')"
                           target="_self">末页</a>
                    </div>
                    <!-- 分页[e] -->
                </div>
            </div>
        </div>
    </div>
</div>
<!--弹出框start-->
<div class="popup_box">
    <div class="popup_tit">编辑夏令营活动<input class="popup_tit_icon popup_close" type="reset" value="x" title="关闭"/></div>
<form:form  id="queryForm"  method="post" modelAttribute="individualPerformance" >
    <form:hidden path="id"/>
        <table class="tab_lab">
            <tr>
                <th>活动项目名称</th>
                <td colspan="3">
                    <form:input path="theme" type="text" placeholder="请输入"/>
                </td>
                <th>年度</th>
                <td>
                    <form:select  path="yearCode" class="chzn-select">
                        <form:options  items="${schoolYear}" itemLabel="yearCode" itemValue="yearCode" />
                    </form:select>
                </td>
            </tr>
            <tr>
                <th>教师</th>
                <td>
                    <form:select  path="otherTeacher" class="chzn-select">
                        <form:option value="">请选择</form:option>
                        <c:forEach items="${teacher}" var="curr">
                            <form:option value="${curr.cname}/${curr.username}">${curr.cname}/${curr.username}</form:option>
                        </c:forEach>
                    </form:select>
                </td>
                <th>选课学生人数</th>
                <td>
                    <form:input path="additionNum" type="number" placeholder="请输入"/>
                </td>
                <th>职称</th>
                <td>
                    <form:select path="positional" class="chzn-select">
                        <form:options items="${listSpecialtyDuty}" itemLabel="CName" itemValue="CName"/>
                    </form:select>
                </td>
            </tr>
            <tr>
                <th>备注</th>
                <td colspan="5">
                    <form:textarea path="memo" rows="3" placeholder="请输入"></form:textarea>
                </td>
            </tr>
        </table>
        <div class="popup_btns">
            <input type="submit" onclick="save()" class="btn" value="确定"/>
            <input class="btn popup_close" type="reset" value="取消"/>
        </div>
</form:form>
</div>
<!--弹出框end-->
<!--编辑 -->
<div id="edit" class="easyui-window" title="编辑" modal="true" dosize="true" maximizable="true" collapsible="true" minimizable="false" closed="true" iconcls="icon-add" style="width: 1000px; height:800px;">
</div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select'           : {search_contains:true},
        '.chzn-select-deselect'  : {allow_single_deselect:true},
        '.chzn-select-no-single' : {disable_search_threshold:10},
        '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
        '.chzn-select-width'     : {width:"100%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/js/popup.js" type="text/javascript"></script>
</body>
</html>
