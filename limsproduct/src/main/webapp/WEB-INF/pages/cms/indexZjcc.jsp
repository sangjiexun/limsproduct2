<%@page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!doctype html>
<html lang="en">
<head>
    <title><spring:message code="title.html.name"/></title>
    <meta name="Generator" content="gvsun">
    <meta name="Author" content="lyyyyyy">
    <meta name="Keywords" content="">
    <meta name="decorator" content="none">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/cms/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cms/media-queries.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>

    <!-- Slider Plugin -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/index/jquery.glide.js"></script>
    <link href="${pageContext.request.contextPath}/css/cms/bdsstyle.css" rel="stylesheet" type="text/css">
    <%session.setAttribute("LOGINTYPE", "cmsIndex"); %>
    <style type="text/css">
        .login_frame img {
            top: 0px;
            margin: 0px 0px 15px 0px;
        }
    </style>

    <script type="text/javascript">
        var flag = 0;
        if (${loginUser.username != "" && loginUser.username != null}) {
            flag = 1;
        }
        ;
        $("#know_map").click(function () {
            alert("您还未登录，请先登录!");
        });
    </script>
</head>
<body>
<div class="logo">
    <div class="mobile-action"></div>
    <img src="images/system_pic/${PROJECT_NAME}_main.png" id="logo">
</div>
<div class="nav">
    <ul class="naver">
        <li>
            <a href="${pageContext.request.contextPath}/home">首页</a>
        </li>
        <c:if test="${needLogin || empty needLogin}">
            <li><a href="#" onclick="login()">系统登录</a></li>
        </c:if>
        <script type="text/javascript">
            $(".naver li").hover(
                function () {
                    $(this).children().addClass("selected");
                    $('.menu', this).stop().slideDown(200);
                },
                function () {
                    $('a', this).removeClass("selected");
                    $('ul', this).stop().slideUp(200);
                }
            )

            function needlogin() {
                alert("请登录!")
            }
        </script>
    </ul>
    <!-- 移动端导航 开始 -->
    <script type="text/javascript">
        var showen = false;

        $(".mobile-action").click(function () {
            if (!showen) {
                $(".nav .naver").stop();
                $(".nav .naver").slideDown("slow");
                showen = true;
            } else {
                $(".nav .naver").stop();
                $(".nav .naver").slideUp("slow");
                showen = false;
            }
        });
    </script>
    <!-- 移动端导航 结束 -->
</div>
<div class="slider">
    <!-- 轮播图 -->
    <ul class="slides">
        <li class="slide">
            <div class="box"><img src="${pageContext.request.contextPath}/images/system_pic/${PROJECT_NAME}_round1.jpg"/></div>
        </li>
        <li class="slide">
            <div class="box"><img src="${pageContext.request.contextPath}/images/system_pic/${PROJECT_NAME}_round2.jpg"/></div>
        </li>
        <li class="slide">
            <div class="box"><img src="${pageContext.request.contextPath}/images/system_pic/${PROJECT_NAME}_round3.jpg"/></div>
        </li>
    </ul>
    <script type="text/javascript">
        var glide = $('.slider').glide({
            //autoplay:true,//是否自动播放 默认值 true如果不需要就设置此值
            animationTime: 500, //动画过度效果，只有当浏览器支持CSS3的时候生效
            arrows: true, //是否显示左右导航器
            arrowsWrapperClass: "arrowsWrapper", //滑块箭头导航器外部DIV类名
            arrowMainClass: "slider-arrow", //滑块箭头公共类名
            arrowRightClass: "slider-arrow--right", //滑块右箭头类名
            arrowLeftClass: "slider-arrow--left", //滑块左箭头类名
            arrowRightText: ">", //定义左右导航器文字或者符号也可以是类
            arrowLeftText: "<",

            nav: true, //主导航器也就是本例中显示的小方块
            navCenter: true, //主导航器位置是否居中
            navClass: "slider-nav", //主导航器外部div类名
            navItemClass: "slider-nav__item", //本例中小方块的样式
            navCurrentItemClass: "slider-nav__item--current" //被选中后的样式
        });
    </script>
    <c:if test="${not empty needLogin && !needLogin}">

        <div class="tool-box" style="background:none;">
            <div class="log-opcity"></div>
            <div class="log-in">
                <div class="username" style="color:black;">欢迎，${username }</div>
                <div class="password">
                    <span style="float:left;width:auto;" class="sds"><a
                            href="${pageContext.request.contextPath}/test">进入实验室智能管理系统</a></span>
                    <span style="float:right;"><a style="background:none;border:none;margin:0;color:black;"
                                                  href="${pageContext.request.contextPath}/pages/logout-redirect.jsp"
                                                  onclick="clearCookie();">退出</a></span>
                </div>
            </div>
        </div>
    </c:if>
</div>

<div class="link-box">
    <div class="link" style="overflow:visible">
        <div class="common-title">信息公告</div>
        <ul class="link_common">
        </ul>
    </div>
</div>
<div class="footer-box">
    <div class="footer">
        <div class="address">
            <div class="title address-tit">联系地址</div>
            <ul>
                <li><spring:message code="all.school.address"/></li>
            </ul>
        </div>
        <div class="friend">
            <div class="title">版权信息</div>
            <ul>
                <li style="text-indent: 20px;width:120%;"><spring:message code="all.school.copyright"/></li>
            </ul>
        </div>
    </div>
    <div class="power">
        POWERED BY | <a href="http://www.gvsun.com">GVSUN</a>
    </div>
</div>
<div class="black_overlay" style="display:none">
    <div class="login_frame" style="display:none">
        <img src="images/system_pic/${PROJECT_NAME}_login.png" height="60" alt=""/>
        <div class="login">
            <div class="holder">
                <form class="sub_form" name='f' action='${pageContext.request.contextPath}/j_spring_security_check'
                      method='POST'>
                    <input class="login_input" style="margin-bottom:15px;" type="text" name="j_username"
                           placeholder="请输入用户名"/>
                    <input class="login_input" style="margin-bottom:15px;background:none;color:#333;" type="password"
                           name="j_password" placeholder="请输入密码"/>
                    <input class="btnl text" type="submit" value="登录" style="width:286px"/>
                </form>
            </div>
        </div>
        <div class="close">
            <i></i>
        </div>
    </div>
</div>
<script type="text/javascript">

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
</script>
</body>

</html>