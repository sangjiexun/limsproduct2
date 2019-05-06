<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="none"/>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>

	<link href="${pageContext.request.contextPath}/css/stylek.css" type="text/css" rel="stylesheet">
  <script type="text/javascript">

  </script>
  <style type="text/css">
  	body{
  		background-image:url("${pageContext.request.contextPath}/images/bgk.png");
  	}
  </style>
  
</head>
  
<body >
	<div id="demo">
		<div id='indemo'>
			<div  id='demo1'>
				<img src="${pageContext.request.contextPath}/images/cloudk.png" >
				<img src="${pageContext.request.contextPath}/images/cloudk.png" >
				<img src="${pageContext.request.contextPath}/images/cloudk.png" >
			</div>
			<div id='demo2'></div>
		</div>
	</div>
	<div class="header" style="height:95px;">
	<img src="${pageContext.request.contextPath}/images/cms/logo.png" style="margin-top:60px;width: 360px;margin-left: 80px;">
 </div>
  <div id="classlist" >
			<h2 style="font-size:30px;">D教${floor}层实验教学动态情况一览表
			  <div CLASS="time">
				<span id="adian_today">

				</span>
			 </div>
			</h2>

			<ul id="vi-title">
				<li id="teacher" style="width:10%">节次</li>
				<li id="classroom">实验室</li>
				<li id="classnum" style="width:20%">课程</li>
				<li id="ItemsName" style="width:20%">实验项目</li>

				<li id="class" style="width:20%">班级</li>
				<li id="num" style="width:10%">人数</li>
			</ul>
			<div id="rolltext">
			<ul id="scrolltext1" class="notic-text" style=";overflow: hidden;">
			<c:forEach items="${timetableList}" var="curr" varStatus="a">

			  <li <c:if test="${a.index%2==0 }">class="one"</c:if> <c:if test="${a.index%2==1 }">class="two"</c:if>>
				<div class="is_flipboard">
					<span id="teacher" style="width:10%">
						<c:forEach items="${curr.timetableAppointmentSameNumbers}" var="curr1">${curr1.startClass}-${curr1.endClass}</c:forEach>
					</span>
					<span id="classrooma" lab="244" >实验室${ids[a.count-1]}</span>
					<span id="classnum" style="width:20%">${curr.schoolCourse.courseName}</span>
					<span id="ItemsName" style="width:20%">${ItemsName[a.count-1]}</span>

					<span class="class" style="width:20%">${classNames[a.count-1]}</span>
					<%--<span class="num" style="width:10%">${curr.schoolCourse.planStudentNumber}</span>--%>
					<span class="num" style="width:10%">${StudentNum[a.count-1]}</span>
				
				</div>
				</li>
			  </c:forEach>
			 
				
			
			</ul>
			
			<ul id="scrolltext2" class="notic-text" style="overflow: hidden;">
			
			
			</ul>
			</div>
			</div>

  <div id="power"><img src="${pageContext.request.contextPath}/images/cms/logo.png" style="margin-top:20px !important;width: 50%;"></div>
  <script type="text/javascript">
  			var rolling1 = document.getElementById("scrolltext1");
	  		var rolling2 = document.getElementById("scrolltext2");
  			var height = rolling1.clientHeight;
  			
            if(height>240){
            	
	  			var speed = 50
	            rolling2.innerHTML = rolling1.innerHTML
				var rolltext = document.getElementById("rolltext");
	            function Marquee() {
	                if(rolltext.scrollTop >= rolling1.scrollHeight) {
	                    rolltext.scrollTop = 0
	                } else {
	                    rolltext.scrollTop++
	                }
	            }
	            var MyMar = setInterval(Marquee, speed)
	            rolltext.onmouseover = function() {
	                clearInterval(MyMar)
	            }
	            rolltext.onmouseout = function() {
	                MyMar = setInterval(Marquee, speed)
	            }
            }
            function refresh() 
			{ 
				window.location.reload(); 
			} 
			setTimeout('refresh()',5*60*1000);
  </script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/cload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/sortTable.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tablesorter.js"></script>
</body>
</html>
