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
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
    <!-- 下拉的样式结束 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
    <script type="text/javascript">
        //登陆虚拟桌面之前的检查：查询还有没有可用的桌面可以分配给学生
        function virtualCheckAndLogin(course_id, virtual_image_id, start_time, end_time) {
            $.ajax({
                url:"${pageContext.request.contextPath}/virtual/virtualCheckAndLogin",
                type:"POST",
                data:{
                    username: ${user.username},
                    course_id: course_id,
                    virtual_image_id:virtual_image_id,
                    start_time:start_time,
                    end_time:end_time
                },
                success:function (virtualImageReservationID) {
                    if(virtualImageReservationID == -2)
                        alert("该课程的虚拟桌面用尽");
                    else
                        window.location.href="${pageContext.request.contextPath}/virtual/virtualLogin?virtualImageReservationid=" + virtualImageReservationID;
                }
            });
        }
        //手动启动今天的虚拟镜像
        var started = false;
        function startVirtualImage() {
            if(started == false) {
                $.ajax({
                    url:"${pageContext.request.contextPath}/virtual/StartVirtualImageByCourseSchedules",
                    type:"GET",
                    success:function (result) {
                        if(result == 'success')
                            alert("启动成功，请勿重复点击");
                        else
                            alert('启动失败');
                    }
                });
                started = true;
            }
            else
                alert("警告：不要重复启动");
        }
    function cancel() {
            window.location.href="${pageContext.request.contextPath}/StudentCurrWeekTimetable"
        }
    </script>
    <style type="text/css">
        td, th, table{
            border: 1px solid gray !important
        }
        .tool-box .chzn-container{
            /*width: 200px!important;*/
        }
    </style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li>
                <a href="javascript:void(0)">
                    课程列表
                </a>
            </li>
            <li class="end">
                <a href="javascript:void(0)">学生周课表</a>
            </li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)">本周课表【第${schoolWeek}周】</a>
            </li>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <%--<div class="title">--%>
                        <%--<div id="title">本周课表【第${schoolWeek}周】</div>--%>
                    <%--</div>--%>
                    <div class="tool-box">
                        <form:form name="queryForm" action="${pageContext.request.contextPath}/StudentCurrWeekTimetable" method="post" >
                            <ul>
                                <li>周次:
                                    <select name="weeks" class="chzn-select">
                                        <option value="">请选择</option>
                                        <c:forEach begin="1" end="20" var="w">
                                            <c:if test="${schoolWeek eq w}">
                                                <option value="${w}" selected="selected">${weeks[w]}</option>
                                            </c:if>
                                            <c:if test="${schoolWeek ne w}">
                                                <option value="${w}" >${weeks[w]}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li>
                                    <input type="submit" value="查询"/>
                                    <input class="cancel-submit" type="button" value="取消查询" onclick="cancel();"/>
                                </li>
                            </ul>
                        </form:form>
                    </div>
                    <table class="tb" id="my_show">
                        <table class="dm_right tab_border tab_timetable" cellspacing="1">
                            <thead>
                            <tr>
                                <th></th>
                                <th id="weekdayName1">星期一<br><c:set var="schoolweekdate" value="${termId }-${schoolWeek }-1"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                                <th id="weekdayName2">星期二<br><c:set var="schoolweekdate" value="${termId }-${schoolWeek }-2"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                                <th id="weekdayName3">星期三<br><c:set var="schoolweekdate" value="${termId }-${schoolWeek }-3"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                                <th id="weekdayName4">星期四<br><c:set var="schoolweekdate" value="${termId }-${schoolWeek }-4"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                                <th id="weekdayName5">星期五<br><c:set var="schoolweekdate" value="${termId }-${schoolWeek }-5"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                                <th id="weekdayName6">星期六<br><c:set var="schoolweekdate" value="${termId }-${schoolWeek }-6"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                                <th id="weekdayName0">星期日<br><c:set var="schoolweekdate" value="${termId }-${schoolWeek }-7"></c:set><fmt:formatDate value='${schoolweeek[schoolweekdate].time}' pattern="yyyy-MM-dd"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="class" varStatus="cStatus" begin="1" end="13">
                            <!-- 判断奇数偶数，以确定日历背景样式 -->
                            <c:if test="${cStatus.count%2!=0}">
                            <tr>
                                </c:if>
                                <c:if test="${cStatus.count%2==0}">
                            <tr>
                                </c:if>

                                <c:if test="${cStatus.index==1}">
                                    <th><span id="className12-13">第一节<font><fmt:formatDate value='${systemtime["1"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["1"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                </c:if>
                                    <c:if test="${cStatus.index==2}">
                                        <th><span id="className12-13">第二节<font><fmt:formatDate value='${systemtime["2"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["2"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                    </c:if>
                                <c:if test="${cStatus.index==3}">
                                    <th><span id="className12-13">第三节<font><fmt:formatDate value='${systemtime["3"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["3"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                </c:if>
                                <c:if test="${cStatus.index==4}">
                                    <th><span id="className12-13">第四节<font><fmt:formatDate value='${systemtime["4"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["4"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                </c:if>
                                <c:if test="${cStatus.index==5}">
                                    <th><span id="className12-13">第五节<font><fmt:formatDate value='${systemtime["5"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["5"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                </c:if>
                                <c:if test="${cStatus.index==6}">
                                    <th><span id="className12-13">第六节<font><fmt:formatDate value='${systemtime["6"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["6"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                </c:if>
                                <c:if test="${cStatus.index==7}">
                                    <th><span id="className12-13">第七节<font><fmt:formatDate value='${systemtime["7"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["7"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                </c:if>
                                <c:if test="${cStatus.index==8}">
                                    <th><span id="className12-13">第八节<font><fmt:formatDate value='${systemtime["8"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["8"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                </c:if>
                                <c:if test="${cStatus.index==9}">
                                    <th><span id="className12-13">第九节<font><fmt:formatDate value='${systemtime["9"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["9"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                </c:if>
                                <c:if test="${cStatus.index==10}">
                                    <th><span id="className12-13">第十节<font><fmt:formatDate value='${systemtime["10"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["10"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                </c:if>
                                <c:if test="${cStatus.index==11}">
                                    <th><span id="className12-13">第十一节<font><fmt:formatDate value='${systemtime["11"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["11"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                </c:if>
                                <c:if test="${cStatus.index==12}">
                                    <th><span id="className12-13">第十二节<font><fmt:formatDate value='${systemtime["12"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["12"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                </c:if>
                                <c:if test="${cStatus.index==13}">
                                    <th><span id="className12-13">第十三节<font><fmt:formatDate value='${systemtime["13"].startDate.time}' pattern="HH:mm:ss"/>-<fmt:formatDate value='${systemtime["13"].endDate.time}' pattern="HH:mm:ss"/></font></span></th>
                                 </c:if>
                                <c:forEach begin="1" end="7"  varStatus="iWeek">
                                    <c:set var="sameClass" value="1"></c:set>
                                    <td class="tb" valign="top" do-labReservation='${iWeek.count},${cStatus.index}'>
                                        <c:forEach  items="${timetableAppointment}" var="current">
                                            <c:if test="${current[2]==iWeek.count}">
                                            <c:if test="${current[4]<=cStatus.index&&current[5]>=cStatus.index}">
                                                <c:if test="${sameClass ne 1}">
                                                    <hr/>
                                                </c:if>
                                         课程：${current[0]}<br>
                                         实验室：${current[6]}<br>
                                         教师：${current[7]}<br>
                                         班级：${current[1]}<br>
                                         节次：<c:if test="${current[4]==current[5]}">
                                                  ${current[4]}<br>
                                                </c:if>
                                               <c:if test="${current[4]!=current[5]}">
                                                ${current[4]}-${current[5]}<br>
                                               </c:if>
                                         周次：${current[3]}<br>
                                                <c:set var="sameClass" value="${sameClass + 1}"></c:set>
                                                <c:forEach items="${courseSchedules}" var="schedule">
                                                    <c:if test="${current[8] eq schedule[0] && current[9] >= schedule[2] && current[10] <= schedule[3]}">
                                                        <button onclick="virtualCheckAndLogin('${schedule[0]}', '${schedule[1]}', '${schedule[2]}', '${schedule[3]}')">登陆虚拟镜像</button>
                                                    </c:if>
                                                </c:forEach>
                                        </c:if>
                                        </c:if>
                                        </c:forEach>
                                    </td>
                                </c:forEach>
                            </tr>
                            </c:forEach>
                        </table>
                    <%--                    <div class="page" >
                                            ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                                            <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=1')" target="_self">首页</a>
                                            <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
                                            第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                                            <option value="${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                                            <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                                                <c:if test="${j.index!=pageModel.currpage}">
                                                    <option value="${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${j.index}">${j.index}</option>
                                                </c:if>
                                            </c:forEach></select>页
                                            <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
                                            <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labCenter/listLabCenter?currpage=${pageModel.lastPage}')" target="_self">末页</a>
                                        </div>--%>
                </div>
            </div>
        </div>
    </div>
</div>
<%--综合课表悬停效果--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/timetableEffect.js"></script>
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
</body>
</html>




