<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title><spring:message code="title.html.name"/></title>
    <meta name="keywords" content="百度地图,百度地图API，百度地图自定义工具，百度地图所见即所得工具" />
    <meta name="description" content="百度地图API自定义地图，帮助用户在可视化操作下生成百度地图" />
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
    <link href="${pageContext.request.contextPath}/css/visualEngine/visual-map.css" rel="stylesheet" type="text/css">
    <%-- <link href="${pageContext.request.contextPath}/css/visualization/floor/panorama_viewer.css" rel='stylesheet' type='text/css'> --%>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/visualization/floor/jquery-1.11.0.min.js"></script>
    <%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/floor/jquery.panorama_viewer.js"></script> --%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/floor/drag.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-point-show.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-point-editor.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-check-data.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualEngine/visual-map.js"></script>
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
    <!--引用百度地图API-->
    <style type="text/css">
        html,body{margin:0;padding:0;}
        .iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
        .iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
    </style>
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

        .panorama img {
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
    <script type="text/javascript">

        $(document).ready(function () {
            $(".equipment_icon").hover(function () {
                $(this).find(".build_detail").animate({opacity: "show", right: "5"}, "slow");
            }, function () {
                $(this).find(".build_detail").animate({opacity: "hide", right: "5"}, "fast");
            });

        });

        function setIframeHeight(iframe) {
            if (iframe) {
                var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
                if (iframeWin.document.body) {
                    iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
                }
            }
        };

        window.onload = function () {
            setIframeHeight(document.getElementById('video-iframe'));

            var buildnumber;
            bulidnumber = "-1";

            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/show/changeBuildTimetable",
                data: {'buildnumber': bulidnumber},
                success: function (data) {
                    $("#currentbuild").html(data);
                }
            });
        };
    </script>
</head>

<body>
<%-- <form id="subform" name='f'
   action='${pageContext.request.contextPath}/j_spring_security_check'
   method='POST'>
   <div class="log-in">
       <div class="log_in_box">
           <img class="close"
               src="${pageContext.request.contextPath}/images/polytechnic/msg_close.png" />
           <img
               src="${pageContext.request.contextPath}/images/cms/logo_2.png" />
           <div class="sat_name">
           </div>
           <fram>
           <div class="username-box">
               <input type="text" name="j_username" placeholder="会员账号" />
           </div>
           <div class="password-box">
               <input type="password" name="j_password" placeholder="密码" />
           </div>
           <div class="log-in-box">
               <input type="submit" value="登录" />
           </div>
           </fram>
       </div>
   </div>
</form>  --%>
<input type="hidden"  id="contextPath" value="${pageContext.request.contextPath}"/>

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
    // Use $(window).load() on live site instead of document ready. This is for the purpose of running locally only
    /* $(document).ready(function () {
        $(".panorama").panorama_viewer({
            repeat: true,
            direction: "horizontal", //让你定义的滚动方向。可接受的价值观是“水平”和“垂直”。默认值是水平
            animationTime: 0, //这允许你设置时间当图像被拖。设置为0以使其瞬间。默认值是700。
            easing: "ease-out",
            // 宽度选项 "ease", "linear", "ease-in", "ease-out", "ease-in-out", and "cubic-bezier(...))". 默认值”。 "ease-out".
            overlay: true // 真/假 这个隐藏的初始指令覆盖
        });
    }); */


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
        <%--<c:forEach items="${buils}" var="build" varStatus="i">--%>
            <%--<div id="build${build.buildNumber}" class="equipment_icon"--%>
                 <%--style="left:${100*build.xCoordinate}%;top:${100*build.yCoordinate}%;" title="查看详情">--%>
                    <%--&lt;%&ndash;<a href="${pageContext.request.contextPath}/visualization/show/floor?buildNumber=${build.buildNumber}" class="schedule" data-value="${build.buildName}">--%>
                    <%--<input type="button" onclick="open('${build.buildNumber}')">--%>
                    <%--&ndash;%&gt;<img src="${pageContext.request.contextPath}/images/visualization/floor/icon.png">--%>
                    <%--&lt;%&ndash;<span class="num">--%>
                    <%--<c:if test="${build.buildNumber ne '12'}">--%>
                    <%--${build.buildName}</c:if></span>--%>
                   <%--&ndash;%&gt;<span class="num showMessage curpoi" id="${build.buildNumber}">${build.buildName}</span>--%>
                <%--<div class="build_detail">--%>
                        <%--${build.buildName}<br>${content}--%>
                <%--</div>--%>
                <%--</a>--%>
                <%--<!-- <div class="schedule"><a href="javascript:void(0);">课表</a></div> -->--%>
            <%--</div>--%>
        <%--</c:forEach>--%>

        <div id="visual_father" class="panorama "
             style="position:relative; background-color:#FFFFFF;left:0px;top:0px;filter:alpha(opacity=100);opacity:1;">
            <%--<img src="${pageContext.request.contextPath}/images/visualization/superschool1.jpg" id="viewer_img">--%>
            <iframe src="${pageContext.request.contextPath}/map.jsp" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
                <%--<div style="width:697px;height:550px;border:#ccc solid 1px;" id="dituContent"></div>--%>
        </div>
    </div>
