<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" import="java.sql.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" /> 
    <%
    String sUrl = ((HttpServletRequest) pageContext.getRequest()).getServletPath(); 
    if (sUrl.indexOf("index.jsp")>-1||sUrl.indexOf("checkCenter")>-1){
    %>
    <meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/test">
    <%}else if(session.getAttribute("LOGINTYPE")!=null && session.getAttribute("LOGINTYPE").equals("res")){
    	%>
    	<meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/lab/labAnnexList?currpage=1">     
    	<%}
    else if(session.getAttribute("LOGINTYPE")!=null && session.getAttribute("LOGINTYPE").equals("manage")){
    	%>
    	<meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=9">     
    	<%}
    else if(session.getAttribute("LOGINTYPE")!=null && session.getAttribute("LOGINTYPE").equals("devMag")){
    	%>
    	<meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/device/listLabRoomDevice?page=1">     
    	<%}
    else if(session.getAttribute("LOGINTYPE")!=null && session.getAttribute("LOGINTYPE").equals("devRes")){
    	%>
    	<meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/device/listLabRoomDevice?page=1&isReservation=1">     
    	<%}
    else if(session.getAttribute("LOGINTYPE")!=null && session.getAttribute("LOGINTYPE").equals("time")){
    	%>
    	<meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/timetable/selfTimetable/listSelfTimetable">     
    	<%}
    else if(session.getAttribute("LOGINTYPE")!=null && session.getAttribute("LOGINTYPE").equals("asset")){
    	%>
    	<meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/asset/auditAssetApps?currpage=1&assetAuditStatus=3">     
    	<%}
    else if(session.getAttribute("LOGINTYPE")!=null && session.getAttribute("LOGINTYPE").equals("system")){
    	%>
    	<meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/report/teachingReport/monthRegister?currpage=1">     
    	<%}
    else if(session.getAttribute("LOGINTYPE")!=null && session.getAttribute("LOGINTYPE").equals("assetRes")){
    	%>
    	<meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/asset/listAssetApps?currpage=1&assetAuditStatus=9">     
    	<%}
    else if(session.getAttribute("LOGINTYPE")!=null && session.getAttribute("LOGINTYPE").equals("itemShow")){
    	%>
    	<meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/checkCenter">     
    	<%}
    else if(session.getAttribute("LOGINTYPE")!=null && session.getAttribute("LOGINTYPE").equals("knowMap")){
    	%>
    	<meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/visualization/show/index">     
    	<%}
    else if( session.getAttribute("LOGINTYPE")==null||session.getAttribute("LOGINTYPE")!=null  && session.getAttribute("LOGINTYPE").equals("index")||(sUrl.indexOf("index.jsp")>-1&&!"ZYY".equals(session.getAttribute("LOGINTYPE")))){
    	%>
    	<meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/">
    	<%}else{%>
    <title><spring:message code="all.trainingRoom.labroom" />智能管理系统</title>
    <meta http-equiv="P3P" content='CP="IDC DSP COR CURa ADMa  OUR IND PHY ONL COM STA"'>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iStyle_Feelings_Base.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
    <!--以下六行不得删除！-->
    <!--[if IE 8] > 
	<meta http-equiv="X-UA-Compatible" content="IE=7"/> 
<![endif]-->
    <!--[if IE 7]>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome-ie7.min.css"/>
<![endif]-->
    <script type="text/javascript"	src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mousewheel.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_Feelings_Core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"></script>
    <script>
        var istyle_feelings_core = null;
        $(document).ready(function (e) {
            istyle_feelings_core = new iStyle_Feelings_Core({
                'settings': {
                    'target': '.iStyle_Feelings_Conteiner[name="demo"]',
                    'testmode': false,
                    'autourl': true,
                    'ie7compat': true,
                    'unselectable': true,
                    'nocookies': false
                },
                'menu': {
                    'switchon': true,
                    'algintoiframe': true,
                    'autoresize': false,
                    'hover_animate': '1'
                },
                'tree': {
                    'switchon': true,
                    'cookies': true,
                    'scrolling': true,
                    'mousewheel': true,
                    'scrollbutton': true,
                    'allwayshowmark': false,
                    'dragwidth': false,
                    'longitudinaldom': '.iStyle_Feelings_Header,.iStyle_Feelings_Footer',
                    'latitudinaldom': '.iStyle_Feelings_Maininner',
                    'hover_animate': '1',
                    'width': 'auto'
                },
                'minwindow': {
                    'switchon': false,
                    'cookies': false,
                    'multiwin': true
                },

                'userinteraction': {
                    'self_adaption': {
                        'target': '.iStyle_Feelings_Maininner',
                        'longitudinaldom': '.iStyle_Feelings_Header,.iStyle_Feelings_Footer',
                        'latitudinaldom': '.iStyle_Feelings_Tree'
                    }
                }
            });
        });
    </script>
    <script type="text/javascript">
        $(document).ready(function(){
            var explorer =navigator.userAgent ;
            if (explorer.indexOf("Firefox") < 0 && explorer.indexOf("Chrome") < 0) {
                $('.black_overlay').css("display", "block");
                $('.login_frame').css("display", "block");
            }
        });
        function closeLayer() {
            $('.black_overlay').css("display", "none");
            $('.login_frame').css("display", "none");
        }
    </script>
    <style type="text/css">
        .login_frame {
            position: fixed;
            background: #fff;
            top: 50%;
            left: 50%;
            width: 480px;
            margin: -155px 0 0 -260px;
            text-align: center;
            padding: 30px 0 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,.3);
            border-radius: 4px;
            z-index: 9999;
        }
        .black_overlay {
            position: fixed;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            background: rgba(0,0,0,.3);
            z-index: 99998;
        }
    </style>
    <script type="text/javascript">
		function changeRole(){
			layer.ready(function(){ 
			    layer.open({
			        type: 2,
			        title: '选择',
			        fix: true,
			        maxmin:true,		        
			        shift:-1,
			        closeBtn: 0,
			        shadeClose: true,
			        move:false,
			        maxmin: false,
			        area: ['536px', '320px'],
			        content: '${pageContext.request.contextPath}/common/changeUserRolePage?username=${sessionScope.loginUser.username}',
			        end: function(){
			        	//search()
			        }
			    });
			});
		}
	</script>
