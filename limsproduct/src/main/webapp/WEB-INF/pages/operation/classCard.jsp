<%@page language="java" contentType="text/html;charset=utf-8"
		pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
		   uri="http://www.springframework.org/security/tags"%>
<fmt:setBundle basename="bundles.projecttermination-resources" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="decorator" content="iframe"/>
	<%--<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=0.35, maximum-scale=0.35">--%>
	<title>视频发布系统</title>
	<% response.setHeader("refresh","3600"); %>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/videopublish/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/videopublish/common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/videopublish/cload.js"></script>
	<link href="${pageContext.request.contextPath}/css/videopublish/adaption_publish.css" type="text/css" rel="stylesheet">
</head>
<body>
<%--注意此处班牌显示是屏幕全屏大小设置的尺寸，在pc端浏览器观看时请打开无lims框架的单独页面并全屏--%>
<%--漂浮云层不用先隐藏--%>
<%--<div id="demo">
	<div id='indemo'>
		<div  id='demo1'>
			<img src="${pageContext.request.contextPath}/images/videopublish/cloud.png" >
			<img src="${pageContext.request.contextPath}/images/videopublish/cloud.png" >
			<img src="${pageContext.request.contextPath}/images/videopublish/cloud.png" >
		</div>
		<div id='demo2'></div>
	</div>
</div>

<script type="text/javascript">
    var speed = 20; //数字越大速度越慢
    var tab = document.getElementById('demo');
    var tab1 = document.getElementById('demo1');
    var tab2 = document.getElementById('demo2');
    tab2.innerHTML = tab1.innerHTML;

    function Marquee() {
        if(tab2.offsetWidth - tab.scrollLeft <= 0)
            tab.scrollLeft -= tab1.offsetWidth
        else {
            tab.scrollLeft++;
        }
    }
    var MyMar = setInterval(Marquee, speed);
    tab.onmouseover = function() {
        clearInterval(MyMar)
    };
    tab.onmouseout = function() {
        MyMar = setInterval(Marquee, speed)
    };
</script>--%>
<div class="header">
	<img src="${pageContext.request.contextPath}/images/videopublish/logo.png">
	<span id="adian_today" class="time"></span>
</div>
<div id="classlist" >
	<input type="hidden" id="pageContext" value="${pageContext.request.contextPath }">
	<input type="hidden" id="lastPage" value="${pageModel.lastPage }">
	<h2><spring:message code="title.school.name"/>实验教学动态情况一览表</h2>
	<ul id="vi-title">
		<li id="classroom">实验室</li>
		<li id="classnum">课程</li>
		<li id="teacher">授课老师</li>
		<li id="class">班级</li>
		<li id="classa">节次</li>
		<li id="studentB">课程人数</li>
	</ul>
	<ul id="scrolltext" class="notic-text">
		<c:forEach items="${objects}" var="curr">
            <li>
                <div class="is_flipboard">
                    <span id="classrooma">${curr[0]}</span>
                    <span id="classnum">${curr[1]}</span>
                    <span id="teacher">${curr[2]}</span>
                    <span id="class">${curr[3]}</span>
                    <span id="classa">${curr[4]}</span>
                    <span id="studentb">${curr[5]}</span>
                </div>
            </li>
        </c:forEach>
	</ul>
</div>
</div>
<script>
    //<!-- 气球 -->
    var timeout;
    function scroll(){
        $('.oneImage').animate({'top':-20}, 1500);
        $('.oneImage').animate({'top':15}, 1500);
        $('.twoImage').animate({'top':-20}, 2000);
        $('.twoImage').animate({'top':15}, 2000);
        //清除定时器的值
        clearTimeout(timeout);
        //设置定时器的值
        timeout = setTimeout(scroll,10);
    }
    //执行滚动函数
    scroll();
</script>
<script type="text/javascript">
    var labRooms = [];
    var oldPage = 1;
    var pno = 0;
    var currpage = 1;
    var timenum = 0;
    var totalPage = 0;//总页数
    var pageSize = 4;//每页显示行数
    var len=$(".is_flipboard").length;
    if(len/pageSize > parseInt(len/pageSize)){
        totalPage=parseInt(len/pageSize)+1;
    }else{
        totalPage=parseInt(len/pageSize);
    }
    $(document).ready(function(e) {
        setTimeout(function(){
            flipflip();
        },3000);
    });
    function flipflip(){
        var speed=500;//单帧动画速度
        var time=0.6;//动画间隔
        var cont=18;//每轮动画间隔倍数

        var swicher=0;

        var div = document.getElementsByClassName("hide_li");
        time=time*1000;
        for(i=0;i<len;i++){
            setTimeout(function(){
                    swicher++;
                    //console.log(swicher);
                    var k=swicher-1;
                    is_flipboard_animate(k,speed,currpage);
                    setInterval(function(){
                        if(timenum%8 == 0){
                            currpage = currpage+1;
                            oldPage = currpage;
                        }
                        is_flipboard_animate(k,speed,currpage)
                    },(len+cont)*time);
                },
                time*i+time);
        }

    }
    var timernum = 0;
    function is_flipboard_animate(index,speed,currpage){
        var div = document.getElementsByClassName("hide_li");
        //console.log(index);
        $(".is_flipboard:eq("+index+")").animate({
            height:"0px",top:"25px"
        },speed,function(){
            var backboard=$("<div class='backboard' style='position:absolute;top:0px; left:0px; width:100%;height:100%;background-color:#999'></div>");
            $(this).append(backboard);
        }).animate({
            height:"36px",top:"7px"
        },speed/1.1).animate({
            height:"0px",top:"25px"
        },speed,function(){
            $(".is_flipboard:eq("+index+") .backboard").remove();
            currpage = currpage+1;
            console.log("index"+currpage);
            console.log("old"+oldPage);
            if($('#lastPage').val() < currpage){
                currpage = 1
            }
            $.ajax({
                url:"${pageContext.request.contextPath}/public/classCardJson",
                type:"get",
                data:{currpage:currpage,
                    type:${type},
                    id:'${id}'},
                dataType:'text',
                success:function(data){
                    labRooms = JSON.parse(data);
                    $("#scrolltext").children().eq(index).children().children().eq(0).html(labRooms[index].labName);
                    $("#scrolltext").children().eq(index).children().children().eq(1).html(labRooms[index].courseName);
                    $("#scrolltext").children().eq(index).children().children().eq(2).html(labRooms[index].teacher);
                    $("#scrolltext").children().eq(index).children().children().eq(3).html(labRooms[index].classes);
                    $("#scrolltext").children().eq(index).children().children().eq(4).html(labRooms[index].status);
                    $("#scrolltext").children().eq(index).children().children().eq(5).html(labRooms[index].stuNum);
                },error:function(err){
                    console.log(err);
                }
            });
            console.log(labRooms[0]);
        }).animate({
            height:"3.4rem",top:"0px"
        },speed*2.5,function(){

        });
        timenum = timenum+1;
        console.log('循环次数'+timenum);
    }

    function getLabRooms(currpage){
        $.ajax({
            url:"${pageContext.request.contextPath}/public/classCardByAjax",
            type:"get",
            data:{currpage:currpage},
            dataType:'text',
            success:function(data){
                console.log('data');
                labRooms=data;
            },error:function(err){
                console.log(err);
            }
        });
    }
</script>
</body>
</html>
