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
	body{
	    overflow:scroll !important;
    }		
    .iStyle_RightInner{
        width:100%!important;
    }
    .iStyle_Iframe{
        width:100%!important;
        margin:0;
        box-shadow:none;
    }
		
</style>

</head>
<body>
<div class="iStyle_RightInner">
    <div class="navigation">
        <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">预约排课</a></li>
            <li class="end"><a href="/nwulims/personal/listMyInfo">选课组</a></li>
        </ul>
        </div>
    </div>

    <div id="TabbedPanels1" class="TabbedPanels">
            <ul class="TabbedPanelsTabGroup iStyle_Marksfeild">
                <li class="TabbedPanelsTab selected iStyle_Mark iStyle_ActiveMark" src="${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=1&id=-1&flag=1"><span>理论课</span></li>
                <li class="TabbedPanelsTab iStyle_Mark" src="${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=1&id=-1&flag=2"><span>实验课</span></li>
            </ul>
      <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
          <div class="content-box">
              <div class="iStyle_Iframe" id="ssd" >
                  <iframe style="width:100%;border:none;" src="${pageContext.request.contextPath}/timetable/listCourseDetails?currpage=1&id=-1&flag=1" id="mainframe"></iframe>
              </div>
           </div>
        </div>
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
