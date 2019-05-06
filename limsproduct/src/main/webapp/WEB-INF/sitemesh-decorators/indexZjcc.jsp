<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="none">
    <title><spring:message code="title.html.name" /></title>
    <meta name="Generator" content="gvsun">
    <meta name="Author" content="lyyyyyy">
    <meta name="Keywords" content="">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <decorator:head></decorator:head>
    <link href="${pageContext.request.contextPath}/css/cms/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cms/media-queries.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>

    <!-- Slider Plugin -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/index/jquery.glide.js"></script>
    <link href="${pageContext.request.contextPath}/css/cms/bdsstyle.css" rel="stylesheet" type="text/css">
	<%session.setAttribute("LOGINTYPE","index"); %>
	<style type="text/css">
		.naver li {
		    width: 11%;
		}
	</style>
</head>
<body>
    <div class="logo">
        <div class="mobile-action"></div>
        <img src="${pageContext.request.contextPath}/images/cms/logo.png" id="logo">
    </div>
    <div class="nav">
        <ul class="naver">
                 <li>
                <a href="${pageContext.request.contextPath}/${siteUrl}">首页</a>
            </li>
        <c:forEach items="${topChannels}" var="topChannel">   
           <c:choose>
                <c:when test="${not empty topChannel.hyperlink }">
                	<li><a href="${pageContext.request.contextPath}/${topChannel.hyperlink}" target="_blank">${topChannel.title }</a></li>
                </c:when>
                <c:when test="${topChannel.title eq'可视化'}">
                      <li><a href="${pageContext.request.contextPath}/${siteUrl}/allLabRoom?currpage=1">${topChannel.title}</a></li>
                </c:when>
                  <c:when test="${topChannel.title eq'实训室预约'}">
                      <li><a href="${pageContext.request.contextPath}/${siteUrl}/zjcc/listLabRoom?currpage=1">${topChannel.title}</a></li>
                </c:when>
                   <c:when test="${topChannel.title eq'设备预约'}">
                  <li><a href="${pageContext.request.contextPath}/${siteUrl}/reportMain">${topChannel.title}</a></li>
                </c:when>
                <c:when test="${topChannel.title eq'实训室借用'}">
                  <li><a href="${pageContext.request.contextPath}/${siteUrl}/achieveShow">${topChannel.title}</a></li>
                </c:when>
                <c:otherwise>
                <li>
                <a href="${pageContext.request.contextPath}/${siteUrl}/channel/${topChannel.id}/1">${topChannel.title}</a>
                    <ul class="menu">
                       <c:forEach items="${topChannel.cmsChannel}" var="Childchannel">
                    <li><a href="${pageContext.request.contextPath}/${siteUrl}/channel/${Childchannel.id}/1">${Childchannel.title}</a></li>
                      </c:forEach>
                  </ul>
            </li>
                </c:otherwise>
           </c:choose>
       </c:forEach>
              
            <c:if test="${needLogin || empty needLogin}">
				<li><a href="#" onclick="login()">系统登录</a>	</li>
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
        <c:forEach items="${allLinks}" var="link">
          <c:if test="${link.cmsTag.id==6}">
          <li class="slide">
                <div class="box" style="background-color: #1abc9c;"><img src="${pageContext.request.contextPath}${link.cmsResource.url}" />
                </div>
            </li>
          </c:if>
        </c:forEach>          
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
	                <span style="float:left;width:auto;" class="sds"><a href="/zjcclims">进入实验室智能管理系统</a></span> 
	                <span style="float:right;"><a style="background:none;border:none;margin:0;color:black;" href="${pageContext.request.contextPath}/pages/logout-redirect.jsp" onclick="clearCookie();">退出</a></span>
	            </div>
	        </div>
	    </div>
	    </c:if>
    </div>
    <div class="content">
    	<c:forEach items="${allLinks}" var="link">
          	<c:if test="${link.linkName eq '报表中心'}">
	        <div class="from content-box">
	            <div class="icon-box">
	                <a href="${pageContext.request.contextPath}/${siteUrl}/reportMain">
	                    <div class="icon"><img src="${pageContext.request.contextPath}/images/cms/form.png">
	                    </div>
	                    <div class="title">${link.linkName}</div>
	                </a>
	            </div>
	        </div>
        	</c:if>
	        <c:if test="${link.linkName eq '实验室资源管理'}">
	        <div class="resource content-box">
	            <div class="icon-box">
	                <a href="${pageContext.request.contextPath}/${siteUrl}/allLabRoom?currpage=1">
	                    <div class="icon"><img src="${pageContext.request.contextPath}/images/cms/resource.png">
	                    </div>
	                    <div class="title">${link.linkName}
	                    </div>
	                </a>
	            </div>
	        </div>
	        </c:if>
	        <c:if test="${link.linkName eq '设备资源管理'}">
	        <div class="lab content-box">
	            <div class="icon-box">
	                <a href="${pageContext.request.contextPath}/${siteUrl}/allEquipment?currpage=1">
	                    <div class="icon"><img src="${pageContext.request.contextPath}/images/cms/lab.png">
	                    </div>
	                    <div class="title">${link.linkName}
	                    </div>
	                </a>
	            </div>
	        </div>
	        </c:if>
        </c:forEach>
    </div>
    <div class="new-box">
    <c:forEach items="${recommendChannels}"  var ="recCchannel">
        <div class="new-tit"><a href="list.html">${recCchannel.title}</a> </div>
        <div class="new">
	        <ul>
	        <c:forEach items="${recCchannel.cmsArticles}" var="articles">
                <li><span class="time"><fmt:formatDate value="${articles.createTime.time}" pattern="yyyy-MM-dd"/></span>
					<a href="${pageContext.request.contextPath}/${siteUrl}/article/${articles.id}">${articles.profile}</a>
                </li>  
	        </c:forEach>
	        </ul>
 	</c:forEach>
        </div>
    </div>
    <div class="link-box">
        <div class="link">
            <ul class="link_school">
            <c:forEach  items="${allLinks }"  var="link">
            <!-- 友情链接 -->
        	<c:if test="${link.cmsTag.id==22}">
                 <li>
                    <a href="${link.linkUrl }"><img src="${pageContext.request.contextPath}${link.resource.url}"><span>${link.linkName}</span>
                    </a>
                </li>
        	</c:if>
        	</c:forEach>
            </ul>
            <ul class="link_friend">
        <c:forEach  items="${allLinks }"  var="link">
        	<!-- 链接类型二 -->
        	<c:if test="${link.cmsTag.id==23}">
        	       <li>
                    <a href="${link.linkUrl}"><img src="${pageContext.request.contextPath}${link.resource.url}"><span>${link.linkName}</span>
                    </a>
                </li>
        	</c:if>
       	</c:forEach>      
            </ul>
        </div>
    </div>
    <div class="footer-box">
        <div class="footer">
            <div class="address">
                <div class="title address-tit">联系地址</div>
                <ul>
                    <li>校址：浙江省杭州萧山高教园区 | 邮编：311231 </li>
                </ul>
            </div>
            <div class="friend">
                <div class="title">版权信息</div>
                <ul>
                    <li style="text-indent: 20px;width:120%;">Copyright 2017 <spring:message code="title.school.name" /> 沪ICP备05003365 技术支持：<a href="http://www.gvsun.com">GVSUN</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="power">
        POWERED BY | 庚商科技
        </div>
    </div>
      <div class="black_overlay" style="display:none">
             <div class="login_frame" style="display:none">
             <img src="${pageContext.request.contextPath}/images/cms/logo_2.png" height="60"  alt="" />
                 <div class="login">
                     <div class="holder">
                     <form class="sub_form" name='f' action='${pageContext.request.contextPath}/j_spring_security_check' method='POST'>
                         <input class="login_input" style="margin-bottom:15px;" type="text" name="j_username" placeholder="请输入用户名" />
                         <input class="login_input" style="margin-bottom:15px;background:none;color:#333;" type="password" name="j_password" placeholder="请输入密码" />
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
	$(function(){
          	$('.black_overlay').css("display","none");
          	$('.login_frame').css("display","none");
           $('.loginbtn').bind("click",function() {
	           $('.black_overlay').css("display","block");
	           $('.login_frame').css("display","block");
           });
           $('.close').bind("click",function(){
	           	$('.black_overlay').css("display","none");
	           	$('.login_frame').css("display","none");
           })
	})
  
		function login(){ 
	 		var urlString="${pageContext.request.contextPath}/cms";
	 		$.ajax({
		           url:"${pageContext.request.contextPath}/cms/setSession?type="+1+"&urlString="+urlString,
		           type:"POST",
		           success:function(data){//AJAX查询成功
		           		if(data=="success"){ 
		        	 		$('.black_overlay').css("display","block");
		        	        $('.login_frame').css("display","block");
		           		}    
		           }
		});
	 	}
</script>
</body>

</html>