</div>


</div>
<div class="roomselected" id="roomselected">
    <input type="text" style="display:none" id="buildNumber"></input>
    <div class="roomname tacenter cw mtb10" id="buildName">

    </div>

    <div class="cw tacenter mtb5">
 						<span>
 							<div class="divred l">全部</div>
 						</span>
        <span class="cw">全部</span>
        <a device_detail='1' class="cw curpoi underline detail" id="device_all"></a>
        <span class="cw">开放</span>
        <a device_detail='2' class="cw curpoi underline detail" id="device_all_open"></a>
        <span class="cw">不开放</span>
        <a device_detail='3' class="cw curpoi underline detail" id="device_all_close"></a>
        <span class="cw">待定 </span>
        <a device_detail='4' class="cw curpoi underline detail" id="device_all_unknown"></a>
        <span class="cw">电控</span>
        <a device_detail='5' class="cw curpoi underline detail" id="device_all_smart"></a>
    </div>

    <div class="cw tacenter mtb5">
 						<span>
 							<div class="divyellow l">>40w</div>
 						</span>
        <span class="cw">全部</span>
        <a device_detail='6' class="cw curpoi underline detail" id="over_40"></a>
        <span class="cw">开放</span>
        <a device_detail='7' class="cw curpoi underline detail" id="over_40_open"></a>
        <span class="cw">不开放</span>
        <a device_detail='8' class="cw curpoi underline detail" id="over_40_close"></a>
        <span class="cw">待定 </span>
        <a device_detail='9' class="cw curpoi underline detail" id="over_40_unknown"></a>
        <span class="cw">电控</span>
        <a device_detail='10' class="cw curpoi underline detail" id="over_40_smart"></a>
    </div>

    <div class="cw tacenter mtb5">
 						<span>
 							<div class="divblue l">10w-40w</div>
 						</span>
        <span class="cw">全部</span>
        <a device_detail='11' class="cw curpoi underline detail" id="between_10and40"></a>
        <span class="cw">开放</span>
        <a device_detail='12' class="cw curpoi underline detail" id="between_10and40_open"></a>
        <span class="cw">不开放</span>
        <a device_detail='13' class="cw curpoi underline detail" id="between_10and40_close"></a>
        <span class="cw">待定 </span>
        <a device_detail='14' class="cw curpoi underline detail" id="between_10and40_unknown"></a>
        <span class="cw">电控</span>
        <a device_detail='15' class="cw curpoi underline detail" id="between_10and40_smart"></a>
    </div>

    <div class="cw tacenter mtb5">
 						<span>
 							<div class="divora l"><10w</div>
 						</span>
        <span class="cw">全部</span>
        <a device_detail='16' class="cw curpoi underline detail" id="less_10"></a>
        <span class="cw">开放</span>
        <a device_detail='17' class="cw curpoi underline detail" id="less_10_open"></a>
        <span class="cw">不开放</span>
        <a device_detail='18' class="cw curpoi underline detail" id="less_10_close"></a>
        <span class="cw">待定 </span>
        <a device_detail='19' class="cw curpoi underline detail" id="less_10_unknown"></a>
        <span class="cw">电控</span>
        <a device_detail='20' class="cw curpoi underline detail" id="less_10_smart"></a>
    </div>
    <div class="mtb5 tacenter" style="margin-top:10px;">
        <a class="choobtn curpoi cancelbtn " id="enterBuild" href='' target="_blank">进入楼宇</a>
        <a class="choobtn curpoi cancelbtn">取消</a>
    </div>
    <%--
   
    <div class="layout_link_container">
    	
    	<div class="layout_link_box">
    	<div class="address">
    	<div class="link_title">联系我们</div>
 ${site.bottomContent}
    </div>
    	<div class="link_box">
			<div class="link_title">友情链接</div>
			<ul class="link_list">
	   		  <c:forEach items="${Links2}"  var="link" end="11">
	   		      <li><a href="${link.linkUrl}">${link.linkName}</a></li>
              </c:forEach>
			</ul>
		</div>
		</div>
    </div>
    --%><!-- <div class="layout_footer">
    
    <div class="layout_power">
    	<p class="copyright">Copyright © 2009  上海中医药大学版权所有  沪ICP备09008682号-2</p>
        <p class="power">power by <a href="http://www.gvsun.com">Gvsun</a>
        </p>
    </div>
    </div> -->
    <script>
        $(".news_list_container").hover(
            function () {
                $(this).find(".news_list").show();
                $(this).addClass("news_selected");
                $(this).siblings().find(".news_list").hide();
                $(this).siblings().removeClass("news_selected")
            },
            function () {
                /* $(this).find(".news_list").hide() */
            }
        );
        // 全局变量
        var bulidnumber;
        var academyNumber;
        $(".equipment_icon").click(function () {
                bulidnumber = $(this).attr("id").substring(5).split('-')[0];
                academyNumber = $(this).attr("id").substring(5).split('-')[1];
                //alert(bulidnumber+"kk");
                if (bulidnumber != '' && bulidnumber != null) {
                    var enterhref = "${pageContext.request.contextPath}/visualization/show/floor?buildNumber=" + bulidnumber;
                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/visualization/show/lookBuild",
                        data: {'bulidnumber': bulidnumber},
                        dataType: 'json',
                        success: function (data) {
                            $.each(data, function (key, values) {
                                // var device_href="${pageContext.request.contextPath}/visualization/show/deviceByAcademy?academyNumber=" + academyNumber+"&typeString=";
                                document.getElementById(key).innerHTML = values;
                                $("#" + key).val("" + values);
                                //$("#"+key).attr("href",device_href+"'"+key+"'");
                            });
                        }
                    });

                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/visualization/show/changeBuildTimetable",
                        data: {'buildnumber': bulidnumber},
                        success: function (data) {
                            $("#currentbuild").html(data);
                        }
                    });

                    $("#enterBuild").attr("href", enterhref);
                    $(".roomselected").css("display", "block");
                } else {
                    alert('尚未绑定学院');
                }

            }
        );
        $(".cancelbtn").click(function () {
            var chooseDiv = document.getElementById("roomselected");
            chooseDiv.style.display = "none";
            //var detailDiv = document.getElementById("detailList");
            //detailDiv.style.display="none";
        });
        $(".cancelDetail").click(function () {
            //var detailDiv = document.getElementById("detailList");
            //detailDiv.style.display="none";
        })
        /*  $(".layout_panorama_container").hover(
             function () {
                 $(".overview_banner").stop().fadeOut(150)
             },
             function () {
                 $(".overview_banner").stop().delay(150).fadeIn(150)
             });  */

        /* $(".buliding_list li a").hover(
            function(){
            	//var str = 
                $(".building_banner").stop().delay(150).fadeIn(150)
            },
            function(){
                $(".building_banner").stop().fadeOut(150)
            }
        ) */
        /*var i = $(".buliding_list li").length;           
        $(".buliding_list li").width($('.buliding_list').width() / i)*/
    </script>
    <div class="black_overlay" style="display:none">
        <div class="login_frame" style="display:none">
            <img src="${pageContext.request.contextPath}/images/cms/logo_2.png" height="60" alt=""/>
            <div class="login">
                <div class="holder">
                    <form class="sub_form" name='f' action='${pageContext.request.contextPath}/j_spring_security_check'
                          method='POST'>
                        <input class="login_input" style="margin-bottom:15px;" type="text" name="j_username"
                               placeholder="请输入用户名"/>
                        <input class="login_input" style="margin-bottom:15px;background:none;color:#333;"
                               type="password" name="j_password" placeholder="请输入密码"/>
                        <button class="btnl text" type="submit" style="width:286px">登录</button>
                    </form>
                </div>
            </div>
            <div class="close">
                <i></i>
            </div>
        </div>
    </div>
