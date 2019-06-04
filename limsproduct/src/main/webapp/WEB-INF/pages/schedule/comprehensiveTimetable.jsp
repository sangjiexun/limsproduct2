<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/timetable/Timetables.min.js" type="text/javascript"></script>
    <!-- 打印开始 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <!-- 打印结束 -->
    <script type="text/javascript">

        // 切换周次查询
        function changeWeek() {
            document.queryForm.submit();
        }
    </script>
    <style>
        #coursesTable {
            padding: 15px 0;
        }

        .Courses-head {
            background-color: #edffff;
        }

        .Courses-head > div {
            text-align: center;
            font-size: 14px;
            line-height: 28px;
        }

        .left-hand-TextDom, .Courses-head {
            background-color: #f2f6f7;
        }

        .Courses-leftHand {
            background-color: #f2f6f7;
            font-size: 12px;
        }

        .Courses-leftHand .left-hand-index {
            color: #9c9c9c;
            margin-bottom: 4px !important;
        }

        .Courses-leftHand .left-hand-name {
            color: #666;
        }

        .Courses-leftHand p {
            text-align: center;
            font-weight: 900;
        }

        .Courses-head > div {
            border-left: none !important;
        }

        .Courses-leftHand > div {
            padding-top: 5px;
            border-bottom: 1px dashed rgb(219, 219, 219);
        }

        .Courses-leftHand > div:last-child {
            border-bottom: none !important;
        }

        .left-hand-TextDom, .Courses-head {
            border-bottom: 1px solid rgba(0, 0, 0, 0.1) !important;
        }

        .Courses-content > ul {
            border-bottom: 1px dashed rgb(219, 219, 219);
            box-sizing: border-box;
        }

        .Courses-content > ul:last-child {
            border-bottom: none !important;
        }

        .highlight-week {
            color: #02a9f5 !important;
        }

        .Courses-content li {
            text-align: center;
            color: #666666;
            font-size: 14px;
            line-height: 50px;
        }

        .Courses-content li span {
            padding: 6px 2px;
            box-sizing: border-box;
            line-height: 18px;
            border-radius: 4px;
            white-space: normal;
            word-break: break-all;
            cursor: pointer;
        }

        .grid-active {
            z-index: 9999;
        }

        .grid-active span {
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
        }
        .course-hasContent div p {
            color: #fff;
        }
    </style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.my.workspace" /></a></li>
            <li class="end"><a href="javascript:void(0)">综合课表</a></li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box">
                        <form name="queryForm" action="${pageContext.request.contextPath}/schedule/comprehensiveTimetable" method="post">
                            <ul>
                                <input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}"/>
                                <li>周次:
                                    <select id="week" name="week" class="chzn-select">
                                        <option value="">请选择周次</option>
                                        <c:forEach items="${schoolWeeks}" var="weeks">
                                            <c:if test="${weeks.week == week}">
                                                <option value="${weeks.week}" selected>第${weeks.week}周</option>
                                            </c:if>
                                            <c:if test="${weeks.week != week}">
                                                <option value="${weeks.week}">第${weeks.week}周</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li>实验室:
                                    <select id="labRoom" name="labRoom" class="chzn-select">
                                        <%--<option value="">请选择实验室</option>--%>
                                        <c:forEach items="${labRoomList}" var="lab">
                                            <c:if test="${lab.id == labRoom}">
                                                <option value="${lab.id}" selected>${lab.labRoomName}</option>
                                            </c:if>
                                            <c:if test="${lab.id != labRoom}">
                                                <option value="${lab.id}">${lab.labRoomName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                                <%--<li>--%>
                                    <%--<input class="btn btn-new" id="myPrint" value="打印课表" type="button"/>--%>
                                <%--</li>--%>
                                <li>
                                    <input class="btn btn-new" id="" value="查询" onclick="changeWeek()" type="button"/>
                                </li>
                            </ul>
                        </form>
                    </div>
                    <div id="coursesTable"></div>
                    <%--<table class="tab_border tab_timetable" id="my_show">--%>
                        <%--<thead>--%>
                        <%--<tr>--%>
                            <%--<th colspan="8">${term} ${user.cname}教师课表</th>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th></th>--%>
                            <%--<th>星期一</th>--%>
                            <%--<th>星期二</th>--%>
                            <%--<th>星期三</th>--%>
                            <%--<th>星期四</th>--%>
                            <%--<th>星期五</th>--%>
                            <%--<th>星期六</th>--%>
                            <%--<th>星期日</th>--%>
                        <%--</tr>--%>
                        <%--</thead>--%>
                        <%--<tbody>--%>
                        <%--<tr>--%>
                            <%--<th>第一节</th>--%>
                            <%--<td id="table_1_1"></td>--%>
                            <%--<td id="table_2_1"></td>--%>
                            <%--<td id="table_3_1"></td>--%>
                            <%--<td id="table_4_1"></td>--%>
                            <%--<td id="table_5_1"></td>--%>
                            <%--<td id="table_6_1"></td>--%>
                            <%--<td id="table_7_1"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第二节</th>--%>
                            <%--<td id="table_1_2"></td>--%>
                            <%--<td id="table_2_2"></td>--%>
                            <%--<td id="table_3_2"></td>--%>
                            <%--<td id="table_4_2"></td>--%>
                            <%--<td id="table_5_2"></td>--%>
                            <%--<td id="table_6_2"></td>--%>
                            <%--<td id="table_7_2"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第三节</th>--%>
                            <%--<td id="table_1_3"></td>--%>
                            <%--<td id="table_2_3"></td>--%>
                            <%--<td id="table_3_3"></td>--%>
                            <%--<td id="table_4_3"></td>--%>
                            <%--<td id="table_5_3"></td>--%>
                            <%--<td id="table_6_3"></td>--%>
                            <%--<td id="table_7_3"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第四节</th>--%>
                            <%--<td id="table_1_4"></td>--%>
                            <%--<td id="table_2_4"></td>--%>
                            <%--<td id="table_3_4"></td>--%>
                            <%--<td id="table_4_4"></td>--%>
                            <%--<td id="table_5_4"></td>--%>
                            <%--<td id="table_6_4"></td>--%>
                            <%--<td id="table_7_4"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第五节</th>--%>
                            <%--<td id="table_1_5"></td>--%>
                            <%--<td id="table_2_5"></td>--%>
                            <%--<td id="table_3_5"></td>--%>
                            <%--<td id="table_4_5"></td>--%>
                            <%--<td id="table_5_5"></td>--%>
                            <%--<td id="table_6_5"></td>--%>
                            <%--<td id="table_7_5"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第六节</th>--%>
                            <%--<td id="table_1_6"></td>--%>
                            <%--<td id="table_2_6"></td>--%>
                            <%--<td id="table_3_6"></td>--%>
                            <%--<td id="table_4_6"></td>--%>
                            <%--<td id="table_5_6"></td>--%>
                            <%--<td id="table_6_6"></td>--%>
                            <%--<td id="table_7_6"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第七节</th>--%>
                            <%--<td id="table_1_7"></td>--%>
                            <%--<td id="table_2_7"></td>--%>
                            <%--<td id="table_3_7"></td>--%>
                            <%--<td id="table_4_7"></td>--%>
                            <%--<td id="table_5_7"></td>--%>
                            <%--<td id="table_6_7"></td>--%>
                            <%--<td id="table_7_7"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第八节</th>--%>
                            <%--<td id="table_1_8"></td>--%>
                            <%--<td id="table_2_8"></td>--%>
                            <%--<td id="table_3_8"></td>--%>
                            <%--<td id="table_4_8"></td>--%>
                            <%--<td id="table_5_8"></td>--%>
                            <%--<td id="table_6_8"></td>--%>
                            <%--<td id="table_7_8"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第九节</th>--%>
                            <%--<td id="table_1_9"></td>--%>
                            <%--<td id="table_2_9"></td>--%>
                            <%--<td id="table_3_9"></td>--%>
                            <%--<td id="table_4_9"></td>--%>
                            <%--<td id="table_5_9"></td>--%>
                            <%--<td id="table_6_9"></td>--%>
                            <%--<td id="table_7_9"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第十节</th>--%>
                            <%--<td id="table_1_10"></td>--%>
                            <%--<td id="table_2_10"></td>--%>
                            <%--<td id="table_3_10"></td>--%>
                            <%--<td id="table_4_10"></td>--%>
                            <%--<td id="table_5_10"></td>--%>
                            <%--<td id="table_6_10"></td>--%>
                            <%--<td id="table_7_10"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第十一节</th>--%>
                            <%--<td id="table_1_11"></td>--%>
                            <%--<td id="table_2_11"></td>--%>
                            <%--<td id="table_3_11"></td>--%>
                            <%--<td id="table_4_11"></td>--%>
                            <%--<td id="table_5_11"></td>--%>
                            <%--<td id="table_6_11"></td>--%>
                            <%--<td id="table_7_11"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第十二节</th>--%>
                            <%--<td id="table_1_12"></td>--%>
                            <%--<td id="table_2_12"></td>--%>
                            <%--<td id="table_3_12"></td>--%>
                            <%--<td id="table_4_12"></td>--%>
                            <%--<td id="table_5_12"></td>--%>
                            <%--<td id="table_6_12"></td>--%>
                            <%--<td id="table_7_12"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第十三节</th>--%>
                            <%--<td id="table_1_13"></td>--%>
                            <%--<td id="table_2_13"></td>--%>
                            <%--<td id="table_3_13"></td>--%>
                            <%--<td id="table_4_13"></td>--%>
                            <%--<td id="table_5_13"></td>--%>
                            <%--<td id="table_6_13"></td>--%>
                            <%--<td id="table_7_13"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>第十四节</th>--%>
                            <%--<td id="table_1_14"></td>--%>
                            <%--<td id="table_2_14"></td>--%>
                            <%--<td id="table_3_14"></td>--%>
                            <%--<td id="table_4_14"></td>--%>
                            <%--<td id="table_5_14"></td>--%>
                            <%--<td id="table_6_14"></td>--%>
                            <%--<td id="table_7_14"></td>--%>
                        <%--</tr>--%>
                        <%--</tbody>--%>
                    <%--</table>--%>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
//    $("#myPrint").click(
//        function(){
//            $("#my_show").jqprint();
//        }
//    );
var courseList = [
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
];
var weekTime = window.innerWidth > 360 ? ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] :
    ['一', '二', '三', '四', '五', '六', '日'];
