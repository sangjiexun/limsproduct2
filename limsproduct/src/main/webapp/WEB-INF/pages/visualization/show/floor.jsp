<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false"
         contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
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

    <META content=IE=Edge,chrome=1 http-equiv=X-UA-Compatible>

    <META name=renderer content=webkit>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

    <link href="${pageContext.request.contextPath}/css/visualization/floor/style.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/visualization/floor/table.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/visualization/floor/layout_header.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/js/lab_video/video-js.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/lab_video/video.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/floor/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/floor/jquery.nav.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/floor/jquery.rotate.min.js"></script>
    <script type="text/javascript">
        function showlabroom(id) {
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/labRoom/openVideoForV',
                data: {'roomId': id},
                success: function (data) {
                    window.location.href = data;
                }
            });
        }

        function openVideo2(id) {
            var url = "${pageContext.request.contextPath}/labRoom/appointment/openVideo2?id=" + id;
            // window.location.href=url;  // 本窗口
            window.open(url); // 新窗口
        }
    </script>
    <script type="text/javascript">
        function device_lend() {
            var room_id = $("#labRoomListForSelect").val();
            var url = "${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1&room_id=null";
            if(room_id>0) {
                url = "${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1&room_id="+room_id;
            }

            window.open(url);
        }
    </script>

    <script type="text/javascript">
        function changeDeviceByName() {
            var name = $("#DeviceName").val();
            var id = $("#DeviceRoomId").val();
            var lend = 0;
            var appoint = 0;
            if ($("#ifLend").is(':checked')) {
                lend = 1
            }
            if ($("#ifAppointment").is(':checked')) {
                appoint = 1;
            }
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/visualization/show/changeRoomDeviceSearched',
                data: {'id': id, 'lend': lend, 'appoint': appoint, 'name': name},
                success: function (data) {
                    $("#deviceTbody tr:gt(0)").empty();
                    $("#deviceTbody tr").not(':eq(0)').empty()
                    $("#deviceTbody").append(data);
                }
            });
        }

        $(function () {
            //一进来判断显示视频
            changeRoomMovie($("#labRoomListForSelect").val());
            //加载后调整视频尺寸的方法
            changSize();
            //浏览器窗口改变时触发
            $(window).resize(function () {
                changSize();
            })

            function changSize() {
                $("#my-video").width($(window).width());
                $("#my-video").height($(window).height());

                var Height = $(window).height();
                var Width = $(window).width()
            };

            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/show/changeRoomTimeTableMap",
                data: {'id': $("#labRoomListForSelect").val()},
                dataType: 'json',
                success: function (data) {
                    $.each(data, function (key, values) {
                        document.getElementById(key).innerHTML = values;
                        $("#" + key).val("" + values);
                    });
                },
                error: function () {
                    console.log("暂无该房间当前课表！");
                }
            });

        })
        setInterval("TT()", 10);

        function TT() {
            var Height = $(window).height();
            var Width = $(window).width()
            /* $(".room_panorama").height(Height); */
            var s = $(".room_list li").length;
            var a = 900 / (s - 1);
            for (var i = 0; i < s; i++) {
                var d = a * i
                $(".room_list li").eq(i).css("left", d);

            }
        }
    </script>
    <script type="text/javascript">
        //楼层
        function changeFloor(buildNumber, floor, floorName) {
            //获取楼层li
            var floorLis = document.getElementById('floorUl').getElementsByTagName('li');
            //切换楼层的房间列表
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/visualization/show/changeFloor',
                data: {'buildNumber': buildNumber, 'floor': floor},
                success: function (data) {
                    $("#room_list").html(data.substring(0, data.indexOf("%")));
                    //右边点击自动更改左边楼层
                    $(".floor_nav_floor").val(floor);
                    //左边点击自动更改右边楼层
                    for (var i = 1; i <= floorLis.length; i++) {
                        // $("#floor" + i).attr("class", "");
                        $("a[name=floorChange]").attr("class", "");
                    }
                    $("#floor" + floor).attr("class", "selected_floor");

                    $("#showfloor").html("");
                    $("#showfloor").append(floorName);
                    $("#showfloor1").hide();
                    $("#showfloor").show();

                    changeRoom(data.substring(data.indexOf("%") + 1));
                }
            });

            $.ajax({
                type: 'GET',
                url: '${pageContext.request.contextPath}/visualization/show/changeSystemFloorPic',
                data: {'buildNumber': buildNumber, 'floor': floor},
                dataType:"text",
                success: function (data) {
                    $(".floor_img").attr("src", "${pageContext.request.contextPath}/" + data);
                }, error: function (data) {
                    console.log("请求失败2");
                }
            });
        }


        //切换实训室
        //切换实训室展示信息
        function changeRoom(id) {
            if(document.getElementById("positionAppoint") != null){
                document.getElementById("positionAppoint").setAttribute("href", "${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList1?currpage=1&selectedRoomId="+ id);
            }
            document.getElementById("labAppoint").setAttribute("href", "${pageContext.request.contextPath}/LabRoomReservation/labRoomList1?currpage=1&selectedRoomId=" + id);
            document.getElementById("deviceAppoint").setAttribute("href", "${pageContext.request.contextPath}/device/listLabRoomDevice?page=1&isReservation=1&isOrder=1&selectedRoomId=" + id);
            if(document.getElementById("deviceLend") != null){
                document.getElementById("deviceLend").setAttribute("href", "${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1&selectedRoomId=" + id);
            }
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/visualization/show/changeRoomNew',
                data: {'id': id},
                success: function (data) {
                    $("#room_banner").html(data[0]);
                    $("#deviceIcon").empty();
                    $.each(data[1],function (index,value) {
                        $("#deviceIcon").append($("<div id="+value.id+" class=\"equipment_icon\"" +
                            "style=\"left:"+100*value.xCoordinate+"%;top:"+100*value.yCoordinate+"%;\" title=\"查看详情\">" +
                            "<a href='javascript:void(0)' id=\"lookdevice\" onmouseup=\"lookDevice("+value.id+")\">"+
                            "<img src=\"${pageContext.request.contextPath}/images/visualization/floor/icon.png\"></a>" +
                            "</div>"));
                    });
                }
            });
            // 课表
            document.getElementById('iframe').contentWindow.location.reload(true);

            //切换实训室的视频
            changeRoomMovie(id);

            //切换实训室全景图
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/visualization/show/changeRoomImage',
                data: {'id': id, 'type': 3},
                success: function (data) {
                    //var str = "<img src='${pageContext.request.contextPath}/"+data+"' >";
                    //document.getElementById('panorama').innerHTML = str;
                    if (data != null && data != "") {
                        $(".demo_photo").attr("src", "${pageContext.request.contextPath}/" + data);
                        $(".demo_photo").show();
                        $("#btnchangeimg").val("切换实时画面")
                    } else {
                        console.log("这个实训室没有全景图");
                    }
                }, error: function (data) {
                    console.log("请求失败2");
                }
            });
            //切换实训室详细信息map
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/show/changeRoomMap",
                data: {'id': id},
                dataType: 'json',
                success: function (data) {
                    $.each(data, function (key, values) {
                        if (key == "isUsed" || key == "appointment") {
                            if (values == 0) {
                                $("#" + key + "2").attr("checked", "checked");
                                $("#label" + key + "2").css("background-image", "url('${pageContext.request.contextPath}/images/visualization/floor/radio.png')");
                                $("#" + key + "1").removeAttr("checked");
                                $("#label" + key + "1").css("background-image", "url('${pageContext.request.contextPath}/images/visualization/floor/unradio.png')");
                            } else {
                                $("#" + key + "1").attr("checked", "checked");
                                $("#label" + key + "1").css("background-image", "url('${pageContext.request.contextPath}/images/visualization/floor/radio.png')");
                                $("#" + key + "2").removeAttr("checked");
                                $("#label" + key + "2").css("background-image", "url('${pageContext.request.contextPath}/images/visualization/floor/unradio.png')");
                            }

                        } else {
                            document.getElementById(key).innerHTML = values;
                            $("#" + key).val("" + values);
                        }
                    });
                },
                error: function () {
                    console.log("请求失败-暂无该房间信息！");
                }
            });

            $("#DeviceRoomId").val(id);

            chageroomAjaxlalala(id);

            //切换实训室 当前课表详细信息map
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/show/changeRoomTimeTableMap",
                data: {'id': id},
                dataType: 'json',
                success: function (data) {

                    $.each(data, function (key, values) {
                        document.getElementById(key).innerHTML = values;
                        $("#" + key).val("" + values);

                    });
                },
                error: function () {
                    console.log("请求失败-暂无该房间当前课表！");
                }
            });

            //切换实训室 当前视频硬件列表
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/show/changeRoomAgentList",
                data: {'id': id},
                dataType: 'text',
                success: function (data) {
                    $("#selectAgent").html(data);
                },
                error: function () {
                    console.log("暂无该房间视频信息！");
                }
            });

            //切换实训室 软件详细信息map
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/show/changeRoomSoftwareMap",
                data: {'id': id},
                dataType: 'text',
                success: function (data) {
                    /* alert(data+"333"); */
                    $("#softwareDetail tr:gt(0)").empty();
                    $("#softwareDetail tr").not(':eq(0)').empty()
                    $("#softwareDetail").append(data);
                },
                error: function () {
                    console.log("暂无该房间软件信息！");
                }
            });

        }
        function chageroomAjaxlalala(id) {
            //切换实训室 设备 详细信息map
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/show/changeRoomDeviceMap",
                data: {'id': id},
                dataType: 'text',
                success: function (data) {
                    $("#deviceTbody tr:gt(0)").empty();

                    $("#deviceTbody tr").not(':eq(0)').empty()
                    $("#deviceTbody").append(data);
                },
                error: function () {
                    console.log("暂无该房间设备信息！");
                }
            });

        }
        function changeRoomMovie(id) {
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/show/changeRoomMovie",
                data: {'id': id},
                dataType: 'text',//json or text
                success: function (data) {
                    console.log("【" + data + "】视频data");
                    //有视频就设置只有视频显示，其他图片隐藏。
                    if (data == "") {
                        //没有视频
                        $("#my-video").hide();
                    } else {
                        //videojs里的改src的方法
                        videojs("my-video", {}).ready(function () {
                            var myPlayer = this;
                            myPlayer.src(data);
                            myPlayer.play();
                        });
                        $("#my-video").show();
                        $(".demo_photo").hide();
                    }
                    $("#btnchangeimg").val("切换全景图片");
                }, error: function (data) {
                    console.log(data);
                }
            });
        }
        $(function () {
            var labroomId = "${labRoom.id }";
            chageroomAjaxlalala (labroomId);
        })
        function changeRoomMovie2(id, agentId) {
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/show/changeRoomMovie2",
                data: {'id': id, 'agentId': agentId},
                dataType: 'text',//json or text
                success: function (data) {
                    console.log("【" + data + "】视频data");
                    //有视频就设置只有视频显示，其他图片隐藏。
                    if (data == "") {
                        //没有视频
                        $("#my-video").hide();
                    } else {
                        //videojs里的改src的方法
                        videojs("my-video", {}).ready(function () {
                            var myPlayer = this;
                            myPlayer.src(data);
                            myPlayer.play();
                        });
                        $("#my-video").show();
                        $(".demo_photo").hide();
                    }
                    $("#btnchangeimg").val("切换全景图片");
                }, error: function (data) {
                    console.log(data);
                }
            });
        }

        function roomDetails() {
            $('html,body').animate({
                scrollTop: $('.floor_info').offset().top
            }, 200);

        }

        //切换楼宇
        function changeBuilding(buildNumber) {
            window.location = '${pageContext.request.contextPath}/visualization/show/floor?buildNumber=' + buildNumber;
        }

        function changeVideoPic(obj) {
            if (obj.value == "切换实时画面") {
                $("#my-video").show();
                $(".demo_photo").hide();
                obj.value = "切换全景图片";
            } else {
                $("#my-video").hide();
                $(".demo_photo").show();
                obj.value = "切换实时画面";
            }
        }

        //查看设备
        function lookDevice(id){
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/show/lookDeviceNew",
                data: {'id':id},
                dataType:'json',
                success:function(data){
                    $.each(data,function(key,values){
                        //alert(key+"="+values);
                        if(key=="qRCodeUrl"){
                            $("#qRCodeUrl").attr("src", $("#contextPath").val()+values);
                        }
                        else if(key=="dimensionalCode"){
                            $("#dimensionalCode").attr("src", $("#contextPath").val()+"/"+values);

                        }else if(key =="labRoomDeviceId"){
                            $("#dimensionalCode").attr("onclick", "turnDeviceQR("+values+")");
                        }
                        else if(key =="videoValue"){
                            $("#qRCodeUrl").attr("onclick", "turnVideoQR("+values+")");
                        }
                        else{
                            if(values!=null){
                                //document.getEleme ntById(key).innerHTML=values;
                                $("#"+key).text(""+values);
                                $("#"+key).val(""+values);
                            }
                        }
                    });
                },
                error:function(){
                    //alert("加载课表失败!");
                    // alert(id);
                }
            });
            $(".equipment_inforamtion").show()
        }
        //关闭设备窗口
        function cancel(){
            $(".equipment_inforamtion").hide()
        }
        function turnDeviceQR(id){
            window.open("${pageContext.request.contextPath}/cmsshow/showDevice?id="+id);
        }
        function turnVideoQR(folderId,type){
            window.location.href="${pageContext.request.contextPath}/tcoursesite/videoLook?folderId="+folderId+"&moduleType="+type;
        }
    </script>

    <style type="text/css">
        .lpc_limit {
            position: relative;
            width: 100%;
            height: 0;
            padding: 27% 0 0 0;
        }

        .layout_panorama_container {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: #2c3136;
            overflow: hidden;
            box-sizing: border-box;
            padding: 10px;
            overflow:visible;
        }

        .banner_left {
            float: left;
            width: 15%;
            height: 100%;
        }

        .banner_left > a {
            display: block;
            background: #3e454c;
            height: 20%;
            width: 100%;
            text-align: center;
        }

        .banner_left > a img {
            height: 65%;
            position: relative;
            top: 17%;
            left: 4%;
        }

        .banner_left ul {
            width: 100%;
            height: 60%;
            overflow: hidden;
        }

        .banner_left li {
            float: left;
            width: 50%;
            height: 50%;
            box-sizing: border-box;
            padding: 11px 5px 0;
        }

        .banner_left li a {
            display: block;
            width: 100%;
            height: 100%;
            box-sizing: border-box;
            background: rgba(255, 255, 255, 0.2);
            border: 1px solid rgba(255, 255, 255, 0.1);
            position: relative;
            text-align: center;
            transition: all 0.3s;
        }

        .banner_left li a:hover {
            background: rgba(255, 255, 255, 0.3);
            border: 1px solid #5d77d6;
        }

        .banner_left li a:hover div {
            color: #fff;
        }

        .banner_left li a img {
            position: absolute;
            width: auto;
            height: 81%;
            box-sizing: border-box;
            padding: 19% 0 18px;
            left: 0;
            right: 0;
            top: 0;
            margin: auto;
        }

        .banner_left li a div {
            position: absolute;
            bottom: 13%;
            width: 100%;
            font-size: 12px;
            color: rgba(255, 255, 255, 0.6);
            line-height: 16px;
            text-decoretion: none;
            text-align: center;

        }

        .banner_left ul span {
            color: white;
            margin-left: 4px;
        }

        .lpc_btm {
            box-sizing: border-box;
            width: 100%;
            height: 20%;
            position: relative;
        }

        .lpc_btm div {
            font-size: 13px;
            text-align: center;
            margin: 0 0 9px 0;
            padding: 10px 2px;
        }

        .lpc_btm div span {
            background: #2c3136;
            color: rgba(255, 255, 255, 0.8);
            padding: 0 10px;
        }

        .lpc_btm div hr {
            border: none;
            border-bottom: 1px solid rgba(255, 255, 255, 0.5);
            margin: -9px 0 0;
        }

        .lpc_btm lable {
            position: absolute;
            bottom: 0;
            box-sizing: border-box;
            width: 100%;
            height: 100%;
            padding: 38px 0 0;
        }

        .lpc_btm select {
            box-sizing: border-box;
            font-size: 12px;
            letter-spacing: 0.5px;
            width: 100%!important;
            height: 100%;
            padding: 5px 5px;
            background: rgba(255, 255, 255, 0.75);
            border: 1px solid rgba(255, 255, 255, 0.1);
            color: #353535;
        }

        .div1, .div2 {
            width: 100%;
            margin: 0 0 60px;
            overflow: hidden;
        }

        #div2 {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            z-index: 9;
            box-sizing: border-box;
            padding: 50px;
        }

        #div2 iframe {
            width: 100%;
            height: 100%;
            background: #fff;
            border: none;
        }

        .close_btn {
            position: absolute;
            right: 0;
            top: 0;
            font-size: 35px;
            width: 50px;
            text-align: center;
            line-height: 50px;
            font-weight: bold;
            color: #eee;
            text-shadow: 0 0 1px #000;
            z-index: 10;
            transition: all 0.3s;
        }

        .close_btn:hover {
            color: #ff0000;
            cursor: pointer;
        }

        .intro1 {
            width: 58%;
            float: left;
        }

        .intro2 {
            box-sizing: border-box;
            width: 42%;
            display: inline-block;
            float: right;
            padding-left: 30px;
        }

        .layout_link_container {
            clear: both;
        }

        #in1, #in2 {
            width: 17px;
            height: 17px;
            margin: 0 5px 0 0;
            position: relative;
            top: 1px;
        }

        #in1:hover,
        #in2:hover {
            color: #484848 !important;
            cursor: pointer;
        }

        #btnchangeimg {
            position: absolute;
            right: 0;
            top: 0;
            background: #2c3136;
            border-radius: 0;
            font-size: 14px;
            color: #fff;
            padding: 5px 10px;
            height: auto;
            letter-spacing: 1px;
            z-index: 9;
            transition: all 0.3s;
        }

        #btnchangeimg:hover {
            background: #fff;
            color: #2c3136;
        }

        .room_panorama {
            float: left;
            width: 70%;
            height: 100%;
            box-sizing: border-box;
            padding: 0 10px;
        }

        .rp_limit {
            width: 100%;
            height: 100%;
            background: #6b6b6b;
            position: relative;
        }

        .room_panorama img {
            height: 100%;
            width: 100%;
            margin-left: 0;
        }

        img {
            border: none;
        }

        .room_list_bg {
            filter: progid:DXImageTransform.Microsoft.Alpha(opacity=20);
            *display: none;
            _display: none;
        }

        .banner_room {
            *background: #aaa; /*IE7 背景变灰色*/
            _background: #aaa; /*IE6 背景变灰色*/
        }

        .room_list li {
            *top: 4px;
            _top: 4px;
            *position: static;
            _position: static;
            *float: left;
            _float: left;
        }

        #replaceMovie {
            width: 100%;
            height: 100%;
            text-align: center;
            position: relative;
        }

        #my-video {
            width: auto !important;
            max-width: 100%;
            height: 100% !important;
            margin: auto;
            background: #949494;
            z-index: -100;
            /* 设置默认，ajax设置显示 */
            display: none;
        }

        .rec {
            position: absolute;
            left: 2%;
            top: 3%;
            font-size: 17px;
            font-weight: bold;
            color: #ff0000;
            text-shadow: 0 0 1px rgba(0, 0, 0, 0.2);
            letter-spacing: 1px;
        }

        .rec font {
            font-size: 17px;
            font-weight: bold;
            color: #ff0000;
            text-shadow: 0 0 1px rgba(0, 0, 0, 0.4);
            margin: 0 3px 0 0;
        }

        .autoSize {
            /* position:absolute;
                       min-width: 100%;
                       min-height: 100%;
                       width: auto;
                       height: auto; */

        }

        .bottom {
            z-index: -200;
        }

        .layout_nav_slidebar {
            position: relative;
            float: right;
            width: 15%;
            height: 100%;
            right: 0;
            top: 0;
            z-index: 1;
            overflow:visible;
        }

        .position_label {
            width: 100%;
            height: 40%;
            margin-bottom: 0;
            background: none;
            overflow: visible;
            position: relative;
            box-sizing: border-box;
            padding: 0 0 10px;
        }

        #school {
            width: 100%;
            height: 100%;
        }

        #building_position_icon {
            /*坐标定死暂时隐藏*/
            display: none;
            top: 69px;
            right: 55px;
            width: 14px;
        }

        .fp_limit {
            width: 100%;
            height: 100%;
            background: none;
            position: relative;
        }

        .floor_position img {
            position: absolute;
            left: 0;
            bottom: 0;
            width: 100%;
            max-height: 100%;
            box-sizing: border-box;
            padding: 0 73px 0 0;
        }

        .floor_nav{
            text-align: right;
            width: 100%;
        }

        .floor_nav select {
            background: none;
            color: #fff;
            margin: 0 0 10px;
        }

        .floor_nav_floor {
            display: inline-block;
        }

        .showfloor{
            display: inline-block;
            font-size: 24px;
            color: #fff;
            padding:0 5px 0 20px
        }

        .showfloor_box:hover .showfloor{
            color:#ffa341;
            cursor:pointer;
        }

        #floorUl{
            position: absolute;
            right: 0;
            top: 150%;
            white-space: nowrap;
            width: 100%;
            height: 0;
            background: rgba(90, 90, 90, 0.9);
            z-index: 6;
            opacity: 0;
            overflow: hidden;
            transition: 0.4s;
        }

        .showfloor_box:hover #floorUl{
            height: auto;
            top:100%;
            opacity: 1;
            transition: 0.4s;
        }

        .floor_nav_floor li {
            text-align: center;
            float: left;
            width: 33.3%;
            line-height: 33px;
        }

        .floor_nav_floor li a {
            display: block;
            font-size: 14px;
            padding-right: 0;
            color: #d4d4d4;
        }

        .floor_nav_floor li a:hover{
            color:#ffa341;
            background: rgba(255,255,255,0.2);
        }

        .floor_info_container {
            width: 100%;
            position: relative;
            overflow: hidden;
            margin: 40px 0 0;
        }

        #nav {
            float: left;
            position: relative;
            left: 0;
            top: 0px;
            width: 15%;
            padding: 0 0px 0 10px;
        }

        #nav li {
            width: 100%;
            margin: 0 0 17px 0;
        }

        #nav a {
            display: block;
            box-sizing: border-box;
            padding: 12px 0;
            color: #6d6d7d;
            text-align: center;
            border: 1px solid #e4e4e4;
            background-color: #ededed;
            text-decoration: none;
        }

        .wrap {
            float: right;
            width: 85%;
            box-sizing: border-box;
            padding: 0 40px 0 30px;
            margin: 0 -10px 0 0;
        }

        .layout_intro_tit {
            display: inline;
            height: 24px;
            line-height: 22px;
            margin: 0 20px 0 0;
            padding: 0 0 0 10px;
            border-left: 5px solid #6197ea;
            letter-spacing: 0.5px;
            color: #484848;
            transition: all 0.3s;
        }

        .not_chosen {
            color: #a0a0a0 !important;
            border: none !important;
        }

        .data_container table td, .data_container table th {
            border-top: 1px solid #e4e5e7;
            border-bottom: 1px solid #e4e5e7;
            padding: 4px 8px;
        }

        .data_container table th {
            white-space: nowrap;
            letter-spacing: 0.5px;
        }

        .data_container table tr:nth-child(odd) {
            background: #f9f9f9;
        }

        .equip_search {
            margin: 20px 0 0;
            font-size: 12px;
        }

        .equip_search input[type="text"] {
            font-size: 12px;
            border: 1px solid #bdbdbd;
            border-radius: 3px;
            padding: 5px 5px;
        }

        .equip_search input[type="checkbox"] {
            margin: 0 0 0 10px;
        }

        .equip_search input[type="button"] {
            width: auto;
            height: auto;
            padding: 3px 6px;
            border: none;
            background: #6297ea;
            color: #fff;
            letter-spacing: 2px;
            text-indent: 1px;
            margin: 0 0 0 10px;
        }

        .title_box {
            margin: 0 0 25px;
        }

        .clear{
            clear:both;
        }
        .equipment_icon {
            margin-top: -30px;
            margin-left: -17px;
        }
    </style>
