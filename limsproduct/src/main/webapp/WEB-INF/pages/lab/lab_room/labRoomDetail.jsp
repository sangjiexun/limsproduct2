<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page language="java" isELIgnored="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%--<fmt:setBundle basename="bundles.lab-resources" />--%>
<html>
<head>
<meta name="decorator" content="iframe" />
<link href="${pageContext.request.contextPath}/css/iconFont.css"
	rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<!-- 下拉框的样式 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/chosen.css" />
<link rel="stylesheet"
	href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.cookie.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>



    <!-- 下拉的样式结束 -->
<script type="text/javascript">
$(document).ready(
	//设置
   	function (){
        listUserForLabRoom();
        $('[data-id]').each(function(i,e){
         $(e).on('click',function(){
         layer.open({
                  type: 2,
                  title: '添加',
                  maxmin: true,
                  shadeClose: true, 
                  area : ['700px' , '350px'],
                  content: '${pageContext.request.contextPath }/labRoom/addAgent?id='+$(e).attr('data-id')
                  })  
    		});                                              
		});
   	});

function listUserForLabRoom(){
    $.post('${pageContext.request.contextPath}/labRoom/listUserForLabRoom',function(data){  //serialize()序列化
        $("#username3").html(data);
        $("#username3").trigger("liszt:updated");
    });
}
/**
添加设备
*/
function openwin(){
    var roomId=document.getElementById("roomId").value;
	var name=document.getElementById("deviceName").value;
	var number=document.getElementById("deviceNumber").value;
	var deviceAddress=document.getElementById("deviceAddress").value;
	$.ajax({
	   	url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceForLab?roomId="+roomId+"&name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page=1",

		type:"POST",
		success:function(data){//AJAX查询成功
			document.getElementById("body").innerHTML=data;
		}
	});
     $("#openwindow").show();
     $("#openwindow").window('open'); 
 }
 /**
 添加实验室禁用时间段
 */
 function newLimitTime() {
     $("#addLabRoomLimitTime").show();
     $("#addLabRoomLimitTime").window('open');
 }
/**
 开放设置
 */
 function editOpenTime() {
    $("#editOpenTime").show();
    $("#showOpenTime").hide();
    $("#show_open_time").hide();
 }

/**
添加软件
*/
function addSoftware(){

	var roomId=document.getElementById("roomId").value;
	var name=document.getElementById("name").value;
	var number=document.getElementById("edition").value;
	$.ajax({
		       
	           url:"${pageContext.request.contextPath}/labRoom/findSoftwareByNameAndEdition?roomId="+roomId+"&name="+name+"&edition="+edition+"&page=1",           
	           type:"POST",
	           success:function(data){//AJAX查询成功
	                  document.getElementById("body1").innerHTML=data;
	            
	           }
	});
     $("#openwindow1").show();
     $("#openwindow1").window('open'); 
 }
/**
添加管理员
*/
function addAdmin(typeId){
	var cname=document.getElementById("cname").value;
	var username=document.getElementById("username").value;
	$.ajax({
	           url:encodeURI(encodeURI("${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&roomId=${labRoom.id}&page=1&typeId="+typeId)),
	           type:"POST",
	           success:function(data){//AJAX查询成功
	        	   if(typeId==1){
	        		   document.getElementById("user_body").innerHTML=data;  
	        	   }else{
	        		   document.getElementById("user_body2").innerHTML=data;
	        	   }
	           }
	});
	if(typeId==1){
		document.getElementById("adminType").value=typeId;
	    $("#addAdmin").show();
	    $("#addAdmin").window('open'); 
	}else{
		document.getElementById("adminType2").value=typeId;
	    $("#addAdmin2").show();
	    $("#addAdmin2").window('open');
	}
 }

/**
添加实验项目
*/
function addOperationItem(){
     $("#addOperationItem").show();
     $("#addOperationItem").window('open');   
    
 }
 function getValue(){
	 var s=[];
     $($("#operation option:selected")).each(function(){
	         s.push(this.value);
     });
 	//alert(s);
 	window.location.href="${pageContext.request.contextPath}/labRoom/saveLabRoomOperationItem?roomId=${labRoom.id}&operationItem="+s+"&type=${type}";
 }
//添加设备信息
function setDeviceInfo(id,deviceId){
	//alert(id+"------"+deviceId);
	document.getElementById("labRoomDeviceId").value=id;
	document.getElementById("deviceId").value=deviceId;
	$("#set").show();
	 var topPos = window.pageYOffset;
     //使得弹出框在屏幕顶端可见
     $('#set').window({left:"100px", top:topPos+"10px"});
     $("#set").window('open');   
}

/**
添加硬件
*/ 
function addAgent(){
	$("#addAgent").show();
	var topPos = window.pageYOffset;
    //使得弹出框在屏幕顶端可见
    $('#addAgent').window({left:"100px", top:topPos+"10px"});

    $("#addAgent").window('open');  
}
/**
添加家具
*/ 
function addRecords(){
	$("#addRecords").show();
	var topPos = window.pageYOffset;
    //使得弹出框在屏幕顶端可见
    $('#addRecords').window({left:"100px", top:"20px"});
    $("#addRecords").window('open');  
}
/**
刷新权限
*/ 
function refreshPermissions(){
    // $.messager.alert("提示","<span style='font-size: 14px; color: red'>数据加载中，请您耐心等待...</span>" );
    $("#refreshing1").hide();
    $("#refreshing2").hide();
    $.ajax({
        url:'${pageContext.request.contextPath}/labRoom/refreshPermissions?roomId='+${labRoom.id},
        type:"POST",
        dataType:"json",
        success:function(data){
            console.log(data);
            if(data.result){
                alert("刷新成功");
            }else{
                alert("刷新失败,请检查网络或者重新刷新");
            }
        },error:function (request){
            alert('请求错误,请检查网络或者重新刷新');
        }
    });
    $("#refreshing1").show();
    $("#refreshing2").show();
}
function refreshZjcc(){
    var roomId=${labRoom.id};
    $.post('${pageContext.request.contextPath}/labRoom/refreshPermissions?roomId='+roomId+'',function(data){  //serialize()序列化
        if(data=="success"){
            alert("刷新成功");
        }else{
            alert("刷新失败，请检查网络或者重新刷新。");
        }
    });
}
// 新版物联-刷新权限
    function refreshPerm() {
            var roomId = ${labRoom.id};
            $.post('${pageContext.request.contextPath}/labRoom/refreshPerm?roomId='+roomId+'',function(data){  //serialize()序列化
                    if(data=="success"){
                            alert("刷新成功");
                        }else{
                            alert("刷新失败，请检查网络或者重新刷新。");
                        }
                });
        }
/**
 * 门禁
 */
function opendoor(agentId){
    $.post('${pageContext.request.contextPath}/labRoom/openDoorPython?agentId='+agentId+'',function(data){  //serialize()序列化
        if(data=="success"){
            alert("开门成功！");
        }else{
            alert("开门失败，请检查当网络连接或者再试一次。");
        }
    });
}
// 新版物联-远程开门
    function openDoorNew(agentId, doorIndex) {
            $.post('${pageContext.request.contextPath}/labRoom/openDoorNew?agentId='+agentId+'&doorIndex='+doorIndex,function(data){  //serialize()序列化
                    if(data=="success"){
                            alert("门禁已经打开");
                        }else{
                            alert("开门失败，请检查当网络连接或者再试一次。");
                        }
                });
        }
// 班牌开门
function opendoor_scr(agentId){
    $.ajax({
        url:'${pageContext.request.contextPath}/labRoom/appointment/openScrPython?agentId='+agentId,
        type:"POST",
        dataType:"json",
        success:function(data){
            console.log(data);
            if(data.result){
                alert("开门成功");
            }else{
                alert("开门失败");
            }
        },error:function (request){
            alert('请求错误!');
        }
    });
}

//AJAX验证是否通过安全准入
function Access(id){
	$.ajax({
	           url:"${pageContext.request.contextPath}/device/securityAccess?id="+id,
	           type:"POST",
	           success:function(data){//AJAX查询成功
	           		if(data=="success"){
	           			//alert("您已经通过安全准入验证"+data);
	                  	window.location.href="${pageContext.request.contextPath}/device/reservationDevice?id="+id;
	           		}else{
	           			alert("您还未通过安全准入验证！"+data);
	           			window.location.href="${pageContext.request.contextPath}/device/findAllTrainingByDeviceId?deviceId="+id;
	           		}    
	           }
	});
}
   
//打开视频
function openVideo1(roomID,id) {
    var url = "${pageContext.request.contextPath}/labRoom/appointment/openVideo?agentId="+id;
    window.open(url); // 新窗口
}
//打开调视频
function openVideoSet(roomID,id) {
    var url = "${pageContext.request.contextPath}/labRoom/appointment/openVideoSet?agentId="+id;
    // window.location.href=url;  // 本窗口
    window.open(url); // 新窗口
}

//开关电源控制
function openAgent(flag,Uid){
    var myData={
        "flag":flag,
        "insUid":Uid
    }
    $.ajax({
        url:"${pageContext.request.contextPath}/labRoom/openAgent?flag="+flag+"&insUid="+Uid,
        type:'POST',
        async:false,
        dataType: "json",
        success:function(data){//AJAX查询成功
            var success="true";
            if (flag == 1){
                alert("电源已打开");
            }
            if (flag == 0){
                alert("电源已关闭");
            }
            if (flag == 3){
                alert("已下发预约数据");
            }
            if (flag == 4){
                alert("已回推记录");
            }
            if (flag == 5){
                alert("门禁已打开");
            }
        },error:function(){
            alert("发生错误！");
        }
    });
}

// 新版物联-开关电源
function openAgentNew(flag,agentId){
    $.post('${pageContext.request.contextPath}/labRoom/openAgentNew?flag='+flag+'&agentId='+agentId,function(data){  //serialize()序列化
        if(data=="success"){
            alert("操作成功！");
        }else{
            alert("操作失败，请检查当网络连接或者再试一次。");
        }
    });
}

function userBrowser(){  
    var browserName=navigator.userAgent.toLowerCase();  
    if(/msie/i.test(browserName) && !/opera/.test(browserName)){  
        return "IE";  
    }else if(/firefox/i.test(browserName)){  
        return "Firefox";  
    }else if(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName)){  
        return "Chrome";  
    }else if(/opera/i.test(browserName)){  
        return "Opera";  
    }else if(/webkit/i.test(browserName) &&!(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName))){
        return "Safari";  
    }else{  
        alert("unKnow");  
    }  
} 
//测试是否为ip地址
function testIsIp(){
	var hardwareIp=$("#hardwareIp").val();
	var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
    if (reSpaceCheck.test(hardwareIp)) {
    	hardwareIp.match(reSpaceCheck);
        if (RegExp.$1 <= 255 && RegExp.$1 >= 0 
        		&& RegExp.$2 <= 255 && RegExp.$2 >= 0 
        		&& RegExp.$3 <= 255 && RegExp.$3 >= 0 
        		&& RegExp.$4 <= 255 && RegExp.$4 >= 0) {
        	//do nothing
        }
        else {
        	alert("您输入的ip地址不合法")
        }
    }
    else {
    	alert("您输入的ip地址不合法")
    }
}
</script>
<script type="text/javascript">
//增加全选功能
function checkAll()
  {
    if($("#check_all").attr("checked"))
    {
      $(":checkbox").attr("checked", true);
    }
    else
    {
      $(":checkbox").attr("checked", false);
    }
  }
  // 添加设备全选
function checkDeviceAll() {
    if($("#checkall").attr("checked"))
    {
        $(":checkbox").attr("checked", true);
    }
    else
    {
        $(":checkbox").attr("checked", false);
    }
}

function sss(){

        var array=new Array();
        var flag; //判断是否一个未选   
        $("input[name='CK']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox  
                    if ($(this).attr("checked")) { //判断是否选中    
                        flag = true; //只要有一个被选择 设置为 true  
                    }  
                })  

        if (flag) {  
           $("input[name='CK']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox  
                        if ($(this).attr("checked")) { //判断是否选中
                            array.push($(this).val()); //将选中的值 添加到 array中 
                        }  
                    })  
           //alert(array);         
           //将要所有要添加的数据传给后台处理   
		   window.location.href="${pageContext.request.contextPath}/labRoom/saveLabRoomDevice?roomId=${labRoom.id}&array="+array+"&type=${type}";
        } else {   
        	alert("请至少选择一条记录");  
        	}  
    	}
 
 //保存软件
 function saveSoftware(){
        var array=new Array();
        var flag; //判断是否一个未选   
        $("input[name='CK']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox  
                    if ($(this).attr("checked")) { //判断是否选中    
                        flag = true; //只要有一个被选择 设置为 true  
                    }  
                })  

        if (flag) {  
           $("input[name='CK']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox  
                        if ($(this).attr("checked")) { //判断是否选中
                            array.push($(this).val()); //将选中的值 添加到 array中 
                        }  
                    })  
           //alert(array);         
           //将要所有要添加的数据传给后台处理   
		   window.location.href="${pageContext.request.contextPath}/labRoom/saveLabRoomSoftware?roomId=${labRoom.id}&array="+array+"&type=${type}";
        } else {   
        	alert("请至少选择一条记录");  
        	}  
    	}
 
