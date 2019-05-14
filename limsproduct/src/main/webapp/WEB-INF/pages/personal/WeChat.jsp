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

<!--新框架样式开始-->
<div class="qrcode_box">
    <div class="qrcode_content">
		<img class="phone" src="../images/phone.png"/>
		<div class="content_box">
		    <div class="qrcode_text">绑定个人账号与微信</div>
			<div class="qrcode_bottom">
		        <img id="QRCode" src=""/>
                <div><a href="" onclick="generateQrCode()" class="updatebtn">更新</a></div>			
                <div class="hint">
				    <label>1.&nbsp;请长按保存图中二维码&nbsp;;</label>
					<div><span>①&nbsp;</span><font>2.&nbsp;</font>打开实验室小程序，进入【我的】<span>;</span><font>，</font><br/><span>②&nbsp;</span>点击【点击微信绑定】<span>;</span><font>,</font><br/><span>③&nbsp;</span>进入【二维码绑定账号】;</div>
					<label>3.&nbsp;点击二维码扫描界面右上角的【相册】，选中刚才保存的二维码，确定后进行识别，完成个人账号与微信小程序的绑定&nbsp;;</label>
					<div><span>④&nbsp;扫一扫，绑定个人账号与微信&nbsp;;</span></div>
				</div>
                <div style="display: none">
                    <img src="${pageContext.request.contextPath}/ValidateCodeServle" id="updateCode" align="middle">
                </div>
	        </div>
		</div>	
	    <div class="content_box">
		    <div class="qrcode_text">如未关注实验室管理小程序，<br/>请进行如下操作：</div>
			<div class="qrcode_bottom">
		        <img src="../images/system_pic/${PROJECT_NAME}_wechat.jpg"/>
                <div class="hint">
				    <label class="tc">请长按识别图中二维码<br/>关注实验室管理小程序</label>
					<div><span>①&nbsp;打开微信&nbsp;;</span></div>
					<div><span>②&nbsp;点击微信主页面右上角的【扫一扫】，进入二维码扫描界面;</span></div>
					<div><span>③&nbsp;扫描图中二维码，关注实验室管理小程序&nbsp;;</span></div>
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
		background: -webkit-linear-gradient(top,#f6f6f9,#afc0d4);
        background: -moz-linear-gradient(top,#f6f6f9,#afc0d4);
        background: -o-linear-gradient(top,#f6f6f9,#afc0d4);
        background: -ms-linear-gradient(top,#f6f6f9,#afc0d4);
        overflow: hidden;
    }
    #waves{
        width: 100%!important;
        height: 100%!important;
    }
    .qrcode_box{
	    display:table;
        position:absolute;
        top:0;
        left: 0;
		width:100%;
		height:100%;
    }
	.qrcode_content{
	    display:table-cell;
	    vertical-align:middle;
        font-size: 0;	
		text-align: center;
		vertical-align:middle;
	}
	.phone{
	    width:288px;
		vertical-align:text-top;
		margin:0 25px 0 0;
	}
	.content_box{
		display: inline-block;
		width:270px;
        padding: 0 25px 0 0;
        font-size: 0;
        border-right: 1px solid rgba(255, 255, 255, 0.5);
		vertical-align:text-top;
	}
	.content_box:last-child{
		border:none;
		padding:0 0 0 25px;
	}
	.qrcode_text {
        font-size: 17px;
        font-weight: bold;
	    color: #20303e;
        text-align: center;
        line-height: 27px;
		height:60px;
		letter-spacing:0.5px;
    }
	.qrcode_bottom div {
        font-size: 14px;
        color: #fff;
    }	
    .qrcode_bottom img {
        width: 235px;
        height: 235px;
        background: rgba(0,0,0,0.3);
    }
	.hint {
        width: 235px;
        margin: 10px auto 0;
        text-align: left;
    }
	.hint div,
	.hint label,
	.hint div span,
	.hint div font{
		font-size:14px;
		color:#20303e;
	}
	.hint label,
	.hint div font{
		display:none;
	}	
	.hint div span {
        display: inline-block;
        margin: 3px 0 0;
    }
	.updatebtn{
		display: inline-block;
        font-size: 14px;
        color: #fff;
		text-decoration:none;
        padding: 5px 30px;
        background: #fa8c1b;
        border-radius: 8px;
        margin: 13px 0 0;
	}	
    .updatebtn:hover{
        opacity: 0.8;
        cursor: pointer;
    }
	.tc{
	    text-align:center;
	}
	@media only screen and (max-width: 961px){
	    .headline-bg{
	        background:-webkit-linear-gradient(top,#0076d1,#5db8ff);
            background:-moz-linear-gradient(top,#0076d1,#5db8ff);
            background:-o-linear-gradient(top,#0076d1,#5db8ff);
            background:-ms-linear-gradient(top,#0076d1,#5db8ff);
	    }
		.qrcode_box{
			display:block;
			overflow:auto;
		}
		.qrcode_content{
		    display:block;
			top:0;
			margin:30px 0;
		}
		.phone{
		    display:none;
		}
		.content_box{
			display:block;
			width:auto;
			padding: 40px 0;
            border-right: none;
		}
		.content_box:last-child{
			margin:0;
			padding:40px 0;
		}
		.qrcode_text{
		    font-size:18px;
            font-weight: normal;
			color:#fff;
			height:auto;
            line-height: 26px;
			margin:0 0 13px;
		}
        .qrcode_bottom img {
		   width:260px;
		   height:260px;
	    }
		.hint{
		   width:260px;
		   margin:13px auto 0;
		}
	    .hint div,
	    .hint label,
	    .hint div span,
	    .hint div font{
	       color:#fff;
	    }
		.hint label,
		.hint div{
		    margin:0 0 9px;
		}
        .hint label{
	       display:block;
        }		
	    .hint div font{
		   display:inline;
	    }	
		.hint div span{
		   display:none;
		}
		.hint div br{
		   display:none;
		}
		.updatebtn{
			margin:13px 0 0;
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