</head>

<body style="" class="selected_floor">
<div class="lpc_limit">
    <div class="layout_panorama_container">
        <div class="banner_left">
            <a href="${pageContext.request.contextPath}/visualization/show/index?campusNumber=${systemCampus}">
                <img src="${pageContext.request.contextPath}/images/system_pic/${proj_name}_logo.png"/>
            </a>
            <ul>
                <c:if test="${jobReservation eq 'true'}">
                    <li>
                        <a id="positionAppoint"
                           href="${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList?currpage=1"
                           target="_blank">
                            <img src="${pageContext.request.contextPath}/images/icon1.png"/>
                            <div>工位预约</div>
                        </a>
                    </li>
                </c:if>
                <li>
                        <a id="labAppoint"
                           onclick="targetUrl('${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1')"
                           target="_blank">
                        <img src="${pageContext.request.contextPath}/images/icon2.png"/>
                        <div><spring:message code="all.trainingRoom.labroom"/>预约</div>
                    </a>
                </li>
                <li>
                    <a id="deviceAppoint"
                       href="${pageContext.request.contextPath}/device/listLabRoomDevice?page=1&isReservation=1&isOrder=1"
                       target="_blank">
                        <img src="${pageContext.request.contextPath}/images/icon4.png"/>
                        <div>大仪设备预约</div>
                    </a>
                </li>
                <c:if test="${deviceLend eq 'true'}">
                    <li>
                        <a id="deviceLend" onclick="device_lend()" target="_blank">
                            <img src="${pageContext.request.contextPath}/images/icon3.png"/>
                            <div>设备借用</div>
                        </a>
                    </li>
                </c:if>
            </ul>
            <div class="lpc_btm">
                <sec:authorize ifNotGranted="ROLE_STUDENT">
                <div>
                    <span>实时视频</span>
                    <hr/>
                </div>
                <lable id="selectAgent">
                    <select onchange="changeRoomMovie2(this.options[this.options.selectedIndex].value.split('/')[0] , this.options[this.options.selectedIndex].value.split('/')[1])">
                        <c:forEach items="${agentList }" var="agent" varStatus="i">
                            <option value="${labRoom.id}/${agent.id }">监控${i.index+1 }</option>
                        </c:forEach>
                    </select>
                </lable>
                </sec:authorize>
            </div>
        </div>
        <div id="panorama" class="room_panorama ">
            <div class="rp_limit">
                <div id ="deviceIcon">
                    <c:forEach items="${labRoomDevices}" var="device" varStatus="i">
                        <div id="device${device.id}" class="equipment_icon"
                             style="left:${100*device.xCoordinate}%;top:${100*device.yCoordinate}%;" title="查看详情">
                            <a href='javascript:void(0)' id="lookdevice" onmouseup="lookDevice(${device.id})">
                                <img src="${pageContext.request.contextPath}/images/visualization/floor/icon.png">
                            </a>
                        </div>
                    </c:forEach>
                </div>
                <!-- 实验室图片 -->
                <sec:authorize ifNotGranted="${noREC}">
                <input type="button" onclick="changeVideoPic(this)" id="btnchangeimg" value="切换实时画面"/>
                </sec:authorize>
                <c:if test="${documentUrl ne ''}">
                    <img src="${pageContext.request.contextPath}/${documentUrl}" class="demo_photo autoSize align"/>
                </c:if>
                <c:if test="${documentUrl eq '' || documentUrl eq null}">
                    <img src="" class="demo_photo autoSize align"/>
                </c:if>

                <!-- 视频 -->
                <div id="replaceMovie">
                    <sec:authorize ifNotGranted="${noREC}">
                        <div class="rec"><font>●</font>REC</div>
                        <video id="my-video" loop muted poster=""><!-- polina.jpg -->
                            <source src="http://${serverIp}:8080/live/${lastFour}.m3u8"
                                    type="application/x-mpegURL">
                            <source src
                                    type="rtmp/flv">
                        </video>
                    </sec:authorize>
                </div>
                <!-- 视频结束  -->
            </div>
        </div>
        <!-- 窗口 -->
        <div class="equipment_inforamtion">
            <img class="close" src="${pageContext.request.contextPath}/images/visualization/floor/msg_close.png"  onclick="cancel()">
            <form:form id="myForm" action="${pageContext.request.contextPath}/visualization/saveDevice" method="POST" modelAttribute="labRoomDevice">
                <div class="">
                    <h2 class="equipment_name" id="deviceName1"></h2>
                </div>
                <table class="equipment_info">
                    <tr style="display:none">
                        <th>鼠标X轴:</th>
                        <td><form:input id="xCoordinate" type="text" path="xCoordinate"/></td>
                        <th>鼠标Y轴:</th>
                        <td><form:input id="yCoordinate" type="text" path="yCoordinate"/></td>
                    </tr>
                    <tr>
                        <th>设备编号:</th>
                        <td><form:input id="deviceNumber" type="text" path="schoolDevice.deviceNumber" readonly="true"/></td>
                        <th>生产厂家:</th>
                        <td><form:input id="deviceSupplier" type="text" path="schoolDevice.manufacturer" readonly="true" /></td>
                       <td><form:input id="deviceFormat" type="text" path="schoolDevice.deviceNumber"/></td>     -->
                    </tr>
                    <tr>
                        <th>仪器型号:</th>
                        <td><form:input id="devicePattern" type="text" path="schoolDevice.deviceNumber" readonly="true"/></td>

                        <th>所属学院:</th>
                        <td><form:input id="academyName" type="text" path="schoolDevice.schoolAcademy.academyName" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th>设备信息:</th>
                        <td colspan="4">
                            <img  id="dimensionalCode" src="${pageContext.request.contextPath}/images/visualization/floor/msg_close.png" style="wdith:100px;height:100px;margin-left:100px;" onclick="turnDeviceQR()" >
                        </td>
                        <th>知识视频:</th>
                        <td colspan="4">
                            <img  id="qRCodeUrl" src="${pageContext.request.contextPath}/images/visualization/floor/msg_close.png" style="wdith:100px;height:100px;margin-left:100px;" >
                        </td>
                    </tr>
                    <tr style="display:none;">
                        <td>实验设备id</td>
                        <td><form:input id="labRoomDeviceId" type="text" path="id" /></td>
                        <th>实验室编号</th>
                        <td><form:input id="labRoomId" type="text" path="labRoom.id" /></td>
                    </tr>
                </table>
            </form:form>
        </div>
        <div class="layout_nav_slidebar">
            <div class="building_position position_label">
                <img
                        src="${pageContext.request.contextPath}/${systemCampus.picUrl}"
                        id="school"> <img
                    src="${pageContext.request.contextPath}/images/visualization/floor/icon.png"
                    id="building_position_icon" style="left:${systemBuild.xCoordinate}">
                <script>
                    var L =
                    ${systemBuild.xCoordinate}
                    var T =
                    ${systemBuild.yCoordinate}
                    var l = L * 100 + "%"
                    var t = T * 100 + "%"
                    $("#building_position_icon").css({
                        left: l,
                        top: t
                    })
                </script>

            </div>
            <div class="floor_position position_label">
                <div class="fp_limit">
                    <img src="${pageContext.request.contextPath}/${floorPic.documentUrl}" usemap="#floor_map" id="floor_img" class="floor_img">
                    <div class="floor_nav">
                        <select onchange="changeBuilding(this.options[this.selectedIndex].value);">
                            <c:forEach items="${systemBuilds}" var="build" varStatus="i">
                                <option value="${build.buildNumber}"
                                        <c:if test='${buildNumber == build.buildNumber}'> selected='selected'
                                        </c:if>>${build.buildName}</option>
                            </c:forEach>
                        </select>
                        <div class="clear"></div>
                        <!-- 楼层显示 -->
                        <ul class="floor_nav_floor showfloor_box">
                            <div class="showfloor" id="showfloor1" title="选择楼层">${floor_name}</div>
                            <div class="showfloor" id="showfloor" title="选择楼层" style="display: none"></div>
                            <ul id="floorUl">
                                <c:forEach items="${floorPics}" varStatus="i" var="curr">
                                    <li><a id="floor${curr.floorNo}" name="floorChange" href='javascript:void(0)'
                                           onclick="changeFloor('${buildNumber}',${curr.floorNo}, '${curr.floorName}')">${curr.floorName}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="room_list" class="lpc_btm">
                <div>
                    <span>切换<spring:message code="all.trainingRoom.labroom"/></span>
                    <hr>
                </div>
                <lable>
                    <select id="labRoomListForSelect" onchange="changeRoom(this.options[this.selectedIndex].value);">
                        <c:forEach items="${labRooms}" var="labRoom" varStatus="i">
                            <option value="${labRoom.id}">${labRoom.labRoomName}</option>
                            <p style="display:none;">${labRoom.id}</p>
                        </c:forEach>
                    </select>
                </lable>
            </div>
        </div>
    </div>