</head>

<body class="iStyle_Feelings_Conteiner" name="demo">
    <div class="iStyle_Feelings_Conteiner" name="demo">
        <div class="iStyle_Feelings_Header">
        <img src="images/system_pic/${PROJECT_NAME}_logo.png"/>
        <div class="iStyle_Feelings_LogoBox">
                <!--<img src="images/logo_01.jpg" alt="">-->
            </div>
        </div>
        <div class="iStyle_Feelings_Tree" slided="false">
        <jsp:include page="/WEB-INF/sitemesh-common/menuAdmin.jsp" />
        </div>
        <div class="iStyle_Feelings_Maininner">
        
            <div class="iStyle_Feelings_UsersBox">
                <form>
                    <span class="system_tit"><spring:message code="title" />&nbsp;
                        <c:if test="${sessionScope.PROJECT_NAME ne 'nwulims'}"><font size="4">${academy.academyName}</font></c:if>
                    </span>
                    <label class="button_above">
                        <font>您好!&nbsp;${sessionScope.authorityName}<i class="icon-user-2"></i>${user.cname}</font>
                    	<%--<a class="button icon-envelope-alt fa-home" href="${sessionScope.cmsUrl}" title="门户首页"></a>--%>
                    	<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_LABORATORYMAINTENANCE">
                    		<a class="button icon-envelope-alt fa-university" href="${sessionScope.cmsSiteUrl}" title="网站管理"></a>
                    	</sec:authorize>
                        <c:if test="${sessionScope.selected_role ne 'ROLE_STUDENT'}">
                            <a class="button icon-envelope-alt fa-slideshare" href="javascript:void(0);" onclick="changeRole()" title="切换角色"></a>
                        </c:if>
                        <%--<sec:authorize ifNotGranted="ROLE_STUDENT,ROLE_TEACHER">
                            <a class="button icon-envelope-alt fa-ils" href="${pageContext.request.contextPath}/checkCenter/" title="切换中心"></a>
                        </sec:authorize>--%>
                        <a id="logout" class="button icon-envelope-alt fa-power-off" href="${pageContext.request.contextPath}/pages/logout-front-redirect.jsp" title="退出登录" target="_parent"> </a>
                    </label>
                </form>
            </div>
            <div class="black_overlay" style="display:none">
                <div class="login_frame" style="display:none">
                    <div class="login">
                        <div class="holder">
                            为避免造成使用不便，请使用谷歌或火狐浏览器<br>
                            <a href='https://www.google.cn/chrome/' target='_blank'>点击下载谷歌</a><br>
                            <a href='http://www.firefox.com.cn/' target='_blank'>点击下载火狐</a><br>
                            <input type="button" onclick="closeLayer()" value="确定" />
                        </div>
                    </div>
                    <div class="close">
                        <i></i>
                    </div>
                </div>
            </div>
            <%
			if (((HttpServletRequest) pageContext.getRequest()).getServletPath().equals("/index.jsp")||sUrl.equals("/checkCenter")){
			}else{
			%>
				<decorator:body />
			<%}%>
            <iframe class="iStyle_Feelings_Iframe" name="iStyle_Feelings_Iframe" frameborder="no" border="0">     
            </iframe>            
            <div class="iStyle_Feelings_Footer"><font>© 2018 庚商网络信息技术有限公司 | @POWER BY GVSUN</font></div>
        </div>        
        <div class="cookiestest">
        </div>
    </div>
<%} %>
</body>

</html>