<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="decorator" content="iframe" />
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>liv2</title>
    <script src="${pageContext.request.contextPath}/lab_video/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/lab_video/video.js"></script>
    <link href="${pageContext.request.contextPath}/lab_video/video-js.css" rel="stylesheet">
   

    <script>
        videojs.options.flash.swf = "${pageContext.request.contextPath}/lab_video/video-js.swf";
     </script>
     <style type="text/css">
     #my-video{
		position:fixed;
		min-width: 66%;
		max-height: 55%;
		width: auto;
		height: auto;
		top: 50%;
		left: 50%;
		transform: translateX(-50%) translateY(-50%);
		-webkit-transform: translateX(-50%) translateY(-50%);
		z-index: -100;
		}
     </style>
</head>
<body>
<video id="my-video" class="video-js" loop muted poster="polina.jpg">
<!--
    <source src="rtmp://live.hkstv.hk.lxdns.com/live/hks" type="rtmp/flv">
    <source src="center.mp4" type="video/mp4">  
-->
    <source src="http://www.gvsun.net:65533/live/sz.m3u8" type="application/x-mpegURL">
	<source src="rtmp://${serverIp}:${hardwarePort}/live/${lastFour}" type="rtmp/flv">
		
		<%--
		<source src="rtmp://192.168.1.13:1935/live/sz" type="rtmp/flv">
		
		
    <!--
    <source src="center.mp4" type="video/mp4">
    <source src="http://10.101.251.251:8080/live/1.m3u8" type="application/x-mpegURL">
    <source src="rtmp://10.101.251.251:1935/live/1" type="rtmp/flv">-->
--%></video>
<script>
	  var player = videojs('my-video');
      player.play();
</script>

</body>
</html>
