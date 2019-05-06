<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <title><spring:message code="title.html.name"/></title>
    <meta name="decorator" content="none"/>
    <meta name="Generator" content="gvsun">
    <meta name="Author" content="lyyyyyy">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/visualization/floor/style.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/visualization/floor/table.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/visualization/floor/layout_header.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/visualEngine/visual-core.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/visualEngine/visual-point-show.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/visualEngine/visual-point-editor.css" rel="stylesheet" type="text/css">
    <%-- <link href="${pageContext.request.contextPath}/css/visualization/floor/panorama_viewer.css" rel='stylesheet' type='text/css'> --%>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/visualization/floor/jquery-1.11.0.min.js"></script>
    <%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/floor/jquery.panorama_viewer.js"></script> --%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/floor/drag.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-point-show.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-point-editor.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-check-data.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
    <!--<script type="text/javascript" src="js/jquery.mousewheel.js"></script>
    <script type="text/javascript" src="js/perfect-scrollbar.js"></script>
<link href="css/perfect-scrollbar.css" rel='stylesheet' type='text/css'>-->
    <!-- <script type="text/javascript">
       /*  setInterval("TT()", 10); */

        function TT() {
            var Height = $(window).height();
            var Width = $(window).width()
            $(".pv-inner,.drag_container,.panorama").height(Height);
            $(".drag_container").width(Width);
            var img_w=$("#viewer_img").width();
        	$(".panorama").width(img_w);
        	//console.log("实际宽度"+$(".panorama").width())
        }
    </script> -->
    <style>
        body {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }

        .layout_logobg_container {
            width: 100%;
            margin: 0;
            height: 13%;
        }

        .cms_logo {
            position: absolute;
            top: 0;
            bottom: 0;
            left: 1.5%;
            height: 80%;
            width: auto;
            margin: auto;
        }

        .right_info {
            position: absolute;
            right: 1.5%;
            top: 0;
            bottom: 0;
            height: 16px;
            margin: auto;
            font-size: 12px;
        }

        .right_info a {
            color: #fff;
        }

        .layout_panorama_container {
            width: 100%;
            height: 87%;
            box-sizing: border-box;
            padding: 1.5%;
        }

        .panorama {
            position: relative;
            width: 100%;
            height: 100%;
        }

        .back_url {
            /*width: auto;*/
            height: 100%;
            position: relative;
            display: block;
        }

        .drag_container {
            position: relative;
            z-index: 0;
            overflow: hidden;
            width: 100%;
            /*width: 49.25%;*/
            height: 100%;
            float: left;
        }

        .schedule_box {
            position: relative;
            z-index: 0;
            overflow: hidden;
            width: 49.25%;
            height: 100%;
            float: right;
            top: 0;
            left: 0;
            border-radius: 0;
            box-shadow: none;
            overflow: auto;
            display: block;
        }

        .layout_logobg_container form {
            position: absolute;
            top: 10px;
            right: 19px;
        }

        #search {
            color: #cecece;
        }

        .schedule a {
            font-size: 12px;
            background: #44B2ED;
            height: 18px;
            display: block;
            line-height: 18px;
            width: 25px;
            border-radius: 5px;
            color: #fff;
        }

        .schedule_box table {
            width: 100%;
            min-height: 90%;
            border-collapse: collapse;
        }

        .schedule_box table td, .schedule_box table th {
            border: none;
            border-bottom: 1px solid #e6e6e6;
            height: 20px;
            padding: 0 7px;
        }

        .schedule_box table th {
            font-size: 13px;
            font-weight: bold;
            white-space: nowrap;
        }

        .schedule_box table td {
            font-size: 12px;
        }

        .schedule_box table caption {
            height: 45px;
            line-height: 45px;
            font-size: 18px;
            text-align: center;
            margin: 0 0 4px;
        }

        .schedule_box table caption font {
            background: #fff;
            padding: 0 15px;
            font-weight: bold;
            letter-spacing: 1px;
        }

        .schedule_box table caption div {
            border-bottom: 1px solid #e6e6e6;
            margin: -22px 0 0;
        }

        .build_detail {
            display: none !important;
        }

        .num {
            font-weight: normal;
            left: 23px;
            color: #fdfdfd;
            top: -24px;
            text-shadow: 0 0 1px rgba(0, 0, 0, 0.6);
            font-size: 14px;
            background: rgba(74, 74, 74, 0.67);
            padding: 6px 12px;
            border-radius: 5px;
            letter-spacing: 1.5px;
            transition: all 0.5s;
            cursor: default;
        }

        .equipment_icon:hover .num {
            background: rgba(74, 74, 74, 0.8);
        }

        .equipment_icon:hover {
            cursor: pointer;
        }

        .roomselected {
            background: rgba(0, 0, 0, 0.67);
            border-radius: 5px;
            color: #fff;
            font-size: 14px;
            padding: 15px;
            border: 1px solid #777;
            box-shadow: none;
            position: absolute;
            display: none;
            top: 48%;
            left: 24%;
            margin: -165px 0 0 -89px;
            z-index: 9999;
        }
        .background_change {
            position: absolute;
            right: 1.5%;
            top: 60%;
        }
    </style>