</div>

<div class="floor_info_container">
    <ul id="nav">
        <li><a href="#intro"><spring:message code="all.trainingRoom.labroom"/>详情</a>
        </li>
        <li><a class="exception" href="#top">返回顶部</a>
        </li>

    </ul>
    <div class="wrap">
        <div class="div1">
            <div class="intro1" class="dowebok" id="intro">
                <div class="title_box">
                    <h2 class="layout_intro_tit"><spring:message code="all.trainingRoom.labroom"/>详情</h2>
                </div>
                <div class="data_container">
                    <form:form name="form" action="" method="post" modelAttribute="labRoom">
                        <form:hidden path="id"/>
                    </form:form>
                    <table>
                        <tr>
                            <th style="width:10%"><spring:message code="all.trainingRoom.labroom"/>名称</th>
                            <td id="labRoomName">&nbsp;&nbsp;${labRoom.labRoomName}</td>
                            <th style="width:10%"><spring:message code="all.trainingRoom.labroom"/>地点</th>
                            <td id="adress">${labRoom.systemBuild.systemCampus.campusName}<!-- 校区 -->
                                ${labRoom.systemBuild.buildName} <!-- 楼栋 -->
                                ${labRoom.systemRoom.roomName}(${labRoom.systemRoom.roomNo})
                            </td>
                            <th style="width:10%">管理机构</th>
                            <td id="departmentNumber">${labRoom.systemRoom.departmentNumber}</td>
                        </tr>
                        <tr>
                            <th><spring:message code="all.trainingRoom.labroom"/>编号</th>
                            <td id="labRoomNumber">${labRoom.labRoomNumber}</td>
                            <th><spring:message code="all.trainingRoom.labroom"/>类型</th>
                            <td id="CLabRoomType">${labRoom.CDictionaryByLabRoom.CName}</td>
                            <th><spring:message code="all.trainingRoom.labroom"/>容量</th>
                            <td id="labRoomCapacity">${labRoom.labRoomCapacity}人</td>
                        </tr>
                        <tr>
                            <th>是否可用</th>
                            <td id="CActiveByLabRoomActive" style="white-space:nowrap;"><input name="isUsed"
                                                                                               type="radio"
                                                                                               disabled="true"
                                                                                               id="isUsed1" value="1"
                                                                                               <c:if test="${labRoom.labRoomActive == 1}">checked="checked"</c:if>><label
                                    for="isUsed1">是</label> &nbsp; <input name="isUsed"
                                                                          type="radio" disabled="true" id="isUsed2"
                                                                          value="0"
                                                                          <c:if test="${labRoom.labRoomActive == 0}">checked="checked"</c:if>><label
                                    for="isUsed2">否</label></td>
                            <th>是否可预约</th>
                            <td id="CActiveByLabRoomReservation" style="white-space:nowrap;"><input
                                    name="isappointment" type="radio" disabled="true"
                                    id="appointment1" value="1"
                                    <c:if test="${labRoom.labRoomReservation == 1}">checked="checked"</c:if>><label
                                    for="appointment1">是</label> &nbsp; <input
                                    name="isappointment" type="radio" disabled="true"
                                    id="appointment2" value="0"
                                    <c:if test="${labRoom.labRoomReservation == 0}">checked="checked"</c:if>><label
                                    for="appointment2">否</label></td>
                            <th>使用面积</th>
                            <td id="labRoomArea">${labRoom.labRoomArea}平方米</td>
                        </tr>
                        <tr>
                            <th>实验室管理员</th>
                            <td id="labRoomAdmin">
                                <c:forEach items="${labRoom.labRoomAdmins}" var="la">
                                    <c:if test="${la.typeId == 1}">
                                        ${la.user.cname}<br/>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <th>总资产</th>
                            <td id="totalAssets">${totalAssets}元</td>
                        </tr>
                        <tr>
                            <th><spring:message code="all.trainingRoom.labroom"/>简介</th>
                            <td id="labRoomIntroduction" colspan="5">
                                ${labRoom.labRoomIntroduction}</td>
                        </tr>
                    </table>
                </div>

            </div>
            <div class="intro2" class="dowebok">
                <div class="title_box">
                    <h2 id="in1" class="layout_intro_tit">实时课表</h2>
                    <h2 id="in2" class="layout_intro_tit not_chosen" title="查看我的课表">我的课表</h2>
                </div>
                <div class="data_container" id="div1">
                    <table>
                        <tr>
                            <th style="vertical-align:middle; text-align:center;">正在上课的课程</th>
                            <th style="vertical-align:middle; text-align:center;">授课教师</th>
                            <th style="vertical-align:middle; text-align:center;">正在上课的班级</th>
                        </tr>
                        <tr id="currentTimetable">
                            <td style="vertical-align:middle; text-align:center;" id="currentCourseName"></td>
                            <td style="vertical-align:middle; text-align:center;" id="currentCourseTeacher"></td>
                            <td style="vertical-align:middle; text-align:center;" id="currentClassName"></td>

                        </tr>
                    </table>
                </div>
                <div class="data_container" id="div2">
                    <div class="close_btn">×</div>
                    <iframe id="iframe" src="${pageContext.request.contextPath}/timetable/listGeneralTimetable?"
                            align="middle"></iframe>
                </div>
            </div>
        </div>
        <!--    设备编号、设备名称、品牌、型号、规格、单价、领用人、领用单位、购置日期； -->
        <div class="div2">
            <div class="intro1" class="dowebok">
                <div class="title_box">
                    <h2 class="layout_intro_tit">设备详情</h2>
                    <form class="equip_search">
                        <label for="name">设备名称</label> <input type="text" id="DeviceName"
                                                              class="form-control" id="name" placeholder="设备名称">
                        <label> <input type="checkbox" id="ifLend">可借用 </label>
                        <label> <input type="checkbox" id="ifAppointment">可预约 </label>
                        <input type="button" onclick="changeDeviceByName()" value="查询">
                    </form>
                </div>
                <div class="data_container">
                    <table id="deviceTbody">
                        <tr>
                            <th style="vertical-align:middle; text-align:center;">序号</th>
                            <th style="vertical-align:middle; text-align:center;">设备编号</th>
                            <th style="vertical-align:middle; text-align:center;">设备名称</th>
                            <th style="vertical-align:middle; text-align:center;">型号</th>
                            <th style="vertical-align:middle; text-align:center;">单价</th>
                            <th style="vertical-align:middle; text-align:center;">是否可借用</th>
                            <th style="vertical-align:middle; text-align:center;">是否可预约</th>
                            <%--<th style="vertical-align:middle; text-align:center;">操作</th>--%>
                        </tr>
                        <input id="DeviceRoomId" value="${labRoom.id }" hidden>
                    </table>
                </div>
            </div>
            <c:if test="${softManage eq 'true'}">
                <div class="intro2" class="dowebok">
                    <div class="title_box">
                        <h2 class="layout_intro_tit">软件详情</h2>
                    </div>
                    <div class="data_container">
                        <table id="softwareDetail">
                            <tr>
                                <th style="vertical-align:middle; text-align:center;">软件名称</th>
                                <th style="vertical-align:middle; text-align:center;">软件版本</th>
                                <th style="vertical-align:middle; text-align:center;">价格</th>
                            </tr>
                            <tr>
                                <td style="vertical-align:middle; text-align:center;">XXX</td>
                                <td style="vertical-align:middle; text-align:center;">XXX</td>
                                <td style="vertical-align:middle; text-align:center;">XX</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<div class="layout_link_container">

    <div class="layout_link_box">
        <div class="address">
            <div class="link_title">联系我们</div>
            <font style="font-size:12px;"><spring:message code="title.school.name"/></font>

        </div>
        <div class="link_box">
            <div class="link_title">友情链接</div>
            <ul class="link_list">
                <c:forEach items="${Links2}" var="link" end="11">
                    <li><a href="${link.linkUrl}">${link.linkName}</a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