//查询设备
function querySchoolDevice(){
    var roomId=document.getElementById("roomId").value;
	var name=$("#deviceName").val();
	var number=$("#deviceNumber").val();
	//var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
	var deviceAddress=$("#deviceAddress").val();
	$.ajax({
	   	url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceForLab?roomId="+roomId+"&name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page=1",
       	type:"POST",
       	success:function(data){//AJAX查询成功
            document.getElementById("body").innerHTML=data;
       	}
	});
}
//取消查询设备
function cancelSchoolDevice(){
    document.getElementById("roomId").value="";
	document.getElementById("deviceName").value=" ";
	document.getElementById("deviceNumber").value=" ";
	document.getElementById("deviceAddress").value=" ";
	$.ajax({
	   	url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceForLab?roomId=&name=&number=&deviceAddress=&page=1",
       	type:"POST",
       	
       	success:function(data){//AJAX查询成功
            document.getElementById("body").innerHTML=data;
       	    document.getElementById("deviceName").value="";
       	}
	});
}
//取消查询软件
function cancelSoftware(){
	document.getElementById("name").value=" ";
	document.getElementById("edition").value=" ";
	$.ajax({
	   	url:"${pageContext.request.contextPath}/labRoom/findSoftwareByNameAndEdition?name=&edition=&page=1",
       	type:"POST", 
       	success:function(data){//AJAX查询成功
            document.getElementById("body1").innerHTML=data;
       	}
	});
}
//查询软件
function querySoftware(){
	var name=document.getElementById("name").value;
	var edition=document.getElementById("edition").value;
	$.ajax({
		
			   url:"${pageContext.request.contextPath}/labRoom/findSoftwareByNameAndEdition?name="+name+"&edition="+edition+"&page=1",           
	           type:"POST",
	           success:function(data){//AJAX查询成功
	                  document.getElementById("body1").innerHTML=data;
	            
	           }
	});
	  
}

//取消查询
function cancelQuery(){
	document.getElementById("deviceName").value="";
	document.getElementById("deviceNumber").value="";
	//document.getElementById("maxDeviceNumber").value="";
	document.getElementById("deviceAddress").value="";
	var name="";
	var number="";
	var deviceAddress="";
	//var maxDeviceNumber="";
	$.ajax({
		       url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page=1",
	           
	           type:"POST",
	           success:function(data){//AJAX查询成功
	                  document.getElementById("body").innerHTML=data;
	            
	           }
	         });
}
//首页
function first(page){
	var name=document.getElementById("deviceName").value;
	var number=document.getElementById("deviceNumber").value;
	//var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
	var deviceAddress=document.getElementById("deviceAddress").value;
	$.ajax({
		       url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,
	           
	           type:"POST",
	           success:function(data){//AJAX查询成功
	                  document.getElementById("body").innerHTML=data;
	            
	           }
	});
}
//上一页
function previous(page){
	if(page==1){
			page=1;
		}else{
			page=page-1;
		}
	var name=document.getElementById("deviceName").value;
	var number=document.getElementById("deviceNumber").value;
	//var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
	var deviceAddress=document.getElementById("deviceAddress").value;
	$.ajax({
		       url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,
	           
	           type:"POST",
	           success:function(data){//AJAX查询成功
	                  document.getElementById("body").innerHTML=data;
	            
	           }
	});
}
//下一页
function next(page,totalPage){
	if(page>=totalPage){
			page=totalPage;
		}else{
			page=page+1
		}
	var name=document.getElementById("deviceName").value;
	var number=document.getElementById("deviceNumber").value;
	//var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
	var deviceAddress=document.getElementById("deviceAddress").value;
	$.ajax({
		       url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,
	           
	           type:"POST",
	           success:function(data){//AJAX查询成功
	                  document.getElementById("body").innerHTML=data;
	            
	           }
	});
}

//软件首页
function firstSoft(page){
    var roomId=document.getElementById("roomId").value;
    var name=document.getElementById("name").value;
    var number=document.getElementById("edition").value;
    $.ajax({
        url:"${pageContext.request.contextPath}/labRoom/findSoftwareByNameAndEdition?name="+name+"&page="+page,

        type:"POST",
        success:function(data){//AJAX查询成功
            document.getElementById("body1").innerHTML=data;

        }
    });
}
//软件添加上一页
function previousSoft(page){
    if(page==1){
        page=1;
    }else{
        page=page-1;
    }
    var roomId=document.getElementById("roomId").value;
    var name=document.getElementById("name").value;
    var number=document.getElementById("edition").value;
    $.ajax({
        url:"${pageContext.request.contextPath}/labRoom/findSoftwareByNameAndEdition?name="+name+"&page="+page,

        type:"POST",
        success:function(data){//AJAX查询成功
            document.getElementById("body1").innerHTML=data;

        }
    });
}
//软件添加下一页
function nextSoft(page,totalPage){
    if(page>=totalPage){
        page=totalPage;
    }else{
        page=page+1
    }
var roomId=document.getElementById("roomId").value;
var name=document.getElementById("name").value;
var number=document.getElementById("edition").value;
$.ajax({
    url:"${pageContext.request.contextPath}/labRoom/findSoftwareByNameAndEdition?name="+name+"&page="+page,

    type:"POST",
    success:function(data){//AJAX查询成功
        document.getElementById("body1").innerHTML=data;

    }
});

}

//软件添加末页
function lastSoft(page){
    var roomId=document.getElementById("roomId").value;
    var name=document.getElementById("name").value;
    var number=document.getElementById("edition").value;
    $.ajax({
        url:"${pageContext.request.contextPath}/labRoom/findSoftwareByNameAndEdition?name="+name+"&page="+page,

        type:"POST",
        success:function(data){//AJAX查询成功
            document.getElementById("body1").innerHTML=data;

        }
    });
}
//末页
function last(page){
	var name=document.getElementById("deviceName").value;
	var number=document.getElementById("deviceNumber").value;
	//var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
	var deviceAddress=document.getElementById("deviceAddress").value;
	$.ajax({
		       url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,
	           
	           type:"POST",
	           success:function(data){//AJAX查询成功
	                  document.getElementById("body").innerHTML=data;
	            
	           }
	});
}
</script>
<script type="text/javascript">
function addUser(type1){
        var array=new Array();
        var flag; //判断是否一个未选   
        $("input[name='CK_name']:checkbox").each(function() { //遍历所有的name为CK_name的 checkbox  
                    if ($(this).attr("checked")) { //判断是否选中    
                        flag = true; //只要有一个被选择 设置为 true  
                    }  
                })  

        if (flag) {  
           $("input[name='CK_name']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox  
                        if ($(this).attr("checked")) { //判断是否选中
                            array.push($(this).val()); //将选中的值 添加到 array中 
                        }  
                    })  
           //alert(array);         
           var typeId=document.getElementById("adminType").value;
           if(type1==2) {
           	typeId=document.getElementById("adminType2").value;
           }
           //将要所有要添加的数据传给后台处理   
		   window.location.href="${pageContext.request.contextPath}/labRoom/saveLabRoomAdmin?roomId=${labRoom.id}&array="+array+"&typeId="+typeId+"&type=${type}";
        } else {   
        	alert("请至少选择一条记录");  
        	}  
    	}

function queryUser(typeId){
		var cname;
		var username;
		var roomId;
	if(typeId==1){
		 cname=document.getElementById("cname").value;
		 username=document.getElementById("username1").value;
		 roomId=${labRoom.id};
	}
	if(typeId==2){
		 cname=document.getElementById("cname2").value;
		 username=document.getElementById("username2").value;
		 roomId=${labRoom.id};
	}
	
	$.ajax({
	           url:"${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername",
	           data: {'cname':cname,'username':username,'roomId':roomId,'page':1,'typeId':typeId},
	           type:"POST",
	           success:function(data){//AJAX查询成功
	        	   if(typeId==1){
	        		   document.getElementById("user_body").innerHTML=data;
	        		}
	        		if(typeId==2){
	        			document.getElementById("user_body2").innerHTML=data;
	        		}

	           }
	});
	  
}
function cancleQuery(typeId){
	var cname="";
	var username="";
	$.ajax({
        url:"${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&roomId=${labRoom.id}&page=1&typeId="+typeId,
        type:"POST",
        success:function(data){//AJAX查询成功
            if(typeId==1){
                document.getElementById("user_body").innerHTML=data;
            }
            if(typeId==2){
                document.getElementById("user_body2").innerHTML=data;
            }

        }
});
}

//首页
function firstPage(page,typeId){
    var cname=document.getElementById("cname").value;
    var username=document.getElementById("username").value;
    $.ajax({
        url:"${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&roomId=${labRoom.id}&page="+page+"&typeId="+typeId,
        type:"POST",
        success:function(data){//AJAX查询成功
            if(typeId==1){
                document.getElementById("user_body").innerHTML=data;
            }
            if(typeId==2){
                document.getElementById("user_body2").innerHTML=data;
            }

        }
    });
}
//上一页
function previousPage(page,typeId){
    if(page==1){
        page=1;
    }else{
        page=page-1;
    }
    var cname=document.getElementById("cname").value;
    var username=document.getElementById("username").value;
    $.ajax({
        url:"${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&roomId=${labRoom.id}&page="+page+"&typeId="+typeId,
        type:"POST",
        success:function(data){//AJAX查询成功
            if(typeId==1){
                document.getElementById("user_body").innerHTML=data;
            }
            if(typeId==2){
                document.getElementById("user_body2").innerHTML=data;
            }

        }
    });
}
//下一页
function nextPage(page,totalPage,typeId){
    if(page>=totalPage){
        page=totalPage;
    }else{
        page=page+1
    }
    var cname=document.getElementById("cname").value;
    var username=document.getElementById("username").value;
    $.ajax({
        url:"${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&roomId=${labRoom.id}&page="+page+"&typeId="+typeId,
        type:"POST",
        success:function(data){//AJAX查询成功
            if(typeId==1){
                document.getElementById("user_body").innerHTML=data;
            }
            if(typeId==2){
                document.getElementById("user_body2").innerHTML=data;
            }
        }
    });
}
//末页
function lastPage(page,typeId){
    var cname=document.getElementById("cname").value;
    var username=document.getElementById("username").value;
    $.ajax({
        url:"${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&roomId=${labRoom.id}&page="+page+"&typeId="+typeId,
        type:"POST",
        success:function(data){//AJAX查询成功
            if(typeId==1){
                document.getElementById("user_body").innerHTML=data;
            }
            if(typeId==2){
                document.getElementById("user_body2").innerHTML=data;
            }

        }
    });
}
</script>
<script type="text/javascript">
	function view_ctrl(idKey,ctrl_ip) {
        var index = layer.open({
            type: 2,
            title: '投影设备列表',
            fix: true,
            maxmin:true,
            shift:-1,
            closeBtn: 1,
            shadeClose: true,
            move:false,
            area: ['1000px', '420px'],
            content: '${pageContext.request.contextPath }/labRoom/viewProjectors/'+ctrl_ip+'/'+idKey,
            end: function(){
            }
        });
        layer.full(index);
    }
    function closeMyWindow(){
        var index=parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
</script>
<script type="text/javascript">
    function checkAllItem() {
        if($("#Item").attr("checked")) {
            $(":checkbox").attr("checked", true);
        }else {
            $(":checkbox").attr("checked", false);
        }
    }
    function checkAlladmin() {
        if($("#admin").attr("checked")) {
            $(":checkbox").attr("checked", true);
        }else {
            $(":checkbox").attr("checked", false);
        }
    }
    function checkAllSoftWare() {
        if($("#SoftWare").attr("checked")) {
            $(":checkbox").attr("checked", true);
        }else {
            $(":checkbox").attr("checked", false);
        }
    }
    function checkAllDevice() {
        if($("#device").attr("checked")) {
            $(":checkbox").attr("checked", true);
        }else {
            $(":checkbox").attr("checked", false);
        }
    }
    function checkAllLabRoomAdmin() {
        if($("#labroomadmin").attr("checked")) {
            $(":checkbox").attr("checked", true);
        }else {
            $(":checkbox").attr("checked", false);
        }
    }
    //批量删除实验项目
    function batchDeleteLabRoomOperationItem(){
        var array=new Array();
        var flag=false; //判断是否一个未选
        $("input[name='operationItems']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
            if ($(this).is(':checked')) { //判断是否选中
                flag = true; //只要有一个被选择 设置为 true
            }
        })

        if (flag) {
            $("input[name='operationItems']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
                if ($(this).is(':checked')) { //判断是否选中
                    array.push($(this).val()); //将选中的值 添加到 array中
                }
            })
            window.location.href="${pageContext.request.contextPath}/labRoom/batchDeleteLabRoomOperationItem?&array="+array+"&roomId=${labRoom.id}&type=${type}";
        }else {
            alert("请至少选择一条记录");
        }
    }
    //批量删除物联管理员
    function batchDeleteLabRoomAdmin(){
        var array=new Array();
        var flag=false; //判断是否一个未选
        $("input[name='admin']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
            if ($(this).is(':checked')) { //判断是否选中
                flag = true; //只要有一个被选择 设置为 true
            }
        })

        if (flag) {
            $("input[name='admin']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
                if ($(this).is(':checked')) { //判断是否选中
                    array.push($(this).val()); //将选中的值 添加到 array中
                }
            })
            window.location.href="${pageContext.request.contextPath}/labRoom/batchDeleteLabRoomAdmin?&array="+array+"&roomId=${labRoom.id}&type=${type}";
        }else {
            alert("请至少选择一条记录");
        }
    }
    //批量删除软件
    function batchDeleteLabRoomSoftware(){
        var array=new Array();
        var flag=false; //判断是否一个未选
        $("input[name='software']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
            if ($(this).is(':checked')) { //判断是否选中
                flag = true; //只要有一个被选择 设置为 true
            }
        })

        if (flag) {
            $("input[name='software']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
                if ($(this).is(':checked')) { //判断是否选中
                    array.push($(this).val()); //将选中的值 添加到 array中
                }
            })
            window.location.href="${pageContext.request.contextPath}/labRoom/batchDeleteLabRoomSoftWare?&array="+array+"&roomId=${labRoom.id}&type=${type}";
        }else {
            alert("请至少选择一条记录");
        }
    }
    //批量删除仪器设备
    function batchDeleteLabDevice(){
        var array=new Array();
        var flag=false; //判断是否一个未选
        $("input[name='labDevice']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
            if ($(this).is(':checked')) { //判断是否选中
                flag = true; //只要有一个被选择 设置为 true
            }
        })

        if (flag) {
            $("input[name='labDevice']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
                if ($(this).is(':checked')) { //判断是否选中
                    array.push($(this).val()); //将选中的值 添加到 array中
                }
            })
            window.location.href="${pageContext.request.contextPath}/labRoom/batchDeleteLabDevice?&array="+array+"&roomId=${labRoom.id}&type=${type}";
        }else {
            alert("请至少选择一条记录");
        }
    }