</body>
<%--<script type="text/javascript">--%>
    <%--//创建和初始化地图函数：--%>
    <%--function initMap(){--%>
        <%--createMap();//创建地图--%>
        <%--setMapEvent();//设置地图事件--%>
        <%--addMapControl();//向地图添加控件--%>
        <%--addMarker();//向地图中添加marker--%>
    <%--}--%>

    <%--//创建地图函数：--%>
    <%--function createMap(){--%>
        <%--var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图--%>
        <%--var point = new BMap.Point(121.350434,31.135446);//定义一个中心点坐标--%>
        <%--map.centerAndZoom(point,12);//设定地图的中心点和坐标并将地图显示在地图容器中--%>
        <%--window.map = map;//将map变量存储在全局--%>
    <%--}--%>

    <%--//地图事件设置函数：--%>
    <%--function setMapEvent(){--%>
        <%--map.enableDragging();//启用地图拖拽事件，默认启用(可不写)--%>
        <%--map.enableScrollWheelZoom();//启用地图滚轮放大缩小--%>
        <%--map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)--%>
        <%--map.enableKeyboard();//启用键盘上下左右键移动地图--%>
    <%--}--%>

    <%--//地图控件添加函数：--%>
    <%--function addMapControl(){--%>
        <%--//向地图中添加缩放控件--%>
        <%--var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});--%>
        <%--map.addControl(ctrl_nav);--%>
        <%--//向地图中添加缩略图控件--%>
        <%--var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});--%>
        <%--map.addControl(ctrl_ove);--%>
        <%--//向地图中添加比例尺控件--%>
        <%--var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});--%>
        <%--map.addControl(ctrl_sca);--%>
    <%--}--%>

    <%--//标注点数组--%>
    <%--var markerArr = [{title:"松江校区",content:"我的备注",point:"121.222309|31.06217",isOpen:0,icon:{w:23,h:25,l:46,t:21,x:9,lb:12}}--%>
        <%--,{title:"延安路校区",content:"我的备注",point:"121.420609|31.212456",isOpen:0,icon:{w:23,h:25,l:46,t:21,x:9,lb:12}}--%>
    <%--];--%>
    <%--//创建marker--%>
    <%--function addMarker(){--%>
        <%--for(var i=0;i<markerArr.length;i++){--%>
            <%--var json = markerArr[i];--%>
            <%--var p0 = json.point.split("|")[0];--%>
            <%--var p1 = json.point.split("|")[1];--%>
            <%--var point = new BMap.Point(p0,p1);--%>
            <%--var iconImg = createIcon(json.icon);--%>
            <%--var marker = new BMap.Marker(point,{icon:iconImg});--%>
            <%--var iw = createInfoWindow(i);--%>
            <%--var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});--%>
            <%--marker.setLabel(label);--%>
            <%--map.addOverlay(marker);--%>
            <%--label.setStyle({--%>
                <%--borderColor:"#808080",--%>
                <%--color:"#333",--%>
                <%--cursor:"pointer"--%>
            <%--});--%>

            <%--(function(){--%>
                <%--var index = i;--%>
                <%--var _iw = createInfoWindow(i);--%>
                <%--var _marker = marker;--%>
                <%--_marker.addEventListener("click",function(){--%>
                    <%--this.openInfoWindow(_iw);--%>
                <%--});--%>
                <%--_iw.addEventListener("open",function(){--%>
                    <%--_marker.getLabel().hide();--%>
                <%--})--%>
                <%--_iw.addEventListener("close",function(){--%>
                    <%--_marker.getLabel().show();--%>
                <%--})--%>
                <%--label.addEventListener("click",function(){--%>
                    <%--_marker.openInfoWindow(_iw);--%>
                <%--})--%>
                <%--if(!!json.isOpen){--%>
                    <%--label.hide();--%>
                    <%--_marker.openInfoWindow(_iw);--%>
                <%--}--%>
            <%--})()--%>
        <%--}--%>
    <%--}--%>
    <%--//创建InfoWindow--%>
    <%--function createInfoWindow(i){--%>
        <%--var json = markerArr[i];--%>
        <%--var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>"+json.content+"</div>");--%>
        <%--return iw;--%>
    <%--}--%>
    <%--//创建一个Icon--%>
    <%--function createIcon(json){--%>
        <%--var icon = new BMap.Icon("${pageContext.request.contextPath}/images/visualEngine/point.png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})--%>
        <%--return icon;--%>
    <%--}--%>

    <%--initMap();//创建和初始化地图--%>
<%--</script>--%>
</html>