<div class="layout_footer">

    <div class="layout_power">
        <p class="copyright">Copyright © 2016 <spring:message code="title.school.name"/></p>
        <p class="power">
            power by <a href="http://www.gvsun.com">Gvsun</a>
        </p>
    </div>
</div>
<script>
    /* 视频的js */
    var player = videojs('my-video');
    player.play();
    $(".room_list li").hover(
        function () {
            var id = $(this).find("p").html();//教室id
            //$(".room_img").attr("src", "${pageContext.request.contextPath}/images/visualization/floor/room"+${buildNumber}+str+".png");
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/visualization/show/changeRoomImage',
                data: {'id': id, 'type': 1},
                success: function (data) {
                    $(".room_img").attr("src", "${pageContext.request.contextPath}/" + data);
                }
            });
        }
    )
    $(".news_list_container").hover(
        function () {
            $(this).find(".news_list").show();
            $(this).addClass("news_selected");
            $(this).siblings().find(".news_list").hide();
            $(this).siblings().removeClass("news_selected")
        },
        function () {
            /*$(this).find(".news_list").hide()*/
        }
    );

    $(".room_list li a").click(
        function () {

            $(".building_banner").stop().fadeIn(150)
        }
    )
    $(".room_list li").first().addClass("room_selected");
    $(".room_list li").click(
        function () {
            $(this).addClass("room_selected")
            $(this).siblings().removeClass("room_selected")
        }
    )
    var s = $(".room_list li").length;
    var a = 900 / (s - 2);
    /*console.log(a);*/
    for (var i = 0; i < s; i++) {
        var d = a * i
        $(".room_list li").eq(i).css("left", d);

    }
    $(function () {
        var elm = $('#nav');
        var startPos = $(elm).offset().top;
        $.event.add(window, "scroll", function () {
            var p = $(window).scrollTop();
            $(elm).css('position', ((p) > startPos) ? 'fixed' : 'absolute');
            $(elm).css('top', ((p) > startPos) ? '100px' : '');
        });
    });

    $(function () {
        $('#nav').onePageNav({
            filter: ':not(.exception)'
        });
    });
    $('.room_list li').each(function () {
        $(this).addClass("rot50");
    });
    $(".floor_nav_floor li").click(
        function () {
            $(this).find("a").addClass("selected_floor");
            $(this).siblings().find("a").removeClass("selected_floor");
        }
    );

    //打开视频
    function openVideo(agentId) {
        var url = "${pageContext.request.contextPath}/labRoom/appointment/openVideo?agentId=" + agentId;
        // window.location.href=url;  // 本窗口
        window.open(url); // 新窗口
    }
</script>
<script>
    $(function () {
        $("#in1").click(function () {
            $(this).removeClass("not_chosen");
            $("#in2").addClass("not_chosen");
            $("#div2").css("display", "none");
            $("#div1").css("display", "block");
        });
        $(".close_btn").click(function () {
            $("#in1").removeClass("not_chosen");
            $("#in2").addClass("not_chosen");
            $("#div2").css("display", "none");
            $("#div1").css("display", "block");
        });
        $("#in2").click(function () {
            $(this).removeClass("not_chosen");
            $("#in1").addClass("not_chosen");
            $("#div2").css("display", "block");
        });
    });
</script>

<script>
    //
    function targetUrl(url) {
        document.form.action=url;
        document.form.submit();
    }
</script>
</body>

</html>