function importAdmin(typeId){
//获取当前屏幕的绝对位置
        var topPos=window.pageYOffset;
        if(typeId==1) {
            $("#searchFile1").window({left: "100px", top: topPos + "px"});
            $("#searchFile1").window('open');
        }else if(typeId==2){
            $("#searchFile").window({left: "100px", top: topPos + "px"});
            $("#searchFile").window('open');
		}
    }
    function importDevice(){
//获取当前屏幕的绝对位置
        var topPos=window.pageYOffset;
            $("#searchFile2").window({left: "100px", top: topPos + "px"});
            $("#searchFile2").window('open');
    }
function  saveLabRoomAdmin(typeId){
    var formData = new FormData();
    if(typeId==2) {
        formData.append("myfile", document.getElementById("file_upload_ori").files[0]);
    }else{
        formData.append("myfile", document.getElementById("file_upload_ori1").files[0]);
	}
    //将要所有要添加的数据传给后台处理
    $.ajax({
        url:"${pageContext.request.contextPath}/labRoom/importLabRoomAdmin?roomId=${labRoom.id}&typeId="+typeId+"&type=${type}",
        type:"POST",
        async:false,
        cache: false,
        contentType: false,
        processData: false,
        dataType: 'text',
        data:formData,
        success:function(saveResult)
        {
           alert(saveResult);
           window.location.href="${pageContext.request.contextPath}/labRoom/getLabRoom?currpage=1&id=${labRoom.id}&type=${type}"
        },
        error:function () {
            alert("请求错误");
        }
    });
}
    function  saveLabRoomDevice(){
        var formData = new FormData();
		formData.append("myfile", document.getElementById("file_upload_ori2").files[0]);
        //将要所有要添加的数据传给后台处理
        $.ajax({
            url:"${pageContext.request.contextPath}/labRoom/importLabRoomDevice?roomId=${labRoom.id}&type=${type}",
            type:"POST",
            async:false,
            cache: false,
            contentType: false,
            processData: false,
            dataType: 'text',
            data:formData,
            success:function(saveResult)
            {
                alert(saveResult);
                window.location.href="${pageContext.request.contextPath}/labRoom/getLabRoom?currpage=1&id=${labRoom.id}&type=${type}"
            },
            error:function () {
                alert("请求错误");
            }
        });
    }
    function uploadDocument(typeId){
        $("#searchFile").window({top: 100});
        $("#searchFile").window('open');
        $("#file_upload").uploadify({
            'formData':{roomId:'${labRoom.id}',type:'${type}',typeId:typeId},    //传值
            'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',
            //提交的controller和要在火狐下使用必须要加的id
            'uploader':'${pageContext.request.contextPath}/labRoom/importLabRoomAdmin;jsessionid=<%=session.getId()%>',
            buttonText:'选择文件',
            //上传成功之后在列表追加显示文档
            'onUploadSuccess' : function(file, data, response) {
                alert(data);
            },
            onQueueComplete : function(data) {//当队列中的所有文件全部完成上传时触发
                //当上传完所有文件的时候关闭对话框并且转到显示界面
                window.location.href="${pageContext.request.contextPath}/labRoom/getLabRoom?currpage=1&id=${labRoom.id}&type=${type}"

            }
        });

    }
    function uploadDevice(){
        $("#searchFile1").window({top: 100});
        $("#searchFile1").window('open');
        $("#file_upload1").uploadify({
            'formData':{roomId:'${labRoom.id}',type:'${type}'},    //传值
            'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',
            //提交的controller和要在火狐下使用必须要加的id
            'uploader':'${pageContext.request.contextPath}/labRoom/importLabRoomDevice;jsessionid=<%=session.getId()%>',
            buttonText:'选择文件',
            //上传成功之后在列表追加显示文档
            'onUploadSuccess' : function(file, data, response) {
                alert(data);
            },
            onQueueComplete : function(data) {//当队列中的所有文件全部完成上传时触发
                //当上传完所有文件的时候关闭对话框并且转到显示界面
                window.location.href="${pageContext.request.contextPath}/labRoom/getLabRoom?currpage=1&id=${labRoom.id}&type=${type}"

            }
        });

    }
</script>

<style>
td {
	word-wrap: break-word
}
.btn {
	padding: 2px 12px;
}
</style>
	<script type="text/javascript">
		function changeOps() {
			var hardware_id = $("#hardware_id").val();
            $("#module1").css("display", "none");
            $("#module2").css("display", "table-cell");
            $("#doorindex1").css("display", "none");
            $("#doorindex2").css("display", "table-cell");;
            $("#doorIndex").attr("disabled","disabled");
            $("#doorIndexInput").attr("disabled",false);
            $("#harewareModule").attr("disabled","disabled");
            $("#harewareModuleInput").attr("disabled",false);
			if(hardware_id==550){
                $("#module1").css("display", "table-cell");
                $("#module2").css("display", "none");
                $("#doorindex1").css("display", "table-cell");
                $("#doorindex2").css("display", "none");
                $("#doorIndexInput").attr("disabled", true);
                $("#doorIndex").removeAttr("disabled");
                $("#harewareModuleInput").attr("disabled",true);
                $("#harewareModule").removeAttr("disabled");
			}else {
                $("#module1").css("display", "none");
                $("#module2").css("display", "table-cell");
                $("#doorindex1").css("display", "none");
                $("#doorindex2").css("display", "table-cell");
                $("#doorIndex").attr("disabled","disabled");
                $("#doorIndexInput").attr("disabled",false);
                $("#harewareModule").attr("disabled","disabled");
                $("#harewareModuleInput").attr("disabled",false);
			}

            // 关键点：触发 select 的 `chosen:updated` 事件
            $("#doorindex").trigger('liszt:updated');
            $("#doorindex").chosen();
        }

        function changeDoor() {
            // if ($(obj).options.selected(0)){
		     //    console.log(1);
			// }
			var count = 0;
			if($("#harewareModule").val() == "1拖1"){
			    count = 1;
			}
            if($("#harewareModule").val() == "1拖4"){
                count = 4;
            }
            if($("#harewareModule").val() == "1拖8"){
                count = 8;
            }
            var doorindex = document.getElementById("doorindex");
            doorindex.options.length = 0;
            doorindex.add(new Option("请选择", "", true, true));
			for(var i = 1; i <= count; i++){
                doorindex.add(new Option(i, i, false, false));
			}

            // 关键点：触发 select 的 `chosen:updated` 事件
            $("#doorindex").trigger('liszt:updated');
            $("#doorindex").chosen();
/*            var doorindexArray=[
                ["请选择"],
                ["1"],
                ["1、2、3、4"],
                ["1、2、3、4、5、6、7、8"]
            ];
			var module=document.getElementById("harewareModule");
            var doorindex = document.getElementById("doorindex");
			var s=doorindexArray[module.selectedIndex-1];
			doorindex.length=1;
            for(var i=0;i<s.length;i++){   //一定要用var作为声明类型
                doorindex[i+1]=new Option(s[i],s[i]);
            }*/
        }
        //实验室禁用时间表单提交
        function submitLimitTimeForm(){
            if($("#startTimeLimit").val()==''){
                alert("请填写起始时间")
            }else if($("#endTimeLimit").val()==''){
                alert("请填写结束时间")
            }else if($("#startDateLimit").val()==''){
                alert("请填写起始日期")
            }else if($("#endDateLimit").val()==''){
                alert("请填写结束日期")
            }else if($("#weekday1Limit").val()==''){
                alert("请选择星期")
            }else{
                document.labRoomLimitTimeForm.submit();
            }
        }
//        开放设置保存
        function saveDeviceOpenTime() {
            var startHour = $("#startHour").val();
            var startMinute = $("#startMinute").val();
            var endHour = $("#endHour").val();
            var endMinute = $("#endMinute").val();
            var startHourInweekend = $("#startHourInweekend").val();
            var endHourInweekend = $("#endHourInweekend").val();
            if (parseInt(startHourInweekend) > parseInt(endHourInweekend)) {
                alert("开始时间不能大于结束时间，请重新选择!");
            }
            else if (parseInt(startHour) > parseInt(endHour)) {
                alert("开始时间不能大于结束时间，请重新选择!");
            } else {
                document.openTimeForm.submit();
                $('#editOpenTime').hide();
                $('#showOpenTime').show();
                $('#show_open_time').show();
            }
        }
	</script>
	<style type="text/css">
		.title{
			display: block;
		}
		.panel {
			border: 1px solid #D3D3D3;
		}
		.chzn-container-multi .chzn-choices li.search-field input[type="text"]{
			width:300px!important;
			height: 23px;
		}
		.window .window-body {
			padding: 0;
		}
	</style>
