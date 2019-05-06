<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <title>设备添加</title>
    <meta name="decorator" content="none"/>
    <meta name="Generator" content="gvsun">
    <meta name="Author" content="lyyyyyy">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/visualization/floor/table.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/visualization/floor/style.css" rel="stylesheet" type="text/css">

    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式 -->

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/floor/jquery-1.11.0.min.js"></script>
    <link href="${pageContext.request.contextPath}/lab_video/video-js.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/lab_video/video.js"></script>
    <script type="text/javascript">
        $(function(){
            //没有视频就不显示
            if($("#videoDivSpan").html()==""){
                $("#videoDiv").hide();
            }else{
                $(".autoSize").hide();//不隐藏 点击一个红气泡 视频会消失
            }
            //加载后调整视频尺寸的方法
            changSize();
            //浏览器窗口改变时触发
            $(window).resize(function () {
                changSize();
            })
            function changSize(){
                $("#my-video").width($(window).width());
                $("#my-video").height($(window).height());
                $("#videoDiv").width($(window).width());
                $("#videoDiv").height($(window).height());

                var Height = $(window).height();
                var Width = $(window).width()
                $(".pv-inner,.drag_container,.panorama").height(Height);
                $(".drag_container").width(Width);
                var img_w=$("#my-video").width();
                var img_H=$("#my-video").height();
                $(".panorama").width(img_w);
            }
        })
        function mouseMove(ev) {
            Ev = ev || window.event;
            var mousePos = mouseCoords(ev);
            var img_width=$("#viewer_img").width();
            var img_height=$("#viewer_img").height();
            var percentage_w=(mousePos.x/img_width).toFixed(2)
            var percentage_h=(mousePos.y/img_height).toFixed(2)
            document.getElementById("xCoordinate").value = percentage_w;
            document.getElementById("yCoordinate").value = percentage_h;
            //新建设备窗口打开
            if($(".equipment_inforamtion").is(':hidden')){
                $("#labRoomDeviceId").val("");
                $("#deviceNumber").val("");
                $("#deviceName").val("");
            }
            $(".equipment_inforamtion").show()
        }

        function mouseCoords(ev) {
            if (ev.pageX || ev.pageY) {
                return {
                    x: ev.pageX,
                    y: ev.pageY
                };
            }
            return {
                x: ev.clientX + document.getElementById("viewer_img").scrollLeft - document.body.clientLeft,
                y: ev.clientY + document.getElementById("viewer_img").scrollTop - document.body.clientTop
            };
        }
        document.ondblclick = mouseMove;
        setInterval("TT()", 10);
        function TT() {
            var Height = $(window).height();
            var Width = $(window).width()
            $(".panorama").height(Height);
            var img_w=$("#viewer_img").width()
            $(".ksajdfh").width(img_w)
        }

        //关闭设备窗口
        function cancel(){
            $(".equipment_inforamtion").hide()
        }

        //编辑设备
        function editDevice(id){
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/visualization/editDevice",
                data: {'id':id},
                dataType:'json',
                success:function(data){
                    $.each(data,function(key,values){
                        //alert(key+"="+values);
                        //document.getElementById(key).innerHTML=values;
                        $("#"+key).val(""+values);
                    });
                },
                error:function(){
                    //alert("加载课表失败!");
                }
            });
            $(".select_chosen").trigger("liszt:updated");
            $('.select_chosen').chosen();
            $(".equipment_inforamtion").show()
        }

        //删除设备
        function deletDevice(){
            var labRoomDeviceid = $("#labRoomDeviceId").val();
            if(labRoomDeviceid == ''){
                $(".equipment_inforamtion").hide();
                return false;
            }else{
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/visualization/deletDevice",
                    data: {'labRoomDeviceId':labRoomDeviceid},
                    success:function(data){
                        if(data){
                            $(".equipment_inforamtion").hide();
                            $("#device"+labRoomDeviceid).remove();
                        }
                    },
                    error:function(){
                    }
                });
            }
        }

        //首页
        function saveDevice(){
            $("#myForm").submit();
        }

    </script>

    <style>
        * {
            margin: 0px;
        }
        .panorama {
            height: 100%;
            position: relative;
        }

        .panorama img {
            height: 100%;
        }

        .equipment_icon{
            position:absolute;

            z-index:1;
        }
        #my-video,#videoDiv {
            position: fixed;
            min-width: 100%;
            min-height: 100%;
            width: auto;
            height: auto;
            top: 50%;
            left: 50%;
            transform: translateX(-50%) translateY(-50%);
            -webkit-transform: translateX(-50%) translateY(-50%);
            z-index: -100;
        }

        .autoSize {
            position: absolute;
            min-width: 100%;
            min-height: 100%;
            width: auto;
            height: auto;
        }

        .align {
            z-index: -150;
        }
    </style>