</head>

<body>
<input type="hidden"  id="contextPath" value="${pageContext.request.contextPath}"/>
<input type="hidden"  id="visual_type" value="${type}"/>
<input type="hidden"  id="directoryName" value="${directoryName}"/>

<div class="layout_logobg_container">
    <img src="${pageContext.request.contextPath}/images/system_pic/${PROJECT_NAME}_main.png" class="cms_logo">
    <%-- <c:if test="${empty user}">
                <form>
                    <input id="search" style="    height: 21px;" name="keyword" value="搜索你喜欢的"
                        onfocus="this.value='';"
                        onblur="if(this.value==''){this.value='搜索你喜欢的'}" />
                        <input class="login_btn" type="button" value="登录" onclick="login()"/>
                </form>
            </c:if> --%>
    <c:if test="${!empty user}">
        <div class="right_info">
            <font style="color:#fff;">您好!${authority} ${user.cname} </font>
            <%
                //前端登录标记
                session.removeAttribute("LOGINTYPE");
            %>
            <a href="${sessionScope.backToCms}" style="margin:0 0 0 13px;">返回门户</a>
            <a href="${pageContext.request.contextPath}/pages/logout-front-redirect.jsp" target="_parent">退出</a>
        </div>
        <div class="background_change">
            <a href="${pageContext.request.contextPath}/visualization/changeBackground?page=1&type=campus" style="margin:0 0 0 13px;">地图管理</a>
            <a href="${pageContext.request.contextPath}/visualization/allProperties" style="margin:0 0 0 13px;">配置管理</a>
            <a href="${pageContext.request.contextPath}/visualization/visualTemplet" style="margin:0 0 0 13px;">模板管理</a>
        </div>
    </c:if>
    <!-- <div class="right_container">
        <p>中文</p>
        <p>English</p>
    </div> -->
</div>
<script>
    $(".close").click(
        function () {
            $(".log-in").hide();
        }
    )

    //登录
    $(function () {
        $('.black_overlay').css("display", "none");
        $('.login_frame').css("display", "none");
        $('.loginbtn').bind("click", function () {
            $('.black_overlay').css("display", "block");
            $('.login_frame').css("display", "block");
        });
        $('.close').bind("click", function () {
            $('.black_overlay').css("display", "none");
            $('.login_frame').css("display", "none");
        })


    })

    function login() {
        var urlString = "${pageContext.request.contextPath}/cms";
        $.ajax({
            url: "${pageContext.request.contextPath}/cms/setSession?type=" + 1 + "&urlString=" + urlString,
            type: "POST",
            success: function (data) {//AJAX查询成功
                if (data == "success") {
                    $('.black_overlay').css("display", "block");
                    $('.login_frame').css("display", "block");
                }
            }
        });
    }

    function open(url) {
        var url = java.net.URLEncoder.encode(url, "utf-8");
        window.location.herf = "${pageContext.request.contextPath}/visualization/show/floor?buildNumber=" + url;
    }
</script>

<div class="layout_panorama_container">
    <div class="drag_container">

        <div id="visual_father" class="panorama "
             style="position:relative; background-color:#FFFFFF;left:0px;top:0px;filter:alpha(opacity=100);opacity:1;">
            <img class="back_url" src="${pageContext.request.contextPath}/images/visualization/superschool1.jpg" id="viewer_img">
            <%--<iframe src="${pageContext.request.contextPath}/map.jsp" width="600" height="300" frameborder="0" scrolling="no"></iframe>--%>
        </div>
    </div>
</div>