</head>
<body>
	<!-- 实训室信息开始 -->
	<div class="tit-box">
		${labRoom.labRoomName} <a class="btn btn-new"
			onclick="closeMyWindow()">返回<spring:message code="all.trainingRoom.labroom"/>管理页</a>
	</div>
	<!-- 左侧栏目开始 -->
	<div class="edit-left" style="width:98%">
		<!-- 实训室详情开始 -->
		<div class="edit-content-box">
			<div class="title">
				<div id="title"><spring:message code="all.trainingRoom.labroom"/>详情</div>
				<!-- <a class="btn btn-edit">修改</a> -->
			</div>
			<div class="edit-content">
				<table>
					<tr>

						<th><spring:message code="all.trainingRoom.labroom"/>名称</th>
						<td>${labRoom.labRoomName}</td>
						<th><spring:message code="all.trainingRoom.labroom"/>编号</th>
						<td>${labRoom.labRoomNumber}</td>
						<th>楼宇(楼层)</th>
						<td>${labRoom.systemBuild.buildName}(${labRoom.floorNo}楼)</td>
						<th><spring:message code="all.trainingRoom.labroom"/>地点</th>
						<td>${labRoom.systemRoom.roomName}</td>
					</tr>
					<tr>
						<th><spring:message code="all.trainingRoom.labroom"/>英文名称</th>
						<td>${labRoom.labRoomEnName}</td>
						<th><spring:message code="all.trainingRoom.labroom"/>简称</th>
						<td>${labRoom.labRoonAbbreviation}</td>
						<%--<th>实训室类别</th>
						<td>${labRoom.CDictionaryByLabRoom.CName}</td>
						--%>
						<th>使用面积</th>
						<td>${labRoom.labRoomArea}</td>
						<th><spring:message code="all.trainingRoom.labroom"/>容量</th>
						<td>${labRoom.labRoomCapacity}</td>
					</tr>
					<tr>
						<%--<th>联系方式</th>
						<td></td>
						--%>
						<th>是否可用</th>
						<c:if test="${labRoom.labRoomActive==1}">
							<td>可用</td>
						</c:if>
						<c:if test="${labRoom.labRoomActive==0}">
							<td>不可用</td>
						</c:if>
						<th>是否可预约</th>
						<c:if test="${labRoom.labRoomReservation==0}">
							<td>不可预约</td>
						</c:if>
						<c:if test="${labRoom.labRoomReservation==1}">
							<td>可预约</td>
						</c:if>
						<th>排课是否判冲</th>
						<c:if test="${labRoom.isSpecial eq 0 || empty labRoom.isSpecial}">
							<td>是</td>
						</c:if>
						<c:if test="${labRoom.isSpecial eq 1}">
							<td>否</td>
						</c:if>
						<th>规章制度</th>
						<td>${labRoom.labRoomRegulations}</td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th><spring:message code="all.trainingRoom.labroom"/>描述</th>
						<td colspan="6">${labRoom.labRoomIntroduction}</td>
					</tr>
					<tr>
						<th><spring:message code="all.trainingRoom.labroom"/>注意事项</th>
						<td colspan="6">${labRoom.labRoomRegulations}</td>
					</tr>
					<tr>
						<th>获奖信息</th>
						<td colspan="6">${labRoom.labRoomPrizeInformation}</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 实训室详情结束 -->


		<div class="edit-content-box" style="overflow:initial;">
			<ul id="myTab" class="nav nav-tabs">
				<c:if test="${type ne 4}">
				<li class="active" id="myTab1"><a href="javascript:void(0)" data-toggle="tab">实验项目</a></li>
				</c:if>
				<li id="myTab2"><a href="javascript:void(0)" data-toggle="tab">物联硬件</a>
				</li>
				<c:if test="${proj_name ne 'zjcclims'}"><!-- 浙江建设物联名单和方正对接 -->
				<li id="myTab3"><a href="javascript:void(0)" data-toggle="tab">物联管理员</a>
				</li>
				</c:if>
				<li id="myTab4"><a href="javascript:void(0)" data-toggle="tab">软件列表</a>
				</li>
				<li id="myTab5"><a href="javascript:void(0)" data-toggle="tab">仪器设备</a>
				</li>
				<li id="myTab6"><a href="javascript:void(0)" data-toggle="tab"><spring:message code="all.trainingRoom.labroom"/>管理员</a>
				</li>
				<c:if test="${type ne 4}">
				<li id="myTab7"><a href="javascript:void(0)" data-toggle="tab">机房电脑使用记录</a>
				</li>
				</c:if>
				<c:if test="${proj_name ne 'zjcclims'}"><!-- 浙江建设物联名单和方正对接 -->
				<li id="myTab8"><a href="javascript:void(0)" data-toggle="tab">授权名单管理</a>
				</li>
				<li id="myTab9"><a href="javascript:void(0)" data-toggle="tab">操作日志</a>
				</li>
				</c:if>
				<li id="myTab10"><a href="javascript:void(0)" data-toggle="tab">实验室设备预约禁用时间段</a>
				</li>
				<li id="myTab11"><a href="javascript:void(0)" data-toggle="tab">实验室设备预约开放时间段</a>
				</li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="p1">
					<!-- 实验项目开始 -->
					<div class="edit-content-box">
						<div class="title">
							<div id="title">实验项目</div>
							<c:if test="${flag==true}">
								<a class="btn btn-new" href="javascript:void(0)"
									onclick="addOperationItem();">添加实验项目</a>
							</c:if>
							<a class="btn btn-new" href="javascript:void(0);" onclick="batchDeleteLabRoomOperationItem();">批量删除</a>
						</div>
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/getLabRoom?id=${labRoom.id}&currpage=1&type=${type}" method="post" modelAttribute="operationItem">
			 <ul>
  				<li><spring:message code="all.training.item"/>名称:
  				<form:select path="lpName" id="lpName">
					<form:option value=""></form:option>
					<c:forEach items="${operationItems}" var="curr">
					    <form:option value="${curr.lpName}">${curr.lpName}</form:option>
					</c:forEach>
				</form:select>
  				</li>
  				<li>所属课程:
  				<form:select path="schoolCourseInfo.courseName" id="schoolCourseInfo.courseName">
					<form:option value=""></form:option>
					<c:forEach items="${operationItems}" var="curr">
					    <form:option value="${curr.schoolCourseInfo.courseName}">${curr.schoolCourseInfo.courseName}</form:option>
					</c:forEach>
				</form:select>
  				</li>
				<li>
					<input type="submit" value="查询"/>
			      <input class="cancel-submit" type="button" value="取消" onclick="window.location.href='${pageContext.request.contextPath}/labRoom/getLabRoom?id=${labRoom.id}&currpage=1&type=${type}';"/>
			      </li>
  			</ul>
  		</form:form>
	</div>
						<div class="edit-content">
							<table class="tb" id="my_show">
								<thead>
									<tr>
										<th><input id="Item" type="checkbox" onclick="checkAllItem();"/></th>
										<th><spring:message code="all.training.item"/>名称</th>
										<th>所属课程</th>
										<th>实验类型</th>
										<th>实验类别</th>
										<th>面向专业</th>
										<th>实验学时</th>
										<th>实验要求</th>
										<c:if test="${flag==true}">
											<th>操作</th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${operationItem1}" var="m">
										<tr align="center">
											<td><input id="check_${m.id}" name="operationItems" type="checkbox" value="${m.id}"/></td>
											<td>${m.lpName}</td>
											<td>[${m.schoolCourseInfo.courseNumber}]${m.schoolCourseInfo.courseName}</td>
											<td>${m.CDictionaryByLpCategoryApp.CName}</td>
											<td>${m.CDictionaryByLpCategoryMain.CName}</td>
											<td>${m.lpMajorFit}</td>
											<td>${m.lpDepartmentHours}</td>
											<td>${m.CDictionaryByLpCategoryRequire.CName}</td>
											<c:if test="${flag==true}">
												<td><a
													href="${pageContext.request.contextPath}/labRoom/deleteLabRoomOperationItem?roomId=${labRoom.id}&id=${m.id}&type=${type}"
													onclick="return confirm('确认删除吗？')">删除 </a>
												</td>
											</c:if>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<!-- 添加实验项目 -->
						<div id="addOperationItem" class="easyui-window " title="添加实验项目"
							align="left" title="" modal="true" maximizable="false"
							collapsible="false" closed="true" minimizable="false"
							style="width: 500px; height: 300px;">
								<table>
									<tr>
										<td>实验项目卡名称</td>
									</tr>
									<tr>
										<td><input type="hidden" id="operationItem"
											name="operationItem"> <select id="operation"
											name="operation" class="chzn-select" multiple="multiple">
												<c:forEach items="${items}" var="m">
													<option value="${m.id}">${m.lpName}(${m.lpCodeCustom})</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td><input type="button" value="提交" onclick="getValue();">
										</td>
										<td><input type="button" value="取消"></td>
									</tr>
								</table>
						</div>
					</div>
					<!-- 实验项目结束 -->
				</div>
				<div class="tab-pane fade" id="p2">
					<!-- 物联硬件开始 -->
					<c:if test="${flag==true}">
						<div class="edit-content-box">
							<div class="title">
								<div id="title">物联硬件</div>
								<sec:authorize
									ifAnyGranted="ROLE_SUPERADMIN,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_DEPARTMENTHEADER,ROLE_COLLEGELEADER">
									<a class="btn btn-new" onclick="addAgent();">添加硬件</a>
								</sec:authorize>
							</div>
							<div class="edit-content">
								<table class="tb" id="my_show" style="table-layout:fixed">
									<thead>
										<tr>
											<th>硬件名称</th>
											<th>IP</th>
											<th style="width:10%">制造商</th>
										    <th>SN/电表号</th>
											<th>服务器</th>
											<c:if test="${flag }">
												<th>远程</th>
												<sec:authorize ifNotGranted="ROLE_CABINETADMIN">
												<th>操作</th>
												</sec:authorize>
											</c:if>
										</tr>
									</thead>
									<c:forEach items="${agentList}" var="agent" varStatus="i">
										<tr align="center">
											<td>
												<c:if test="${(agent.CDictionary.CNumber=='2' || agent.CDictionary.CNumber=='6') && agent.CDictionary.CCategory=='c_agent_type'}">
													<!-- 门禁 -->
													<c:if test="${flag && agent.doorindex ne null}">
														<a href="${pageContext.request.contextPath}/lab/entranceList?id=${agent.id}&page=1">${agent.doorindex}号门${agent.CDictionary.CName}</a>
													</c:if>
													<c:if test="${flag && agent.doorindex eq null}">
														<a href="${pageContext.request.contextPath}/lab/entranceList?id=${agent.id}&page=1">${agent.CDictionary.CName}</a>
													</c:if>
													<c:if test="${!flag && agent.doorindex ne null}">
														${agent.doorindex}号门${agent.CDictionary.CName}
													</c:if>
													<c:if test="${!flag && agent.doorindex eq null}">
														${agent.CDictionary.CName}
													</c:if>
												</c:if>
												<c:if test="${(agent.CDictionary.CNumber!='2' && agent.CDictionary.CNumber!='6' && agent.CDictionary.CCategory=='c_agent_type')}">
													${agent.CDictionary.CName}
												</c:if>
											</td>
											<td>${agent.hardwareIp}</td>
											<td>${agent.manufactor}</td>
											<td>${agent.snNo}</td>
											<td>${agent.commonServer.serverName}</td>
											<c:if test="${flag }">
												<td>
													<c:choose>
														<c:when test="${agent.CDictionary.CNumber eq '2'}">
															<%--<a href="javascript:void(0)"--%>
															   <%--onclick="opendoor(${agent.id});">开门</a>--%>
															<c:if test="${newServer eq 'true'}"><!-- 新版物联 -->
																<a href="javascript:void(0)" onclick="openDoorNew(${agent.id},${agent.doorindex});">开门</a>
															</c:if>
															<c:if test="${newServer ne 'true'}">
																<a href="javascript:void(0)" onclick="opendoor(${agent.id});">开门</a>
															</c:if>
														</c:when>
														<c:when test="${agent.CDictionary.CNumber eq '6'}">
															<a href="javascript:void(0)"
															   onclick="opendoor_scr(${agent.id});">开门</a>
														</c:when>
														<c:when test="${agent.CDictionary.CNumber eq '3'}">
															<a href="javascript:void(0)"
															   onclick="openVideo1('${labRoom.id}','${agent.id}');">开视频</a>
															<sec:authorize
																	ifAnyGranted="ROLE_SUPERADMIN,ROLE_LABMANAGER,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR">
																<br/>
																<c:if test="${yuntai eq true}">
																	<a href="javascript:void(0)"
																	   onclick="openVideoSet('${labRoom.id}','${agent.id}');">调视频</a>
																</c:if>
															</sec:authorize>
														</c:when>
														<c:when test="${agent.CDictionary.CNumber eq '4'}">
															<sec:authorize ifNotGranted="ROLE_CABINETADMIN">
																<c:if test="${newServer eq 'true'}"><!-- 新版物联 -->
																	<a href="javascript:void(0)" onclick="openAgentNew(1,${agent.id})">开电源</a>
																	<a href="javascript:void(0)" onclick="openAgentNew(0,${agent.id})">关电源</a>
																</c:if>
																<c:if test="${newServer ne 'true'}">
																	<a href="javascript:void(0)" onclick="openAgent(1,${agent.id})">开电源</a>
																	<a href="javascript:void(0)" onclick="openAgent(0,${agent.id})">关电源</a>
																</c:if>
															</sec:authorize>
														</c:when>
														<c:when test="${agent.CDictionary.CNumber eq '7'}">
															<a href="javascript:void(0)" onclick="view_ctrl(${labRoom.id},'${agent.hardwareIp}');">查看</a>
														</c:when>
														<c:otherwise>未定义</c:otherwise>
													</c:choose>
												</td>
												<sec:authorize ifNotGranted="ROLE_CABINETADMIN">
													<td><a href="${pageContext.request.contextPath}/labRoom/updateLabRoomAgent?id=${agent.id}&type=${type}">修改</a>
														<a onclick="return confirm('确认要删除吗？')"
														   href="${pageContext.request.contextPath}/labRoom/deleteLabRoomAgent?id=${agent.id}&type=${type}">删除</a>
													</td>
												</sec:authorize>
											</c:if>
										</tr>
									</c:forEach>

								</table>
							</div>
						</div>
					</c:if>
					<!-- 添加物联硬件 -->
					<div id="addAgent" class="easyui-window " title="添加物联硬件"
						align="left" title="" modal="true" maximizable="false"
						collapsible="false" closed="true" minimizable="false"
						style="width: 500px; height: 300px;">
						<form:form
							action="${pageContext.request.contextPath}/labRoom/saveLabRoomAgent?roomId=${labRoom.id}&type=${type}"
							modelAttribute="agent">

							<table class="tb" id="my_show">
								<tr>
									<td>硬件名称：</td>
									<td><form:select path="CDictionary.id" class="chzn-select" id="hardware_id" onchange="changeOps();">
										<c:forEach items="${types}" var="t">
											<c:if test="${t.CNumber ne '7' || !isCtrl}">
												<form:option value="${t.id}">${t.CName}</form:option>
											</c:if>
										</c:forEach>
										</form:select>
									</td>
									<td>Ip：</td>
									<td><form:input path="hardwareIp" id="hardwareIp" onchange="testIsIp()" /></td>
								</tr>

								<tr>
									<td>制造商：</td>
									<td><form:select path="manufactor" class="chzn-select">
									<form:option value="">请选择</form:option>
									<form:option value="adkfp">adkfp</form:option>
									<form:option value="aopu">aopu</form:option>
									<form:option value="gvsun">gvsun</form:option>
									<form:option value="wiegand">wiegand</form:option>
									</form:select></td>
                                    <td>规格:</td>
                                    <td id="module1" style="display: none;">
                                        <form:select id="harewareModule" path="harewareModule"  class="chzn-select" onchange="changeDoor()">
                                            <form:option value="">请选择</form:option>
                                            <form:option value="1拖1">1拖1</form:option>
                                            <form:option value="1拖4">1拖4</form:option>
                                            <form:option value="1拖8">1拖8</form:option>
                                        </form:select>
                                    </td>
                                    <td id="module2" style="display: table-cell;"><input id="harewareModuleInput" name="harewareModuleInput" /></td>
								</tr>
								<tr>
									<td>设备号(门号)：</td>
									<td id="doorindex1" style="display: none;">
										<form:select id="doorindex" path="doorindex" class="chzn-select">
										<form:option value="">请选择</form:option>
										<form:option value="1">1</form:option>
										<form:option value="2">2</form:option>
										<form:option value="3">3</form:option>
										<form:option value="4">4</form:option>
										<form:option value="5">5</form:option>
										<form:option value="6">6</form:option>
										<form:option value="7">7</form:option>
										<form:option value="8">8</form:option>

									</form:select>
									</td>
									<td id="doorindex2" style="display: table-cell;"><input id="doorIndexInput" name="doorIndexInput" onkeyup="value=value.replace(/[^\d]/g,'') "/></td>
									<td>version：</td>
									<td><form:input path="hardwareVersion"  /></td>
								</tr>
                                <tr>
                                    <td>SN/电表号：</td>
                                    <td><form:input path="snNo" /></td>
									<td>物联服务器：</td>
									<td><form:select path="commonServer.id" class="chzn-select">
											<form:options items="${serverList}" itemLabel="serverName" itemValue="id" />
										</form:select></td>
								</tr>
                                <tr>
                                    <td colspan="2"><input type="submit" value="提交"></td>
                                </tr>
							</table>
						</form:form>
					</div>
					<div id="openVIdeo" class="easyui-window " title="查看视频"
						align="left" title="" modal="true" maximizable="false"
						collapsible="false" closed="true" minimizable="false"
						style="width: 1000px; height: 500px;"></div>
					<!-- 添加物联硬件结束 -->
					<!-- 物联硬件结束 -->
				</div>
				<div class="tab-pane fade" id="p3">
					<!-- 物联管理员 -->
					<div class="edit-content-box">
						<div class="title">
							<div id="title">物联管理员</div>
							<c:if test="${authLevel gt 0 && authLevel lt 7}">
								<sec:authorize ifNotGranted="ROLE_CABINETADMIN">
									<c:if test="${not empty Access}">
										<%--<a id="refreshing1" class="btn btn-new" onclick="refreshPermissions();"--%>
										   <%--href="javascript:void(0)">刷新权限</a>--%>
										<c:if test="${newServer eq 'true'}"><!-- 新版物联 -->
											<a id="refreshing1" class="btn btn-new" onclick="refreshPerm();"  href="javascript:void(0)">刷新权限</a>
										</c:if>
										<c:if test="${newServer ne 'true' && proj_name ne 'zjcclims'}">
											<a id="refreshing1" class="btn btn-new" onclick="refreshPermissions();"  href="javascript:void(0)">刷新权限</a>
										</c:if>
										<c:if test="${newServer ne 'true' && proj_name eq 'zjcclims'}">
											<a id="refreshing1" class="btn btn-new" onclick="refreshZjcc();"  href="javascript:void(0)">刷新权限</a>
										</c:if>
									</c:if>
									<a class="btn btn-new" href="javascript:void(0)" onclick="addAdmin(2);">添加物联管理员</a>
									<a class="btn btn-new" href="javascript:void(0);" onclick="batchDeleteLabRoomAdmin();">批量删除</a>
									<input class="btn btn-new" type="button" value="批量导入" onclick="importAdmin(2);"/>
								</sec:authorize>
							</c:if>
						</div>
						<div class="edit-content">
							<table class="tb" id="my_show">
								<thead>
									<tr>
										<th><input id="admin" type="checkbox" onclick="checkAlladmin();"/></th>
										<th>姓名</th>
										<th>工号</th>
										<c:if test="${authLevel gt 0 && authLevel lt 7}">
											<th>操作</th>
										</c:if>
									</tr>
								</thead>
								<c:forEach items="${agentAdmin}" var="admin">
									<tr align="center">
										<td><input id="check_${admin.id}" name="admin" type="checkbox" value="${admin.id}"/></td>
										<td>${admin.user.cname}</td>
										<td>${admin.user.username}</td>
										<c:if test="${authLevel gt 0 && authLevel lt 7}">
											<td><a
												href="${pageContext.request.contextPath}/labRoom/deleteLabRoomAdmin?id=${admin.id}&type=${type}"
												onclick="return confirm('你确定删除吗？')">删除</a>
											</td>
										</c:if>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
					<!-- 添加物联管理员 -->
					<div id="addAdmin2" class="easyui-window " title="添加物联管理员"
						align="left" title="" modal="true" maximizable="false"
						collapsible="false" closed="true" minimizable="false"
						style="width: 600px; height: 500px;">
						<div class="content-box">
							<form:form id="userForm2" method="post" modelAttribute="admin">
								<table class="tb" id="my_show2">
									<tr>
										<td>姓名：<form:input id="cname2" path="user.cname" />
										</td>
										<td>工号：<form:input id="username2" path="user.username" />
											<a onclick="queryUser(2);">搜索</a> <a onclick="cancleQuery(2);">取消</a>
										</td>
										<td><input type="hidden" id="adminType2"> <input
											type="button" value="添加" onclick="addUser(2);"></td>
									</tr>
								</table>
							</form:form>

							<table id="my_show2">
								<thead>
									<tr>
										<th style="width:10% !important">选择</th>
										<th style="width:30% !important">姓名</th>
										<th style="width:30% !important">工号</th>
										<th style="width:30% !important">所属学院</th>

									</tr>
								</thead>

								<tbody id="user_body2">

								</tbody>
							</table>
						</div>
					</div>
					<!-- 添加物联管理员结束 -->
					<!-- 物联管理员结束 -->
				</div>
				<div class="tab-pane fade" id="p4">
					<!-- 软件开始 -->
					<div class="edit-content-box">
						<div class="title">
							<div id="title">软件列表</div>
							<c:if test="${flag==true}">
								<a class="btn btn-new" onclick="addSoftware()">添加软件</a>
							</c:if>
							<a class="btn btn-new" href="javascript:void(0);" onclick="batchDeleteLabRoomSoftware();">批量删除</a>
						</div>
						<div class="edit-content">
							<table class="tb" id="my_show">
								<thead>
									<tr>
										<th width="10%"><input id="SoftWare" type="checkbox" onclick="checkAllSoftWare();"/></th>
										<th width="10%">软件序号</th>
										<th width="10%">软件名称</th>
										<th width="10%">软件版本</th>
										<th width="10%">价格</th>
										<th width="10%">有无加密狗</th>
										<c:if test="${flag==true}">
											<th width="20%">操作</th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${softwareList}" var="software" varStatus="i">
										<tr align="center">
											<td><input id="check_${software.id}" name="software" type="checkbox" value="${software.id}"/></td>
											<td>${software.id}</td>
											<td>${software.name}</td>
											<td>${software.edition}</td>
											<td>${software.price}</td>
											<td><c:if test="${software.dongle==1}">
								有
								</c:if> <c:if test="${software.dongle==0}">
								无
								</c:if></td>
											<td><c:if test="${flag==true}">
													<a
														href="${pageContext.request.contextPath}/labRoom/deleteLabRoomSoftware?softwareId=${software.id}&&labRoomId=${labRoom.id}&type=${type}"
														onclick="return confirm('你确定删除吗？')">删除</a>
												</c:if></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!-- 添加软件开始 -->
					<div id="openwindow1" class="easyui-window " title="添加软件"
						align="left" title="" maximizable="false" collapsible="false"
						closed="true" minimizable="false"
						style="width: 800px; height: 500px;">
						<div class="content-box">
							<form:form id="queryForm1" method="post"
								modelAttribute="software">
								<table>
									<tr>
										<input id="roomId" value="${labRoom.id}" type="hidden">
										<td>软件名称: <form:input id="name" path="name" /></td>
										<td>软件版本: <form:input id="edition" path="edition" /></td>
										<td><input type="button" value="搜索"
											onclick="querySoftware();"></td>
										<td><input type="button" value="取消"
											onclick="cancelSoftware()"></td>
										<td><input type="button" value="添加"
											onclick="saveSoftware();"></td>
									</tr>
								</table>
							</form:form>
							<table class="eq" id="my_show">
								<thead>
									<tr>
										<th style="width:10% !important">软件序号</th>
										<th style="width:10% !important">软件名称</th>
										<th style="width:15% !important">软件版本</th>
										<th style="width:20% !important">价格</th>
										<th style="width:20% !important">有无加密狗</th>
										<th><input id="check_all" type="checkbox"
											onclick="checkAll();" />
										</th>
									</tr>
								</thead>
								<tbody id="body1">
								</tbody>
							</table>
						</div>
					</div>
					<!-- 添加软件结束 -->
					<!-- 实验室软件结束 -->
				</div>
				<div class="tab-pane fade" id="p5">
					<!-- 仪器设备开始 -->
					<div class="edit-content-box">
						<div class="title">
							<div id="title">仪器设备</div>
							<c:if test="${flag==true}">
								<a class="btn btn-new" onclick="openwin() ">添加设备</a>
								<a class="btn btn-new" href="javascript:void(0);" onclick="batchDeleteLabDevice();">批量删除</a>
                                <input class="btn btn-new" type="button" value="批量导入" onclick="importDevice();"/>
							</c:if>
						</div>
						<div class="edit-content">
							<table class="tb" id="my_show">
								<thead>
									<tr>
										<th width="5%"><input id="device" type="checkbox" onclick="checkAllDevice();"/></th>
										<th width="5%">序号</th>
										<th width="20%">设备名称</th>
										<th width="20%">设备编号</th>
										<c:if test="${flag==true}">
											<th width="20%">操作</th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${labDeviceList}" var="labDevice"
										varStatus="i">
										<tr align="center">
											<td><input id="check_${labDevice.id}" name="labDevice" type="checkbox" value="${labDevice.id}"/></td>
											<td>${i.count}</td>
											<td>${labDevice.schoolDevice.deviceName}</td>
											<td>${labDevice.schoolDevice.deviceNumber}</td>
											<td>
												<!-- 预约的权限未配置，待确定 --> <%-- <c:if test="${labDevice.CActiveByAllowAppointment.id==1}">
								<a href="javascript:void(0)" onclick="Access('${labDevice.id}');">预约</a>&nbsp;&nbsp;
								</c:if> --%> <c:if test="${flag==true}">
													<a
														href="${pageContext.request.contextPath}/labRoom/deleteLabRoomDeviceNew?labDeviceId=${labDevice.id}&type=${type}"
														onclick="return confirm('你确定删除吗？')">删除</a>
													<%--<a data-id="${labDevice.id }" href="#">添加硬件</a>--%>
													<%-- <a href="javascript:void(0)" onclick="setDeviceInfo('${labDevice.id}','${labDevice.schoolDevice.deviceNumber}');">设置</a> --%>
												</c:if></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>


						<div id="openwindow" class="easyui-window " title="添加设备"
							align="left" title="" maximizable="false" collapsible="false"
							closed="true" minimizable="false"
							style="width: 800px; height: 500px;">
							<div class="content-box">
								<form:form id="queryForm" method="post"
									modelAttribute="schoolDevice">
									<table>
										<tr>
											<td>设备名称: <form:input id="deviceName" path="deviceName" />
											</td>
											<td>设备编号: <input type="text" id="deviceNumber"
												placeholder="请输入数字" maxlength="40" validType="length[0,9]" />
											</td>
											<td>所属<spring:message code="all.trainingRoom.labroom"/>: <form:input id="deviceAddress"
													path="deviceAddress" /></td>
											<td><input type="button" value="搜索"
												onclick="querySchoolDevice();"></td>

											<td><input type="button" value="取消"
												onclick="cancelSchoolDevice()"></td>

											<td><input type="button" value="添加" onclick="sss();">
											</td>
										</tr>
									</table>
								</form:form>
								<table class="eq" id="my_show">
									<thead>
										<tr>
											<th style="width:10% !important">设备编号</th>
											<th style="width:15% !important">设备名称</th>
											<th style="width:20% !important">设备型号</th>
											<%--<th style="width:10% !important">保管员</th>--%>
											<th style="width:10% !important">保管员</th>
											<th style="width:10% !important">设备规格</th>
											<th style="width:20% !important">设备价格</th>
											<%--<th style="width:4% !important">选择</th>	--%>
											<th><input id="checkall" type="checkbox"
												onclick="checkDeviceAll();" />
											</th>
										</tr>
									</thead>

									<tbody id="body">

									</tbody>
								</table>
							</div>
						</div>
						<!-- 添加设备页面 结束-->
					</div>
					<!-- 仪器设备结束 -->
				</div>
				<div class="tab-pane fade" id="p6">
					<!-- 实训室管理者开始 -->
					<div class="edit-content-box">
						<div class="title">
							<div id="title"><spring:message code="all.trainingRoom.labroom"/>管理员</div>
							<c:if test="${authLevel gt 0 && authLevel lt 6}">
								<c:if test="${not empty Access}">
									<%--<a id="refreshing2" class="btn btn-new" onclick="refreshPermissions();"  href="javascript:void(0)">刷新权限</a>--%>
									<c:if test="${newServer eq 'true'}"><!-- 新版物联 -->
										<a id="refreshing2" class="btn btn-new" onclick="refreshPerm();" href="javascript:void(0)">刷新权限</a>
									</c:if>
									<c:if test="${newServer ne 'true' && proj_name ne 'zjcclims'}">
										<a id="refreshing1" class="btn btn-new" onclick="refreshPermissions();"  href="javascript:void(0)">刷新权限</a>
									</c:if>
									<c:if test="${newServer ne 'true' && proj_name eq 'zjcclims'}">
										<a id="refreshing1" class="btn btn-new" onclick="refreshZjcc();"  href="javascript:void(0)">刷新权限</a>
									</c:if>
								</c:if>
								<a class="btn btn-new" onclick="addAdmin(1) ">添加<spring:message code="all.trainingRoom.labroom"/>管理员</a>
								<a class="btn btn-new" href="javascript:void(0);" onclick="batchDeleteLabRoomAdmin();">批量删除</a>
								<input class="btn btn-new" type="button" value="批量导入" onclick="importAdmin(1);"/>
							</c:if>
						</div>
						<div class="edit-content">
							<table class="tb" id="my_show">
								<thead>
									<tr>
										<th width="20%"><input id="labroomadmin" type="checkbox" onclick="checkAllLabRoomAdmin();"/></th>
										<th width="20%">姓名</th>
										<th width="20%">工号</th>
										<c:if test="${authLevel gt 0 && authLevel lt 6}">
											<th width="5%">操作</th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${adminList}" var="admin">
										<tr align="center">
											<td><input id="check_${admin.id}" name="admin" type="checkbox" value="${admin.id}"/></td>
											<td>${admin.user.cname}</td>
											<td>${admin.user.username}</td>
											<c:if test="${authLevel gt 0 && authLevel lt 6}">
												<td><a
													href="${pageContext.request.contextPath}/labRoom/deleteLabRoomAdmin?id=${admin.id}&type=${type}"
													onclick="return confirm('你确定删除吗？')">删除</a>
												</td>
											</c:if>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!-- 添加管理员开始 -->
					<div id="addAdmin" class="easyui-window " title="添加<spring:message code="all.trainingRoom.labroom"/>管理员"
						align="left" title="" modal="true" maximizable="false"
						collapsible="false" closed="true" minimizable="false"
						style="width: 600px; height: 500px;">
						<div class="content-box">
							<form:form id="userForm" method="post" modelAttribute="admin">
								<table class="tb" id="my_show">
									<tr>
										<td>姓名：<form:input id="cname" path="user.cname" />
										</td>
										<td>工号：<form:input id="username1" path="user.username" />
											<a onclick="queryUser(1);">搜索</a> <a onclick="cancleQuery(1);">取消</a>
										</td>
										<td><input type="hidden" id="adminType"> <input
											type="button" value="添加" onclick="addUser(1);"></td>
									</tr>
								</table>
							</form:form>

							<table id="my_show">
								<thead>
									<tr>
										<th style="width:10% !important">选择</th>
										<th style="width:30% !important">姓名</th>
										<th style="width:30% !important">工号</th>
										<th style="width:30% !important">所属学院</th>

									</tr>
								</thead>

								<tbody id="user_body">

								</tbody>
							</table>
						</div>
					</div>
					<!-- 添加实训室管理员结束 -->
					<!-- 实训室管理者结束 -->
				</div>
	<div class="tab-pane fade" id="p7">
		<!-- 机房电脑使用记录开始 -->
		<div class="content-box">
    		<div class="title">
    			<div id="title">机房电脑使用列表</div>
    		</div>
    		<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/getLabRoom?id=${labRoom.id}&currpage=1&type=${type}" method="post" modelAttribute="viewUseOfLc">
			 <ul>
  				<li>使用人： </li>
  				<li>
  				<form:select path="username" id="username">
					<form:option value=""></form:option>
					<c:forEach items="${allViewUseOfLcLists}" var="curr" varStatus="a">
					    <form:option value="${curr.username}">${listUser[a.count-1].cname}</form:option>
					</c:forEach>
				</form:select>
  				</li>
  				<li>使用时间：</li>
  				<li>
				<li>
				<form:select path="" id="begintime" name="begintime">
					<form:option value=""></form:option>
					<c:forEach items="${allViewUseOfLcLists}" var="curr">
					    <form:option value="${curr.begintime.time}"><fmt:formatDate value="${curr.begintime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></form:option>
					</c:forEach>
				</form:select>
				</li>
				<li>到</li>
				<li>
				<form:select path="" id="endtime" name="endtime">
					<form:option value=""></form:option>
					<c:forEach items="${allViewUseOfLcLists}" var="curr">
					    <form:option value="${curr.endtime.time}"><fmt:formatDate value="${curr.endtime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></form:option>
					</c:forEach>
				</form:select>
				</li>
				</li>
				<li>
			      <input type="button" value="取消" onclick="window.location.href='${pageContext.request.contextPath}/labRoom/getLabRoom?id=${labRoom.id}&currpage=1&type=${type}';"/>
			      <input type="submit" value="查询"/></li>
  				</ul>

		</form:form>
	</div>
            <table class="tb" id="my_show">
                <thead>
                    <tr>
                    	<th>机器名称</th>
                        <th>使用人</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                    </tr>
                </thead>

                <tbody>
                		<c:forEach items="${allViewUseOfLcList}" var="curr" varStatus="a">
                		<tr>
                        <td>${curr.machinename}</td>
                        <td>${listUser[a.count-1].cname}</td>
                        <td><fmt:formatDate value="${curr.begintime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${curr.endtime.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                		</c:forEach>

                </tbody>
            </table>
            <!-- 分页模块