</head>

<body style="" class="selected_floor">
<div class="layout_panorama_container">
    <div style="position:relative; width:100%;" class="ksajdfh">
        <!-- 视频开始 -->
        <div id="videoDiv">
            <video id="my-video"   loop muted poster="" ><!-- polina.jpg -->
                <source src="http://${serverIp}:8080/live/${lastFour}.m3u8" type="application/x-mpegURL">
                <source src="rtmp://${serverIp}:${hardwarePort}/live/${lastFour}" type="rtmp/flv">
            </video>
            <span style="display:none" id="videoDivSpan">${serverIp}</span>
        </div>
        <!-- 视频结束 -->
        <c:forEach items="${labRoomDevices}" var="labRoomDevice"  varStatus="i">
            <div id="device${labRoomDevice.id}" class="equipment_icon" style="left:${100*labRoomDevice.xCoordinate}%;top:${100*labRoomDevice.yCoordinate}%;">
                <a href='javascript:void(0)' onclick="editDevice(${labRoomDevice.id})">
                    <img src="${pageContext.request.contextPath}/images/visualization/floor/icon.png" ></a></div>
        </c:forEach>
        <div class="panorama ">
            <c:forEach items="${labRoom.commonDocuments}" var="d">
                <c:if test="${d.type==3}">
                    <img src="${pageContext.request.contextPath}/${d.documentUrl}" id="viewer_img" class="autoSize align">
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>

<div class="equipment_inforamtion">
    <img class="close" src="${pageContext.request.contextPath}/images/visualization/floor/msg_close.png"  onclick="cancel()">
    <form:form id="myForm" action="${pageContext.request.contextPath}/visualization/saveDevice" method="POST" modelAttribute="labRoomDevice">
        <div class="">
            <h2 class="equipment_name" >编辑设备信息</h2>
        </div>
        <table class="equipment_info">
            <tr>
                <th>鼠标X轴:</th>
                <td><form:input id="xCoordinate" type="text" path="xCoordinate" /></td>
                <th>鼠标Y轴:</th>
                <td><form:input id="yCoordinate" type="text" path="yCoordinate" /></td>
            </tr>
            <tr>
                <th>实验设备:</th>
                <td colspan="3" ><form:select id="labRoomDeviceId" path="id">
                    <c:forEach items="${labRoom.labRoomDevices}" var="labRoomDevice"  varStatus="i">
                        <form:option value="${labRoomDevice.id}">${labRoomDevice.schoolDevice.deviceNumber} &nbsp;&nbsp;${labRoomDevice.schoolDevice.deviceName}</form:option>
                    </c:forEach>
                </form:select></td>
            </tr>
            <tr style="display:none;">
                <th><spring:message code="all.trainingRoom.labroom"/>ID</th>
                <td><form:input id="labRoomId" type="text" path="labRoom.id" value="${labRoom.id}"/></td>
            </tr>
        </table>
        <input type="submit" value="提交" class="alt_btn" onclick="saveDevice()">
        <input type="button" value="删除" class="alt_btn" onclick="deletDevice()">
    </form:form>
</div>

<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select'           : {search_contains:true},
        '.chzn-select-deselect'  : {allow_single_deselect:true},
        '.chzn-select-no-single' : {disable_search_threshold:10},
        '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
        '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
    /* 视频的js */
    var player = videojs('my-video');
    player.play();
</script>
<!-- 下拉框的js -->

</body>

</html>