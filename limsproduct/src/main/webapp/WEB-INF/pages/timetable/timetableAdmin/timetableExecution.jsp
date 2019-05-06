<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<head>
    <meta name="decorator" content="iframe"/>
    <%--<link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/css/style.css">--%>
    <link type="text/css" rel="stylesheet"	href="${pageContext.request.contextPath}/css/iconFont.css">
    <link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript">
        //跳转
        function targetUrl(url)
        {
            document.form.action=url;
            document.form.submit();
        }
    </script>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <style type="text/css">
        /*.chzn-container{width: 95% !important}*/
        .content-box table tr{height:55px;}
        .content-box table thead tr{height:auto !important}
    </style>

</head>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.my.workspace" /></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.training.record" /></a></li>
        </ul>
    </div>
</div>
<div id="TabbedPanels1" class="TabbedPanels">

</div>
<div class="iStyle_RightInner">

    <div class="right-content">
        <div id="TabbedPanels1" class="TabbedPanels">
            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">
                    <div class="content-box">
                    <div class="tool-box">
                        <form:form name="form" method="Post" action="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=${status}&isposted=${isPosted}" modelAttribute="timetableAppointment">
                            <ul>
                                <li>课程/教师/地点/备注:<form:input  path="confirmRemark" id="confirmRemark" /></li>
                                <li>学期:
                                    <select  name="term" class="chzn-select" id="term" >
                                        <option value="">--请选择--</option>
                                        <c:forEach items="${schoolTerms}" var="t">
                                            <c:if test="${t.id eq term}">
                                                <option value="${t.id}" selected="selected" >${t.termName }</option>
                                            </c:if>
                                            <c:if test="${t.id ne term}">
                                                <option value="${t.id}" >${t.termName }</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li>
                                    <input type="submit" value="查询" id="search" />
                                    <a href="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&status=${status }&id=-1&isposted=${isPosted}"><input class="cancel-submit" type="button" value="取消"></a>
                                </li>
                            </ul>

                        </form:form>
                    </div>
                    </div>
                    <div class="">
                    <div class="content-box">
                        <%--<div class="title">--%>
                            <%--<div id="title">排课管理列表</div>--%>
                        <%--</div>--%>
                        <table>
                            <thead>
                            <tr>
                                <th width="30px;">序号</th>
                                <th width="70px;">班级</th>
                                <th width="70px;">课程名称</th>
                                <th width="130px;">实验项目名称</th>
                                <th width="30px;">星期</th>
                                <th width="45px;">节次</th>
                                <th width="45px;">周次</th>
                                <th>上课教师</th>
                                <th>上课地点</th>
                                <th width="10%;">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${labroomTimetableRegisters}" var="v"  varStatus="i">
                                <tr>
                                    <td>${i.count+(page-1)*pageSize}</td>
                                    <td>${v[13]}</td>
                                    <td>${v[2]}</td>
                                    <td>${v[3]}</td>
                                    <td>${v[4]}</td>
                                    <td>${v[5]}-${v[6]}</td>
                                    <td>${v[7]}</td>
                                    <td>${v[8]}</td>
                                    <td>${v[9]}</td>
                                    <td>
                                        <sec:authorize  ifAnyGranted="ROLE_SUPERADMIN,ROLE_TEACHER,ROLE_LABMANAGER">
                                            <c:if test="${empty v[11]}">
                                                <input id="remark${i.count}" type="text"  placeholder="确认备注"/>
                                                <a onclick="confirmAppointment(${i.count},${v[0]},${v[10]})">上课确认</a>
                                            </c:if>
                                            <c:if test="${not empty v[11]}">
                                                确认人：${v[11]}<br>
                                                备注：${v[12]}
                                            </c:if>
                                        </sec:authorize>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="page" >
                            共${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                            <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=${status}&isposted=${isPosted}')" target="_self">首页</a>
                            <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=${pageModel.previousPage}&id=-1&status=${status}&isposted=${isPosted}')" target="_self">上一页</a>
                            第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                            <option value="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=${page}&id=-1&status=${status}&isposted=${isPosted}">${page}</option>
                            <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                                <c:if test="${j.index!=page}">
                                    <option value="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=${j.index}&id=-1&status=${status}&isposted=${isPosted}">${j.index}</option>
                                </c:if>
                            </c:forEach></select>页
                            <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=${pageModel.nextPage}&id=-1&status=${status}&isposted=${isPosted}')" target="_self">下一页</a>
                            <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=${pageModel.lastPage}&id=-1&status=${status}&isposted=${isPosted}')" target="_self">末页</a>
                        </div>


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
            '.chzn-select' : {
                search_contains : true
            },
            '.chzn-select-deselect' : {
                allow_single_deselect : true
            },
            '.chzn-select-no-single' : {
                disable_search_threshold : 10
            },
            '.chzn-select-no-results' : {
                no_results_text : 'Oops, nothing found!'
            },
            '.chzn-select-width' : {
                width : "95%"
            }
        }
        for ( var selector in config) {
            $(selector).chosen(config[selector]);
        }
        //上课确认
        function confirmAppointment(i,id,flag) {
            var remark = $("#remark"+i).val();
            window.location.href="${pageContext.request.contextPath}/timetable/confirmAppointment?id="+id+"&currpage=${page}&flag="+flag+"&remark="+remark;
        }
    </script>
    <!-- 下拉框的js -->
