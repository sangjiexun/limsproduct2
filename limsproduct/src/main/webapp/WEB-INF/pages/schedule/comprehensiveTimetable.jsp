<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
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
        .left-hand-TextDom {
            height: 57px!important;
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
        .highlight-week span{
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
        .course-hasContent div {
            /*position: absolute;*/
            /*top: 40%;*/
            /*left: 32%;*/
            /*height: 30%;*/
            /*!* width: 50%; *!*/
            /*margin: -15% 0 0 -25%;*/
        }
        .course-hasContent div p {
            color: #fff;
        }
        .course-hasContent div p a{
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
                                <li>课程:
                                    <input id="courseName" name="courseName" value="${courseName}" type="text"/>
                                </li>
                                <li>实验项目:
                                    <input id="itemName" name="itemName" value="${itemName}" type="text"/>
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
var contextPath = $("meta[name='contextPath']").attr("content");
$(document).ready(function(){
var courseList = [
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
    ['','','','','','','','','','','',''],
];
var weekday = new Array();
<c:forEach items="${weekDayDate}" var="curr" varStatus="i">
weekday.push('${curr}')
</c:forEach>
console.log(weekday);
var weektime = [];
for(var z=0;z<weekday.length;z++){
    if(z == 0){
        weektime.push('周一<br><span>'+ weekday[z] +'</span>')
    }
    if(z == 1){
        weektime.push('周二<br><span>'+ weekday[z] +'</span>')
    }
    if(z == 2){
        weektime.push('周三<br><span>'+ weekday[z] +'</span>')
    }
    if(z == 3){
        weektime.push('周四<br><span>'+ weekday[z] +'</span>')
    }
    if(z == 4){
        weektime.push('周五<br><span>'+ weekday[z] +'</span>')
    }if(z == 5){
        weektime.push('周六<br><span>'+ weekday[z] +'</span>')
    }if(z == 6){
        weektime.push('周日<br><span>'+ weekday[z] +'</span>')
    }

}
//var weekTime = window.innerWidth > 360 ? ['周一<br><span>2019-06-03</span>', '周二<br><span>2019-06-03</span>', '周三<br><span>2019-06-03</span>', '周四<br><span>2019-06-03</span>', '周五<br><span>2019-06-03</span>', '周六<br><span>2019-06-03</span>', '周日<br><span>2019-06-03</span>'] :
var weekTime = window.innerWidth > 360 ? weektime :
    ['一', '二', '三', '四', '五', '六', '日'];
//var dateTime = ['2019-06-03','2019-06-03','2019-06-03','2019-06-03','2019-06-03','2019-06-03','2019-06-03']
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
        Gheight: 60
    }
});
    var week = $("#week").val();
    var itemName = $("#itemName").val();
    var courseName = $("#courseName").val();
    var labRoomId = $("#labRoom").val();
    var zuulServerUrl = $("#zuulServerUrl").val();
    var data1 = JSON.stringify({
        "week": week,
        "itemName": itemName,
        "labRoomId": labRoomId,
        "courseName": courseName,
    });
    $.ajax({
        type:"post",
//        url:zuulServerUrl+"/timetable-service/api/schedule/timetable?labRoomId="+ labRoomId + "&week=" + week+ "&itemName=" + itemName+ "&courseName=" + courseName,
        url:zuulServerUrl+"/timetable-service/api/schedule/timetable",
        data:data1,
        headers: {Authorization: getJWTAuthority()},
        contentType: "application/json;charset=UTF-8",
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
                while( parseInt(data[i].startClass)<= parseInt(data[i].endClass)){
                    arr.push(data[i].startClass++);
                }
                console.log(arr)
                for(var j = 0; j<arr.length;j++){
                    var str = "<div>";
                    str+= "<p>"+ data[i].courseName+"@"+data[i].courseNo +"</p>";
                    if(data[i].items!=0){
                        str+= "<p>实验项目："
                        for(var y =0;y<data[i].items.length;y++){
                            str+="<a href='javascript:void(0);' onclick='listItemMaterialRecordRest("+ data[i].items[y].itemId +")' data='"+ data[i].items[y].itemId +"'>"+data[i].items[y].itemName+",</a>"
                        }
                        str+="</p>"
                    }
                    if(data[i].labRoom!=null){
                        str+= "<p>实验室："+ data[i].labRoom+"</p>";
                    }
                    if(data[i].teacher!=null){
                        str+= "<p>授课教师："+ data[i].teacher+"</p>";
                    }
                    if(data[i].tutor!=null){
                        str+= "<p>助教："+ data[i].tutor+"</p>";
                    }
                    if(data[i].classes!=null){
                        str+= "<p>班级："+ data[i].classes+"</p>";
                    }
                    if(data[i].resources!=null){
//                            str+= "<p>资源链接："+ data[i].resources+"</p>";
                        str+= "<p><a href='http://"+ data[i].resources +"'>资源链接</a></p>";
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
function listItemMaterialRecordRest(id) {
    var index = layer.open({
        type: 2,
        title: '详情',
        maxmin: true,
        shadeClose: true,
        content: "${pageContext.request.contextPath}/openOperationItem/viewOpenOperationItem/1/" + id,
    });
    layer.full(index);
}

function getJWTAuthority() {
    var authorization = "";
    initDirectoryEngine({
        getHostsUrl: contextPath + "/shareApi/getHosts",
        getAuthorizationUrl: contextPath + "/shareApi/getAuthorization"
    });
    getAuthorization({
        async: false,
        success: function (data) {
            authorization = data;
        }
    });
    return authorization;
}


//切换课表
//function onChange() {
//    var courseListOther = [
//        ['', '', '', '', '毛概@14208', '毛概@14208', '', '', '', '选修', '', ''],
//        ['大学英语(Ⅳ)@10203', '大学英语(Ⅳ)@10203', '', '', '模拟电子技术基础@16204', '模拟电子技术基础@16204', '', '', '', '', '', ''],
//        ['', '', '信号与系统@11302', '信号与系统@11302', '', '', '电路、信号与系统实验', '电路、信号与系统实验', '', '', '', ''],
//        ['形势与政策(Ⅳ)@15208', '形势与政策(Ⅳ)@15208', '', '', '电装实习@11301', '电装实习@11301', '', '', '', '大学体育', '大学体育', ''],
//        ['大学体育(Ⅳ)', '大学体育(Ⅳ)', '', '', '数据结构与算法分析', '数据结构与算法分析', '', '', '信号与系统', '信号与系统', '', ''],
//    ];
//
//    Timetable.setOption({
//        timetables: courseListOther,
//        week: ['一', '二', '三', '四', '五', '六', '日'],
//        styles: {
//            palette: ['#dedcda', '#ff4081']
//        },
//        timetableType: courseType,
//        gridOnClick: function (e) {
//            console.log(e);
//        }
//    });
//};
</script>
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
