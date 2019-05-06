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
	<link href="${pageContext.request.contextPath}/css/videopublish/style.css" type="text/css" rel="stylesheet">
	<style>
	    body,
		.dmrrr{
		    background:#333!important;		    
		}
	    .dmrrr{
		    width:640px;
		    height:256px;
		    background:#111!important;
		    padding:0!important;
			overflow:hidden!important;
		}
		#demo{
		    display:none;
		}
		li {
		    box-sizing:border-box;
		    color: rgb(255,0,128);
            border-top: 1px solid rgb(0,128,192);	
		}
		li,
		.is_flipboard,
		#scrolltext li span,
		#vi-title li{
			height: 23px!important;
            min-height: 23px!important;
            line-height: 23px!important;
		}
		.header {
		    display:none;
			height: auto!important;
		}
		.header img {
			margin-top: 11px!important;
			margin-left: 4%!important;
			width: 110px!important;
		}		
		#classlist{
		    margin:0!important;
		}
		#classlist h2 {
			width: auto!important;
            text-indent: 0;
            text-align: center!important;
            color: #f7f7f7!important;
            margin: 7px 0 0px!important;
            overflow: hidden;
		}
		#classlist div{
            font-size: 14px;
			color: rgb(255,255,255);
		}
		#classlist #time {
            display: inline-block;
            float: right;
            font-size: 12px;
            font-weight: normal;
            /*text-indent: -68px;*/
            margin: 0 2px 6px 0;
            white-space: nowrap;
            overflow: hidden;
			color: rgb(255,255,255);
		}		
		#vi-title{
		    width: 100%;
            background: #111;
			border-top:1px solid rgb(0,128,192);
			border-bottom:none;
		}
		#vi-title li{
		    color:#f7f7f7;
		    border-top:none;		
		}
		#vi-title li:nth-child(even) {
            border-left: 1px solid rgb(0,128,192);
            border-right: 1px solid rgb(0,128,192);
        }
		#vi-title, #scrolltext{
		    width:100%!important;
		}
		#classroom,
		#classrooma {
			width: 29%;
		}
		#classnum {
			width: 26%;
		}
		#class,
		#classa,
		#teacher {
			width: 15%;
		}
		.is_flipboard,
		#scrolltext li span,
		#vi-title li{
		    box-sizing:border-box;
			font-size: 11px;
			text-indent: 0px;
			background:#111;
			color: rgb(255,0,128);
		}

		#vi-title li{
			color: #fff;
		}
		#scrolltext li span:nth-child(odd){
            border-left: 1px solid #111;
            border-right: 1px solid #111;		    
		}			
		#scrolltext li span:nth-child(even){
            border-left: 1px solid rgb(0,128,192);
            border-right: 1px solid rgb(0,128,192);		    
		}	
	</style>
</head>
<body>
<div id="demo">
		<div id='indemo'>
			<div  id='demo1'>
				<img src="${pageContext.request.contextPath}/images/videopublish/cloud.png" >
				<img src="${pageContext.request.contextPath}/images/videopublish/cloud.png" >
				<img src="${pageContext.request.contextPath}/images/videopublish/cloud.png" >
			</div>
			<div id='demo2'></div>
		</div>
	</div>
 <div class="header" style="height:140px;">
	<img src="${pageContext.request.contextPath}/images/videopublish/logo.png" style="margin-top:60px;width: 360px;margin-left: 80px;">
 </div>
  <div id="classlist" >
	  <input type="hidden" id="pageContext" value="${pageContext.request.contextPath }">
	  <input type="hidden" id="lastPage" value="${pageModel.lastPage }">
	  <h2>
	  <div>浙江建设职业技术学院实训室实验教学动态情况一览表</div>
	  <div id="time">
		  <script>
              <%--document.getElementById('time').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay())+' 第'+${week}+'周';--%>
              document.getElementById('time').innerHTML=new Date().toLocaleTimeString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay())+' 第'+${week}+'周';
              setInterval("document.getElementById('time').innerHTML=new Date().toLocaleTimeString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay())+' 第'+${week}+'周';",1000);
		  </script>
	  </div>
	  </h2>
			<ul id="vi-title">
				<li id="classroom">实验室</li>
				<li id="classnum">课程</li>
				<li id="teacher">授课老师</li>
				<li id="class">班级</li>
				<li id="classa">节次</li>
			</ul>
			<ul id="scrolltext" class="notic-text" style=";overflow: hidden;">
				<c:forEach items="${objects}" var="curr">
					<li>
						<div class="is_flipboard">
							<span id="classrooma">${curr[0]}</span>
							<span id="classnum">${curr[1]}</span>
							<span id="teacher">${curr[2]}</span>
							<span id="class">${curr[3]}</span>
							<span id="classa">${curr[4]}</span>
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
            var totalPage = $('#lastPage').val();
            currpage = currpage%totalPage;
			//console.log(index);
			$(".is_flipboard:eq("+index+")").animate({
				height:"0px",top:"25px"
			},speed,function(){
				var backboard=$("<div class='backboard' style='position:absolute;top:0px; left:0px; width:100%;height:100%;background-color:#333'></div>");
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
                            try{
                                $("#scrolltext").children().eq(index).children().children().eq(0).html(labRooms[index].labName);
                                $("#scrolltext").children().eq(index).children().children().eq(1).html(labRooms[index].courseName);
                                $("#scrolltext").children().eq(index).children().children().eq(2).html(labRooms[index].teacher);
                                $("#scrolltext").children().eq(index).children().children().eq(3).html(labRooms[index].classes);
                                $("#scrolltext").children().eq(index).children().children().eq(4).html(labRooms[index].status);
                            }catch(err){
                                $("#scrolltext").children().eq(index).children().children().eq(0).html("");
                                $("#scrolltext").children().eq(index).children().children().eq(1).html("");
                                $("#scrolltext").children().eq(index).children().children().eq(2).html("");
                                $("#scrolltext").children().eq(index).children().children().eq(3).html("");
                                $("#scrolltext").children().eq(index).children().children().eq(4).html("");
                            }
//                           if (labRooms[index].labName == undefined&&labRooms[index].labName == null){
//                               $("#scrolltext").children().eq(index).children().children().eq(0).html("");
//                               $("#scrolltext").children().eq(index).children().children().eq(1).html("");
//                               $("#scrolltext").children().eq(index).children().children().eq(2).html("");
//                               $("#scrolltext").children().eq(index).children().children().eq(3).html("");
//                               $("#scrolltext").children().eq(index).children().children().eq(4).html("");
//						   }else {
//                               $("#scrolltext").children().eq(index).children().children().eq(0).html(labRooms[index].labName);
//                               $("#scrolltext").children().eq(index).children().children().eq(1).html(labRooms[index].courseName);
//                               $("#scrolltext").children().eq(index).children().children().eq(2).html(labRooms[index].teacher);
//                               $("#scrolltext").children().eq(index).children().children().eq(3).html(labRooms[index].classes);
//                               $("#scrolltext").children().eq(index).children().children().eq(4).html(labRooms[index].status);
//						   }
                        },error:function(err){
                            console.log(err);
                        }
                    });
				console.log(labRooms[0]);
		    }).animate({
				height:"50px",top:"0px"
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