</div>
</body>
<!--可视化初始化-->
<script th:inline="javascript">
    var iconUrl = "${pageContext.request.contextPath}/images/visualEngine"
    initDirectoryEngine({
        getHostsUrl:"${pageContext.request.contextPath}/shareApi/getHosts",
        getAuthorizationUrl:"${pageContext.request.contextPath}/shareApi/getAuthorization"
    });
    <%--getDirectoryId({--%>
        <%--&lt;%&ndash;directoryNames:"实验室管理/"+[[${courseInfoVO.title}]]+"("+[[${siteId}]]+")"+"/可视化引擎",&ndash;%&gt;--%>
<%--//        directoryNames:"实验室管理/庚商实验室/可视化引擎",--%>
        <%--directoryNames:"${directoryName}",--%>
        <%--type:2,--%>
        <%--success:function (directId) {--%>
    <%--initVisualCore([[${createUser}]]);--%>
    <%--//初始化可视化标记展示(展示在哪个标签里,底图的路径)--%>
    <%--//                initVisualPoints($("#teachOutlineImage").children(":first-child").children(":first-child"),directId);--%>
    <%--//initVisualPoints($(".course-banner").parent(),directId);--%>
    <%--initVisualPoints($("#visual_father"),directId);--%>
    <%--//初始化编辑按钮--%>
    <%--initDictionaryEdit();--%>
    <%--//初始化标记按钮--%>
    <%--var pointsUrls = new Array();--%>
    <%--pointsUrls.push(iconUrl+"/point.png");--%>
    <%--pointsUrls.push(iconUrl+"/ckwj.png");--%>
    <%--pointsUrls.push(iconUrl+"/cxd.png");--%>
    <%--pointsUrls.push(iconUrl+"/dh.png");--%>
    <%--pointsUrls.push(iconUrl+"/gndh.png");--%>
    <%--pointsUrls.push(iconUrl+"/gnmc.png");--%>
    <%--pointsUrls.push(iconUrl+"/jh.png");--%>
    <%--pointsUrls.push(iconUrl+"/mj.png");--%>
    <%--pointsUrls.push(iconUrl+"/sp.png");--%>
    <%--pointsUrls.push(iconUrl+"/tp.png");--%>
    <%--pointsUrls.push(iconUrl+"/zs.png");--%>
    <%--pointsUrls.push(iconUrl+"/zdhx.png");--%>
    <%--pointsUrls.push(iconUrl+"/zdhx2.png");--%>
    <%--pointsUrls.push(iconUrl+"/zjmc.png");--%>
    <%--pointsUrls.push(iconUrl+"/zzxx.png");--%>
    <%--initPointImageUrls(pointsUrls);--%>
        <%--}--%>
        <%--});--%>
    getDirectoryId({
        <%--directoryNames:"实验室管理/"+[[${courseInfoVO.title}]]+"("+[[${siteId}]]+")"+"/可视化引擎",--%>
//        directoryNames:"实验室管理/庚商实验室/可视化引擎",
        directoryNames:"${directoryName}",
        type:2,
        success:function (directId) {
            getBackgroundByDirectoryId({
                directId:directId,
                success:function (data) {
                    console.log(data);
                    $("#viewer_img").attr("src",data.url);
                    //初始化可视化核心(可视化引擎地址，当前登陆人)
                    initVisualCore([[${createUser}]]);
                    //初始化可视化标记展示(展示在哪个标签里,底图的路径)
//                initVisualPoints($("#teachOutlineImage").children(":first-child").children(":first-child"),directId);
                    //initVisualPoints($(".course-banner").parent(),directId);
                    initVisualPoints($("#visual_father"),directId);
                    //初始化编辑按钮
                    initDictionaryEdit();
                    //初始化标记按钮
                    var pointsUrls = new Array();
                    pointsUrls.push(iconUrl+"/point.png");
                    pointsUrls.push(iconUrl+"/ckwj.png");
                    pointsUrls.push(iconUrl+"/cxd.png");
                    pointsUrls.push(iconUrl+"/dh.png");
                    pointsUrls.push(iconUrl+"/gndh.png");
                    pointsUrls.push(iconUrl+"/gnmc.png");
                    pointsUrls.push(iconUrl+"/jh.png");
                    pointsUrls.push(iconUrl+"/mj.png");
                    pointsUrls.push(iconUrl+"/sp.png");
                    pointsUrls.push(iconUrl+"/tp.png");
                    pointsUrls.push(iconUrl+"/zs.png");
                    pointsUrls.push(iconUrl+"/zdhx.png");
                    pointsUrls.push(iconUrl+"/zdhx2.png");
                    pointsUrls.push(iconUrl+"/zjmc.png");
                    pointsUrls.push(iconUrl+"/zzxx.png");
                    initPointImageUrls(pointsUrls);
                }
            })

        }
    });
</script>
</html>