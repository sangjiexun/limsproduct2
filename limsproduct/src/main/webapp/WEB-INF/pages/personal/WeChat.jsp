<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta name="decorator" content="iframe"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=0.5, maximum-scale=1">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css"/>
    <script type="text/javascript">
        $(document).ready(function(){
            myReload();
        })
        //刷新验证码
        function myReload() {
            document.getElementById("updateCode").src = "${pageContext.request.contextPath}/ValidateCodeServle?time=" + new Date();
            generateQrCode();
        }

        //生成二维码
        function generateQrCode() {
            $.ajax({
                url:"${pageContext.request.contextPath}/personal/generateQrCodeForWeChat?",
                type: 'POST',
                success: function(data) {
                    $("#QRCode").attr("src",data);
                }
            });
        }
    </script>
</head>

<body>
<div class="headline-bg index-headline-bg wavesWapper">
    <canvas id="waves" class="waves"></canvas>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/curve_global.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/curve_index.js"></script>

<!--使用新框架与新样式的时候，请把原样式与原样式都隐去，否则会影响正常显示！-->
<!--新框架样式开始-->
<div class="qrcode_box">
    <div class="qrcode_content">
	    <div class="content_box">
		    <div class="qrcode_text">
			    <font>❶</font>
				<div>打开微信，扫一扫，<br>关注实验室小程序</div>
			</div>
			<div class="qrcode_bottom">
	            <div>关注实验室小程序</div>
		        <img src="../images/system_pic/${PROJECT_NAME}_wechat.jpg"/>
                <div class="hint">长按识别图中二维码</div>
	        </div>
		</div>	
		<div class="content_box">
		    <div class="qrcode_text">
			    <font>❷</font>
				<div>打开实验室小程序，进入【我的】<span>，</span>点击【点击微信绑定】<span>，</span><br>进入【二维码绑定账号】<span>，</span>扫一扫，绑定个人账号与微信</div>
			</div>
			<div class="qrcode_bottom">
	            <div>绑定个人账号与微信</div>
		        <img id="QRCode" src=""/>
                <div class="hint">长按识别图中二维码</div>
                <div><a href="" onclick="generateQrCode()" class="updatebtn">更新</a></div>
                <div style="display: none">
                    <img src="${pageContext.request.contextPath}/ValidateCodeServle" id="updateCode" align="middle">
                </div>
	        </div>
		</div>	
	</div>
</div>
<style>
    .dmrrr{
        padding: 0;
        width: 100%;
        height: 100%;
    }
    body {
        font-size: 0;
        margin: 0;
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }
    .headline-bg{
        width: 100%;
        height:100%;
        background:-webkit-linear-gradient(top,#0076d1,#5db8ff);
        background:-moz-linear-gradient(top,#0076d1,#5db8ff);
        background:-o-linear-gradient(top,#0076d1,#5db8ff);
        background:-ms-linear-gradient(top,#0076d1,#5db8ff);
        overflow: hidden;
    }
    #waves{
        width: 100%!important;
        height: 100%!important;
    }
    .qrcode_box{
        position: absolute;
        top:0;
        left: 0;
		width:100%;
		height:100%;
    }
	.qrcode_content{
        position: relative;
        top: 50%;
        font-size: 0;	
		text-align: center;
        margin: -159px 0 0;
	}
	.content_box{
		display: inline-block;
        padding: 0px 60px;
        font-size: 0;
        border-right: 1px solid rgba(255, 255, 255, 0.5);
		vertical-align:text-top;
	}
	.content_box:last-child{
		border:none;
	}
	.qrcode_text {
        font-size: 0;
        text-align: left;
        margin: 0 0 29px;
        overflow: hidden;
    }
    .qrcode_text font {
        float:left;
        font-size: 30px;
        color: #fff;
        line-height: 30px;
        height: 30px;
        margin: 0 7px 0 0;
    }
    .qrcode_text div {
		float:left;
	    color: #fff;
        font-size: 17px;
        font-weight: bold;
        line-height: 30px;
		letter-spacing:0.5px;
		vertical-align:top;
    }
	.qrcode_text div span{
		font-size:17px;
		color:#fff;
	}
	.qrcode_bottom div {
        font-size: 14px;
        color: #fff;
    }	
    .qrcode_bottom img {
        width: 200px;
        height: 200px;
        background: rgba(0,0,0,0.3);
    }
	.hint{
		display:none;
	}
	.updatebtn{
		display: inline-block;
        font-size: 14px;
        color: #fff;
        padding: 5px 30px;
        background: #fa8c1b;
        border-radius: 8px;
        margin: 10px 0 0;
	}	
    .updatebtn:hover{
        opacity: 0.8;
        cursor: pointer;
    }
	.space{
		display:none;
	}
	@media only screen and (max-width: 961px){
		.qrcode_box{
			display:block;
			overflow:auto;
		}
		.qrcode_content{
			top:0;
			margin:30px 0;
		}
		.content_box{
			display:block;
			padding: 35px 0;
            border-right: none;
		}
		.content_box:last-child{
			margin:0;
		}
		.qrcode_text{
			text-align:center;
			margin:0 0 22px;
		}
		.qrcode_text br{
			display:none;
		}
		.qrcode_text font {
			float:none;
			display: block;
            font-size: 35px;
            line-height: 35px;
            height: 35px;
            margin: 0 auto 3px;
        }
		.qrcode_text div{
			font-size: 17px;
            font-weight: normal;
            float: none;
            display: inline-block;
            line-height: 26px;
		}
       .qrcode_bottom img {
		   width:260px;
		   height:260px;
	    }
        .hint{
	       display:block;
        }
		.updatebtn{
			margin:25px 0 0;
		}
		.qrcode_text span{
			display:block;
			font-size:0;
			line-height:0;
		}
	}
</style>
<!--新框架样式结束-->

</body>
</html>