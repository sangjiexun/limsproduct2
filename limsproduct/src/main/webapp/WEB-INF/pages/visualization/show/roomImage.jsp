<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <title><spring:message code="title.html.name" /></title>
    <meta name="decorator" content="none"/>
    <meta name="Generator" content="gvsun">
    <meta name="Author" content="lyyyyyy">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

    <link href="${pageContext.request.contextPath}/css/visualization/floor/style.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/visualization/floor/table.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/visualization/floor/layout_header.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/floor/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/visualization/floor/drag.js"></script>
    <!--<script type="text/javascript" src="js/jquery.mousewheel.js"></script>
    <script type="text/javascript" src="js/perfect-scrollbar.js"></script>
	<link href="css/perfect-scrollbar.css" rel='stylesheet' type='text/css'>-->
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/lab_video/video-js.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/lab_video/video.js"></script>
    <script type="text/javascript">
    function showDevice1(id){
        layer.ready(function(){
            layer.open({
                type: 2,
                title: '设备查看',
                fix: true,
                maxmin:true,
                shift:-1,
                closeBtn: 1,
                shadeClose: true,
                move:false,
                maxmin: false,
                area: ['1000px', '420px'],
                content: '../listDevices?page=1&id='+id,
                end: function(){
                    //search()
                }
            });
        });
    }
    function showdevice(){
    
     $(".equipment_inforamtion_list").show();
    }
    /* 调整视频的js */
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
			 /* 视频的js */
		 	 var player = videojs('my-video');
		      player.play();
    })
        setInterval("TT()", 100);

        function TT() {
            var Height = $(window).height();
            var Width = $(window).width()
            $(".pv-inner,.drag_container,.panorama").height(Height);
            $(".drag_container").width(Width);
            var img_w=$("#viewer_img").width();
            var img_H=$("#viewer_img").height();
        	$(".panorama").width(img_w);
        	//console.log(img_H,img_w)
        } 
        function noDevice(){
        	$(".equipment_inforamtion").hide()
        }
            //编辑设备
    function lookDevice(id){
    	$.ajax({
    		type: "POST",
    		url: "${pageContext.request.contextPath}/visualization/show/lookDevice",
    		data: {'id':id},
    		dataType:'json',
    		success:function(data){
    			$.each(data,function(key,values){  
    				//alert(key+"="+values);
    				//document.getElementById(key).innerHTML=values;
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
    					document.getElementById(key).innerHTML=values;
        				$("#"+key).val(""+values);
    				}
    			 }); 
    		},
    		error:function(){
    			//alert("加载课表失败!");
    			alert(id);
    			}
    	});
    	$(".equipment_inforamtion").show()
    }
        //关闭设备窗口
    function cancel(){
    	$(".equipment_inforamtion").hide()
    }
         //关闭设备列表窗口
    function cancellist(){
    	$(".equipment_inforamtion_list").hide()
    }
        
        function turnDeviceQR(id){
        	window.open("${pageContext.request.contextPath}/cmsshow/showDevice?id="+id);
        }
        function turnVideoQR(folderId,type){
        	window.location.href="${pageContext.request.contextPath}/tcoursesite/videoLook?folderId="+folderId+"&moduleType="+type;
        }
    </script>
    <script type="text/javascript">
     //首页
	function first(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//末页
	function last(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//上一页
	function previous(url){
		var page=${page};
		if(page==1){
			page=1;
		}else{
			page=page-1;
		}
		//alert("上一页的路径："+url+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	//下一页
	function next(url){
		var totalPage=${pageModel.totalPage};
		var page=${page};
		if(page>=totalPage){
			page=totalPage;
		}else{
			page=page+1
		}
		//alert("下一页的路径："+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	} 
    </script>
       <style>
        * {
            margin: 0px;
        }
        .panorama {
            position: relative;
        }
        
        .panorama img {
            height: 100%;
            position:relative;
        }
        .drag_container{
        	position:relative;
        	    z-index: 0;
        	overflow:hidden;
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
	min-height: 100%;
	width: auto;
	height: auto;
}

#dragDiv,.align {
	z-index: -150;
}

.bottom {
	z-index: -200;
}
    </style> 
</head>
<body style="" class="selected_floor" >
	<input type="hidden"  id="contextPath" value="${pageContext.request.contextPath}"/>
	<input type="hidden"  id="sessionId" value="<%=session.getId()%>"/>
    <div class="layout_panorama_container">
 		<div class="drag_container">
 			
        <div class="panorama" id="dragDiv" style="position:relative; background-color:#FFFFFF;left:0px;top:0px;filter:alpha(opacity=100);opacity:1;">
            <c:forEach items="${labRoom.commonDocuments}" var="d">
				<c:if test="${d.type==3}">
				    <img src="${pageContext.request.contextPath}/${d.documentUrl}" id="viewer_img" class="autoSize align">
				</c:if>
			</c:forEach>
            <!-- 红气泡 -->
			<!-- 视频开始 -->

			<div id="videoDiv">
				<video id="my-video"   loop muted poster="" >
		    	<source src="http://${serverIp}:8080/live/${lastFour}.m3u8" type="application/x-mpegURL">
				<source src="rtmp://${serverIp}:${hardwarePort}/live/${lastFour}" type="rtmp/flv">
				</video>
				
				<span style="display:none" id="videoDivSpan">${serverIp}</span>
			</div>
			<!-- 视频结束 -->
		    <c:forEach items="${labRoomDevices}" var="labRoomDevice"  varStatus="i">
		    <div id="device${labRoomDevice.id}" class="equipment_icon" style="left:${100*labRoomDevice.xCoordinate}%;top:${100*labRoomDevice.yCoordinate}%;">
		    <a href='javascript:void(0)' id="lookdevice" onmouseover="lookDevice(${labRoomDevice.id})" onmouseout="noDevice();">
		    <img src="${pageContext.request.contextPath}/images/visualization/floor/icon.png" ></a></div>
		    </c:forEach>
        </div>
        </div>
        <div class="layout_logo_container" style="position:fixed;"><img style="width: 60%;" src="${pageContext.request.contextPath}/images/visualization/floor/sub_logo.png">
        </div> 
        <div class="layout_logo_container" style="position:fixed;margin-left: 1100px;cursor: pointer; background:#ebebeb;padding:5px;" onclick="showDevice1(${id})">设备列表</div>    
    </div>
                    <div class="equipment_inforamtion">
                  
                    <img class="close" src="${pageContext.request.contextPath}/images/visualization/floor/msg_close.png"  onclick="cancel()">
                <form:form id="myForm" action="${pageContext.request.contextPath}/visualization/saveDevice" method="POST" modelAttribute="labRoomDevice">     
                     <div class="">
                     	<h2 class="equipment_name" id="deviceName"></h2>
                     	
                     </div>
                     	
                     <table class="equipment_info">
                       <tr style="display:none"> 
                       <th>鼠标X轴</th> 
                       <td><form:input id="xCoordinate" type="text" path="xCoordinate"/></td>
                       <th>鼠标Y轴</th>
                       <td><form:input id="yCoordinate" type="text" path="yCoordinate"/></td>
                       </tr>
                       <tr>
                       <th>设备编号</th>
                       <td><form:input id="deviceNumber" type="text" path="schoolDevice.deviceNumber" readonly="true"/></td>  
                        <th>生产厂家</th>
                       <td><form:input id="deviceSupplier" type="text" path="schoolDevice.manufacturer" readonly="true" /></td>           
                     <!--    <th>仪器规格</th>
                       <td><form:input id="deviceFormat" type="text" path="schoolDevice.deviceNumber"/></td>     -->                          
                       </tr>
                       <tr>
            		  
                       <th>仪器型号</th>
                       <td><form:input id="devicePattern" type="text" path="schoolDevice.deviceNumber" readonly="true"/></td>                    
                       
            		   <th>所属学院</th>
                       <td><form:input id="academyName" type="text" path="schoolDevice.schoolAcademy.academyName" readonly="true"/></td>   
                       <%--<th>仪器型号</th>
                       <td><form:input id="devicePattern" type="text" path="schoolDevice.deviceNumber"/></td>                    
                       --%></tr>
                       <tr>
                        <th>设备信息</th>
                        <td colspan="4">
                       <img  id="dimensionalCode" src="${pageContext.request.contextPath}/images/visualization/floor/msg_close.png" style="wdith:100px;height:100px;margin-left:100px;" onclick="turnDeviceQR()" >
                       </td>
                        <th>知识视频</th>
                       <td colspan="4">
                       <img  id="qRCodeUrl" src="${pageContext.request.contextPath}/images/visualization/floor/msg_close.png" style="wdith:100px;height:100px;margin-left:100px;" >
                       </td>
                       </tr>
                       
                       <tr style="display:none;">
                       <td>实验设备id</td>
                       <td><form:input id="labRoomDeviceId" type="text" path="id" /></td>
                       <th><spring:message code="all.trainingRoom.labroom"/>编号</th>
                       <td><form:input id="labRoomId" type="text" path="labRoom.id" /></td>   
                       </tr>
                     </table>
                     <!-- <input type="button" value="返回" class="alt_btn""> -->
                     
                </form:form>
                </div>
                
                 <div class="equipment_inforamtion_list">
                <img class="close_list" src="${pageContext.request.contextPath}/images/visualization/floor/msg_close.png"  onclick="cancellist()">
                   <div class="">
                     </div>
                     <table class="equipment_info">
                     <th style="vertical-align:middle; text-align:center;">设备编号</th>
                     <th style="vertical-align:middle; text-align:center;">设备名称</th>
                     <th style="vertical-align:middle; text-align:center;">品牌</th>
                     <th style="vertical-align:middle; text-align:center;">型号</th>
                     <th style="vertical-align:middle; text-align:center;">规格</th>
                     <th style="vertical-align:middle; text-align:center;">单价</th>
                     <th style="vertical-align:middle; text-align:center;">领用人</th>
                     <th style="vertical-align:middle; text-align:center;">领用单位</th>
                     <th style="vertical-align:middle; text-align:center;">购置日期</th>
					 <c:forEach items="${labRoomDeviceInfo}" var="labRoomDevice"  varStatus="i">
					       <tr>
					       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.deviceNumber}</td>
					       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.deviceName}</td>
					       <td style="vertical-align:middle; text-align:center;">*</td>
					       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.devicePattern}</td>
					       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.devicePrice}</td>
					       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.deviceNumber}</td>
					       <td style="vertical-align:middle; text-align:center;">${labRoomDevice.schoolDevice.userByUserNumber.username}</td>
					       <td style="vertical-align:middle; text-align:center;">*</td>
					       <td style="vertical-align:middle; text-align:center;"><fmt:formatDate value="${labRoomDevice.schoolDevice.deviceBuyDate.time}" pattern="yyyy-MM-dd" /></td>
					       </tr>
					 </c:forEach>
                     </table>
                  <div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
         <a href="${pageContext.request.contextPath}/visualization/show/roomImage?page=1&id=${id }" target="_self">首页</a>			    
	<a href="${pageContext.request.contextPath}/visualization/show/roomImage?page=${pageModel.previousPage}&id=${id }" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/visualization/show/roomImage?page=${page}&id=${id}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/visualization/show/roomImage?page=${j.index}&id=${id}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
 	<a href="${pageContext.request.contextPath}/visualization/show/roomImage?page=${pageModel.nextPage}&id=${id }" target="_self">下一页</a>
 	<a href="${pageContext.request.contextPath}/visualization/show/roomImage?page=${pageModel.lastPage}&id=${id }" target="_self">末页</a>
    </div>
                  </div>
    
</body>

</html>