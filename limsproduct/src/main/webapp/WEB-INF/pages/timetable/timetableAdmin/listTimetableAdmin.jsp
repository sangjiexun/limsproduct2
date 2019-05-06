<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.pageModel-resources" />
<html>
<head>
<meta name="decorator" content="iframe"/>
<title>无标题文档</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/is_LeftList.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/is_Searcher.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/is_SystemUI_Allpages.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
<style>
	body{overflow:scroll !important;
		}
		
</style>

</head>
<body style="overflow: scroll !important;">

<div class="iStyle_Conteiner">
<div class="iStyle_RightInner">

<div class="navigation">
<div id="navigation">
<ul>
	<li><a href="javascript:void(0)">排课管理</a></li>
	<li class="end">排课管理</li>
</ul>
</div>
</div>

<div class="iStyle_Searchfeild">
<div class="iStyle_Tagsfeild"></div>
<div class="iStyle_Marksfeild">
     <div class="iStyle_Mark iStyle_ActiveMark" src="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=-1"><span>全部</span></div>
     <div class="iStyle_Mark" src="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=10"><span>正在进行中</span></div> 
     <div class="iStyle_Mark" src="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=1"><span>已发布</span></div>
     <div class="iStyle_Mark" src="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=2"><span>未发布</span></div>
</div>
</div>

<div class="iStyle_Iframe" id="ssd" >
     <iframe  scrolling="yes" src="${pageContext.request.contextPath}/timetable/timetableAdminIframe?currpage=1&id=-1&status=-1" id="mainframe">
     </iframe>
    
</div>
</div>
</div>
 <!-- <script type="text/javascript">
		$(document).ready(function(e) {
			var hei=0;
			var is_debug_resize=setInterval(function(){
				if($("#mainframe").length>0){
					//hei=0;
					hei=$("#mainframe").contents().find("html").height();
					//console.log(hei);
					$("#mainframe").css({"height":hei+'px'});
					$("#ssd").css({"height":hei+'px'});
					} 
				});
				
			});
	</script> -->
	
	
	
	<script type="text/javascript">		
$(document).ready(function(e) {
		var hei=0;
		var is_debug_resize=setInterval(function(){
			if($("#mainframe").length>0){
				//hei=0;
				
			//	right_frame.document.body.scrollHeight
				hei=$("#mainframe").contents().find("html").get(0).scrollHeight;
				//console.log(hei);
				$("#mainframe").css({"height":hei+'px'});
				$("#ssd").css({"height":hei+'px'});
				}
			},100);
	});
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_Icons.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_LeftList.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Downlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Allpages.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Interface.js"></script>
</body>
</html>
