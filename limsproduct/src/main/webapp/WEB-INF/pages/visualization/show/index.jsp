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
    <%--visual为新增样式，叠加覆盖在layout_header上，layout_header在其他文件使用，故不可直接为单张页面修改--%>
    <link href="${pageContext.request.contextPath}/css/visualization/floor/visual.css" rel="stylesheet" type="text/css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/visualization/floor/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/floor/drag.js"></script>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
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

        function showCampusTimetable() {
            setIframeHeight(document.getElementById('video-iframe'));

            var buildnumber;
            bulidnumber = "-1";

            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/show/changeBuildTimetable",
                data: {'buildnumber': bulidnumber,'campusNumber':${systemCampus.campusNumber}},
                success: function (data) {
                    $("#currentbuild").html(data);
                    $(".schedule_box").show()
                }
            });
        };
    </script>
</head>

<body>
<div class="visual_logo">
    <img src="${pageContext.request.contextPath}/images/system_pic/${PROJECT_NAME}_main.png" />
    <font>可视化系统</font>
</div>
<c:if test="${!empty user}">
    <div class="right_info">
        <div class="top_badges">
            <div class="badges_title">切换校区</div>
            <select onchange="changeCampus(this.options[this.selectedIndex].value);">
                <option value="">${systemCampus.campusName}</option>
                <c:forEach items="${listSystemCampus}" var="s">
                    <option value="${s.campusNumber}">${s.campusName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="top_badges user_show">
            <img src="${pageContext.request.contextPath}/images/visualization/user_head.jpg"/>
            <div class="text">${user.cname}<span>【${authority}】</span></div>
            <div class="user_detail">
                <div class="ud_pointer"></div>
                <div class="ud_top">
                    <img src="${pageContext.request.contextPath}/images/visualization/user_head.jpg"/>
                    <div class="ud_info">
                        <div>${user.cname}</div>
                        <div>${authority}</div>
                    </div>
                </div>
                <a class="ud_logout" href="${pageContext.request.contextPath}/pages/logout-front-redirect.jsp" target="_parent">退出登录</a>
            </div>
        </div>
        <script type="text/javascript">
            $(".user_show").click(
                function(){
                    $(".user_detail").slideDown();
                }
            );
            $(document).bind("click", function() {
                $('.user_detail').slideUp();
            });

            $(".user_show").click(function(event) {
                event.stopPropagation();
            });
            $(".user_detail").click(function(event) {
                event.stopPropagation();
            });
        </script>
        <%
            //前端登录标记
            session.removeAttribute("LOGINTYPE");
        %>
    </div>
</c:if>
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

<script type="text/javascript">
    //切换校区
    function changeCampus(campusNumber) {
        window.location="${pageContext.request.contextPath}/visualization/show/index?campusNumber="+campusNumber;
    }
</script>

<div class="layout_panorama_container">
    <div class="drag_container">
        <c:forEach items="${buils}" var="build" varStatus="i">
            <div id="build${build.buildNumber}" class="equipment_icon" style="left:${100*build.xCoordinate}%;top:${100*build.yCoordinate}%;" title="查看详情">
                <img src="${pageContext.request.contextPath}/images/visualization/floor/position_icon.png">
                <span class="num showMessage curpoi" id="${build.buildNumber}">${build.buildName}</span>
                <div class="build_detail">${build.buildName}<br>${content}</div>
            </div>
        </c:forEach>

        <div class="panorama "
             style="position:relative; background-color:#FFFFFF;left:0px;top:0px;filter:alpha(opacity=100);opacity:1;">
            <img src="${pageContext.request.contextPath}/${systemCampus.picUrl}" id="viewer_img">
        </div>
    </div>
</div>
<div class="schedule_box">
    <span class="schedule_close" title="关闭课表">×</span>
    <%--<div class="schedule_title">${systemCampus.campusName}所有实验楼课表</div>--%>
    <div class="schedule_limit">
        <table id="currentbuild">
            <caption>
                <font>
                    <span>${systemCampus.campusName}所有实验楼今日课表</span>
                </font>
            </caption>
            <tr>
                <th>课程名称</th>
                <th>班级</th>
                <th><spring:message code="all.trainingRoom.labroom"/></th>
                <th>上课时间</th>
                <th>教师</th>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </table>
        <script type="text/javascript">
            $(".schedule").hover(
                function () {
                    var N = $(this).attr("data-value")
                    if (N == 4) {
                        $(".schedule_box").show()
                    }
                },
                function () {
                    $(".schedule_box").hide()
                }
            )
        </script>
    </div>
</div>

</div>
<div class="quick_side">
    <div class="qs_square schedule_show">
        <div class="qs_title">今日<br/>课表</div>
        <img src="${pageContext.request.contextPath}/images/visualization/table.jpg"/>
    </div>
    <a class="qs_square" href="${sessionScope.backToCms}">
        <div class="qs_title">返回<br/>主页</div>
        <img src="${pageContext.request.contextPath}/images/visualization/home.jpg"/>
    </a>
</div>
<script type="text/javascript">
    $(".schedule_show").click(
        function(){
            showCampusTimetable();
            //$(".schedule_box").fadeToggle();
        }
    );
    $(".schedule_close").click(
        function(){
            $(".schedule_box").fadeOut();
        }
    );
</script>
<div class="roomselected" id="roomselected">
    <input type="text" style="display:none" id="buildNumber"></input>
    <div class="roomname tacenter cw mtb10" id="buildName"></div>

    <div class="cw tacenter mtb5">
        <span>
            <div class="divred l">全部</div>
        </span>
        <span class="cw">全部</span>
        <a device_detail='1' class="cw curpoi underline detail" id="device_all"></a>
        <c:if test="${showroom eq true}">
            <span class="cw">开放</span>
            <a device_detail='2' class="cw curpoi underline detail" id="device_all_open"></a>
            <span class="cw">不开放</span>
            <a device_detail='3' class="cw curpoi underline detail" id="device_all_close"></a>
            <span class="cw">待定 </span>
            <a device_detail='4' class="cw curpoi underline detail" id="device_all_unknown"></a>
            <span class="cw">电控</span>
            <a device_detail='5' class="cw curpoi underline detail" id="device_all_smart"></a>
        </c:if>
    </div>

    <div class="cw tacenter mtb5">
        <span>
            <div class="divyellow l">>40w</div>
        </span>
        <span class="cw">全部</span>
        <a device_detail='6' class="cw curpoi underline detail" id="over_40"></a>
        <c:if test="${showroom eq true}">
            <span class="cw">开放</span>
            <a device_detail='7' class="cw curpoi underline detail" id="over_40_open"></a>
            <span class="cw">不开放</span>
            <a device_detail='8' class="cw curpoi underline detail" id="over_40_close"></a>
            <span class="cw">待定 </span>
            <a device_detail='9' class="cw curpoi underline detail" id="over_40_unknown"></a>
            <span class="cw">电控</span>
            <a device_detail='10' class="cw curpoi underline detail" id="over_40_smart"></a>
        </c:if>
    </div>

    <div class="cw tacenter mtb5">
        <span>
            <div class="divblue l">10w-40w</div>
        </span>
        <span class="cw">全部</span>
        <a device_detail='11' class="cw curpoi underline detail" id="between_10and40"></a>
        <c:if test="${showroom eq true}">
            <span class="cw">开放</span>
            <a device_detail='12' class="cw curpoi underline detail" id="between_10and40_open"></a>
            <span class="cw">不开放</span>
            <a device_detail='13' class="cw curpoi underline detail" id="between_10and40_close"></a>
            <span class="cw">待定 </span>
            <a device_detail='14' class="cw curpoi underline detail" id="between_10and40_unknown"></a>
            <span class="cw">电控</span>
            <a device_detail='15' class="cw curpoi underline detail" id="between_10and40_smart"></a>
        </c:if>
    </div>

    <div class="cw tacenter mtb5">
        <span>
            <div class="divora l"><10w</div>
        </span>
        <span class="cw">全部</span>
        <a device_detail='16' class="cw curpoi underline detail" id="less_10"></a>
        <c:if test="${showroom eq true}">
            <span class="cw">开放</span>
            <a device_detail='17' class="cw curpoi underline detail" id="less_10_open"></a>
            <span class="cw">不开放</span>
            <a device_detail='18' class="cw curpoi underline detail" id="less_10_close"></a>
            <span class="cw">待定 </span>
            <a device_detail='19' class="cw curpoi underline detail" id="less_10_unknown"></a>
            <span class="cw">电控</span>
            <a device_detail='20' class="cw curpoi underline detail" id="less_10_smart"></a>
        </c:if>
    </div>
    <div class="mtb5 tacenter" style="margin-top:10px;">
        <a class="choobtn curpoi cancelbtn " id="enterBuild" href='' target="_blank">进入楼宇</a>
        <a class="choobtn curpoi cancelbtn">取消</a>
    </div>
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
                                document.getElementById(key).innerHTML = values;
                                $("#" + key).val("" + values);
                            });
                        }
                    });

                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/visualization/show/changeBuildTimetable",
                        data: {'buildnumber': bulidnumber,'campusNumber':${systemCampus.campusNumber}},
                        success: function (data) {
                            $("#currentbuild").html(data);
                            $(".schedule_box").show()
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
        });
        $(".cancelDetail").click(function () {
        })
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
</div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select': {search_contains: true},
        '.chzn-select-deselect': {allow_single_deselect: true},
        '.chzn-select-no-single': {disable_search_threshold: 10},
        '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>

</html>