var day = new Date().getDay();
var courseType = [
//    [{index: '1', name: '8:30'}, 1],
    [{index: '1', name: ''}, 1],
    [{index: '2', name: ''}, 1],
    [{index: '3', name: ''}, 1],
    [{index: '4', name: ''}, 1],
    [{index: '5', name: ''}, 1],
    [{index: '6', name: ''}, 1],
    [{index: '7', name: ''}, 1],
    [{index: '8', name: ''}, 1],
    [{index: '9', name: ''}, 1],
    [{index: '10', name: ''}, 1],
    [{index: '11', name: ''}, 1],
    [{index: '12', name: ''}, 1]
];
// 实例化(初始化课表)
var Timetable = new Timetables({
    el: '#coursesTable',
    timetables: courseList,
    week: weekTime,
    timetableType: courseType,
    highlightWeek: day,
    gridOnClick: function (e) {
//        alert(e.name + '  ' + e.week + ', 第' + e.index + '节课, 课长' + e.length + '节');
        console.log(e);
    },
    styles: {
        Gheight: 50
    }
});

$(document).ready(function(){
    var week = $("#week").val();
    var labRoomId = $("#labRoom").val();
    var zuulServerUrl = $("#zuulServerUrl").val();
    $.ajax({
        type:"GET",
        url:zuulServerUrl+"/timetable-service/api/schedule/timetable?labRoomId="+ labRoomId + "&week=" + week,
//                data:{'week': week},
        success:function (data) {
            courseList = [
                ['','','','','','','','','','','',''],
                ['','','','','','','','','','','',''],
                ['','','','','','','','','','','',''],
                ['','','','','','','','','','','',''],
                ['','','','','','','','','','','',''],
                ['','','','','','','','','','','',''],
                ['','','','','','','','','','','',''],
            ];
            console.log(courseList);
            // 遍历课程表
            for(var i=0; i<data.length; i++) {
                var arr = [];
                while(data[i].startClass <= data[i].endClass){
                    arr.push(data[i].startClass++);
                }
                console.log(arr)
                for(var j = 0; j<arr.length;j++){
                    var str = "<div>";
                        str+= "<p>"+ data[i].courseName+"@"+data[i].courseNo +"</p>";
                        if(data[i].itemName!=null){
                            str+= "<p>"+ data[i].itemName+"</p>";
                        }
                        if(data[i].labRoom!=null){
                            str+= "<p>"+ data[i].labRoom+"</p>";
                        }
                        if(data[i].teacher!=null){
                            str+= "<p>"+ data[i].teacher+"</p>";
                        }
                        if(data[i].tutor!=null){
                            str+= "<p>"+ data[i].tutor+"</p>";
                        }
                        if(data[i].resources!=null){
                            str+= "<p>"+ data[i].resources+"</p>";
                        }
                        str+= "</div>";
//                    courseList[data[i].weekday-1][arr[j]-1] = data[i].courseName+"@"+data[i].courseNo+","+data[i].itemName+","+data[i].labRoom;
                    courseList[data[i].weekday-1][arr[j]-1] = str;
//                    courseList[data[i].weekday-1][data[i].endClass-1] = data[i].courseName;
                }
//                $("#table_"+data[i].weekday+"_"+data[i].section).html(data[i].course+"<br>"+data[i].item+"<br>"+data[i].address+data[i].labRoomNumber+"<br>"+data[i].weeks);
            }
            Timetable.setOption({
                timetables: courseList,
            });
        }
    });
});
//var courseList = [
//    ['大学英语(Ⅳ)@10203', '大学英语(Ⅳ)@10203', '', '', '', '', '毛概@14208', '毛概@14208', '', '', '', '选修'],
//    ['', '', '信号与系统@11302', '信号与系统@11302', '模拟电子技术基础@16204', '模拟电子技术基础@16204', '', '', '', '', '', ''],
//    ['大学体育(Ⅳ)', '大学体育(Ⅳ)', '形势与政策(Ⅳ)@15208', '形势与政策(Ⅳ)@15208', '', '', '电路、信号与系统实验', '电路、信号与系统实验', '', '', '', ''],
//    ['', '', '', '', '电装实习@11301', '电装实习@11301', '', '', '', '大学体育', '大学体育', ''],
//    ['', '', '数据结构与算法分析', '数据结构与算法分析', '', '', '', '', '信号与系统', '信号与系统', '', ''],
//];


