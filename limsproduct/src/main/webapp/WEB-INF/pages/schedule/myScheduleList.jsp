<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
    <!-- 打印开始 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <!-- 打印结束 -->
    <script type="text/javascript">
        $(document).ready(function(){
            var week = $("#week").val();
            $.ajax({
                type:"GET",
                url:"${pageContext.request.contextPath}/schedule/ajaxScheduleList",
                data:{'week': week},
                success:function (data) {
                    // 遍历课程表
                    for(var i=0; i<data.length; i++) {
                        $("#table_"+data[i].weekday+"_"+data[i].section).html(data[i].course+"<br>"+data[i].item+"<br>"+data[i].address+data[i].labRoomNumber+"<br>"+data[i].weeks);
                    }
                }
            });
        });
        // 切换周次查询
        function changeWeek() {
            document.queryForm.submit();
        }
    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.my.workspace" /></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.class.schedule"/></a></li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box">
                        <form name="queryForm" action="${pageContext.request.contextPath}/schedule/myScheduleList" method="post">
                            <ul>
                                <li><select id="week" name="week" class="chzn-select" onchange="changeWeek()">
                                    <option value="">请选择周次</option>
                                    <c:forEach items="${schoolWeeks}" var="weeks">
                                        <c:if test="${weeks.week == week}">
                                            <option value="${weeks.week}" selected>第${weeks.week}周</option>
                                        </c:if>
                                        <c:if test="${weeks.week != week}">
                                            <option value="${weeks.week}">第${weeks.week}周</option>
                                        </c:if>
                                    </c:forEach>
                                </select></li>
                                <li>
                                    <input class="btn btn-new" id="myPrint" value="打印课表" type="button"/>
                                </li>
                            </ul>
                        </form>
                    </div>

                    <table class="tab_border tab_timetable" id="my_show">
                        <thead>
                        <tr>
                            <th colspan="8">${term} ${user.cname}教师课表</th>
                        </tr>
                        <tr>
                            <th></th>
                            <th>星期一</th>
                            <th>星期二</th>
                            <th>星期三</th>
                            <th>星期四</th>
                            <th>星期五</th>
                            <th>星期六</th>
                            <th>星期日</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th>第一节</th>
                            <td id="table_1_1"></td>
                            <td id="table_2_1"></td>
                            <td id="table_3_1"></td>
                            <td id="table_4_1"></td>
                            <td id="table_5_1"></td>
                            <td id="table_6_1"></td>
                            <td id="table_7_1"></td>
                        </tr>
                        <tr>
                            <th>第二节</th>
                            <td id="table_1_2"></td>
                            <td id="table_2_2"></td>
                            <td id="table_3_2"></td>
                            <td id="table_4_2"></td>
                            <td id="table_5_2"></td>
                            <td id="table_6_2"></td>
                            <td id="table_7_2"></td>
                        </tr>
                        <tr>
                            <th>第三节</th>
                            <td id="table_1_3"></td>
                            <td id="table_2_3"></td>
                            <td id="table_3_3"></td>
                            <td id="table_4_3"></td>
                            <td id="table_5_3"></td>
                            <td id="table_6_3"></td>
                            <td id="table_7_3"></td>
                        </tr>
                        <tr>
                            <th>第四节</th>
                            <td id="table_1_4"></td>
                            <td id="table_2_4"></td>
                            <td id="table_3_4"></td>
                            <td id="table_4_4"></td>
                            <td id="table_5_4"></td>
                            <td id="table_6_4"></td>
                            <td id="table_7_4"></td>
                        </tr>
                        <tr>
                            <th>第五节</th>
                            <td id="table_1_5"></td>
                            <td id="table_2_5"></td>
                            <td id="table_3_5"></td>
                            <td id="table_4_5"></td>
                            <td id="table_5_5"></td>
                            <td id="table_6_5"></td>
                            <td id="table_7_5"></td>
                        </tr>
                        <tr>
                            <th>第六节</th>
                            <td id="table_1_6"></td>
                            <td id="table_2_6"></td>
                            <td id="table_3_6"></td>
                            <td id="table_4_6"></td>
                            <td id="table_5_6"></td>
                            <td id="table_6_6"></td>
                            <td id="table_7_6"></td>
                        </tr>
                        <tr>
                            <th>第七节</th>
                            <td id="table_1_7"></td>
                            <td id="table_2_7"></td>
                            <td id="table_3_7"></td>
                            <td id="table_4_7"></td>
                            <td id="table_5_7"></td>
                            <td id="table_6_7"></td>
                            <td id="table_7_7"></td>
                        </tr>
                        <tr>
                            <th>第八节</th>
                            <td id="table_1_8"></td>
                            <td id="table_2_8"></td>
                            <td id="table_3_8"></td>
                            <td id="table_4_8"></td>
                            <td id="table_5_8"></td>
                            <td id="table_6_8"></td>
                            <td id="table_7_8"></td>
                        </tr>
                        <tr>
                            <th>第九节</th>
                            <td id="table_1_9"></td>
                            <td id="table_2_9"></td>
                            <td id="table_3_9"></td>
                            <td id="table_4_9"></td>
                            <td id="table_5_9"></td>
                            <td id="table_6_9"></td>
                            <td id="table_7_9"></td>
                        </tr>
                        <tr>
                            <th>第十节</th>
                            <td id="table_1_10"></td>
                            <td id="table_2_10"></td>
                            <td id="table_3_10"></td>
                            <td id="table_4_10"></td>
                            <td id="table_5_10"></td>
                            <td id="table_6_10"></td>
                            <td id="table_7_10"></td>
                        </tr>
                        <tr>
                            <th>第十一节</th>
                            <td id="table_1_11"></td>
                            <td id="table_2_11"></td>
                            <td id="table_3_11"></td>
                            <td id="table_4_11"></td>
                            <td id="table_5_11"></td>
                            <td id="table_6_11"></td>
                            <td id="table_7_11"></td>
                        </tr>
                        <tr>
                            <th>第十二节</th>
                            <td id="table_1_12"></td>
                            <td id="table_2_12"></td>
                            <td id="table_3_12"></td>
                            <td id="table_4_12"></td>
                            <td id="table_5_12"></td>
                            <td id="table_6_12"></td>
                            <td id="table_7_12"></td>
                        </tr>
                        <tr>
                            <th>第十三节</th>
                            <td id="table_1_13"></td>
                            <td id="table_2_13"></td>
                            <td id="table_3_13"></td>
                            <td id="table_4_13"></td>
                            <td id="table_5_13"></td>
                            <td id="table_6_13"></td>
                            <td id="table_7_13"></td>
                        </tr>
                        <tr>
                            <th>第十四节</th>
                            <td id="table_1_14"></td>
                            <td id="table_2_14"></td>
                            <td id="table_3_14"></td>
                            <td id="table_4_14"></td>
                            <td id="table_5_14"></td>
                            <td id="table_6_14"></td>
                            <td id="table_7_14"></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#myPrint").click(
        function(){
            $("#my_show").jqprint();
        }
    );
</script>
<%--综合课表悬停效果--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/timetableEffect.js"></script>
</body>
</html>