<div class="page" >
    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=1')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.lastPage}')" target="_self">末页</a>
</div>
<!-- 分页模块 -->
</div>

					<!-- 机房电脑使用记录结束 -->
				</div>
				<div class="tab-pane fade" id="p8">
					<!-- 授权名单管理开始 -->
					<div class="content-box">
						<div class="title">
							<div id="title">授权名单管理</div>
						</div>
<%--						<div class="tool-box">
							<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/getLabRoom?id=${labRoom.id}&currpage=1&type=${type}" method="post" modelAttribute="viewUseOfLc">
								<ul>
									<span>用户名：</span>
									<input id="cname" name="cname" type="text" value="${cname}"/>
									<span>学号：</span>
									<input id="username" name="username" type="text" value="${username}"/>
									<input type="submit" value="查询"/>
									<input type="button" value="取消" onclick="cancelQuery(${id});"/>
								</ul>
							</form:form>
						</div>--%>
						<div class="title" style="border-top:none;clear:both;">
							<div class="select_s" >
								<form name="form" action="${pageContext.request.contextPath}/labRoom/saveLabRoomAuthorized?roomId=${labRoom.id}&type=${type}" method="post" >
									<div class="tool-box" style="float:left;">
										<ul>
										<li>人员名称/编号：</li>
										<li>
										<select id="username3" name="username3" class="chzn-select">
											<%--<c:forEach items="${userList}" var="curr">--%>
												<%--<option value="${curr.username}">${curr.cname }${curr.username}</option>--%>
											<%--</c:forEach>--%>
										</select>
										</li>
										</ul>
									</div>
									<div style="float:left;">
										<span class="f14" style="float:left;">开始日期：</span>
										<input id="startDate" name="startDate" class="Wdate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:100px; margin-top: 1px;" readonly />
									</div>
									<div style="float:left;">
										<span class="f14" style="float:left;">结束日期：</span>
										<input id="endDate" name="endDate" class="Wdate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:100px; margin-top: 1px;" readonly />
									</div>
									<div style="float:left;">
										<span class="f14" style="float:left;">开始时间：</span>
										<input id="startTime" name="startTime" class="Wdate" type="text" onclick="WdatePicker({dateFmt:'HH:mm'})" style="width:100px; margin-top: 1px;" readonly />
									</div>
									<div style="float:left;">
										<span class="f14" style="float:left;">结束时间：</span>
										<input id="endTime" name="endTime" class="Wdate" type="text" onclick="WdatePicker({dateFmt:'HH:mm'})" style="width:100px; margin-top: 1px;" readonly />
									</div>
									<input class="search r btn btn-new" type="submit" value="添加" />
								</form>
							</div>
						</div>
					</div>
				<div class="edit-content">
					<table class="tb" id="my_show">
						<thead>
						<tr>
							<%--<th><input type="checkbox"  onclick="checkAll_admin();"  id="check-all-admin"/></th>--%>
							<th style="width:5%">序号</th>
							<th>人员</th>
							<th>开始日期</th>
							<th>结束日期</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>操作</th>
						</tr>
						</thead>
						<c:forEach items="${authorizeUsers}" var="admin" varStatus="i">
							<tr align="center">
								<%--<td><input type="checkbox"  name="CK_admin"  value="${admin.id }"/></td>--%>
								<td>${i.count}</td>
								<td>${admin.user.cname}(${admin.user.username})</td>
								<td><fmt:formatDate value="${admin.startDate.time}" pattern="yyyy-MM-dd "/></td>
								<td><fmt:formatDate value="${admin.endDate.time}" pattern="yyyy-MM-dd "/></td>
								<td><fmt:formatDate value="${admin.startTime.time}" pattern="HH:mm:ss "/></td>
								<td><fmt:formatDate value="${admin.endTime.time}" pattern="HH:mm:ss "/></td>
								<td>
									<a href="${pageContext.request.contextPath}/labRoom/updateLabRoomAuthorizeUser?id=${admin.id}&type=${type}">修改</a>
									<a onclick="return confirm('确认要删除吗？')"
									   href="${pageContext.request.contextPath}/labRoom/deleteLabRoomAuthorizeUser?id=${admin.id}&type=${type}">删除</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				</div>
				<div class="tab-pane fade" id="p9">
					<!-- 授权名单管理开始 -->
					<div class="content-box">
						<div class="title">
							<div id="title">操作日志</div>
						</div>
						<%--						<div class="tool-box">
                                                    <form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/getLabRoom?id=${labRoom.id}&currpage=1&type=${type}" method="post" modelAttribute="viewUseOfLc">
                                                        <ul>
                                                            <span>用户名：</span>
                                                            <input id="cname" name="cname" type="text" value="${cname}"/>
                                                            <span>学号：</span>
                                                            <input id="username" name="username" type="text" value="${username}"/>
                                                            <input type="submit" value="查询"/>
                                                            <input type="button" value="取消" onclick="cancelQuery(${id});"/>
                                                        </ul>
                                                    </form:form>
                                                </div>--%>
					</div>
					<div class="edit-content">
						<table class="tb" id="my_show">
							<thead>
							<tr>
								<%--<th><input type="checkbox"  onclick="checkAll_admin();"  id="check-all-admin"/></th>--%>
								<th style="width:5%">序号</th>
								<th>操作人</th>
								<th>具体操作</th>
							</tr>
							</thead>
							<c:forEach items="${logs}" var="logs" varStatus="i">
								<tr align="center">
										<%--<td><input type="checkbox"  name="CK_admin"  value="${admin.id }"/></td>--%>
									<td>${i.count}</td>
									<td>${logs[1]}(${logs[0]})</td>
									<td>${logs[2]}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				<div class="tab-pane fade" id="p10">
					<div class="content-box">
						<div class="title">
							<div id="title">
                                实验室设备禁用时间设置
							</div>
							<c:if test="${flag==true}">
								<a class="btn btn-new" onclick="newLimitTime() ">添加实验室禁用时间段</a>
							</c:if>
							<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/appointment/findLabRoomByLabAnnexId?annexId=${annexId}&page=1">返回列表页面</a>--%>
						</div>
					</div>
					<div class="edit-content">
						<table class="tb" id="my_show">
							<thead>
							<tr>
								<th width="35%">学期</th>
								<th width="15%">周次</th>
								<th width="15%">节次</th>
								<th width="15%">星期</th>
								<th width="10%">类型</th>
								<sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_LABMANAGER">
									<th width="10%">操作</th>
								</sec:authorize>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${labRoomLimitTimes}" var="admin">
								<tr align="center">
									<td>${admin.schoolTerm.termName }</td>
									<td>${admin.startweek}-${admin.endweek}周</td>
									<td>${admin.startclass}-${admin.endclass}节</td>
									<td>周${admin.weekday}</td>
									<td>
										<c:if test="${admin.flag eq 0 }">手动添加</c:if>
										<c:if test="${admin.flag eq 1 }">排课生成</c:if>
									</td>
									<%--<td>--%>
										<sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_LABMANAGER">
									<td><a onclick="return confirm('确定要删除吗？')" href="${pageContext.request.contextPath}/labRoomSetting/deleteLabRoomDeviceLimitTime?id=${admin.id}&&labRoomId=${labRoom.id}">删除</a></td>
									</sec:authorize>
									<%--</td>--%>
								</tr>
							</c:forEach>

							</tbody>
						</table>
					</div>
				</div>
				<div class="tab-pane fade" id="p11">
					<div class="content-box">
						<div class="title">
							<div id="title">实验室设备开放时间设置</div>
							<c:if test="${flag==true}">
								<a class="btn btn-new" id="show_open_time" onclick="editOpenTime() ">设置</a>
							</c:if>
							<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/appointment/findLabRoomByLabAnnexId?annexId=${annexId}&page=1">返回列表页面</a>--%>
						</div>
					</div>
					<div class="edit-content">
						<table id="showOpenTime">
							<tr>
								<th>是否允许周末预约</th>
								<td>
									<c:if test="${labRoom.openInweekend eq 1}">是
									</c:if>
									<c:if test="${labRoom.openInweekend eq 0}">否
									</c:if>
									<c:if test="${labRoom.openInweekend ne 0 && labRoom.openInweekend ne 1}">否
									</c:if>
								</td>
								<!--  <td style="width:15%"></th>
                                 <td style="width:15%"></td>
                                 <td style="width:15%"></th> -->
							</tr>
							<c:if test="${labRoom.openInweekend eq 1}">
								<tr>
									<th>周末预约起止时间</th>
									<td>
											${startWeekendHour}&nbsp;时${startWeekendMinute}&nbsp;分
										至
											${endWeekendHour}&nbsp;时${endWeekendMinute}&nbsp;分
									</td>
								</tr>
							</c:if>
							<tr>
								<th>工作日预约起止时间</th>
								<td>
									${startHour}&nbsp;时${startMinute}&nbsp;分
									至
									${endHour}&nbsp;时${endMinute}&nbsp;分
								</td>
							</tr>

						</table>
						<div id="editOpenTime" style="display: none">
							<form id="openTimeForm" name="openTimeForm"
								  action="${pageContext.request.contextPath}/labRoomSetting/saveLabRoomDeviceOpenTime?id=${labRoom.id}"
								  method="post" modelAttribute="labRoom">
								<%--<div class="edit-edit">--%>
								<div>
									<table>
										<!-- <span><font color="red">温馨提示：起止时间相关填写框，只允许输入数字。输入数字与时间的对应关系举例：8 为 8时整，8.25 为  8时15分， 8.5 为  8时30分， 8.75 为  8时45分</font></span> -->
										<tr>
											<th>是否允许周末预约</th>
											<td>
												<c:if test="${labRoom.openInweekend eq 1}">
													<input type="radio" name="openInweekend" checked="true" value="1"/>是
													<input type="radio" name="openInweekend" value="0"/>否
												</c:if>
												<c:if test="${labRoom.openInweekend eq 0}">
													<input type="radio" name="openInweekend" value="1"/>是
													<input type="radio" name="openInweekend" checked="true" value="0"/>否
												</c:if>
												<c:if test="${labRoom.openInweekend ne 0 && labRoom.openInweekend ne 1}">
													<input type="radio" name="openInweekend" value="1"/>是
													<input type="radio" name="openInweekend" value="0"/>否
												</c:if>
											</td>
											<!-- <td style="width:15%"></th>
                                            <td style="width:15%"></td>
                                            <td style="width:15%"></th> -->
										</tr>
										<tr id="weekendOpen">
											<th>周末预约起止时间</th>
											<td>
												<%--<select id="startHourInweekend" name="startHourInweekend">
                                                    <c:forEach begin="0" end="24" var="i">
                                                        <c:if test="${i eq labRoom.startHourInweekend}">
                                                        <option value="${i }" selected="selected">${i }</option>
                                                        </c:if>
                                                        <option value="${i }">${i }</option>
                                                    </c:forEach>
                                                </select>--%>
												<%-- <input id="startHourInweekend" value="${labRoom.startHourInweekend }"
                                                tpye="number" onkeyup="value=value.replace(/[^\d,^\.]/g,'') " name="startHourInweekend"/> --%>

												<%--  <input style="display: inline-block;width: 90%;" value="${labRoom.startHourInweekend }" name="startHourInweekend" type="text" class="layui-input" id="startHourInweekend" /> &nbsp;时 --%>
												<select class="chzn-select" id="startHourInweekend" name="startHourInweekend">
													<option value="0">请选择</option>
													<option value="0"
															<c:if test="${'0' eq startWeekendHour}">selected</c:if>>0
													</option>
													<option value="1"
															<c:if test="${'1' eq startWeekendHour}">selected</c:if>>1
													</option>
													<option value="2"
															<c:if test="${'2' eq startWeekendHour}">selected</c:if>>2
													</option>
													<option value="3"
															<c:if test="${'3' eq startWeekendHour}">selected</c:if>>3
													</option>
													<option value="4"
															<c:if test="${'4' eq startWeekendHour}">selected</c:if>>4
													</option>
													<option value="5"
															<c:if test="${'5' eq startWeekendHour}">selected</c:if>>5
													</option>
													<option value="6"
															<c:if test="${'6' eq startWeekendHour}">selected</c:if>>6
													</option>
													<option value="7"
															<c:if test="${'7' eq startWeekendHour}">selected</c:if>>7
													</option>
													<option value="8"
															<c:if test="${'8' eq startWeekendHour}">selected</c:if>>8
													</option>
													<option value="9"
															<c:if test="${'9' eq startWeekendHour}">selected</c:if>>9
													</option>
													<option value="10"
															<c:if test="${'10' eq startWeekendHour}">selected</c:if>>10
													</option>
													<option value="11"
															<c:if test="${'11' eq startWeekendHour}">selected</c:if>>11
													</option>
													<option value="12"
															<c:if test="${'12' eq startWeekendHour}">selected</c:if>>12
													</option>
													<option value="13"
															<c:if test="${'13' eq startWeekendHour}">selected</c:if>>13
													</option>
													<option value="14"
															<c:if test="${'14' eq startWeekendHour}">selected</c:if>>14
													</option>
													<option value="15"
															<c:if test="${'15' eq startWeekendHour}">selected</c:if>>15
													</option>
													<option value="16"
															<c:if test="${'16' eq startWeekendHour}">selected</c:if>>16
													</option>
													<option value="17"
															<c:if test="${'17' eq startWeekendHour}">selected</c:if>>17
													</option>
													<option value="18"
															<c:if test="${'18' eq startWeekendHour}">selected</c:if>>18
													</option>
													<option value="19"
															<c:if test="${'19' eq startWeekendHour}">selected</c:if>>19
													</option>
													<option value="20"
															<c:if test="${'20' eq startWeekendHour}">selected</c:if>>20
													</option>
													<option value="21"
															<c:if test="${'21' eq startWeekendHour}">selected</c:if>>21
													</option>
													<option value="22"
															<c:if test="${'22' eq startWeekendHour}">selected</c:if>>22
													</option>
													<option value="23"
															<c:if test="${'23' eq startWeekendHour}">selected</c:if>>23
													</option>
												</select>
												<span>时</span>
												<select class="chzn-select" id="startMinuteInweekend"
														name="startMinuteInweekend">
													<option value="00">请选择</option>
													<option value="00"
															<c:if test="${'0' eq startWeekendMinute}">selected</c:if>>00
													</option>
													<option value="15"
															<c:if test="${'15' eq startWeekendMinute}">selected</c:if>>15
													</option>
													<option value="30"
															<c:if test="${'30' eq startWeekendMinute}">selected</c:if>>30
													</option>
													<option value="45"
															<c:if test="${'45' eq startWeekendMinute}">selected</c:if>>45
													</option>
												</select>
												<span>分</span>
												至
												<%--<select id="endHourInweekend" name="endHourInweekend">
                                                    <c:forEach begin="0" end="24" var="i">
                                                       <c:if test="${i eq labRoom.endHourInweekend }">
                                                        <option value="${i }" selected="selected">${i }</option>
                                                        </c:if>
                                                        <option value="${i }">${i }</option>
                                                    </c:forEach>
                                                </select>--%>
												<%-- <input id="endHourInweekend" value="${labRoom.endHourInweekend }"
                                                tpye="number" onkeyup="value=value.replace(/[^\d,^\.]/g,'') " name="endHourInweekend"/> &nbsp;时 --%>
												<%--  <input style="display: inline-block;width: 90%;" value="${labRoom.endHourInweekend }" name="endHourInweekend" type="text" class="layui-input" id="endHourInweekend" />&nbsp;时 --%>
												<select class="chzn-select" id="endHourInweekend" name="endHourInweekend">
													<option value="0">请选择</option>
													<option value="0"
															<c:if test="${'0' eq startWeekendHour}">selected</c:if>>0
													</option>
													<option value="1" <c:if test="${'1' eq endWeekendHour}">selected</c:if>>
														1
													</option>
													<option value="2" <c:if test="${'2' eq endWeekendHour}">selected</c:if>>
														2
													</option>
													<option value="3" <c:if test="${'3' eq endWeekendHour}">selected</c:if>>
														3
													</option>
													<option value="4" <c:if test="${'4' eq endWeekendHour}">selected</c:if>>
														4
													</option>
													<option value="5" <c:if test="${'5' eq endWeekendHour}">selected</c:if>>
														5
													</option>
													<option value="6" <c:if test="${'6' eq endWeekendHour}">selected</c:if>>
														6
													</option>
													<option value="7" <c:if test="${'7' eq endWeekendHour}">selected</c:if>>
														7
													</option>
													<option value="8" <c:if test="${'8' eq endWeekendHour}">selected</c:if>>
														8
													</option>
													<option value="9" <c:if test="${'9' eq endWeekendHour}">selected</c:if>>
														9
													</option>
													<option value="10"
															<c:if test="${'10' eq endWeekendHour}">selected</c:if>>10
													</option>
													<option value="11"
															<c:if test="${'11' eq endWeekendHour}">selected</c:if>>11
													</option>
													<option value="12"
															<c:if test="${'12' eq endWeekendHour}">selected</c:if>>12
													</option>
													<option value="13"
															<c:if test="${'13' eq endWeekendHour}">selected</c:if>>13
													</option>
													<option value="14"
															<c:if test="${'14' eq endWeekendHour}">selected</c:if>>14
													</option>
													<option value="15"
															<c:if test="${'15' eq endWeekendHour}">selected</c:if>>15
													</option>
													<option value="16"
															<c:if test="${'16' eq endWeekendHour}">selected</c:if>>16
													</option>
													<option value="17"
															<c:if test="${'17' eq endWeekendHour}">selected</c:if>>17
													</option>
													<option value="18"
															<c:if test="${'18' eq endWeekendHour}">selected</c:if>>18
													</option>
													<option value="19"
															<c:if test="${'19' eq endWeekendHour}">selected</c:if>>19
													</option>
													<option value="20"
															<c:if test="${'20' eq endWeekendHour}">selected</c:if>>20
													</option>
													<option value="21"
															<c:if test="${'21' eq endWeekendHour}">selected</c:if>>21
													</option>
													<option value="22"
															<c:if test="${'22' eq endWeekendHour}">selected</c:if>>22
													</option>
													<option value="23"
															<c:if test="${'23' eq endWeekendHour}">selected</c:if>>23
													</option>
												</select>
												<span>时</span>
												<select class="chzn-select" id="endMinuteInweekend"
														name="endMinuteInweekend">
													<option value="00">请选择</option>
													<option value="00"
															<c:if test="${'0' eq endWeekendMinute}">selected</c:if>>00
													</option>
													<option value="15"
															<c:if test="${'15' eq endWeekendMinute}">selected</c:if>>15
													</option>
													<option value="30"
															<c:if test="${'30' eq endWeekendMinute}">selected</c:if>>30
													</option>
													<option value="45"
															<c:if test="${'45' eq endWeekendMinute}">selected</c:if>>45
													</option>
												</select>
												<span>分</span>
											</td>
										</tr>
										<tr>
											<th>工作日预约起止时间</th>
											<td>
												<%--<select id="startHour" name="startHour">
                                                    <c:forEach begin="0" end="24" var="i">
                                                        <c:if test="${i eq labRoom.startHour }">
                                                        <option value="${i }" selected="selected">${i }</option>
                                                        </c:if>
                                                        <option value="${i }">${i }</option>
                                                    </c:forEach>
                                                </select>--%>
												<%-- <input id="startHour" value="${labRoom.startHour }" tpye="number" onkeyup="value=value.replace(/[^\d,^\.]/g,'') " name="startHour"/> &nbsp;时 --%>
												<%-- <input style="display: inline-block;width: 90%;" value="${labRoom.startHour }" name="startHour" type="text" class="layui-input" id="startHour" /> &nbsp;时 --%>
												<select class="chzn-select" id='startHour' name='startHour'>
													<option value="0">请选择</option>
													<option value="0"
															<c:if test="${'0' eq startWeekendHour}">selected</c:if>>0
													</option>
													<option value="1" <c:if test="${'1' eq startHour}">selected</c:if>>1
													</option>
													<option value="2" <c:if test="${'2' eq startHour}">selected</c:if>>2
													</option>
													<option value="3" <c:if test="${'3' eq startHour}">selected</c:if>>3
													</option>
													<option value="4" <c:if test="${'4' eq startHour}">selected</c:if>>4
													</option>
													<option value="5" <c:if test="${'5' eq startHour}">selected</c:if>>5
													</option>
													<option value="6" <c:if test="${'6' eq startHour}">selected</c:if>>6
													</option>
													<option value="7" <c:if test="${'7' eq startHour}">selected</c:if>>7
													</option>
													<option value="8" <c:if test="${'8' eq startHour}">selected</c:if>>8
													</option>
													<option value="9" <c:if test="${'9' eq startHour}">selected</c:if>>9
													</option>
													<option value="10" <c:if test="${'10' eq startHour}">selected</c:if>>
														10
													</option>
													<option value="11" <c:if test="${'11' eq startHour}">selected</c:if>>
														11
													</option>
													<option value="12" <c:if test="${'12' eq startHour}">selected</c:if>>
														12
													</option>
													<option value="13" <c:if test="${'13' eq startHour}">selected</c:if>>
														13
													</option>
													<option value="14" <c:if test="${'14' eq startHour}">selected</c:if>>
														14
													</option>
													<option value="15" <c:if test="${'15' eq startHour}">selected</c:if>>
														15
													</option>
													<option value="16" <c:if test="${'16' eq startHour}">selected</c:if>>
														16
													</option>
													<option value="17" <c:if test="${'17' eq startHour}">selected</c:if>>
														17
													</option>
													<option value="18" <c:if test="${'18' eq startHour}">selected</c:if>>
														18
													</option>
													<option value="19" <c:if test="${'19' eq startHour}">selected</c:if>>
														19
													</option>
													<option value="20" <c:if test="${'20' eq startHour}">selected</c:if>>
														20
													</option>
													<option value="21" <c:if test="${'21' eq startHour}">selected</c:if>>
														21
													</option>
													<option value="22" <c:if test="${'22' eq startHour}">selected</c:if>>
														22
													</option>
													<option value="23" <c:if test="${'23' eq startHour}">selected</c:if>>
														23
													</option>
												</select>
												<span>时</span>
												<select class="chzn-select" id='startMinute' name='startMinute'>
													<option value="00">请选择</option>
													<option value="00" <c:if test="${'0' eq startMinute}">selected</c:if>>
														00
													</option>
													<option value="15" <c:if test="${'15' eq startMinute}">selected</c:if>>
														15
													</option>
													<option value="30" <c:if test="${'30' eq startMinute}">selected</c:if>>
														30
													</option>
													<option value="45" <c:if test="${'45' eq startMinute}">selected</c:if>>
														45
													</option>
												</select>
												<span>分</span>
												至
												<%--<select id="endHour" name="endHour">
                                                    <c:forEach begin="0" end="24" var="i">
                                                       <c:if test="${i eq labRoom.endHour }">
                                                        <option value="${i }" selected="selected">${i }</option>
                                                        </c:if>
                                                        <option value="${i }">${i }</option>
                                                    </c:forEach>
                                                </select>--%>
												<%--  <input id="endHour" value="${labRoom.endHour }" tpye="number" onkeyup="value=value.replace(/[^\d,^\.]/g,'') " name="endHour"/> &nbsp;时 --%>
												<%--  <input style="display: inline-block;width: 90%;" value="${labRoom.endHour }" name="endHour" type="text" class="layui-input" id="endHour" /> &nbsp;时 --%>
												<select class="chzn-select" id='endHour' name='endHour' value="${endHour}">

													<option value="0">请选择</option>
													<option value="0"
															<c:if test="${'0' eq startWeekendHour}">selected</c:if>>0
													</option>
													<option value="1" <c:if test="${'1' eq endHour}">selected</c:if>>1
													</option>
													<option value="2" <c:if test="${'2' eq endHour}">selected</c:if>>2
													</option>
													<option value="3" <c:if test="${'3' eq endHour}">selected</c:if>>3
													</option>
													<option value="4" <c:if test="${'4' eq endHour}">selected</c:if>>4
													</option>
													<option value="5" <c:if test="${'5' eq endHour}">selected</c:if>>5
													</option>
													<option value="6" <c:if test="${'6' eq endHour}">selected</c:if>>6
													</option>
													<option value="7" <c:if test="${'7' eq endHour}">selected</c:if>>7
													</option>
													<option value="8" <c:if test="${'8' eq endHour}">selected</c:if>>8
													</option>
													<option value="9" <c:if test="${'9' eq endHour}">selected</c:if>>9
													</option>
													<option value="10" <c:if test="${'10' eq endHour}">selected</c:if>>10
													</option>
													<option value="11" <c:if test="${'11' eq endHour}">selected</c:if>>11
													</option>
													<option value="12" <c:if test="${'12' eq endHour}">selected</c:if>>12
													</option>
													<option value="13" <c:if test="${'13' eq endHour}">selected</c:if>>13
													</option>
													<option value="14" <c:if test="${'14' eq endHour}">selected</c:if>>14
													</option>
													<option value="15" <c:if test="${'15' eq endHour}">selected</c:if>>15
													</option>
													<option value="16" <c:if test="${'16' eq endHour}">selected</c:if>>16
													</option>
													<option value="17" <c:if test="${'17' eq endHour}">selected</c:if>>17
													</option>
													<option value="18" <c:if test="${'18' eq endHour}">selected</c:if>>18
													</option>
													<option value="19" <c:if test="${'19' eq endHour}">selected</c:if>>19
													</option>
													<option value="20" <c:if test="${'20' eq endHour}">selected</c:if>>20
													</option>
													<option value="21" <c:if test="${'21' eq endHour}">selected</c:if>>21
													</option>
													<option value="22" <c:if test="${'22' eq endHour}">selected</c:if>>22
													</option>
													<option value="23" <c:if test="${'23' eq endHour}">selected</c:if>>23
													</option>
												</select>
												<span>时</span>
												<select class="chzn-select" id="endMinute" name="endMinute">
													<option value="00">请选择</option>
													<option value="00" <c:if test="${'0' eq endMinute}">selected</c:if>>00
													</option>
													<option value="15" <c:if test="${'15' eq endMinute}">selected</c:if>>
														15
													</option>
													<option value="30" <c:if test="${'30' eq endMinute}">selected</c:if>>
														30
													</option>
													<option value="45" <c:if test="${'45' eq endMinute}">selected</c:if>>
														45
													</option>
												</select>
												<span>分</span>
											</td>
										</tr>

									</table>

								</div>
								<div class="editOpenTime-action" style="overflow:hidden">
									<input style="    float: right;  margin: 0 10px 10px 0;" class="btn" id="save1"
										   type="button" value="保存" onclick="saveDeviceOpenTime()"/>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>


	</div>
	<!-- 左侧栏目结束 -->


	<!-- 右侧栏目开始 -->
	<div class="edit-right" style="width:49%">



		<%--<!-- 药品柜管理员 -->
		<div class="edit-content-box">
			<div class="title">
				<div id="title">药品柜管理员</div>
				<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER">
				<a class="btn btn-new" href="javascript:void(0)" onclick="addAdmin(3);">添加药品柜管理员</a>
				</sec:authorize>
			</div>
			<div class="edit-content">
				<table class="tb" id="my_show">
					<thead>
						<tr>
							<th>姓名</th>
							<th>工号</th>
							<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER">
							<th>操作</th>
							</sec:authorize>
						</tr>
					</thead>
						<c:forEach items="${cabinetAdmin}" var="admin">
						<tr align="center">
							<td>${admin.user.cname}</td>
							<td>${admin.user.username}</td>
							<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_ASSETMANAGEMENT,ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER">
							<td><a href="${pageContext.request.contextPath}/labRoom/deleteLabRoomAdmin?id=${admin.id}" onclick="return confirm('你确定删除吗？')">删除</a></td>
							</sec:authorize>
						</tr>
						</c:forEach>
				</table>
			</div>
		</div>
		<!-- 药品柜管理员结束 -->


		--%>
	</div>
	<!-- 右侧栏目结束 -->
    <!-- 导入管理员-->
	<div id="searchFile" class="easyui-window" title="导入物联管理员" closed="true" iconCls="icon-add" style="width:500px;height:300px">
		<form id="form_file_ori" name="form_file_ori" method="post"
			  enctype="multipart/form-data">
			<table  border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<div id="queue"></div> <input id="file_upload_ori"name="file_upload_ori" type="file" multiple="true">
						<input type="button" onclick="saveLabRoomAdmin(2)" value="导入" />
						<br> 下载：<a href=" ${pageContext.request.contextPath}/pages/importSample/importLabRoomAdmin.xls">下载范例 </a><br>
					</td>
					<!-- <div id="queue"></div>
                  <input id="file_upload" name="file_upload" type="file" multiple="true"> -->
				</tr>
			</table>
		</form>
	</div>
    <!-- 导入管理员结束-->
	<div id="searchFile1" class="easyui-window" title="导入实验室管理员" closed="true" iconCls="icon-add" style="width:500px;height:300px">
		<form id="form_file_ori1" name="form_file_ori" method="post"
			  enctype="multipart/form-data">
			<table  border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<div id="queue1"></div> <input id="file_upload_ori1"name="file_upload_ori" type="file" multiple="true">
						<input type="button" onclick="saveLabRoomAdmin(1)" value="导入" />
						<br> 下载：<a href=" ${pageContext.request.contextPath}/pages/importSample/importLabRoomAdmin.xls">下载范例 </a><br>
					</td>
					<!-- <div id="queue"></div>
                  <input id="file_upload" name="file_upload" type="file" multiple="true"> -->
				</tr>
			</table>
		</form>
	</div>
    <!-- 导入仪器设备-->
	<div id="searchFile2" class="easyui-window" title="导入仪器设备" closed="true" iconCls="icon-add" style="width:500px;height:300px">
		<form id="form_file_ori2" name="form_file_ori" method="post"
			  enctype="multipart/form-data">
			<table  border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<div id="queue2"></div> <input id="file_upload_ori2"name="file_upload_ori" type="file" multiple="true">
						<input type="button" onclick="saveLabRoomDevice()" value="导入" />
						<br> 下载：<a href=" ${pageContext.request.contextPath}/pages/importSample/importLabRoomDevice.xls">下载范例 </a><br>
					</td>
					<!-- <div id="queue"></div>
                  <input id="file_upload" name="file_upload" type="file" multiple="true"> -->
				</tr>
			</table>
		</form>
	</div>
    <!-- 导入仪器设备结束-->
	<%--添加实验室禁用时间段--%>
	<div id="addLabRoomLimitTime" class="easyui-window" title="禁用时间段设置" align="left" title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 800px; height: 300px;">
		<div class="content-box">
			<br>
			<form:form name="labRoomLimitTimeForm" action="${pageContext.request.contextPath}/labRoomSetting/saveLabRoomDeviceLimitTime" method="post"   modelAttribute="labRoomLimitTime">
				<input type="hidden" name="labId" id="labId" value="${labRoom.id}" >
				<table class="tb" id="my_show">

					<tr>
						<td>禁用时间</td>
						<td>
							<input id="startTimeLimit" class="Wdate" type="text" name="startTime"
								   onclick="WdatePicker({dateFmt:'HH:mm'})" style="width:140px;"
								   readonly />
						</td>
						<td>
							<input id="endTimeLimit" class="Wdate" type="text" name="endTime"
								   onclick="WdatePicker({dateFmt:'HH:mm'})" style="width:140px;"
								   readonly />
						</td>
					</tr>

					<tr>
						<td>禁用日期</td>
						<td>
							<input id="startDateLimit" class="Wdate" type="text" name="startDate"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:140px;"
								   readonly />
						</td>
						<td>
							<input id="endDateLimit" class="Wdate" type="text" name="endDate"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:140px;"
								   readonly />
						</td>
					</tr>

					<tr>
						<td>星期：</td>
						<td colspan="2">
							<select class="chzn-select" data-placeholder="请选择星期" multiple="multiple" id="weekday1Limit" name="weekday1" style="width:300px">
								<option value ="0" >全部 </option>
								<c:forEach var="var"  begin="1" end="7" varStatus="status">
									<option value ="${var}" >周${var} </option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<br>
				<%--<hr>--%>
				<input type="hidden" id="adminType">
				<input type="button" onclick="submitLimitTimeForm()" value="提交">
			</form:form>
		</div>
	</div>
	<!-- 下拉框的js -->
	<script
		src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
		type="text/javascript" charset="utf-8"></script>
	<script
		src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
		type="text/javascript" charset="utf-8"></script>
	<!-- <script
		src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script> -->
	<!-- <script
		src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
	<script type="text/javascript">
						    var config = {
						      '.chzn-select': {search_contains : true},
						      '.chzn-select-deselect'  : {allow_single_deselect:true},
						      '.chzn-select-no-single' : {disable_search_threshold:10},
						      '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
						      '.chzn-select-width'     : {width:"0%"}
						    }
						    for (var selector in config) {
						      $(selector).chosen(config[selector]);
						    }
	</script>
	<!-- 下拉框的js -->
	<script type="text/javascript">
					$(".btn-edit").click(function(){
						$(this).parent().next().slideUp(); //原信息隐藏
						$(this).parent().next().next().children(".edit-edit").slideDown();//修改信息显示
						$(this).hide();//修改按钮隐藏
						$(this).parent().next().next().children(".edit-action").show();	//返回保存按钮显示					
					});
					$(".btn-return").click(function(){
						$(this).parent().hide();//返回保存按钮隐藏
						$(this).parent().parent().prev().prev().children(".btn-edit").show();//修改按钮显示
						$(this).parent().prev().slideUp();//修改信息隐藏
						$(this).parent().parent().prev().slideDown();//原始信息显示
0
					});
	</script>
	<script  type="text/javascript">
	$(function () {
		$('#myTab li').click(function() {
		    var str = this.id;
			var index = parseInt(str.slice(5));
			$(this).addClass('active');
			$(this).siblings().removeClass('active');
			$('#p'+(index)).siblings().removeClass('active');
			$('#p'+(index)).addClass('active in');
			$.cookie("active",index,{"path":"/", expires:30})
		});
        var cookie_active = $.cookie("active");
        if(cookie_active!=null){
            $('#myTab li').siblings().removeClass('active');
            $('#myTab'+(cookie_active)).addClass('active');
            $('#p'+(cookie_active)).siblings().removeClass('active');
            $('#p'+(cookie_active)).addClass('active in');
        }
    });
	</script>
</body>
</html>