//切换课表
function onChange() {
    var courseListOther = [
        ['', '', '', '', '毛概@14208', '毛概@14208', '', '', '', '选修', '', ''],
        ['大学英语(Ⅳ)@10203', '大学英语(Ⅳ)@10203', '', '', '模拟电子技术基础@16204', '模拟电子技术基础@16204', '', '', '', '', '', ''],
        ['', '', '信号与系统@11302', '信号与系统@11302', '', '', '电路、信号与系统实验', '电路、信号与系统实验', '', '', '', ''],
        ['形势与政策(Ⅳ)@15208', '形势与政策(Ⅳ)@15208', '', '', '电装实习@11301', '电装实习@11301', '', '', '', '大学体育', '大学体育', ''],
        ['大学体育(Ⅳ)', '大学体育(Ⅳ)', '', '', '数据结构与算法分析', '数据结构与算法分析', '', '', '信号与系统', '信号与系统', '', ''],
    ];

    Timetable.setOption({
        timetables: courseListOther,
        week: ['一', '二', '三', '四', '五', '六', '日'],
        styles: {
            palette: ['#dedcda', '#ff4081']
        },
        timetableType: courseType,
        gridOnClick: function (e) {
            console.log(e);
        }
    });
};
</script>
<%--综合课表悬停效果--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/timetableEffect.js"></script>
</body>
</html>
